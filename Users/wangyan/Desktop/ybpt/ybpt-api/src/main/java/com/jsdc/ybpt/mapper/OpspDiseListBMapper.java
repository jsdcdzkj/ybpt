package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.MedinsInfoBDao;
import com.jsdc.ybpt.dao.OpspDiseListBDao;
import com.jsdc.ybpt.vo.MedinsInfoBVo;
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
public interface OpspDiseListBMapper extends BaseMapper<OpspDiseListBVo> {
    @SelectProvider(type= OpspDiseListBDao.class,method = "OpspDiseList")
    Page<OpspDiseListBVo> OpspDiseList(Page page,OpspDiseListBVo opspDiseListBVo);
}
