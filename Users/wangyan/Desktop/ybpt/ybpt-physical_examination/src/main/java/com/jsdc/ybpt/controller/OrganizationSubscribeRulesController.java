package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.service.OrgSubscribeRulesService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orgSubscribeRulesMobile")
public class OrganizationSubscribeRulesController {
    @Autowired
    private OrgSubscribeRulesService orgSubscribeRulesService;

    @GetMapping("findOrgSubRulesList")
    @ResponseBody
    public ResultInfo findSubRules(String id,String state) {
        return ResultInfo.success(orgSubscribeRulesService.findSubRules(id,state));
    }
}
