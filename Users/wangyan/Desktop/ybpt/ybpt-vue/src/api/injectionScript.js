import axios from "axios";
import {baseURL} from '@/config'

let url = "http://127.0.0.1:8888/api/service/injectionScript";
// let urlUpload = "http://localhost:8080/common/upload";
let urlUpload = baseURL +"/common/upload";

export function upload(form) {
    if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
        axios.post(urlUpload, form, {
            headers: {
                "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
            }
        });
    }
}

//是否存在 调用非白名单，无法获取用户信息
export function anticon(frameName) {
    return {
        "frameName": frameName,
        "scriptInput": "",
        "scriptOut": "if(document.querySelector('.anticon .anticon-close-circle')){return 1}else{return 0}"
    };
}

export function send(frameName,scriptInput,scriptOut) {
    return {
        "frameName": frameName,
        "scriptInput": scriptInput,
        "scriptOut": scriptOut
    };
}

//审核
export function examine(checkText,proposal) {
    return {
        "frameName": "N040207_01",
        "scriptInput": "examine('" + checkText + "', '" + proposal + "') " +
            " " +
            "function examine(result, opinion) { " +
            "    click('[for=result]', 0, '.ant-select-selection__placeholder'); " +
            "    var insertText = new InputEvent('input', { " +
            "        inputType: 'insertText', data: null, dataTransfer: null, isComposing: false " +
            "    }); " +
            "    setTimeout(function () { " +
            "        var resultLis = document.querySelector('[for=result]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < resultLis.length; i++) { " +
            "            if (resultLis[i].innerHTML == result) { " +
            "                resultLis[i].click(); " +
            "            } " +
            "        } " +
            "        document.querySelector('#opinion').value = opinion; " +
            "        document.querySelector('#opinion').dispatchEvent(insertText) " +
            "        setTimeout(function () { " +
            "            document.querySelector('.ant-modal-footer .ant-btn-primary').click(); " +
            "        }, 500) " +
            "    }, 500) " +
            "} " +
            " " +
            "function click() { " +
            "    if (arguments.length == 1) { " +
            "        document.querySelector(arguments[0]).click(); " +
            "    } else if (arguments.length == 2) { " +
            "        document.querySelector(arguments[0]).parentNode.parentNode.querySelector(arguments[1]).click(); " +
            "    } else if (arguments.length == 3) { " +
            "        document.querySelectorAll(arguments[0])[arguments[1]].parentNode.parentNode.querySelector(arguments[2]).click(); " +
            "    } else if (arguments.length == 4) { " +
            "        document.querySelectorAll(arguments[0])[arguments[1]].parentNode.parentNode.querySelectorAll(arguments[2])[arguments[3]].click(); " +
            "    } " +
            " " +
            "}",
        "scriptOut": ""
    };
}


//点击菜单
export function menu(title,scriptOut) {
    if (scriptOut){
        scriptOut = "function isExist(){if(document.querySelector('"+scriptOut+"')){return 0}}isExist();"
    }
    return {
        "frameName": "home",
        "scriptInput": "function menu() { " +
            "    $('.swiper-slide-div').click(); " +
            "    setTimeout(function () { " +
            "        $('span[class=\"menu-title-context\"]').each(function (i) { " +
            "            if ($(this).html() == '" + title + "') { " +
            "                $(this).click() " +
            "            } " +
            "        }); " +
            "    }, 100) " +
            "} " +
            " " +
            "menu();",
        "scriptOut": scriptOut
    };
}
//关闭选项卡
export function closeTab() {
    post({
        "frameName": "home",
        "scriptInput": "document.querySelector('.el-icon-close').click();",
        "scriptOut": ""
    });
}

//撤消
function cancel() {
    return {
        "frameName": "home",
        "scriptInput": "",
        "scriptOut": ""
    };
}

//点击工具类
export function click(frameName, dom, idx,scriptOut) {
    var scriptInput = "";
    if (idx) {
        scriptInput = "function click(dom) { " +
            "    if (document.querySelectorAll('" + dom + "')[" + idx + "]) { " +
            "        document.querySelectorAll('" + dom + "')[" + idx + "].click(); " +
            "    } " +
            "} " +
            " " +
            "click();";
    } else {
        scriptInput = "function click(dom) { " +
            "    if (document.querySelector('" + dom + "')) { " +
            "        document.querySelector('" + dom + "').click(); " +
            "    } " +
            "} " +
            " " +
            "click();";
    }
    post({
        "frameName": frameName, "scriptInput": scriptInput, "scriptOut": scriptOut
    });
}

