<%@page pageEncoding="UTF-8"%>

<%--jsp 静态态引入 <%@ include file="header.jsp"%> --%>

<div class="footer-btn-w">
    <div data-role="footer" data-position="fixed">
        <div class="footer-buttons">
            <a style="font-weight: normal" data-role="button" data-icon="home" data-transition="none" href="<c:url value='/app.html'/>">首页</a>&nbsp;&nbsp;&nbsp;
            <a style="font-weight: normal" data-role="button" data-icon="gear" data-transition="none" href="<c:url value='/app/category.html'/>">继续购物</a>&nbsp;&nbsp;&nbsp;
        </div>
    </div>
</div>