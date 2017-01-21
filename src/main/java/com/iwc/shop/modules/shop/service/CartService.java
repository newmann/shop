/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.CartDao;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.exception.CartServiceException;
import com.iwc.shop.modules.shop.utils.ShopProductAttributeUtils;
import com.iwc.shop.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 购物车Service
 * @author Tony Wong
 * @version 2015-04-16
 */
@Service
@Transactional(readOnly = true)
public class CartService extends CrudService<CartDao, Cart> {

	@Autowired
	CartItemService itemService;

	@Autowired
	CartItemAttributeService cartItemAttributeService;

	@Autowired
	ShopProductService productService;

//	/**
//	 * If user logged in get by user, otherwise get by cookie
//	 * 注意：不要在Service层调用 com.iwc.shop.modules.shop.utils.CookieUtils.CookieUtils.getCookieId(request, response)，
//	 * 否则有可能产生Cause: java.sql.SQLException: Connection is read-only
//	 * @param request
//	 * @return
//	 */
//	public Cart get(HttpServletRequest request, HttpServletResponse response) {
//		Cart cart = null;
//
//		//get by user
//
//		//get by cookie
//		String cookieId = CookieUtils.getCookieId(request, response);
//		cart = getByCookieId(cookieId);
//
//		return cart;
//	}

    public Cart getByCookieId(String cookieId) {
        Cart cart = new Cart();
        Cookie cookie = new Cookie(cookieId);
        cart.setCookie(cookie);
        return dao.getByCookieId(cart);
    }

    public Cart getByUserId(String userId) {
        Cart cart = new Cart();
        User user = new User(userId);
        cart.setUser(user);
        return dao.getByUserId(cart);
    }

    public Cart getByAppCartCookieId(String appCartCookieId) {
        Cart cart = new Cart();
        cart.setAppCartCookieId(appCartCookieId);
        return dao.getByAppCartCookieId(cart);
    }

	/**
	 * 获得购物车的个数
	 * @return
	 */
	public int countItemsByCookieId(String id, String isSelected) {
		List<CartItem> itemList = itemService.findByCookieId(id, isSelected);
        return countItems(itemList);
	}

	/**
	 * 获得购物车的个数
	 * @return
	 */
	public int countItemsByUserId(String id, String isSelected) {
		List<CartItem> itemList = itemService.findByUserId(id, isSelected);
		return countItems(itemList);
	}

	/**
	 * 统计获得购物车的个数, 不用查找数据库
	 * @return
	 */
	public int countItems(List<CartItem> itemList) {
		int count = 0;
		for (CartItem item : itemList) {
			count += item.getCount();
		}
		return count;
	}

    /**
     * 统计获得购物车的个数和总额
     * @return
     */
    public Map<String, Object> countItemsAndTotoalPriceByUserId(String userId, String isSelected) {
        List<CartItem> itemList = itemService.findByUserId(userId, isSelected);
        return countItemsAndTotoalPrice(itemList, isSelected);
    }

    /**
     * 统计获得购物车的个数和总额
     * @return
     */
    public Map<String, Object> countItemsAndTotoalPriceByCookieId(String id, String isSelected) {
        List<CartItem> itemList = itemService.findByCookieId(id, isSelected);
        return countItemsAndTotoalPrice(itemList, isSelected);
    }

    /**
     * 统计获得购物车的个数和总额
     * @return
     */
    public Map<String, Object> countItemsAndTotoalPriceByAppCartCookieId(String appCartCookieId, String isSelected) {
        List<CartItem> itemList = itemService.findByAppCartCookieId(appCartCookieId, isSelected);
        return countItemsAndTotoalPrice(itemList, isSelected);
    }

