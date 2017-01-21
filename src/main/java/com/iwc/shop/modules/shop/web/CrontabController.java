package com.iwc.shop.modules.shop.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.Order;
import com.iwc.shop.modules.shop.entity.OrderStatus;
import com.iwc.shop.modules.shop.service.OrderService;
import com.iwc.shop.modules.shop.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用Linux的定时任务访问该Controller
 * @author Tony Wong
 * @version 2015-09-16
 */
@Controller
@RequestMapping("/crontab")
public class CrontabController extends BaseController {
	
	@Autowired
	private OrderService orderService;

    @Autowired
    OrderStatusService orderStatusService;

    private static final String ACCESS_TOKEN = "crontab-access-token-2-tony-4-ygcr-9361mbk-jqa";

    /**
     * 更改订单状态，如果用户已经在线付款了且付款时间大约3小时，则设置在线支付-已收货-交易完成
     * Linux访问的URL：http://www.ygcr8.com/crontab/setOPStatusReceived/crontab-access-token-2-tony-4-ygcr-9361mbk-jqa
     */
    @RequestMapping(value = "/setOPStatusReceived/{accessToken}")
    public String setOPStatusReceived(@PathVariable String accessToken, HttpServletResponse response) {
        logger.info("~~~~~~~~~~~~~~~~~~~~~~ Linux的定时任务访问，更改订单状态，开始>>>");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        if (!ACCESS_TOKEN.equalsIgnoreCase(accessToken)) {
            result = false;
            message = "ACCESS_TOKEN(" + accessToken + ")不正确";
            logger.info(message);
            logger.info("~~~~~~~~~~~~~~~~~~~~~~ Linux的定时任务访问，更改订单状态，结束<<<;");
            return renderString(response, result, message, data);
        }

        //查找已经在线付款配送中(statusUnion='2-300')、并且付款时间超过3小时的订单列表
        int overHour = 3;
        List<Order> orderList = orderService.findOpDelivering(overHour);

        if (orderList == null || orderList.isEmpty()) {
            result = true;
            message = "没有在线付款配送中、并且付款时间超过" + overHour + "小时的订单需要更改订单状态";
            logger.info(message);
            logger.info("~~~~~~~~~~~~~~~~~~~~~~ Linux的定时任务访问，更改订单状态，结束<<<;");
            return renderString(response, result, message, data);
        }

        logger.info("有" + orderList.size() + "个在线付款配送中、并且付款时间超过" + overHour + "小时的订单需要更改订单状态");

        int i = 0;
        for (Order order : orderList) {
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

            i++;
            logger.info("序号({})：成功设置订单(ID:{})状态为已收货-交易完成", i, order.getId());
        }

        result = true;
        message = "成功设置" + orderList.size() + "个订单的状态";
        logger.info(message);
        logger.info("~~~~~~~~~~~~~~~~~~~~~~ Linux的定时任务访问，更改订单状态，结束<<<;");
        return renderString(response, result, message, data);
    }
}
