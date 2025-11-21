package com.jsdc.ybpt.controller_agreement_sign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagMechanism;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_query.medicalOrg.MedicalDept;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.MedicalOrgService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.agreementsignService.NetTagMechanismService;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.agreementsignVo.NetTagMechanismVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 协议网签-医药机构网签表(医药机构关联网签表)(医药机构关联补充协议表)
 */
@RestController
@RequestMapping("/tagmechanism")
public class NetTagMechanismController {

    @Autowired
    private NetTagMechanismService service;
    @Autowired
    private MedicalOrgService medicalOrgService;
    @Autowired
    private FixmedinsBService fixmedinsBService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDictService sysDictService;

    /**
     * 网签审核列表
     *
     * @authon zln
     */
    @PostMapping("/selectPageList")
    public ResultInfo selectPageList(@RequestBody NetTagMechanismVo vo) {
        //获取当前机构编码
        Page<NetTagMechanismVo> page = service.selectPageList(new Page<>(vo.getPageNo(), vo.getPageSize()), vo);
        return ResultInfo.success(page);
    }

    /**
     * 医保端
     * 新增补充协议
     */
    @RequestMapping("/saveBcxy")
    public ResultInfo saveBcxy(@RequestBody NetTagMechanism bean) {
        return service.onSaveBcxy(bean);
    }

    @RequestMapping("/onSaveBcxy2")
    public ResultInfo onSaveBcxy2(@RequestBody NetTagMechanism bean) {
        return service.onSaveBcxy2(bean);
    }


    /**
     * 医保端
     * 网签解约
     */
    @RequestMapping("/updJy")
    public ResultInfo updJy(@RequestBody NetTagMechanism bean) {
        return ResultInfo.success(service.updJy(bean));
    }

    /**
     * 医药机构端
     * 新增申请/续约
     */
    @RequestMapping("/insertNetTagAgreement")
    public ResultInfo insertNetTagAgreement(@RequestBody NetTagMechanism bean) throws ParseException {
        return service.onSave(bean);
    }

    /**
     * 医药机构端
     * 生成pdf
     */
    @RequestMapping("/generatePDF")
    public ResultInfo generatePDF(@RequestBody NetTagMechanism bean) {
        return service.generatePDF(bean);
    }

    /**
     * 医药机构端
     * 网签撤销
     */
    @RequestMapping("/revoke")
    public ResultInfo revoke(@RequestBody NetTagMechanism bean) {
        return ResultInfo.success(service.onRevoke(bean));
    }

    /**
     * 医药机构端
     * 网签查看
     */
    @RequestMapping("/view")
    public ResultInfo view(@RequestBody NetTagMechanism bean) {
        return ResultInfo.success(service.details(bean.getId()));
    }

    /**
     * 医药机构端
     * 网签列表
     */
    @PostMapping("/getPageList")
    public ResultInfo getPageList(@RequestBody NetTagMechanismVo vo) {
        //获取当前机构编码
        SysUser sysUser = sysUserService.getUser();
        vo.setMechanism_code(sysUser.getOrg_code());
        //类型(0协议 1补充协议)
//        vo.setType("1");
        Page<NetTagMechanismVo> page = service.selectPageList(new Page<>(vo.getPageNo(), vo.getPageSize()), vo);
        page.getRecords().forEach(a -> {
            if (a.getStatus() == 0) {
                a.setStatus_name("待审核");
            } else if (a.getStatus() == 1) {
                a.setStatus_name("已签章");
            } else if (a.getStatus() == 4) {
                a.setStatus_name("已驳回");
            } else if (a.getStatus() == 2) {
                a.setStatus_name("已解约");
            } else if (a.getStatus() == 3) {
                a.setStatus_name("已过期");
            }
            fixmedinsBService.selectByListPage(a);
        });
        return ResultInfo.success(page);
    }

