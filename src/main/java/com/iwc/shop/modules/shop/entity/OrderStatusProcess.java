package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.Map;

/**
 * 订单状态流程集
 * @author Tony Wong
 * @version 2015-08-08
 */
public class OrderStatusProcess extends DataEntity<OrderStatusProcess> {

	private static final long serialVersionUID = 1L;

	private String statusUnion; //格式：roughPayType-status 如 1-100
    private String label;
    private String isFinished;
    private Integer step;
    private String cssClass;

    public OrderStatusProcess() {
        super();
    }

    public OrderStatusProcess(String id) {
        super(id);
    }

    /**
     * 只转化当前字段, 方便给json用
     * @return
     */
    public Map<String, Object> toSimpleObj() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("statusUnion", statusUnion);
        map.put("label", label);
        map.put("statusUnion", statusUnion);
        map.put("isFinished", isFinished);
        map.put("step", step);
        map.put("cssClass", cssClass);
        return map;
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

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
