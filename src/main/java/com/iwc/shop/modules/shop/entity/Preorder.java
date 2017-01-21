package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.entity.User;

import java.util.Map;

/**
 * 预购订单实体
 * @author Tony Wong
 * @version 2015-04-19
 */
public class Preorder extends DataEntity<Preorder> {

	private static final long serialVersionUID = 1L;

	private Cart cart;
	private Cookie cookie;
	private User user;
	private String ip;
	private Integer totalCount;
	private Float totalPrice;
	private String isOrdered; //是否已经下单
    private CouponUser couponUser;
    private Float couponUserTotalPrice;
    private Float originTotalPrice;
    private Area area;
    private String areaName;
    private String areaParentId;
    private String areaPathIds;
    private String areaPathNames;
    private String areaSimpleName;
    private String areaZipCode;
    private String storeId; //用户选择不同的地址，就会有不同的店铺id
    private String addressFullname;
    private String addressTelephone;
    private String addressDetail;
    private String addressId;
    private String payType; //@see Order entity
    private String roughPayType;
    private String minTotalPriceLabel; //最少支付金额标记

	// isOrdered
	public static final String IS_ORDERED_NO = "0";
	public static final String IS_ORDERED_YES = "1";

	public Preorder() {
		super();
	}

	public Preorder(String id) {
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

	/**
	 * 只转化当前字段, 方便给json用
	 * @return
	 */
	public Map<String, Object> toSimpleObj() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", id);
		map.put("totalCount", totalCount);
		map.put("totalPrice", totalPrice);
        map.put("couponUserTotalPrice", couponUserTotalPrice);
        map.put("originTotalPrice", originTotalPrice);
        map.put("areaName", areaName);
        map.put("areaParentId", areaParentId);
        map.put("areaPathIds", areaPathIds);
        map.put("areaPathNames", areaPathNames);
        map.put("areaSimpleName", areaSimpleName);
        map.put("areaZipCode", areaZipCode);
        map.put("storeId", storeId);
        map.put("addressFullname", addressFullname);
        map.put("addressTelephone", addressTelephone);
        map.put("addressDetail", addressDetail);
        map.put("addressId", addressId);
        map.put("payType", payType);
        map.put("minTotalPriceLabel", minTotalPriceLabel);
		return map;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Cookie getCookie() {
		return cookie;
	}

	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getIsOrdered() {
		return isOrdered;
	}

	public void setIsOrdered(String isOrdered) {
		this.isOrdered = isOrdered;
	}

    public CouponUser getCouponUser() {
        return couponUser;
    }

    public void setCouponUser(CouponUser couponUser) {
        this.couponUser = couponUser;
    }

    public Float getCouponUserTotalPrice() {
        return couponUserTotalPrice;
    }

    public void setCouponUserTotalPrice(Float couponUserTotalPrice) {
        this.couponUserTotalPrice = couponUserTotalPrice;
    }

    public Float getOriginTotalPrice() {
        return originTotalPrice;
    }

    public void setOriginTotalPrice(Float originTotalPrice) {
        this.originTotalPrice = originTotalPrice;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(String areaParentId) {
        this.areaParentId = areaParentId;
    }

    public String getAreaPathIds() {
        return areaPathIds;
    }

    public void setAreaPathIds(String areaPathIds) {
        this.areaPathIds = areaPathIds;
    }

    public String getAreaPathNames() {
        return areaPathNames;
    }

    public void setAreaPathNames(String areaPathNames) {
        this.areaPathNames = areaPathNames;
    }

    public String getAreaSimpleName() {
        return areaSimpleName;
    }

    public void setAreaSimpleName(String areaSimpleName) {
        this.areaSimpleName = areaSimpleName;
    }

    public String getAreaZipCode() {
        return areaZipCode;
    }

    public void setAreaZipCode(String areaZipCode) {
        this.areaZipCode = areaZipCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAddressFullname() {
        return addressFullname;
    }

    public void setAddressFullname(String addressFullname) {
        this.addressFullname = addressFullname;
    }

    public String getAddressTelephone() {
        return addressTelephone;
    }

    public void setAddressTelephone(String addressTelephone) {
        this.addressTelephone = addressTelephone;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRoughPayType() {
        return roughPayType;
    }

    public void setRoughPayType(String roughPayType) {
        this.roughPayType = roughPayType;
    }

    public String getMinTotalPriceLabel() {
        return minTotalPriceLabel;
    }

    public void setMinTotalPriceLabel(String minTotalPriceLabel) {
        this.minTotalPriceLabel = minTotalPriceLabel;
    }
}
