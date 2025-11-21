package com.jsdc.ybpt.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.common.HyhLog;
import com.jsdc.ybpt.common.utils.ApiUtils;
import com.jsdc.ybpt.mapper.AutonomousMedicalMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.*;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/checkApi")
//@AllArgsConstructor
public class ExternalCheckApi {


    @Autowired
    private CivilworkerInfoService service;

    @Autowired
    private OrganizationInfoService organizationInfoService;

    @Autowired
    private EmpSubscribeRecordService empSubscribeRecordService;

    @Autowired
    private PersonSubscribeRecordService personSubscribeRecordService;

    @Autowired
    private MedicalItemService medicalItemService;

    @Autowired
    private PackInfoService packInfoService;

    @Autowired
    private OrgSubscribeRulesService orgSubscribeRulesService;
    @Autowired
    private AutonomousMedicalMapper autonomousMedicalMapper;


    /**
     * 对外接口—体检报告上传
     */
    @HyhLog()
    @PostMapping("/uploadFile")
    @ResponseBody
    public ResultInfo multipleCommentImageUpload(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(required = false, value = "id") String id,
                                                 @RequestParam(required = false, value = "civilworker_id") String civilworker_id,

                                                 @RequestParam(required = false, value = "year") String year) {
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        if (Strings.isNotEmpty(id)) {
            PersonSubscribeRecord psb = personSubscribeRecordService.getOrgCode(id);
            if (psb==null){
                return ResultInfo.error("上传失败，不在本机构体检或上传id有误");
            }
            if (!orgCode.equals(psb.getOrg_id())){
                return ResultInfo.error("上传失败，不在本机构体检");
            }
        }


        return personSubscribeRecordService.multipleCommentImageUpload(request, response, id, civilworker_id, year,orgCode);


    }


    /**
     * @return author:
     * @param: [login_name, pwd]
     */
    @HyhLog()
    @PostMapping("login")
    public ResultInfo login(HttpSession session, @RequestBody CivilworkerInfo vo) {
        CivilworkerInfo civilworkerInfo = service.civilLoginH5(vo);
        if (civilworkerInfo != null) {
            session.setAttribute("civil", civilworkerInfo);
            return ResultInfo.success("登录成功");
        }
        return ResultInfo.error("没有此人");
    }


    @HyhLog()
    @PostMapping("testAop")
    public ResultInfo testAop(String ddd) {
        return ResultInfo.success("aop!!!");
    }


    /**
     * 获取被协助人的信息
     *
     * @param vo
     * @return
     */
    @HyhLog()
    @PostMapping("/setRegist")
    public ResultInfo getRegist(@RequestBody CivilworkerInfo vo) {
        if ((!StringUtils.hasLength(vo.getPwd())) || vo.getPwd().length() < 6) {
            return ResultInfo.error("密码长度不能小于6位");
        }
        this.service.selectQualifiedCivil(vo.getCertno());
        CivilworkerInfo dbCivi = service.getCivilworkerInfoByCardNo(vo);
        return service.setRegist(dbCivi, vo);
    }


