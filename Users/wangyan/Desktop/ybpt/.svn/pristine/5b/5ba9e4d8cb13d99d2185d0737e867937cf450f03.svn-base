package com.jsdc.ybpt.service;


import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.CivilworkerInfoMapper;
import com.jsdc.ybpt.mapper.EmployingInfoMapper;
import com.jsdc.ybpt.mapper.RelocatedInfoMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.EmployingInfo;
import com.jsdc.ybpt.model_check.RelocatedInfo;
import com.jsdc.ybpt.util.IdCardNumberMethod;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.PeopleCount;
import com.jsdc.ybpt.vo.PhysicalRatioVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CivilworkerInfoService extends BaseService<CivilworkerInfo> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CivilworkerInfoMapper civilworkerInfoMapper;

    @Autowired
    private EmployingInfoMapper employingInfoMapper;

    @Autowired
    private RelocatedInfoMapper relocatedInfoMapper;

    /**
     * 公务员新增
     * Author wzn
     * Date 2022/5/24 9:57
     */
    public void addCiviWorkerInfo(CivilworkerInfo civilworkerInfo) {
        SysUser sysUser = sysUserService.getUser();
//        LambdaQueryWrapper<EmployingInfo> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(EmployingInfo::getEmp_no, civilworkerInfo.getAdministrative_unit());
//        EmployingInfo employingInfo = this.employingInfoMapper.selectOne(wrapper);
        civilworkerInfo.setCreateUser(sysUser.getUsername());
        civilworkerInfo.setCreateTime(new Date());
//        civilworkerInfo.setAdministrative_unit(employingInfo.getParentOrgCode());
        civilworkerInfo.setIs_del("0");
        civilworkerInfo.insert();
    }

    /**
     * 公务员修改
     * Author wzn
     * Date 2022/5/24 10:10
     */
    public void updateCiviWorkerInfo(CivilworkerInfo civilworkerInfo) {
        SysUser sysUser = sysUserService.getUser();
        civilworkerInfo.setUpdateUser(sysUser.getUsername());
        civilworkerInfo.setUpdateTime(new Date());
        civilworkerInfo.updateById();
    }

    /**
     * 公务员列表接口
     * Author wzn
     * Date 2022/5/24 10:52
     */
    public Page<CivilworkerInfo> selectList(CivilworkerInfo civilworkerInfo) {
        SysUser sysUser = sysUserService.getUser();
        Page<CivilworkerInfo> page = new Page<>(civilworkerInfo.getPageNo(), civilworkerInfo.getPageSize());
        return civilworkerInfoMapper.civiList(page, civilworkerInfo, sysUser);
    }

    /**
     * 公务员导出查询
     * Author wzn
     * Date 2022/6/17 10:04
     */
    public List<CivilworkerInfo> selectListExcel(CivilworkerInfo civilworkerInfo) {
        SysUser sysUser = sysUserService.getUser();

        QueryWrapper<CivilworkerInfo> queryWrapper = new QueryWrapper<>();
        if (!"".equals(civilworkerInfo.getName()) && null != civilworkerInfo.getName()) {
            queryWrapper.like("name", civilworkerInfo.getName());
        }
        if (!"".equals(civilworkerInfo.getEmp_name()) && null != civilworkerInfo.getEmp_name()) {
            queryWrapper.like("emp_name", civilworkerInfo.getEmp_name());
        }

        if (!"".equals(civilworkerInfo.getCertno()) && null != civilworkerInfo.getCertno()) {
            queryWrapper.eq("certno", civilworkerInfo.getCertno());
        }
        if (!"".equals(civilworkerInfo.getEmp_code()) && null != civilworkerInfo.getEmp_code()) {
            queryWrapper.eq("emp_code", civilworkerInfo.getEmp_code());
        }

        queryWrapper.eq("is_del", "0");
//        if ("4".equals(sysUser.getUser_type())) {
//            queryWrapper.eq("emp_code", sysUser.getOrg_code());
//        }
        if ("4".equals(sysUser.getUser_type())) {
            queryWrapper.eq("emp_code", sysUser.getOrg_code());
        }
        queryWrapper.orderByDesc("createTime");
        List<CivilworkerInfo> fixmedinsBPage = civilworkerInfoMapper.selectList(queryWrapper);
        return fixmedinsBPage;
    }

    //校验是否已经添加过此人
    public boolean check(String certNo) {
        boolean isAdd = false;
        QueryWrapper<CivilworkerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("certno", certNo);
        queryWrapper.eq("is_del", "0");

        List<CivilworkerInfo> civilworkerInfoList = civilworkerInfoMapper.selectList(queryWrapper);
        if (civilworkerInfoList.size() > 0) {
            isAdd = true;
        }
        return isAdd;
    }

    /**
     * 查看身份证<code>certNo</code> 有没有被删除的公务员
     * @param certNo 身份证
     * @return
     */
    public List<CivilworkerInfo> checkDelCivilworkerInfo(String certNo) {
        boolean isAdd = false;
        QueryWrapper<CivilworkerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("certno", certNo);
        queryWrapper.eq("is_del", "1");

        List<CivilworkerInfo> civilworkerInfoList = civilworkerInfoMapper.selectList(queryWrapper);

        return civilworkerInfoList;
    }

    @DS("reflowData")
    public List<CivilworkerInfo> syncCivilData(String type, String updateTime) {
        return civilworkerInfoMapper.syncCivilData(type, updateTime);
    }


    /**
     * 统计体检资格人数
     * Author wzn
     * Date 2022/5/27 15:35
     */
    public PeopleCount tjCount() {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<CivilworkerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("qualifications", "1");
        if (!"1".equals(sysUser.getUser_type())) {
            queryWrapper.eq("emp_code", sysUser.getOrg_code());
        }
        Long count = civilworkerInfoMapper.selectCount(queryWrapper);
        PeopleCount peopleCount1 = new PeopleCount();
        peopleCount1.setIcon("user-check");
        peopleCount1.setNum(count + "");
        peopleCount1.setTitle("体检资格");
        peopleCount1.setLink("/vab/player");
        peopleCount1.setColor("#ffc069");
        return peopleCount1;
    }

    /**
     * 异地安置人员统计
     * Author wzn
     * Date 2022/6/7 15:55
     */
    public PeopleCount tjCount2() {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<RelocatedInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (!"1".equals(sysUser.getUser_type())) {
            queryWrapper.eq("org_code", sysUser.getOrg_code());
        }
        Long count = relocatedInfoMapper.selectCount(queryWrapper);
        PeopleCount peopleCount1 = new PeopleCount();
        peopleCount1.setIcon("user-check");
        peopleCount1.setNum(count + "");
        peopleCount1.setTitle("异地安置人员");
        peopleCount1.setLink("/vab/player");
        peopleCount1.setColor("#ffc069");
        return peopleCount1;
    }

    /**
     * 统计 体检占比
     *
     * @param type
     * @param deptNo
     * @return
     */
    public Map<String, String> getPhysicalRatio(String type, String deptNo) {
        //待体检:0 , 已体检:1，已过期:2， 已上传报告:3
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("0", "0");
        hashMap.put("1", "0");
        hashMap.put("2", "0");
        hashMap.put("3", "0");
        List<PhysicalRatioVo> vos = civilworkerInfoMapper.getPhysicalRatio(type, deptNo);
        for (PhysicalRatioVo vo : vos) {
            hashMap.put(vo.getSched(), vo.getNum());
        }
        return hashMap;
    }

    /**
     * 判断此公务员<code>civilworkerInfo</code> 在 <code>relocationYear</code>有没有异地安置标志
     * @param civilworkerInfo
     * @return
     */
    public boolean isRelocationYear(CivilworkerInfo civilworkerInfo,String relocationYear) {
        boolean flag = false;
        RelocatedInfo info = new RelocatedInfo();
        info.setCivilworker_id(civilworkerInfo.getCertno());
        info.setRelocated_year(relocationYear);
        RelocatedInfo rInfo = this.isRelocationYear(info);
        if (rInfo != null && null != rInfo.getId() && rInfo.getId() != "") {
            flag = true;
        }

        return flag;
    }

    public RelocatedInfo isRelocationYear(RelocatedInfo info) {
        LambdaQueryWrapper<RelocatedInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RelocatedInfo::getCivilworker_id, info.getCivilworker_id()).eq(RelocatedInfo::getIs_del, "0")
                .eq(RelocatedInfo::getRelocated_year, info.getRelocated_year());

        RelocatedInfo rInfo = this.relocatedInfoMapper.selectOne(queryWrapper);
        return rInfo;
    }

    /**
     * 异地安置人员列表接口
     * Author wzn
     * Date 2022/6/1 10:44
     */
    public Page<CivilworkerInfo> relocationList(CivilworkerInfo civilworkerInfo) {
        Page<CivilworkerInfo> page = new Page<>(civilworkerInfo.getPageNo(), civilworkerInfo.getPageSize());
        QueryWrapper<CivilworkerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("outside_put", "1");
        queryWrapper.eq("is_del", "0");
        if (!"".equals(civilworkerInfo.getEmp_name()) && null != civilworkerInfo.getEmp_name()) {
            queryWrapper.like("emp_name", civilworkerInfo.getEmp_name());
        }
        if (!"".equals(civilworkerInfo.getName()) && null != civilworkerInfo.getName()) {
            queryWrapper.like("name", civilworkerInfo.getName());
        }
        if (!"".equals(civilworkerInfo.getCertno()) && null != civilworkerInfo.getCertno()) {
            queryWrapper.eq("certno", civilworkerInfo.getCertno());
        }
        Page<CivilworkerInfo> civilworkerInfoPage = civilworkerInfoMapper.selectPage(page, queryWrapper);
        return civilworkerInfoPage;
    }

    public CivilworkerInfo civilLoginH5(CivilworkerInfo vo) {
        LambdaQueryWrapper<CivilworkerInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CivilworkerInfo::getLogin_Name, vo.getLogin_Name());
        queryWrapper.eq(CivilworkerInfo::getIs_del, "0");
        queryWrapper.eq(CivilworkerInfo::getPwd, SaSecureUtil.md5(vo.getPwd()));
        return civilworkerInfoMapper.selectOne(queryWrapper);
    }

    public CivilworkerInfo getCivilworkerInfoByCardNo(CivilworkerInfo vo) {
        QueryWrapper<CivilworkerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("certno", StringUtils.trim(vo.getCertno()));
        queryWrapper.eq("death_flag", "1");
        //异地安置分年份 用个公务员信息要判断体检年度是否被设置异地安置
        //queryWrapper.eq("qualifications", "1");
        queryWrapper.eq("is_del", "0");
        return civilworkerInfoMapper.selectOne(queryWrapper);
    }

    public EmployingInfo getEmployingInfo(CivilworkerInfo civil){
        LambdaQueryWrapper<EmployingInfo> employingInfoWrapper = new LambdaQueryWrapper<>();
        employingInfoWrapper.eq(EmployingInfo::getEmp_no, civil.getEmp_code());
        employingInfoWrapper.eq(EmployingInfo::getIs_del, "0");
        EmployingInfo employingInfo = employingInfoMapper.selectOne(employingInfoWrapper);
        return employingInfo;
    }

    public CivilworkerInfo getQualifiedCivilWorkerInfo(CivilworkerInfo civilworkerInfo) {
        LambdaQueryWrapper<CivilworkerInfo> ciWrapper = new LambdaQueryWrapper<>();
        ciWrapper.eq(CivilworkerInfo::getCertno, civilworkerInfo.getCertno())
                .eq(CivilworkerInfo::getDeath_flag, "1")
                //eq(CivilworkerInfo::getQualifications, "1")  //异地安置分年份
                .eq(CivilworkerInfo::getIs_del, "0");
        return this.civilworkerInfoMapper.selectOne(ciWrapper);
    }

    public ResultInfo setRegist(CivilworkerInfo ci, CivilworkerInfo vo) {
        ResultInfo rInfo = ResultInfo.success();
        if (ci != null && "".equals(StringUtils.trim(ci.getLogin_Name()))) {
            ci.setLogin_Name(vo.getLogin_Name());
            ci.setPwd(SaSecureUtil.md5(vo.getPwd())); //todo 后期密码要加密码
            ci.updateById();
            rInfo.setMsg("注册成功");
        } else if (ci != null && !"".equals(StringUtils.trim(ci.getLogin_Name()))) {
            rInfo.setMsg("已注册,用户名" + ci.getLogin_Name());
        } else {
            rInfo.setMsg("无体检资格");
        }

        return rInfo;
    }

    public ResultInfo validate(CivilworkerInfo ci, CivilworkerInfo vo) {
        ResultInfo rInfo = ResultInfo.success();
        if (ci != null && "".equals(StringUtils.trim(ci.getLogin_Name()))) {
            CivilworkerInfo info = selectCivilWorkerByLoginName(vo.getLogin_Name());
            if (info != null && !"".equals(StringUtils.trim(info.getId()))) {//用户名已被注册
                rInfo.setData(ci);
                rInfo.setMsg(ci.getLogin_Name() + ",用户名已被注册");
            } else {
                rInfo.setData(ci);
                ci.setLogin_Name(vo.getLogin_Name());
                ci.setPwd(SaSecureUtil.md5(vo.getPwd())); //todo 后期密码要加密码
                ci.updateById();
                rInfo.setMsg("注册成功");
            }
        } else if (ci != null && !"".equals(StringUtils.trim(ci.getLogin_Name()))) {
            rInfo.setData(ci);
            rInfo.setMsg("已注册,用户名" + ci.getLogin_Name());
        } else {
            rInfo.setData(new CivilworkerInfo());
            rInfo.setMsg("无体检资格");
        }

        return rInfo;
    }

    public ResultInfo selectCivInfo(String num, String code) {
        SysUser sysUser = this.sysUserService.getUser();
        if ("1".equals(sysUser.getUser_type())) {
            code = null;
        }
        CivilworkerInfo civilworkerInfo = this.civilworkerInfoMapper.selectCivInfo(num, code);
        if (!org.springframework.util.StringUtils.isEmpty(civilworkerInfo)) {
            civilworkerInfo.setSex(IdCardNumberMethod.getSexFromIdCard(civilworkerInfo.getCertno()) == 1 ? "男" : "女");
            civilworkerInfo.setAge(IdCardNumberMethod.getAgeForIdcard(civilworkerInfo.getCertno()) + "");
            return ResultInfo.success(civilworkerInfo);
        } else {
            return ResultInfo.error("查无此人");
        }
    }

    public CivilworkerInfo selectCivilWorkerByLoginName(String loginName) {
        LambdaQueryWrapper<CivilworkerInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CivilworkerInfo::getLogin_Name, loginName);
        wrapper.eq(CivilworkerInfo::getIs_del, "0");
        return this.civilworkerInfoMapper.selectOne(wrapper);
    }

    public CivilworkerInfo checkAutoLogin(String certNo) {
        LambdaQueryWrapper<CivilworkerInfo> ciWrapper = new LambdaQueryWrapper<>();
        ciWrapper.eq(CivilworkerInfo::getCertno, certNo)
                .eq(CivilworkerInfo::getDeath_flag, "1")
                .eq(CivilworkerInfo::getQualifications, "1")
                .eq(CivilworkerInfo::getIs_del, "0");
        return this.civilworkerInfoMapper.selectOne(ciWrapper);
    }

    public CivilworkerInfo selectQualifiedCivil(String cardId) {
        LambdaQueryWrapper<CivilworkerInfo> civilWorkerWrapper = new LambdaQueryWrapper<>();
        civilWorkerWrapper
                .eq(CivilworkerInfo::getIs_del, "0")
                .eq(CivilworkerInfo::getCertno, cardId)
                .eq(CivilworkerInfo::getQualifications, "1")
                .eq(CivilworkerInfo::getDeath_flag, "1");

        //异地安置体检年度
                /*.and(Wrapper -> Wrapper.isNull(CivilworkerInfo::getOutside_flag).or().eq(CivilworkerInfo::getOutside_flag, "0")
                        .or().ne(CivilworkerInfo::getOutside_put, "1"));*/

        CivilworkerInfo civilworkerInfo = this.civilworkerInfoMapper.selectOne(civilWorkerWrapper);
        if (null == civilworkerInfo) {
            throw new CustomException("此用户没有体检资格");
        }
        // 查看此用户的行政单位，从而查看此行政单位的上级行政单位，如果没有，则不允许注册
        if (!StringUtils.isEmpty(civilworkerInfo.getEmp_code())) {
            LambdaQueryWrapper<EmployingInfo> employingInfoWrapper = new LambdaQueryWrapper<>();
            employingInfoWrapper.eq(EmployingInfo::getEmp_no, civilworkerInfo.getEmp_code());
            employingInfoWrapper.eq(EmployingInfo::getIs_del, "0");
            EmployingInfo employingInfo = this.employingInfoMapper.selectOne(employingInfoWrapper);
            if (employingInfo  == null) {
                throw new CustomException("无法根据用人单位找到该人");
            }
            if (StringUtils.isEmpty(employingInfo.getParentOrgCode())) {
                throw new CustomException("您所在单位暂未开通体检权限");
            }
        } else {
            throw new CustomException("未找到此用户的所属单位");
        }

        return civilworkerInfo;
    }

}