	/**
	 * 统计获得购物车的个数和总额, 不用查找数据库,
     *  如果 isSelected = 1，则获得购物车被选项的总额，
     *  如果 isSelected = 0，则获得购物车非被选项的总额，
     *  如果 isSelected = null，则获得购物车总额，
	 * @return
	 */
	public Map<String, Object> countItemsAndTotoalPrice(List<CartItem> itemList, String isSelected) {
        Map<String, Object> map = Maps.newHashMap();
		int cartNum = 0;
        float totalPrice = 0;
		for (CartItem item : itemList) {
			float price = item.getProduct().getPrice();
			int count = item.getCount();
			if (item.getProduct().getFeaturedPrice() != null)
                price = item.getProduct().getFeaturedPrice();
            if (isSelected == null || item.getIsSelected().equals(isSelected)) {
                totalPrice += (price * count);
                cartNum += count;
                for (CartItemAttribute attr : item.getAttributeList()) {
                    ShopProductAttributeItemValue itemValue = attr.getAttributeItemValue();
                    totalPrice += (itemValue.getPrice() * count);
                }
            }
		}
		map.put("cartNum", cartNum);
        map.put("totalPrice", totalPrice);
        return map;
	}

    /**
     * 统计获得购物车的总额, 不用查找数据库,
     *  如果 isSelected = 1，则获得购物车被选项的总额，
     *  如果 isSelected = 0，则获得购物车非被选项的总额，
     *  如果 isSelected = null，则获得购物车总额，
     * @return
     */
    public Float countTotoalPrice(List<CartItem> itemList, String isSelected) {
        Map<String, Object> map = countItemsAndTotoalPrice(itemList, isSelected);
        Float totoalPrice = Float.valueOf(map.get("totalPrice").toString());
        return totoalPrice;
    }

	/**
	 * 添加产品到购物车
	 * @param productId
	 * @param count
     * @param userId 当前用户
	 * @param attributes 的格式 itemId_itemValueId, 如1_1
	 * @throws CartServiceException
	 */
	@Transactional(readOnly = false)
	public void addItemByUserId(String productId, Integer count, List<String> attributes, String userId) throws CartServiceException {
		//if product exist?
		ShopProduct product = productService.get(productId);
		if (product == null) {
			String message = "ShopProduct(id:" + productId + ") doesn't exist";
			throw new CartServiceException(message);
		}

        Cart cart;
        List<Map<String, String>> attributeMaps = ShopProductAttributeUtils.idstringToMap(attributes);

        if (StringUtils.isNotBlank(userId)) {
            cart = getByUserId(userId);
        } else {
            throw new CartServiceException("Error: 购物车不存在");
        }

		if (cart == null) { //购物车不存在
			cart = new Cart();
            User user = new User(userId);
            cart.setUser(user);
			save(cart);

			//add item
			CartItem item = new CartItem();
			item.setProduct(product);
			item.setCount(count);
			item.setCart(cart);
            item.setUserId(userId);
			item.setIsSelected(Global.YES);
            item.setIsOrdered(Global.NO);
			itemService.save(item);

			//add item attributes
			for (Map<String, String> attr : attributeMaps) {
				CartItemAttribute itemAttr = new CartItemAttribute();
				ShopProductAttributeItem attrItem = new ShopProductAttributeItem(attr.get("itemId"));
				ShopProductAttributeItemValue attrValue = new ShopProductAttributeItemValue(attr.get("valueId"));
				itemAttr.setItem(item);
				itemAttr.setAttributeItem(attrItem);
				itemAttr.setAttributeItemValue(attrValue);
				itemAttr.setAttributeIdstring(attr.get("idstring"));
				cartItemAttributeService.save(itemAttr);
			}
		}
		else { //购物车存在
			CartItem item = itemService.getByCartIdProductIdAttributes(cart, product, attributes);
			if (item == null) { //对应属性的产品不在购物车里
				//add item
				CartItem newItem = new CartItem();
				newItem.setProduct(product);
				newItem.setCount(count);
				newItem.setCart(cart);
                newItem.setUserId(userId);
                newItem.setIsSelected(Global.YES);
                newItem.setIsOrdered(Global.NO);
				itemService.save(newItem);

				//add item attributes
				for (Map<String, String> attr : attributeMaps) {
					CartItemAttribute itemAttr = new CartItemAttribute();
					ShopProductAttributeItem attrItem = new ShopProductAttributeItem(attr.get("itemId"));
					ShopProductAttributeItemValue attrValue = new ShopProductAttributeItemValue(attr.get("valueId"));
					itemAttr.setItem(newItem);
					itemAttr.setAttributeItem(attrItem);
					itemAttr.setAttributeItemValue(attrValue);
					itemAttr.setAttributeIdstring(attr.get("idstring"));
					cartItemAttributeService.save(itemAttr);
				}
			}
			else { //对应属性的产品在购物车里
				item.setCount(item.getCount() + count);
                item.setIsSelected(Global.YES);
				itemService.save(item);
			}
		}
	}

