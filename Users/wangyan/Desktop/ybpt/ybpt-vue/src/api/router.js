import request from '@/utils/request'

export function getRouterList(data) {
  return request({
    url: '/sysMenu/getMenus',
    method: 'post',
    data,
  })
}

export function getMenusByUser(data) {
  return request({
    url: '/sysMenu/getMenusByUser',
    method: 'post',
    params:data,
  })
}
