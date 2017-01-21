/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.IpAddrUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.*;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 优惠券Controller
 * @author Tony Wong
 * @version 2015-05-17
 */
@Controller
@RequestMapping("/app/coupon")
public class AppCouponController extends AppBaseController {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponUserService couponUserService;

	/**
	 * 优惠券列表, 根据userId查看
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		if (!isLoggedIn(request)) {
			return renderNotLoggedIn(response);
		}

		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();
		List<Map<String, Object>> oCouponUserList = Lists.newArrayList();
        String userId = getUserId(request);

        List<CouponUser> couponUserList = couponUserService.findByUserId(userId);
        for (CouponUser couponUser : couponUserList) {
            String oLeftTimeStr;
            long leftTime;
            Date nowDate = new Date();
            if (couponUser.getHasUsed().equals(Global.NO)) {
                if (couponUser.getEndDate() != null) {
                    if (couponUser.getEndDate().getTime() > nowDate.getTime()) {
                        leftTime = (couponUser.getEndDate().getTime() - nowDate.getTime()) / (24 * 3600 * 1000) + 1;
                        oLeftTimeStr = "剩" + leftTime + "天";
                    } else {
                        leftTime = 0;
                        oLeftTimeStr = "已过期";
                    }
                } else {
                    leftTime = -1; //永久有效
                    oLeftTimeStr = "有效";
                }
            } else {
                leftTime = 0;
                oLeftTimeStr = "已使用";
            }

            String oStartDateStr = "";
            String oEndDateStr = "";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if (couponUser.getStartDate() != null) {
                oStartDateStr = df.format(couponUser.getStartDate());
            }
            if (couponUser.getEndDate() != null) {
                oEndDateStr = df.format(couponUser.getEndDate());
            }

            Map<String, Object> oCouponUser = couponUser.toSimpleObj();
            oCouponUser.put("leftTime", leftTime);
            oCouponUser.put("leftTimeStr", oLeftTimeStr);
            oCouponUser.put("startDateStr", oStartDateStr);
            oCouponUser.put("endDateStr", oEndDateStr);
            oCouponUserList.add(oCouponUser);
        }

        result = true;
        message = "";
        data.put("couponList", oCouponUserList);
        return renderString(response, result, message, data);
	}

}
