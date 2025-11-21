package com.jsdc.ybpt.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.*;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.PeopleCount;
import com.jsdc.ybpt.vo.PersonSubscribeRecordVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@RequestMapping("/civi")
public class CivilworkerInfoController {
    @Autowired
    private CivilworkerInfoService civilworkerInfoService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CivilworkerVirfyService civilworkerVirfyService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private EmployingInfoService employingInfoService;

    @Autowired
    private PersonSubscribeRecordService personSubscribeRecordService;

    @Autowired
    private AdministrativeUnitService administrativeUnitService;

@Autowired
private DeptInfoService deptInfoService;


    /**
     * 公务员新增接口
     * Author wzn
     * Date 2022/5/24 10:08
     */
    @PostMapping("/addCiviWorkerInfo")
    public ResultInfo addCiviWorkerInfo(@RequestBody CivilworkerInfo civilworkerInfo) {
        SysUser sysUser = sysUserService.getUser();

        if (sysUser.getUser_type().equals("1")) {
            //校验是否已经添加过此人
            boolean isAdd = civilworkerInfoService.check(civilworkerInfo.getCertno());
            if (isAdd) {
                throw new CustomException("此用户已存在！");
            }
            civilworkerInfo.setInfo_source("2");

            /**
             * fix bug 公务员预约记录一开始只展示有效体检记录，有体检机构反馈公务员体检后去世了，单位删除了公务员信息体检机构上传不了体检报告，故预约记录展示已删除的公务员预约记录。
             * 展示已删除的公务员预约记录后 假如公务员有多次删除记录 预约记录会展示多条 。所以公务员换单位先删除原单位公务员信息 ，新单位在加公务员信息 就要启用原来公务员记录
             * 要保证同一个身份证只有一条公务员信息
             *
             * 用人单位端新加的公务员 要医保审批完以后
             * 批量审核
             * 单个审核
             **/
            Map<String,Object> map = changeCivilworkerInfo(civilworkerInfo);
            boolean flag = (boolean)map.get("isChange");
            if(flag){
                return (ResultInfo)map.get("resultInfo");
            }


            civilworkerInfoService.addCiviWorkerInfo(civilworkerInfo);

        } else if ("4".equals(sysUser.getUser_type())) {
            boolean isCheck = civilworkerVirfyService.check(civilworkerInfo.getCertno(), "1");
            if (isCheck) {
                throw new CustomException("此用户已在审核中！");
            }
            boolean isAdd = civilworkerInfoService.check(civilworkerInfo.getCertno());
            if (isAdd) {
                throw new CustomException("此用户已存在！");
            }
            CivilworkerVirfy civilworkerVirfy = new CivilworkerVirfy();
            BeanUtils.copyProperties(civilworkerInfo, civilworkerVirfy);
            civilworkerVirfy.setCw_id(civilworkerInfo.getId());
            civilworkerVirfy.setVerify_type("1");
            civilworkerVirfy.setInfo_source("3");
            civilworkerVirfy.setEmp_code(sysUser.getOrg_code());
            civilworkerVirfy.setId("");
            civilworkerVirfy.setBirthday(civilworkerInfo.getBirthday());
            civilworkerInfo.setCardType(civilworkerVirfy.getCardType());
            civilworkerVirfy.setApply_id(sysUser.getOrg_code());
//            EmployingInfo employingInfo = employingInfoService.selectByEmpCode();
//            if("".equals(employingInfo.getParentOrgCode())){
//                throw  new CustomException("禁止重复添加！") ;
//            }
//            civilworkerVirfy.setParentCode(employingInfo.getParentOrgCode());
            civilworkerVirfyService.addCiviVirfy(civilworkerVirfy);
        }

        return ResultInfo.success();
    }


    /**
     * 公务员修改接口
     * Author wzn
     * Date 2022/5/24 10:14
     */
    @PostMapping("/updateCiviWorkerInfo")
    public ResultInfo updateCiviWorkerInfo(@RequestBody CivilworkerInfo civilworkerInfo) {

        if(StringUtils.isNotEmpty(civilworkerInfo.getIs_dept())){
            if (StringUtils.isEmpty(civilworkerInfo.getDept_id())){
                civilworkerInfo.setDept_id("");
            }
            civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo);
            return ResultInfo.success();
        }

