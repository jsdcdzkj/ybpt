import request from '@/utils/request'

export function selectMedicalDeptList(data) {
  return request({
    url: '/medicalOrg/selectMedicalDept_page',
    method: 'post',
    params:data,
  })
}
export function exportMedicalDept(data) {
  return request({
    url: '/medicalOrg/exportMedicalDept',
    method: 'post',
    params:data,
    responseType:'blob'
  })
}