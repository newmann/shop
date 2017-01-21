/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.OrderItemDao;
import com.iwc.shop.modules.shop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 订单项Service
 * @author Tony Wong
 * @version 2015-04-19
 */
@Service
@Transactional(readOnly = true)
public class OrderItemService extends CrudService<OrderItemDao, OrderItem> {

	@Autowired
	OrderItemAttributeService attributeService;

	@Autowired
	ShopProductAttributeItemService attrItemService;

	@Autowired
	ShopProductAttributeItemValueService attrValueService;

	/**
	 * 获取订单项，包括产品属性
	 */
	public List<OrderItem> findByOrderId(Order order) {
		OrderItem newItem = new OrderItem();
		newItem.setOrder(order);
		List<OrderItem> list = findList(newItem);

		for (OrderItem item : list) {
			List<OrderItemAttribute> attrList = attributeService.findByItemId(item.getId());

			item.setAttributeList(attrList);
		}

		return list;
	}

    public List<OrderItem> findByOrderId(String orderId) {
        Order order = new Order(orderId);
        OrderItem newItem = new OrderItem();
        newItem.setOrder(order);
        List<OrderItem> list = findList(newItem);

        for (OrderItem item : list) {
            List<OrderItemAttribute> attrList = attributeService.findByItemId(item.getId());
            item.setAttributeList(attrList);
        }

        return list;
    }
    public List<Map<String, Object>> findByOrderId4SimpleObj(Order order) {
        List<Map<String, Object>> oOrderItemList = Lists.newArrayList();

        OrderItem newItem = new OrderItem();
        newItem.setOrder(order);
        List<OrderItem> list = findList(newItem);

        for (OrderItem item : list) {
            Map<String, Object> oOrderItem = item.toSimpleObj();
            List<Map<String, Object>> oAttrList = Lists.newArrayList();
            String oAttrPrintNames = "";

            List<OrderItemAttribute> attrList = attributeService.findByItemId(item.getId());
            for (OrderItemAttribute attr : attrList) {
                Map<String, Object> oAttr = attr.toSimpleObj();

                // set oAttrPrintNames
                if (StringUtils.isNotBlank(attr.getAttributeItemValuePrintName())) {
                    if (StringUtils.isBlank(oAttrPrintNames)) {
                        oAttrPrintNames = attr.getAttributeItemValuePrintName();
                    } else {
                        oAttrPrintNames += "、" + attr.getAttributeItemValuePrintName();
                    }
                }

                oAttrList.add(oAttr);
            }

            oOrderItem.put("attrList", oAttrList);
            oOrderItem.put("attrPrintNames", oAttrPrintNames);
            oOrderItemList.add(oOrderItem);
        }

        return oOrderItemList;
    }
    //在前端float可能被解释为int，float，所以统一用string代替
    public List<Map<String, Object>> findByOrderId4SimpleObjString(Order order) {
        List<Map<String, Object>> oOrderItemList = Lists.newArrayList();

        OrderItem newItem = new OrderItem();
        newItem.setOrder(order);
        Page<OrderItem> page = new Page<OrderItem>();
        page.setOrderBy("a.type");
        newItem.setPage(page);
        List<OrderItem> list = findList(newItem);

        for (OrderItem item : list) {
            Map<String, Object> oOrderItem = item.toSimpleObjString();
            List<Map<String, Object>> oAttrList = Lists.newArrayList();
            String oAttrPrintNames = "";

            List<OrderItemAttribute> attrList = attributeService.findByItemId(item.getId());
            for (OrderItemAttribute attr : attrList) {
                Map<String, Object> oAttr = attr.toSimpleObj();

                // set oAttrPrintNames
                if (StringUtils.isNotBlank(attr.getAttributeItemValuePrintName())) {
                    if (StringUtils.isBlank(oAttrPrintNames)) {
                        oAttrPrintNames = attr.getAttributeItemValuePrintName();
                    } else {
                        oAttrPrintNames += "、" + attr.getAttributeItemValuePrintName();
                    }
                }

                oAttrList.add(oAttr);
            }

            oOrderItem.put("attrList", oAttrList);
            oOrderItem.put("attrPrintNames", oAttrPrintNames);
            oOrderItemList.add(oOrderItem);
        }

        return oOrderItemList;
    }

	public List<Map<String, Object>> findTopSeller4App() {
		return dao.findTopSeller4App();
	}
}
