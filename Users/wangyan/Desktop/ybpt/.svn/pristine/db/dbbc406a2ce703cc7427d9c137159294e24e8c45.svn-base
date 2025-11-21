package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author ：苹果
 * @Description：部门信息
 * @Date ：2022/7/9 14:07
 * @Modified By：
 */
@Data
@Entity(name = "dept_info")
@TableName("dept_info")
public class DeptInfo extends Model<DeptInfo> {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    //部门编号
    private String dept_no;
    //部门名称
    private String dept_name;
    //所属单位
    private String emp_code;
    //删除标识
    private String is_del;
}
