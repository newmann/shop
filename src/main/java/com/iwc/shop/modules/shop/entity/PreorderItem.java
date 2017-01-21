package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.List;
import java.util.Map;

/**
 * 预购订单项实体
 * @author Tony Wong
 * @version 2015-04-19
 */
public class PreorderItem extends DataEntity<PreorderItem> {

	private static final long serialVersionUID = 1L;

	private Preorder preorder;
	private CartItem cartItem; //CartItem有可能被删除而导致无法关联，也可能没有走购物车流程的直接购买
	private ShopProduct product;	//Product有可能被删除而导致无法关联
	private String name;
	private String image;
	private String imageSmall;
	private String featuredImage;
	private Float price; //当个产品的最终价格，即价格+属性价格
	private Integer count;
	private Float subtotalPrice;
    private String type;//产品类型

	private List<PreorderItemAttribute> attributeList;

	public PreorderItem() {
		super();
	}

	public PreorderItem(String id) {
		super(id);
	}

	/**
	 * 只转化当前字段, 方便给json用
	 * @return
	 */
	public Map<String, Object> toSimpleObj() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", id);
		map.put("name", name);
		map.put("imageSmall", Global.URL + imageSmall);
        map.put("price", price);
        map.put("count", count);
        map.put("subtotalPrice", subtotalPrice);
        map.put("type", type);
		return map;
	}

	public Preorder getPreorder() {
		return preorder;
	}

	public void setPreorder(Preorder preorder) {
		this.preorder = preorder;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	public ShopProduct getProduct() {
		return product;
	}

	public void setProduct(ShopProduct product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getSubtotalPrice() {
		return subtotalPrice;
	}

	public void setSubtotalPrice(Float subtotalPrice) {
		this.subtotalPrice = subtotalPrice;
	}

	public String getFeaturedImage() {
		return featuredImage;
	}

	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}

	public List<PreorderItemAttribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<PreorderItemAttribute> attributeList) {
		this.attributeList = attributeList;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
