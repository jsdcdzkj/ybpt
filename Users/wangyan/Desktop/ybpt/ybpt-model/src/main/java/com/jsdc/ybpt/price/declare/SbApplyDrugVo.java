package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 申诉
 *
 */
@Data
public class SbApplyDrugVo extends Model<SbApplyDrugVo> {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String org_name; //单位名称
    private String org_code;//单位医保编码
    //账号类型  1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行 7非定点机构 8非定点药店
    private String user_type;
    //标题
    private String title;
    //经营性质 营利性 民办非营利 政府非营利
    private String natures;
    //申报类型 西药 中成药 中药饮片 中药配方颗粒
    private String type;
    //加价率
    private String premium;
    private String memo;//备注

    //状态 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回
    private String status;
    //驳回原因
    private String reason;
    //合同ID
    private String contractId;
    //签章pdf
    private String pdf_path;
    //签章pdf下载
    private String down_pdf_path;
    //授权书合同ID
    private String auth_contractId;
    //授权书签章pdf
    private String auth_pdf_path;
    //授权书签章pdf下载
    private String auth_down_pdf_path;

    //初审负责人
    private String first_trialer;

    //复审
    private String second_trialer;

    //终审负责人
    private String end_trialer;

    //驳回负责人
    private String rejecter;

    //授权书生成人
    private String auth_trialer;

    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_sbApply;//统筹区
    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_sbApply_name;//统筹区

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private String IsAudit;//是否有审核权限

    @Transient
    @TableField(exist = false)
    private String is_export;

    @Transient
    @TableField(exist = false)
    private String startTime;
    @Transient
    @TableField(exist = false)
    private String endTime;


    private String createUser;      //创建人
    private String updateUser;      //创建用户
    private String is_del;          //删除标志、有效状态
}
