import request from '@/utils/request'



export function saveEmpSubscribeRecord(data) {
    return request({
        url: '/empSubscribeRecord/saveEmpSubscribeRecord',
        method: 'post',
        data,
    })
}


export function findEmpSubscribeRecordOne(data) {
    return request({
        url: '/empSubscribeRecord/findEmpSubscribeRecordOne',
        method: 'post',
        params: {
            id: data
        }
    })
}


export function findEmpSubscribeRecord(data) {
    return request({
        url: '/empSubscribeRecord/findEmpSubscribeRecord',
        method: 'post',
        data,
    })
}


export function backoutSubscribe(data) {
    return request({
        url: '/empSubscribeRecord/backoutSubscribe',
        method: 'post',
        params: {
            id: data
        }
    })
}



export function findCivilworkerNotIn(data) {
    return request({
        url: '/empSubscribeRecord/findCivilworkerNotIn',
        method: 'get',
        params: {
            id: data.id,
            year: data.year,
            name: data.name
        }
    })
}




export function findCivilworkerIn(data) {
    return request({
        url: '/empSubscribeRecord/findCivilworkerIn',
        method: 'get',
        params: {
            id: data.id,
            year: data.year,
            name: data.name
        }
    })
}


export function excel(data) {
    return request({
        url: '/empSubscribeRecord/excel',
        method: 'get',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params: {
            time: data.time,
            empName: data.uo_name,
            packName: data.pack_name,
            pageNo: data.pageNo,
            pageSize: data.pageSize,
            uoid: data.uo_id,

        }
    })
}