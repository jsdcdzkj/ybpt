package com.jsdc.ybpt.mapper.formula;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.formula.domain.dto.LaborDTO;
import com.jsdc.ybpt.formula.domain.entity.Labor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 制剂定价测算-劳务支出(Labor)数据库访问层
 *
 * @author yc
 * @since 2024-05-14 11:23:36
 */
@Mapper
public interface LaborMapper extends BaseMapper<Labor> {
  
    List<Labor> selectDataList(Labor labor);

    List<Labor> pageByDTO(LaborDTO laborDTO);
    
    Labor getVOById(Integer id);
}

