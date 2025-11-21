$(function () {
    $("#member").on("click", function () {
        window.location.replace("/civil/user/userCenter?cardId=" + getCardId());
    })

    $("#psrList").click(function () {
        // window.location.href="";
        window.location.replace("/civil/personSubscribeRecord/psrList?cardId=" + getCardId());
        // $.ajax({
        //     async: false,
        //     url: "/civil/personSubscribeRecord/psrListRequest",
        //     type: "post",
        //     data: {
        //         cardId: getCardId()
        //     },
        //     // contentType: 'application/json',
        //     dataType: "json",
        //
        // })
    })

    // var taocanid;
    $('.days').on('click', 'li', function () {
        var _self = $(this);
        if (_self.hasClass('disabled')) {

        } else if (_self.hasClass('closed')) {

        } else {
            _self.addClass('active');
            _self.siblings().removeClass('active');
        }
    });


    $('#taocan').on('click', function () {
        if (org_id === '') {
            layer.msg("请先选择年份", {icon: 1, time: 1100}, function () {
            });
            return;
        }

        if ($("#showType").attr("data-id") === undefined) {
            layer.msg("请先选择机构", {icon: 1, time: 1100}, function () {
            });
            return;
        }

        $(".popup").show();
    });
    $('.icon-close').on('click', function () {
        $(".popup").hide();
    });

    $('.popup .btn').on('click', function () {
        $(".popup").hide();
        //location.href="info.html";
    });


    $("#tongYongPackInfoList").on('click', '.tp_tit', function () {
        // console.log("选择了 通用 套餐")
        // 清空日期
        apply_date = "";

        console.log(tongyongPackInfoList)
        // console.log($(this).parent().attr('data-id'));
        packInfoId = $(this).parent().attr('data-id');
        for (let i = 0; i < tongyongPackInfoList.length; i++) {
            if (tongyongPackInfoList[i].id === packInfoId) {
                // console.log("money");
                // console.log(tongyongPackInfoList[i].pack_money);
                packMoney = tongyongPackInfoList[i].pack_money;
                $("#packMoney").text("套餐价格: " + packMoney + "元");
                getMedicalItem(packInfoId);
                packName = tongyongPackInfoList[i].pack_name;
                $('#taocan').text(packName);
            }
        }
    })


    $('#dateList').on('click', 'li', function () {
        let day = $(this).children(".date").text();
        if (day < 10) {
            day = "0" + day;
        }

        let yearMonthRow = ($("#yearMonth").text());
        let split = yearMonthRow.split('-');
        if (split[1] < 10) {
            split[1] = "0" + split[1];
            console.log(split[1])
        }

        let join = split.join("-");
        apply_date = join + "-" + day
    })

    // $("#bell").on("click", function () {
    //     console.log("点击了铃铛")
    //     window.location.replace("/civil/user/notice.page?cardId=" + getCardId());
    // })

    $(".submit").click(function () {
        if (!regExpPhone($("#phoneNumber").val())) {
            layer.msg("手机号不合法, 请重新填写", {icon: 2, time: 1200}, function () {
                return false;
            });
            return;
        } else {
            phone = $("#phoneNumber").val();
        }
        civil_id = $("#civil_id").val();
        uid = $("#civil_uid").val();

        if (packYear == null || packYear === "") {
            layer.msg("请选择套餐年份", {icon: 2, time: 1200}, function () {
                return false;
            });
            return;
        }

        if (org_id == null || org_id === "") {
            layer.msg("请选择机构~", {icon: 2, time: 1200}, function () {
                return false;
            });
            return;
        }

        if (packName == null || packName === "") {
            layer.msg("请选择套餐~", {icon: 2, time: 1200}, function () {
                return false;
            });
            return;
        }

        if (packInfoId == null || packInfoId === "") {
            layer.msg("请选择套餐~~", {icon: 2, time: 1200}, function () {
                return false;
            });
            return;
        }

        if (apply_date == null || apply_date === "") {
            layer.msg("申请日期为空", {icon: 2, time: 1200}, function () {
                return false;
            });
            return;
        }


        let employeeSubscribeVo = {
            'pid': packInfoId,
            'year': packYear,
            'money': packMoney,
            'start': apply_date,
            'end': apply_date,
            'apply_date': apply_date,
            'org_id': org_id,
            'org_name': org_name,
            'phone': phone,
            'cardId': getCardId()
        };

        $.ajax({
            async: false,
            url: '/civil/personSubscribeRecord/save',
            type: 'post',
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(employeeSubscribeVo),
            success: function (response) {
                console.log(response)
                if (response.code === 0) {
                    layer.msg("预约成功！", {icon: 1, time: 1500}, function () {
                        window.location.replace("/civil/personSubscribeRecord/psrList?cardId="+ getCardId());
                    });
                } else {
                    layer.msg(response.msg, {icon: 2, time: 1500}, function () {
                    });
                }
            },
            error: function (response) {
                console.log(response)
                layer.msg(response.status + " " + response.statusText)
            }
        })
    });

    // 选择上月时触发获取getAndSetOrgSubRules()
    $("#prev").on("click", function () {
        getAndSetOrgSubRules()
    })

    $("#next").on("click", function () {
        getAndSetOrgSubRules()
    })
})


