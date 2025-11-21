import request from '@/utils/request'

export function selectPharInfo(data) {
  return request({
    url: '/pharInfo/selectPharInfo',
    method: 'post',
    data,
  })
}

