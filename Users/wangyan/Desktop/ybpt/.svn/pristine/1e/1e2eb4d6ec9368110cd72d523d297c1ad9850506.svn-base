package com.jsdc.ybpt.service;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.capitalSettlement.QsInfo;
import com.jsdc.ybpt.capitalSettlement.QsInfoDetails;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.LoanApplication;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_query.MedinsInfoB;
import com.jsdc.ybpt.model_query.RtalPhacB;
import com.jsdc.ybpt.service.agreementsignService.NetTagMechanismService;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.agreementsignVo.FixmedinsBVo;
import com.jsdc.ybpt.vo.agreementsignVo.NetTagMechanismVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FixmedinsBService extends BaseService<FixmedinsB> {

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private NetTagMechanismService netTagMechanismService;

    /**
     * 机构同步
     * Author wzn
     * Date 2022/5/18 11:37
     */
    @DS("reflowData")
    public List<FixmedinsB> selectByCode(FixmedinsB fixmedinsB) {
        return fixmedinsBMapper.selectByCode(fixmedinsB);
    }

    @DS("reflowData")
    public RtalPhacB selectRtalPhacByCode(RtalPhacB rtalPhacB) {
        return fixmedinsBMapper.selectRtalPhacByCode(rtalPhacB);
    }

    @DS("reflowData")
    public MedinsInfoB selectMedinsInfoByCode(MedinsInfoB medinsInfoB) {
        return fixmedinsBMapper.selectMedinsInfoByCode(medinsInfoB);
    }

    /**
     * 贷款申请医疗机构查询的信息
     * Author wzn
     * Date 2022/7/6 9:39
     */
    @DS("reflowData")
    public LoanApplication dkApplySelect(LoanApplication loanApplication) {
        return fixmedinsBMapper.dkApplySelect(loanApplication);
    }


    /**
     * 贷款申请药店查询的信息
     * Author wzn
     * Date 2022/7/6 17:13
     */
    @DS("reflowData")
    public LoanApplication ydApplyInfoList(LoanApplication loanApplication) {
        return fixmedinsBMapper.ydApplyInfoList(loanApplication);
    }


    /**
     * 医疗机构新增
     * Author wzn
     * Date 2022/5/18 14:50
     */
    public void add(FixmedinsB fixmedinsB) {
        fixmedinsB.setIs_del("0");
        fixmedinsB.setCreateTime(new Date());
        fixmedinsB.insert();
    }

    /**
     * 医疗机构修改
     * Author wzn
     * Date 2022/6/24 14:44
     */
    public void editData(FixmedinsB fixmedinsB) {
        SysUser sysUser = sysUserService.getUser();
        fixmedinsB.setUpdateTime(new Date());
        fixmedinsB.setUpdateUser(sysUser.getUsername());
        fixmedinsB.updateById();
    }

    /**
     * 机构唯一性校验
     * Author wzn
     * Date 2022/5/18 15:23
     */
    public Boolean checkOnly(FixmedinsB fixmedinsB) {
        boolean isOnly = true;
        //查询是否唯一
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixmedins_code", fixmedinsB.getFixmedins_code());
        queryWrapper.eq("is_del", "0");
        List<FixmedinsB> fixmedinsBList = fixmedinsBMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(fixmedinsBList)) {
            isOnly = false;
        }
        return isOnly;
    }

    /**
     * 医疗机构列表
     * Author wzn
     * Date 2022/5/18 15:28
     */
    public Page<FixmedinsB> selectList(FixmedinsB fixmedinsB) {
        Page<FixmedinsB> page = new Page<>(fixmedinsB.getPageNo(), fixmedinsB.getPageSize());
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        if (!"".equals(fixmedinsB.getFixmedins_code()) && null != fixmedinsB.getFixmedins_code()) {
            queryWrapper.like("fixmedins_code", fixmedinsB.getFixmedins_code());
        }
        if (!"".equals(fixmedinsB.getFixmedins_type()) && null != fixmedinsB.getFixmedins_type()) {
            queryWrapper.eq("fixmedins_type", fixmedinsB.getFixmedins_type());
        }
        if (!"".equals(fixmedinsB.getFixmedins_name()) && null != fixmedinsB.getFixmedins_name()) {
            queryWrapper.like("fixmedins_name", fixmedinsB.getFixmedins_name());
        }
        Page<FixmedinsB> fixmedinsBPage = fixmedinsBMapper.selectPage(page, queryWrapper);

        HashMap<String, String> tcqMap = new HashMap();
        List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
        tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
        for (FixmedinsB x : fixmedinsBPage.getRecords()) {
            x.setFix_blng_admdvs_sbApply_name(tcqMap.get(x.getFix_blng_admdvs_sbApply()));
        }

        return fixmedinsBPage;
    }

    public List<Map<String, String>> getAll(FixmedinsB fixmedinsB) {
        SysUser sysUser = sysUserService.getUser();
        String admdvs = sysUser.getOrg_code();
        LambdaQueryWrapper<FixmedinsB> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(fixmedinsB.getFixmedins_type()), FixmedinsB::getFixmedins_type, fixmedinsB.getFixmedins_type());
        wrapper.eq(FixmedinsB::getFix_blng_admdvs, admdvs);
        wrapper.eq(StringUtils.isNotEmpty(fixmedinsB.getAggrement_lv()), FixmedinsB::getAggrement_lv, fixmedinsB.getAggrement_lv());
        wrapper.in(!CollectionUtils.isEmpty(fixmedinsB.getAggrement_lvs()), FixmedinsB::getAggrement_lv, fixmedinsB.getAggrement_lvs());
        wrapper.eq(FixmedinsB::getIs_del, "0");
        List<FixmedinsB> fixmedinsBS = fixmedinsBMapper.selectList(wrapper);
        List<Map<String, String>> res = fixmedinsBS.stream().map(x -> {
            Map<String, String> map = new HashMap<>();
            map.put("key", x.getFixmedins_code());
            map.put("label", x.getFixmedins_name());
            return map;
        }).collect(Collectors.toList());
        return res;

    }

    public ResultInfo editAggrementLevel(FixmedinsBVo vo) {
        LambdaUpdateWrapper<FixmedinsB> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(FixmedinsB::getAggrement_lv, vo.getLevel());
        wrapper.in(FixmedinsB::getFixmedins_code, vo.getIds());
        fixmedinsBMapper.update(null, wrapper);
        return ResultInfo.success();
    }

    /**
     * 机构下拉数据
     * Author wzn
     * Date 2022/5/19 16:43
     */
    public List<FixmedinsB> dropDownData(String type) {
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixmedins_type", type);
        return fixmedinsBMapper.selectList(queryWrapper);
    }

    /**
     * 根据类型和机构编号查询
     * Author wzn
     * Date 2022/6/22 11:44
     */
    public FixmedinsB selectByFixmedinsCode(FixmedinsB fixmedinsB) {
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixmedins_code", fixmedinsB.getFixmedins_code());
        queryWrapper.eq("authorizationCode", fixmedinsB.getAuthorizationCode());
        queryWrapper.eq("is_del", "0");
        return fixmedinsBMapper.selectOne(queryWrapper);
    }

    public List<FixmedinsB> pldr() {
        return fixmedinsBMapper.pldr();
    }


    /**
     * 根据编码查询零售药店数据
     *
     * @param mechanism_code
     * @param fixmedins_type
     * @return
     * @authon zln
     */
    public FixmedinsB selectByYSYD(String mechanism_code, String fixmedins_type) {
        FixmedinsB fixmedinsB = fixmedinsBMapper.selectByYSYD(mechanism_code, fixmedins_type);
        fixmedinsB.setCred_lv_name(netTagMechanismService.getLevelList().get(fixmedinsB.getAggrement_lv()));
        return fixmedinsB;
    }


    /**
     * 根据编码查询零售药店数据
     *
     * @param vo
     * @return
     * @authon zln
     */
    public void selectByListPage(NetTagMechanismVo vo) {
        if (null != vo.getMedical_type()) {
            FixmedinsB fixmedinsB = fixmedinsBMapper.selectByYSYD(vo.getMechanism_code(), vo.getMedical_type());
            if(StringUtils.isNotEmpty(fixmedinsB.getAggrement_lv())){
                vo.setCred_lv_name(netTagMechanismService.getLevelList().get(fixmedinsB.getAggrement_lv()));
            }

            if ("1".equals(vo.getMedical_type())) {
                vo.setCred_lv_type("医疗机构");
            } else {
                vo.setCred_lv_type("零售药店");
            }
            if (null != fixmedinsB) {
                vo.setFixmedins_name((fixmedinsB.getFixmedins_name() == null) ? "" : fixmedinsB.getFixmedins_name());//机构名称
                vo.setMechanism_code((fixmedinsB.getFixmedins_code() == null) ? "" : fixmedinsB.getFixmedins_code());//机构类型名称
                vo.setMedical_code((fixmedinsB.getMedins_mgtcode() == null) ? "" : fixmedinsB.getMedins_mgtcode());//医保编码
                vo.setLegrep_name((fixmedinsB.getLegrep_name() == null) ? "" : fixmedinsB.getLegrep_name());//法人
                vo.setLegrep_person((fixmedinsB.getLegrep_person() == null) ? "" : fixmedinsB.getLegrep_person());//联系人
                vo.setDept_resper_tel((fixmedinsB.getLegrep_mobile() == null) ? "" : fixmedinsB.getLegrep_mobile());//联系电话
            }
        }
    }

    /**
     * 分页查询
     * @param vo
     * @return
     */
    public ResultInfo getPage(FixmedinsBVo vo){
        LambdaQueryWrapper<FixmedinsB> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(vo.getFixmedins_name()), FixmedinsB::getFixmedins_name, vo.getFixmedins_name());
        wrapper.like(StringUtils.isNotEmpty(vo.getFixmedins_code()), FixmedinsB::getFixmedins_code, vo.getFixmedins_code());
        if(StringUtils.isEmpty(vo.getAdmdvs())){
            SysUser sysUser = sysUserService.getUser();
            wrapper.eq(FixmedinsB::getFix_blng_admdvs, sysUser.getOrg_code());
        }else{
            wrapper.eq(FixmedinsB::getFix_blng_admdvs, vo.getAdmdvs());
        }
        if(StrUtil.isNotEmpty(vo.getFixmedins_type())){
            wrapper.eq(FixmedinsB::getFixmedins_type,vo.getFixmedins_type());
        }
        if(StrUtil.isNotEmpty(vo.getCategory())){
            wrapper.eq(FixmedinsB::getCategory,vo.getCategory());
        }
        if(StrUtil.isNotEmpty(vo.getAggrement_lv())){
            wrapper.eq(FixmedinsB::getAggrement_lv,vo.getAggrement_lv());
        }
        wrapper.eq(FixmedinsB::getIs_del, "0");
        Page<FixmedinsB> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
        Page<FixmedinsB> pageInfo = fixmedinsBMapper.selectPage(page, wrapper);
        pageInfo.getRecords().forEach(x -> {
            x.setCred_lv_name(StringUtils.equals(x.getFixmedins_type(), "1")?"医疗机构":"药店");
            if (StringUtils.isNotEmpty(x.getCategory())) {
                x.setCategory_name(StringUtils.equals(x.getCategory(), "1") ? "门诊" : "住院");
            }
            if(StringUtils.isNotEmpty(x.getAggrement_lv())){
                x.setAggrement_lv_name(getLevelList().get(x.getAggrement_lv()));
            }
        });
        return ResultInfo.success(pageInfo);
    }

    /**
     * 机构等级（1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级）
     */
    public Map<String, String> getLevelList() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "一级");
        map.put("2", "二级");
        map.put("3", "三级");
        map.put("4", "A级别");
        map.put("5", "B级别");
        map.put("6", "C级别");
        map.put("9", "未定级");
        return map;

    }


    /**
    *手动更新机构部分信息
    * Author wzn
    * Date 2024/2/28 14:56
    */
    @Transactional
    public void importData(MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler());
            reader.read(in, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {

                if (rowIndex == 0) {
                    return;
                }
                int s = rowlist.size();
                FixmedinsB fixmedinsB = new FixmedinsB() ;

                for (int i = 0; i < rowlist.size(); i++) {

                    if (null != rowlist.get(0)) {
                        //查询是否有该机构
                        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>() ;
                        queryWrapper.eq("fixmedins_code",rowlist.get(0).toString()) ;
                        queryWrapper.eq("is_del","0") ;
                        System.out.println(rowlist.get(0).toString());
                        FixmedinsB f = fixmedinsBMapper.selectOne(queryWrapper) ;
                        if(null != f){
                            fixmedinsB.setId(f.getId());
                        }
                    }

                    //机构名称
                    if (null != rowlist.get(1)) {
                        if(StringUtils.isNotEmpty(rowlist.get(1).toString())){
                            fixmedinsB.setFixmedins_name(rowlist.get(1).toString());
                        }
                    }
                    //法人
                    if (null != rowlist.get(2)) {
                        if(StringUtils.isNotEmpty(rowlist.get(2).toString())){
                            fixmedinsB.setLegrep_name(rowlist.get(2).toString());
                        }

                    }
                    //地址
                    if (null != rowlist.get(3)) {
                        if(StringUtils.isNotEmpty(rowlist.get(3).toString())){
                            fixmedinsB.setAddress(rowlist.get(3).toString());
                        }

                    }
                }

                if(StringUtils.isNotEmpty(fixmedinsB.getId())){
                    fixmedinsB.updateById() ;
                }

            }
        };
    }




}
