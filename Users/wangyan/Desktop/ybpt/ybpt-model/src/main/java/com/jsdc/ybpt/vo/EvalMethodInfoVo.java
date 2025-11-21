package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.eval_.EvalMethodInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author ：苹果
 * @Description：考核设计评价项目内容办法详情表
 * @Date ：2023/11/16 13:46
 * @Modified By：
 */
@Data
public class EvalMethodInfoVo extends EvalMethodInfo {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
}
