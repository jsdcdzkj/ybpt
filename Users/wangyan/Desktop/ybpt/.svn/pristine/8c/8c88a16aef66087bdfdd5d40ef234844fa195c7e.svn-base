package com.jsdc.ybpt.mapper.formula;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.formula.domain.dto.MaterialConsumeDTO;
import com.jsdc.ybpt.formula.domain.entity.MaterialConsume;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 制剂定价测算-材料消耗支出(MaterialConsume)数据库访问层
 *
 * @author yc
 * @since 2024-05-14 11:23:36
 */
@Mapper
public interface MaterialConsumeMapper extends BaseMapper<MaterialConsume> {
  
    List<MaterialConsume> selectDataList(MaterialConsume materialConsume);

    List<MaterialConsume> pageByDTO(MaterialConsumeDTO materialConsumeDTO);
    
    MaterialConsume getVOById(Integer id);
}

