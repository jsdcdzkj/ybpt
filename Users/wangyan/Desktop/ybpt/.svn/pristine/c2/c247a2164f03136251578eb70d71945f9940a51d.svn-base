package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author ：苹果
 * @Description：配置
 * @Date ：2023/1/3 15:18
 * @Modified By：
 */
@Data
@TableName("is_config")
@Entity(name = "is_config")
public class IsConfig extends Model<IsConfig> implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private  Integer id;

    private String isOpen;

    private String memo;
}
