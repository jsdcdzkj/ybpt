package com.jsdc.ybpt.service.eval;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.eval_.EvalDesignCategory;
import com.jsdc.ybpt.mapper.eval.EvalDesignCategoryMapper;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.EvalDesignCategoryVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvalDesignCategoryService extends BaseService<EvalDesignCategory> {

    @Autowired
    private EvalDesignCategoryMapper evalDesignCategoryMapper;

    public Page getPageList(EvalDesignCategoryVo vo) {
        LambdaQueryWrapper<EvalDesignCategory> wrapper = new LambdaQueryWrapper<>();
        Page<EvalDesignCategory> page = page(new Page<>(vo.getPageNo(), vo.getPageSize()), wrapper);
        return page;
    }

    public ResultInfo saveOrUpdate(EvalDesignCategoryVo bean) {
        if (StringUtils.isEmpty(bean.getId())) {
            bean.setId(IdUtil.simpleUUID());
            save(bean);
        } else {
            updateById(bean);
        }
        return ResultInfo.success();
    }

    public ResultInfo del(String id){
        evalDesignCategoryMapper.deleteById(id);
        return ResultInfo.success();
    }

    public EvalDesignCategory getEntity(String id){
        EvalDesignCategory designCategory=getById(id);
        return designCategory;
    }
}

