package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Maps;
import com.iwc.shop.common.mapper.JsonMapper;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.entity.User;

import java.util.Map;

/**
 * 用户地址实体
 * @author Tony Wong
 * @version 2015-04-19
 */
public class Address extends DataEntity<Address> {

	private static final long serialVersionUID = 1L;

	private Cookie cookie;
	private User user;
	private Area area;
	private String detail;
	private String fullname;
	private String telephone;
	private String isDefault;

	//isDefault
	public static final String IS_DEFAULT_NO = "0";
	public static final String IS_DEFAULT_YES = "1";

	public Address() {
		super();
	}

	public Address(String id) {
		super(id);
	}

	/**
	 * 插入之前执行方法
	 */
	@Override
	public void preInsert(){
		super.preInsert();
		if (isDefault == null)
			isDefault = IS_DEFAULT_NO;
	}

    /**
     * 只转化当前字段, 方便给json用
     * @return
     */
	public Map<String, Object> toSimpleObj() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("detail", detail);
        map.put("fullname", fullname);
        map.put("telephone", telephone);
        map.put("isDefault", isDefault);
        return map;
    }

	public Cookie getCookie() {
		return cookie;
	}

	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
