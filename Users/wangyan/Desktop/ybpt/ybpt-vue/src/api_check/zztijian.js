import request from '@/utils/request_civil'

export function getList(data) {
    return request({
        url: '/AutonomousMedical/getList', method: 'post', params: data,
    })
}

export function toEdit(data) {
    return request({
        url: '/AutonomousMedical/toEdit', method: 'post', params: data,
    })
}

export function edit(data) {
    return request({
        url: '/AutonomousMedical/edit', method: 'post', params: data,
    })
}