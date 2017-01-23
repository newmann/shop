<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    #main {padding: 80px 10px 0;}

    a.btn, button.btn{
        border:1px solid #4390af; border-radius: 6px; background-color: #55abce;
        padding: 10px 0; display: block; margin: 0 10px;
        font-size: 16px; color: #fff; text-align: center;
    }
    a.btn{
        background-color: #3394bb;
    }
    input{
        border:1px solid #4390af; border-radius: 6px;
        padding: 10px 0; margin: 0 10px;
        font-size: 16px;
    }
    .input-block-level {
        width: 100%;
        min-height: 30px;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
    .register{margin:20px 0 0 10px;}
    .forget{float:right; margin:20px 10px 0 0;}
</style>
<div id="main">
    <form id="user-register-form" action="${url}/app/user/login-post.html" method="post">
        <div>
            <label for="username">手机号</label>
            <input type="text" id="username" name="username" class="input-block-level" value="${username}">
        </div>
        <div>
            <label for="register-code">验证码</label>
            <input type="text" id="register-code" name="register-code" class="input-block-level">
        </div>
        <div>
            <label for="password">密码</label>
            <input type="password" id="password" name="password" class="input-block-level">
        </div>
        <a class="btn" onclick="submitUserRegisterForm()" tapmode="">注 册</a>
    </form>
</div>