    /**
     * 体检机构协助 预约
     *
     * @param vo
     * @return
     */
    @HyhLog()
    @PostMapping("/save")
    public ResultInfo savePersonSubscribeRecord(@RequestBody EmpSubscribeRecordVo vo) {
        if (StringUtils.isEmpty(vo.getYear())) {
            return ResultInfo.error("年份不能为空");
        }
        //wids
        CivilworkerInfo cVo = new CivilworkerInfo();
        cVo.setCertno(vo.getCertno());
        CivilworkerInfo civil = service.getCivilworkerInfoByCardNo(cVo);
        if (null == civil || "".equals(civil.getId())) {
            return ResultInfo.error("无预约资格");
        }

        //异地安置判断
        boolean relocationYearFlag = service.isRelocationYear(civil,vo.getYear());
        if(relocationYearFlag){
            return ResultInfo.error("此公务员在"+vo.getYear()+"年度申请已申请异地安置");
        }

        //上级行政单位验证
        EmployingInfo employingInfo = service.getEmployingInfo(civil);
        if (com.jsdc.ybpt.util.StringUtils.isEmpty(employingInfo.getParentOrgCode())) {
            return ResultInfo.error("您所在单位暂未开通体检权限");
        }

        Integer count = autonomousMedicalMapper.findAutonomousMedicalCount(employingInfo.getEmp_no());
        if (count > 0) {
            return ResultInfo.error("该单位已申请自主体检");
        }

        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        OrganizationInfo organizationInfo = organizationInfoService.selectByOrgCode(apiLoginInfo.getOrgCode(),apiLoginInfo.getAuthCode());
        //公务所在的行政管理单位 和体检机构的行政管理单位不一致
        if(!employingInfo.getParentOrgCode().equals(organizationInfo.getOrg_code())){
            return ResultInfo.error("公务员和体检机构不在同一统筹区");
        }

        String orgCode = apiLoginInfo.getOrgCode();
        List<OrgSubscribeRules> orgSubscribeRules=orgSubscribeRulesService.list(Wrappers.<OrgSubscribeRules>lambdaQuery().
                eq(OrgSubscribeRules::getTime,vo.getApply_date()).
                eq(OrgSubscribeRules::getIsbook,"1").
                eq(OrgSubscribeRules::getState,"0").
                eq(OrgSubscribeRules::getOrg_id,orgCode));

        if (orgSubscribeRules.isEmpty()){
            return ResultInfo.error(String.format("%s无体检规则",vo.getApply_date()));
        }else if(orgSubscribeRules.size()!=1){
            return ResultInfo.error("预约规则有误");
        }else if((Integer.parseInt(orgSubscribeRules.get(0).getLimit_person())-
                Integer.parseInt(orgSubscribeRules.get(0).getBooking_person()))<=0){
            return ResultInfo.error(String.format("%s预约名额已满",vo.getApply_date()));
        }

        OrganizationInfo org = organizationInfoService.getEntity(orgCode);
        if (null != org) {
            vo.setOrg_name(org.getOrg_name());
        }
        ArrayList<String> wids = new ArrayList<>();
        wids.add(civil.getCertno());
        vo.setWids(wids);
        vo.setOrg_id(orgCode);
        vo.setUid(civil.getEmp_id());
        boolean flag = false;

        vo.setCardId(civil.getCertno());
        // 查询个人预约记录，如果在该年已经提交，则不允许再次提交
        if (this.personSubscribeRecordService.countPsrNum(vo) > 0) {
            return ResultInfo.error("已经在" + vo.getYear() + "年提交, 请勿重复提交");
        }

        PackInfo packInfo = empSubscribeRecordService.getPackInfo(vo);
        if(null == packInfo){
            return ResultInfo.error("查无此套餐");
        }

        try {
             empSubscribeRecordService.saveEmpSubscribeRecordForCivilPersonally(vo);
            flag = true;
        } catch (Exception e) {
            flag = false;
            return ResultInfo.error(e.getMessage());
        }

        if (flag) {
            return ResultInfo.success("预约成功");
        }
        return ResultInfo.error("预约失败");
    }

    /**
     * 体检机构确认
     *
     * @param id
     * @return
     */
    @HyhLog()
    @PostMapping("conformById")
    public ResultInfo conformById(@RequestParam String id) {
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        return personSubscribeRecordService.conformByIdAPI(id,apiLoginInfo);
    }


    /**
     * @Description: 预约撤销
     * @param: [id, uid, pid, time, num]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/5/26
     * @time: 14:41
     */
    @HyhLog()
    @PostMapping("/backoutSubscribe")
    public ResultInfo backoutSubscribe(String id) {
        if (StringUtils.isEmpty(id)){
            return ResultInfo.error("参数验证错误");
        }
        return this.empSubscribeRecordService.backoutSubscribe(id);
    }


    /**
     * @Description: 对外接口--获取体检项
     * @param: [pageNo, pageSize, itemName, itemNo]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/14
     * @time: 9:11
     */
//    @HyhLog()
    @RequestMapping("getMedicalItemList")
    public ResultInfo getMedicalItemList(String itemName, String itemNo) {
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        if (StringUtils.isEmpty(apiLoginInfo.getOrgCode())) {
            return ResultInfo.error("451", "参数验证错误");
        }
        MedicalItemVo vo = new MedicalItemVo();
        vo.setItem_name(itemName);
        vo.setItem_no(itemNo);
        vo.setOrg_id(apiLoginInfo.getOrgCode());
        return ResultInfo.success(this.medicalItemService.getMedicalItemList1(vo));
    }


