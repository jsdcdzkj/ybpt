package com.jsdc.ybpt.vo;

import com.jsdc.ybpt.model_check.MedicalItem;
import lombok.Data;

import java.util.List;

@Data
public class MedicalItemVo extends MedicalItem {
  private String ids;
  private Integer pageNo = 1;
  private Integer pageSize = 10;
  private List<String> outsideApiIds;
}
