/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.shop.dao.ShopProductAttributeDao;
import com.iwc.shop.modules.shop.dao.ShopProductAttributeItemDao;
import com.iwc.shop.modules.shop.dao.ShopProductAttributeItemValueDao;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.entity.ShopProductAttribute;
import com.iwc.shop.modules.shop.entity.ShopProductAttributeItem;
import com.iwc.shop.modules.shop.entity.ShopProductAttributeItemValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品属性Service
 * @author Tony Wong
 * @version 2015-05-15
 */
@Service
@Transactional(readOnly = true)
public class ShopProductAttributeService extends CrudService<ShopProductAttributeDao, ShopProductAttribute> {

	@Autowired
	ShopProductAttributeItemValueService valueService;

	/**
	 * 获取产品属性列表，不包括关联的属性值列表
	 * @param productId
	 * @return
	 */
	public List<ShopProductAttribute> findByProductId(String productId) {
		ShopProduct product = new ShopProduct(productId);
		return findByProductId(product);
	}

	/**
	 * 获取产品属性列表，不包括关联的属性值列表
	 * @param product
	 * @return
	 */
	public List<ShopProductAttribute> findByProductId(ShopProduct product) {
		ShopProductAttribute attribute = new ShopProductAttribute();
		attribute.setProduct(product);
		return findList(attribute);
	}

	/**
	 * 获取产品属性列表，包括关联的属性值列表
	 * @param productId
	 * @param withValueList
	 * @return
	 */
	public List<ShopProductAttribute> findByProductId(String productId, boolean withValueList) {
		ShopProduct product = new ShopProduct(productId);
		return findByProductId(product, true);
	}

	/**
	 * 获取产品属性列表，包括关联的属性值列表
	 * @param product
	 * @param withValueList
	 * @return
	 */
	public List<ShopProductAttribute> findByProductId(ShopProduct product, boolean withValueList) {
		List<ShopProductAttribute> attributeList = findByProductId(product);
		for (ShopProductAttribute attribute : attributeList) {
			if (attribute.getItem() != null) {
				List<ShopProductAttributeItemValue> valueList = valueService.findByItemId(attribute.getItem());
				attribute.getItem().setValueList(valueList);
			}
		}
		return attributeList;
	}

	@Transactional(readOnly = false)
	public void deleteByProductId(String productId) {
		dao.deleteByProductId(productId);
	}
}
