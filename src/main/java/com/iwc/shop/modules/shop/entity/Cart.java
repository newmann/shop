package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Sets;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.User;

import java.util.Set;

/**
 * @author Tony Wong
 */
public class Cart extends DataEntity<Cart> {
	
	private static final long serialVersionUID = 1L;
	
//	/**
//	 * 当用户登陆后并且该用户的cart items对应cookieKey不等于当前的cookieKey时，则合并最后的cart item
//	 * 总是用cookieKey来标识cart
//	 */
	//private String cookieKey; // Cookie.key
	private Cookie cookie;
	private User user;
	private String ip;
	private String appCartCookieId;

	private Set<CartItem> items = Sets.newHashSet();

	public Cart() {
		super();
	}

	public Cart(String id) {
		super();
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Set<CartItem> getItems() {
		return items;
	}
	
	public void setItems(Set<CartItem> items) {
		this.items = items;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Cookie getCookie() {
		return cookie;
	}

	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

    public String getAppCartCookieId() {
        return appCartCookieId;
    }

    public void setAppCartCookieId(String appCartCookieId) {
        this.appCartCookieId = appCartCookieId;
    }
}
