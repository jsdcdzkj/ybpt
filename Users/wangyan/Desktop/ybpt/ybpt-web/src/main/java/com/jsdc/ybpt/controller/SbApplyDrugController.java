package com.jsdc.ybpt.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model.UnfixedMechanism;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.price.declare.SbApplyDrug;
import com.jsdc.ybpt.price.declare.SbApplyDrugVo;
import com.jsdc.ybpt.price.declare.SbApplyVo;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.service.declare.SbApplyService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 申诉药品(SbApply)表控制层
 *
 * @author wangYan
 * @since 2023-02-01 16:58:15
 */
@RestController
@RequestMapping("sbApplyDrug")
public class SbApplyDrugController {
    @Resource
    private SbApplyDrugService sbApplyDrugService;
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页
     * @param sbApplyDrug
     * @return
     */
    @RequestMapping("/page")
    public ResultInfo page(@RequestBody SbApplyDrug sbApplyDrug) {
        return ResultInfo.success(sbApplyDrugService.getSbApplyDrug(sbApplyDrug));
    }

    /**
     * 保存
     * @param sbApplyDrug
     * @return
     */
    @RequestMapping("/insert")
    public ResultInfo insert(@RequestBody SbApplyDrug sbApplyDrug) {
        return sbApplyDrugService.insert(sbApplyDrug);
    }

    /**
     * 审核
     * @param sbApplyDrug
     * @return
     */
    @RequestMapping("/update")
    public ResultInfo update(@RequestBody SbApplyDrug sbApplyDrug) {
        if ("1".equals(sbApplyDrug.getStatus())) {
            sbApplyDrug.setFirst_trialer(sysUserService.getUser().getName());
            sbApplyDrug.setFirst_time(new Date());
            sbApplyDrugService.updateById(sbApplyDrug);
        } else if ("2".equals(sbApplyDrug.getStatus())) {
            sbApplyDrug.setSecond_trialer(sysUserService.getUser().getName());
            sbApplyDrug.setSecond_time(new Date());
            sbApplyDrugService.updateById(sbApplyDrug);
        } else if ("3".equals(sbApplyDrug.getStatus())) {
            sbApplyDrug.setEnd_trialer(sysUserService.getUser().getName());
            sbApplyDrug.setEnd_time(new Date());
            sbApplyDrugService.updateById(sbApplyDrug);
        } else if ("5".equals(sbApplyDrug.getStatus())) {
            sbApplyDrug.setRejecter(sysUserService.getUser().getName());
            sbApplyDrug.setReject_time(new Date());
            sbApplyDrugService.updateById(sbApplyDrug);
        }
        return ResultInfo.success();
    }


    /**
     * 明细下载
     * @param vo
     * @return
     */
    @RequestMapping("/down")
    public ResultInfo down(SbApplyDrugVo vo) {
        return sbApplyDrugService.down(vo);
    }

    /**
     * 生成受理书
     * @param id
     * @return
     */
    @RequestMapping("/bedViewPdf")
    public ResultInfo bedViewPdf(String id) {
        return sbApplyDrugService.bedViewPdf(id);
    }

    /**
     * 导出
     */
    @RequestMapping("/drugExport")
    public void drugExport(HttpServletResponse response, SbApplyDrug sbApplyDrug) throws IOException {
        Page<SbApplyDrug> page = sbApplyDrugService.getSbApplyDrug(sbApplyDrug);
        List<SbApplyDrug> list = page.getRecords();

        // 一次性写出内容，使用默认样式，强制输出标题
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("org_name", "单位名称");
        writer.addHeaderAlias("org_code", "单位医保编码");
        writer.addHeaderAlias("premium", "加价率");
        writer.addHeaderAlias("fix_blng_admdvs_sbApply_name", "统筹区");
        writer.addHeaderAlias("natures", "经营性质");
        writer.addHeaderAlias("type", "药品类别");
        writer.addHeaderAlias("statusName", "审核状态");
        writer.addHeaderAlias("reason", "驳回原因");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("first_time", "初审时间");
        writer.addHeaderAlias("first_trialer", "初审负责人");
        writer.addHeaderAlias("second_time", "复审时间");
        writer.addHeaderAlias("second_trialer", "复审负责人");
        writer.addHeaderAlias("end_time", "终审时间");
        writer.addHeaderAlias("end_trialer", "终审负责人");

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
     * 批量审核
     */
    @PostMapping("/batchAudit")
    public ResultInfo batchAudit(String ids) {
        List<String> split= Arrays.asList(ids.split(","));
        SbApplyDrug sbApplyDrug = null ;
        for (String x : split) {
            sbApplyDrug = sbApplyDrugService.getById(x);
            Integer i = Integer.parseInt(sbApplyDrug.getStatus()) + 1;
            sbApplyDrug.setStatus(String.valueOf(i));
            this.update(sbApplyDrug);
        }
        return ResultInfo.success();
    }

}

