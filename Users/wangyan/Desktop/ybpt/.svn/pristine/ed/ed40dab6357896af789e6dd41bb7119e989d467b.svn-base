package com.jsdc.ybpt.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.util.ReconciliationUtils;
import com.jsdc.ybpt.vo.ReconciliationExcelVo;
import com.jsdc.ybpt.vo.ReconciliationType;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/reconciliation")
public class ReconciliationController {

    @Autowired
    private OrgReconciliationService orgReconciliationService;
    @Autowired
    private OrgReconciliationMonthService orgReconciliationMonthService;
    @Autowired
    private BizReconciliationService bizReconciliationService;
    @Autowired
    private BizReconciliationMonthService bizReconciliationMonthService;
    @Autowired
    private SysDictService sysDictService;

    @RequestMapping("/getReconciliationMonth")
    public ResultInfo getReconciliationMonth(BizReconciliationMonth bizReconciliationMonth) {
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<BizReconciliationMonth>()
                .like(StringUtils.hasText(bizReconciliationMonth.getFixmedins_name()), BizReconciliationMonth::getFixmedins_name, bizReconciliationMonth.getFixmedins_name())
                .like(StringUtils.hasText(bizReconciliationMonth.getFixmedins_code()), BizReconciliationMonth::getFixmedins_code, bizReconciliationMonth.getFixmedins_code())
                .eq(StringUtils.hasText(bizReconciliationMonth.getReconciliation_type()), BizReconciliationMonth::getReconciliation_type, bizReconciliationMonth.getReconciliation_type())
                .eq(StringUtils.hasText(bizReconciliationMonth.getSettle_date()), BizReconciliationMonth::getSettle_date, bizReconciliationMonth.getSettle_date())
                .eq(StringUtils.hasText(bizReconciliationMonth.getInsu_admdvs()), BizReconciliationMonth::getInsu_admdvs, bizReconciliationMonth.getInsu_admdvs())
                .eq(BizReconciliationMonth::getIs_del, "0")
                .orderByDesc(BizReconciliationMonth::getSettle_date,BizReconciliationMonth::getInsu_admdvs);
        Page<BizReconciliationMonth> page = bizReconciliationMonthService.page(new Page<>(bizReconciliationMonth.getPageNo(), bizReconciliationMonth.getPageSize()), wrapper);

        if (page.getRecords() != null && page.getRecords().size() > 0) {
            //查询统筹区
            HashMap<String, String> tcqMap = new HashMap();
            List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
            tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
            //查询对账类型
            HashMap<String, String> dzMap = new HashMap();
            List<SysDict> dz = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "reconciliationType").eq("is_del", "0"));
            dz.forEach(x -> dzMap.put(x.getValue(), x.getLabel()));
            //对账结果
            page.getRecords().forEach(x -> {
                x.setAdmdvsName(tcqMap.get(x.getInsu_admdvs()));
                x.setReconciliationName(dzMap.get(x.getReconciliation_type()));
            });
        }
        return ResultInfo.success(page);
    }

    /**
     * 月对账导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportReconciliationMont")
    public void exportReconciliationMont(BizReconciliationMonth bizReconciliationMonth, HttpServletResponse response) throws Exception {
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<BizReconciliationMonth>()
                .like(StringUtils.hasText(bizReconciliationMonth.getFixmedins_name()), BizReconciliationMonth::getFixmedins_name, bizReconciliationMonth.getFixmedins_name())
                .like(StringUtils.hasText(bizReconciliationMonth.getFixmedins_code()), BizReconciliationMonth::getFixmedins_code, bizReconciliationMonth.getFixmedins_code())
                .eq(StringUtils.hasText(bizReconciliationMonth.getReconciliation_type()), BizReconciliationMonth::getReconciliation_type, bizReconciliationMonth.getReconciliation_type())
                .eq(StringUtils.hasText(bizReconciliationMonth.getSettle_date()), BizReconciliationMonth::getSettle_date, bizReconciliationMonth.getSettle_date())
                .eq(StringUtils.hasText(bizReconciliationMonth.getInsu_admdvs()), BizReconciliationMonth::getInsu_admdvs, bizReconciliationMonth.getInsu_admdvs())
                .eq(BizReconciliationMonth::getIs_del, "0")
                .orderByDesc(BizReconciliationMonth::getSettle_date,BizReconciliationMonth::getInsu_admdvs);
        List<BizReconciliationMonth> list = bizReconciliationMonthService.list(wrapper);
        if (list != null && list.size() > 0) {
            //查询统筹区
            HashMap<String, String> tcqMap = new HashMap();
            List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
            tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
            //查询对账类型
            HashMap<String, String> dzMap = new HashMap();
            List<SysDict> dz = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "reconciliationType").eq("is_del", "0"));
            dz.forEach(x -> dzMap.put(x.getValue(), x.getLabel()));
            //对账结果
            list.forEach(x -> {
                x.setAdmdvsName(tcqMap.get(x.getInsu_admdvs()));
                x.setReconciliationName(dzMap.get(x.getReconciliation_type()));
            });
        }

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点机构编号");
        writer.addHeaderAlias("fixmedins_name", "定点机构名称");
        writer.addHeaderAlias("settle_date", "结算日期");
        writer.addHeaderAlias("reconciliationName", "对账类型");
        writer.addHeaderAlias("admdvsName", "统筹区");
        writer.addHeaderAlias("medfee_sumamt", "医疗费总额");
        writer.addHeaderAlias("hifp_pay", "统筹基金支出");
        writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助");
        writer.addHeaderAlias("hifes_pay", "补充医疗保险基金");
        writer.addHeaderAlias("hifmi_pay", "大病补充医疗保险基金");
        writer.addHeaderAlias("maf_pay", "医疗救助基金");
        writer.addHeaderAlias("othfund_pay", "倾斜救助基金");
        writer.addHeaderAlias("acct_pay", "个人账户支出");
        writer.addHeaderAlias("cash_payamt", "现金支付金额");
        writer.addHeaderAlias("ownpay_hosp_part", "自费医院负担部分");
        writer.addHeaderAlias("hifob_pay", "大额医疗补助基金");
        writer.addHeaderAlias("acct_mulaid_pay", "账户共济支付金额");
        writer.addHeaderAlias("person_num", "人数");
        writer.addHeaderAlias("person_count", "人次");
        writer.addHeaderAlias("check_time", "对账时间");
        //只导出定义字段
        writer.setOnlyAlias(true) ;
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
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
     * 日对账列表
     *
     * @param fixmedins_code
     * @param settle_date
     * @param reconciliation_type
     * @param insu_admdvs
     * @param check_status
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/getList")
    public ResultInfo getReconciliationData(String fixmedins_code, String settle_date, String reconciliation_type, String insu_admdvs, String check_status, Integer pageNo, Integer pageSize) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del", "0");
        if (StringUtils.hasText(fixmedins_code)) {
            qw.eq("fixmedins_code", fixmedins_code);
        }
        if (StringUtils.hasText(settle_date)) {
            qw.eq("settle_date", settle_date);
        }
        if (StringUtils.hasText(reconciliation_type)) {
            qw.eq("reconciliation_type", reconciliation_type);
        }
        if (StringUtils.hasText(insu_admdvs)) {
            qw.eq("insu_admdvs", insu_admdvs);
        }
        if (StringUtils.hasText(check_status)) {
            qw.eq("check_status", check_status);
        }
        qw.orderByDesc("settle_date", "insu_admdvs");
        Page<OrgReconciliation> pageinfo = orgReconciliationService.page(new Page<>(pageNo, pageSize), qw);
        if (pageinfo.getRecords() != null && pageinfo.getRecords().size() > 0) {
            //查询统筹区
            HashMap<String, String> tcqMap = new HashMap();
            List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
            tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
            //查询对账类型
            HashMap<String, String> dzMap = new HashMap();
            List<SysDict> dz = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "reconciliationType").eq("is_del", "0"));
            dz.forEach(x -> dzMap.put(x.getValue(), x.getLabel()));
            pageinfo.getRecords().forEach(x -> {
                x.setAdmdvsName(tcqMap.get(x.getInsu_admdvs()));
                x.setReconciliationName(dzMap.get(x.getReconciliation_type()));
            });
        }
        return ResultInfo.success(pageinfo);
    }

    /**
     * 日对账导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportDayList")
    public void exportDayList(String fixmedins_code, String settle_date, String reconciliation_type, String insu_admdvs, String check_status, HttpServletResponse response) throws Exception {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del", "0");
        if (StringUtils.hasText(fixmedins_code)) {
            qw.eq("fixmedins_code", fixmedins_code);
        }
        if (StringUtils.hasText(settle_date)) {
            qw.eq("settle_date", settle_date);
        }
        if (StringUtils.hasText(reconciliation_type)) {
            qw.eq("reconciliation_type", reconciliation_type);
        }
        if (StringUtils.hasText(insu_admdvs)) {
            qw.eq("insu_admdvs", insu_admdvs);
        }
        if (StringUtils.hasText(check_status)) {
            qw.eq("check_status", check_status);
        }
        qw.orderByDesc("settle_date", "insu_admdvs");
        List<OrgReconciliation> list = orgReconciliationService.list(qw);
        if (list != null && list.size() > 0) {
            //查询统筹区
            HashMap<String, String> tcqMap = new HashMap();
            List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
            tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
            //查询对账类型
            HashMap<String, String> dzMap = new HashMap();
            List<SysDict> dz = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "reconciliationType").eq("is_del", "0"));
            dz.forEach(x -> dzMap.put(x.getValue(), x.getLabel()));
            HashMap<String,String> statusMap = new HashMap<>();
            statusMap.put("0","未对账");
            statusMap.put("1","对账通过");
            statusMap.put("2","对账不通过");
            list.forEach(x -> {
                x.setAdmdvsName(tcqMap.get(x.getInsu_admdvs()));
                x.setReconciliationName(dzMap.get(x.getReconciliation_type()));
                x.setCheck_status(statusMap.get(x.getCheck_status()));
            });
        }

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点机构编号");
        writer.addHeaderAlias("fixmedins_name", "定点机构名称");
        writer.addHeaderAlias("settle_date", "结算日期");
        writer.addHeaderAlias("reconciliationName", "对账类型");
        writer.addHeaderAlias("admdvsName", "统筹区");
        writer.addHeaderAlias("medfee_sumamt", "医疗费总额");
        writer.addHeaderAlias("hifp_pay", "统筹基金支出");
        writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助");
        writer.addHeaderAlias("hifes_pay", "补充医疗保险基金");
        writer.addHeaderAlias("hifmi_pay", "大病补充医疗保险基金");
        writer.addHeaderAlias("hifob_pay", "大额医疗补助基金");
        writer.addHeaderAlias("maf_pay", "医疗救助基金");
        writer.addHeaderAlias("othfund_pay", "其他基金");
        writer.addHeaderAlias("acct_pay", "个人账户支出");
        writer.addHeaderAlias("cash_payamt", "现金支付金额");
        writer.addHeaderAlias("ownpay_hosp_part", "自费医院负担部分");
        writer.addHeaderAlias("person_num", "人数");
        writer.addHeaderAlias("person_count", "人次");
        writer.addHeaderAlias("check_time", "对账时间");
        writer.addHeaderAlias("check_status", "对账结果");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
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
     * 月对账列表
     *
     * @param fixmedins_code
     * @param settle_date
     * @param reconciliation_type
     * @param insu_admdvs
     * @param check_status
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/getList_month")
    public ResultInfo getReconciliationData_month(String fixmedins_code, String settle_date, String reconciliation_type, String insu_admdvs, String check_status, Integer pageNo, Integer pageSize) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del", "0");
        if (StringUtils.hasText(fixmedins_code)) {
            qw.eq("fixmedins_code", fixmedins_code);
        }
        if (StringUtils.hasText(settle_date)) {
            qw.eq("settle_date", settle_date);
        }
        if (StringUtils.hasText(reconciliation_type)) {
            qw.eq("reconciliation_type", reconciliation_type);
        }
        if (StringUtils.hasText(insu_admdvs)) {
            qw.eq("insu_admdvs", insu_admdvs);
        }
        if (StringUtils.hasText(check_status)) {
            qw.eq("check_status", check_status);
        }
        Page<OrgReconciliationMonth> pageinfo = orgReconciliationMonthService.page(new Page<>(pageNo, pageSize), qw);
        if (pageinfo.getRecords() != null && pageinfo.getRecords().size() > 0) {
            //查询统筹区
            HashMap<String, String> tcqMap = new HashMap();
            List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
            tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
            //查询对账类型
            HashMap<String, String> dzMap = new HashMap();
            List<SysDict> dz = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "reconciliationType").eq("is_del", "0"));
            dz.forEach(x -> dzMap.put(x.getValue(), x.getLabel()));
            //对账结果
            pageinfo.getRecords().forEach(x -> {
                x.setAdmdvsName(tcqMap.get(x.getInsu_admdvs()));
                x.setReconciliationName(dzMap.get(x.getReconciliation_type()));
            });
        }
        return ResultInfo.success(pageinfo);
    }
    /**
     * 月对账导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportMonthList")
    public void exportMonthList(String fixmedins_code, String settle_date, String reconciliation_type, String insu_admdvs, String check_status, HttpServletResponse response) throws Exception {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del", "0");
        if (StringUtils.hasText(fixmedins_code)) {
            qw.eq("fixmedins_code", fixmedins_code);
        }
        if (StringUtils.hasText(settle_date)) {
            qw.eq("settle_date", settle_date);
        }
        if (StringUtils.hasText(reconciliation_type)) {
            qw.eq("reconciliation_type", reconciliation_type);
        }
        if (StringUtils.hasText(insu_admdvs)) {
            qw.eq("insu_admdvs", insu_admdvs);
        }
        if (StringUtils.hasText(check_status)) {
            qw.eq("check_status", check_status);
        }
        qw.orderByDesc("settle_date", "insu_admdvs");
        List<OrgReconciliationMonth> list = orgReconciliationMonthService.list(qw);
        if (list != null && list.size() > 0) {
            //查询统筹区
            HashMap<String, String> tcqMap = new HashMap();
            List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
            tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
            //查询对账类型
            HashMap<String, String> dzMap = new HashMap();
            List<SysDict> dz = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "reconciliationType").eq("is_del", "0"));
            dz.forEach(x -> dzMap.put(x.getValue(), x.getLabel()));
            //对账结果
            HashMap<String,String> statusMap = new HashMap<>();
            statusMap.put("0","未对账");
            statusMap.put("1","对账通过");
            statusMap.put("2","对账不通过");
            list.forEach(x -> {
                x.setAdmdvsName(tcqMap.get(x.getInsu_admdvs()));
                x.setReconciliationName(dzMap.get(x.getReconciliation_type()));
                x.setCheck_status(statusMap.get(x.getCheck_status()));
            });
        }

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点机构编号");
        writer.addHeaderAlias("fixmedins_name", "定点机构名称");
        writer.addHeaderAlias("settle_date", "结算日期");
        writer.addHeaderAlias("reconciliationName", "对账类型");
        writer.addHeaderAlias("admdvsName", "统筹区");
        writer.addHeaderAlias("medfee_sumamt", "医疗费总额");
        writer.addHeaderAlias("hifp_pay", "统筹基金支出");
        writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助");
        writer.addHeaderAlias("hifes_pay", "补充医疗保险基金");
        writer.addHeaderAlias("hifmi_pay", "大病补充医疗保险基金");
        writer.addHeaderAlias("maf_pay", "医疗救助基金");
        writer.addHeaderAlias("othfund_pay", "倾斜救助基金");
        writer.addHeaderAlias("acct_pay", "个人账户支出");
        writer.addHeaderAlias("cash_payamt", "现金支付金额");
        writer.addHeaderAlias("ownpay_hosp_part", "自费医院负担部分");
        writer.addHeaderAlias("hifob_pay", "大额医疗补助基金");
        writer.addHeaderAlias("acct_mulaid_pay", "账户共济支付金额");
        writer.addHeaderAlias("person_num", "人数");
        writer.addHeaderAlias("person_count", "人次");
        writer.addHeaderAlias("check_time", "对账时间");
        writer.addHeaderAlias("check_status", "对账结果");
        //只导出定义字段
        writer.setOnlyAlias(true) ;
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
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
     * 日对账
     *
     * @param orgReconciliation
     * @return
     */
    @PostMapping("/checkReconciliation")
    public ResultInfo checkReconciliation(@RequestBody OrgReconciliation orgReconciliation) {
        return orgReconciliationService.checkReconciliation(orgReconciliation);
    }

    /**
     * 月对账
     *
     * @param orgReconciliation
     * @return
     */
    @PostMapping("/checkReconciliation_month")
    public ResultInfo checkReconciliation_month(@RequestBody OrgReconciliationMonth orgReconciliation) {
        return orgReconciliationMonthService.checkReconciliation(orgReconciliation);
    }


    /**
     * 重新生成日对账记录
     *
     * @param orgReconciliationId
     * @return
     */
    @PostMapping("/genNewReconciliation")
    public ResultInfo genReconciliationByOrg(String orgReconciliationId) {
        //查询需要对账的记录
        OrgReconciliation orgReconciliation = orgReconciliationService.getById(orgReconciliationId);
        //获取需要重新统计的记录
        BizReconciliation b = bizReconciliationService.getById(orgReconciliation.getBizId());
        List<BizReconciliation> reconciliations = null;
        //获取新纪录
        if("3".equals(b.getInsu_admdvs()) || "4".equals(b.getInsu_admdvs())){
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap_yd();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            reconciliations = bizReconciliationService.getReflowReconciliationData_yd(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }else {
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            reconciliations = bizReconciliationService.getReflowReconciliationData(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }

        if (reconciliations != null && reconciliations.size() > 0) {
            BizReconciliation nb = reconciliations.get(0);
            //判断新老数据是否相等
            if (
                    contrast(b.getMedfee_sumamt(), nb.getMedfee_sumamt()) &&
                    contrast(b.getHifp_pay(), nb.getHifp_pay()) &&
                    contrast(b.getCvlserv_pay(), nb.getCvlserv_pay()) &&
                    contrast(b.getHifes_pay(), nb.getHifes_pay()) &&
                    contrast(b.getHifmi_pay(), nb.getHifmi_pay()) &&
                    contrast(b.getMaf_pay(), nb.getMaf_pay()) &&
                    contrast(b.getOthfund_pay(), nb.getOthfund_pay()) &&
                    contrast(b.getAcct_pay(), nb.getAcct_pay()) &&
                    contrast(b.getCash_payamt(), nb.getCash_payamt()) &&
                    contrast(b.getHifdm_pay(), nb.getHifdm_pay()) &&
                    contrast(b.getAcct_mulaid_pay(), nb.getAcct_mulaid_pay()) &&
                    contrast(b.getPerson_num(), nb.getPerson_num()) &&
                    contrast(b.getPerson_count(), nb.getPerson_count()) &&
                    contrast(b.getOwnpay_hosp_part(), nb.getOwnpay_hosp_part())
            ) {
                return ResultInfo.error("数据无变化，若确认完成冲账，请稍后再次尝试！");
            } else {
                nb.setId(b.getId());
                nb.setFixmedins_name(b.getFixmedins_name());
                nb.setSettle_date(b.getSettle_date());
                nb.setReconciliation_type(b.getReconciliation_type());
                nb.setIs_del("0");
                nb.setCreateTime(DateUtil.formatDateTime(new Date()));
                nb.updateById();
                return ResultInfo.success();
            }
        } else {
            return ResultInfo.error("无数据需要对账");
        }
    }


    /**
     * 重新生成月对账记录
     *
     * @param orgReconciliationMonthId
     * @return
     */
    @PostMapping("/genNewReconciliation_month")
    public ResultInfo genReconciliationByOrgMonth(String orgReconciliationMonthId) {
        //查询需要对账的记录
        OrgReconciliationMonth orgReconciliation = orgReconciliationMonthService.getById(orgReconciliationMonthId);
        //获取需要重新统计的记录
        BizReconciliationMonth b = bizReconciliationMonthService.getById(orgReconciliation.getBizId());
        //缺失数据，补救方法
        List<BizReconciliationMonth> reconciliations = null;
        if(null == b){
            //异地
            if("3".equals(orgReconciliation.getInsu_admdvs()) || "4".equals(orgReconciliation.getInsu_admdvs())){
                HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap_yd();
                ReconciliationType rt = reMap.get(orgReconciliation.getReconciliation_type());
                reconciliations = bizReconciliationMonthService.getReflowReconciliationData_yd(orgReconciliation.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), orgReconciliation.getFixmedins_code(), orgReconciliation.getInsu_admdvs());
            }else {
                HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap();
                ReconciliationType rt = reMap.get(orgReconciliation.getReconciliation_type());
                reconciliations = bizReconciliationMonthService.getReflowReconciliationData(orgReconciliation.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), orgReconciliation.getFixmedins_code(), orgReconciliation.getInsu_admdvs());
            }
            BizReconciliationMonth nb = reconciliations.get(0);
            nb.setId(orgReconciliation.getBizId());
            nb.insert() ;
            return ResultInfo.success();
        }

        //获取新纪录

        if("3".equals(b.getInsu_admdvs()) || "4".equals(b.getInsu_admdvs())){
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap_yd();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            reconciliations = bizReconciliationMonthService.getReflowReconciliationData_yd(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }else {
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            reconciliations = bizReconciliationMonthService.getReflowReconciliationData(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }
        if (reconciliations != null && reconciliations.size() > 0) {
            BizReconciliationMonth nb = reconciliations.get(0);
            //判断新老数据是否相等
            if (
                    contrast(b.getMedfee_sumamt(), nb.getMedfee_sumamt()) &&
                    contrast(b.getHifp_pay(), nb.getHifp_pay()) &&
                    contrast(b.getCvlserv_pay(), nb.getCvlserv_pay()) &&
                    contrast(b.getHifes_pay(), nb.getHifes_pay()) &&
                    contrast(b.getHifmi_pay(), nb.getHifmi_pay()) &&
                    contrast(b.getMaf_pay(), nb.getMaf_pay()) &&
                    contrast(b.getOthfund_pay(), nb.getOthfund_pay()) &&
                    contrast(b.getAcct_pay(), nb.getAcct_pay()) &&
                    contrast(b.getCash_payamt(), nb.getCash_payamt()) &&
                    contrast(b.getHifob_pay(), nb.getHifob_pay()) &&
                    contrast(b.getAcct_mulaid_pay(), nb.getAcct_mulaid_pay()) &&
                    contrast(b.getPerson_num(), nb.getPerson_num()) &&
                    contrast(b.getPerson_count(), nb.getPerson_count()) &&
                    contrast(b.getOwnpay_hosp_part(), nb.getOwnpay_hosp_part())
            ) {
                return ResultInfo.error("数据无变化，若确认完成冲账，请稍后再次尝试！");
            } else {
                nb.setId(b.getId());
                nb.setFixmedins_name(b.getFixmedins_name());
                nb.setSettle_date(b.getSettle_date());
                nb.setReconciliation_type(b.getReconciliation_type());
                nb.setIs_del("0");
                nb.setCreateTime(DateUtil.formatDateTime(new Date()));
                nb.updateById();
                return ResultInfo.success();
            }
        } else {
            return ResultInfo.error("无数据需要对账");
        }
    }

    /**
     * 日详情导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/dayDetailsExcel")
    public void dayDetailsExcel(String orgReconciliationId, HttpServletResponse response) throws Exception {
        //查询需要对账的记录
         OrgReconciliation orgReconciliation = orgReconciliationService.getById(orgReconciliationId);
        //获取需要重新统计的记录
        BizReconciliation b = bizReconciliationService.getById(orgReconciliation.getBizId());
        List<ReconciliationExcelVo> details = null;
        //获取新纪录
        if("3".equals(b.getInsu_admdvs()) || "4".equals(b.getInsu_admdvs())){
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap_yd();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            details = bizReconciliationService.getDetailsExcel_yd(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }else{
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            details = bizReconciliationService.getDetailsExcel(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("SETL_DATE", "结算期");
        writer.addHeaderAlias("SETL_ID", "结算ID");
        writer.addHeaderAlias("MDTRT_ID", "就诊ID");
        writer.addHeaderAlias("INIT_SETL_ID", "原结算ID");
        writer.addHeaderAlias("MEDINS_SETL_ID", "医药机构结算ID");
        writer.addHeaderAlias("PSN_NO", "人员编号");
        writer.addHeaderAlias("PSN_TYPE", "人员类别");
        writer.addHeaderAlias("CERTNO", "证件号码");
        writer.addHeaderAlias("INSUTYPE", "险种类型");
        writer.addHeaderAlias("SETL_TIME", "结算时间");
        writer.addHeaderAlias("MED_TYPE", "医疗类别");
        writer.addHeaderAlias("INSU_ADMDVS", "参保所属医保区划");
        writer.addHeaderAlias("FIXMEDINS_CODE", "定点医药机构编号");
        writer.addHeaderAlias("FIXMEDINS_NAME", "定点医药机构名称");
        writer.addHeaderAlias("REFD_SETL_FLAG", "退费结算标志");
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
     * 日对账明细导出
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping("/dayDetailsExcel_month")
    public void dayDetailsExcelMonth(String orgReconciliationId, HttpServletResponse response) throws Exception {
        //查询需要对账的记录
        OrgReconciliationMonth orgReconciliation = orgReconciliationMonthService.getById(orgReconciliationId);
        //获取需要重新统计的记录
        BizReconciliationMonth b = bizReconciliationMonthService.getById(orgReconciliation.getBizId());
        List<ReconciliationExcelVo> details = null;
        //获取新纪录
        if("3".equals(b.getInsu_admdvs()) || "4".equals(b.getInsu_admdvs())){
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap_yd();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            details = bizReconciliationMonthService.getDetailsExcel_yd(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }else{
            HashMap<String, ReconciliationType> reMap = ReconciliationUtils.getReconciliationTypeMap();
            ReconciliationType rt = reMap.get(b.getReconciliation_type());
            details = bizReconciliationMonthService.getDetailsExcel(b.getSettle_date(), rt.getInsutype(), rt.getMedTypes(), rt.getPsnTypes(), b.getFixmedins_code(), b.getInsu_admdvs());
        }
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("SETL_DATE", "结算期");
        writer.addHeaderAlias("SETL_ID", "结算ID");
        writer.addHeaderAlias("MDTRT_ID", "就诊ID");
        writer.addHeaderAlias("INIT_SETL_ID", "原结算ID");
        writer.addHeaderAlias("MEDINS_SETL_ID", "医药机构结算ID");
        writer.addHeaderAlias("PSN_NO", "人员编号");
        writer.addHeaderAlias("PSN_TYPE", "人员类别");
        writer.addHeaderAlias("CERTNO", "证件号码");
        writer.addHeaderAlias("INSUTYPE", "险种类型");
        writer.addHeaderAlias("SETL_TIME", "结算时间");
        writer.addHeaderAlias("MED_TYPE", "医疗类别");
        writer.addHeaderAlias("INSU_ADMDVS", "参保所属医保区划");
        writer.addHeaderAlias("FIXMEDINS_CODE", "定点医药机构编号");
        writer.addHeaderAlias("FIXMEDINS_NAME", "定点医药机构名称");
        writer.addHeaderAlias("REFD_SETL_FLAG", "退费结算标志");
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
     * 对比两个数是否相等
     *
     * @param num1
     * @param num2
     * @return
     */
    private Boolean contrast(String num1, String num2) {
        num1 = num1 == null ? "0" : num1;
        num2 = num2 == null ? "0" : num2;
        return new BigDecimal(num1).compareTo(new BigDecimal(num2)) == 0;
    }
}
