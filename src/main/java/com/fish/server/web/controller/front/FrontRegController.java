package com.fish.server.web.controller.front;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fish.server.base.util.Md5PwdEncoder;
import com.fish.server.base.util.PwdEncoder;
import com.fish.server.redis.RedisCacheUtil;
import com.fish.server.web.bean.user.User;
import com.fish.server.web.service.UserService;
import com.fish.server.websocket.bean.DataObj;

@Controller
@RequestMapping("/reg")
public class FrontRegController {
	private static final Logger logger = LoggerFactory.getLogger(FrontRegController.class);
	
	public FrontRegController(){
		
		
	}

	@Autowired
	private RedisCacheUtil redisCacheUtil;

	@Autowired
	private UserService userService;

	// 验证来自wx胡请求
	@RequestMapping("/telnewuser")
	@ResponseBody
	public Object wxcheckserver(HttpServletRequest request) throws Exception, IOException {
		DataObj resData = new DataObj();
		String tel = request.getParameter("tel");
		String pwd = request.getParameter("pwd");
		
		User  oldUser = userService.getUserByAccount(tel);
		if(oldUser != null)
		{
			resData.setCode(0);
		}
		else
		{
			PwdEncoder pwdEncoder = new Md5PwdEncoder();
			pwd = pwdEncoder.encodePassword(pwd);
			User newUser = new User();
			newUser.setAccount(tel);
			newUser.setCreateTime(new Date());
			newUser.setNick(tel);
			newUser.setSex(0);
			newUser.setMobile(tel);
			newUser.setAvalible(1);
			newUser.setLogo("");
			
			userService.saveUser(newUser);
			resData.setCode(1);
			resData.setJsonObj(newUser);
		}
		return resData;

	}

	

}
