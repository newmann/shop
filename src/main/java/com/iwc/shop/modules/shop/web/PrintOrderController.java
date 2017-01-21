package com.iwc.shop.modules.shop.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.JsonUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.Order;
import com.iwc.shop.modules.shop.entity.OrderItem;
import com.iwc.shop.modules.shop.entity.OrderStatus;
import com.iwc.shop.modules.shop.service.OrderItemService;
import com.iwc.shop.modules.shop.service.OrderService;
import com.iwc.shop.modules.shop.service.OrderStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动打印订单Controller
 * @author Tony Wong
 * @version 2015-04-16
 */

@Controller
@RequestMapping("/print-order")
public class PrintOrderController extends BaseController {

	private static final String token = "XJ0CE548F92JMN4Q162KFJ8UF3QX";
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

    @Autowired
    private OrderStatusService orderStatusService;

	@RequestMapping("/auto-print")
	public String index(ModelMap m, HttpServletRequest request){
        String token = request.getParameter("token");
        if (!token.equals(this.token)) {
            return null;
        }

        m.put("token", this.token);
        return "modules/shop/print-order/auto-print";
	}

    /**
     * 获取一个没有打印过的最旧的订单给前台自动打印
     *  1，在线支付，没有付款不能打印
     *  2，现金支付，下单就能打印
     * @throws IOException
     */
    @RequestMapping("/get-oldest-for-auto-print")
    public String getOldestUnprintOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonUtils.setResponse(response);

        String token = request.getParameter("token");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        if (token.equals(this.token)) {
            Order order = orderService.getOldest4AutoPrint();
            if (order != null && StringUtils.isNotBlank(order.getId())) {
                result = true;
                message = "一个最旧的未打印过的订单";
                data.put("order", order.toSimpleObjString());
            } else {
                result = false;
                message = "没有未打印过的订单";
                data.put("printLog", false);
            }
        } else {
            result = false;
            message = "token不正确";
            data.put("printLog", true);
        }

