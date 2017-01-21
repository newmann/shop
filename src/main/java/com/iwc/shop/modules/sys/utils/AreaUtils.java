/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.sys.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.modules.shop.entity.Cookie;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.sys.dao.DictDao;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.entity.Dict;
import com.iwc.shop.modules.sys.service.AreaService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 区域工具类
 * @author Tony Wong
 * @version 2015-4-19
 */
public class AreaUtils {
	
	private static AreaService areaService = SpringContextHolder.getBean(AreaService.class);

	public static final String COOKIE_NAME_AREA_ID = "areaId";

	/** cache name and keys **/
	public static final String CACHE_NAME = "Area";
	public static final String CK_AREA_ = "Area_";
	public static final String CK_FIND_BY_PARENT_ID = "FindByParentId_";

	/**
	 * 获取当前的区域areaId
	 * 如果不存在Cookie里，则把areaId保存在客户端里
	 * @return String
	 */
	public static String getAreaId(HttpServletRequest request, HttpServletResponse response) {
		String areaId = com.iwc.shop.common.utils.CookieUtils.getCookie(request, COOKIE_NAME_AREA_ID);
		if (StringUtils.isBlank(areaId)) {
			areaId = Area.DEFAULT_AREA_ID;
			com.iwc.shop.common.utils.CookieUtils.setCookie(response, COOKIE_NAME_AREA_ID, areaId, "/", CookieUtils.COOKIE_DEFAULT_AGE);
		}

		return areaId;
	}

	/**
	 * 获取区域Area
	 *
	 * @return
	 */
	public static Area getArea(String id) {
		Area area = null;

		if (StringUtils.isNotBlank(id)) {
			String ckName = CK_AREA_ + id;
			area = (Area) getCache(ckName);
			if (area == null) {
				area = areaService.gets(id);
				putCache(ckName, area);
			}
		}

		return area;
	}

	/**
	 * 获取当前的区域Area
	 * 如果areaId不存在客户端的Cookie，则把areaId保存在客户端
	 *
	 * @return
	 */
	public static Area getArea(HttpServletRequest request, HttpServletResponse response) {
		String areaId = getAreaId(request, response);
		Area area = null;

		if (StringUtils.isNotBlank(areaId)) {
			String ckName = CK_AREA_ + areaId;
			area = (Area) getCache(ckName);
			if (area == null) {
				area = getArea(Area.DEFAULT_AREA_ID);
				if (area == null) {
					area = areaService.gets(Area.DEFAULT_AREA_ID);
				}
				areaId = area.getId();
				setAreaIdToCookie(areaId, response);
			}
		}

		return area;
	}

	/**
	 * 获取Children
	 *
	 * @return
	 */
	public static List<Area> findByParentId(String areaId) {
		List<Area> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(areaId)) {
			String ckName = CK_FIND_BY_PARENT_ID + areaId;
			list = (List<Area>) getCache(ckName);
			if (list == null) {
				list = areaService.findByParentId(areaId);
				putCache(ckName, list);
			}
		}
		return list;
	}

	/**
	 * 获取级联的地址列表
	 * @return {{entity, children}, {entity, children}...}
	 */
	public static List<Map<String,Object>> findChainedList(Area area) {
		List<Map<String,Object>> list = Lists.newArrayList();
		String[] areaIds = area.getPathIds().split("/");
		for (String areaId : areaIds) {
			Map<String, Object> map = Maps.newHashMap();
			Area entity = getArea(areaId);
			List<Area> children = findByParentId(entity.getId());
			map.put("area", entity);
			map.put("children", children);
			list.add(map);
		}
		return list;
	}
	/**
	 * 获取级联的地址列表 for json
	 * @return {{entity, children}, {entity, children}...}
	 */
	public static List<Map<String,Object>> findChainedList4SimpleObj(Area area) {
		List<Map<String,Object>> oList = Lists.newArrayList();
		String[] areaIds = area.getPathIds().split("/");
		for (String areaId : areaIds) {
			Map<String, Object> oMap = Maps.newHashMap();
            Map<String, Object> oArea;
            List<Map<String, Object>> oChildren = Lists.newArrayList();

			Area entity = getArea(areaId);
			List<Area> children = findByParentId(entity.getId());

            oArea = entity.toSimpleObj();

            for (Area child : children) {
                Map<String, Object> oChild = child.toSimpleObj();
                oChildren.add(oChild);
            }

            oMap.put("area", oArea);
            oMap.put("children", oChildren);
			oList.add(oMap);
		}
		return oList;
	}
	
	public static void setAreaIdToCookie(String areaId, HttpServletResponse response) {
		com.iwc.shop.common.utils.CookieUtils.setCookie(response, COOKIE_NAME_AREA_ID, areaId, "/", CookieUtils.COOKIE_DEFAULT_AGE);
	}

	// ============== Shop Category Cache ==============

	public static Object getCache(String key) {
		return CacheUtils.get(CACHE_NAME, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(CACHE_NAME, key, value);
	}

	public static void removeCache(String key) {
		CacheUtils.remove(CACHE_NAME, key);
	}

	public static void removeCache() {
	}
}
