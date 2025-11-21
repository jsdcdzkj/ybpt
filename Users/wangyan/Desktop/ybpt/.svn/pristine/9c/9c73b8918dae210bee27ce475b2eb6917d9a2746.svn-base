package com.jsdc.ybpt.vo.notice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysFile;
import com.jsdc.ybpt.model_check.Notice;
import lombok.Data;

import javax.persistence.Transient;
import java.util.ArrayList;

@Data
public class NoticeVo extends Notice {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private ArrayList<String> fileIdList = new ArrayList<>();

    private ArrayList<FileInfo> fileInfoList = new ArrayList<>();

    private ArrayList<String> delFileIdList = new ArrayList<>();


    private String createDate;

    private String startTime;

    private String endTime;

    private ArrayList<String> rangeList = new ArrayList<>();

    private String accepter_code;

    private String is_read;

}
