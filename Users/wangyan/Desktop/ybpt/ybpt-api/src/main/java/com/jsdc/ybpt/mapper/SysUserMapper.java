package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.SysUserDao;
import com.jsdc.ybpt.model.SysUser;
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
public interface SysUserMapper extends BaseMapper<SysUser> {
    @SelectProvider(type= SysUserDao.class,method = "hehe")
    Page<SysUser> selectUserPage(Page page);

    @SelectProvider(type= SysUserDao.class,method = "selectByPage")
    Page<SysUser> selectByPage(Page page, SysUser sysUser);
}
