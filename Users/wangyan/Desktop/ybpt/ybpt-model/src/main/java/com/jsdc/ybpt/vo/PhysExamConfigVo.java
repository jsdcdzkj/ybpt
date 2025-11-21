package com.jsdc.ybpt.vo;

import com.jsdc.ybpt.model_check.PhysExamConfig;
import lombok.Data;

@Data
public class PhysExamConfigVo extends PhysExamConfig {
  private String ids;
  private Integer pageNo = 1;
  private Integer pageSize = 10;
}
