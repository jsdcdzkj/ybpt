package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * CLNC_EXAM_RPOT_IMG_INFO_D（临床检查报告影像信息表）
 */
@Data
public class ImgInfoVo {
    //定点医药机构编号
    private String fixmedins_code;
    //就诊流水号;
    private String mdtrt_sn;
    //临床检查报告影像id;
    private String clnc_exam_rpot_img_id;
    //报告单号;
    private String rpotc_no;
    //全量唯一记录号;
    private String ful_rid;
    //检查id;
    private String exam_id;
    //患者姓名;
    private String patn_name;
    //图像流水号;
    private String img_sn;
    // 检查时间;
    private String exam_time;
    //检查类别;
    private String exam_type;
    //存储路径;
    private String stog_path;
    //序列数量;
    private String sn_cnt;
    // 图像数量;
    private String img_cnt;
    //人员编号
    private String psn_no;
    //统筹区
    private String poolarea_no ;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

}
