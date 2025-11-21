import request from '@/utils/request'
import { baseURL } from '@/config/setting.config'
import axios from 'axios'

export function fileDeliveryList(data) {
  return request({
    url: '/fileGeneralDelivery/fileGeneralDeliveryList',
    method: 'post',
    data,
  })
}

export function updateFileDelivery(data) {
  return request({
    url: '/fileGeneralDelivery/updateFileGeneralDelivery',
    method: 'post',
    data,
  })
}

//上传文件
let urlUpload = baseURL + 'fileGeneralDelivery/upload'
export async function importData(form) {
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
        console.info(res)
      })
  }
  return result
}
