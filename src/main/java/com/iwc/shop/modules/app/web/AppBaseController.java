/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.beanvalidator.BeanValidators;
import com.iwc.shop.common.mapper.JsonMapper;
import com.iwc.shop.common.utils.DateUtils;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.SystemService;
import com.iwc.shop.modules.sys.service.UserService;
import com.iwc.shop.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.iwc.shop.modules.sys.service.ResultCode; //子类会使用

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * APP控制器支持类
 * @author Tony Wong
 * @version 2015-07-12
 */
public abstract class AppBaseController {

    /**
     * 是否暂停下单
     */
    protected boolean STOP_ORDER = false;
    protected String STOP_ORDER_LABEL = "亲~今天是月光茶人年会日，在APP上暂时不能下单，请明天再来";

    //总是提交客户端类型参数，标明是什么类型的客户端
    protected final String kTerminalTypeName = "_terminal-type";
    protected final String kTerminalTypeValue_ios = "ios";
    protected final String kTerminalTypeValue_android = "android";
    protected final String kTerminalTypeName_apicloud = "apicloud";

    /**
     * 是否暂停优惠劵
     */
    //买一送一优惠劵
    protected boolean STOP_COUPON_BUY_ONE_SEND_ONE = true;

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * APP授权码
     */
    protected final String appAuthToken = "newmamm-d8exd2ae8_0a218t3c7a3a_f9g4af7wd9bb-byl";

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	@Autowired
	private UserService userService;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}
	
	/**
	 * 添加Model消息
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}

	/**
	 * 客户端返回JSON字符串
	 */
	protected String renderString(HttpServletResponse response, boolean result, String message, Object data) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
		return renderString(response, JsonMapper.toJsonString(map), "application/json");
	}

    /**
     * 客户端返回JSON字符串
     */
    protected String renderString(HttpServletResponse response, boolean result, int resultCode, String message, Object data) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("result", result);
        map.put("resultCode", resultCode);
        map.put("message", message);
        map.put("data", data);
        return renderString(response, JsonMapper.toJsonString(map), "application/json");
    }
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {  
        return "error/400";
    }
	
	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({AuthenticationException.class})
    public String authenticationException() {  
        return "error/403";
    }
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}

    /**
     * 获取当前用户ID, 由app传userId过来,
	 * 先判断isLoggedIn(userId), 再调用该方法
     */
    protected String getUserId(HttpServletRequest request){
        return request.getParameter("userId");
    }

	/**
	 * 获取当前用户ID, 由app传userId过来
	 */
	protected User getUser(HttpServletRequest request){
		String userId = getUserId(request);
		return userService.get(userId);
	}

    /**
     * 根据APP传过来的授权码来判断app是否有权限访问
     */
    protected boolean isValidApp(HttpServletRequest request){
        //开发开源的ios时暂时把这个验证屏蔽
        return true;
        /*
        String appAuthToken = request.getParameter("appAuthToken");
        if (StringUtils.isNotBlank(appAuthToken) && appAuthToken.equals(this.appAuthToken)) {
            return true;
        } else {
            return false;
        }
        */
    }

    /**
     * 获取APP传过来的临时购物车唯一标记
     */
    protected String getAppCartCookieId(HttpServletRequest request){
        return request.getParameter("appCartCookieId");
    }

	/**
	 * 返回没有登录的JSON字符串
	 */
    protected String renderNotLoggedIn(HttpServletResponse response) {
        boolean result = false;
        int resultCode = ResultCode.UserNotLogedin;
        String message = "亲~您还没登录";
        Map<String, Object> data = Maps.newHashMap();
        return renderString(response, result, resultCode, message, data);
    }

    /**
     * 返回没有授权码的JSON字符串
     */
    protected String renderInvalidApp(HttpServletResponse response) {
        boolean result = false;
        String message = "亲~您还没获得授权，请更新APP后访问";
        Map<String, Object> data = Maps.newHashMap();
        return renderString(response, result, message, data);
    }


    /*===========================
     * 注意：因为app端没有用cookie来做session，shiro的登出（SecurityUtils.getSubject().logout();）不起作用，
     *      当改密码再登录时还是会引用之前的密码而导致登录失败。
     *      因为对shiro还不熟悉，暂时用加密再比较密码的方法来进行登录。
     * 登录判断：
     *      用户登录要用 user.loginName + user.password, 登录后生成新的 user.appLoginToken
     *      判断用户是否登录的session：user.id + user.appLoginToken
     * 登录后：重新生成appLoginToken，实现单点登录，手机掉了只要再次登录，掉了的手机就不能登录了
     =================*/
    /**
     * 如果登录成功返回User，否则返回null
     * @return
     */
    protected User _login(String loginName, String password){
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password))
            return null;

        User nUser = new User();
        nUser.setLoginName(loginName);
        User user = userService.getByLoginName2(loginName);

        if (user != null && SystemService.validatePassword(password, user.getPassword())) {
            //更新appLoginToken
            user.setAppLoginToken(userService.genAppLoginToken());
            userService.updateAppLoginToken(user);
            return user;
        }

        return null;
    }

    /**
     * 登出
     * @return
     */
    protected boolean _logout(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String token = request.getParameter("appLoginToken");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)) {
            User user = userService.get(userId);
            if (user.getAppLoginToken().equals(token)) {
                //更新appLoginToken
                user.setAppLoginToken(userService.genAppLoginToken());
                userService.updateAppLoginToken(user);
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    /**
     * 用户是否已经登录, 由app传userId和appLoginToken过来
     * @return
     */
    protected boolean isLoggedIn(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String token = request.getParameter("appLoginToken");

        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)
                && userService.isAppLoggedIn(userId, token)) {
            return true;
        } else {
            return false;
        }
    }
}
