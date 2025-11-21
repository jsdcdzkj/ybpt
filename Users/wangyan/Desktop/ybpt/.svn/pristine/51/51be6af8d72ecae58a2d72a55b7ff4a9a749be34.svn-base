package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.QueryMapper;
import com.jsdc.ybpt.model_query.*;
import com.jsdc.ybpt.model_query.medicalOrg.FixmedinsCntrRegD;
import com.jsdc.ybpt.model_query.medicalOrg.FixmedinsGather;
import com.jsdc.ybpt.model_query.medicalOrg.School;
import com.jsdc.ybpt.model_query.personnel.OrganizationGinseng;
import com.jsdc.ybpt.model_query.personnel.PersonalChanges;
import com.jsdc.ybpt.model_query.personnel.ReflAppyEvtCVo;
import com.jsdc.ybpt.model_query.settlement.*;
import com.jsdc.ybpt.service.QueryService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private QueryService service;
    @Autowired
    private QueryMapper mapper;

    /**
     * 医师信息
     *
     * @return
     */
    @RequestMapping("/drInfoB")
    public ResultInfo drInfoB(DrInfoB drInfoB) {
        return service.drInfoB(drInfoB);
    }

    /**
     * 单位参保信息
     *
     * @return
     */
    @RequestMapping("/empInsuD")
    public ResultInfo empInsuD(EmpInsuD empInsuD) {
        return service.empInsuD(empInsuD);
    }

    /**
     * 参保单位信息
     *
     * @return
     */
    @RequestMapping("/insuEmpInfoB")
    public ResultInfo insuEmpInfoB(InsuEmpInfoB insuEmpInfoB) {
        return service.insuEmpInfoB(insuEmpInfoB);
    }

    /**
     * 医疗机构信息表
     *
     * @return
     */
    @RequestMapping("/medinsInfoB")
    public ResultInfo medinsInfoB(MedinsInfoB medinsInfoB) {
        return service.medinsInfoB(medinsInfoB);
    }

    /**
     * 医疗技师信息
     *
     * @return
     */
    @RequestMapping("/medTecnInfoB")
    public ResultInfo medTecnInfoB(MedTecnInfoB medTecnInfoB) {
        return service.medTecnInfoB(medTecnInfoB);
    }

    /**
     * 护士信息
     *
     * @return
     */
    @RequestMapping("/nursInfoB")
    public ResultInfo nursInfoB(NursInfoB nursInfoB) {
        return service.nursInfoB(nursInfoB);
    }

    /**
     * 门慢门特登记事件记录表
     *
     * @return
     */
    @RequestMapping("/opspRegEvtC")
    public ResultInfo opspRegEvtC(OpspRegEvtC opspRegEvtC) {
        return service.opspRegEvtC(opspRegEvtC);
    }


    /**
     * 经办人员信息
     *
     * @return
     */
    @RequestMapping("/optPsnB")
    public ResultInfo optPsnB(OptPsnB optPsnB) {
        return service.optPsnB(optPsnB);
    }

    /**
     * 异地申请事件记录
     *
     * @return
     */
    @RequestMapping("/outAppyEvtC")
    public ResultInfo outAppyEvtC(OutAppyEvtC outAppyEvtC) {
        return service.outAppyEvtC(outAppyEvtC);
    }

    /**
     * 药师信息
     *
     * @return
     */
    @RequestMapping("/pharInfoB")
    public ResultInfo pharInfoB(PharInfoB pharInfoB) {
        return service.pharInfoB(pharInfoB);
    }

    /**
     * 专家信息
     *
     * @return
     */
    @RequestMapping("/profInfoB")
    public ResultInfo eprofInfoB(ProfInfoB profInfoB) {
        return service.profInfoB(profInfoB);
    }

    /**
     * 人员缴费基数信息表
     *
     * @return
     */
    @RequestMapping("/psnClctstdD")
    public ResultInfo psnClctstdD(PsnClctstdD psnClctstdD) {
        return service.psnClctstdD(psnClctstdD);
    }

    /**
     * 人员参保信息
     *
     * @return
     */
    @RequestMapping("/psnInsuD")
    public ResultInfo psnInsuD(PsnInsuD psnInsuD) {
        return service.psnInsuD(psnInsuD);
    }

    /**
     * 人员参保状态
     *
     * @return
     */
    @RequestMapping("/psnInsuStasB")
    public ResultInfo psnInsuStasB(PsnInsuStasB psnInsuStasB) {
        return service.psnInsuStasB(psnInsuStasB);
    }

    /**
     * 零售药店信息
     *
     * @return
     */
    @RequestMapping("/rtalPhacB")
    public ResultInfo rtalPhacB(RtalPhacB rtalPhacB) {
        return service.rtalPhacB(rtalPhacB);
    }

    /**
     * 特药审批备案事件记录
     *
     * @return
     */
    @RequestMapping("/spdrugApprFilEvtC")
    public ResultInfo spdrugApprFilEvtC(SpdrugApprFilEvtC spdrugApprFilEvtC) {
        return service.spdrugApprFilEvtC(spdrugApprFilEvtC);
    }

    /**
     * 2.1.3零报办件数据查询
     *
     * @return
     */
    @RequestMapping("/sporadicHandle")
    public ResultInfo sporadic(SporadicHandle sporadicHandle) {
        return service.sporadicHandle(sporadicHandle);
    }
    @RequestMapping("/sporadicHandleExport")
    public void sporadicExport(HttpServletResponse response, SporadicHandle sporadicHandle) throws Exception {
        Page<SporadicHandle> page = mapper.sporadicHandle(new Page(1, 999), sporadicHandle);
        List<SporadicHandle> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "开始时间");
        writer.addHeaderAlias("todo", "终止时间");
        writer.addHeaderAlias("todo", "统筹区");
        writer.addHeaderAlias("todo", "受理号");
        writer.addHeaderAlias("todo", "受理时间");
        writer.addHeaderAlias("todo", "超时时间");
        writer.addHeaderAlias("todo", "结算时间");
        writer.addHeaderAlias("todo", "结算人员");
        writer.addHeaderAlias("todo", "一审时间");
        writer.addHeaderAlias("todo", "一审人员");
        writer.addHeaderAlias("todo", "二审时间");
        writer.addHeaderAlias("todo", "二审人员");
        writer.addHeaderAlias("todo", "身份证号码");
        writer.addHeaderAlias("todo", "姓名");
        writer.addHeaderAlias("todo", "医疗类别");
        writer.addHeaderAlias("todo", "发票金额");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.1.5徐州市生育津贴结算汇总查询
     *
     * @return
     */
    @RequestMapping("/childbirth")
    public ResultInfo childbirth(ChildbirthAllowance childbirthAllowance) {
        return service.childbirth(childbirthAllowance);
    }
    @RequestMapping("/childbirthExport")
    public void childbirthExport(HttpServletResponse response, ChildbirthAllowance childbirthAllowance) throws Exception {
        Page<ChildbirthAllowance> page = mapper.childbirth(new Page(1, 999), childbirthAllowance);
        List<ChildbirthAllowance> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "姓名");
        writer.addHeaderAlias("todo", "身份证号");
        writer.addHeaderAlias("todo", "参保人统筹区");
        writer.addHeaderAlias("todo", "人员类型");
        writer.addHeaderAlias("todo", "医疗类别");
        writer.addHeaderAlias("todo", "生育日期");
        writer.addHeaderAlias("todo", "月工资基数");
        writer.addHeaderAlias("todo", "津贴天数");
        writer.addHeaderAlias("todo", "津贴金额");
        writer.addHeaderAlias("todo", "营养费金额");
        writer.addHeaderAlias("todo", "合计金额");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.1.6住院费用重复数据查询
     *
     * @return
     */
    @RequestMapping("/hospitalization")
    public ResultInfo hospitalization(HospitalizationCosts hospitalizationCosts) {
        return service.hospitalization(hospitalizationCosts);
    }
    @RequestMapping("/hospitalizationExport")
    public void hospitalizationExport(HttpServletResponse response, HospitalizationCosts hospitalizationCosts) throws Exception {
        Page<HospitalizationCosts> page = mapper.hospitalization(new Page(1, 999), hospitalizationCosts);
        List<HospitalizationCosts> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "姓名");
        writer.addHeaderAlias("todo", "身份证号码");
        writer.addHeaderAlias("todo", "入院时间");
        writer.addHeaderAlias("todo", "出院时间");
        writer.addHeaderAlias("todo", "医疗费总额");
        writer.addHeaderAlias("todo", "参保所属统筹区");
        writer.addHeaderAlias("todo", "支付地点类别");
        writer.addHeaderAlias("todo", "结算人");
        writer.addHeaderAlias("todo", "结算时间");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.3.1结算数据月度分析
     *
     * @return
     */
    @RequestMapping("/setlDMonth")
    public ResultInfo setlDMonth(SetlDMonth setlDMonth) {
        return service.setlDMonth(setlDMonth);
    }
    @RequestMapping("/setlDMonthExport")
    public void setlDMonthExport(HttpServletResponse response, SetlDMonth setlDMonth) throws Exception {
        Page<SetlDMonth> page = mapper.setlDMonth(new Page(1, 999), setlDMonth);
        List<SetlDMonth> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "医药机构级别");
        writer.addHeaderAlias("fixmedins_code", "机构编码");
        writer.addHeaderAlias("fixmedins_code", "机构名称");
        writer.addHeaderAlias("fixmedins_code", "人次");
        writer.addHeaderAlias("fixmedins_code", "人数");
        writer.addHeaderAlias("fixmedins_code", "总费用");
        writer.addHeaderAlias("fixmedins_code", "统筹");
        writer.addHeaderAlias("fixmedins_code", "大额");
        writer.addHeaderAlias("fixmedins_code", "公务员");
        writer.addHeaderAlias("fixmedins_code", "个账");
        writer.addHeaderAlias("fixmedins_code", "现金");
        writer.addHeaderAlias("fixmedins_code", "补充保险");
        writer.addHeaderAlias("fixmedins_code", "医疗救助");
        writer.addHeaderAlias("fixmedins_code", "次均费用");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.3.2年终决算数据查询
     *
     * @return
     */
    @RequestMapping("/setlDYear")
    public ResultInfo setlDYear(SetlDYear setlDYear) {
        return service.SetlDYear(setlDYear);
    }
    @RequestMapping("/setlDYearExport")
    public void setlDYearExport(HttpServletResponse response, SetlDYear setlDYear) throws Exception {
        Page<SetlDYear> page = mapper.SetlDYear(new Page(1, 999), setlDYear);
        List<SetlDYear> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "统筹区");
        writer.addHeaderAlias("fixmedins_code", "定点医疗机构编码");
        writer.addHeaderAlias("fixmedins_code", "定点医疗机名称");
        writer.addHeaderAlias("fixmedins_code", "待遇类别");
        writer.addHeaderAlias("fixmedins_code", "结算类别");
        writer.addHeaderAlias("fixmedins_code", "人次");
        writer.addHeaderAlias("fixmedins_code", "人数");
        writer.addHeaderAlias("fixmedins_code", "百门诊人次");
        writer.addHeaderAlias("fixmedins_code", "百门诊住院率");
        writer.addHeaderAlias("fixmedins_code", "总费用");
        writer.addHeaderAlias("fixmedins_code", "住院天");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.3.3月结算数据汇总查询
     *
     * @return
     */
    @RequestMapping("/setlDMonthGather")
    public ResultInfo setlDMonthGather(SetlDMonthGather setlDMonthGather) {
        return service.setlDMonthGather(setlDMonthGather);
    }

    @RequestMapping("/setlDMonthGatherExport")
    public void setlDMonthGatherExport(HttpServletResponse response, SetlDMonthGather setlDMonthGather) throws Exception {
        Page<SetlDMonthGather> page = mapper.setlDMonthGather(new Page(1, 999), setlDMonthGather);
        List<SetlDMonthGather> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "机构编码");
        writer.addHeaderAlias("fixmedins_code", "机构名称");
        writer.addHeaderAlias("fixmedins_code", "统筹");
        writer.addHeaderAlias("fixmedins_code", "大额");
        writer.addHeaderAlias("fixmedins_code", "公务员");
        writer.addHeaderAlias("fixmedins_code", "个账");
        writer.addHeaderAlias("fixmedins_code", "现金");
        writer.addHeaderAlias("fixmedins_code", "补充保险");
        writer.addHeaderAlias("fixmedins_code", "医疗救助");
        writer.addHeaderAlias("fixmedins_code", "应支付小计");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.3.4结算信息汇总查询
     *
     * @return
     */
    @RequestMapping("/setlD")
    public ResultInfo setlD(SetlD setlD) {
        return service.setlD(setlD);
    }
    @RequestMapping("/setlDExport")
    public void setlDExport(HttpServletResponse response, SetlD setlDr) throws Exception {
        Page<SetlD> page = mapper.setlD(new Page(1, 999), setlDr);
        List<SetlD> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("poolarea_no", "统筹区");
        writer.addHeaderAlias("fixmedins_code", "定点医疗机构编码");
        writer.addHeaderAlias("fixmedins_name", "定点医疗机构名称");
        writer.addHeaderAlias("person_num", "就诊人次");
        writer.addHeaderAlias("person_count", "就诊人数");
        writer.addHeaderAlias("medfee_sumamt", "医疗总费用");
        writer.addHeaderAlias("pool_prop_selfpay", "基本医疗基金支出");
        writer.addHeaderAlias("hifob_pay", "大额医疗补助基金支出");
        writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助基金支出");
        writer.addHeaderAlias("acct_pay", "个人账户支出");
        writer.addHeaderAlias("cash_payamt", "个人现金支出");
        writer.addHeaderAlias("ownpay_hosp_part", "自费中医院承担部分");
        writer.addHeaderAlias("hifes_pay", "补充医疗保险基金支出");
        writer.addHeaderAlias("hifmi_pay", "大病保险基金支出");
        writer.addHeaderAlias("maf_pay", "医疗救助基金支出");
        writer.addHeaderAlias("hifdm_pay", "伤残人员医疗保障基金支出");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.4.1单病种费用查询
     *
     * @return
     */
    @RequestMapping("/setlDByDiseNoPay")
    public ResultInfo setlDByDiseNoPay(SetlDPay setlDPay) {
        return service.setlDByDiseNoPay(setlDPay);
    }
    @RequestMapping("/setlDByDiseNoPayExcel")
    public void setlDByDiseNoPayExcel(HttpServletResponse response, SetlDPay setlDPay) throws Exception {
        Page<SetlDPay> page = mapper.setlDByDiseNoPay(new Page(1, 999), setlDPay);
        List<SetlDPay> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点机构编码");
        writer.addHeaderAlias("fixmedins_code", "国家医院编号");
        writer.addHeaderAlias("fixmedins_code", "受理医疗类别");
        writer.addHeaderAlias("fixmedins_code", "结算医疗类别");
        writer.addHeaderAlias("fixmedins_code", "定点机构名称");
        writer.addHeaderAlias("fixmedins_code", "国家医院名称");
        writer.addHeaderAlias("fixmedins_code", "结算日期");
        writer.addHeaderAlias("fixmedins_code", "医疗费总额");
        writer.addHeaderAlias("fixmedins_code", "账户支付");
        writer.addHeaderAlias("fixmedins_code", "现金支付");
        writer.addHeaderAlias("fixmedins_code", "统筹支付");
        writer.addHeaderAlias("fixmedins_code", "大额支付");
        writer.addHeaderAlias("fixmedins_code", "二次补助支付");
        writer.addHeaderAlias("fixmedins_code", "医疗救助支付");
        writer.addHeaderAlias("fixmedins_code", "自费金额");
        writer.addHeaderAlias("fixmedins_code", "起付标准自付");
        writer.addHeaderAlias("fixmedins_code", "公务员补助支付");
        writer.addHeaderAlias("fixmedins_code", "参照公务员基金支付");
        writer.addHeaderAlias("fixmedins_code", "医院垫付");
        writer.addHeaderAlias("fixmedins_code", "其他基金支付");
        writer.addHeaderAlias("fixmedins_code", "总报销金额");
        writer.addHeaderAlias("fixmedins_code", "产前检查费");
        writer.addHeaderAlias("fixmedins_code", "自理费用");
        writer.addHeaderAlias("fixmedins_code", "范围内费用");
        writer.addHeaderAlias("fixmedins_code", "符合基本医疗费用");
        writer.addHeaderAlias("fixmedins_code", "进入统筹费用");
        writer.addHeaderAlias("fixmedins_code", "统筹分段自付");
        writer.addHeaderAlias("fixmedins_code", "进入大额费用");
        writer.addHeaderAlias("fixmedins_code", "大额自付");
        writer.addHeaderAlias("fixmedins_code", "超封顶线个人自付金额");
        writer.addHeaderAlias("fixmedins_code", "医院等级");
        writer.addHeaderAlias("fixmedins_code", "参保组织管理码");
        writer.addHeaderAlias("fixmedins_code", "单位名称");
        writer.addHeaderAlias("fixmedins_code", "个人管理编码");
        writer.addHeaderAlias("fixmedins_code", "社会保障");
        writer.addHeaderAlias("fixmedins_code", "姓名");
        writer.addHeaderAlias("fixmedins_code", "性别");
        writer.addHeaderAlias("fixmedins_code", "年龄");
        writer.addHeaderAlias("fixmedins_code", "就诊流水号");
        writer.addHeaderAlias("fixmedins_code", "单据号");
        writer.addHeaderAlias("fixmedins_code", "医疗人员类别");
        writer.addHeaderAlias("fixmedins_code", "病区");
        writer.addHeaderAlias("fixmedins_code", "科室");
        writer.addHeaderAlias("fixmedins_code", "床位号");
        writer.addHeaderAlias("fixmedins_code", "病案号");
        writer.addHeaderAlias("fixmedins_code", "住院号");
        writer.addHeaderAlias("fixmedins_code", "电话号码");
        writer.addHeaderAlias("fixmedins_code", "常住所在地地址");
        writer.addHeaderAlias("fixmedins_code", "入院日期");
        writer.addHeaderAlias("fixmedins_code", "入院病种识别码");
        writer.addHeaderAlias("fixmedins_code", "入院病种名称");
        writer.addHeaderAlias("fixmedins_code", "入院国家病种编码");
        writer.addHeaderAlias("fixmedins_code", "入院国家病种名称");
        writer.addHeaderAlias("fixmedins_code", "出院日期");
        writer.addHeaderAlias("fixmedins_code", "出院原因");
        writer.addHeaderAlias("fixmedins_code", "出院病种识别码");
        writer.addHeaderAlias("fixmedins_code", "出院病种名称");
        writer.addHeaderAlias("fixmedins_code", "出院国家病种编码");
        writer.addHeaderAlias("fixmedins_code", "出院国家病种名称");
        writer.addHeaderAlias("fixmedins_code", "本地政策参数");
        writer.addHeaderAlias("fixmedins_code", "主治医师编码");
        writer.addHeaderAlias("fixmedins_code", "主治医师姓名");
        writer.addHeaderAlias("fixmedins_code", "基本药物费用");
        writer.addHeaderAlias("fixmedins_code", "药品费用");
        writer.addHeaderAlias("fixmedins_code", "诊疗项目费用");
        writer.addHeaderAlias("fixmedins_code", "材料费用");
        writer.addHeaderAlias("fixmedins_code", "单病种限额");
        writer.addHeaderAlias("fixmedins_code", "统筹报销比例");
        writer.addHeaderAlias("fixmedins_code", "进入公补费用");
        writer.addHeaderAlias("fixmedins_code", "公补自付");
        writer.addHeaderAlias("fixmedins_code", "进入二次补助费用");
        writer.addHeaderAlias("fixmedins_code", "二次补助自付");
        writer.addHeaderAlias("fixmedins_code", "乙类药品");
        writer.addHeaderAlias("fixmedins_code", "乙类诊疗自理");
        writer.addHeaderAlias("fixmedins_code", "乙类材料");
        writer.addHeaderAlias("fixmedins_code", "灵活就业人员报销减少金额");
        writer.addHeaderAlias("fixmedins_code", "灵活就业人员超统筹封顶后进入费用");
        writer.addHeaderAlias("fixmedins_code", "灵活就业人员超统筹自付");
        writer.addHeaderAlias("fixmedins_code", "超标准自费床位费");
        writer.addHeaderAlias("fixmedins_code", "特殊材料自理");
        writer.addHeaderAlias("fixmedins_code", "账户余额");
        writer.addHeaderAlias("fixmedins_code", "审核扣款金额");
        writer.addHeaderAlias("fixmedins_code", "本年医疗费用累计");
        writer.addHeaderAlias("fixmedins_code", "本年现金支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年账户支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年统筹支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年大额支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年公补支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年参照公补支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年二次补助支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年符合基本医疗费用累计");
        writer.addHeaderAlias("fixmedins_code", "本年门诊起付标准累计");
        writer.addHeaderAlias("fixmedins_code", "本年住院起付标准累计");
        writer.addHeaderAlias("fixmedins_code", "本年进入统筹医疗用累计");
        writer.addHeaderAlias("fixmedins_code", "本年进入大额医疗费用累计");
        writer.addHeaderAlias("fixmedins_code", "本年进入二次报销费用累计");
        writer.addHeaderAlias("fixmedins_code", "本年门诊报销支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年门慢公补支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年门特报销支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年门特公补支付累计");
        writer.addHeaderAlias("fixmedins_code", "本年超封顶线个人自付累计");
        writer.addHeaderAlias("fixmedins_code", "本年住院次数累计");
        writer.addHeaderAlias("fixmedins_code", "基本药物统筹支付金额");
        writer.addHeaderAlias("fixmedins_code", "基本药物大额支付金额");
        writer.addHeaderAlias("fixmedins_code", "基本药物公务员补助支付金额");
        writer.addHeaderAlias("fixmedins_code", "基本药物参照公务员补助支付金额");
        writer.addHeaderAlias("fixmedins_code", "基本药物账户支付金额");
        writer.addHeaderAlias("fixmedins_code", "本次住院次数");
        writer.addHeaderAlias("fixmedins_code", "就医地行政区划代码");
        writer.addHeaderAlias("fixmedins_code", "出院描述");
        writer.addHeaderAlias("fixmedins_code", "离院方式");
        writer.addHeaderAlias("fixmedins_code", "按责任支付比例");
        writer.addHeaderAlias("fixmedins_code", "结算信息ID");
        writer.addHeaderAlias("fixmedins_code", "就诊信息ID");
        writer.addHeaderAlias("fixmedins_code", "结算年度");
        writer.addHeaderAlias("fixmedins_code", "报销标志");
        writer.addHeaderAlias("fixmedins_code", "业务周期号");
        writer.addHeaderAlias("fixmedins_code", "发送方交易流水号");
        writer.addHeaderAlias("fixmedins_code", "跨年报销标志");
        writer.addHeaderAlias("fixmedins_code", "支付状态");
        writer.addHeaderAlias("fixmedins_code", "财务支付流水号");
        writer.addHeaderAlias("fixmedins_code", "受理号");
        writer.addHeaderAlias("fixmedins_code", "有效标志");
        writer.addHeaderAlias("fixmedins_code", "备注");
        writer.addHeaderAlias("fixmedins_code", "说明");
        writer.addHeaderAlias("fixmedins_code", "中途结算标志");
        writer.addHeaderAlias("fixmedins_code", "住院天数");
        writer.addHeaderAlias("fixmedins_code", "跨年结算方式");
        writer.addHeaderAlias("fixmedins_code", "计划生育手术或生育日期");
        writer.addHeaderAlias("fixmedins_code", "就医地行政区划代码");
        writer.addHeaderAlias("fixmedins_code", "统筹区编码");
        writer.addHeaderAlias("fixmedins_code", "PSAM编号");
        writer.addHeaderAlias("fixmedins_code", "读卡器编号");
        writer.addHeaderAlias("fixmedins_code", "经办人");
        writer.addHeaderAlias("fixmedins_code", "经办时间");
        writer.addHeaderAlias("fixmedins_code", "医疗困难类型");
        writer.addHeaderAlias("fixmedins_code", "建档立卡人员标志");
        writer.addHeaderAlias("fixmedins_code", "民政待遇类别");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.4.2门诊按病种收付费待遇报表查询
     *
     * @return
     */
    @RequestMapping("/setlDByDiseNoTrt")
    public ResultInfo setlDByDiseNoTrt(SetlD setlD) {
        return service.setlDByDiseNoTrt(setlD);
    }
    @RequestMapping("/setlDByDiseNoTrtExcel")
    public void setlDByDiseNoTrtExcel(HttpServletResponse response, SetlD setlD) throws Exception {
        Page<SetlD> page = mapper.setlDByDiseNoTrt(new Page(1, 999), setlD);
        List<SetlD> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "备案编号");
        writer.addHeaderAlias("fixmedins_code", "身份证号");
        writer.addHeaderAlias("fixmedins_code", "审批标志");
        writer.addHeaderAlias("fixmedins_code", "国家病种编码");
        writer.addHeaderAlias("fixmedins_code", "国家病种名称");
        writer.addHeaderAlias("fixmedins_code", "本地政策参数");
        writer.addHeaderAlias("fixmedins_code", "项目名称");
        writer.addHeaderAlias("fixmedins_code", "限额");
        writer.addHeaderAlias("fixmedins_code", "协议医院编号");
        writer.addHeaderAlias("fixmedins_code", "协议医院名称");
        writer.addHeaderAlias("fixmedins_code", "协议医院等级");
        writer.addHeaderAlias("fixmedins_code", "参保人身份证号");
        writer.addHeaderAlias("fixmedins_code", "参保人姓名");
        writer.addHeaderAlias("fixmedins_code", "险种");
        writer.addHeaderAlias("fixmedins_code", "统筹区");
        writer.addHeaderAlias("fixmedins_code", "协议日期");
        writer.addHeaderAlias("fixmedins_code", "开始日期");
        writer.addHeaderAlias("fixmedins_code", "终止日期");
        writer.addHeaderAlias("fixmedins_code", "申报来源");
        writer.addHeaderAlias("fixmedins_code", "申请日期");
        writer.addHeaderAlias("fixmedins_code", "有效标识");
        writer.addHeaderAlias("fixmedins_code", "备注");
        writer.addHeaderAlias("fixmedins_code", "备案类别");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.4.3按病种付费病人结算明细信息查询
     *
     * @return
     */
    @RequestMapping("/setlDByDiseNo")
    public ResultInfo setlDByDiseNo(SetlD setlD) {
        return service.setlDByDiseNo(setlD);
    }
    @RequestMapping("/setlDByDiseNoExcel")
    public void setlDByDiseNoExcel(HttpServletResponse response, SetlD setlD) throws Exception {
        Page<SetlD> page = mapper.setlDByDiseNoTrt(new Page(1, 999), setlD);
        List<SetlD> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点机构编码");
        writer.addHeaderAlias("fixmedins_name", "定点机构名称");
        writer.addHeaderAlias("dise_no", "病种编号");
        writer.addHeaderAlias("dise_name", "病种名称");
        writer.addHeaderAlias("certno", "身份证号");
        writer.addHeaderAlias("psn_name", "姓名");
        writer.addHeaderAlias("emp_no", "单位编码");
        writer.addHeaderAlias("medfee_sumamt", "医疗总费用");
        writer.addHeaderAlias("hifp_pay", "统筹基金");
        writer.addHeaderAlias("hifmi_pay", "大病基金");
        writer.addHeaderAlias("cvlserv_pay", "公务员基金");
        writer.addHeaderAlias("acct_pay", "个人账户");
        writer.addHeaderAlias("cash_payamt", "个人现金");
        writer.addHeaderAlias("ownpay_hosp_part", "医院垫付");
        writer.addHeaderAlias("hifdm_pay", "职工居民标识");
        writer.addHeaderAlias("age_flag", "是否70岁以上");
        writer.addHeaderAlias("setl_time", "结算日期");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.4.4按病种付费统计查询
     *
     * @return
     */
    @RequestMapping("/setlDByDiseNoCount")
    public ResultInfo setlDByDiseNoCount(SetlD setlD) {
        return service.setlDByDiseNoCount(setlD);
    }

    /**
     * 2.5.6转诊转院备案情况查询
     *
     * @return
     */
    @RequestMapping("/reflAppyEvtC")
    public ResultInfo reflAppyEvtC(ReflAppyEvtCVo reflAppyEvtCVo) {
        return service.reflAppyEvtC(reflAppyEvtCVo);
    }

    /**
     * 2.5.13个人变更信息查询
     *
     * @return
     */
    @RequestMapping("/personal")
    public ResultInfo personal(PersonalChanges personalChanges) {
        return service.personal(personalChanges);
    }

    @RequestMapping("/personalExport")
    public void personalExport(HttpServletResponse response, PersonalChanges personalChanges) throws Exception {
        Page<PersonalChanges> page = mapper.personal(new Page(1, 999), personalChanges);
        List<PersonalChanges> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "姓名");
        writer.addHeaderAlias("todo", "身份证号");
        writer.addHeaderAlias("todo", "单位名称");
        writer.addHeaderAlias("todo", "险种类型");
        writer.addHeaderAlias("todo", "变更类型");
        writer.addHeaderAlias("todo", "变更原因");
        writer.addHeaderAlias("todo", "变更日期");
        writer.addHeaderAlias("todo", "经办人");
        writer.addHeaderAlias("todo", "经办时间");
        writer.addHeaderAlias("todo", "备注");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.5.12参保业务经办情况查询
     *
     * @return
     */
    @RequestMapping("/organization")
    public ResultInfo organization(OrganizationGinseng organizationGinseng) {
        return service.organization(organizationGinseng);
    }

    @RequestMapping("/organizationExport")
    public void organizationExport(HttpServletResponse response, OrganizationGinseng organizationGinseng) throws Exception {
        Page<OrganizationGinseng> page = mapper.organization(new Page(1, 999), organizationGinseng);
        List<OrganizationGinseng> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "业务名称");
        writer.addHeaderAlias("todo", "单位名称");
        writer.addHeaderAlias("todo", "人数");
        writer.addHeaderAlias("todo", "经办人");
        writer.addHeaderAlias("todo", "经办时间");
        writer.addHeaderAlias("todo", "复核人");
        writer.addHeaderAlias("todo", "复核时间");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.6.1定点机构综合查询
     *
     * @return
     */
    @RequestMapping("/fixmedinsGather")
    public ResultInfo fixmedinsGather(FixmedinsGather fixmedinsGather) {
        return service.fixmedinsGather(fixmedinsGather);
    }

    @RequestMapping("/fixmedinsGatherExport")
    public void fixmedinsGatherExport(HttpServletResponse response, FixmedinsGather fixmedinsGather) throws Exception {
        Page<FixmedinsGather> page = mapper.fixmedinsGather(new Page(1, 999), fixmedinsGather);
        List<FixmedinsGather> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "医药机构编号");
        writer.addHeaderAlias("fixmedins_code", "医药机构名称");
        writer.addHeaderAlias("fixmedins_code", "所属行政区划");
        writer.addHeaderAlias("fixmedins_code", "附加属性类别");
        writer.addHeaderAlias("fixmedins_code", "附加属性代码值");
        writer.addHeaderAlias("fixmedins_code", "区划类别");
        writer.addHeaderAlias("fixmedins_code", "开始时间");
        writer.addHeaderAlias("fixmedins_code", "结束时间");
        writer.addHeaderAlias("fixmedins_code", "操作");
        writer.addHeaderAlias("fixmedins_code", "协议信息");
        writer.addHeaderAlias("fixmedins_code", "服务信息");
        writer.addHeaderAlias("fixmedins_code", "科室信息");
        writer.addHeaderAlias("fixmedins_code", "医师信息");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.6.3定点医药机构协议信息
     *
     * @return
     */
    @RequestMapping("/fixmedinsCntrRegD")
    public ResultInfo fixmedinsCntrRegD(FixmedinsCntrRegD fixmedinsCntrRegD) {
        return service.fixmedinsCntrRegD(fixmedinsCntrRegD);
    }
    @RequestMapping("/fixmedinsCntrRegDExcel")
    public void fixmedinsCntrRegDExcel(HttpServletResponse response, FixmedinsCntrRegD fixmedinsCntrRegD) throws Exception {
        Page<FixmedinsCntrRegD> page = mapper.fixmedinsCntrRegD(new Page(1, 999), fixmedinsCntrRegD);
        List<FixmedinsCntrRegD> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点医药机构代码");
        writer.addHeaderAlias("fix_blng_admdvs", "定点归属医保区划");
        writer.addHeaderAlias("fix_cntr_type", "协议类型");
        writer.addHeaderAlias("fix_cntr_name", "协议名称");
        writer.addHeaderAlias("cntr_sign_year", "签订协议年度");
        writer.addHeaderAlias("begntime", "开始时间");
        writer.addHeaderAlias("endtime", "结束时间");
        writer.addHeaderAlias("vali_flag", "有效标志");
        writer.addHeaderAlias("memo", "备注");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.6.4定点医药机服务协议信息
     *
     * @return
     */
    @RequestMapping("/fixmedinsCntrRegDByMedinsInfoB")
    public ResultInfo fixmedinsCntrRegDByMedinsInfoB(FixmedinsCntrRegD fixmedinsCntrRegD) {
        return service.fixmedinsCntrRegDByMedinsInfoB(fixmedinsCntrRegD);
    }

    @RequestMapping("/fixmedinsCntrRegDByMedinsInfoBExport")
    public void fixmedinsCntrRegDByMedinsInfoBExport(HttpServletResponse response, FixmedinsCntrRegD fixmedinsCntrRegD) throws Exception {
        Page<FixmedinsCntrRegD> page = mapper.fixmedinsCntrRegDByMedinsInfoB(new Page(1, 999), fixmedinsCntrRegD);
        List<FixmedinsCntrRegD> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点医药机构代码");
        writer.addHeaderAlias("fix_blng_admdvs", "定点归属医保区划");
        writer.addHeaderAlias("fix_cntr_type", "协议类型");
        writer.addHeaderAlias("fix_cntr_name", "协议名称");
        writer.addHeaderAlias("cntr_sign_year", "签订协议年度");
        writer.addHeaderAlias("begntime", "开始时间");
        writer.addHeaderAlias("endtime", "结束时间");
        writer.addHeaderAlias("vali_flag", "有效标志");
        writer.addHeaderAlias("memo", "备注");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.6.7医药机构目录对照情况查询
     *
     * @return
     */
    @RequestMapping("/medicalContrast")
    public ResultInfo medicalContrast(MedinsInfoB medinsInfoB) {
        return service.medicalContrast(medinsInfoB);
    }
    @RequestMapping("/medicalContrastExcel")
    public void medicalContrastExcel(HttpServletResponse response, MedinsInfoB medinsInfoB) throws Exception {
        Page<MedinsInfoB> page = mapper.medicalContrast(new Page(1, 999), medinsInfoB);
        List<MedinsInfoB> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("medins_code", "医药机构编号");
        writer.addHeaderAlias("medins_name", "医疗机构名称");
        writer.addHeaderAlias("fixmedins_code", "医疗目录编号");
        writer.addHeaderAlias("fixmedins_code", "医疗目录名称");
        writer.addHeaderAlias("fixmedins_code", "医保目录编号");
        writer.addHeaderAlias("fixmedins_code", "医保目录名称");
        writer.addHeaderAlias("fixmedins_code", "收费项目等级");
        writer.addHeaderAlias("fixmedins_code", "收费项目类别");
        writer.addHeaderAlias("fixmedins_code", "医院价格");
        writer.addHeaderAlias("fixmedins_code", "开始时间");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.7.1学校缴费信息统计查询
     *
     * @return
     */
    @RequestMapping("/schoolCount")
    public ResultInfo schoolCount(School school) {
        return service.schoolCount(school);
    }
    @RequestMapping("/schoolCountExport")
    public void schoolCountExport(HttpServletResponse response, School school) throws Exception {
        Page<School> page = mapper.schoolCount(new Page(1, 999), school);
        List<School> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "学校编号");
        writer.addHeaderAlias("todo", "学校名称");
        writer.addHeaderAlias("todo", "缴费年度");
        writer.addHeaderAlias("todo", "缴费人数合计");
        writer.addHeaderAlias("todo", "基本医疗保险费");
        writer.addHeaderAlias("todo", "照护险金额");
        writer.addHeaderAlias("todo", "基本医疗财政补助");
        writer.addHeaderAlias("todo", "照护险财政补助");
        writer.addHeaderAlias("todo", "医保统筹");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.7.2学生缴费信息明细查询
     *
     * @return
     */
    @RequestMapping("/schoolDetail")
    public ResultInfo schoolDetail(School school) {
        return service.schoolDetail(school);
    }
    @RequestMapping("/schoolDetailExport")
    public void schoolDetailExport(HttpServletResponse response, School school) throws Exception {
        Page<School> page = mapper.schoolDetail(new Page(1, 999), school);
        List<School> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "学校编码");
        writer.addHeaderAlias("todo", "学校名称");
        writer.addHeaderAlias("todo", "班级编号");
        writer.addHeaderAlias("todo", "班级名称");
        writer.addHeaderAlias("todo", "个人编号");
        writer.addHeaderAlias("todo", "社会保障号码");
        writer.addHeaderAlias("todo", "姓名");
        writer.addHeaderAlias("todo", "性别");
        writer.addHeaderAlias("todo", "参保状态");
        writer.addHeaderAlias("todo", "人员类别");
        writer.addHeaderAlias("todo", "个人缴费金额");
        writer.addHeaderAlias("todo", "基本医疗保险费");
        writer.addHeaderAlias("todo", "照护险金额");
        writer.addHeaderAlias("todo", "基本医疗财政补助");
        writer.addHeaderAlias("todo", "照护险财政补助");
        writer.addHeaderAlias("todo", "医保统筹");
        writer.addHeaderAlias("todo", "缴费年度");
        writer.addHeaderAlias("todo", "缴费日期");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }

    /**
     * 2.7.5医疗救助明细查询
     *
     * @return
     */
    @RequestMapping("/medicalAssistance")
    public ResultInfo medicalAssistance(School school) {
        return service.schoolDetail(school);
    }
    @RequestMapping("/medicalAssistanceExport")
    public void medicalAssistanceExport(HttpServletResponse response, School school) throws Exception {
        Page<School> page = mapper.schoolDetail(new Page(1, 999), school);
        List<School> list = page.getRecords();
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("todo", "证件号码");
        writer.addHeaderAlias("todo", "姓名");
        writer.addHeaderAlias("todo", "定点编码");
        writer.addHeaderAlias("todo", "定点名称");
        writer.addHeaderAlias("todo", "医院等级");
        writer.addHeaderAlias("todo", "医疗待遇类别");
        writer.addHeaderAlias("todo", "医疗类别");
        writer.addHeaderAlias("todo", "医疗费总额");
        writer.addHeaderAlias("todo", "账户支付");
        writer.addHeaderAlias("todo", "统筹支付");
        writer.addHeaderAlias("todo", "大额支付");
        writer.addHeaderAlias("todo", "公补支付");
        writer.addHeaderAlias("todo", "参公支付");
        writer.addHeaderAlias("todo", "符合基本医疗费");
        writer.addHeaderAlias("todo", "医院垫付");
        writer.addHeaderAlias("todo", "二次补助支付");
        writer.addHeaderAlias("todo", "现金支付");
        writer.addHeaderAlias("todo", "民政救助支付");
        writer.addHeaderAlias("todo", "入院日期");
        writer.addHeaderAlias("todo", "出院日期");
        writer.addHeaderAlias("todo", "住院天数");
        writer.addHeaderAlias("todo", "经办时间");
        writer.addHeaderAlias("todo", "所属区");
        writer.addHeaderAlias("todo", "统筹区编码");
        writer.addHeaderAlias("todo", "民政待遇类别");
        writer.addHeaderAlias("todo", "其他基金支付");
        writer.addHeaderAlias("todo", "总报销金额");
        writer.addHeaderAlias("todo", "自费金额");
        writer.addHeaderAlias("todo", "自理费用");
        writer.addHeaderAlias("todo", "范围内费用");
        writer.addHeaderAlias("todo", "起付标准自付");
        writer.addHeaderAlias("todo", "进入统筹费用");
        writer.addHeaderAlias("todo", "统筹分段自付");
        writer.addHeaderAlias("todo", "报销标志");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        export(response, writer);
    }


    private void export(HttpServletResponse response, ExcelWriter writer) throws IOException {
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
