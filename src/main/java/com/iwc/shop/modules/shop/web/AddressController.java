package com.iwc.shop.modules.shop.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.JsonUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.Address;
import com.iwc.shop.modules.shop.entity.Cookie;
import com.iwc.shop.modules.shop.service.AddressService;
import com.iwc.shop.modules.shop.service.CookieService;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.service.AreaService;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 用户地址Controller
 * @author Tony Wong
 * @version 2015-04-16
 */

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {
	
	@Autowired
	private AddressService addressService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private CookieService cookieService;
	
	@RequestMapping("/ajax-add")
	public void ajaxAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean result = false;
		String message = "";
		Address address = null;
//		String cookieKey = cookieService.getCookieKey(request, response);
//		String areaIdString = request.getParameter("areaId");
//		String telephone = request.getParameter("telephone");
//		String detail = request.getParameter("detail");
//		String fullname = request.getParameter("fullname");
//
//		if (StringUtils.isNotBlank(areaIdString) && StringUtils.isNotBlank(telephone)
//				&& StringUtils.isNotBlank(detail) && StringUtils.isNotBlank(fullname)) {
//			Integer areaId = Integer.valueOf(areaIdString);
//			Area area = areaService.get(areaId);
//			if (area != null) {
//				// save address
//				address = new Address();
//				address.setArea(area);
//				address.setTelephone(telephone);
//				address.setDetail(detail);
//				address.setFullname(fullname);
//				address.setCookieKey(cookieKey);
//				addressService.save(address);
//
//				// set areaId to cookie
//				CookieUtil.setCookie(response, CookieNameMap.AREA_ID, address.getArea().getId().toString());
//
//				result = true;
//				message = "地址已保存";
//			} else {
//				result = false;
//				message = "Area(ID:" + areaId + ") 不存在";
//			}
//		} else {
//			result = false;
//			message = "地址表单没有填写完整";
//		}
		
		JsonUtils.setResponse(response);
		response.getWriter().print(JsonUtils.toString(result, message, address));
	}
	
	@RequestMapping(value = "/ajax-save-last-area")
	public void ajaxSaveLastArea(@RequestParam String areaId,
								 @RequestParam String telephone,
								 @RequestParam String fullname,
								 @RequestParam String detail,
								 HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();
		Address address;
		String cookieId = CookieUtils.getCookieId(request, response);

		if (StringUtils.isNotBlank(areaId) && StringUtils.isNotBlank(telephone)
				&& StringUtils.isNotBlank(detail) && StringUtils.isNotBlank(fullname)) {
			Area area = AreaUtils.getArea(areaId);
			if (area != null) {
				// save address
				address = addressService.getDefaultByCookieId(cookieId);
				
				if (address == null) {
					address = new Address();
					Cookie cookie = new Cookie(cookieId);
					address.setCookie(cookie);
				}

				address.setArea(area);
				address.setTelephone(telephone);
				address.setDetail(detail);
				address.setFullname(fullname);
				addressService.save(address);
				
				// set areaId to cookie
				AreaUtils.setAreaIdToCookie(address.getArea().getId(), response);
				
				result = true;
				message = "地址已保存";
				Map<String, Object> areaData = Maps.newHashMap();
				areaData.put("id", address.getArea().getId());
				areaData.put("name", address.getArea().getName());
				areaData.put("pathIds", address.getArea().getPathIds());
				areaData.put("pathNames", address.getArea().getPathNames());
				data.put("area", areaData);
				data.put("id", address.getId());
				data.put("fullname", address.getFullname());
				data.put("detail", address.getDetail());
				data.put("telephone", address.getTelephone());
			} else {
				result = false;
				message = "Area(ID:" + areaId + ") 不存在";
			}
		} else {
			result = false;
			message = "地址表单没有填写完整";
		}
		
		JsonUtils.setResponse(response);
		response.getWriter().print(JsonUtils.toString(result, message, data));
	}
}
