/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.CouponUserDao;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 购物车Service
 * @author Tony Wong
 * @version 2015-07-16
 */
@Service
@Transactional(readOnly = true)
public class CouponUserService extends CrudService<CouponUserDao, CouponUser> {

    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    /**
     * 查找用户的所有优惠券
     * @param userId
     * @return
     */
    public List<CouponUser> findByUserId(String userId) {
        List<CouponUser> list = Lists.newArrayList();
        List<CouponUser> usefulList = findUsefulCoupon(userId);
        List<CouponUser> unUsefulList = findUnUsefulCoupon(userId);

        for (CouponUser usefulCoupon : usefulList) {
            list.add(usefulCoupon);
        }
        for (CouponUser unUsefulCoupon : unUsefulList) {
            list.add(unUsefulCoupon);
        }

        return list;
    }

    /**
     * 给新注册用户发送优惠券
     */
    @Transactional(readOnly = false)
	public boolean send4NewUser(String userId) {
		if (StringUtils.isBlank(userId)) {
            return false;
        }

        User user = userService.get(userId);

        if (user == null) {
            return false;
        }

        if (hasSentId1ForRegister(userId)) {
            return false;
        }

        CouponUser couponUser1 = new CouponUser();
        Coupon coupon = couponService.get(Coupon.ID_REGISTER1);

        Date date = new Date();
        Date startDate;
        Date endDate;

        //startDate
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        startDate = startCalendar.getTime();

        //endDate
        startCalendar.add(Calendar.DAY_OF_YEAR, +(coupon.getDurationDay()-1));
        startCalendar.set(Calendar.HOUR_OF_DAY, 23);
        startCalendar.set(Calendar.MINUTE, 59);
        startCalendar.set(Calendar.SECOND, 59);
        startCalendar.set(Calendar.MILLISECOND, 0);
        endDate = startCalendar.getTime();

//        Calendar endCalendar = new GregorianCalendar();
//        long durationTime = (coupon.getDurationDay()-1) * 3600 * 24 * 1000;
//        Date tmpDate = new Date();
//        tmpDate.setTime(startDate.getTime() + durationTime);
//        endCalendar.setTime(tmpDate);
//        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
//        endCalendar.set(Calendar.MINUTE, 59);
//        endCalendar.set(Calendar.SECOND, 59);
//        endDate = endCalendar.getTime();

        //保存第一次
        couponUser1.setUser(user);
        couponUser1.setHasUsed(Global.NO);
        couponUser1.setCouponId(coupon.getId());
        couponUser1.setName(coupon.getName());
        couponUser1.setDesc(coupon.getDesc());
        couponUser1.setPrice(coupon.getPrice());
        couponUser1.setStartDate(startDate);
        couponUser1.setEndDate(endDate);
        couponUser1.setType(coupon.getType());
        couponUser1.setTypeDesc(coupon.getTypeDesc());
        couponUser1.setDurationDay(coupon.getDurationDay());
        couponUser1.setDurationDayDesc(coupon.getDurationDayDesc());
        couponUser1.setUsedType(coupon.getUsedType());
        couponUser1.setUsedTypeDesc(coupon.getUsedTypeDesc());
        save(couponUser1);

        //保存第二次
        CouponUser couponUser2 = new CouponUser();
        couponUser2.setUser(couponUser1.getUser());
        couponUser2.setStartDate(couponUser1.getStartDate());
        couponUser2.setEndDate(couponUser1.getEndDate());
        couponUser2.setHasUsed(couponUser1.getHasUsed());
        couponUser2.setCouponId(coupon.getId());
        couponUser2.setName(couponUser1.getName());
        couponUser2.setDesc(couponUser1.getDesc());
        couponUser2.setPrice(couponUser1.getPrice());
        couponUser2.setDurationDay(couponUser1.getDurationDay());
        couponUser2.setDurationDayDesc(couponUser1.getDurationDayDesc());
        couponUser2.setType(couponUser1.getType());
        couponUser2.setTypeDesc(couponUser1.getTypeDesc());
        couponUser2.setUsedType(couponUser1.getUsedType());
        couponUser2.setUsedTypeDesc(couponUser1.getUsedTypeDesc());
        save(couponUser2);

        //保存第三次
        CouponUser couponUser3 = new CouponUser();
        couponUser3.setUser(couponUser1.getUser());
        couponUser3.setStartDate(couponUser1.getStartDate());
        couponUser3.setEndDate(couponUser1.getEndDate());
        couponUser3.setHasUsed(couponUser1.getHasUsed());
        couponUser3.setCouponId(coupon.getId());
        couponUser3.setName(couponUser1.getName());
        couponUser3.setDesc(couponUser1.getDesc());
        couponUser3.setPrice(couponUser1.getPrice());
        couponUser3.setDurationDay(couponUser1.getDurationDay());
        couponUser3.setDurationDayDesc(couponUser1.getDurationDayDesc());
        couponUser3.setType(couponUser1.getType());
        couponUser3.setTypeDesc(couponUser1.getTypeDesc());
        couponUser3.setUsedType(couponUser1.getUsedType());
        couponUser3.setUsedTypeDesc(couponUser1.getUsedTypeDesc());
        save(couponUser3);

        return true;
	}

    /**
     * 是否给新注册用户发送优惠券ID1
     */
    public boolean hasSentId1ForRegister(String userId) {
        CouponUser couponUser = new CouponUser();
        Coupon coupon = new Coupon(Coupon.ID_REGISTER1);
        User user = new User(userId);
        couponUser.setCouponId(coupon.getId());
        couponUser.setUser(user);

        Map<String, Object> map = dao.hasSentId1ForRegister(couponUser);
        int count = 0;
        if (map != null)
            count = Integer.valueOf(map.get("count").toString());

        if (count > 0)
            return true;
        else
            return false;
    }

    /**
     * 获得有用的优惠券数量
     */
    public int countUsefulCoupon(String userId) {
        CouponUser couponUser = new CouponUser();
        User user = new User(userId);
        couponUser.setUser(user);
        couponUser.setEndDate(new Date());
        couponUser.setHasUsed(Global.NO);

        Map<String, Object> map = dao.countUsefulCoupon(couponUser);
        int count = 0;
        if (map != null)
            count = Integer.valueOf(map.get("count").toString());

        return count;
    }

    /**
     * 查找有用的优惠券列表, 即未过期未使用的
     */
    public List<CouponUser> findUsefulCoupon(String userId) {
        CouponUser couponUser = new CouponUser();
        User user = new User(userId);
        couponUser.setUser(user);
        couponUser.setEndDate(new Date());
        couponUser.setHasUsed(Global.NO);

        return dao.findUsefulCoupon(couponUser);
    }

    /**
     * 查找无用的优惠券列表, 即已过期的和未过期已使用的
     */
    public List<CouponUser> findUnUsefulCoupon(String userId) {
        CouponUser couponUser = new CouponUser();
        User user = new User(userId);
        couponUser.setUser(user);
        couponUser.setEndDate(new Date());
        couponUser.setHasUsed(Global.YES);
        return dao.findUnUsefulCoupon(couponUser);
    }

    /**
     * 转化list为普通对象，并且计算剩余的天数等
     */
    public List<Map<String, Object>> listToSimpleObjWithCal(List<CouponUser> list) {
        List<Map<String, Object>> oCouponUserList = Lists.newArrayList();

        for (CouponUser couponUser : list) {
            oCouponUserList.add(couponUser.toSimpleObjWithCal());
        }

        return oCouponUserList;
    }
}
