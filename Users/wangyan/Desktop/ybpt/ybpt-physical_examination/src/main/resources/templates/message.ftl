<!doctype html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>消息通知</title>
    <meta name="keywords" content="主页"/>
    <meta name="description" content="主页"/>
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/iosSelect.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <link rel="stylesheet" href="/civil/lib/css/sellTicket.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/iosSelect.js" type="text/javascript" charset="utf-8"></script>
    <#--    <script src="/civil/lib/js/applyPersonSubscribeRecord.js" type="text/javascript" charset="utf-8"></script>-->
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/userCenter.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
    <#--    <script src="/civil/lib/js/notice.js" type="text/javascript" charset="utf-8"></script>-->

</head>

<body>
<div class="wrap">
    <header class="header header-bg-blue" id="header">
        <div class="topbar">
            <span class="btn-left white"><i class="iconfont icon-fanhui"></i></span>
            <div class="words ellipsis white">通知消息</div>
        </div>
    </header>
    <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
    <div class="contWrap news-container">
        <#list noticeVoList as notice>
            <div class="messbox">
                <div>
                    <div class="title" onclick="tapNotice('${notice.id}')">
                        <h3 class="h3"><i class="iconfont icon-xiaoxi11"></i><#if notice.title??> ${notice.title}</#if>
                        </h3>
                        <div class="time">${(notice.launchTime)?string("yyyy-MM-dd HH:mm:ss")}</div>
                    </div>
                    <div class="content"><#if notice.content??> ${notice.content}</#if></div>
                </div>
            </div>
        </#list>
    </div>
</div>
`
</body>
<script>
    function tapNotice(noticeId) {
        window.location.replace("/civil/user/getNoticeEntity?noticeId=" + noticeId + "&cardId='${decryptedCardId}'");
    }

</script>

</html>
