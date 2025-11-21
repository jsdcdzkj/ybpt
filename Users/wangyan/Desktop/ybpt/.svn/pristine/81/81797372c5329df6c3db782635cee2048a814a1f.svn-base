package com.jsdc.ybpt.controller_formula;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.jsdc.ybpt.agreementsignModel.NetTagRegister;
import com.jsdc.ybpt.formula.domain.entity.Catalog;
import com.jsdc.ybpt.formula.domain.entity.NotifyApply;
import com.jsdc.ybpt.mapper.formula.CatalogMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.service.AdministrativeUnitService;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.agreementsignService.NetTagRegisterService;
import com.jsdc.ybpt.service.agreementsignService.SignService;
import com.jsdc.ybpt.service.formula.NotifyApplyService;
import com.jsdc.ybpt.util.DocUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 制剂告知申请(NotifyApply)控制层
 *
 * @author yc
 * @since 2024-05-14 11:25:27
 */
@RestController
@RequestMapping("/notifyApply")
@Slf4j
//@Api(tags = "制剂告知申请(FORMULA_NOTIFY_APPLY)APi")
public class NotifyApplyController   {

    @Resource
    private NotifyApplyService notifyApplyService;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FixmedinsBService fixmedinsBService;

    @Autowired
    private AdministrativeUnitService administrativeUnitService;

    @Autowired
    private SignService signService;

    @Autowired
    private NetTagRegisterService registerService;

    @Resource
    private CatalogMapper catalogMapper;

    @Value("${fastDfs_url}")
    private  String upload_url;

    /**
     * 制剂告知审核列表
     * Author wb
     * Date Date 2024/05/15 14:26
     */
    @RequestMapping("selectPreparationNotificationReviewList")
    public ResultInfo submitSuccess(@RequestBody NotifyApply notifyApply) {
        Page<NotifyApply> notifyApplyPage = notifyApplyService.selectPreparationNotificationReviewList(notifyApply);
        return ResultInfo.success(notifyApplyPage);
    }

    /**
     * 功能描述: <br>
     * 〈制剂告知申请列表〉
     *
     * @param notifyApply
     * @Return: com.jsdc.ybpt.vo.ResultInfo
     * @Author: yc
     * @Date: 2024/5/15 17:14
     */
    @RequestMapping("applyPage")
    public ResultInfo applyPage(@RequestBody NotifyApply notifyApply) {
        Page<NotifyApply> notifyApplyPage = notifyApplyService.applyPage(notifyApply);
        return ResultInfo.success(notifyApplyPage);
    }

    /**
     * 功能描述: <br>
     * 〈保存〉
     *
     * @param notifyApply
     * @Return: com.jsdc.ybpt.vo.ResultInfo
     * @Author: yc
     * @Date: 2024/5/16 9:30
     */
    @RequestMapping("save")
    public ResultInfo save( @RequestBody NotifyApply notifyApply) {
        return ResultInfo.success(notifyApplyService.saveVO(notifyApply));
    }

    @RequestMapping("checkSave")
    public ResultInfo checkSave( @RequestBody NotifyApply notifyApply) {
        return notifyApplyService.checkSave(notifyApply);
    }

    @RequestMapping("update")
    public ResultInfo update( @RequestBody NotifyApply notifyApply) {
        return ResultInfo.success(notifyApplyService.updateVO(notifyApply));
    }

    @RequestMapping("detailInfo/{id}")
    public ResultInfo detailInfo( @PathVariable("id") String id) {
        return ResultInfo.success(notifyApplyService.detailInfo(id));
    }

    /**
     * 上传附件
     */
    @RequestMapping("/uploadDetailFile")
    public ResultInfo uploadDetailFile(String fileInfoId, MultipartFile file) {
        //上传文件服务器
        if (file != null) {
            FastDfsParams params = new FastDfsParams("notifyApply/detail", file, "25", "");
            params.setFileName(file.getOriginalFilename());
            return fastDfsUtil.uploadFile(params);
        }
        return ResultInfo.success();
    }

    @RequestMapping("/deleteDetailFile")
    public ResultInfo uploadDetailFile(String fileInfoId) {
        //清除文件
        if (StrUtil.isNotEmpty(fileInfoId)) {
            FileInfo fileInfo = fileInfoService.getById(fileInfoId);
            fastDfsUtil.deleteFile(fileInfo);
        }
        return ResultInfo.success();
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response) {
        notifyApplyService.download(response);
    }

    /**
     * 制剂告知审核接口
     * Author wb
     * Date 2023/2/10 14:47
     */
    @PostMapping("/audit")
    public ResultInfo audit(@RequestBody NotifyApply notifyApply) {
        notifyApplyService.audit(notifyApply);
        return ResultInfo.success();
    }


