package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.capitalSettlement.QsInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.LoanApplication;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_query.MedinsInfoB;
import com.jsdc.ybpt.model_query.RtalPhacB;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.agreementsignVo.FixmedinsBVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


/**
*机构
* Author wzn
* Date 2022/5/18 11:39
*/
@RestController
@RequestMapping("/medins")
public class FixmedinsBController {
    @Autowired
    private FixmedinsBService fixmedinsBService;

    @Autowired
    private SysUserService sysUserService ;



    /**
    *同步医疗机构信息
    * Author wzn
    * Date 2022/5/18 14:49
    */
    @PostMapping("/selectByCode")
    public ResultInfo selectByCode(@RequestBody FixmedinsB fixmedinsB){
        return ResultInfo.success(fixmedinsBService.selectByCode(fixmedinsB).get(0));
    }

    /**
    *贷款申请查询
    * Author wzn
    * Date 2022/7/6 14:45
    */
    @PostMapping("/dkApplySelect")
    public ResultInfo selectByCode(){
        SysUser sysUser = sysUserService.getUser() ;
        LoanApplication loanApplication  = new LoanApplication() ;
        loanApplication.setFixmedins_code(sysUser.getOrg_code());
        //2:医疗机构 3:零售药店
        LoanApplication loanApplication1 = null ;
        if("2".equals(sysUser.getUser_type())){
            loanApplication1 =   fixmedinsBService.dkApplySelect(loanApplication);

        }else if("3".equals(sysUser.getUser_type())){
            loanApplication1 = fixmedinsBService.ydApplyInfoList(loanApplication) ;
        }
        return ResultInfo.success(loanApplication1);
    }

    /**
    *同步所有医疗机构数据到本地
    * Author wzn
    * Date 2022/5/19 15:28
    */
    @PostMapping("/synchronousAll")
    public ResultInfo synchronousAll(){
        FixmedinsB fixmedinsB = new FixmedinsB() ;
        List<FixmedinsB> fixmedinsBS = fixmedinsBService.selectByCode(fixmedinsB) ;
        for(FixmedinsB f:fixmedinsBS){
            //校验是否唯一
            Boolean isOnly = fixmedinsBService.checkOnly(f) ;

            //查详情表更新到本地
            //定点医疗服务机构类型 1:机构 2：药店
            // fixmedins_type;
            if("2".equals(f.getFixmedins_type())){
                RtalPhacB rtalPhacB = new RtalPhacB();
                rtalPhacB.setRtal_phac_code(f.getFixmedins_code());
                RtalPhacB rtalPhacB1 = fixmedinsBService.selectRtalPhacByCode(rtalPhacB) ;
                if(null != rtalPhacB1){
                    f.setPhac_chan_type(rtalPhacB1.getPhac_chan_type());
                    //地址
                    f.setAddress(rtalPhacB1.getAddr());
                    //统一社会信用代码
                    f.setUscc(rtalPhacB1.getUscc());
                    //医保区划
                    f.setPossession(rtalPhacB1.getAdmdvs());
                    //许可证
                    f.setLicense(rtalPhacB1.getDrug_biz_lic_no());
                    //法人
                    f.setLegrep_name(rtalPhacB1.getLegrep_name());
                    //联系电话
                    f.setLegrep_mobile(rtalPhacB1.getTel());
                }
            }else  if("1".equals(f.getFixmedins_type())){
                MedinsInfoB medinsInfoB = new MedinsInfoB() ;
                medinsInfoB.setMedins_code(f.getFixmedins_code());
                MedinsInfoB medinsInfoB1 = fixmedinsBService.selectMedinsInfoByCode(medinsInfoB) ;
                if(null != medinsInfoB1){
                    f.setMedins_type(medinsInfoB1.getMedins_type());
                    f.setMedins_type_name(medinsInfoB1.getMedins_type_name());
                    //地址
                    f.setAddress(medinsInfoB1.getAddr());
                    //社会统一信用代码
                    f.setUscc(medinsInfoB1.getUscc());
                    //经营性质 注:医保给Excel文件统一刷,故不同步此字段
//                    f.setBiznet(medinsInfoB1.getBiznat());
                    //医保区划
                    f.setPossession(medinsInfoB1.getAdmdvs());
                    //医疗机构类型
                    f.setMedins_type(medinsInfoB1.getMedins_type());
                    //法人
                    f.setLegrep_name(medinsInfoB1.getLegrep_name());
                    //许可证
                    f.setLicense(medinsInfoB1.getMedins_prac_lic_regno());
                }
            }
            if(!isOnly){
                QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>() ;
                queryWrapper.eq("fixmedins_code",f.getFixmedins_code()) ;
                f.update(queryWrapper) ;
            }else {
                f.setCreateTime(new Date());
                f.setIs_del("0");
                f.insert() ;
            }
        }
        return ResultInfo.success();
    }

    /**
    *新增医疗机构
    * Author wzn
    * Date 2022/5/18 14:49
    */
    @PostMapping("/add")
    public ResultInfo add(@RequestBody FixmedinsB fixmedinsB){
        //校验是否唯一
        Boolean isOnly = fixmedinsBService.checkOnly(fixmedinsB) ;
        if(!isOnly){
            throw new CustomException("该机构已存在，禁止重复添加！") ;
        }
        fixmedinsBService.add(fixmedinsB) ;
        return ResultInfo.success();
    }

    @PostMapping("/editData")
    public ResultInfo editData(@RequestBody FixmedinsB fixmedinsB){
        fixmedinsBService.editData(fixmedinsB) ;
        return ResultInfo.success();
    }


    /**
    *医疗机构列表
    * Author wzn
    * Date 2022/5/18 15:36
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody FixmedinsB fixmedinsB){
        return ResultInfo.success(fixmedinsBService.selectList(fixmedinsB));
    }

    /**
    *医疗机构下拉数据接口
    * Author wzn
    * Date 2022/5/19 16:44
    */
    @PostMapping("/dropDownData")
    public ResultInfo dropDownData(String type){
        return ResultInfo.success(fixmedinsBService.dropDownData(type));
    }

    @RequestMapping("getAll.do")
    public ResultInfo getAll(@RequestBody FixmedinsB fixmedinsB){
        return ResultInfo.success(fixmedinsBService.getAll(fixmedinsB));
    }

    @RequestMapping("editAggrementLevel.do")
    public ResultInfo editAggrementLevel(@RequestBody FixmedinsBVo vo){
        return fixmedinsBService.editAggrementLevel(vo);
    }

    /**
     * 分页查询
     * @param vo
     * @return
     */
    @RequestMapping("getPage.do")
    public ResultInfo getPage(@RequestBody FixmedinsBVo vo){
        return fixmedinsBService.getPage(vo);
    }

    /**
     *手动更新机构部分信息(一次性接口)
     * Author wzn
     * Date 2024/2/28 14:56
     */
    @RequestMapping("/importData")
    public ResultInfo importData(MultipartFile file) {
        fixmedinsBService.importData(file);
        return ResultInfo.success();
    }


}
