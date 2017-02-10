/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.google.common.collect.Lists;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.servlet.ValidateCodeServlet;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 网站首页Controller
 * @author Tony Wong
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	private static final String VIEW_PATH = "modules/shop/index/";

	@Autowired
	private ShopProductService productService;

	/**
	 * 网站首页
	 */
	@RequestMapping
	public String index(Model model) {
		List<ShopCategory> firstCategoryList = ShopCategoryUtils.findFirstList();
		List<ShopProduct> featuredHomeDayProductList = productService.findFeaturedHomeDay();
        List<ShopProduct> featuredHomeSpecialProductList = productService.findFeaturedHomeSpecial();

        model.addAttribute("firstCategoryList", firstCategoryList);
		model.addAttribute("featuredHomeDayProductList", featuredHomeDayProductList);
        model.addAttribute("featuredHomeSpecialProductList", featuredHomeSpecialProductList);
		return VIEW_PATH + "index.html";
	}
	
}
