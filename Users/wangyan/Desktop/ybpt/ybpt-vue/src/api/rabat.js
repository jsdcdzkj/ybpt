import request from '@/utils/request'

export function opspDiseRabit() {
    //点击菜单
    var jsonData = "function ceshis() {\n" +
        "    $('.swiper-slide-div').click();\n" +
        "    setTimeout(function () {\n" +
        "        $('span[class=\\\"menu-title-context\\\"]').each(function (i) {\n" +
        "            if ($(this).html() == '门慢门特登记') {\n" +
        "                $(this).click()\n" +
        "            }\n" +
        "        });\n" +
        "    }, 100)\n" +
        "}ceshis();"
    const rabit = {
        frameName: "home",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function selectIdCard(data) {
    //查询身份证信息
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}" +
        "function enter(name) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var event = document.createEvent('Event');\n" +
        "    event.initEvent('keyup', true, false);\n" +
        "    event = Object.assign(event, {\n" +
        "        ctrlKey: false,\n" +
        "        metaKey: false,\n" +
        "        altKey: false,\n" +
        "        which: 13,\n" +
        "        keyCode: 13,\n" +
        "        key: 'Enter',\n" +
        "        code: 'Enter'\n" +
        "    });\n" +
        "    dom.focus();\n" +
        "    dom.dispatchEvent(event);\n" +
        "}" +
        "function ceshis() {\n" +
        "    keyUpValue('#certno', '" + data + "');\n" +
        "    enter('#certno'); setTimeout(function () {$('.ant-btn-success').click();}, 900)\n" +
        "}   ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}


export function agentAdd(agnterName, type, type2, agnterCertno, agnterTel, agnterAddr) {
    //选择代办人
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}" +
        "function ceshis() {\n" +
        "    document.querySelector('.ant-drawer-wrapper-body .ant-drawer-body').firstChild.firstChild.nextElementSibling.firstChild.nextElementSibling.nextElementSibling.nextElementSibling.firstChild.firstChild.lastChild.click();\n" +
        "    keyUpValue('#agnterName', '" + agnterName + "');\n" +
        "    $('#agnterCertType').click();\n" +
        "    setTimeout(function () {\n" +
        "        var dom = document.querySelector('#agnterCertType').parentNode.lastChild.firstChild.firstChild.firstChild.firstChild;\n" +
        "        for (var i = 0; i < dom.children.length; i++) {\n" +
        "            if (dom.children[i].innerHTML == '" + type + "') {\n" +
        "                dom.children[i].click();\n" +
        "                setTimeout(function () {\n" +
        "                    $('#agnterRlts').click();\n" +
        "                    var dom1 = document.querySelector('#agnterRlts').parentNode.lastChild.firstChild.firstChild.firstChild.firstChild;\n" +
        "                    console.log(dom1);\n" +
        "                    for (var i = 0; i < dom1.children.length; i++) {\n" +
        "                        if (dom1.children[i].innerHTML == '" + type2 + "') {\n" +
        "                            dom1.children[i].click();\n" +
        "                        }\n" +
        "                    }\n" +
        "                }, 900);\n" +
        "            }\n" +
        "        }\n" +
        "    }, 900);\n" +
        "    keyUpValue('#agnterCertno', '" + agnterCertno + "');\n" +
        "    keyUpValue('#agnterTel', '" + agnterTel + "');\n" +
        "    keyUpValue('#agnterAddr', '" + agnterAddr + "');\n" +
        "}ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}


export function selectMedinsCode(medins_code) {
    //选择机构
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}" +
        " function ceshis() {\n" +
        "    $('#ideFixmedinsNo').click();\n" +
        "    setTimeout(function () {\n" +
        "        keyUpValue('.ant-modal-body #fixmedinsCode', '" + medins_code + "');\n" +
        "        setTimeout(function () {\n" +
        "            $('.ant-modal-body .ant-btn-primary').click();\n" +
        "            setTimeout(function () {\n" +
        "                $('.ant-modal-body .ant-radio-input').click();\n" +
        "                $('.ant-modal-footer .ant-btn-primary').click();\n" +
        "            }, 900);\n" +
        "        }, 200);\n" +
        "    }, 600);\n" +
        "} ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function selectPharCode(phar_code) {
    //选择药师
    var jsonData = "function ceshis() {\n" +
        "    $('#diagDrCode').click();\n" +
        "    setTimeout(function () {\n" +
        "        keyUpValue('.ant-modal-body #pracPsnNo', '" + phar_code + "');\n" +
        "        setTimeout(function () {\n" +
        "            $('.ant-modal-body .ant-btn-primary').click();\n" +
        "            setTimeout(function () {\n" +
        "                $('.ant-modal-body .ant-radio-inner').click();\n" +
        "                $('.ant-modal-footer .ant-btn-primary').click();\n" +
        "            }, 900);\n" +
        "        }, 200);\n" +
        "    }, 600);\n" +
        "} ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}


export function time(hospIdeDate, begainDate, enddate, appyDate, dcla) {
    //选择时间
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}" +
        "function enter(name) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var event = document.createEvent('Event');\n" +
        "    event.initEvent('keyup', true, false);\n" +
        "    event = Object.assign(event, {\n" +
        "        ctrlKey: false,\n" +
        "        metaKey: false,\n" +
        "        altKey: false,\n" +
        "        which: 13,\n" +
        "        keyCode: 13,\n" +
        "        key: 'Enter',\n" +
        "        code: 'Enter'\n" +
        "    });\n" +
        "    dom.focus();\n" +
        "    dom.dispatchEvent(event);\n" +
        "}" +
        "function ceshis() {\n" +
        "    $('#hospIdeDate .ant-calendar-picker-input').click();\n" +
        "    setTimeout(function () {\n" +
        "        keyUpValue('.ant-calendar-panel .ant-calendar-input', '" + hospIdeDate + "');\n" +
        "        enter('.ant-calendar-panel .ant-calendar-input');\n" +
        "        setTimeout(function () {\n" +
        "            document.querySelector('#ideFixmedinsNo').parentNode.parentNode.parentNode.parentNode.parentNode.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.lastChild.firstChild.firstChild.firstChild.firstChild.firstChild.click();\n" +
        "            setTimeout(function () {\n" +
        "                keyUpValue('.ant-calendar-panel .ant-calendar-input', '" + begainDate + "');\n" +
        "                enter('.ant-calendar-panel .ant-calendar-input');\n" +
        "                setTimeout(function () {\n" +
        "                    document.querySelector('#ideFixmedinsNo').parentNode.parentNode.parentNode.parentNode.parentNode.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.lastChild.firstChild.firstChild.firstChild.firstChild.firstChild.click();\n" +
        "                    setTimeout(function () {\n" +
        "                        keyUpValue('.ant-calendar-panel .ant-calendar-input', '" + enddate + "');\n" +
        "                        enter('.ant-calendar-panel .ant-calendar-input');\n" +
        "                        setTimeout(function () {\n" +
        "                            document.querySelector('#ideFixmedinsNo').parentNode.parentNode.parentNode.parentNode.parentNode.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.lastChild.firstChild.firstChild.firstChild.firstChild.firstChild.click();\n" +
        "                            setTimeout(function () {\n" +
        "                                keyUpValue('.ant-calendar-panel .ant-calendar-input', '" + appyDate + "');\n" +
        "                                enter('.ant-calendar-panel .ant-calendar-input');\n" +
        "                            }, 500);\n" +
        "                        }, 500);\n" +
        "                    }, 500);\n" +
        "                }, 500);\n" +
        "            }, 500);\n" +
        "        }, 500);\n" +
        "        $('#dclaSouc').click();\n" +
        "        setTimeout(function () {\n" +
        "            var dom = document.querySelector('.ant-select-dropdown-content ul');\n" +
        "            for (var i = 0; i < dom.children.length; i++) {\n" +
        "                if (dom.children[i].innerHTML == '" + dcla + "') {\n" +
        "                    dom.children[i].click();\n" +
        "                }\n" +
        "            }\n" +
        "        }, 300);\n" +
        "    }, 500);\n" +
        "} ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}


export function reason(appyRea) {
    //选择药师
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}" +
        "function ceshis() {\n" +
        "    keyUpValue('#appyRea', '" + appyRea + "')\n" +
        "} ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function opspDise(opsp_dise_code, diseType, medins_code, beginDate, endDate) {
    //选择病种
    var jsonData = "function enter(name){\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var event = document.createEvent('Event');\n" +
        "    event.initEvent('keyup', true, false);\n" +
        "    event = Object.assign(event, {\n" +
        "        ctrlKey: false,\n" +
        "        metaKey: false,\n" +
        "        altKey: false,\n" +
        "        which: 13,\n" +
        "        keyCode: 13,\n" +
        "        key: 'Enter',\n" +
        "        code: 'Enter'\n" +
        "    });\n" +
        "    dom.focus();\n" +
        "    dom.dispatchEvent(event);\n" +
        "}\n" +
        "function keyUpValue(name, value){\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}\n" +
        "function ceshis() {\n" +
        "    var beginDate  ='" + beginDate + "' ; var endDate = '" + endDate + "' ;  $('#opspDiseCode').click();\n" +
        "    setTimeout(function () {\n" +
        "        keyUpValue('.ant-modal-body #opspDiseCode', '" + opsp_dise_code + "');\n" +
        "    }, 300);\n" +
        "    setTimeout(function () {\n" +
        "        $('#diseType').click();\n" +
        "    }, 600);\n" +
        "    setTimeout(function () {\n" +
        "        var dom = document.querySelector('#diseType').parentNode.lastChild.firstChild.firstChild.firstChild.firstChild;\n" +
        "        for (var i = 0; i < dom.children.length; i++) {\n" +
        "            if (dom.children[i].innerHTML == '" + diseType + "') {\n" +
        "                dom.children[i].click();\n" +
        "                $('.ant-modal-body .ant-btn-primary').click();\n" +
        "                setTimeout(function () {\n" +
        "                    $('.ant-modal-body .ant-radio-input').click();\n" +
        "                    $('.ant-modal-footer .ant-btn-primary').click();\n" +
        "                    keyUpValue('#fixmedinsCode', '" + medins_code + "');\n" +
        "                }, 1100);\n" +
        "            }\n" +
        "        }\n" +
        "    }, 900);\n" +
        "    if (beginDate != '' && beginDate != undefined && beginDate != null) {\n" +
        "    setTimeout(function () {\n" +
        "            document.querySelector('#fixmedinsCode').parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.lastChild.firstChild.firstChild.firstChild.firstChild.firstChild.click();\n" +
        "            setTimeout(function () {\n" +
        "                keyUpValue('.ant-calendar-panel .ant-calendar-input', '" + beginDate + "');\n" +
        "                enter('.ant-calendar-panel .ant-calendar-input');\n" +
        "                if(endDate != '' && endDate != undefined && endDate != null){\n" +
        "                    setTimeout(function () {\n" +
        "                        document.querySelector('#fixmedinsCode').parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.lastChild.firstChild.firstChild.firstChild.firstChild.firstChild.click();\n" +
        "                        setTimeout(function () {\n" +
        "                            keyUpValue('.ant-calendar-panel .ant-calendar-input', '" + endDate + "');\n" +
        "                            enter('.ant-calendar-panel .ant-calendar-input');\n" +
        "                        }, 500);\n" +
        "                    }, 800);\n" +
        "                }\n" +
        "            }, 500);\n" +
        "    }, 1900);\n" +
        "    }else if(endDate != '' && endDate != undefined && endDate != null){\n" +
        "        setTimeout(function () {\n" +
        "            document.querySelector('#fixmedinsCode').parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.firstChild.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.lastChild.firstChild.firstChild.firstChild.firstChild.firstChild.click();\n" +
        "            setTimeout(function () {\n" +
        "                keyUpValue('.ant-calendar-panel .ant-calendar-input', '" + endDate + "');\n" +
        "                enter('.ant-calendar-panel .ant-calendar-input');\n" +
        "            }, 500);\n" +
        "        }, 1900);\n" +
        "    }\n" +
        "    setTimeout(function (){\n" +
        "        $('.ant-card-body .ant-btn-primary').click();\n" +
        "    }, 6400);\n" +
        "}\n" +
        "ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}


export function saveData() {
    //选择
    var jsonData = "function ceshis() {$('.footer .ant-btn-primary').click();$('.ant-modal-content .ant-btn-primary').click();}ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function tip() {
    //获取成功提示
    var jsonData = "function ceshi() {$('.ant-modal-content .ant-btn-primary').click();}ceshi();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: "function ceshi(){\n" +
            "    var content = \"\";\n" +
            "    if (document.querySelector('.ant-modal-content .ant-modal-confirm-content')) {\n" +
            "        content = document.querySelector('.ant-modal-content .ant-modal-confirm-content').innerHTML;\n" +
            "    }\n" +
            "    return content;\n" +
            "}\n" +
            "ceshi();"
    }
    return rabit;
}

export function errTip() {
    //获取错误提示
    var jsonData = ""
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: "function ceshi(){return document.querySelector('.ant-message .ant-message-error span').innerHTML}ceshi();"
    }
    return rabit;
}

export function closeErrTip() {
    //关闭错误提示
    var jsonData = ""
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: "function ceshi() {\n" +
            "    var content =\"\" ; \n" +
            " console.log(document.querySelector('.ant-message .ant-message-error span'));   if(document.querySelector('.ant-message .ant-message-error span')){\n" +
            "        content = document.querySelector('.ant-message .ant-message-error span').innerHTML ;\n" +
            "    }\n" +
            "    return content ;\n" +
            "}\n" +
            "ceshi();"
    }
    return rabit;
}


