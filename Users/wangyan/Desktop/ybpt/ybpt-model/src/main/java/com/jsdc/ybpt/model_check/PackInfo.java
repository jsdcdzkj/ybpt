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
import java.util.List;

/**
 * 套餐信息
 */
@Data
@Entity(name = "pack_info")
@TableName("pack_info")
public class PackInfo extends Model {
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  @Id
  private String id;//主键
  private String pack_year;//套餐年份 (取年份)
  private String pack_name;//套餐名称
  private String pack_money;//套餐金额
  private String phys_exam_id;//套餐规则ID (外键-体检规则设置表phys exam_config)
  private String pack_source;//套餐来源 (机构:1，通用:2)
  private String org_id;//机构ID (医保局ID、外键-体检机构信息)
  private String org_name;//机构名称
  private String if_open;//是否上架 (不上架:0 ， 上架：1)
  private String if_use;//启用禁用 (禁用:0 ， 启用：1)
  private String applicable_scope;//适用范围
  private String applicable_scope_name;//适用范围-转义
  private String memo;//描述
  private String gender;//性别  1男2女
  private String status;//审核状态 (待审:0，通过:1， 驳回:2)
  private String verify_time;//审核时间
  private String verify_reason;//审核意见
  private String verify_user;//审核人

  private String createUser;      //创建人
  private String createTime;      //创建时间
  private String updateUser;      //创建用户
  private String updateTime;      //创建时间
  private String is_del;          //删除标志、有效状态

  @TableField(exist = false)
  @Transient
  private List<MedicalItem> Item;


  @TableField(exist = false)
  @Transient
  private Integer count;
}
