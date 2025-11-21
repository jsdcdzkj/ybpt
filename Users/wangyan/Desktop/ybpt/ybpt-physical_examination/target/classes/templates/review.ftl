<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>服务评价</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="/civil/lib/css/pingjia.css"/>
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/flexible.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/zepto.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/review.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
<!--返回-->
<div class="top">
    <i class="btn-left"></i>服务评价
</div>
<!--服务评价-->
<div class="fwpj">
    <span>服务评价</span>
    <div class="star" id="fwpj">
        <span class="on"></span>
        <span class="on"></span>
        <span class="on"></span>
        <span class="on"></span>
        <span class="on"></span>
    </div>
</div>
<div class="form-box">
    <!--具体说说看-->
    <div class="jtssk">
        <p class="title"><span>-</span>具体说说看<span>-</span></p>
        <div class="items">
            <div class="item">
                <span>专业性</span>
                <div class="star" id="zyx">
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                </div>
            </div>
            <div class="item">
                <span>服务态度</span>
                <div class="star" id="td">
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                </div>
            </div>
            <div class="item">
                <span>时效性</span>
                <div class="star" id="sxx">
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                    <span class="on"></span>
                </div>
            </div>
        </div>
    </div>
    <!--选择标签-->
    <div class="xzbq">
        <p class="title"><span>-</span>选择或自定义任一标签<span>-</span></p>
        <div class="tags">
            <ul>
                <li class="lis">专业性强</li>
                <li class="lis">态度谦和</li>
                <li class="lis">声音甜美</li>
                <li class="lis">速度很快</li>
                <li class="lis">服务周到</li>
                <li class="lis">效率高</li>
                <li class="lis">服务恶劣</li>
                <li class="lis">服务满意</li>
            </ul>
        </div>
    </div>
    <div class="remark">
        <textarea name="content" id="remark" placeholder="说点什么吧..."></textarea>
    </div>

</div>
<div class="submit">
    <button id="submit">提交</button>
</div>
<div id="psrId" data-value="${psrId}" ></div>
<div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
</body>

</html>
