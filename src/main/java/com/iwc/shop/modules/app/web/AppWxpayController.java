/**
 * Copyright &copy; 2015 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.entity.Order;
import com.iwc.shop.modules.shop.entity.OrderStatus;
import com.iwc.shop.modules.shop.service.OrderService;
import com.iwc.shop.modules.shop.service.OrderStatusService;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.UserService;
import com.wxpay.util.ConfigUtil;
import com.wxpay.util.PayCommonUtil;
import com.wxpay.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * 微信支付Controller
 * @author Tony Wong
 * @version 2015-07-14
 */
@Controller
@RequestMapping("/app/wxpay")
public class AppWxpayController extends AppBaseController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderStatusService orderStatusService;

    @Autowired
    UserService userService;

//    /**
//     * 手机支付后，微信支付服务器通知
//     */
//    @RequestMapping(value="/notify")
//    public String notify(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> params = request.getParameterMap();
//        String return_code = request.getParameter("return_code");
//        String return_msg = request.getParameter("return_msg");
////        logger.info("微信回调信息params: {}" + params);
////        logger.info("微信回调信息return_code: {}" + return_code);
////        logger.info("微信回调信息return_msg: {}" + return_msg);
//        System.out.println("微信回调信息params:" + params);
//        System.out.println("微信回调信息return_code:" + return_code);
//        System.out.println("微信回调信息return_msg:" + return_msg);
//
//
//        try {
//            response.reset();
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().print("fail");
//            return null;
//        } catch (IOException e) {
//            return null;
//        }
//    }

    /**
     * 手机支付后，手机回调通知
     * 因为获取不到微信支付回调通知的参数，还不知道什么原因
     * 先用app回调替换
     * @todo
     */
