package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.OrgSubscribeRules;
import com.jsdc.ybpt.service.OrgSubscribeRulesService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.OrgSubscribeRulesVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author libin
 * @create 2022-05-31 12:00:07
 */
@RestController
@RequestMapping("/orgSubscribeRules")
public class OrgSubscribeRulesController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OrgSubscribeRulesService orgSubscribeRulesService;

    @PostMapping("/addOrgSubscribeRules")
    public ResultInfo addOrgSubscribeRules(@RequestBody OrgSubscribeRulesVo orgSubscribeRules) {
        SysUser sysUser = sysUserService.getUser();

        orgSubscribeRulesService.addOrgSubscribeRules(orgSubscribeRules);
        return ResultInfo.success();
    }

    @PostMapping("/updateOrgSubscribeRules")
    public ResultInfo updateOrgSubscribeRules(@RequestBody OrgSubscribeRules orgSubscribeRules) {
        orgSubscribeRulesService.updateOrgSubscribeRules(orgSubscribeRules);
        return ResultInfo.success();
    }

    @PostMapping("/selectList")
    public ResultInfo selectOrgSubscribeRulesList(@RequestBody OrgSubscribeRules orgSubscribeRules) {
        Page<OrgSubscribeRules> page = orgSubscribeRulesService.selectList(orgSubscribeRules);
        return ResultInfo.success(page);
    }

    @PostMapping("/delOrgSubscribeRules")
    public ResultInfo delOrgSubscribeRules(@RequestBody OrgSubscribeRules orgSubscribeRules) {
        return ResultInfo.success();
    }

    @PostMapping("/getOrgSubscribeRulesList")
    public ResultInfo getOrgSubscribeRulesList() {
        return ResultInfo.success(orgSubscribeRulesService.getList());
    }

    @PostMapping("/getRulesListByOrgId")
    public ResultInfo getRulesListByOrgId(@RequestBody OrgSubscribeRules orgSubscribeRules){
        return ResultInfo.success(orgSubscribeRulesService.getList(orgSubscribeRules));
    }

    /**
     * @Description: 批量预约查询规则
     * @param: [id]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/8
     * @time: 15:45
     */
    @GetMapping("findSubRules")
    public ResultInfo findSubRules(String id,String state ) {
        return  ResultInfo.success(orgSubscribeRulesService.findSubRules(id,state));
    }

    @PostMapping("/removeDuplicateRules")
    public ResultInfo removeDuplicateRules(){
        orgSubscribeRulesService.removeDuplicateRules();
        return ResultInfo.success();
    }

}
