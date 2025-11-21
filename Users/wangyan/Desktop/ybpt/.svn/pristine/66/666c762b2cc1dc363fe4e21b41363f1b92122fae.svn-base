package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.CommonDao;
import com.jsdc.ybpt.dao.EmployingInfoDao;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.model_check.EmployingInfo;
import com.jsdc.ybpt.vo.NatDataDicAVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface EmployingInfoMapper extends BaseMapper<EmployingInfo> {

    @SelectProvider(type= EmployingInfoDao.class,method = "yrdwList")
    List<EmployingInfo> yrdwList();

    @SelectProvider(type= EmployingInfoDao.class,method = "otherSelect")
    List<EmployingInfo> otherSelect();

    @SelectProvider(type = EmployingInfoDao.class, method = "getEmployingInfoListCheckedByOrg")
    List<EmployingInfo> getEmployingInfoListCheckedByOrg(String medicalInsuranceNum);

    @SelectProvider(type= EmployingInfoDao.class,method = "getDepartmentListByEmpCode")
    List<DeptInfo> getDepartmentListByEmpCode(String empCode);
}
