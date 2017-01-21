/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.common.persistence.SortEntity;
import com.iwc.shop.modules.sys.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 公用的商品属性Entity
 * @author Tony Wong
 * @version 2015-04-07
 */
public class ShopProductAttributeItem extends SortEntity<ShopProductAttributeItem> {

	private static final long serialVersionUID = 1L;

	private List<ShopProductAttributeItemValue> valueList = Lists.newArrayList();
	private String name;
	private String printName;

	public ShopProductAttributeItem() {
		super();
	}

	public ShopProductAttributeItem(String id) {
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
        map.put("printName", printName);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<ShopProductAttributeItemValue> getValueList() {
		return valueList;
	}

	public void setValueList(List<ShopProductAttributeItemValue> valueList) {
		this.valueList = valueList;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}
}


