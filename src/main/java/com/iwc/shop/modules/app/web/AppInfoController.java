package com.iwc.shop.modules.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tony Wong
 */
@Controller
@RequestMapping("/app/info")
public class AppInfoController extends AppBaseController {

    /**
     * 技术支持
     */
    @RequestMapping("/support")
    public String support() {
        return "modules/app/info/support";
    }
}
