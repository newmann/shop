/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.sys.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

/**
 * 区域Entity
 * @author Tony Wong
 * @version 2013-05-15
 */
public class Area extends TreeEntity<Area> {

	private static final long serialVersionUID = 1L;
//	private Area parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
//	private String name; 	// 区域名称
//	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）

	private String simpleName; //简称
	private Integer level;
	private String pathIds; //id路径，包括本id
	private String pathNames; //name路径，包括本name
	private String zipCode; //邮政编码
    private String shippingGroup; //送货组

    private String storeId; //店铺id，一个地址属于一个店铺，根据店铺来打印订单

    //shoppingGroup
    public static final String SHIPPING_GROUP_STORE = "1"; //店内消费

	public static final String PROVINCE_PARENT_ID = "1";

    public static final String DEFAULT_AREA_ID = "29722";

    public static final String PREFIX_AREA_ID = "29722";

	public Area(){
		super();
		this.sort = 30;
	}

	public Area(String id){
		super(id);
	}

	/**
	 * 只转化当前字段, 方便给json用
	 * @return
	 */
	public Map<String, Object> toSimpleObj() {
		Map<String, Object> map = Maps.newHashMap();
        String pathNames4Print = "";
        if (pathNames != null) {
            pathNames4Print = pathNames.replace("中国/广东省/", "");
            pathNames4Print = pathNames4Print.replace("/", "");
        }
		map.put("id", id);
		map.put("name", name);
		map.put("pathNames", pathNames);
        map.put("pathNames4Print", pathNames4Print);
        map.put("shippingGroup", shippingGroup);
        map.put("store_id", storeId);
		return map;
	}
	
//	@JsonBackReference
//	@NotNull
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}
//
//	@Length(min=1, max=2000)
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}
//	
//	@Length(min=1, max=100)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}

	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
//
//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPathIds() {
		return pathIds;
	}

	public void setPathIds(String pathIds) {
		this.pathIds = pathIds;
	}

	public String getPathNames() {
		return pathNames;
	}

	public void setPathNames(String pathNames) {
		this.pathNames = pathNames;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

    public String getShippingGroup() {
        return shippingGroup;
    }

    public void setShippingGroup(String shippingGroup) {
        this.shippingGroup = shippingGroup;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}