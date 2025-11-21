package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.capitalSettlement.QsInfo;
import com.jsdc.ybpt.capitalSettlement.QsInfoDetails;
import com.jsdc.ybpt.mapper.QsInfoDetailsMapper;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.service.CapitalSettlementService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("capitalSettlement")
public class CapitalSettlementController {
    @Autowired
    private CapitalSettlementService capitalSettlementService ;

    @Autowired
    private SysDictService sysDictService ;
    @Autowired
    private QsInfoDetailsMapper qsInfoDetailsMapper ;

    @Autowired
    private SysUserService sysUserService ;


    /**
    *年度清算数据导入
    * Author wzn
    * Date 2023/1/5 15:10
    */
    @RequestMapping("/importData")
    public ResultInfo importData(QsInfo qsInfo, MultipartFile file) {

        capitalSettlementService.importData(qsInfo,file);
        return ResultInfo.success();
    }

    /**
    *年度清算数据列表
    * Author wzn
    * Date 2023/1/5 16:36
    */
    @PostMapping("/getList")
    public ResultInfo getList(@RequestBody QsInfo qsInfo){
        Page<QsInfo> qsInfoPage = capitalSettlementService.getList(qsInfo) ;
        if(null != qsInfoPage && qsInfoPage.getRecords().size()>0){
            for(QsInfo a:qsInfoPage.getRecords()){
                if(StringUtils.isNotEmpty(a.getAdmdvs())){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("admdvs-area");
                    sysDict.setValue(a.getAdmdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        a.setAdmdvs(sysDict1.getLabel());
                    }
                }

            }
        }
        return ResultInfo.success(qsInfoPage);
    }


    /**
    *年度清算数据删除
    * Author wzn
    * Date 2023/1/5 16:41
    */
    @PostMapping("/delQs")
    public ResultInfo delQs(@RequestBody QsInfo qsInfo) {
        QsInfo qsInfo1 = new QsInfo() ;
        qsInfo1.setId(qsInfo.getId());
        qsInfo1.setIs_del("1");
        qsInfo1.updateById() ;
        //删明细表
        QueryWrapper<QsInfoDetails> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("qs_info_id",qsInfo.getId()) ;
        qsInfoDetailsMapper.delete(queryWrapper) ;
        return ResultInfo.success();
    }


    /**
    *资金清算明细
    * Author wzn
    * Date 2023/1/5 16:47
    */
    @PostMapping("/detailInfo")
    public ResultInfo detailInfo(@RequestBody QsInfoDetails qsInfoDetails) {
        Page<QsInfoDetails> qsInfoDetailsPage = capitalSettlementService.detail(qsInfoDetails) ;
        if(null != qsInfoDetailsPage && qsInfoDetailsPage.getRecords().size()>0){
            for(QsInfoDetails q:qsInfoDetailsPage.getRecords()){
                if("0".equals(q.getType())){
                    q.setType("汇总");
                }else if("1".equals(q.getType())){
                    q.setType("职工门诊");
                }else if("2".equals(q.getType())){
                    q.setType("职工住院");
                }else if("3".equals(q.getType())){
                    q.setType("居民门诊");
                }else if("4".equals(q.getType())){
                    q.setType("居民住院");
                }else if("5".equals(q.getType())){
                    q.setType("伤残门诊");
                }else if("6".equals(q.getType())){
                    q.setType("伤残住院");
                }else if("7".equals(q.getType())){
                    q.setType("职工、灵活就业人员生育");
                }else if("8".equals(q.getType())){
                    q.setType("居民生育");
                }else if("9".equals(q.getType())){
                    q.setType("离休");
                }
            }
        }
        return ResultInfo.success(qsInfoDetailsPage);
    }

    /**
     *资金清算明细单条详情
     * Author wzn
     * Date 2023/1/6 8:39
     */
    @PostMapping("/oneInfo")
    public ResultInfo oneInfo(String id) {
        QsInfoDetails qsInfoDetails1 = capitalSettlementService.oneInfo(id) ;
        return ResultInfo.success(qsInfoDetails1);
    }

    /**
    *资金清算明细单条修改
    * Author wzn
    * Date 2023/1/6 9:36
    */
    @PostMapping("/edit")
    public ResultInfo edit(@RequestBody QsInfoDetails qsInfoDetails) {
        qsInfoDetails.setUpdateTime(new Date());
        qsInfoDetails.setUpdateUser(sysUserService.getUser().getUsername());
        qsInfoDetails.updateById() ;
        return ResultInfo.success();
    }

    /**
     *生成确认书
     * Author wzn
     * Date 2023/1/7 9:14
     */
    @PostMapping("/createConfirming")
    public ResultInfo createConfirming(String id) {
        capitalSettlementService.createConfirming(id) ;
        return ResultInfo.success();
    }

}
