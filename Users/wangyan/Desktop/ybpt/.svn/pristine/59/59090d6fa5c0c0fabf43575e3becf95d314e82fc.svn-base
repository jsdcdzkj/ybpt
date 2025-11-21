package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.DirectoryDao;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.model_query.IncomeAndExpenditure;
import com.jsdc.ybpt.model_query.medicalOrg.BasicFix;
import com.jsdc.ybpt.model_query.personnel.OpspRegEvt;
import com.jsdc.ybpt.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DirectoryMapper extends BaseMapper<CatalogItem> {

    @SelectProvider(type= DirectoryDao.class,method = "selectCatalogItem")
    Page<CatalogItem> selectCatalogItem(Page page,CatalogItem catalogItem);


    @SelectProvider(type= DirectoryDao.class,method = "queryOpsp")
    Page<OpspRegEvt> queryOpsp(Page page, OpspRegEvt opspRegEvt);

    @SelectProvider(type= DirectoryDao.class,method = "basicMedicalInfo")
    Page<BasicFix> basicMedicalInfo(Page page, BasicFix basicFix);

    @SelectProvider(type= DirectoryDao.class,method = "basicMedicalInfoExport")
    List<BasicFix> basicMedicalInfoExport(BasicFix basicFix);

    @SelectProvider(type= DirectoryDao.class,method = "personalIncomeAndExpenditure")
    Page<IncomeAndExpenditure> personalIncomeAndExpenditure(Page page, IncomeAndExpenditure incomeAndExpenditure);

    @SelectProvider(type= DirectoryDao.class,method = "personalIncomeAndExpenditureExport")
    List<IncomeAndExpenditure> personalIncomeAndExpenditureExport(IncomeAndExpenditure incomeAndExpenditure);

    @SelectProvider(type= DirectoryDao.class,method = "zeroReportData")
    Page<ZeroReport> zeroReportData(Page page, ZeroReport zeroReport);

    @SelectProvider(type= DirectoryDao.class,method = "zeroReportDataExport")
    List<ZeroReport> zeroReportDataExport(ZeroReport zeroReport);

    @SelectProvider(type= DirectoryDao.class,method = "zeroReportViewData")
    Page<ZeroReportReview> zeroReportViewData(Page page, ZeroReportReview zeroReportReview);

    @SelectProvider(type= DirectoryDao.class,method = "zeroReportDataViewExport")
    List<ZeroReportReview> zeroReportDataViewExport(ZeroReportReview zeroReportReview);

    @SelectProvider(type= DirectoryDao.class,method = "scopeOfMedicationData")
    Page<ScopeOfMedication> scopeOfMedicationData(Page page, ScopeOfMedication scopeOfMedication);

    @SelectProvider(type= DirectoryDao.class,method = "scopeOfMedicationDataExport")
    List<ScopeOfMedication> scopeOfMedicationDataExport(ScopeOfMedication scopeOfMedication);


    @SelectProvider(type= DirectoryDao.class,method = "sporadicReimbursementData")
    Page<SporadicReimbursement> sporadicReimbursementData(Page page, SporadicReimbursement sporadicReimbursement);

    @SelectProvider(type= DirectoryDao.class,method = "sporadicReimbursementDataExport")
    List<SporadicReimbursement> sporadicReimbursementDataExport(SporadicReimbursement sporadicReimbursement);

    @SelectProvider(type= DirectoryDao.class,method = "maternityMedicalExpensesData")
    Page<MaternityMedicalExpenses> maternityMedicalExpensesData(Page page, MaternityMedicalExpenses maternityMedicalExpenses);

    @SelectProvider(type= DirectoryDao.class,method = "maternityMedicalExpensesDataExport")
    List<MaternityMedicalExpenses> maternityMedicalExpensesDataExport(MaternityMedicalExpenses maternityMedicalExpenses);


    @SelectProvider(type= DirectoryDao.class,method = "maternityBenefitsData")
    Page<MaternityBenefits> maternityBenefitsData(Page page, MaternityBenefits maternityBenefits);


    @SelectProvider(type= DirectoryDao.class,method = "maternityBenefitsDataExport")
    List<MaternityBenefits> maternityBenefitsDataExport(MaternityBenefits maternityBenefits);

    @SelectProvider(type= DirectoryDao.class,method = "reimbursementSettlementData")
    Page<ReimbursementSettlement> reimbursementSettlementData(Page page, ReimbursementSettlement reimbursementSettlement);


    @SelectProvider(type= DirectoryDao.class,method = "reimbursementSettlementDataExport")
    List<ReimbursementSettlement> reimbursementSettlementDataExport(ReimbursementSettlement reimbursementSettlement);


    @SelectProvider(type= DirectoryDao.class,method = "fertilitySettlementProgressData")
    Page<FertilitySettlementProgress> fertilitySettlementProgressData(Page page, FertilitySettlementProgress fertilitySettlementProgress);

    @SelectProvider(type= DirectoryDao.class,method = "fertilitySettlementProgressDataExport")
    List<FertilitySettlementProgress> fertilitySettlementProgressDataExport(FertilitySettlementProgress fertilitySettlementProgress);

    @SelectProvider(type= DirectoryDao.class,method = "offsiteFilingData")
    Page<OffsiteFiling> offsiteFilingData(Page page, OffsiteFiling offsiteFiling);

    @SelectProvider(type= DirectoryDao.class,method = "offsiteFilingDataExport")
    List<OffsiteFiling> offsiteFilingDataExport(OffsiteFiling offsiteFiling);


    @SelectProvider(type= DirectoryDao.class,method = "medicineInfoData")
    Page<MedicineInfo> medicineInfoData(Page page, MedicineInfo medicineInfo);

    @SelectProvider(type= DirectoryDao.class,method = "medicineInfoDataExport")
    List<MedicineInfo> medicineInfoDataExport(MedicineInfo medicineInfo);

    @SelectProvider(type= DirectoryDao.class,method = "medicalServiceData")
    Page<MedicalService> medicalServiceData(Page page, MedicalService medicalService);

    @SelectProvider(type= DirectoryDao.class,method = "medicalServiceDataExport")
    List<MedicalService> medicalServiceDataExport(MedicalService medicalService);

    @SelectProvider(type= DirectoryDao.class,method = "medicalConsumablesData")
    Page<MedicalConsumables> medicalConsumablesData(Page page, MedicalConsumables medicalConsumables);

    @SelectProvider(type= DirectoryDao.class,method = "medicalConsumablesDataExport")
    List<MedicalConsumables> medicalConsumablesDataExport(MedicalConsumables medicalConsumables);

    @SelectProvider(type= DirectoryDao.class,method = "billingComparison")
    List<SetlDVo> billingComparison(SetlDVo setlDVo);

    @SelectProvider(type= DirectoryDao.class,method = "insuranceData")
    Page<Insurance> insuranceData(Page page, Insurance insurance);

    @SelectProvider(type= DirectoryDao.class,method = "insuranceDataExport")
    List<Insurance> insuranceDataExport(Insurance insurance);

}
