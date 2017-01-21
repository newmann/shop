package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.Date;
import java.util.Map;

/**
 * @author Tony Wong
 */
public class Coupon extends DataEntity<Coupon> {

	private static final long serialVersionUID = 1L;

    private String name;
    private String desc;
    private Float price;
    private Date startDate;
    private Date endDate;
    private String type;
    private String typeDesc;
    private Integer durationDay;
    private String durationDayDesc;
    private Integer countPerUser;
    private Integer usedType;
    private String usedTypeDesc;

    public static final String ID_REGISTER1 = "1"; //买一送一, 买多送多, 无上限哈, 给新注册用户发放三张
    public static final String ID_SHAKE_SEZI_USER = "2"; // 每天摇一摇的用户都有一张

    public static final String TYPE_REGISTER_USER = "1"; // 给新注册用户发放
    public static final String TYPE_SHAKE_SEZI_USER = "2"; // 每天摇一摇的用户都有一张

	public Coupon() {
		super();
	}

	public Coupon(String id) {
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
        map.put("name", name);
        map.put("price", price);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Integer getDurationDay() {
        return durationDay;
    }

    public void setDurationDay(Integer durationDay) {
        this.durationDay = durationDay;
    }

    public String getDurationDayDesc() {
        return durationDayDesc;
    }

    public void setDurationDayDesc(String durationDayDesc) {
        this.durationDayDesc = durationDayDesc;
    }

    public Integer getCountPerUser() {
        return countPerUser;
    }

    public void setCountPerUser(Integer countPerUser) {
        this.countPerUser = countPerUser;
    }

    public Integer getUsedType() {
        return usedType;
    }

    public void setUsedType(Integer usedType) {
        this.usedType = usedType;
    }

    public String getUsedTypeDesc() {
        return usedTypeDesc;
    }

    public void setUsedTypeDesc(String usedTypeDesc) {
        this.usedTypeDesc = usedTypeDesc;
    }
}
