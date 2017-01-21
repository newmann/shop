/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.mobile.web;

import com.iwc.shop.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * app客户端下载Controller
 * @author Tony Wong
 * @version 2015-07-06
 */
@Controller
@RequestMapping(value = "/m")
public class MIndexController extends BaseController {

	@RequestMapping("/download")
	public String download() {
		return "modules/mobile/index/download";
	}
	
}
