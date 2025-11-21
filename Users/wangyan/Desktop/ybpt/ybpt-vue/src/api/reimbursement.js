import request from '@/utils/request'

export function getBirthList(data) {
  return request({
    url: '/reimbursement/selectBirthSettlement',
    method: 'post',
    data,
  })
}
export function exportBirthsettlement(data) {
  return request({
    url: '/reimbursement/exportBirthsettlement',
    method: 'post',
    data,
    responseType:'blob'
  })
}
export function getBirthList_org(data) {
  return request({
    url: '/reimbursement/selectBirthSettlement_org',
    method: 'post',
    data,
  })
}
export function exportBirthsettlement_org(data) {
  return request({
    url: '/reimbursement/exportBirthsettlement_org',
    method: 'post',
    data,
    responseType:'blob'
  })
}