package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.User;

import java.util.Map;

/**
 * @author Tony Wong
 */
public class HistoryProduct extends DataEntity<HistoryProduct> {

    private static final long serialVersionUID = 1L;

    private User user;
    private ShopProduct product;
    private Integer count;

    public HistoryProduct() {
        super();
    }

    public HistoryProduct(String id) {
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
        map.put("count", count);
        return map;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShopProduct getProduct() {
        return product;
    }

    public void setProduct(ShopProduct product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
