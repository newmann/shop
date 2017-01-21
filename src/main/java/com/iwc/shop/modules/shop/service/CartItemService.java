/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.IdGen;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.CartItemDao;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.utils.ShopProductAttributeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 购物车项Service
 * @author Tony Wong
 * @version 2015-04-16
 */
@Service
@Transactional(readOnly = true)
public class CartItemService extends CrudService<CartItemDao, CartItem> {

	@Autowired
	CartItemAttributeService attrService;

	@Autowired
	ShopProductAttributeItemService attrItemService;

	@Autowired
	ShopProductAttributeItemValueService attrValueService;

	/**
	 * 获取单条数据, 包括对应的产品属性
	 * @param id
	 * @return
	 */
	public CartItem getWithAttribute(String id) {
		CartItem item = dao.get(id);
		if (item != null) {
			List<CartItemAttribute> attrList = attrService.findByItemId(item);
			for (CartItemAttribute attr : attrList) {
				attr.setAttributeItem(attrItemService.get(attr.getAttributeItem().getId()));
				attr.setAttributeItemValue(attrValueService.get(attr.getAttributeItemValue().getId()));
			}
			item.setAttributeList(attrList);
		}
		return item;
	}

	/**
	 * 获得购物车唯一的产品
	 * @param cart
	 * @param product
	 * @param attributes 的格式 itemId_valueId, 如1_1
	 * @return
	 */
	public CartItem getByCartIdProductIdAttributes(Cart cart, ShopProduct product, List<String> attributes) {
		CartItem newItem = new CartItem();
		newItem.setCart(cart);
		newItem.setProduct(product);
		newItem.setIsOrdered(CartItem.IS_ORDERED_NO);
		List<CartItem> itemList = dao.findByCartIdProductId(newItem);

		for (CartItem item : itemList) {
			List<Map<String, String>> attributeMaps = ShopProductAttributeUtils.idstringToMap(attributes);

			//对应产品属性的产品是否添加到购物车?
			List<CartItemAttribute> attributeList = attrService.findByItemId(item);

			//属性都为空，则是已经添加的商品
			if ((attributeMaps == null || attributeMaps.isEmpty()) && (attributeList == null || attributeList.isEmpty())) {
				return item;
			}

			//属性个数和值都相等，则是已经添加的商品
			if (attributeList != null && attributeMaps != null && !attributeMaps.isEmpty() && !attributeList.isEmpty()
					&& attributeList.size() == attributeMaps.size()) {
				int existCount = 0;
				for (Map<String, String> map : attributeMaps) {
					for (CartItemAttribute attr : attributeList) {
						if (map.get("idstring").equals(attr.getAttributeIdstring())) {
							existCount++;
						}
					}
				}
				if (existCount == attributeMaps.size()) {
					return item;
				}
			}
		}

		return null;
	}

	/**
	 * 获得购物车唯一的产品
	 * @param cartId
	 * @param productId
	 * @param attributes 的格式 itemId_valueId, 如1_1
	 * @return
	 */
	public CartItem getByCartIdProductIdAttributes(String cartId, String productId, List<Map<String, String>> attributes) {
		CartItem item = new CartItem();
		Cart cart = new Cart(cartId);
		ShopProduct product = new ShopProduct(productId);
		item.setCart(cart);
		item.setProduct(product);
		item.setIsOrdered(CartItem.IS_ORDERED_NO);
		return dao.getByCartIdProductId(item);
	}

	/**
	 * @todo
	 * 这里获得不是唯一的产品，应该是个列表，待改
	 * @param cartId
	 * @param productId
	 * @return
	 */
	public CartItem getByCartIdProductId(String cartId, String productId) {
		CartItem item = new CartItem();
		Cart cart = new Cart(cartId);
		ShopProduct product = new ShopProduct(productId);
		item.setCart(cart);
		item.setProduct(product);
		item.setIsOrdered(CartItem.IS_ORDERED_NO);
		return dao.getByCartIdProductId(item);
	}

