package com.fish.server.websocket;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.fish.server.base.key.ConstantKey;
import com.fish.server.base.util.HttpClientUtil;
import com.fish.server.base.util.JsonUtil;
import com.fish.server.base.util.PropertitesUtil;
import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.service.FinanceService;
import com.fish.server.web.service.UserLogService;
import com.fish.server.web.service.UserService;
import com.fish.server.web.service.UserStaticService;
import com.fish.server.websocket.base.key.BaseKey;
import com.fish.server.websocket.base.key.SessionKey;
import com.fish.server.websocket.bean.DataObj;
import com.fish.server.websocket.bean.SessionUser;
import com.fish.server.websocket.listener.OnDataListener;
import com.fish.server.websocket.service.UserDataService;
import com.fish.server.websocket.session.SessionService;

public class WebSocketServer implements ConnectListener, DisconnectListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	private static SocketIOServer socketIOServer;

	@Autowired
	private UserService userService;

	@Autowired
	private UserLogService userLogService;


	@Autowired
	private UserStaticService userStaticService;

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private FinanceService financeService;

	@Autowired
	private RedisCacheUtil redisCacheUtil;

	private String[] checkMacs;

	public void init() {

		Configuration socketioConfig = new Configuration();
		socketioConfig.setPingTimeout(10000);
		socketioConfig.setHostname(PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "socketio.host"));
		socketioConfig.setPort(PropertitesUtil.readIntValue(PropertitesUtil.PROJECT_PROPERTITES, "socketio.port"));
		if (!StringUtils.isEmpty(PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "socketio.origin"))) {
			socketioConfig.setOrigin(PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "socketio.origin"));
		}

		socketIOServer = new SocketIOServer(socketioConfig);
		socketIOServer.addEventListener(BaseKey.DATA_EVENT, DataObj.class, new OnDataListener());
		socketIOServer.addConnectListener(this);
		socketIOServer.addDisconnectListener(this);
		socketIOServer.start();

		logger.info("启动msgserver成功");


	}

	public static void destroy() {
		if (socketIOServer != null) {
			socketIOServer.stop();
		}
	}
	
	@Override
	public void onConnect(SocketIOClient client) {

		DataObj data;

		String host = client.getRemoteAddress().toString();
		List<String> domain = client.getHandshakeData().getHeaders().get("Host");

		String authKey = paseParam(client, SessionKey.SESSION_AUTHKEY);

		User userMap = null;

		
		if (authKey != null) {
			userMap = userService.loginUser(authKey);
		}

		if (userMap == null) {
			userMap = new  User();
			userMap.setId(0);
			userMap.setAccount(new Date().getTime() + "");
			userMap.setNick("匿名用户");
		}
		logger.info("客户端进入:" + userMap.getAccount());
		logger.info("客户端所在ip和端口:" + host);
		logger.info("客户端所在域名:" + domain.get(0));
		// 是否重复登陆
		SessionUser sessionUser = SessionService.getClient(userMap.getAccount());
		if (sessionUser != null) {
			logger.info("相同账号在别的客户端登陆");
			data = new DataObj();
			data.setNotice("相同账号在别的客户端登陆");
			data.setCode(6);
			sessionUser.getClient().sendEvent(BaseKey.CONNECT_RESULT_EVENT, data);
			sessionUser.getClient().disconnect();
		}

		client.set(SessionKey.SESSION_ACCOUNT, userMap.getAccount());
		sessionUser = SessionService.addClient(client);
		if (sessionUser != null) {
			
			userService.initUser(userMap.getId());
			data = new DataObj();
			sessionUser.setUser(userMap);
			data.setCode(1);
			data.setJsonObj(userMap);
			client.sendEvent(BaseKey.CONNECT_RESULT_EVENT, data);

		}
		logger.info("系统目前人数为:" + SessionService.getClientCount());
	}

	private String paseParam(SocketIOClient client, String key) {
		HandshakeData handshakeData = client.getHandshakeData();
		Map<String, List<String>> params = handshakeData.getUrlParams();
		List<String> theValues = params.get("cp");
		if (theValues == null || theValues.size() == 0)
			return null;
		String theValue = theValues.get(0);
		byte[] bytes = Base64Utils.decodeFromString(theValue);
		String jsonStr = null;
		try {
			jsonStr = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = JsonUtil.getJsonMap(jsonStr);
		return (String) map.get(key);
	}

	private Map<String, Object> paseParamMap(SocketIOClient client) {
		HandshakeData handshakeData = client.getHandshakeData();
		Map<String, List<String>> params = handshakeData.getUrlParams();
		List<String> theValues = params.get("cp");
		if (theValues == null || theValues.size() == 0)
			return null;
		String theValue = theValues.get(0);
		byte[] bytes = Base64Utils.decodeFromString(theValue);
		String jsonStr = null;
		try {
			jsonStr = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = JsonUtil.getJsonMap(jsonStr);
		return map;
	}

	@Override
	public void onDisconnect(SocketIOClient client) {
		// TODO Auto-generated method stub
		if (client != null) {
			try {
				int uid = client.get(SessionKey.SESSION_ACCOUNT);
				logger.info("客户端离开:" + uid);
				userService.addLoginOutTime(uid);
				SessionService.removeClient(client);
			} catch (Exception e) {
				// logger.error(e.toString());
			}
		}
	}

}
