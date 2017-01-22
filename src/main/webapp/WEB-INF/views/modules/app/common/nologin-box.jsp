<%@page pageEncoding="UTF-8"%>

<%--jsp 静态态引入 <%@ include file="*.jsp"%> --%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<style type="text/css">
    <%-- #w-nologin-tip --%>
    #w-nologin-tip {background: none repeat scroll 0 0 #fffdee; border: 1px solid #edd28b; color: #f70; line-height: 25px; margin-bottom: 20px; padding: 10px 20px;}
    #w-nologin-tip i {background: url("${url}/static/app/images/icon-warning1.png") no-repeat scroll 0 0; display: inline-block; height: 16px; margin-right: 10px; overflow: hidden; vertical-align: middle; width: 16px;}
    #w-nologin-tip a {margin-left:10px;}
</style>

<c:if test="${not fns:isLoggedIn()}">
    <div id="w-nologin-tip" class="tip">
        <div class="tip-text"><i></i>您还没有登录！登录后购买有优惠！</div>
        <a onclick="openUserLogin(this)" data-url="${url}/app/user/login.html" tapmode="">登录</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a onclick="openUserRegister(this)" data-url="${url}/app/user/register.html" tapmode="">注册</a>
    </div>
</c:if>