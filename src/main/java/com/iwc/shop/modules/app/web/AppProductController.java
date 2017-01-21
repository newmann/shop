package com.iwc.shop.modules.app.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.entity.ShopProductAttribute;
import com.iwc.shop.modules.shop.entity.ShopProductAttributeItemValue;
import com.iwc.shop.modules.shop.service.*;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import org.restlet.data.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Tony Wong
 */

@Controller
@RequestMapping("/app/product")
public class AppProductController extends AppBaseController {
	
	@Autowired
	private ShopProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CollectProductService collectProductService;

    @Autowired
    private HistoryProductService historyProductService;
	
//    @ModelAttribute("cart")
//    public Cart cart(HttpServletRequest request, HttpServletResponse response) {
//    	return cartService.get(request, response);
//    }
//
//    @ModelAttribute("cartItemsCount")
//    public int cartItemsCount(HttpServletRequest request, HttpServletResponse response) {
//		return cartService.countItems(request, response);
//    }

	/**
	 * 查看商品
	 */
	@RequestMapping(value = "/{id}")
	public String view(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        String appCartCookieId = request.getParameter("appCartCookieId");

        boolean result;
        String message;
        int cartNum;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> oProduct;
        List<Map<String, Object>> oAttrList = Lists.newArrayList();

		ShopProduct product = ShopProductUtils.getProduct(id);

        if (product == null) {
            result = false;
            message = "商品(ID:" + id + ")不存在";
            return renderString(response, result, message, data);
        }

        for (ShopProductAttribute attr : product.getAttributeList()) {
            Map<String, Object> oItem = attr.getItem().toSimpleObj();
            List<Map<String, Object>> valueList = Lists.newArrayList();
            for (ShopProductAttributeItemValue attrValue : attr.getItem().getValueList()) {
                Map<String, Object> value = attrValue.toSimpleObj();
                valueList.add(value);
            }
            oItem.put("valueList", valueList);
            oAttrList.add(oItem);
        }

        oProduct = product.toSimpleObj();
        oProduct.put("attrList", oAttrList);

        if (isLoggedIn(request)) {
            String userId = getUserId(request);
            cartNum = cartItemService.countByUserId(userId, Global.YES);

            //添加浏览产品历史
            historyProductService.add(userId, id);
        } else {
            cartNum = cartItemService.countByAppCartCookieId(appCartCookieId, Global.YES);
        }


        result = true;
        message = "";
        data.put("product", oProduct);
        data.put("cartNum", cartNum);
        data.put("hasCollectedProduct", collectProductService.hasCollected(getUserId(request), product.getId()));

        return renderString(response, result, message, data);
	}

	/**
	 * 查看商品列表
	 */
	@RequestMapping(value = "/list/{categoryId}")
	public String list(@PathVariable String categoryId, HttpServletResponse response) {
		boolean result;
		String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oProductList = Lists.newArrayList();

        //取得排列第一的目录
        if ("firstSortCategory".equals(categoryId)) {
            ShopCategory category = ShopCategoryUtils.getFirstSortCategory();
            if (category != null)
                categoryId = category.getId();
        }

        List<ShopProduct> list = ShopProductUtils.findActiveByCategoryId(categoryId);
        for (ShopProduct product : list) {
            Map<String, Object> oProduct = product.toSimpleObj();
            oProductList.add(oProduct);
        }

        result = true;
        message = "";
        data.put("productList", oProductList);
        return renderString(response, result, message, data);
	}

    /**
     * 查看打折的商品列表
     */
    @RequestMapping(value = "/list-featured-price")
    public String listFeaturedPrice(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oProductList = Lists.newArrayList();

        List<ShopProduct> list = productService.findFeaturedPrice();
        for (ShopProduct product : list) {
            Map<String, Object> oProduct = product.toSimpleObj();
            oProductList.add(oProduct);
        }

        result = true;
        message = "";
        data.put("productList", oProductList);
        return renderString(response, result, message, data);
    }

    /**
     * 为你精选的商品列表
     */
    @RequestMapping(value = "/list-featured-topic")
    public String listFeaturedTopic(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oProductList = Lists.newArrayList();

        List<ShopProduct> list = productService.findAppFeaturedTopic(20);
        for (ShopProduct product : list) {
            Map<String, Object> oProduct = product.toSimpleObj();
            oProductList.add(oProduct);
        }

        result = true;
        message = "";
        data.put("productList", oProductList);
        return renderString(response, result, message, data);
    }



    /**
     * 销量最高的商品列表
     */
    @RequestMapping(value = "/list-top-seller")
    public String listTopSeller(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oProductList = Lists.newArrayList();

        List<Map<String, Object>> list = orderItemService.findTopSeller4App();
        for (Map<String, Object> item : list) {
            if (item.get("productId") != null) {
                ShopProduct product = ShopProductUtils.getProduct(item.get("productId").toString());
                if (product != null) {
                    Map<String, Object> oProduct = product.toSimpleObj();
                    oProduct.put("totalCount", Integer.valueOf(item.get("totalCount").toString()) + 1000);
                    oProductList.add(oProduct);
                }
            }
        }

        result = true;
        message = "";
        data.put("productList", oProductList);
        return renderString(response, result, message, data);
    }
}
