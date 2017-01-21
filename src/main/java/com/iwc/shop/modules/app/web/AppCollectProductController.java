/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.CollectProduct;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.CollectProductService;
import com.iwc.shop.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 收藏商品Controller
 * @author Tony Wong
 * @version 2015-05-17
 */
@Controller
@RequestMapping("/app/collect-product")
public class AppCollectProductController extends AppBaseController {

    @Autowired
    CollectProductService collectProductService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result = true;
        String message = "";
        Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);
        List<Map<String, Object>> oCollectProductList = collectProductService.findByUserId4SimpleObj(userId);
        data.put("collectProductList", oCollectProductList);

        return renderString(response, result, message, data);
	}

    /**
     * 收藏商品
     */
    @RequestMapping("/add/{productId}")
    public String add(@PathVariable String productId, HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);

        if (StringUtils.isBlank(productId)) {
            result = false;
            message = "商品ID不能为空";
            return renderString(response, result, message, data);
        }

        //用户是否已经收藏了产品
        if (!collectProductService.hasCollected(userId, productId)) { //还没收藏则添加
            CollectProduct collectProduct = new CollectProduct();
            collectProduct.setProduct(new ShopProduct(productId));
            collectProduct.setUser(new User(userId));
            if (StringUtils.isBlank(collectProduct.getId())) {
                collectProduct.setCreateBy(new User(userId));
                collectProduct.setUpdateBy(new User(userId));
            } else {
                collectProduct.setUpdateBy(new User(userId));
            }
            collectProductService.save(collectProduct);
        }

        result = true;
        message = "成功收藏商品";
        return renderString(response, result, message, data);
    }

    /**
     * 取消收藏商品
     */
    @RequestMapping("/delete/{productId}")
    public String delete(@PathVariable String productId, HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);

        if (StringUtils.isBlank(productId)) {
            result = false;
            message = "商品ID不能为空";
            return renderString(response, result, message, data);
        }

        //用户是否已经收藏了产品
        CollectProduct collectProduct = new CollectProduct();
        collectProduct.setProduct(new ShopProduct(productId));
        collectProduct.setUser(new User(userId));
        List<CollectProduct> collectProductList = collectProductService.findByUserId(userId);
        for (CollectProduct cp : collectProductList) {
            cp.setDelFlag(CollectProduct.DEL_FLAG_DELETE);
            if (StringUtils.isBlank(cp.getId())) {
                cp.setCreateBy(new User(userId));
                cp.setUpdateBy(new User(userId));
            } else {
                cp.setUpdateBy(new User(userId));
            }
            collectProductService.save(cp);
        }

        result = true;
        message = "成功取消收藏商品";
        return renderString(response, result, message, data);
    }
}
