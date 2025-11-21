package com.jsdc.ybpt.service;

import com.jsdc.ybpt.dao.CommonDao;
import com.jsdc.ybpt.dao.DeptInfoDao;
import com.jsdc.ybpt.mapper.DeptInfoMapper;
import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.vo.DepartmentVo;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {
    @Autowired
    private DeptInfoMapper deptInfoMapper;
    public List<DepartmentVo> getDeptInfoListByCertNo(String certNo) {
        return this.deptInfoMapper.getDeptInfoListByCertNo(certNo);
    }

    public DeptInfo getDeptInfoById(String deptId) {
        return this.deptInfoMapper.selectById(deptId);
    }
}
