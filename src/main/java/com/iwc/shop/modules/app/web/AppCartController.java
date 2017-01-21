/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.IdGen;
import com.iwc.shop.common.utils.JsonUtils;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.entity.CartItem;
import com.iwc.shop.modules.shop.service.CartItemService;
import com.iwc.shop.modules.shop.service.CartService;
import com.iwc.shop.modules.shop.service.CouponUserService;
import com.iwc.shop.modules.shop.service.exception.CartServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 购物车Controller
 * @author Tony Wong
 * @version 2015-05-16
 */
@Controller
@RequestMapping("/app/cart")
public class AppCartController extends AppBaseController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService itemService;

    @Autowired
    CouponUserService couponUserService;

	/**
	 * 购物车页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request, HttpServletResponse response) {
        String appCartCookieId = request.getParameter("appCartCookieId");
        String userId = getUserId(request);

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        int oCountUsefulCoupon = 0;
        boolean isLoggedIn = isLoggedIn(request);

        //客户端没有appCartCookieId时（如首次访问），则生成它
        //这样查询就不会导致appCartCookieId被忽略掉
        if (StringUtils.isBlank(appCartCookieId)) {
            //重新为客户端生成appCartCookieId
            appCartCookieId = IdGen.uuid();
        }

        if (isLoggedIn(request)) {
            data = itemService.findByUserIdWithCount4Json(userId, null);
            oCountUsefulCoupon = couponUserService.countUsefulCoupon(userId);
        } else {
            data = itemService.findByAppCartCookieIdWithCount4Json(appCartCookieId, null);
        }

        result = true;
        message = "";
        data.put("isLoggedIn", isLoggedIn);
        data.put("countUsefulCoupon", oCountUsefulCoupon);
        return renderString(response, result, message, data);
	}

	/**
     * 添加产品到购物车，如果appCartCookieId为空则生成唯一的它
	 * attributes 的格式 itemId_itemValueId, 如["3_15","2_10",...]
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, HttpServletResponse response) {
		String productId = request.getParameter("productId");
		int count = StringUtils.isNotBlank(request.getParameter("count")) ? Integer.valueOf(request.getParameter("count")) : 1;
        String appCartCookieId = StringUtils.isNotBlank(request.getParameter("appCartCookieId")) ? request.getParameter("appCartCookieId") : IdGen.uuid();
		String attributesStr = request.getParameter("attributes");
		List<String> attributes = StringUtils.arrayStringToList(attributesStr);
        int cartNum;

		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);

        if(STOP_ORDER) {
            result = false;
            message = STOP_ORDER_LABEL;
            return renderString(response, result, message, data);
        }

		if (count <= 0)
			count = 1;

        try {
            if (isLoggedIn(request)) {
                cartService.addItemByUserId(productId, count, attributes, userId);
                cartNum = itemService.countByUserId(getUserId(request), Global.YES);
            } else {
                cartService.addItemByAppCartCookieId(productId, count, attributes, appCartCookieId);
                cartNum = itemService.countByAppCartCookieId(appCartCookieId, Global.YES);
            }

            result = true;
            message = "商品已加入购物车";
            data.put("cartNum", cartNum);
            if (StringUtils.isBlank(request.getParameter("appCartCookieId"))) {
                data.put("appCartCookieId", appCartCookieId);
            }
        } catch (CartServiceException e) {
            result = false;
            message = e.getMessage();
        }

        return renderString(response, result, message, data);
	}

	@RequestMapping("/setIsSelected/{itemId}")
	public String setIsSelected(@PathVariable String itemId,
								HttpServletRequest request, HttpServletResponse response) throws IOException{
		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();

        String terminalType = request.getParameter(kTerminalTypeName);
		String isSelected = request.getParameter("is-selected");
        String appCartCookieId = request.getParameter("appCartCookieId");
		CartItem item = itemService.get(itemId);

		if (item == null) {
			result = false;
			message = "产品不存在";
            response.getWriter().print(JsonUtils.toString(result, message, data));
		}

		item.setIsSelected(isSelected);
		itemService.save(item);

        Map<String, Object> map;
        if (isLoggedIn(request)) {
            map = cartService.countItemsAndTotoalPriceByUserId(getUserId(request), Global.YES);
        } else {
            map = cartService.countItemsAndTotoalPriceByAppCartCookieId(appCartCookieId, Global.YES);
        }


        result = true;
        message = "设置成功";
        data.put("cartNum", map.get("cartNum"));
        data.put("totalPrice", map.get("totalPrice"));

        //ios手机端需要的字符串
        if (kTerminalTypeValue_ios.equalsIgnoreCase(terminalType)
                || kTerminalTypeValue_android.equalsIgnoreCase(terminalType)) {
            return renderString(response, result, message, data);
        }
        //apicloud需要的字符串
        else {
            response.getWriter().print(JsonUtils.toString(result, message, data));
            return null;
        }
	}

	@RequestMapping("/setCartItemNum/{itemId}")
	public String setCartItemNum(@PathVariable String itemId,
								HttpServletRequest request, HttpServletResponse response) throws IOException{
		//logger.debug("============");

        boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();

        String terminalType = request.getParameter(kTerminalTypeName);
		String countStr = request.getParameter("count");
        String appCartCookieId = request.getParameter("appCartCookieId");
		Integer count = Integer.valueOf(countStr);
		CartItem item = itemService.get(itemId);

		if (item == null) {
			result = false;
			message = "产品不存在";
            response.getWriter().print(JsonUtils.toString(result, message, data));
		}

		item.setCount(count);
		itemService.save(item);

        Map<String, Object> map;
        if (isLoggedIn(request)) {
            map = cartService.countItemsAndTotoalPriceByUserId(getUserId(request), Global.YES);
        } else {
            map = cartService.countItemsAndTotoalPriceByAppCartCookieId(appCartCookieId, Global.YES);
        }

		result = true;
		message = "设置成功";
        data.put("cartNum", map.get("cartNum"));
        data.put("totalPrice", map.get("totalPrice"));

        //ios手机端需要的字符串
        if (kTerminalTypeValue_ios.equalsIgnoreCase(terminalType)
                || kTerminalTypeValue_android.equalsIgnoreCase(terminalType)) {
            return renderString(response, result, message, data);
        }
        //apicloud需要的字符串
        else {
		    response.getWriter().print(JsonUtils.toString(result, message, data));
            return null;
        }
	}

    @RequestMapping("/delete-item/{itemId}")
    public String deleteItem(@PathVariable String itemId,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException{
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        String terminalType = request.getParameter(kTerminalTypeName);
        String appCartCookieId = request.getParameter("appCartCookieId");

        CartItem item = itemService.get(itemId);

        if (item == null) {
            result = false;
            message = "产品不存在";
            response.getWriter().print(JsonUtils.toString(result, message, data));
        }

        item.setDelFlag(CartItem.DEL_FLAG_DELETE);
        itemService.save(item);

        Map<String, Object> map;
        if (isLoggedIn(request)) {
            map = cartService.countItemsAndTotoalPriceByUserId(getUserId(request), Global.YES);
        } else {
            map = cartService.countItemsAndTotoalPriceByAppCartCookieId(appCartCookieId, Global.YES);
        }

        result = true;
        message = "成功删除";
        data.put("cartNum", map.get("cartNum"));
        data.put("totalPrice", map.get("totalPrice"));

        //ios手机端需要的字符串
        if (kTerminalTypeValue_ios.equalsIgnoreCase(terminalType)
                || kTerminalTypeValue_android.equalsIgnoreCase(terminalType)) {
            return renderString(response, result, message, data);
        }
        //apicloud需要的字符串
        else {
            response.getWriter().print(JsonUtils.toString(result, message, data));
            return null;
        }
    }
}