$(function () {
    $('.btn-left').on('click', function () {
        window.history.back();
        window.location.replace("/civil/user/userCenter")
    });

    $(".submit").on('click', function () {
        let oralPwd = $("[name='oralPwd']").val();
        let newPwd =  $("[name='newPwd']").val();
        let passwordConfirm = $("[name='passwordConfirm']").val();
        let updatePwd = JSON.stringify({
            "pwd": oralPwd,
            "newPassword": newPwd,
            "passwordConfirm": passwordConfirm
        });
        $.ajax({
            async: false,
            url: "modifyPassword",
            type: "post",
            data: updatePwd,
            contentType: 'application/json',
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    layer.msg(response.data, {icon: 1, time: 1000}, function () {
                        $.ajax({
                            async: false,
                            url: "logout",
                            type: "post",
                            dataType: "json",
                            success: function (response) {
                                if (response.code === 0) {
                                    layer.msg(response.data, {icon: 1, time: 1000}, function () {
                                        window.location.replace("/civil/user/toLogin");
                                    });
                                } else {
                                    layer.msg(response.msg, {icon: 2, time: 2000}, function () {
                                        return false;
                                    });
                                }
                            },
                            error: function (response) {
                                console.log(response)
                            }
                        });
                    });
                } else {
                    layer.msg(response.msg, {icon: 2, time: 1000}, function () {
                        return false;
                    });
                }
            },
            error: function (response) {
                console.log(response)
            }
        })
    })
})