package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 医保门慢门特病种目录表
 */
@Data
public class OpspDiseListBVo extends Model {

    //门慢门特病种目录代码
    private String opsp_dise_code;
    //门慢门特病种名称
    private String opsp_dise_name;
    //门慢门特病种大类代码
    private String opsp_dise_majcls_code;
    //门慢门特病种大类名称
    private String opsp_dise_majcls_name;
    //险种类型
    private String insutype;
    //病种类型代码
    private String dise_type_code;
    //结束日期
    private String enddate;
    private String beginDate;
    private Integer pageNo ;
    private String medins_code ;
    private String medins_name ;
    private String medinslv ;

    private Integer pageSize ;
    private String  certNo ;
}
