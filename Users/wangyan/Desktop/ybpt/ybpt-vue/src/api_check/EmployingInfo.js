import request from '@/utils/request_civil'

export function selectList(data) {
    return request({
        url: '/employing_info/selectList',
        method: 'post',
        data,
    })
}

export function updateEmployingInfo(data) {
    return request({
        url: '/employing_info/updateEmployingInfo',
        method: 'post',
        data,
    })
}


export function info(data) {
    return request({
        url: '/employing_info/info?id=' + data,
        method: 'post',
    })
}

export function selectByEmpCode() {
    return request({
        url: '/employing_info/selectByEmpCode',
        method: 'post',
    })
}

export function getDepartmentListByEmpCode(data) {
    return request({
        url: '/employing_info/getDepartmentListByEmpCode',
        method: 'get',
        params: {
            empCode: data
        }
    })
}

export function saveDepartment(data) {
    return request({
        url: '/employing_info/saveDepartment',
        method: 'post',
        data
    })
}






