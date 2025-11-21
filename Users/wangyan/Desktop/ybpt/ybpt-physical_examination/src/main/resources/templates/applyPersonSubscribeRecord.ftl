<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>预约体检</title>
    <meta name="keywords" content="预约体检"/>
    <meta name="description" content="预约体检"/>
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/iosSelect.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/iosSelect.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/applyPersonSubscribeRecord.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/notice.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
<div class="fillinfo wrap">
    <header class="header header-bg-blue" id="header">
        <div class="topbar">
            <#--            <span class="btn-left white"><i class="iconfont icon-fanhui" onclick="window.history.back()"></i></span>-->
            <span id="bell" class="btn-right white homeset"><i class="iconfont icon-xiaoxi5"></i></span>
            <div class="mesinfo">${totalCount}</div>
            <div class="words white ellipsis">预约</div>
<#--            <span class="btn-right white"><i class="iconfont icon-home"></i></span>-->
        </div>
    </header>
    <div class="main">
        <ul class="info">
            <li>
                <div class="p1 ">套餐年份</div>
                <div class="p2" id="dataTime" name="dataTime">
                    <p id="availableYearList">请选择年份</p>
                    <#--                    <select id="availableYearList">-->
                    <#--                        <option>2022</option>-->
                    <#--                        <option>2023</option>-->
                    <#--                        <option>2024</option>-->
                    <#--                        <option>2025</option>-->
                    <#--                        <option>2026</option>-->
                    <#--                    </select>-->
                </div>
                <div class="arrow__right"></div>
            </li>
            <li>
                <div class="p1">选择机构</div>
                <div class="p2" id="showType">机构名称</div>
                <input type="hidden" name="type_id" id="typeId" value="">
                <div class="arrow__right"></div>
            </li>
            <li>
                <div class="p1">套餐选择</div>
                <div class="p2" id="taocan">
                    去选择套餐
                </div>
                <input type="hidden" id="taocanid">
                <div class="arrow__right"></div>
            </li>
            <li>
                <div class="p1">预留手机号</div>
                <div class="p2 phone-input">
                    <input id="phoneNumber" type="number" value="">
                </div>

            </li>

        </ul>
        <div class="date_wrap">
            <div class="box">
                <div class="header-title">
                    <div class="title">预约时间</div>
                    <div class="date" type="">
                        <a href="javascript:;" class="a1" id="prev">上月</a>
                        <h4 id="yearMonth">2019年10月</h4>

                        <a href="javascript:;" class="a2" id="next">下月</a>
                    </div>
                </div>
                <ul class="weekdays">
                    <li><p>日</p><small>SUN</small></li>
                    <li><p>一</p><small>MON</small></li>
                    <li><p>二</p><small>TUS</small></li>
                    <li><p>三</p><small>WEN</small></li>
                    <li><p>四</p><small>FOU</small></li>
                    <li><p>五</p><small>FRI</small></li>
                    <li><p>六</p><small>SAT</small></li>

                </ul>
                <ul class="days" id="dateList">

                </ul>
            </div>
        </div>
        <#--        <ul class="info">-->
        <#--            <li>-->
        <#--                <div class="p1 ">剩余预约名额</div>-->
        <#--                <div class="p2">-->
        <#--                    <div class="van-cell van-field">-->
        <#--                        <div class="van-cell__value van-field__value">-->
        <#--                            <div class="van-field__body">-->
        <#--                                <input type="text" class="van-field__control" value="50">-->
        <#--                            </div>-->
        <#--                        </div>-->
        <#--                    </div>-->
        <#--                </div>-->
        <#--            </li>-->

        <#--        </ul>-->
        <!-- <div class="tips">
            <h3><i class="iconfont icon-intro"></i>提示</h3>
            <p>1.3米（含）以下，或6岁及以下儿童不需要预约</p>
        </div> -->

        <div class="bottom" style="position: relative;">
            <div class="submit">确认</div>
        </div>
    </div>

    <footer class="menu_bar block menu_bar_ticket" id="footer">
        <div id="home" class="menu-tab-item on">
            <span class="icon"><i class="iconfont icon-zhuye1"></i></span>
            <span class="menu-tab-label">预约</span>
        </div>
        <div id="psrList" class="menu-tab-item">
            <span class="icon"><i class="iconfont icon-dingdan"></i></span>
            <span class="menu-tab-label">预约列表</span>
        </div>
        <div id="member" class="menu-tab-item">
            <span class="icon"><i class="iconfont icon-wode1"></i></span>
            <span class="menu-tab-label">我的</span>
        </div>
    </footer>

    <div class="popup">
        <div class="overlay"></div>
        <div class="wrapper wxtx">
            <i class="iconfont icon-close"></i>
            <div class="title"><h2>套餐选择</h2></div>
            <div class="cont justify">
                <div class="box">
                    <h1 class="cont-title">
                        通用套餐
                    </h1>
                    <ul class="times" id="tongYongPackInfoList">
                        <#--                        <li data-id="1" data-name="通用套餐-套餐1" data-content="<p>项目5</p><p>项目6</p><p>项目7</p>">-->
                        <#--                            <div class="tp_tit">套餐1</div>-->
                        <#--                        </li>-->
                        <#--                        <li data-id="2" data-name="通用套餐-套餐2" data-content="<p>项目15</p><p>项目6</p><p>项目7</p>">-->
                        <#--                            <div class="tp_tit">套餐2</div>-->
                        <#--                        </li>-->
                        <#--                        <li data-id="3" data-name="通用套餐-套餐3" data-content="<p>项目25</p><p>项目6</p><p>项目7</p>">-->
                        <#--                            <div class="tp_tit">套餐3</div>-->
                        <#--                        </li>-->
                    </ul>
                </div>
                <div class="box">
                    <h1 class="cont-title">
                        通用套餐+新增项目
                    </h1>
                    <ul class="times" id="jigouPackInfoList">
                        <#--                        <li data-id="6"-->
                        <#--                            data-content="<p>项目55</p><p>项目6</p><p>项目7</p><p>项目55</p><p>项目6</p><p>项目7</p><p>项目55</p><p>项目6</p><p>项目7</p><p>项目55</p><p>项目6</p><p>项目7</p><p>项目55</p><p>项目6</p><p>项目7</p><p>项目55</p><p>项目6</p><p>项目7</p>">-->
                        <#--                            <div class="tp_tit">套餐1</div>-->
                        <#--                        </li>-->
                        <#--                        <li data-id="7"  data-content="<p>项目65</p><p>项目6</p><p>项目7</p>">-->
                        <#--                            <div class="tp_tit">套餐2</div>-->
                        <#--                        </li>-->
                        <#--                        <li data-id="8"  data-content="<p>项目75</p><p>项目6</p><p>项目7</p>">-->
                        <#--                            <div class="tp_tit">套餐3</div>-->
                        <#--                        </li>-->
                    </ul>
                </div>
                <div id="packMoney" class="box" style="display: none">
                    <h1 class="cont-title">
                        <#--                        套餐价格：80元-->
                    </h1>

                </div>
                <div class="box">
                    <h1 class="cont-title">
                        套餐包含项目
                    </h1>
                    <div class="word" id="xmcontent">
                        <#--                        <p>项目1</p>-->
                        <#--                        <p>项目1</p>-->
                        <#--                        <p>项目1</p>-->
                    </div>
                </div>

            </div>
            <div id="yes" class="btn">确定</div>
            <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
        </div>
    </div>
