package com.iwc.shop.modules.app.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Tony Wong
 */
@Controller
@RequestMapping("/app/category")
public class AppCategoryController extends AppBaseController {

	@Autowired
	private ShopCategoryService categoryService;

	@RequestMapping("")
	public String index(ModelMap m) {
		List<ShopCategory> categoryList = categoryService.findFirstList();
		m.put("categoryList", categoryList);
		return "modules/app/category/index";
	}

    /**
     * 查看商品目录列表
     */
    @RequestMapping("/list")
    public String list(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oCategoryList = Lists.newArrayList();
        //List<ShopCategory> categoryList = categoryService.findFirstList();
        List<ShopCategory> categoryList = ShopCategoryUtils.findFirstList();
        for (ShopCategory category : categoryList) {
            Map<String, Object> oCategory = category.toSimpleObj();
            oCategoryList.add(oCategory);
        }

        result = true;
        message = "";
        data.put("categoryList", oCategoryList);
        return renderString(response, result, message, data);
    }

    /**
     * 查看商品目录列表和对应的产品列表
     */
    @RequestMapping("/list-with-product")
    public String listWithProduct(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oCategoryList = ShopCategoryUtils.findFirstListWithProduct4App();

        result = true;
        message = "";
        data.put("categoryList", oCategoryList);
        return renderString(response, result, message, data);
    }
}
