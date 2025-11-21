import request from '@/utils/request'


export function selectList(data) {
  return request({
    url: '/evalFormulaConfig/getConfigList',
    method: 'post',
    data,
  })
}

export function addConfigs(data) {
  return request({
    url: '/evalFormulaConfig/addConfigs',
    method: 'post',
    data
  })
}

export function delConfig(data) {
  return request({
    url: '/evalFormulaConfig/delConfig',
    method: 'post',
    data,
  })
}




