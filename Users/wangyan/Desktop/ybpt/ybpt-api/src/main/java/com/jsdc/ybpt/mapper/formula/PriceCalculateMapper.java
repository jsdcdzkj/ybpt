package com.jsdc.ybpt.mapper.formula;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.formula.domain.dto.PriceCalculateDTO;
import com.jsdc.ybpt.formula.domain.entity.PriceCalculate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 制剂定价测算(PriceCalculate)数据库访问层
 *
 * @author yc
 * @since 2024-05-14 11:23:36

 */
@Mapper
public interface PriceCalculateMapper extends BaseMapper<PriceCalculate> {
  
    List<PriceCalculate> selectDataList(PriceCalculate priceCalculate);

    List<PriceCalculate> pageByDTO(PriceCalculateDTO priceCalculateDTO);
    
    PriceCalculate getVOById(Integer id);
}

