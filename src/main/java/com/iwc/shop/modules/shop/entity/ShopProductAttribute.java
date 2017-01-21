/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.entity;

import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.User;

/**
 * 商品属性Entity
 * @author Tony Wong
 * @version 2015-04-07
 */
public class ShopProductAttribute extends DataEntity<ShopProductAttribute> {

	private static final long serialVersionUID = 1L;

	private ShopProductAttributeItem item;
	private ShopProduct product;

	public ShopProductAttribute() {
		super();
	}

	public ShopProductAttribute(String id) {
		this();
		this.id = id;
	}

    public ShopProductAttributeItem getItem() {
        return item;
    }

    public void setItem(ShopProductAttributeItem item) {
        this.item = item;
    }

    public ShopProduct getProduct() {
        return product;
    }

    public void setProduct(ShopProduct product) {
        this.product = product;
    }
}


