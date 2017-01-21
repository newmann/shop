package com.iwc.shop.modules.app.web;

import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * App home page controller
 *
 * @author Tony Wong
 */
@Controller
@RequestMapping("/app/home")
public class AppHomeController extends AppBaseController {

    public static final Integer CATEGORY_COUNT = 4;
    public static final Integer PRODUCT_COUNT = 12;

    @Autowired
    private ShopCategoryService categoryService;

    @Autowired
    private ShopProductService productService;

    @RequestMapping("")
    public String index(ModelMap m) {
        List<ShopCategory> featuredCategoryList = categoryService.findAppFeaturedHome(CATEGORY_COUNT);
        List<ShopProduct> featuredProductList = productService.findAppFeaturedHome(PRODUCT_COUNT);

        m.put("featuredCategoryList", featuredCategoryList);
        m.put("featuredProductList", featuredProductList);
        return "modules/app/home/index";
    }
}
