import request from '@/utils/request'
import axios from "axios";
import { baseURL } from "@/config/setting.config";
//签章 认证
export async function regedit(data) {
    return request({
        url: '/sign/companyAuthentication.do',
        method: 'post',
        data,
    })

}

export function checkAuthentication(data) {
    return request({
        url: '/sign/checkAuthentication.do',
        method: 'post',
        params: data,
    })
}

export function authAutoSign(data) {
    return request({
        url: '/sign/authAutoSign.do',
        method: 'post',
        params: data,
    })
}

export function personalAuthentication(data) {
    return request({
        url: '/sign/personalAuthentication.do',
        method: 'post',
        params: data,
    })
}

export function changeCompanyInfo(data) {
    return request({
        url: '/sign/changeCompanyInfo.do',
        method: 'post',
        params: data,
    })
}

export function getRegisterInfo(data) {
    return request({
        url: '/sign/getRegisterInfo.do',
        method: 'post',
        params: data,
    })
}