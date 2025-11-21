package com.jsdc.ybpt.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsdc.ybpt.assessment.KHLog;
import com.jsdc.ybpt.mapper.KHLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KHLogService {
    @Autowired
    private KHLogMapper khLogMapper ;

    public Boolean add(String kh_manage_id, String title, String content) {
        KHLog khLog = new KHLog();
        khLog.setKh_manage_id(kh_manage_id);
        khLog.setTitle(title);
        khLog.setSubmit_time(DateUtil.formatDateTime(new Date()));
        khLog.setContent(content);
        return khLog.insert();
    }


    /**
    *日志详情接口
    * Author wzn
    * Date 2022/11/25 11:48
    */
    public List<KHLog> logList(String id){
        QueryWrapper<KHLog> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("kh_manage_id",id) ;
        queryWrapper.orderByAsc("submit_time") ;
        List<KHLog> khLogList = khLogMapper.selectList(queryWrapper) ;
        if(CollectionUtils.isNotEmpty(khLogList)){
            for(KHLog k:khLogList){
                if(StringUtils.isNotEmpty(k.getContent())){
                    k.setType("danger");
                }else {
                    k.setType("primary");
                }
            }
        }
        return khLogList ;
    }
}