</div>

<script src="/civil/lib/js/data.js"></script>
<script src="/civil/lib/js/iscroll-probe.js"></script>
<script src="/civil/lib/js/iosSelect.js"></script>
<script type="text/javascript">
    var packYear = '';
    var org_id = '';
    var org_name = '';
    var tongyongPackInfoList = [];
    var jigouPackInfoList = [];
    var packName = '';
    var packInfoId = '';
    var apply_date = '';
    var packMoney = '';
    var civil_id = '';
    var uid = '';
    var orgSubList;
    var phone = '';
    var showTypeDom = document.querySelector('#showType');
    var typeIdDom = document.querySelector('#typeId');
    showTypeDom.addEventListener('click', function () {
        if (org_id === '') {
            layer.msg("请先选择年份", {icon: 1, time: 1100}, function () {

            });
            return;
        }
        var typeId = showTypeDom.dataset['id'];
        var typeName = showTypeDom.dataset['value'];

        var typeSelect = new IosSelect(1,
            [datajg],
            {
                container: '.container',
                title: '体检机构选择',
                itemHeight: 50,
                itemShowCount: 3,
                oneLevelId: typeId,
                callback: function (selectOneObj) {
                    console.log(selectOneObj);
                    typeIdDom.value = selectOneObj.id;
                    showTypeDom.innerHTML = selectOneObj.value;
                    showTypeDom.dataset['id'] = selectOneObj.id;
                    showTypeDom.dataset['value'] = selectOneObj.value;
                    org_id = selectOneObj.id;
                    org_name = selectOneObj.value;
                    setPackInfoNameForJiGou();
                    getAndSetOrgSubRules();
                }
            });
    });

    $('.times').on('click', 'li', function () {
        packInfoId = $(this).attr("data-id");
        $("ul.times > li").each(function () {
            $(this).removeClass("active");
        });

        $(this).addClass("active");

        $("#taocan").html($(this).attr("data-name"))
        $("#taocanid").val($(this).attr("data-name"))
        var xmcontent = $(this).attr("data-content")
        $("#xmcontent").html(xmcontent)
        for (let i = 0; i < jigouPackInfoList.length; i++) {
            if (jigouPackInfoList[i].id === packInfoId) {
                // console.log(jigouPackInfoList[i].pack_money);
                packMoney = jigouPackInfoList[i].pack_money;
                $("#packMoney").text("套餐价格: " + packMoney + "元");
                getMedicalItem(packInfoId);
                packName = jigouPackInfoList[i].pack_name;
                $('#taocan').text(packName);
                break;
            }
        }

        for (let i = 0; i < tongyongPackInfoList.length; i++) {
            if (tongyongPackInfoList[i].id === packInfoId) {
                // console.log(jigouPackInfoList[i].pack_money);
                packMoney = tongyongPackInfoList[i].pack_money;
                $("#packMoney").text("套餐价格: " + packMoney + "元");
                getMedicalItem(packInfoId);
                packName = tongyongPackInfoList[i].pack_name;
                $('#taocan').text(packName);
                break;
            }
        }
    });


    // $("#jigouPackInfoList").on('click', '.tp_tit', function () {
    //     // console.log("选择了 机构 套餐")
    //     console.log("选中了套餐");
    //     // console.log($(this).parent().attr('data-id'));
    //     packInfoId = $(this).parent().attr('data-id');
    //     for (let i = 0; i < jigouPackInfoList.length; i++) {
    //         if (jigouPackInfoList[i].id === packInfoId) {
    //             // console.log("money");
    //             // console.log(jigouPackInfoList[i].pack_money);
    //             packMoney = jigouPackInfoList[i].pack_money;
    //             $("#packMoney").text("套餐价格: " + packMoney + "元");
    //             getMedicalItem(packInfoId);
    //             packName = jigouPackInfoList[i].pack_name;
    //             $('#taocan').text(packName);
    //         }
    //     }
    // })


    var $dataTime = $('#dataTime');
    // 初始化时间数据（点击默认显示当前日期，年份数据是当前年份的前后五年）
    var dateTime = (function () {
        var now = new Date();
        var nowYear = now.getFullYear();
        var nowMonth = now.getMonth() + 1;
        var nowDate = now.getDate();
        $dataTime.attr("data-year", nowYear);
        $dataTime.attr("data-month", nowMonth);
        $dataTime.attr("data-date", nowDate);

        function formatYear(nowYear) {
            var arr = [];
            for (var i = nowYear - 5; i <= nowYear + 5; i++) {
                arr.push({id: i + "", value: i + "年"})
            }
            return arr
        }

        function formatMonth() {
            var arr = [];
            for (var i = 1; i <= 12; i++) {
                arr.push({id: i + "", value: i + "月"})

            }
            return arr
        }

        function formatDate(count) {
            var arr = [];
            for (var i = 1; i <= count; i++) {
                arr.push({id: i + "", value: i + "日"})
            }
            return arr
        }

        var yearData = function (callback) {
            callback(formatYear(nowYear))
        };
        var monthData = function (year, callback) {
            callback(formatMonth())
        };
        var dateData = function (year, month, callback) {
            if (/^(1|3|5|7|8|10|12)$/.test(month)) {
                callback(formatDate(31))
            } else {
                if (/^(4|6|9|11)$/.test(month)) {
                    callback(formatDate(30))
                } else {
                    if (/^2$/.test(month)) {
                        if (year % 4 === 0 && year % 100 !== 0 || year % 400 === 0) {
                            callback(formatDate(29))
                        } else {
                            callback(formatDate(28))
                        }
                    } else {
                        throw new Error("month is illegal")
                    }
                }
            }
        };
        var hourData = function (one, two, three, callback) {
            var hours = [];
            for (var i = 0, len = 24; i < len; i++) {
                hours.push({id: i, value: i + "时"})
            }
            callback(hours)
        };
        var minuteData = function (one, two, three, four, callback) {
            var minutes = [];
            for (var i = 0, len = 60; i < len; i++) {
                minutes.push({id: i, value: i + "分"})
            }
            callback(minutes)
        };
        var secondsData = function (one, two, three, four, five, callback) {
            var seconds = [];
            for (var i = 0, len = 60; i < len; i++) {
                seconds.push({id: i, value: i + "秒"})
            }
            callback(seconds)
        };
        return [yearData, monthData, dateData, hourData, minuteData, secondsData]
    })();

    $dataTime.on("click", function () {
        new IosSelect(1, dateTime, {
            title: '年份选择',
            itemShowCount: 9,
            oneLevelId: $dataTime.attr('data-year'),
            // twoLevelId: $dataTime.attr('data-month'),
            // threeLevelId: $dataTime.attr('data-date'),
            // fourLevelId: $dataTime.attr('data-hour'),
            // fiveLevelId: $dataTime.attr('data-minute'),
            // sixLevelId: $dataTime.attr('data-second'),
            callback: function (selectOneObj, selectTwoObj, selectThreeObj, selectFourObj, selectFiveObj, selectSixObj) {
                // 清空原选择机构
                let org = $('#showType');
                org.prop("data-value", "");
                org.text("请选择机构");
                org_id = null;

                $('#taocan').text("选择套餐");

                $dataTime.attr('data-year', selectOneObj.id);
                // $dataTime.attr('data-month', selectTwoObj.id);
                // $dataTime.attr('data-date', selectThreeObj.id);
                // $dataTime.attr('data-hour', selectFourObj.id);
                // $dataTime.attr('data-minute', selectFiveObj.id);
                // $dataTime.attr('data-second', selectSixObj.id);
                //$dataTime.val(selectOneObj.value + selectTwoObj.value + selectThreeObj.value + ' ' + selectFourObj.value + ':' + selectFiveObj.value + ':' + selectSixObj.value);
                $dataTime.text(selectOneObj.value);
                console.log("年份已经被选择");
                // 清空原来的通用套餐
                $('#tongYongPackInfoList').empty();
                // 清空原来的机构套餐
                $('#jigouPackInfoList').empty();
                datajg = [];
                packYear = selectOneObj.value.toString().replace("年", '');
                setPackInfoTongYong()
                getOrgList();

            }
        });
    });

    function getOrgList() {
        $.ajax({
            async: false,
            url: '/civil/packInfo/getOrgList',
            type: 'get',
            data: {},
            success: function (response) {
                let result = response.data;
                if (Array.isArray(result) && result.length >= 0) {
                    for (let i = 0; i < result.length; i++) {
                        console.log(result[i].medical_insurance_num)
                        datajg.push({'id': result[i].medical_insurance_num, 'value': result[i].org_name});
                    }
                    console.log(datajg)
                } else {
                    datajg = [{'id': '000001', 'value': '该年没有机构或医保发布有效套餐'}];
                }
            }
        })
    }
