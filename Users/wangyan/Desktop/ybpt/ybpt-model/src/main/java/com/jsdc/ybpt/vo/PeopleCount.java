package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Transient;


@Data
public class PeopleCount extends Model {
    private String icon ;
    private String num ;
    private String title ;
    private String link ;
    private String color ;



}
