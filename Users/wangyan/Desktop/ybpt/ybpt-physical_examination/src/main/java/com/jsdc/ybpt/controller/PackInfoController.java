package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.model_check.PackInfo;
import com.jsdc.ybpt.service.PackInfoService;
import com.jsdc.ybpt.service.PersonSubscribeRecordService;
import com.jsdc.ybpt.vo.OrganizationInfoVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/packInfo")
public class PackInfoController {
    @Autowired
    private PackInfoService service;

    @Autowired
    private PersonSubscribeRecordService personSubscribeRecordService;

    /**
     * @Description: 1.根据packYear和packSource查询该年度不同来源的组织机构
     * 2.根据packYear查询所有符合该年份的组织机构和医保局列表
     * @param: [packYear, packSource]
     * @return: ResultInfo<List < PackInfo>>
     * @author: yc
     */
    @ResponseBody
    @GetMapping("/getOrgList")
    public ResultInfo getOrgList() {
        List<OrganizationInfo> orgListPack = service.getOrgListPack(new OrganizationInfoVo());
        return ResultInfo.success(orgListPack);
    }

    /**
     * @Description: 1.根据年份packYear、套餐来源packSource和org_id获取符合条件的机构套餐列表
     * 2.根据年份packYear、套餐来源packSource获取医保局通用套餐列表
     * @param: [packYear, packSource, orgId]
     * @return: ResultInfo<List < PackInfo>>
     * @author: yc
     */
    @ResponseBody
    @GetMapping("/getPackInfoList")
    public ResultInfo getPackInfoList(@RequestParam String packYear, @RequestParam String packSource, String orgId) {
        List<PackInfo> packInfoList = service.getPackInfoList(packYear, packSource, orgId);
        return ResultInfo.success(packInfoList);
    }

    @GetMapping("/getMedicalItemListByPackInfoId")
    @ResponseBody
    public ResultInfo getMedicalItemListByPackInfoId(String packInfoId) {
        List<Map<String, String>> itemToMealByPackInfo = this.personSubscribeRecordService.findItemToMealByPackInfoId(packInfoId);
        return ResultInfo.success(itemToMealByPackInfo);
    }
}
