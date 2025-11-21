import request from '@/utils/request'

export function upload(data) {
  return request({
    url: '/common/upload',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    method: 'post',
    data,
  })
}

export function readImg(data) {
  return request({
    url: '/common/readImg?filePath='+data,
    method: 'get'
  })
}

export function upData(data) {
  return request({
    url: '/common/upData?dic_type_code='+data,
    method: 'post'
  })
}





