package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.MedinsDeptBMapper;
import com.jsdc.ybpt.model.MedinsDeptB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.MedicalItem;
import com.jsdc.ybpt.vo.MedinsDeptBVo;
import com.jsdc.ybpt.vo.ResultInfo;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedinsDeptBService extends BaseService<MedinsDeptB> {
  @Autowired
  private MedinsDeptBMapper mapper;
  @Autowired
  private SysUserService sysUserService;
  @Autowired
  private FixmedinsBService fixmedinsBService;

  /**
   * 分页查询
   *
   * @return
   */
  @DS("master")
  public Page<MedinsDeptB> getList(MedinsDeptBVo vo) {
    SysUser sysUser = sysUserService.getUser();
    Page<MedinsDeptB> medinsDeptBPage;
    LambdaQueryWrapper<MedinsDeptB> lambda = new LambdaQueryWrapper();
    lambda.eq(MedinsDeptB::getOrg_code, sysUser.getOrg_code());
    lambda.eq(MedinsDeptB::getUser_type, sysUser.getUser_type());
    if (StringUtils.isNotEmpty(vo.getDept_no())) {
      lambda.like(MedinsDeptB::getDept_no, vo.getDept_no());
    }
    if (StringUtils.isNotEmpty(vo.getDept_name())) {
      lambda.like(MedinsDeptB::getDept_name, vo.getDept_name());
    }
    if (StringUtils.isNotEmpty(vo.getDept_type())) {
      lambda.eq(MedinsDeptB::getDept_type, vo.getDept_type());
    }
    lambda.eq(MedinsDeptB::getVali_flag, "1");
    medinsDeptBPage = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
    return medinsDeptBPage;
  }

  public ResultInfo getListAll(MedinsDeptBVo vo) {
    List list;
    if (StringUtils.isNotEmpty(vo.getOrg_code()) && StringUtils.isNotEmpty(vo.getUser_type())) {
      LambdaQueryWrapper<MedinsDeptB> lambda = new LambdaQueryWrapper();
      lambda.eq(MedinsDeptB::getOrg_code, vo.getOrg_code());
      lambda.eq(MedinsDeptB::getUser_type, vo.getUser_type());
      lambda.eq(MedinsDeptB::getVali_flag, "1");
      list = mapper.selectList(lambda);
    } else {
      list = new ArrayList();
    }
    return ResultInfo.success(list);
  }

  /**
   * 修改
   *
   * @param medinsDeptB
   * @return
   */
  @DS("master")
  public ResultInfo edit(MedinsDeptB medinsDeptB) {
    SysUser sysUser = sysUserService.getUser();

    if(StringUtils.isNotEmpty(medinsDeptB.getId())){
      //去重
      MedinsDeptB m = mapper.selectById(medinsDeptB.getId());
      if(!m.getDept_no().equals(medinsDeptB.getDept_no())){
        LambdaQueryWrapper lambdaName = new LambdaQueryWrapper<MedinsDeptB>()
                .eq(MedinsDeptB::getDept_no, medinsDeptB.getDept_no())
                .eq(MedinsDeptB::getOrg_code, sysUser.getOrg_code())
                .eq(MedinsDeptB::getVali_flag, "1");
        Long countName = mapper.selectCount(lambdaName);
        if (countName > 0) {
          return ResultInfo.error("【科室编码】重复,请重新填写！");
        }
      }
    }else{
      LambdaQueryWrapper lambdaName = new LambdaQueryWrapper<MedinsDeptB>()
              .eq(MedinsDeptB::getDept_no, medinsDeptB.getDept_no())
              .eq(MedinsDeptB::getOrg_code, sysUser.getOrg_code());
      Long countName = mapper.selectCount(lambdaName);
      if (countName > 0) {
        return ResultInfo.error("【科室编码】重复,请重新填写！");
      }
    }

    return ResultInfo.success(medinsDeptB.insertOrUpdate());
  }

  @DS("master")
  public ResultInfo toEdit(MedinsDeptBVo vo) {
    SysUser sysUser = sysUserService.getUser();
    return ResultInfo.success(sysUser);
  }
}
