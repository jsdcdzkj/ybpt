import request from '@/utils/request'

import {baseURL} from "@/config/setting.config";
import axios from "axios";

//上传文件
let urlUpload = baseURL + "opspDise/uploadPic";
export async function uploadPic(form) {
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

export function OpspDiseList(data) {
  return request({
    url: '/opspDise/OpspDiseList',
    method: 'post',
    data,
  })
}

export function opspCheckList(data) {
    return request({
        url: '/opspDise/opspCheckList',
        method: 'post',
        data,
    })
}

export function checkResult(data) {
    return request({
        url: '/opspDise/checkResult',
        method: 'post',
        data,
    })
}

export function revoke(data) {
    return request({
        url: '/opspDise/revoke?id='+data,
        method: 'post',
    })
}





export function add(data) {
  return request({
    url: '/opspDise/add',
    method: 'post',
    data,
  })
}

export function checkAdd(data) {
    return request({
        url: '/opspDise/checkAdd',
        method: 'post',
        data,
    })
}



export function selectList(data) {
    return request({
        url: '/opspDise/selectList',
        method: 'post',
        data,
    })
}
export function mutuallyExclusiveOrNot(data) {
    return request({
        url: '/opspDise/mutuallyExclusiveOrNot',
        method: 'post',
        data,
    })
}



export function info(data) {
    return request({
        url: '/opspDise/info?id='+data,
        method: 'post',
    })
}

export function diseasesList(data) {
    return request({
        url: '/opspDise/diseasesList?id='+data,
        method: 'post',
    })
}

export function picList(data) {
    return request({
        url: '/opspDise/picList?id='+data,
        method: 'post',
    })
}

export function opspDisePrint(data) {
    return request({
        url: '/opspDise/opspDisePrint?id='+data,
        method: 'post',
    })
}










