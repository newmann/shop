/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.dao;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.shop.entity.ShopProductAttributeItem;

/**
 * 商品公用属性DAO接口
 * @author Tony Wong
 * @version 2015-04-06
 */
@MyBatisDao
public interface ShopProductAttributeItemDao extends CrudDao<ShopProductAttributeItem> {
	
}
