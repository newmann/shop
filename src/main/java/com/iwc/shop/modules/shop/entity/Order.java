package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.entity.User;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单实体
 * @author Tony Wong
 * @version 2015-04-19
 */
public class Order extends DataEntity<Order> {

	private static final long serialVersionUID = 1L;

	private Cart cart;
	private Cookie cookie;
	private User user;
    private Preorder preorder;
    private Area area;
    private OrderStatus orderStatus;
    private String statusUnion;
    private String areaName;
    private String areaParentId;
    private String areaPathIds;
    private String areaPathNames;
    private String areaSimpleName;
    private String areaZipCode;
    private String storeId; //用户选择不同的地址，就会有不同的店铺id
	private String ip;
	private Integer totalCount;
	private Float totalPrice;
    private String addressFullname;
    private String addressTelephone;
    private String addressDetail;
    private String addressId;
    private String serialNo;
    private Integer printCount;
    private CouponUser couponUser;
    private Float couponUserTotalPrice;
    private Float originTotalPrice;
    private String notice;
    private String hasPaid;
    private Date paidDate;
    private String payType;
    private String roughPayType;
    private String opTransactionId; //在线支付的交易ID
    private String minTotalPriceLabel; //最少支付金额标记

    private List<OrderItem> itemList;

    //支付类型
    public static final String PAY_TYPE_CASH = "1";     //现金支付
    public static final String PAY_TYPE_WX = "2";       //微信支付
    public static final String PAY_TYPE_ALIPAY = "3";   //支付宝

    //粗略的支付类型
    public static final String ROUGH_PAY_TYPE_CASH = "1";     //现金支付
    public static final String ROUGH_PAY_TYPE_OP = "2";       //在线支付

    //最小送货金额
    // 送货要求：1，只有商品总额大于该值才送货；2，店内消费没这个要求
    public static final Float MIN_PRODUCT_AMOUNT_FOR_SHIPPING = 15f;

    //最少支付金额为1元
    public static final Float MIN_TOTAL_PRICE = 1f;
    public static final String MIN_TOTAL_PRICE_LABEL = "APP要求最少支付金额为￥1.00元";

    /**
     * 外卖下单时间段有限制，店内消费下单时间段没限制
     */
    public static final int ORDER_TIME_START_HOUR = 11;
    public static final int ORDER_TIME_END_HOUR = 24;
    public static final String ORDER_TIME_VALID_MSG = "亲~您在下单时间内可以下单";
    public static final String ORDER_TIME_INVALID_Title = "";
    public static final String ORDER_TIME_INVALID_MSG = "亲~外卖下单时间为11:00到24:00，请原谅";

    public Order() {
		super();
	}

    public Order(String id) {
        super(id);
    }

    public void preInsert(){
        super.preInsert();
        if (printCount == null)
            printCount = 0;
    }

    /**
     * 只转化当前字段, 方便给json用
     * @return
     */
    public Map<String, Object> toSimpleObj() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("createDate", createDate);
        map.put("createDateString", df.format(createDate));
        map.put("serialNo", serialNo);
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
        map.put("notice", notice);
        map.put("statusUnion", statusUnion);
        map.put("minTotalPriceLabel", minTotalPriceLabel);
        map.put("printCount", printCount);

        map.put("hasPaid", hasPaid);
        String hasPaidString;
        if (Global.YES.equals(hasPaid))
            hasPaidString = "已付款";
        else
            hasPaidString = "未付款";
        map.put("hasPaidString", hasPaidString);

        map.put("payType", payType);
        map.put("roughPayType", roughPayType);
        String payTypeString;
        if (PAY_TYPE_CASH.equals(payType))
            payTypeString = "现金支付";
        else if (PAY_TYPE_WX.equals(payType))
            payTypeString = "微信支付";
        else if (PAY_TYPE_ALIPAY.equals(payType))
            payTypeString = "支付宝支付";
        else
            payTypeString = "未知";
        map.put("payTypeString", payTypeString);

        String areaPathNames4Print = "";
        if (areaPathNames != null) {
            areaPathNames4Print = areaPathNames.replace("中国/广东省/", "");
            areaPathNames4Print = areaPathNames4Print.replace("/", "");
        }
        map.put("areaPathNames4Print", areaPathNames4Print);

        return map;
    }

    /**
     * 只转化当前字段, 方便给json用, 在前端float可能被解释为int，float，所以统一用string代替
     * @return
     */
    public Map<String, Object> toSimpleObjString() {
        Map<String, Object> map = toSimpleObj();
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("totalPrice", df.format(totalPrice));
        map.put("couponUserTotalPrice", df.format(couponUserTotalPrice));
        map.put("originTotalPrice", df.format(originTotalPrice));
        return map;
    }

    /**
     * 是否正确的支付方式
     */
    public static boolean isValidPayType(String payType) {
        if (StringUtils.isBlank(payType))
            return false;

        if (PAY_TYPE_CASH.equals(payType)
            || PAY_TYPE_WX.equals(payType)
            || PAY_TYPE_ALIPAY.equals(payType)) {
            return true;
        }

        return false;
    }

    /**
     * 是否正确的在线支付方式
     */
    public static boolean isValidOpPayType(String payType) {
        if (StringUtils.isBlank(payType))
            return false;

        if (PAY_TYPE_WX.equals(payType)
                || PAY_TYPE_ALIPAY.equals(payType)) {
            return true;
        }

        return false;
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

    public Preorder getPreorder() {
        return preorder;
    }

    public void setPreorder(Preorder preorder) {
        this.preorder = preorder;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
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

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(String hasPaid) {
        this.hasPaid = hasPaid;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getRoughPayType() {
        return roughPayType;
    }

    public void setRoughPayType(String roughPayType) {
        this.roughPayType = roughPayType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOpTransactionId() {
        return opTransactionId;
    }

    public void setOpTransactionId(String opTransactionId) {
        this.opTransactionId = opTransactionId;
    }

    public String getStatusUnion() {
        return statusUnion;
    }

    public void setStatusUnion(String statusUnion) {
        this.statusUnion = statusUnion;
    }

    public String getMinTotalPriceLabel() {
        return minTotalPriceLabel;
    }

    public void setMinTotalPriceLabel(String minTotalPriceLabel) {
        this.minTotalPriceLabel = minTotalPriceLabel;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    /**
     * 转换支付方式为粗略的支付方式
     */
    public static String payTypeToRoughPayType(String payType) {
        if (Order.PAY_TYPE_WX.equals(payType)
                || Order.PAY_TYPE_ALIPAY.equals(payType)) {
            return Order.ROUGH_PAY_TYPE_OP;
        }

        if (Order.PAY_TYPE_CASH.equals(payType)) {
            return Order.ROUGH_PAY_TYPE_CASH;
        }

        return null;
    }

    /**
     * 是否在下单时间段
     * @return
     */
    public static boolean isValidOrderTime() {
        Calendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(ORDER_TIME_START_HOUR <= hour && hour <= ORDER_TIME_END_HOUR){
            return true;
        }else{
            return false;
        }
    }
}
