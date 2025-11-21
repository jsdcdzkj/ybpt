package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.DiagListBDao;
import com.jsdc.ybpt.dao.OpspDiseListBDao;
import com.jsdc.ybpt.vo.DiagListBVo;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:40
 * @Version 1.0
 */
@Mapper
public interface DiagListBMapper extends BaseMapper<DiagListBVo> {
    @SelectProvider(type= DiagListBDao.class,method = "diagList")
    Page<DiagListBVo> diagList(Page page, DiagListBVo diagListBVo);
}
