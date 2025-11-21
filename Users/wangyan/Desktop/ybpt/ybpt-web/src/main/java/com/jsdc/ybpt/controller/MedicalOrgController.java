package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.medicalOrg.MedicalDept;
import com.jsdc.ybpt.model_query.reimbursement.BirthSettlement;
import com.jsdc.ybpt.service.MedicalOrgService;
import com.jsdc.ybpt.service.ReimbursementService;
import com.jsdc.ybpt.vo.ReimbursementQuery;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/medicalOrg")
public class MedicalOrgController {
    @Autowired
    private MedicalOrgService medicalOrgService;

    @RequestMapping("/selectMedicalDept_page")
    public ResultInfo selectMedicalDept_page(Integer pageNo,Integer pageSize,String fixmedins_code, String fixmedins_name, String dept_no, String dept_name){
        Page<MedicalDept> page = medicalOrgService.selectMedicalDept_page( pageNo, pageSize, fixmedins_code,  fixmedins_name,  dept_no,  dept_name);
        return ResultInfo.success(page);
    }
    @RequestMapping("/exportMedicalDept")
    public void selectMedicalDept_excel(HttpServletResponse response,String fixmedins_code, String fixmedins_name, String dept_no, String dept_name) throws Exception{
        List<MedicalDept> details = medicalOrgService.selectMedicalDept_excel(fixmedins_code,  fixmedins_name,  dept_no,  dept_name);
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点医疗机构名称");
        writer.addHeaderAlias("fixmedins_name", "定点医疗机构代码");
        writer.addHeaderAlias("dept_no", "科室编码");
        writer.addHeaderAlias("dept_name", "科室名称");
        writer.addHeaderAlias("begntime", "开始时间");
        writer.addHeaderAlias("endtime", "结束时间");
        writer.addHeaderAlias("dept_resper_name", "科室负责人姓名");
        writer.addHeaderAlias("dept_resper_tel", "科室负责人电话");
        writer.addHeaderAlias("aprv_bed_cnt", "批准床位数量");
        writer.addHeaderAlias("dr_psncnt", "医师人数");
        writer.addHeaderAlias("phar_psncnt", "药师人数");
        writer.addHeaderAlias("nurs_psncnt", "护士人数");
        writer.addHeaderAlias("tecn_psncnt", "技师人数");
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