export function successMed() {
    //点击知道了
    var jsonData = "function ceshi() { $('.ant-modal-content .ant-btn-primary').click(); \n" +
        "}\n" +
        "ceshi();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function closeAll() {
    //关闭页面
    var jsonData = "function ceshi() { $('#tab-N040201_xz .el-icon-close').click(); \n" +
        "}\n" +
        "ceshi();"
    const rabit = {
        frameName: "home",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function auditCloseAll() {
    //关闭页面
    var jsonData = "function ceshi() { document.querySelector('#tab-N040201_01 .el-icon-close').click() ;  \n" +
        "}\n" +
        "ceshi();"
    const rabit = {
        frameName: "home",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function auditMenu() {
    //门慢特审核选择菜单
    var jsonData = "function ceshis(){$('.swiper-slide-div').click();setTimeout(function(){$('span[class=\\\"menu-title-context\\\"]').each(function(i){if($(this).html()=='门慢门特登记审核'){$(this).click()}});},100)} ceshis();"
    const rabit = {
        frameName: "home",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function checkSelect(psn_cert_type, dise_type_code, certno) {
    //门慢特审批查询
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}\n" +
        "function enter(name) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var event = document.createEvent('Event');\n" +
        "    event.initEvent('keyup', true, false);\n" +
        "    event = Object.assign(event, {\n" +
        "        ctrlKey: false,\n" +
        "        metaKey: false,\n" +
        "        altKey: false,\n" +
        "        which: 13,\n" +
        "        keyCode: 13,\n" +
        "        key: 'Enter',\n" +
        "        code: 'Enter'\n" +
        "    });\n" +
        "    dom.focus();\n" +
        "    dom.dispatchEvent(event);\n" +
        "}\n" +
        "function ceshis() {\n" +
        "    document.querySelector('#psnCertType').click();\n" +
        "    setTimeout(function () {\n" +
        "        var dom = document.querySelector('.ant-select-dropdown-content').parentNode.lastChild.firstChild;\n" +
        "        for (var i = 0; i < dom.children.length; i++) {\n" +
        "            console.log(dom.children[i].innerHTML);\n" +
        "            if (dom.children[i].innerHTML == '" + psn_cert_type + "') {\n" +
        "                dom.children[i].click();\n" +
        "                setTimeout(function () {\n" +
        "                    document.querySelector('#diseTypeCode').click();\n" +
        "                    setTimeout(function () {\n" +
        "                        var dom = document.querySelector('.ant-select-dropdown-content').parentNode.parentNode.parentNode.nextElementSibling.firstChild.firstChild.firstChild.firstChild;\n" +
        "                        for (var i = 0; i < dom.children.length; i++) {\n" +
        "                            console.log(dom.children[i].innerHTML);\n" +
        "                            if (dom.children[i].innerHTML == '" + dise_type_code + "') {\n" +
        "                                dom.children[i].click();\n" +
        "                                keyUpValue('#certno', '" + certno + "');\n" +
        "                                setTimeout(function () {\n" +
        "                                    enter('#certno');\n" +
        "                                    setTimeout(function () {\n" +
        "                                        $('.search-btn .ant-btn-primary').click();\n" +
        "                                    }, 500);\n" +
        "                                }, 500);\n" +
        "                            }\n" +
        "                        }\n" +
        "                    }, 900);\n" +
        "                }, 900);\n" +
        "            }\n" +
        "        }\n" +
        "    }, 900);\n" +
        "}\n" +
        "ceshis();"
    const rabit = {
        frameName: "N040201_01",
        scriptInput: jsonData,
        scriptOut: "function ceshi() {\n" +
            "    var content = \"\" ;\n" +
            "   console.log(content); if(document.querySelector('.ant-message .ant-message-info span')){\n" +
            "        content = document.querySelector('.ant-message .ant-message-info span').innerHTML ;\n" +
            "   console.log(content); }\n" +
            "    return content ;\n" +
            "}\n" +
            "ceshi();"
    }
    return rabit;
}


export function auditSave(associationId, result, reason) {
    //门慢特审批提交
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}\n" +
        "function ceshis() {\n" +
        "    setTimeout(function () {\n" +
        "   var trArr = $('.ant-table-body .ant-table-fixed .ant-table-tbody tr ');\n" +
        "    for (var p = 0; p < trArr.length; p++) {\n" +
        "        var tdArr = $(trArr[p]).find('td');\n" +
        "        for (var k = 0; k < tdArr.length; k++) {\n" +
        "            var text = $(tdArr[k]).find('span').text();\n" +
        "            if (text == '" + associationId + "') {\n" +
        "                var cs = $(tdArr[k]);\n" +
        "                $(cs).prev().prev().prev().children().children().children().click();\n" +
        "                setTimeout(function () {\n" +
        "                    $('.body .ant-card-extra .ant-btn-primary').click();\n" +
        "                    setTimeout(function () {\n" +
        "                        document.querySelector('#result').click();\n" +
        "                    }, 500);\n" +
        "                    setTimeout(function () {\n" +
        "                        var dom = document.querySelector('#result').parentNode.lastChild.firstChild.firstChild.firstChild.firstChild;\n" +
        "                        for (var i = 0; i < dom.children.length; i++) {\n" +
        "                            console.log(dom.children[i].innerHTML);\n" +
        "                            if (dom.children[i].innerHTML == '" + result + "') {\n" +
        "                                dom.children[i].click();\n" +
        "                                keyUpValue('#opinion', '" + reason + "');\n" +
        "                                setTimeout(function () {\n" +
        "                                    $('.ant-modal-footer .ant-btn-primary').click();\n" +
        "                                }, 500);\n" +
        "                            }\n" +
        "                        }\n" +
        "                    }, 900);\n" +
        "                }, 900);\n" +
        "            }\n" +
        "        }\n" +
        "    }\n" +
        "}, 2000);\n" +
        "}\n" +
        "ceshis();"
    const rabit = {
        frameName: "N040201_01",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function auditSuccesstip() {
    //获取成功提示
    var jsonData = "function ceshis() {document.querySelector('.ant-modal-content .ant-modal-confirm-content').parentNode.parentNode.parentNode.nextElementSibling.firstChild.firstChild.click();}ceshis();"
    const rabit = {
        frameName: "N040201_01",
        scriptInput: jsonData,
        scriptOut: "function ceshis() {var content = '';if (document.querySelector('.ant-modal-content .ant-modal-confirm-content')) {content = document.querySelector('.ant-modal-content .ant-modal-confirm-content').innerHTML;}return content;}ceshis();"
    }
    return rabit;
}

function keyUpValue(name, value) {
    var dom = document.querySelector(name);
    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});
    dom.value = value;
    dom.dispatchEvent(evt);
}

export function selectCxIdCard(data) {
    //门慢特查询列表
    var jsonData = "function keyUpValue(name, value) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false});\n" +
        "    dom.value = value;\n" +
        "    dom.dispatchEvent(evt);\n" +
        "}\n" +
        "function enter(name) {\n" +
        "    var dom = document.querySelector(name);\n" +
        "    var event = document.createEvent('Event');\n" +
        "    event.initEvent('keyup', true, false);\n" +
        "    event = Object.assign(event, {\n" +
        "        ctrlKey: false,\n" +
        "        metaKey: false,\n" +
        "        altKey: false,\n" +
        "        which: 13,\n" +
        "        keyCode: 13,\n" +
        "        key: 'Enter',\n" +
        "        code: 'Enter'\n" +
        "    });\n" +
        "    dom.focus();\n" +
        "    dom.dispatchEvent(event);\n" +
        "}\n" +
        "function ceshis() {\n" +
        "    keyUpValue('#certno', '" + data + "');\n" +
        "    enter('#certno');\n" +
        "    setTimeout(function () {\n" +
        "        $('.search-btn .ant-btn-primary').click();\n" +
        "        setTimeout(function () {\n" +
        "            $('.ant-pagination-options .ant-select-selection__rendered').click();\n" +
        "            setTimeout(function () {\n" +
        "                document.querySelector('.ant-select-dropdown-content').lastChild.lastChild.click();\n" +
        "                $('.ant-select-dropdown-content').lastChild.lastChild.click();\n" +
        "            }, 600)\n" +
        "        }, 600)\n" +
        "    }, 600)\n" +
        "}\n" +
        "ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}

export function revokeRabit(isApprove, isDel, certNo, disease) {
    //门慢特撤销
    var jsonData = "function ceshis() {\n" +
        "    var trArr = $('.ant-table-fixed .ant-table-tbody tr');\n" +
        "\n" +
        "    for (var i = 0; i < trArr.length; i++) {\n" +
        "        var text = trArr[i].innerText;\n" +
        "        if (text.indexOf('" + isApprove + "') >= 0 && text.indexOf('" + isDel + "') >= 0 && text.indexOf('" + certNo + "') >= 0 && text.indexOf('" + disease + "') >= 0) {\n" +
        "            trArr[i].lastChild.firstChild.firstChild.lastChild.firstChild.click();\n" +
        "            setTimeout(function () {\n" +
        "                $('.ant-modal-content .ant-btn-primary').click();\n" +
        "            }, 500);\n" +
        "        }\n" +
        "    }\n" +
        "}\n" +
        "ceshis();"
    const rabit = {
        frameName: "N040201",
        scriptInput: jsonData,
        scriptOut: ""
    }
    return rabit;
}



