package com.fish.server.web.controller.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	protected String getViewPath(String string) {
		// TODO Auto-generated method stub
		return "web" + string;
	}

	protected String setExceptionRequest(HttpServletRequest request, Exception e) {
		request.setAttribute("myexception", e);
		return "common/error";
	}

	protected Map<String, Object> setJson(boolean suc, String msg, Object object) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("success", suc);
		json.put("message", msg);
		json.put("data", object);
		return json;
	}

	protected void setAjaxException(Map<String, Object> json) {
		// TODO Auto-generated method stub
		json.put("success", false);
		json.put("message", "server error");
	}
}
