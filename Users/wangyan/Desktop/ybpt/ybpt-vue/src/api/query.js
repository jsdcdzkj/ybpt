import request from '@/utils/request'

export function drInfoB(data) {
    return request({
        url: '/query/drInfoB',
        method: 'post',
        params: data,
    })
}

export function empInsuD(data) {
    return request({
        url: '/query/empInsuD',
        method: 'post',
        params: data,
    })
}

export function insuEmpInfoB(data) {
    return request({
        url: '/query/insuEmpInfoB',
        method: 'post',
        params: data,
    })
}

export function medinsInfoB(data) {
    return request({
        url: '/query/medinsInfoB',
        method: 'post',
        params: data,
    })
}

export function medTecnInfoB(data) {
    return request({
        url: '/query/medTecnInfoB',
        method: 'post',
        params: data,
    })
}

export function nursInfoB(data) {
    return request({
        url: '/query/nursInfoB',
        method: 'post',
        params: data,
    })
}

export function opspRegEvtC(data) {
    return request({
        url: '/query/opspRegEvtC',
        method: 'post',
        params: data,
    })
}

export function optPsnB(data) {
    return request({
        url: '/query/optPsnB',
        method: 'post',
        params: data,
    })
}

export function outAppyEvtC(data) {
    return request({
        url: '/query/outAppyEvtC',
        method: 'post',
        params: data,
    })
}

export function pharInfoB(data) {
    return request({
        url: '/query/pharInfoB',
        method: 'post',
        params: data,
    })
}

export function profInfoB(data) {
    return request({
        url: '/query/profInfoB',
        method: 'post',
        params: data,
    })
}

export function psnClctstdD(data) {
    return request({
        url: '/query/psnClctstdD',
        method: 'post',
        params: data,
    })
}

export function psnInsuD(data) {
    return request({
        url: '/query/psnInsuD',
        method: 'post',
        params: data,
    })
}

export function psnInsuStasB(data) {
    return request({
        url: '/query/psnInsuStasB',
        method: 'post',
        params: data,
    })
}

export function rtalPhacB(data) {
    return request({
        url: '/query/rtalPhacB',
        method: 'post',
        params: data,
    })
}

export function spdrugApprFilEvtC(data) {
    return request({
        url: '/query/spdrugApprFilEvtC',
        method: 'post',
        params: data,
    })
}


export function selectCatalogItem(data) {
    return request({
        url: '/directory/selectCatalogItem',
        method: 'post',
        data,
    })
}

export function selectSpecialDrugFiling(data) {
    return request({
        url: '/specialDrugFiling/selectSpecialDrugFiling',
        method: 'post',
        data,
    })
}


export function setlD(data) {
    return request({
        url: '/query/setlD',
        method: 'post',
        params: data,
    })
}

export function setlDExport(data) {
    return request({
        url: '/query/setlDExport',
        method: 'post',
        params: data,
    })
}

export function setlDYear(data) {
    return request({
        url: '/query/setlD',
        method: 'post',
        params: data,
    })
}

export function setlDYearExcel(data) {
    return request({
        url: '/query/setlDYearExcel',
        method: 'post',
        params: data,
    })
}

export function queryOpsp(data) {
    return request({
        url: '/directory/queryOpsp',
        method: 'post',
        data,
    })
}


export function setlDByDiseNo(data) {
    return request({
        url: '/query/setlDByDiseNo',
        method: 'post',
        params: data,
    })
}

export function setlDByDiseNoExcel(data) {
    return request({
        url: '/query/setlDByDiseNoExcel',
        method: 'post',
        params: data,
    })
}

export function setlDByDiseNoTrtExcel(data) {
    return request({
        url: '/query/setlDByDiseNoTrtExcel',
        method: 'post',
        params: data,
    })
}

export function setlDByDiseNoCount(data) {
    return request({
        url: '/query/setlDByDiseNoCount',
        method: 'post',
        params: data,
    })
}


