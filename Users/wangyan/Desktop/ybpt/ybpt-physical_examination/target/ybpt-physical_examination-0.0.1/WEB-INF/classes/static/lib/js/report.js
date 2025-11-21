$(function () {
    $('.btn-left').on('click', function () {
        window.location.replace('/civil/personSubscribeRecord/psrList?cardId=' + getCardId());
    });
})

