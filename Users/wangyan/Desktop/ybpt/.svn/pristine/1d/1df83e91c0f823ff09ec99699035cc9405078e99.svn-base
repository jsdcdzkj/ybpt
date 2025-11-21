package com.jsdc.ybpt.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model.UnfixedMechanism;
import com.jsdc.ybpt.price.declare.SbDentalMedical;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.UnfixedMechanismService;
import com.jsdc.ybpt.service.declare.SbDentalMedicalService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (SbDentalMedical)表控制层
 *
 * @author wangYan
 * @since 2023-04-01 08:21:35
 */
@RestController
@RequestMapping("sbDentalMedical")
public class SbDentalMedicalController {
    /**
     * 服务对象
     */
    @Resource
    private SbDentalMedicalService sbDentalMedicalService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;
    @Autowired
    private UnfixedMechanismService unfixedMechanismService;

    /**
     * 分页查询所有数据
     *
     * @param sbDentalMedical 查询实体
     * @return 所有数据
     */
    @RequestMapping("/getPage")
    public ResultInfo getPage(Integer pageNo, Integer pageSize, SbDentalMedical sbDentalMedical) {
        SysUser sysUser = sysUserService.getUser();
        LambdaQueryWrapper<SbDentalMedical> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(sbDentalMedical.getFix_blng_admdvs())) {
            lambdaQueryWrapper.eq(SbDentalMedical::getFix_blng_admdvs, sbDentalMedical.getFix_blng_admdvs());
        }
        if (StringUtils.isNotBlank(sbDentalMedical.getUser_type())) {
            if ("非定点".equals(sbDentalMedical.getUser_type())) {
                lambdaQueryWrapper.in(SbDentalMedical::getUser_type, "7", "8");
            } else {
                lambdaQueryWrapper.and(
                        x -> x.notIn(SbDentalMedical::getUser_type, "7", "8")
                                .or().isNull(SbDentalMedical::getUser_type)
                );
            }
        }
        if (StringUtils.isNotBlank(sbDentalMedical.getOrg_code())) {
            lambdaQueryWrapper.eq(SbDentalMedical::getOrg_code, sbDentalMedical.getOrg_code());
        }
        if (StringUtils.isNotBlank(sbDentalMedical.getStatus())) {
            lambdaQueryWrapper.eq(SbDentalMedical::getStatus, sbDentalMedical.getStatus());
        }
        lambdaQueryWrapper.eq(SbDentalMedical::getIs_del, "0");

