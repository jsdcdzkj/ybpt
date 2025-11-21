package com.jsdc.ybpt.mapper.formula;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.NotifyApplyDao;
import com.jsdc.ybpt.dao.declare.SbApplyDao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.formula.domain.dto.NotifyApplyDTO;
import com.jsdc.ybpt.formula.domain.entity.NotifyApply;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.declare.SbApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 制剂告知申请(NotifyApply)数据库访问层
 *
 * @author yc
 * @since 2024-05-14 11:23:36
 */
@Mapper
public interface NotifyApplyMapper extends BaseMapper<NotifyApply> {
  
    List<NotifyApply> selectDataList(NotifyApply notifyApply);

    List<NotifyApply> pageByDTO(NotifyApplyDTO notifyApplyDTO);
    
    NotifyApply getVOById(Integer id);

    Page<NotifyApply> applyPage(Page<NotifyApply> objectPage,@Param("notifyApply") NotifyApply notifyApply);

    @SelectProvider(type = NotifyApplyDao.class, method = "getApplyPage")
    Page<NotifyApply> getApplyPage(Page page, NotifyApply notifyApply, SysUser sysUser);

    @SelectProvider(type = NotifyApplyDao.class, method = "getNotifyApplyExportList")
    List<NotifyApply> getNotifyApplyExportList(NotifyApply notifyApply,SysUser sysUser);
}

