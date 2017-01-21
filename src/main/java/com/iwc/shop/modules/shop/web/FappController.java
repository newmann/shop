/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.ShopProductService;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * app客户端下载Controller
 * @author Tony Wong
 * @version 2013-5-29
 */
@Controller
@RequestMapping(value = "/fapp")
public class FappController extends BaseController {

	private static final String VIEW_PATH = "modules/shop/fapp/";

	@RequestMapping
	public String index() {
		return VIEW_PATH + "index";
	}
	
}
