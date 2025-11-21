package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.mapper.AutonomousMedicalMapper;
import com.jsdc.ybpt.model_check.*;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.EmpSubscribeRecordVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author libin
 * @create 2022/6/2 10:35
 * 现场协助
 */
@RestController
@RequestMapping("/physicalAst")
public class PhysicalAstController {
    @Autowired
    CivilworkerInfoService  civilworkerInfoService;
    @Autowired
    OrganizationInfoService organizationInfoService;
    @Autowired
    OrgSubscribeRulesService orgSubscribeRulesService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EmpSubscribeRecordService empSubscribeRecordService;

    @Autowired
    private PersonSubscribeRecordService personSubscribeRecordService;
    @Autowired
    private AutonomousMedicalMapper autonomousMedicalMapper;

    /**
     * 获取被协助人的信息
     * @param vo
     * @return
     */
    @RequestMapping("/getEntity")
    public ResultInfo toEdit(CivilworkerInfo vo) {
        //civilworkerInfoService.getCivilworkerInfoByCardNo(vo)
        return ResultInfo.success();
    }

    /**
     * 获取被协助人的信息
     * @param vo
     * @return
     */
    @PostMapping("/setRegist")
    public ResultInfo getRegist(@RequestBody CivilworkerInfo vo) {
        this.civilworkerInfoService.selectQualifiedCivil(vo.getCertno());
        CivilworkerInfo dbCivi = civilworkerInfoService.getCivilworkerInfoByCardNo(vo);
        //
        if (dbCivi!=null&& Strings.isNotEmpty(vo.getTelephone())){
            dbCivi.setTelephone(vo.getTelephone());
            dbCivi.updateById();
        }

        return civilworkerInfoService.validate(dbCivi,vo);
    }

    /**
     * 获取所有机构
     * @return
     */
    @RequestMapping("/getAllOrgList")
    public ResultInfo getAllOrgList() {
        return ResultInfo.success(organizationInfoService.getOrganizations());
    }

    /**
     * 获取所有机构 选择天的预约人数
     * @return
     */
    @RequestMapping("/getBookingNum")
    public ResultInfo getBookingNum(@RequestBody OrgSubscribeRules rule) {
        //return ResultInfo.success(orgSubscribeRulesService.getEntity(rule));
        //fix bug 线上会出现一天有多个规则
        List<OrgSubscribeRules> rules = orgSubscribeRulesService.getEntityList(rule);
        if( null != rules && rules.size() == 1){
            return ResultInfo.success(rules.get(0));
        }else if(null == rules || (null != rules && rules.size() == 0)){
            return ResultInfo.success();//没配置规则
        }else{
            return ResultInfo.error(rule.getTime()+"本日预约规则配置出现问题请联系管理员");
        }
    }

    @RequestMapping("/setLoginOrg")
    public ResultInfo setLoginOrg() {
        return ResultInfo.success(sysUserService.getUser());
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultInfo save(@RequestBody EmpSubscribeRecordVo vo) {
        //CivilworkerInfo qualifiedCivil =civilworkerInfoService.selectQualifiedCivil(vo.getCertno());
        CivilworkerInfo ci = new CivilworkerInfo();
        ci.setCertno(vo.getCertno());
//        CivilworkerInfo civil = civilworkerInfoService.getCivilworkerInfoByCardNo(ci);
        CivilworkerInfo civil = civilworkerInfoService.selectQualifiedCivil(vo.getCertno());

        OrganizationInfo org = organizationInfoService.getEntityByOrgMedicalInsuranceNum(vo.getOrg_id());
        vo.setOrg_name(org.getOrg_name());
        if (civil == null) {
            throw new RuntimeException("没有用户信息，请确认身份证号");
        }

        boolean flag = civilworkerInfoService.isRelocationYear(civil,vo.getYear());
        if(flag){
            throw new RuntimeException("该公务员开通了异地安置");
        }

        //上级行政单位验证
        EmployingInfo employingInfo = civilworkerInfoService.getEmployingInfo(civil);
        if (com.jsdc.ybpt.util.StringUtils.isEmpty(employingInfo.getParentOrgCode())) {
            throw new RuntimeException("您所在单位暂未开通体检权限");
        }

        Integer count = autonomousMedicalMapper.findAutonomousMedicalCount(employingInfo.getEmp_no());
        if (count > 0) {
            return ResultInfo.error("该单位已申请自主体检");
        }

        if(!employingInfo.getParentOrgCode().equals(org.getOrg_code())){
            throw new RuntimeException("公务员和体检机构不在同一统筹区");
        }


        ArrayList<String> wids = new ArrayList<>();
        wids.add(civil.getCertno());
        vo.setWids(wids);
        vo.setUid(civil.getEmp_id());
        if (vo.getWids().size() > 0 && vo.getWids().get(0) == null) {
            throw new RuntimeException("员工id不能为空");
        }
        if (StringUtils.isEmpty(vo.getYear())) {
            throw new RuntimeException("year补不能为空");
        }
        if (StringUtils.isEmpty(vo.getApply_date())) {
            throw new RuntimeException("applyDate不能为空");
        }
        if (StringUtils.isEmpty(vo.getPid())) {
            throw new RuntimeException("packInfoId不能为空");
        }
//        if (StringUtils.isEmpty(vo.getMoney())) {
//            throw new RuntimeException("money不能为空");
//        }
        if (StringUtils.isEmpty(vo.getOrg_name())) {
            throw new RuntimeException("org_name不能为空");
        }
        if (StringUtils.isEmpty(vo.getOrg_id())) {
            throw new RuntimeException("org_id名称不能为空");
        }
        vo.setCardId(vo.getCertno());

        // 查询个人预约记录，如果在该年已经提交，则不允许再次提交
        if (this.personSubscribeRecordService.countPsrNum(vo) > 0) {
            return ResultInfo.error("已经在" + vo.getYear() + "年提交, 请勿重复提交");
        }
        PackInfo packInfo = empSubscribeRecordService.getPackInfo(vo);
        if(null == packInfo){
            return ResultInfo.error("查无此套餐");
        }
        return empSubscribeRecordService.saveEmpSubscribeRecordForCivilPersonally(vo);
    }

}
