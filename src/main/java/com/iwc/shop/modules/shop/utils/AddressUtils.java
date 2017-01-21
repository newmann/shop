/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.entity.Address;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.AddressService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.utils.AreaUtils;

import java.util.List;

/**
 * 用户地址工具类
 * @author Tony Wong
 * @version 2015-04-06
 */
public class AddressUtils {

	private static AddressService addressService = SpringContextHolder.getBean(AddressService.class);

	/** cache name and keys **/
	public static final String CACHE_NAME = "Address";
	public static final String CK_address_ = "address_";

	/**
	 * 获得默认Address对象，包括关联的Area
     * @todo 更新Area要清空对应的Address缓存, 该功能还没做
	 * @param id
	 * @return
	 */
	public static Address get (String id) {
		String key = CK_address_ + id;
        Address address = (Address) getCache(key);
		if (address == null) {
            address = addressService.get(id);
            if (address != null && address.getArea() != null
                    && StringUtils.isNotBlank(address.getArea().getId())) {
                Area area = AreaUtils.getArea(address.getArea().getId());
                address.setArea(area);
            }
			putCache(key, address);
		}
		return address;
	}

	// ============== cache ==============

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