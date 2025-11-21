package com.jsdc.ybpt.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Console;
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
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.price.advice.Advice;
import com.jsdc.ybpt.price.advice.AdviceSummary;
import com.jsdc.ybpt.price.advice.vo.AdviceVo;
import com.jsdc.ybpt.price.declare.*;
import com.jsdc.ybpt.price.drug.SbChinesePatentMedicine;
import com.jsdc.ybpt.price.drug.SbWesternMedicine;
import com.jsdc.ybpt.price.zlproject.SbZlProject;
import com.jsdc.ybpt.priceBackUp.SbChinesePatentMedicineBackUp;
import com.jsdc.ybpt.priceBackUp.SbWesternMedicineBackUp;
import com.jsdc.ybpt.priceBackUp.project.SbProjectBackUp;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.service.agreementsignService.NetTagRegisterService;
import com.jsdc.ybpt.service.agreementsignService.SignService;
import com.jsdc.ybpt.service.declare.*;
import com.jsdc.ybpt.util.DocUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("drugPrice")
public class DrugPriceController {
    @Autowired
    private SbWesternMedicineService sbWesternMedicineService;

    @Autowired
    private SbWesternMedicineBackUpService sbWesternMedicineBackUpService;

    @Autowired
    private SbChinesePatentMedicineBackUpService sbChinesePatentMedicineBackUpService;

    @Autowired
    private SbProjectBackUpService sbProjectBackUpService;

    @Autowired
    private SbApplyService sbApplyService;


    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper ;


    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private SignService signService;
    @Autowired
    private NetTagRegisterService registerService;
    @Autowired
    private SysUserService sysUserService;


    @Autowired
    private SbBusinessProjectService sbBusinessProjectService;


    @Autowired
    private SbBusinessExplainService sbBusinessExplainService;

    @Autowired
    private FixmedinsBService fixmedinsBService;

    @Autowired
    private SbCivilianMedicalService sbCivilianMedicalService;

    @Autowired
    private SbGovernmentMedicalService sbGovernmentMedicalService;


    @Autowired
    private SbCivilianMaterialsService sbCivilianMaterialsService;

    @Autowired
    private SbChinesePatentMedicineService sbChinesePatentMedicineService;

    @Autowired
    private SbProjectService sbProjectService;
    @Autowired
    private AdministrativeUnitService administrativeUnitService;

    @Value("${printPath}")
    private String printPath;

    @Autowired
    private SbBedDeclarationService sbBedDeclarationService;

    @Autowired
    private SbBedDetailsService sbBedDetailsService;


    @Autowired
    private AdviceService adviceService;

    @Autowired
    private AdviceSummaryService adviceSummaryService;


    /**
     * 西药导入
     * Author wzn
     * Date 2023/2/1 14:27
     */
    @RequestMapping("/importData")
    public ResultInfo importData(MultipartFile file, String year) {
        sbWesternMedicineService.importData(file, year);
        return ResultInfo.success();
    }


