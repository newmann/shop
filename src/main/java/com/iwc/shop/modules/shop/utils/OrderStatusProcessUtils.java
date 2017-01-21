/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.modules.shop.entity.OrderStatus;
import com.iwc.shop.modules.shop.entity.OrderStatusProcess;
import com.iwc.shop.modules.shop.service.OrderStatusProcessService;
import com.iwc.shop.modules.shop.service.OrderStatusService;

import java.util.List;

/**
 * 订单状态序列集,记录不会被修改，所有不用请缓存
 * @author Tony Wong
 * @version 2015-08-08
 */
public class OrderStatusProcessUtils {

	private static OrderStatusProcessService orderStatusService = SpringContextHolder.getBean(OrderStatusProcessService.class);

	/** cache name and keys **/
	public static final String CACHE_NAME = "OrderStatusProcess";
	public static final String CK_OrderStatusProcess_ = "OrderStatusProcess_";
    public static final String CK_findByStatusUnion_ = "findByStatusUnion_";

	public static OrderStatusProcess get(String id) {
		String key = CK_OrderStatusProcess_ + id;
        OrderStatusProcess process = (OrderStatusProcess) getCache(key);
		if (process == null) {
            process = orderStatusService.get(id);
			putCache(key, process);
		}
		return process;
	}

    public static List<OrderStatusProcess> findByStatusUnion(String statusUnion) {
        String key = CK_findByStatusUnion_ + statusUnion;
        List<OrderStatusProcess> processList = (List<OrderStatusProcess>) getCache(key);
        if (processList == null) {
            processList = orderStatusService.findByStatusUnion(statusUnion);
            putCache(key, processList);
        }
        return processList;
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