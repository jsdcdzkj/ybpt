package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class FileInfoService extends BaseService<FileInfo> {

    public List<FileInfo> getFileInfoByBizId(String bizId,String bizType){
        QueryWrapper qw = new QueryWrapper<FileInfo>();
        if(StringUtils.isNotEmpty(bizId)){
            qw.eq("bizId",bizId);
        }
        if(StringUtils.isNotEmpty(bizType)){
            qw.eq("bizType",bizType);
        }
        List<FileInfo> fileInfos = list(qw);
        return fileInfos;
    }

}
