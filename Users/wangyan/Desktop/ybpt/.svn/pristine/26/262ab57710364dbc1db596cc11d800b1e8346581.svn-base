package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/heartBeat.do")
public class HeartBeatController {
    @RequestMapping("touch")
    public ResultInfo touch() {
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("flag", true);
        return ResultInfo.success(map, "正常");
    }
}
