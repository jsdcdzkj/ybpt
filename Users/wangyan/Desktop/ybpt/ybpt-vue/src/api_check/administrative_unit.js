import request from '@/utils/request_civil'

export function selectListAll() {
  return request({
    url: '/administrative_unit/selectListAll',
    method: 'post',
  })
}


