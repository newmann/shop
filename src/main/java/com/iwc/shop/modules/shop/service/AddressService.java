/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.AddressDao;
import com.iwc.shop.modules.shop.entity.Address;
import com.iwc.shop.modules.shop.entity.Cookie;
import com.iwc.shop.modules.shop.utils.AddressUtils;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.AreaService;
import com.iwc.shop.modules.sys.utils.AreaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户地址Service
 * @author Tony Wong
 * @version 2015-04-19
 */
@Service
@Transactional(readOnly = true)
public class AddressService extends CrudService<AddressDao, Address> {

    @Autowired
    AreaService areaService;

    @Transactional(readOnly = false)
    public void save(Address address) {
        super.save(address);

        AddressUtils.removeCache(AddressUtils.CK_address_ + address.getId());
    }

    /**
     * 获得默认Address对象，包括关联的Area
     * @param userId
     * @return
     */
    public Address getDefaultByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        Address address = new Address();
        User user = new User(userId);
        address.setUser(user);
        address.setIsDefault(Global.YES);
        return dao.getDefaultByUserId(address);
    }
    /**
     * 获得默认Address对象，包括关联的Area
     * @param userId
     * @return
     */
    public Map<String, Object> getDefaultByUserId4SimpleObj(String userId) {
        Map<String, Object> oAddress = Maps.newHashMap();
        Address address =  getDefaultByUserId(userId);
        if (address != null) {
            oAddress = address.toSimpleObj();
            if (address.getArea() != null) {
                Map<String, Object> oArea = address.getArea().toSimpleObj();
                oAddress.put("area", oArea);
            }
        }
        return oAddress;
    }

    /**
     * 获得默认Address对象，包括关联的Area
     * @todo
     * @param cookieId
     * @return
     */
    public Address getDefaultByCookieId(String cookieId) {
        if (StringUtils.isBlank(cookieId)) {
            return null;
        }

        Address address = new Address();
        Cookie cookie = new Cookie(cookieId);
        Page<Address> page = new Page<Address>();
        String orderBy = "a.update_date DESC";
        page.setPageNo(1);
        page.setPageSize(1);
        page.setOrderBy(orderBy);
        address.setCookie(cookie);
        address.setIsDefault(Global.YES);
        List<Address> addressList = findPage(page, address).getList();

        if (addressList != null && !addressList.isEmpty()) {
            Address rAddress = addressList.get(0);
            Area area = AreaUtils.getArea(rAddress.getArea().getId());
            rAddress.setArea(area);
            return rAddress;
        }
        else {
            return null;
        }
    }

    public List<Address> findByUserId(String userId) {
        Address address = new Address();
        address.setUser(new User(userId));
        return dao.findList(address);
    }
    public List<Map<String, Object>> findByUserId4SimpleObj(String userId) {
        List<Map<String, Object>> oAddressList = Lists.newArrayList();
        List<Address> addressList = findByUserId(userId);
        for (Address address : addressList) {
            Map<String, Object> oAddress = address.toSimpleObj();
            Map<String, Object> oArea = Maps.newHashMap();
            if (address.getArea() != null) {
                oArea = address.getArea().toSimpleObj();
            }
            oAddress.put("area", oArea);
            oAddressList.add(oAddress);
        }
        return oAddressList;
    }
}
