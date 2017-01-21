package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单状态序列集
 * @author Tony Wong
 * @version 2015-08-08
 */
public class OrderStatus extends DataEntity<OrderStatus> {

	private static final long serialVersionUID = 1L;

	private Order order;
	private Integer status;
	private String roughPayType; //关联于 Order.roughPayType
	private String statusUnion; //格式：roughPayType-status 如 1-100
    private String label;
    private String pendingLabel;

    //根据statusUnion获取
    private List<OrderStatusProcess> statusProcessList;

    //status
    //现金支付，最后三步同时完成
    public static final Integer STATUS_CASH_ORDERED = 100; //已下单
    public static final Integer STATUS_CASH_DELIVERING = 200; //配送中
    public static final Integer STATUS_CASH_RECEIVED = 300; //已收货
    public static final Integer STATUS_CASH_PAID = 400; //已付款
    public static final Integer STATUS_CASH_FINISHED = 1000; //交易完成
    public static final Integer STATUS_CASH_CANCEL = 9999; //交易取消
    //在线支付online payment，最后二步同时完成
    public static final Integer STATUS_OP_ORDERED = 100; //已下单
    public static final Integer STATUS_OP_PAID = 200; //已付款
    public static final Integer STATUS_OP_DELIVERING = 300; //配送中
    public static final Integer STATUS_OP_RECEIVED = 400; //已收货
    public static final Integer STATUS_OP_FINISHED = 1000; //交易完成
    public static final Integer STATUS_OP_CANCEL = 9999; //交易取消

    //statusUnion 状态标识 roughPayType-status
    //现金支付，最后三步同时完成
    public static final String STATUS_UNION_CASH_ORDERED = "1-100"; //已下单，流程展示：已下单>待配送>待收货>待付款>交易完成
    public static final String STATUS_UNION_CASH_DELIVERING = "1-200"; //配送中，流程展示：已下单>配送中>待收货>待付款>交易完成
    public static final String STATUS_UNION_CASH_RECEIVED = "1-300"; //已收货，流程展示：已下单>已配送>已收货>待付款>交易完成
    public static final String STATUS_UNION_CASH_PAID = "1-400"; //已付款，流程展示：已下单>已配送>已收货>已付款>交易完成
    public static final String STATUS_UNION_CASH_FINISHED = "1-1000"; //交易完成，流程展示：已下单>已配送>已收货>已付款>交易完成
    public static final String STATUS_UNION_CASH_CANCEL = "1-9999"; //交易取消，流程展示：已下单>...>交易取消
    //在线支付online payment，最后二步同时完成
    public static final String STATUS_UNION_OP_ORDERED = "2-100"; //已下单，流程展示：已下单>待配送>待付款>待收货>交易完成
    public static final String STATUS_UNION_OP_PAID = "2-200"; //已付款，流程展示：已下单>已付款>待配送>待收货>交易完成
    public static final String STATUS_UNION_OP_DELIVERING = "2-300"; //配送中，流程展示：已下单>已付款>配送中>待收货>待付款>交易完成
    public static final String STATUS_UNION_OP_RECEIVED = "2-400"; //已收货，流程展示：已下单>已付款>已配送>已收货>交易完成
    public static final String STATUS_UNION_OP_FINISHED = "2-1000"; //交易完成，流程展示：已下单>已付款>已配送>已收货>交易完成
    public static final String STATUS_UNION_OP_CANCEL = "2-9999"; //交易取消，流程展示：已下单>未付款>交易取消

    //label
    //现金支付，最后三步同时完成
    public static final String LABEL_CASH_ORDERED = "已下单"; //已下单
    public static final String LABEL_CASH_DELIVERING = "配送中"; //配送中
    public static final String LABEL_CASH_RECEIVED = "已收货"; //已收货
    public static final String LABEL_CASH_PAID = "已付款"; //已付款
    public static final String LABEL_CASH_FINISHED = "交易完成"; //交易完成
    public static final String LABEL_CASH_CANCEL = "交易取消"; //交易取消
    //对应等待label
    public static final String LABEL_CASH_ORDERED_PENDING = "待配送";
    public static final String LABEL_CASH_DELIVERING_PENDING = "待收货";
    public static final String LABEL_CASH_RECEIVED_PENDING = "待付款";
    public static final String LABEL_CASH_PAID_PENDING = "已付款";
    public static final String LABEL_CASH_FINISHED_PENDING = "交易完成";
    public static final String LABEL_CASH_FINISHED_CANCEL = "交易取消";

    //在线支付online payment，最后二步同时完成
    public static final String LABEL_OP_ORDERED = "已下单"; //已下单
    public static final String LABEL_OP_PAID = "已付款"; //已付款
    public static final String LABEL_OP_DELIVERING = "配送中"; //配送中
    public static final String LABEL_OP_RECEIVED = "已收货"; //已收货
    public static final String LABEL_OP_FINISHED = "交易完成"; //交易完成
    public static final String LABEL_OP_CANCEL = "交易取消"; //交易取消
    //对应等待label
    public static final String LABEL_OP_ORDERED_PENDING = "待付款";
    public static final String LABEL_OP_PAID_PENDING = "待配送";
    public static final String LABEL_OP_DELIVERING_PENDING = "待收货";
    public static final String LABEL_OP_RECEIVED_PENDING = "已收货";
    public static final String LABEL_OP_FINISHED_PENDING = "交易完成";
    public static final String LABEL_OP_FINISHED_CANCEL = "交易取消";

	public OrderStatus() {
		super();
	}

	public OrderStatus(String id) {
		super(id);
	}

    /**
     * 只转化当前字段, 方便给json用
     * @return
     */
    public Map<String, Object> toSimpleObj() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("status", status);
        map.put("roughPayType", roughPayType);
        map.put("statusUnion", statusUnion);
        map.put("label", label);
        map.put("pendingLabel", pendingLabel);
        return map;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoughPayType() {
        return roughPayType;
    }

    public void setRoughPayType(String roughPayType) {
        this.roughPayType = roughPayType;
    }

    public String getStatusUnion() {
        return statusUnion;
    }

    public void setStatusUnion(String statusUnion) {
        this.statusUnion = statusUnion;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<OrderStatusProcess> getStatusProcessList() {
        return statusProcessList;
    }

    public void setStatusProcessList(List<OrderStatusProcess> statusProcessList) {
        this.statusProcessList = statusProcessList;
    }

    public String getPendingLabel() {
        return pendingLabel;
    }

    public void setPendingLabel(String pendingLabel) {
        this.pendingLabel = pendingLabel;
    }
}
