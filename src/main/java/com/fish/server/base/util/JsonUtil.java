package com.fish.server.base.util;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {

	public static String getJsonMsg(Object data) {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonMsg = null;
		try {
			jsonMsg = objectMapper.writeValueAsString(data);
		} catch (Exception e) {

		}
		return jsonMsg;
	}

	public static Map<String, Object> getJsonMap(String jsonMsg) {
		// TODO Auto-generated method stub

		Map<String, Object> map = null;
		try {
			if (jsonMsg != null && !jsonMsg.equals("")) {
				ObjectMapper objectMapper = new ObjectMapper();
				map = objectMapper.readValue(jsonMsg, Map.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		return map;
	}

}
