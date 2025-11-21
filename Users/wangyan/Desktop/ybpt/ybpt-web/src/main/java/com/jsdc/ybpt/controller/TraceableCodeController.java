package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.assessment.KHManage;
import com.jsdc.ybpt.infoAssessment.InfoAssessment;
import com.jsdc.ybpt.service.InfoAssessmentService;
import com.jsdc.ybpt.service.TraceableCodeService;
import com.jsdc.ybpt.traceableCode.TraceableCode;
import com.jsdc.ybpt.vo.AssessmentVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/traceableCode")
public class TraceableCodeController {
    @Autowired
    private TraceableCodeService traceableCodeService;


    /**
     *
     *修改追溯码
     * @author wzn
     * @date 2024/10/28 10:50
     */
    @PostMapping("/updateInfoAssessment")
    public ResultInfo updateInfoAssessment(@RequestBody TraceableCode traceableCode) {
        traceableCodeService.updateTraceableCode(traceableCode);
        return ResultInfo.success();
    }


    /**
     *
     *删除追溯码重复
     * @author wzn
     * @date 2024/10/28 11:27
     */
    @RequestMapping("/delTraceableCode")
    public ResultInfo delTraceableCode(String id) {
        traceableCodeService.delTraceableCode(id); ;
        return ResultInfo.success();
    }

    /**
    *列表
    * Author wzn
    * Date 2022/7/4 13:53
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody TraceableCode traceableCode){
        Page<TraceableCode> traceableCodePage = traceableCodeService.selectList(traceableCode) ;
        return ResultInfo.success(traceableCodePage);
    }


    /**
     *
     *文件导入
     * @author wzn
     * @date 2024/10/28 14:22
     */
    @RequestMapping("importData")
    public ResultInfo uploadFile(MultipartFile file) {
        traceableCodeService.importData(file);
        return ResultInfo.success();
    }


    /**
     *
     *导出
     * @author wzn
     * @date 2024/11/13 17:11
     */
    @RequestMapping("traceableCodeExport")
    public void assessmentExport(HttpServletResponse response, TraceableCode vo) throws IOException {
        List<TraceableCode> list = traceableCodeService.selectAllList(vo);

        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("traceTheSerialNumber", "追溯流水号");
        writer.addHeaderAlias("fixmedins_code", "定点医药机构编号");
        writer.addHeaderAlias("fixmedins_name", "定点医药机构名称");
        writer.addHeaderAlias("med_list_codg", "医疗目录编码");
        writer.addHeaderAlias("medins_list_codg", "医药机构目录编码");
        writer.addHeaderAlias("medins_list_name", "医药机构目录名称");
        writer.addHeaderAlias("batchSerialNumber", "定点医药机构批次流水号");
        writer.addHeaderAlias("salesSerialNumber", "定点医药机构商品销售流水号");
        writer.addHeaderAlias("mdtrt_id", "就诊id");
        writer.addHeaderAlias("settlementType", "就诊结算类型");
        writer.addHeaderAlias("account_seria_number", "记账流水号");
        writer.addHeaderAlias("drugTracingCode", "药品追溯码");
        writer.addHeaderAlias("opter_id", "经办人id");
        writer.addHeaderAlias("opter_name", "经办人姓名");
        writer.addHeaderAlias("opt_time", "经办时间");
        writer.addHeaderAlias("optins_no", "经办机构编号");
        writer.addHeaderAlias("poolarea_no", "统筹区编号");
        writer.addHeaderAlias("dismantlingMark", "拆零标志");
        writer.addHeaderAlias("psn_no", "人员编号");
        writer.addHeaderAlias("psn_cert_type", "人员证件类型");
        writer.addHeaderAlias("certno", "证件号码");
        writer.addHeaderAlias("name", "人员姓名");

        //只导出定义字段
        writer.setOnlyAlias(true);
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


}
