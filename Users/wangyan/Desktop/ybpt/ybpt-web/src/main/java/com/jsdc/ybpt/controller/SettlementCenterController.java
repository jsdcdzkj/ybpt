package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.service.PsnFixMainDetlDService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*结算中心
* Author wzn
* Date 2022/6/13 16:13
*/
@RestController
@RequestMapping("/settlementCenter")
public class SettlementCenterController {
    @Autowired
    private PsnFixMainDetlDService psnFixMainDetlDService ;
    @Autowired
    private SysDictService sysDictService ;


    /**
    *人员定点登记信息查询
    * Author wzn
    * Date 2022/6/13 16:13
    */
    @PostMapping("/selectPsnList")
    public ResultInfo selectPsnList(@RequestBody PsnFixMainDetlDVo psnFixMainDetlDVo) {
        return ResultInfo.success(psnFixMainDetlDService.selectPsnList(psnFixMainDetlDVo));
    }


    /**
    *定点医疗机构就诊信息查询
    * Author wzn
    * Date 2022/6/14 14:14
    */
    @PostMapping("/consultationInfo")
    public ResultInfo selectPsnList(@RequestBody MdtrtDVo mdtrtDVo) {
        return ResultInfo.success(psnFixMainDetlDService.consultationInfo(mdtrtDVo));
    }


    /**
    *费用明细查询列表
    * Author wzn
    * Date 2022/6/14 16:34
    */
    @PostMapping("/chargeDetails")
    public ResultInfo selectPsnList(@RequestBody FeeListDVo feeListDVo ) {
        return ResultInfo.success(psnFixMainDetlDService.chargeDetails(feeListDVo));
    }

    /**
    *医疗保障基金结算清单信息查询
    * Author wzn
    * Date 2022/6/15 9:01
    */
    @PostMapping("/listInformation")
    public ResultInfo listInformation(@RequestBody MdcsFundSetlListVo mdcsFundSetlListVo ) {
        Page<MdcsFundSetlListVo> mdcsFundSetlListVoPage = psnFixMainDetlDService.listInformation(mdcsFundSetlListVo) ;
        if(CollectionUtils.isNotEmpty(mdcsFundSetlListVoPage.getRecords())){
            for(MdcsFundSetlListVo m:mdcsFundSetlListVoPage.getRecords()){
                if(!"".equals(m.getInsutype()) && null != m.getInsutype()){
                    SysDict sysDict2 = new SysDict() ;
                    sysDict2.setDict_type("INSUTYPE");
                    sysDict2.setValue(m.getInsutype());
                    SysDict sysDict3 = sysDictService.selectByValue(sysDict2) ;
                    if(null != sysDict3){
                        m.setInsutype(sysDict3.getLabel());
                    }
                }
            }
        }
        return ResultInfo.success(mdcsFundSetlListVoPage);
    }

    /**
    *住院病案首页信息查询
    * Author wzn
    * Date 2022/6/15 12:04
    */
    @PostMapping("/beInHospitalInfo")
    public ResultInfo beInHospitalInfo(@RequestBody IptMedcasBasVo iptMedcasBasVo) {
        Page<IptMedcasBasVo> iptMedcasBasVoPage =psnFixMainDetlDService.beInHospitalInfo(iptMedcasBasVo) ;
        if(CollectionUtils.isNotEmpty(iptMedcasBasVoPage.getRecords())){
            for(IptMedcasBasVo m:iptMedcasBasVoPage.getRecords()){
                if(!"".equals(m.getInsutype()) && null != m.getInsutype()){
                    SysDict sysDict2 = new SysDict() ;
                    sysDict2.setDict_type("INSUTYPE");
                    sysDict2.setValue(m.getInsutype());
                    SysDict sysDict3 = sysDictService.selectByValue(sysDict2) ;
                    if(null != sysDict3){
                        m.setInsutype(sysDict3.getLabel());
                    }
                }
            }
        }
        return ResultInfo.success(iptMedcasBasVoPage);
    }


    /**
    *医疗拨付信息查询
    * Author wzn
    * Date 2022/6/15 16:21
    */
    @PostMapping("/medDfrDInfo")
    public ResultInfo beInHospitalInfo(@RequestBody MedDfrDVo medDfrDVo) {
        Page<MedDfrDVo> medDfrDVoPage = psnFixMainDetlDService.medDfrDInfo(medDfrDVo) ;
        if(CollectionUtils.isNotEmpty(medDfrDVoPage.getRecords())){
            for(MedDfrDVo m:medDfrDVoPage.getRecords()){
                if(!"".equals(m.getInsutype()) && null != m.getInsutype()){
                    SysDict sysDict2 = new SysDict() ;
                    sysDict2.setDict_type("INSUTYPE");
                    sysDict2.setValue(m.getInsutype());
                    SysDict sysDict3 = sysDictService.selectByValue(sysDict2) ;
                    if(null != sysDict3){
                        m.setInsutype(sysDict3.getLabel());
                    }
                }
            }
        }
        return ResultInfo.success(medDfrDVoPage);
    }


    /**
    *异地就医信息查询
    * Author wzn
    * Date 2022/6/16 11:59
    */
    @PostMapping("/outmedMdtrt")
    public ResultInfo outmedMdtrt(@RequestBody OutmedMdtrtVo outmedMdtrtVo) {
        return ResultInfo.success(psnFixMainDetlDService.outmedMdtrt(outmedMdtrtVo));
    }

    /**
    *异地就医费用明细
    * Author wzn
    * Date 2022/6/16 17:26
    */
    @PostMapping("/outmedFeeList")
    public ResultInfo outmedFeeList(@RequestBody OutmedFeeListVo outmedFeeListVo) {
        return ResultInfo.success(psnFixMainDetlDService.outmedFeeList(outmedFeeListVo));
    }

    /**
    *病理检查报告信息
    * Author wzn
    * Date 2022/6/17 16:30
    */
    @PostMapping("/reportInfo")
    public ResultInfo reportInfo(@RequestBody RpotInfoVo rpotInfoVo) {
        return ResultInfo.success(psnFixMainDetlDService.reportInfo(rpotInfoVo));
    }

    /**
    *结算信息查询
    * Author wzn
    * Date 2022/6/20 15:25
    */
    @PostMapping("/setlList")
    public ResultInfo reportInfo(@RequestBody SetlDVo setlDVo) {
        return ResultInfo.success(psnFixMainDetlDService.setlList(setlDVo));
    }


    /**
     *定点医疗机构门急诊诊疗处方信息查询
     * Author wzn
     * Date 2022/6/20 15:25
     */
    @PostMapping("/rxInfoList")
    public ResultInfo reportInfo(@RequestBody RxInfoVo rxInfoVo) {
        return ResultInfo.success(psnFixMainDetlDService.rxInfoList(rxInfoVo));
    }


    /**
     *（临床检查报告影像信息查询
     * Author wzn
     * Date 2022/6/20 15:25
     */
    @PostMapping("/imgList")
    public ResultInfo rxInfoList(@RequestBody ImgInfoVo imgInfoVo) {
        return ResultInfo.success(psnFixMainDetlDService.imgList(imgInfoVo));
    }

}
