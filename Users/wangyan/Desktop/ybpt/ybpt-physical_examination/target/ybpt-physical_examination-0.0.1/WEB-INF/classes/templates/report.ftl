<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>体检报告</title>
    <meta name="keywords" content="账号管理"/>
    <meta name="description" content="账号管理"/>
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/report.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
    <style>
        .iframe-box{
            height: calc(100% - 1vw);
            padding-top: 16.533vw;
        }

    </style>
</head>
<body>
<div class="wrap userCenter">
    <header class="header headerwhitebg">
        <div class="topbar">
            <span class="btn-left"><i class="iconfont icon-fanhui"></i></span>
            <div class="words ellipsis">体检报告</div>
            <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
        </div>
    </header>
    <div class="iframe-box">
    <iframe
            src="/civil/lib/js/pdfjs-2.14.305-dist/web/viewer.html?file=${downloadFileUrl}"

<#--            src="/civil/lib/js/pdfjs-2.14.305-dist/web/viewer.html?file=https://tj.xzybzx.org.cn/fileRead${}?download=0"-->
<#--            src="/civil/lib/js/pdfjs-2.14.305-dist/web/viewer.html?file=http://localhost:9090/uploadtjreportPath/2022/TJ00008/32030219440415165X/32030219440415165X_20220926171408384-1294601062/0cf9b2735f1944419182fc1a48525020.pdf"-->
            width="100%" height="100%">
    </iframe>
    </div>
</div>
</body>
</html>