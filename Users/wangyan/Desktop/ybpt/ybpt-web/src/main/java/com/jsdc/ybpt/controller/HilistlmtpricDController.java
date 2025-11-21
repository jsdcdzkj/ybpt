package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_query.HilistlmtpricD;
import com.jsdc.ybpt.service.HilistlmtpricDService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hilistlmtpricD")
public class HilistlmtpricDController {
    @Autowired
    private HilistlmtpricDService service;
    @RequestMapping("/hilistlmtpricQuery")
    public ResultInfo hilistlmtpricQuery(@RequestBody HilistlmtpricD hilistlmtpricD){
        return ResultInfo.success(service.hilistlmtpricQuery(hilistlmtpricD));
    }
}
