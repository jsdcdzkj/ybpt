package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.EmployingInfo;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.vo.PhysicalRatioVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计报表
 */
@RestController
@RequestMapping("/statistic")
public class StatisticChartController {
    @Autowired
    private PackInfoService packInfoService;
    @Autowired
    private EmployingInfoService employingInfoService;
    @Autowired
    private CivilworkerInfoService civilworkerInfoService;
    @Autowired
    private AutonomousMedicalService autonomousMedicalService;
    @Autowired
    private PersonSubscribeRecordService personSubscribeRecordService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * @param
     * @return ResultInfo.success
     * @Description 医保中心 套餐年份报表
     */

    @RequestMapping("/packageChart/getYearList")
    public ResultInfo getYearList() {
        List<String> yearList = packInfoService.getYearList();
        return ResultInfo.success(yearList);
    }

    @RequestMapping("/packageChart/getRatio")
    public ResultInfo getPackageChart(String year, String orgId) {
        SysUser user = sysUserService.getUser();
        orgId = user.getOrg_code();
        //套餐来源 (机构:1，通用:2)
        Map<String, String> packageChart = packInfoService.getPackageChart(year, orgId);

        return ResultInfo.success(packageChart);
    }

    /**
     * 获取体检占比 全部用人单位
     *
     * @return
     */
    @RequestMapping("/getEmploying")
    public ResultInfo getEmploying() {
        List<EmployingInfo> eList = employingInfoService.getAllEmploying();
        return ResultInfo.success(eList);
    }

    /**
     * 体检占比
     *
     * @param year
     * @param orgId
     * @return
     */
    @RequestMapping("/getPhysicalRatio")
    public ResultInfo getPhysicalRatio(String year, String orgId) {
        Map<String, String> ratio = civilworkerInfoService.getPhysicalRatio(year, orgId);
        return ResultInfo.success(ratio);
    }

    /**
     * 组织占比
     */
    @RequestMapping("/getOrganization")
    public ResultInfo getOrganization(String year) {
        Map<String, String> org = autonomousMedicalService.getOrganization(year);
        return ResultInfo.success(org);
    }

    /**
     * 服务排行
     */
    @RequestMapping("/getRanking")
    public ResultInfo getRanking(String year) {
        List ranking = personSubscribeRecordService.getRanking(year);
        return ResultInfo.success(ranking);
    }

    @RequestMapping("/getRankingYear")
    public ResultInfo getRankingYear() {
        List<String> yearList = personSubscribeRecordService.getYearList();
        return ResultInfo.success(yearList);
    }

    @RequestMapping("/getEmployingInfoPhysicalRatioCheckedByOrg")
    public ResultInfo adminUnitPhysicalRatioCheckedByOrg(String year, String empCode) {
        // 待体检:0 , 已体检:1，已过期:2， 已上传报告:3 ，已撤销：4    13  24
        HashMap<String, Integer> sched = new HashMap<>();
        sched.put("un", 0);
        sched.put("ed", 0);
        sched.put("gq", 0);
        sched.put("scbg", 0);

        SysUser user = sysUserService.getUser();
        String org_code = "";
        if(!user.getUser_type().equals("1")){
            org_code=user.getOrg_code();
        }
        List<PhysicalRatioVo> physicalRatioVoList = this.personSubscribeRecordService.adminUnitPhysicalRatioCheckedByOrg(year, empCode, org_code);
        for (int i = 0; i < physicalRatioVoList.size(); i++) {
            if ("0".equals(physicalRatioVoList.get(i).getSched())){
                sched.put("un", sched.get("un") + physicalRatioVoList.get(i).getCount());
            }
            if ("1".equals(physicalRatioVoList.get(i).getSched())){
                sched.put("ed", sched.get("ed") + physicalRatioVoList.get(i).getCount());
            }
            if("3".equals(physicalRatioVoList.get(i).getSched())){
                sched.put("scbg", sched.get("scbg") + physicalRatioVoList.get(i).getCount());
            }
            if("2".equals(physicalRatioVoList.get(i).getSched())){
                sched.put("gq",sched.get("gq") + physicalRatioVoList.get(i).getCount());
            }
        }
//        physicalRatioVoList.forEach( physicalRatio -> {
//            if ("1".equals(physicalRatio.getSched()) || "3".equals(physicalRatio.getSched())) {
//                sched.put("un", sched.get("un") + physicalRatio.getCount());
//            }
//            if ("0".equals(physicalRatio.getSched()) || "2".equals(physicalRatio.getSched()) || "4".equals(physicalRatio.getSched())) {
//                sched.put("ed", sched.get("ed") + physicalRatio.getCount());
//            }
//        }
//        );
        return ResultInfo.success(sched);
    }

    @RequestMapping("/yearListPhysicalRatioCheckedByOrg")
    public ResultInfo yearListPhysicalRatioCheckedByOrg() {
        SysUser user = this.sysUserService.getUser();
        String org_code = user.getOrg_code();
        List<String> yearList = this.personSubscribeRecordService.getYearListPhysicalRatioCheckedByOrg(org_code);
        if (yearList == null) {
            ArrayList<String> collect = new ArrayList<>();
            collect.add(0, "暂无");
            return ResultInfo.success(collect);
        }
        return ResultInfo.success(yearList);
    }

    @RequestMapping("/getEmployingInfoListCheckedByOrg")
    public ResultInfo employingInfoListCheckedByOrg() {
        SysUser user = this.sysUserService.getUser();
        String org_code = user.getOrg_code();
        List<EmployingInfo> employingInfo = employingInfoService.getEmployingInfoListCheckedByOrg(org_code);
        return ResultInfo.success(employingInfo);
    }

    /**
     * @Description: 折线图
     * @param: [code]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/29 
     * @time: 20:05
     */
    @RequestMapping("/eChat")
    public ResultInfo eChat() {
        return ResultInfo.success(personSubscribeRecordService.eChat());
    }

    /**
     * @Description: 折线图医保端
     * @param: [code]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/29
     * @time: 20:05
     */
    @RequestMapping("/eChatYB")
    public ResultInfo eChatYB() {
        return ResultInfo.success(personSubscribeRecordService.eChatYB());
    }


}
