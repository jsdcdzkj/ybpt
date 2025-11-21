import request from '@/utils/request'
import { baseURL } from '@/config/setting.config'
import axios from 'axios'

export function selectPageList(data) {
  return request({
    url: '/infoAssessment/selectList',
    method: 'post',
    data,
  })
}

export function updateInfoAssessment(data) {
  return request({
    url: '/infoAssessment/updateInfoAssessment',
    method: 'post',
    data,
  })
}

export function down(data) {
  return request({
    url: '/infoAssessment/down',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

//上传文件
let urlUpload = baseURL + 'infoAssessment/importData'
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

export function delInfoAssessment(data) {
  return request({
    url: '/infoAssessment/delInfoAssessment?id=' + data.id,
    method: 'get',
    data,
  })
}
