package com.jsdc.ybpt.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.DataGovernanceMapper;
import com.jsdc.ybpt.model.DataGovernance;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.terminal.TerminalInfo;
import com.jsdc.ybpt.terminal.TerminalNetwork;
import com.jsdc.ybpt.terminal.TerminalUser;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class DataGovernanceService extends BaseService<DataGovernance> {
    @Autowired
    private DataGovernanceMapper mainMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FixmedinsBService fixmedinsBService;

    /**
     * 分页
     */
    public Page<DataGovernance> selectPage(DataGovernance bean){
        SysUser sysUser =  sysUserService.getUser();
        if(!"1".equals(sysUser.getUser_type())){
            bean.setFixedPointNumberPermission(sysUser.getOrg_code());
        }
        Page<DataGovernance> page = mainMapper.queryPage(new Page<>(bean.getPageNo(), bean.getPageSize()),bean);
        return page;
    }

    /**
     * 列表
     */
    public List<DataGovernance> selectList(DataGovernance bean){
        SysUser sysUser =  sysUserService.getUser();
        if(!"1".equals(sysUser.getUser_type())){
            bean.setFixedPointNumberPermission(sysUser.getOrg_code());
        }
        List<DataGovernance> list = mainMapper.queryList(bean);
        return list;
    }

    public ResultInfo importData(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            // 使用 InputStream 创建 ExcelReader
            ExcelReader reader = ExcelUtil.getReader(inputStream);

            // 读取 Excel 数据并映射到 DataGovernance 类
            List<Map<String, Object>> readAll = reader.readAll();

            //当天重复导入，删除数据重新新增
            String dateFormat = DateUtil.getDateFormat(new Date(), "yyyyMMdd");
            List uploadNoList = getUploadNo();
            for (int i = 0; i < uploadNoList.size(); i++) {
                if (dateFormat.equals(uploadNoList.get(i))) {
                    QueryWrapper<DataGovernance> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("upload_no",uploadNoList.get(i));
                    mainMapper.delete(queryWrapper);
                }
            }

            SysUser sysUser =  sysUserService.getUser();
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getIs_del, "0").eq(FixmedinsB::getFixmedins_code, sysUser.getOrg_code()));
            for (Map<String, Object> objectMap : readAll) {
                DataGovernance bean = new DataGovernance();
                bean.setFixedPointNumber(getStringValue(objectMap, "定点编号"));
                bean.setNameTable(getStringValue(objectMap, "所处表名"));
                bean.setFixedPointName(getStringValue(objectMap, "定点名称"));
                bean.setPersonal_number(getStringValue(objectMap, "个人编号"));
                bean.setName(getStringValue(objectMap, "姓名"));
                bean.setIdNumber(getStringValue(objectMap, "身份证号"));
                bean.setDataTime(getStringValue(objectMap, "创建时间"));
                bean.setVisitID(getStringValue(objectMap, "就诊ID"));
                bean.setSettlementID(getStringValue(objectMap, "结算ID"));
                bean.setErrorCode(getStringValue(objectMap, "错误代码"));
                bean.setRuleName(getStringValue(objectMap, "规则名称"));
                bean.setMedicalCategory(getStringValue(objectMap, "医疗类别"));
//                bean.setDeliveryTime(getStringValue(objectMap, "下发时间"));
                if(StringUtils.isNotEmpty(bean.getFixedPointNumber())){
                    bean.setDeliveryTime(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
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
        QueryWrapper qw = new QueryWrapper<DataGovernance>();
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
        QueryWrapper qw = new QueryWrapper<DataGovernance>();
        qw.groupBy("medicalCategory");
        qw.select("medicalCategory");
        qw.orderByDesc("medicalCategory");
        List<String> list = listObjs(qw);
        return list;
    }

    /**
     * 新增修改
     */
    public void addUpdateInfo(DataGovernance bean, SysUser sysUser, FixmedinsB fixmedinsB){
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


    public boolean checkOnly(DataGovernance bean){
        boolean checkOnly = true ; //没有重复
        QueryWrapper<DataGovernance> queryWrapper = new QueryWrapper<>() ;
//        queryWrapper.eq("medical_code",bean.getFixedPointNumber()) ;
//        queryWrapper.eq("is_del","0") ;
//        List<DataGovernance> terminalInfos = mainMapper.selectList(queryWrapper) ;
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
        DataGovernance bean = new DataGovernance() ;
        bean.setId(id);
        bean.setIs_del("1");
        return updateById(bean);
    }
}
