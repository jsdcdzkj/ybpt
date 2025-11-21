<!doctype html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>个人中心</title>
    <meta name="keywords" content="个人中心"/>
    <meta name="description" content="个人中心"/>
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <link rel="stylesheet" href="/civil/lib/css/sellTicket.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/psrList.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/notice.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
<div class="wrap">
    <header class="header blue-back">
        <div class="topbar">
            <div class="words ellipsis white">预约列表</div>
            <span id="bell" class="btn-right white homeset"><i class="iconfont icon-xiaoxi5"></i></span>
            <div class="mesinfo">${totalCount}</div>
        </div>
    </header>

    <div class="contWrap">
        <ul class="tabs-panel">
            <li id="all" class="active">全部</li>
            <li id="subscribed">待体检</li>
            <li id="completed">已完成</li>
        </ul>
        <div class="tabs-content">
            <ul id="personSubscribeList" class="tab-box orderlist active">

            </ul>

            <#--<ul id="" class="tab-box orderlist">
                <div class="has-no-order">
                    <div class="bg">
                        <span class="iconfont icon-dingdan6"></span>
                    </div>
                    <div class="text">还没有数据</div>
                </div>
            </ul>-->
            <#--                <ul class="tab-box orderlist">-->
            <#--                    <li>-->
            <#--                        <div class="row1">-->
            <#--                            <div class="ordernum"><span-->
            <#--                                        class="iconfont icon-shijian3"></span><span>某某某体检机构</span>-->
            <#--                            </div>-->
            <#--                            <div class="state green">已完成</div>-->
            <#--                        </div>-->
            <#--                        <div class="row2">-->
            <#--                            <div class="item-row">-->
            <#--                                <span class="p1">套餐年份：</span>-->
            <#--                                <span class="p2">12345612465</span>-->
            <#--                            </div>-->
            <#--                            <div class="item-row">-->
            <#--                                <span class="p1">套餐金额：</span>-->
            <#--                                <span class="p2">F6556</span>-->
            <#--                            </div>-->
            <#--                            <div class="item-row">-->
            <#--                                <span class="p1">预约时间：</span>-->
            <#--                                <span class="p2">F6556</span>-->
            <#--                            </div>-->
            <#--                        </div>-->

            <#--                        <div class="row4">-->
            <#--                            <div class="btns">查看报告</div>-->

            <#--                        </div>-->
            <#--                    </li>-->
            <#--                </ul>-->


        </div>
    </div>


    <footer class="menu_bar block menu_bar_ticket" id="footer">
        <div id="home" class="menu-tab-item">
            <span class="icon"><i class="iconfont icon-zhuye1"></i></span>
            <span class="menu-tab-label">预约</span>
        </div>
        <div id="psrList" class="menu-tab-item on">
            <span class="icon"><i class="iconfont icon-dingdan"></i></span>
            <span class="menu-tab-label">预约列表</span>
        </div>
        <div id="member" class="menu-tab-item">
            <span class="icon"><i class="iconfont icon-wode1"></i></span>
            <span class="menu-tab-label">我的</span>
        </div>
    </footer>
    <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
</div>
<script>
    $(function () {
        $(".tabs-panel li").mouseover(function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            //console.log(index);
            // $('.tabs-content .tab-box:eq(' + index + ')').addClass('active').siblings().removeClass('active');
        });
    })
</script>
</body>

</html>
