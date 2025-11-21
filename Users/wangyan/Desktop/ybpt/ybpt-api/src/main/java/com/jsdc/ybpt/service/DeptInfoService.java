package com.jsdc.ybpt.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.DeptInfoMapper;
import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ：苹果
 * @Description：
 * @Date ：2022/7/9 14:14
 * @Modified By：
 */


@Service
@RequiredArgsConstructor
public class DeptInfoService  extends BaseService<DeptInfo> {

    private final DeptInfoMapper deptInfoMapper;

    public ResultInfo findDeptInfo(DeptInfo info){
        LambdaQueryWrapper<DeptInfo> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(info.getDept_no()),DeptInfo::getDept_no,info.getDept_no()).
                eq(StringUtils.isNotEmpty(info.getId()),DeptInfo::getId,info.getId()).
                eq(StringUtils.isNotEmpty(info.getEmp_code()),DeptInfo::getEmp_code,info.getEmp_code()).
                eq(DeptInfo::getIs_del,"0");
        List<DeptInfo>lists=this.deptInfoMapper.selectList(wrapper);
        return ResultInfo.success(lists);
    }
}
