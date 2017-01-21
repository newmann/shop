/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.shop.dao.SeziDao;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 色子每天摇一摇对象Service
 * @author Tony Wong
 * @version 2015-04-16
 */
@Service
@Transactional(readOnly = true)
public class SeziService extends CrudService<SeziDao, Sezi> {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponUserService couponUserService;

    /**
     * 查找今天摇的色子列表
     * @param userId
     * @return
     */
	public List<Sezi> findTodayShakeByUserId(String userId) {
        Sezi sezi = new Sezi();
        Map<String, String> sqlMap = Maps.newHashMap();

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //todayStartStr
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date todayStart = startCalendar.getTime();
        String todayStartStr = df.format(todayStart);

        //todayEndStr
        startCalendar.set(Calendar.HOUR_OF_DAY, 23);
        startCalendar.set(Calendar.MINUTE, 59);
        startCalendar.set(Calendar.SECOND, 59);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date todayEnd = startCalendar.getTime();
        String todayEndStr = df.format(todayEnd);

        String where = "a.create_date between '" + todayStartStr + "' AND '" + todayEndStr + "'";
        sqlMap.put("where", where);

        sezi.setUserId(userId);
        sezi.setSqlMap(sqlMap);

        return findList(sezi);
    }

    /**
     * 获得今天已经摇一摇的次数
     * @param userId
     * @return
     */
    public int countDailyShakeByUserId(String userId) {
        int count = 0;
        Sezi sezi = new Sezi();
        Map<String, String> sqlMap = Maps.newHashMap();

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //todayStartStr
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date todayStart = startCalendar.getTime();
        String todayStartStr = df.format(todayStart);

        //todayEndStr
        startCalendar.set(Calendar.HOUR_OF_DAY, 23);
        startCalendar.set(Calendar.MINUTE, 59);
        startCalendar.set(Calendar.SECOND, 59);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date todayEnd = startCalendar.getTime();
        String todayEndStr = df.format(todayEnd);

        String where = "a.create_date between '" + todayStartStr + "' AND '" + todayEndStr + "'";
        sqlMap.put("where", where);

        sezi.setUserId(userId);
        sezi.setSqlMap(sqlMap);

        Map<String, Long> retMap = dao.countDailyShakeByUserId(sezi);
        if (retMap != null) {
            count = Integer.valueOf(retMap.get("count").toString());
        }

        return count;
    }

    /**
     * 判断今天是否还可以摇一摇
     */
    public boolean canDailyShakeByUserId(String userId) {
        if (countDailyShakeByUserId(userId) < Sezi.SHAKE_COUNT_DAILY)
            return true;
        else
            return false;
    }

    /**
     * 保存今天摇的色子，每天只能保存三次, 总是能优惠一块钱
     * @return 返回今天摇的色子列表
     */
    @Transactional(readOnly = false)
    public List<Sezi> saveTodayShake(String userId, String num) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(num)) {
            return null;
        }

        List<Sezi> list;
        if (canDailyShakeByUserId(userId)) {
            Sezi sezi = new Sezi();
            User user = new User(userId);
            sezi.setUserId(userId);
            sezi.setNum(num);
            sezi.setPrice(Float.valueOf(num.toString()));
            sezi.setCreateBy(user);
            sezi.setUpdateBy(user);
            save(sezi);

            //如果是第三次保存，则保存最大值
            Sezi findSezi = new Sezi();
            list = findTodayShakeByUserId(userId);
            if (list.size() >= Sezi.SHAKE_COUNT_DAILY) {
                Sezi maxSezi = list.get(0);
                int maxNum = 0;
                for (Sezi entity : list) {
                    if (maxNum <= Integer.valueOf(entity.getNum())) {
                        maxNum = Integer.valueOf(entity.getNum());
                        maxSezi = entity;
                    }
                }
                maxSezi.setIsMaxInDay(Global.YES);

                //如果三次摇到都是0，则最大值为1
                if (Integer.valueOf(maxSezi.getNum()) < 1) {
                    maxSezi.setNum("1");
                    maxSezi.setPrice(1f);
                }
                save(maxSezi);

                //把最大值放入优惠券数据表
                Date date = new Date();
                Calendar startCalendar = new GregorianCalendar();
                startCalendar.setTime(date);
                startCalendar.set(Calendar.HOUR_OF_DAY, 23);
                startCalendar.set(Calendar.MINUTE, 59);
                startCalendar.set(Calendar.SECOND, 59);
                startCalendar.set(Calendar.MILLISECOND, 0);
                Date endDate = startCalendar.getTime();

                Coupon coupon = couponService.get(Coupon.ID_SHAKE_SEZI_USER);
                CouponUser couponUser = new CouponUser();
                couponUser.setCouponId(coupon.getId());
                couponUser.setHasUsed(Global.NO);
                couponUser.setStartDate(date);
                couponUser.setEndDate(endDate);
                couponUser.setDesc(coupon.getDesc());
                couponUser.setType(coupon.getType());
                couponUser.setTypeDesc(coupon.getTypeDesc());
                couponUser.setUsedType(coupon.getUsedType());
                couponUser.setUsedTypeDesc(coupon.getUsedTypeDesc());
                couponUser.setDurationDay(coupon.getDurationDay());
                couponUser.setDurationDayDesc(coupon.getDurationDayDesc());
                couponUser.setName(coupon.getName());
                couponUser.setPrice(maxSezi.getPrice());
                couponUser.setCreateBy(maxSezi.getCreateBy());
                couponUser.setCreateDate(maxSezi.getCreateDate());
                couponUser.setUpdateBy(maxSezi.getUpdateBy());
                couponUser.setUpdateDate(maxSezi.getUpdateDate());
                couponUser.setUser(user);
                couponUserService.save(couponUser);
            }
        } else {
            list = findTodayShakeByUserId(userId);
        }

        return list;
    }


}