         SysUser sysUser = sysUserService.getUser();
        if (sysUser.getUser_type().equals("1")) {
            civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo);
        } else if ("4".equals(sysUser.getUser_type())) {
//            boolean isCheck = civilworkerVirfyService.check(civilworkerInfo.getCertno(), "3");
//            boolean isAdd = civilworkerInfoService.check(civilworkerInfo.getCertno());
//            if (isAdd) {
//                throw new CustomException("此用户已存在！");
//            }
            boolean isCheck = civilworkerVirfyService.check(civilworkerInfo.getCertno(), "3");
            if (isCheck) {
                throw new CustomException("此用户已在审核中！");
            }
            CivilworkerInfo civilworkerInfo1 = new CivilworkerInfo();
            civilworkerInfo1.setIs_del("1");
            civilworkerInfo1.setId(civilworkerInfo.getId());
            civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo1);

            CivilworkerVirfy civilworkerVirfy = new CivilworkerVirfy();
            BeanUtils.copyProperties(civilworkerInfo, civilworkerVirfy);
            civilworkerVirfy.setCw_id(civilworkerInfo.getId());
            civilworkerVirfy.setVerify_type("3");
            civilworkerVirfy.setId("");
            civilworkerVirfy.setCardType(civilworkerVirfy.getCardType());
            civilworkerVirfy.setApply_id(sysUser.getOrg_code());
            EmployingInfo employingInfo = employingInfoService.selectByEmpCode();
            civilworkerVirfy.setParentCode(employingInfo.getAdmdvs());
            civilworkerVirfy.setBirthday(civilworkerInfo.getBirthday());
            civilworkerVirfyService.addCiviVirfy(civilworkerVirfy);
        }

        return ResultInfo.success();
    }

    /**
     * 公务员删除接口
     * Author wzn
     * Date 2022/5/24 10:18
     */
    @PostMapping("/delCiviWorkerInfo")
    public ResultInfo delCiviWorkerInfo(@RequestBody CivilworkerInfo civilworkerInfo) {

        SysUser sysUser = sysUserService.getUser();
        boolean isCheck = civilworkerVirfyService.check(civilworkerInfo.getCertno(), "2");
        if (isCheck) {
            throw new CustomException("审核中，禁止重复操作！");
        }
        if (sysUser.getUser_type().equals("1")) {
            CivilworkerInfo civilworkerInfo1 = new CivilworkerInfo();
            civilworkerInfo1.setIs_del("1");
            civilworkerInfo1.setId(civilworkerInfo.getId());
            civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo1);
        } else if ("4".equals(sysUser.getUser_type())) {


            CivilworkerVirfy civilworkerVirfy = new CivilworkerVirfy();
            BeanUtils.copyProperties(civilworkerInfo, civilworkerVirfy);
            civilworkerVirfy.setCw_id(civilworkerInfo.getId());
            civilworkerVirfy.setVerify_type("2");
            civilworkerVirfy.setApply_id(sysUser.getOrg_code());
            civilworkerVirfy.setId(IdUtil.simpleUUID());
            civilworkerVirfy.setCardType(civilworkerVirfy.getCardType());
            civilworkerVirfy.setBirthday(civilworkerInfo.getBirthday());
            civilworkerVirfyService.addCiviVirfy(civilworkerVirfy);
        }
        return ResultInfo.success();
    }

    /**
    *公务员列表接口
    * Author wzn
    * Date 2022/5/24 10:47
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody CivilworkerInfo civilworkerInfo){
        Page<CivilworkerInfo> page = civilworkerInfoService.selectList(civilworkerInfo) ;
        List<CivilworkerInfo> civilworkerInfos = page.getRecords() ;
        if(null != civilworkerInfos && civilworkerInfos.size() >0){
            for(CivilworkerInfo c:civilworkerInfos){
                c.setSex(IdCardNumberMethod.getSexFromIdCard(c.getCertno())+"");
//                c.setAge(IdCardNumberMethod.getAgeForIdcard(c.getCertno())+"");

                if(!"".equals(c.getEmp_type()) && null != c.getEmp_type()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("EMP_TYPE");
                    sysDict.setValue(c.getEmp_type());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setEmp_type(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getInsutype()) && null != c.getInsutype()){
                    SysDict sysDict2 = new SysDict() ;
                    sysDict2.setDict_type("INSUTYPE");
                    sysDict2.setValue(c.getInsutype());
                    SysDict sysDict3 = sysDictService.selectByValue(sysDict2) ;
                    if(null != sysDict3){
                        c.setInsutype(sysDict3.getLabel());
                    }
                }

                if(!"".equals(c.getInsu_state()) && null != c.getInsu_state()){
                    SysDict sysDict4 = new SysDict() ;
                    sysDict4.setDict_type("PSN_INSU_STAS");
                    sysDict4.setValue(c.getInsu_state());
                    SysDict sysDict5 = sysDictService.selectByValue(sysDict4) ;
                    if(null != sysDict5){
                        c.setInsu_state(sysDict5.getLabel());
                    }
                }

                if(!"".equals(c.getInsu_admdvs()) && null != c.getInsu_admdvs()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("ADMDVS");
                    sysDict.setValue(c.getInsu_admdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setInsu_admdvs(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getAdmdvs()) && null != c.getAdmdvs()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("ADMDVS");
                    sysDict.setValue(c.getAdmdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setAdmdvs(sysDict1.getLabel());
                    }
                }


                if(!"".equals(c.getOutside_flag())){
                    if("1".equals(c.getOutside_flag())){
                        c.setOutside_flag("是");
                    }else{
                        c.setOutside_flag("否");
                    }
                }

                if(!"".equals(c.getDeath_flag()) && null != c.getDeath_flag()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("SURV_STAS");
                    sysDict.setValue(c.getDeath_flag());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setDeath_flag(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getQualifications())){
                    if("0".equals(c.getQualifications())){
                        c.setQualifications("无");
                    }else{
                        c.setQualifications("有");
                    }
                }

            }
        }
        return ResultInfo.success(page);
    }


    /**
    *审核列表接口
    * Author wzn
    * Date 2022/5/25 18:53
    */
    @PostMapping("/shList")
    public ResultInfo shList(@RequestBody CivilworkerVirfy civilworkerVirfy){
        Page<CivilworkerVirfy> page = civilworkerVirfyService.selectList(civilworkerVirfy) ;
        if(null != page.getRecords() && page.getRecords().size()>0){
            for(CivilworkerVirfy c:page.getRecords()){
                c.setSex(IdCardNumberMethod.getSexFromIdCard(c.getCertno())+"");
//                c.setAge(IdCardNumberMethod.getAgeForIdcard(c.getCertno())+"");
                if(!"".equals(c.getEmp_type()) && null != c.getEmp_type()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("EMP_TYPE");
                    sysDict.setValue(c.getEmp_type());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setEmp_type(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getInsutype()) && null != c.getInsutype()){
                    SysDict sysDict2 = new SysDict() ;
                    sysDict2.setDict_type("INSUTYPE");
                    sysDict2.setValue(c.getInsutype());
                    SysDict sysDict3 = sysDictService.selectByValue(sysDict2) ;
                    if(null != sysDict3){
                        c.setInsutype(sysDict3.getLabel());
                    }
                }

                if(!"".equals(c.getInsu_state()) && null != c.getInsu_state()){
                    SysDict sysDict4 = new SysDict() ;
                    sysDict4.setDict_type("PSN_INSU_STAS");
                    sysDict4.setValue(c.getInsu_state());
                    SysDict sysDict5 = sysDictService.selectByValue(sysDict4) ;
                    if(null != sysDict5){
                        c.setInsu_state(sysDict5.getLabel());
                    }
                }
                if(!"".equals(c.getInsu_admdvs()) && null != c.getInsu_admdvs()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("ADMDVS");
                    sysDict.setValue(c.getInsu_admdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setInsu_admdvs(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getAdmdvs()) && null != c.getAdmdvs()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("ADMDVS");
                    sysDict.setValue(c.getAdmdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setAdmdvs(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getOutside_flag())){
                    if("0".equals(c.getOutside_flag())){
                        c.setOutside_flag("否");
                    }else{
                        c.setOutside_flag("是");
                    }
                }

                if(!"".equals(c.getQualifications())){
                    if("0".equals(c.getQualifications())){
                        c.setQualifications("无");
                    }else{
                        c.setQualifications("有");
                    }
                }
            }
        }
        return ResultInfo.success(page);
    }


    /**
    *审核接口
    * Author wzn
    * Date 2022/5/25 19:26
    */
    @PostMapping("/check")
    public ResultInfo check(@RequestBody CivilworkerVirfy civilworkerVirfy){
        civilworkerVirfy.setVerify_time(new Date());
        civilworkerVirfy.updateById() ;
        CivilworkerInfo civilworkerInfo = new CivilworkerInfo() ;
        CivilworkerVirfy civilworkerVirfy1 = civilworkerVirfyService.getById(civilworkerVirfy.getId()) ;
        if(null != civilworkerVirfy1.getDept_id() && !"".equals(civilworkerVirfy1.getDept_id()) ){
            DeptInfo deptInfo=deptInfoService.getById(civilworkerVirfy1.getDept_id());
            civilworkerInfo.setDept_name(deptInfo.getDept_name());
        }

        //新增还是删除
        if("1".equals(civilworkerVirfy1.getVerify_type())){
            if("1".equals(civilworkerVirfy.getStatus())){
                //新增操作

                BeanUtils.copyProperties(civilworkerVirfy1,civilworkerInfo );
                civilworkerInfo.setId("");
                civilworkerInfo.setAdministrative_unit(civilworkerVirfy1.getEmp_code());
                boolean isAdd = civilworkerInfoService.check(civilworkerInfo.getCertno());
                if (isAdd) {
                    throw new CustomException("此用户已存在！");
                }

                /**
                 * fix bug 公务员预约记录一开始只展示有效体检记录，有体检机构反馈公务员体检后去世了，单位删除了公务员信息体检机构上传不了体检报告，故预约记录展示已删除的公务员预约记录。
                 * 展示已删除的公务员预约记录后 假如公务员有多次删除记录 预约记录会展示多条 。所以公务员换单位先删除原单位公务员信息 ，新单位在加公务员信息 就要启用原来公务员记录
                 * 要保证同一个身份证只有一条公务员信息
                 *
                 * 用人单位端新加的公务员 要医保审批完以后
                 * 批量审核
                 * 单个审核
                 **/
                Map<String,Object> map = changeCivilworkerInfo(civilworkerInfo);
                boolean flag = (boolean)map.get("isChange");
                if(flag){
                    return (ResultInfo)map.get("resultInfo");
                }

               civilworkerInfoService.addCiviWorkerInfo(civilworkerInfo);
            }

        }else if("2".equals(civilworkerVirfy1.getVerify_type())) {
            if("1".equals(civilworkerVirfy.getStatus())){
//                BeanUtils.copyProperties(civilworkerVirfy1,civilworkerInfo );
                civilworkerInfo.setIs_del("1");
                civilworkerInfo.setId(civilworkerVirfy1.getCw_id());
                civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo);
            }
        }else if("3".equals(civilworkerVirfy1.getVerify_type())) {
            if("1".equals(civilworkerVirfy.getStatus())){
                //新增操作
                BeanUtils.copyProperties(civilworkerVirfy1,civilworkerInfo );
                civilworkerInfo.setId("");
                civilworkerInfoService.addCiviWorkerInfo(civilworkerInfo);
            }else {
                civilworkerInfo.setIs_del("0");
                civilworkerInfo.setId(civilworkerVirfy1.getCw_id());
                civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo);
            }

        }
        return ResultInfo.success();
    }

    /**
     *审核接口
     * Author wzn
     * Date 2022/5/25 19:26
     */
    @PostMapping("/checkAll")
    public ResultInfo checkAll(@RequestBody CivilworkerVirfy civilworkerVirfy){
        if(null == civilworkerVirfy.getIds() || civilworkerVirfy.getIds().size() ==0){
            return ResultInfo.error("请先选择要审批的公务员信息");
        }
        LambdaQueryWrapper<CivilworkerVirfy> wrapper=new LambdaQueryWrapper<>();
        wrapper.in(CivilworkerVirfy::getId,civilworkerVirfy.getIds());
        civilworkerVirfy.setVerify_time(new Date());
        civilworkerVirfy.update(wrapper) ;
        CivilworkerInfo civilworkerInfo = new CivilworkerInfo() ;
        List<CivilworkerVirfy> civilworkerVirfys = civilworkerVirfyService.listByIds(civilworkerVirfy.getIds()) ;
        for (CivilworkerVirfy civilworkerVirfy1 : civilworkerVirfys) {
            if(null != civilworkerVirfy1.getDept_id() && !"".equals(civilworkerVirfy1.getDept_id()) ){
                DeptInfo deptInfo=deptInfoService.getById(civilworkerVirfy1.getDept_id());
                civilworkerInfo.setDept_name(deptInfo.getDept_name());
            }
            //新增还是删除
            if("1".equals(civilworkerVirfy1.getVerify_type())){
                if("1".equals(civilworkerVirfy.getStatus())){
                    //新增操作
                    BeanUtils.copyProperties(civilworkerVirfy1,civilworkerInfo );
                    civilworkerInfo.setId("");
                    civilworkerInfo.setAdministrative_unit(civilworkerVirfy1.getEmp_code());
                    boolean isAdd = civilworkerInfoService.check(civilworkerInfo.getCertno());
                    if (isAdd) {
                        throw new CustomException("此用户已存在！");
                    }

                    /**
                     * fix bug 公务员预约记录一开始只展示有效体检记录，有体检机构反馈公务员体检后去世了，单位删除了公务员信息体检机构上传不了体检报告，故预约记录展示已删除的公务员预约记录。
                     * 展示已删除的公务员预约记录后 假如公务员有多次删除记录 预约记录会展示多条 。所以公务员换单位先删除原单位公务员信息 ，新单位在加公务员信息 就要启用原来公务员记录
                     * 要保证同一个身份证只有一条公务员信息
                     *
                     * 用人单位端新加的公务员 要医保审批完以后
                     * 批量审核
                     * 单个审核
                     **/
                    Map<String,Object> map = changeCivilworkerInfo(civilworkerInfo);
                    boolean flag = (boolean)map.get("isChange");
                    if(flag){
                        return (ResultInfo)map.get("resultInfo");
                    }

                    civilworkerInfoService.addCiviWorkerInfo(civilworkerInfo);
                }
            }else if("2".equals(civilworkerVirfy1.getVerify_type())) {
                if("1".equals(civilworkerVirfy.getStatus())){
//                BeanUtils.copyProperties(civilworkerVirfy1,civilworkerInfo );
                    civilworkerInfo.setIs_del("1");
                    civilworkerInfo.setId(civilworkerVirfy1.getCw_id());
                    civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo);
                }
            }else if("3".equals(civilworkerVirfy1.getVerify_type())) {
                if("1".equals(civilworkerVirfy.getStatus())){
                    //新增操作
                    BeanUtils.copyProperties(civilworkerVirfy1,civilworkerInfo );
                    civilworkerInfo.setId("");
                    civilworkerInfoService.addCiviWorkerInfo(civilworkerInfo);
                }else {
                    civilworkerInfo.setIs_del("0");
                    civilworkerInfo.setId(civilworkerVirfy1.getCw_id());
                    civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo);
                }
            }
        }
        return ResultInfo.success();
    }




    /**
     * 公务员同步
     *
     * @param type "1":初始化同步
     * @return
     */
    @PostMapping("/syncCivilData")
    public ResultInfo syncCivilData(String type){
        SysUser sysUser = sysUserService.getUser();
        SysDict sysDict = sysDictService.getOne(new QueryWrapper<SysDict>().eq("dict_type","syncDate").eq("value","syncCivilDate").eq("is_del","0"));
        //查询公务员信息
        List<CivilworkerInfo> civilworkerInfos = null;
        HashMap cardMap = new HashMap();
        HashMap empMap = new HashMap();
        if("1".equals(type)){
            civilworkerInfos =civilworkerInfoService.syncCivilData("1","");
            for (CivilworkerInfo c: civilworkerInfos) {
                EmployingInfo e = new EmployingInfo();
                if(c.getEmp_code() != null && !empMap.containsKey(c.getEmp_code())){
                    empMap.put(c.getEmp_code(),"");
                    //同步用人机构数据
                    e.setId(IdUtil.simpleUUID());
                    e.setEmp_name(c.getEmp_name());
                    e.setEmp_no(c.getEmp_code());
                    e.setEmp_type(c.getEmp_type());
                    e.setEmp_address(c.getEmp_addr());
                    e.setAdmdvs(c.getEmp_admdvs());
                    e.setIs_del("0");
                    e.setCreateTime(new Date());
                    e.setCreateUser(sysUser.getId());
                    e.insert();
                }
                //判断是否已经存在。。已经存在则忽略此条记录
                if(!cardMap.containsKey(c.getCertno())){
                    cardMap.put(c.getCertno(),"");
                    c.setId(IdUtil.simpleUUID());
                    c.setEmp_id(e.getId());
                    if("2".equals(c.getInsu_state())){
                        c.setQualifications("0");
                    }else{
                        c.setQualifications("1");
                    }
                    c.setInfo_source("1");
                    c.setIs_del("0");
                    c.setCreateTime(new Date());
                    c.setCreateUser(sysUser.getId());
                    c.insert();
                }
            }

        }else{
            civilworkerInfos =civilworkerInfoService.syncCivilData("1",sysDict.getLabel());
            for (CivilworkerInfo c: civilworkerInfos) {
                //判断是否已经存在。。已经存在则忽略此条记录
                if(!cardMap.containsKey(c.getCertno())){
                    //同步用人机构数据
                    cardMap.put(c.getCertno(),"");
                    //查询数据库已经存在的数据，有则更新 ，无则新增
                    CivilworkerInfo ci =  civilworkerInfoService.getOne(new QueryWrapper<CivilworkerInfo>().eq("certno",c.getCertno()).eq("is_del","0"));
                    if(ci != null){
                        if(!ci.getEmp_code().equals(c.getEmp_code())){
                            //第一人民医院公务员信息有 ，单位没有 commit by  xj
                            EmployingInfo e =employingInfoService.getOne(new QueryWrapper<EmployingInfo>().eq("emp_no",c.getEmp_code()).eq("is_del","0"));
                            if(e == null){
                                e = new EmployingInfo();
                                //同步用人机构数据
                                e.setId(IdUtil.simpleUUID());
                                e.setEmp_name(c.getEmp_name());
                                e.setEmp_no(c.getEmp_code());
                                e.setEmp_type(c.getEmp_type());
                                e.setEmp_address(c.getEmp_addr());
                                e.setAdmdvs(c.getEmp_admdvs());
                                e.setIs_del("0");
                                e.setCreateUser(sysUser.getId());
                                e.setCreateTime(new Date());
                                e.insert();
                            }
                            //单位没有 commit by  xj
                            ci.setEmp_code(c.getEmp_code());
                            ci.setEmp_name(c.getEmp_name());
                            ci.setUpdateTime(new Date());
                            ci.setUpdateUser(sysUser.getId());
                            ci.updateById();
                        }
                    }else{
                        EmployingInfo e =employingInfoService.getOne(new QueryWrapper<EmployingInfo>().eq("emp_no",c.getEmp_code()).eq("is_del","0"));
                        if(e == null){
                            e = new EmployingInfo();
                            //同步用人机构数据
                            e.setId(IdUtil.simpleUUID());
                            e.setEmp_name(c.getEmp_name());
                            e.setEmp_no(c.getEmp_code());
                            e.setEmp_type(c.getEmp_type());
                            e.setEmp_address(c.getEmp_addr());
                            e.setAdmdvs(c.getEmp_admdvs());
                            e.setIs_del("0");
                            e.setCreateUser(sysUser.getId());
                            e.setCreateTime(new Date());
                            e.insert();
                            cardMap.put(c.getEmp_code(),"");
                        }
                        c.setId(IdUtil.simpleUUID());
                        c.setEmp_id(e.getId());
                        c.setQualifications("1");
                        if(!"1".equals(c.getInsu_state())){
                            c.setQualifications("0");
                        }
                        c.setInfo_source("1");
                        c.setCreateUser(sysUser.getId());
                        c.setIs_del("0");
                        c.setCreateTime(new Date());
                        c.insert();
                    }

                }
            }
        }
        //同步时间维护
        sysDict.setLabel(DateUtil.formatDateTime(new Date()));
        sysDict.updateById();
        return ResultInfo.success();
    }

    /**
     * 关键信息更新
     *
     * @param
     * @return
     */
    @PostMapping("/mainCivilData")
    public ResultInfo mainCivilData(){
        SysUser sysUser = sysUserService.getUser();
        SysDict sysDict = sysDictService.getOne(new QueryWrapper<SysDict>().eq("dict_type","syncDate").eq("value","syncMainDate").eq("is_del","0"));
        //查询所有状态公务员
        List<CivilworkerInfo> civilworkerInfos = civilworkerInfoService.syncCivilData("",sysDict.getLabel());
        HashMap<String,CivilworkerInfo> cMap = new HashMap();
        civilworkerInfos.forEach(x->{
            if(!cMap.containsKey(x.getCertno())){
                cMap.put(x.getCertno(),x);
            }
        });
        //查询数据库中公务员
        List<CivilworkerInfo> civilworkerInfos_base = civilworkerInfoService.list(new QueryWrapper<CivilworkerInfo>().eq("is_del","0"));
        civilworkerInfos_base.forEach(x->{
            if(cMap.containsKey(x.getCertno())){
                CivilworkerInfo c = cMap.get(x.getCertno());
                x.setOutside_flag(c.getOutside_flag());
                x.setDeath_flag(c.getDeath_flag());
                if(!"1".equals(c.getDeath_flag())){
                    x.setQualifications("0");
                }
                if(!x.getEmp_code().equals(c.getEmp_code())){
                    //第一人民医院公务员信息有 ，单位没有 commit by  xj
                    EmployingInfo e =employingInfoService.getOne(new QueryWrapper<EmployingInfo>().eq("emp_no",c.getEmp_code()).eq("is_del","0"));
                    if(e == null){
                        e = new EmployingInfo();
                        //同步用人机构数据
                        e.setId(IdUtil.simpleUUID());
                        e.setEmp_name(c.getEmp_name());
                        e.setEmp_no(c.getEmp_code());
                        e.setEmp_type(c.getEmp_type());
                        e.setEmp_address(c.getEmp_addr());
                        e.setAdmdvs(c.getEmp_admdvs());
                        e.setIs_del("0");
                        e.setCreateUser(sysUser.getId());
                        e.setCreateTime(new Date());
                        e.insert();
                    }
                    //单位没有 commit by  xj
                    x.setEmp_code(c.getEmp_code());
                    x.setEmp_name(c.getEmp_name());
                }
                x.setInsu_state(c.getInsu_state());
                if(!"1".equals(c.getInsu_state())){
                    x.setQualifications("0");
                }
                x.setUpdateTime(new Date());
                x.setUpdateUser(sysUser.getId());
                x.updateById();
            }

        });
        //同步时间维护
        sysDict.setLabel(DateUtil.formatDateTime(new Date()));
        sysDict.updateById();
        return ResultInfo.success();
    }

    /**
    *体检统计人数
    * Author wzn
    * Date 2022/5/27 15:58
    */
    @PostMapping("/count")
    public ResultInfo count(){
        List<PeopleCount> peopleCountList = new ArrayList<>() ;
        PeopleCount peopleCount = civilworkerInfoService.tjCount() ;
        PeopleCount peopleCount2 = civilworkerInfoService.tjCount2() ;
        PeopleCount peopleCount3 = personSubscribeRecordService.tjCount() ;
        PeopleCount peopleCount4 = personSubscribeRecordService.getCount() ;
        peopleCountList.add(peopleCount) ;
        peopleCountList.add(peopleCount2) ;
        peopleCountList.add(peopleCount3) ;
        peopleCountList.add(peopleCount4) ;
        return ResultInfo.success(peopleCountList);
    }


    /**
    *预约记录
    * Author wzn
    * Date 2022/5/31 16:13
    */
    @PostMapping("/yyselectList")
    public ResultInfo couselectListnt(@RequestBody PersonSubscribeRecordVo personSubscribeRecordVo){
        Page<PersonSubscribeRecordVo> page = personSubscribeRecordService.selectList(personSubscribeRecordVo) ;

        return ResultInfo.success(page);
    }


    @PostMapping("/yyExport")
    public void yyExport(@RequestBody PersonSubscribeRecordVo personSubscribeRecordVo, HttpServletResponse response) throws Exception{


        personSubscribeRecordService.yyExport(personSubscribeRecordVo,response);

    }




    /**
    *未预约记录
    * Author wzn
    * Date 2022/6/25 14:48
    */
    @PostMapping("/noAppointment")
    public ResultInfo noAppointment(@RequestBody PersonSubscribeRecordVo personSubscribeRecordVo){
        Page<CivilworkerInfo> page = personSubscribeRecordService.noAppointment(personSubscribeRecordVo) ;
        if(null != page.getRecords() && page.getRecords().size()>0){
            for(CivilworkerInfo c:page.getRecords()){
                c.setSex(IdCardNumberMethod.getSexFromIdCard(c.getCertno())+"");
                c.setAge(IdCardNumberMethod.getAgeForIdcard(c.getCertno())+"");
                if(!"".equals(c.getEmp_type()) && null != c.getEmp_type()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("EMP_TYPE");
                    sysDict.setValue(c.getEmp_type());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setEmp_type(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getInsutype()) && null != c.getInsutype()){
                    SysDict sysDict2 = new SysDict() ;
                    sysDict2.setDict_type("INSUTYPE");
                    sysDict2.setValue(c.getInsutype());
                    SysDict sysDict3 = sysDictService.selectByValue(sysDict2) ;
                    if(null != sysDict3){
                        c.setInsutype(sysDict3.getLabel());
                    }
                }

                if(!"".equals(c.getInsu_state()) && null != c.getInsu_state()){
                    SysDict sysDict4 = new SysDict() ;
                    sysDict4.setDict_type("PSN_INSU_STAS");
                    sysDict4.setValue(c.getInsu_state());
                    SysDict sysDict5 = sysDictService.selectByValue(sysDict4) ;
                    if(null != sysDict5){
                        c.setInsu_state(sysDict5.getLabel());
                    }
                }
                if(!"".equals(c.getInsu_admdvs()) && null != c.getInsu_admdvs()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("ADMDVS");
                    sysDict.setValue(c.getInsu_admdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setInsu_admdvs(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getAdmdvs()) && null != c.getAdmdvs()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("ADMDVS");
                    sysDict.setValue(c.getAdmdvs());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setAdmdvs(sysDict1.getLabel());
                    }
                }

                if(!"".equals(c.getOutside_flag())){
                    if("0".equals(c.getOutside_flag())){
                        c.setOutside_flag("否");
                    }else{
                        c.setOutside_flag("是");
                    }
                }

                if(!"".equals(c.getQualifications())){
                    if("0".equals(c.getQualifications())){
                        c.setQualifications("无");
                    }else{
                        c.setQualifications("有");
                    }
                }

                if(!"".equals(c.getDeath_flag()) && null != c.getDeath_flag()){
                    SysDict sysDict = new SysDict() ;
                    sysDict.setDict_type("SURV_STAS");
                    sysDict.setValue(c.getDeath_flag());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict) ;
                    if(null != sysDict1){
                        c.setDeath_flag(sysDict1.getLabel());
                    }
                }
            }
        }
        return ResultInfo.success(page);
    }


    /**
     *异地安置人员列表接口
     * Author wzn
     * Date 2022/6/1 11:05
     */
    @RequestMapping("/relocationList")
    public ResultInfo relocationList(@RequestBody CivilworkerInfo civilworkerInfo) {
        Page<CivilworkerInfo> page= civilworkerInfoService.relocationList(civilworkerInfo);
        if(null != page.getRecords() && page.getRecords().size()>0){
            for(CivilworkerInfo c:page.getRecords()){
                c.setSex(IdCardNumberMethod.getSexFromIdCard(c.getCertno())+"");
                c.setAge(IdCardNumberMethod.getAgeForIdcard(c.getCertno())+"");
            }
        }
            return ResultInfo.success(page);
        }


    /**
     * @Description: 异地安置同步身份证信息
     * @param: [num]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/11
     * @time: 11:19
     */
    @RequestMapping("/selectCivInfo")
    public ResultInfo selectCivInfo( String num,String code){
        return  this.civilworkerInfoService.selectCivInfo(num,code);
    }


    @RequestMapping("/civInfoExcel")
    public void dayDetailsExcel( CivilworkerInfo civilworkerInfo,HttpServletResponse response) throws Exception {
       List<CivilworkerInfo> civilworkerInfos =  civilworkerInfoService.selectListExcel(civilworkerInfo) ;
       Map<String,String> map1 = new HashMap<>() ;
        SysDict sysDict = new SysDict() ;
        sysDict.setDict_type("EMP_TYPE");
        List<SysDict> sysDict1 = sysDictService.select(sysDict) ;
        for(SysDict s:sysDict1){
            map1.put(s.getValue(),s.getLabel()) ;
        }
        Map<String,String> map2 = new HashMap<>() ;
        SysDict sysDict2 = new SysDict() ;
        sysDict2.setDict_type("INSUTYPE");
        List<SysDict> sysDictList2 = sysDictService.select(sysDict2) ;
        for(SysDict s:sysDictList2){
            map2.put(s.getValue(),s.getLabel()) ;
        }

        Map<String,String> map3 = new HashMap<>() ;
        SysDict sysDict3 = new SysDict() ;
        sysDict3.setDict_type("PSN_INSU_STAS");
        List<SysDict> sysDictList3 = sysDictService.select(sysDict3) ;
        for(SysDict s:sysDictList3){
            map3.put(s.getValue(),s.getLabel()) ;
        }

        Map<String,String> map4 = new HashMap<>() ;
        SysDict sysDict4 = new SysDict() ;
        sysDict4.setDict_type("ADMDVS");
        List<SysDict> sysDictList4 = sysDictService.select(sysDict4) ;
        for(SysDict s:sysDictList4){
            map4.put(s.getValue(),s.getLabel()) ;
        }


       if(null != civilworkerInfos && civilworkerInfos.size() >0){
            for(CivilworkerInfo c:civilworkerInfos){
//                c.setSex(IdCardNumberMethod.getSexFromIdCard(c.getCertno())+"");
//                c.setAge(IdCardNumberMethod.getAgeForIdcard(c.getCertno())+"");
                if(!"".equals(c.getEmp_type()) && null != c.getEmp_type()){
                    if(null != sysDict1){
                        c.setEmp_type(map1.get(c.getEmp_type()));
                    }
                }

                if(!"".equals(c.getInsutype()) && null != c.getInsutype()){
                    c.setInsutype(map2.get(c.getInsutype()));
                }

                if(!"".equals(c.getInsu_state()) && null != c.getInsu_state()){
                    c.setInsu_state(map3.get(c.getInsu_state()));
                }
                if(!"".equals(c.getAdmdvs()) && null != c.getAdmdvs()){
                    c.setInsu_admdvs(map4.get(c.getInsu_admdvs()));
                    c.setAdmdvs(map4.get(c.getAdmdvs()));
                }

                if(!"".equals(c.getOutside_flag())){
                    if("1".equals(c.getOutside_flag())){
                        c.setOutside_flag("是");
                    }else{
                        c.setOutside_flag("否");
                    }
                }

                if(!"".equals(c.getDeath_flag())){
                    if("1".equals(c.getDeath_flag())){
                        c.setDeath_flag("否");
                    }else{
                        c.setDeath_flag("是");
                    }
                }
                if(!"".equals(c.getQualifications())){
                    if("0".equals(c.getQualifications())){
                        c.setQualifications("无");
                    }else{
                        c.setQualifications("有");
                    }
                }

            }
        }
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.setOnlyAlias(true) ;
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("certno", "身份证号");
        writer.addHeaderAlias("insu_admdvs", "参保统筹区");
        writer.addHeaderAlias("admdvs", "所属统筹区");
        writer.addHeaderAlias("emp_name", "单位名称");
        writer.addHeaderAlias("emp_code", "单位编码");
        writer.addHeaderAlias("emp_type", "单位类型");
        writer.addHeaderAlias("insutype", "参保险种");
        writer.addHeaderAlias("insu_state", "参保状态");
        writer.addHeaderAlias("outside_flag", "异地就医");
        writer.addHeaderAlias("death_flag", "死亡标志");
        writer.addHeaderAlias("qualifications", "体检资格");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(civilworkerInfos, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
    *公务员详情
    * Author wzn
    * Date 2022/6/20 16:45
    */
    @RequestMapping("/info")
    public ResultInfo info(String id){
        CivilworkerInfo civilworkerInfo = civilworkerInfoService.getById(id) ;

        return  ResultInfo.success(civilworkerInfo) ;
    }
//
//    @RequestMapping("/url")
//    public ResultInfo url(){
//        OkHttpClientUtil.doPostJson("http://localhost:8080/employing_info/selectList","{pageNo: 1, pageSize: 10}");
//        return  ResultInfo.success() ;
//    }



    private Map<String,Object> changeCivilworkerInfo(CivilworkerInfo civilworkerInfo) {
        Map<String,Object>  map = new HashMap<>();
        map.put("isChange",false);
        /**
         * fix bug 公务员预约记录一开始只展示有效体检记录，有体检机构反馈公务员体检后去世了，单位删除了公务员信息体检机构上传不了体检报告，故预约记录展示已删除的公务员预约记录。
         * 展示已删除的公务员预约记录后 假如公务员有多次删除记录 预约记录会展示多条 。所以公务员换单位先删除原单位公务员信息 ，新单位在加公务员信息 就要启用原来公务员记录
         * 要保证同一个身份证只有一条公务员信息
         *
         * 用人单位端新加的公务员 要医保审批完以后
         * 批量审核
         * 单个审核
         **/
        List<CivilworkerInfo>  cInfos = civilworkerInfoService.checkDelCivilworkerInfo(civilworkerInfo.getCertno());
        if(null != cInfos && cInfos.size() >= 2){
            //同一个身份证号在系统中有多条公务员
           // return ResultInfo.error("身份证号"+civilworkerInfo.getCertno()+"在本系中有多条公务员信息，请确认身份证信息或者联系管理员解决");
            map.put("isChange",true);
            map.put("resultInfo",ResultInfo.error("身份证号"+civilworkerInfo.getCertno()+"在本系中有多条公务员信息，请确认身份证信息或者联系管理员解决"));
        }else if(null != cInfos && cInfos.size() ==1) {
            //公务员信息已在本系统录入过 修改公务员信息
            SysUser sysUser = sysUserService.getUser();
            CivilworkerInfo dbCivilworker = cInfos.get(0);
            civilworkerInfo.setId(dbCivilworker.getId());
            civilworkerInfo.setIs_del("0");
            civilworkerInfo.setCreateTime(new Date());
            civilworkerInfo.setUpdateTime(new Date());
            civilworkerInfo.setCreateUser(sysUser.getId());
            civilworkerInfo.setUpdateUser(sysUser.getId());
            civilworkerInfoService.updateCiviWorkerInfo(civilworkerInfo);
            //return ResultInfo.success();
            map.put("isChange",true);
            map.put("resultInfo",ResultInfo.success());
        }

        return map;
    }

    @RequestMapping("/getXzqh")
    public ResultInfo getXzqh(){
        LambdaQueryWrapper<AdministrativeUnit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdministrativeUnit::getIs_del, "0");
        List<AdministrativeUnit> list = administrativeUnitService.list(wrapper);
        return ResultInfo.success(list);
    }

}