    /**
     * 西药列表
     * Author wzn
     * Date 2023/2/1 14:27
     */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody SbWesternMedicine sbWesternMedicine) {
        //已生效数据
        if ("0".equals(sbWesternMedicine.getStatus()) || StringUtils.isEmpty(sbWesternMedicine.getStatus())) {
            Page<SbWesternMedicine> bankPage = sbWesternMedicineService.selectList(sbWesternMedicine);
            return ResultInfo.success(bankPage);
        } else {
            Page<SbWesternMedicineBackUp> bankPage = sbWesternMedicineService.selectBackUpList(sbWesternMedicine);
            return ResultInfo.success(bankPage);
        }
    }

    /**
     * 西药导入比对国家编码
     *
     * @return
     */
    @RequestMapping("/westernMedicineDrugCode")
    public ResultInfo westernMedicineDrugCode(@RequestBody SbApply sbCivilianMedicals) {
        List list = new ArrayList();
        if (sbCivilianMedicals != null && sbCivilianMedicals.getSbCivilianMedical() != null && sbCivilianMedicals.getSbCivilianMedical().size() > 0) {
            for (int i = 0; i < sbCivilianMedicals.getSbCivilianMedical().size(); i++) {
                SbCivilianMedical sbCivilianMedical = sbCivilianMedicals.getSbCivilianMedical().get(i);
                String project_code = sbCivilianMedical.getProject_code();
                if (StringUtils.isNotBlank(project_code)) {
                    QueryWrapper<SbWesternMedicine> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("nationalDrugCode", project_code);
                    queryWrapper.eq("is_del", "0");
                    SbWesternMedicine sbWesternMedicine = sbWesternMedicineService.getOne(queryWrapper);
                    if (sbWesternMedicine != null) {
                        list.add(sbWesternMedicine);
                    } else {
                        return ResultInfo.error("第 【" + (i + 2) + "】行，" + project_code + ",没有此西药，请重新编辑！");
                    }
                } else {
                    return ResultInfo.error("第 【" + (i + 2) + "】行，", "编码不能未空!");
                }

            }
        } else {
            return ResultInfo.error("请重新保存模版，未读取到模版数据！");
        }

        return ResultInfo.success(list);
    }


    /**
     * 中成药导入
     * Author wzn
     * Date 2023/2/1 15:14
     */
    @RequestMapping("/patentMedicineimportData")
    public ResultInfo patentMedicineimportData(MultipartFile file, String year) {
        sbWesternMedicineService.patentMedicineimportData(file, year);
        return ResultInfo.success();
    }

    /**
     * 中成药列表
     * Author wzn
     * Date 2023/2/1 15:15
     */
    @PostMapping("/chinesePatentMedicineList")
    public ResultInfo chinesePatentMedicineList(@RequestBody SbChinesePatentMedicine sbChinesePatentMedicine) {

        //已生效数据
        if ("0".equals(sbChinesePatentMedicine.getStatus()) || StringUtils.isEmpty(sbChinesePatentMedicine.getStatus())) {
            Page<SbChinesePatentMedicine> bankPage = sbWesternMedicineService.chinesePatentMedicineList(sbChinesePatentMedicine);
            return ResultInfo.success(bankPage);
        } else {
            Page<SbChinesePatentMedicineBackUp> bankPage = sbWesternMedicineService.chinesePatentBackUpMedicineList(sbChinesePatentMedicine);
            return ResultInfo.success(bankPage);
        }
    }

    /**
     * 中成药导入比对国家编码
     *
     * @return
     */
    @RequestMapping("/chinesePatentMedicineDrugCode")
    public ResultInfo chinesePatentMedicineDrugCode(@RequestBody SbApply sbCivilianMedicals) {
        List list = new ArrayList();
        if (sbCivilianMedicals != null && sbCivilianMedicals.getSbCivilianMedical() != null && sbCivilianMedicals.getSbCivilianMedical().size() > 0) {
            for (int i = 0; i < sbCivilianMedicals.getSbCivilianMedical().size(); i++) {
                SbCivilianMedical sbCivilianMedical = sbCivilianMedicals.getSbCivilianMedical().get(i);
                String project_code = sbCivilianMedical.getProject_code();
                if (StringUtils.isNotBlank(project_code)) {
                    QueryWrapper<SbChinesePatentMedicine> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("nationalDrugCode", project_code);
                    queryWrapper.eq("is_del", "0");
                    SbChinesePatentMedicine sbWesternMedicine = sbChinesePatentMedicineService.getOne(queryWrapper);
                    if (sbWesternMedicine != null) {
                        list.add(sbWesternMedicine);
                    } else {
                        return ResultInfo.error("第 【" + (i + 2) + "】行，" + project_code + ",没有此中成药，请重新编辑！");
                    }
                } else {
                    return ResultInfo.error("第 【" + (i + 2) + "】行，", "编码不能未空!");
                }

            }
        } else {
            return ResultInfo.error("请重新保存模版，未读取到模版数据！");
        }

        return ResultInfo.success(list);
    }

    /**
     * 诊疗项目导入
     * Author wzn
     * Date 2023/2/2 11:34
     */
    @RequestMapping("/projectimportData")
    public ResultInfo projectimportData(MultipartFile file, String year) {
        sbWesternMedicineService.projectimportData(file, year);
        return ResultInfo.success();
    }

    /**
     * 诊疗列表接口
     * Author wzn
     * Date 2023/2/2 14:05
     */
    @PostMapping("/zlProjectMedicineList")
    public ResultInfo zlProjectMedicineList(@RequestBody SbZlProject zlProject) {
        SysUser sysUser = sysUserService.getUser();
        //已生效数据
        if ("0".equals(zlProject.getStatus()) || StringUtils.isEmpty(zlProject.getStatus())) {
            Page<SbZlProject> sbZlProjectPage = sbWesternMedicineService.zlProjectMedicineList(zlProject);
            if (!"1".equals(sysUser.getUser_type())) {
                //自设项目仅自己申请的才能看见
                if (CollectionUtil.isNotEmpty(sbZlProjectPage.getRecords())) {
                    for (int i = 0; i < sbZlProjectPage.getRecords().size(); i++) {
                        if (StringUtils.isNotEmpty(sbZlProjectPage.getRecords().get(i).getBelongToOrg())) {
                            if (!sbZlProjectPage.getRecords().get(i).getBelongToOrg().equals(sysUser.getOrg_code())) {
                                if (sbZlProjectPage.getRecords().size() == 1) {
                                    sbZlProjectPage.getRecords().remove(sbZlProjectPage.getRecords().get(i));
                                    i--;
                                }

                            }
                        }

                    }
                }
            }
            return ResultInfo.success(sbZlProjectPage);
        } else {
            Page<SbProjectBackUp> bankPage = sbWesternMedicineService.zlProjectBackUpMedicineList(zlProject);
            return ResultInfo.success(bankPage);
        }


    }

    /**
     * 校验同一个机构，同一个项目6个月内 只能申请一次
     */
    @RequestMapping("/verifySbZlProject")
    public ResultInfo verifySbZlProject(String project_code, String type) {
        SysUser sysUser = sysUserService.getUser() ;


        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("fixmedins_code",  sysUser.getOrg_code());
        FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper) ;
        String msg = "";

        //查是否有项目编码正在审核的，
        Integer count = sbProjectService.verify(project_code, type);
        if (count > 0) {
            msg = "该项目正在审核,禁止重复添加！";
            return ResultInfo.success(msg);
        }
        if(null != fixmedinsB){
            if("3".equals(fixmedinsB.getBiznet())){
                //政府盈利性校验规则

                    //查审核通过的  是否大于6个月
                    BigDecimal bigDecimal = new BigDecimal(6);
                    BigDecimal count2 = sbProjectService.verify2(project_code, type);
                    if (null != count2) {
                        int flag = count2.compareTo(bigDecimal);
                        if (flag != 1) {
                            msg = "已申请,请六个月后再试！";
                            return ResultInfo.success(msg);
                        }
                }
            }
        }
        return ResultInfo.success(msg);

    }

    /**
     * 西药获取批次
     *
     * @throws Exception
     */
    @RequestMapping("/getUploadNo")
    public ResultInfo getUploadNo() {
        QueryWrapper qw = new QueryWrapper<SbWesternMedicineBackUp>();
        qw.groupBy("batch");
        qw.select("batch");
        qw.orderByDesc("batch");
        List<String> list = sbWesternMedicineBackUpService.listObjs(qw);
        return ResultInfo.success(list);
    }

    /**
     * 中成药获取批次
     *
     * @throws Exception
     */
    @RequestMapping("/getChinaUploadNo")
    public ResultInfo getChinaUploadNo() {
        QueryWrapper qw = new QueryWrapper<SbChinesePatentMedicineBackUp>();
        qw.groupBy("batch");
        qw.select("batch");
        qw.orderByDesc("batch");
        List<String> list = sbChinesePatentMedicineBackUpService.listObjs(qw);
        return ResultInfo.success(list);
    }


    /**
     * 诊疗项目批次号
     * Author wzn
     * Date 2023/2/7 8:50
     */
    @RequestMapping("/getProjectNo")
    public ResultInfo getProjectNo() {
        QueryWrapper qw = new QueryWrapper<SbChinesePatentMedicineBackUp>();
        qw.groupBy("batch");
        qw.select("batch");
        qw.orderByDesc("batch");
        List<String> list = sbProjectBackUpService.listObjs(qw);
        return ResultInfo.success(list);
    }

    /**
     * 药品备份数据导出
     * Author wzn
     * Date 2023/2/4 9:19
     */
    @RequestMapping("/westDrugExport")
    public void exportSettleAbnormalData(HttpServletResponse response, String upload_no) throws Exception {
        QueryWrapper qw = new QueryWrapper<SbWesternMedicineBackUp>();
        qw.eq("batch", upload_no);
        qw.eq("is_del", "0");
        List<SbWesternMedicineBackUp> details = sbWesternMedicineBackUpService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("batch", "批次号");
        writer.addHeaderAlias("sortingCodeNumber", "分类编码");
        writer.addHeaderAlias("common_name_code", "药品通用名编码");
        writer.addHeaderAlias("drugNames", "医保药品名称");
        writer.addHeaderAlias("paymentCategory", "医保支付类别");
        writer.addHeaderAlias("dosageForm", "医保剂型");
        writer.addHeaderAlias("productNameCoding", "产品名编码");
        writer.addHeaderAlias("registeredName", "注册名称");
        writer.addHeaderAlias("productName", "商品名称");
        writer.addHeaderAlias("actualDosageForm", "实际剂型");
        writer.addHeaderAlias("actualSpecification", "实际规格");
        writer.addHeaderAlias("packagingMaterial", "包装材质");
        writer.addHeaderAlias("minimumPackingQuantity", "最小包装数量");
        writer.addHeaderAlias("unit", "单位");
        writer.addHeaderAlias("valorize", "政府定价（元）");
        writer.addHeaderAlias("purchaseCeilingPrice", "省集中采购上限价");
        writer.addHeaderAlias("paymentCriteria", "医保支付标准");
        writer.addHeaderAlias("approvalNumber", "批准文号");
        writer.addHeaderAlias("medicineEnterprise", "药品企业");
        writer.addHeaderAlias("limitPayment", "医保限定支付范围");
        writer.addHeaderAlias("serialNumber", "编号");
        writer.addHeaderAlias("bidDeclarationNumber", "招标申报编号");
        writer.addHeaderAlias("remark", "备注");
        writer.addHeaderAlias("nationalDrugCode", "国家药品代码");
        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details,
                true);
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
     * 药品数据导出
     * Author wzn
     * Date 2023/2/4 9:19
     */
    @RequestMapping("/westExport")
    public void westExport(HttpServletResponse response) throws Exception {
        QueryWrapper qw = new QueryWrapper<SbWesternMedicine>();
        qw.eq("is_del", "0");
        List<SbWesternMedicine> details = sbWesternMedicineService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("batch", "批次号");
        writer.addHeaderAlias("sortingCodeNumber", "分类编码");
        writer.addHeaderAlias("common_name_code", "药品通用名编码");
        writer.addHeaderAlias("drugNames", "医保药品名称");
        writer.addHeaderAlias("paymentCategory", "医保支付类别");
        writer.addHeaderAlias("dosageForm", "医保剂型");
        writer.addHeaderAlias("productNameCoding", "产品名编码");
        writer.addHeaderAlias("registeredName", "注册名称");
        writer.addHeaderAlias("productName", "商品名称");
        writer.addHeaderAlias("actualDosageForm", "实际剂型");
        writer.addHeaderAlias("actualSpecification", "实际规格");
        writer.addHeaderAlias("packagingMaterial", "包装材质");
        writer.addHeaderAlias("minimumPackingQuantity", "最小包装数量");
        writer.addHeaderAlias("unit", "单位");
        writer.addHeaderAlias("valorize", "政府定价（元）");
        writer.addHeaderAlias("purchaseCeilingPrice", "省集中采购上限价");
        writer.addHeaderAlias("paymentCriteria", "医保支付标准");
        writer.addHeaderAlias("approvalNumber", "批准文号");
        writer.addHeaderAlias("medicineEnterprise", "药品企业");
        writer.addHeaderAlias("limitPayment", "医保限定支付范围");
        writer.addHeaderAlias("serialNumber", "编号");
        writer.addHeaderAlias("bidDeclarationNumber", "招标申报编号");
        writer.addHeaderAlias("remark", "备注");
        writer.addHeaderAlias("nationalDrugCode", "国家药品代码");
        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details,
                true);
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
     * 中成药备份数据导出
     * Author wzn
     * Date 2023/2/4 9:19
     */
    @RequestMapping("/chinaDrugExport")
    public void chinaDrugExport(HttpServletResponse response, String upload_no) throws Exception {
        QueryWrapper qw = new QueryWrapper<SbChinesePatentMedicineBackUp>();
        qw.eq("batch", upload_no);
        qw.eq("is_del", "0");
        List<SbChinesePatentMedicineBackUp> details = sbChinesePatentMedicineBackUpService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("batch", "批次号");
        writer.addHeaderAlias("sortingCodeNumber", "分类编码");
        writer.addHeaderAlias("common_name_code", "药品通用名编码");
        writer.addHeaderAlias("drugNames", "医保药品名称");
        writer.addHeaderAlias("paymentCategory", "医保支付类别");
        writer.addHeaderAlias("productNameCoding", "产品名编码");
        writer.addHeaderAlias("registeredName", "注册名称");
        writer.addHeaderAlias("productName", "商品名称");
        writer.addHeaderAlias("actualDosageForm", "实际剂型");
        writer.addHeaderAlias("actualSpecification", "实际规格");
        writer.addHeaderAlias("packagingMaterial", "包装材质");
        writer.addHeaderAlias("minimumPackingQuantity", "最小包装数量");
        writer.addHeaderAlias("unit", "单位");
        writer.addHeaderAlias("valorize", "政府定价（元）");
        writer.addHeaderAlias("purchaseCeilingPrice", "省集中采购上限价");
        writer.addHeaderAlias("paymentCriteria", "医保支付标准");
        writer.addHeaderAlias("approvalNumber", "批准文号");
        writer.addHeaderAlias("medicineEnterprise", "药品企业");
        writer.addHeaderAlias("limitPayment", "医保限定支付范围");
        writer.addHeaderAlias("serialNumber", "编号");
        writer.addHeaderAlias("bidDeclarationNumber", "招标申报编号");
        writer.addHeaderAlias("remark", "备注");
        writer.addHeaderAlias("nationalDrugCode", "国家药品代码");
        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details, true);
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
     * 中成药数据导出
     * Author wzn
     * Date 2023/2/4 9:19
     */
    @RequestMapping("/chinaExport")
    public void chinaExport(HttpServletResponse response) throws Exception {
        QueryWrapper qw = new QueryWrapper<SbChinesePatentMedicine>();
        qw.eq("is_del", "0");
        List<SbChinesePatentMedicine> details = sbChinesePatentMedicineService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("batch", "批次号");
        writer.addHeaderAlias("sortingCodeNumber", "分类编码");
        writer.addHeaderAlias("common_name_code", "药品通用名编码");
        writer.addHeaderAlias("drugNames", "医保药品名称");
        writer.addHeaderAlias("paymentCategory", "医保支付类别");
        writer.addHeaderAlias("productNameCoding", "产品名编码");
        writer.addHeaderAlias("registeredName", "注册名称");
        writer.addHeaderAlias("productName", "商品名称");
        writer.addHeaderAlias("actualDosageForm", "实际剂型");
        writer.addHeaderAlias("actualSpecification", "实际规格");
        writer.addHeaderAlias("packagingMaterial", "包装材质");
        writer.addHeaderAlias("minimumPackingQuantity", "最小包装数量");
        writer.addHeaderAlias("unit", "单位");
        writer.addHeaderAlias("valorize", "政府定价（元）");
        writer.addHeaderAlias("purchaseCeilingPrice", "省集中采购上限价");
        writer.addHeaderAlias("paymentCriteria", "医保支付标准");
        writer.addHeaderAlias("approvalNumber", "批准文号");
        writer.addHeaderAlias("medicineEnterprise", "药品企业");
        writer.addHeaderAlias("limitPayment", "医保限定支付范围");
        writer.addHeaderAlias("serialNumber", "编号");
        writer.addHeaderAlias("bidDeclarationNumber", "招标申报编号");
        writer.addHeaderAlias("remark", "备注");
        writer.addHeaderAlias("nationalDrugCode", "国家药品代码");
        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details, true);
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
     * 诊疗项目备份数据导出
     * Author wzn
     * Date 2023/2/6 16:42
     */
    @RequestMapping("/projectExport")
    public void projectExport(HttpServletResponse response, String upload_no) throws Exception {
        QueryWrapper qw = new QueryWrapper<SbProjectBackUp>();
        qw.eq("batch", upload_no);
        qw.eq("is_del", "0");
        List<SbProjectBackUp> details = sbProjectBackUpService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("batch", "批次号");
        writer.addHeaderAlias("provincialProjectCode", "省项目编码");
        writer.addHeaderAlias("directoryName", "医保目录名称");
        writer.addHeaderAlias("chargeUnit", "计价单位");
        writer.addHeaderAlias("projectConnotation", "项目内涵");
        writer.addHeaderAlias("excludedContent", "除外内容");
        writer.addHeaderAlias("explain", "说明");
        writer.addHeaderAlias("chargeType", "收费类别");
        writer.addHeaderAlias("levelOfChargeItem", "收费项目等级");
        writer.addHeaderAlias("restrictedUseSign", "限制使用标志");
        writer.addHeaderAlias("nonChildOne", "非儿童一级限价");
        writer.addHeaderAlias("nonChildTwo", "非儿童二级限价");
        writer.addHeaderAlias("nonChildThree", "非儿童三级限价");
        writer.addHeaderAlias("childOne", "儿童一级限价（六周岁及以下）");
        writer.addHeaderAlias("childTwo", "儿童二级限价（六周岁及以下）");
        writer.addHeaderAlias("childThree", "儿童三级限价（六周岁及以下）");
        writer.addHeaderAlias("disabledSoldier", "一至六级残疾军人二次限价");
        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details, true);
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
     * 诊疗项目数据导出
     * Author wzn
     * Date 2023/2/6 16:42
     */
    @RequestMapping("/projectzcExport")
    public void projectzcExport(HttpServletResponse response) throws Exception {
        QueryWrapper qw = new QueryWrapper<SbZlProject>();
        qw.eq("is_del", "0");
        List<SbZlProject> details = sbProjectService.list(qw);
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("batch", "批次号");
        writer.addHeaderAlias("provincialProjectCode", "省项目编码");
        writer.addHeaderAlias("directoryName", "医保目录名称");
        writer.addHeaderAlias("chargeUnit", "计价单位");
        writer.addHeaderAlias("projectConnotation", "项目内涵");
        writer.addHeaderAlias("excludedContent", "除外内容");
        writer.addHeaderAlias("explain", "说明");
        writer.addHeaderAlias("chargeType", "收费类别");
        writer.addHeaderAlias("levelOfChargeItem", "收费项目等级");
        writer.addHeaderAlias("restrictedUseSign", "限制使用标志");
        writer.addHeaderAlias("nonChildOne", "非儿童一级限价");
        writer.addHeaderAlias("nonChildTwo", "非儿童二级限价");
        writer.addHeaderAlias("nonChildThree", "非儿童三级限价");
        writer.addHeaderAlias("childOne", "儿童一级限价（六周岁及以下）");
        writer.addHeaderAlias("childTwo", "儿童二级限价（六周岁及以下）");
        writer.addHeaderAlias("childThree", "儿童三级限价（六周岁及以下）");
        writer.addHeaderAlias("disabledSoldier", "一至六级残疾军人二次限价");
        //只导出定义字段
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(details, true);
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
     * 告知手续列表接口
     * Author wzn
     * Date 2023/2/7 14:03
     */
    @PostMapping("/sbApplyList")
    public ResultInfo sbApplyList(@RequestBody SbApply sbApply) {
        Page<SbApply> sbApplyPage = sbApplyService.sbApplyList(sbApply);
        return ResultInfo.success(sbApplyPage);
    }

    @RequestMapping("/sbApplyExport")
    public void sbApplyExport(HttpServletResponse response, SbApply sbApply) throws IOException {
        sbApply.setIs_export("1");
        Page<SbApply> sbApplyPage = sbApplyService.sbApplyList(sbApply);
        List<SbApply> list = sbApplyPage.getRecords();

        // 一次性写出内容，使用默认样式，强制输出标题
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("org_name", "单位名称");
        writer.addHeaderAlias("org_code", "单位医保编码");
        writer.addHeaderAlias("project_name", "项目名称");
        writer.addHeaderAlias("project_code", "项目编码");
        writer.addHeaderAlias("unit", "计价单位");
        writer.addHeaderAlias("price", "价格");
        writer.addHeaderAlias("fix_blng_admdvs_name", "统筹区");
        writer.addHeaderAlias("aggrement_lv", "协议等级");
        writer.addHeaderAlias("natures", "经营性质");
        writer.addHeaderAlias("user_type", "定点类型");
        writer.addHeaderAlias("type", "申报类型");
        writer.addHeaderAlias("status", "审核状态");
        writer.addHeaderAlias("reason", "驳回原因");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("first_time", "初审时间");
        writer.addHeaderAlias("first_trialer", "初审负责人");
        writer.addHeaderAlias("second_time", "复审时间");
        writer.addHeaderAlias("second_trialer", "复审负责人");
        writer.addHeaderAlias("end_time", "终审时间");
        writer.addHeaderAlias("end_trialer", "终审负责人");

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
     * 告知手续审核接口
     * Author wzn
     * Date 2023/2/10 14:47
     */
    @PostMapping("/audit")
    public ResultInfo audit(@RequestBody SbApply apply) {
        sbApplyService.audit(apply);
        return ResultInfo.success();
    }


    /**
     * 告知手续批量审核接口
     * Author wzn
     * Date 2023/6/20 10:18
     */
    @PostMapping("/batchAudit")
    public ResultInfo batchAudit(String ids) {
        String[] split = ids.split(",");
        SbApply sbApply = null;
        for (String s : split) {
            sbApply = new SbApply();
            sbApply.setId(s);
            sbApplyService.audit(sbApply);
        }
        return ResultInfo.success();
    }

    /**
     * 生成受理书
     * Author wzn
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
        SbApply sbApply = sbApplyService.getById(id);
        File templateFile = null;
        if ("4".equals(sbApply.getType())) {
            templateFile = new File(rootFile, "/templates/受理书/4 市场调节价项目.docx");
        }
        if ("6".equals(sbApply.getType())) {
            templateFile = new File(rootFile, "/templates/受理书/6 自设项目.docx");
        }
        if ("7".equals(sbApply.getType())) {
            templateFile = new File(rootFile, "/templates/受理书/7 新增医疗服务项目.docx");
        }
        if ("8".equals(sbApply.getType())) {
            templateFile = new File(rootFile, "/templates/受理书/8 市管未定价项目.docx");
        }


        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;

        sbApply.setYear(new DateTime().toString("yyyy"));
        sbApply.setMonth(new DateTime().toString("MM"));
        sbApply.setDay(new DateTime().toString("dd"));
        //查询统筹区
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(new QueryWrapper<FixmedinsB>().eq("fixmedins_code", sbApply.getOrg_code()).eq("is_del", "0"));
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(new QueryWrapper<AdministrativeUnit>().eq("emp_no", fixmedinsB.getFix_blng_admdvs_sbApply()).eq("is_del", "0"));
        sbApply.setFix_blng_admdvs(administrativeUnit.getNotificationProcedureName());
        String type = "";
        if ("6".equals(sbApply.getType())) {
            QueryWrapper<SbBusinessProject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("apply_id", id);
            List<SbBusinessProject> sbBusinessProjects = sbBusinessProjectService.list(queryWrapper);

            for (int i = 0; i < sbBusinessProjects.size(); i++) {
                if (i < sbBusinessProjects.size() - 1) {
                    type += sbBusinessProjects.get(i).getProject_name() + "、";
                } else {
                    type += sbBusinessProjects.get(i).getProject_name();
                }
            }
            sbApply.setNames(type);
        } else if ("4".equals(sbApply.getType())) {//医疗服务
            //经营性质
            if (!"政府非营利".equals(sbApply.getNatures())) {
                QueryWrapper<SbCivilianMedical> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("sb_apply_id", id);
                List<SbCivilianMedical> sbCivilianMedicals = sbCivilianMedicalService.list(queryWrapper);

                for (int i = 0; i < sbCivilianMedicals.size(); i++) {
                    if (i < sbCivilianMedicals.size() - 1) {
                        type += sbCivilianMedicals.get(i).getProject_name() + "、";
                    } else {
                        type += sbCivilianMedicals.get(i).getProject_name();
                    }
                }

            } else if ("政府非营利".equals(sbApply.getNatures())) {
                QueryWrapper<SbGovernmentMedical> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("sb_apply_id", id);
                List<SbGovernmentMedical> sbGovernmentMedicalList = sbGovernmentMedicalService.list(queryWrapper);

                for (int i = 0; i < sbGovernmentMedicalList.size(); i++) {
                    if (i < sbGovernmentMedicalList.size() - 1) {
                        type += sbGovernmentMedicalList.get(i).getProject_name() + "、";
                    } else {
                        type += sbGovernmentMedicalList.get(i).getProject_name();
                    }
                }
            }
            sbApply.setNames(type);

        } else if ("1".equals(sbApply.getType()) || "2".equals(sbApply.getType())) {//西药/中药
            QueryWrapper<SbCivilianMaterials> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sb_apply_id", id);
            List<SbCivilianMaterials> sbCivilianMedicals = sbCivilianMaterialsService.list(queryWrapper);

            for (int i = 0; i < sbCivilianMedicals.size(); i++) {
                if (i < sbCivilianMedicals.size() - 1) {
                    type += sbCivilianMedicals.get(i).getProject_name() + "、";
                } else {
                    type += sbCivilianMedicals.get(i).getProject_name();
                }
            }
            sbApply.setNames(type);
            // 公立 新增医疗服务 市管未定价项目
        } else if (("7".equals(sbApply.getType()) || "8".equals(sbApply.getType())) && (("3").equals(sbApply.getNatures()) || ("政府非营利").equals(sbApply.getNatures()))) {
            QueryWrapper<SbGovernmentMedical> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sb_apply_id", id);
            List<SbGovernmentMedical> sbGovernmentMedicalList = sbGovernmentMedicalService.list(queryWrapper);

            for (int i = 0; i < sbGovernmentMedicalList.size(); i++) {
                if (i < sbGovernmentMedicalList.size() - 1) {
                    type += sbGovernmentMedicalList.get(i).getProject_name() + "、";
                } else {
                    type += sbGovernmentMedicalList.get(i).getProject_name();
                }
            }
            sbApply.setNames(type);
            // 非公立 新增医疗服务 市管未定价项目
        } else if (("7".equals(sbApply.getType()) || "8".equals(sbApply.getType())) && !(("3").equals(sbApply.getNatures()) || !"政府非营利".equals(sbApply.getNatures()))) {
            QueryWrapper<SbCivilianMedical> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sb_apply_id", id);
            List<SbCivilianMedical> sbCivilianMedicals = sbCivilianMedicalService.list(queryWrapper);

            for (int i = 0; i < sbCivilianMedicals.size(); i++) {
                if (i < sbCivilianMedicals.size() - 1) {
                    type += sbCivilianMedicals.get(i).getProject_name() + "、";
                } else {
                    type += sbCivilianMedicals.get(i).getProject_name();
                }
            }
            sbApply.setNames(type);
        }
        sbApply.setNames(type);

        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);

        try {
            DocUtil.word2RedDocument(templateFile.getPath(), data, docxPath, pdfPath);
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
            sbApply.setAuth_contractId(contractId);
            //机构签章
            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, administrativeUnit.getEmp_no()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object = signService.autoSignZjqsBySeal(register.getCompany_customer_id(), contractId, "", "0", administrativeUnit.getNotificationProcedureName(), "0", "2", "2", register.getProcedures_seal_id());
            String result = (String) object.get("result");
            if (result.equals("success")) {
                sbApply.setAuth_pdf_path(object.getString("viewpdf_url"));
                sbApply.setAuth_down_pdf_path(object.getString("download_url"));
                sbApply.setStatus("4");
                sbApply.setAuth_trialer(sysUser.getId());
                sbApply.setAuth_time(new Date());
                sbApplyService.updateById(sbApply);
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


    /**
     * Author wzn
     * Date 2023/2/13 11:53
     */
    @PostMapping("/addBusinessProject")
    public ResultInfo addBusinessProject(@RequestBody SbApply sbApply2) {
        SysUser sysUser = sysUserService.getUser();

        if (CollectionUtil.isNotEmpty(sbApply2.getSbApplyVo())) {
            String username = sysUser.getUsername();
            //主表数据
            SbApply sbApply = new SbApply();
            sbApply.setId(IdUtil.simpleUUID());
            sbApply.setOrg_name(sbApply2.getSbApplyVo().get(0).getOrg_name());
            sbApply.setOrg_code(sbApply2.getSbApplyVo().get(0).getOrg_code());
            sbApply.setNatures("营利性");
            sbApply.setType("6");
            sbApply.setStatus("0");
            sbApply.setIs_del("0");
            sbApply.setCreateTime(new Date());
            sbApply.setCreateUser(username);
            sbApply.setYear(new DateTime().toString("yyyy"));
            sbApply.setMonth(new DateTime().toString("MM"));
            sbApply.setDay(new DateTime().toString("dd"));
            for (int i = 0; i < sbApply2.getSbApplyVo().size(); i++) {
                SbApplyVo s = sbApply2.getSbApplyVo().get(i);
                //回填名称和编码
                sbApply.setProject_code(s.getProject_code());
                sbApply.setProject_name(s.getProject_name());
                //回填计价单位和价格
                sbApply.setUnit(s.getUnit());
                sbApply.setPrice(s.getPrice());

                if (s.getProject_code().length() != 9) {
                    return ResultInfo.error("项目编码:" + s.getProject_code() + "格式错误！");
                }


                //次主表
                List<SbApply> sbApplyList = sbBedDeclarationService.verify3(s.getProject_code(),"6");
                if(CollectionUtil.isNotEmpty(sbApplyList)){
                    String oldPrice  = sbApplyList.get(0).getPrice() ;

                    // 示例价格
                    if(StringUtils.isNotEmpty(oldPrice)){
                        String price1 = oldPrice;
                        String price2 = s.getPrice();

                        // 将字符串转换为BigDecimal
                        BigDecimal bdPrice1 = new BigDecimal(price1);
                        BigDecimal bdPrice2 = new BigDecimal(price2);

                        // 计算price2是否比price1高50%
                        BigDecimal fiftyPercentMore = bdPrice1.multiply(new BigDecimal("1.5"));

                        if (bdPrice2.compareTo(fiftyPercentMore) >= 0) {
                            throw new RuntimeException( "提价幅度不得超过50%!") ;
                        }
                    }

                }


                SbBusinessProject sbBusinessProject = new SbBusinessProject();
                sbBusinessProject.setId(IdUtil.simpleUUID());
                sbBusinessProject.setDept_name(s.getOrg_name());
                sbBusinessProject.setDept_code(s.getOrg_code());
                sbBusinessProject.setNatures("营利性");
                sbBusinessProject.setProject_name(s.getProject_name());
                //项目编码校验唯一性
                QueryWrapper<SbBusinessProject> qw = new QueryWrapper<>();
                qw.eq("project_code", s.getProject_code());

                Long count = sbBusinessProjectService.count(qw);
                if (count != 0) {
                    return ResultInfo.error("项目编码:" + s.getProject_code() + "重复！");
                }
                sbBusinessProject.setProject_code(s.getProject_code());
                sbBusinessProject.setProject_content(s.getProject_content());
                sbBusinessProject.setExcept_content(s.getExcept_content());
                sbBusinessProject.setUnit(s.getUnit());
                sbBusinessProject.setExplain(s.getExplain());
                sbBusinessProject.setChild_or_not(s.getChild_or_not());
                sbBusinessProject.setPrice(s.getPrice());
                sbBusinessProject.setType(s.getType());
                sbBusinessProject.setTypeName(s.getTypeName());
                sbBusinessProject.setService_price(s.getService_price());
                sbBusinessProject.setApply_id(sbApply.getId());
                sbBusinessProject.setCreateTime(new Date());
                sbBusinessProject.setCreateUser(username);
                sbBusinessProject.setIs_del("0");
                sbBusinessProject.insert();
                //绑定附件
                if (StringUtils.isNotBlank(s.getFileInfoId())) {
                    FileInfo fileInfo = fileInfoService.getById(s.getFileInfoId());
                    fileInfo.setBizId(sbBusinessProject.getId());
                    fileInfo.updateById();
                }
                //子表
                SbBusinessExplain sbBusinessExplain = new SbBusinessExplain();
                sbBusinessExplain.setId(IdUtil.simpleUUID());
                sbBusinessExplain.setBusiness_id(sbBusinessProject.getId());
                sbBusinessExplain.setProject_name(s.getProject_name());
                sbBusinessExplain.setProject_code(s.getProject_code());
                sbBusinessExplain.setProject_content(s.getProject_content());
                sbBusinessExplain.setExcept_content(s.getExcept_content());
                sbBusinessExplain.setUnit(s.getUnit());
                sbBusinessExplain.setExplain(s.getExplain());
                sbBusinessExplain.setPrice(s.getPrice());
                sbBusinessExplain.setIs_norm(s.getIs_norm());
                sbBusinessExplain.setNorm_code(s.getNorm_code());
                sbBusinessExplain.setNorm_name(s.getNorm_name());
                sbBusinessExplain.setSense(s.getSense());
                sbBusinessExplain.setBase(s.getBase());
                sbBusinessExplain.setNorm(s.getNorm());
                sbBusinessExplain.setRisk(s.getRisk());
                sbBusinessExplain.setApply_unit(s.getApply_unit());
                sbBusinessExplain.setResult(s.getResult());
                sbBusinessExplain.setDeclare(s.getDeclare());
                sbBusinessExplain.setMatters(s.getMatters());
                sbBusinessExplain.setPrice_manager(s.getPrice_manager());
                sbBusinessExplain.setOrg_code(s.getOrg_code());
                sbBusinessExplain.setOrg_name(s.getOrg_name());
                sbBusinessExplain.setLegal(s.getLegal());
                sbBusinessExplain.setDept_date(sbApply.getYear() + sbApply.getMonth() + sbApply.getDay());
                sbBusinessExplain.insert();
            }


            //获取模板文档
            File rootFile = null;
            try {
                rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File templateFile = new File(rootFile, "/templates/自设项目.docx");
            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;
            QueryWrapper<SbBusinessProject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("apply_id", sbApply.getId());
            List<SbBusinessProject> sbBusinessProjects = sbBusinessProjectService.list(queryWrapper);
            if (CollectionUtil.isNotEmpty(sbBusinessProjects)) {
                AtomicInteger i = new AtomicInteger(1);
                ArrayList<SbBusinessExplain> arrayList = new ArrayList();
                for (SbBusinessProject s : sbBusinessProjects) {
                    if ("0".equals(s.getService_price())) {
                        s.setService_price("否");
                    } else if ("1".equals(s.getService_price())) {
                        s.setService_price("是");
                    }
                    QueryWrapper queryWrapper1 = new QueryWrapper();
                    queryWrapper1.eq("business_id", s.getId());
                    SbBusinessExplain sbBusinessExplain = sbBusinessExplainService.getOne(queryWrapper1);
                    if (null != sbBusinessExplain) {
                        if ("0".equals(sbBusinessExplain.getIs_norm())) {
                            sbBusinessExplain.setNorm_name("否");
                        }
                        sbBusinessExplain.setYear(sbApply.getYear());
                        sbBusinessExplain.setMonth(sbApply.getMonth());
                        sbBusinessExplain.setDay(sbApply.getDay());
                        sbBusinessExplain.setNatures(sbApply.getNatures());
                    }

                    arrayList.add(sbBusinessExplain);
                    s.setIndex(i.getAndIncrement());
                }

                sbApply.setSbBusinessProjects(sbBusinessProjects);
                sbApply.setArrayList(arrayList);
            }

            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("sbBusinessProjects", policy)
                    .bind("arrayList", policy)
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
                List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "3").eq("bizId", sbApply.getId()));
                for (FileInfo fileInfo : fileInfos) {
                    fastDfsUtil.deleteFile(fileInfo);
                }
                multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
                params = new FastDfsParams("zsxm", multipartFile, "5", sbApply.getId());
                params.setFileName(pdfFileName);
                ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
                FileInfo fileInfo = (FileInfo) resultInfo.getData();
                //上传
                String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
                if (StrUtil.isEmpty(contractId)) {
                    return ResultInfo.error("申请上传失败！");
                }
                sbApply.setContractId(contractId);
                //机构签章
                NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
                JSONObject object_ = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", "单位公章", "0", "2", "2");
                JSONObject object = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", sbApply.getOrg_name(), "0", "2", "2");
                String result = (String) object.get("result");
                if (result.equals("success")) {
                    sbApply.setPdf_path(object.getString("viewpdf_url"));
                    sbApply.setDown_pdf_path(object.getString("download_url"));
                    sbApply.insert();
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
        }


        return ResultInfo.success();
    }


    /**
     * 自设项目导出
     * Author wzn
     * Date 2023/4/21 9:32
     */
    @PostMapping("/infoExcel")
    public String infoExcel(String applyId) {
        QueryWrapper<SbBusinessProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id", applyId);
        SbBusinessProject sbBusinessProject = sbBusinessProjectService.getOne(queryWrapper);


        String file_name = "";

        TemplateExportParams params = new TemplateExportParams("templates/自设项目导出模板.xlsx");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sbBusinessProject", sbBusinessProject);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File(printPath);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            file_name = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
            fos = new FileOutputStream(printPath + "/" + file_name);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name;
    }


    /**
     * 公立/非公立医院病房床位申报  提交
     * Author wzn
     * Date 2023/5/29 11:19
     */
    @PostMapping("/bedDeclaration")
    public ResultInfo bedDeclaration(@RequestBody SbApply sbApply2) {
        SysUser sysUser = sysUserService.getUser();

        String username = sysUser.getUsername();
        //主表数据
        SbApply sbApply = new SbApply();
        sbApply.setOrg_code(sbApply2.getOrg_code());
        sbApply.setOrg_name(sbApply2.getOrg_name());
        sbApply.setUser_type("2");
        sbApply.setNatures(sbApply2.getNatures());
        sbApply.setId(IdUtil.simpleUUID());
        sbApply.setType("9");
        sbApply.setStatus("0");
        sbApply.setIs_del("0");
        sbApply.setCreateTime(new Date());
        sbApply.setCreateUser(username);
        if ("政府非营利".equals(sbApply2.getNatures())) {
            sbApply.setHeadline("公立");
        } else {
            sbApply.setHeadline("非公立");
        }

        SbBedDeclaration sbBedDeclaration = sbApply2.getSbBedDeclaration();
        sbBedDeclaration.setDept_name(sbBedDeclaration.getDept_name());
        sbBedDeclaration.setAggrement_lv(sbBedDeclaration.getAggrement_lv());
        sbBedDeclaration.setBeds_all_count(sbBedDeclaration.getBeds_all_count());
        sbBedDeclaration.setLinkman(sbBedDeclaration.getLinkman());
        sbBedDeclaration.setPhone(sbBedDeclaration.getPhone());
        sbBedDeclaration.setCreateTime(new Date());
        sbBedDeclaration.setCreateUser(username);
        sbBedDeclaration.setApply_id(sbApply.getId());
        sbBedDeclaration.insert();

        if (sbBedDeclaration.getFileInfoIds().length > 0) {
            for (int i = 0; i < sbBedDeclaration.getFileInfoIds().length; i++) {
                //绑定附件
                if (StringUtils.isNotBlank(sbBedDeclaration.getFileInfoIds()[i])) {
                    FileInfo fileInfo = fileInfoService.getById(sbBedDeclaration.getFileInfoIds()[i]);
                    fileInfo.setBizId(sbBedDeclaration.getId());
                    fileInfo.updateById();
                }
            }
        }


        for (int i = 0; i < sbApply2.getSbBedDetailsList().size(); i++) {
            SbBedDetails s = sbApply2.getSbBedDetailsList().get(i);



            List<SbApply> sbApplyList = sbBedDeclarationService.verify3(s.getProject_code(),"9");
            if(CollectionUtil.isNotEmpty(sbApplyList)){
                String oldPrice  = sbApplyList.get(0).getPrice() ;

                // 示例价格
                if(StringUtils.isNotEmpty(oldPrice)){
                    String price1 = oldPrice;
                    String price2 = s.getPrice();

                    // 将字符串转换为BigDecimal
                    BigDecimal bdPrice1 = new BigDecimal(price1);
                    BigDecimal bdPrice2 = new BigDecimal(price2);

                    // 计算price2是否比price1高50%
                    BigDecimal fiftyPercentMore = bdPrice1.multiply(new BigDecimal("1.5"));

                    if (bdPrice2.compareTo(fiftyPercentMore) >= 0) {
                        throw new RuntimeException( "提价幅度不得超过50%!") ;
                    }
                }

            }

            //回填名称和编码
            sbApply.setProject_code(s.getProject_code());
            sbApply.setProject_name(s.getProject_name());
            //回填计价单位和价格
            sbApply.setPrice(s.getPrice());

            //次主表
            SbBedDetails sbBedDetails = new SbBedDetails();
            sbBedDetails.setId(IdUtil.simpleUUID());
            sbBedDetails.setProject_code(s.getProject_code());
            sbBedDetails.setProject_name(s.getProject_name());
            sbBedDetails.setInpatientWard(s.getInpatientWard());
            sbBedDetails.setBed_declaration_id(sbBedDeclaration.getId());
            sbBedDetails.setBed_count(s.getBed_count());
            sbBedDetails.setBed_number(s.getBed_number());
//                sbBedDetails.setBed_class(s.getBed_class());
            sbBedDetails.setPrice(s.getPrice());
            sbBedDetails.setRemark(s.getRemark());
            sbBedDetails.insert();
        }


        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File templateFile = new File(rootFile, "/templates/医院病房床位申报表.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;

        QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id", sbApply.getId());
        SbBedDeclaration sbBedDeclaration1 = sbBedDeclarationService.getOne(queryWrapper);
        sbApply.setSbBedDeclaration(sbBedDeclaration1);

        QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("bed_declaration_id", sbBedDeclaration1.getId());
        List<SbBedDetails> sbBedDetailsList = sbBedDetailsService.list(queryWrapper2);
        sbApply.setSbBedDetailsList(sbBedDetailsList);

        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("sbBedDetailsList", policy)
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
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "3").eq("bizId", sbApply.getId()));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("zsxm", multipartFile, "5", sbApply.getId());
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
           //todo 测试删除上传
            String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
            if (StrUtil.isEmpty(contractId)) {
                return ResultInfo.error("申请上传失败！");
            }

            sbApply.setContractId(contractId);
            //机构签章
            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object_ = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", "单位公章", "0", "2", "2");
            JSONObject object = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", sbApply.getOrg_name(), "0", "2", "2");
            String result = (String) object.get("result");
            if (result.equals("success")) {
                sbApply.setPdf_path(object.getString("viewpdf_url"));
                sbApply.setDown_pdf_path(object.getString("download_url"));
                sbApply.insert();
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


    /**
     * 病房床位生成受理书
     * Author wzn
     * Date 2023/2/13 9:31
     */
    @RequestMapping("/bedViewPdf")
    public ResultInfo bedViewPdf(String id) {
        SysUser sysUser = sysUserService.getUser();
        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File templateFile = new File(rootFile, "/templates/受理书/9 其他病房床位.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;
        SbApply sbApply = sbApplyService.getById(id);
        sbApply.setYear(new DateTime().toString("yyyy"));
        sbApply.setMonth(new DateTime().toString("MM"));
        sbApply.setDay(new DateTime().toString("dd"));

        if ("政府非营利".equals(sbApply.getNatures())) {
            sbApply.setHeadline("公立");
        } else {
            sbApply.setHeadline("非公立");
        }
        //查询统筹区
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(new QueryWrapper<FixmedinsB>().eq("fixmedins_code", sbApply.getOrg_code()).eq("is_del", "0"));
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(new QueryWrapper<AdministrativeUnit>().eq("emp_no", fixmedinsB.getFix_blng_admdvs_sbApply()).eq("is_del", "0"));
        sbApply.setFix_blng_admdvs(administrativeUnit.getNotificationProcedureName());

        QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id", sbApply.getId());
        SbBedDeclaration sbBedDeclaration1 = sbBedDeclarationService.getOne(queryWrapper);
        sbApply.setSbBedDeclaration(sbBedDeclaration1);

        QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("bed_declaration_id", sbBedDeclaration1.getId());
        List<SbBedDetails> sbBedDetailsList = sbBedDetailsService.list(queryWrapper2);
        sbApply.setSbBedDetailsList(sbBedDetailsList);

        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("sbBedDetailsList", policy)
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
            sbApply.setAuth_contractId(contractId);
            //机构签章

            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, administrativeUnit.getEmp_no()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object = signService.autoSignZjqsBySeal(register.getCompany_customer_id(), contractId, "", "0", administrativeUnit.getNotificationProcedureName(), "0", "2", "2", register.getProcedures_seal_id());
            String result = (String) object.get("result");
            if (result.equals("success")) {
                sbApply.setAuth_pdf_path(object.getString("viewpdf_url"));
                sbApply.setAuth_down_pdf_path(object.getString("download_url"));
                sbApply.setStatus("4");
                sbApply.setAuth_trialer(sysUser.getId());
                sbApply.setAuth_time(new Date());
                sbApplyService.updateById(sbApply);
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


    @RequestMapping("/getInfo")
    public ResultInfo getInfo() {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixmedins_code", sysUser.getOrg_code());
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(queryWrapper);
        //1:营利性 2:民办非营利 3:政府非营利
        SbBedApplyVo sbBedApplyVo = new SbBedApplyVo();
        if (null != fixmedinsB) {
            sbBedApplyVo.setOrg_code(fixmedinsB.getFixmedins_code());
            sbBedApplyVo.setOrg_name(fixmedinsB.getFixmedins_name());
            if ("1".equals(fixmedinsB.getBiznet())) {
                sbBedApplyVo.setNatures("营利性");
            } else if ("2".equals(fixmedinsB.getBiznet())) {
                sbBedApplyVo.setNatures("民办非营利");
            } else if ("3".equals(fixmedinsB.getBiznet())) {
                sbBedApplyVo.setNatures("政府非营利");
            }
            HashMap<String, String> hashMap = sbApplyService.aggrement_lv();
            sbBedApplyVo.setAggrement_lv(hashMap.get(fixmedinsB.getAggrement_lv()));


        }
        return ResultInfo.success(sbBedApplyVo);
    }


    /**
     * 公立医院单人间、套间病房床位申报
     * Author wzn
     * Date 2023/5/31 8:47
     */
    @PostMapping("/soloBedDeclaration")
    public ResultInfo soloBedDeclaration(@RequestBody SbApply sbApply2) {
        SysUser sysUser = sysUserService.getUser();


        String username = sysUser.getUsername();
        //主表数据
        SbApply sbApply = new SbApply();
        sbApply.setOrg_code(sbApply2.getOrg_code());
        sbApply.setOrg_name(sbApply2.getOrg_name());
        sbApply.setUser_type("2");
        sbApply.setNatures(sbApply2.getNatures());
        sbApply.setId(IdUtil.simpleUUID());
        sbApply.setType("10");
        sbApply.setStatus("0");
        sbApply.setIs_del("0");
        sbApply.setCreateTime(new Date());
        sbApply.setCreateUser(username);
        if ("政府非营利".equals(sbApply2.getNatures())) {
            sbApply.setHeadline("公立");
        } else {
            sbApply.setHeadline("非公立");
        }

        SbBedDeclaration sbBedDeclaration = sbApply2.getSbBedDeclaration();
        sbBedDeclaration.setDept_name(sbBedDeclaration.getDept_name());
        sbBedDeclaration.setAggrement_lv(sbBedDeclaration.getAggrement_lv());
        sbBedDeclaration.setBeds_all_count(sbBedDeclaration.getBeds_all_count());
        sbBedDeclaration.setLinkman(sbBedDeclaration.getLinkman());
        sbBedDeclaration.setPhone(sbBedDeclaration.getPhone());
        sbBedDeclaration.setCreateTime(new Date());
        sbBedDeclaration.setCreateUser(username);
        sbBedDeclaration.setApply_id(sbApply.getId());
        sbBedDeclaration.insert();

        if (sbBedDeclaration.getFileInfoIds().length > 0) {
            for (int i = 0; i < sbBedDeclaration.getFileInfoIds().length; i++) {
                //绑定附件
                if (StringUtils.isNotBlank(sbBedDeclaration.getFileInfoIds()[i])) {
                    FileInfo fileInfo = fileInfoService.getById(sbBedDeclaration.getFileInfoIds()[i]);
                    fileInfo.setBizId(sbBedDeclaration.getId());
                    fileInfo.updateById();
                }
            }
        }


        for (int i = 0; i < sbApply2.getSbBedDetailsList().size(); i++) {



            SbBedDetails s = sbApply2.getSbBedDetailsList().get(i);

            List<SbApply> sbApplyList = sbBedDeclarationService.verify3(s.getProject_code(),"10");
            if(CollectionUtil.isNotEmpty(sbApplyList)){
                String oldPrice  = sbApplyList.get(0).getPrice() ;

                // 示例价格
                if(StringUtils.isNotEmpty(oldPrice)){
                    String price1 = oldPrice;
                    String price2 = s.getPrice();

                    // 将字符串转换为BigDecimal
                    BigDecimal bdPrice1 = new BigDecimal(price1);
                    BigDecimal bdPrice2 = new BigDecimal(price2);

                    // 计算price2是否比price1高50%
                    BigDecimal fiftyPercentMore = bdPrice1.multiply(new BigDecimal("1.5"));

                    if (bdPrice2.compareTo(fiftyPercentMore) >= 0) {
                        throw new RuntimeException( "提价幅度不得超过50%!") ;
                    }
                }

            }

            //回填名称和编码
            sbApply.setProject_code(s.getProject_code());
            sbApply.setProject_name(s.getProject_name());
            //回填计价单位和价格
            sbApply.setPrice(s.getPrice());
            //次主表
            SbBedDetails sbBedDetails = new SbBedDetails();
            sbBedDetails.setId(IdUtil.simpleUUID());
            sbBedDetails.setProject_code(s.getProject_code());
            sbBedDetails.setProject_name(s.getProject_name());
            sbBedDetails.setInpatientWard(s.getInpatientWard());
            sbBedDetails.setBed_declaration_id(sbBedDeclaration.getId());
            sbBedDetails.setBed_count(s.getBed_count());
            sbBedDetails.setBed_number(s.getBed_number());
            sbBedDetails.setBed_class(s.getBed_class());
            sbBedDetails.setPrice(s.getPrice());
            sbBedDetails.setRemark(s.getRemark());
            sbBedDetails.insert();
        }


        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File templateFile = new File(rootFile, "/templates/公立医院单人间、套间病房床位申报表.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;

        QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id", sbApply.getId());
        SbBedDeclaration sbBedDeclaration1 = sbBedDeclarationService.getOne(queryWrapper);
        sbApply.setSbBedDeclaration(sbBedDeclaration1);

        QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("bed_declaration_id", sbBedDeclaration1.getId());
        List<SbBedDetails> sbBedDetailsList = sbBedDetailsService.list(queryWrapper2);
        sbApply.setSbBedDetailsList(sbBedDetailsList);

        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("sbBedDetailsList", policy)
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
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "3").eq("bizId", sbApply.getId()));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("zsxm", multipartFile, "5", sbApply.getId());
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
//            tode 测试删除上传
            String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
            if (StrUtil.isEmpty(contractId)) {
                return ResultInfo.error("申请上传失败！");
            }
            sbApply.setContractId(contractId);
            //机构签章
            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object_ = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", "单位公章", "0", "2", "2");
            JSONObject object = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", sbApply.getOrg_name(), "0", "2", "2");
            String result = (String) object.get("result");
            if (result.equals("success")) {
                sbApply.setPdf_path(object.getString("viewpdf_url"));
                sbApply.setDown_pdf_path(object.getString("download_url"));
                sbApply.insert();
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


    /**
     * 公立医院单人间、套间病房床位生成受理书
     * Author wzn
     * Date 2023/5/31 8:52
     */
    @RequestMapping("/soloBedViewPdf")
    public ResultInfo soloBedViewPdf(String id) {
        SysUser sysUser = sysUserService.getUser();
        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File templateFile = new File(rootFile, "/templates/受理书/10 单人间、套间病房床位.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;
        SbApply sbApply = sbApplyService.getById(id);
        sbApply.setYear(new DateTime().toString("yyyy"));
        sbApply.setMonth(new DateTime().toString("MM"));
        sbApply.setDay(new DateTime().toString("dd"));

        if ("政府非营利".equals(sbApply.getNatures())) {
            sbApply.setHeadline("公立");
        } else {
            sbApply.setHeadline("非公立");
        }
        //查询统筹区
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(new QueryWrapper<FixmedinsB>().eq("fixmedins_code", sbApply.getOrg_code()).eq("is_del", "0"));
        AdministrativeUnit administrativeUnit = administrativeUnitService.getOne(new QueryWrapper<AdministrativeUnit>().eq("emp_no", fixmedinsB.getFix_blng_admdvs_sbApply()).eq("is_del", "0"));
        sbApply.setFix_blng_admdvs(administrativeUnit.getNotificationProcedureName());

        QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id", sbApply.getId());
        SbBedDeclaration sbBedDeclaration1 = sbBedDeclarationService.getOne(queryWrapper);
        sbApply.setSbBedDeclaration(sbBedDeclaration1);

        QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("bed_declaration_id", sbBedDeclaration1.getId());
        List<SbBedDetails> sbBedDetailsList = sbBedDetailsService.list(queryWrapper2);
        sbApply.setSbBedDetailsList(sbBedDetailsList);

        Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("sbBedDetailsList", policy)
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
            sbApply.setAuth_contractId(contractId);
            //机构签章

            NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, administrativeUnit.getEmp_no()).eq(NetTagRegister::getIs_del, 0));
            JSONObject object = signService.autoSignZjqsBySeal(register.getCompany_customer_id(), contractId, "", "0", administrativeUnit.getNotificationProcedureName(), "0", "2", "2", register.getProcedures_seal_id());
            String result = (String) object.get("result");
            if (result.equals("success")) {
                sbApply.setAuth_pdf_path(object.getString("viewpdf_url"));
                sbApply.setAuth_down_pdf_path(object.getString("download_url"));
                sbApply.setStatus("4");
                sbApply.setAuth_trialer(sysUser.getId());
                sbApply.setAuth_time(new Date());
                sbApplyService.updateById(sbApply);
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


    /**
     * 校验同一个机构，同一个项目6个月内 只能申请一次
     * Author wzn
     * Date 2023/5/31 15:55
     */
    @RequestMapping("/verify")
    public ResultInfo verify(String project_code) {
        SysUser sysUser = sysUserService.getUser();



        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("fixmedins_code",  sysUser.getOrg_code());
        FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper) ;

        String msg = "";

        //查是否有项目编码正在审核的，
        Integer count = sbBedDeclarationService.verify(project_code);
        if (count > 0) {
            msg = "该项目正在审核,禁止重复添加！";
            return ResultInfo.success(msg);
        }
        if(null != fixmedinsB){
            if("3".equals(fixmedinsB.getBiznet())){
                //查审核通过的  是否大于6个月
                BigDecimal bigDecimal = new BigDecimal(6);
                BigDecimal count2 = sbBedDeclarationService.verify2(project_code);
                if (null != count2) {
                    int flag = count2.compareTo(bigDecimal);
                    if (flag != 1) {
                        msg = "已申请,请六个月后再试！";
                        return ResultInfo.success(msg);
                    }
                }
            }

        }


        return ResultInfo.success(msg);
    }


    /**
     *签章
     * Author wzn
     * Date 2023/6/26 13:52
     */


    /**
     * 徐州市中医医疗服务项目价格调整word导出
     * Author wzn
     * Date 2023/6/26 14:47
     */
    @RequestMapping("/tcmWord")
    public void tcmWord(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File templateFile = new File(rootFile, "/templates/价格word导出.docx");
        String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
        String pdfFileName = IdUtil.simpleUUID() + ".pdf";
        String pdfPath = rootFile + "/TEMP/" + pdfFileName;
        Advice advice = adviceService.getById(id);
        advice.setFilling_time(DateUtil.format(advice.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<AdviceSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("advice_id", advice.getId());
        List<AdviceSummary> adviceSummaryList = adviceSummaryService.list(queryWrapper);

        for (AdviceSummary a : adviceSummaryList) {
            a.setFilling_time(advice.getFilling_time());
            a.setIndex(a.getIndex() + 1);
            //内涵一次性耗材消耗（按实际购进价计）
            if (StringUtils.isNotEmpty(a.getOne_cost_list())) {
                List<AdviceVo> one_cost_list = JSONObject.parseArray(a.getOne_cost_list(), AdviceVo.class);
                a.setOne_cost_list1(one_cost_list);
            }

            //电、水、气等常规消耗
            if (StringUtils.isNotEmpty(a.getConventional_cost_list())) {
                List<AdviceVo> conventional_cost_list = JSONObject.parseArray(a.getConventional_cost_list(), AdviceVo.class);
                a.setConventional_cost_list1(conventional_cost_list);
            }
            //劳务费用
            if (StringUtils.isNotEmpty(a.getLabor_cost_list())) {
                List<AdviceVo> labor_cost_list = JSONObject.parseArray(a.getLabor_cost_list(), AdviceVo.class);
                a.setLabor_cost_list1(labor_cost_list);
            }
            //仪器设备折旧费
            if (StringUtils.isNotEmpty(a.getDepreciation_cost_list())) {
                List<AdviceVo> depreciation_cost_list = JSONObject.parseArray(a.getDepreciation_cost_list(), AdviceVo.class);
                a.setDepreciation_cost_list1(depreciation_cost_list);
            }
            //仪器设备维修费
            if (StringUtils.isNotEmpty(a.getMaintenance_cost_list())) {
                List<AdviceVo> maintenance_cost_list = JSONObject.parseArray(a.getMaintenance_cost_list(), AdviceVo.class);
                a.setMaintenance_cost_list1(maintenance_cost_list);
            }
            //专用房屋折旧及维修
            if (StringUtils.isNotEmpty(a.getSpecial_cost_list())) {
                List<AdviceVo> special_cost_list = JSONObject.parseArray(a.getSpecial_cost_list(), AdviceVo.class);
                a.setSpecial_cost_list1(special_cost_list);
            }
            //房屋大修理费
            if (StringUtils.isNotEmpty(a.getHousing_cost_list())) {
                List<AdviceVo> housing_cost_list = JSONObject.parseArray(a.getHousing_cost_list(), AdviceVo.class);
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

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, "徐州市医疗服务项目价格调整.docx"));
        FileUtils.writeBytes(docxPath, response.getOutputStream());

    }


    /**
     * 中医院Excel导出
     * Author wzn
     * Date 2023/6/26 15:17
     */
    @RequestMapping("/tcmExcel")
    public void tcmExcel(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Advice advice = adviceService.getById(id);
        advice.setFilling_time(DateUtil.format(advice.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        QueryWrapper<AdviceSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("advice_id", advice.getId());
        List<AdviceSummary> adviceSummaryList = adviceSummaryService.list(queryWrapper);
        for (AdviceSummary a : adviceSummaryList) {
            a.setIndex(a.getIndex() + 1);
        }

        TemplateExportParams params = new TemplateExportParams("/templates/医疗服务.xlsx");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("advice", advice);
        map.put("adviceSummaryList", adviceSummaryList);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        File savefile = new File(printPath);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        String excelPath = printPath + "/" + IdUtil.simpleUUID() + ".xlsx";

        try {
            fos = new FileOutputStream(excelPath);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, "徐州市医疗服务项目价格调整.xlsx"));
        FileUtils.writeBytes(excelPath, response.getOutputStream());
        FileUtils.deleteFile(excelPath);
    }
}
