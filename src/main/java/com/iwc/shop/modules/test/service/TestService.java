/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.test.entity.Test;
import com.iwc.shop.modules.test.dao.TestDao;

/**
 * 测试Service
 * @author Tony Wong
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
