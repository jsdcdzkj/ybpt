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
 * @Author ：苹果
 * @Description：项目套餐关联信息表
 * @Date ：2022/5/26 17:18
 * @Modified By：
 */
@Data
@Entity(name = "item_to_meal")
@TableName("item_to_meal")
public class ItemToMeal extends Model {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;//主键

    private String pack_id;//外键-套餐信息

    private String item_id;//外键-体检项信息

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;
}
