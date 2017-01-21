package com.iwc.shop.common.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 重庆漫道公司的短信接口客户端
 * 月光茶人的： ID：185      账号：SDK01357      密码：001357   500元/0.10元/条=5000条
 * 查看当前短信数：http://web.mdjc.net.cn:8888/logins.html
 */
public class SmsClient {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger("SmsClient");

	// 企业ID
	private String userid = "185";

	// 服务器地址
	//private String serverUrl = "http://sdk9.mdjc.net.cn:8888/sms.aspx"; //测试用的
    private String serverUrl = "http://sdk8.mdjc.net.cn:8888/sms.aspx"; //对应UTF-8
    //private String serverUrl = "http://sdk8.mdjc.net.cn:8888/smsGBK.aspx"; //对应GB2312

	// 用户帐号，由系统管理员
	private String account = "SDK01357";

	// 用户账号对应的密码
	private String password = "001357";

    public SmsClient() {}

	public SmsClient(String userid, String account, String password, String serverUrl) {
		this.userid = userid;
		this.account = account;
		this.password = password;
		this.serverUrl = serverUrl;
	}

	public String getAccount() {
		return account;
	}

	/**
	 * 查询余额与发送量
	 * @return 请求返回值
	 * @throws Exception
	 */
	public String getBalance() throws Exception {

		// 参数赋值
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("action", "overage"));

		// 提交请求
		String result = HttpUtil.request(serverUrl, params);
		return result;
	}

	/**
	 * 非法关键词检查
	 * @param content 待检查内容
	 * @return 返回结果
	 * @throws Exception
	 */
	public String checkContent(String content) throws Exception {
		// 参数赋值
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("action", "checkkeyword"));
		params.add(new BasicNameValuePair("content", content));

		// 提交请求
		String result = HttpUtil.request(serverUrl, params);
		return result;
	}

	/**
	 * 获取返回报告数据
	 * @return 返回报告数据
	 * @throws Exception
	 */
	public String getReport() throws Exception {

		String url = "http://118.244.214.125:8888/statusApi.aspx";
		// 参数赋值
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("action", "query"));

		// 提交请求
		String result = HttpUtil.request(url, params);
		return result;
	}

	/**
	 * 获取上行数据
	 * @return 获取上行数据
	 * @throws Exception
	 */
	public String getMo() throws Exception {

		String url = "http://118.244.214.125:8888/callApi.aspx";
		// 参数赋值
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("action", "query"));

		// 提交请求
		String result = HttpUtil.request(url, params);
		return result;
	}

	public String getPassword() {
		return password;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getUserid() {
		return userid;
	}

	/**
	 * 发送短信（utf-8格式内容）
	 *
	 * @param mobile
	 *            手机号，多个使用半角逗号分隔
	 * @param content
	 *            内容
	 * @param sendTime
	 *            定时时间，格式2010-10-24 09:08:10，小于当前时间或为空表示立即发送
	 * @param extno
	 *            扩展码
	 * @return 发送返回值
	 * @throws Exception
	 *             抛出异常
	 */
	public String sendSms(String mobile, String content, String sendTime,
			String extno) throws Exception {

        logger.info("短信的配置 userid: {}, account: {}, password: {}, serverUrl: {}", userid, account, password, serverUrl);

		// 参数赋值
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", userid));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("sendTime", sendTime));
		params.add(new BasicNameValuePair("action", "send"));
		params.add(new BasicNameValuePair("extno", extno));

		// 提交请求
		String result = HttpUtil.request(serverUrl, params);
		return result;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
