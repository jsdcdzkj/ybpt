package com.jsdc.ybpt.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.ConsumablesSign;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.IsConfig;
import com.jsdc.ybpt.service.ConsumablesSignService;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.IsConfigService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (ConsumablesSign)表控制层
 *
 * @author wangYan
 * @since 2023-02-16 19:46:22
 */
@RestController
@RequestMapping("consumablesSign")
public class ConsumablesSignController {
    /**
     * 服务对象
     */
    @Resource
    private ConsumablesSignService consumablesSignService;
    @Resource
    private SysUserService sysUserService;
    @Autowired
    FixmedinsBService fixmedinsBService;
    @Autowired
    private IsConfigService isConfigService;

    /**
     * 分页查询所有数据
     *
     * @param consumablesSign 查询实体
     * @return 所有数据
     */
    @RequestMapping("selectAll")
    public ResultInfo selectAll(Integer pageNo, Integer pageSize, ConsumablesSign consumablesSign) {
        SysUser sysUser = sysUserService.getUser();
        LambdaQueryWrapper<ConsumablesSign> lambda = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(consumablesSign.getMcs_code_new())) {
            lambda.eq(ConsumablesSign::getMcs_code_new, consumablesSign.getMcs_code_new());
        }
        if (StringUtils.isNotEmpty(consumablesSign.getMcs_code())) {
            lambda.eq(ConsumablesSign::getMcs_code, consumablesSign.getMcs_code());
        }
        if (StringUtils.isNotEmpty(consumablesSign.getStatus())) {
            lambda.eq(ConsumablesSign::getStatus, consumablesSign.getStatus());
        }
        if("1".equals(sysUser.getUser_type()) && StringUtils.isNotEmpty(consumablesSign.getAdmdvs())){
            lambda.eq(ConsumablesSign::getAdmdvs, consumablesSign.getAdmdvs());
        }
        if("1".equals(sysUser.getUser_type()) && StringUtils.isNotEmpty(consumablesSign.getCreateUser())){
            lambda.eq(ConsumablesSign::getCreateUser, consumablesSign.getCreateUser());
        }
        if ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())) {
            lambda.eq(ConsumablesSign::getCreateUser, sysUser.getOrg_code());
        }
        lambda.orderByDesc(ConsumablesSign::getCreateTime);
        lambda.eq(ConsumablesSign::getIs_del, "0");
        return ResultInfo.success(this.consumablesSignService.page(new Page(pageNo, pageSize), lambda));
    }

    /**
     * 新增修改数据
     *
     * @param consumablesSign 实体对象
     * @return 新增结果
     */
    @RequestMapping("insertOrUpdate")
    public ResultInfo insertOrUpdate(@RequestBody ConsumablesSign consumablesSign) {
        if (StringUtils.isEmpty(consumablesSign.getId())) {
            SysUser sysUser = sysUserService.getUser();
            consumablesSign.setMcs_code_time(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
            consumablesSign.setCreateUser(sysUser.getOrg_code());
            consumablesSign.setCreateUserName(sysUser.getOrg_name());
            consumablesSign.setCreateTime(new Date());
            consumablesSign.setStatus("0");
            consumablesSign.setIs_del("0");
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery()
                    .eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code()).eq(FixmedinsB::getIs_del, "0")
            );
            consumablesSign.setAdmdvs(fixmedinsB.getFix_blng_admdvs());
        }
        return ResultInfo.success(this.consumablesSignService.saveOrUpdate(consumablesSign));
    }


    /**
     * 中心端申诉开关控制
     *
     * @return 单条数据
     */
    @RequestMapping("/isOpen")
    public ResultInfo isOpen() {
        return ResultInfo.success(isConfigService.getById(2));
    }

    /**
     * 中心端申诉开关控制 修改
     * @param bean
     * @return
     */
    @RequestMapping("/isOpenEdit")
    public ResultInfo isOpenEdit(IsConfig bean) {
        return ResultInfo.success(isConfigService.upRelocatedConfig(bean));
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(ConsumablesSign consumablesSign, HttpServletResponse response) throws Exception {
        SysUser sysUser = sysUserService.getUser();
        LambdaQueryWrapper<ConsumablesSign> lambda = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(consumablesSign.getMcs_code_new())) {
            lambda.eq(ConsumablesSign::getMcs_code_new, consumablesSign.getMcs_code_new());
        }
        if (StringUtils.isNotEmpty(consumablesSign.getMcs_code())) {
            lambda.eq(ConsumablesSign::getMcs_code, consumablesSign.getMcs_code());
        }
        if (StringUtils.isNotEmpty(consumablesSign.getStatus())) {
            lambda.eq(ConsumablesSign::getStatus, consumablesSign.getStatus());
        }
        if("1".equals(sysUser.getUser_type()) && StringUtils.isNotEmpty(consumablesSign.getAdmdvs())){
            lambda.eq(ConsumablesSign::getAdmdvs, consumablesSign.getAdmdvs());
        }
        if ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())) {
            lambda.eq(ConsumablesSign::getCreateUser, sysUser.getOrg_code());
        }
        lambda.orderByDesc(ConsumablesSign::getCreateTime);
        List<ConsumablesSign> list = consumablesSignService.list(lambda);

        if (null != list && list.size() > 0) {
            for (ConsumablesSign c : list) {
                if (!"".equals(c.getStatus())) {
                    if ("0".equals(c.getStatus())) {
                        c.setStatus("待审核");
                    } else if ("1".equals(c.getStatus())) {
                        c.setStatus("已受理");
                    } else if ("2".equals(c.getStatus())) {
                        c.setStatus("已驳回");
                    }
                }

            }
        }
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.setOnlyAlias(true);
        writer.addHeaderAlias("mcs_code_new", "现27位国家编码");
        writer.addHeaderAlias("mcs_code", "原27位国家编码");
        writer.addHeaderAlias("mcs_code_time", "国家编码调整变动时间");
        writer.addHeaderAlias("reg_fil_prod_name", "注册备案产品名称");
        writer.addHeaderAlias("reg_fil_no", "注册备案号");
        writer.addHeaderAlias("reg_fil_prod_name", "注册备案产品名称");
        writer.addHeaderAlias("name_individual_product", "单件产品名称");
        writer.addHeaderAlias("mcs_entp", "生产企业");
        writer.addHeaderAlias("spec", "规格");
        writer.addHeaderAlias("mol", "型号");
        writer.addHeaderAlias("product_num", "省平台挂网编码");
        writer.addHeaderAlias("fixmedins_contacts", "定点机构联系人");
        writer.addHeaderAlias("fixmedins_phone", "定点机构联系电话");
        writer.addHeaderAlias("verify_reason", "申诉说明");
        writer.addHeaderAlias("reason", "驳回原因");
        writer.addHeaderAlias("createTime", "提交时间");
        writer.addHeaderAlias("createUser", "机构编码");
        writer.addHeaderAlias("createUserName", "机构名称");
        writer.addHeaderAlias("status", "审核状态");
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
    @RequestMapping("/selectOne{id}")
    public ResultInfo selectOne(@PathVariable Serializable id) {
        return ResultInfo.success(this.consumablesSignService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param consumablesSign 实体对象
     * @return 新增结果
     */
    @RequestMapping("insert")
    public ResultInfo insert(@RequestBody ConsumablesSign consumablesSign) {
        return ResultInfo.success(this.consumablesSignService.save(consumablesSign));
    }

    /**
     * 修改数据
     *
     * @param consumablesSign 实体对象
     * @return 修改结果
     */
    @RequestMapping("update")
    public ResultInfo update(@RequestBody ConsumablesSign consumablesSign) {
        return ResultInfo.success(this.consumablesSignService.updateById(consumablesSign));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @RequestMapping("/delete")
    public ResultInfo delete(@RequestParam("idList") List<Long> idList) {
        return ResultInfo.success(this.consumablesSignService.removeByIds(idList));
    }
}

