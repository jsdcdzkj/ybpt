$(function () {
    function initPersonSubscribeRecordList(type) {
        if (type === undefined) {
            type = "all";
        }
        // 初始化，获取预约列表

        $.ajax({
            async: false,
            url: "/civil/personSubscribeRecord/getPersonSubscribeRecordList/" + type,
            type: "post",
            data: {
                cardId: getCardId()
            },
            // contentType: 'application/json',
            dataType: "json",
            success: function (response) {
                showPersonSubscribeList(response);
            },
            error: function (response) {
                console.log(response)
            }
        })
    }

    initPersonSubscribeRecordList();

    $('#home').on('click', function () {
        window.location.replace('/civil/personSubscribeRecord/toApplyPersonSubscribeRecord?cardId=' + getCardId());
    });

    $('#member').on('click', function () {
        window.location.replace('/civil/user/userCenter?cardId=' + getCardId());
    });

    $("#all").click(function () {
        // localStorage.setItem("schedule", "all");
        initPersonSubscribeRecordList("all");
    })

// 待体检
    $("#subscribed").click(function () {
        // localStorage.setItem("schedule", "un");
        initPersonSubscribeRecordList("un");
    })

// 已体检
    $("#completed").click(function () {
        // localStorage.setItem("schedule", "ed");
        initPersonSubscribeRecordList("ed");
    })

    function showPersonSubscribeList(response) {
        $("#personSubscribeList").empty();
        if (response.code === 0) {
            if (response.data !== null && Array.isArray(response.data)) {
                const list = response.data;
                let id;
                let oname;
                let year;
                let apply_date;
                let sched;
                let packMoney;
                let packName;
                for (let i = 0; i < list.length; i++) {
                    id = list[i].id;
                    oname = list[i].oname;
                    year = list[i].year + "年";
                    apply_date = list[i].apply_date;
                    sched = list[i].sched;
                    packMoney = list[i].packMoney;
                    packName = list[i].pname;

                    // let serviceStar = list[i].service_star;

                    let row1 =

                        "<div class='row1'>" +
                        "<div class='ordernum'>" +
                        "<span class='iconfont icon-shijian2'></span>" +
                        "<span>" + oname + "</span>" +
                        "</div>" +
                        "<div class='state green'>" + sched + "</div>" +
                        "</div>";
                    let row2 =
                        "<div class='row2'>" +
                        "<div class='item-row'>" +
                        "<span class='p1'>套餐名称</span>" +
                        "<span class='p2'>" + packName + "</span>" +
                        "</div>" +

                        "<div class='item-row'>" +
                        "<span class='p1'>套餐年份</span>" +
                        "<span class='p2'>" + year + "</span>" +
                        "</div>" +
                        // "<div class='item-row'>" +
                        // "<span class='p1'>套餐金额：</span>" +
                        // "<span class='p2'>" + packMoney + "</span>" +
                        // "</div>" +
                        "<div class='item-row'>" +
                        "<span class='p1'>预约时间：</span>" +
                        "<span class='p2'>" + apply_date + "</span>" +
                        "</div>" +
                        "</div>";
                    // let reviewStatus = "";
                    // if (serviceStar == null) {
                    //     reviewStatus = "已评价";
                    // } else {
                    //     reviewStatus = "未评价"
                    // }
                    let row4;

                    switch (sched) {
                        case "已上传报告": {
                            row4 = "<div class='row4'>" +
                                "<div data-name=" + '"' + id + '"' + " class='btns report' onclick='report(this)'" + ">在线查看</div>" +
                                "<div data-name=" + '"' + id + '"' + " class='btns downloadReport' onclick='downloadReport(this)'" + ">下载报告</div>" +
                                "<div id=" + '"' + id + '"' + " onclick='review(id)' class='btns review' " + ">评价</div>" +
                                "</div>"
                            break;
                        }
                        case "待体检": {

                            row4 = `<div class='row4'><div id='${id}' class='btns jiaofei recall' onclick="recall('${id}')">撤消</div></div>`;
                            // row4 = "<div class='row4'><div class='btns jiaofei recall' onclick='recall(id)'" + ">撤消</div></div>";

                            break;
                        }
                        case "已体检": {
                            row4 = "<div class='row4'><div id=" + '"' + id + '"' + " onclick='review(id)' class='btns review'" + ">评价</div></div>";
                            break;
                        }
                        default: {
                            row4 = "<div></div>"
                        }
                    }
                    let li = "<li>" + row1 + row2 + row4 + "</li>";

                    $("#personSubscribeList").append(li)
                }
            }
        } else {
            $("#personSubscribeList").empty();
            $("#personSubscribeList").append("<div class=\"text\">还没有数据</div>")
        }
    }

    // $(".recall").on("click", function () {
    //     console.log("点击了撤销recall");
    //     // console.log($(this).attr("id"))
    //     let personSubscribeRecordId = $(this).attr("id");
    //     // console.log('personSubscribeRecordId------>' + personSubscribeRecordId)
    //     $.ajax({
    //         async: false,
    //         url: "/civil/personSubscribeRecord/backOut",
    //         type: "post",
    //         data: {
    //             "personSubscribeRecordId": personSubscribeRecordId,
    //             "cardId": getCardId()
    //         },
    //         // contentType: 'application/json',
    //         dataType: "json",
    //         success: function (response) {
    //             console.log(response)
    //             if (response.code === 0) {
    //                 layer.msg("撤销成功", {icon: 1, time: 1500}, function () {
    //                     window.location.replace("/civil/personSubscribeRecord/psrList?cardId=" + getCardId());
    //                 });
    //             } else {
    //                 layer.msg(response.msg, {icon: 2, time: 1800}, function () {
    //                     return false;
    //                 });
    //             }
    //         },
    //         error: function (response) {
    //             console.log(response)
    //         }
    //     })
    // })

})

