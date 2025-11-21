<!doctype html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport"
        content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi" />
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>消息通知</title>
    <meta name="keywords" content="主页" />
    <meta name="description" content="主页" />
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <link rel="stylesheet" href="/civil/lib/css/sellTicket.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/userCenter.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/config.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/notice.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>
    <div class="wrap">
        <header class="header header-bg-blue" id="header">
            <div class="topbar">
                <span class="btn-left white"><i class="iconfont icon-fanhui"></i></span>
                <div class="words ellipsis white">消息通知</div>
            </div>
        </header>
        <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
        <div class="contWrap" style="height:100%;">
            <div class="mesdetail">
                <div class="title">${notice.title}</div>
                <div class="time">${(notice.launchTime)?string("yyyy-MM-dd HH:mm:ss")}</div>
                <div class="content">${notice.content}</div>
                <ul class="filelist">
                    <#list fileInfoList as fileInfo>
                        <li onclick="downloadNoticeFile('${fileInfo.fileUrl}')" data-content="${fileInfo.fileUrl}"><i class="iconfont icon-dingdan4"></i>${fileInfo.fileName}</li>
<#--                    <li><a href="http://www.wangyan.zone:8888${fileInfo.fileUrl}"><i class="iconfont icon-dingdan4"></i>${fileInfo.fileName}</a></li>-->
<#--                    <li><a href="https://tj.xzybzx.org.cn${fileInfo.fileUrl}"><i class="iconfont icon-dingdan4"></i>${fileInfo.fileName}</a></li>-->
                    </#list>
                </ul>
            </div>
        </div>
    </div>

</body>

</html>
