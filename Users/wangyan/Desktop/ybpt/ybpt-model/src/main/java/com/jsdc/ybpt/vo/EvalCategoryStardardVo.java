package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.eval_.EvalCategoryStardard;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核指标
 * @Date ：2023/11/16 13:39
 * @Modified By：
 */
@Data
public class EvalCategoryStardardVo  extends EvalCategoryStardard {
    private Integer pageNo = 1;
    private Integer pageSize = 10;


}
