package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.service.TreeService;
import com.iwc.shop.modules.shop.dao.ShopCategoryDao;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 栏目Service
 * @author Tony Wong
 * @version 2015-04-06
 */
@Service
@Transactional(readOnly = true)
public class ShopCategoryService extends TreeService<ShopCategoryDao, ShopCategory> {

    @Autowired
    private ShopProductService productService;

	@Transactional(readOnly = false)
	public void save(ShopCategory category) {
		super.save(category);
		ShopCategoryUtils.removeAllCache();
	}

	@Transactional(readOnly = false)
	public void delete(ShopCategory category) {
		super.delete(category);
        ShopCategoryUtils.removeAllCache();
	}

	/**
	 * 获得第一级目录列表
	 */
	public List<ShopCategory> findFirstList() {
		return findByParentId(ShopCategory.ROOT_ID);
	}

	/**
	 * 获得子目录列表
	 */
	public List<ShopCategory> findByParentId(String parentId){
		ShopCategory entity = new ShopCategory();
		ShopCategory parent = new ShopCategory();
		parent.setId(parentId);
		entity.setParent(parent);
		return dao.findByParentId(entity);
	}
	
	public Page<ShopCategory> find(Page<ShopCategory> page, ShopCategory category) {
//		DetachedCriteria dc = dao.createDetachedCriteria();
//		if (category.getSite()!=null && StringUtils.isNotBlank(category.getSite().getId())){
//			dc.createAlias("site", "site");
//			dc.add(Restrictions.eq("site.id", category.getSite().getId()));
//		}
//		if (category.getParent()!=null && StringUtils.isNotBlank(category.getParent().getId())){
//			dc.createAlias("parent", "parent");
//			dc.add(Restrictions.eq("parent.id", category.getParent().getId()));
//		}
//		if (StringUtils.isNotBlank(category.getInMenu()) && Category.SHOW.equals(category.getInMenu())){
//			dc.add(Restrictions.eq("inMenu", category.getInMenu()));
//		}
//		dc.add(Restrictions.eq(Category.FIELD_DEL_FLAG, Category.DEL_FLAG_NORMAL));
//		dc.addOrder(Order.asc("site.id")).addOrder(Order.asc("sort"));
//		return dao.find(page, dc);
//		page.setSpringPage(dao.findByParentId(category.getParent().getId(), page.getSpringPage()));
//		return page;
		category.setPage(page);
		return page;
	}

    /**
     * 取得排列第一的目录
     */
    public ShopCategory getFirstSortCategory() {
        ShopCategory category = new ShopCategory();
        return dao.getFirstSortCategory(category);
    }


	/*====================== for app ========================*/

	/**
	 * app首页 - 分类推荐
	 */
	public List<ShopCategory> findAppFeaturedHome(Integer count) {
		Page page = new Page<ShopCategory>();
		page.setPageNo(1);
		page.setPageSize(count);
		page.setOrderBy("a.app_featured_home_sort");
		ShopCategory category = new ShopCategory();
		category.setAppFeaturedHome(ShopCategory.APP_FEATURED_HOME_YES);
		return findPage(page, category).getList();
	}

    /**
     * 获得第一级目录列表和对应的产品列表
     * @return [{
     *      id:""
     *      name:""
     *      productList:[{
     *          id:""
     *          name:""
     *          imageSmall:""
     *          price:""
     *          featuredPrice:""
     *      },...]
     * },...]
     */
    public List<Map<String, Object>> findFirstListWithProduct4App() {
        List<Map<String, Object>> cateList = Lists.newArrayList();
        List<ShopCategory> categoryList = findByParentId(ShopCategory.ROOT_ID);
        for (ShopCategory category : categoryList) {
            Map<String, Object> cate = Maps.newHashMap();
            List<Map<String, Object>> prodList = Lists.newArrayList();

            List<ShopProduct> productList = productService.findByCategoryId(category.getId());
            for (ShopProduct product : productList) {
                Map<String, Object> prod = Maps.newHashMap();
                prod.put("id", product.getId());
                prod.put("categoryId", product.getCategory().getId());
                prod.put("name", product.getName());
                prod.put("imageSmall", product.getImageSmall());
                prod.put("price", product.getPrice());
                prod.put("featuredPrice", product.getFeaturedPrice());
                prod.put("featuredPosition", product.getFeaturedPosition());
                prod.put("featuredPositionSort", product.getFeaturedPositionSort());
                prod.put("sort", product.getSort());
                prodList.add(prod);
            }

            cate.put("id", category.getId());
            cate.put("name", category.getName());
            cate.put("productList", prodList);
            cateList.add(cate);
        }
        return cateList;
    }
}
