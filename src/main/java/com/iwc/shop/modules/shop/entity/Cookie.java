package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Sets;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.User;

import java.util.Set;

/**
 * @author Tony Wong
 */
public class Cookie extends DataEntity<Cookie> {

	private static final long serialVersionUID = 1L;

//	/**
//	 * 当用户登陆后并且该用户的cart items对应cookieKey不等于当前的cookieKey时，则合并最后的cart item
//	 * 总是用cookieKey来标识cart
//	 */
	//private String cookieKey; // Cookie.key
	// 用 Cookie.id 替换

	private String ip;

	public Cookie() {
		super();
	}

	public Cookie(String id) {
		super(id);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
