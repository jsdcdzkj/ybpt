package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.IsConfig;
import com.jsdc.ybpt.service.IsConfigService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ：苹果
 * @Description：
 * @Date ：2023/1/3 16:08
 * @Modified By：
 */
@RestController
@RequestMapping("/IsConfig")
public class IsConfigController {
    @Autowired
    private IsConfigService isConfigService;

    @PostMapping("/relocatedConfig")
    public ResultInfo relocatedConfig() {
        return this.isConfigService.relocatedConfig();
    }

    @PostMapping("/upRelocatedConfig")
    public ResultInfo upRelocatedConfig(@RequestBody IsConfig bean) {
        return this.isConfigService.upRelocatedConfig(bean);
    }
}
