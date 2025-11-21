import request from '@/utils/request'

export function getList(data) {
    return request({
        url: '/medinsDeptB/getList', method: 'post', params: data,
    })
}

export function getListAll(data) {
    return request({
        url: '/medinsDeptB/getListAll', method: 'post', params: data,
    })
}

export function toEdit(data) {
    return request({
        url: '/medinsDeptB/toEdit', method: 'post', params: data,
    })
}

export function edit(data) {
    return request({
        url: '/medinsDeptB/edit', method: 'post', params: data,
    })
}