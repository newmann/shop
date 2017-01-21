/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.modules.shop.entity.ShopProductAttributeItem;
import com.iwc.shop.modules.shop.service.ShopProductAttributeItemService;

import java.util.List;

/**
 * 商品属性项工具类
 * @author Tony Wong
 * @version 2015-04-06
 */
public class ShopProductAttributeItemUtils {

	private static ShopProductAttributeItemService attrItemService = SpringContextHolder.getBean(ShopProductAttributeItemService.class);

	/** cache name and keys **/
	public static final String CACHE_NAME = "ShopProductAttributeItem";
	public static final String CK_ENTITY_ = "ShopProductAttributeItem_";
	public static final String CK_LIST = "ShopProductAttributeItem_List";

	public static ShopProductAttributeItem getAttrItem (String itemId) {
		String key = CK_ENTITY_ + itemId;
		ShopProductAttributeItem item = (ShopProductAttributeItem) getCache(key);
		if (item == null) {
			item = attrItemService.get(itemId);
			putCache(key, item);
		}
		return item;
	}

	/**
	 * 商品属性项列表
	 */
	public static List<ShopProductAttributeItem> getList() {
		List<ShopProductAttributeItem> list = (List<ShopProductAttributeItem>) getCache(CK_LIST);
		if (list == null) {
			list = attrItemService.findAll();
			putCache(CK_LIST, list);
		}
		return list;
	}

	// ============== shop product cache ==============

	public static Object getCache(String key) {
		return CacheUtils.get(CACHE_NAME, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(CACHE_NAME, key, value);
	}

	public static void removeCache(String key) {
		CacheUtils.remove(CACHE_NAME, key);
	}

	public static void removeCaches() {
		//removeCache(CK_LIST_PREFIX);
	}

}