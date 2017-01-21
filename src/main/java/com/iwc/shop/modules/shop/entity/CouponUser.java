package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.User;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Tony Wong
 */
public class CouponUser extends DataEntity<CouponUser> {

    private static final long serialVersionUID = 1L;

    private User user;
    private String hasUsed;
    private String couponId;
    private String name;
    private String desc;
    private Float price;
    private Date startDate;
    private Date endDate;
    private String type;
    private String typeDesc;
    private Integer durationDay;
    private String durationDayDesc;
    private Integer usedType;
    private String usedTypeDesc;

    public CouponUser() {
        super();
        this.setHasUsed(Global.NO);
    }

    public CouponUser(String id) {
        super();
        this.id = id;
        this.setHasUsed(Global.NO);
    }

    /**
     * 只转化当前字段, 方便给json用
     * @return
     */
    public Map<String, Object> toSimpleObj() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("hasUsed", hasUsed);
        map.put("name", name);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("price", price);
        map.put("usedType", usedType);
        map.put("usedTypeDesc", usedTypeDesc);
        map.put("type", type);
        map.put("typeDesc", typeDesc);
        return map;
    }

    /**
     * 只转化当前字段, 方便给json用, 在前端float可能被解释为int，float，所以统一用string代替
     * @return
     */
    public Map<String, Object> toSimpleObjString() {
        Map<String, Object> map = toSimpleObj();
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("price", df.format(price));
        return map;
    }

    /**
     * 转化CouponUser为普通对象，并且计算剩余的天数等
     */
    public Map<String, Object> toSimpleObjWithCal() {
        Map<String, Object> map = toSimpleObj();
        String oLeftTimeStr;
        long leftTime;
        Date nowDate = new Date();
        if (hasUsed.equals(Global.NO)) {
            if (endDate != null) {
                if (endDate.getTime() > nowDate.getTime()) {
                    leftTime = (endDate.getTime() - nowDate.getTime()) / (24 * 3600 * 1000) + 1;
                    oLeftTimeStr = "剩" + leftTime + "天";
                } else {
                    leftTime = 0;
                    oLeftTimeStr = "已过期";
                }
            } else {
                leftTime = -1; //永久有效
                oLeftTimeStr = "有效";
            }
        } else {
            leftTime = 0;
            oLeftTimeStr = "已使用";
        }

        String oStartDateStr = "";
        String oEndDateStr = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (startDate != null) {
            oStartDateStr = df.format(startDate);
        }
        if (endDate != null) {
            oEndDateStr = df.format(endDate);
        }

        map.put("leftTime", leftTime);
        map.put("leftTimeStr", oLeftTimeStr);
        map.put("startDateStr", oStartDateStr);
        map.put("endDateStr", oEndDateStr);

        return map;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHasUsed() {
        return hasUsed;
    }

    public void setHasUsed(String hasUsed) {
        this.hasUsed = hasUsed;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
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