export function basicMedicalInfo(data) {
    return request({
        url: '/directory/basicMedicalInfo',
        method: 'post',
        data,
    })
}

export function personalIncomeAndExpenditure(data) {
    return request({
        url: '/directory/personalIncomeAndExpenditure',
        method: 'post',
        data,
    })
}


export function basicMedicalInfoExport(data) {
    return request({
        url: '/directory/basicMedicalInfoExport',
        method: 'post',
        data,
        responseType:'blob'
    })
}

export function personalIncomeAndExpenditureExport(data) {
    return request({
        url: '/directory/personalIncomeAndExpenditureExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


export function reflAppyEvtC(data) {
    return request({
        url: '/query/reflAppyEvtC',
        method: 'post',
        params: data,
    })
}

export function fixmedinsGather(data) {
    return request({
        url: '/query/fixmedinsGather',
        method: 'post',
        params: data,
    })
}

export function fixmedinsGatherExport(data) {
    return request({
        url: '/query/fixmedinsGatherExport',
        method: 'post',
        params: data,
    })
}

export function fixmedinsCntrRegD(data) {
    return request({
        url: '/query/fixmedinsCntrRegD',
        method: 'post',
        params: data,
    })
}

export function fixmedinsCntrRegDExcel(data) {
    return request({
        url: '/query/fixmedinsCntrRegDExcel',
        method: 'post',
        params: data,
    })
}

export function fixmedinsCntrRegDByMedinsInfoB(data) {
    return request({
        url: '/query/fixmedinsCntrRegDByMedinsInfoB',
        method: 'post',
        params: data,
    })
}

export function fixmedinsCntrRegDByMedinsInfoBExport(data) {
    return request({
        url: '/query/fixmedinsCntrRegDByMedinsInfoBExport',
        method: 'post',
        params: data,
    })
}

export function medicalContrast(data) {
    return request({
        url: '/query/medicalContrast',
        method: 'post',
        params: data,
    })
}

export function medicalContrastExcel(data) {
    return request({
        url: '/query/medicalContrastExcel',
        method: 'post',
        params: data,
    })
}

export function zeroReportData(data) {
    return request({
        url: '/directory/zeroReportData',
        method: 'post',
        data,
    })
}


export function zeroReportDataExport(data) {
    return request({
        url: '/directory/zeroReportDataExport',
        method: 'post',
        data,
        responseType:'blob'
    })
}

export function zeroReportViewData(data) {
    return request({
        url: '/directory/zeroReportViewData',
        method: 'post',
        data,
    })
}

export function zeroReportDataViewExport(data) {
    return request({
        url: '/directory/zeroReportDataViewExport',
        method: 'post',
        data,
        responseType:'blob'
    })
}

export function scopeOfMedicationData(data) {
    return request({
        url: '/directory/scopeOfMedicationData',
        method: 'post',
        data,
    })
}

export function scopeOfMedicationDataExport(data) {
    return request({
        url: '/directory/scopeOfMedicationDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


export function childbirth(data) {
    return request({
        url: '/query/childbirth',
        method: 'post',
        params: data,
    })
}

export function childbirthExport(data) {
    return request({
        url: '/query/childbirthExport',
        method: 'post',
        params: data,
    })
}

export function sporadicHandle(data) {
    return request({
        url: '/query/sporadicHandle',
        method: 'post',
        params: data,
    })
}

export function sporadicHandleExport(data) {
    return request({
        url: '/query/sporadicHandleExport',
        method: 'post',
        params: data,
    })
}

export function medicalAssistance(data) {
    return request({
        url: '/query/medicalAssistance',
        method: 'post',
        params: data,
    })
}

export function medicalAssistanceExport(data) {
    return request({
        url: '/query/medicalAssistanceExport',
        method: 'post',
        params: data,
    })
}

export function hospitalization(data) {
    return request({
        url: '/query/hospitalization',
        method: 'post',
        params: data,
    })
}

export function hospitalizationExport(data) {
    return request({
        url: '/query/hospitalizationExport',
        method: 'post',
        params: data,
    })
}

export function personal(data) {
    return request({
        url: '/query/personal',
        method: 'post',
        params: data,
    })
}

export function personalExport(data) {
    return request({
        url: '/query/personalExport',
        method: 'post',
        params: data,
    })
}

export function organization(data) {
    return request({
        url: '/query/organization',
        method: 'post',
        params: data,
    })
}

export function organizationExport(data) {
    return request({
        url: '/query/organizationExport',
        method: 'post',
        params: data,
    })
}


export function sporadicReimbursementData(data) {
    return request({
        url: '/directory/sporadicReimbursementData',
        method: 'post',
        data,
    })
}

export function sporadicReimbursementDataExport(data) {
    return request({
        url: '/directory/sporadicReimbursementDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}




export function maternityMedicalExpensesData(data) {
    return request({
        url: '/directory/maternityMedicalExpensesData',
        method: 'post',
        data,
    })
}

export function maternityMedicalExpensesDataExport(data) {
    return request({
        url: '/directory/maternityMedicalExpensesDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}



export function schoolCount(data) {
    return request({
        url: '/query/schoolCount',
        method: 'post',
        params: data,
    })
}

export function schoolCountExport(data) {
    return request({
        url: '/query/schoolCountExport',
        method: 'post',
        params: data,
    })
}

export function schoolDetail(data) {
    return request({
        url: '/query/schoolDetail',
        method: 'post',
        params: data,
    })
}

export function schoolDetailExport(data) {
    return request({
        url: '/query/schoolDetailExport',
        method: 'post',
        params: data,
    })
}


export function maternityBenefitsData(data) {
    return request({
        url: '/directory/maternityBenefitsData',
        method: 'post',
        data,
    })
}

export function maternityBenefitsDataExport(data) {
    return request({
        url: '/directory/maternityBenefitsDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function reimbursementSettlementData(data) {
    return request({
        url: '/directory/reimbursementSettlementData',
        method: 'post',
        data,
    })
}

export function reimbursementSettlementDataExport(data) {
    return request({
        url: '/directory/reimbursementSettlementDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function fertilitySettlementProgressData(data) {
    return request({
        url: '/directory/fertilitySettlementProgressData',
        method: 'post',
        data,
    })
}


export function fertilitySettlementProgressDataExport(data) {
    return request({
        url: '/directory/fertilitySettlementProgressDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function offsiteFilingData(data) {
    return request({
        url: '/directory/offsiteFilingData',
        method: 'post',
        data,
    })
}


export function offsiteFilingDataExport(data) {
    return request({
        url: '/directory/offsiteFilingDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function medicineInfoData(data) {
    return request({
        url: '/directory/medicineInfoData',
        method: 'post',
        data,
    })
}


export function medicineInfoDataExport(data) {
    return request({
        url: '/directory/medicineInfoDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}

export function medicalServiceData(data) {
    return request({
        url: '/directory/medicalServiceData',
        method: 'post',
        data,
    })
}



export function medicalServiceDataExport(data) {
    return request({
        url: '/directory/medicalServiceDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


export function medicalConsumablesData(data) {
    return request({
        url: '/directory/medicalConsumablesData',
        method: 'post',
        data,
    })
}


export function medicalConsumablesDataExport(data) {
    return request({
        url: '/directory/medicalConsumablesDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}



export function billingComparison(data) {
    return request({
        url: '/directory/billingComparison',
        method: 'post',
        data,
    })
}

export function contrast(data) {
    return request({
        url: '/directory/contrast',
        method: 'post',
        data,
    })
}

export function insuranceData(data) {
    return request({
        url: '/directory/insuranceData',
        method: 'post',
        data,
    })
}

export function insuranceDataExport(data) {
    return request({
        url: '/directory/insuranceDataExport',
        method: 'post',
        data,
        responseType: 'blob'
    })
}
