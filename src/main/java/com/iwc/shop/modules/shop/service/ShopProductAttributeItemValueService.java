/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.shop.dao.ShopProductAttributeItemDao;
import com.iwc.shop.modules.shop.dao.ShopProductAttributeItemValueDao;
import com.iwc.shop.modules.shop.entity.ShopProductAttributeItem;
import com.iwc.shop.modules.shop.entity.ShopProductAttributeItemValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公用的商品属性值Service
 * @author Tony Wong
 * @version 2015-05-15
 */
@Service
@Transactional(readOnly = true)
public class ShopProductAttributeItemValueService extends CrudService<ShopProductAttributeItemValueDao, ShopProductAttributeItemValue> {

	/**
	 * 通过属性itemId获取属性值列表
	 */
	public List<ShopProductAttributeItemValue> findByItemId(String itemId) {
		ShopProductAttributeItem item = new ShopProductAttributeItem(itemId);
		return findByItemId(item);
	}

	/**
	 * 通过属性itemId获取属性值列表
	 */
	public List<ShopProductAttributeItemValue> findByItemId(ShopProductAttributeItem item) {
		ShopProductAttributeItemValue value = new ShopProductAttributeItemValue();
		value.setItem(item);
		return findList(value);
	}
}