export function enterCertno(frameName,certno) {
    return {
        "frameName": frameName,
        "scriptInput": "function enter(name) { " +
            "    var dom = document.querySelector(name); " +
            "    var event = document.createEvent('Event'); " +
            "    event.initEvent('keyup', true, false); " +
            "    event = Object.assign(event, { " +
            "        ctrlKey: false, " +
            "        metaKey: false, " +
            "        altKey: false, " +
            "        which: 13, " +
            "        keyCode: 13, " +
            "        key: 'Enter', " +
            "        code: 'Enter' " +
            "    }); " +
            "    dom.focus(); " +
            "    dom.dispatchEvent(event); " +
            "} " +
            " " +
            "function keyUpValue(name, value) { " +
            "    var dom = document.querySelector(name); " +
            "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false}); " +
            "    dom.value = value; " +
            "    dom.dispatchEvent(evt); " +
            "} " +
            " " +
            "function enterCertno() { " +
            "    keyUpValue('#certno', '" + certno + "'); " +
            "    enter('#certno'); " +
            "} " +
            " " +
            "enterCertno();",
        "scriptOut": "function isExist(){if(document.querySelector('#certno').value == " + certno + "){return 0}}isExist();"
    };
}

export function openMSg() {
    return {
        "frameName": "N040207",
        "scriptInput": "document.querySelector('.ant-btn-success').click();",
        "scriptOut": "function openMSg() { " +
            "    if (document.querySelector('.ant-modal-confirm-content')) { " +
            "        return document.querySelector('.ant-modal-confirm-content').textContent; " +
            "    } " +
            "    ; " +
            "    if (document.querySelector('.ant-drawer-content')) { " +
            "        return 0; " +
            "    } " +
            "    ; " +
            "} " +
            " " +
            "openMSg()"
    };
}

