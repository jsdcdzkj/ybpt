function getCardId() {
    const cardId = $("#decryptedCardId").attr("value");
    if(null === cardId || '' === cardId) {
        console.log("未获得身份证号");
    }
    console.log(cardId)
    return cardId;
}

var fileServerUrl = "http://www.wangyan.zone:8888"
