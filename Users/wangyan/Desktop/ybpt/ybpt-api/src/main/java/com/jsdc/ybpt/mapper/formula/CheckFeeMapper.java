package com.jsdc.ybpt.mapper.formula;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.formula.domain.dto.CheckFeeDTO;
import com.jsdc.ybpt.formula.domain.entity.CheckFee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 制剂定价测算-检验费(CheckFee)数据库访问层
 *
 * @author yc
 * @since 2024-05-14 11:23:36
 */
@Mapper
public interface CheckFeeMapper extends BaseMapper<CheckFee> {
  
    List<CheckFee> selectDataList(CheckFee checkFee);

    List<CheckFee> pageByDTO(CheckFeeDTO checkFeeDTO);
    
    CheckFee getVOById(Integer id);
}

