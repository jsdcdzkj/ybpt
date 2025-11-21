import request from '@/utils/request'

export function medinsInfoBVoPage(data) {
  return request({
    url: '/medinsInfo/medinsInfoBVoPage',
    method: 'post',
    data,
  })
}

export function selectMedinsInfoType(data) {
  return request({
    url: '/medinsInfo/selectMedinsInfoType',
    method: 'post',
    data,
  })
}


