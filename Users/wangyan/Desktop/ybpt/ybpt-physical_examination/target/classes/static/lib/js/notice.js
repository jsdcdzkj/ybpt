$(function () {
    $("#bell").on("click", function () {
        console.log("点击了铃铛")
        window.location.replace("/civil/user/notice.page?cardId=" + getCardId());
    })
})

function downloadNoticeFile(fileUrl) {
    var user = navigator.userAgent;
    //android端
    var isAndroid = user.indexOf("Android") > -1 || user.indexOf("Adr") > -1;
    //ios端
    var isiOS = !!user.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
    if (isAndroid) {
        window.open(civilServerUrl + fileUrl);
    } else if (isiOS) {
        //这个是ios操作系统
        window.location.href = civilServerUrl + fileUrl;
    }
}