        response.getWriter().print(JsonUtils.toString(result, message, data));
        return null;
    }

    /**
     * 获得将要打印的订单的html
     */
    @RequestMapping("/get-frame-html")
    public String getFrameHtml(HttpServletRequest request, HttpServletResponse response, ModelMap m) {
        String token = request.getParameter("token");
        String orderId = request.getParameter("orderId");

        if (!token.equals(this.token)) {
            return null;
        }

        Order order = orderService.getX(orderId);

        if (order == null || StringUtils.isBlank(order.getId())
                || order.getPrintCount() > 0) {
            return null;
        }

        //设置自动打印的单数到cookie "auto-print-num"
        String printNumName = "auto-print-num";
        String printNum = com.iwc.shop.common.utils.CookieUtils.getCookie(request, printNumName);
        if (StringUtils.isBlank(printNum)) {
            // set to client
            printNum = "1";
            com.iwc.shop.common.utils.CookieUtils.setCookie(response, printNumName, printNum, "/", 31536000);
        } else {
            int printNumInt = Integer.valueOf(printNum);
            printNumInt++;
            if (printNumInt > 1000)
                printNumInt = 1;
            printNum = String.valueOf(printNumInt);
        }
        com.iwc.shop.common.utils.CookieUtils.setCookie(response, printNumName, printNum, "/", 31536000);

        List<OrderItem> orderItemList = orderItemService.findByOrderId(order);

        m.put("printNum", printNum);
        m.put("order", order);
        m.put("orderItemList", orderItemList);
        m.put("token", token);
        return "modules/shop/print-order/frame-html";
    }

    /**
     * //设置已经打印的订单, 设置订单状态为配送中
     */
    @RequestMapping("/set-print")
    public String setPrint(HttpServletRequest request, HttpServletResponse response) throws IOException{
        JsonUtils.setResponse(response);

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String token = request.getParameter("token");
        String orderId = request.getParameter("orderId");
        String storeId = request.getParameter("storeId");

        if (!token.equals(this.token)) {
            result = false;
            message = "token不正确";
            data.put("printLog", true);
            response.getWriter().print(JsonUtils.toString(result, message, data));
            return null;
        }

        if (storeId == null || storeId.isEmpty()) {
            result = false;
            message = "没有店铺ID";
            response.getWriter().print(JsonUtils.toString(result, message, data));
            return null;
        }

        Order order = orderService.get(orderId);

        if (order == null || StringUtils.isBlank(order.getId())) {
            result = false;
            message = "订单(" + orderId + ")不存在";
            data.put("printLog", false);
            response.getWriter().print(JsonUtils.toString(result, message, data));
            return null;
        }

        //设置打印次数
        if (order.getPrintCount() == null || order.getPrintCount() < 1) {
            order.setPrintCount(1);
        }
        order.setPrintCount(order.getPrintCount() + 1);

        //设置订单状态为配送中
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrder(order);
        if (Order.ROUGH_PAY_TYPE_CASH.equals(order.getRoughPayType())) { //现金支付
            orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_CASH);
            orderStatus.setStatus(OrderStatus.STATUS_CASH_DELIVERING);
            orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_CASH_DELIVERING);
            orderStatus.setLabel(OrderStatus.LABEL_CASH_DELIVERING);
            orderStatus.setPendingLabel(OrderStatus.LABEL_CASH_DELIVERING_PENDING);
        } else if (Order.ROUGH_PAY_TYPE_OP.equals(order.getRoughPayType())){ //在线支付
            orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
            orderStatus.setStatus(OrderStatus.STATUS_OP_DELIVERING);
            orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_DELIVERING);
            orderStatus.setLabel(OrderStatus.LABEL_OP_DELIVERING);
            orderStatus.setPendingLabel(OrderStatus.LABEL_OP_DELIVERING_PENDING);
        }
        orderStatusService.save(orderStatus);

        //保存订单状态到订单
        order.setOrderStatus(orderStatus);
        order.setStatusUnion(orderStatus.getStatusUnion());
        orderService.save(order);

        result = true;
        message = "已设置打印的订单";
        response.getWriter().print(JsonUtils.toString(result, message, data));
        return null;
    }



   //============ 第二次调整打印机程序用 ===========//
    /**
     * 获取一个没有打印过的最旧的订单给前台自动打印, 用java程序调用标签打印机程序
     *  1，在线支付，没有付款不能打印
     *  2，现金支付，下单就能打印
     * @throws IOException
     */
    @RequestMapping("/get-unprint-oldest-order")
    public String getUnprintOldestOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonUtils.setResponse(response);

        String token = request.getParameter("token");
        String storeId = request.getParameter("storeId");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        if (storeId == null || storeId.isEmpty()) {
            result = false;
            message = "没有店铺ID";
            response.getWriter().print(JsonUtils.toString(result, message, data));
            return null;
        }

        if (token.equals(this.token)) {
            Order order = orderService.getOldest4AutoPrintByStoreId(storeId);
            if (order != null && StringUtils.isNotBlank(order.getId())) {
                Map<String, Object> oOrder = order.toSimpleObjString();
                List<Map<String, Object>> orderItemList = orderItemService.findByOrderId4SimpleObjString(order);
                if (orderItemList != null && orderItemList.size() > 0) {
                    result = true;
                    message = "一个未打印过的最旧的订单";

                    //优惠券
                    Map<String, Object> oCouponUser = new HashMap<String, Object>();
                    if (order.getCouponUser() != null && !order.getCouponUser().getId().isEmpty()) {
                        oCouponUser = order.getCouponUser().toSimpleObjString();
                    }

                    oOrder.put("couponUser", oCouponUser);
                    data.put("order", oOrder);
                    data.put("orderItemList", orderItemList);
                } else {
                    result = false;
                    message = "订单异常，有未打印过的订单(ID:" + order.getId() + ")" + "但是没有订单项";
                    data.put("order", order.toSimpleObjString());
                }
            } else {
                result = false;
                message = "没有未打印过的订单";
            }
        } else {
            result = false;
            message = "token不正确";
        }

        response.getWriter().print(JsonUtils.toString(result, message, data));
        return null;
    }

}
