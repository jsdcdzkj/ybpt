$(function () {
    console.log("评价初始化-------------")
    var fwpj = 4;
    var zyx = 4;
    var td = 4;
    var sxx = 4;
    var label = '';
    var remark = '';
    var id = $("#psrId").attr("data-value")
    initReview()

    function initReview() {
        $.ajax({
            async: false,
            url: "/civil/review/getReviewByPsrId",
            type: "get",
            data: {
                "id": id,
                "cardId": getCardId()
            },
            // contentType: 'application/json',
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    console.log(response);
                    // 如果查到服务评价记录，则把记录回显在在页面,并不能再次提交
                    if (response.data.service_star !== null) {// 如果查到服务评价记录，则把记录回显在在页面
                        var result = response.data;
                        fwpj = result.service_star;
                        zyx = result.professional_star;
                        td = result.service_status_star;
                        sxx = result.react_star;
                        remark = result.service_review;
                        label = result.service_label;

                        // 回显 服务评价星级 fwpj
                        initFwpj(fwpj);

                        // 回显 专业性星级 zyx
                        initXyx(zyx)

                        // 回显服务态度星级 td
                        initTd(td)

                        initSxx(sxx);

                        // 对标签进行回显
                        initLabel(label);

                        // 对提交按钮改为disable, text改为已评论
                        $('#submit').text("谢谢，您已评论").attr("disabled", 'disabled');
                        // 回显评论内容,只读
                        if (remark == null || remark === '') {
                            $('textarea').prop('placeholder', "").attr('readonly', 'readonly');
                        } else {
                            $('textarea').prop('placeholder', remark).attr('readonly', 'readonly');
                        }
                    }
                }
            },
            error: function (response) {
                console.log(response)
            }
        })
    }

    // 回显服务星级
    function initFwpj(fwpj) {
        let children = $('#fwpj span').parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= fwpj; i++) {
            $(children[i]).addClass("on");
        }
    }

    // 回显专业性
    function initXyx(zyx) {
        let children = $('#zyx span').parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= zyx; i++) {
            $(children[i]).addClass("on");
        }
    }

    // 回显服务态度星级
    function initTd(td) {
        let children = $('#td span').parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= td; i++) {
            $(children[i]).addClass("on");
        }
    }

    // 回显时效性
    function initSxx(sxx) {
        let children = $('#sxx span').parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= sxx; i++) {
            $(children[i]).addClass("on");
        }
    }

    // 对标签进行回显
    function initLabel(label) {
        if (label != null && label !== "") {
            var labelArray = label.split(',');
            for (let i = 0; i < labelArray.length; i++) {
                $(".xzbq li:contains('" + labelArray[i] + "')").addClass('on');
            }
        }
    }

    // 评价打分
    $('#fwpj span').click(function () {
        let children = $(this).parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= $(this).index(); i++) {
            $(children[i]).addClass("on");
        }
        fwpj = $(this).index();
        console.log(fwpj);
    })


    // 专业性
    $('#zyx span').click(function () {
        let children = $(this).parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= $(this).index(); i++) {
            $(children[i]).addClass("on");
        }
        zyx = $(this).index();
        console.log("zyx");
        console.log(zyx);
    })

    // 态度
    $('#td span').click(function () {
        let children = $(this).parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= $(this).index(); i++) {
            $(children[i]).addClass("on");
        }
        td = $(this).index();
    })

    // 时效性
    $('#sxx span').click(function () {
        let children = $(this).parent().children();
        children.removeAttr('class');
        for (let i = 0; i <= $(this).index(); i++) {
            $(children[i]).addClass("on");
        }
        sxx = $(this).index();
        console.log("sxx");
        console.log(sxx);
    })

// 标签选择
    $('.tags ul li').click(function () {
        $(this).toggleClass('on')
        var lis = document.getElementsByClassName('lis on');//数组
        var lisLen = lis.length;
        var list = [];
        for (var i = 0; i < lisLen; i++) {
            //var存在变量提升, lis[i].index = i 为事先存储元素的索引值。若不写，打印的元素索引值会一直是元素的总长度。
            lis[i].index = i;//想省略这句，在for循环中，可用es6中的let替换var
            // lis[i].onclick = function(){
            // 	console.log(this.index);//元素索引值
            // }
            list[i] = lis[i].innerHTML;
        }
        label = list.join(",");
        // label=$(".tags li[class='on']").attr("value");
        console.log(label)
    })

    $(".btn-left").click(function () {
        window.location.replace("/civil/personSubscribeRecord/psrList?cardId=" + getCardId());
    })

// 提交
    $('#submit').click(function () {
        remark = $('#remark').val();
        let personSubscribeRecord = {
            "service_star": fwpj,
            "professional_star": zyx,
            "service_status_star": td,
            "react_star": sxx,
            "service_label": label,
            "service_review": remark,
            "id": id
        };
        console.log(personSubscribeRecord)
        $.ajax({
            async: false,
            url: "/civil/review/save",
            type: "post",
            data: JSON.stringify(personSubscribeRecord),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                console.log(res)
                if (res.code === 0) {
                    layer.msg("评价成功", {icon: 1, time: 1200}, function () {
                        window.location.replace("/civil/personSubscribeRecord/psrList?cardId=" + getCardId());
                    });
                } else {
                    layer.msg(res.msg, {icon: 2, time: 1500}, function () {
                    });
                }
            },
            error: function (response) {
                console.log(response)
            }
        })
    })


})