package com.jsdc.ybpt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentVo {
    private String id;
    //部门编号
    private String dept_no;
    //部门名称
    private String dept_name;
    //所属单位
    private String emp_code;

    private String isCheck = "0"; // 当前部门 "1", 非当前部门 "0"
}
