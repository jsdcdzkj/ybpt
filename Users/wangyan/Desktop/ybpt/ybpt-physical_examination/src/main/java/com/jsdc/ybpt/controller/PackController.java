package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.model_check.PackInfo;
import com.jsdc.ybpt.service.PackInfoService;
import com.jsdc.ybpt.vo.OrganizationInfoVo;
import com.jsdc.ybpt.vo.PackInfoVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐信息
 */
@RestController
@RequestMapping("/pack")
public class PackController {
    @Autowired
    PackInfoService service;

    @RequestMapping("/getList")
    public ResultInfo getList(PackInfoVo vo) {
        return ResultInfo.success(service.getList(vo));
    }

    @RequestMapping("/toEdit")
    public ResultInfo toEdit(PackInfoVo vo) {
        return ResultInfo.success(service.toEdit(vo));
    }

    @RequestMapping("/edit")
    public ResultInfo edit(@RequestBody PackInfoVo vo) {
        return service.edit(vo);
    }

    /**
     * 获取已机构套餐上架/未上架
     *
     * @param vo
     * @return
     */
    @RequestMapping("/getSelectOrgList")
    public ResultInfo getSelectOrgList(PackInfoVo vo) {
        return ResultInfo.success(service.getOrgList(vo));
    }

    /**
     * 获取套餐中体检项
     * @param vo
     * @return
     */
    @RequestMapping("/getEntity")
    public ResultInfo getEntity(@RequestBody PackInfoVo vo) {
        return ResultInfo.success(service.getEntity(vo));
    }

    /**
     * 获取机构套餐上架
     * @param ids
     * @return
     */
    @RequestMapping("/setOrgOption")
    public ResultInfo setOrgOpenOption(String ids,String flag) {
            return ResultInfo.success(service.setOrgIfOpen(ids,"T".equalsIgnoreCase(flag)?true:false));
    }



    /**
     * @Description: 获取套餐机构
     * @param: [packYear, packSource]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: yc
     * @date: 2022/6/7
     * @time: 10:48
     */
    @ResponseBody
    @RequestMapping("getOrgList")
    public ResultInfo getPackInfoList (String packYear, String packSource) {
        List<PackInfo> packInfoList = service.getOrgList(packYear, packSource);
        return ResultInfo.success(packInfoList);
    }

    /**
     * @Description: 获取机构定义的套餐
     * @param: [packYear, packSource]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/7
     * @time: 15:21
     */
    @ResponseBody
    @RequestMapping("getDeptList")
    public ResultInfo getDeptList(PackInfoVo vo) {
        List<PackInfo> packInfoList = service.getDeptList(vo);
        return ResultInfo.success(packInfoList);
    }

/**
 * @Description: 获取机构定义的套餐
 * @param: [packYear, packSource]
 * @return: com.jsdc.ybpt.vo.ResultInfo
 * @author: 苹果
 * @date: 2022/6/7
 * @time: 15:21
 */
    @ResponseBody
    @RequestMapping("getPackInfoList")
    public ResultInfo getPackInfoList(String packYear, String packSource, String orgId) {
        List<PackInfo> packInfoList = service.getPackInfoList(packYear, packSource,orgId);
        return ResultInfo.success(packInfoList);
    }
    /***
     * @Description: 查询体检机构下是否新建套餐
     * @param: [packYear, packSource]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/24
     * @time: 11:10
     */
    @ResponseBody
    @GetMapping("/getOrgList1")
    public ResultInfo getOrgList1(String packYear,String packSource) {
        List<PackInfo> packInfoList = service.getOrgList1(packYear, packSource);
        return ResultInfo.success(packInfoList);
    }
    
    
    /***
     * @Description: 查询体检机构
     * @param: []
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/29 
     * @time: 19:16
     */
    @ResponseBody
    @GetMapping("/getOrgListPack")
    public ResultInfo getOrgListPack(OrganizationInfoVo vo){
        List<OrganizationInfo> orgs = service.getOrgListPack(vo);
        return ResultInfo.success(orgs);
    }
}
