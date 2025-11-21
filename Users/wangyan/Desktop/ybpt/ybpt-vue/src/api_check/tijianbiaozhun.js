import request from '@/utils/request_civil'

export function getList(data) {
    return request({
        url: '/PhysExamConfig/getList', method: 'post', params: data,
    })
}

export function toEdit(data) {
    return request({
        url: '/PhysExamConfig/toEdit', method: 'post', params: data,
    })
}

export function edit(data) {
    return request({
        url: '/PhysExamConfig/edit', method: 'post', params: data,
    })
}