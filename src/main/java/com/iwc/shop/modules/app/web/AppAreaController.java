/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Maps;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 区域Controller
 * @author Tony Wong
 * @version 2015-07-23
 */
@Controller
@RequestMapping("/app/area")
public class AppAreaController extends AppBaseController {

    /**
     * 获得系统默认的地址信息
     * @param response
     * @return
     */
    @RequestMapping(value = "/get-prefix-area-chained-list")
    public String getPrefixAreaChainedList(HttpServletResponse response) {
        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        Area prefixArea = AreaUtils.getArea(Area.PREFIX_AREA_ID);
        Map<String, Object> oArea = prefixArea.toSimpleObj();
        List<Map<String, Object>> oAreaChainedList = AreaUtils.findChainedList4SimpleObj(prefixArea);

        result = true;
        message = "";
        data.put("prefixArea", oArea);
        data.put("areaChainedList", oAreaChainedList);
        return renderString(response, result, message, data);
    }

}
