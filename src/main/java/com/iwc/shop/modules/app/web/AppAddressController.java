package com.iwc.shop.modules.app.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.JsonUtils;
import com.iwc.shop.common.utils.ValidateUtils;
import com.iwc.shop.modules.shop.entity.Address;
import com.iwc.shop.modules.shop.entity.Cookie;
import com.iwc.shop.modules.shop.service.AddressService;
import com.iwc.shop.modules.shop.service.CookieService;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.AreaService;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用户地址Controller
 * @author Tony Wong
 * @version 2015-04-16
 */

@Controller
@RequestMapping("/app/address")
public class AppAddressController extends AppBaseController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AreaService areaService;

    /**
     * 添加收货地址，在app下单页面添加，添加的地址则为默认地址
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
		String message;
        Map<String, Object> data = Maps.newHashMap();

		String areaId = request.getParameter("areaId");
		String telephone = request.getParameter("telephone");
		String detail = request.getParameter("detail");
		String fullname = request.getParameter("fullname");
        String userId = getUserId(request);

        //字段必须填写验证
        if (StringUtils.isBlank(areaId)) {
            result = false;
            message = "区域地址areaId(:" + areaId + ") 不能为空";
            return renderString(response, result, message, data);
        }
        if (StringUtils.isBlank(fullname)) {
            result = false;
            message = "请填写收货人姓名，用于收货";
            return renderString(response, result, message, data);
        }
        if (StringUtils.isBlank(telephone)) {
            result = false;
            message = "请填写手机号码，用于收货";
            return renderString(response, result, message, data);
        }
        if (!ValidateUtils.isMobile(telephone)) {
            result = false;
            message = ValidateUtils.getErrMsg();
            return renderString(response, result, message, data);
        }

        Area area = AreaUtils.getArea(areaId);
        if (area == null) {
            result = false;
            message = "区域地址(ID:" + areaId + ") 不存在";
            return renderString(response, result, message, data);
        }

        //非店内消费必须填写门牌地址
        if (!Area.SHIPPING_GROUP_STORE.equals(area.getShippingGroup()) && StringUtils.isBlank(detail)) {
            result = false;
            message = "请填写门牌地址，用于收货";
            return renderString(response, result, message, data);
        }

        //如果是店内消费，门牌地址为空
        if (Area.SHIPPING_GROUP_STORE.equals(area.getShippingGroup())) {
            detail = null;
        }

        //清除默认地址
        List<Address> addressList = addressService.findByUserId(userId);
        for (Address addr : addressList) {
            if (Global.YES.equals(addr.getIsDefault())) {
                addr.setIsDefault(Global.NO);
                addressService.save(addr);
            }
        }

        Address address = new Address();
        address.setArea(area);
        address.setTelephone(telephone);
        address.setDetail(detail);
        address.setFullname(fullname);
        address.setUser(new User(userId));
        address.setIsDefault(Global.YES);
        addressService.save(address);

        Map<String, Object> oAddress = address.toSimpleObj();
        Map<String, Object> oArea = area.toSimpleObj();
        oAddress.put("area", oArea);
        List<Map<String, Object>> oAddressList = addressService.findByUserId4SimpleObj(userId);

        result = true;
        message = "";
        data.put("addressList", oAddressList);
        data.put("address", oAddress);
        return renderString(response, result, message, data);
	}

    @RequestMapping(value="/edit/{id}")
    public String edit(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        Address address = addressService.get(id);
        if (address == null) {
            result = false;
            message = "地址(ID:" + id + ") 不存在";
            return renderString(response, result, message, data);
        }

        if (address.getArea() == null) {
            result = false;
            message = "区域地址Area不存在";
            return renderString(response, result, message, data);
        }

        if (address.getArea() == null) {
            result = false;
            message = "区域地址Area不存在";
            return renderString(response, result, message, data);
        }

        Area prefixArea = AreaUtils.getArea(Area.PREFIX_AREA_ID);
        Map<String, Object> oPrefixArea = prefixArea.toSimpleObj();
        Map<String, Object> oAddress = address.toSimpleObj();
        Map<String, Object> oArea = address.getArea().toSimpleObj();
        List<Map<String, Object>> areaChainedList = AreaUtils.findChainedList(address.getArea());
        oAddress.put("area", oArea);

        result = true;
        message = "";
        data.put("address", oAddress);
        data.put("prefixArea", oPrefixArea);
        data.put("areaChainedList", areaChainedList);
        return renderString(response, result, message, data);
    }

    /**
     * 修改收货地址，在app下单页面修改，修改的地址则为默认地址
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/edit-post/{id}")
    public String editPost(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        String areaId = request.getParameter("areaId");
        String telephone = request.getParameter("telephone");
        String fullname = request.getParameter("fullname");
        String detail = request.getParameter("detail");
        String userId = getUserId(request);

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        //字段必须填写验证
        if (StringUtils.isBlank(areaId)) {
            result = false;
            message = "区域地址areaId(:" + areaId + ") 不能为空";
            return renderString(response, result, message, data);
        }
        if (StringUtils.isBlank(fullname)) {
            result = false;
            message = "请填写收货人姓名，用于收货";
            return renderString(response, result, message, data);
        }
        if (StringUtils.isBlank(telephone)) {
            result = false;
            message = "请填写手机号码，用于收货";
            return renderString(response, result, message, data);
        }
        if (!ValidateUtils.isMobile(telephone)) {
            result = false;
            message = ValidateUtils.getErrMsg();
            return renderString(response, result, message, data);
        }

        Area area = AreaUtils.getArea(areaId);
        if (area == null) {
            result = false;
            message = "区域地址Area(ID:" + areaId + ") 不存在";
            return renderString(response, result, message, data);
        }

        Address address = addressService.get(id);
        if (address == null) {
            result = false;
            message = "地址(ID:" + address.getId() + ") 不存在";
            return renderString(response, result, message, data);
        }

        //非店内消费必须填写门牌地址
        if (!Area.SHIPPING_GROUP_STORE.equals(area.getShippingGroup()) && StringUtils.isBlank(detail)) {
            result = false;
            message = "请填写门牌地址，用于收货";
            return renderString(response, result, message, data);
        }

        //如果是店内消费，门牌地址为空
        if (Area.SHIPPING_GROUP_STORE.equals(area.getShippingGroup())) {
            detail = null;
        }

        //清除默认地址
        List<Address> addressList = addressService.findByUserId(userId);
        for (Address addr : addressList) {
            if (Global.YES.equals(addr.getIsDefault())) {
                addr.setIsDefault(Global.NO);
                addressService.save(addr);
            }
        }

        //修改的地址转为默认地址
        address.setIsDefault(Global.YES);
        address.setArea(area);
        address.setTelephone(telephone);
        address.setDetail(detail);
        address.setFullname(fullname);
        addressService.save(address);

        Map<String, Object> oAddress = address.toSimpleObj();
        Map<String, Object> oArea = area.toSimpleObj();
        oAddress.put("area", oArea);
        List<Map<String, Object>> oAddressList = addressService.findByUserId4SimpleObj(userId);

        result = true;
        message = "";
        data.put("addressList", oAddressList);
        data.put("address", oAddress);
        return renderString(response, result, message, data);
    }

    @RequestMapping(value="/set-default-address/{id}")
    public String setDefaultAddress(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);

        Address address = addressService.get(id);
        if (address == null) {
            result = false;
            message = "地址(ID:" + address.getId() + ") 不存在";
            return renderString(response, result, message, data);
        }

        if (address.getArea() == null || StringUtils.isBlank(address.getArea().getId())) {
            result = false;
            message = "区域地址不存在";
            return renderString(response, result, message, data);
        }

        //要用保存实体的方法保存，因为保存后要删除缓存
        List<Address> addressList = addressService.findByUserId(userId);
        for (Address addr : addressList) {
            addr.setIsDefault(Global.NO);
            addressService.save(addr);
        }
        address.setIsDefault(Global.YES);
        addressService.save(address);

        Map<String, Object> oAddress = address.toSimpleObj();
        Map<String, Object> oArea = address.getArea().toSimpleObj();
        oAddress.put("area", oArea);
        List<Map<String, Object>> oAddressList = addressService.findByUserId4SimpleObj(userId);

        result = true;
        message = "";
        data.put("addressList", oAddressList);
        data.put("address", oAddress);
        return renderString(response, result, message, data);
    }

    @RequestMapping(value="/list")
    public String listAddress(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        String userId = request.getParameter("userId");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oAddressList = addressService.findByUserId4SimpleObj(userId);

        result = true;
        message = "";
        data.put("addressList", oAddressList);
        return renderString(response, result, message, data);
    }

    /**
     * 删除收货地址，在app下单页面删除
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/delete/{id}")
    public String delete(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);

        Address address = addressService.get(id);
        if (address == null || address.getUser() == null
                || address.getUser().getId() == null) {
            result = false;
            message = "地址(ID:" + id + ") 不存在";
            return renderString(response, result, message, data);
        }

        if (address.getArea() == null || StringUtils.isBlank(address.getArea().getId())) {
            result = false;
            message = "区域地址不存在";
            return renderString(response, result, message, data);
        }

        address.setDelFlag(Address.DEL_FLAG_DELETE);
        addressService.save(address);

        Map<String, Object> oAddress = address.toSimpleObj();
        Map<String, Object> oArea = address.getArea().toSimpleObj();
        oAddress.put("area", oArea);
        List<Map<String, Object>> oAddressList = addressService.findByUserId4SimpleObj(userId);

        result = true;
        message = "";
        data.put("addressList", oAddressList);
        data.put("address", oAddress);
        return renderString(response, result, message, data);
    }
}
