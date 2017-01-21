/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.sys.dao;

import java.util.List;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.modules.sys.entity.Dict;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;

/**
 * 字典DAO接口
 * @author Tony Wong
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
