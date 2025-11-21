package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
public class SbBedApplyVo extends Model<SbBedApplyVo> {
    //单位名称
    private String org_name;
    //单位医保编码
    private String org_code;

    //经营性质
    private String natures;
    //医院等级
    private String aggrement_lv;

}
