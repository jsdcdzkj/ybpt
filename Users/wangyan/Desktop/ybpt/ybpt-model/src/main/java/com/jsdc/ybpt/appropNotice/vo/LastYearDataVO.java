package com.jsdc.ybpt.appropNotice.vo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 去年数据实体
 * @Author WangXiao
 * @Date 2024/5/30
 */
@Data
public class LastYearDataVO {

    @Alias("险种")
    private String xzlx;

    @Alias("医保区划")
    private String tcq;

    @Alias("机构名称")
    private String orgName;

    @Alias("机构编码")
    private String orgCode;

    @Alias("机构等级")
    private String jb;

    @Alias("结算期")
    private String jsq;

    @Alias("医疗费总额")
    private BigDecimal ylfze;

    @Alias("统筹基金")
    private BigDecimal tcjj;

    @Alias("生育基金")
    private BigDecimal syjj;
}
