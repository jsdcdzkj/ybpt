package com.jsdc.ybpt.mapper.formula;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.formula.domain.dto.ManageLossOtherFeeDTO;
import com.jsdc.ybpt.formula.domain.entity.ManageLossOtherFee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 制剂定价测算-管理费、损耗及其他(ManageLossOtherFee)数据库访问层
 *
 * @author yc
 * @since 2024-05-14 11:23:36
 */
@Mapper
public interface ManageLossOtherFeeMapper extends BaseMapper<ManageLossOtherFee> {
  
    List<ManageLossOtherFee> selectDataList(ManageLossOtherFee manageLossOtherFee);

    List<ManageLossOtherFee> pageByDTO(ManageLossOtherFeeDTO manageLossOtherFeeDTO);
    
    ManageLossOtherFee getVOById(Integer id);
}

