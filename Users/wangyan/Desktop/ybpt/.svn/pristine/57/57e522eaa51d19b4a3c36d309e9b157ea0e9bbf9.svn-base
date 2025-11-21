import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

export function addConsumables(data) {
  return request({
    url: '/consumables/addConsumables',
    method: 'post',
    data,
  })
}

//上传文件
let urlUpload = baseURL + "consumables/fileUpload";
export async function fileUpload(form) {
  var result = {};
  if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
    await axios.post(urlUpload, form, {
      headers: {
        "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
      }
    }).then((res) => {
      result = res;
      console.info(res);
    });
  }
  return result;
}

export function fileDel(data) {
  return request({
    url: '/consumables/fileDel',
    method: 'post',
    params: data,
  })
}

export function getFile(data) {
  return request({
    url: '/consumables/getFile',
    method: 'post',
    params: data,
  })
}


export function updateConsumables(data) {
  return request({
    url: '/consumables/updateConsumables',
    method: 'post',
    data,
  })
}


export function delConsumables(data) {
  return request({
    url: '/consumables/delConsumables',
    method: 'post',
    data,
  })
}

export function selectList(data) {
  return request({
    url: '/consumables/selectList',
    method: 'post',
    data,
  })
}

export function excel(data) {
  return request({
    url: '/consumables/excel',
    method: 'post',
    params: data,
    responseType: 'blob'
  })
}

export function consumables9001(data) {
  return request({
    url: '/consumables/consumables9001',
    method: 'post',
    params: data,
  })
}

export function consumables9002(data) {
  return request({
    url: '/consumables/consumables9002',
    method: 'post',
    params: data,
  })
}

export function consumables9003(data) {
  return request({
    url: '/consumables/consumables9003',
    method: 'post',
    params: data,
  })
}


export function selectAll(data) {
  return request({
    url: '/consumablesSign/selectAll',
    method: 'post',
    params: data,
  })
}

export function insertOrUpdate(data) {
  return request({
    url: '/consumablesSign/insertOrUpdate',
    method: 'post',
    data,
  })
}

export function isOpen(data) {
  return request({
    url: '/consumablesSign/isOpen',
    method: 'post',
    data,
  })
}

export function isOpenEdit(data) {
  return request({
    url: '/consumablesSign/isOpenEdit',
    method: 'post',
    params: data,
  })
}

export function exportExcel(data) {
  return request({
    url: '/consumablesSign/exportExcel',
    method: 'post',
    params: data,
    responseType: 'blob'
  })
}