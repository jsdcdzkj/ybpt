package com.jsdc.ybpt.price.advice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 价格调整建议 主表
 *
 * @author wangYan
 * @since 2023/5/9
 */
@Data
@Entity(name = "advice")
@TableName("advice")
public class Advice extends Model<Advice> {
    @TableId
    @Id
    private String id;
    //经营性质 1:营利性 2:民办非营利 3:政府非营利
    private String natures;
    //账号类型  1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行 7非定点机构 8非定点药店
    private String user_type;
    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs;
    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_name;
    //单位名称
    private String org_name;
    //单位医保编码
    private String org_code;
    //协议等级1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级
    private String aggrement_lv;
    //签章pdf
    private String pdf_path;
    //签章pdf下载
    private String down_pdf_path;
    @Transient
    @TableField(exist = false)
    private List<AdviceSummary> details;
    //合同ID
    private String contractId;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    @Transient
    @TableField(exist = false)
    private String is_export;

    @Transient
    @TableField(exist = false)
    private String isAudit;

    @Transient
    @TableField(exist = false)
    private String[] queryDate;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private List<AdviceSummary> adviceSummaryList ;

    @Transient
    @TableField(exist = false)
    private String filling_time ;









}
