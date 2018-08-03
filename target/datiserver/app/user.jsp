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
		<script src="js/jquery1.10.2.min.js"></script>
		<script src="js/util.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
		<script src="js/init.js"></script>
	</head>

	<body>
		<span id="tip"></span>
		
	</body>

	<script type="text/javascript">
	
		function getlogininfo(openid, access_token) {
			$.ajax({
				type: 'get',
				url: '<%=CommonConstants.contextPath%>/login/wxgetUser',
				data: {
					openid: openid,
					access_token: access_token
				},
				dataType: 'json',
				async: false,
				cache: false,
				success: function(result) {
					
				}
			});
		}

		

		var openid = GetQueryString("openid");
		var access_token = GetQueryString("access_token");
		getlogininfo(openid, access_token);
		
	</script>

</html>