	/**
	 * 获得购物车的项, 包括对应的产品属性
	 * @param cookieId
	 * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
	 */
	public List<CartItem> findByCookieId(String cookieId, String isSelected) {
		CartItem newItem = new CartItem();
        newItem.setCookieId(cookieId);
		newItem.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            newItem.setIsSelected(isSelected);
		List<CartItem> list = dao.findByCookieId(newItem);

		for (CartItem item : list) {
			List<CartItemAttribute> attrList = attrService.findByItemId(item);
			for (CartItemAttribute attr : attrList) {
				attr.setAttributeItem(attrItemService.get(attr.getAttributeItem().getId()));
				attr.setAttributeItemValue(attrValueService.get(attr.getAttributeItemValue().getId()));
			}

			item.setAttributeList(attrList);
		}

		return list;
	}

    /**
     * 获得购物车的项, 包括对应的产品属性
     * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
     */
    public List<CartItem> findByAppCartCookieId(String appCartCookieId, String isSelected) {
        CartItem newItem = new CartItem();
        newItem.setAppCartCookieId(appCartCookieId);
        newItem.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            newItem.setIsSelected(isSelected);
        List<CartItem> list = dao.findByAppCartCookieId(newItem);

        for (CartItem item : list) {
            List<CartItemAttribute> attrList = attrService.findByItemId(item);
            for (CartItemAttribute attr : attrList) {
                attr.setAttributeItem(attrItemService.get(attr.getAttributeItem().getId()));
                attr.setAttributeItemValue(attrValueService.get(attr.getAttributeItemValue().getId()));
            }

            item.setAttributeList(attrList);
        }

        return list;
    }

    /**
     * 获得购物车的项, 包括对应的产品属性
     * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
     */
    public List<CartItem> findByUserId(String userId, String isSelected) {
        CartItem newItem = new CartItem();
        newItem.setUserId(userId);
        newItem.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            newItem.setIsSelected(isSelected);
        List<CartItem> list = dao.findByUserId(newItem);

        for (CartItem item : list) {
            List<CartItemAttribute> attrList = attrService.findByItemId(item);
            for (CartItemAttribute attr : attrList) {
                attr.setAttributeItem(attrItemService.get(attr.getAttributeItem().getId()));
                attr.setAttributeItemValue(attrValueService.get(attr.getAttributeItemValue().getId()));
            }

            item.setAttributeList(attrList);
        }

        return list;
    }

    /**
     * 获得购物车的项, 包括对应的产品属性
     * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
     */
    public List<Map<String, Object>> findByUserId4Json(String userId, String isSelected) {
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            item.setIsSelected(isSelected);

        return find4Json(item);
    }

    /**
     * 获得购物车的项, 包括对应的产品属性
     * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
     */
    public List<Map<String, Object>> findByCookieId4Json(String cookieId, String isSelected) {
        CartItem item = new CartItem();
        item.setCookieId(cookieId);
        item.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            item.setIsSelected(isSelected);

        return find4Json(item);
    }

    /**
     * 获得购物车的项, 包括对应的产品属性
     * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
     */
    public Map<String, Object> findByUserIdWithCount4Json(String userId, String isSelected) {
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            item.setIsSelected(isSelected);

        return findWithCount4Json(item, isSelected);
    }

    /**
     * 获得购物车的项, 包括对应的产品属性
     * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
     */
    public Map<String, Object> findByAppCartCookieIdWithCount4Json(String appCartCookieId, String isSelected) {
        if (StringUtils.isBlank(appCartCookieId))
            appCartCookieId = IdGen.uuid();

        CartItem item = new CartItem();
        item.setAppCartCookieId(appCartCookieId);
        item.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            item.setIsSelected(isSelected);

        return findWithCount4Json(item, isSelected);
    }

