/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.PreorderItemDao;
import com.iwc.shop.modules.shop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 预购订单项Service
 * @author Tony Wong
 * @version 2015-04-19
 */
@Service
@Transactional(readOnly = true)
public class PreorderItemService extends CrudService<PreorderItemDao, PreorderItem> {

	@Autowired
	ShopProductService productService;

	@Autowired
	PreorderItemAttributeService attributeService;

	@Autowired
	ShopProductAttributeItemService attrItemService;

	@Autowired
	ShopProductAttributeItemValueService attrValueService;

	/**
	 * 获取单条数据, 包括对应的产品属性
	 * @param id
	 * @return
	 */
	public PreorderItem getWithAttribute(String id) {
		PreorderItem item = dao.get(id);
		if (item != null) {
			List<PreorderItemAttribute> attrList = attributeService.findByItemId(item);
			item.setAttributeList(attrList);
		}
		return item;
	}

	public List<PreorderItem> findByPreorderId(String preorderId) {
        Preorder preorder = new Preorder(preorderId);
		PreorderItem newItem = new PreorderItem();
		newItem.setPreorder(preorder);
		List<PreorderItem> list = findList(newItem);

		for (PreorderItem item : list) {
			List<PreorderItemAttribute> attrList = attributeService.findByItemId(item);
			item.setAttributeList(attrList);
		}

		return list;
	}
	public List<Map<String, Object>> findByPreorderId4Json(Preorder preorder) {
        List<Map<String, Object>> oPreorderItemList = Lists.newArrayList();

		PreorderItem newItem = new PreorderItem();
		newItem.setPreorder(preorder);
		List<PreorderItem> list = findList(newItem);

		for (PreorderItem item : list) {
            Map<String, Object> oPreorderItem = item.toSimpleObj();
            List<Map<String, Object>> oAttrList = Lists.newArrayList();
			String oAttrPrintNames = "";

			List<PreorderItemAttribute> attrList = attributeService.findByItemId(item);
            for (PreorderItemAttribute attr : attrList) {
                Map<String, Object> oAttr = attr.toSimpleObj();

                // set oAttrPrintNames
                if (StringUtils.isNotBlank(attr.getAttributeItemValuePrintName())) {
                    if (StringUtils.isBlank(oAttrPrintNames)) {
                        oAttrPrintNames = attr.getAttributeItemValuePrintName();
                    } else {
                        oAttrPrintNames += "、" + attr.getAttributeItemValuePrintName();
                    }
                }

                oAttrList.add(oAttr);
            }

            oPreorderItem.put("attrList", oAttrList);
            oPreorderItem.put("attrPrintNames", oAttrPrintNames);
            oPreorderItemList.add(oPreorderItem);
		}

		return oPreorderItemList;
	}

    /**
     *
     * @param preorderId
     * @param orderBy such as "a.price ASC"
     * @return
     */
    public List<PreorderItem> findByPreorderIdNoAttr(String preorderId, String orderBy) {
        Preorder preorder = new Preorder(preorderId);
        PreorderItem newItem = new PreorderItem();
        if (StringUtils.isNotBlank(orderBy)) {
            Page<PreorderItem> page = new Page<PreorderItem>();
            page.setOrderBy(orderBy);
            newItem.setPage(page);
        }
        newItem.setPreorder(preorder);
        List<PreorderItem> list = findList(newItem);
        return list;
    }
}