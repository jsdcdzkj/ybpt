import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

export function getSettleErrorDataList(data) {
    return request({
        url: '/settleErrorData/getSettleErrorDataList',
        method: 'post',
        params:data,
    })
}

//上传文件
let urlUpload = baseURL + "settleErrorData/importData";
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


export function exportSettleErrorDataData(data) {
    return request({
        url: '/settleErrorData/exportSettleErrorDataData',
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
        url: '/settleErrorData/getUploadNo',
        method: 'post',
        params:data,
    })
}



