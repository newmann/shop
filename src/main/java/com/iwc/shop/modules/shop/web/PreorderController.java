/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.iwc.shop.common.utils.IpAddrUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.*;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.service.AreaService;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 预购订单Controller
 * @author Tony Wong
 * @version 2015-04-19
 */
@Controller
@RequestMapping("/preorder")
public class PreorderController extends BaseController {

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private PreorderItemService preorderItemService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private AddressService addressService;

	/**
	 * 预购订单页面
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/{id}")
	public String view(@PathVariable String id,
					   HttpServletRequest request, HttpServletResponse response, Model model) {
		String cookieId = CookieUtils.getCookieId(request, response);
		Area area = null;
		Map<String, Object> areaSelector = null;

		// get address and areaSelector
		Address address = addressService.getDefaultByCookieId(cookieId);
		if (address != null && address.getArea() != null) {
			area = AreaUtils.getArea(address.getArea().getId());
			address.setArea(area);
		} else {
			area = AreaUtils.getArea(request, response);
		}
		areaSelector = areaService.getAreaSelector(area);

		Preorder preorder = preorderService.get(id);
		List<PreorderItem> preorderItemList = preorderItemService.findByPreorderId(preorder.getId());

		model.addAttribute("preorder", preorder);
		model.addAttribute("preorderItemList", preorderItemList);
		model.addAttribute("area", area);
		model.addAttribute("areaSelector", areaSelector);
		model.addAttribute("address", address);
		return "modules/shop/preorder/view.html";
	}

	/**
	 * 通过购物车添加产品到预备订单
	 * 	 表单 cartItemProps 的值的格式：{cartItemId}_{productId}_{count}
	 */
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public String add(HttpServletRequest request, HttpServletResponse response) {
		String cookieId = CookieUtils.getCookieId(request, response);
		String[] cartItemProps = request.getParameterValues("cartItemProps");
		Float totalPrice = (float) 0.0;
		Integer totalCount = 0;

		// save preorder
		Preorder preorder = new Preorder();
		Cookie cookie = new Cookie(cookieId);
		preorder.setIp(IpAddrUtils.getIpAddr(request));
		preorder.setCookie(cookie);
		preorder.setTotalCount(totalCount);
		preorder.setTotalPrice(totalPrice);
		preorderService.save(preorder);

		// save preorderItems
		for(String prop : cartItemProps) {
			String[] props = prop.split("_");
			String cartItemId = props[0];
			String productId = props[1];
			Integer count = Integer.valueOf(props[2]);
			ShopProduct product = ShopProductUtils.getProduct(productId);

			// product not exist, remove cartItem
			if (product == null) {
				CartItem cartItem = cartItemService.get(cartItemId);
				if (cartItem != null) {
					cartItem.setDelFlag(CartItem.DEL_FLAG_DELETE);
					cartItemService.save(cartItem);
				}
				continue;
			}

			Float subtotalPrice = product.getPrice() * count;
			totalPrice += subtotalPrice;
			totalCount += count;

			PreorderItem item = new PreorderItem();
			CartItem cartItem = new CartItem(cartItemId);
			item.setCartItem(cartItem);
			item.setProduct(product);
			item.setImage(product.getImage());
			item.setImageSmall(product.getImageSmall());
			item.setName(product.getName());
			item.setPrice(product.getPrice());
			item.setSubtotalPrice(subtotalPrice);
			item.setCount(count);
			item.setPreorder(preorder);
			preorderItemService.save(item);
		}

		// save total
		preorder.setTotalPrice(totalPrice);
		preorder.setTotalCount(totalCount);
		preorderService.save(preorder);

		return "redirect:/preorder/" + preorder.getId() + ".html";
	}
}
