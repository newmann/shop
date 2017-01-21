/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.dao;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.shop.entity.CartItem;

import java.util.List;
import java.util.Map;

/**
 * 购物车项DAO接口
 * @author Tony Wong
 * @version 2015-04-16
 */
@MyBatisDao
public interface CartItemDao extends CrudDao<CartItem> {

    List<CartItem> findByCartIdProductId(CartItem item);

    List<CartItem> findByUserId(CartItem item);

    List<CartItem> findByCookieId(CartItem item);

    List<CartItem> findByAppCartCookieId(CartItem item);

    /**
     * @todo
     * @param item
     * @return
     */
    CartItem getByCartIdProductId(CartItem item);

    Map<String, Object> countByUserId(CartItem item);

    Map<String, Object> countByCookieId(CartItem item);

    Map<String, Object> countByAppCartCookieId(CartItem item);

    void deleteByCookieId(CartItem item);

    void clearByUserId(CartItem item);
}
