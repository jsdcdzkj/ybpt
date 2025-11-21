package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.CommonDao;
import com.jsdc.ybpt.dao.SysUserDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.vo.NatDataDicAVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:40
 * @Version 1.0
 */
@Mapper
public interface CommonMapper extends BaseMapper<NatDataDicAVo> {
    @SelectProvider(type= CommonDao.class,method = "selectNameById")
    List<NatDataDicAVo> selectNameById(String dic_type_code,String nat_dic_val_code);

    @SelectProvider(type= CommonDao.class,method = "selectDicList")
    List<NatDataDicAVo> selectDicList(String dic_type_code);

}
