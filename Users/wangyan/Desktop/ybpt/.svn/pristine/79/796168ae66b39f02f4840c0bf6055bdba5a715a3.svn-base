import request from '@/utils/request'
import { baseURL } from '@/config/setting.config'
import axios from 'axios'

export function selectList(data) {
  return request({
    url: '/traceableCode/selectList',
    method: 'post',
    data,
  })
}

export function updateInfoAssessment(data) {
  return request({
    url: '/traceableCode/updateInfoAssessment',
    method: 'post',
    data,
  })
}

export function traceableCodeExport(data) {
  return request({
    url: '/traceableCode/traceableCodeExport',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

//上传文件
let urlUpload = baseURL + 'traceableCode/importData'
export async function importFile(form) {
  var result = {}
  if (
    null != form.get('file') &&
    '' != form.get('file') &&
    undefined != form.get('file')
  ) {
    await axios
      .post(urlUpload, form, {
        headers: {
          'Content-Type': 'multipart/form-data',
          accessToken: localStorage.getItem('accessToken'),
        },
      })
      .then((res) => {
        result = res
      })
  }
  return result
}

export function delTraceableCode(data) {
  return request({
    url: '/traceableCode/delTraceableCode?id=' + data.id,
    method: 'get',
    data,
  })
}
