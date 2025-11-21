package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.eval_.*;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核设计表
 * @Date ：2023/11/16 11:55
 * @Modified By：
 */
@Data
public class EvalDesignVo extends EvalDesign {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    private Integer piont=0;

}
