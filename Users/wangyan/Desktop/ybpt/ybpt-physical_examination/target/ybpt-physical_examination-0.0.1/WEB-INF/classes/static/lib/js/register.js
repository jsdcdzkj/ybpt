$(function () {
    $(".popup").show();
    isDel();
    eyeOpen();
    eyeOpenAg()

    $(".btn-left").click(function () {
        let openid = $("#openId").text();
        window.location.replace("/civil/user/toLogin?openid=" + openid + "&idcard=" + getCardId());
    })

    $("#register").click(function () {
        // let certno = $("#sdkIdentity").val().trim();
        // let realName = $("#sdkRealName").val().trim();
        // console.log('$("#sdkIdentity").val()')
        // console.log($("#sdkIdentity").val())
        if ($("#sdkIdentity").val() === '') {
            layer.msg('身份证号不能为空', {icon: 1, time: 1200}, function () {
            });
            return;
        }



        if ($("#sdkRealName").val() === '') {
            layer.msg('真实姓名不能为空', {icon: 1, time: 1200}, function () {
            });
            return;
        }

        if (!($(".agreement-box").children("i").hasClass("checked"))) {
            layer.msg('请选中免责协议', {icon: 1, time: 1200}, function () {
            });
            return;
        }

        let certno = $("#sdkIdentity").val().trim();
        let name = $("#sdkRealName").val().trim();

        if (getCardId() !== certno) {
            layer.msg("输入的身份证号与当前授权的微信用户不一致", {icon: 2, time: 1700}, function () {
            })
            return;
        }

        let register = JSON.stringify({
            certno,
            name,
        });

        $.ajax({
            async: false,
            url: "/civil/user/register",
            type: "post",
            contentType: "application/json",
            dateType: "json",
            data: register,
            dataType: "json",
            success: function (response) {
                if (response.code === 0) {
                    layer.msg('注册成功', {icon: 1, time: 1000}, function () {
                        window.location.replace("/civil/personSubscribeRecord/toApplyPersonSubscribeRecord?cardId=" + getCardId());
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

    // $("#register").click(function () {
    //     var certno = $("#sdkIdentity").val().trim();
    //     var login_Name = $("#sdkUsername").val();
    //     var pwd = $("#sdkPassword").val();
    //     var passwordConfirm = $("#passwordConfirm").val();
    //     var realName = $("#realName").val();
    //     if (!($(".agreement-box").children("i").hasClass("checked"))) {
    //         layer.msg('请选中免责协议', {icon: 1, time: 1200}, function () {
    //             return false;
    //         });
    //         return
    //     }
    //     if (getCardId() !== certno) {
    //         layer.msg("输入的身份证号与当前授权的微信用户不一致",  {icon:2, time:1500}, function () {
    //
    //         })
    //         return;
    //     }
    //
    //     if (isOpen === '0') {
    //         layer.msg("密码规则：字母+数字+特殊符号+长度为8-16位",  {icon:2, time:1500}, function () {
    //         })
    //         return;
    //     }
    //
    //
    //     if (passwordConfirm !== pwd) {
    //         layer.msg('两次密码输入不一致', {icon: 1, time: 1000}, function () {
    //             $("#sdkPassword").val("");
    //             $("#passwordConfirm").val("");
    //             $("#sdkPassword").focus();
    //             return false;
    //         });
    //     }
    //
    //     var register = JSON.stringify({
    //         "certno": certno,
    //         "login_Name": login_Name,
    //         "passwordConfirm": passwordConfirm,
    //         'pwd': pwd,
    //     });
    //
    //     $.ajax({
    //         async: false,
    //         url: "/civil/user/register",
    //         type: "post",
    //         contentType: "application/json",
    //         dateType: "json",
    //         data: register,
    //         dataType: "json",
    //         success: function (response) {
    //             if (response.code === 0) {
    //                 layer.msg('注册成功', {icon: 1, time: 1000}, function () {
    //                     window.location.replace("/civil/personSubscribeRecord/toApplyPersonSubscribeRecord?cardId=" + getCardId());
    //                 });
    //             } else {
    //                 layer.msg(response.msg, {icon: 2, time: 1000}, function () {
    //                     return false;
    //                 });
    //             }
    //         },
    //         error: function (response) {
    //             console.log(response)
    //         }
    //     })
    // })

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

        $(".checkbox").addClass('checked')

        //if (!_self.hasClass('dis')) {
        $(".popup").hide();
        //location.href="info.html";
        // }

    });

    function isDel() {
        // if ($("#sdkUsername").val().length >= 1) {
        //     $("#uiEmptyUsername").show();
        // }
        $("#sdkRealName").keyup(function () {
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

        $("#passwordConfirm").keyup(function () {
            if ($(this).val().length >= 1) {
                $("#uiEmptyPasswordAg").show();
            } else {
                $("#uiEmptyPasswordAg").hide();
            }
        })

        $(".input-empty").click(function () {
            $(this).siblings("input").val("");
            $(this).hide();
            $("#sdkSubmit").hide();
            $("#uiSdkSubmit").show();
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

    function eyeOpenAg() {
        $('#uiShowTextAg').click(function () {
            let pass_type = $('#passwordConfirm').attr('type');
            if (pass_type === 'password') {
                $('#passwordConfirm').attr('type', 'text');
                $('#uiShowTextAg').find('i').removeClass('icon-biyanjing').addClass('icon-yanjing_yincang');
            } else {
                $('#passwordConfirm').attr('type', 'password');
                $('#uiShowTextAg').find('i').removeClass('icon-yanjing_yincang').addClass('icon-biyanjing');
            }
        })
    }

    function regExpPassword(str) {
        var pattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@#&()]).{8,16}$/;
        return pattern.test(str);
    }

    $("#sdkPassword").blur(function () {
        if (regExpPassword($(this).val())) {
            isOpen = '1';
            $('.tips-password').hide();
        } else {
            $('.tips-password').show()

        }
    });
})

var isOpen = "0";