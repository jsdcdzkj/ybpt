package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.assessment.KHAssessmentContent;
import com.jsdc.ybpt.dao.KHAssessmentContentDao;
import com.jsdc.ybpt.vo.AssessmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface KHAssessmentContentMapper extends BaseMapper<KHAssessmentContent> {

    @SelectProvider(type = KHAssessmentContentDao.class,method = "getList")
    public List<KHAssessmentContent> getList(AssessmentVo vo);
}
