/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.CouponDao;
import com.iwc.shop.modules.shop.dao.CouponUserDao;
import com.iwc.shop.modules.shop.entity.Coupon;
import com.iwc.shop.modules.shop.entity.CouponUser;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 优惠券Service
 * @author Tony Wong
 * @version 2015-07-16
 */
@Service
@Transactional(readOnly = true)
public class CouponService extends CrudService<CouponDao, Coupon> {


}
