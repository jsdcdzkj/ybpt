<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>账号管理</title>
    <meta name="keywords" content="账号管理"/>
    <meta name="description" content="账号管理"/>
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/userCenter.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="wrap userCenter">
    <header class="header headerwhitebg">
        <div class="topbar">
            <span class="btn-left"><i class="iconfont icon-fanhui"></i></span>
            <div class="words ellipsis">账号管理</div>
        </div>
    </header>
    <div class="contWrap">
        <section class="memberinfo sec_box">
            <ul>

                <li id="zhanghao" class="nosanj">
                    <p>身份证号</p>
                    <em><span>${decryptedCardId}</span></em>
                </li>
                <li id="department" class="nomr">
                    <p>所属部门</p>
                    <em><span></span></em>
                </li>
<#--                <li id="updpwd" class="nomr">-->
<#--                    <p>修改密码</p>-->
<#--                    <em><span></span></em>-->
<#--                </li>-->
            </ul>
        </section>
        <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
    </div>
<#--    <div class="bottom">-->
<#--        <div class="submit">退出当前账号</div>-->
<#--    </div>-->
</div>
</body>
</html>