package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 部分下线的成员
 */
@Data
@Entity(name = "net_tag_agreement_status")
@TableName("net_tag_agreement_status")
public class NetTagAgreementStatusMember {

    @Id
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /** 协议网签-网签模板表id */
    private String net_tag_agreement_id;

    /** 机构标识 */
    private String org_id;

    /** 机构名称 */
    private String org_name;

    /** 机构编码 */
    private String org_code;

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

}
