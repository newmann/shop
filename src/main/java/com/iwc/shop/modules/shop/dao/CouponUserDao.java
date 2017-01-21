/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.dao;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.shop.entity.CouponUser;

import java.util.List;
import java.util.Map;

/**
 * 用户关联的优惠券DAO接口
 * @author Tony Wong
 * @version 2015-07-16
 */
@MyBatisDao
public interface CouponUserDao extends CrudDao<CouponUser> {

    Map<String, Object> hasSentId1ForRegister(CouponUser couponUser);

    Map<String, Object> countUsefulCoupon(CouponUser couponUser);

    List<CouponUser> findUsefulCoupon(CouponUser couponUser);

    List<CouponUser> findUnUsefulCoupon(CouponUser couponUser);

}
