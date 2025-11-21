package com.jsdc.ybpt.service;


import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.CivilworkerInfoMapper;
import com.jsdc.ybpt.mapper.OrganizationInfoMapper;
import com.jsdc.ybpt.mapper.PackInfoMapper;
import com.jsdc.ybpt.mapper.SysUserMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.model_check.PackInfo;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.OrganizationInfoVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class OrganizationInfoService extends BaseService<OrganizationInfo> {
    @Autowired
    private OrganizationInfoMapper mapper;
    @Autowired
    private CivilworkerInfoMapper civilworkerInfoMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PackInfoMapper packInfoMapper;


    /**
     * 分页查询
     *
     * @return
     */
    @DS("master")
    public Page<OrganizationInfo> getList(OrganizationInfoVo vo) {
        SysUser sysUser = sysUserService.getUser();
        Page<OrganizationInfo> page;
        LambdaQueryWrapper<OrganizationInfo> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getOrg_name())) {
            lambda.like(OrganizationInfo::getOrg_name, vo.getOrg_name());
        }
        if (StringUtils.isNotEmpty(vo.getMedical_insurance_num())) {
            lambda.like(OrganizationInfo::getMedical_insurance_num, vo.getMedical_insurance_num());
        }
        if (StringUtils.isNotEmpty(vo.getCredit_level())) {
            lambda.eq(OrganizationInfo::getCredit_level, vo.getCredit_level());
        }
        if (StringUtils.isNotEmpty(vo.getAdministrative_unit())) {
            lambda.eq(OrganizationInfo::getAdministrative_unit, vo.getAdministrative_unit());
        }
        lambda.eq(OrganizationInfo::getIs_del, "0");
        lambda.eq(OrganizationInfo::getOrg_code, sysUser.getOrg_code());
        lambda.orderByDesc(OrganizationInfo::getCreateTime);
        page = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
        return page;
    }

    /**
     * 查询全部 下拉数据
     *
     * @param vo
     * @return
     */
    @DS("master")
    public List<OrganizationInfo> getListAll(OrganizationInfoVo vo) {
        SysUser sysUser = sysUserService.getUser();
        List<OrganizationInfo> list;
        LambdaQueryWrapper<OrganizationInfo> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getOrg_name())) {
            lambda.like(OrganizationInfo::getOrg_name, vo.getOrg_name());
        }
        if (StringUtils.isNotEmpty(vo.getMedical_insurance_num())) {
            lambda.like(OrganizationInfo::getMedical_insurance_num, vo.getMedical_insurance_num());
        }
        if (StringUtils.isNotEmpty(vo.getCredit_level())) {
            lambda.eq(OrganizationInfo::getCredit_level, vo.getCredit_level());
        }
        lambda.eq(OrganizationInfo::getIs_del, "0");

        lambda.eq(OrganizationInfo::getOrg_code, sysUser.getOrg_code());
        lambda.orderByDesc(OrganizationInfo::getCreateTime);
        list = mapper.selectList(lambda);


        return list;
    }

    /**
     * 查询全部 下拉数据
     *
     * @param vo
     * @return
     */
    @DS("master")
    public List<OrganizationInfo> getAll(OrganizationInfoVo vo) {
        List<OrganizationInfo> list;
        LambdaQueryWrapper<OrganizationInfo> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getOrg_name())) {
            lambda.like(OrganizationInfo::getOrg_name, vo.getOrg_name());
        }
        if (StringUtils.isNotEmpty(vo.getMedical_insurance_num())) {
            lambda.like(OrganizationInfo::getMedical_insurance_num, vo.getMedical_insurance_num());
        }
        if (StringUtils.isNotEmpty(vo.getCredit_level())) {
            lambda.eq(OrganizationInfo::getCredit_level, vo.getCredit_level());
        }
        lambda.eq(OrganizationInfo::getIs_del, "0");

        lambda.orderByDesc(OrganizationInfo::getCreateTime);
        list = mapper.selectList(lambda);


        list.forEach(x -> {
            if (StringUtils.isNotEmpty(x.getMedical_insurance_num())) {
                List<PackInfo> packInfos = packInfoMapper.selectList
                        (Wrappers.<PackInfo>lambdaQuery().
                                eq(PackInfo::getOrg_id, x.getMedical_insurance_num()).
                                eq(PackInfo::getPack_source,"1").
                                eq(PackInfo::getStatus,"1").
                                eq(PackInfo::getIf_use,"1").
                                eq(PackInfo::getIs_del,"0"));
                if (!packInfos.isEmpty()) {
                    x.setOrg_name(String.format("%s--有优惠套餐", x.getOrg_name()));
                    x.setIs_select("1");
                }
            }

        });


        return list;
    }

    /**
     * 编辑
     *
     * @return
     */
    @DS("master")
    public ResultInfo toEdit(OrganizationInfoVo vo) {
        Integer medical_insurance_num = getMedicalInsuranceNum();
        switch (String.valueOf(medical_insurance_num).length()){
            case 1:
                return ResultInfo.success("TJ" + "0000" + medical_insurance_num);
            case 2:
                return ResultInfo.success("TJ" + "000" + medical_insurance_num);
            case 3:
                return ResultInfo.success("TJ" + "00" + medical_insurance_num);
            case 4:
                return ResultInfo.success("TJ" + "0" + medical_insurance_num);
            case 5:
                return ResultInfo.success("TJ" + medical_insurance_num);
            default:
                return ResultInfo.success("TJ00001");
        }
    }

    /**
     * 保存编辑
     *
     * @return
     */
    @DS("master")
    public ResultInfo edit(OrganizationInfoVo vo) {
        SysUser sysUser = sysUserService.getUser();
        //删除
        if (StringUtils.isNotEmpty(vo.getIds())) {
            String[] ids = vo.getIds().split(",");
            for (int i = 0; i < ids.length; i++) {
                LambdaQueryWrapper<PackInfo> lambda = new LambdaQueryWrapper<>();
                lambda.eq(PackInfo::getIs_del, "0");
                lambda.eq(PackInfo::getOrg_id, vo.getMedical_insurance_num());
                Long idx = packInfoMapper.selectCount(lambda);
                if (idx > 0) {
                    return ResultInfo.error("该套餐已被申请，无法删除！");
                }
                OrganizationInfo organizationInfo = mapper.selectById(ids[i]);
                LambdaQueryWrapper lambdaSysUser = new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getOrg_code, organizationInfo.getMedical_insurance_num())
                        .eq(SysUser::getIs_del, "0");
                Long idxSysUser = sysUserMapper.selectCount(lambdaSysUser);
                if (idxSysUser > 0) {
                    return ResultInfo.error("该套餐已被用户绑定，无法删除！");
                }

                vo.setId(ids[i]);
                vo.setIs_del("1");
                vo.updateById();
            }
            return ResultInfo.success("删除成功");
        }

        if (StringUtils.isNotEmpty(vo.getId())) {
            vo.setUpdateUser(sysUser.getName());
            vo.setUpdateTime(DateUtil.formatDateTime(new Date()));
        } else {
            if (StringUtils.isNotEmpty(vo.getOrg_name())) {
                LambdaQueryWrapper<OrganizationInfo> lambda = new LambdaQueryWrapper<>();
                lambda.eq(OrganizationInfo::getIs_del, "0");
                lambda.eq(OrganizationInfo::getOrg_name, vo.getOrg_name());
                Long idx = mapper.selectCount(lambda);
                if (idx > 0) {
                    return ResultInfo.error("该机构名称已存在，禁止重复添加!");
                }
                LambdaQueryWrapper<OrganizationInfo> lambdaCode = new LambdaQueryWrapper<>();
                lambdaCode.eq(OrganizationInfo::getIs_del, "0");
                lambdaCode.eq(OrganizationInfo::getMedical_insurance_num, vo.getMedical_insurance_num());
                Long idxCode = mapper.selectCount(lambdaCode);
                if (idxCode > 0) {
                    return ResultInfo.error("该机编码已存在，禁止重复添加!");
                }
            }

            Integer medical_insurance_num = getMedicalInsuranceNum();
            switch (String.valueOf(medical_insurance_num).length()){
                case 1:
                    vo.setMedical_insurance_num("TJ" + "0000" + medical_insurance_num);
                    break;
                case 2:
                    vo.setMedical_insurance_num("TJ" + "000" + medical_insurance_num);
                    break;
                case 3:
                    vo.setMedical_insurance_num("TJ" + "00" + medical_insurance_num);
                    break;
                case 4:
                    vo.setMedical_insurance_num("TJ" + "0" + medical_insurance_num);
                    break;
                case 5:
                    vo.setMedical_insurance_num("TJ" + medical_insurance_num);
                    break;
                default:
                    vo.setMedical_insurance_num("TJ00001");
                    break;
            }

            vo.setCreateUser(sysUser.getName());
            vo.setOrg_code(sysUser.getOrg_code());
            vo.setCreateTime(DateUtil.formatDateTime(new Date()));
        }
        return ResultInfo.success(vo.insertOrUpdate());
    }

    private Integer getMedicalInsuranceNum() {
        LambdaQueryWrapper<OrganizationInfo> lambda = new LambdaQueryWrapper();
        lambda.orderByDesc(OrganizationInfo::getCreateTime);
        List<OrganizationInfo> list = mapper.selectList(lambda);
        String code = list.get(0).getMedical_insurance_num();
        String medical_insurance_num = Pattern.compile("[^0-9]").matcher(code).replaceAll("").trim();
        return Integer.valueOf(medical_insurance_num) + 1;
    }

    /**
     * 获取所有机构
     *
     * @return
     */
    public List<OrganizationInfo> getOrganizations() {
        LambdaQueryWrapper<OrganizationInfo> lambda = new LambdaQueryWrapper();
        return mapper.selectList(lambda);
    }


    public CivilworkerInfo getCivilworkerInfoByCardNo(CivilworkerInfo vo) {

        QueryWrapper<CivilworkerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("certno", vo.getCertno());
        return civilworkerInfoMapper.selectOne(queryWrapper);
    }

    public OrganizationInfo getEntity(String org_id) {
        return mapper.selectById(org_id);
    }

    public OrganizationInfo getEntityByOrgMedicalInsuranceNum(String medicalInsuranceNum) {
        LambdaQueryWrapper<OrganizationInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationInfo::getMedical_insurance_num, medicalInsuranceNum);
        OrganizationInfo organizationInfo = this.mapper.selectOne(wrapper);
        return organizationInfo;
    }


    /**
     * 根据类型和机构编号查询
     * Author wzn
     * Date 2022/6/22 11:44
     */
    public OrganizationInfo selectByOrgCode(OrganizationInfo organizationInfo) {
        QueryWrapper<OrganizationInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("medical_insurance_num", organizationInfo.getMedical_insurance_num());
        queryWrapper.eq("authorizationCode", organizationInfo.getAuthorizationCode());
        queryWrapper.eq("is_del", "0");
        return mapper.selectOne(queryWrapper);
    }

    /**
     *
     * @param min   Medical_insurance_num  医保编码
     * @param aCode AuthorizationCode 授权码
     * @return
     */
    public OrganizationInfo selectByOrgCode(String min,String aCode) {
        OrganizationInfo organizationInfo = new OrganizationInfo();
        organizationInfo.setMedical_insurance_num(min);
        organizationInfo.setAuthorizationCode(aCode);
        return selectByOrgCode(organizationInfo);
    }

}
