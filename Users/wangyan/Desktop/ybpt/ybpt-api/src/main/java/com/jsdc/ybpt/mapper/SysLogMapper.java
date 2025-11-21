package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.model.SysLog;
import com.jsdc.ybpt.service.SysLogService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