function downloadReport(obj) {
    let psrId = $(obj).attr("data-name");
    $.ajax({
        async: false,
        url: "/civil/personSubscribeRecord/getReport",
        type: "get",
        data: {
            "psrId": psrId,
            "cardId": getCardId()
        },
        // contentType: 'application/json',
        dataType: "json",
        success: function (response) {
            console.log(response)
            if (response.code === 0) {
                var user = navigator.userAgent;
                //android端
                var isAndroid = user.indexOf("Android") > -1 || user.indexOf("Adr") > -1;
                //ios端
                var isiOS = !!user.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
                if (isAndroid) {
                    window.open("https://tj.xzybzx.org.cn/file/" + response.data);
                }else if (isiOS) {
                    //这个是ios操作系统
                    window.location.href = "https://tj.xzybzx.org.cn/file/" + response.data;
                }

            } else {
                layer.msg(response.msg, {icon: 2, time: 1800}, function () {
                    return false;
                });
            }
        },
        error: function (response) {
            console.log(response)
        }
    })
}

function review(id) {
    window.location.replace("/civil/review/reviewPage?psrId=" + id + "&cardId=" + getCardId());
}

function report(obj) {
    let psrId = $(obj).attr("data-name");
    window.location.replace("/civil/personSubscribeRecord/report?psrId=" + psrId + "&cardId=" + getCardId());
}

function recall(id) {
    $.ajax({
        async: false,
        url: "/civil/personSubscribeRecord/backOut",
        type: "post",
        data: {
            "personSubscribeRecordId": id,
            "cardId": getCardId()
        },
        // contentType: 'application/json',
        dataType: "json",
        success: function (response) {
            console.log(response)

            if (response.code === 0) {
                layer.msg("撤销成功", {icon: 1, time: 1500}, function () {
                    window.location.replace("/civil/personSubscribeRecord/psrList?cardId=" + getCardId());
                });
            } else {
                layer.msg(response.msg, {icon: 2, time: 1800}, function () {
                    return false;
                });
            }
        },
        error: function (response) {
            console.log(response)
        }
    })
}

