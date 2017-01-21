/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.shop.dao.OrderStatusDao;
import com.iwc.shop.modules.shop.entity.OrderStatus;
import com.iwc.shop.modules.shop.utils.OrderStatusProcessUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单状态序列集Service
 * @author Tony Wong
 * @version 2015-04-19
 */
@Service
@Transactional(readOnly = true)
public class OrderStatusService extends CrudService<OrderStatusDao, OrderStatus> {

    public OrderStatus getX(String id) {
        OrderStatus orderStatus = get(id);
        if (orderStatus != null) {
            orderStatus.setStatusProcessList(OrderStatusProcessUtils.findByStatusUnion(orderStatus.getStatusUnion()));

        }
        return orderStatus;
    }

}
