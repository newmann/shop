/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.OrderDao;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.utils.OrderStatusUtils;
import com.iwc.shop.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单Service
 * @author Tony Wong
 * @version 2015-04-19
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CouponUserService couponUserService;

    public Order getForUser(String orderId, String userId) {
        Order order = get(orderId);
        if (order != null && StringUtils.isNotBlank(userId))
            return order;
        else
            return null;
    }

    public Order getX(String id) {
        Order order = get(id);
        if (StringUtils.isNotBlank(id) && order != null) {
            //订单状态
            if (order.getOrderStatus() != null) {
                order.setOrderStatus(OrderStatusUtils.get(order.getOrderStatus().getId()));
            }
            //是否有优惠券
            if (order.getCouponUser() != null) {
                CouponUser couponUser = couponUserService.get(order.getCouponUser().getId());
                order.setCouponUser(couponUser);
            }
        }
        return order;
    }

    /**
     * 查找待付款订单列表, 即在线支付已付款订单未完成的订单（OrderStatus.STATUS_UNION_OP_PAID）
     */
    public List<Order> findPendingPay(String userId) {
        Order order = new Order();
        order.setUser(new User(userId));
        Map<String, String> sqlMap = Maps.newHashMap();
        String where = "(a.status_union = '" + OrderStatus.STATUS_UNION_OP_ORDERED + "')";
        sqlMap.put("where", where);
        order.setSqlMap(sqlMap);
        return dao.findList(order);
    }

    /**
     * 查找待收货订单列表, 即
     *  1，现金支付已下单、配送中的订单OrderStatus.STATUS_UNION_CASH_ORDERED, OrderStatus.STATUS_UNION_OP_DELIVERING）
     *  2，在线支付已付款、配送中的订单OrderStatus.STATUS_UNION_OP_PAID, OrderStatus.STATUS_UNION_OP_DELIVERING
     */
    public List<Order> findDelivering(String userId) {
        Order order = new Order();
        order.setUser(new User(userId));
        Map<String, String> sqlMap = Maps.newHashMap();
        String where = "(a.status_union = '" + OrderStatus.STATUS_UNION_CASH_ORDERED + "'"
                + " OR a.status_union = '" + OrderStatus.STATUS_UNION_CASH_DELIVERING + "'"
                + " OR a.status_union = '" + OrderStatus.STATUS_UNION_OP_PAID + "'"
                + " OR a.status_union = '" + OrderStatus.STATUS_UNION_OP_DELIVERING + "')";
        sqlMap.put("where", where);
        order.setSqlMap(sqlMap);
        return dao.findList(order);
    }

    /**
     * 查找交易完成订单列表OrderStatus.STATUS_UNION_CASH_FINISHED, OrderStatus.STATUS_UNION_OP_FINISHED）
     */
    public List<Order> findFinished(String userId) {
        Order order = new Order();
        order.setUser(new User(userId));
        Map<String, String> sqlMap = Maps.newHashMap();
        String where = "(a.status_union = '" + OrderStatus.STATUS_UNION_CASH_FINISHED + "'"
                + " OR a.status_union = '" + OrderStatus.STATUS_UNION_OP_FINISHED + "')";
        sqlMap.put("where", where);
        order.setSqlMap(sqlMap);
        return dao.findList(order);
    }

    /**
     * 获取一个没有打印过的最旧的订单
     */
    public Order getOldestUnPrint() {
        Order order = new Order();
        order.setPrintCount(0);
        return dao.getOldestUnPrint(order);
    }

    /**
     * 获取一个没有打印过的最旧的订单给前台自动打印
     *  1，在线支付，没有付款不能打印
     *  2，现金支付，下单就能打印
     */
    public Order getOldest4AutoPrint() {
        Order nOrder = new Order();
        nOrder.setPrintCount(0);
        Order order = dao.getOldest4AutoPrint(nOrder);
        if (order != null && order.getId() != null) {
            order = getX(order.getId());
        }
        return order;
    }

    /**
     * 通过店铺获取一个没有打印过的最旧的订单给前台自动打印
     *  1，在线支付，没有付款不能打印
     *  2，现金支付，下单就能打印
     */
    public Order getOldest4AutoPrintByStoreId(String storeId) {
        Order nOrder = new Order();
        nOrder.setPrintCount(0);
        nOrder.setStoreId(storeId);
        Order order = dao.getOldest4AutoPrintByStoreId(nOrder);
        if (order != null && order.getId() != null) {
            order = getX(order.getId());
        }
        return order;
    }

    /**
     * 如果这个订单存在，则不能再次提交preorder
     */
    public Order getByPreorderId(String preorderId) {
        if (StringUtils.isBlank(preorderId))
            return null;

        Order pOrder = new Order();
        Preorder pPreorder = new Preorder(preorderId);
        pOrder.setPreorder(pPreorder);
        Order order = dao.getByPreorderId(pOrder);
        return order;
    }

    public Map<String, Object> toSimpleObj(Order order) {
        Map<String, Object> oOrder = Maps.newHashMap();
        if (order != null) {
            oOrder = order.toSimpleObj();

            //set orderStatus and statusProcessList
            OrderStatus orderStatus = order.getOrderStatus();
            if (orderStatus != null) {
                Map<String, Object> oOrderStatus = orderStatus.toSimpleObj();
                List<OrderStatusProcess> statusProcessList = orderStatus.getStatusProcessList();
                if (statusProcessList != null && !statusProcessList.isEmpty()) {
                    List<Map<String, Object>> oStatusProcessList = Lists.newArrayList();
                    for (OrderStatusProcess statusProcess : statusProcessList) {
                        Map<String, Object> oStatusProcess = statusProcess.toSimpleObj();
                        oStatusProcessList.add(oStatusProcess);
                    }
                    oOrderStatus.put("statusProcessList", oStatusProcessList);
                }
                oOrder.put("orderStatus", oOrderStatus);
            }

            //set couponUser
            CouponUser couponUser = order.getCouponUser();
            if (couponUser != null) {
                Map<String, Object> oCouponUser = couponUser.toSimpleObj();
                oOrder.put("couponUser", oCouponUser);
            }
        }
        return oOrder;
    }

    /**
     * 查找已经在线付款配送中(statusUnion='2-300')、并且付款时间超过N小时的订单列表
     */
    public List<Order> findOpDelivering(int overHour) {
        //当前时间减去hour的字符串
        long l = new Date().getTime(); //当前时间的毫秒数
        long s = 3600 * overHour * 1000; //hour小时的毫秒数
        Date date = new Date(l-s); //减去hour小时后的时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(date);

        Order order = new Order();
        Map<String, String> sqlMap = Maps.newHashMap();
        String where = "(a.status_union = '" + OrderStatus.STATUS_UNION_OP_DELIVERING + "'"
                     + " AND a.has_paid = '" + Global.YES + "'"
                     + " AND a.rough_pay_type = '" + Order.ROUGH_PAY_TYPE_OP + "'"
                     + " AND a.paid_date IS NOT NULL"
                     + " AND UNIX_TIMESTAMP(a.paid_date) < UNIX_TIMESTAMP('" + dateStr + "')"
                     + ")";
        sqlMap.put("where", where);
        order.setSqlMap(sqlMap);
        return dao.findList(order);
    }
}
