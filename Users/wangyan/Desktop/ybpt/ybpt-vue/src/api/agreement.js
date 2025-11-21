import request from '@/utils/request'

export function getAgreement(data) {
    return request({
        url: '/agreement/getAgreement',
        method: 'post'
    })
}

export function saveOrUpdate(data) {
    return request({
        url: '/agreement/saveOrUpdate',
        method: 'post',
        data
    })
}