package com.jsdc.ybpt.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.date.DateTime;
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
import com.jsdc.ybpt.mapper.SbApplyDrugMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.price.declare.SbApplyDrug;
import com.jsdc.ybpt.price.declare.SbApplyDrugVo;
import com.jsdc.ybpt.service.agreementsignService.NetTagRegisterService;
import com.jsdc.ybpt.service.agreementsignService.SignService;
import com.jsdc.ybpt.util.DocUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * (SbApplyDrug)表服务接口
 *
 * @author wangYan
 * @since 2023-08-22
 */
@Service
public class SbApplyDrugService extends BaseService<SbApplyDrug> {

    @Autowired
    private SysUserService sysUserService;
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
    private SbApplyDrugMapper sbApplyDrugMapper;
    @Autowired
    private FixmedinsBService fixmedinsBService;
    @Autowired
    private AdministrativeUnitService administrativeUnitService;
    @Value("${sbApplyPath}")
    private String path;

    public Page<SbApplyDrug> getSbApplyDrug(SbApplyDrug sbApplyDrug) {
        SysUser sysUser = sysUserService.getUser();

        Page<SbApplyDrug> page = new Page<>();
        if (sbApplyDrug.getPageNo() == null || sbApplyDrug.getPageSize() == null) {
            List<SbApplyDrug> list = sbApplyDrugMapper.sbApplyDrugList(sbApplyDrug);
            page.setRecords(list);
        } else {
            page = sbApplyDrugMapper.sbApplyDrugPage(new Page(sbApplyDrug.getPageNo(), sbApplyDrug.getPageSize()), sbApplyDrug);
        }
        HashMap<String, String> tcqMap = new HashMap();
        List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
        tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
        for (SbApplyDrug x : page.getRecords()) {
            //判断是否有审核权限。前端展示用
            if ("1".equals(sysUser.getUser_type()) && sysUser.getOrg_code().equals(x.getFix_blng_admdvs_sbApply())) {
                x.setIsAudit("true");
            }
            //状态 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回
            switch (x.getStatus()){
                case "0":
                    x.setStatusName("待初审");
                    break;
                case "1":
                    x.setStatusName("待复审");
                    break;
                case "2":
                    x.setStatusName("待终审");
                    break;
                case "3":
                    x.setStatusName("待生成受理书");
                    break;
                case "4":
                    x.setStatusName("完成");
                    break;
                case "5":
                    x.setStatusName("驳回");
                    break;

            }
            x.setFix_blng_admdvs_sbApply_name(tcqMap.get(x.getFix_blng_admdvs_sbApply()));
        }
        return page;
    }


