import request from '@/utils/request';
import { baseURL } from '@/config'
import axios from "axios";
export function count() {
  return request({
    url: '/civi/count',
    method: 'post',
  })
}

export function yyselectList(data) {
  return request({
    url: '/civi/yyselectList',
    method: 'post',
    data,
  })
}

export function noAppointment(data) {
  return request({
    url: '/civi/noAppointment',
    method: 'post',
    data,
  })
}

export function findPersonSubscribeRecord(data) {
  console.log(data)
  return request({
    url: '/personSubscribeRecord/findPersonSubscribeRecord',
    method: 'post',
    params: {
      pageNo: data.pageNo,
      pageSize: data.pageSize,
      oname: data.oname,
      pname: data.pname
    }
  })
}


export function findCiviWorkerByRid(data) {
  return request({
    url: '/personSubscribeRecord/findCiviWorkerByRid',
    method: 'post',
    params: {
      rid: data.rid,
      oid: data.oid,
      time: data.time,
      code: data.code,
    }
  })
}


export function findItemToMealByPid(data) {
  return request({
    url: '/personSubscribeRecord/findItemToMealByPid',
    method: 'get',
    params: {
      pid: data
    }
  })
}

export function conformById(data) {

  return request({
    url: '/personSubscribeRecord/conformById',
    method: 'post',
    params: {
      id: data.id
    }
  })
}

//上传文件

let urlUpload = baseURL + "/personSubscribeRecord/upload";

export function uploadFile(form) {
  if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
    axios.post(urlUpload, form, {
      headers: {
        "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
      }
    });
  }
}
