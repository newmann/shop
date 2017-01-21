package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Lists;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.List;

/**
 * @author Tony Wong
 */
public class CartItem extends DataEntity<CartItem> {

	private static final long serialVersionUID = 1L;

	private Cart cart;
	private ShopProduct product;
	private Integer count;
	private String isOrdered; //是否已经下单
	private String isSelected;
	private String userId;
	private String cookieId;
	private String appCartCookieId;
	
	private List<CartItemAttribute> attributeList;
	
	// isOrdered
	public static final String IS_ORDERED_NO = "0";
	public static final String IS_ORDERED_YES = "1";

	public CartItem() {
		super();
	}

	public CartItem(String id) {
		super(id);
	}

	/**
	 * 插入之前执行方法
	 */
	@Override
	public void preInsert(){
		super.preInsert();
		if (isOrdered == null)
			isOrdered = IS_ORDERED_NO;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public ShopProduct getProduct() {
		return product;
	}

	public void setProduct(ShopProduct product) {
		this.product = product;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getIsOrdered() {
		return isOrdered;
	}

	public void setIsOrdered(String isOrdered) {
		this.isOrdered = isOrdered;
	}

	public List<CartItemAttribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<CartItemAttribute> attributeList) {
		this.attributeList = attributeList;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getAppCartCookieId() {
        return appCartCookieId;
    }

    public void setAppCartCookieId(String appCartCookieId) {
        this.appCartCookieId = appCartCookieId;
    }
}
