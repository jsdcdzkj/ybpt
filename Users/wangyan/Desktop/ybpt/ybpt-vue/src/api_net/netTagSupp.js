import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";


export function getList(data) {
    return request({
        url: '/tagsupp/selectNetTagSupp',
        method: 'post',
        data,
    })
}

//补充协议管理-补充协议列表查询
export function getNetTagSuppList(data) {
    return request({
        url: '/tagsupp/getList',
        method: 'post',
        data,
    })
}

//补充协议管理新增修改
export function editSupp(data) {
    return request({
        url: '/tagsupp/insertNetTagSupp',
        method: 'post',
        data,
    })
}

//补充协议（医保端）列表
export function getAllNetTagMechanism(data) {
    return request({
        url: '/tagsupp/getAllNetTagMechanism',
        method: 'post',
        data,
    })
}

//上传文件

let urlUpload = baseURL + "tagsupp/upload";

export async function uploadFile(form) {
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

//文件ID获取文件信息
export function getFileInfo(data) {
    return request({
        url: '/tagsupp/getFileInfo',
        method: 'post',
        data,
    })
}

//文件ID获取文件路径
export function getFileUrl(data) {
    return request({
        url: '/tagsupp/getFileUrl',
        method: 'post',
        data,
    })
}

export function getSupById(data) {
    return request({
        url: '/tagsupp/getOneNetTagSupp',
        method: 'post',
        data,
    })
}

//医药机构段获取补充协议列表
export function getMechanismList(data) {
    return request({
        url: '/tagsupp/getMechanismList',
        method: 'post',
        data,
    })
}

//查看详情
export function getInfoById(data) {
    return request({
        url: '/tagsupp/getInfoById',
        method: 'post',
        data,
    })
}

//医药机构端确认补充协议按钮
export function mechanismSure(data) {
    return request({
        url: '/tagsupp/mechanismSure',
        method: 'post',
        data,
    })
}
//验证是否可以网签申请
export function validateApply(data) {
    return request({
        url: '/tagsupp/validateApply',
        method: 'post',
    })
}