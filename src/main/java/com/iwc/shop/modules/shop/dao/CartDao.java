/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.dao;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.shop.entity.Cart;

/**
 * 购物车DAO接口
 * @author Tony Wong
 * @version 2015-04-16
 */
@MyBatisDao
public interface CartDao extends CrudDao<Cart> {

    Cart getByUserId(Cart cart);

    Cart getByCookieId(Cart cart);

    Cart getByAppCartCookieId(Cart cart);

    void deleteByCookieId(Cart cart);
}
