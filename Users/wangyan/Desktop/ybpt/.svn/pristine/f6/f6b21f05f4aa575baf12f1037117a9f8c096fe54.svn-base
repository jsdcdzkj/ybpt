package com.jsdc.ybpt.service;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.assessment.*;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.*;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KhTaskManageService extends BaseService<KhTaskManage> {

    @Autowired
    private KhTaskManageMapper khTaskManageMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;

    @Autowired
    private KHManageMapper khManageMapper;

    @Autowired
    private KhAssessmentMapper khAssessmentMapper ;

    @Autowired
    private KhAssessmentDetailMapper khAssessmentDetailMapper ;
    @Autowired
    private BlacklistMapper blacklistMapper  ;

    /**
     * 新增考核任务
     * Author wzn
     * Date 2022/11/21 14:47
     */
    public void addTaskMange(KhTaskManage khTaskManage) {

        boolean check = this.checkOnly(khTaskManage);
        if (!check) {
            throw new CustomException("禁止重复添加！");
        }
        khTaskManage.setExpiration_time(khTaskManage.getExpiration_time().substring(0,10));
        SysUser sysUser = sysUserService.getUser();
        khTaskManage.setId(IdUtil.simpleUUID());
        khTaskManage.setAdmdvs(sysUser.getOrg_code());
        khTaskManage.setPublish_status("0");
        khTaskManage.setCreateUser(sysUser.getUsername());
        khTaskManage.setCreateTime(new Date());
        khTaskManage.setIs_del("0");
        khTaskManage.insert();

        //往黑名单加数据
        if(CollectionUtils.isNotEmpty(khTaskManage.getMedicalCodeList())){
            for(String b:khTaskManage.getMedicalCodeList()){
                Blacklist blacklist = new Blacklist() ;
                blacklist.setId(IdUtil.simpleUUID());
                blacklist.setTaskManageId(khTaskManage.getId());
                blacklist.setFixmedins_code(b);
                blacklist.insert() ;
            }
        }
    }


    public List<Map<String, String>> getAll(KhTaskManage khTaskManage) {
        SysUser sysUser = sysUserService.getUser();
        //查该医保账号下的符合条件的所有药店/机构
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        if("1".equals(khTaskManage.getOrg_type())){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("fix_blng_admdvs", sysUser.getOrg_code());
            queryWrapper.eq("fixmedins_type", khTaskManage.getOrg_type());
            queryWrapper.eq("category", khTaskManage.getCategory());
            queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
            queryWrapper.eq("is_del", "0");
        }else{
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("fix_blng_admdvs", sysUser.getOrg_code());
            queryWrapper.eq("fixmedins_type", khTaskManage.getOrg_type());
            queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
            queryWrapper.eq("is_del", "0");
        }

        List<FixmedinsB> fixmedinsBList = fixmedinsBMapper.selectList(queryWrapper);
        List<Map<String, String>> res = fixmedinsBList.stream().map(x -> {
            Map<String, String> map = new HashMap<>();
            map.put("key", x.getFixmedins_code());
            map.put("label", x.getFixmedins_name());
            return map;
        }).collect(Collectors.toList());
        return res;

    }

    /**
     * 修改考核任务
     * Author wzn
     * Date 2022/11/21 15:04
     */
    public void updTaskMange(KhTaskManage khTaskManage) {
        KhTaskManage khTaskManage1 = khTaskManageMapper.selectById(khTaskManage.getId());
        if (null != khTaskManage1) {
            if (!"0".equals(khTaskManage1.getPublish_status())) {
                throw new CustomException("禁止修改！");
            }
        }
        boolean check = this.checkOnly(khTaskManage);
        if (!check) {
            throw new CustomException("禁止重复添加！");
        }
        SysUser sysUser = sysUserService.getUser();
        khTaskManage.setUpdateUser(sysUser.getUsername());
        khTaskManage.setUpdateTime(new Date());
        khTaskManage.updateById();

//删除之前黑名单数据
        QueryWrapper<Blacklist> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("taskManageId",khTaskManage.getId()) ;
        blacklistMapper.delete(queryWrapper) ;

        //往黑名单加数据
        if(CollectionUtils.isNotEmpty(khTaskManage.getMedicalCodeList())){
            for(String b:khTaskManage.getMedicalCodeList()){
                Blacklist blacklist = new Blacklist() ;
                blacklist.setId(IdUtil.simpleUUID());
                blacklist.setTaskManageId(khTaskManage.getId());
                blacklist.setFixmedins_code(b);
                blacklist.insert() ;
            }
        }
    }

    /**
     * 删除考核任务
     * Author wzn
     * Date 2022/11/21 15:08
     */
    public void delTaskMange(String id) {
        KhTaskManage khTaskManage1 = khTaskManageMapper.selectById(id);
        if (null != khTaskManage1) {
            if (!"0".equals(khTaskManage1.getPublish_status())) {
                throw new CustomException("已发布,禁止删除！");
            }
        }
        khTaskManage1.setIs_del("1");
        khTaskManageMapper.updateById(khTaskManage1);
    }

    public List<FixmedinsB> getOrg(String id){
        SysUser sysUser = sysUserService.getUser();
        KhTaskManage khTaskManage = khTaskManageMapper.selectById(id);
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        if("1".equals(khTaskManage.getOrg_type())){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("fix_blng_admdvs", sysUser.getOrg_code());
            queryWrapper.eq("fixmedins_type", khTaskManage.getOrg_type());
            queryWrapper.eq("category", khTaskManage.getCategory());
            queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
            queryWrapper.eq("is_del", "0");
        }else{
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("fix_blng_admdvs", sysUser.getOrg_code());
            queryWrapper.eq("fixmedins_type", khTaskManage.getOrg_type());
            queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
            queryWrapper.eq("is_del", "0");
        }
        ArrayList arrayList = new ArrayList();
        QueryWrapper<Blacklist> blacklistQueryWrapper = new QueryWrapper<>() ;
        blacklistQueryWrapper.eq("taskManageId",khTaskManage.getId()) ;
        List<Blacklist> blacklistList = blacklistMapper.selectList(blacklistQueryWrapper) ;
        if(CollectionUtils.isNotEmpty(blacklistList)){
            for(Blacklist b:blacklistList){
                arrayList.add(b.getFixmedins_code());
            }
            queryWrapper.notIn("fixmedins_code",arrayList);
        }

        List<FixmedinsB> fixmedinsBList = fixmedinsBMapper.selectList(queryWrapper);
        return fixmedinsBList ;
    }

    /**
     * 发布考核任务
     * Author wzn
     * Date 2022/11/21 15:50
     */
    public void publishTaskMange(String id) {
        KhTaskManage khTaskManage = khTaskManageMapper.selectById(id);
        if (null != khTaskManage) {
//            if (!"0".equals(khTaskManage.getPublish_status())) {
//                throw new CustomException("发布状态异常！");
//            }
            if (StringUtils.isEmpty(khTaskManage.getAssessment_id())) {
                throw new CustomException("未选择考核单，禁止发布！");
            }
            SysUser sysUser = sysUserService.getUser();

            if (!"1".equals(sysUser.getUser_type())) {
                throw new CustomException("仅限管理员发布考核任务！");
            }

            //查该医保账号下的符合条件的所有药店/机构
            QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
            if("1".equals(khTaskManage.getOrg_type())){
                queryWrapper.eq("fix_blng_admdvs", sysUser.getOrg_code());
                queryWrapper.eq("fixmedins_type", khTaskManage.getOrg_type());
                queryWrapper.eq("category", khTaskManage.getCategory());
                queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
                queryWrapper.eq("is_del", "0");
            }else{
                queryWrapper.eq("fix_blng_admdvs", sysUser.getOrg_code());
                queryWrapper.eq("fixmedins_type", khTaskManage.getOrg_type());
                queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
                queryWrapper.eq("is_del", "0");
            }

            ArrayList arrayList = new ArrayList();
            QueryWrapper<Blacklist> blacklistQueryWrapper = new QueryWrapper<>() ;
            blacklistQueryWrapper.eq("taskManageId",khTaskManage.getId()) ;
            List<Blacklist> blacklistList = blacklistMapper.selectList(blacklistQueryWrapper) ;
            if(CollectionUtils.isNotEmpty(blacklistList)){
                for(Blacklist b:blacklistList){
                    arrayList.add(b.getFixmedins_code());
                }
                queryWrapper.notIn("fixmedins_code",arrayList);
            }

            List<FixmedinsB> fixmedinsBList = fixmedinsBMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(fixmedinsBList)) {
                //向机构下发考核任务
                for (FixmedinsB f : fixmedinsBList) {
                    //先查询有无考核记录
                    QueryWrapper<KHManage> queryWrapper1 = new QueryWrapper<>();
                    if ("1".equals(khTaskManage.getOrg_type())) {
                        queryWrapper1.eq("fixmedins_code", f.getFixmedins_code());
                        queryWrapper1.eq("org_type", khTaskManage.getOrg_type());
                        queryWrapper1.eq("category", khTaskManage.getCategory());
                        queryWrapper1.eq("aggrement_lv", khTaskManage.getAggrement_lv());
                        queryWrapper1.eq("year", khTaskManage.getYear_of_assessment());
                    } else {
                        queryWrapper1.eq("fixmedins_code", f.getFixmedins_code());
                        queryWrapper1.eq("org_type", khTaskManage.getOrg_type());
                        queryWrapper1.eq("aggrement_lv", khTaskManage.getAggrement_lv());
                        queryWrapper1.eq("year", khTaskManage.getYear_of_assessment());
                    }
                    Long count = khManageMapper.selectCount(queryWrapper1);
                    if (count == 0) {
                        KHManage khManage = new KHManage();
                        khManage.setId(IdUtil.simpleUUID());
                        khManage.setAdmdvs(sysUser.getOrg_code());
                        khManage.setTask_manage_id(id);
                        khManage.setOrg_type(khTaskManage.getOrg_type());
                        khManage.setCategory(khTaskManage.getCategory());
                        khManage.setExpiration_time(khTaskManage.getExpiration_time());
                        khManage.setAggrement_lv(khTaskManage.getAggrement_lv());
                        khManage.setYear(khTaskManage.getYear_of_assessment());
                        khManage.setFixmedins_name(f.getFixmedins_name());
                        khManage.setFixmedins_code(f.getFixmedins_code());
                        khManage.setStatus("0");
                        khManage.setIs_del("0");
                        khManage.setCreateUser(sysUser.getUsername());
                        khManage.setCreateTime(new Date());
                        khManage.insert();
                    }


                }

            }
            khTaskManage.setPublish_status("1");
            khTaskManage.updateById();
        }
    }


    /**
     * 撤销发布
     * Author wzn
     * Date 2022/11/22 10:05
     */
    public ResultInfo undoPublication(String id) {
        KhTaskManage khTaskManage = khTaskManageMapper.selectById(id);
        if ("0".equals(khTaskManage.getPublish_status())) {
            return ResultInfo.error("发布状态异常!");
        }
        QueryWrapper<KHManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_manage_id", id);
        List<KHManage> khManageList = khManageMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(khManageList)) {
            for (KHManage k : khManageList) {
                if (!"0".equals(k.getStatus())) {
                    return ResultInfo.error("已有机构提交审核,禁止撤回！");
                }
                //删除所有考核任务,且发布状态改为待发布0
                k.deleteById();
            }
        }
        khTaskManage.setPublish_status("0");
        khTaskManage.updateById();
        return ResultInfo.success() ;
    }


    /**
     * 考核任务列表
     * Author wzn
     * Date 2022/11/22 11:37
     */
    public Page<KhTaskManage> selectTaskManageList(KhTaskManage khTaskManage) {
        Page<KhTaskManage> page = new Page<>(khTaskManage.getPageNo(), khTaskManage.getPageSize());
        QueryWrapper<KhTaskManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (StringUtils.isNotEmpty(khTaskManage.getTask_name())) {
            queryWrapper.like("task_name", khTaskManage.getTask_name());
        }
        if (StringUtils.isNotEmpty(khTaskManage.getOrg_type())) {
            queryWrapper.eq("org_type", khTaskManage.getOrg_type());
        }
        if (StringUtils.isNotEmpty(khTaskManage.getAggrement_lv())) {
            queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
        }

        if (StringUtils.isNotEmpty(khTaskManage.getYear_of_assessment())) {
            queryWrapper.eq("year_of_assessment", khTaskManage.getYear_of_assessment());
        }

        if (StringUtils.isNotEmpty(khTaskManage.getPublish_status())) {
            queryWrapper.eq("publish_status", khTaskManage.getPublish_status());
        }

        queryWrapper.eq("admdvs", sysUserService.getUser().getOrg_code());
        queryWrapper.orderByDesc("createTime") ;
        Page<KhTaskManage> taskManagePage = khTaskManageMapper.selectPage(page, queryWrapper);
        return taskManagePage;
    }


    //唯一性校验
    public boolean checkOnly(KhTaskManage khTaskManage) {
        boolean checkOnly = true; //没有重复
        QueryWrapper<KhTaskManage> queryWrapper = new QueryWrapper<>();
        if("1".equals(khTaskManage.getOrg_type())){
            queryWrapper.eq("is_del", "0");
            queryWrapper.eq("org_type", khTaskManage.getOrg_type());
            queryWrapper.eq("category", khTaskManage.getCategory());
            queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
            queryWrapper.eq("year_of_assessment", khTaskManage.getYear_of_assessment());
            queryWrapper.eq("admdvs", sysUserService.getUser().getOrg_code());
        }else{
            queryWrapper.eq("is_del", "0");
            queryWrapper.eq("org_type", khTaskManage.getOrg_type());
            queryWrapper.eq("aggrement_lv", khTaskManage.getAggrement_lv());
            queryWrapper.eq("year_of_assessment", khTaskManage.getYear_of_assessment());
            queryWrapper.eq("admdvs", sysUserService.getUser().getOrg_code());
        }

        List<KhTaskManage> khTaskManageList = khTaskManageMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(khTaskManageList)) {
            checkOnly = false;
            if (null != khTaskManage.getId()) {
                if (khTaskManage.getId().equals(khTaskManageList.get(0).getId())) {
                    checkOnly = true;
                }
            }
        }
        return checkOnly;
    }


    /**
    *考核任务详情信息
    * Author wzn
    * Date 2022/11/23 16:25
    */
    public KhTaskManage info(String id) {
        KhTaskManage khTaskManage = khTaskManageMapper.selectById(id) ;
        QueryWrapper<Blacklist> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("taskManageId",id) ;
        List<Blacklist> blacklistList = blacklistMapper.selectList(queryWrapper) ;
        ArrayList arrayList = new ArrayList() ;
        if(CollectionUtils.isNotEmpty(blacklistList)){
            for(Blacklist b:blacklistList){
                arrayList.add(b.getFixmedins_code()) ;
            }
        }
        khTaskManage.setMedicalCodeList(arrayList);
        return khTaskManage;
    }



    /**
    *考核单详情接口
    * Author wzn
    * Date 2022/11/24 10:39
    */
    public Map assesDetail(String id){
        Map map = new HashMap() ;
        KhTaskManage khTaskManage = khTaskManageMapper.selectById(id);
        KhAssessment khAssessment = khAssessmentMapper.selectById(khTaskManage.getAssessment_id()) ;
        //考核单信息
        map.put("khAssessment",khAssessment) ;
        //考核项列表
        QueryWrapper<KhAssessmentDetail> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("assessment_id",khTaskManage.getAssessment_id()) ;
        queryWrapper.orderByAsc("sort") ;
        List<KhAssessmentDetail> khAssessmentDetailList = khAssessmentDetailMapper.selectList(queryWrapper) ;
        if(CollectionUtils.isNotEmpty(khAssessmentDetailList)){
            List<String> stringList = null ;
            for(KhAssessmentDetail k:khAssessmentDetailList){
                stringList = new ArrayList<>() ;
                if("1".equals(k.getIs_text())){
                    stringList.add("文本描述") ;
                }
                if("1".equals(k.getIs_file())){
                    stringList.add("文件上传") ;
                }
                k.setFormOfReply(stringList);
            }
            map.put("list",khAssessmentDetailList) ;
        }
        return map ;
    }



}
