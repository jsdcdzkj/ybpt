import request from '@/utils/request'

export function getDiseList(data) {
  return request({
    url: '/diseaseMutex/selectDiseaseList',
    method: 'post',
    params: data,
  })
}

export function getDiseaseMutexList(data) {
  return request({
    url: '/diseaseMutex/selectDiseaseMutexList',
    method: 'post',
    data,
  })
}

export function saveDiseaseMutex(data) {
  return request({
    url: '/diseaseMutex/saveDiseaseMutex',
    method: 'post',
    data,
  })
}

export function delDiseaseMutex(data) {
  return request({
    url: '/diseaseMutex/delDiseaseMutex',
    method: 'post',
    data,
  })
}