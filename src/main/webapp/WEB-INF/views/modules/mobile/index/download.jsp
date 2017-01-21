<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, height=device-height, target-densitydpi=device-dpi" />
    <title>APP下载</title>
    <style type="text/css">
        .wrapper{
            position: relative;
        }
        .logo-area {
            margin: 20px;
        }
        .btn-logo{
            background: url("/static/mobile/images/logo-245x150.png") no-repeat center;
            height: 150px;
            display: block;
        }
        .btn-android {
            background: url("/static/mobile/images/app-android.png") no-repeat center;
            height: 136px;
            display: block;
        }
        .btn-iphone {
            background: url("/static/mobile/images/app-iphone.png") no-repeat center;
            height: 142px;
            display: block;
        }
        .az_note {
            border: 1px solid #7fc31e;
            margin-top:30px;
        }
        .az-down-sm {
            color: #7fc31e;
            font-size: 32px;
            line-height: 26px;
            text-align: center;
        }
        .open-note{
            color: #7fc31e;
            font-size: 32px;
            line-height: 26px;
            text-align: center;
            color: #fe8a23;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <div class="btns">
            <div class="logo-area">
                <a class="btn-logo" href="#"></a>
            </div>
            <div id="web-hint">
                <a class="btn-android" href="/static/file/ygcr-android.apk"></a>
                <a class="btn-iphone" href="https://itunes.apple.com/cn/app/yue-guang-cha-ren/id1025653813?mt=8"></a>
            </div>
            <div id="weixin-hint" class="az_note">
                <p class="az-down-sm">请点击微信右上角<br></p>
                <p class="open-note">选择「在浏览器中打开」</p>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        //判断是否微信浏览器
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/iphone/i)) { //iPhone手机扫一扫
            window.location.href = "https://itunes.apple.com/cn/app/yue-guang-cha-ren/id1025653813?mt=8";
            var web = document.getElementById('web-hint');
            web.style.cssText = "display:none";
        } else if (ua.match(/micromessenger/i)) { //用微信扫一扫
            var web = document.getElementById('web-hint');
            web.style.cssText = "display:none";
        } else { //其他工具扫一扫
            var weixin = document.getElementById('weixin-hint');
            weixin.style.cssText = "display:none";
        }
    </script>
</body>
</html>