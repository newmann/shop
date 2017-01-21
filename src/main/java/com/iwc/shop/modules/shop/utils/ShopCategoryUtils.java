/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.utils;

import com.google.common.collect.Lists;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.mapper.JsonMapper;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.SpringContextHolder;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import org.springframework.ui.Model;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 内容管理工具类
 * @author Tony Wong
 * @version 2013-5-29
 */
public class ShopCategoryUtils {
	
	private static ShopCategoryService categoryService = SpringContextHolder.getBean(ShopCategoryService.class);
    private static ServletContext context = SpringContextHolder.getBean(ServletContext.class);

	/** cache name and keys **/
	public static final String CACHE_NAME = "ShopCategory";
	public static final String CK_Category = "Category_";
    public static final String CK_findFirstList = "findFirstList";
    public static final String CK_getFirstSortCategory = "getFirstSortCategory";

    //每次目录或产品的修改都要清空这个缓存
    public static final String CK_findFirstListWithProduct4App = "findFirstListWithProduct4App";

	public static ShopCategory getCategory(String categoryId) {
		String key = CK_Category + categoryId;
		ShopCategory category = (ShopCategory) getCache(key);
		if (category == null) {
			category = categoryService.get(categoryId);
			putCache(key, category);
		}
		return category;
	}

	/**
	 * 获得第一级目录列表
	 */
	public static List<ShopCategory> findFirstList(){
        String key = CK_findFirstList;
		List<ShopCategory> list = (List<ShopCategory>) getCache(key);
		if (list == null){
			list = categoryService.findFirstList();
			putCache(key, list);
		}
		return list;
	}

    /**
     * 取得排列第一的目录
     */
    public static ShopCategory getFirstSortCategory() {
        String key = CK_getFirstSortCategory;
        ShopCategory category = (ShopCategory) getCache(key);
        if (category == null) {
            category = categoryService.getFirstSortCategory();
            putCache(key, category);
        }
        return category;
    }

    /*====================== for app ========================*/
    /**
     * 获得第一级目录列表和对应的产品列表
     * @return [{
     *      id:""
     *      name:""
     *      productList:[{
     *          id:""
     *          name:""
     *          imageSmall:""
     *      },...]
     * },...]
     */
    public static List<Map<String, Object>> findFirstListWithProduct4App() {
        List<Map<String, Object>> list = categoryService.findFirstListWithProduct4App();
        return list;
    }


	// ============== Shop Category Cache ==============
	
	public static Object getCache(String key) {
		return CacheUtils.get(CACHE_NAME, key);
	}

	public static void putCache(String key, Object value) {
		CacheUtils.put(CACHE_NAME, key, value);
	}

	public static void removeCache(String key) {
		CacheUtils.remove(CACHE_NAME, key);
	}

    public static void removeAllCache() {
        CacheUtils.removeAll(CACHE_NAME);
    }

    /**
     * 每次目录或产品的修改都要清空这个缓存
     */
    public static void removeCache4_findFirstListWithProduct4App() {
        CacheUtils.remove(CACHE_NAME, CK_findFirstListWithProduct4App);
    }
}