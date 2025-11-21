package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.OrgSubscribeRules;
import com.jsdc.ybpt.service.CivilworkerInfoService;
import com.jsdc.ybpt.service.CivilworkerVirfyService;
import com.jsdc.ybpt.service.OrgSubscribeRulesService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mybatis")
public class MybatisController {
    @Autowired
    private CivilworkerInfoService civilworkerInfoService;

    @Autowired
    private OrgSubscribeRulesService orgSubscribeRulesService;

    @RequestMapping("/test1")
    public ResultInfo test1() {
        List<OrgSubscribeRules> orgSubscribeRules = this.orgSubscribeRulesService.mybatisOrg();
        return ResultInfo.success(orgSubscribeRules);
    }
}
