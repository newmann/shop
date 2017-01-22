<%@page pageEncoding="UTF-8"%>

<%--jsp 静态态引入 <%@ include file="header.jsp"%> --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div data-role="header" data-position="fixed" data-tap-toggle="false">
    <c:if test="${!empty showBackButton}">
        <a data-transition="none" href="#" data-role="button" data-rel="back" data-icon="back" class="btn-back">返回</a>
    </c:if>
    <h1 style="font-size: 1.5em; font-weight: normal; margin: 0; padding: 0 0 0.2em 0;">
        <img class="logo-img" src="/static/app/images/logo.png">
        <span class="logo-title">${pageTitle}</span>
    </h1>
</div>