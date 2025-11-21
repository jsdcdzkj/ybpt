package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.Agreement;
import com.jsdc.ybpt.service.AgreementService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.coyote.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agreement")
public class AgreementController {
    @Autowired
    private AgreementService service;

    @RequestMapping("/saveOrUpdate")
    public ResultInfo saveOrUpdate(@RequestBody Agreement agreement) {
        if (this.service.saveOrUpdate(agreement)) {
            return ResultInfo.success();
        }
        return ResultInfo.error("保存失败");
    }

    @RequestMapping("/getAgreement")
    public ResultInfo getAgreement() {
        Agreement agreement = this.service.getAgreement();
        return ResultInfo.success(agreement);
    }
}
