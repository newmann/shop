/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.dao;

import com.iwc.shop.common.persistence.TreeDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.shop.entity.ShopCategory;

import java.util.List;
import java.util.Map;

/**
 * 产品目录DAO接口
 * @author Tony Wong
 * @version 2015-04-06
 */
@MyBatisDao
public interface ShopCategoryDao extends TreeDao<ShopCategory> {

    public ShopCategory getFirstSortCategory(ShopCategory category);
	
}
