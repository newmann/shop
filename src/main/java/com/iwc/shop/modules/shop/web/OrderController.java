/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.iwc.shop.common.utils.IpAddrUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.*;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单Controller
 * @author Tony Wong
 * @version 2015-04-19
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private PreorderService preorderService;

	@Autowired
	private PreorderItemService preorderItemService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShopProductService productService;

	@Autowired
	private CookieService cookieService;

	@Autowired
	private AddressService addressService;

	/**
	 * 查看订单
	 * @param id
	 * @param request
	 * @param m
	 * @return
	 */
	@RequestMapping("/{id}")
	public String view(@PathVariable String id,
					   HttpServletRequest request, HttpServletResponse response, ModelMap m) {
		String cookieId = CookieUtils.getCookieId(request, response);
		Order order = orderService.get(id);
		List<OrderItem> orderItemList = orderItemService.findByOrderId(order);
		int cartItemsCount = cartService.countItemsByCookieId(cookieId, null);

		//用cookieId作为查看订单的凭证
		if (order.getCookie() != null && cookieId.equals(order.getCookie().getId())) {
			m.put("order", order);
		}

		m.put("cartItemsCount", cartItemsCount);
		m.put("orderItemList", orderItemList);
		return "modules/shop/order/view.html";
	}

	/**
	 * 将预备订单转化为订单
	 */
	@RequestMapping(value="/add/{preorderId}", method=RequestMethod.POST)
	public String add(@PathVariable String preorderId, @RequestParam String addressId,
					  HttpServletRequest request, HttpServletResponse response) {
		Preorder preorder = preorderService.get(preorderId);
		List<PreorderItem> preorderItemList = preorderItemService.findByPreorderId(preorderId);
		Address address = addressService.get(addressId);

		// preorder not exist
		if (preorder == null || preorderItemList == null || preorderItemList.isEmpty()) {
			throw new RuntimeException("预备订单(" + preorderId + ")不存在, 或没有订单项");
		}

		// address not exist
		if (address == null) {
			throw new RuntimeException("送餐地址(" + addressId + ")不存在");
		}

		// area not exist
		if (address.getArea() == null) {
			throw new RuntimeException("送餐地区(" + addressId + ")不存在");
		}

		// save order
		Order order = new Order();
		order.setIp(IpAddrUtils.getIpAddr(request));
		order.setCookie(address.getCookie());
		order.setCart(preorder.getCart());
		order.setPreorder(preorder);
		order.setTotalPrice(preorder.getTotalPrice());
		order.setTotalCount(preorder.getTotalCount());
		order.setArea(address.getArea());
		order.setAreaName(address.getArea().getName());
		order.setAreaParentId(address.getArea().getParentId());
		order.setAreaPathIds(address.getArea().getPathIds());
		order.setAreaPathNames(address.getArea().getPathNames());
		order.setAreaSimpleName(address.getArea().getSimpleName());
		order.setAreaZipCode(address.getArea().getZipCode());
        order.setStoreId(address.getArea().getStoreId());
		order.setAddressDetail(address.getDetail());
		order.setAddressFullname(address.getFullname());
		order.setAddressTelephone(address.getTelephone());
		orderService.save(order);

		// save order items
		for(PreorderItem preorderItem : preorderItemList) {
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setPreorderItem(preorderItem);
			item.setProduct(preorderItem.getProduct());
			item.setCartItem(preorderItem.getCartItem());
			item.setName(preorderItem.getName());
			item.setImage(preorderItem.getImage());
			item.setFeaturedImage(preorderItem.getFeaturedImage());
			item.setImageSmall(preorderItem.getImageSmall());
			item.setPrice(preorderItem.getPrice());
			item.setSubtotalPrice(preorderItem.getSubtotalPrice());
			item.setCount(preorderItem.getCount());
			orderItemService.save(item);

			// update CartItem's count and isOrdered
			CartItem cartItem = cartItemService.get(preorderItem.getCartItem().getId());
			if (cartItem != null) {
				Integer orderItemCount = item.getCount();
				Integer cartItemCount = cartItem.getCount();
				if (orderItemCount.equals(cartItemCount)) {
					cartItem.setIsOrdered(CartItem.IS_ORDERED_YES);
				}
				else if (orderItemCount < cartItemCount) {
					cartItem.setCount(cartItemCount - orderItemCount);
				}
				cartItemService.save(cartItem);
			}
		}

		// update preorder status
		preorder.setIsOrdered(Preorder.IS_ORDERED_YES);
		preorderService.save(preorder);

		return "redirect:/order/success/" + order.getId() + ".html";
	}

	@RequestMapping("/success/{id}")
	public String success(@PathVariable String id,
						  HttpServletRequest request, HttpServletResponse response, ModelMap m) {
		String cookieId = CookieUtils.getCookieId(request, response);
		Order order = orderService.get(id);
		List<CartItem> cartItemList = cartItemService.findByCookieId(cookieId, null);
		int cartItemsCount = cartService.countItems(cartItemList);

		//用cookieId作为查看订单的凭证
		if (order.getCookie() != null && cookieId.equals(order.getCookie().getId())) {
			m.put("order", order);
		}

		m.put("cartItemsCount", cartItemsCount);
		m.put("cartItemList", cartItemList);
		return "modules/shop/order/success.html";
	}
}
