import request from '@/utils/request'
import {baseURL} from "@/config/setting.config";
import axios from "axios";

export function verifySbZlProject(data) {
    return request({
        url: '/drugPrice/verifySbZlProject',
        method: 'post',
        params: data,
    })
}

export function selectList(data) {
  return request({
    url: '/drugPrice/selectList',
    method: 'post',
    data,
  })
}

export function westernMedicineDrugCode(data) {
    return request({
        url: '/drugPrice/westernMedicineDrugCode',
        method: 'post',
        data
    })
}

export function chinesePatentMedicineList(data) {
    return request({
        url: '/drugPrice/chinesePatentMedicineList',
        method: 'post',
        data,
    })
}

export function chinesePatentMedicineDrugCode(data) {
    return request({
        url: '/drugPrice/chinesePatentMedicineDrugCode',
        method: 'post',
        data,
    })
}


//上传文件
let urlUpload = baseURL + "drugPrice/importData";
export async function importData(form) {
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

//上传文件
let urlUpload2 = baseURL + "drugPrice/patentMedicineimportData";
export async function patentMedicineimportData(form) {
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


export function zlProjectMedicineList(data) {
    return request({
        url: '/drugPrice/zlProjectMedicineList',
        method: 'post',
        data,
    })
}

export function nloodList(data) {
    return request({
        url: '/drugPrice/nloodList',
        method: 'post',
        data,
    })
}

export function nonProjectMedicineList(data) {
    return request({
        url: '/drugPrice/nonProjectMedicineList',
        method: 'post',
        data,
    })
}


//上传文件
let urlUpload3 = baseURL + "drugPrice/projectimportData";
export async function projectimportData(form) {
    var result = {};
    if (null != form.get("file") && "" != form.get("file") && undefined != form.get("file")) {
        await axios.post(urlUpload3, form, {
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

export function getUploadNo() {
    return request({
        url: '/drugPrice/getUploadNo',
        method: 'post'
    })
}

export function getChinaUploadNo() {
    return request({
        url: '/drugPrice/getChinaUploadNo',
        method: 'post'
    })
}


export function westDrugExport(data) {
    return request({
        url: '/drugPrice/westDrugExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function chinaDrugExport(data) {
    return request({
        url: '/drugPrice/chinaDrugExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function projectExport(data) {
    return request({
        url: '/drugPrice/projectExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function getProjectNo() {
    return request({
        url: '/drugPrice/getProjectNo',
        method: 'post'
    })
}


export function sbApplyList(data) {
    return request({
        url: '/drugPrice/sbApplyList',
        method: 'post',
        data,
    })
}

export function sbApplyExport(data) {
    return request({
        url: '/drugPrice/sbApplyExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function audit(data) {
    return request({
        url: '/drugPrice/audit',
        method: 'post',
        data,
    })
}

export function batchAudit(data) {
    return request({
        url: '/drugPrice/batchAudit?ids='+data,
        method: 'post',
    })
}



export function viewPdf(data) {
    return request({
        url: '/drugPrice/viewPdf?id='+data,
        method: 'post',
    })
}

export function bedViewPdf(data) {
    return request({
        url: '/drugPrice/bedViewPdf?id='+data,
        method: 'post',
    })
}

export function soloBedViewPdf(data) {
    return request({
        url: '/drugPrice/soloBedViewPdf?id='+data,
        method: 'post',
    })
}



export function zzPdf(data) {
    return request({
        url: '/drugPrice/zzPdf?id='+data,
        method: 'post',
    })
}



export function addBusinessProject(data) {
    return request({
        url: '/drugPrice/addBusinessProject',
        method: 'post',
        data,
    })
}

export function businessProjectList() {
    return request({
        url: '/drugPrice/businessProjectList',
        method: 'post'
    })
}

export function businessProjectListInfo(data) {
    return request({
        url: '/drugPrice/businessProjectListInfo?id='+data,
        method: 'post',
    })
}

export function delBusinessProjectList(data) {
    return request({
        url: '/drugPrice/delBusinessProjectList?id='+data,
        method: 'post',
    })
}



export function westExport(data) {
    return request({
        url: '/drugPrice/westExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function chinaExport(data) {
    return request({
        url: '/drugPrice/chinaExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function projectzcExport(data) {
    return request({
        url: '/drugPrice/projectzcExport',
        method: 'post',
        responseType: "blob",
        headers: {
            'Content-Type': 'application/x-download'
        },
        params:data,
    })
}

export function infoExcel(data) {
    return request({
        url: '/drugPrice/infoExcel?id='+data,
        method: 'post',
    })
}

export function getInfo() {
    return request({
        url: '/drugPrice/getInfo',
        method: 'post',
    })
}

export function bedDeclaration(data) {
    return request({
        url: '/drugPrice/bedDeclaration',
        method: 'post',
        data,
    })
}

export function soloBedDeclaration(data) {
    return request({
        url: '/drugPrice/soloBedDeclaration',
        method: 'post',
        data,
    })
}


export function verify(data) {
    return request({
        url: '/drugPrice/verify?project_code='+data,
        method: 'post',
    })
}
