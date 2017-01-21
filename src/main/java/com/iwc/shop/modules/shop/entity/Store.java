package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.Map;

/**
 * 店铺实体
 * @author Tony Wong
 * @version 2015-04-19
 */
public class Store extends DataEntity<Store> {

	private static final long serialVersionUID = 1L;

	private String name;

    private static final String ID_ShenZhen_XiaShiWei = "1";    //深圳福永下十围店
    private static final String ID_ShenZhen_XingWei = "2";      //深圳福永兴围店

	public Store() {
		super();
	}

	public Store(String id) {
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
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
