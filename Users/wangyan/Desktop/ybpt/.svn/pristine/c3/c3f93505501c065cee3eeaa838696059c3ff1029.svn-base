import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

//保存考核单
export function saveAssess(data) {
  return request({
    url: '/assessment/saveAssess',
    method: 'post',
    data
  })
}
//获取考核单详情
export function getAssessDetail(data) {
  return request({
    url: '/assessment/getAssessDetail',
    method: 'post',
    params : data
  })
}
//查询考核单列表
export function getAssessList(data) {
  return request({
    url: '/assessment/getAssessList',
    method: 'post',
    data
  })
}
//删除考核单
export function delAssess(data) {
  return request({
    url: '/assessment/delAssess',
    method: 'post',
    params : data
  })
}

//新增考核任务
export function addTaskMange(data) {
  return request({
    url: '/assessment/addTaskMange',
    method: 'post',
    data
  })
}

export function getAll(data) {
    return request({
        url: '/assessment/getAll',
        method: 'post',
        data
    })
}


export function exportData(data) {
    return request({
        url: '/assessment/exportData?id='+data,
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
    })
}


//修改考核任务
export function updTaskMange(data) {
  return request({
    url: '/assessment/updTaskMange',
    method: 'post',
    data
  })
}

//删除考核任务
export function delTaskMange(data) {
  return request({
    url: '/assessment/delTaskMange?id='+data,
    method: 'post'
  })
}

//发布考核任务
export function publishTaskMange(data) {
  return request({
    url: '/assessment/publishTaskMange?id='+data,
    method: 'post'
  })
}

//撤销发布
export function undoPublication(data) {
  return request({
    url: '/assessment/undoPublication?id='+data,
    method: 'post'
  })
}


//考核任务列表
export function selectTaskManageList(data) {
  return request({
    url: '/assessment/selectTaskManageList',
    method: 'post',
    data
  })
}

//考核单下拉数据
export function checkListDropDownData() {
  return request({
    url: '/assessment/checkListDropDownData',
    method: 'post'
  })
}

//根据选择内容，默认选择一个考核单
export function selectBy(data) {
  return request({
    url: '/assessment/selectBy',
    method: 'post',
    data
  })
}


//考核列表
export function assessmentList(data) {
  return request({
    url: '/assessment/assessmentList',
    method: 'post',
    data
  })
}
//考核详情
export function assessmentDetails(data) {
  return request({
    url: '/assessment/assessmentDetails',
    method: 'post',
    data
  })
}
//考核填报 抽查
let urlUpload = baseURL + "assessment/assessmentFill";
export async function assessmentFill(form) {
  var result = {};
  await axios.post(urlUpload, form, {
    headers: {
      "Content-Type": "multipart/form-data", "accessToken": localStorage.getItem("accessToken")
    }
  }).then((res) => {
    result = res;
    console.info(res);
  });
  return result;
}
//考核导出
export function assessmentExport(data) {
  return request({
    url: '/assessment/assessmentExport',
    method: 'post',
      params:data,
      responseType:'blob'
  })
}

//考核任务详情
export function info(data) {
    return request({
        url: '/assessment/info?id='+data,
        method: 'post'
    })
}

//考核单详情接口
export function assesDetail(data) {
    return request({
        url: '/assessment/assesDetail?id='+data,
        method: 'post'
    })
}

//更新过期时间
export function submitExpirationTime(data) {
    return request({
        url: '/assessment/submitExpirationTime',
        method: 'post',
        data
    })
}


//提交审核接口
export function submitForReview(data) {
    return request({
        url: '/assessment/submitForReview?id='+data,
        method: 'post'
    })
}
export function submitReview(data) {
    return request({
        url: '/assessment/submitReview?id='+data,
        method: 'post'
    })
}
//提交审核通过
export function submitSuccess(data) {
    return request({
        url: '/assessment/submitSuccess?id='+data,
        method: 'post'
    })
}
//提交审核驳回
export function submitReject(data) {
    return request({
        url: '/assessment/submitReject?id='+data,
        method: 'post'
    })
}
//提交审核驳回
export function batchAudit(data) {
    return request({
        url: '/assessment/batchAudit',
        method: 'post',
        data
    })
}

export function cancleSub(data) {
    return request({
        url: '/assessment/cancleSub?id='+data,
        method: 'post'
    })
}

export function logList(data) {
    return request({
        url: '/assessment/logList?id='+data,
        method: 'post'
    })
}


