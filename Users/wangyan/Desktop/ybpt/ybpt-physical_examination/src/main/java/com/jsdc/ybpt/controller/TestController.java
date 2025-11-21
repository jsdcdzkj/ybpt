package com.jsdc.ybpt.controller;


import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/test.do")
    public ResultInfo heartTest() {
        return ResultInfo.success("ok");
    }

    @GetMapping(value = "/test1")
    public ResultInfo heartTest1() {
        return ResultInfo.success("ok1");
    }


    @RequestMapping(value = "/test2")
    public ResultInfo heartTest2() {
        return ResultInfo.success("ok2");
    }
}
