/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.shop.dao.ShopProductDao;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品Service
 * @author Tony Wong
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ShopProductService extends CrudService<ShopProductDao, ShopProduct> {

	@Autowired
	ShopProductAttributeService attributeService;

	/**
	 * 获取产品实体, 包含属性和属性值
	 */
	public ShopProduct get(String id) {
		ShopProduct product = dao.get(id);
		if (product != null)
			product.setAttributeList(attributeService.findByProductId(product, true));
		return product;
	}

	@Transactional(readOnly = false)
	public void save(ShopProduct product) {
		//HTML解码product description
		if (product.getDescription() != null) {
			product.setDescription(StringEscapeUtils.unescapeHtml4(product.getDescription()));
		}

		super.save(product);

		ShopProductUtils.removeAllCache();
        ShopCategoryUtils.removeCache4_findFirstListWithProduct4App();
	}

	@Transactional(readOnly = false)
	public void delete(ShopProduct product) {
		super.delete(product);
        ShopProductUtils.removeAllCache();
        ShopCategoryUtils.removeCache4_findFirstListWithProduct4App();
	}

	@Transactional(readOnly = false)
	public List<ShopProduct> findByCategoryId(String categoryId) {
		ShopProduct product = new ShopProduct();
		ShopCategory category = new ShopCategory();
		category.setId(categoryId);
		product.setCategory(category);
		return findList(product);
	}

    @Transactional(readOnly = false)
    public List<ShopProduct> findActiveByCategoryId(String categoryId) {
        ShopProduct product = new ShopProduct();
        ShopCategory category = new ShopCategory();
        category.setId(categoryId);
        product.setCategory(category);
        product.setStatus(ShopProduct.STATUS_ACTIVE);
        return findList(product);
    }

	/**
	 * 首页 - 每日特价
	 */
	public List<ShopProduct> findFeaturedHomeDay() {
		Page page = new Page<ShopProduct>();
		page.setPageNo(1);
		page.setPageSize(2);
		ShopProduct product = new ShopProduct();
		product.setFeaturedPosition(ShopProduct.FEATURED_POSITION_HOME_DAY);
		return findPage(page, product).getList();
	}

	/**
	 * 首页 - 每日特卖
	 */
	public List<ShopProduct> findFeaturedHomeSpecial() {
        Page page = new Page<ShopProduct>();
        page.setPageNo(1);
        page.setPageSize(2);
        ShopProduct product = new ShopProduct();
        product.setFeaturedPosition(ShopProduct.FEATURED_POSITION_HOME_SPECIAL);
        return findPage(page, product).getList();
	}


	/*====================== for app ========================*/

	/**
	 * app首页 - 商品推荐
	 */
	public List<ShopProduct> findAppFeaturedHome(Integer count) {
		Page page = new Page<ShopProduct>();
		page.setPageNo(1);
		page.setPageSize(count);
		page.setOrderBy("a.app_featured_home_sort");
		ShopProduct product = new ShopProduct();
		product.setAppFeaturedHome(ShopProduct.APP_FEATURED_HOME_YES);
		return findPage(page, product).getList();
	}

	/**
	 * app 乐呵呵主题的商品推荐列表
	 */
	public List<ShopProduct> findAppFeaturedTopic(int count) {
		ShopProduct product = new ShopProduct();
		Page page = new Page<ShopProduct>();
		page.setPageNo(1);
		page.setPageSize(count);
		page.setOrderBy("a.app_featured_topic_sort");
		product.setAppFeaturedTopic(Global.YES);
		return findPage(page, product).getList();
	}

	/**
	 * 获得打折产品列表
	 */
	public List<ShopProduct> findFeaturedPrice() {
		ShopProduct product = new ShopProduct();
		return dao.findFeaturedPrice(product);
	}

//	/**
//	 * 更新缓存
//	 */
//	private void removeCaches(ShopProduct product) {
//		//更新缓存 - 该产品
//		ShopProductUtils.removeCache(ShopProductUtils.CK_product_ + product.getId());
//		//更新缓存 - 带categoryId的产品列表
//		if (product.getCategory() != null && product.getCategory().getId() != null) {
//			ShopProductUtils.removeCache(ShopProductUtils.CK_findByCategoryId_ + product.getCategory().getId());
//		}
//		//更新缓存
//		ShopProductUtils.removeCaches();
//	}
}
