package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.MedinsInfoBDao;
import com.jsdc.ybpt.dao.PharInfoBDao;
import com.jsdc.ybpt.vo.MedinsInfoBVo;
import com.jsdc.ybpt.vo.PharInfoBVo;
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
public interface PharInfoBMapper extends BaseMapper<PharInfoBVo> {
    @SelectProvider(type= PharInfoBDao.class,method = "selectPharInfo")
    Page<PharInfoBVo> selectPharInfo(Page page, String phar_code,String phar_name);
}
