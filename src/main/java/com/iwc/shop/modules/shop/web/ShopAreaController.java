/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.JsonUtils;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.service.AreaService;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import com.iwc.shop.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域Controller
 * @author Tony Wong
 * @version 2015-4-26
 */
@Controller
@RequestMapping(value = "/area")
public class ShopAreaController extends BaseController {

	@Autowired
	private AreaService areaService;
	
	/**
	 * List by parentId, return json format string
	 *
	 * @throws IOException
	 */
	@RequestMapping("/ajax-list/{parentId}")
	public void ajaxList(@PathVariable String parentId, HttpServletResponse response) throws IOException {
		List<Area> areaList = areaService.findByParentId(parentId);

		// @todo
		List<Map<String, Object>> list = Lists.newArrayList();
		for (Area area : areaList) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", area.getId());
			map.put("parentId", area.getParentId());
			map.put("name", area.getName());
			list.add(map);
		}

		boolean res = true;
		String msg = "区域列表";

		JsonUtils.setResponse(response);
		response.getWriter().print(JsonUtils.toString(res, msg, list));
	}

	/**
	 * List by parentId, return html string
	 *
	 * @throws IOException
	 */
	@RequestMapping("/ajax-list-html/{parentId}")
	public void ajaxListHtml(@PathVariable String parentId, HttpServletResponse response) throws IOException {
		Area area = areaService.gets(parentId);
		String[] path = area.getPathIds().split("/");
		List<Area> areaList = areaService.findByParentId(parentId);
		Map<String, Object> data = Maps.newHashMap();
		StringBuilder sb = new StringBuilder();
		boolean result = false;
		String message = "";

		if (areaList != null && areaList.size() > 0) {
			sb.append("<div class='tab-content' data-index='" + path.length + "'><ul>");
			for (Area tmpArea : areaList) {
				sb.append("<li><a rel='nofollow' data-area-id='" + tmpArea.getId() + "' href='#none'>" + tmpArea.getName() + "</a></li>");
			}
			sb.append("</ul></div>");
		}

		result = true;
		message = "区域列表";

        /* 不能使用entity作为JsonUtils的数据对象，否则会出现StackOverflow, 可用Map代替 */
        //data.put("area", area);
        Map<String, Object> areaMap = Maps.newHashMap();
        areaMap.put("id", area.getId());
        areaMap.put("name", area.getName());
        areaMap.put("pathIds", area.getPathIds());
        areaMap.put("pathNames", area.getPathNames());

        data.put("area", areaMap);
		data.put("list", sb.toString());

		JsonUtils.setResponse(response);
		response.getWriter().print(JsonUtils.toString(result, message, data));
	}

	/**
	 * 级联选择器获取城市列表
	 */
	@RequestMapping("/ajax-find-city")
	public void ajaxFindCity(@RequestParam String province, HttpServletResponse response) throws IOException {
		List<Area> list = AreaUtils.findByParentId(province);
		List<Map<String, String>> mapList = Lists.newArrayList();

//		Map<String, String> firstMap = Maps.newHashMap();
//		firstMap.put("", "请选择");
//		mapList.add(firstMap);

		for (Area area : list) {
			Map<String, String> map = Maps.newHashMap();
			map.put(area.getId(), area.getName());
			mapList.add(map);
		}

		JsonUtils.setResponse(response);
		response.getWriter().print(JSON.toJSONString(mapList));
	}

	/**
	 * 级联选择器获取区列表
	 */
	@RequestMapping("/ajax-find-district")
	public void ajaxFindDistrict(@RequestParam String city, HttpServletResponse response) throws IOException {
		List<Area> list = AreaUtils.findByParentId(city);
		List<Map<String, String>> mapList = Lists.newArrayList();

//		Map<String, String> firstMap = Maps.newHashMap();
//		firstMap.put("", "请选择");
//		mapList.add(firstMap);

		for (Area area : list) {
			Map<String, String> map = Maps.newHashMap();
			map.put(area.getId(), area.getName());
			mapList.add(map);
		}

		JsonUtils.setResponse(response);
		response.getWriter().print(JSON.toJSONString(mapList));
	}

	/**
	 * 级联选择器获取村庄列表
	 */
	@RequestMapping("/ajax-find-village")
	public void ajaxFindVillage(@RequestParam String district, HttpServletResponse response) throws IOException {
		List<Area> list = AreaUtils.findByParentId(district);
		List<Map<String, String>> mapList = Lists.newArrayList();

//		Map<String, String> firstMap = Maps.newHashMap();
//		firstMap.put("", "请选择");
//		mapList.add(firstMap);

		for (Area area : list) {
			Map<String, String> map = Maps.newHashMap();
			map.put(area.getId(), area.getName());
			mapList.add(map);
		}

		JsonUtils.setResponse(response);
		response.getWriter().print(JSON.toJSONString(mapList));
	}

	/**
	 * 级联选择器获取街道列表
	 */
	@RequestMapping("/ajax-find-street")
	public void ajaxFindStreet(@RequestParam String village, HttpServletResponse response) throws IOException {
		List<Area> list = AreaUtils.findByParentId(village);
		List<Map<String, String>> mapList = Lists.newArrayList();

//		Map<String, String> firstMap = Maps.newHashMap();
//		firstMap.put("", "请选择");
//		mapList.add(firstMap);

		for (Area area : list) {
			Map<String, String> map = Maps.newHashMap();
			map.put(area.getId(), area.getName());
			mapList.add(map);
		}

		JsonUtils.setResponse(response);
		response.getWriter().print(JSON.toJSONString(mapList));
	}

	/***************** demo function ******************/
	/**
	 * Demo：替换字符|为/
	 *
	 * @param response
	 */
	@RequestMapping("/ajax-demo")
	public void ajaxDemo(HttpServletResponse response) {
		/*
		// Area data = areaService.gets(2);
		// String pathId = data.getPathId();
		// System.out.println(pathId);
		// pathId = pathId.replace("|", "/");
		// System.out.println(pathId);
		// data.setPathId(pathId);
		// areaService.save(data);

		List<Area> data = areaService.findAll();
		// List<Area> data = areaService.findByParentId(1);
		for (Area entity : data) {
			String pathIds = entity.getPathIds();
			if (pathIds.contains("|")) {
				pathIds = pathIds.replace("|", "/");
				entity.setPathIds(pathIds);
				areaService.save(entity);
			}
		}

		boolean result = true;
		String message = "";

		JsonUtils.setResponse(response);
		try {
			response.getWriter().print(JsonUtils.toString(result, message, data));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
}
