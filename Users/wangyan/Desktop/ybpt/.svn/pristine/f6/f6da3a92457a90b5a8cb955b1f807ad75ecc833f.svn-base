package com.jsdc.ybpt.agreementsignModel;

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
 * 协议网签-网签模板表
 */
@Data
@Entity(name = "net_tag_agreement")
@TableName("net_tag_agreement")
public class NetTagAgreement  extends Model<NetTagAgreement> {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 机构类别type(1-医疗机构 2-零售药店) 注:目前就这两种
     */
    private Integer category_id;
    /** 机构等级（1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级）字典表获取 */
    private String net_grade_id;
    /** 年份 */
    private String year;
    /** 状态(0已上线 1已下线 2部分下线) */
    private Integer status;
    /** 标题 */
    private String title;
    /** 附件 */
    private String file_id;
    /** 医保机构编码 */
    private String medical_code;
    /** 创建人 */
    private String createUser;
    /** 创建时间 */
    private Date createTime;
    /** 创建用户 */
    private String updateUser;
    /** 创建时间 */
    private Date updateTime;
    /** 删除标志、有效状态 */
    private String is_del;

    /** 甲方机构名称 **/
    private String party_a_name;
    /** 甲方机构地址 **/
    private String party_a_address;
    /** 甲方签章 **/
    private String party_a_seal;
    /** 甲方法定代表人 **/
    private String party_a_legal_person;

    /** 乙方机构名称 **/
    private String party_b_name;
    /** 乙方机构地址 **/
    private String party_b_address;
    /** 乙方机构编码 **/
    private String party_b_code;
    /** 乙方签章 **/
    private String party_b_seal;
    /** 乙方法定代表人 **/
    private String party_b_legal_person;

    /** 附件 word */
    private String file_word;

    /** 附件 pdf */
    private String file_pdf;

    @Transient
    @TableField(exist = false)
    private NetTagFile netTagFile = new NetTagFile();

    /**
     * 分页pageNo
     */
    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    /**
     * 分页pageSize
     */
    @Transient
    @TableField(exist = false)
    private Integer pageSize ;
    @Transient
    @TableField(exist = false)
    private String pdf_path;

    /**
     * 部分下线ids
     */
    @Transient
    @TableField(exist = false)
    private List<String> org_ids = new ArrayList<>();

    /**
     * 机构等级
     */
    @Transient
    @TableField(exist = false)
    private List<String> net_grade_ids = new ArrayList<>();

}
