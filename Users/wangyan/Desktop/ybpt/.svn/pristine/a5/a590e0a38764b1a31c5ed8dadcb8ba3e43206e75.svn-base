import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";


export function selectList(data) {
  return request({
    url: '/evalDesign/selectList',
    method: 'post',
    data,
  })
}

export function getPageList(data) {
  return request({
    url: '/evalDesign/pageList',
    method: 'post',
    params:data,
  })
}

export function del(data) {
  return request({
    url: '/evalDesign/del',
    method: 'post',
    params:data,
  })
}

export function getEntity(data) {
  return request({
    url: '/evalDesign/getEntity',
    method: 'post',
    params:data,
  })
}


export function saveOrUploadEval(data) {
  return request({
    url: '/evalDesign/saveOrUploadEval',
    method: 'post',
    data,
  })
}

export function updateMoneyShow(data) {
  return request({
    url: '/evalDesign/updateMoneyShow',
    method: 'post',
    data,
  })
}

export function publishTaskMange(data) {
  return request({
    url: '/evalDesign/publishTaskMange?id='+data,
    method: 'post',
  })
}

export function startFinish(data) {
  return request({
    url: '/evalDesign/startFinish?id='+data,
    method: 'post',
  })
}

export function zbList(data) {
  return request({
    url: '/evalDesign/zbList?id='+data,
    method: 'post',
  })
}


//上传文件
let urlUpload = baseURL + "evalDesign/uploadOrgFile";
export async function uploadOrgFile(form) {
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


let urlUpload2 = baseURL + "evalDesign/uploadMoneyFile";
export async function uploadMoneyFile(form) {
  var result = {};
  if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
    await axios.post(urlUpload2, form, {
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



export function getFile(data) {
  return request({
    url: '/evalDesign/getFile?id='+data,
    method: 'post',
  })
}

export function getFile2(data) {
  return request({
    url: '/evalDesign/getFile2?id='+data,
    method: 'post',
  })
}

export function delEvalTaskManage(data) {
  return request({
    url: '/evalDesign/delEvalTaskManage?id='+data,
    method: 'post',
  })
}


export function calculateScore(data) {
  return request({
    url: '/evalDesign/calculateScore?id='+data,
    method: 'post',
  })
}


export function updateStatus(data) {
  return request({
    url: '/evalDesign/updateStatus?id='+data,
    method: 'post',
  })
}

export function updatecsStatus(data) {
  return request({
    url: '/evalDesign/updatecsStatus?id='+data,
    method: 'post',
  })
}



export async function uploadFile(form) {
  var result = {};
  await axios.post(baseURL + "evalDesign/uploadDesignFile", form, {
      headers: {
          "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
      }
  }).then((res) => {
      result = res.data.data;
      console.info(result);
  });
  return result;
}


export function getFileInfo(data) {
  return request({
    url: '/evalDesign/getDesignFile',
    method: 'post',
    params:data,
  })
}
export function designList(data) {
  return request({
    url: '/evalDesign/designList',
    method: 'post',
    data,
  })
}


export function getMedinsDeptB(data) {
  return request({
    url: '/evalDesign/getMedinsDeptB',
    method: 'post',
    data,
  })
}


export function getUserList(data) {
  return request({
    url: '/evalDesign/getUserList?deptCode='+data,
    method: 'post',
  })
}

export function addTaskManage(data) {
  return request({
    url: '/evalDesign/addTaskManage',
    method: 'post',
    data,
  })
}

export function updTaskManage(data) {
  return request({
    url: '/evalDesign/updTaskManage',
    method: 'post',
    data,
  })
}


export async function uploadEvalOrgDetail(form) {
  var result = {};
  if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
    await axios.post(baseURL + "eval/uploadEvalOrgDetail", form, {
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

export async function uploadAppealEvalOrgDetail(form) {
  var result = {};
  if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
    await axios.post(baseURL + "eval/uploadAppealEvalOrgDetail", form, {
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

export function removeUploadEvalOrgDetail(data) {
  return request({
    url: '/eval/removeUploadEvalOrgDetail',
    method: 'post',
    params:data,
  })
}

export async function importScore(form) {
  var result = {};
  if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
    await axios.post(baseURL + "eval/importScore", form, {
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

export async function importTargetScore(form) {
  var result = {};
  if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
    await axios.post(baseURL + "eval/importTargetScore", form, {
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

export function audit(data) {
  return request({
    url: '/eval/audit',
    method: 'post',
    data,
  })
}

export function evalTaskManagePage(data) {
  return request({
    url: '/eval/evalTaskManagePage',
    method: 'post',
    params:data,
  })
}

export function getEvalTaskManage(data) {
  return request({
    url: '/eval/getEvalTaskManage',
    method: 'post',
    params:data,
  })
}

export function getOrgDetailPage(data) {
  return request({
    url: '/eval/getOrgDetailPage',
    method: 'post',
    params:data,
  })
}

export function setOrgDetail(data) {
  return request({
    url: '/eval/setOrgDetail',
    method: 'post',
    params:data,
  })
}

export function getOrgDetailLog(data) {
  return request({
    url: '/eval/getOrgDetailLog',
    method: 'post',
    params:data,
  })
}

export function setEvalOrgDetail(data) {
  return request({
    url: '/eval/setEvalOrgDetail',
    method: 'post',
    data,
  })
}

export function evalOrgTaskPage(data) {
  return request({
    url: '/eval/evalOrgTaskPage',
    method: 'post',
    params:data,
  })
}

export function updateOrgTaskManagerStatus(data) {
  return request({
    url: '/eval/updateOrgTaskManagerStatus',
    method: 'post',
    params:data,
  })
}

export function getEvalOrgTask(data) {
  return request({
    url: '/eval/getEvalOrgTask',
    method: 'post',
    params:data,
  })
}



export function info(data) {
  return request({
    url: '/evalDesign/info?id='+data,
    method: 'post',
  })
}

export function getUser(data) {
  return request({
    url: '/evalDesign/getUser?id='+data,
    method: 'post',
  })
}

export function calculate(data) {
  return request({
    url: '/evalDesign/calculate?id='+data,
    method: 'post',
  })
}

export function noScore(data) {
  return request({
    url: '/evalDesign/noScore',
    method: 'post',
    data,
  })
}

export function getHisPageList(data) {
  return request({
    url: '/evalDesign/getHisPageList',
    method: 'post',
    data,
  })
}


export function updeById(data) {
  return request({
    url: '/evalDesign/updeById',
    method: 'post',
    data,
  })
}

export function getTaskEntity(data) {
  return request({
    url: '/evalDesign/getTaskEntity',
    method: 'post',
    params:data,
  })
}






export function evalOrgTaskExport(data) {
  return request({
    url: '/evalDesign/evalOrgTaskExport',
    method: 'post',
    responseType: "blob",
    headers: {
      'Content-Type': 'application/x-download'
    },
    params:data,
  })
}

export function evalOrgDetailExport(data) {
  return request({
    url: '/eval/evalOrgDetailExport',
    method: 'post',
    responseType: "blob",
    headers: {
      'Content-Type': 'application/x-download'
    },
    params:data,
  })
}


export function taskManageDropDown(data) {
  return request({
    url: '/evalDesign/taskManageDropDown',
    method: 'post',
  })
}


//上传文件
let upload2 = baseURL + "evalDesign/uploadPzFile";
export async function uploadPzFile(form) {
    var result = {};
    if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
        await axios.post(upload2, form, {
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