	/**
	 * 添加产品到购物车
	 * @param productId
	 * @param count
	 * @param appCartCookieId
	 * @param attributes 的格式 itemId_itemValueId, 如1_1
	 * @throws CartServiceException
	 */
	@Transactional(readOnly = false)
	public void addItemByAppCartCookieId(String productId, Integer count, List<String> attributes, String appCartCookieId) throws CartServiceException {
		//if product exist?
		ShopProduct product = productService.get(productId);
		if (product == null) {
			String message = "ShopProduct(id:" + productId + ") doesn't exist";
			throw new CartServiceException(message);
		}

		Cart cart;
		List<Map<String, String>> attributeMaps = ShopProductAttributeUtils.idstringToMap(attributes);

		if (StringUtils.isNotBlank(appCartCookieId)) {
			cart = getByAppCartCookieId(appCartCookieId);
		} else {
			throw new CartServiceException("Error: 购物车不存在");
		}

		if (cart == null) { //购物车不存在
			cart = new Cart();
			cart.setAppCartCookieId(appCartCookieId);
			save(cart);

			//add item
			CartItem item = new CartItem();
			item.setProduct(product);
			item.setCount(count);
			item.setCart(cart);
			item.setAppCartCookieId(appCartCookieId);
			item.setIsSelected(Global.YES);
            item.setIsSelected(Global.YES);
			itemService.save(item);

			//add item attributes
			for (Map<String, String> attr : attributeMaps) {
				CartItemAttribute itemAttr = new CartItemAttribute();
				ShopProductAttributeItem attrItem = new ShopProductAttributeItem(attr.get("itemId"));
				ShopProductAttributeItemValue attrValue = new ShopProductAttributeItemValue(attr.get("valueId"));
				itemAttr.setItem(item);
				itemAttr.setAttributeItem(attrItem);
				itemAttr.setAttributeItemValue(attrValue);
				itemAttr.setAttributeIdstring(attr.get("idstring"));
				cartItemAttributeService.save(itemAttr);
			}
		}
		else { //购物车存在
			CartItem item = itemService.getByCartIdProductIdAttributes(cart, product, attributes);
			if (item == null) { //对应属性的产品不在购物车里
				//add item
				CartItem newItem = new CartItem();
				newItem.setProduct(product);
				newItem.setCount(count);
				newItem.setCart(cart);
				newItem.setAppCartCookieId(appCartCookieId);
				newItem.setIsSelected(Global.YES);
                newItem.setIsSelected(Global.YES);
				itemService.save(newItem);

				//add item attributes
				for (Map<String, String> attr : attributeMaps) {
					CartItemAttribute itemAttr = new CartItemAttribute();
					ShopProductAttributeItem attrItem = new ShopProductAttributeItem(attr.get("itemId"));
					ShopProductAttributeItemValue attrValue = new ShopProductAttributeItemValue(attr.get("valueId"));
					itemAttr.setItem(newItem);
					itemAttr.setAttributeItem(attrItem);
					itemAttr.setAttributeItemValue(attrValue);
					itemAttr.setAttributeIdstring(attr.get("idstring"));
					cartItemAttributeService.save(itemAttr);
				}
			}
			else { //对应属性的产品在购物车里
				item.setCount(item.getCount() + count);
				item.setIsSelected(Global.YES);
				itemService.save(item);
			}
		}
	}

    @Transactional(readOnly = false)
    public void deleteByCookieId(String cookieId) {
        Cart cart = new Cart();
        Cookie cookie = new Cookie(cookieId);
        cart.setCookie(cookie);
        dao.deleteByCookieId(cart);

        itemService.deleteByCookieId(cookieId);
    }
}
