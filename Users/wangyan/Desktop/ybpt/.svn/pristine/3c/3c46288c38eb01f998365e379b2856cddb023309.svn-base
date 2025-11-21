import request from '@/utils/request'
import { baseURL } from '@/config/setting.config'
import axios from 'axios'

export function selectList(data) {
  return request({
    url: '/dataGovernance/selectList',
    method: 'post',
    params: data,
  })
}
export function getMedicalCategory(data) {
  return request({
    url: '/dataGovernance/getMedicalCategory',
    method: 'get',
    params: data,
  })
}
export function getUploadNo(data) {
  return request({
    url: '/dataGovernance/getUploadNo',
    method: 'get',
    params: data,
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
    url: '/dataGovernance/export',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

export function templateDownload(data) {
  return request({
    url: '/dataGovernance/templateDownload',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

//上传文件
let urlUpload = baseURL + 'dataGovernance/importData'
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
    url: '/dataGovernance/delData',
    method: 'post',
    params: data,
  })
}
