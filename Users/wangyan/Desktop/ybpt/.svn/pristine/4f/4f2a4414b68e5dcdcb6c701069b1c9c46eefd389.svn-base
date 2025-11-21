package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.DeptInfoDao;
import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.vo.DepartmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DeptInfoMapper  extends BaseMapper<DeptInfo> {
    @SelectProvider(type= DeptInfoDao.class,method = "getDeptInfoListByCertNo")
    List<DepartmentVo> getDeptInfoListByCertNo(String certNo);
}
