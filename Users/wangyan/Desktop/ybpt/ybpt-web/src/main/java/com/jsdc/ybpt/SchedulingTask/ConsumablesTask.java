package com.jsdc.ybpt.SchedulingTask;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.model.Consumables;
import com.jsdc.ybpt.service.ConsumablesService;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

//@Component
public class ConsumablesTask {
    @Autowired
    private ConsumablesService consumablesService;
    @Value("${consumablesUrl}")
    private String consumablesUrl;


    /**
     * 根据未审核状态 查询耗材申报
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点执行一次
    public void queryAssociation() {
        LambdaQueryWrapper lambda = new LambdaQueryWrapper<Consumables>()
                .eq(Consumables::getStatus, "1")
                .eq(Consumables::getIs_del, "0");
        List<Consumables> list = consumablesService.list(lambda);
        for (Consumables c : list) {
            JSONObject json = new JSONObject();
            json.set("jyCode", "9001");
            String result = HttpUtil.post(consumablesUrl, consumablesService.getResultInfo(c, json));
            JSONObject jsonObject = JSONUtil.parseObj(result);
            if (StringUtils.isNotEmpty(jsonObject.getJSONObject("data"))) {
                JSONObject data = jsonObject.getJSONObject("data");
                String DCLA_STAS = data.getStr("DCLA_STAS");//申报处理状态
                switch (DCLA_STAS) {
                    case "已处理":
                        c.setStatus("2");
                        c.updateById();
                        break;
                }
            }
        }
    }


}
