import request from '@/utils/request'

export function addBank(data) {
  return request({
    url: '/bank/addBank',
    method: 'post',
    data,
  })
}

export function selectList(data) {
  return request({
    url: '/bank/selectList',
    method: 'post',
    data,
  })
}


export function selectListAll() {
  return request({
    url: '/bank/selectListAll',
    method: 'post',
  })
}




export function delBank(data) {
  return request({
    url: '/bank/delBank',
    method: 'post',
    data,
  })
}

export function updateBank(data) {
  return request({
    url: '/bank/updateBank',
    method: 'post',
    data,
  })
}

export function info(data) {
  return request({
    url: '/bank/info?id='+data,
    method: 'post',
  })
}



