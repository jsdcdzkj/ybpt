import request from '@/utils/request'

//申诉列表查询
export function getList(data) {
    return request({
        url: '/tagappeal/getAllNetTagAppeal',
        method: 'post',
        data,
    })
}

//id查询
export function getOneNetTagAppeal(data) {
    return request({
        url: '/tagappeal/getOneNetTagAppeal?id=' + data,
        method: 'post',
        // data,
    })
}

//确认
export function getSure(data) {
    return request({
        url: '/tagappeal/getSure?id=' + data,
        method: 'post',
        // data,
    })
}

//申诉保存
export function onSave(data) {
    return request({
        url: '/tagappeal/onSave',
        method: 'post',
        data,
    })
}