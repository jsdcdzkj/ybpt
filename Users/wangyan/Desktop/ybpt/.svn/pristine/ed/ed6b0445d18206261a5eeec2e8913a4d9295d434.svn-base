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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 申诉
 *
 */
@Data
@Entity(name = "Sb_apply")
@TableName("Sb_apply")
public class SbApply extends Model<SbApply> {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    //项目名称
    private String project_name;
    //项目编码
    private String project_code;
    //单位名称
    private String org_name;
    //单位医保编码
    private String org_code;
    //计价单位
    private String unit;
    //价格
    private String price;
    //账号类型  1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行 7非定点机构 8非定点药店
    private String user_type;
    //标题
    private String title;
    //经营性质 1:营利性 2:民办非营利 3:政府非营利
    private String natures;
    //申报类型 1.西药 2.中成药 3.中药饮片 4.市场调节价项目 5. 耗材 6.自设项目 7.新增医疗服务 8.市管未定价项目 9.其他病房床位 10.单人间、套房床位
    private String type;
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
    //是否高于公立医疗机构价格(西药时 判断销售价格是否高于采购价格)
    private String high_price;
    @Transient
    @TableField(exist = false)
    private String high_price_name;


    //公立医疗机构实行市场调节价管理医疗服务项目价格明细表
    @Transient
    @TableField(exist = false)
    private List<SbGovernmentMedical> sbGovernmentMedical;
    @Transient
    @TableField(exist = false)
    private List<SbGovernmentMedical> sbGovernmentMedical_println;
    //非公立医疗机构医疗服务项目自主定价明细表
    @Transient
    @TableField(exist = false)
    private List<SbCivilianMedical> sbCivilianMedical;
    @Transient
    @TableField(exist = false)
    private List<SbCivilianMedical> sbCivilianMedical_println;
    //自主定价明细表
    @Transient
    @TableField(exist = false)
    private List<SbCivilianMaterials> sbCivilianMaterial;

    @Transient
    @TableField(exist = false)
    private List<SbBusinessProject> sbBusinessProjects ;

    @Transient
    @TableField(exist = false)
    private ArrayList<SbBusinessExplain> arrayList ;

    @Transient
    @TableField(exist = false)
    private List<SbApplyVo> sbApplyVo;

    @Transient
    @TableField(exist = false)
    private String aggrement_lv;


    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private String year;

    @Transient
    @TableField(exist = false)
    private String month;

    @Transient
    @TableField(exist = false)
    private String day;

    @Transient
    @TableField(exist = false)
    private String names;


    //定点归属医保区划
    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs;

    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_name;

    @Transient
    @TableField(exist = false)
    private String isAudit;
    //初审负责人
    private String first_trialer;
    private Date first_time;

    //复审
    private String second_trialer;
    private Date second_time;

    //终审负责人
    private String end_trialer;
    private Date end_time;

    //驳回负责人
    private String rejecter;
    private Date reject_time;

    //授权书生成人
    private String auth_trialer;
    private Date auth_time;

    @Transient
    @TableField(exist = false)
    private String[] queryDate;
    @Transient
    @TableField(exist = false)
    private String[] authDate;
    @Transient
    @TableField(exist = false)
    private String is_export;

    @Transient
    @TableField(exist = false)
    private List<SbBedDetails> sbBedDetailsList;

    @Transient
    @TableField(exist = false)
    private SbBedDeclaration sbBedDeclaration;

    //床位申报模板标题
    @Transient
    @TableField(exist = false)
    private String headline ;

    @Transient
    @TableField(exist = false)
    private String startTime;
    @Transient
    @TableField(exist = false)
    private String endTime;
    @Transient
    @TableField(exist = false)
    private String startTimeAuth;
    @Transient
    @TableField(exist = false)
    private String endTimeAuth;



    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
}
