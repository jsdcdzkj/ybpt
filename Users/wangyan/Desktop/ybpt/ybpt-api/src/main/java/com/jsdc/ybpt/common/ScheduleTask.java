package com.jsdc.ybpt.common;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.ybpt.mapper.PersonSubscribeRecordMapper;
import com.jsdc.ybpt.model_check.PersonSubscribeRecord;
import com.jsdc.ybpt.service.PersonSubscribeRecordService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ScheduleTask
 * Description:
 * date: 2022/7/22 11:36
 *
 * @author bn
 */
@Component
public class ScheduleTask {

    @Autowired
    PersonSubscribeRecordMapper recordMapper;


//    @Scheduled(cron = "0/10 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    private void personSubscribeRecordTask(){

        String date= DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        recordMapper.update(null,
                Wrappers.<PersonSubscribeRecord>lambdaUpdate().
                        set(PersonSubscribeRecord::getSched,"2").
                        eq(PersonSubscribeRecord::getIs_del,"0").
                        eq(PersonSubscribeRecord::getSched,"0").
                        apply("to_date(apply_date,'yyyy-mm-dd') < to_date('"+date+"','yyyy-mm-dd')"));
    }
}
