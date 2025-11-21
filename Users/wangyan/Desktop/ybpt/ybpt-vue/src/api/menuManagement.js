import request from '@/utils/request'


export function saveMenu(data) {
  return request({
    url: '/sysMenu/saveMenu',
    method: 'post',
    data,
  })
}

export function getLayoutMenus(data) {
  return request({
    url: '/sysMenu/getLayoutMenus',
    method: 'post',
    data,
  })
}

export function doEdit(data) {
  return request({
    url: '/sysMenu/getLayoutMenus',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/sysMenu/delMenu',
    method: 'post',
    params:data,
  })
}



