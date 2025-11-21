package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.service.terminal.TerminalInfoService;
import com.jsdc.ybpt.terminal.TerminalInfo;
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

/**
 * 医疗机构终端信息
 */
@RestController
@RequestMapping("/terminalInfo")
public class TerminalInfoController {
    @Autowired
    private TerminalInfoService terminalInfoService;

    /**
     * 新增修改接口
     */
    @PostMapping("/addUpdateInfo")
    public ResultInfo addUpdateInfo(@RequestBody TerminalInfo terminalInfo) {
        terminalInfoService.addUpdateInfo(terminalInfo);
        return ResultInfo.success();
    }


    /**
    * 删除接口
    */
    @PostMapping("/delInfo")
    public ResultInfo delInfo(String id) {
        return ResultInfo.success(terminalInfoService.delInfo(id));
    }

    /**
    * 分页接口
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(TerminalInfo bean){
        Page<TerminalInfo> terminalInfoList = terminalInfoService.selectPage(bean);
        return ResultInfo.success(terminalInfoList);
    }

    /**
     * 列表接口
     */
    @PostMapping("/selectListAll")
    public ResultInfo selectListAll(TerminalInfo bean){
        List<TerminalInfo> terminalInfoList = terminalInfoService.selectList(bean);
        return ResultInfo.success(terminalInfoList);
    }

    /**
     * 导出
     */
    @RequestMapping("export")
    public void assessmentExport(HttpServletResponse response, TerminalInfo bean) throws IOException {
        List<TerminalInfo> list = terminalInfoService.selectList(bean);

        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("org_name", "机构名称");
        writer.addHeaderAlias("org_code", "机构编码");
        writer.addHeaderAlias("area", "统筹区");
        writer.addHeaderAlias("medical_code", "医保编码");
        writer.addHeaderAlias("address", "机构地址");
        writer.addHeaderAlias("contacts", "机构联系人");
        writer.addHeaderAlias("contact_number", "机构联系电话");
        writer.addHeaderAlias("cred_lv_name", "医疗等级");
        writer.addHeaderAlias("operator_name", "运营商");
        writer.addHeaderAlias("terminalUserNumber", "开通账号数量");
        writer.addHeaderAlias("terminalNetworkNumber", "访问医保网的终端数量");
        writer.addHeaderAlias("terminalNetworkOtherNumber", "同时访问医保网和其他网络的终端数量");
        writer.addHeaderAlias("dumb_number", "哑终端数量");
        writer.addHeaderAlias("antivirus", "使用的杀毒软件");
        writer.addHeaderAlias("status_name", "审核状态");

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
    * 详情接口
    */
    @PostMapping("/info")
    public ResultInfo info(String id){
        TerminalInfo terminalInfo = terminalInfoService.detail(id) ;
        return ResultInfo.success(terminalInfo);
    }

    /**
     * 审核接口
     * @param id 主键
     * @param status 状态
     * @param verify_reason 审核意见
     */
    @PostMapping("/audit")
    public ResultInfo audit(String id,Integer status,String verify_reason){
        TerminalInfo terminalInfo = terminalInfoService.audit(id,status,verify_reason);
        return ResultInfo.success(terminalInfo);
    }

    /**
     * 文件上传
     * @param file 文件
     * @param id 主键
     * @return
     */
    @PostMapping("/upload")
    public ResultInfo upload(MultipartFile file, String id){
        return terminalInfoService.upload(file,id);
    }

    /**
     * 删除文件
     * @param id 文件id
     * @return
     */
    @RequestMapping("/removeUpload")
    public ResultInfo removeUpload(String id) {
        return ResultInfo.success(terminalInfoService.removeUpload(id));
    }


}
