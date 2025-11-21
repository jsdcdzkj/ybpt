import request from '@/utils/request_civil'
import {baseURL_civil} from "@/config/setting.config";
import axios from "axios";


export function selectPageList(data) {
    return request({
        url: '/notice/getPageList',
        method: 'post',
        data,
    })
}

export function getPageList2(data) {
    return request({
        url: '/notice/getPageList2',
        method: 'post',
        data,
    })
}


export function getPageListForAccepter(data) {
    return request({
        url: '/notice/getPageListForAccepter',
        method: 'post',
        data,
    })
}


export function saveApi(data) {
    return request({
        url: '/notice/save',
        method: 'post',
        data,
    })
}

export function editApi(data) {
    return request({
        url: '/notice/edit',
        method: 'post',
        data,
    })
}


export function getEntityByIdApi(id) {
    return request({
        url: `/notice/getEntityById/${id}`,
        method: 'get',
    })
}

//上传通知附件
let urlUpload = baseURL_civil + "notice/importData";
export function importData(form, fn) {
    var result = {};
    if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
         axios.post(urlUpload, form, {
            headers: {
                "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
            }
        }).then((res) => {
            result = res;
            fn(result);
        });
    } else {
        fn(-1)
    }
}

export function deleteById(id) {
    return request({
        url: `/notice/delete/${id}`,
        method: 'post'
    })
}

export function launchApi(id) {
    return request({
        url: `/notice/launch/${id}`,
        method: 'post'
    })
}

export function getTop2NoticeForAccepterAndTotalCountApi() {
    return request({
        url: `/notice/getTop2NoticeForAccepterAndTotalCount`,
        method: 'post'
    })
}

export function getTop2NoticeForAccepterAndTotalCountPage(data) {
    return request({
        url: `/notice/getTop2NoticeForAccepterAndTotalCountPage`,
        method: 'post',
        data,
    })
}

export function recallApi(id) {
    return request({
        url: `/notice/recall/${id}`,
        method: 'post'
    })
}

export function setNoticeIsReadApi(noticeId) {
    return request({
        url: `/notice/setIsRead/${noticeId}`,
        method: 'post'
    })
}
