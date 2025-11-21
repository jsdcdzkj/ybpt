package com.jsdc.ybpt.service;


import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.AutonomousMedicalMapper;
import com.jsdc.ybpt.mapper.CivilworkerInfoMapper;
import com.jsdc.ybpt.mapper.PersonSubscribeRecordMapper;
import com.jsdc.ybpt.mapper.RelocatedInfoMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.*;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.AutonomousMedicalVo;
import com.jsdc.ybpt.vo.PersonSubscribeRecordVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AutonomousMedicalService extends BaseService<AutonomousMedical> {
  @Autowired
  private AutonomousMedicalMapper mapper;
  @Autowired
  private SysUserService sysUserService;
  @Autowired
  private EmployingInfoService employingInfoService;
  @Autowired
  private RelocatedInfoMapper relocatedInfoMapper;
  @Autowired
  private PersonSubscribeRecordMapper personSubscribeRecordMapper;
  @Autowired
  private CivilworkerInfoMapper civilworkerInfoMapper;

  /**
   * 分页查询
   *
   * @return
   */
  @DS("master")
  public Page<AutonomousMedical> getList(AutonomousMedicalVo vo) {
    SysUser sysUser = sysUserService.getUser();
    Page<AutonomousMedical> page;
    LambdaQueryWrapper<AutonomousMedical> lambda = new LambdaQueryWrapper();
    //用人单位只能显示本单位数据
    if (sysUser.getUser_type().equals("4")) {
      lambda.eq(AutonomousMedical::getImp_no, sysUser.getOrg_code());
    }
    if (StringUtils.isNotEmpty(vo.getImp_name())) {
      lambda.like(AutonomousMedical::getImp_name, vo.getImp_name());
    }
    if (StringUtils.isNotEmpty(vo.getApply_year())) {
      lambda.eq(AutonomousMedical::getApply_year, vo.getApply_year());
    }
    lambda.eq(AutonomousMedical::getIs_del, "0");
    lambda.orderByAsc(AutonomousMedical::getStatus);
    page = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
    return page;
  }

  /**
   * 编辑
   *
   * @return
   */
  @DS("master")
  public ResultInfo toEdit(AutonomousMedicalVo vo) {
    SysUser sysUser = sysUserService.getUser();
    Map map = new HashMap();
    map.put("sysUser", sysUser);
    if (sysUser.getUser_type().equals("4")) {
      LambdaQueryWrapper lambda = new LambdaQueryWrapper<EmployingInfo>().eq(EmployingInfo::getIs_del, "0").eq(EmployingInfo::getEmp_no, sysUser.getOrg_code());
      EmployingInfo employingInfo = employingInfoService.getOne(lambda);
      map.put("employingInfo", employingInfo);
    }

    return ResultInfo.success(map);
  }

  /**
   * 保存编辑
   *
   * @return
   */
  @DS("master")
  public ResultInfo edit(AutonomousMedicalVo vo) {
    SysUser sysUser = sysUserService.getUser();

    //审核
    if (StringUtils.isNotEmpty(vo.getVerify_ids())) {
      String[] ids = vo.getVerify_ids().split(",");
      for (int i = 0; i < ids.length; i++) {
        vo.setId(ids[i]);
        vo.setVerify_time(DateUtil.formatDateTime(new Date()));
        vo.setVerify_user(sysUser.getUsername());
        if (vo.updateById() == false) {
          ResultInfo.error("审核失败，请检查数据后刷新页面重新审核！");
        }
      }
      return ResultInfo.success("审核成功！");
    }

    //删除
    if (StringUtils.isNotEmpty(vo.getIs_del()) && vo.getIs_del().equals("1")) {
      vo.updateById();
      return ResultInfo.success("删除成功！");
    }

    LambdaQueryWrapper lambdaRelocatedInfo = new LambdaQueryWrapper<RelocatedInfo>()
        .eq(RelocatedInfo::getOrg_code, sysUser.getOrg_code())
        .eq(RelocatedInfo::getIs_del, "0");
    Long relocatedInfo = relocatedInfoMapper.selectCount(lambdaRelocatedInfo);
    if (relocatedInfo > 0) {
      return ResultInfo.error("本单位存在异地安置人员，请删除后重新提交！");
    }

//    LambdaQueryWrapper lambdaPersonSubscribeRecord = new LambdaQueryWrapper<PersonSubscribeRecord>()
//        .eq(PersonSubscribeRecord::getOrg_id, sysUser.getOrg_code())
//        .eq(PersonSubscribeRecord::getYear, vo.getApply_year())
//        .notIn(PersonSubscribeRecord::getSched, "4")
//        .eq(PersonSubscribeRecord::getIs_del, "0");
//    List<PersonSubscribeRecord> personSubscribeRecordList = personSubscribeRecordMapper.selectList(lambdaPersonSubscribeRecord);
    PersonSubscribeRecordVo personSubscribeRecordVo = new PersonSubscribeRecordVo();
    personSubscribeRecordVo.setSysUser(sysUser);
    personSubscribeRecordVo.setEmp_code(sysUser.getOrg_code());
    List<PersonSubscribeRecordVo> personSubscribeRecordList = personSubscribeRecordMapper.listAll(personSubscribeRecordVo) ;

    if (personSubscribeRecordList.size() > 0) {
      String msg = "本单位有人员已经预约体检,无法申请自主体检！\n";
      msg += "预约人员如下：\n";
      for (PersonSubscribeRecordVo personSubscribeRecord : personSubscribeRecordList) {
        msg += personSubscribeRecord.getCertno() + ",\n";
      }
      return ResultInfo.error(msg);

    }

    LambdaQueryWrapper lambdaCivilworkerInfo_count = new LambdaQueryWrapper<CivilworkerInfo>()
        .eq(CivilworkerInfo::getEmp_code, sysUser.getOrg_code())
        .eq(CivilworkerInfo::getIs_del, "0");
    Long count = civilworkerInfoMapper.selectCount(lambdaCivilworkerInfo_count);
    vo.setImp_count(String.valueOf(count));

    if (StringUtils.isNotEmpty(vo.getId())) {
      vo.setUpdateUser(sysUser.getName());
      vo.setUpdateTime(DateUtil.formatDateTime(new Date()));
    } else {
      vo.setCreateUser(sysUser.getName());
      vo.setCreateTime(DateUtil.formatDateTime(new Date()));
    }

    //终止时间
    if (StringUtils.isNotEmpty(vo.getExist_state()) && vo.getExist_state().equals("0")) {
      vo.setEnd_time(DateUtil.formatDateTime(new Date()));
    }


    return ResultInfo.success(vo.insertOrUpdate());
  }


  /**
   * 统计 组织占比
   */
  public Map<String, String> getOrganization(String year) {
    Map<String, String> map = new HashMap<>();
    String zongShu = mapper.getOrganization(year);
    String ziZhu = mapper.getOrganizationZiZhu();
    // 自主：0   ， 非自主：1
    map.put("0", ziZhu);//自主
    map.put("1", zongShu);//非自主
    return map;
  }
}
