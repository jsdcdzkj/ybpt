package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.capitalSettlement.QsOrgConfirmation;
import com.jsdc.ybpt.dao.QsOrgConfirmationDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
    public interface QsOrgConfirmationMapper extends BaseMapper<QsOrgConfirmation> {

    @SelectProvider(type= QsOrgConfirmationDao.class,method = "getList")
    Page<QsOrgConfirmation> getList(Page<QsOrgConfirmation> page, QsOrgConfirmation bean);
}
