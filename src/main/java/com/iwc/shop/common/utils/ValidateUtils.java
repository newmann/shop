/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**=
 * 验证工具类
 * @version 2015-07-07
 */
public class ValidateUtils {
	
	private static Logger log = LoggerFactory.getLogger(ValidateUtils.class);

    /**
     * 最新验证的错误信息
     */
    private static String errMsg = "";

	/**
	 * 手机号验证
	 *
	 * @param  str
	 * @return boolean 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		StringUtils.trim(str);
		Pattern p = null;
		Matcher m = null;
		boolean b = false;

        if (str == null || str.length() < 11) {
            errMsg = "手机号必须为11位数字";
            return false;
        }

		p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();

        if (!b) {
            errMsg = "请输入正确的手机号";
        } else {
            errMsg = "";
        }

		return b;
	}

	/**
	 * 电话号码验证
	 *
	 * @param  str
	 * @return boolean 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		StringUtils.trim(str);
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(str.length() >9)
		{   m = p1.matcher(str);
			b = m.matches();
		}else{
			m = p2.matcher(str);
			b = m.matches();
		}

        if (!b) {
            errMsg = "请输入正确的电话号码";
        } else {
            errMsg = "";
        }

		return b;
	}

	/**
	 * 密码验证
	 *
	 * @param  str
	 * @return boolean 验证通过返回true
	 */
	public static boolean isPassword(String str) {
		StringUtils.trim(str);
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		if (str == null || str.length() < 6) {
			errMsg = "密码必须为6位以上";
			b = false;
		} else {
			b = true;
			errMsg = "";
		}

		return b;
	}

	public static String getErrMsg() {
		return errMsg;
	}

}
