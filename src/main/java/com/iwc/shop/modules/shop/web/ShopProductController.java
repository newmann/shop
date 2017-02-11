/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.CartService;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品Controller
 * @author Tony Wong
 * @version 2015-04-07
 */
@Controller
@RequestMapping(value = "/product")
public class ShopProductController extends BaseController {

	@Autowired
	private ShopCategoryService categoryService;

	@Autowired
	private ShopProductService productService;

	@Autowired
	private CartService cartService;

	/**
	 * 查看商品
	 */
	@RequestMapping(value = "/{id}")
	public String view(@PathVariable String id, Model model, HttpServletRequest request, HttpServletResponse response) {
		ShopProduct product = ShopProductUtils.getProduct(id);

		model.addAttribute("product", product);
		return "modules/shop/product/view.html";
	}

    /**
     * 查看商品列表
     */
    @RequestMapping(value = "/list/{categoryId}")
    public String list(@PathVariable String categoryId, Model model) {
        List<ShopCategory> firstCategoryList = ShopCategoryUtils.findFirstList();
        List<ShopProduct> productList = ShopProductUtils.findByCategoryId(categoryId);

		model.addAttribute("categoryId", categoryId);
        model.addAttribute("firstCategoryList", firstCategoryList);
        model.addAttribute("productList", productList);
        return "modules/shop/product/list.html";
    }
	
}
