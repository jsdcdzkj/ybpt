package com.jsdc.fddserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: fddServer
 * @className: TestController
 * @author: wp
 * @description:
 * @date: 2022/9/14 15:48
 */
@RestController
public class TestController {

    @GetMapping("test.do")
    public String test(){
        return "123";
    }
}
