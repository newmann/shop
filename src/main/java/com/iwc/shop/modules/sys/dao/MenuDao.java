/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.sys.dao;

import java.util.List;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.modules.sys.entity.Menu;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;

/**
 * 菜单DAO接口
 * @author Tony Wong
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
