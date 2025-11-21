package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 医疗机构科室信息表
 */
@Data
@Entity(name = "medins_dept_b")
@TableName("medins_dept_b")
public class MedinsDeptB extends Model {
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  @Id
  private String id;//主键
  private String org_code;      //机构编码
  private String org_name;      //机构名称
  private String user_type;     //账号类型


  private String dept_type;//科室类型
  private String dept_no;//科室编号
  private String begntime;//开始时间
  private String dept_name;//科室名称
  private String itro;//简介
  private String dept_resper_name;//科室负责人姓名
  private String dept_resper_tel;//科室负责人电话
  private String dept_med_serv_scp;//科室医疗服务范围
  private String dept_estbdat;//科室成立日期
  private String aprv_bed_cnt;//批准床位数量
  private String hi_crtf_bed_cnt;//医保认可床位数
  private String dr_psncnt;//医师人数
  private String phar_psncnt;//药师人数
  private String nurs_psncnt;//护士人数
  private String tecn_psncnt;//技师人数
  private String endtime;//结束时间
  private String vali_flag;//有效标志
  private String memo;//备注
  private String rid;//数据唯一记录号
  private String crte_time;//数据创建时间
  private String updt_time;//数据更新时间
  private String poolarea_no;//统筹区编号
  private String optins_no;//经办机构编号
  private String opt_time;//经办时间
  private String opter_name;//经办人姓名
  private String opter_id;//经办人ID
  private String crte_optins_no;//创建机构编号
  private String crter_name;//创建人姓名
  private String crter_id;//创建人ID
  private String ver;//版本号
  private String caty;//科别
  private String sync_prnt_flag;//同步上级标志

}
