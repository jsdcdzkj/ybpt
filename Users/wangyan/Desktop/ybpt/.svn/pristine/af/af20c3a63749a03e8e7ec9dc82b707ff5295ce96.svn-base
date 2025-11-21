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
 * 体检项信息表
 */
@Data
@Entity(name = "medical_item")
@TableName("medical_item")
public class MedicalItem extends Model {
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  @Id
  private String id;//主键
  private String item_name;//项目名称
  private String item_no;//项目编码

  private String year;// 年份
  private String item_type;//项目类型
  private String item_type_name;//项目类型名称
  private String is_generic;//是否通用 1是 0否
  private String org_id;//体检机构ID
  private String item_state;//启用禁用 （禁用:0，启用:1）

  private String createUser;      //创建人
  private String createTime;      //创建时间
  private String updateUser;      //创建用户
  private String updateTime;      //创建时间
  private String is_del;          //删除标志、有效状态

  @TableField(exist = false)
  @Transient
  private String  pid;
}
