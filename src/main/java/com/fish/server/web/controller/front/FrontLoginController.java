package com.fish.server.web.controller.front;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fish.server.base.key.ConstantKey;
import com.fish.server.base.util.HttpClientUtil;
import com.fish.server.base.util.JsonUtil;
import com.fish.server.base.util.Md5PwdEncoder;
import com.fish.server.base.util.PwdEncoder;
import com.fish.server.base.util.SignUtil;
import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.finance.Finance;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.bean.userstatic.UserStatic;
import com.fish.server.web.common.CommonConstants;
import com.fish.server.web.service.UserService;
import com.fish.server.websocket.base.key.CmdConst;
import com.fish.server.websocket.bean.DataObj;
import com.fish.server.websocket.session.SessionService;

@Controller
@RequestMapping("/login")
public class FrontLoginController {
	private static final Logger logger = LoggerFactory.getLogger(FrontLoginController.class);
	
	public FrontLoginController(){
		
		
	}

	@Autowired
	private RedisCacheUtil redisCacheUtil;

	@Autowired
	private UserService userService;

	// 验证来自wx胡请求
	@RequestMapping("/wxcheckserver")
	@ResponseBody
	public Object wxcheckserver(HttpServletRequest request) throws Exception, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败

		// if (SignUtil.checkSignature(signature, timestamp, nonce)) {
		// return echostr;
		// }
		return echostr;

	}
	//
	@RequestMapping("/tellogin")
	@ResponseBody
	public Object tellogin(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		DataObj resData = new DataObj();
		String tel = request.getParameter("tel");
		String pwd = request.getParameter("pwd");
		User user  = userService.getUserByAccount(tel);
		if(user == null)
		{
			resData.setCode(0);
		}
		else
		{		
			PwdEncoder pwdEncoder = new Md5PwdEncoder();
			pwd = pwdEncoder.encodePassword(pwd);
			if(user.getPwd().equals(pwd))
			{
				Map<String, Object> userMap = new HashMap();
				resData.setCode(1);
				resData.setJsonObj(user);
				userMap.put("authKey", pwdEncoder.encodePassword(user.getAccount()));
				redisCacheUtil.setCacheObject((String) userMap.get("authKey"), user);
			}
		}
		return resData;
	}

	// 验证来自wx胡请求
	@RequestMapping("/wxgetUser")
	@ResponseBody
	public Object wxgetUser(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {

		DataObj resData = new DataObj();
		String openid = (String) request.getParameter("openid");
		String access_token = (String) request.getParameter("access_token");
	
	
		Map<String, Object> userMap = null;

		// 利用code获取openID
		StringBuffer url = new StringBuffer();
		// 非静默授权时
		User user = null;
		
		url.append("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid
				+ "&lang=zh_CN");
		
		userMap = new HashMap();
		String account = null;
		try {
			@SuppressWarnings({ "rawtypes" })
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			if (access_token != null && openid != null && access_token.indexOf("test") > -1
					&& openid.indexOf("test") > -1) {
				account = openid;
				userMap = new HashMap();
				userMap.put("nickname", account);
				userMap.put("headimgurl", account);
				userMap.put("sex", 1);
			} else {
				String results = HttpClientUtil.get(url.toString(), params);
				logger.info("wxgetUser results:" + results);
				userMap = JsonUtil.getJsonMap(results);

				account = (String) userMap.get("openid");
			}
			if(StringUtils.isNotBlank(account))
			{
				user = userService.getUserByAccount(account);
			
				if (user == null) {
					user = new User();
					user.setCreateTime(new Date());
					user.setAccount(account);
					user.setNick((String) userMap.get("nickname"));
					user.setLogo((String) userMap.get("headimgurl"));
					if(userMap.get("sex") != null)
						user.setSex((int) userMap.get("sex"));
					userService.saveUser(user);
				} else {
					user.setNick((String) userMap.get("nickname"));
					user.setLogo((String) userMap.get("headimgurl"));
					userService.updateUser(user);
				}
				userMap = new HashMap();
				resData.setCode(1);
				Md5PwdEncoder md5 = new Md5PwdEncoder();
				userMap.put("authKey", md5.encodePassword(user.getAccount()));
				redisCacheUtil.setCacheObject((String) userMap.get("authKey"), user);
				resData.setJsonObj(userMap);
			}
			else
			{
				resData.setCode(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resData.setCode(0);
		}
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		return mapper.writeValueAsString(resData);

	}
	private Map cachWxgetTokenOpenId = new HashMap();
	// 验证来自wx胡请求
	@RequestMapping("/wxgetTokenOpenId")
	public void wxgetToken(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		// 微信加密签名
		Map<String, Object> tokenMap = null;

		String openID = "";
		String access_token = "";
		String code = request.getParameter("code");
		if (code == null || "".equals(code)) {
			return;
		} else {
			
			if(cachWxgetTokenOpenId.get("date_"+code) != null)
			{
				Date cacheData = (Date) cachWxgetTokenOpenId.get("date_"+code);
				if(new Date().getTime() - cacheData.getTime() >= 7200 * 1000)
				{
					cachWxgetTokenOpenId.put("openID_"+code, null);
					cachWxgetTokenOpenId.put("access_token_"+code, null);
					cachWxgetTokenOpenId.put("date_"+code, null);
				}
				else
				{
					openID = (String) cachWxgetTokenOpenId.get("openID_"+code);
					access_token = (String) cachWxgetTokenOpenId.get("access_token_"+code);
				}
			}
			else
			{
				tokenMap = wxgetTokenMap(code);
				if (tokenMap != null && tokenMap.containsKey("openid")) {
					openID = (String) tokenMap.get("openid");
					cachWxgetTokenOpenId.put("openID_"+code, openID);
				}
				if (tokenMap != null && tokenMap.containsKey("access_token")) {
					access_token = (String) tokenMap.get("access_token");
					cachWxgetTokenOpenId.put("access_token_"+code, access_token);
				}
				cachWxgetTokenOpenId.put("date_"+code, new Date());
			}
		}
		
		response.sendRedirect(CommonConstants.gameIndex + "?openid=" + openID + "&access_token=" + access_token);
	}

	@RequestMapping("/wxJsApiTicket")
	@ResponseBody
	public  Map wxJsApiTicket(String useHtml) {
		Map res = new HashMap();
		String access_token = wxAccessToken();
		String ticket = "";
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";// 这个url链接和参数不能变
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String results = HttpClientUtil.get(url.toString(), params);

			Map tokenMap = JsonUtil.getJsonMap(results);
			if (tokenMap != null && tokenMap.containsKey("ticket")) {
				ticket = (String) tokenMap.get("ticket");

				// 3、时间戳和随机字符串
				String nonceStr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);// 随机字符串
				String timestamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳

				System.out.println("accessToken:" + access_token + "\njsapi_ticket:" + ticket + "\n时间戳：" + timestamp
						+ "\n随机字符串：" + nonceStr);

				// 4、将参数排序并拼接字符串
				String str = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url="
						+ useHtml;

				// 5、将字符串进行sha1加密
				String signature = SignUtil.SHA1(str);
				System.out.println("参数：" + str + "\n签名：" + signature);
				res.put("timestamp", timestamp);
				res.put("signature", signature);
				res.put("nonceStr", nonceStr);
				res.put("appId", CommonConstants.wx_APPID);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public  String wxAccessToken() {
		
		String access_token = null;
		String grant_type = "client_credential";// 获取access_token填写client_credential

		// 这个url链接地址和参数皆不能变
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid="
				+ CommonConstants.wx_APPID + "&secret=" + CommonConstants.wx_SECRET;

		try {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String results = HttpClientUtil.get(url.toString(), params);
			Map res = JsonUtil.getJsonMap(results);
			access_token = (String) res.get("access_token");
			return access_token;
			
		} catch (Exception e) {
			access_token = null;
			e.printStackTrace();
		}
		return access_token;
	}

	private Map<String, Object> wxgetTokenMap(String code) {
		// TODO Auto-generated method stub
		// 利用code获取openID
		Map<String, Object> tokenMap = null;
		StringBuffer url = new StringBuffer();
		// 非静默授权时

		url.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + CommonConstants.wx_APPID + "&secret="
				+ CommonConstants.wx_SECRET + "&code=" + code + "&grant_type=authorization_code");
		try {
			@SuppressWarnings({ "rawtypes" })
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String results = HttpClientUtil.get(url.toString(), params);
			logger.info("wxgetTokenMap results:" + results);
			tokenMap = JsonUtil.getJsonMap(results);
			return tokenMap;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
