import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";

export function getPsnInfoB(data) {
    return request({
        url: '/reflAppyEvtC/getPsnInfoB', method: 'post', params: data,
    })
}

export function getReflAppyEvtC(data) {
    return request({
        url: '/reflAppyEvtC/getReflAppyEvtC', method: 'post', params: data,
    })
}

export function reflAppyEvtCPrint(data) {
    return request({
        url: '/reflAppyEvtC/print', method: 'post', params: data,
    })
}

export function reflAppyEvtCDownload(data) {
    return request({
        url: '/reflAppyEvtC/download', method: 'get', params: data,
    })
}

export function picList(data) {
    return request({
        url: '/reflAppyEvtC/picList', method: 'post', params: data,
    })
}

//上传文件
let urlUpload = baseURL + "reflAppyEvtC/upload";
export async function upload(form) {
    var result = {};
    if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
        await axios.post(urlUpload, form, {
            headers: {
                "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
            }
        }).then((res) => {
            result = res;
            console.info(res);
        });
    }
    return result;
}

export function cancelReflAppyEvtC(data) {
    return request({
        url: '/reflAppyEvtC/cancelReflAppyEvtC', method: 'post', params: data,
    })
}


export function getNatDataDicA(data) {
    return request({
        url: '/reflAppyEvtC/getNatDataDicA', method: 'post', params: data,
    })
}

export function subReflAppyEvt(data) {
    return request({
        url: '/reflAppyEvtC/subReflAppyEvt', method: 'post', params: data,
    })
}

export function getMedinsInfoB(data) {
    return request({
        url: '/reflAppyEvtC/getMedinsInfoB', method: 'post', params: data,
    })
}

export function getMedinsInfoBPage(data) {
    return request({
        url: '/reflAppyEvtC/getMedinsInfoBPage', method: 'post', params: data,
    })
}

export function getMedinsInfoBOne(data) {
    return request({
        url: '/reflAppyEvtC/getMedinsInfoBOne', method: 'post', params: data,
    })
}

export function getDiagList(data) {
    return request({
        url: '/reflAppyEvtC/getDiagList', method: 'post', params: data,
    })
}

export function getNatDataDicAByAdmdvs(data) {
    return request({
        url: '/reflAppyEvtC/getNatDataDicAByAdmdvs', method: 'post', params: data,
    })
}


export function getMdtrtD(data) {
    return request({
        url: '/reflAppyEvtC/getMdtrtD', method: 'post', params: data,
    })
}

export function checkReflAppyEvtC(data) {
    return request({
        url: '/reflAppyEvtC/checkReflAppyEvtC', method: 'post', params: data,
    })
}
