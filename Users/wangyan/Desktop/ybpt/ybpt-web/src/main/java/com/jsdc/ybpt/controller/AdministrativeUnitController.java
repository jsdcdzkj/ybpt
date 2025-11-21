package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.service.AdministrativeUnitService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/administrative_unit")
public class AdministrativeUnitController {
    @Autowired
    private AdministrativeUnitService administrativeUnitService;

    @Autowired
    private SysDictService sysDictService ;

    /**
     * 行政单位新增接口
     * Author wzn
     * Date 2022/5/24 10:08
     */
    @PostMapping("/addAu")
    public ResultInfo addCiviWorkerInfo(@RequestBody AdministrativeUnit administrativeUnit) {
        administrativeUnitService.addCiviWorkerInfo(administrativeUnit);
        return ResultInfo.success();
    }


    /**
    *行政单位修改接口
    * Author wzn
    * Date 2022/5/24 10:14
    */
    @PostMapping("/updateAu")
    public ResultInfo updateCiviWorkerInfo(@RequestBody AdministrativeUnit administrativeUnit) {
        administrativeUnitService.updateCiviWorkerInfo(administrativeUnit);
        return ResultInfo.success();
    }

    /**
    *行政单位删除接口
    * Author wzn
    * Date 2022/5/24 10:18
    */
    @PostMapping("/delAu")
    public ResultInfo delCiviWorkerInfo(@RequestBody AdministrativeUnit administrativeUnit) {
        AdministrativeUnit administrativeUnit1 = new AdministrativeUnit() ;
        administrativeUnit1.setId(administrativeUnit.getId());
        administrativeUnit1.setIs_del("1");
        administrativeUnitService.updateById(administrativeUnit1);
        return ResultInfo.success();
    }

    /**
    *行政单位列表接口
    * Author wzn
    * Date 2022/5/24 10:47
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody AdministrativeUnit administrativeUnit){
        Page<AdministrativeUnit> administrativeUnitList = administrativeUnitService.selectList(administrativeUnit) ;
        if(null != administrativeUnitList && administrativeUnitList.getRecords().size()>0){
            for(AdministrativeUnit a:administrativeUnitList.getRecords()){
                a.setAdmdvsCode(a.getAdmdvs());
                SysDict sysDict = new SysDict() ;
                sysDict.setDict_type("ADMDVS");
                sysDict.setValue(a.getAdmdvs());
                SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                if(null != sysDict1){
                    a.setAdmdvs(sysDict1.getLabel());
                }

            }
        }
        return ResultInfo.success(administrativeUnitList);
    }

    @PostMapping("/selectListAll")
    public ResultInfo selectListAll(){
        List<AdministrativeUnit> administrativeUnitList = administrativeUnitService.selectListAll() ;
        return ResultInfo.success(administrativeUnitList);
    }


    /**
    *行政单位详情接口
    * Author wzn
    * Date 2022/5/25 16:51
    */
    @PostMapping("/info")
    public ResultInfo info(String id){
        AdministrativeUnit administrativeUnit = administrativeUnitService.info(id) ;
        return ResultInfo.success(administrativeUnit);
    }





}
