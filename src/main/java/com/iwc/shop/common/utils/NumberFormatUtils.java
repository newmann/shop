/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**=
 * @author Tony Wong
 * @version 2015-07-07
 */
public class NumberFormatUtils {
	
	private static Logger log = LoggerFactory.getLogger(NumberFormatUtils.class);

	public static String formatPrice(Object price){
		DecimalFormat df = new DecimalFormat("#.0");
		return df.format(price);
	}

}
