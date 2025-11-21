package com.jsdc.ybpt.mapper.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.CheckSuspicionsDao;
import com.jsdc.ybpt.dao.notice.NoticeToAccepterDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.Notice;
import com.jsdc.ybpt.model_check.NoticeScope;
import com.jsdc.ybpt.model_check.NoticeToAccepter;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeToAccepterMapper extends BaseMapper<NoticeToAccepter> {

    @InsertProvider(type = NoticeToAccepterDao.class,method = "importDataSql")
    void importDataSql(String sql);
}
