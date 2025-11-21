package com.jsdc.ybpt.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.OpspRegdService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.OpspRegDVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
*门慢门特查询
* Author wzn
* Date 2022/4/21 15:51
*/
@RestController
@RequestMapping("/opspRegd")
public class OpspRegdController {
    @Autowired
    private OpspRegdService opspRegdService;

    @RequestMapping("/selectByIdCard")
    public ResultInfo selectByIdCard(String certno,String psn_cert_type){
        List<OpspRegDVo> opspRegDVoList =  opspRegdService.selectByIdCard(certno,psn_cert_type) ;
        if(null != opspRegDVoList && opspRegDVoList.size() != 0){
            return ResultInfo.success(opspRegDVoList.get(0));
        }else {
            return ResultInfo.success();
        }
    }

    /**
    *通过身份证查单位
    * Author wzn
    * Date 2022/4/29 15:51
    */
    @RequestMapping("/selectCompanyByIdCard")
    public ResultInfo selectCompanyByIdCard(@RequestBody OpspRegDVo opspRegDVo){
        List<OpspRegDVo> opspRegDVoList =  opspRegdService.selectCompanyByIdCard(opspRegDVo.getCertno(),opspRegDVo.getPsn_cert_type()) ;
        if(null != opspRegDVoList && opspRegDVoList.size() != 0){
            return ResultInfo.success(opspRegDVoList.get(0));
        }else {
            return ResultInfo.success();
        }
    }


}
