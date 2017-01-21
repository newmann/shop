/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web.admin;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 后台订单Controller
 * @author Tony Wong
 * @version 2015-04-07
 */
@Controller
@RequestMapping("/${adminPath}/order")
public class AdminOrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService itemService;

    @Autowired
    OrderStatusService orderStatusService;

	@ModelAttribute("order")
	public Order get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return orderService.getX(id);
		}
		else {
			return new Order();
		}
	}

	/**
	 * 所有订单
	 */
	@RequiresPermissions("order:view")
    @RequestMapping(value = "")
	public String index(@ModelAttribute Order order, HttpServletRequest request, HttpServletResponse response, ModelMap m) {
		Page<Order> nPage = new Page<Order>(request, response);
        nPage.setOrderBy("a.create_date desc");
        Page<Order> page = orderService.findPage(nPage, order);
        int i = 0;
        for (Order fOrder : page.getList()) {
            page.getList().set(i, orderService.getX(fOrder.getId()));
            i++;
        }

		m.put("page", page);
		return "modules/shop/admin/order/index";
	}

	/**
	 * 查看订单
	 */
	@RequiresPermissions("order:view")
	@RequestMapping(value = "/view")
	public String view(@ModelAttribute Order order, HttpServletRequest request, HttpServletResponse response, ModelMap m) {
		List<OrderItem> orderItemList = itemService.findByOrderId(order);
		m.put("orderItemList", orderItemList);
		return "modules/shop/admin/order/view";
	}

    /**
     * 更改订单状态 - 设置现金支付-配送中
     */
    @RequiresPermissions("order:edit")
    @RequestMapping(value = "/setCashStatusDelivering")
    public String setCashStatusDelivering(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        String message;
        String redirectUrl = adminPath + "/order/view?id=" + order.getId();

        if (order == null || StringUtils.isBlank(order.getId())) {
            message = "订单(ID:" + order.getId() + ")不存在";
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        //验证是否现金支付已下单
        if (!OrderStatus.STATUS_UNION_CASH_ORDERED.equals(order.getStatusUnion())) {
            message = "订单(ID:" + order.getId() + ")状态不是现金支付已下单，不能操作。订单的statusUnion=" + order.getStatusUnion();
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_CASH);
        orderStatus.setStatus(OrderStatus.STATUS_CASH_DELIVERING);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_CASH_DELIVERING);
        orderStatus.setLabel(OrderStatus.LABEL_CASH_DELIVERING);
        orderStatus.setPendingLabel(OrderStatus.LABEL_CASH_DELIVERING_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

        message = "设置成功";
        addMessage(redirectAttributes, message);
        return "redirect:" + redirectUrl;
    }

    /**
     * 更改订单状态 - 设置现金支付-已收货, 已付款, 交易完成
     */
    @RequiresPermissions("order:edit")
    @RequestMapping(value = "/setCashStatusReceived")
    public String setCashStatusReceived(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        String message;
        String redirectUrl = adminPath + "/order/view?id=" + order.getId();

        if (order == null || StringUtils.isBlank(order.getId())) {
            message = "订单(ID:" + order.getId() + ")不存在";
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        //验证是否现金支付配送中
        if (!OrderStatus.STATUS_UNION_CASH_DELIVERING.equals(order.getStatusUnion())) {
            message = "订单(ID:" + order.getId() + ")状态不是现金支付配送中，不能操作。订单的statusUnion=" + order.getStatusUnion();
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        //设置已收货
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_CASH);
        orderStatus.setStatus(OrderStatus.STATUS_CASH_RECEIVED);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_CASH_RECEIVED);
        orderStatus.setLabel(OrderStatus.LABEL_CASH_RECEIVED);
        orderStatus.setPendingLabel(OrderStatus.LABEL_CASH_RECEIVED_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

        //设置已付款
        orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_CASH);
        orderStatus.setStatus(OrderStatus.STATUS_CASH_PAID);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_CASH_PAID);
        orderStatus.setLabel(OrderStatus.LABEL_CASH_PAID);
        orderStatus.setPendingLabel(OrderStatus.LABEL_CASH_PAID_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        order.setHasPaid(Global.YES);
        orderService.save(order);

        //设置交易完成
        orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_CASH);
        orderStatus.setStatus(OrderStatus.STATUS_CASH_FINISHED);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_CASH_FINISHED);
        orderStatus.setLabel(OrderStatus.LABEL_CASH_FINISHED);
        orderStatus.setPendingLabel(OrderStatus.LABEL_CASH_FINISHED_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

        message = "设置成功";
        addMessage(redirectAttributes, message);
        return "redirect:" + redirectUrl;
    }

    /**
     * 更改订单状态 - 设置在线支付-已付款
     */
    @RequiresPermissions("order:edit")
    @RequestMapping(value = "/setOPStatusPaid")
    public String setOPStatusPaid(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        String message;
        String redirectUrl = adminPath + "/order/view?id=" + order.getId();

        if (order == null || StringUtils.isBlank(order.getId())) {
            message = "订单(ID:" + order.getId() + ")不存在";
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        //验证是否设置在线支付已下单
        if (!OrderStatus.STATUS_UNION_OP_ORDERED.equals(order.getStatusUnion())) {
            message = "订单(ID:" + order.getId() + ")状态不是在线支付已下单，不能操作。订单的statusUnion=" + order.getStatusUnion();
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
        orderStatus.setStatus(OrderStatus.STATUS_OP_PAID);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_PAID);
        orderStatus.setLabel(OrderStatus.LABEL_OP_PAID);
        orderStatus.setPendingLabel(OrderStatus.LABEL_OP_PAID_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        order.setHasPaid(Global.YES);
        orderService.save(order);

        message = "设置成功";
        addMessage(redirectAttributes, message);
        return "redirect:" + redirectUrl;
    }

    /**
     * 更改订单状态 - 设置在线支付-配送中
     */
    @RequiresPermissions("order:edit")
    @RequestMapping(value = "/setOPStatusDelivering")
    public String setOPStatusDelivering(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        String message;
        String redirectUrl = adminPath + "/order/view?id=" + order.getId();

        if (order == null || StringUtils.isBlank(order.getId())) {
            message = "订单(ID:" + order.getId() + ")不存在";
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        //验证是否设置在线支付已付款
        if (!OrderStatus.STATUS_UNION_OP_PAID.equals(order.getStatusUnion())) {
            message = "订单(ID:" + order.getId() + ")状态不是在线支付已付款，不能操作。订单的statusUnion=" + order.getStatusUnion();
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
        orderStatus.setStatus(OrderStatus.STATUS_OP_DELIVERING);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_DELIVERING);
        orderStatus.setLabel(OrderStatus.LABEL_OP_DELIVERING);
        orderStatus.setPendingLabel(OrderStatus.LABEL_OP_DELIVERING_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

        message = "设置成功";
        addMessage(redirectAttributes, message);
        return "redirect:" + redirectUrl;
    }

    /**
     * 更改订单状态 - 设置在线支付-已收货
     */
    @RequiresPermissions("order:edit")
    @RequestMapping(value = "/setOPStatusReceived")
    public String setOPStatusReceived(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        String message;
        String redirectUrl = adminPath + "/order/view?id=" + order.getId();

        if (order == null || StringUtils.isBlank(order.getId())) {
            message = "订单(ID:" + order.getId() + ")不存在";
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        //验证是否设置在线支付配送中
        if (!OrderStatus.STATUS_UNION_OP_DELIVERING.equals(order.getStatusUnion())) {
            message = "订单(ID:" + order.getId() + ")状态不是在线支付配送中，不能操作。订单的statusUnion=" + order.getStatusUnion();
            addMessage(redirectAttributes, message);
            return "redirect:" + redirectUrl;
        }

        //设置已收货
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
        orderStatus.setStatus(OrderStatus.STATUS_OP_RECEIVED);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_RECEIVED);
        orderStatus.setLabel(OrderStatus.LABEL_OP_RECEIVED);
        orderStatus.setPendingLabel(OrderStatus.LABEL_OP_RECEIVED_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

        //设置交易完成
        orderStatus = new OrderStatus();
        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
        orderStatus.setStatus(OrderStatus.STATUS_OP_FINISHED);
        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_FINISHED);
        orderStatus.setLabel(OrderStatus.LABEL_OP_FINISHED);
        orderStatus.setPendingLabel(OrderStatus.LABEL_OP_FINISHED_PENDING);
        orderStatus.setOrder(order);
        orderStatusService.save(orderStatus);

        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

        message = "设置成功";
        addMessage(redirectAttributes, message);
        return "redirect:" + redirectUrl;
    }
}
