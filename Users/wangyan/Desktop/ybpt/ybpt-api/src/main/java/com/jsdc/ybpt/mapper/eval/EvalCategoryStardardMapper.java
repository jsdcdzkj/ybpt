package com.jsdc.ybpt.mapper.eval;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.eval.EvalCategoryStardardDao;
import com.jsdc.ybpt.dao.eval.EvalOrgTaskDao;
import com.jsdc.ybpt.eval_.EvalCategoryStardard;
import com.jsdc.ybpt.eval_.EvalOrgDetail;
import com.jsdc.ybpt.eval_.EvalOrgTask;
import com.jsdc.ybpt.eval_.EvalStardardMethod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface EvalCategoryStardardMapper  extends BaseMapper<EvalCategoryStardard> {


    /**
     *根据考核单查询自定义配置的所有办法
     * Author wzn
     * Date 2023/11/29 9:48
     */
    @SelectProvider(type = EvalCategoryStardardDao.class, method = "getMethod")
    List<EvalStardardMethod> getMethod(String id);

    @SelectProvider(type = EvalCategoryStardardDao.class, method = "getSum")
    EvalOrgDetail getSum(String id);


    /**
    *根据考核单查询所有办法 指标
    * Author wzn
    * Date 2023/11/29 17:13
    */
    @SelectProvider(type = EvalCategoryStardardDao.class, method = "getMedAndStar")
    List<EvalStardardMethod> getMedAndStar(String id);



}