        Page<SbDentalMedical> sbDentalMedicalPage = this.sbDentalMedicalService.page(new Page<>(pageNo, pageSize), lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(sbDentalMedicalPage.getRecords())) {
            for (SbDentalMedical s : sbDentalMedicalPage.getRecords()) {
                //查询统筹区
//                HashMap<String, String> tcqMap = new HashMap();
//                List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
//                tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
//                s.setFix_blng_admdvs_name(tcqMap.get(s.getFix_blng_admdvs()));
                s.setIsAudit("false");
                //判断是否有审核权限。前端展示用
                if ("1".equals(sysUser.getUser_type()) && sysUser.getOrg_code().equals(s.getFix_blng_admdvs())) {
                    s.setIsAudit("true");
                }
                if ("7".equals(s.getUser_type()) || "8".equals(s.getUser_type())) {
                    s.setUser_type("非定点");
                } else {
                    s.setUser_type("定点");
                }
            }
        }
        return ResultInfo.success(sbDentalMedicalPage);
    }

    @RequestMapping("/export")
    public void export(HttpServletResponse response, SbDentalMedical sbDentalMedical) throws IOException {
        SysUser sysUser = sysUserService.getUser();
        LambdaQueryWrapper<SbDentalMedical> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!"1".equals(sysUser.getUser_type())) {//行政管理
            lambdaQueryWrapper.eq(SbDentalMedical::getOrg_code, sysUser.getOrg_code());
        }
        if (StringUtils.isNotBlank(sbDentalMedical.getOrg_code())) {
            lambdaQueryWrapper.eq(SbDentalMedical::getOrg_code, sbDentalMedical.getOrg_code());
        }
        if (StringUtils.isNotBlank(sbDentalMedical.getStatus())) {
            lambdaQueryWrapper.eq(SbDentalMedical::getStatus, sbDentalMedical.getStatus());
        }
        lambdaQueryWrapper.eq(SbDentalMedical::getIs_del, "0");
        List<SbDentalMedical> list = sbDentalMedicalService.list(lambdaQueryWrapper);
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getStatus()) {
                case "0":
                    list.get(i).setStatus("待初审");
                    break;
                case "1":
                    list.get(i).setStatus("待复审");
                    break;
                case "2":
                    list.get(i).setStatus("待终审");
                    break;
                case "3":
                    list.get(i).setStatus("待生成受理书");
                    break;
                case "4":
                    list.get(i).setStatus("完成");
                    break;
                case "5":
                    list.get(i).setStatus("驳回");
                    break;
            }
            if ("7".equals(list.get(i).getUser_type()) || "8".equals(list.get(i).getUser_type())) {
                list.get(i).setUser_type("非定点");
            } else {
                list.get(i).setUser_type("定点");
            }
        }

        // 一次性写出内容，使用默认样式，强制输出标题
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("org_code", "国家编码");
        writer.addHeaderAlias("org_name", "机构名称");
        writer.addHeaderAlias("aggrement_lv", "协议等级");
        writer.addHeaderAlias("natures", "经营性质");
        writer.addHeaderAlias("user_type", "定点类型");
        writer.addHeaderAlias("price", "申报价格");
        writer.addHeaderAlias("limit_price", "同级限价");
        writer.addHeaderAlias("status", "审核状态");
        writer.addHeaderAlias("reason", "驳回原因");

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
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @RequestMapping("/selectOne/{id}")
    public ResultInfo selectOne(@PathVariable Serializable id) {
        return ResultInfo.success(this.sbDentalMedicalService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sbDentalMedical 实体对象
     * @return 新增结果
     */
    @RequestMapping("/insert")
    public ResultInfo insert(@RequestBody SbDentalMedical sbDentalMedical) {
        return ResultInfo.success(this.sbDentalMedicalService.save(sbDentalMedical));
    }

    /**
     * 修改数据
     *
     * @param sbDentalMedical 实体对象
     * @return 修改结果
     */
    @RequestMapping("/update")
    public ResultInfo update(@RequestBody SbDentalMedical sbDentalMedical) {
        return ResultInfo.success(this.sbDentalMedicalService.updateById(sbDentalMedical));
    }

    /**
     * 新增修改数据
     *
     * @param sbDentalMedical 实体对象
     * @return 修改结果
     */
    @RequestMapping("/insertOrUpdate")
    public ResultInfo insertOrUpdate(@RequestBody SbDentalMedical sbDentalMedical) {
        SysUser sysUser = sysUserService.getUser();
        FixmedinsB fixmedinsB = new FixmedinsB();
        if ("7".equals(sysUser.getUser_type()) || "8".equals(sysUser.getUser_type())) {
            UnfixedMechanism unfixedMechanism = unfixedMechanismService.getOne(Wrappers.<UnfixedMechanism>lambdaQuery()
                    .eq(UnfixedMechanism::getFixmedins_code, sysUser.getOrg_code())
                    .eq(UnfixedMechanism::getIs_del, "0")
            );
            fixmedinsB.setFix_blng_admdvs(unfixedMechanism.getFix_blng_admdvs());
            fixmedinsB.setAggrement_lv(unfixedMechanism.getAggrement_lv());
        } else {
            QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("fixmedins_code", sysUser.getOrg_code());
            fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper);
        }
        if (sbDentalMedical.getId() == null) {
            sbDentalMedical.setId(IdUtil.simpleUUID());
            sbDentalMedical.setUser_type(sysUser.getUser_type());
            sbDentalMedical.setFix_blng_admdvs(fixmedinsB.getFix_blng_admdvs());
            sbDentalMedical.setOrg_code(sysUser.getOrg_code());
            sbDentalMedical.setOrg_name(sysUser.getOrg_name());
            sbDentalMedical.setCreateUser(sysUser.getId());
            sbDentalMedical.setCreateTime(new Date());
            sbDentalMedical.setIs_del("0");
        } else {
            sbDentalMedical.setUpdateUser(sysUser.getId());
            sbDentalMedical.setUpdateTime(new Date());
        }
        return ResultInfo.success(this.sbDentalMedicalService.saveOrUpdate(sbDentalMedical));
    }
}