    /**
     * @Description: 对外接口--获取通用（机构）套餐
     * @param: [pageNo, pageSize, packYear, packName, orgId, packSource]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/14
     * @time: 9:23
     */
//    @HyhLog()
    @RequestMapping("getPackInfoList")
    public ResultInfo getPackInfoList(String packYear, String packName) {
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        if (StringUtils.isEmpty(apiLoginInfo.getOrgCode())) {
            return ResultInfo.error("451", "参数验证错误");
        }
        PackInfoVo vo = new PackInfoVo();
        vo.setPack_name(packName);
        vo.setPack_year(packYear);
        vo.setPack_source("1");
        vo.setOrgCode(apiLoginInfo.getOrgCode());
        return ResultInfo.success(this.packInfoService.getPackInfoList1(vo));
    }

    /**
     * @Description: 对外接口--保存体检规则
     * @param: [dateArray, num, state, endTime, startTime]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/14
     * @time: 9:55
     */
    @HyhLog()
    @RequestMapping("addOrgSubscribeRules")
    public ResultInfo addOrgSubscribeRules(@RequestBody(required = false) SubscribeRulesVo vo) {
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        if (StringUtils.isEmpty(vo.getDateArray()) || StringUtils.isEmpty(vo.getState()) || StringUtils.isEmpty(apiLoginInfo.getOrgCode())) {
            return ResultInfo.error("451", "参数验证错误");
        }
        OrgSubscribeRulesVo vo1 = new OrgSubscribeRulesVo();
        vo1.setDateArray(JSON.toJSONString(vo.getDateArray()));
        vo1.setState(vo.getState());
        vo1.setOrg_id(apiLoginInfo.getOrgCode());
        this.orgSubscribeRulesService.addSubscribeRules(vo1);
        return ResultInfo.success();
    }

//    @HyhLog()
//    @RequestMapping("addOrgSubscribeRulesaaaa")
//    public ResultInfo addOrgSubscribeRulesaaaa(@RequestBody(required = false) SubscribeRulesVo rulesVo) {
//        ApiLoginInfo apiLoginInfo=ApiUtils.getApiLoginInfo();
//        if (StringUtils.isEmpty(rulesVo.getDateArray()) ||StringUtils.isEmpty(rulesVo.getState())|| StringUtils.isEmpty(apiLoginInfo.getOrgCode())) {
//            return ResultInfo.error("451", "参数验证错误");
//        }
//        OrgSubscribeRulesVo vo =new OrgSubscribeRulesVo();
//        vo.setDateArray(JSON.toJSONString(rulesVo.getDateArray()));
////        vo.setLimit_person(num);
//        vo.setState(rulesVo.getState());
////        vo.setStart_time(startTime);
////        vo.setEnd_time(endTime);
//        vo.setOrg_id(apiLoginInfo.getOrgCode());
//        this.orgSubscribeRulesService.addSubscribeRules(vo);
//        return  ResultInfo.success();
//    }


