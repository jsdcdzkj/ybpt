import request from '@/utils/request'
import { baseURL } from '@/config/setting.config'
import axios from 'axios'

//拨付通知分页查询
export function approNoticeList(data) {
  return request({
    url: '/approNotice/page',
    method: 'post',
    data,
  })
}

//拨付通知分页查询
export function pageByOrgCode(data) {
  return request({
    url: '/approNotice/pageByOrgCode',
    method: 'post',
    data,
  })
}

//上传校验
export function approValidUpload(data) {
  return request({
    url: '/approNotice/validUpload',
    method: 'post',
    data,
  })
}

//上传数据预览
export async function approNoticePreview(form) {
  let urlUpload = baseURL + '/approNotice/preview'
  var result = {}
  // if (
  //   null != form.get('file') &&
  //   '' != form.get('file') &&
  //   undefined != form.get('file')
  // ) {
  await axios
    .post(urlUpload, form, {
      headers: {
        'Content-Type': 'multipart/form-data',
        accessToken: localStorage.getItem('accessToken'),
      },
    })
    .then((res) => {
      result = res
      // console.info(res)
    })
  // }
  return result
}
// export function approNoticePreview(data) {
//   return request({
//     headers: {
//       'content-Type': 'multipart/form-data',
//       // accessToken: localStorage.getItem('accessToken'),
//     },
//     url: '/approNotice/preview',
//     method: 'post',
//     data:data
//   })
// }

//生成数据
export async function approNoticeGenerate(form) {
  let urlUpload = baseURL + '/approNotice/generate'
  var result = {}
  // if (
  //   null != form.get('file') &&
  //   '' != form.get('file') &&
  //   undefined != form.get('file')
  // ) {
  await axios
    .post(urlUpload, form, {
      headers: {
        'Content-Type': 'multipart/form-data',
        accessToken: localStorage.getItem('accessToken'),
      },
    })
    .then((res) => {
      result = res
      // console.info(res)
    })
  // }
  return result
}
// export function approNoticeGenerate(data) {
//   return request({
//     url: '/approNotice/generate',
//     method: 'post',
//     data,
//   })
// }

//发送数据
export function approNoticeSend(data) {
  return request({
    url: '/approNotice/send?id=' + data.id,
    method: 'get',
    data,
  })
}

//删除数据
export function approNoticeDel(data) {
  return request({
    url: '/approNotice/delete?id=' + data.id,
    method: 'get',
    data,
  })
}

//查看汇总数据
export function approNoticeViewSummary(data) {
  return request({
    url: '/approNotice/viewSummary',
    method: 'post',
    data,
  })
}

//查看数据明细
export function approNoticeViewDataDetail(data) {
  return request({
    url: '/approNotice/viewDataDetail',
    method: 'post',
    data,
  })
}

//下载汇总数据
export function approNoticeDownloadSummary(data) {
  return request({
    url: '/approNotice/downloadSummary',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

//下载数据明细
export function approNoticeDownloadDataDetail(data) {
  return request({
    url: '/approNotice/downloadDataDetail',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

//生成拨付分析数据
export function approNoticeGenerateApproAnalyse(data) {
  return request({
    url: '/approNotice/generateApproAnalyse',
    method: 'post',
    data,
  })
}

//查看汇总数据分析
export function approNoticeViewSummaryAnalyse(data) {
  return request({
    url: '/approNotice/viewSummaryAnalyse',
    method: 'post',
    data,
  })
}

//查看数据明细分析
export function approNoticeViewDetailAnalyse(data) {
  return request({
    url: '/approNotice/viewDetailAnalyse',
    method: 'post',
    data,
  })
}

//下载汇总数据分析
export function approNoticeDownloadSummaryAnalyse(data) {
  return request({
    url: '/approNotice/downloadSummaryAnalyse',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

//下载数据明细分析
export function approNoticeDownloadDetailAnalyse(data) {
  return request({
    url: '/approNotice/downloadDetailAnalyse',
    method: 'post',
    responseType: 'blob',
    data,
  })
}

//压缩下载价格申报明细文件
export function getTempAddress() {
  return request({
    url: '/approNotice/getTempAddress',
    method: 'post',
    // responseType: 'blob',
    // headers: {
    //   'Content-Type': 'application/x-download',
    // },
  })
}

//上传文件
let urlUpload = baseURL + 'capitalSettlement/importData'
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

/**2024-7-16 新 */

//拨付文档上传分页查询
export function appropDocumentList(data) {
  return request({
    url: '/appropDocument/page',
    method: 'post',
    data,
  })
}

//拨付文档删除
export function appropDocumentDel(data) {
  return request({
    url: 'appropDocument/detete?id=' + data.id,
    method: 'get',
    data,
  })
}

//发布拨付文档
export function appropDocumentSend(data) {
  return request({
    url: '/appropDocument/publish?id=' + data.id,
    method: 'get',
    data,
  })
}

//取消发布拨付文档
export function appropDocumentCancelSend(data) {
  return request({
    url: '/appropDocument/off?id=' + data.id,
    method: 'get',
    data,
  })
}

//拨付文档下载
export function appropDocumentDownLoad(data) {
  return request({
    url: '/appropDocument/downLoadFile?id=' + data.id,
    method: 'get',
    responseType: 'blob',
    data,
  })
}

//拨付文档在线预览
export function appropDocumentPreviewFile(data) {
  return request({
    url: '/appropDocument/previewFile?id=' + data.id,
    method: 'get',
    responseType: 'blob',
    data,
  })
}

//上传文档类型权限列表查询
export function appropDocumentAuthList(data) {
  return request({
    url: '/appropDocument/authList',
    method: 'get',
    data,
  })
}

//上传文档校验文件
export function appropDocumentValidate(data) {
  return request({
    url: '/appropDocument/validate',
    method: 'post',
    data,
  })
}

//拨付数据上传保存
export async function appropDocumentSave(form) {
  let urlUpload = baseURL + '/appropDocument/save'
  var result = {}
  await axios
    .post(urlUpload, form, {
      headers: {
        'Content-Type': 'multipart/form-data',
        accessToken: localStorage.getItem('accessToken'),
      },
    })
    .then((res) => {
      result = res
      // console.info(res)
    })
  // }
  return result
}

//上传文档查询
export function appropDocumentDocList(data) {
  return request({
    url: '/appropDocument/docList',
    method: 'post',
    data,
  })
}

//数据预览
export function appropDocumentPreview(data) {
  return request({
    url: '/approNotice/preview',
    method: 'post',
    data,
  })
}

//生成数据
export function appropDocumentGenerate(data) {
  return request({
    url: '/approNotice/generate',
    method: 'post',
    data,
  })
}
