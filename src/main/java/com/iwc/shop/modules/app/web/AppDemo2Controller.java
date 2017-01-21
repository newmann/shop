/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.JsonUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 测试用的Controller
 * @author Tony Wong
 * @version 2015-04-19
 */
@Controller
@RequestMapping("/app")
public class AppDemo2Controller extends AppBaseController {

	/**
	 * 级联选择
	 */
	@RequestMapping("/news")
	public String view(ModelMap m) {
		List<Area> provinceList = AreaUtils.findByParentId(Area.PROVINCE_PARENT_ID);
		m.put("provinceList", provinceList);
		return "modules/app/demo2/news";
	}

	/**
	 * 级联选择
	 */
	@RequestMapping("/life")
	public String life(ModelMap m) {
		List<Area> provinceList = AreaUtils.findByParentId(Area.PROVINCE_PARENT_ID);
		m.put("provinceList", provinceList);
		return "modules/app/demo2/life";
	}
}
