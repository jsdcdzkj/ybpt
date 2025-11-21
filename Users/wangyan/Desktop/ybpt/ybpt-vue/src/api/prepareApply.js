import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

//申请提交接口
export function handleSave(data) {
  return request({
    url: '/notifyApply/save',
    method: 'post',
    data,
  })
}

//申请修改接口
export function handleUpdate(data) {
  return request({
    url: '/notifyApply/update',
    method: 'post',
    data,
  })
}

//详情接口
export function getDetail(id) {
  return request({
    url: `notifyApply/detailInfo/${id}`,
    method: 'get',
  })
}

// //文件上传接口
// export function uploadDetailFile(data) {
//   return request({
//     url: '/notifyApply/uploadDetailFile',
//     method: 'post',
//     data
//   })
// }
export async function uploadDetailFile(form) {
  var result = {}
  if (
    null != form.get('file') &&
    '' != form.get('file') &&
    undefined != form.get('file')
  ) {
    await axios
      .post(baseURL + 'notifyApply/uploadDetailFile', form, {
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

//模板下载
export function download(data) {
  return request({
    url: '/notifyApply/download',
    method: 'post',
    data,
    responseType: "blob"
  })
}
//模板下载
export function handleExport(data) {
  return request({
    url: 'notifyApply/export',
    method: 'post',
    data,
    responseType: "blob"
  })
}

//列表接口
export function applyPage(data) {
  return request({
    url: '/notifyApply/applyPage',
    method: 'post',
    data,
  })
}

//列表接口
export function catalogPage(data) {
  return request({
    url: '/catalog/page',
    method: 'post',
    data,
  })
}

//列表接口
export function deleteDetailFile(data) {
  return request({
    url: '/notifyApply/deleteDetailFile',
    method: 'post',
    data,
  })
}
//列表接口
export function checkSave(data) {
  return request({
    url: '/notifyApply/checkSave',
    method: 'post',
    data,
  })
}