    /**
     * 获取零售药店和医疗机构等级数据
     * 机构等级（1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级）字典表获取
     */
    @RequestMapping("/selectOrganizationLevel/{type}")
    public ResultInfo selectOrganizationLevel(@PathVariable String type) {
        //医疗机构等级
        if (type.equals("1")) {
            return ResultInfo.success(medicalOrgService.selectOrganizationLevel(type));
        } else {
            //零售药店等级
            List<MedicalDept> list = new ArrayList<>();
            MedicalDept bean = new MedicalDept();
            bean.setCred_lv("4");
            bean.setCred_lv_name("A级");
            list.add(bean);
            MedicalDept bean2 = new MedicalDept();
            bean2.setCred_lv("5");
            bean2.setCred_lv_name("B级");
            list.add(bean2);
            MedicalDept bean3 = new MedicalDept();
            bean3.setCred_lv("6");
            bean3.setCred_lv_name("C级");
            list.add(bean3);
            return ResultInfo.success(list);
        }
    }

    /**
     * 获取当前登陆用户所属的机构信息
     */
    @RequestMapping(value = "getOrganizationInfo.do")
    public ResultInfo getOrganizationInfo() {
        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();
        //user_type 账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行
        if (sysUser.getUser_type().equals("2")) {
            FixmedinsB fixmedinsB = fixmedinsBService.selectByYSYD(orgCode, "1");
            return ResultInfo.success(fixmedinsB);
        } else if (sysUser.getUser_type().equals("3")) {
            FixmedinsB fixmedinsB = fixmedinsBService.selectByYSYD(orgCode, "2");
            return ResultInfo.success(fixmedinsB);
        }

//        //测试数据
//        MedicalDept medicalDept = new MedicalDept();
//        medicalDept.setFixmedins_code("定点医疗机构代码");
//        medicalDept.setFixmedins_name("定点医疗机构名称");
//        medicalDept.setCred_lv_name("信用等级名称");
//        medicalDept.setTel("联系电话");
//        medicalDept.setAddr("联系地址");
//        medicalDept.setDrug_biz_lic_no("药品经营许可证号");
//        medicalDept.setLegrep_name("法定代表人姓名");
//        medicalDept.setBiz_way("经营方式");
//        medicalDept.setBiznat("经营性质");
//        medicalDept.setUscc("统一社会信用代码");
        return ResultInfo.success();
    }

    /**
     * 查看功能
     *
     * @return
     */
    @RequestMapping("/details")
    public ResultInfo details(@RequestBody NetTagMechanism bean) {
        //NetTagMechanism mechanism = service.getById(bean.getId());

        return ResultInfo.success(service.details(bean.getId()));
    }

    /**
     * 网签审核
     *
     * @return
     */
    @RequestMapping("/updateStatus")
    public ResultInfo updateStatus(@RequestBody NetTagMechanism bean) {
        return service.updateStatus(bean);
    }

    /**
     * 更新机构信息
     * @param fixmedinsB
     * @return
     */
    @RequestMapping("orgInfoMaintain.do")
    public ResultInfo orgInfoMaintain(@RequestBody FixmedinsB fixmedinsB){
        return service.orgInfoMaintain(fixmedinsB);
    }

    /**
     * 检验机构信息是否已经维护过
     * 每个机构只能维护一次机构信息
     * @return
     */
    @RequestMapping("checkMaintained.do")
    public ResultInfo checkMaintained(){
        return service.checkMaintained();
    }

    @RequestMapping("getXzqh.do")
    public ResultInfo getXzqh(){
        return service.getXzqh();
    }

    /**
     * 获取还未进行网签申请的机构
     * @param vo
     * @return
     */
    @RequestMapping("getOrgUnSeal.do")
    public ResultInfo getOrgUnSeal(@RequestBody NetTagMechanismVo vo){
        return service.getOrgUnSeal(vo);
    }

    @RequestMapping("exportUnSeal.do")
    public void exportUnSeal(@RequestBody NetTagMechanismVo vo, HttpServletResponse response){
        service.exportUnSeal(vo, response);
    }

}
