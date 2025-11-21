package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.UnfixedMechanism;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.UnfixedMechanismService;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/unfixedMechanism")
public class UnfixedMechanismController {
    @Autowired
    private UnfixedMechanismService unfixedMechanismService;

    @Autowired
    private SysDictService sysDictService ;


    /**
    *非定点机构新增
    * Author wzn
    * Date 2023/4/10 10:12
    */
    @PostMapping("/addUnfixedMechanism")
    public ResultInfo addUnfixedMechanism(@RequestBody UnfixedMechanism unfixedMechanism) {
        unfixedMechanismService.addUnfixedMechanism(unfixedMechanism);
        return ResultInfo.success();
    }


    /**
    *非定点机构修改
    * Author wzn
    * Date 2023/4/10 10:12
    */
    @PostMapping("/updateUnfixedMechanism")
    public ResultInfo updateCiviWorkerInfo(@RequestBody UnfixedMechanism unfixedMechanism) {
        unfixedMechanismService.updateUnfixedMechanism(unfixedMechanism);
        return ResultInfo.success();
    }

    /**
    *非定点机构删除
    * Author wzn
    * Date 2023/4/10 10:13
    */
    @PostMapping("/delUnfixedMechanism")
    public ResultInfo delUnfixedMechanism(@RequestBody UnfixedMechanism unfixedMechanism) {
        UnfixedMechanism unfixedMechanism1 = new UnfixedMechanism() ;
        unfixedMechanism1.setId(unfixedMechanism.getId());
        unfixedMechanism1.setIs_del("1");
        unfixedMechanismService.updateById(unfixedMechanism1);
        return ResultInfo.success();
    }

    /**
    *非定点机构列表
    * Author wzn
    * Date 2023/4/10 10:15
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody UnfixedMechanism unfixedMechanism){
        Page<UnfixedMechanism> unfixedMechanismPage = unfixedMechanismService.selectList(unfixedMechanism) ;
        if(null != unfixedMechanismPage.getRecords() && unfixedMechanismPage.getRecords().size()>0){
            for(UnfixedMechanism a:unfixedMechanismPage.getRecords()){

                if(StringUtils.isNotEmpty(a.getFix_blng_admdvs())){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("admdvs-area");
                    sysDict.setValue(a.getFix_blng_admdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        a.setFix_blng_admdvs(sysDict1.getLabel());
                    }
                }


                if(StringUtils.isNotEmpty(a.getFixmedins_type())){
                    if("2".equals(a.getFixmedins_type())){
                        a.setFixmedins_type("非定点医疗机构");
                        if ("1".equals(a.getAggrement_lv())) {
                            a.setAggrement_lv("一级");
                        } else if ("2".equals(a.getAggrement_lv())) {
                            a.setAggrement_lv("二级");
                        } else if ("3".equals(a.getAggrement_lv())) {
                            a.setAggrement_lv("三级");
                        } else if ("9".equals(a.getAggrement_lv())) {
                            a.setAggrement_lv("未定级");
                        }
                    }else if("3".equals(a.getFixmedins_type())){
                        a.setFixmedins_type("非定点零售药店");
                        if ("4".equals(a.getAggrement_lv())) {
                            a.setAggrement_lv("A级");
                        } else if ("5".equals(a.getAggrement_lv())) {
                            a.setAggrement_lv("B级");
                        } else if ("6".equals(a.getAggrement_lv())) {
                            a.setAggrement_lv("C级");
                        }
                    }
                }

                if(StringUtils.isNotEmpty(a.getManage_quality())){
                    if("1".equals(a.getManage_quality())){
                        a.setManage_quality("营利性");
                    }else if("2".equals(a.getManage_quality())){
                        a.setManage_quality("民办非营利");
                    }else if("3".equals(a.getManage_quality())){
                        a.setManage_quality("政府非营利");
                    }
                }
            }
        }
        return ResultInfo.success(unfixedMechanismPage);
    }


    /**
    *非定点机构详情
    * Author wzn
    * Date 2023/4/10 10:15
    */
    @PostMapping("/info")
    public ResultInfo info(String id){
        UnfixedMechanism unfixedMechanism = unfixedMechanismService.info(id) ;
        return ResultInfo.success(unfixedMechanism);
    }







}
