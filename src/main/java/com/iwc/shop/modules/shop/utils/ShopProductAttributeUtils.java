/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 商品属性工具类
 * @author Tony Wong
 * @version 2015-04-06
 */
public class ShopProductAttributeUtils {

	/**
	 * Convert idstring to map
	 * @param attributes 的格式 itemId_itemValueId, 如1_1
	 * @return map的格式 {itemId: 1, valueId: 1, idstring: 1_1}
	 */
	public static List<Map<String, String>> idstringToMap(List<String> attributes) {
		List<Map<String, String>> attributeMaps = Lists.newArrayList();
		if (attributes != null) {
			for (String idstring : attributes) {
				Map<String, String> map = Maps.newHashMap();
				String[] array = idstring.split("_");
				map.put("itemId", array[0]);
				map.put("valueId", array[1]);
				map.put("idstring", idstring);
				attributeMaps.add(map);
			}
		}
		return attributeMaps;
	}

}