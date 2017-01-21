/**
 * Copyright &copy; 2015 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.alipay.util.AlipayNotify;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.RamdomUtils;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.entity.Order;
import com.iwc.shop.modules.shop.entity.OrderStatus;
import com.iwc.shop.modules.shop.entity.Sezi;
import com.iwc.shop.modules.shop.service.OrderService;
import com.iwc.shop.modules.shop.service.OrderStatusService;
import com.iwc.shop.modules.shop.service.SeziService;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 支付宝Controller
 * @author Tony Wong
 * @version 2015-07-14
 */
@Controller
@RequestMapping("/app/alipay")
public class AppAlipayController extends AppBaseController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderStatusService orderStatusService;

    @Autowired
    UserService userService;

	/**
	 * 手机支付后，支付宝服务器通知
	 */
	@RequestMapping(value="/notify")
	public String notify(HttpServletRequest request, HttpServletResponse response) {
        logger.info("~~~~~~~~~~~~~~~~~~支付宝，服务器返回的通知>>>开始>>>");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //商户订单号
		String orderId = request.getParameter("out_trade_no");
        //支付宝交易号
        String tradeNo = request.getParameter("trade_no");
        //交易状态
        String tradeStatus = request.getParameter("trade_status");

        String backStr; //返回给支付宝的字符串
        if(AlipayNotify.verify(params)){//验证成功
            //请在这里加上商户的业务逻辑程序代码
            if(tradeStatus.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //该种交易状态只在两种情况下出现
                //1、开通了普通即时到账，买家付款成功后。
                //2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。

                //业务逻辑
                Order order = orderService.get(orderId);
                if (order != null) {
                    if (!Global.YES.equals(order.getHasPaid())) {
                        //更改在线支付的订单状态, 设置订单已支付
                        OrderStatus orderStatus = new OrderStatus();
                        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
                        orderStatus.setStatus(OrderStatus.STATUS_OP_PAID);
                        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_PAID);
                        orderStatus.setLabel(OrderStatus.LABEL_OP_PAID);
                        orderStatus.setPendingLabel(OrderStatus.LABEL_OP_PAID_PENDING);
                        orderStatus.setOrder(order);
                        orderStatusService.save(orderStatus);

                        order.setHasPaid(Global.YES);
                        order.setPaidDate(new Date());
                        order.setOrderStatus(orderStatus);
                        order.setStatusUnion(orderStatus.getStatusUnion());
                        order.setOpTransactionId(tradeNo);
                        orderService.save(order);

                        //保存最近的支付类型
                        if (order.getUser() != null && StringUtils.isNotBlank(order.getUser().getId())) {
                            User user = userService.get(order.getUser().getId());
                            if (user != null) {
                                user.setLatestPayType(order.getPayType());
                                userService.save(user);
                            }
                        }
                    }
                    backStr = "success";
                } else {
                    backStr = "fail";
                }
            } else if (tradeStatus.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。

                //业务逻辑, 同上
                Order order = orderService.get(orderId);
                if (order != null) {
                    if (!Global.YES.equals(order.getHasPaid())) {
                        //更改在线支付的订单状态, 设置订单已支付
                        OrderStatus orderStatus = new OrderStatus();
                        orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
                        orderStatus.setStatus(OrderStatus.STATUS_OP_PAID);
                        orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_PAID);
                        orderStatus.setLabel(OrderStatus.LABEL_OP_PAID);
                        orderStatus.setPendingLabel(OrderStatus.LABEL_OP_PAID_PENDING);
                        orderStatus.setOrder(order);
                        orderStatusService.save(orderStatus);

                        order.setHasPaid(Global.YES);
                        order.setPaidDate(new Date());
                        order.setOrderStatus(orderStatus);
                        order.setStatusUnion(orderStatus.getStatusUnion());
                        order.setOpTransactionId(tradeNo);
                        orderService.save(order);

                        //保存最近的支付类型
                        if (order.getUser() != null && StringUtils.isNotBlank(order.getUser().getId())) {
                            User user = userService.get(order.getUser().getId());
                            if (user != null) {
                                user.setLatestPayType(order.getPayType());
                                userService.save(user);
                            }
                        }
                    }
                    backStr = "success";
                } else {
                    backStr = "fail";
                }
            } else {
                backStr = "success";
            }
        }else{//验证失败
            backStr = "fail";
        }

        try {
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(backStr);
        } catch (IOException e) {
        }

        logger.info("~~~~~~~~~~~~~~~~~~支付宝，服务器返回的通知<<<结束<<<");
        return null;
	}
}
