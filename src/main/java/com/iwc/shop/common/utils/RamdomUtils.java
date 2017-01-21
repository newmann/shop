/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 生成随机数
 * @author Tony Wong
 * @version 2015-07-13
 */
public class RamdomUtils {

    private static Logger log = LoggerFactory.getLogger(RamdomUtils.class);

    public static final String NUMBER_CODE = "0123456789";

    public static final String NUMBER_CODE_SEZI = "123456";

    public static final String NUMBER_CODE_SEZI_CHEAT = "00112233456";

    public static final String NUMBER_CODE_SEZI_CHEAT1 = "000011110000011110000111100001111000022000001111000002200001111220000011111110000022111100001111002200001111000011110000";

    /**
     * 生成注册码
     */
    public static String genRegisterCode() {
        return genNumber(4);
    }

	public static String genNumber(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<len; i++) {
            int index = new Random().nextInt(NUMBER_CODE.length());
            sb.append(NUMBER_CODE.charAt(index));
        }
        return sb.toString();
	}

    /**
     * 生成色子随机数
     */
    public static String genSeziNum(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<len; i++) {
            int index = new Random().nextInt(NUMBER_CODE_SEZI.length());
            sb.append(NUMBER_CODE_SEZI_CHEAT.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 欺骗生成色子随机数
     */
    public static String genSeziNumCheat(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<len; i++) {
            int index = new Random().nextInt(NUMBER_CODE_SEZI_CHEAT.length());
            sb.append(NUMBER_CODE_SEZI_CHEAT.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 欺骗生成色子随机数1
     */
    public static String genSeziNumCheat1(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<len; i++) {
            int index = new Random().nextInt(NUMBER_CODE_SEZI_CHEAT1.length());
            sb.append(NUMBER_CODE_SEZI_CHEAT1.charAt(index));
        }
        return sb.toString();
    }

}
