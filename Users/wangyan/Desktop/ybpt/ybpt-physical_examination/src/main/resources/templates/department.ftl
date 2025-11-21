<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>部门信息维护</title>
    <meta name="keywords" content="部门信息维护"/>
    <meta name="description" content="部门信息维护"/>

    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/department.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="wrap fillinfo">
    <header class="header headerwhitebg">
        <div class="topbar">
            <span class="btn-left"><i class="iconfont icon-fanhui"></i></span>
            <div class="words ellipsis">部门信息维护</div>
            <span class="btn-right"><i class="iconfont icon-home"></i></span>
        </div>
    </header>
    <div class="contWrap">
        <ul class="dept-box">
<#--            <li>-->
<#--                <p>部门名称1</p>-->
<#--                <span></span>-->
<#--            </li>-->
<#--            <li class="active">-->
<#--                <p>部门名称2</p>-->
<#--                <span></span>-->
<#--            </li>-->
<#--            <li>-->
<#--                <p>部门名称3</p>-->
<#--                <span></span>-->
<#--            </li>-->
        </ul>
    </div>

    <div class="bottom">
        <div class="submit">保存</div>
    </div>
    <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
</div>
<script>
    // $(function (){
    //     $('.dept-box li').on('click', function () {
    //         $(this).siblings().removeClass('active')
    //         $(this).addClass('active')
    //     })
    // })
</script>
</body>
</html>