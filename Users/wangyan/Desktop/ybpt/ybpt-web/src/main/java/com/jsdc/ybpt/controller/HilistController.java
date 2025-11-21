package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_query.HilistB;
import com.jsdc.ybpt.service.HilistService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hilistB")
public class HilistController {


    @Autowired
    private HilistService hilistService;

    @RequestMapping("/query")
    public ResultInfo queryALl(){
        List<HilistB>  list=hilistService.hilistBQueryAll();
        return ResultInfo.success(list);
    }
    @RequestMapping("/queryPage")
    public ResultInfo queryPage(@RequestBody  HilistB hilistB){
        return ResultInfo.success(hilistService.queryPage(hilistB));
    }
}