    public ResultInfo insert(SbApplyDrug sbApplyDrug) {
        //校验
        Long count = sbApplyDrugMapper.selectCount(Wrappers.<SbApplyDrug>lambdaQuery()
                .eq(SbApplyDrug::getOrg_code, sbApplyDrug.getOrg_code())
                .eq(SbApplyDrug::getType, sbApplyDrug.getType())
                .eq(SbApplyDrug::getIs_del, "0")
                        .in(SbApplyDrug::getStatus,"0","1","2")//状态 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回
                );
        if(count > 0){
            return ResultInfo.error("您已申请告知此药品类别，请勿重复告知");
        }
        List<SbApplyDrug> list = sbApplyDrugMapper.selectList(Wrappers.<SbApplyDrug>lambdaQuery()
                .eq(SbApplyDrug::getOrg_code, sbApplyDrug.getOrg_code())
                .eq(SbApplyDrug::getType, sbApplyDrug.getType())
                .eq(SbApplyDrug::getIs_del, "0")
                .in(SbApplyDrug::getStatus,"3","4")//状态 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回
        );
        Date date = new Date();
        for (int i = 0; i < list.size(); i++) {
            SbApplyDrug x = list.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTime(x.getCreateTime());//设置起时间
            cal.add(Calendar.YEAR, 1);//增加一年
            if (date.before(cal.getTime())) {
                return ResultInfo.error("您已告知此药别类别加价率，请12个月后再次告知！");
            }
        }


        SysUser sysUser = sysUserService.getUser();

        //保存信息
        String sb_apply_id = IdUtil.simpleUUID();
        sbApplyDrug.setId(sb_apply_id);
        sbApplyDrug.setCreateTime(new Date());
        sbApplyDrug.setUser_type(sysUser.getUser_type());
        sbApplyDrug.setIs_del("0");

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
            File templateFile = new File(rootFile, "/templates/word转pdf模版/药品加价率告知明细表.docx");
            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;
            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApplyDrug), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("sbApplyDrug", policy)
                    .build();
            DocUtil.word2RedDocument(templateFile.getPath(), config, data, docxPath, pdfPath);
            Console.log(data);
            //上传文件服务器
            File pdfFile = new File(pdfPath);
            MultipartFile multipartFile = null;
            FastDfsParams params = null;
            //删除文件
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "5").eq("bizId", sb_apply_id));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("price_declaration/applyDrug", multipartFile, "12", sb_apply_id);
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            //上传
            String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
            if (StrUtil.isEmpty(contractId)) {
                return ResultInfo.error("申请上传失败！");
            }
            sbApplyDrug.setContractId(contractId);

            //机构签章
            if ("7".equals(sysUser.getUser_type()) || "8".equals(sysUser.getUser_type())) {
                sbApplyDrug.setPdf_path(fileInfo.getFileUrl());
                sbApplyDrug.setDown_pdf_path(fileInfo.getFileUrl());
                sbApplyDrug.insert();
            } else {
                NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
                JSONObject object = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", sbApplyDrug.getOrg_name(), "0", "2", "2");
                String result = (String) object.get("result");
                if (result.equals("success")) {
                    sbApplyDrug.setPdf_path(object.getString("viewpdf_url"));
                    sbApplyDrug.setDown_pdf_path(object.getString("download_url"));
                    sbApplyDrug.insert();
                } else {
                    String msg = (String) object.get("msg");
                    return ResultInfo.error(msg);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    public ResultInfo down(SbApplyDrugVo vo) {
        String file_name = "";
        Map<String, Object> data = new HashMap<String, Object>();
        TemplateExportParams params = new TemplateExportParams();
        params = new TemplateExportParams("/templates/下载明细/药品加价率.xlsx");

        data.put("bean", vo);
        Workbook workbook = ExcelExportUtil.exportExcel(params, data);
        File savefile = new File(path);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            file_name = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
            fos = new FileOutputStream(path + "/" + file_name);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success(file_name);
    }

    public ResultInfo bedViewPdf(String id) {
        SysUser sysUser = sysUserService.getUser();
        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File templateFile = new File(rootFile, "/templates/受理书/药品加价率告知受理书.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;
        SbApplyDrug sbApplyDrug = this.getById(id);
        sbApplyDrug.setYear(new DateTime().toString("yyyy"));
        sbApplyDrug.setMonth(new DateTime().toString("MM"));
        sbApplyDrug.setDay(new DateTime().toString("dd"));

        //查询统筹区
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(new QueryWrapper<FixmedinsB>().eq("fixmedins_code", sbApplyDrug.getOrg_code()).eq("is_del","0"));
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(new QueryWrapper<AdministrativeUnit>().eq("emp_no",fixmedinsB.getFix_blng_admdvs_sbApply()).eq("is_del","0"));
        sbApplyDrug.setFix_blng_admdvs_sbApply_name(administrativeUnit.getNotificationProcedureName());

        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApplyDrug), Map.class);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("sbApplyDrug", policy)
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
        try {
            docFile = new File(docxPath);
            pdfFile = new File(pdfPath);
            //删除文件
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "3").eq("bizId", id));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("sls", multipartFile, "5", id);
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            //上传
            String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
            if (StrUtil.isEmpty(contractId)) {
                return ResultInfo.error("申请上传失败！");
            }
            sbApplyDrug.setAuth_contractId(contractId);
            //机构签章
            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, administrativeUnit.getEmp_no()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object = signService.autoSignZjqsBySeal(register.getCompany_customer_id(), contractId, "", "0", administrativeUnit.getNotificationProcedureName(), "0", "2", "2",register.getProcedures_seal_id());
            String result = (String) object.get("result");
            if (result.equals("success")) {
                sbApplyDrug.setAuth_pdf_path(object.getString("viewpdf_url"));
                sbApplyDrug.setAuth_down_pdf_path(object.getString("download_url"));
                sbApplyDrug.setStatus("4");
                sbApplyDrug.setAuth_trialer(sysUser.getId());
                sbApplyDrug.setAuth_time(new Date());
                this.updateById(sbApplyDrug);
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
        return ResultInfo.success();
    }
}

