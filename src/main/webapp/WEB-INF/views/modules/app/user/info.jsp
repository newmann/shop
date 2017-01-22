<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    #main {margin-top:15px;}
    a.btn, button.btn{
        border:1px solid #4390af; border-radius: 6px; background-color: #55abce;
        padding: 10px 0; display: block; margin: 0 10px;
        font-size: 16px; color: #fff; text-align: center;
    }
    a.btn{
        background-color: #3394bb;
    }
</style>
<div id="main">
    <div class="">
        <label>手机号:</label>
        <label>${fns:getUser().loginName}</label>
    </div>
    <div>
        <a class="btn" onclick="userLogout(this)" data-url="${url}/app/user/logout.html" tapmode="">退出</a>
    </div>
</div>