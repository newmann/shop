/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.act.dao;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.modules.act.entity.Act;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;

/**
 * 审批DAO接口
 * @author Tony Wong
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
