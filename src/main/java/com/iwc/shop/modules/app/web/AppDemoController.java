/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.IpAddrUtils;
import com.iwc.shop.common.utils.JsonUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.*;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/app/demo")
public class AppDemoController extends AppBaseController {

	/**
	 * 级联选择
	 */
	@RequestMapping("/select-chained")
	public String view(ModelMap m) {
		List<Area> provinceList = AreaUtils.findByParentId(Area.PROVINCE_PARENT_ID);
		m.put("provinceList", provinceList);
		return "modules/app/demo/select_chained";
	}

	/**
	 * 级联选择数据
	 */
	@RequestMapping("/ajax-select-chained")
	public void ajaxAdd(HttpServletResponse response) throws IOException {
		boolean result = false;
		String message = "";
		Map<String, String> data = Maps.newHashMap();
		data.put("", "--");
		data.put("series-1", "1 series");
		data.put("series-2", "2 series");
		data.put("series-3", "3 series");
		data.put("series-4", "4 series");
		data.put("selected", "series-3");

		JsonUtils.setResponse(response);
		response.getWriter().print(JSON.toJSONString(data));
	}
}
