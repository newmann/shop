package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.DataEntity;

import java.util.Map;

/**
 * @author Tony Wong
 */
public class OrderItemAttribute extends DataEntity<OrderItemAttribute> {

	private static final long serialVersionUID = 1L;

	private OrderItem item;
	private String attributeIdstring;

	/**
	 * 来自 ShopProductAttributeItem
	 */
	private String attributeItemId;
	private String attributeItemName;
	private String attributeItemPrintName;
	private Integer attributeItemSort;

	/**
	 * 来自 ShopProductAttributeItemValue
	 */
	private String attributeItemValueId;
	private String attributeItemValueName;
	private String attributeItemValuePrintName;
	private Float attributeItemValuePrice;
    private Integer attributeItemValueSort;
    private String attributeItemValueIsStandard;

	public OrderItemAttribute() {
		super();
	}

	/**
	 * 只转化当前字段, 方便给json用
	 * @return
	 */
	public Map<String, Object> toSimpleObj() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", id);
		map.put("attributeItemId", attributeItemId);
		map.put("attributeItemName", attributeItemName);
		map.put("attributeItemPrintName", attributeItemPrintName);
		map.put("attributeItemValueId", attributeItemValueId);
		map.put("attributeItemValueName", attributeItemValueName);
		map.put("attributeItemValuePrintName", attributeItemValuePrintName);
		map.put("attributeItemValuePrice", attributeItemValuePrice);
        map.put("attributeItemValueIsStandard", attributeItemValueIsStandard);
		return map;
	}

	public OrderItem getItem() {
		return item;
	}

	public void setItem(OrderItem item) {
		this.item = item;
	}

	public String getAttributeIdstring() {
		return attributeIdstring;
	}

	public void setAttributeIdstring(String attributeIdstring) {
		this.attributeIdstring = attributeIdstring;
	}

	public String getAttributeItemId() {
		return attributeItemId;
	}

	public void setAttributeItemId(String attributeItemId) {
		this.attributeItemId = attributeItemId;
	}

	public String getAttributeItemName() {
		return attributeItemName;
	}

	public void setAttributeItemName(String attributeItemName) {
		this.attributeItemName = attributeItemName;
	}

	public String getAttributeItemPrintName() {
		return attributeItemPrintName;
	}

	public void setAttributeItemPrintName(String attributeItemPrintName) {
		this.attributeItemPrintName = attributeItemPrintName;
	}

	public Integer getAttributeItemSort() {
		return attributeItemSort;
	}

	public void setAttributeItemSort(Integer attributeItemSort) {
		this.attributeItemSort = attributeItemSort;
	}

	public String getAttributeItemValueId() {
		return attributeItemValueId;
	}

	public void setAttributeItemValueId(String attributeItemValueId) {
		this.attributeItemValueId = attributeItemValueId;
	}

	public String getAttributeItemValueName() {
		return attributeItemValueName;
	}

	public void setAttributeItemValueName(String attributeItemValueName) {
		this.attributeItemValueName = attributeItemValueName;
	}

	public String getAttributeItemValuePrintName() {
		return attributeItemValuePrintName;
	}

	public void setAttributeItemValuePrintName(String attributeItemValuePrintName) {
		this.attributeItemValuePrintName = attributeItemValuePrintName;
	}

	public Integer getAttributeItemValueSort() {
		return attributeItemValueSort;
	}

	public void setAttributeItemValueSort(Integer attributeItemValueSort) {
		this.attributeItemValueSort = attributeItemValueSort;
	}

	public Float getAttributeItemValuePrice() {
		return attributeItemValuePrice;
	}

	public void setAttributeItemValuePrice(Float attributeItemValuePrice) {
		this.attributeItemValuePrice = attributeItemValuePrice;
	}

    public String getAttributeItemValueIsStandard() {
        return attributeItemValueIsStandard;
    }

    public void setAttributeItemValueIsStandard(String attributeItemValueIsStandard) {
        this.attributeItemValueIsStandard = attributeItemValueIsStandard;
    }
}
