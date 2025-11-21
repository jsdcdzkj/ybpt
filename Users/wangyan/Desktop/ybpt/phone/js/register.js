$(function () {
    isDel();
    eyeOpen();
    eyeOpenAg()

    $(".checkbox").click(function () {
        $(this).toggleClass("checked");
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

        $(".checkbox").addClass('checked')

        //if (!_self.hasClass('dis')) {
        $(".popup").hide();
        //location.href="info.html";
        // }

    });
})

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
        if ($(this).val().length >= 6) {
            $("#sdkSubmit").show();
            $("#uiSdkSubmit").hide();
        } else {
            $("#sdkSubmit").hide();
            $("#uiSdkSubmit").show();
        }
    });

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
        let pass_type = $('#sdkPasswordAg').attr('type');
        if (pass_type === 'password') {
            $('#sdkPasswordAg').attr('type', 'text');
            $('#uiShowTextAg').find('i').removeClass('icon-biyanjing').addClass('icon-yanjing_yincang');
        } else {
            $('#sdkPasswordAg').attr('type', 'password');
            $('#uiShowTextAg').find('i').removeClass('icon-yanjing_yincang').addClass('icon-biyanjing');
        }
    })
}