/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.sys.dao;

import java.util.List;

import com.iwc.shop.common.persistence.CrudDao;
import com.iwc.shop.common.persistence.annotation.MyBatisDao;
import com.iwc.shop.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author Tony Wong
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {

    /**
     * 获得用户表的实体
     */
    public User getEntity(String id);

	/**
	 * 根据登录名称查询用户
	 * @param user
	 * @return
	 */
	public User getByLoginName(User user);

    /**
     * 根据手机号查询用户
     * @param user
     * @return
     */
    public User getByMobile(User user);

	/**
	 * 前台根据登录名称查询用户
	 * @param user
	 * @return
	 */
	public User getByLoginName2(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);

	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);

	/**
	 * 前台用户插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole4Frontend(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);



    /**=======================
     * 自建会话系统给app用
     * 判断用户是否登录的条件：user.id + user.app_login_token
     =======================*/

	/**
	 * 更新APP用户登录令牌
	 * @param user
	 * @return
	 */
	public int updateAppLoginToken(User user);

    public long isAppLoggedIn(User user);

}
