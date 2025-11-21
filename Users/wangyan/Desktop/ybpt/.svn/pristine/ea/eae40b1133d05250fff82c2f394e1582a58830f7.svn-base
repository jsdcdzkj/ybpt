package com.jsdc.ybpt.model_check;

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
 * 公务员申请审核表
 */
@Data
@Entity(name = "civilworker_virfy")
@TableName("civilworker_virfy")
public class CivilworkerVirfy extends Model<CivilworkerVirfy> {
    @TableId
    @Id
    private String id;

    //姓名
    private String name;
    //公务员ID
    private String cw_id;

    //性别男:1 ，女:2
    @Transient
    @TableField(exist = false)
    private String sex;

    //年龄
    @Transient
    @TableField(exist = false)
    private String age;

    //身份证号码
    private String certno;
    // 手机号
    private String telephone;
    //参保统筹区
    private String insu_admdvs;
    //所属统筹区
    private String admdvs;
    //单位ID
    private String emp_id;
    //单位名称
    private String emp_name;
    //单位编码
    private String emp_code;
    //单位类型公立:1，非公立:2
    private String emp_type;
    //参保险种
    private String insutype;
    //参保状态
    private String insu_state;
    //异地就医
    private String outside_flag;
    //是否异地安置人员否:0，是:1
    private String outside_put;
    //死亡标志
    private String death_flag;

    //体检资格
    private String qualifications;

    //审核类型新增:1， 删除:2  修改3
    private String verify_type;
    //审核状态  待审核:0，通过:1，驳回:2
    private String status;
    //审核时间
    private Date verify_time;
    //申请单位ID
    private String apply_id;
    //审批单位ID
    private String audit_id;
    //申请原因
    private String reason;
    //同步:1， 医保:2，用人单位:3
    private String info_source;
    //上级行政单位
    private String parentCode;
    //所属部门id
    private String dept_id;
    //所属部门
    private String dept_name;

    //外键关联用
    @Transient
    @TableField(exist = false)
    private String parentorgcode;
    @Transient
    @TableField(exist = false)
    //所属部门
    private String dname;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;
    //数据标志
    private String flag;
    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    @Transient
    @TableField(exist = false)
    private List<String> ids;

    private String cardType;//证件类型   1 身份证  2 港澳台  3其他

    private String birthday; //出生日期

}
