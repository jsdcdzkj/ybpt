import request from '@/utils/request'

/**
 * 查询列表分页
 * @param data
 * @returns {*}
 */
export function geAgreementtList(data) {
    return request({
        url: '/tagagreement/selectNetTagAgreement',
        method: 'post',
        data,
    })
}

/**
 * 查询列表
 */
export function getList(data) {
    return request({
        url: '/tagagreement/getList',
        method: 'post',
        data,
    })
}

/**
 * 根据登录用的得到甲方信息
 */
export function selectNetTagAgreementByLogin() {
    return request({
        url: '/tagagreement/selectNetTagAgreementByLogin',
        method: 'post',
    })
}

/**
 * 协议管理新增
 */
export function editXygl(data) {
    return request({
        url: '/tagagreement/insertNetTagAgreement',
        method: 'post',
        data,
    })
}

/**
 * id查询
 */
export function getOneInfo(data) {
    return request({
        url: '/tagagreement/getOneInfo',
        method: 'post',
        data,
    })
}

/**
 * updateNetTagAgreementStatus 改变状态
 */
export function updateNetTagAgreementStatus(data) {
    return request({
        url: '/tagagreement/updateNetTagAgreementStatus',
        method: 'post',
        data,
    })
}

/**
 * 查询回流库机构
 */
export function selectHlkList(data) {
    return request({
        url: '/tagagreement/selectHlkList',
        method: 'post',
        data,
    })
}


//获取医疗机构和零售药店列表
export function getJgYdList(data) {
    return request({
        url: '/medins/getAll.do',
        method: 'post',
        data,
    })
}