	/**
	 * 获得购物车的项, 包括对应的产品属性
	 * @return
	 *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
	 */
	public List<Map<String, Object>> find4Json(CartItem dItem) {
        List<Map<String, Object>> oList = Lists.newArrayList();
		List<CartItem> list = findList(dItem);

		for (CartItem item : list) {
            Map<String, Object> oItem = Maps.newHashMap();
            oList.add(oItem);

            Map<String, Object> oProduct =item.getProduct().toSimpleObj();
            List<Map<String, Object>> oAttrList = Lists.newArrayList();
            String oAttrPrintNames = "";

            oItem.put("id", item.getId());
            oItem.put("count", item.getCount());
            oItem.put("isSelected", item.getIsSelected());
            oItem.put("product", oProduct);
            oItem.put("attrList", oAttrList);
            oItem.put("attrPrintNames", oAttrPrintNames);

            ShopProduct product = item.getProduct();
            Float price_plusAttrPrice = product.getPrice();
            Float featuredPrice_plusAttrPrice = product.getFeaturedPrice();
            List<CartItemAttribute> attrList = attrService.findByItemId(item);
			for (CartItemAttribute attr : attrList) {
                Map<String, Object> oAttr = Maps.newHashMap();
                oAttrList.add(oAttr);

                Map<String, Object> oAttrItem = Maps.newHashMap();
                Map<String, Object> oAttrValue = Maps.newHashMap();

                oAttr.put("id", attr.getId());
                oAttr.put("attrItem", oAttrItem);
                oAttr.put("attrValue", oAttrValue);

                ShopProductAttributeItem attrItem = attrItemService.get(attr.getAttributeItem().getId());
                ShopProductAttributeItemValue attrValue = attrValueService.get(attr.getAttributeItemValue().getId());

                oAttrItem.put("id", attrItem.getId());
                oAttrItem.put("name", attrItem.getName());

                oAttrValue.put("id", attrValue.getId());
                oAttrValue.put("name", attrValue.getName());
                oAttrValue.put("price", attrValue.getPrice());
                oAttrValue.put("printName", attrValue.getPrintName());

                // set oAttrPrintNames
                if (StringUtils.isNotBlank(attrValue.getPrintName())) {
                    if (StringUtils.isBlank(oAttrPrintNames)) {
                        oAttrPrintNames = attrValue.getPrintName();
                    } else {
                        oAttrPrintNames += "、" + attrValue.getPrintName();
                    }
                }

                price_plusAttrPrice += attrValue.getPrice();
                if (featuredPrice_plusAttrPrice != null) {
                    featuredPrice_plusAttrPrice += attrValue.getPrice();
                }
			}

            oProduct.put("price_plusAttrPrice", price_plusAttrPrice);
            oProduct.put("featuredPrice_plusAttrPrice", featuredPrice_plusAttrPrice);
		}

		return oList;
	}

