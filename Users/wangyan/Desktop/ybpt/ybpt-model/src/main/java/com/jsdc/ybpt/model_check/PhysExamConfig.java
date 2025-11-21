package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 医保体检规则设置（体检标准）
 */
@Data
@Entity(name = "phys_exam_config")
@TableName("phys_exam_config")
public class PhysExamConfig extends Model {
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  @Id
  private String id;//主键
  private String year;//年份 (格式为 yyyy  例如2022)
  private Integer examination_num;//年体检次数 (年体检次数)
  private Integer cancel_num;//撤销次数
  private String standard_name;//标准名称
  private String standard_money;//标准金额 (体检金额标准)
  //行政单位
  private String administrative_unit;
  private String createUser;      //创建人
  private String createTime;      //创建时间
  private String updateUser;      //创建用户
  private String updateTime;      //创建时间
  private String is_del;          //删除标志、有效状态
}
