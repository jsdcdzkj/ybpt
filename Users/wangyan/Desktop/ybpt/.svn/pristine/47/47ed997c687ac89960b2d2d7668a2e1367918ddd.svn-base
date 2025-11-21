package com.jsdc.ybpt.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.jsdc.ybpt.model.DataDistribution;
import com.jsdc.ybpt.service.DataDistributionService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集采地区带量执行数据下发
 */
@RestController
@RequestMapping("/dataDistribution")
public class DataDistributionController {
    @Autowired
    private DataDistributionService mainService;

    /**
    * 分页接口
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(DataDistribution bean){
        return ResultInfo.success(mainService.selectPage(bean));
    }

    /**
     * 列表接口
     */
    @PostMapping("/selectListAll")
    public ResultInfo selectListAll(DataDistribution bean){
        return ResultInfo.success(mainService.selectList(bean));
    }

    /**
     * 导入接口
     */
    @RequestMapping("/importData")
    public ResultInfo importData(MultipartFile file) {
        return mainService.importData(file);
    }

    /**
     * 删除接口
     */
    @PostMapping("/delData")
    public ResultInfo delData(String id) {
        return ResultInfo.success(mainService.delData(id));
    }

    /**
     * 模版下载接口
     */
    @RequestMapping("templateDownload")
    public void templateDownload(HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        TemplateExportParams params = new TemplateExportParams();
        params = new TemplateExportParams("templates/国采地区带量执行报表.xlsx");
        Workbook workbook = ExcelExportUtil.exportExcel(params, data);

        // 设置响应头，告诉浏览器这是个附件，需要下载
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=国采地区带量执行报表.xlsx");

        // 获取响应的输出流
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 将工作簿写入输出流
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 关闭工作簿和输出流
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出接口
     */
    @RequestMapping("export")
    public void export(HttpServletResponse response, DataDistribution bean) throws IOException {
        List<DataDistribution> list = mainService.selectList(bean);

        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("name", "医疗机构");
        writer.addHeaderAlias("fixedPointNumber", "医疗机构编码");
        writer.addHeaderAlias("addr", "地区");
        writer.addHeaderAlias("batch_name", "批次名称");
        writer.addHeaderAlias("batch_start", "批次开始时间");
        writer.addHeaderAlias("batch_end", "批次结束时间");
        writer.addHeaderAlias("progress", "序时进度");
        writer.addHeaderAlias("contract_total", "签约产品总数");
        writer.addHeaderAlias("purchase_num", "零采购产品数");
        writer.addHeaderAlias("purchase_percentage", "零采购产品数占比");
        writer.addHeaderAlias("index_num", "低于序时进度产品数");
        writer.addHeaderAlias("index_percentage", "低于序时进度产品数占比");
        writer.addHeaderAlias("excess_num", "超量采购产品数");
        writer.addHeaderAlias("excess_percentage", "超量采购产品数占比");
        writer.addHeaderAlias("normal_num", "正常采购产品数");
        writer.addHeaderAlias("normal_percentage", "正常采购产品数占比");
        writer.addHeaderAlias("unselected_total", "对应非中选产品总数");
        writer.addHeaderAlias("unselected_num", "采购非中选产品数");
        writer.addHeaderAlias("selected_num", "非中选超量产品数");
        writer.addHeaderAlias("selectedExcess_num", "非中选超额产品数");
        writer.addHeaderAlias("fungible_total", "对应可替代产品总数");
        writer.addHeaderAlias("fungible_numbr", "采购可替代产品数");
        writer.addHeaderAlias("unselected_excess_number", "低于序时进度且非中选超量的产品数");
        writer.addHeaderAlias("upload_no", "批次号");

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


    /**
     * 获取批次接口
     */
    @RequestMapping("/getUploadNo")
    public ResultInfo getUploadNo(){
        List<String> list = mainService.getUploadNo();
        return ResultInfo.success(list);
    }

    /**
     * 获取医疗列表接口
     */
    @RequestMapping("/getMedicalCategory")
    public ResultInfo getMedicalCategory(){
        List<String> list = mainService.getMedicalCategory();
        return ResultInfo.success(list);
    }

}
