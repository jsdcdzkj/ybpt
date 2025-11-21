package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 自主体检申请表
 */
@Data
@Entity(name = "autonomous_medical")
@TableName("autonomous_medical")
public class AutonomousMedical extends Model {
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  @Id
  private String id;//主键

  private String apply_year;//申请年份
  private String apply_reason;//申请原由
  private String exist_state;//存续状态 （终止:0，存续:1）
  private String end_time;//终止时间

  private String imp_no;//申请单位编号
  private String imp_id;//申请单位ID
  private String imp_name;//申请单位名称
  private String imp_count;//申请单位总人数

  private String finance_account;// 财务账户
  private String deposit_bank; // 开户银行
  private String bank_account;// 银行账户
  private String contacts;// 联系人
  private String contacts_phone;// 联系方式

  private String status;//审核状态 (待审:0，通过:1， 驳回:2)
  private String verify_time;//审核时间
  private String verify_reason;//审核意见
  private String verify_user;//审核人


  private String createUser;      //创建人
  private String createTime;      //创建时间
  private String updateUser;      //创建用户
  private String updateTime;      //创建时间
  private String is_del;
}
