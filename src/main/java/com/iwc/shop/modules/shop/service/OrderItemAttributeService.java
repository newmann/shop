/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.shop.dao.OrderItemAttributeDao;
import com.iwc.shop.modules.shop.entity.OrderItem;
import com.iwc.shop.modules.shop.entity.OrderItemAttribute;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预购订单产品属性Service
 * @author Tony Wong
 * @version 2015-05-28
 */
@Service
@Transactional(readOnly = true)
public class OrderItemAttributeService extends CrudService<OrderItemAttributeDao, OrderItemAttribute> {

    public List<OrderItemAttribute> findByItemId(String itemId) {
        OrderItem item = new OrderItem(itemId);
        OrderItemAttribute attribute = new OrderItemAttribute();
        attribute.setItem(item);
        return dao.findList(attribute);
    }

}
