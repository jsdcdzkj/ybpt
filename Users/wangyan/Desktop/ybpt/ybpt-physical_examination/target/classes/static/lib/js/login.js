$(function () {
    isDel();
    eyeOpen();
    $("#sdkSubmit").click(function () {
        let loginInfo = {"login_Name": $("#sdkUsername").val(), "pwd": $("#sdkPassword").val()}
        $.ajax({
            url: "/civil/user/login",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(loginInfo),
            type: 'POST',
            async: false,
            success: function (response) {
                if (response.code === 0) {
                    layer.msg('登录成功', {icon: 1, time: 1000}, function () {
                        window.location.replace("/civil/personSubscribeRecord/toApplyPersonSubscribeRecord");
                    });
                } else {
                    layer.msg('用户名或密码错误', {icon: 2, time: 1200}, function () {
                        return false;
                    });
                }
            }
        });
    })

    // function getUrlParam(i) {
    //     debugger;
    //     let reg = new RegExp("(^|&)" + i + "=([^&]*)(&|$)");
    //     let r = window.location.search.substr(1).match(reg);
    //     return r[2];
    // }

    function isDel() {
        if ($("#sdkUsername").val().length >= 1) {
            $("#uiEmptyUsername").show();
        }
        $("#sdkUsername").keyup(function () {
            if ($(this).val().length >= 1) {
                $("#uiEmptyUsername").show();
            } else {
                $("#uiEmptyUsername").hide();
            }
        });

        $("#sdkPassword").keyup(function () {
            if ($(this).val().length >= 1) {
                $("#uiEmptyPassword").show();
            } else {
                $("#uiEmptyPassword").hide();
            }
            // if ($(this).val().length >= 6) {
            //     $("#sdkSubmit").show();
            //     $("#uiSdkSubmit").hide();
            // } else {
            //     $("#sdkSubmit").hide();
            //     $("#uiSdkSubmit").show();
            // }
        });

        $(".input-empty").click(function () {
            $(this).siblings("input").val("");
            //     $(this).hide();
            // $("#sdkSubmit").hide();
            // $("#uiSdkSubmit").show();
        });
    }


    function eyeOpen() {
        $('#uiShowText').click(function () {
            let pass_type = $('#sdkPassword').attr('type');
            if (pass_type === 'password') {
                $('#sdkPassword').attr('type', 'text');
                $('#uiShowText').find('i').removeClass('icon-biyanjing').addClass('icon-yanjing_yincang');
            } else {
                $('#sdkPassword').attr('type', 'password');
                $('#uiShowText').find('i').removeClass('icon-yanjing_yincang').addClass('icon-biyanjing');
            }
        })
    }


    $("#uiSdkSubmit").on("click", function () {
        if (null == $("#sdkUsername").val() || '' == $("#sdkUsername").val()) {
            layer.msg('登录名不能为空', {icon: 2, time: 1200}, function () {
                return false;
            });
        }
    });
    $("#uiSdkSubmit").on("click", function () {
        if (null == $("#sdkPassword").val() || '' == $("#sdkPassword").val()) {
            layer.msg('密码不能为空', {icon: 2, time: 1200}, function () {
                return false;
            });
            return false;
        }
    });


    $('.fwxy').on('click', function () {
        $(".popup").show();
//        var time = 5;
//    var timeId = setInterval(function () {
//      if (time > 0) {
//        time--;
//        $(".btn").text("请仔细阅读公告(" + time + ")");
//      } else {
//        $(".btn").disabled = false;
//      $(".btn").removeClass("dis");
//        $(".btn").text("我已阅读并同意");
//      }
//    }, 1000)
    });

    $('.icon-close').on('click', function () {
        $(".popup").hide();
    });
    $('.popup .btn').on('click', function () {

        //if (!_self.hasClass('dis')) {
        $(".popup").hide();
        //location.href="info.html";
        // }
    });

    $('#toRegister').on('click', function () {
        window.location.replace("/civil/user/toRegister?cardId=" + getCardId());
    });

})