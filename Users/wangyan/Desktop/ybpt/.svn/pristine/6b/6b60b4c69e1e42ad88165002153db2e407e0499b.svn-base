package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Transient;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：添加预约记录
 * @Date ：2022/5/31 9:48
 * @Modified By：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmpSubscribeRecordVo {

    private String time;//上午下午

    private String pid;//套餐id

    private String uid;//单位id

    private List<String> wids;//人员id

    private String year;

    private String money;

    private String usertype;

    private List<String >times;//时间

    private String start;

    private String end;

    private String org_name; // 要去的体检机构的org_name

    private String org_id;  // 要去的体检机构的org_id

    private String id;

    private String apply_date; // 体检日期，是要体检的那天，注意不是提交日期

    private String certno; //体检机构协助预约 时用

    private Integer num;

    private String cardType;//证件类型   1 身份证  2 港澳台  3其他

    @Transient
    @TableField(exist = false)
    private String phone;

    @Transient
    @TableField(exist = false)
    private String cardId;


}
