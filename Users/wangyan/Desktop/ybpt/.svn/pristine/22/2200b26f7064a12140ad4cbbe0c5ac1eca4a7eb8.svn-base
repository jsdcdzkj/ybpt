package com.jsdc.ybpt.service;


import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.PackInfoMapper;
import com.jsdc.ybpt.mapper.PhysExamConfigMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.PackInfo;
import com.jsdc.ybpt.model_check.PhysExamConfig;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.PhysExamConfigVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PhysExamConfigService extends BaseService<PhysExamConfig> {
  @Autowired
  private PhysExamConfigMapper mapper;
  @Autowired
  private SysUserService sysUserService;
  @Autowired
  private PackInfoMapper packInfoMapper;

  /**
   * 分页查询
   *
   * @return
   */
  @DS("master")
  public Page<PhysExamConfig> getList(PhysExamConfigVo vo) {
    Page<PhysExamConfig> autonomousMedical;
    LambdaQueryWrapper<PhysExamConfig> lambda = new LambdaQueryWrapper();
    if (StringUtils.isNotEmpty(vo.getStandard_name())) {
      lambda.like(PhysExamConfig::getStandard_name, vo.getStandard_name());
    }
    if (StringUtils.isNotEmpty(vo.getYear())) {
      lambda.eq(PhysExamConfig::getYear, vo.getYear());
    }
    if (StringUtils.isNotEmpty(vo.getAdministrative_unit())) {
      lambda.eq(PhysExamConfig::getAdministrative_unit, vo.getAdministrative_unit());
    }
    lambda.eq(PhysExamConfig::getIs_del, "0");
    autonomousMedical = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
    return autonomousMedical;
  }

  /**
   * 编辑
   *
   * @return
   */
  @DS("master")
  public ResultInfo toEdit(PhysExamConfigVo vo) {
    return ResultInfo.success(vo.selectById());
  }

  /**
   * 保存编辑
   *
   * @return
   */
  @DS("master")
  public ResultInfo edit(PhysExamConfigVo vo) {
    SysUser sysUser = sysUserService.getUser();

    if (StringUtils.isNotEmpty(vo.getIs_del()) && vo.getIs_del().equals("1")) {
      LambdaQueryWrapper lambdaDel = new LambdaQueryWrapper<PackInfo>().eq(PackInfo::getIs_del, "0").eq(PackInfo::getPhys_exam_id, vo.getId());
      Long idxDel = packInfoMapper.selectCount(lambdaDel);
      if (idxDel > 0) {
        return ResultInfo.error("套餐管理存在关联的标准，请先删除套餐后，再删除标准！");
      }
      return ResultInfo.success(vo.insertOrUpdate());
    }

    if (StringUtils.isNotEmpty(vo.getId())) {
      PhysExamConfig physExamConfig = mapper.selectById(vo.getId());
      if (!physExamConfig.getYear().equals(vo.getYear())) {
        LambdaQueryWrapper lambdaUpdate = new LambdaQueryWrapper<PhysExamConfig>().eq(PhysExamConfig::getIs_del, "0").eq(PhysExamConfig::getYear, vo.getYear());
        Long idxUpdate = mapper.selectCount(lambdaUpdate);
        if (idxUpdate > 0) {
          return ResultInfo.error("一年只能存一个规则不能重复添加");
        }
      }
      vo.setUpdateUser(sysUser.getName());
      vo.setUpdateTime(DateUtil.formatDateTime(new Date()));
    } else {
      LambdaQueryWrapper lambda = new LambdaQueryWrapper<PhysExamConfig>().eq(PhysExamConfig::getIs_del, "0").eq(PhysExamConfig::getYear, vo.getYear());
      Long idx = mapper.selectCount(lambda);
      if (idx > 0) {
        return ResultInfo.error("一年只能存一个规则不能重复添加");
      }
      vo.setCreateUser(sysUser.getName());
      vo.setCreateTime(DateUtil.formatDateTime(new Date()));
    }

    if (StringUtils.isNotEmpty(vo.getIds())) {
      String[] ids = vo.getIds().split(",");
      for (int i = 0; i < ids.length; i++) {
        vo.setId(ids[i]);
        vo.setIs_del("1");
        vo.updateById();
      }
    }
    return ResultInfo.success(vo.insertOrUpdate());
  }
}
