package com.jsdc.ybpt.mapper.formula;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.formula.domain.dto.FixedAssetsDepreDTO;
import com.jsdc.ybpt.formula.domain.entity.FixedAssetsDepre;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 制剂定价测算-固定资产折旧(FixedAssetsDepre)数据库访问层
 *
 * @author yc
 * @since 2024-05-14 11:23:36
 */
@Mapper
public interface FixedAssetsDepreMapper extends BaseMapper<FixedAssetsDepre> {
  
    List<FixedAssetsDepre> selectDataList(FixedAssetsDepre fixedAssetsDepre);

    List<FixedAssetsDepre> pageByDTO(FixedAssetsDepreDTO fixedAssetsDepreDTO);
    
    FixedAssetsDepre getVOById(Integer id);
}

