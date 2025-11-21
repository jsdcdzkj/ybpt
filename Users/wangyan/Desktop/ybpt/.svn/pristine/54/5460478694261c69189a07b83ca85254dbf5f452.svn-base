package com.jsdc.ybpt.price.materials;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 材料
 */
@Data
@Entity(name = "sb_materials")
@TableName("sb_materials")
public class SbMaterials extends Model<SbMaterials> {
    @TableId
    @Id
    private String id;

    //收费项目编码
    private String itemCoding;
    //项目名称
    private String projectName;
    //收费项目等级
    private String projectLevel;
    //特殊医用材料名称
    private String nameOfTheMaterial;
    //    说明
    private String explain;
    //变更类型
    private String changeType;
    //变更原因
    private String reasonsForChange;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

}
