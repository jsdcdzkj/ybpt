package com.jsdc.ybpt.SchedulingTask;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.model_check.PackInfo;
import com.jsdc.ybpt.service.PackInfoService;
import com.jsdc.ybpt.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：体检套餐定时任务
 * @Date ：2024/6/24 14:52
 * @Modified By：
 */
@Component
public class PackTask {

    @Autowired
    private PackInfoService packInfoService;


    @Scheduled(cron = "0 0 0 1 1 *")
    public  void  closePack(){
        LocalDate currentDate = LocalDate.now(); // 获取当前日期
        LocalDate previousYearDate = currentDate.minusYears(1); // 获取当前日期前一年的日期
        String formattedDate = previousYearDate.format(DateTimeFormatter.ofPattern("yyyy")); // 将日期格式化为指定格式的字符串
        LambdaQueryWrapper<PackInfo> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(PackInfo::getIs_del,0).eq(PackInfo::getPack_year,formattedDate);
        List<PackInfo> list = packInfoService.list(wrapper);
        for (PackInfo packInfo : list) {
            packInfo.setIf_open("0");
            packInfo.updateById();
        }
    }
}
