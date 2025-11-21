package com.jsdc.ybpt.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author libin
 * @create 2022/6/27 13:45
 */
@Data
public class SubscribeRulesVo {

    private String state;
    private  List<DateArrayVo> dateArray;
}
