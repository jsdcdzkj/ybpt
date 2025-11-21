package com.jsdc.ybpt.mapper.eval;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.eval.EvalOrgTaskDao;
import com.jsdc.ybpt.eval_.EvalOrgDetail;
import com.jsdc.ybpt.eval_.EvalOrgTask;
import com.jsdc.ybpt.vo.EvalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface EvalOrgDetailMapper extends BaseMapper<EvalOrgDetail> {

    @SelectProvider(type = EvalOrgTaskDao.class, method = "getOrgDetail")
    List<EvalVo> getOrgDetailList(EvalVo vo);

    @SelectProvider(type = EvalOrgTaskDao.class, method = "getOrgDetail")
    Page<EvalVo> getOrgDetailPage(Page page,EvalVo vo);
}
