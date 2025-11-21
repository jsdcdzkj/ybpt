import request from '@/utils/request'

export function viewPdf(data) {
    return request({
        url: '/liquidation/viewPdf',
        method: 'post',
        params: data,
    })
}

export function getList(data) {
    return request({
        url: '/liquidation/getList',
        method: 'post',
        data,
    })
}

export function signConfirmation(data) {
    return request({
        url: '/liquidation/signConfirmation',
        method: 'post',
        params:data,
    })
}


export function exportList(data) {
    return request({
        url: '/liquidation/exportList',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}








