package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.assessment.KHManage;
import com.jsdc.ybpt.dao.KHManageDao;
import com.jsdc.ybpt.vo.AssessmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface KHManageMapper extends BaseMapper<KHManage> {

    @SelectProvider(type = KHManageDao.class, method = "getList")
    Page<AssessmentVo> getList(Page page, AssessmentVo vo);
}