    /**
     * 获得购物车的项, 包括对应的产品属性
     * @return
     *  {CartItem: {CartItemAttribute(list):{CartItemAttributeItem, CartItemAttributeItemValue}}}
     */
    public Map<String, Object> findWithCount4Json(CartItem dItem, String isSelected) {
        Map<String, Object> result = Maps.newHashMap();
        int cartNum = 0;
        float totalPrice = 0;

        List<Map<String, Object>> oList = Lists.newArrayList();
        List<CartItem> list = findList(dItem);

        for (CartItem item : list) {
            //计算价格和个数
            float price = item.getProduct().getPrice();
            int count = item.getCount();
            if (item.getProduct().getFeaturedPrice() != null) {
                price = item.getProduct().getFeaturedPrice();
            }
            if (isSelected == null || item.getIsSelected().equals(isSelected)) {
                totalPrice += (price * count);
                cartNum += count;
            }

            Map<String, Object> oItem = Maps.newHashMap();
            oList.add(oItem);

            Map<String, Object> oProduct = item.getProduct().toSimpleObj();
            List<Map<String, Object>> oAttrList = Lists.newArrayList();
            String oAttrPrintNames = "";

            oItem.put("id", item.getId());
            oItem.put("count", item.getCount());
            oItem.put("isSelected", item.getIsSelected());
            oItem.put("product", oProduct);
            oItem.put("attrList", oAttrList);

            ShopProduct product = item.getProduct();
            Float price_plusAttrPrice = product.getPrice();
            Float featuredPrice_plusAttrPrice = product.getFeaturedPrice();
            List<CartItemAttribute> attrList = attrService.findByItemId(item);
            for (CartItemAttribute attr : attrList) {
                Map<String, Object> oAttr = Maps.newHashMap();
                oAttrList.add(oAttr);

                Map<String, Object> oAttrItem = Maps.newHashMap();
                Map<String, Object> oAttrValue = Maps.newHashMap();

                oAttr.put("id", attr.getId());
                oAttr.put("attrItem", oAttrItem);
                oAttr.put("attrValue", oAttrValue);

                ShopProductAttributeItem attrItem = attrItemService.get(attr.getAttributeItem().getId());
                ShopProductAttributeItemValue attrValue = attrValueService.get(attr.getAttributeItemValue().getId());

                oAttrItem.put("id", attrItem.getId());
                oAttrItem.put("name", attrItem.getName());

                oAttrValue.put("id", attrValue.getId());
                oAttrValue.put("name", attrValue.getName());
                oAttrValue.put("price", attrValue.getPrice());
                oAttrValue.put("printName", attrValue.getPrintName());

                // set oAttrPrintNames
                if (StringUtils.isNotBlank(attrValue.getPrintName())) {
                    if (StringUtils.isBlank(oAttrPrintNames)) {
                        oAttrPrintNames = attrValue.getPrintName();
                    } else {
                        oAttrPrintNames += "、" + attrValue.getPrintName();
                    }
                }

                price_plusAttrPrice += attrValue.getPrice();
                if (featuredPrice_plusAttrPrice != null) {
                    featuredPrice_plusAttrPrice += attrValue.getPrice();
                }

                //计算价格和个数
                if (isSelected == null || item.getIsSelected().equals(isSelected)) {
                    totalPrice += (attrValue.getPrice() * count);
                }
            }

            //String要放在最后赋值，否则会取不到最新的值
            oItem.put("attrPrintNames", oAttrPrintNames);

            oProduct.put("price_plusAttrPrice", price_plusAttrPrice);
            oProduct.put("featuredPrice_plusAttrPrice", featuredPrice_plusAttrPrice);
        }

        result.put("cartNum", cartNum);
        result.put("totalPrice", totalPrice);
        result.put("cartItemList", oList);


        return result;
    }

    /**
     * 购物车的个数
     */
	public int countByUserId(String userId, String isSelected) {
        int count = 0;
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setIsSelected(isSelected);
        item.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            item.setIsSelected(isSelected);

        Map<String, Object> map = dao.countByUserId(item);
        if (map != null)
            count = Integer.valueOf(map.get("count").toString());

        return count;
    }

    /**
     * 购物车的个数
     */
    public int countByCookieId(String cookieId, String isSelected) {
        int count = 0;
        CartItem item = new CartItem();
        item.setCookieId(cookieId);
        item.setIsSelected(isSelected);
        item.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            item.setIsSelected(isSelected);

        Map<String, Object> map = dao.countByCookieId(item);
        if (map != null)
            count = Integer.valueOf(map.get("count").toString());

        return count;
    }

    /**
     * 购物车的个数
     */
    public int countByAppCartCookieId(String appCartCookieId, String isSelected) {
        int count = 0;
        CartItem item = new CartItem();
        item.setAppCartCookieId(appCartCookieId);
        item.setIsSelected(isSelected);
        item.setIsOrdered(CartItem.IS_ORDERED_NO);
        if (StringUtils.isNotBlank(isSelected))
            item.setIsSelected(isSelected);

        Map<String, Object> map = dao.countByAppCartCookieId(item);
        if (map != null)
            count = Integer.valueOf(map.get("count").toString());

        return count;
    }

    /**
     * 清空购物车项
     */
    @Transactional(readOnly = false)
    public void clearByUserId(String userId) {
        CartItem item = new CartItem();
        item.setUserId(userId);
        dao.clearByUserId(item);
    }

    @Transactional(readOnly = false)
    public void deleteByCookieId(String cookieId) {
        CartItem item = new CartItem();
        item.setCookieId(cookieId);
        dao.deleteByCookieId(item);
    }
}
