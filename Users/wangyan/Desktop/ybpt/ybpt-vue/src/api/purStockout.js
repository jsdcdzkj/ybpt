import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

export function getPage(data) {
    return request({
        url: '/purStockout/getPage',
        method: 'post',
        data,
    })
}

export async function insertOrUpdate(data) {
    var result = {};
    await axios.post(baseURL + "purStockout/insertOrUpdate", data, {
        headers: {
            "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
        }
    }).then((res) => {
        result = res;
        console.info(res);
    });
    return result;
    // return request({
    //     url: '/purStockout/insertOrUpdate',
    //     method: 'post',
    //     data,
    // })
}

export async function uploadFile(form) {
    var result = {};
    await axios.post(baseURL + "purStockout/uploadFile", form, {
        headers: {
            "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
        }
    }).then((res) => {
        result = res;
        console.info(res);
    });
    return result;
}

export function selectOne(data) {
    return request({
        url: '/purStockout/selectOne/',
        method: 'post',
        params:data,
    })
}

export function getFile(data) {
    return request({
        url: '/purStockout/getFile/' + data,
        method: 'post',
    })
}

export function purStockoutExport(data) {
    return request({
        url: '/purStockout/export',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}