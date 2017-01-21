/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.shop.dao.PreorderItemAttributeDao;
import com.iwc.shop.modules.shop.entity.PreorderItem;
import com.iwc.shop.modules.shop.entity.PreorderItemAttribute;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预购订单产品属性Service
 * @author Tony Wong
 * @version 2015-05-28
 */
@Service
@Transactional(readOnly = true)
public class PreorderItemAttributeService extends CrudService<PreorderItemAttributeDao, PreorderItemAttribute> {

    public List<PreorderItemAttribute> findByItemId(PreorderItem item) {
        PreorderItemAttribute attribute = new PreorderItemAttribute();
        attribute.setItem(item);
        return dao.findList(attribute);
    }

    public List<PreorderItemAttribute> findByItemId(String itemId) {
        PreorderItem item = new PreorderItem(itemId);
        return findByItemId(item);
    }

}
