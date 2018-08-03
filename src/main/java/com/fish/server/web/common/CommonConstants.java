package com.fish.server.web.common;

import com.fish.server.base.util.PropertitesUtil;

public class CommonConstants {

	public static String contextPath = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "contextPath");
	public static String staticServer = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "staticServer");
	public static String uploadImageServer = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES,
			"uploadImageServer");
	public static String staticImage = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "staticImage");
	static {
		System.out.println(staticImage);
	}
	public static String projectName = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "projectName");
	public static final String MYDOMAIN = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "mydomain");

	/** 邮箱正则表达式 */
	public static String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/** 电话号码正则表达式 */
	public static String telRegex = "^1[0-9]{10}$";
	/** 后台用户登录名正则表达式 */
	public static String loginRegex = "^([0-9]*[a-zA-Z]+[0-9]*){6,20}$";
	/** 图片验证码Session的K */
	public static final String RAND_CODE = "COMMON_RAND_CODE";

	public static final String LOGIN_SYC_USER = "login_sys_user";

	public static String gameIndex = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "gameIndex");
	public static String wx_SECRET = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "wx_SECRET");
	public static String wx_APPID = PropertitesUtil.readValue(PropertitesUtil.PROJECT_PROPERTITES, "wx_APPID");
}
