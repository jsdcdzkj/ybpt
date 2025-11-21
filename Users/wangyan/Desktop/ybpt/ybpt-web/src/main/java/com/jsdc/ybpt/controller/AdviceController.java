package com.jsdc.ybpt.controller;



import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.price.advice.Advice;
import com.jsdc.ybpt.service.AdviceService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * (Advice)表控制层
 *
 * @author wangYan
 * @since 2023-06-25
 */
@RestController
@RequestMapping("/advice")
public class AdviceController{
    /**
     * 服务对象
     */
    @Resource
    private AdviceService adviceService;

    /**
     * 分页查询所有数据
     *
     * @param advice 查询实体
     * @return 所有数据
     */
    @RequestMapping("/getPage")
    public ResultInfo getPage(@RequestBody Advice advice) {
        return ResultInfo.success(this.adviceService.getAdvice(advice));
    }

    @RequestMapping("/sbApplyExport")
    public void adviceExport(HttpServletResponse response, @RequestBody Advice advice) throws IOException {
        Page<Advice> page = adviceService.getAdvice(advice);
        List<Advice> list = page.getRecords();

        // 一次性写出内容，使用默认样式，强制输出标题
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("org_name", "单位名称");
        writer.addHeaderAlias("org_code", "单位医保编码");
        writer.addHeaderAlias("fix_blng_admdvs_name", "统筹区");
        writer.addHeaderAlias("aggrement_lv", "协议等级");
        writer.addHeaderAlias("natures", "经营性质");
        writer.addHeaderAlias("user_type", "定点类型");
        writer.addHeaderAlias("type", "申报类型");
        writer.addHeaderAlias("createTime", "创建时间");

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
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @RequestMapping("{id}")
    public ResultInfo selectOne(@PathVariable Serializable id) {
        return ResultInfo.success(this.adviceService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param advice 实体对象
     * @return 新增结果
     */
    @RequestMapping("/insert")
    public ResultInfo insert(@RequestBody Advice advice) {
        return this.adviceService.saveAdvice(advice);
    }

    /**
     * 修改数据
     *
     * @param advice 实体对象
     * @return 修改结果
     */
    @RequestMapping("/update")
    public ResultInfo update(@RequestBody Advice advice) {
        return ResultInfo.success(this.adviceService.updateById(advice));
    }

    /**
     * 删除数据
     *
     * @param advice 实体对象
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public ResultInfo delete(@RequestBody Advice advice) {
        advice.setIs_del("1");
        return ResultInfo.success(this.adviceService.updateById(advice));
    }
}

