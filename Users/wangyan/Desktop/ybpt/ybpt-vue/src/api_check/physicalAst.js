import request from '@/utils/request_civil'

/**
 * 现场协助登陆
 * @param data
 */
export function getPhysicalAst(data) {
    return request({
        url: '/physicalAst/getEntity',
        method: 'post',
        params: data,
    })
}
export function getAllOrgList(data) {
    return request({
        url: '/physicalAst/getAllOrgList',
        method: 'post',
        params: data,
    })
}
export function setRegist(data) {
    return request({
        url: '/physicalAst/setRegist',
        method: 'post',
        data,
    })
}
export function getBookingNum(data) {
    return request({
        url: '/physicalAst/getBookingNum',
        method: 'post',
        data,
    })
}
//获取当前登陆的机构
export function setLoginOrg() {
    return request({
        url: '/physicalAst/setLoginOrg',
        method: 'post',
    })
}
//机构协助预约
export function commitBook(data) {
    return request({
        url: '/physicalAst/save',
        method: 'post',
        data,
    })
}