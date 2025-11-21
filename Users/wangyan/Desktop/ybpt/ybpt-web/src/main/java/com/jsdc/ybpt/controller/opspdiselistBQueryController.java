package com.jsdc.ybpt.controller;


import com.jsdc.ybpt.model_query.OpspdiselistB;
import com.jsdc.ybpt.service.OpspdiselistBQueryService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/opspdiselistB")
public class opspdiselistBQueryController {

    @Autowired
    private OpspdiselistBQueryService queryService;

    @RequestMapping("/opspquery")
    public ResultInfo opspQueryAll(){
        List<OpspdiselistB> list=queryService.opspQueryAll();
        return ResultInfo.success(list);
    }
    @RequestMapping("/opspQueryPage")
    public ResultInfo opspQueryPage(@RequestBody OpspdiselistB opspdiselistB){
        return ResultInfo.success(queryService.opspQueryPage(opspdiselistB));
    }
}
