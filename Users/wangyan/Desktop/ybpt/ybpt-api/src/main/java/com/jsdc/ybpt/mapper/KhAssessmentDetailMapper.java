package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.assessment.KhAssessmentDetail;
import com.jsdc.ybpt.dao.KhAssessmentDetailDao;
import com.jsdc.ybpt.vo.AssessmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface KhAssessmentDetailMapper extends BaseMapper<KhAssessmentDetail> {

    @SelectProvider(type = KhAssessmentDetailDao.class, method = "getList")
    List<KhAssessmentDetail> getList(AssessmentVo vo);
}
