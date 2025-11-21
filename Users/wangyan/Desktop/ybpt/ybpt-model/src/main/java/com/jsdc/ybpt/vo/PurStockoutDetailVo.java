package com.jsdc.ybpt.vo;

import com.jsdc.ybpt.pur.PurStockoutDetail;
import lombok.Data;

@Data
public class PurStockoutDetailVo extends PurStockoutDetail {

    private String org_name;//医药机构名称
    private String org_code;//医药机构省平台编码
    private String fix_blng_admdvs;//统筹区
    private String stockout_type;//缺货分类 1.药品 2.耗材
    private String fix_blng_admdvs_name;
    private String stockout_type_name;
}
