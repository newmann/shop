/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.sys.service;

import com.iwc.shop.common.security.Cryptos;
import com.iwc.shop.common.service.CrudService;
import com.iwc.shop.modules.sys.dao.UserDao;
import com.iwc.shop.modules.sys.entity.Office;
import com.iwc.shop.modules.sys.entity.Role;
import com.iwc.shop.modules.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service
 * @author Tony Wong
 * @version 2015-06-16
 */
@Service
@Transactional(readOnly = true)
public class UserService extends CrudService<UserDao, User> {

    public User getByMobile(String mobile) {
        if (StringUtils.isBlank(mobile))
            return null;

        User user = new User();
        user.setMobile(mobile);
        return dao.getByMobile(user);
    }

	public User getByLoginName2(String loginName) {
		User user = new User();
		user.setLoginName(loginName);
		return dao.getByLoginName2(user);
	}

	/**
	 * 保存前台用户, 为了权限分配，该用户的company_id, office_id必须为Office.ID_FRONTEND_USER
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveFrontendUser(User user) {
		Office company = new Office(Office.ID_FRONTEND_OFFICE);
		Office office = new Office(Office.ID_FRONTEND_OFFICE);
		user.setCompany(company);
		user.setOffice(office);
		save(user);

		user.setRole(new Role(Role.ID_FRONTEND_ROLE));
		dao.insertUserRole4Frontend(user);
	}



    /**=======================
     * 自建会话系统给app用
     * 判断用户是否登录的条件：user.id + user.app_login_token
     =======================*/

    /**
     * 生成APP用户登录令牌（32位）
     */
    public String genAppLoginToken() {
        String token = Cryptos.generateAesKeyString();
        if (token.length() > 32) {
            token = token.substring(0, 31);
        }
        return token;
    }

    /**
     * 更新APP用户登录令牌
     */
    @Transactional(readOnly = false)
    public void updateAppLoginToken(String userId, String token) {
        //remove cache
        User user = new User(userId);
        user.setAppLoginToken(token);
        dao.updateAppLoginToken(user);
    }

    /**
     * 更新APP用户登录令牌
     */
    @Transactional(readOnly = false)
    public void updateAppLoginToken(User user) {
        //remove cache
        dao.updateAppLoginToken(user);
    }

    public boolean isAppLoggedIn(String userId, String token) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)) {
            User user = new User(userId);
            user.setAppLoginToken(token);
            long count = dao.isAppLoggedIn(user);
            if (count > 0)
                return true;
            else
                return false;
        } else {
            return false;
        }
    }
}