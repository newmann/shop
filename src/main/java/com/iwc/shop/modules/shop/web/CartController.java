/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.Cart;
import com.iwc.shop.modules.shop.entity.CartItem;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.CartItemService;
import com.iwc.shop.modules.shop.service.CartService;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车Controller
 * @author Tony Wong
 * @version 2015-04-16
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService itemService;

	/**
	 * 购物车页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		String cookieId = CookieUtils.getCookieId(request, response);
		List<CartItem> cartItemList = itemService.findByCookieId(cookieId, null);
		model.addAttribute("cartItemList", cartItemList);
		return "modules/shop/cart/index";
	}

	/**
	 * 商品已加入购物车
	 */
	@RequestMapping("success/{productId}")
	public String success(@PathVariable String productId,
						  HttpServletRequest request, HttpServletResponse response, Model model) {
		String cookieId = CookieUtils.getCookieId(request, response);
		List<CartItem> cartItemList = itemService.findByCookieId(cookieId, null);
		model.addAttribute("cartItemList", cartItemList);
		return "modules/shop/cart/success";
	}
	
}
