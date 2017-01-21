/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.iwc.shop.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 浏览商品记录Controller
 * @author Tony Wong
 * @version 2015-05-17
 */
@Controller
@RequestMapping("/app/history")
public class AppHistoryController extends AppBaseController {

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap m) {

		return "modules/app/history/list";
	}
}
