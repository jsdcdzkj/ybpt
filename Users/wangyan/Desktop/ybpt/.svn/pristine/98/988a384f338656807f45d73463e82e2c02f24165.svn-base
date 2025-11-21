import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

export function getList(data) {
    return request({
        url: '/checkSuspicions/getList',
        method: 'post',
        params:data,
    })
}

export function getUploadNo(data) {
    return request({
        url: '/checkSuspicions/getUploadNo',
        method: 'post',
        params:data,
    })
}

export function audit(data) {
    return request({
        url: '/checkSuspicions/audit',
        method: 'post',
        data
    })
}

export function lookFileInfo(data) {
    return request({
        url: '/checkSuspicions/lookFileInfo',
        method: 'post',
        params:data,
    })
}

export function attachmentUploading(data) {
    return request({
        url: '/checkSuspicions/attachmentUploading',
        method: 'post',
        data
    })
}


//上传文件
let urlUpload = baseURL + "checkSuspicions/importData";
export async function importData(form) {
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


export function exportCheckSuspicionsData(data) {
    return request({
        url: '/checkSuspicions/exportCheckSuspicionsData',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function downloadOfEvidence(rid) {
    return request({
        url: '/checkSuspicions/downloadOfEvidence?rid='+rid,
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        }
    })
}
