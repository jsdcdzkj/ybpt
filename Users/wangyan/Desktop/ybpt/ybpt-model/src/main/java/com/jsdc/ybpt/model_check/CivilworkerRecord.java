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
 * @Description：公务员年度体检记录
 * @Date ：2022/5/26 16:59
 * @Modified By：
 */
@Data
@TableName("civilworker_record")
@Entity(name = "civilworker_record")
public class CivilworkerRecord extends Model {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;//主键

    private String civilworker_id;//公务员id

    private String year;//年份

    private String check_flag;//  是否可预约体检 0：否 1：是
}
