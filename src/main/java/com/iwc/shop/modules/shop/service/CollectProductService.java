/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.dao.CollectProductDao;
import com.iwc.shop.modules.shop.dao.CollectProductDao;
import com.iwc.shop.modules.shop.entity.CollectProduct;
import com.iwc.shop.modules.shop.entity.Coupon;
import com.iwc.shop.modules.shop.entity.CollectProduct;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.UserService;
import org.restlet.data.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 收藏商品Service
 * @author Tony Wong
 * @version 2015-07-16
 */
@Service
@Transactional(readOnly = true)
public class CollectProductService extends CrudService<CollectProductDao, CollectProduct> {

    /**
     * 查找用户的所有收藏商品
     * @param userId
     * @return
     */
    public List<CollectProduct> findByUserId(String userId) {
        CollectProduct cp = new CollectProduct();
        cp.setUser(new User(userId));
        List<CollectProduct> list = dao.findList(cp);
        for (CollectProduct collectProduct : list) {
            ShopProduct product = ShopProductUtils.getProduct(collectProduct.getProduct().getId());
            if (product != null && StringUtils.isNotBlank(product.getId()))
                collectProduct.setProduct(product);
            else
                collectProduct.setProduct(null);
        }
        return list;
    }
    /**
     * 查找用户的所有收藏商品
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findByUserId4SimpleObj(String userId) {
        List<Map<String, Object>> oList = Lists.newArrayList();
        CollectProduct cp = new CollectProduct();
        cp.setUser(new User(userId));
        List<CollectProduct> list = dao.findList(cp);
        for (CollectProduct collectProduct : list) {
            Map<String, Object> oCollectProduct = collectProduct.toSimpleObj();
            Map<String, Object> oProduct = Maps.newHashMap();
            ShopProduct product = ShopProductUtils.getProduct(collectProduct.getProduct().getId());
            if (product != null && StringUtils.isNotBlank(product.getId())) {
                oProduct = product.toSimpleObj();
            }
            oCollectProduct.put("product", oProduct);
            oList.add(oCollectProduct);
        }
        return oList;
    }

    /**
     * 用户是否收藏了该商品
     */
    public boolean hasCollected(String userId, String productId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId))
            return false;

        CollectProduct collectProduct = new CollectProduct();
        collectProduct.setProduct(new ShopProduct(productId));
        collectProduct.setUser(new User(userId));
        Map<String, Object> result = dao.hasCollected(collectProduct);
        long count = 0;
        if (result != null)
            count = Long.valueOf(result.get("count").toString());

        if (count > 0)
            return true;
        else
            return false;
    }
}
