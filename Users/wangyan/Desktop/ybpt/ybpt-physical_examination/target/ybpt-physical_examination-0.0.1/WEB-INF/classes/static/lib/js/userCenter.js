$(function () {
    console.log("个人中心")

    $('#updpwd').on('click', function () {
        window.location.replace('/civil/user/toUserModifyPwd');
    });


    $('.btn-left').on('click', function () {
        window.location.replace('/civil/personSubscribeRecord/toApplyPersonSubscribeRecord?cardId='+ getCardId());
    });

    $('#department').on('click', function () {
        window.location.replace("/civil/department/toDepartment?cardId=" + getCardId())
    })

    $('.submit').on('click', function () {
        $.ajax({
            async: false,
            url: "/civil/user/logout",
            type: "post",
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    layer.msg(response.data, {icon: 1, time: 1000}, function () {
                        window.location.replace("/civil/user/toLogin");
                    });
                } else {
                    layer.msg(response.msg);
                }
            },
            error: function (response) {
                console.log(response)
            }
        });
    })
})
