import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

export function selectList(data) {
  return request({
    url: '/capitalSettlement/getList',
    method: 'post',
    data,
  })
}

export function detailInfo(data) {
    return request({
        url: '/capitalSettlement/detailInfo',
        method: 'post',
        data,
    })
}


export function oneInfo(data) {
    return request({
        url: '/capitalSettlement/oneInfo?id='+data,
        method: 'post',
    })
}

export function edit(data) {
    return request({
        url: '/capitalSettlement/edit',
        method: 'post',
        data,
    })
}

export function delQs(data) {
    return request({
        url: '/capitalSettlement/delQs',
        method: 'post',
        data,
    })
}

export function createConfirming(data) {
    return request({
        url: '/capitalSettlement/createConfirming?id='+data,
        method: 'post',
    })
}


//上传文件
let urlUpload = baseURL + "capitalSettlement/importData";
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
