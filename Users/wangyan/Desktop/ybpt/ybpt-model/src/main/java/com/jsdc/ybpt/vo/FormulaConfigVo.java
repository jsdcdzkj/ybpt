package com.jsdc.ybpt.vo;

import com.jsdc.ybpt.eval_.EvalFormulaConfig;
import lombok.Data;

import java.util.List;

@Data
public class FormulaConfigVo {
    private List<EvalFormulaConfig> configs;
}