//    @RequestMapping(value="/app-notify")
//    public String appNotify(HttpServletRequest request, HttpServletResponse response) {
//        if (!isValidApp(request)) {
//            return renderInvalidApp(response);
//        }
//
//        boolean result;
//        String message;
//        Map<String, Object> data = Maps.newHashMap();
//
//        String apiKey = request.getParameter("apiKey");
//        String mchId = request.getParameter("mchId");
//        String partnerKey = request.getParameter("partnerKey");
//        String transactionId = request.getParameter("transactionId");
//        String orderId = request.getParameter("orderId");
//
//        if (!WxpayConfig.apiKey.equals(apiKey)
//                || !WxpayConfig.mchId.equals(mchId)
//                || !WxpayConfig.partnerKey.equals(partnerKey)) {
//            result = false;
//            message = "微信支付失败，微信参数有误，请联系月光茶人技术人员";
//            return renderString(response, result, message, data);
//        }
//
//        Order order = orderService.get(orderId);
//        if (StringUtils.isBlank(orderId) || order == null) {
//            result = false;
//            message = "微信支付失败，订单(ID:" + orderId + ")不存在，请联系月光茶人技术人员";
//            return renderString(response, result, message, data);
//        }
//
//        if (!Global.YES.equals(order.getHasPaid())) {
//            //更改在线支付的订单状态, 设置订单已支付
//            OrderStatus orderStatus = new OrderStatus();
//            orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
//            orderStatus.setStatus(OrderStatus.STATUS_OP_PAID);
//            orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_PAID);
//            orderStatus.setLabel(OrderStatus.LABEL_OP_PAID);
//            orderStatus.setPendingLabel(OrderStatus.LABEL_OP_PAID_PENDING);
//            orderStatus.setOrder(order);
//            orderStatusService.save(orderStatus);
//
//            order.setHasPaid(Global.YES);
//            order.setOrderStatus(orderStatus);
//            order.setOpTransactionId(transactionId);
//            order.setStatusUnion(orderStatus.getStatusUnion());
//            orderService.save(order);
//        }
//
//        result = true;
//        message = "";
//        return renderString(response, result, message, data);
//    }

	/**
	 * 手机支付后，微信支付服务器返回的通知
     * @todo 以后有时间再进行签名验证
     *  微信返回的参数如下：
     *      transaction_id=1000940483201509010763452148
     *      nonce_str=f22e4747da1aa27e363d86d40ff442fe
     *      bank_type=CFT
     *      openid=oeCjxwIONdD3eUNwsCd7YTerE3HU
     *      sign=B2992D87D1D1F20BC29DE9A798C6F2CF （记住要比较签名）
     *      fee_type=CNY
     *      mch_id=1260755101
     *      cash_fee=1
     *      out_trade_no=d7861c86602f443a8dcf9cf3d2e6d51d
     *      appid=wxe4f2622e8f6bec27
     *      total_fee=1 (单位为分)
     *      trade_type=APP
     *      result_code=SUCCESS
     *      time_end=20150901175310
     *      is_subscribe=N
     *      return_code=SUCCESS
     *      ---返回给微信的字符串：<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[]]></return_msg></xml>
     */
	@RequestMapping(value="/notify")
    public String notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("~~~~~~~~~~~~~~~~~~微信支付，服务器返回的通知>>>开始>>>");

        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();

        //微信返回的参数
        String result = new String(outSteam.toByteArray(), "utf-8");//获取微信调用我们notify_url的返回信息
        Map<Object, Object> map = XMLUtil.doXMLParse(result);
        for(Object keyValue : map.keySet()){
            logger.info(keyValue + "=" + map.get(keyValue));
        }
        String resultCode = String.valueOf(map.get("result_code"));
        String transactionId = String.valueOf(map.get("transaction_id"));
        String orderId = String.valueOf(map.get("out_trade_no"));
        String appid = String.valueOf(map.get("appid"));
        String mchId = String.valueOf(map.get("mch_id"));
        String cashFee = String.valueOf(map.get("cash_fee"));
        Float totalPrice = Float.valueOf(cashFee) * 0.01f;

        String myReturnCode;
        String myReturnMsg;
        String myReturnErr = "月光茶人的业务逻辑验证失败";

        if (resultCode.equalsIgnoreCase("SUCCESS")) {
            logger.info("---付款成功---");

            //============== 我的业务逻辑 ==========================//
            //验证微信传过来的参数
            Order order = orderService.get(orderId);
            if (StringUtils.isBlank(orderId) || order == null) {
                myReturnCode = "FAIL";
                logger.info("微信支付失败，订单(ID:" + orderId + ")不存在，请联系月光茶人技术人员");
                return writeToWxpay(myReturnCode, myReturnErr, response);
            }
            if (StringUtils.isBlank(cashFee) || !totalPrice.equals(order.getTotalPrice())) {
                myReturnCode = "FAIL";
                logger.info("微信支付失败，订单金额(订单ID:" + orderId + "，金额:" + totalPrice + ")不正确，请联系月光茶人技术人员");
                return writeToWxpay(myReturnCode, myReturnErr, response);
            }
            if (StringUtils.isBlank(appid) || !appid.equals(ConfigUtil.APPID)) {
                myReturnCode = "FAIL";
                logger.info("微信支付失败，配置不正确(APPID:" + appid + ")，请联系月光茶人技术人员");
                return writeToWxpay(myReturnCode, myReturnErr, response);
            }
            if (StringUtils.isBlank(mchId) || !mchId.equals(ConfigUtil.MCH_ID)) {
                myReturnCode = "FAIL";
                logger.info("微信支付失败，配置不正确(MCH_ID:" + mchId + ")，请联系月光茶人技术人员");
                return writeToWxpay(myReturnCode, myReturnErr, response);
            }
            if (StringUtils.isBlank(appid) || !appid.equals(ConfigUtil.APPID)) {
                myReturnCode = "FAIL";
                logger.info("微信支付失败，配置不正确(APPID:" + orderId + ")，请联系月光茶人技术人员");
                return writeToWxpay(myReturnCode, myReturnErr, response);
            }

            //更改在线支付的订单状态, 设置订单已支付
            if (!Global.YES.equals(order.getHasPaid())) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
                orderStatus.setStatus(OrderStatus.STATUS_OP_PAID);
                orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_PAID);
                orderStatus.setLabel(OrderStatus.LABEL_OP_PAID);
                orderStatus.setPendingLabel(OrderStatus.LABEL_OP_PAID_PENDING);
                orderStatus.setOrder(order);
                orderStatusService.save(orderStatus);

                order.setHasPaid(Global.YES);
                order.setPaidDate(new Date());
                order.setOrderStatus(orderStatus);
                order.setOpTransactionId(transactionId);
                order.setStatusUnion(orderStatus.getStatusUnion());
                orderService.save(order);

                //保存最近的支付类型
                if (order.getUser() != null && StringUtils.isNotBlank(order.getUser().getId())) {
                    User user = userService.get(order.getUser().getId());
                    if (user != null) {
                        user.setLatestPayType(order.getPayType());
                        userService.save(user);
                    }
                }

                logger.info("成功更改订单状态(orderId:" + orderId + ", statusUnion:" + orderStatus.getStatusUnion() + ")");
            }

            myReturnCode = "SUCCESS";
            myReturnMsg = "";
        } else {
            myReturnCode = "FAIL";
            myReturnMsg = "微信支付端返回错误，微信自己返回的result_code=SUCCESS";
        }

        return writeToWxpay(myReturnCode, myReturnMsg, response);
    }

    private String writeToWxpay(String myReturnCode, String myReturnMsg, HttpServletResponse response) {
        response.reset();
        response.setCharacterEncoding("utf-8");

        try {
            response.getWriter().write(PayCommonUtil.setXML(myReturnCode, myReturnMsg)); //告诉微信服务器，我收到信息了
        } catch (IOException e) {
        }

        logger.info("---返回给微信的字符串：" + PayCommonUtil.setXML(myReturnCode, myReturnMsg));
        logger.info("~~~~~~~~~~~~~~~~~~微信支付，服务器返回的通知<<<结束<<<");

        return null;
    }
    
