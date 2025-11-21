package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_query.HilistselfpaypropB;
import com.jsdc.ybpt.service.HilistselfpaypropBService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/hilistselfpaypropB")
public class HilistselfpaypropBController {

    @Autowired
    private HilistselfpaypropBService service;
    @RequestMapping("/paypropQuery")
    public ResultInfo paypropQuery(@RequestBody HilistselfpaypropB hilistselfpaypropB){
       return ResultInfo.success(service.paypropQuery(hilistselfpaypropB));
    }
}
