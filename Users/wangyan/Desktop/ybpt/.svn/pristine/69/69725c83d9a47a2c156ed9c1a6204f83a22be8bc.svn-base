package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.HilistlmtpricD;
import com.jsdc.ybpt.model_query.reimbursement.BirthSettlement;
import com.jsdc.ybpt.model_query.reimbursement.BirthSettlement_org;
import com.jsdc.ybpt.service.ReimbursementService;
import com.jsdc.ybpt.vo.ReimbursementQuery;
import com.jsdc.ybpt.vo.ReimbursementQuery_org;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/reimbursement")
public class ReimbursementController {
    @Autowired
    private ReimbursementService reimbursementService;

    @RequestMapping("/selectBirthSettlement")
    public ResultInfo selectBirthSettlement(@RequestBody ReimbursementQuery reimbursementQuery){
        Page page = reimbursementService.selectBirthSettlement(reimbursementQuery);
        return ResultInfo.success(page);
    }

    @RequestMapping("/selectBirthSettlement_org")
    public ResultInfo selectBirthSettlement_org(@RequestBody ReimbursementQuery_org reimbursementQuery_org){
        Page page = reimbursementService.selectBirthSettlement_org_page(reimbursementQuery_org);
        return ResultInfo.success(page);
    }

    @RequestMapping("/exportBirthsettlement")
    public void exportBirthsettlement(HttpServletResponse response,@RequestBody ReimbursementQuery reimbursementQuery) throws Exception{
        List<BirthSettlement> details = reimbursementService.birthSettlement_excel(reimbursementQuery);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("psn_name", "姓名");
        writer.addHeaderAlias("certno", "身份证");
        writer.addHeaderAlias("insu_admdvs", "参保人统筹区");
        writer.addHeaderAlias("psn_type", "人员类型");
        writer.addHeaderAlias("med_type", "医疗类别");
        writer.addHeaderAlias("setl_time", "结算日期");
        writer.addHeaderAlias("fixmedins_code", "医院编码");
        writer.addHeaderAlias("fixmedins_name", "医院名称");
        writer.addHeaderAlias("hosp_lv", "医院级别");
        writer.addHeaderAlias("medfee_sumamt", "总费用");
        writer.addHeaderAlias("reimbursement", "报销金额");
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

    @RequestMapping("/exportBirthsettlement_org")
    public void exportBirthsettlement_org(HttpServletResponse response,@RequestBody ReimbursementQuery_org reimbursementQuery_org) throws Exception{
        List<BirthSettlement_org> details = reimbursementService.selectBirthSettlement_org_excel(reimbursementQuery_org);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "医院编码");
        writer.addHeaderAlias("fixmedins_name", "医院名称");
        writer.addHeaderAlias("matn_type", "生育类别");
        writer.addHeaderAlias("hosp_lv", "医院等级");
        writer.addHeaderAlias("insutype", "险种类别");
        writer.addHeaderAlias("count", "人次");
        writer.addHeaderAlias("num", "人数");
        writer.addHeaderAlias("medfee_sumamt", "医疗费总额");
        writer.addHeaderAlias("hifp_pay", "统筹基金支出");
        writer.addHeaderAlias("hifob_pay", "大额医疗补助基金支出");
        writer.addHeaderAlias("cvlserv_pay", "公务员医疗补助资金支出");
        writer.addHeaderAlias("acct_pay", "个人账户支出");
        writer.addHeaderAlias("cash_payamt", "现金支付金额");
        writer.addHeaderAlias("ownpay_hosp_part", "自费中医院负担部分");
        writer.addHeaderAlias("maf_pay", "医疗救助");
        writer.addHeaderAlias("insu_admdvs", "统筹区");

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
}
