/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductService;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * 商品工具类
 * @author Tony Wong
 * @version 2015-04-06
 */
public class ShopProductUtils {

	private static ShopProductService productService = SpringContextHolder.getBean(ShopProductService.class);

	/** cache name and keys **/
	public static final String CACHE_NAME = "ShopProduct";
	public static final String CK_Product_ = "Product_";
	public static final String CK_findByCategoryId_ = "findByCategoryId_"; //分类ID过滤的产品列表
    public static final String CK_findActiveByCategoryId_ = "findActiveByCategoryId_"; //分类ID过滤的可显示的产品列表
	public static final String CK_findAppFeaturedTopic = "findAppFeaturedTopic"; //app乐呵呵主题的商品推荐列表

	public static ShopProduct getProduct (String productId) {
		String key = CK_Product_ + productId;
		ShopProduct product = (ShopProduct) getCache(key);
		if (product == null) {
			product = productService.get(productId);
			putCache(key, product);
		}
		return product;
	}

    public static List<ShopProduct> findByCategoryId(String categoryId) {
        String key = CK_findByCategoryId_ + categoryId;
        List<ShopProduct> list = (List<ShopProduct>) getCache(key);
        if (list == null) {
            list = productService.findByCategoryId(categoryId);
            putCache(key, list);
        }
        return list;
    }

    public static List<ShopProduct> findActiveByCategoryId(String categoryId) {
        String key = CK_findActiveByCategoryId_ + categoryId;
        List<ShopProduct> list = (List<ShopProduct>) getCache(key);
        if (list == null) {
            list = productService.findActiveByCategoryId(categoryId);
            putCache(key, list);
        }
        return list;
    }

	/**
	 * app乐呵呵主题的商品推荐列表
	 */
	public static List<ShopProduct> findAppFeaturedTopic() {
		int count = 18;
		String key = CK_findAppFeaturedTopic;
		List<ShopProduct> list = (List<ShopProduct>) getCache(key);
		if (list == null) {
			list = productService.findAppFeaturedTopic(count);
			putCache(key, list);
		}
		return list;
	}

	// ============== shop product cache ==============

	public static Object getCache(String key) {
		return CacheUtils.get(CACHE_NAME, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(CACHE_NAME, key, value);
	}

	public static void removeAllCache() {
		CacheUtils.removeAll(CACHE_NAME);
	}
}