//  ----------------------------------------------------需要修改------------------------------------
function getMedicalItem(packInfoId) {
    $.ajax({
        async: false,
        url: '/civil/packInfo/getMedicalItemListByPackInfoId',
        type: 'get',
        data: {
            'packInfoId': packInfoId,
        },
        success: function (response) {
            $('#taocan').text(packName);
            console.log(response)
            if (response.code === 0 && Array.isArray(response.data)) {
                console.log(response.data)
                let medicalItemList = response.data;
                if (medicalItemList[0] !== null) {
                    let data_content = '';
                    // ("#"+ packInfoId).attr("data-content")

                    for (let i = 0; i < medicalItemList.length; i++) {
                        if (medicalItemList[i].GENERIC === '0') {
                            data_content += "<p>" + medicalItemList[i].NAME + "<span style='color:red'> - 赠送项目 </span></p>";
                        } else {
                            data_content += "<p>" + medicalItemList[i].NAME + "</p>";
                        }
                    }
                    // $("li[data-id='" + packInfoId + "']").attr("data-content", data_content)
                    $("#xmcontent").html(data_content)
                }
            }
        }

    })
}

//  ----------------------------------------------------需要修改------------------------------------


function getAndSetOrgSubRules() {
    // 根据页面的年月2022-06，遍历获取该年份的orgSubRules中的2022-06的所有规则
    // 遍历当前日历，遇到匹配的orgSubRules则，设为可高亮可预约。
    // 对于小于今天的数据continue
    let apply_date_row = ($("#yearMonth").text());
    // console.log(apply_date_row);
    let split = apply_date_row.split('-');
    if (split[1] < 10) {
        split[1] = "0" + split[1];
    }

    console.log(org_id);

    let join = split.join("-");

    // 如果org_id为空，则弹出提示
    if (org_id === null || org_id === '') {
        layer.msg("请先选择机构");
    }

    $.ajax({
        async: false,
        url: '/civil/orgSubscribeRulesMobile/findOrgSubRulesList',
        data: {
            'id': org_id,
            'state': '0'
        },
        type: "get",
        dataType: "json",
        success: function (response) {
            if (Array.isArray(response.data) && response.data.length >= 0) {
                let ruleList = response.data;
                for (let i = 0; i < ruleList.length; i++) {
                    // 比较orgRule中的time与今天的时间，如果小于今天，直接跳过
                    if (new Date(ruleList[i].time).before(new Date())) {
                        continue;
                    }


                    if (ruleList[i].time.slice(0, 7) === join) {
                        let availableNum = parseInt(ruleList[i].limit_person) - parseInt(ruleList[i].booking_person)
                        let day = (ruleList[i].time.slice(8, 10));

                        if (day < 10) {
                            day = day.slice(1, 2);
                        }
                        let li = $("#dateList [name='" + day + "']");
                        li.attr("class", "date");
                        li.children("p").html("可预约<br/>" + availableNum)
                        // $(".xzbq li:contains('" + labelArray[i] + "')").addClass('on');
                    }
                }
            }
        },
        error: function (response) {
            console.log(response)
        }
    });
}


Date.prototype.before = function (date, format) {
    if (!format) return date.getTime() > this.getTime()
    let d1 = new Date(this.format(format)),
        d2 = new Date(date.format(format));
    return d2.getTime() > d1.getTime()
}


function setPackInfoNameForJiGou() {
    $('#jigouPackInfoList').empty();
    $('#taocan').text("请选择套餐");
    $.ajax({
        async: false,
        url: '/civil/packInfo/getPackInfoList',
        type: 'get',
        data: {
            'packYear': packYear,
            'orgId': org_id,
            'packSource': '1' // 机构自定义套餐
        },
        success: function (response) {
            jigouPackInfoList = response.data;
            // console.log(response)
            if (Array.isArray(jigouPackInfoList) && jigouPackInfoList.length > 0) {
                for (let i = 0; i < jigouPackInfoList.length; i++) {
                    let li = "<li data-id=" + jigouPackInfoList[i].id + " data-content=''>" +
                        "<div class='tp_tit'>" + jigouPackInfoList[i].pack_name + "</div></li>";
                    $('#jigouPackInfoList').append(li);
                }

            }
        }
    })
}

function regExpPhone(str) {
    var pattern = /^((1[3,5,8,7,9][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/;
    return pattern.test(str);
}

function setPackInfoTongYong() {
    $.ajax({
        async: false,
        url: '/civil/packInfo/getPackInfoList',
        type: 'get',
        data: {
            'packYear': packYear,
            'packSource': '2' // 通用
        },
        success: function (response) {
            tongyongPackInfoList = response.data;
            console.log(tongyongPackInfoList)
            if (Array.isArray(tongyongPackInfoList) && tongyongPackInfoList.length > 0) {
                for (let i = 0; i < tongyongPackInfoList.length; i++) {
                    let li = "<li data-id=" + tongyongPackInfoList[i].id + " data-content=''>" +
                        "<div class='tp_tit'>" + tongyongPackInfoList[i].pack_name + "</div></li>";
                    $('#tongYongPackInfoList').append(li);
                }
            }
        }
    })
}

// function getCardId() {
//     debugger
//     const cardId = $("#decryptedCardId").attr("value");
//     if(null === cardId || '' === cardId) {
//         console.log("未获得身份证号");
//     }
//     console.log(cardId)
//     return cardId;
// }


