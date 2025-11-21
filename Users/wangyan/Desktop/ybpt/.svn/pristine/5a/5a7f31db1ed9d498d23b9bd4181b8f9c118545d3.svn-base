package com.jsdc.ybpt.mapper.eval;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.AdviceDao;
import com.jsdc.ybpt.dao.eval.EvalOrgTaskDao;
import com.jsdc.ybpt.eval_.EvalOrgTask;
import com.jsdc.ybpt.eval_.EvalOrgTaskVo;
import com.jsdc.ybpt.model.Bank;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.advice.Advice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface EvalOrgTaskMapper extends BaseMapper<EvalOrgTask> {


    @SelectProvider(type = EvalOrgTaskDao.class, method = "averageScore")
    EvalOrgTask averageScore(String id);


    @SelectProvider(type = EvalOrgTaskDao.class, method = "noScore")
    Page<EvalOrgTaskVo> noScore(Page page, EvalOrgTaskVo evalOrgTaskVo);
}
