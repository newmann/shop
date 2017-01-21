package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.User;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 订单项实体
 * @author Tony Wong
 * @version 2015-04-19
 */
public class OrderItem extends DataEntity<OrderItem> {

	private static final long serialVersionUID = 1L;

	private PreorderItem preorderItem;
	private CartItem cartItem; //CartItem有可能被删除而导致无法关联，也可能没有走购物车流程的直接购买
	private ShopProduct product;	//Product有可能被删除而导致无法关联
    private Order order;
    private User user;
	private String name;
	private String image;
	private String imageSmall;
    private String featuredImage;
	private Float price;
	private Integer count;
	private Float subtotalPrice;
    private String type;

	private List<OrderItemAttribute> attributeList;

	public OrderItem() {
		super();
	}

	public OrderItem(String id) {
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

        if (product != null) {
            Map<String, Object> oProduct = Maps.newHashMap();
            oProduct.put("id", product.getId());
            map.put("product", oProduct);
        }

        return map;
    }

    /**
     * 只转化当前字段, 方便给json用, 在前端float可能被解释为int，float，所以统一用string代替
     * @return
     */
    public Map<String, Object> toSimpleObjString() {
        Map<String, Object> map = toSimpleObj();
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("subtotalPrice", df.format(subtotalPrice));
        map.put("price", df.format(price));
        return map;
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

    public PreorderItem getPreorderItem() {
        return preorderItem;
    }

    public void setPreorderItem(PreorderItem preorderItem) {
        this.preorderItem = preorderItem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<OrderItemAttribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<OrderItemAttribute> attributeList) {
		this.attributeList = attributeList;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
