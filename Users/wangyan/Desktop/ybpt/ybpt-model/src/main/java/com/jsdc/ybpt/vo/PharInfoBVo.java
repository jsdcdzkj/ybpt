package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 *医师信息表
 */
@Data
public class PharInfoBVo extends Model {

    //医师代码
    private String  phar_code;
    //医师姓名
    private String  phar_name;


    private Integer pageNo ;

    private Integer pageSize ;



}
