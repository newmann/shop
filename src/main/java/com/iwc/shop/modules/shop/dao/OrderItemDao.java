/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.dao;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.shop.entity.OrderItem;

import java.util.List;
import java.util.Map;

/**
 * 订单项DAO接口
 * @author Tony Wong
 * @version 2015-04-16
 */
@MyBatisDao
public interface OrderItemDao extends CrudDao<OrderItem> {

    List<Map<String, Object>> findTopSeller4App();

}
