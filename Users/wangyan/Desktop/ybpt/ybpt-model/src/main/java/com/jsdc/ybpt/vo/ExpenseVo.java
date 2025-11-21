package com.jsdc.ybpt.vo;

import com.jsdc.ybpt.model_check.Expense;
import com.jsdc.ybpt.model_check.ExpenseDetail;
import lombok.Data;

/**
 * @Author ：苹果
 * @Description：费用明细
 * @Date ：2024/6/11 11:05
 * @Modified By：
 */
@Data

public class ExpenseVo extends Expense {

    private Integer pageNo;
    private Integer pageSize;
}
