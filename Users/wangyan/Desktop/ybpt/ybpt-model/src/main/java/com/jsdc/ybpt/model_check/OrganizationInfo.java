package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 体检机构信息表
 */
@Data
@Entity(name = "organization_info")
@TableName("organization_info")
public class OrganizationInfo extends Model {
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  @Id
  private String id;//主键
  private String org_code;//所属机构
  private String org_name;//机构名称
  private String org_linkman;//机构联系人
  private String org_phone;//联系电话
  private String medical_insurance_num;//医保编码
  private String credit_level;//医保信用等级 （A级:1，B级:2，C级:3）
  private String credit_level_name;//医保信用等级 （A级:1，B级:2，C级:3）
  private String national_info_level;//国家医保信息编码 (一级:1，二级:2，三级:3，未定级:4)
  private String national_info_level_name;//国家医保信息编码 (一级:1，二级:2，三级:3，未定级:4)
  private String administrative_divisions;//所在行政区 (行政区划字典表)
  private String administrative_divisions_name;//所在行政区 (行政区划字典表)
  private String detail_address;//详细地址
  private String ownership;//所有制形式 (公立:1，非公立:2)
  private String ownership_name;//所有制形式 (公立:1，非公立:2)
  private String credit_code;//社会统一信用代码
  private String admdvs;//所属统筹区 (统筹区)
  private String admdvs_name;//所属统筹区 (统筹区)
  //授权码
  private String authorizationCode;
  //行政单位
  private String administrative_unit;
  private String createUser;      //创建人
  private String createTime;      //创建时间
  private String updateUser;      //创建用户
  private String updateTime;      //创建时间
  private String is_del;          //删除标志、有效状态

  // 页面展示 是否有体检套餐 0 没有 1.有
  @TableField(exist = false)
  @Transient
  private String  is_select="0";
}