</script>
<script type="text/javascript">
    var dateList = document.getElementById("dateList")
    var prev = document.getElementById("prev")
    var next = document.getElementById("next")
    var h4 = document.getElementsByTagName("h4")[0]
    var iNow = 0
    prev.onclick = function () {
        iNow--
        calender(iNow)

    }
    next.onclick = function () {
        iNow++
        calender(iNow)
    }
    calender(iNow)

    function calender(n) {
        var date = new Date
        // 获取今天是几号
        var nowDate = date.getDate()
        // 把时间拨到n的偏移量所代表的月份的1号
        date.setMonth(date.getMonth() + n, 1)
        // 得到当前年份
        var year = date.getFullYear()
        // 得到当前月份
        var month = date.getUTCMonth() + 1

        // 设置标题
        h4.innerHTML = year + "-" + month;
        // 得到一号是星期几
        var week = date.getDay()
        var nowMonth = date.getMonth()
        // 这个月总共有多少天
        // 把日期拨到下个月的0号(这个月的最后一天)
        date.setMonth(nowMonth + 1, 0)
        var allDays = date.getDate()
        var str = ""
        // week个空的li
        for (var i = 0; i < week; i++) {
            str += "<li class='kong'><div class=\"date\"></div><p class=\"small\"></p></li>"
        }
        // allDays个有日期的li
        for (var j = 1; j <= allDays; j++) {
            // 判断月份是当前的还是以后的？
            if (n > 0) {
                // 以后的月份,只需要判断周末
                if ((week + j) % 7 === 0 || (week + j) % 7 === 1) {
                    // 判断周末
                    // 空白li的数量加上当前日期对7求余
                    str += "<li name=" + j + " class=\"closed\"><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
                } else {
                    str += "<li name=" + j + " class=\"closed\"><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
                }
            } else if (n < 0) {
                // 以前的日期,直接全部置灰
                str += "<li name=" + j + " class=\"closed\"><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
            } else {
                // 当前月
                if (j < nowDate) {
                    // 以前的日期
                    str += "<li name=" + j + " class=\"closed\"><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
                } else if (j === nowDate) {
                    // 今天
                    // str += "<li class='today'><div class=\"date\">" + j + "</div><p class=\"small\">可预约</p></li>"
                    str += "<li name=" + j + " class=\"closed\"><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
                } else if ((week + j) % 7 === 0 || (week + j) % 7 === 1) {
                    // 判断周末
                    // 空白li的数量加上当前日期对7求余
                    str += "<li name=" + j + " class='closed'><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
                } else {
                    // str += "<li class='closed'><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
                    str += "<li name=" + j + " class='closed'><div class=\"date\">" + j + "</div><p class=\"small\">不可预约</p></li>"
                }
            }
        }
        dateList.innerHTML = str
    }
</script>
</body>
</html>
