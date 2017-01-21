package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.Map;

/**
 * 色子每天摇一摇对象
 * @author Tony Wong
 */
public class Sezi extends DataEntity<Sezi> {

	private static final long serialVersionUID = 1L;

	private String num;
	private String userId;
	private String isMaxInDay;
    private Float price;

    /**
     * 每天摇一摇可以保存的次数
     */
    public static final int SHAKE_COUNT_DAILY = 3;

	public Sezi() {
		super();
	}

	public Sezi(String id) {
		super();
		this.id = id;
	}

    /**
     * 只转化当前字段, 方便给json用
     * @return
     */
    public Map<String, Object> toSimpleObj() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("createDate", createDate);
        map.put("isMaxInDay", isMaxInDay);
        map.put("price", price);
        map.put("num", num);
        return map;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsMaxInDay() {
        return isMaxInDay;
    }

    public void setIsMaxInDay(String isMaxInDay) {
        this.isMaxInDay = isMaxInDay;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
