$(function () {
    initDepartmentList();
    var deptId = '';
    $('.btn-left').on('click', function () {
        window.location.replace('/civil/user/userCenter?cardId=' + getCardId());
    });

    $('.dept-box li').on('click', function () {
        $(this).siblings().removeClass('active')
        $(this).addClass('active');
        deptId = $(this).attr('id');
    })

    $('.submit').on('click', function () {
        let selectDeptId = ($('.dept-box li.active').attr('id'));
        $.ajax({
            async: false,
            url: "/civil/department/save",
            type: "post",
            data: {
                "deptId": selectDeptId,
                "cardId": getCardId()
            },
            // contentType: 'application/json',
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    layer.msg("保存成功", {icon: 1, time: 1200}, function () {
                        window.location.replace('/civil/department/toDepartment?cardId=' + getCardId());
                    });
                } else {
                    layer.msg(response.msg, {icon: 2, time: 1200}, function () {
                    });
                }
            },
            error: function (response) {
                layer.msg("保存失败", {icon: 2, time: 1500}, function () {
                    console.log(response)
                });
            }
        })
    })

    function initDepartmentList() {
        $.ajax({
            async: false,
            url: "/civil/department/getDepartmentList",
            type: "get",
            data: {
                cardId: getCardId()
            },
            // contentType: 'application/json',
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    if (Array.isArray(response.data)) {
                        let deptBox = "";
                        let result = response.data;
                        for (let i = 0; i < result.length; i++) {
                            if (result[i].isCheck === "1") {
                                deptBox += "<li class='active' id='" + result[i].id + "'><p>" + result[i].dept_name + "</p><span></span></li>";
                            } else {
                                deptBox += "<li id='" + result[i].id + "'><p>" + result[i].dept_name + "</p><span></span></li>";
                            }
                        }
                        $(".dept-box").append(deptBox);
                    }
                }
            },
            error: function (response) {
                layer.msg("", {icon: 2, time: 1500}, function () {
                });
            }
        })
    }
})


