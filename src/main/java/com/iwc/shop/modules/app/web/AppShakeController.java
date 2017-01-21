/**
 * Copyright &copy; 2015 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.RamdomUtils;
import com.iwc.shop.modules.shop.entity.Sezi;
import com.iwc.shop.modules.shop.service.SeziService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 摇一摇Controller
 * @author Tony Wong
 * @version 2015-07-14
 */
@Controller
@RequestMapping("/app/shake")
public class AppShakeController extends AppBaseController {

    @Autowired
    SeziService seziService;

	/**
	 * 色子摇一摇产生随机数
	 */
	@RequestMapping(value="/sezi")
	public String sezi(HttpServletRequest request, HttpServletResponse response) {
		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oSeziList = Lists.newArrayList();
        boolean isLoggedIn = isLoggedIn(request);
        String userId = getUserId(request);

        Map<String, Object> oSezi = Maps.newHashMap();
		String num = RamdomUtils.genSeziNumCheat(1);
		oSezi.put("num", num);
        oSezi.put("price", num);
        oSeziList.add(oSezi);

        if (isLoggedIn) {
            List<Map<String, Object>> oSeziList2 = Lists.newArrayList();
            Map<String, Object> oSezi2 = Maps.newHashMap();
            num = RamdomUtils.genSeziNumCheat1(1);
            List<Sezi> seziList = seziService.saveTodayShake(userId, num);
            for (Sezi sezi: seziList) {
                oSezi2 = sezi.toSimpleObj();
                oSeziList2.add(oSezi2);
            }
            oSeziList = oSeziList2;
        }

		result = true;
		message = "";
		data.put("seziList", oSeziList);
        data.put("isLoggedIn", isLoggedIn);
		return renderString(response, result, message, data);
	}
}
