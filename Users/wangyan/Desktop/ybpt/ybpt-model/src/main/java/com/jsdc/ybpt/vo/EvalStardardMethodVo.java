package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.eval_.EvalStardardMethod;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核设计评价项目内容办法表
 * @Date ：2023/11/16 13:42
 * @Modified By：
 */
@Data
public class EvalStardardMethodVo extends EvalStardardMethod {
    private Integer pageNo = 1;
    private Integer pageSize = 10;


}
