package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.mapper.DirectoryMapper;
import com.jsdc.ybpt.model_query.IncomeAndExpenditure;
import com.jsdc.ybpt.model_query.medicalOrg.BasicFix;
import com.jsdc.ybpt.model_query.personnel.OpspRegEvt;
import com.jsdc.ybpt.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectoryService extends BaseService<CatalogItem> {

    @Autowired
    private DirectoryMapper directoryMapper ;



    /**
    *2.2.2按目录项目查询使用情况
    * Author wzn
    * Date 2022/7/26 14:21
    */
    @DS("reflowData")
    public Page<CatalogItem> selectCatalogItem(CatalogItem catalogItem) {
        Page<CatalogItem> page = new Page<>(catalogItem.getPageNo(), catalogItem.getPageSize());
        Page<CatalogItem> catalogItemPage = directoryMapper.selectCatalogItem(page,catalogItem) ;
        return catalogItemPage ;
    }

    /**
    *2.5.2门慢、门特审批信息查询
    * Author wzn
    * Date 2022/8/1 14:14
    */
    @DS("reflowData")
    public Page<OpspRegEvt> queryOpsp(OpspRegEvt opspRegEvt) {
        Page<OpspRegEvt> page = new Page<>(opspRegEvt.getPageNo(), opspRegEvt.getPageSize());
        Page<OpspRegEvt> opspRegEvtPage = directoryMapper.queryOpsp(page,opspRegEvt) ;
        return opspRegEvtPage ;
    }


    /**
    *2.6.2定点医药机构基本信息
    * Author wzn
    * Date 2022/8/3 9:59
    */
    @DS("reflowData")
    public Page<BasicFix> basicMedicalInfo(BasicFix basicFix) {
        Page<BasicFix> page = new Page<>(basicFix.getPageNo(), basicFix.getPageSize());
        Page<BasicFix> basicFixPage = directoryMapper.basicMedicalInfo(page,basicFix) ;
        return basicFixPage ;
    }

    @DS("reflowData")
    public List<BasicFix> basicMedicalInfoExport(BasicFix basicFix) {
        List<BasicFix> basicFixList = directoryMapper.basicMedicalInfoExport(basicFix) ;
        return basicFixList ;
    }

    @DS("reflowData")
    public Page<IncomeAndExpenditure> personalIncomeAndExpenditure(IncomeAndExpenditure incomeAndExpenditure) {
        Page<IncomeAndExpenditure> page = new Page<>(incomeAndExpenditure.getPageNo(), incomeAndExpenditure.getPageSize());
        Page<IncomeAndExpenditure> incomeAndExpenditurePage = directoryMapper.personalIncomeAndExpenditure(page,incomeAndExpenditure) ;
        return incomeAndExpenditurePage ;
    }


    @DS("reflowData")
    public List<IncomeAndExpenditure> personalIncomeAndExpenditureExport(IncomeAndExpenditure incomeAndExpenditure) {
        List<IncomeAndExpenditure> incomeAndExpenditureList = directoryMapper.personalIncomeAndExpenditureExport(incomeAndExpenditure) ;
        return incomeAndExpenditureList ;
    }


    @DS("reflowData")
    public Page<ZeroReport> zeroReportData(ZeroReport zeroReport) {
        Page<ZeroReport> page = new Page<>(zeroReport.getPageNo(), zeroReport.getPageSize());
        Page<ZeroReport> zeroReportPage = directoryMapper.zeroReportData(page,zeroReport) ;
        return zeroReportPage ;
    }

    @DS("reflowData")
    public List<ZeroReport> zeroReportDataExport(ZeroReport zeroReport) {
        List<ZeroReport> zeroReportList = directoryMapper.zeroReportDataExport(zeroReport) ;
        return zeroReportList ;
    }

    @DS("reflowData")
    public Page<ZeroReportReview> zeroReportViewData(ZeroReportReview zeroReportReview) {
        Page<ZeroReportReview> page = new Page<>(zeroReportReview.getPageNo(), zeroReportReview.getPageSize());
        Page<ZeroReportReview> zeroReportReviewPage = directoryMapper.zeroReportViewData(page,zeroReportReview) ;
        return zeroReportReviewPage ;
    }

    @DS("reflowData")
    public List<ZeroReportReview> zeroReportDataViewExport(ZeroReportReview zeroReportReview) {
        List<ZeroReportReview> zeroReportReviewList = directoryMapper.zeroReportDataViewExport(zeroReportReview) ;
        return zeroReportReviewList ;
    }


    @DS("reflowData")
    public Page<ScopeOfMedication> scopeOfMedicationData(ScopeOfMedication scopeOfMedication) {
        Page<ScopeOfMedication> page = new Page<>(scopeOfMedication.getPageNo(), scopeOfMedication.getPageSize());
        Page<ScopeOfMedication> scopeOfMedicationPage = directoryMapper.scopeOfMedicationData(page,scopeOfMedication) ;
        return scopeOfMedicationPage ;
    }


    @DS("reflowData")
    public List<ScopeOfMedication> scopeOfMedicationDataExport(ScopeOfMedication scopeOfMedication) {
        List<ScopeOfMedication> scopeOfMedicationList = directoryMapper.scopeOfMedicationDataExport(scopeOfMedication) ;
        return scopeOfMedicationList ;
    }

    @DS("reflowData")
    public Page<SporadicReimbursement> sporadicReimbursementData(SporadicReimbursement sporadicReimbursement) {
        Page<SporadicReimbursement> page = new Page<>(sporadicReimbursement.getPageNo(), sporadicReimbursement.getPageSize());
        Page<SporadicReimbursement> sporadicReimbursementPage = directoryMapper.sporadicReimbursementData(page,sporadicReimbursement) ;
        return sporadicReimbursementPage ;
    }


    @DS("reflowData")
    public List<SporadicReimbursement> sporadicReimbursementDataExport(SporadicReimbursement sporadicReimbursement) {
        List<SporadicReimbursement> scopeOfMedications = directoryMapper.sporadicReimbursementDataExport(sporadicReimbursement) ;
        return scopeOfMedications ;
    }

    @DS("reflowData")
    public Page<MaternityMedicalExpenses> maternityMedicalExpensesData(MaternityMedicalExpenses maternityMedicalExpenses) {
        Page<MaternityMedicalExpenses> page = new Page<>(maternityMedicalExpenses.getPageNo(), maternityMedicalExpenses.getPageSize());
        Page<MaternityMedicalExpenses> maternityMedicalExpensesPage = directoryMapper.maternityMedicalExpensesData(page,maternityMedicalExpenses) ;
        return maternityMedicalExpensesPage ;
    }



    @DS("reflowData")
    public List<MaternityMedicalExpenses> maternityMedicalExpensesDataExport(MaternityMedicalExpenses maternityMedicalExpenses) {
        List<MaternityMedicalExpenses> maternityMedicalExpensesList = directoryMapper.maternityMedicalExpensesDataExport(maternityMedicalExpenses) ;
        return maternityMedicalExpensesList ;
    }


    @DS("reflowData")
    public Page<MaternityBenefits> maternityBenefitsData(MaternityBenefits maternityBenefits) {
        Page<MaternityBenefits> page = new Page<>(maternityBenefits.getPageNo(), maternityBenefits.getPageSize());
        Page<MaternityBenefits> maternityBenefitsPage = directoryMapper.maternityBenefitsData(page,maternityBenefits) ;
        return maternityBenefitsPage ;
    }


    @DS("reflowData")
    public List<MaternityBenefits> maternityBenefitsDataExport(MaternityBenefits maternityBenefits) {
        List<MaternityBenefits> maternityBenefitsList = directoryMapper.maternityBenefitsDataExport(maternityBenefits) ;
        return maternityBenefitsList ;
    }

    @DS("reflowData")
    public Page<ReimbursementSettlement> reimbursementSettlementData(ReimbursementSettlement reimbursementSettlement) {
        Page<ReimbursementSettlement> page = new Page<>(reimbursementSettlement.getPageNo(), reimbursementSettlement.getPageSize());
        Page<ReimbursementSettlement> reimbursementSettlementPage = directoryMapper.reimbursementSettlementData(page,reimbursementSettlement) ;
        return reimbursementSettlementPage ;
    }


    @DS("reflowData")
    public List<ReimbursementSettlement> reimbursementSettlementDataExport(ReimbursementSettlement reimbursementSettlement) {
        List<ReimbursementSettlement> reimbursementSettlementList = directoryMapper.reimbursementSettlementDataExport(reimbursementSettlement) ;
        return reimbursementSettlementList ;
    }

    @DS("reflowData")
    public Page<FertilitySettlementProgress> fertilitySettlementProgressData(FertilitySettlementProgress fertilitySettlementProgress) {
        Page<FertilitySettlementProgress> page = new Page<>(fertilitySettlementProgress.getPageNo(), fertilitySettlementProgress.getPageSize());
        Page<FertilitySettlementProgress> fertilitySettlementProgressPage = directoryMapper.fertilitySettlementProgressData(page,fertilitySettlementProgress) ;
        return fertilitySettlementProgressPage ;
    }


    @DS("reflowData")
    public List<FertilitySettlementProgress> fertilitySettlementProgressDataExport(FertilitySettlementProgress fertilitySettlementProgress) {
        List<FertilitySettlementProgress> fertilitySettlementProgressList = directoryMapper.fertilitySettlementProgressDataExport(fertilitySettlementProgress) ;
        return fertilitySettlementProgressList ;
    }

    @DS("reflowData")
    public Page<OffsiteFiling> offsiteFilingData(OffsiteFiling offsiteFiling) {
        Page<OffsiteFiling> page = new Page<>(offsiteFiling.getPageNo(), offsiteFiling.getPageSize());
        Page<OffsiteFiling> offsiteFilingPage = directoryMapper.offsiteFilingData(page,offsiteFiling) ;
        return offsiteFilingPage ;
    }


    @DS("reflowData")
    public List<OffsiteFiling> offsiteFilingDataExport(OffsiteFiling offsiteFiling) {
        List<OffsiteFiling> offsiteFilingList = directoryMapper.offsiteFilingDataExport(offsiteFiling) ;
        return offsiteFilingList ;
    }

    @DS("reflowData")
    public Page<MedicineInfo> medicineInfoData(MedicineInfo medicineInfo) {
        Page<MedicineInfo> page = new Page<>(medicineInfo.getPageNo(), medicineInfo.getPageSize());
        Page<MedicineInfo> medicineInfoPage = directoryMapper.medicineInfoData(page,medicineInfo) ;
        return medicineInfoPage ;
    }


    @DS("reflowData")
    public List<MedicineInfo> medicineInfoDataExport(MedicineInfo medicineInfo) {
        List<MedicineInfo> medicineInfoList = directoryMapper.medicineInfoDataExport(medicineInfo) ;
        return medicineInfoList ;
    }

    @DS("reflowData")
    public Page<MedicalService> medicalServiceData(MedicalService medicalService) {
        Page<MedicalService> page = new Page<>(medicalService.getPageNo(), medicalService.getPageSize());
        Page<MedicalService> medicalServicePage = directoryMapper.medicalServiceData(page,medicalService) ;
        return medicalServicePage ;
    }

    @DS("reflowData")
    public List<MedicalService> medicalServiceDataExport(MedicalService medicalService) {
        List<MedicalService> medicalServiceList = directoryMapper.medicalServiceDataExport(medicalService) ;
        return medicalServiceList ;
    }


    @DS("reflowData")
    public Page<MedicalConsumables> medicalConsumablesData(MedicalConsumables medicalConsumables) {
        Page<MedicalConsumables> page = new Page<>(medicalConsumables.getPageNo(), medicalConsumables.getPageSize());
        Page<MedicalConsumables> medicalConsumablesPage = directoryMapper.medicalConsumablesData(page,medicalConsumables) ;
        return medicalConsumablesPage ;
    }


    @DS("reflowData")
    public List<MedicalConsumables> medicalConsumablesDataExport(MedicalConsumables medicalConsumables) {
        List<MedicalConsumables> medicalConsumablesList = directoryMapper.medicalConsumablesDataExport(medicalConsumables) ;
        return medicalConsumablesList ;
    }

    @DS("reflowData")
    public List<SetlDVo> billingComparison(SetlDVo setlDVo) {
        List<SetlDVo> setlDVoPage = directoryMapper.billingComparison(setlDVo) ;
        return setlDVoPage ;
    }

    /**
    *结算数据比对
    * Author wzn
    * Date 2022/9/15 9:47
    */
    public List<Map<String,String>> contrast(SetlDVo setlDVo,SetlDVo setlDVo2){
        List<Map<String,String>> list = new ArrayList<>() ;
        Map<String,String> map = new HashMap<>()  ;
        map.put("name","增减额") ;
return null ;
    }


    @DS("reflowData")
    public Page<Insurance> insuranceData(Insurance insurance) {
        Page<Insurance> page = new Page<>(insurance.getPageNo(), insurance.getPageSize());
        Page<Insurance> insurancePage = directoryMapper.insuranceData(page,insurance) ;
        return insurancePage ;
    }

    @DS("reflowData")
    public List<Insurance> insuranceDataExport(Insurance insurance) {
        List<Insurance> setlDVoPage = directoryMapper.insuranceDataExport(insurance) ;
        return setlDVoPage ;
    }
}
