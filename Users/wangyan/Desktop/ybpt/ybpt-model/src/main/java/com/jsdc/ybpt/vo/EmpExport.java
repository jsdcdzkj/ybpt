package com.jsdc.ybpt.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ：苹果
 * @Description：
 * @Date ：2022/6/15 16:22
 * @Modified By：
 */
//@Excel("预约列表")
@Data
public class EmpExport implements Serializable {
//
//    @ExcelField(value = "套餐年份")
    private String year;
//
//    @ExcelField(value = "套餐类型")
    private String pack_name;
//
//    @ExcelField(value = "体检机构")
    private String oname;
//
//    @ExcelField(value = "预约时间")
    private String start_time;
//
//    @ExcelField(value = "预约人数")
    private String subscribe_num;
    //
//    @ExcelField(value = "预约方式")
    private String is_personal;

}
