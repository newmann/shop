/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品目录Controller
 * @author Tony Wong
 * @version 2015-04-07
 */
@Controller
@RequestMapping(value = "/category")
public class ShopCategoryController extends BaseController {

	private static final String VIEW_PATH = "modules/shop/category/";

	@Autowired
	private ShopCategoryService categoryService;

	@Autowired
	private ShopProductService productService;

    @RequestMapping(value = "/{id}")
	public String view(Model model) {
		List<ShopCategory> firstCategoryList = ShopCategoryUtils.findFirstList();
		List<ShopProduct> featuredHomeDayProductList = productService.findFeaturedHomeDay();

		model.addAttribute("firstCategoryList", firstCategoryList);
		model.addAttribute("featuredHomeDayProductList", featuredHomeDayProductList);
		return VIEW_PATH + "view";
	}
	
}