//	public String notify(HttpServletRequest request, HttpServletResponse response) {
//        //微信回调可以访问这个地址，但是获取不到微信支付回调通知的参数，还不知道什么原因
//        //先用app回调替换
//        try {
//            response.reset();
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().print("Success");
//            return null;
//        } catch (IOException e) {
//            return null;
//        }

        ///////////////
//        //得到request对象中用来封装数据的Map集合
//        Map<String,String[]> map = request.getParameterMap();
//        for(Map.Entry<String, String[]> me : map.entrySet()){
//            String name = me.getKey();
//            String [] v = me.getValue();
//            System.out.println(name+"="+v[0]);
//        }
//
//        //商户号
//        String partner = "1260755101";
//        //密钥
//        String key = "DI85KH2960KFIEW7VHA62M9N8F75U46D";
//        //创建支付应答对象
//        ResponseHandler resHandler = new ResponseHandler(request, response);
//        resHandler.setKey(key);
//        //判断签名
//        if(resHandler.isTenpaySign()) {
//            //通知id
//            String notify_id = resHandler.getParameter("notify_id");
//            //创建请求对象
//            RequestHandler queryReq = new RequestHandler(null, null);
//            //通信对象
//            TenpayHttpClient httpClient = new TenpayHttpClient();
//            //应答对象
//            ClientResponseHandler queryRes = new ClientResponseHandler();
//
//            //通过通知ID查询，确保通知来至财付通
//            queryReq.init();
//            queryReq.setKey(key);
//            queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
//            queryReq.setParameter("partner", partner);
//            queryReq.setParameter("notify_id", notify_id);
//
//            //通信对象
//            httpClient.setTimeOut(30);
//            //设置请求内容
//            try {
//                httpClient.setReqContent(queryReq.getRequestURL());
//                System.out.println("queryReq:" + queryReq.getRequestURL());
//            } catch (UnsupportedEncodingException e) {
//            }
//
//            //后台调用
//            if(httpClient.call()) {
//                //设置结果参数
//                try {
//                    queryRes.setContent(httpClient.getResContent());
//                    System.out.println("queryRes:" + httpClient.getResContent());
//                } catch (Exception e) {
//                }
//
//                queryRes.setKey(key);
//
//                //获取返回参数
//                String retcode = queryRes.getParameter("retcode");
//                String trade_state = queryRes.getParameter("trade_state");
//
//                String trade_mode = queryRes.getParameter("trade_mode");
//
//                //判断签名及结果
//                if(queryRes.isTenpaySign()&& "0".equals(retcode) && "0".equals(trade_state) && "1".equals(trade_mode)) {
//                    System.out.println("订单查询成功");
//                    //取结果参数做业务处理
//                    System.out.println("out_trade_no:" + queryRes.getParameter("out_trade_no")+
//                            " transaction_id:" + queryRes.getParameter("transaction_id"));
//                    System.out.println("trade_state:" + queryRes.getParameter("trade_state")+
//                            " total_fee:" + queryRes.getParameter("total_fee"));
//                    //如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
//                    System.out.println("discount:" + queryRes.getParameter("discount")+
//                            " time_end:" + queryRes.getParameter("time_end"));
//
//                    //商户订单号
//                    String orderId = queryRes.getParameter("out_trade_no");
//                    //商户订单金额
//                    Float totalFee = Float.valueOf(queryRes.getParameter("total_fee"));
//                    //商户交易ID
//                    String transactionId = queryRes.getParameter("transaction_id");
//
//                    //------------------------------
//                    //处理业务开始
//                    //------------------------------
//
//                    //处理数据库逻辑
//                    //注意交易单不要重复处理
//                    //注意判断返回金额//业务逻辑
//
//                    Order order = orderService.get(orderId);
//                    if (order != null) {
//                        if (totalFee.equals(order.getTotalPrice())) { //判断返回金额
//                            if (!Global.YES.equals(order.getHasPaid())) {
//                                //更改在线支付的订单状态, 设置订单已支付
//                                OrderStatus orderStatus = new OrderStatus();
//                                orderStatus.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
//                                orderStatus.setStatus(OrderStatus.STATUS_OP_PAID);
//                                orderStatus.setStatusUnion(OrderStatus.STATUS_UNION_OP_PAID);
//                                orderStatus.setLabel(OrderStatus.LABEL_OP_PAID);
//                                orderStatus.setPendingLabel(OrderStatus.LABEL_OP_PAID_PENDING);
//                                orderStatus.setOrder(order);
//                                orderStatusService.save(orderStatus);
//
//                                order.setHasPaid(Global.YES);
//                                order.setOrderStatus(orderStatus);
//                                order.setOpTransactionId(transactionId);
//                                orderService.save(order);
//
//                                try {
//                                    resHandler.sendToCFT("Success"); //返回给微信支付
//                                } catch (IOException e) {
//                                }
//                            }
//                        } else {
//                            logger.error("微信支付返回金额不正确，微信transaction_id:{}，微信订单金额total_fee:{}，" +
//                                    "本地订单号:{}，本地订单金额:{}", transactionId, totalFee, orderId, order.getTotalPrice());
//                        }
//                    }
//                    //------------------------------
//                    //处理业务完毕
//                    //------------------------------
//                }
//                else{
//                    //错误时，返回结果未签名，记录retcode、retmsg看失败详情。
//                    System.out.println("查询验证签名失败或业务错误");
//                    System.out.println("retcode:" + queryRes.getParameter("retcode")+
//                            " retmsg:" + queryRes.getParameter("retmsg"));
//                }
//
//            } else {
//                System.out.println("后台调用通信失败");
//
//                System.out.println(httpClient.getResponseCode());
//                System.out.println(httpClient.getErrInfo());
//                //有可能因为网络原因，请求已经处理，但未收到应答。
//            }
//        }
//        else{
//            System.out.println("通知签名验证失败");
//            //key=DI85KH2960KFIEW7VHA62M9N8F75U46D => sign:9047379b4289bf38510ea5402f6c258a tenpaySign:
//            System.out.println(resHandler.getDebugInfo());
//        }
//
//        return null;
//	}
}
