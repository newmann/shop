<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="pageId" value="user-login" scope="request"/>
<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="shop_default"/>
	<meta name="description" content="月光茶人" />
	<meta name="keywords" content="月光茶人" />
</head>
<body>


<!-- content -->
<div id="" class="main-content clearfix">
	<div id="main">
		<form id="user-login-form" action="${url}/shop/user/login-post.html" method="post">
			<div>
				<label for="username">手机号</label>
				<input type="text" id="username" name="username" class="input-block-level" value="${username}">
			</div>
			<div>
				<label for="password">密码</label>
				<input type="password" id="password" name="password" class="input-block-level">
			</div>
			<a class="btn" onclick="submitUserLoginForm()" tapmode="">登 录</a>
		</form>

		<a class="register" onclick="openUserRegister(this)" data-url="${url}/shop/user/register.html" tapmode="">立即注册</a>
		<a class="forget">忘记密码?</a>
	</div>
</div>
<!-- /content -->


</body>
</html>