    /**
     * 制剂告知批量审核接口
     * Author wb
     * Date 2023/6/20 10:18
     */
    @PostMapping("/batchAudit")
    public ResultInfo batchAudit(@RequestBody NotifyApply notifyApplyBatchAudit) {
        String rejectReason = notifyApplyBatchAudit.getRejectReason();
        String ids = notifyApplyBatchAudit.getIds();
        String[] split = ids.split(",");
        NotifyApply notifyApply = null;
        for (String s : split) {
            notifyApply = new NotifyApply();
            notifyApply.setId(s);
            if(StringUtils.isNotEmpty(rejectReason)){
                notifyApply.setStatus("5");
                notifyApply.setRejectReason(rejectReason);
            }
            notifyApplyService.audit(notifyApply);
        }
        return ResultInfo.success();
    }

    @RequestMapping("/notifyApplyExport")
    public void notifyApplyExport(HttpServletResponse response, NotifyApply notifyApply) throws IOException {
        notifyApply.setIsExport("1");
        Page<NotifyApply> notifyApplyPage = notifyApplyService.notifyApplyExportList(notifyApply);
        List<NotifyApply> list = notifyApplyPage.getRecords();

        // 一次性写出内容，使用默认样式，强制输出标题
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("orgName", "单位名称");
        writer.addHeaderAlias("orgCode", "单位医保编码");
        writer.addHeaderAlias("fixBlngAdmdvsName", "统筹区");
        writer.addHeaderAlias("aggrementLv", "协议等级");
        writer.addHeaderAlias("biznet", "经营性质");
        writer.addHeaderAlias("nationalFormulaCode", "国家医疗机构制剂代码");
        writer.addHeaderAlias("formulaName", "制剂名称");
        writer.addHeaderAlias("approvalNo", "制剂批准文号");
        writer.addHeaderAlias("registerCompanyName", "制剂注册单位");
        writer.addHeaderAlias("dosageForm", "剂型");
        writer.addHeaderAlias("specs", "规格");
        writer.addHeaderAlias("minPackage", "最小包装");
        writer.addHeaderAlias("unit", "单位");
        writer.addHeaderAlias("price", "价格（元）");
        writer.addHeaderAlias("lastApplyPrice", "上次申报价格（元）");
        writer.addHeaderAlias("isInCategoryDesc", "是否在医保制剂目录");
        writer.addHeaderAlias("status", "审核状态");
        writer.addHeaderAlias("rejectReason", "驳回原因");
        writer.addHeaderAlias("createtime", "创建时间");
        writer.addHeaderAlias("firstCheckTime", "初审时间");
        writer.addHeaderAlias("firstCheckUser", "初审负责人");
        writer.addHeaderAlias("secondCheckTime", "复审时间");
        writer.addHeaderAlias("secondCheckUser", "复审负责人");
        writer.addHeaderAlias("finishCheckTime", "终审时间");
        writer.addHeaderAlias("finishCheckUser", "终审负责人");
        writer.addHeaderAlias("generalAcceptLetterTime", "生成受理书时间");
        writer.addHeaderAlias("rejectTime", "驳回时间");
        writer.addHeaderAlias("rejectUser", "驳回负责人");

        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
     * 生成受理书
     * Author wb
     * Date 2023/2/13 9:31
     */
    @RequestMapping("/viewPdf")
    public ResultInfo viewPdf(String id) {
        SysUser sysUser = sysUserService.getUser();
        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        NotifyApply notifyApply = notifyApplyService.getById(id);
        File templateFile = new File(rootFile, "/templates/受理书/医疗机构制剂自主定价告知受理书.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;
        log.info("==== docxPath :{} " , docxPath);
        log.info("==== pdfPath :{} " , pdfPath);

        notifyApply.setYear(new DateTime().toString("yyyy"));
        notifyApply.setMonth(new DateTime().toString("MM"));
        notifyApply.setDay(new DateTime().toString("dd"));
        //查询统筹区
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(new QueryWrapper<FixmedinsB>().eq("fixmedins_code", notifyApply.getOrgCode()).eq("is_del", "0"));
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(new QueryWrapper<AdministrativeUnit>().eq("emp_no", fixmedinsB.getFix_blng_admdvs_sbApply()).eq("is_del", "0"));
        notifyApply.setFixBlngAdmdvs(administrativeUnit.getNotificationProcedureName());

        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(notifyApply), Map.class);

        try {

            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("laborList", new LoopRowTableRenderPolicy())
                    .bind("materialConsumeList", policy)
                    .bind("fixedAssetsDepreList", policy)
                    .bind("checkFeeList", policy)
                    .build();
            DocUtil.word2RedDocument(templateFile.getPath(),config, data, docxPath, pdfPath);
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
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "26").eq("bizId", id));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("notifyApply", multipartFile, "26", id);
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            //上传
            String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
            if (StrUtil.isEmpty(contractId)) {
                return ResultInfo.error("申请上传失败！");
            }
            notifyApply.setAcceptLetterContractId(contractId);
            //机构签章
            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, administrativeUnit.getEmp_no()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object = signService.autoSignZjqsBySeal(register.getCompany_customer_id(), contractId, "", "0", administrativeUnit.getNotificationProcedureName(), "0", "2", "2", register.getProcedures_seal_id());
            String result = (String) object.get("result");
            log.info("==== 机构签章 " , object.toString());
            if (result.equals("success")) {
                notifyApply.setAcceptLetterPdfPath(object.getString("viewpdf_url"));
                notifyApply.setAcceptLetterDownPdfPath(object.getString("download_url"));
                notifyApply.setStatus("4");
                notifyApply.setGeneralAcceptLetterTime(new Date());
                notifyApplyService.updateById(notifyApply);
                if("0".equals(notifyApply.getIsInCategory())){
                    Catalog catalog = new Catalog();
                    catalog.setCategoryCode(notifyApply.getCategoryCode());
                    catalog.setGenericNameCode(notifyApply.getGenericNameCode());
                    catalog.setProductNameCode(notifyApply.getProductNameCode());
                    catalog.setFormulaName(notifyApply.getFormulaName());
                    catalog.setGoodsName(notifyApply.getGoodsName());
                    catalog.setPayType(notifyApply.getPayType());
                    catalog.setDosageForm(notifyApply.getDosageForm());
                    catalog.setSpecs(notifyApply.getSpecs());
                    catalog.setMinPriceUnit(notifyApply.getMinPriceUnit());
                    catalog.setUnit(notifyApply.getUnit());
                    catalog.setMinPackage(notifyApply.getMinPackage());
                    catalog.setApprovalNo(notifyApply.getApprovalNo());
                    catalog.setRemark(notifyApply.getRemark());
                    catalog.setCatalogCode(notifyApply.getCatalogCode());
                    catalog.setRegisterCompanyName(notifyApply.getRegisterCompanyName());
                    catalog.setNationalFormulaCode(notifyApply.getNationalFormulaCode());
                    catalog.setSelfPayRatio(notifyApply.getSelfPayRatio());
                    catalog.setIsDel("0");
                    catalog.setId(IdUtil.simpleUUID());
                    catalog.setCreateuser(sysUser.getId());
                    catalog.setCreatetime(new Date());
                    catalogMapper.insert(catalog);
                }
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

    @RequestMapping("/export")
    public void export(HttpServletResponse response,@RequestBody  NotifyApply notifyApply) throws IOException {
        notifyApply.setPageNo(1);
        notifyApply.setPageSize(-1);
        notifyApply.setIsExport("1");
        Page<NotifyApply> notifyApplyPage = notifyApplyService.applyPage(notifyApply);
        List<NotifyApply> list = notifyApplyPage.getRecords();

        // 一次性写出内容，使用默认样式，强制输出标题
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("orgName", "单位名称");
        writer.addHeaderAlias("orgCode", "单位医保编码");
        writer.addHeaderAlias("fixBlngAdmdvsName", "统筹区");
        writer.addHeaderAlias("aggrementLv", "协议等级");
        writer.addHeaderAlias("biznet", "经营性质");
        writer.addHeaderAlias("nationalFormulaCode", "国家医疗机构制剂代码");
        writer.addHeaderAlias("formulaName", "制剂名称");
        writer.addHeaderAlias("approvalNo", "制剂批准文号");
        writer.addHeaderAlias("registerCompanyName", "制剂注册单位");
        writer.addHeaderAlias("dosageForm", "剂型");
        writer.addHeaderAlias("specs", "规格");
        writer.addHeaderAlias("minPackage", "最小包装");
        writer.addHeaderAlias("unit", "单位");
        writer.addHeaderAlias("price", "价格（元）");
        writer.addHeaderAlias("lastApplyPrice", "上次申报价格（元）");
        writer.addHeaderAlias("isInCategoryDesc", "是否在医保制剂目录");
        writer.addHeaderAlias("status", "审核状态");
        writer.addHeaderAlias("rejectReason", "驳回原因");
        writer.addHeaderAlias("createtime", "创建时间");
        writer.addHeaderAlias("firstCheckTime", "初审时间");
        writer.addHeaderAlias("firstCheckUser", "初审负责人");
        writer.addHeaderAlias("secondCheckTime", "复审时间");
        writer.addHeaderAlias("secondCheckUser", "复审负责人");
        writer.addHeaderAlias("finishCheckTime", "终审时间");
        writer.addHeaderAlias("finishCheckUser", "终审负责人");
        writer.addHeaderAlias("generalAcceptLetterTime", "生成受理书时间");
        writer.addHeaderAlias("rejectTime", "驳回时间");
        writer.addHeaderAlias("rejectUser", "驳回负责人");

        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
}

