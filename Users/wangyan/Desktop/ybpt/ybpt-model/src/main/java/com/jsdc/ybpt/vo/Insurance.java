package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 3.6城乡居民基本医疗保险人员统计表
 * Author wzn
 * Date 2022/9/16 11:32
 */
@Data
public class Insurance {



    //人员参保状态
    private String psn_insu_stas;

    //人员类别
    private String psn_type;
    //参保所属医保区划
    private String insu_admdvs;

    private String count;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;
}
