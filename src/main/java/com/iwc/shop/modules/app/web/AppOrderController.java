/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.AlipayClientConfig;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.utils.IpAddrUtils;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.*;
import com.iwc.shop.modules.shop.utils.OrderStatusUtils;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单Controller
 * @author Tony Wong
 * @version 2015-05-17
 */
@Controller
@RequestMapping("/app/order")
public class AppOrderController extends AppBaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService itemService;

	@Autowired
	private OrderItemAttributeService attributeService;

    @Autowired
    private OrderStatusService orderStatusService;

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private PreorderItemService preorderItemService;

	@Autowired
	private CartItemService cartItemService;

    @Autowired
    private CouponUserService couponUserService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

	/**
	 * 查看订单列表
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oOrderList = Lists.newArrayList();
        String userId = getUserId(request);

		Order order = new Order();
        order.setUser(new User(userId));
		Page<Order> page = new Page<Order>(request, response);
		page.setOrderBy("a.create_date desc");
		page.setPageNo(1);
		page.setPageSize(30);

        //不包含取消的订单
        Map<String, String> sqlMap = Maps.newHashMap();
        String where = "(a.status_union != '" + OrderStatus.STATUS_UNION_CASH_CANCEL + "'"
                + " AND a.status_union != '" + OrderStatus.STATUS_UNION_OP_CANCEL + "')";
        sqlMap.put("where", where);
        order.setSqlMap(sqlMap);

		page = orderService.findPage(page, order);

		if (page != null && page.getList() != null) {
            List<Order> orderList = page.getList();
			for (Order o : orderList) {
                Map<String, Object> oOrder = o.toSimpleObj();
                Map<String, Object> oOrderStatus = Maps.newHashMap();
                List<Map<String, Object>> oOrderItemList = Lists.newArrayList();

                if (o.getOrderStatus() != null) {
                    OrderStatus orderStatus = OrderStatusUtils.get(o.getOrderStatus().getId());
                    if (orderStatus != null) {
                        oOrderStatus = orderStatus.toSimpleObj();
                    }
                }

				OrderItem item = new OrderItem();
				item.setOrder(o);
                List<OrderItem> orderItemList = itemService.findList(item);
                if (orderItemList != null && !orderItemList.isEmpty()) {
                    for (OrderItem orderItem : orderItemList) {
                        Map<String, Object> oOrderItem = orderItem.toSimpleObj();
                        oOrderItemList.add(oOrderItem);
                    }
                }

				//o.setItemList(itemService.findList(item));
                oOrder.put("orderItemList", oOrderItemList);
                oOrder.put("orderStatus", oOrderStatus);
                oOrderList.add(oOrder);
			}
		}

        result = true;
        message = "";
        data.put("orderList", oOrderList);
        return renderString(response, result, message, data);
	}

    /**
     * 查看待付款订单列表
     */
    @RequestMapping("/list-pending-pay")
    public String listPendingPay(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oOrderList = Lists.newArrayList();
        String userId = getUserId(request);

        Order order = new Order();
        order.setUser(new User(userId));
        List<Order> orderList = orderService.findPendingPay(userId);

        for (Order o : orderList) {
            Map<String, Object> oOrder = o.toSimpleObj();
            Map<String, Object> oOrderStatus = Maps.newHashMap();
            List<Map<String, Object>> oOrderItemList = Lists.newArrayList();

            if (o.getOrderStatus() != null) {
                OrderStatus orderStatus = OrderStatusUtils.get(o.getOrderStatus().getId());
                if (orderStatus != null) {
                    oOrderStatus = orderStatus.toSimpleObj();
                }
            }

            OrderItem item = new OrderItem();
            item.setOrder(o);
            List<OrderItem> orderItemList = itemService.findList(item);
            if (orderItemList != null && !orderItemList.isEmpty()) {
                for (OrderItem orderItem : orderItemList) {
                    Map<String, Object> oOrderItem = orderItem.toSimpleObj();
                    oOrderItemList.add(oOrderItem);
                }
            }

            //o.setItemList(itemService.findList(item));
            oOrder.put("orderItemList", oOrderItemList);
            oOrder.put("orderStatus", oOrderStatus);
            oOrderList.add(oOrder);
        }

        result = true;
        message = "";
        data.put("orderList", oOrderList);
        data.put("showCancelOrderBtn", true);
        return renderString(response, result, message, data);
    }

    /**
     * 查看待收货订单列表
     */
    @RequestMapping("/list-delivering")
    public String listDelivering(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oOrderList = Lists.newArrayList();
        String userId = getUserId(request);

        Order order = new Order();
        order.setUser(new User(userId));
        List<Order> orderList = orderService.findDelivering(userId);

        for (Order o : orderList) {
            Map<String, Object> oOrder = o.toSimpleObj();
            Map<String, Object> oOrderStatus = Maps.newHashMap();
            List<Map<String, Object>> oOrderItemList = Lists.newArrayList();

            if (o.getOrderStatus() != null) {
                OrderStatus orderStatus = OrderStatusUtils.get(o.getOrderStatus().getId());
                if (orderStatus != null) {
                    oOrderStatus = orderStatus.toSimpleObj();
                }
            }

            OrderItem item = new OrderItem();
            item.setOrder(o);
            List<OrderItem> orderItemList = itemService.findList(item);
            if (orderItemList != null && !orderItemList.isEmpty()) {
                for (OrderItem orderItem : orderItemList) {
                    Map<String, Object> oOrderItem = orderItem.toSimpleObj();
                    oOrderItemList.add(oOrderItem);
                }
            }

            //o.setItemList(itemService.findList(item));
            oOrder.put("orderItemList", oOrderItemList);
            oOrder.put("orderStatus", oOrderStatus);
            oOrderList.add(oOrder);
        }

        result = true;
        message = "";
        data.put("orderList", oOrderList);
        return renderString(response, result, message, data);
    }

    /**
     * 查看交易完成订单列表
     */
    @RequestMapping("/list-finished")
    public String listFinished(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oOrderList = Lists.newArrayList();
        String userId = getUserId(request);

        List<Order> orderList = orderService.findFinished(userId);

        for (Order o : orderList) {
            Map<String, Object> oOrder = o.toSimpleObj();
            Map<String, Object> oOrderStatus = Maps.newHashMap();
            List<Map<String, Object>> oOrderItemList = Lists.newArrayList();

            if (o.getOrderStatus() != null) {
                OrderStatus orderStatus = OrderStatusUtils.get(o.getOrderStatus().getId());
                if (orderStatus != null) {
                    oOrderStatus = orderStatus.toSimpleObj();
                }
            }

            OrderItem item = new OrderItem();
            item.setOrder(o);
            List<OrderItem> orderItemList = itemService.findList(item);
            if (orderItemList != null && !orderItemList.isEmpty()) {
                for (OrderItem orderItem : orderItemList) {
                    Map<String, Object> oOrderItem = orderItem.toSimpleObj();
                    oOrderItemList.add(oOrderItem);
                }
            }

            //o.setItemList(itemService.findList(item));
            oOrder.put("orderItemList", oOrderItemList);
            oOrder.put("orderStatus", oOrderStatus);
            oOrderList.add(oOrder);
        }

        result = true;
        message = "";
        data.put("orderList", oOrderList);
        return renderString(response, result, message, data);
    }

	/**
	 * 查看订单
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/{id}")
	public String view(@PathVariable String id,
					   HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> oOrder;
        Map<String, Object> oCouponUser = Maps.newHashMap();
        boolean oShowPayBtn = false;

        Order order = orderService.getX(id);
        if (order == null || order.getUser() == null
                || StringUtils.isBlank(order.getUser().getId())) {
            result = false;
            message = "订单(ID:" + id + ")不存在";
            return renderString(response, result, message, data);
        }

        oOrder = orderService.toSimpleObj(order);
        if (order.getCouponUser() != null) {
            oCouponUser = order.getCouponUser().toSimpleObjWithCal();
        }
        List<Map<String, Object>> oOrderItemList = itemService.findByOrderId4SimpleObj(order);

        //如果是待付款则显示去付款按钮
        if (OrderStatus.STATUS_UNION_OP_ORDERED.equals(order.getStatusUnion())) {
            oShowPayBtn = true;
        }

        if (STOP_ORDER) {
            oShowPayBtn = false;
        }

        result = true;
        message = "";
        oOrder.put("couponUser", oCouponUser);
        data.put("order", oOrder);
        data.put("orderItemList", oOrderItemList);
        data.put("showPayBtn", oShowPayBtn);
        return renderString(response, result, message, data);
	}

	/**
	 * 将预备订单转化为订单
	 */
	@RequestMapping(value="/add/{preorderId}", method=RequestMethod.POST)
	public String add(@PathVariable String preorderId,
					  HttpServletRequest request, HttpServletResponse response) {
		if (!isLoggedIn(request)) {
			return renderNotLoggedIn(response);
		}

		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();
        User user = getUser(request);

        String addressId = request.getParameter("addressId");
        String notice = request.getParameter("notice");

        if(STOP_ORDER) {
            result = false;
            message = STOP_ORDER_LABEL;
            return renderString(response, result, message, data);
        }

        if (StringUtils.isBlank(addressId)) {
            result = false;
            message = "请选择收货地址";
            return renderString(response, result, message, data);
        }

        Address address = addressService.get(addressId);

        if (address == null) {
            result = false;
            message = "收货地址(ID:" + addressId + ")不存在";
            return renderString(response, result, message, data);
        }
        // area not exist
        if (address.getArea() == null) {
            result = false;
            message = "收货地区不存在";
            return renderString(response, result, message, data);
        }
        // area not exist
        if (StringUtils.isBlank(address.getFullname())) {
            result = false;
            message = "收货人姓名不存在";
            return renderString(response, result, message, data);
        }
        // address not exist
        if (StringUtils.isBlank(address.getTelephone())) {
            result = false;
            message = "收货人电话不存在";
            return renderString(response, result, message, data);
        }
        // 如果不是店内消费，收货门牌地址必须填写
        if (!Area.SHIPPING_GROUP_STORE.equals(address.getArea().getShippingGroup()) && StringUtils.isBlank(address.getDetail())) {
            result = false;
            message = "收货门牌地址不存在";
            return renderString(response, result, message, data);
        }

        //判断是否在下单时间段内和是否在店内消费
        if (!Area.SHIPPING_GROUP_STORE.equals(address.getArea().getShippingGroup()) && !Order.isValidOrderTime()) {
            result = false;
            message = Order.ORDER_TIME_INVALID_MSG;
            data.put("showAlert", true);
            data.put("showAlertTitle", Order.ORDER_TIME_INVALID_Title);
            data.put("showAlertMsg", Order.ORDER_TIME_INVALID_MSG);
            return renderString(response, result, message, data);
        }

        //不能重复提交订单
        Order existOrder = orderService.getByPreorderId(preorderId);
        if (existOrder != null && StringUtils.isNotBlank(existOrder.getId())) {
            result = false;
            //message = "请不要重复提交预备订单(预备订单ID:" + preorderId + ")";
            message = "请不要重复提交订单";
            logger.warn("请不要重复提交预备订单(ID:{})", preorderId);
            return renderString(response, result, message, data);
        }

		Preorder preorder = preorderService.get(preorderId);
		List<PreorderItem> preorderItemList = preorderItemService.findByPreorderId(preorder.getId());

        // preorder not exist
        if (preorder == null || preorderItemList == null || preorderItemList.isEmpty()) {
            result = false;
            message = "预备订单(ID:" + preorderId + ")不存在, 或没有预备订单项";
            return renderString(response, result, message, data);
        }

        //支付方式检查
        if (StringUtils.isBlank(preorder.getPayType())) {
            result = false;
            message = "请选择支付方式";
            return renderString(response, result, message, data);
        }
        if (!Order.isValidPayType(preorder.getPayType())) {
            result = false;
            message = "预备订单(ID:" + preorderId + ")的支付类型(payType:" + preorder.getPayType() + ")有误";
            return renderString(response, result, message, data);
        }

        //判断送货要求：1，只有商品总额大于该值才送货；2，店内消费没这个要求
        if (!Area.SHIPPING_GROUP_STORE.equals(address.getArea().getShippingGroup())
                && preorder.getOriginTotalPrice() < Order.MIN_PRODUCT_AMOUNT_FOR_SHIPPING) {
            result = false;
            message = "只有商品总额大于￥" + Order.MIN_PRODUCT_AMOUNT_FOR_SHIPPING.intValue() + "元才能送货上门";
            return renderString(response, result, message, data);
        }

        //要求最少支付总额
        if (preorder.getTotalPrice() < Order.MIN_TOTAL_PRICE) {
            result = false;
            message = Order.MIN_TOTAL_PRICE_LABEL;
            return renderString(response, result, message, data);
        }

        //serialNo
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String serialNo = df.format(date) + String.valueOf(calendar.get(Calendar.MILLISECOND));

		// save order
		Order order = new Order();
		order.setUser(user);
		order.setIp(IpAddrUtils.getIpAddr(request));
		order.setCart(preorder.getCart());
		order.setPreorder(preorder);
		order.setTotalPrice(preorder.getTotalPrice());
		order.setTotalCount(preorder.getTotalCount());
        order.setCouponUserTotalPrice(preorder.getCouponUserTotalPrice());
        order.setCouponUser(preorder.getCouponUser());
        order.setOriginTotalPrice(preorder.getOriginTotalPrice());
		order.setArea(address.getArea());
		order.setAreaName(address.getArea().getName());
		order.setAreaParentId(address.getArea().getParentId());
		order.setAreaPathIds(address.getArea().getPathIds());
		order.setAreaPathNames(address.getArea().getPathNames());
		order.setAreaSimpleName(address.getArea().getSimpleName());
		order.setAreaZipCode(address.getArea().getZipCode());
        order.setStoreId(address.getArea().getStoreId());
		order.setAddressDetail(address.getDetail());
		order.setAddressFullname(address.getFullname());
		order.setAddressTelephone(address.getTelephone());
        order.setAddressId(address.getId());
        order.setSerialNo(serialNo);
        order.setNotice(notice);
        order.setPayType(preorder.getPayType());
        order.setRoughPayType(preorder.getRoughPayType());
        order.setHasPaid(Global.NO);
        order.setMinTotalPriceLabel(preorder.getMinTotalPriceLabel());
		orderService.save(order);

        //设置订单状态为已下单
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrder(order);
        if (Order.ROUGH_PAY_TYPE_CASH.equals(order.getRoughPayType())) { //现金支付
            orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_CASH);
            orderStatus.setStatus(OrderStatus.STATUS_CASH_ORDERED);
            orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_CASH_ORDERED);
            orderStatus.setLabel(OrderStatus.LABEL_CASH_ORDERED);
            orderStatus.setPendingLabel(OrderStatus.LABEL_CASH_ORDERED_PENDING);
        } else if (Order.ROUGH_PAY_TYPE_OP.equals(order.getRoughPayType())){ //在线支付
            orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
            orderStatus.setStatus(OrderStatus.STATUS_OP_ORDERED);
            orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_ORDERED);
            orderStatus.setLabel(OrderStatus.LABEL_OP_ORDERED);
            orderStatus.setPendingLabel(OrderStatus.LABEL_OP_ORDERED_PENDING);
        }
        orderStatusService.save(orderStatus);

        //保存订单状态到订单
        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

		// save order items
		for(PreorderItem preorderItem : preorderItemList) {
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setPreorderItem(preorderItem);
			item.setProduct(preorderItem.getProduct());
			item.setCartItem(preorderItem.getCartItem());
			item.setName(preorderItem.getName());
			item.setImage(preorderItem.getImage());
			item.setFeaturedImage(preorderItem.getFeaturedImage());
			item.setImageSmall(preorderItem.getImageSmall());
			item.setPrice(preorderItem.getPrice());
			item.setSubtotalPrice(preorderItem.getSubtotalPrice());
			item.setCount(preorderItem.getCount());
            item.setType(preorderItem.getType());
			itemService.save(item);

			// save order item attributes
			if (preorderItem.getAttributeList() != null) {
				for (PreorderItemAttribute attr : preorderItem.getAttributeList()) {
					OrderItemAttribute itemAttr = new OrderItemAttribute();
                    itemAttr.setItem(item);
                    itemAttr.setAttributeItemId(attr.getId());
                    itemAttr.setAttributeItemName(attr.getAttributeItemName());
                    itemAttr.setAttributeItemPrintName(attr.getAttributeItemPrintName());
                    itemAttr.setAttributeItemSort(attr.getAttributeItemSort());
                    itemAttr.setAttributeItemValueId(attr.getAttributeItemValueId());
                    itemAttr.setAttributeItemValueName(attr.getAttributeItemValueName());
                    itemAttr.setAttributeItemValuePrintName(attr.getAttributeItemValuePrintName());
                    itemAttr.setAttributeItemValueSort(attr.getAttributeItemValueSort());
                    itemAttr.setAttributeItemValuePrice(attr.getAttributeItemValuePrice());
                    itemAttr.setAttributeItemValueIsStandard(attr.getAttributeItemValueIsStandard());
                    itemAttr.setAttributeIdstring(attr.getAttributeIdstring());
					attributeService.save(itemAttr);
				}
			}

			// update CartItem's count and isOrdered
			CartItem cartItem = cartItemService.get(preorderItem.getCartItem().getId());
			if (cartItem != null) {
				Integer orderItemCount = item.getCount();
				Integer cartItemCount = cartItem.getCount();
				if (orderItemCount.equals(cartItemCount)) {
					cartItem.setIsOrdered(CartItem.IS_ORDERED_YES);
				}
				else if (orderItemCount < cartItemCount) {
					cartItem.setCount(cartItemCount - orderItemCount);
				}
				cartItemService.save(cartItem);
			}
		}

		// update preorder status
		preorder.setIsOrdered(Preorder.IS_ORDERED_YES);
		preorderService.save(preorder);

        //set couponUser
        CouponUser couponUser = order.getCouponUser();
        if (couponUser != null && StringUtils.isNotBlank(couponUser.getId())) {
            couponUser = couponUserService.get(couponUser);
            couponUser.setHasUsed(Global.YES);
            couponUserService.save(couponUser);
        }

        //保存现金支付类型，在线支付时由在线支付系统的服务器返回通知时保存
        if (Order.PAY_TYPE_CASH.equals(order.getPayType())) {
            user.setLatestPayType(order.getPayType());
            userService.save(user);
        }

        //也可以通过后台传来的值配置客户端的支付宝
        if (AlipayClientConfig.isUsed) {
            data.put("alipayConfig", AlipayClientConfig.getConfig());
        }

        Map<String, Object> oOrder = order.toSimpleObj();
		result = true;
		message = "订单已成功提交";
		data.put("order", oOrder);
        return renderString(response, result, message, data);
	}

	@RequestMapping("/success/{id}")
	public String success(@PathVariable String id,
						  HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> oOrder = Maps.newHashMap();

		Order order = orderService.get(id);
        if (order != null) {
            result = true;
            message = "";
            oOrder = order.toSimpleObj();
        } else {
            result = false;
            message = "订单(ID:" + id + ")不存在";
        }

        String successPayText;
        String successWaitText;
        String successDeliveryText = "由月光茶人专人配送";

        if (Global.YES.equals(order.getHasPaid())) {
            successPayText = "恭喜，订单支付成功";
            successWaitText = "请耐心等待";
        } else {
            successPayText = "恭喜，您已成功下单";
            successWaitText = "请准备好零钱耐心等待";
        }

        data.put("successPayText", successPayText);
        data.put("successWaitText", successWaitText);
        data.put("successDeliveryText", successDeliveryText);

        data.put("order", oOrder);
        return renderString(response, result, message, data);
	}

    @RequestMapping("/fail/{id}")
    public String fail(@PathVariable String id,
                          HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> oOrder = Maps.newHashMap();

        Order order = orderService.get(id);
        if (order != null) {
            result = true;
            message = "";
            oOrder = order.toSimpleObj();
        } else {
            result = true;
            message = "订单(ID:" + id + ")不存在";
            order = new Order(); //避免order.getHasPaid()为null值
        }

        String failPayText;
        String failWaitText;
        String failDeliveryText = "支付成功后才能配送";

        if (Global.YES.equals(order.getHasPaid())) {
            failPayText = "订单支付失败";
            failWaitText = "请耐心等待";
        } else {
            failPayText = "订单支付失败";
            failWaitText = "请准备好零钱耐心等待";
        }

        data.put("failPayText", failPayText);
        data.put("failWaitText", failWaitText);
        data.put("failDeliveryText", failDeliveryText);

        data.put("order", oOrder);
        return renderString(response, result, message, data);
    }

    /**
     * 设置在线支付类型，只有在线支付而未付款的订单才能调用，
     *  在app订单查看页面和订单支付失败页面点击去微信支付，去支付宝支付
     */
    @RequestMapping("/set-pay-type")
    public String selectPayType(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        String userId = request.getParameter("userId");
        String orderId = request.getParameter("orderId");
        String payType = request.getParameter("payType");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        if (!Order.isValidOpPayType(payType)) {
            result = false;
            message = "在线支付方式(PayType:" + payType + ")不正确";
            return renderString(response, result, message, data);
        }

        Order order = orderService.getForUser(orderId, userId);

        if (order == null) {
            result = false;
            message = "订单(ID:" + orderId + ")不存在";
            return renderString(response, result, message, data);
        }

        //判断是否在下单时间段内和是否在店内消费
        if (!Area.SHIPPING_GROUP_STORE.equals(order.getArea().getShippingGroup()) && !Order.isValidOrderTime()) {
            result = false;
            message = Order.ORDER_TIME_INVALID_MSG;
            data.put("showAlert", true);
            data.put("showAlertTitle", Order.ORDER_TIME_INVALID_Title);
            data.put("showAlertMsg", Order.ORDER_TIME_INVALID_MSG);
            return renderString(response, result, message, data);
        }

        order.setPayType(payType);
        orderService.save(order);

        Map<String, Object> oOrder = order.toSimpleObj();
        result = true;
        message = "";
        data.put("order", oOrder);

        return renderString(response, result, message, data);
    }

    /**
     * 取消订单，退回优惠券，只有在线支付待付款的订单能取消订单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cancel/{id}")
    public String cancel(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        Order order = orderService.getForUser(id, getUserId(request));

        if (order == null) {
            result = false;
            message = "订单(ID:" + id + ")不存在";
            return renderString(response, result, message, data);
        }

        //判断是否在线支付方式
        String payType = order.getPayType();
        if (!Order.isValidOpPayType(payType)) {
            result = false;
            message = "在线支付方式(PayType:" + order.getPayType() + ")不正确";
            return renderString(response, result, message, data);
        }

        //判断订单是否已支付
        if (Global.YES.equals(order.getHasPaid())) {
            result = false;
            message = "订单(ID:" + id + ")已付款，不能取消";
            return renderString(response, result, message, data);
        }

        //判断是否在线支付待付款的订单
        String statusUnion = order.getStatusUnion();
        if (OrderStatus.STATUS_UNION_OP_ORDERED.equals(statusUnion)) {
            message = "订单已取消";
            //归还优惠红包给用户
            if (order.getCouponUser() != null) {
                CouponUser couponUser = couponUserService.get(order.getCouponUser().getId());
                if (couponUser != null) {
                    couponUser.setHasUsed(Global.NO);
                    couponUserService.save(couponUser);
                    message = "订单已取消，优惠红包已归还给您";
                }
            }

            //添加已取消订单状态记录
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrder(order);
            orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
            orderStatus.setStatus(OrderStatus.STATUS_OP_CANCEL);
            orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_CANCEL);
            orderStatus.setLabel(OrderStatus.LABEL_OP_CANCEL);
            orderStatus.setPendingLabel(OrderStatus.LABEL_OP_CANCEL);
            orderStatusService.save(orderStatus);

            //保存订单状态到订单
            order.setOrderStatus(orderStatus);
            order.setStatusUnion(orderStatus.getStatusUnion());
            orderService.save(order);

            result = true;
        } else {
            result = false;
            message = "订单状态(statusUnion:" + statusUnion + ")不正确";
        }

        Map<String, Object> oOrder = order.toSimpleObj();
        data.put("order", oOrder);

        return renderString(response, result, message, data);
    }
}
