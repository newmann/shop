/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.modules.shop.entity.OrderStatus;
import com.iwc.shop.modules.shop.service.OrderStatusService;

/**
 * 订单状态序列集,记录不会被修改，所有不用请缓存
 * @author Tony Wong
 * @version 2015-08-08
 */
public class OrderStatusUtils {

	private static OrderStatusService orderStatusService = SpringContextHolder.getBean(OrderStatusService.class);

	/** cache name and keys **/
	public static final String CACHE_NAME = "OrderStatus";
	public static final String CK_OrderStatus_ = "OrderStatus_";

	public static OrderStatus get(String orderStatusId) {
		String key = CK_OrderStatus_ + orderStatusId;
        OrderStatus orderStatus = (OrderStatus) getCache(key);
		if (orderStatus == null) {
			orderStatus = orderStatusService.getX(orderStatusId);
			putCache(key, orderStatus);
		}
		return orderStatus;
	}

	// ============== shop product cache ==============

	public static Object getCache(String key) {
		return CacheUtils.get(CACHE_NAME, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(CACHE_NAME, key, value);
	}

	public static void removeAllCache() {
		CacheUtils.removeAll(CACHE_NAME);
	}
}