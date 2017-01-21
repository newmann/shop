package com.iwc.shop.modules.shop.entity;

import com.iwc.shop.common.persistence.DataEntity;

/**
 * @author Tony Wong
 */
public class CartItemAttribute extends DataEntity<CartItemAttribute> {

	private static final long serialVersionUID = 1L;

	private CartItem item;
	private ShopProductAttributeItem attributeItem;
	private ShopProductAttributeItemValue attributeItemValue;
	private String attributeIdstring;
	
	public CartItemAttribute() {
		super();
	}

	public CartItem getItem() {
		return item;
	}

	public void setItem(CartItem item) {
		this.item = item;
	}

	public ShopProductAttributeItem getAttributeItem() {
		return attributeItem;
	}

	public void setAttributeItem(ShopProductAttributeItem attributeItem) {
		this.attributeItem = attributeItem;
	}

	public ShopProductAttributeItemValue getAttributeItemValue() {
		return attributeItemValue;
	}

	public void setAttributeItemValue(ShopProductAttributeItemValue attributeItemValue) {
		this.attributeItemValue = attributeItemValue;
	}

	public String getAttributeIdstring() {
		return attributeIdstring;
	}

	public void setAttributeIdstring(String attributeIdstring) {
		this.attributeIdstring = attributeIdstring;
	}
}
