/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.utils.CacheUtils;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.common.utils.ValidateUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.shop.entity.Cart;
import com.iwc.shop.modules.shop.entity.CartItem;
import com.iwc.shop.modules.shop.service.CartItemService;
import com.iwc.shop.modules.shop.service.CartService;
import com.iwc.shop.modules.shop.service.CouponUserService;
import com.iwc.shop.modules.shop.utils.CookieUtils;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.security.FormAuthenticationFilter;
import com.iwc.shop.modules.sys.security.UsernamePasswordToken;
import com.iwc.shop.modules.sys.service.ResultCode;
import com.iwc.shop.modules.sys.service.SmsService;
import com.iwc.shop.modules.sys.service.SystemService;
import com.iwc.shop.modules.sys.service.UserService;
import com.iwc.shop.modules.sys.utils.SmsUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 在线商店的用户登录Controller
 * @author Newmann
 * @version 2017-1-23
 */
@Controller
@RequestMapping("/shop/user")
public class ShopUserController extends BaseController {
    private static final String VIEW_PATH = "modules/shop/user/";

	@Autowired
	UserService userService;

    @Autowired
    SmsService smsService;

    @Autowired
    CouponUserService couponUserService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @RequiresPermissions("sys:user:view")
	@RequestMapping(value = "")
	public String index() {
		return VIEW_PATH + "index";
	}

    @RequestMapping("/get-user")
    public String getUser(HttpServletRequest request, HttpServletResponse response) {
        boolean result;
//        int resultCode;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        if (isLoggedIn()) {
            result = true;
//            resultCode = ResultCode.Success;
            message = "用户信息";
            String userId = request.getParameter("userId");
            User user = userService.get(userId);
            Map<String, Object> oUser = user.toSimpleObj();
            data.put("user", oUser);
        } else {
            result = false;
//            resultCode = ResultCode.Failure;
            message = "当前没有登录用户";
        }

