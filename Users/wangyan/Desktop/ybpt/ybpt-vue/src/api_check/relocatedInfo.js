import request from '@/utils/request_civil'



export function findRelocatedInfo(data) {
    return request({
        url: '/relocatedInfo/findRelocatedInfo',
        method: 'post',
        data,
    })
}


export function saveRelocatedInfo(data) {
    return request({
        url: '/relocatedInfo/saveRelocatedInfo',
        method: 'post',
        data,
    })
}


export function updateRelocatedInfo(data) {
    return request({
        url: '/relocatedInfo/updateRelocatedInfo',
        method: 'post',
        data,
    })
}

export function delRelocatedInfo(data) {
    return request({
        url: '/relocatedInfo/delRelocatedInfo',
        method: 'post',
        params: {
            ids: data
        }
    })
}

export function relocatedConfig() {
    return request({
        url: '/IsConfig/relocatedConfig',
        method: 'post',
    })
}

export function upRelocatedConfig(data) {
    return request({
        url: '/IsConfig/upRelocatedConfig',
        method: 'post',
        data,
    })
}

export function yyExport(data) {
    return request({
        url: '/relocatedInfo/export',
        method: 'get',
        params: {
            name: data.name,
            num: data.num,
            pageNo: data.pageNo,
            pageSize: data.pageSize,
        },
        responseType: 'blob'
    })
}