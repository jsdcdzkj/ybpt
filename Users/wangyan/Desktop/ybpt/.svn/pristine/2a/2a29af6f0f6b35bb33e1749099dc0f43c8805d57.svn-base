package com.jsdc.ybpt.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.DataDistributionMapper;
import com.jsdc.ybpt.model.DataDistribution;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DataDistributionService extends BaseService<DataDistribution> {
    @Autowired
    private DataDistributionMapper mainMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FixmedinsBService fixmedinsBService;

    /**
     * 分页
     */
    public Page<DataDistribution> selectPage(DataDistribution bean){
        SysUser sysUser =  sysUserService.getUser();
        if(!"1".equals(sysUser.getUser_type())){
            bean.setFixedPointNumberPermission(sysUser.getOrg_code());
        }
        Page<DataDistribution> page = mainMapper.queryPage(new Page<>(bean.getPageNo(), bean.getPageSize()),bean);
        return page;
    }

    /**
     * 列表
     */
    public List<DataDistribution> selectList(DataDistribution bean){
        SysUser sysUser =  sysUserService.getUser();
        if(!"1".equals(sysUser.getUser_type())){
            bean.setFixedPointNumberPermission(sysUser.getOrg_code());
        }
        List<DataDistribution> list = mainMapper.queryList(bean);
        return list;
    }

    public ResultInfo importData(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            // 使用 InputStream 创建 ExcelReader
            ExcelReader reader = ExcelUtil.getReader(inputStream);

            // 读取 Excel 数据并映射到 DataDistribution 类
            List<Map<String, Object>> readAll = reader.readAll();

            //当天重复导入，删除数据重新新增
            String dateFormat = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            List uploadNoList = getUploadNo();
            for (int i = 0; i < uploadNoList.size(); i++) {
                if (dateFormat.equals(uploadNoList.get(i))) {
                    QueryWrapper<DataDistribution> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("upload_no",uploadNoList.get(i));
                    mainMapper.delete(queryWrapper);
                }
            }

            SysUser sysUser =  sysUserService.getUser();
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getIs_del, "0").eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code()));
            for (Map<String, Object> objectMap : readAll) {
                DataDistribution bean = new DataDistribution();
                bean.setName(getStringValue(objectMap, "医疗机构"));
                bean.setFixedPointNumber(getStringValue(objectMap, "医疗机构编码"));
                bean.setAddr(getStringValue(objectMap, "地区"));
                bean.setBatch_name(getStringValue(objectMap, "批次名称"));
                bean.setBatch_start(getStringValue(objectMap, "批次开始时间"));
                bean.setBatch_end(getStringValue(objectMap, "批次结束时间"));
                bean.setProgress(getStringValue(objectMap, "序时进度"));
                bean.setContract_total(getStringValue(objectMap, "签约产品总数"));
                bean.setPurchase_num(getStringValue(objectMap, "零采购产品数"));
                bean.setPurchase_percentage(getStringValue(objectMap, "零采购产品数占比"));
                bean.setIndex_num(getStringValue(objectMap, "低于序时进度产品数"));
                bean.setIndex_percentage(getStringValue(objectMap, "低于序时进度产品数占比"));
                bean.setExcess_num(getStringValue(objectMap, "超量采购产品数"));
                bean.setExcess_percentage(getStringValue(objectMap, "超量采购产品数占比"));
                bean.setNormal_num(getStringValue(objectMap, "正常采购产品数"));
                bean.setNormal_percentage(getStringValue(objectMap, "正常采购产品数占比"));
                bean.setUnselected_total(getStringValue(objectMap, "对应非中选产品总数"));
                bean.setUnselected_num(getStringValue(objectMap, "采购非中选产品数"));
                bean.setSelected_num(getStringValue(objectMap, "非中选超量产品数"));
                bean.setSelectedExcess_num(getStringValue(objectMap, "非中选超额产品数"));
                bean.setFungible_total(getStringValue(objectMap, "对应可替代产品总数"));
                bean.setFungible_numbr(getStringValue(objectMap, "采购可替代产品数"));
                bean.setUnselected_excess_number(getStringValue(objectMap, "低于序时进度且非中选超量的产品数"));

                if(StringUtils.isNotEmpty(bean.getFixedPointNumber())){
                    addUpdateInfo(bean, sysUser, fixmedinsB);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResultInfo.error("读取 Excel 文件失败，请刷新页面重新尝试！");
        }

        return ResultInfo.success();
    }

    /**
     * 获取批次
     */
    public List<String> getUploadNo() {
        QueryWrapper qw = new QueryWrapper<DataDistribution>();
        qw.groupBy("upload_no");
        qw.select("upload_no");
        qw.orderByDesc("upload_no");
        List<String> list = listObjs(qw);
        return list;
    }

    /**
     * 获取医疗类别
     */
    public List<String> getMedicalCategory() {
        QueryWrapper qw = new QueryWrapper<DataDistribution>();
        qw.groupBy("medicalCategory");
        qw.select("medicalCategory");
        qw.orderByDesc("medicalCategory");
        List<String> list = listObjs(qw);
        return list;
    }

    /**
     * 新增修改
     */
    public void addUpdateInfo(DataDistribution bean, SysUser sysUser, FixmedinsB fixmedinsB){
        //唯一性校验
        boolean check = this.checkOnly(bean) ;
        if(!check){
            throw new CustomException("编码已存在，禁止重复添加") ;
        }
        if(StringUtils.isNotEmpty(bean.getId())){
            bean.updateById();
        }else {
            bean.setId(UUID.randomUUID().toString());
            bean.setAdmdvs(fixmedinsB.getFixmedins_code());
            bean.setUpload_no(DateUtil.getDateFormat(new Date(), "yyyyMMdd"));
            bean.setCreateUser(sysUser.getUsername());
            bean.setCreateTime(new Date());
            bean.setIs_del("0");
            bean.insert();
        }

    }


    public boolean checkOnly(DataDistribution bean){
        boolean checkOnly = true ; //没有重复
        QueryWrapper<DataDistribution> queryWrapper = new QueryWrapper<>() ;
//        queryWrapper.eq("medical_code",bean.getFixedPointNumber()) ;
//        queryWrapper.eq("is_del","0") ;
//        List<DataDistribution> terminalInfos = mainMapper.selectList(queryWrapper) ;
//        if(!terminalInfos.isEmpty()){
//            checkOnly = false ;
//            if(null!=bean.getId()){
//                if(bean.getId().equals(terminalInfos.get(0).getId())){
//                    checkOnly = true ;
//                }
//            }
//        }
        return checkOnly ;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        if (map == null || key == null) {
            return "";
        }
        Object value = map.get(key);
        return value != null ? value.toString() : "";
    }

    /**
     * 删除
     */
    public boolean delData(String id) {
        DataDistribution bean = new DataDistribution() ;
        bean.setId(id);
        bean.setIs_del("1");
        return updateById(bean);
    }
}