export function setSubmit(form) {
    return {
        "frameName": "N040207",
        "scriptInput": "setSubmit('" + form.fixmedins_code + "', '', '" + form.mdtrtarea_admdvs_name + "', 'E10.000', '', '西医诊断', '" + form.mdtrt_id + "', '" + form.begndate + "', '" + form.enddate + "', '" + form.hosp_agre_refl_flag_name + "', '" + form.reflin_medins_no + "', '', '" + form.refl_date + "', '" + form.refl_setl_flag_name + "', '" + form.dcla_souc_name + "', '" + form.refl_fil_type_name + "', '" + form.tel + "', '" + form.drord + "', '" + form.dise_cond_dscr + "', '" + form.refl_rea + "', '" + form.agnter_name + "', '" + form.agnter_cert_type_name + "', '" + form.agnter_certno + "', '" + form.agnter_tel + "', '" + form.agnter_rlts_name + "', '" + form.agnter_addr + "');" +
            "function setSubmit(fixmedinsCode, fixmedinsName, mdtrtareaAdmdvs, diagCode, diagName, diagType, mdtrtId, begndate, enddate, hospAgreReflFlag, toFixmedinsCode, toFixmedinsName, reflDate, reflSetlFlag, dclaSouc, reflFilType, tel, drord, diseCondDscr, reflRea, agnterName, agnterCertType, agnterCertno, agnterTel, agnterRlts, agnterAddr) { " +
            "    if (document.querySelector('.ant-message .anticon-close')) { " +
            "        document.querySelector('.ant-message .anticon-close').click(); " +
            "    } " +
            "    if(document.querySelectorAll('.ant-card-head-arrow')[1]){ " +
            "        document.querySelectorAll('.ant-card-head-arrow')[1].click(); " +
            "    } " +
            "    click('[for=fixmedinsCode]', 0, '.ant-input-suffix .anticon-search'); " +
            "    click('[for=mdtrtareaAdmdvs]', '.ant-cascader-input'); " +
            "    click('[for=diagCode]', '.ant-input-suffix .anticon-search'); " +
            "    click('[for=hospAgreReflFlag]', '.ant-select-selection__placeholder'); " +
            "    if (toFixmedinsCode != '') { " +
            "        click('[for=fixmedinsCode]', 1, '.ant-input-suffix .anticon-search'); " +
            "    } " +
            "    click('[for=reflSetlFlag]', '.ant-select-selection__placeholder'); " +
            "    click('[for=dclaSouc]', '.ant-select-selection__placeholder'); " +
            "    if (reflFilType != '') { " +
            "        click('[for=reflFilType]', '.ant-select-selection__placeholder'); " +
            "    } " +
            "    inputValue('[for=tel]', '#tel', tel); " +
            "    inputValue('[for=drord]', '#drord', drord); " +
            "    inputValue('[for=diseCondDscr]', '#diseCondDscr', diseCondDscr); " +
            "    inputValue('[for=reflRea]', '#reflRea', reflRea); " +
            "    inputValue('[for=agnterName]', '#agnterName', agnterName); " +
            "    if (agnterCertType != '') { " +
            "        click('[for=agnterCertType]', '.ant-select-selection__placeholder'); " +
            "    } " +
            "    inputValue('[for=agnterCertno]', '#agnterCertno', agnterCertno); " +
            "    inputValue('[for=agnterTel]', '#agnterTel', agnterTel); " +
            "    if (agnterRlts != '') { " +
            "        click('[for=agnterRlts]', '.ant-select-selection__placeholder'); " +
            "    } " +
            "    inputValue('[for=agnterAddr]', '#agnterAddr', agnterAddr); " +
            "    click('[for=begndate]', '.ant-calendar-picker-input'); " +
            "    click('[for=enddate]', '.ant-calendar-picker-input'); " +
            "    click('[for=reflDate]', '.ant-calendar-picker-input'); " +
            "    var insertText = new InputEvent('input', { " +
            "        inputType: 'insertText', " +
            "        data: null, " +
            "        dataTransfer: null, " +
            "        isComposing: false " +
            "    }); " +
            "    var enter = document.createEvent('Event'); " +
            "    enter.initEvent('keyup', true, false); " +
            "    enter = Object.assign(enter, { " +
            "        ctrlKey: false, " +
            "        metaKey: false, " +
            "        altKey: false, " +
            "        which: 13, " +
            "        keyCode: 13, " +
            "        key: 'Enter', " +
            "        code: 'Enter' " +
            "    }); " +
            "    setTimeout(function () { " +
            "        document.querySelectorAll('.ant-calendar-input')[0].value = reflDate; " +
            "        document.querySelectorAll('.ant-calendar-input')[0].dispatchEvent(insertText); " +
            "        document.querySelectorAll('.ant-calendar-input')[0].focus(); " +
            "        document.querySelectorAll('.ant-calendar-input')[0].dispatchEvent(enter); " +
            "        var mdtrtareaAdmdvList = mdtrtareaAdmdvs.split('/'); " +
            "        var mdtrtareaAdmdvsLis = document.querySelector('[for=mdtrtareaAdmdvs]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < mdtrtareaAdmdvsLis.length; i++) { " +
            "            if (mdtrtareaAdmdvsLis[i].innerText == mdtrtareaAdmdvList[0]) { " +
            "                mdtrtareaAdmdvsLis[i].click(); " +
            "            } " +
            "        } " +
            "        var hospAgreReflFlagLis = document.querySelector('[for=hospAgreReflFlag]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < hospAgreReflFlagLis.length; i++) { " +
            "            if (hospAgreReflFlagLis[i].innerHTML == hospAgreReflFlag) { " +
            "                hospAgreReflFlagLis[i].click(); " +
            "            } " +
            "        } " +
            "        var reflSetlFlagLis = document.querySelector('[for=reflSetlFlag]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < reflSetlFlagLis.length; i++) { " +
            "            if (reflSetlFlagLis[i].innerHTML == reflSetlFlag) { " +
            "                reflSetlFlagLis[i].click(); " +
            "            } " +
            "        } " +
            "        var dclaSoucLis = document.querySelector('[for=dclaSouc]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < dclaSoucLis.length; i++) { " +
            "            if (dclaSoucLis[i].innerHTML == dclaSouc) { " +
            "                dclaSoucLis[i].click(); " +
            "            } " +
            "        } " +
            "        var reflFilTypeLis = document.querySelector('[for=reflFilType]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < reflFilTypeLis.length; i++) { " +
            "            if (reflFilTypeLis[i].innerHTML == reflFilType) { " +
            "                reflFilTypeLis[i].click(); " +
            "            } " +
            "        } " +
            "        var agnterCertTypeLis = document.querySelector('[for=agnterCertType]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < agnterCertTypeLis.length; i++) { " +
            "            if (agnterCertTypeLis[i].innerHTML == agnterCertType) { " +
            "                agnterCertTypeLis[i].click(); " +
            "            } " +
            "        } " +
            "        var agnterRltsLis = document.querySelector('[for=agnterRlts]').parentNode.parentNode.querySelectorAll('li'); " +
            "        for (var i = 0; i < agnterRltsLis.length; i++) { " +
            "            if (agnterRltsLis[i].innerHTML == agnterRlts) { " +
            "                agnterRltsLis[i].click(); " +
            "            } " +
            "        } " +
            "        for (var i = 0; i < document.querySelectorAll('.ant-modal-header').length; i++) { " +
            "            if (document.querySelectorAll('.ant-modal-header')[i].innerText == '医疗机构查询' &&  fixmedinsCode != '') { " +
            "                document.querySelectorAll('.ant-modal-content')[i].querySelectorAll('input')[0].value = fixmedinsCode; " +
            "            } " +
            "            if (document.querySelectorAll('.ant-modal-header')[i].innerText == '诊断查询'  &&  diagCode != '') { " +
            "                document.querySelectorAll('.ant-modal-content')[i].querySelectorAll('input')[0].value = diagCode; " +
            "                document.querySelectorAll('.ant-modal-content')[i].querySelector('.ant-select-selection__rendered ').click() " +
            "            } " +
            "            if (document.querySelectorAll('.ant-modal-header')[i].innerText == '转往医疗机构查询' &&  toFixmedinsCode != '') { " +
            "                document.querySelectorAll('.ant-modal-content')[i].querySelectorAll('input')[0].value = toFixmedinsCode; " +
            "            } " +
            "            document.querySelectorAll('.ant-modal-content')[i].querySelectorAll('input')[0].dispatchEvent(insertText); " +
            "        } " +
            " " +
            "        setTimeout(function () { " +
            "            document.querySelectorAll('.ant-calendar-input')[1].value = begndate; " +
            "            document.querySelectorAll('.ant-calendar-input')[1].dispatchEvent(insertText); " +
            "            document.querySelectorAll('.ant-calendar-input')[1].focus(); " +
            "            document.querySelectorAll('.ant-calendar-input')[1].dispatchEvent(enter); " +
            "            var mdtrtareaAdmdvList = mdtrtareaAdmdvs.split('/'); " +
            "            var mdtrtareaAdmdvsLis = document.querySelector('[for=mdtrtareaAdmdvs]').parentNode.parentNode.querySelectorAll('li'); " +
            "            if (mdtrtareaAdmdvList.length >= 2) { " +
            "                for (var i = 0; i < mdtrtareaAdmdvsLis.length; i++) { " +
            "                    if (mdtrtareaAdmdvsLis[i].innerText == mdtrtareaAdmdvList[1]) { " +
            "                        mdtrtareaAdmdvsLis[i].click(); " +
            "                    } " +
            "                } " +
            "            } " +
            "            for (var i = 0; i < document.querySelectorAll('.ant-modal-header').length; i++) { " +
            "                if (document.querySelectorAll('.ant-modal-header')[i].innerText == '诊断查询') { " +
            "                    var diagTypeLi = document.querySelectorAll('.ant-modal-content')[i].querySelectorAll('.ant-select-dropdown-menu-item'); " +
            "                    for (var i = 0; i < diagTypeLi.length; i++) { " +
            "                        if (diagTypeLi[i].innerHTML == diagType) { " +
            "                            diagTypeLi[i].click(); " +
            "                        } " +
            "                    } " +
            "                } " +
            "            } " +
            "            for (var i = 0; i < document.querySelectorAll('.ant-modal-content').length; i++) { " +
            "                document.querySelectorAll('.ant-modal-content')[i].querySelector('.ant-btn-primary').click(); " +
            "            } " +
            "            setTimeout(function () { " +
            "                document.querySelectorAll('.ant-calendar-input')[0].value = enddate; " +
            "                document.querySelectorAll('.ant-calendar-input')[0].dispatchEvent(insertText); " +
            "                document.querySelectorAll('.ant-calendar-input')[0].focus(); " +
            "                document.querySelectorAll('.ant-calendar-input')[0].dispatchEvent(enter); " +
            "                var mdtrtareaAdmdvList = mdtrtareaAdmdvs.split('/'); " +
            "                var mdtrtareaAdmdvsLis = document.querySelector('[for=mdtrtareaAdmdvs]').parentNode.parentNode.querySelectorAll('li'); " +
            "                if (mdtrtareaAdmdvList.length >= 3) { " +
            "                    for (var i = 0; i < mdtrtareaAdmdvsLis.length; i++) { " +
            "                        if (mdtrtareaAdmdvsLis[i].innerText == mdtrtareaAdmdvList[2]) { " +
            "                            mdtrtareaAdmdvsLis[i].click(); " +
            "                        } " +
            "                    } " +
            "                } " +
            "                for (var i = 0; i < document.querySelectorAll('.ant-modal-content').length; i++) { " +
            "                    document.querySelectorAll('.ant-modal-content')[i].querySelector('.ant-radio-inner').click(); " +
            "                } " +
            "                for (var i = 0; i < document.querySelectorAll('.ant-modal-content').length; i++) { " +
            "                    document.querySelectorAll('.ant-modal-content')[i].querySelector('.ant-modal-footer .ant-btn-primary').click(); " +
            "                } " +
            "                document.querySelector('.footer .ant-btn-primary').click(); " +
            "                setTimeout(function () { " +
            "                    document.querySelector('[classname=ant-modal-btns] .ant-btn-primary').click(); " +
            "                }, 2000); " +
            "            }, 2000); " +
            "        }, 2000); " +
            "    }, 2000); " +
            "} " +
            " " +
            "function click() { " +
            "    if (arguments.length == 1) { " +
            "        document.querySelector(arguments[0]).click(); " +
            "    } else if (arguments.length == 2) { " +
            "        document.querySelector(arguments[0]).parentNode.parentNode.querySelector(arguments[1]).click(); " +
            "    } else if (arguments.length == 3) { " +
            "        document.querySelectorAll(arguments[0])[arguments[1]].parentNode.parentNode.querySelector(arguments[2]).click(); " +
            "    } else if (arguments.length == 4) { " +
            "        document.querySelectorAll(arguments[0])[arguments[1]].parentNode.parentNode.querySelectorAll(arguments[2])[arguments[3]].click(); " +
            "    } " +
            "} " +
            " " +
            "function inputValue() { " +
            "    var dom; " +
            "    if (arguments.length == 2) { " +
            "        dom = document.querySelector(arguments[0]); " +
            "    } else if (arguments.length == 3) { " +
            "        dom = document.querySelector(arguments[0]).parentNode.parentNode.querySelector(arguments[1]); " +
            "    } else if (arguments.length == 4) { " +
            "        dom = document.querySelectorAll(arguments[0])[arguments[1]].parentNode.parentNode.querySelector(arguments[2]); " +
            "    } else if (arguments.length == 5) { " +
            "        dom = document.querySelectorAll(arguments[0])[arguments[1]].parentNode.parentNode.querySelectorAll(arguments[2])[arguments[3]]; " +
            "    } " +
            "    var evt = new InputEvent('input', {inputType: 'insertText', data: null, dataTransfer: null, isComposing: false}); " +
            "    if (arguments.length == 2) { " +
            "        dom.value = value = arguments[1]; " +
            "    } else if (arguments.length == 3) { " +
            "        dom.value = arguments[2]; " +
            "    } else if (arguments.length == 4) { " +
            "        dom.value = arguments[3]; " +
            "    } else if (arguments.length == 5) { " +
            "        dom.value = arguments[4]; " +
            "    } " +
            "    dom.dispatchEvent(evt); " +
            "}",
        "scriptOut": "function isExist() {" +
            "     console.log(document.querySelector('.ant-message-notice-content span')) " +
            "    if (document.querySelector('.ant-message-notice-content span')) { " +
            "        return document.querySelector('.ant-message-notice-content span').innerText; " +
            "    } " +
            "    if (document.querySelector('.ant-modal-confirm-success')) { " +
            "        document.querySelector('.ant-modal-confirm-success .ant-btn-primary').click(); " +
            "        return 0; " +
            "    } " +
            "} " +
            " " +
            "isExist();"
    };
}

export function reload() {
    post({
        "frameName": "home",
        "scriptInput": "function reload(){window.location.reload(true);} reload();",
        "scriptOut": ""
    });

}

function post(data) {
    axios.post(url, data).then(function (res) {
    });
}