        return renderString(response, result,  message, data);
    }


    /**
     * 注册 - 提交手机号码
     */
    @RequestMapping(value = "/register-step1-post")
    public String registerStep1(HttpServletRequest request, HttpServletResponse response) {

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        data.put("userLoginName", username);

        if (ValidateUtils.isMobile(username)) {
            result = true;
            message = "";
        } else {
            result = false;
            message = ValidateUtils.getErrMsg();
        }
        if(result){
            User user = userService.getByLoginName2(username);
            if (user != null && StringUtils.isNotBlank(user.getId())) {
                result = false;
                message = "电话号码已存在";
            } else {
                //发送手机验证码
                SmsUtils.sendRegisterCode(username);
                result = true;
                message = "手机验证码已发送";

            }
        }
        return renderString(response, result, message, data);
    }

	/**
	 * 注册 - 提交手机号码、密码
	 */
	@RequestMapping(value = "/register-step2-post")
	public String registerStep2(HttpServletRequest request, HttpServletResponse response) {

		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        String password = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM);
        data.put("userLoginName", username);
        data.put("userPassword", password);

		if (ValidateUtils.isMobile(username) && ValidateUtils.isPassword(password)) {
			result = true;
			message = "";
		} else {
			result = false;
			message = ValidateUtils.getErrMsg();
		}
        if (result) {
            User user = userService.getByLoginName2(username);
            if (user != null && StringUtils.isNotBlank(user.getId())) {
                result = false;
                message = "电话号码已存在";
            }
        }
		return renderString(response, result, message, data);
	}

	/**
	 * 注册 - 提交手机号码、密码、验证码
	 */
	@RequestMapping(value = "/register-step3-post", method = RequestMethod.POST)
	public String registerStep3Post(HttpServletRequest request, HttpServletResponse response) {

		boolean result;
		String message;
		Map<String, Object> data = Maps.newHashMap();
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        String password = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM);
        String code = request.getParameter("code");

        if (!ValidateUtils.isMobile(username) || !ValidateUtils.isPassword(password)) {
            result = false;
            message = "提交的手机号码或密码不符合规则";
            return renderString(response, result, message, data);
        }

        User u = userService.getByLoginName2(username);
        if (u != null && StringUtils.isNotBlank(u.getId())) {
            result = false;
            message = "电话号码已存在";
            return renderString(response, result, message, data);
        }

        //比较验证码
        if (smsService.checkRegisterCode(username, code)) {
            //保存用户
            User user = new User();
            user.setLoginName(username);
            user.setPassword(SystemService.entryptPassword(password));
            user.setMobile(username);
            user.setRemarks("前台用户");
            user.setRegisterFrom(User.REGISTER_FROM_WEB);
            userService.saveFrontendUser(user);

            //用户自动登录
//            String userId = user.getId();
//            UsernamePasswordToken token = new UsernamePasswordToken();
//            token.setUsername(username);
//            token.setPassword(password.toCharArray());
//            token.setRememberMe(true);
//            try {
//                SecurityUtils.getSubject().login(token);
//            }
//            catch (AuthenticationException e) {
//                logger.debug("/app/user/register-step3-post throw AuthenticationException: {}", e.getMessage());
//                result = false;
//                message = "用户名或密码错误";
//                return renderString(response, result, message, data);
//            }
//            catch (Exception e) {
//                logger.debug("/app/user/register-step3-post throw Exception: {}",  e.getMessage());
//                result = false;
//                message = e.getMessage();
//                return renderString(response, result, message, data);
//            }
//            //更新app登录令牌
//            user.setAppLoginToken(userService.genAppLoginToken());
//            userService.updateAppLoginToken(user);
//            User loginUser = _login(username, password);
            User loginUser = userService.getByLoginName2(username);
            //给新注册用户发送优惠券
//            if (!STOP_COUPON_BUY_ONE_SEND_ONE) {
//                couponUserService.send4NewUser(loginUser.getId());
//            }

            //转移购物车项给用户
            String cookieId = CookieUtils.getCookieId(request, response);
            if (StringUtils.isNotBlank(cookieId)) {
                List<CartItem> cartItemList = cartItemService.findByCookieId(cookieId, null);
                if (cartItemList != null && !cartItemList.isEmpty()) {
                    //创建用户购物车
                    Cart cart = new Cart();
                    cart.setUser(u);
                    cartService.save(cart);
                    //把产品转给该用户
                    for (CartItem cartItem : cartItemList) {
                        cartItem.setUserId(loginUser.getId());
                        cartItemService.save(cartItem);
                    }
                }
            }


            Map<String, Object> oUser = loginUser.toSimpleObj();

            result = true;
            message = "恭喜, 您已经成功注册了";
            data.put("user", oUser);

        } else {
            result = false;
            message = "请输入正确的验证码";
        }

        return renderString(response, result, message, data);
	}
    /**
     * 注册 - 一次性提交手机号码、密码、验证码
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerStep3Post(HttpServletRequest request, HttpServletResponse response, Model model) {

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        String password = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM);
        String code = request.getParameter("code");

        if (!ValidateUtils.isMobile(username) || !ValidateUtils.isPassword(password)) {
            result = false;
            message = "提交的手机号码或密码不符合规则";
            return renderString(response, result, message, data);
        }

        User u = userService.getByLoginName2(username);
        if (u != null && StringUtils.isNotBlank(u.getId())) {
            result = false;
            message = "电话号码已存在";
            return renderString(response, result, message, data);
        }

        //比较验证码
        if (smsService.checkRegisterCode(username, code)) {
            //保存用户
            User user = new User();
            user.setLoginName(username);
            user.setPassword(SystemService.entryptPassword(password));
            user.setMobile(username);
            user.setRemarks("前台用户");
            user.setRegisterFrom(User.REGISTER_FROM_WEB);
            userService.saveFrontendUser(user);

            //用户自动登录

            User loginUser = userService.getByLoginName2(username);
            //给新注册用户发送优惠券
//            if (!STOP_COUPON_BUY_ONE_SEND_ONE) {
//                couponUserService.send4NewUser(loginUser.getId());
//            }

            //转移购物车项给用户
            String cookieId = CookieUtils.getCookieId(request, response);
            if (StringUtils.isNotBlank(cookieId)) {
                List<CartItem> cartItemList = cartItemService.findByCookieId(cookieId, null);
                if (cartItemList != null && !cartItemList.isEmpty()) {
                    //创建用户购物车
                    Cart cart = new Cart();
                    cart.setUser(u);
                    cartService.save(cart);
                    //把产品转给该用户
                    for (CartItem cartItem : cartItemList) {
                        cartItem.setUserId(loginUser.getId());
                        cartItemService.save(cartItem);
                    }
                }
            }


            Map<String, Object> oUser = loginUser.toSimpleObj();

            result = true;
            message = "恭喜, 您已经成功注册了";
            data.put("user", oUser);

        } else {
            result = false;
            message = "请输入正确的验证码";
        }

        return "modules/shop/user/register";
    }

    /**
     * 重置密码 - 提交手机号码
     */
    @RequestMapping("/forget-password-step1-post")
    public String forgetPasswordStep1Post(HttpServletRequest request, HttpServletResponse response) {

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        String mobile = request.getParameter("mobile");

        if (!ValidateUtils.isMobile(mobile)) {
            result = false;
            message = ValidateUtils.getErrMsg();
            return renderString(response, result, message, data);
        }

        User user = userService.getByMobile(mobile);
        if (user == null) {
            result = false;
            message = "电话号码不存在";
            return renderString(response, result, message, data);
        }

        //发送重置密码的验证码
        SmsUtils.sendForgetPasswordCode(mobile);

        result = true;
        message = "";
        data.put("mobile", mobile);
        return renderString(response, result, message, data);
    }

    /**
     * 重置密码 - 提交手机号码和密码
     */
    @RequestMapping("/forget-password-step2-post")
    public String forgetPasswordStep2Post(HttpServletRequest request, HttpServletResponse response) {

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");

        if (!ValidateUtils.isMobile(mobile)) {
            result = false;
            message = ValidateUtils.getErrMsg();
            return renderString(response, result, message, data);
        }

        if (!ValidateUtils.isPassword(mobile)) {
            result = false;
            message = ValidateUtils.getErrMsg();
            return renderString(response, result, message, data);
        }

        User user = userService.getByMobile(mobile);
        if (user == null) {
            result = false;
            message = "电话号码不存在";
            return renderString(response, result, message, data);
        }

        result = true;
        message = "";
        data.put("mobile", mobile);
        data.put("password", password);
        return renderString(response, result, message, data);
    }

    /**
     * 重置密码 - 提交手机号码、密码、验证码
     */
    @RequestMapping("/forget-password-step3-post")
    public String forgetPasswordStep3Post(HttpServletRequest request, HttpServletResponse response) {

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");
        String code = request.getParameter("code");

        if (!ValidateUtils.isMobile(mobile)) {
            result = false;
            message = ValidateUtils.getErrMsg();
            return renderString(response, result, message, data);
        }

        if (!ValidateUtils.isPassword(mobile)) {
            result = false;
            message = ValidateUtils.getErrMsg();
            return renderString(response, result, message, data);
        }

        User user = userService.getByMobile(mobile);

        if (user == null) {
            result = false;
            message = "电话号码不存在";
            return renderString(response, result, message, data);
        }

        //比较验证码
        if (smsService.checkForgetPasswordCode(mobile, code)) {
            //保存用户新密码
            user.setPassword(SystemService.entryptPassword(password));
            userService.save(user);

            //用户自动登录
//            String userId = user.getId();
//            UsernamePasswordToken token = new UsernamePasswordToken();
//            token.setUsername(user.getLoginName());
//            token.setPassword(password.toCharArray());
//            token.setRememberMe(true);
//            try {
//                SecurityUtils.getSubject().login(token);
//            }
//            catch (AuthenticationException e) {
//                logger.debug("/app/user/forget-password-step3-post throw AuthenticationException: {}", e.getMessage());
//                result = false;
//                message = "用户名或密码错误";
//                return renderString(response, result, message, data);
//            }
//            catch (Exception e) {
//                logger.debug("/app/user/forget-password-step3-post throw Exception: {}", e.getMessage());
//                result = false;
//                message = e.getMessage();
//                return renderString(response, result, message, data);
//            }
//            //更新app登录令牌
//            user.setAppLoginToken(userService.genAppLoginToken());
//            userService.updateAppLoginToken(user);

            User loginUser = userService.getByLoginName2(mobile);
            //转移购物车项给用户
            String userId = loginUser.getId();
            String cookieId = CookieUtils.getCookieId(request, response);
            if (StringUtils.isNotBlank(cookieId)) {
                List<CartItem> cartItemList = cartItemService.findByCookieId(cookieId, null);
                if (cartItemList != null && !cartItemList.isEmpty()) {
                    Cart cart = cartService.getByUserId(userId);
                    if (cart != null) { //清空用户的购物车项
                        cartItemService.clearByUserId(userId);
                    } else { //创建用户购物车
                        cart = new Cart();
                        cart.setUser(user);
                        cartService.save(cart);
                    }
                    //把产品转给该用户
                    for (CartItem cartItem : cartItemList) {
                        cartItem.setUserId(userId);
                        cartItemService.save(cartItem);
                    }
                }
            }


            result = true;
            message = "";
            Map<String, Object> oUser = loginUser.toSimpleObj();
            data.put("user", oUser);
            data.put("mobile", mobile);
            data.put("password", password);
        } else {
            result = false;
            message = "请输入正确的验证码";
        }

        return renderString(response, result, message, data);
    }


}
