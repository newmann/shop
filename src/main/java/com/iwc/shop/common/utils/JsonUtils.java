package com.iwc.shop.common.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tony Wong
 * @version 2015-4-26
 */
public class JsonUtils {
    public static String toString(boolean result) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        map.put("msg", "");
        map.put("data", new HashMap<String, Object>());
        return JSON.toJSONString(map);
    }

    public static String toString(boolean result, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        map.put("message", message);
        map.put("data", new HashMap<String, Object>());
        return JSON.toJSONString(map);
    }

    public static String toString(boolean result, String message, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        map.put("message", message);
        map.put("data", data);
        return JSON.toJSONString(map);
    }

    /**
     * set response for json for html
     *
     * @param response
     */
    public static void setResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
    }
}
