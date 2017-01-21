/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Maps;
import com.iwc.shop.modules.shop.service.HistoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 浏览产品历史Controller
 * @author Tony Wong
 * @version 2015-05-17
 */
@Controller
@RequestMapping("/app/history-product")
public class AppHistoryProductController extends AppBaseController {

    @Autowired
    HistoryProductService historyProductService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result = true;
        String message = "";
        Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);
        List<Map<String, Object>> oHistoryProductList = historyProductService.findByUserId4SimpleObj(userId);
        data.put("historyProductList", oHistoryProductList);

        return renderString(response, result, message, data);
	}
}
