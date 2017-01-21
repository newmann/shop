/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.Map;

/**
 * 公用的商品属性值Entity
 * @author Tony Wong
 * @version 2015-04-07
 */
public class ShopProductAttributeItemValue extends DataEntity<ShopProductAttributeItemValue> {

	private static final long serialVersionUID = 1L;

	private ShopProductAttributeItem item;
	private String name;
	private Float price;
	private String printName;
	private Integer sort;
    private String isStandard; //value:0,1

	public ShopProductAttributeItemValue() {
		super();
	}

	public ShopProductAttributeItemValue(String id) {
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
        map.put("price", price);
        map.put("printName", printName);
        map.put("isStandard", isStandard);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public ShopProductAttributeItem getItem() {
		return item;
	}

	public void setItem(ShopProductAttributeItem item) {
		this.item = item;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }
}


