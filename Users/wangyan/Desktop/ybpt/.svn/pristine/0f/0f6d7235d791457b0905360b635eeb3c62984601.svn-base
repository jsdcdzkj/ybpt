import request from '@/utils/request'

export function page(data) {
    return request({
        url: '/sbApplyDrug/page',
        method: 'post',
        data,
    })
}

export function insert(data) {
    return request({
        url: '/sbApplyDrug/insert',
        method: 'post',
        data,
    })
}

export function update(data) {
    return request({
        url: '/sbApplyDrug/update',
        method: 'post',
        data,
    })
}


export function down(data) {
    return request({
        url: '/sbApplyDrug/down', method: 'post', params: data,
    })
}

export function bedViewPdf(data) {
    return request({
        url: '/sbApplyDrug/bedViewPdf?id='+data,
        method: 'post',
    })
}

export function drugExport(data) {
    return request({
        url: '/sbApplyDrug/drugExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function batchAudit(data) {
    return request({
        url: '/sbApplyDrug/batchAudit?ids='+data,
        method: 'post',
    })
}


