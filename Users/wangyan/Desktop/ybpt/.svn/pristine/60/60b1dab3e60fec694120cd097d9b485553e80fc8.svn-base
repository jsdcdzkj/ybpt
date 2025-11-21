import request from '@/utils/request'
import { baseURL } from '@/config/setting.config'
import axios from 'axios'

export function selectList(data) {
  return request({
    url: '/terminalInfo/selectList',
    method: 'post',
    params: data,
  })
}

export function addUpdateInfo(data) {
  return request({
    url: '/terminalInfo/addUpdateInfo',
    method: 'post',
    data,
  })
}

export function infoDetail(data) {
  return request({
    url: '/terminalInfo/info',
    method: 'post',
    params: data,
  })
}

export function exportExel(data) {
  return request({
    url: '/terminalInfo/export',
    method: 'get',
    responseType: 'blob',
    params: data,
  })
}
export function audit(data) {
  return request({
    url: '/terminalInfo/audit',
    method: 'post',
    params: data,
  })
}

export async function uploadEvalOrgDetail(form) {
  var result = {}
  if (
    null != form.get('file') &&
    '' != form.get('file') &&
    undefined != form.get('file')
  ) {
    await axios
      .post(baseURL + 'terminalInfo/upload', form, {
        headers: {
          'Content-Type': 'multipart/form-data',
          accessToken: localStorage.getItem('accessToken'),
        },
      })
      .then((res) => {
        result = res
        console.info(res)
      })
  }
  return result
}

export function removeUploadEvalOrgDetail(data) {
  return request({
    url: '/terminalInfo/removeUpload',
    method: 'post',
    params: data,
  })
}