    /**
     * @Description: 对外接口--添加或者撤销预约规则
     * @param: [time, num, state, id]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/6/14
     * @time: 11:41
     */
    @HyhLog()
    @RequestMapping("saveOrUpdateOrgSubscribeRules")
    public ResultInfo saveOrUpdateOrgSubscribeRules(String time, String num, String state, String id) {
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(num) || StringUtils.isEmpty(state) || StringUtils.isEmpty(apiLoginInfo.getOrgCode())) {
            return ResultInfo.error("451", "参数验证错误");
        }
        OrgSubscribeRulesVo vo = new OrgSubscribeRulesVo();
        vo.setTime(time);
        vo.setLimit_person(num);
        vo.setState(state);
        vo.setId(id);
        vo.setOrg_id(apiLoginInfo.getOrgCode());
        this.orgSubscribeRulesService.saveOrUpdateOrgSubscribeRules(vo);
        return ResultInfo.success();
    }

    /**
     * @param item_name
     * @param item_no
     * @param
     * @return com.jsdc.ybpt.vo.ResultInfo
     * @Description 对外接口: 体检项新增
     */
    @HyhLog
    @PostMapping("medicalItem/save")
    public ResultInfo medicalItemSave(@RequestParam String item_name,
                                      @RequestParam String item_no,
                                      @RequestParam String item_type,
                                      @RequestParam(required = false) String year) {

        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        OrganizationInfo organizationInfo = this.organizationInfoService.getEntityByOrgMedicalInsuranceNum(orgCode);
        MedicalItem medicalItem = new MedicalItem();
        medicalItem.setOrg_id(orgCode);
        medicalItem.setItem_name(item_name);
        medicalItem.setItem_no(item_no);
        medicalItem.setItem_type(item_type);
        medicalItem.setCreateUser(organizationInfo.getOrg_name());
        if (com.jsdc.ybpt.util.StringUtils.checkYear(year)) {
            medicalItem.setYear(year);
        } else if (null == year) {
            LocalDateTime now = LocalDateTime.now();
            medicalItem.setYear(String.valueOf(now.getYear()));
        } else {
            return ResultInfo.error("参数year不合法");
        }

        return this.medicalItemService.saveOutSide(medicalItem);

    }

    /**
     * @param
     * @return com.jsdc.ybpt.vo.ResultInfo
     * @Description 对外接口: 体检项编辑
     */
    @HyhLog
    @PostMapping("medicalItem/edit")
    public ResultInfo medicalItemEdit(@RequestParam String id,
                                      String item_name,
                                      String item_no,
                                      String item_type,
                                      String item_state,
                                      String is_del,
                                      String year) {
        // id必传，且要有效
        // 如果传了 item_name,检查除自己外的所有item_name
        // 如果传了 item_no,检查出自己外的所有item_no
        // 增加updateUser,updateTime
        // 如果有些参数不传,则不修改
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        OrganizationInfo organizationInfo = this.organizationInfoService.getEntityByOrgMedicalInsuranceNum(orgCode);

        long one = System.currentTimeMillis();

        MedicalItem medicalItemDB = this.medicalItemService.belongToOrg(orgCode, id);

        long two = System.currentTimeMillis();
        System.out.println("0001:");
        System.out.println(two - one);
        if (medicalItemDB == null) {
            return ResultInfo.error("此体检项目不属于该用户");
        }

        if (year != null) {
            if (!com.jsdc.ybpt.util.StringUtils.checkYear(year)) {
                return ResultInfo.error("参数非法:pack_year");
            }
        }


        medicalItemDB.setItem_no(item_name);
        medicalItemDB.setItem_name(item_name);
        medicalItemDB.setItem_no(item_no);
        medicalItemDB.setItem_type(item_type);
        medicalItemDB.setItem_state(item_state);
        medicalItemDB.setIs_del(is_del);
        medicalItemDB.setYear(year);
        medicalItemDB.setUpdateUser(organizationInfo.getOrg_name());
        medicalItemDB.setUpdateTime(DateUtil.formatDateTime(new Date()));
        long three = System.currentTimeMillis();
        System.out.println("0002:");
        System.out.println(three - two);
        return this.medicalItemService.editOutSide(medicalItemDB, three);
    }

    /**
     * @param
     * @return
     */
    @HyhLog
    @PostMapping("medicalItem/delete")
    public ResultInfo medicalItemDelete(@RequestParam(name = "ids") List<String> ids) {
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        MedicalItemVo medicalItemVo = new MedicalItemVo();
        medicalItemVo.setOutsideApiIds(ids);
        medicalItemVo.setOrg_id(orgCode);
        return this.medicalItemService.deleteOutSide(medicalItemVo);
    }

    @HyhLog
    @PostMapping("packInfo/save")
    public ResultInfo packInfoSave(@RequestBody PackInfoVo vo) {
        if (!StringUtils.hasLength(vo.getPack_name())) {
            return ResultInfo.error("pack_name不能为空");
        }
        if (vo.getItem_to_meal() == null || vo.getItem_to_meal().isEmpty()) {
            return ResultInfo.error("item_to_meal不合法");
        }
        if (!(com.jsdc.ybpt.util.StringUtils.checkYear(vo.getPack_year()) || vo.getPack_year().length() == 4)) {
            return ResultInfo.error("参数非法:pack_year");
        }
        if (!com.jsdc.ybpt.util.StringUtils.isNumeric(vo.getPack_money())) {
            return ResultInfo.error("参数非法：pack_money");
        }

        if (!StringUtils.hasLength(vo.getApplicable_scope())) {
            vo.setApplicable_scope("0");
        }

        if (!this.packInfoService.isInApplicableScope(vo)) {
            return ResultInfo.error("applicable_scope:" + vo.getApplicable_scope() + "无效");
        }
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        OrganizationInfo organizationInfo = this.organizationInfoService.getEntityByOrgMedicalInsuranceNum(orgCode);
        vo.setOrg_id(orgCode);
        vo.setOrg_name(organizationInfo.getOrg_name());
        vo.setPack_source("1");
        vo.setCreateUser(organizationInfo.getOrg_name());
        return this.packInfoService.orgSaveOutSide(vo);
    }

    @HyhLog
    @PostMapping("packInfo/edit")
    public ResultInfo packInfoEdit(@RequestBody PackInfoVo vo) {
        if (!StringUtils.hasLength(vo.getId())) {
            return ResultInfo.error("id不能为空");
        }

        if (StringUtils.hasText(vo.getApplicable_scope())) {
            if (!this.packInfoService.isInApplicableScope(vo)) {
                return ResultInfo.error("applicable_scope:" + vo.getApplicable_scope() + "无效");
            }
        }

        if (!com.jsdc.ybpt.util.StringUtils.checkYear(vo.getPack_year())) {
            return ResultInfo.error("参数非法:pack_year");
        }

        // 体检机构组织没有如下字段权限
        vo.setVerify_ids(null);
        vo.setStatus(null);
        vo.setCreateTime(null);
        vo.setPack_source(null);
        vo.setOrg_name(null);
        vo.setOrgCode(null);
        vo.setPhys_exam_id(null);
        vo.setVerify_ids(null);
        vo.setVerify_time(null);
        vo.setVerify_reason(null);
        vo.setVerify_user(null);
        vo.setPack_source(null);

        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        OrganizationInfo organizationInfo = this.organizationInfoService.getEntityByOrgMedicalInsuranceNum(orgCode);
        vo.setUpdateUser(organizationInfo.getOrg_name());
        vo.setOrg_id(orgCode);
        return this.packInfoService.orgEditOutSide(vo);
    }

    @HyhLog
    @PostMapping("packInfo/delete")
    public ResultInfo packInfoDelete(@RequestParam(name = "ids") List<String> ids) {

        if (ids.isEmpty()) {
            return ResultInfo.error("参数异常:ids:长度不能为空");
        }
        PackInfoVo packInfoVo = new PackInfoVo();
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        packInfoVo.setOrg_id(orgCode);
        packInfoVo.setOutsideApiIds(ids);
        return this.packInfoService.orgDeleteOutSide(packInfoVo);
    }

    @HyhLog
    @PostMapping("getOrgSubscribeRulesList")
    public ResultInfo getOrgSubscribeRulesList(String startTime, String endTime) {
        OrgSubscribeRules ruleVo = new OrgSubscribeRules();
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        String orgCode = apiLoginInfo.getOrgCode();
        ruleVo.setOrg_id(orgCode);
        ruleVo.setRuleStartTime(startTime);
        ruleVo.setRuleEndTime(endTime);
        return ResultInfo.success(orgSubscribeRulesService.getList(ruleVo));
    }

    @HyhLog
    @PostMapping("/getSubscribeRecords")
    public ResultInfo couselectListnt(@RequestBody PersonSubscribeRecordVo personSubscribeRecordVo) {
        if (personSubscribeRecordVo.getPageSize()>100){
            return ResultInfo.error("每页显示最多100条！");
        }
        ApiLoginInfo apiLoginInfo = ApiUtils.getApiLoginInfo();
        SysUser sysUser = new SysUser();
        sysUser.setUser_type(apiLoginInfo.getOrgType());
        sysUser.setOrg_code(apiLoginInfo.getOrgCode());
        personSubscribeRecordVo.setOrg_code(apiLoginInfo.getOrgCode());
        String orgCode = apiLoginInfo.getOrgCode();
        personSubscribeRecordVo.setOrg_id(orgCode);
        personSubscribeRecordVo.setSysUser(sysUser);
        Page<PersonSubscribeRecordVo> page = personSubscribeRecordService.selectListAPI(personSubscribeRecordVo);
        return ResultInfo.success(page);
    }
}
