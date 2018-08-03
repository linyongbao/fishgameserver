<%@page import="com.dati.server.web.common.CommonConstants"%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<title></title>
		
		<script type="text/javascript">
		
			window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf2bea250b653f68d&redirect_uri=<%=CommonConstants.contextPath%>/login/wxgetTokenOpenId&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		</script>
	</head>
	<body>
	</body>
</html>
