/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 关于我们的信息Controller
 * @author Tony Wong
 * @version 2015-05-17
 */
@Controller
@RequestMapping("/app/about")
public class AppAboutController extends AppBaseController {

	/**
	 * 关于我们
	 */
	@RequestMapping("/us")
	public String list(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> us = Maps.newHashMap();

        String desc = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月光茶人为客户提供休闲温馨的环境，以最真诚的态度服务于我们的客户，提供香浓的咖啡、健康的奶茶和美味的小吃。为了让客户享受更便捷的服务和优惠价格，我们开发了月光茶人APP，欢迎使用。";
        String copyright = "Copyright © 2015";
        String company = "深圳市宝安区福永月光茶人饮品店";
        String address = "深圳市宝安区福永东福围东街103-5";
        String tel = "联系电话：0755-29350032";
        us.put("desc", desc);
        us.put("copyright", copyright);
        us.put("company", company);
        us.put("address", address);
        us.put("tel", tel);

        result = true;
        message = "";
        data.put("us", us);
        return renderString(response, result, message, data);
	}

    /**
     * 技术支持
     */
    @RequestMapping("/support")
    public String support(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> support = Maps.newHashMap();

        String desc1 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果您在购买的过程中遇到任何问题，请联系我们可爱的工程师。";
        String desc2 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如能协助我们可爱的工程师解决问题，相信他会送给您一个优惠大红包滴。";
        String name = "联系人：黄工";
        String tel = "手机号：15889639895";
        String qq = "QQ号：&nbsp;&nbsp;908601756";
        String email = "Email：&nbsp;&nbsp;908601756@qq.com";
        support.put("desc1", desc1);
        support.put("desc2", desc2);
        support.put("name", name);
        support.put("tel", tel);
        support.put("qq", qq);
        support.put("email", email);

        result = true;
        message = "";
        data.put("support", support);
        return renderString(response, result, message, data);
    }

    /**
     * 技术支持
     */
    @RequestMapping("/address")
    public String address(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> address = Maps.newHashMap();

        String desc1 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎光临月光茶人饮品店。";
        String desc2 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月光茶人提供最自然、最健康、最好吃的、最好喝的饮品和小吃。";
        String name = "门店名称：月光茶人饮品店";
        String addr = "门店地址：深圳宝安区福永东福围东街103-5";
        String tel = "门店电话：0755-29350032";
        String email = "门店邮箱：908601756@qq.com";
        address.put("desc1", desc1);
        address.put("desc2", desc2);
        address.put("name", name);
        address.put("addr", addr);
        address.put("tel", tel);
        address.put("email", email);

        result = true;
        message = "";
        data.put("address", address);
        return renderString(response, result, message, data);
    }
}
