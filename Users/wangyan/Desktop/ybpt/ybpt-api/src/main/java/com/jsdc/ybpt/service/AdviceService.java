package com.jsdc.ybpt.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.jsdc.ybpt.agreementsignModel.NetTagRegister;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.AdviceMapper;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.price.advice.Advice;
import com.jsdc.ybpt.price.advice.AdviceSummary;
import com.jsdc.ybpt.price.advice.vo.AdviceVo;
import com.jsdc.ybpt.service.agreementsignService.NetTagRegisterService;
import com.jsdc.ybpt.service.agreementsignService.SignService;
import com.jsdc.ybpt.util.DateUtil;
import com.jsdc.ybpt.util.DocUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Advice)表服务接口
 *
 * @author wangYan
 * @since 2023-06-21
 */
@Service
public class AdviceService extends BaseService<Advice> {

    @Autowired
    private AdviceMapper adviceMapper;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UnfixedMechanismService unfixedMechanismService;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Autowired
    private SignService signService;

    @Autowired
    private NetTagRegisterService registerService;

    @Autowired
    private AdviceSummaryService adviceSummaryService ;

    public Page<Advice> getAdvice(Advice advice) {
        SysUser sysUser = sysUserService.getUser();
        Page<Advice> page = new Page<>();
        if (StringUtils.isNotBlank(advice.getIs_export()) && "1".equals(advice.getIs_export())) {
            List<Advice> list = adviceMapper.list(advice, sysUser);
            for (Advice s : list) {
                if ("7".equals(s.getUser_type()) || "8".equals(s.getUser_type())) {
                    s.setUser_type("非定点");
                } else {
                    s.setUser_type("定点");
                }
            }
            page.setRecords(list);
        } else {
            page = adviceMapper.page(new Page<>(advice.getPageNo(), advice.getPageSize()), advice, sysUser);
        }

        if (CollectionUtil.isNotEmpty(page.getRecords())) {
            for (Advice s : page.getRecords()) {
                //查询统筹区
                HashMap<String, String> tcqMap = new HashMap();
                List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
                tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
                s.setFix_blng_admdvs_name(tcqMap.get(s.getFix_blng_admdvs()));
                s.setIsAudit("false");
                //判断是否有审核权限。前端展示用
                if ("1".equals(sysUser.getUser_type()) && sysUser.getOrg_code().equals(s.getFix_blng_admdvs())) {
                    s.setIsAudit("true");
                }
                if ("7".equals(s.getUser_type()) || "8".equals(s.getUser_type())) {
                    s.setUser_type("非定点");
                } else {
                    s.setUser_type("定点");
                }
                //协议等级
                FixmedinsB fixmedinsB = new FixmedinsB();
                if ("7".equals(s.getUser_type()) || "8".equals(s.getUser_type())) {
                    UnfixedMechanism unfixedMechanism = unfixedMechanismService.getOne(Wrappers.<UnfixedMechanism>lambdaQuery()
                            .eq(UnfixedMechanism::getFixmedins_code, s.getOrg_code())
                            .eq(UnfixedMechanism::getIs_del, "0")
                    );
                    fixmedinsB.setFix_blng_admdvs(unfixedMechanism.getFix_blng_admdvs());
                    fixmedinsB.setAggrement_lv(unfixedMechanism.getAggrement_lv());
                } else {
                    QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("fixmedins_code", s.getOrg_code());
                    fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper);
                }
                if (fixmedinsB != null) {
                    s.setAggrement_lv(aggrement_lv().get(fixmedinsB.getAggrement_lv()));
                }
            }
        }
        return page;
    }

    public HashMap<String, String> aggrement_lv() {
        HashMap<String, String> aggrement_lvMap = new HashMap();
        aggrement_lvMap.put("1", "一级");
        aggrement_lvMap.put("2", "二级");
        aggrement_lvMap.put("3", "三级");
        aggrement_lvMap.put("4", "A级");
        aggrement_lvMap.put("5", "B级");
        aggrement_lvMap.put("6", "C级");
        aggrement_lvMap.put("9", "未定级");
        return aggrement_lvMap;
    }

    /**
     * 保存数据
     * @param advice
     * @return
     */
    public ResultInfo saveAdvice(Advice advice){
        SysUser sysUser = sysUserService.getUser();
        advice.setId(IdUtil.simpleUUID());
        advice.setOrg_name(sysUser.getOrg_name());
        advice.setOrg_code(sysUser.getOrg_code());
        advice.setCreateUser(sysUser.getId());
        advice.setUser_type(sysUser.getUser_type());
        advice.setIs_del("0");
        advice.setCreateTime(new Date());
        if(advice.getDetails() != null && advice.getDetails().size()>0){
            for (AdviceSummary detail : advice.getDetails()) {
                detail.setAdvice_id(advice.getId());
                detail.setOrg_name(sysUser.getOrg_name());
                detail.setId(IdUtil.simpleUUID());
                detail.insert();
            }

        }
        ResultInfo resultInfo = this.tcmViewPdf(advice) ;
        return resultInfo;
    }




    public ResultInfo tcmViewPdf(Advice advice) {
        SysUser sysUser = sysUserService.getUser();
        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File templateFile = new File(rootFile, "/templates/中医价格.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;

        advice.setFilling_time(DateUtil.getDateFormat(advice.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<AdviceSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("advice_id", advice.getId());
        List<AdviceSummary>  adviceSummaryList= adviceSummaryService.list(queryWrapper) ;

        for(AdviceSummary a:adviceSummaryList){
            a.setFilling_time(advice.getFilling_time());
            a.setIndex(a.getIndex() + 1);
            //内涵一次性耗材消耗（按实际购进价计）
            if(StringUtils.isNotEmpty(a.getOne_cost_list())){
                List<AdviceVo> one_cost_list = JSONObject.parseArray(a.getOne_cost_list(),AdviceVo.class);
                a.setOne_cost_list1(one_cost_list);
            }

            //电、水、气等常规消耗
            if(StringUtils.isNotEmpty(a.getConventional_cost_list())){
                List<AdviceVo> conventional_cost_list = JSONObject.parseArray(a.getConventional_cost_list(),AdviceVo.class);
                a.setConventional_cost_list1(conventional_cost_list);
            }
            //劳务费用
            if(StringUtils.isNotEmpty(a.getLabor_cost_list())){
                List<AdviceVo> labor_cost_list = JSONObject.parseArray(a.getLabor_cost_list(),AdviceVo.class);
                a.setLabor_cost_list1(labor_cost_list);
            }
            //仪器设备折旧费
            if(StringUtils.isNotEmpty(a.getDepreciation_cost_list())){
                List<AdviceVo> depreciation_cost_list = JSONObject.parseArray(a.getDepreciation_cost_list(),AdviceVo.class);
                a.setDepreciation_cost_list1(depreciation_cost_list);
            }
            //仪器设备维修费
            if(StringUtils.isNotEmpty(a.getMaintenance_cost_list())){
                List<AdviceVo> maintenance_cost_list = JSONObject.parseArray(a.getMaintenance_cost_list(),AdviceVo.class);
                a.setMaintenance_cost_list1(maintenance_cost_list);
            }
            //专用房屋折旧及维修
            if(StringUtils.isNotEmpty(a.getSpecial_cost_list())){
                List<AdviceVo> special_cost_list = JSONObject.parseArray(a.getSpecial_cost_list(),AdviceVo.class);
                a.setSpecial_cost_list1(special_cost_list);
            }
            //房屋大修理费
            if(StringUtils.isNotEmpty(a.getHousing_cost_list())){
                List<AdviceVo> housing_cost_list = JSONObject.parseArray(a.getHousing_cost_list(),AdviceVo.class);
                a.setHousing_cost_list1(housing_cost_list);
            }
           }


        advice.setAdviceSummaryList(adviceSummaryList);


        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(advice), Map.class);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("adviceSummaryList", policy)
                .bind("one_cost_list1", policy)
                .bind("conventional_cost_list1", policy)
                .bind("labor_cost_list1", policy)
                .bind("depreciation_cost_list1", policy)
                .bind("maintenance_cost_list1", policy)
                .bind("special_cost_list1", policy)
                .bind("housing_cost_list1", policy)
                .build();

        try {
            Console.log(data);
            DocUtil.word2RedDocument(templateFile.getPath(), config, data, docxPath, pdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传文件服务器
        File docFile = null;
        File pdfFile = null;
        MultipartFile multipartFile = null;
        FastDfsParams params = null;
        try{
            docFile = new File(docxPath);
            pdfFile = new File(pdfPath);
            //删除文件
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "11").eq("bizId", advice.getId()));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("zy", multipartFile, "11", advice.getId());
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            //上传
            String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
            if (StrUtil.isEmpty(contractId)) {
                return ResultInfo.error("申请上传失败！");
            }
            advice.setContractId(contractId);
            //机构签章
            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object_ = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", "单位公章", "0", "2", "2");
            JSONObject object = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", advice.getOrg_name(), "0", "2", "2");
            String result = (String) object.get("result");
            if (result.equals("success")) {
                advice.setPdf_path(object.getString("viewpdf_url"));
                advice.setDown_pdf_path(object.getString("download_url"));
                advice.insert();
            } else {
                String msg = (String) object.get("msg");
                return ResultInfo.error(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            docFile.delete();
            pdfFile.delete();
        }
return ResultInfo.success() ;
    }
}

