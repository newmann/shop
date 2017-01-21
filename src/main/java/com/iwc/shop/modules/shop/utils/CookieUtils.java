/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.IpAddrUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.modules.shop.entity.Cookie;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.CookieService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品工具类
 * @author Tony Wong
 * @version 2015-04-06
 */
public class CookieUtils {

	/**
	 * cookieId name
	 */
	public static final String COOKIE_ID_NAME = "cookieId";

	/**
	 * 默认过期时间为一年： 365*24*60*60
	 */
	public static final int COOKIE_DEFAULT_AGE = 31536000;

	private static CookieService cookieService = SpringContextHolder.getBean(CookieService.class);

	/**
	 * 获取cookieId
	 * 如果不存在，则保存cookie，并且把cookieId保存在客户端里
	 * 注意：不要在service层调用该方法，否则会报数据库事务错误，
	 * 		java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed，
	 * 		因为改方法调用了cookieService.save(cookie);
	 *
	 * @return String
	 */
	public static String getCookieId(HttpServletRequest request, HttpServletResponse response) {
		String cookieId = com.iwc.shop.common.utils.CookieUtils.getCookie(request, CookieUtils.COOKIE_ID_NAME);

		if (StringUtils.isBlank(cookieId)) {
			// save Cookie
			Cookie cookie = new Cookie();
			cookie.setIp(IpAddrUtils.getIpAddr(request));
			cookieService.save(cookie);
			cookieId = cookie.getId();

			// set to client
			com.iwc.shop.common.utils.CookieUtils.setCookie(response, CookieUtils.COOKIE_ID_NAME, cookieId, "/", COOKIE_DEFAULT_AGE);
		}

		return cookieId;
	}
}