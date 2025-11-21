package com.jsdc.ybpt.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ReconciliationType
 * @Description TODO
 * @Author xujian
 * @Date 2022/4/28 8:56
 * @Version 1.0
 */
@Data
public class ReconciliationType {
    private String type;//对账类型
    private String insutype;//险种类型
    private String medTypes;//医疗类别
    private String psnTypes;//人员类别

    public ReconciliationType(String type, String insutype, String psnTypes, String medTypes) {
        this.type = type;
        this.insutype = insutype;
        this.psnTypes = psnTypes;
        this.medTypes = medTypes;
    }
}
