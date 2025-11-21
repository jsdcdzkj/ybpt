package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_query.DatadictypeA;
import com.jsdc.ybpt.service.DatadictypeAService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/datadictypeA")
public class DatadictypeAController {
    @Autowired
    private DatadictypeAService service;
    @RequestMapping("/datadictypeQuery")
    public ResultInfo datadictypeQuery(@RequestBody DatadictypeA datadictypeA){
        return ResultInfo.success(service.datadictypeQuery(datadictypeA));
    }
}
