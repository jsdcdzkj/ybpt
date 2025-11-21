package com.jsdc.ybpt.mapper.terminal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.AdviceDao;
import com.jsdc.ybpt.dao.terminal.TerminalInfoDao;
import com.jsdc.ybpt.terminal.TerminalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface TerminalInfoMapper extends BaseMapper<TerminalInfo> {

    @SelectProvider(type = TerminalInfoDao.class, method = "queryPage")
    Page<TerminalInfo> queryPage(Page page, TerminalInfo bean);
    @SelectProvider(type = TerminalInfoDao.class, method = "queryList")
    List<TerminalInfo> queryList(TerminalInfo bean);
}
