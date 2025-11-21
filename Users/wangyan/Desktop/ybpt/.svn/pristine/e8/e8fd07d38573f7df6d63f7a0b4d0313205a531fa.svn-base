import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/sysRole/getList',
    method: 'post',
    params:data,
  })
}
//根据角色ID获取对应的菜单ID
export function getMenusByRoleId(data) {
  return request({
    url: '/sysRole/getMenusByRoleId',
    method: 'post',
    params:data,
  })
}
//保存角色
export function saveRole(data) {
  return request({
    url: '/sysRole/saveRole',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/sysRole/delRole',
    method: 'post',
    params:data,
  })
}
