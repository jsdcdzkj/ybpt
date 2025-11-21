import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

export function getMedicalCareAbnormalList(data) {
    return request({
        url: '/medicalAbnormal/getMedicalCareAbnormalList',
        method: 'post',
        params:data,
    })
}

//上传文件
let urlUpload = baseURL + "medicalAbnormal/importData";
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

let urlUpload2 = baseURL + "medicalAbnormal/importData2";
export async function importData2(form) {
    var result = {};
    if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
        await axios.post(urlUpload2, form, {
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

export function exportmedicalAbnormalData(data) {
    return request({
        url: '/medicalAbnormal/exportmedicalAbnormalData',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function exportSettleAbnormalData(data) {
    return request({
        url: '/medicalAbnormal/exportSettleAbnormalData',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function getUploadNo(data) {
    return request({
        url: '/medicalAbnormal/getUploadNo',
        method: 'post',
        params:data,
    })
}



