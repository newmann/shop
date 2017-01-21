/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.common.config;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 配置客户端的支付宝，客户端的配置文件在 res/key.xml
 * 优先选用客户端的配置，当要更换客户端的支付宝配置是也可以通过后台传来的值配置客户端的支付宝
 * @author Tony Wong
 * @version 2015-08-11
 */
public class AlipayClientConfig {

    //是否使用后台配置
    public static final boolean isUsed = false;

    public static final String partner = "2088902694429102";
    public static final String seller = "15013625155";
    public static final String rsaPriKey = "MIICXAIBAAKBgQD10qhOdJdiVuNBDpyGEFiHvM+A6ym4LDOC8+0TNoFfiiC9zgIxx+vXCpAKXmHzY8JNo+NPDxN2dj4HT3PtlwxmB4+YYS0Pp5ycwLZNZF+0/MIXiyDFpzAcqCyET2A1iaQSXwx3QJl0Y+5nhdzlgXnKyknb1gVrutuJFK0eg6kpMwIDAQABAoGAXe0k0/1mjnOml4gOtIbwXMT/AEJaiX2SHRo43pt36Vps0jkxy3WZHmxxxxRzFr1qU6IoS1/2TtA1OTPZltGdMWs8kvvbeElDW3Yq2yW1kvZ8+KK8oHQ5rZHlj//sNHmqvd76poJ0rKlEfavk2lKfZGMtl6E6vNJcwcTyk62lpikCQQD/L5UAS84c/Rs7nCYkkGP4e8SIqnQYPSP0hPaygXKDsDCRJwtLuDZTNcuo0Paje6xQqEqSAhWgxlQs8xQh5y7tAkEA9pttqjy4ybwXyS+G4b+rBXtR+uTxN1WrLu9klPxntexH2v89l9YZEWotQ7vPtkx64yfSJ4RiOcI7ydc2FXqUnwJALCnB8bEs1JuBMQnPGBjdFE3nL3fAa8l5BYJVZ4M7nErLvPMy40HYNQJkYqz3YkevBK57aVVvBMoOG1FeNJfb6QJBAKBjdm/CDm0maFHxF0i73/8wtaD4Reo3TZ65FDHR96HaiuFkICsglC0/YTEsdWubUlFrFMOe1kO6kCg2UHanwwECQBhBEhyB2puAsVLGj7z9rBlhsP6C+XmbX4DCSQV/Ww/A0oDVmMIYxxizJUm4FZUrsCLKqgRhQlHF2gv9N5mSoxU=";
    public static final String rsaPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static final String notifyURL = "http://www.ygcr8.com/app/alipay/notify";

    public static Map<String, Object> getConfig() {
        Map<String, Object> config = Maps.newHashMap();
        config.put("partner", partner);
        config.put("seller", seller);
        config.put("rsaPriKey", rsaPriKey);
        config.put("rsaPubKey", rsaPubKey);
        config.put("notifyURL", notifyURL);
        return config;
    }
}
