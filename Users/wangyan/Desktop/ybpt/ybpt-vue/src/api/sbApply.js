import request from '@/utils/request'
import { baseURL } from '@/config/setting.config'
import axios from 'axios'

//上传医疗凭证
export async function uploadLicence(form) {
  var result = {}
  if (
    null != form.get('file') &&
    '' != form.get('file') &&
    undefined != form.get('file')
  ) {
    await axios
      .post(baseURL + 'sbApply/uploadLicence', form, {
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

//上传价格明细文件
export async function uploadDetailFile(form) {
  var result = {}
  await axios
    .post(baseURL + 'fileInfo/uploadDetailFile', form, {
      headers: {
        'Content-Type': 'multipart/form-data',
        accessToken: localStorage.getItem('accessToken'),
      },
    })
    .then((res) => {
      result = res
      console.info(res)
    })
  return result
}

//压缩下载价格申报明细文件
export function downloadDetailFile(data) {
  return request({
    url: '/fileInfo/downloadDetailFile',
    method: 'post',
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-download',
    },
    params: data,
  })
}

export function getLicence(data) {
  return request({
    url: '/sbApply/getLicence',
    method: 'post',
    params: data,
  })
}

export function getFixmedinsB(data) {
  return request({
    url: '/sbApply/getFixmedinsB',
    method: 'post',
    data,
  })
}

export function down(data) {
  return request({
    url: '/sbApply/down',
    method: 'post',
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-download',
    },
    params: data,
  })
}

export function insertGovernmentMedical(data) {
  return request({
    url: '/sbApply/insertGovernmentMedical',
    method: 'post',
    data,
  })
}

export function insertCivilianMedical(data) {
  return request({
    url: '/sbApply/insertCivilianMedical',
    method: 'post',
    data,
  })
}

export function insertCivilianMaterial(data) {
  return request({
    url: '/sbApply/insertCivilianMaterial',
    method: 'post',
    data,
  })
}

export function lookStateHospital(data) {
  return request({
    url: '/sbApply/lookStateHospital',
    method: 'post',
    params: data,
  })
}

export function getMedicalService(data) {
  return request({
    url: '/drugPrice/zlProjectMedicineList',
    method: 'post',
    data,
  })
}

export function getList(data) {
  return request({
    url: '/sbApply/selectAll',
    method: 'post',
    params: data,
  })
}

export function detailInfo(data) {
  return request({
    url: '/capitalSettlement/detailInfo',
    method: 'post',
    data,
  })
}

export function oneInfo(data) {
  return request({
    url: '/capitalSettlement/oneInfo?id=' + data,
    method: 'post',
  })
}

export function edit(data) {
  return request({
    url: '/capitalSettlement/edit',
    method: 'post',
    data,
  })
}

export function delQs(data) {
  return request({
    url: '/capitalSettlement/delQs',
    method: 'post',
    data,
  })
}

export function createConfirming(data) {
  return request({
    url: '/capitalSettlement/createConfirming?id=' + data,
    method: 'post',
  })
}

export function dentalGetPage(data) {
  return request({
    url: '/sbDentalMedical/getPage',
    method: 'post',
    params: data,
  })
}

export function dentalExport(data) {
  return request({
    url: '/sbDentalMedical/export',
    method: 'post',
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-download',
    },
    params: data,
  })
}

export function dentalInsertOrUpdate(data) {
  return request({
    url: '/sbDentalMedical/insertOrUpdate',
    method: 'post',
    data,
  })
}

export function dentalUpdate(data) {
  return request({
    url: '/sbDentalMedical/update',
    method: 'post',
    data,
  })
}
