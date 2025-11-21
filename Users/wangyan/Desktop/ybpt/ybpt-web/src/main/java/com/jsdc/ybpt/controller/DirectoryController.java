package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.model_query.IncomeAndExpenditure;
import com.jsdc.ybpt.model_query.medicalOrg.BasicFix;
import com.jsdc.ybpt.model_query.personnel.OpspRegEvt;
import com.jsdc.ybpt.service.DirectoryService;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/directory")
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;


    /**
    *2.2.2按目录项目查询使用情况
    * Author wzn
    * Date 2022/7/26 14:24
    */
    @RequestMapping("/selectCatalogItem")
    public ResultInfo selectCatalogItem(@RequestBody CatalogItem catalogItem) {
        Page<CatalogItem> catalogItemPage = directoryService.selectCatalogItem(catalogItem);
        return ResultInfo.success(catalogItemPage);
    }

    @RequestMapping("/queryOpsp")
    public ResultInfo queryOpsp(@RequestBody OpspRegEvt opspRegEvt) {
        Page<OpspRegEvt> opspRegEvtPage = directoryService.queryOpsp(opspRegEvt);
        return ResultInfo.success(opspRegEvtPage);
    }

    /**
    *2.6.2定点医药机构基本信息
    * Author wzn
    * Date 2022/8/3 10:00
    */
    @RequestMapping("/basicMedicalInfo")
    public ResultInfo basicMedicalInfo(@RequestBody BasicFix basicFix) {
        Page<BasicFix> basicFixPage = directoryService.basicMedicalInfo(basicFix);
        return ResultInfo.success(basicFixPage);
    }

    /**
    *2.5.14个人账户收支情况查询
    * Author wzn
    * Date 2022/8/10 12:07
    */
    @RequestMapping("/personalIncomeAndExpenditure")
    public ResultInfo personalIncomeAndExpenditure(@RequestBody IncomeAndExpenditure incomeAndExpenditure) {
        Page<IncomeAndExpenditure> basicFixPage = directoryService.personalIncomeAndExpenditure(incomeAndExpenditure);
        return ResultInfo.success(basicFixPage);
    }


    @RequestMapping("/basicMedicalInfoExport")
    public void exportBirthsettlement(HttpServletResponse response, @RequestBody BasicFix basicFix) throws Exception{
        List<BasicFix> details = directoryService.basicMedicalInfoExport(basicFix);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "医药机构代码");
        writer.addHeaderAlias("fixmedins_name", "医药机构名称");
        writer.addHeaderAlias("medins_abbr", "医疗机构简称");
        writer.addHeaderAlias("fixmedins_type", "医药机构服务类型");
        writer.addHeaderAlias("poolarea_no", "行政区");
        writer.addHeaderAlias("fix_blng_admdvs", "定点归属医保区划");
        writer.addHeaderAlias("hosp_lv", "医院等级");
        writer.addHeaderAlias("lmtpric_hosp_lv", "限价医院等级");
        writer.addHeaderAlias("dedc_hosp_lv", "起付线医院等级");
        writer.addHeaderAlias("hi_resper_name", "医保办负责人姓名");
        writer.addHeaderAlias("hi_resper_tel", "医保办负责人联系电话");
        writer.addHeaderAlias("hi_resper_cert_type", "医保办负责人证件类型");
        writer.addHeaderAlias("hi_resper_certno", "医保办负责人证件号码");
        writer.addHeaderAlias("nat_plaf_code", "国家异地平台机构编号");
        writer.addHeaderAlias("prov_plaf_code", "省内异地平台机构编号");
        writer.addHeaderAlias("fix_onln_open_flag", "定点联网开通标志");
        writer.addHeaderAlias("out_onln_open_type", "异地联网开通类型");
        writer.addHeaderAlias("out_fixmedins_type", "异地医药机构类型");
        writer.addHeaderAlias("begntime", "开始时间");
        writer.addHeaderAlias("endtime", "结束时间");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
    *2.5.14个人账户收支情况查询导出
    * Author wzn
    * Date 2022/8/11 15:11
    */
    @RequestMapping("/personalIncomeAndExpenditureExport")
    public void personalIncomeAndExpenditureExport(HttpServletResponse response, @RequestBody IncomeAndExpenditure incomeAndExpenditure) throws Exception{
        List<IncomeAndExpenditure> details = directoryService.personalIncomeAndExpenditureExport(incomeAndExpenditure);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("psn_name", "姓名");
        writer.addHeaderAlias("certno", "证件号码");
        writer.addHeaderAlias("insu_admdvs", "医保区划");
        writer.addHeaderAlias("year", "年度");
        writer.addHeaderAlias("crte_time", "收支时间");
        writer.addHeaderAlias("acct_incexpd_type", "账户收支类型");
        writer.addHeaderAlias("inc_sumamt", "收支总金额");
        writer.addHeaderAlias("optins_no", "经办机构编号");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
    *2.1零报数据查询
    * Author wzn
    * Date 2022/8/17 10:18
    */
    @RequestMapping("/zeroReportData")
    public ResultInfo personalIncomeAndExpenditure(@RequestBody ZeroReport zeroReport) {
        Page<ZeroReport> zeroReportPage = directoryService.zeroReportData(zeroReport);
        return ResultInfo.success(zeroReportPage);
    }

/**
*零报数据查询导出
* Author wzn
* Date 2022/8/17 10:22
*/
    @RequestMapping("/zeroReportDataExport")
    public void personalIncomeAndExpenditureExport(HttpServletResponse response, @RequestBody ZeroReport zeroReport) throws Exception{
        List<ZeroReport> zeroReportList = directoryService.zeroReportDataExport(zeroReport);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("medicalCategory", "医疗类别");
        writer.addHeaderAlias("totalMedicalExpenses", "医疗费总额");
        writer.addHeaderAlias("accountPayment", "账户支付");
        writer.addHeaderAlias("totalFundPayments", "基金支付总额");
        writer.addHeaderAlias("paymentAmount", "支付金额");
        writer.addHeaderAlias("fundPayment", "基金支付");
        writer.addHeaderAlias("largeFundPayment", "大额基金支付");
        writer.addHeaderAlias("civilServiceGrant", "公务员补助");
        writer.addHeaderAlias("criticalIllnessFundPayments", "大病基金支付");
        writer.addHeaderAlias("supplementaryFundPayments", "补充基金支付");
        writer.addHeaderAlias("personalManagementCode", "个人管理编码");
        writer.addHeaderAlias("socialSecurityNumber", "社会保障号");
        writer.addHeaderAlias("fullName", "姓名");
        writer.addHeaderAlias("admissionDate", "入院日期");
        writer.addHeaderAlias("dischargeDate", "出院日期");
        writer.addHeaderAlias("acceptanceNumber", "受理号");
        writer.addHeaderAlias("settlementTime", "结算时间");
        writer.addHeaderAlias("reviewTime", "复核时间");
        writer.addHeaderAlias("manager", "经办人");
        writer.addHeaderAlias("personnelCategory", "人员类别");
        writer.addHeaderAlias("overallPlanningArea", "参保所属统筹区");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(zeroReportList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
    *2.1.2零报待复核数据查询
    * Author wzn
    * Date 2022/8/17 14:37
    */
    @RequestMapping("/zeroReportViewData")
    public ResultInfo personalIncomeAndExpenditure(@RequestBody ZeroReportReview zeroReportReview) {
        Page<ZeroReportReview> zeroReportReviewPage = directoryService.zeroReportViewData(zeroReportReview);
        return ResultInfo.success(zeroReportReviewPage);
    }


    /**
    *2.1.2零报待复核数据导出
    * Author wzn
    * Date 2022/8/17 14:40
    */
    @RequestMapping("/zeroReportDataViewExport")
    public void zeroReportDataViewExport(HttpServletResponse response, @RequestBody ZeroReportReview zeroReportReview) throws Exception{
        List<ZeroReportReview> zeroReportReviewList = directoryService.zeroReportDataViewExport(zeroReportReview);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fullName", "姓名");
        writer.addHeaderAlias("identityNumber", "身份证号");
        writer.addHeaderAlias("manager", "经办人");
        writer.addHeaderAlias("settlementTime", "结算时间");
        writer.addHeaderAlias("way", "途径");
        writer.addHeaderAlias("overallPlanningArea", "参保所属统筹区");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(zeroReportReviewList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }



    /**
    *2.2.2用药范围查询
    * Author wzn
    * Date 2022/8/19 10:15
    */
    @RequestMapping("/scopeOfMedicationData")
    public ResultInfo scopeOfMedicationData(@RequestBody ScopeOfMedication scopeOfMedication) {
        Page<ScopeOfMedication> scopeOfMedicationPage = directoryService.scopeOfMedicationData(scopeOfMedication);
        return ResultInfo.success(scopeOfMedicationPage);
    }


    /**
    *2.2.2用药范围查询导出接口
    * Author wzn
    * Date 2022/8/19 10:16
    */
    @RequestMapping("/scopeOfMedicationDataExport")
    public void zeroReportDataViewExport(HttpServletResponse response, @RequestBody ScopeOfMedication scopeOfMedication) throws Exception{
        List<ScopeOfMedication> scopeOfMedicationList = directoryService.scopeOfMedicationDataExport(scopeOfMedication);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("dssdcCode", "门慢门特病种目录代码");
        writer.addHeaderAlias("typeOfBusinessApplication", "业务申请类型");
        writer.addHeaderAlias("typeOfInsurancep", "险种类型");
        writer.addHeaderAlias("medicalInsuranceZoning", "参保所属医保区划");
        writer.addHeaderAlias("medicalInsuranceDirectoryCode", "医保目录编码");
        writer.addHeaderAlias("medicalInsuranceDirectoryName", "医保目录名称");
        writer.addHeaderAlias("startDate", "开始日期");
        writer.addHeaderAlias("endDate", "结束日期");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(scopeOfMedicationList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }



    /**
    *2.5.7零星报销受理情况查询
    * Author wzn
    * Date 2022/8/25 9:46
    */
    @RequestMapping("/sporadicReimbursementData")
    public ResultInfo sporadicReimbursementData(@RequestBody SporadicReimbursement sporadicReimbursement) {
        Page<SporadicReimbursement> sporadicReimbursementPage = directoryService.sporadicReimbursementData(sporadicReimbursement);
        return ResultInfo.success(sporadicReimbursementPage);
    }



    /**
    *2.5.7零星报销受理情况查询导出
    * Author wzn
    * Date 2022/8/25 10:13
    */
    @RequestMapping("/sporadicReimbursementDataExport")
    public void sporadicReimbursementDataExport(HttpServletResponse response, @RequestBody SporadicReimbursement sporadicReimbursement) throws Exception{
        List<SporadicReimbursement> sporadicReimbursements = directoryService.sporadicReimbursementDataExport(sporadicReimbursement);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("identityNumber", "身份证号");
        writer.addHeaderAlias("typeOfInsurance", "险种类型");
        writer.addHeaderAlias("medicalCategory", "医疗类别");
        writer.addHeaderAlias("acceptanceNumber", "就医开始时间");
        writer.addHeaderAlias("endTime", "就医结束时间");
        writer.addHeaderAlias("checkInTime", "登记时间");
        writer.addHeaderAlias("registrar", "登记经办人");
        writer.addHeaderAlias("companyName", "单位名称");
        writer.addHeaderAlias("civilServantSign", "公务员标志");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(sporadicReimbursements, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
    *2.5.8生育医疗费受理情况查询
    * Author wzn
    * Date 2022/8/25 11:29
    */
    @RequestMapping("/maternityMedicalExpensesData")
    public ResultInfo maternityMedicalExpensesData(@RequestBody MaternityMedicalExpenses maternityMedicalExpenses) {
        Page<MaternityMedicalExpenses> maternityMedicalExpensesPage = directoryService.maternityMedicalExpensesData(maternityMedicalExpenses);
        return ResultInfo.success(maternityMedicalExpensesPage);
    }



    /**
    *生育医疗费受理情况查询导出
    * Author wzn
    * Date 2022/8/25 11:29
    */
    @RequestMapping("/maternityMedicalExpensesDataExport")
    public void maternityMedicalExpensesDataExport(HttpServletResponse response, @RequestBody MaternityMedicalExpenses maternityMedicalExpenses) throws Exception{
        List<MaternityMedicalExpenses> maternityMedicalExpensesList = directoryService.maternityMedicalExpensesDataExport(maternityMedicalExpenses);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fullName", "姓名");
        writer.addHeaderAlias("gender", "性别");
        writer.addHeaderAlias("identityNumber", "身份证号");
        writer.addHeaderAlias("typeOfInsurance", "险种类型");
        writer.addHeaderAlias("birthRegistrationCategory", "生育登记类别");
        writer.addHeaderAlias("birthDate", "计划生育手术或生育日期");
        writer.addHeaderAlias("checkInTime", "登记时间");
        writer.addHeaderAlias("registrar", "登记经办人");
        writer.addHeaderAlias("companyName", "单位名称");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(maternityMedicalExpensesList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
    *2.5.9生育津贴受理情况查询
    * Author wzn
    * Date 2022/8/26 15:05
    */
    @RequestMapping("/maternityBenefitsData")
    public ResultInfo maternityMedicalExpensesData(@RequestBody MaternityBenefits maternityBenefits) {
        Page<MaternityBenefits> maternityBenefitsPage = directoryService.maternityBenefitsData(maternityBenefits);
        return ResultInfo.success(maternityBenefitsPage);
    }



    /**
    *生育津贴受理情况查询导出
    * Author wzn
    * Date 2022/8/26 15:06
    */
    @RequestMapping("/maternityBenefitsDataExport")
    public void maternityMedicalExpensesDataExport(HttpServletResponse response, @RequestBody MaternityBenefits maternityBenefits) throws Exception{
        List<MaternityBenefits> maternityBenefitsList = directoryService.maternityBenefitsDataExport(maternityBenefits);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fullName", "姓名");
        writer.addHeaderAlias("gender", "性别");
        writer.addHeaderAlias("identityNumber", "身份证号");
        writer.addHeaderAlias("typeOfInsurance", "险种类型");
        writer.addHeaderAlias("birthRegistrationCategory", "生育登记类别");
        writer.addHeaderAlias("birthDate", "计划生育手术或生育日期");
        writer.addHeaderAlias("nextToTheWomb", "胎次");
        writer.addHeaderAlias("numberOfFetuses", "胎儿数");
        writer.addHeaderAlias("checkInTime", "登记时间");
        writer.addHeaderAlias("registrar", "登记经办人");
        writer.addHeaderAlias("companyName", "单位名称");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(maternityBenefitsList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
     * 2.5.10零星报销结算进度查询
     * @param
     * @return
     */
    @RequestMapping("/reimbursementSettlementData")
    public ResultInfo reimbursementSettlementData(@RequestBody ReimbursementSettlement reimbursementSettlement) {
        Page<ReimbursementSettlement> reimbursementSettlementPage = directoryService.reimbursementSettlementData(reimbursementSettlement);
        return ResultInfo.success(reimbursementSettlementPage);
    }


    /**
     * 2.5.10零星报销结算进度查询导出
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping("/reimbursementSettlementDataExport")
    public void reimbursementSettlementDataExport(HttpServletResponse response, @RequestBody ReimbursementSettlement reimbursementSettlement) throws Exception{
        List<ReimbursementSettlement> reimbursementSettlementList = directoryService.reimbursementSettlementDataExport(reimbursementSettlement);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fullName", "姓名");
        writer.addHeaderAlias("identityNumber", "身份证号");
        writer.addHeaderAlias("medicalCategory", "医疗类别");
        writer.addHeaderAlias("institutionName", "定点医药机构名称");
        writer.addHeaderAlias("startDateOfMedicalTreatment", "就医开始日期");
        writer.addHeaderAlias("endDateOfMedicalTreatment", "就医结束日期");
        writer.addHeaderAlias("totalCost", "费用总额");
        writer.addHeaderAlias("currentBusinessSegment", "当前业务环节");
        writer.addHeaderAlias("followUpBusiness", "后续业务环节");
        writer.addHeaderAlias("auditResults", "审核结果");
        writer.addHeaderAlias("auditOpinion", "审核意见");
        writer.addHeaderAlias("manager", "经办人");
        writer.addHeaderAlias("handlingTime", "经办时间");
        writer.addHeaderAlias("timeoutDays", "超时天数");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(reimbursementSettlementList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
     *
     * 2.5.11生育待遇结算进度查询
     * @param
     * @return
     */
    @RequestMapping("/fertilitySettlementProgressData")
    public ResultInfo reimbursementSettlementData(@RequestBody FertilitySettlementProgress fertilitySettlementProgress) {
        Page<FertilitySettlementProgress> fertilitySettlementProgressPage = directoryService.fertilitySettlementProgressData(fertilitySettlementProgress);
        return ResultInfo.success(fertilitySettlementProgressPage);
    }


    /**
     * 2.5.11生育待遇结算进度查询
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping("/fertilitySettlementProgressDataExport")
    public void reimbursementSettlementDataExport(HttpServletResponse response, @RequestBody FertilitySettlementProgress fertilitySettlementProgress) throws Exception{
        List<FertilitySettlementProgress> fertilitySettlementProgressList = directoryService.fertilitySettlementProgressDataExport(fertilitySettlementProgress);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fullName", "姓名");
        writer.addHeaderAlias("identityNumber", "身份证号");
        writer.addHeaderAlias("gender", "性别");
        writer.addHeaderAlias("companyName", "单位名称");
        writer.addHeaderAlias("treatmentType", "待遇类型");
        writer.addHeaderAlias("fertilityCategory", "生育类别");
        writer.addHeaderAlias("birthDate", "计划生育手术或生育日期");
        writer.addHeaderAlias("totalMedicalExpenses", "医疗费用总额");
        writer.addHeaderAlias("amountOfMedicalExpensesPaid", "医疗费支付金额");
        writer.addHeaderAlias("maternityBenefits", "生育津贴");
        writer.addHeaderAlias("oneTimeNutritionFee", "一次性营养费");
        writer.addHeaderAlias("prenatalCheckupFee", "产前检查费");
        writer.addHeaderAlias("maleEmployeeCareAllowance", "男职工护理津贴");
        writer.addHeaderAlias("currentBusinessSegment", "当前业务环节");
        writer.addHeaderAlias("followUpBusiness", "后续业务环节");
        writer.addHeaderAlias("auditResults", "审核结果");
        writer.addHeaderAlias("auditOpinion", "审核意见");
        writer.addHeaderAlias("manager", "经办人");
        writer.addHeaderAlias("handlingTime", "经办时间");
        writer.addHeaderAlias("timeoutDays", "超时天数");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(fertilitySettlementProgressList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
     * 异地就医备案情况查询
     * @param
     * @return
     */
    @RequestMapping("/offsiteFilingData")
    public ResultInfo offsiteFilingData(@RequestBody OffsiteFiling offsiteFiling) {
        Page<OffsiteFiling> offsiteFilingPage = directoryService.offsiteFilingData(offsiteFiling);
        return ResultInfo.success(offsiteFilingPage);
    }

    /**
     * 异地就医备案情况查询导出
     * @param
     * @return
     */
    @RequestMapping("/offsiteFilingDataExport")
    public void reimbursementSettlementDataExport(HttpServletResponse response, @RequestBody OffsiteFiling offsiteFiling) throws Exception{
        List<OffsiteFiling> offsiteFilingList = directoryService.offsiteFilingDataExport(offsiteFiling);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fullName", "姓名");
        writer.addHeaderAlias("identityNumber", "身份证号");
        writer.addHeaderAlias("medicalInsuranceZoning", "医保区划");
        writer.addHeaderAlias("personType", "人员类型");
        writer.addHeaderAlias("filingType", "备案类型");
        writer.addHeaderAlias("typeOfInsurance", "险种类型");
        writer.addHeaderAlias("placeOfPlacement", "安置地");
        writer.addHeaderAlias("administrativeRegion", "所属行政区");
        writer.addHeaderAlias("startDate", "开始日期");
        writer.addHeaderAlias("endDate", "结束日期");
        writer.addHeaderAlias("applicationTime", "申请时间");
        writer.addHeaderAlias("manager", "经办人");
        writer.addHeaderAlias("validState", "有效状态");
        writer.addHeaderAlias("retirementSign", "离退休标志");
        writer.addHeaderAlias("declarationSource", "申报来源");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(offsiteFilingList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
    *药品目录信息查询
    * Author wzn
    * Date 2022/9/5 14:36
    */
    @RequestMapping("/medicineInfoData")
    public ResultInfo offsiteFilingData(@RequestBody MedicineInfo medicineInfo) {
        Page<MedicineInfo> medicineInfoPage = directoryService.medicineInfoData(medicineInfo);
        return ResultInfo.success(medicineInfoPage);
    }

    @RequestMapping("/medicineInfoDataExport")
    public void medicineInfoDataExport(HttpServletResponse response, @RequestBody MedicineInfo medicineInfo) throws Exception{
        List<MedicineInfo> medicineInfoList = directoryService.medicineInfoDataExport(medicineInfo);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("med_list_codg", "医疗目录编码");
        writer.addHeaderAlias("drug_prodname", "药品商品名");
        writer.addHeaderAlias("drug_genname", "药品通用名");
        writer.addHeaderAlias("drug_dosform", "药品剂型");
        writer.addHeaderAlias("drug_spec", "药品规格");
        writer.addHeaderAlias("pacspec", "包装规格");
        writer.addHeaderAlias("begndate", "开始日期");
        writer.addHeaderAlias("enddate", "结束日期");
        writer.addHeaderAlias("prodentp_name", "生产企业名称");
        writer.addHeaderAlias("lmt_usescp", "限制使用范围");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(medicineInfoList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
    *医疗服务项目信息查询
    * Author wzn
    * Date 2022/9/7 14:26
    */
    @RequestMapping("/medicalServiceData")
    public ResultInfo medicalServiceData(@RequestBody MedicalService medicalService) {
        Page<MedicalService> medicalServicePage = directoryService.medicalServiceData(medicalService);
        return ResultInfo.success(medicalServicePage);
    }



    /**
    *医疗服务项目信息查询导出
    * Author wzn
    * Date 2022/9/7 14:26
    */
    @RequestMapping("/medicalServiceDataExport")
    public void medicalServiceDataExport(HttpServletResponse response, @RequestBody MedicalService medicalService) throws Exception{
        List<MedicalService> medicalServiceList = directoryService.medicalServiceDataExport(medicalService);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("med_list_codg", "医疗目录编码");
        writer.addHeaderAlias("med_chrgitm_type", "医疗收费项目类别");
        writer.addHeaderAlias("chrgitm_lv", "收费项目等级");
        writer.addHeaderAlias("prcunt", "计价单位");
        writer.addHeaderAlias("trt_exct_cont", "诊疗除外内容");
        writer.addHeaderAlias("trt_item_cont", "诊疗项目内涵");
        writer.addHeaderAlias("servitem_name", "医疗服务项目名称");
        writer.addHeaderAlias("begndate", "开始日期");
        writer.addHeaderAlias("enddate", "结束日期");
        writer.addHeaderAlias("selfpay_prop_psn_type", "自付比列类型");
        writer.addHeaderAlias("selfpay_prop", "自付比列");
        writer.addHeaderAlias("hilist_lmtpric_type", "限价类型");
        writer.addHeaderAlias("hilist_pric_uplmt_amt", "医保目录定价上限金额");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(medicalServiceList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
    *医用耗材目录信息查询
    * Author wzn
    * Date 2022/9/8 14:32
    */
    @RequestMapping("/medicalConsumablesData")
    public ResultInfo medicalServiceData(@RequestBody MedicalConsumables medicalConsumables) {
        Page<MedicalConsumables> medicalConsumablesPage = directoryService.medicalConsumablesData(medicalConsumables);
        return ResultInfo.success(medicalConsumablesPage);
    }



    /**
    *医用耗材目录信息查询导出
    * Author wzn
    * Date 2022/9/8 14:34
    */
    @RequestMapping("/medicalConsumablesDataExport")
    public void medicalServiceDataExport(HttpServletResponse response, @RequestBody MedicalConsumables medicalConsumables) throws Exception{
        List<MedicalConsumables> medicalConsumablesList = directoryService.medicalConsumablesDataExport(medicalConsumables);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("mcs_name", "耗材名称");
        writer.addHeaderAlias("med_list_codg", "医疗目录编码");
        writer.addHeaderAlias("med_chrgitm_type", "医疗收费项目类别");
        writer.addHeaderAlias("chrgitm_lv", "收费项目等级");
        writer.addHeaderAlias("begndate", "开始日期");
        writer.addHeaderAlias("enddate", "结束日期");
        writer.addHeaderAlias("selfpay_prop_psn_type", "自付比列类型");
        writer.addHeaderAlias("selfpay_prop", "自付比列");
        writer.addHeaderAlias("hilist_lmtpric_type", "限价类型");
        writer.addHeaderAlias("hilist_pric_uplmt_amt", "医保目录定价上限金额");
        writer.addHeaderAlias("lmt_used_flag", "限制使用标志");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(medicalConsumablesList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


    /**
    *结算发生数据对比
    * Author wzn
    * Date 2022/9/14 11:18
    */
    @RequestMapping("/billingComparison")
    public ResultInfo billingComparison(@RequestBody SetlDVo setlDVo) {
        List<SetlDVo> setlDVoPage = directoryService.billingComparison(setlDVo);
        if(setlDVoPage.size()>1){
            throw new CustomException("查询到多条数据，请缩小查询范围");
        }
        if(null != setlDVoPage && setlDVoPage.size()>0){
            return ResultInfo.success(setlDVoPage.get(0));
        }else {
            return ResultInfo.success();
        }
    }


    /**
     *结算数据比对
     * Author wzn
     * Date 2022/9/15 9:47
     */
    @RequestMapping("/contrast")
    public ResultInfo contrast(@RequestBody SetlDVo setlDVo,@RequestBody SetlDVo setlDVo2) {
        List<Map<String,String>> setlDVoPage = directoryService.contrast(setlDVo,setlDVo2);
        return ResultInfo.success();
    }

    /**
     *3.6城乡居民基本医疗保险人员统计表
     * Author wzn
     * Date 2022/9/16 11:28
     */
    @RequestMapping("/insuranceData")
    public ResultInfo medicalServiceData(@RequestBody Insurance insurance) {
        Page<Insurance> insurancePage = directoryService.insuranceData(insurance);
        return ResultInfo.success(insurancePage);
    }

    @RequestMapping("/insuranceDataExport")
    public void medicalServiceDataExport(HttpServletResponse response, @RequestBody Insurance insurance) throws Exception{
        List<Insurance> insuranceList = directoryService.insuranceDataExport(insurance);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("insu_admdvs", "参保所属医保区划");
        writer.addHeaderAlias("psn_type", "人员类别");
        writer.addHeaderAlias("count", "合计");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(insuranceList, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }


}
