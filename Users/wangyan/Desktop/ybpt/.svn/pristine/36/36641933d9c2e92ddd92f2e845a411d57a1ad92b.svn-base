package com.jsdc.ybpt.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.*;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.*;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.OrganizationInfoVo;
import com.jsdc.ybpt.vo.PackInfoChartVo;
import com.jsdc.ybpt.vo.PackInfoVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PackInfoService extends BaseService<PackInfo> {
    @Autowired
    private PackInfoMapper mapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PhysExamConfigMapper physExamConfigMapper;
    @Autowired
    private ItemToMealMapper itemToMealMapper;
    @Autowired
    private MedicalItemMapper medicalItemMapper;

    @Autowired
    private OrganizationInfoMapper organizationInfoMapper;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private PersonSubscribeRecordService personSubscribeRecordService;

    /**
     * 分页查询
     *
     * @return
     */
    @DS("master")
    public Page<PackInfo> getList(PackInfoVo vo) {
        Page<PackInfo> autonomousMedical;
        LambdaQueryWrapper<PackInfo> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getPack_source())) {
            lambda.eq(PackInfo::getPack_source, vo.getPack_source());
        }
        if (StringUtils.isNotEmpty(vo.getPack_name())) {
            lambda.like(PackInfo::getPack_name, vo.getPack_name());
        }
        if (StringUtils.isNotEmpty(vo.getPack_year())) {
            lambda.eq(PackInfo::getPack_year, vo.getPack_year());
        }
        if (StringUtils.isNotEmpty(vo.getOrg_id())) {
            lambda.eq(PackInfo::getOrg_id, vo.getOrg_id());
        }
        if (StringUtils.isNotEmpty(vo.getIf_open())) {
            lambda.eq(PackInfo::getIf_open, vo.getIf_open());
        }
        if (StringUtils.isNotEmpty(vo.getIf_use())) {
            lambda.eq(PackInfo::getIf_use, vo.getIf_use());
        }
        lambda.orderByDesc(PackInfo::getCreateTime);
        SysUser sysUser = sysUserService.getUser();
        if ("5".equals(sysUser.getUser_type()) && "1".equals(vo.getPack_source())) { //5：体检机构  体检机构只能展示本机构的数据
            lambda.eq(PackInfo::getOrg_id, sysUser.getOrg_code());
        }

        lambda.eq(PackInfo::getIs_del, "0");
        lambda.orderByDesc(PackInfo::getCreateTime);
        autonomousMedical = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
        return autonomousMedical;
    }

    /**
     * 获取已机构套餐上架/未上架
     *
     * @param vo
     * @return
     */
    @DS("master")
    public Page<PackInfo> getOrgList(PackInfoVo vo) {
        Page<PackInfo> autonomousMedical;
        LambdaQueryWrapper<PackInfo> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getPack_source())) {
            lambda.eq(PackInfo::getPack_source, vo.getPack_source());
        }
        if (StringUtils.isNotEmpty(vo.getPack_name())) {
            lambda.like(PackInfo::getPack_name, vo.getPack_name());
        }
        if (StringUtils.isNotEmpty(vo.getPack_year())) {
            lambda.eq(PackInfo::getPack_year, vo.getPack_year());
        }
        if (StringUtils.isNotEmpty(vo.getIf_open())) {
            lambda.eq(PackInfo::getIf_open, vo.getIf_open());
        }
        lambda.eq(PackInfo::getIs_del, "0");
        SysUser sysUser = sysUserService.getUser();
        if ("5".equals(sysUser.getUser_type()) && "1".equals(vo.getPack_source())) { //5：体检机构  体检机构只能展示本机构的数据
            lambda.eq(PackInfo::getOrg_id, sysUser.getOrg_code());
            lambda.eq(PackInfo::getStatus, "1");//审核通过的
        }
        autonomousMedical = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
        return autonomousMedical;
    }


    /**
     * 编辑
     *
     * @return
     */
    @DS("master")
    public ResultInfo toEdit(PackInfoVo vo) {
        SysUser sysUser = sysUserService.getUser();
        Map map = new HashMap();
        map.put("sysUser", sysUser);
        if (StringUtils.isNotEmpty(vo.getId())) {
            LambdaQueryWrapper<ItemToMeal> lambdaItemToMeal = new LambdaQueryWrapper();
            lambdaItemToMeal.eq(ItemToMeal::getPack_id, vo.getId());
            List<ItemToMeal> list = itemToMealMapper.selectList(lambdaItemToMeal);
            ArrayList<MedicalItem> item_to_meal = new ArrayList<>();
            for (ItemToMeal itemToMeal : list) {
                MedicalItem medicalItem = medicalItemMapper.selectById(itemToMeal.getItem_id());
                item_to_meal.add(medicalItem);
            }
            List<MedicalItem> collect = item_to_meal.stream().sorted(new Comparator<MedicalItem>() {
                @Override
                public int compare(MedicalItem o1, MedicalItem o2) {
                    int i1 = Integer.parseInt(o1.getIs_generic());
                    int i2 = Integer.parseInt(o2.getIs_generic());
                    return i1 - i2;
                }
            }).collect(Collectors.toList());

            map.put("item_to_meal", collect);
        }

        return ResultInfo.success(map);
    }

    /**
     * 保存编辑
     *
     * @return
     */
    @DS("master")
    public ResultInfo edit(PackInfoVo vo) {
        SysUser sysUser = sysUserService.getUser();
        //批量审核
        if (StringUtils.isNotEmpty(vo.getVerify_ids())) {
            String[] ids = vo.getVerify_ids().split(",");
            for (int i = 0; i < ids.length; i++) {
                PackInfo packInfo = new PackInfo();
                packInfo.setId(ids[i]);
                packInfo.setStatus(vo.getStatus());
                packInfo.setVerify_reason(vo.getVerify_reason());
                packInfo.setVerify_time(DateUtil.formatDateTime(new Date()));
                packInfo.setVerify_user(sysUser.getUsername());
                packInfo.setUpdateUser(sysUser.getUsername());
                if (!packInfo.updateById()) {
                    return ResultInfo.error("审核失败，请检查数据后刷新页面重新审核！");
                }
            }
            return ResultInfo.success("审核成功！");
        }
        //批量删除
        if (StringUtils.isNotEmpty(vo.getIds())) {
            String[] ids = vo.getIds().split(",");
            for (int i = 0; i < ids.length; i++) {
                //清空项目套餐关联信息表
                LambdaQueryWrapper itemToMealLambda = new LambdaQueryWrapper<ItemToMeal>().eq(ItemToMeal::getPack_id, ids[i]);
                List<ItemToMeal> itemToMeals = itemToMealMapper.selectList(itemToMealLambda);
                for (ItemToMeal itemToMeal : itemToMeals) {
                    itemToMeal.deleteById();
                }
                vo.setId(ids[i]);
                vo.setIs_del("1");
                vo.updateById();
            }
            return ResultInfo.success("删除成功！");
        }

        if (StringUtils.isNotEmpty(vo.getId())) {
            if (vo.getItem_to_meal() != null) {
                itemToMealMapper.delete(new LambdaQueryWrapper<ItemToMeal>().eq(ItemToMeal::getPack_id, vo.getId()));
                for (String item_id : vo.getItem_to_meal()) {
                    ItemToMeal itemToMeal = new ItemToMeal();
                    itemToMeal.setPack_id(vo.getId());
                    itemToMeal.setItem_id(item_id);
                    itemToMeal.insert();
                }
            }

            //驳回重新编辑 【待审】
            if (StringUtils.isNotEmpty(vo.getStatus()) && ("2").equals(vo.getStatus())) {
                vo.setStatus("0");
            }

            vo.setUpdateUser(sysUser.getName());
            vo.setUpdateTime(DateUtil.formatDateTime(new Date()));
        } else {
            if (StringUtils.isNotEmpty(vo.getPack_year())) {
                LambdaQueryWrapper lambda = new LambdaQueryWrapper<PhysExamConfig>().eq(PhysExamConfig::getIs_del, "0").eq(PhysExamConfig::getYear, vo.getPack_year());
                Long idx = physExamConfigMapper.selectCount(lambda);
                if (idx == 1) {
                    PhysExamConfig physExamConfig = physExamConfigMapper.selectOne(lambda);
                    vo.setPhys_exam_id(physExamConfig.getId());
                } else {
                    return ResultInfo.error("必须先添加套餐年份内的体检标准！");
                }
            }
            if (StringUtils.isNotEmpty(vo.getPack_name())) {
                LambdaQueryWrapper lambda = new LambdaQueryWrapper<PackInfo>().eq(PackInfo::getIs_del, "0").eq(PackInfo::getPack_year, vo.getPack_year()).eq(PackInfo::getPack_name, vo.getPack_name());
                Long idx = mapper.selectCount(lambda);
                if (idx > 0) {
                    return ResultInfo.error("同年内有重复套餐,请勿重复添加！");
                }
            }

            String UUID = IdUtil.simpleUUID();
            vo.setId(UUID);
            if (vo.getItem_to_meal() != null) {
                itemToMealMapper.delete(new LambdaQueryWrapper<ItemToMeal>().eq(ItemToMeal::getPack_id, UUID));
                for (String item_id : vo.getItem_to_meal()) {
                    ItemToMeal itemToMeal = new ItemToMeal();
                    itemToMeal.setPack_id(UUID);
                    itemToMeal.setItem_id(item_id);
                    itemToMeal.insert();
                }
            }

            vo.setOrg_id(sysUser.getOrg_code());
            vo.setOrg_name(sysUser.getOrg_name());

            vo.setCreateUser(sysUser.getName());
            vo.setCreateTime(DateUtil.formatDateTime(new Date()));
        }
        return ResultInfo.success(vo.insertOrUpdate());
    }

    public List<MedicalItem> getEntity(PackInfoVo vo) {
        LambdaQueryWrapper<ItemToMeal> lambdaItemToMeal = new LambdaQueryWrapper();
        lambdaItemToMeal.eq(ItemToMeal::getPack_id, vo.getId());
        List<ItemToMeal> list = itemToMealMapper.selectList(lambdaItemToMeal);
        List<MedicalItem> medicalItemList = new ArrayList();
        for (ItemToMeal itemToMeal : list) {
            MedicalItem medicalItem = medicalItemMapper.selectById(itemToMeal.getItem_id());
            medicalItemList.add(medicalItem);
        }

        List<MedicalItem> collect = medicalItemList.stream().sorted(new Comparator<MedicalItem>() {
            @Override
            public int compare(MedicalItem o1, MedicalItem o2) {
                int i1 = Integer.parseInt(o1.getIs_generic());
                int i2 = Integer.parseInt(o2.getIs_generic());
                return i1 - i2;
            }
        }).collect(Collectors.toList());


        return collect;
    }

    /**
     * 套餐上下架
     *
     * @param ids
     * @param flag
     */
    public boolean setOrgIfOpen(String ids, boolean flag) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            LambdaQueryWrapper lambda = new LambdaQueryWrapper<PackInfo>().eq(PackInfo::getId, id);
            PackInfo packInfoTemp = mapper.selectOne(lambda);
            if (flag) {//上架
                packInfoTemp.setIf_open("1");
            } else {//下架
                packInfoTemp.setIf_open("0");
            }
            packInfoTemp.updateById();
        }
        return true;
    }

    @DS("master")
    public Map<String, String> getPackageChart(String year, String orgId) {
        //套餐来源 (机构:1，通用:2 , 总计:3)
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "0");
        map.put("2", "0");
        map.put("3", "0");
        SysUser sysUser = sysUserService.getUser();
        OrganizationInfo organizationInfo = null;
        if (sysUser.getUser_type().equals("5")) {
            LambdaQueryWrapper lambdaOrganizationInfo = new LambdaQueryWrapper<OrganizationInfo>()
                    .eq(OrganizationInfo::getMedical_insurance_num, sysUser.getOrg_code())
                    .eq(OrganizationInfo::getIs_del, "0");
            ;
            organizationInfo = organizationInfoMapper.selectOne(lambdaOrganizationInfo);
        }


        List<PackInfoChartVo> packInfoRatioVoList = this.mapper.getPackInfoRatio(year, orgId, sysUser, organizationInfo);
        for (PackInfoChartVo vo : packInfoRatioVoList) {
            map.put(vo.getPackSource(), vo.getNum());
        }
        int org = Integer.parseInt(map.get("1"));
        int gen = Integer.parseInt(map.get("2"));
        map.put("3", String.valueOf(org + gen));
        return map;
    }

    // 医保中心或者体检机构获取套餐年份列表
    public List<String> getYearList() {
        SysUser user = sysUserService.getUser();
        OrganizationInfo organizationInfo = null;
        if (user.getUser_type().equals("5")) {
            LambdaQueryWrapper lambdaOrganizationInfo = new LambdaQueryWrapper<OrganizationInfo>()
                    .eq(OrganizationInfo::getMedical_insurance_num, user.getOrg_code())
                    .eq(OrganizationInfo::getIs_del, "0");
            ;
            organizationInfo = organizationInfoMapper.selectOne(lambdaOrganizationInfo);
        }


        // 获取当前用户的org_code
        String org_code = user.getOrg_code();
        QueryWrapper<PackInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT PACK_YEAR").
                eq("STATUS", "1").
                eq("IS_DEL", "0").
                in("5".equals(user.getUser_type()), "org_id", Arrays.asList(org_code, organizationInfo != null ? organizationInfo.getOrg_code() : ""))
                .orderByDesc("PACK_YEAR");

        if (user.getUser_type().equals("5")) {

        }

        List<PackInfo> packInfos = mapper.selectList(queryWrapper);
        ArrayList<String> yearList = new ArrayList<>();
        packInfos.forEach((packInfo -> {
            yearList.add(packInfo.getPack_year());
        }));
        return yearList;
    }

    // 获取符合条件的组织机构的列表
    public List<PackInfo> getOrgList(String packYear, String packSource) {
        QueryWrapper<PackInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT ORG_ID, ORG_NAME").eq("IF_OPEN", "1").eq("IS_DEL", "0").eq("IF_USE", "1").eq("PACK_YEAR", packYear).eq(!StringUtils.isEmpty(packSource), "PACK_SOURCE", packSource);
        return mapper.selectList(queryWrapper);
    }

    public List<PackInfo> getPackInfoList(String packYear, String packSource, String orgId) {
        LambdaQueryWrapper<PackInfo> packInfoWrapper = new LambdaQueryWrapper<>();
        packInfoWrapper.eq(PackInfo::getPack_year, packYear);
        if (StringUtils.isNotEmpty(orgId)) {
            packInfoWrapper.eq(PackInfo::getOrg_id, orgId);
        }
        packInfoWrapper.eq(PackInfo::getPack_source, packSource);
        packInfoWrapper.eq(PackInfo::getIf_use, "1");
        packInfoWrapper.eq(PackInfo::getStatus, "1");
        packInfoWrapper.eq(PackInfo::getIs_del, "0");
        List<PackInfo> packInfoList = mapper.selectList(packInfoWrapper);
        return packInfoList;
    }


    public Page<PackInfo> getPackInfoList(PackInfoVo vo) {
        Page<PackInfo> autonomousMedical;
        LambdaQueryWrapper<PackInfo> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getPack_source())) {
            lambda.eq(PackInfo::getPack_source, vo.getPack_source());
        }
        if (StringUtils.isNotEmpty(vo.getPack_name())) {
            lambda.like(PackInfo::getPack_name, vo.getPack_name());
        }
        if (StringUtils.isNotEmpty(vo.getPack_year())) {
            lambda.eq(PackInfo::getPack_year, vo.getPack_year());
        }
        if (StringUtils.isNotEmpty(vo.getOrg_id())) {
            lambda.eq(PackInfo::getOrg_id, vo.getOrg_id());
        }
        lambda.orderByDesc(PackInfo::getCreateTime);
        if ("1".equals(vo.getPack_source())) { //5：体检机构  体检机构只能展示本机构的数据
            lambda.eq(PackInfo::getOrg_id, vo.getOrgCode());
        }
        lambda.eq(PackInfo::getIs_del, "0");
        lambda.orderByDesc(PackInfo::getCreateTime);
        autonomousMedical = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
        return autonomousMedical;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultInfo orgSaveOutSide(PackInfoVo vo) {
        if (!this.isPhysExamConfig(vo)) {
            return ResultInfo.error("医保局必须先添加" + vo.getPack_year() + "年份内的套餐体检标准才可新增套餐。或者体检标准大于1，非法");
        }

        LambdaQueryWrapper<PackInfo> packInfoWrapper = new LambdaQueryWrapper<>();
        packInfoWrapper.eq(PackInfo::getIs_del, "0")
                .eq(PackInfo::getOrg_id, vo.getOrg_id())
                .eq(PackInfo::getPack_year, vo.getPack_year())
                .eq(PackInfo::getPack_name, vo.getPack_name());
        Long packInfoCount = this.mapper.selectCount(packInfoWrapper);
        if (packInfoCount > 0) {
            return ResultInfo.error("同年内已有相同名称的套餐,请勿重复添加！");
        }

        List<String> medicalItemList = vo.getItem_to_meal();
        for (String item_id : medicalItemList) {
            LambdaQueryWrapper<MedicalItem> itemToMealWrapper = new LambdaQueryWrapper<>();
            itemToMealWrapper.eq(MedicalItem::getId, item_id);
            itemToMealWrapper.eq(MedicalItem::getIs_generic, "0");
            itemToMealWrapper.eq(MedicalItem::getOrg_id, vo.getOrg_id());
            if (this.medicalItemMapper.selectCount(itemToMealWrapper) != 1) {
                return ResultInfo.error("无效的体检项目id:" + item_id + ", 请添加自己的体检项目id,医保局体检项目会自动加入");
            }
        }

        // 根据年份获取医保局体检项
        List<String> medicalItemListOfYB = this.medicalItemMapper.getMedicalItemListOfYbByPackYear(vo.getPack_year());
        medicalItemList.addAll(medicalItemListOfYB);
        String UUID = IdUtil.simpleUUID();
        vo.setId(UUID);
        vo.setIf_open("1");
        vo.setIf_use("1");
        vo.setIs_del("0");
        vo.setCreateTime(DateUtil.formatDateTime(new Date()));
        vo.setStatus("0");
        vo.insert();

        for (String itemId : medicalItemList) {
            ItemToMeal itemToMeal = new ItemToMeal();
            itemToMeal.setPack_id(UUID);
            itemToMeal.setItem_id(itemId);
            itemToMeal.insert();
        }
        return ResultInfo.success(vo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo orgEditOutSide(PackInfoVo vo) {
        LambdaQueryWrapper<PackInfo> packInfoWrapper = new LambdaQueryWrapper<>();
        packInfoWrapper.eq(PackInfo::getId, vo.getId()).eq(PackInfo::getOrg_id, vo.getOrg_id());
        PackInfo packInfo = this.mapper.selectOne(packInfoWrapper);
        if (null == packInfo) {
            return ResultInfo.error("此用户没有编辑该套餐id:" + vo.getId() + "的权限");
        }

        // 有尚未完成的预约记录， 不能修改
        Long countPsr = this.personSubscribeRecordService.getValidPsrByPackId(vo.getId());
        if (countPsr > 0) {
            return ResultInfo.error("该套餐正在被使用, 不可修改");
        }

        if (!("1").equals(vo.getIf_open())) {
            vo.setIf_open("0");
        }

        // 机构套餐名不可重复
        if (StringUtils.isNotEmpty(vo.getPack_name())) {
            LambdaQueryWrapper<PackInfo> otherPackInfoWrapper = new LambdaQueryWrapper<>();
            otherPackInfoWrapper.eq(PackInfo::getIs_del, "0").eq(PackInfo::getPack_name, vo.getPack_name()).ne(PackInfo::getId, vo.getId());
            Long packInfoCount = this.mapper.selectCount(otherPackInfoWrapper);
            if (packInfoCount > 0) {
                return ResultInfo.error("已有相同名称的套餐,请重新编辑！");
            }
        }

        List<String> medicalItemList = vo.getItem_to_meal();
        // 如果体检项不为空，1.删除旧的体检项，2.之后再重新插入新的体检项
        if (medicalItemList != null) {
            for (String item_id : medicalItemList) {
                LambdaQueryWrapper<MedicalItem> medicalItemWrapper = new LambdaQueryWrapper<>();
                medicalItemWrapper.eq(MedicalItem::getId, item_id);
                medicalItemWrapper.eq(MedicalItem::getIs_del, "0");
                // 如果体检项不存在则拒绝插入
                if (this.medicalItemMapper.selectCount(medicalItemWrapper) != 1) {
                    return ResultInfo.error("无效的体检项目id:" + item_id + ", 请编辑自己的体检项目id,医保局体检项目会自动加入");
                }
            }
            // 1.删除旧的体检项
            LambdaQueryWrapper<ItemToMeal> itemToMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            itemToMealLambdaQueryWrapper.eq(ItemToMeal::getPack_id, vo.getId());
            this.itemToMealMapper.delete(itemToMealLambdaQueryWrapper);

            // 获取医保体检项列表
            List<String> medicalItemListOfYB = this.medicalItemMapper.getMedicalItemListOfYbByPackYear(packInfo.getPack_year());
            medicalItemList.addAll(medicalItemListOfYB);
            for (String itemId : medicalItemList) {
                ItemToMeal itemToMeal = new ItemToMeal();
                itemToMeal.setPack_id(vo.getId());
                itemToMeal.setItem_id(itemId);
                itemToMeal.insert();
            }
        }

        vo.setUpdateTime(DateUtil.formatDateTime(new Date()));
        if (vo.updateById()) {
            return ResultInfo.success(vo);
        }
        return ResultInfo.error("修改失败");
    }


    private boolean isPhysExamConfig(PackInfoVo vo) {
        LambdaQueryWrapper<PhysExamConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PhysExamConfig::getIs_del, "0").eq(PhysExamConfig::getYear, vo.getPack_year());
        Long idx = physExamConfigMapper.selectCount(wrapper);
        if (idx == 1) {
            PhysExamConfig physExamConfig = physExamConfigMapper.selectOne(wrapper);
            vo.setPhys_exam_id(physExamConfig.getId());
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo orgDeleteOutSide(PackInfoVo vo) {
        for (String packInfoId : vo.getOutsideApiIds()) {
            LambdaQueryWrapper<PackInfo> packInfoWrapper = new LambdaQueryWrapper<>();
            packInfoWrapper.eq(PackInfo::getId, packInfoId).eq(PackInfo::getOrg_id, vo.getOrg_id());
            PackInfo packInfo = this.mapper.selectOne(packInfoWrapper);
            if (null == packInfo) {
                throw new CustomException("参数异常:id:" + packInfoId + "无效或者此用户没有操作权限", 400);
            }
            if (("1").equals(packInfo.getStatus())) {
                throw new CustomException("不能删除已经通过审核的套餐:" + packInfoId, 400);
            }
            //清空item_to_meal中间表对应记录
            LambdaQueryWrapper<ItemToMeal> itemToMealLambda = new LambdaQueryWrapper<>();
            itemToMealLambda.eq(ItemToMeal::getPack_id, packInfoId);
            List<ItemToMeal> itemToMeals = itemToMealMapper.selectList(itemToMealLambda);
            if (null != itemToMeals) {
                for (ItemToMeal itemToMeal : itemToMeals) {
                    itemToMeal.deleteById();
                }
                vo.setId(packInfoId);
                vo.setIs_del("1");
                if (!vo.updateById()) {
                    return ResultInfo.error("删除失败");
                }
            }
        }
        return ResultInfo.success();
    }

    public List<PackInfo> getPackInfoList1(PackInfoVo vo) {
        LambdaQueryWrapper<PackInfo> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getPack_source())) {
            lambda.eq(PackInfo::getPack_source, vo.getPack_source());
        }
        if (StringUtils.isNotEmpty(vo.getPack_name())) {
            lambda.like(PackInfo::getPack_name, vo.getPack_name());
        }
        if (StringUtils.isNotEmpty(vo.getPack_year())) {
            lambda.eq(PackInfo::getPack_year, vo.getPack_year());
        }
        if (StringUtils.isNotEmpty(vo.getOrg_id())) {
            lambda.eq(PackInfo::getOrg_id, vo.getOrg_id());
        }
        lambda.orderByDesc(PackInfo::getCreateTime);
        if ("1".equals(vo.getPack_source())) { //5：体检机构  体检机构只能展示本机构的数据
            lambda.eq(PackInfo::getOrg_id, vo.getOrgCode());
        }
        lambda.eq(PackInfo::getIs_del, "0");
        lambda.orderByDesc(PackInfo::getCreateTime);
        List<PackInfo> autonomousMedical = mapper.selectList(lambda);
        List<MedicalItem> medicalItems = this.medicalItemMapper.findMedicalItemAll();
        for (PackInfo packInfo : autonomousMedical) {
            List<MedicalItem> lists = new ArrayList<>();
            for (MedicalItem medicalItem : medicalItems) {
                if (packInfo.getId().equals(medicalItem.getPid())) {
                    lists.add(medicalItem);
                }
            }
            packInfo.setItem(lists);
        }
        return autonomousMedical;
    }

    public List<PackInfo> getOrgList1(String packYear, String packSource) {
        List<PackInfo> packInfos = this.mapper.getOrgList(packYear, packSource);
        return packInfos;
    }

    public boolean isInApplicableScope(PackInfoVo vo) {
        SysDict sysDict = new SysDict();
        sysDict.setValue(vo.getApplicable_scope());
        sysDict.setDict_type("applicable_scope");
        sysDict.setIs_del("0");
        SysDict sysDictDB = this.sysDictService.selectByValueAndType(sysDict);
        return null != sysDictDB;
    }

    public List<OrganizationInfo> getOrgListPack(OrganizationInfoVo vo) {
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
        list = organizationInfoMapper.selectList(lambda);


        list.forEach(x -> {
            if (StringUtils.isNotEmpty(x.getMedical_insurance_num())) {
                List<PackInfo> packInfos = mapper.selectList(Wrappers.<PackInfo>lambdaQuery().
                        eq(PackInfo::getOrg_id, x.getMedical_insurance_num()).eq(PackInfo::getPack_source, "1").eq(PackInfo::getIs_del, "0").
                        eq(PackInfo::getIf_open, "1").eq(PackInfo::getIf_use, "1").eq(PackInfo::getStatus, "1"));
                if (!packInfos.isEmpty()) {
                    x.setIs_select("1");
                }
            }
        });
        return list;
    }

    public List<PackInfo> getDeptList(PackInfoVo vo) {
        LambdaQueryWrapper<PackInfo> packInfoWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(vo.getOrg_id())) {
            packInfoWrapper.eq(PackInfo::getOrg_id, vo.getOrg_id());
        }
        if (StringUtils.isNotEmpty(vo.getIf_open())) {
            packInfoWrapper.eq(PackInfo::getIf_open, vo.getIf_open());
        }
        packInfoWrapper.eq(PackInfo::getPack_source, vo.getPack_source());
        packInfoWrapper.eq(PackInfo::getIf_use, "1");
        packInfoWrapper.eq(PackInfo::getStatus, "1");
        packInfoWrapper.eq(PackInfo::getIs_del, "0");
        List<PackInfo> packInfoList = mapper.selectList(packInfoWrapper);
        return packInfoList;
    }
}
