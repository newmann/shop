package com.iwc.shop.modules.app.web;

import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Tony Wong
 */
@Controller
@RequestMapping("/app/topic")
public class AppTopicController extends AppBaseController {

    @RequestMapping("")
    public String index(ModelMap m) {
        List<ShopProduct> productList = ShopProductUtils.findAppFeaturedTopic();
        m.put("productList", productList);
        return "modules/app/topic/index";
    }
}
