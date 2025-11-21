package com.jsdc.ybpt.service;


import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.ItemToMealMapper;
import com.jsdc.ybpt.mapper.MedicalItemMapper;
import com.jsdc.ybpt.mapper.OrganizationInfoMapper;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.ItemToMeal;
import com.jsdc.ybpt.model_check.MedicalItem;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.MedicalItemVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MedicalItemService extends BaseService<MedicalItem> {
    @Autowired
    private MedicalItemMapper mapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private ItemToMealMapper itemToMealMapper;

    @Autowired
    private OrganizationInfoMapper organizationInfoMapper;

    /**
     * 分页查询
     *
     * @return
     */
    @DS("master")
    public Page<MedicalItem> getList(MedicalItemVo vo) {
        Page<MedicalItem> autonomousMedical;
        LambdaQueryWrapper<MedicalItem> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getItem_name())) {
            lambda.like(MedicalItem::getItem_name, vo.getItem_name());
        }
        if (StringUtils.isNotEmpty(vo.getItem_no())) {
            lambda.like(MedicalItem::getItem_no, vo.getItem_no());
        }
        if (StringUtils.isNotEmpty(vo.getItem_type())) {
            lambda.eq(MedicalItem::getItem_type, vo.getItem_type());
        }
        lambda.eq(MedicalItem::getIs_del, "0");
        SysUser sysUser = sysUserService.getUser();
        //行政管理单位 只能看到本机构数据
        if (sysUser.getUser_type().equals("1")) {
            lambda.eq(MedicalItem::getOrg_id, sysUser.getOrg_code());
        }
        //体检机构 可以看到所属行政管理单位的数据
        if (sysUser.getUser_type().equals("5")) {
            LambdaQueryWrapper lambdaOrganizationInfo = new LambdaQueryWrapper<OrganizationInfo>()
                    .eq(OrganizationInfo::getMedical_insurance_num, sysUser.getOrg_code())
                    .eq(OrganizationInfo::getIs_del,"0");
            OrganizationInfo organizationInfo = organizationInfoMapper.selectOne(lambdaOrganizationInfo);
            lambda.in(MedicalItem::getOrg_id, sysUser.getOrg_code(), organizationInfo.getOrg_code());
        }
        lambda.orderByAsc(MedicalItem::getIs_generic);
        lambda.orderByDesc(MedicalItem::getCreateTime);

        autonomousMedical = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
        return autonomousMedical;
    }

    /**
     * 查询全部
     *
     * @param vo
     * @return
     */
    @DS("master")
    public List getListAll(MedicalItemVo vo) {
        SysUser sysUser = sysUserService.getUser();
        LambdaQueryWrapper<MedicalItem> lambda = new LambdaQueryWrapper();
        //行政管理单位 只能看到本机构数据
        if (sysUser.getUser_type().equals("1")) {
            lambda.eq(MedicalItem::getOrg_id, sysUser.getOrg_code());
            lambda.eq(MedicalItem::getYear,vo.getYear());
        }
        //体检机构 可以看到所属行政管理单位的数据
        if (sysUser.getUser_type().equals("5")) {
            LambdaQueryWrapper lambdaOrganizationInfo = new LambdaQueryWrapper<OrganizationInfo>()
                    .eq(OrganizationInfo::getMedical_insurance_num, sysUser.getOrg_code())
                    .eq(OrganizationInfo::getIs_del,"0");;
            OrganizationInfo organizationInfo = organizationInfoMapper.selectOne(lambdaOrganizationInfo);
            lambda.in(MedicalItem::getOrg_id, sysUser.getOrg_code(), organizationInfo.getOrg_code());
        }
        lambda.eq(MedicalItem::getItem_state, "1");//启用禁用 （禁用:0，启用:1）
        lambda.eq(MedicalItem::getIs_del, "0");

        List list = mapper.selectList(lambda);
        return list;
    }

    public List<MedicalItem> getListUnion(MedicalItemVo vo) {
        SysUser sysUser = sysUserService.getUser();
        OrganizationInfo organizationInfo =null;
        if (sysUser.getUser_type().equals("5")) {
            LambdaQueryWrapper lambdaOrganizationInfo = new LambdaQueryWrapper<OrganizationInfo>()
                    .eq(OrganizationInfo::getMedical_insurance_num, sysUser.getOrg_code())
                    .eq(OrganizationInfo::getIs_del,"0");;
           organizationInfo = organizationInfoMapper.selectOne(lambdaOrganizationInfo);
        }

        return mapper.getListUnion(sysUser,vo,organizationInfo);
    }



    /**
     * 编辑
     *
     * @return
     */
    @DS("master")
    public ResultInfo toEdit(MedicalItemVo vo) {
        return ResultInfo.success(sysUserService.getUser());
    }

    /**
     * 保存编辑
     *
     * @return
     */
    @DS("master")
    public ResultInfo edit(MedicalItemVo vo) {
        SysUser sysUser = sysUserService.getUser();
        //删除
        if (StringUtils.isNotEmpty(vo.getIds())) {
            String[] ids = vo.getIds().split(",");
            for (int i = 0; i < ids.length; i++) {
                LambdaQueryWrapper packInfoLambda = new LambdaQueryWrapper<ItemToMeal>().eq(ItemToMeal::getItem_id, ids[i]);
                Long count = itemToMealMapper.selectCount(packInfoLambda);
                if (count > 0) {
                    throw new CustomException("请先删除或解除体检套餐信息", 400);
                }
                vo.setId(ids[i]);
                vo.setIs_del("1");
                vo.updateById();
            }
            return ResultInfo.success("删除成功");
        }

        //是否通用
        if (sysUser.getUser_type().equals("1")) {
            vo.setIs_generic("1");
        } else {
            vo.setIs_generic("0");
        }

        if (StringUtils.isNotEmpty(vo.getId())) {
            //去重
            MedicalItem medicalItem = mapper.selectById(vo.getId());
            if(!medicalItem.getItem_name().equals(vo.getItem_name())){
                LambdaQueryWrapper lambdaName = new LambdaQueryWrapper<MedicalItem>().eq(MedicalItem::getItem_name, vo.getItem_name()).eq(MedicalItem::getIs_del, "0").eq(MedicalItem::getOrg_id, sysUser.getOrg_code());
                Long countName = mapper.selectCount(lambdaName);
                if (countName > 0) {
                    return ResultInfo.error("【项目名称】重复,请重新填写！");
                }
            }
            if(!medicalItem.getItem_no().equals(vo.getItem_no())){
                LambdaQueryWrapper lambdaNo = new LambdaQueryWrapper<MedicalItem>().eq(MedicalItem::getItem_no, vo.getItem_no()).eq(MedicalItem::getIs_del, "0").eq(MedicalItem::getOrg_id, sysUser.getOrg_code());
                Long countNo = mapper.selectCount(lambdaNo);
                if (countNo > 0) {
                    return ResultInfo.error("【项目编码】重复,请重新填写！");
                }
            }
            vo.setUpdateUser(sysUser.getName());
            vo.setUpdateTime(DateUtil.formatDateTime(new Date()));
        } else {
            //去重
            LambdaQueryWrapper lambdaName = new LambdaQueryWrapper<MedicalItem>().eq(MedicalItem::getItem_name, vo.getItem_name()).eq(MedicalItem::getIs_del, "0").eq(MedicalItem::getOrg_id, sysUser.getOrg_code());
            Long countName = mapper.selectCount(lambdaName);
            if (countName > 0) {
                return ResultInfo.error("【项目名称】重复,请重新填写！");
            }
            LambdaQueryWrapper lambdaNo = new LambdaQueryWrapper<MedicalItem>().eq(MedicalItem::getItem_no, vo.getItem_no()).eq(MedicalItem::getIs_del, "0").eq(MedicalItem::getOrg_id, sysUser.getOrg_code());
            Long countNo = mapper.selectCount(lambdaNo);
            if (countNo > 0) {
                return ResultInfo.error("【项目编码】重复,请重新填写！");
            }

            vo.setOrg_id(sysUser.getOrg_code());
            vo.setCreateUser(sysUser.getName());
            vo.setCreateTime(DateUtil.formatDateTime(new Date()));
        }
        return ResultInfo.success(vo.insertOrUpdate());
    }


    public Page<MedicalItem> getMedicalItemList(MedicalItemVo vo) {
        Page<MedicalItem> autonomousMedical;
        LambdaQueryWrapper<MedicalItem> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getItem_name())) {
            lambda.like(MedicalItem::getItem_name, vo.getItem_name());
        }
        if (StringUtils.isNotEmpty(vo.getItem_no())) {
            lambda.like(MedicalItem::getItem_no, vo.getItem_no());
        }
        lambda.eq(MedicalItem::getIs_del, "0");
        lambda.eq(MedicalItem::getOrg_id, vo.getOrg_id());
        autonomousMedical = mapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), lambda);
        return autonomousMedical;
    }

    public ResultInfo saveOutSide(MedicalItem mi) {
        // 新增
        LambdaQueryWrapper<MedicalItem> NameWrapper = new LambdaQueryWrapper<>();
        NameWrapper.eq(MedicalItem::getItem_name, mi.getItem_name())
                .eq(MedicalItem::getIs_del, "0")
                .eq(MedicalItem::getOrg_id, mi.getOrg_id());
        Long countName = mapper.selectCount(NameWrapper);
        if (countName > 0) {
            return ResultInfo.error("【项目名称】重复,请重新填写！");
        }
        LambdaQueryWrapper<MedicalItem> lambdaNo = new LambdaQueryWrapper<>();
        lambdaNo.eq(MedicalItem::getItem_no, mi.getItem_no())
                .eq(MedicalItem::getIs_del, "0")
                .eq(MedicalItem::getOrg_id, mi.getOrg_id());
        Long countNo = mapper.selectCount(lambdaNo);
        if (countNo > 0) {
            return ResultInfo.error("【项目编码】重复,请重新填写！");
        }

        SysDict sysDict = new SysDict();
        sysDict.setValue(mi.getItem_type());
        sysDict.setDict_type("item_type");
        sysDict.setIs_del("0");
        SysDict sysDictDB = this.sysDictService.selectByValueAndType(sysDict);
        if (null == sysDictDB) {
            return ResultInfo.error("item_type:" + mi.getItem_type() + " 不存在");
        }
        mi.setItem_type_name(sysDictDB.getLabel());
        mi.setItem_state("1");
        mi.setIs_del("0");
        mi.setCreateTime(DateUtil.formatDateTime(new Date()));
        mi.setIs_generic("0");
        mi.insert();
        return ResultInfo.success(mi);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo editOutSide(MedicalItem mi, long s) {

        long four = System.currentTimeMillis();
        System.out.println("0003");   // 0004
        System.out.println(four-s);
        if (StringUtils.isNotEmpty(mi.getItem_name())) {
            LambdaQueryWrapper<MedicalItem> NameWrapper = new LambdaQueryWrapper<>();
            NameWrapper.eq(MedicalItem::getItem_name, mi.getItem_name())
                       .eq(MedicalItem::getIs_del, "0")
                       .eq(MedicalItem::getOrg_id, mi.getOrg_id())
                       .ne(MedicalItem::getId, mi.getId());
            Long countName = mapper.selectCount(NameWrapper);
            if (countName > 0) {
                return ResultInfo.error("列表中的其他项已存在此【项目名称】,请重新填写！");
            }
        }
        System.out.println("0004");   // 0004
        long five = System.currentTimeMillis();
        System.out.println(five-four);
        if (StringUtils.isNotEmpty(mi.getItem_no())) {
            LambdaQueryWrapper<MedicalItem> ItemNoWrapper = new LambdaQueryWrapper<>();
            ItemNoWrapper.eq(MedicalItem::getItem_no, mi.getItem_no())
                         .eq(MedicalItem::getIs_del, "0")
                         .eq(MedicalItem::getOrg_id, mi.getOrg_id())
                         .ne(MedicalItem::getId, mi.getId());
            Long countNo = mapper.selectCount(ItemNoWrapper);
            if (countNo > 0) {
                return ResultInfo.error("列表中的其他项已存在此【项目编码】,请重新填写！");
            }
        }
        System.out.println("0006");   // 0006
        long six = System.currentTimeMillis();
        System.out.println(six-five);

        if (mi.updateById()) {
            return ResultInfo.success(mi);
        }
        System.out.println("0007");   // 0007
        long seven = System.currentTimeMillis();  // 0007
        System.out.println(seven-six);
        return ResultInfo.error("修改失败");
    }

    /**
     * @Description medicalItem是否属于此机构，如果不属于，则null， 属于则返回此实体
     * @param orgId,medicalItemId
     * @return
     */
    public MedicalItem belongToOrg(String orgId, String medicalItemId) {
        LambdaQueryWrapper<MedicalItem> medicalItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        medicalItemLambdaQueryWrapper.eq(MedicalItem::getOrg_id, orgId).eq(MedicalItem::getId, medicalItemId);
        return this.mapper.selectOne(medicalItemLambdaQueryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo deleteOutSide(MedicalItemVo vo) {
        for (int i = 0; i < vo.getOutsideApiIds().size(); i++) {
            // 判断ids是否属于此用户
            String id = vo.getOutsideApiIds().get(i);
            LambdaQueryWrapper<MedicalItem> medicalItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
            medicalItemLambdaQueryWrapper.eq(MedicalItem::getOrg_id, vo.getOrg_id()).eq(MedicalItem::getId, id);
            if (mapper.selectCount(medicalItemLambdaQueryWrapper) <= 0) {
                throw new CustomException("id:" + id + "不属于此用户");
            }

            LambdaQueryWrapper<ItemToMeal> itemToMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            itemToMealLambdaQueryWrapper.eq(ItemToMeal::getItem_id, id);
            ItemToMeal itemToMeal = itemToMealMapper.selectOne(itemToMealLambdaQueryWrapper);
            if (null != itemToMeal) {
                throw new CustomException("请先删除或解除体检套餐信息, 套餐id为:" + itemToMeal.getPack_id());
            }
            vo.setId(id);
            vo.setIs_del("1");
            vo.setUpdateTime(DateUtil.formatDateTime(new Date()));

            if (!vo.updateById()) {
                throw new CustomException("批量删除失败");
            }
        }
        return ResultInfo.success("删除成功");
    }


    public List<MedicalItem> getMedicalItemList1(MedicalItemVo vo) {
        LambdaQueryWrapper<MedicalItem> lambda = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getItem_name())) {
            lambda.like(MedicalItem::getItem_name, vo.getItem_name());
        }
        if (StringUtils.isNotEmpty(vo.getItem_no())) {
            lambda.like(MedicalItem::getItem_no, vo.getItem_no());
        }
        lambda.eq(MedicalItem::getIs_del, "0");
        lambda.eq(MedicalItem::getOrg_id, vo.getOrg_id());
        List<MedicalItem> autonomousMedical = mapper.selectList(lambda);
        LambdaQueryWrapper<MedicalItem> lambda1 = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(vo.getItem_name())) {
            lambda1.like(MedicalItem::getItem_name, vo.getItem_name());
        }
        if (StringUtils.isNotEmpty(vo.getItem_no())) {
            lambda1.like(MedicalItem::getItem_no, vo.getItem_no());
        }
        lambda1.eq(MedicalItem::getIs_del, "0");
        lambda1.eq(MedicalItem::getIs_generic,"1");
        autonomousMedical.addAll(mapper.selectList(lambda1));
        return autonomousMedical;
    }


}
