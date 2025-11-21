package com.jsdc.ybpt.service.declare;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
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
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.SbBedDeclarationMapper;
import com.jsdc.ybpt.mapper.SbBedDetailsMapper;
import com.jsdc.ybpt.mapper.declare.*;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.price.declare.*;
import com.jsdc.ybpt.price.zlproject.SbZlProject;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.UnfixedMechanismService;
import com.jsdc.ybpt.service.agreementsignService.NetTagRegisterService;
import com.jsdc.ybpt.service.agreementsignService.SignService;
import com.jsdc.ybpt.util.DocUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * (SbApply)表服务接口
 *
 * @author wangYan
 * @since 2023-02-01 16:22:06
 */
@Service
public class SbApplyService extends BaseService<SbApply> {
    @Autowired
    private SbApplyMapper sbApplyMapper;
    @Autowired
    private SbGovernmentMedicalMapper sbGovernmentMedicalMapper;
    @Autowired
    private SbMedicalCalculateMapper sbMedicalCalculateMapper;
    @Autowired
    private SbLabourMapper sbLabourMapper;
    @Autowired
    private SbFixedAssetsMapper sbFixedAssetsMapper;
    @Autowired
    private SbMaterialsConsumptionMapper sbMaterialsConsumptionMapper;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private SignService signService;
    @Autowired
    private NetTagRegisterService registerService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SbBusinessExplainService sbBusinessExplainService;
    @Autowired
    private SbZlProjectService sbZlProjectService;

    @Autowired
    private SbBusinessProjectService sbBusinessProjectService;

    @Autowired
    private SbGovernmentMedicalService sbGovernmentMedicalService;
    @Autowired
    private SbCivilianMedicalService sbCivilianMedicalService;


    @Autowired
    private SbCivilianMedicalMapper sbCivilianMedicalMapper;

    @Autowired
    private SbCivilianMaterialsMapper sbCivilianMaterialsMapper;

    @Autowired
    private UnfixedMechanismService unfixedMechanismService;

    @Autowired
    private SbBedDeclarationMapper sbBedDeclarationMapper;

    @Autowired
    private SbBedDetailsMapper sbBedDetailsMapper;

    @Value("${sbApplyPath}")
    private String path;

    /**
     * 告知手续列表接口
     * Author wzn
     * Date 2023/2/7 14:03
     */
    public Page<SbApply> sbApplyList(SbApply sbApply) {
        SysUser sysUser = sysUserService.getUser();
        Page<SbApply> sbApplyPage = new Page<>();
        if (StringUtils.isNotBlank(sbApply.getIs_export()) && "1".equals(sbApply.getIs_export())) {
            List<SbApply> list = sbApplyMapper.getApplyList(sbApply, sysUser);
            for (SbApply s : list) {
                if ("1".equals(s.getType())) {
                    s.setType("西药");
                } else if ("2".equals(s.getType())) {
                    s.setType("中成药");
                } else if ("3".equals(s.getType())) {
                    s.setType("中药饮片");
                } else if ("4".equals(s.getType())) {
                    s.setType("市场调节价项目");
                } else if ("5".equals(s.getType())) {
                    s.setType("药品耗材");
                } else if ("6".equals(s.getType())) {
                    s.setType("自设项目");
                } else if ("7".equals(s.getType())) {
                    s.setType("新增医疗服务项目");
                } else if ("8".equals(s.getType())) {
                    s.setType("市管未定价项目");
                } else if ("9".equals(s.getType())) {
                    s.setType("其他病房床位");
                } else if ("10".equals(s.getType())) {
                    s.setType("单人间、套房床位");
                }
                if ("0".equals(s.getStatus())) {
                    s.setStatus("待初审");
                } else if ("1".equals(s.getStatus())) {
                    s.setStatus("待复审");
                } else if ("2".equals(s.getStatus())) {
                    s.setStatus("待终审");
                } else if ("3".equals(s.getStatus())) {
                    s.setStatus("待生成受理书");
                } else if ("4".equals(s.getStatus())) {
                    s.setStatus("完成");
                } else if ("5".equals(s.getStatus())) {
                    s.setStatus("驳回");
                }
                if ("7".equals(s.getUser_type()) || "8".equals(s.getUser_type())) {
                    s.setUser_type("非定点");
                } else {
                    s.setUser_type("定点");
                }
            }
            sbApplyPage.setRecords(list);
        } else {
            sbApplyPage = sbApplyMapper.getApplyPage(new Page<>(sbApply.getPageNo(), sbApply.getPageSize()), sbApply, sysUser);
        }
        if (CollectionUtil.isNotEmpty(sbApplyPage.getRecords())) {
            for (SbApply s : sbApplyPage.getRecords()) {
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


        return sbApplyPage;
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
     * 公立医疗机构实行市场调节价管理医疗服务项目价格明细表保存
     *
     * @return
     */
    public ResultInfo insertGovernmentMedical(SbApply sbApply) {
        SysUser sysUser = sysUserService.getUser();

        //申诉信息
        String sb_apply_id = IdUtil.simpleUUID();
        sbApply.setId(sb_apply_id);
        sbApply.setCreateTime(new Date());
        sbApply.setUser_type(sysUser.getUser_type());
        sbApply.setIs_del("0");

        //公立医疗机构明细
        List<SbGovernmentMedical> sbGovernmentMedical = sbApply.getSbGovernmentMedical();
        AtomicBoolean istime = new AtomicBoolean(false);
        AtomicBoolean isPrice = new AtomicBoolean(false);
        AtomicReference<String> name = new AtomicReference<>("");
        sbGovernmentMedical.forEach(x -> {
            //回填名称和编码
            sbApply.setProject_code(x.getProject_code());
            sbApply.setProject_name(x.getProject_name());
            //回填计价单位和价格
            sbApply.setUnit(x.getUnit());
            sbApply.setPrice(x.getPrice());

            //先查询本机构是否已经申请过该项目
            QueryWrapper<SbGovernmentMedical> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("project_code", x.getProject_code());
            List<SbGovernmentMedical> sbGovernmentMedicals = sbGovernmentMedicalMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(sbGovernmentMedicals)) {
                for (SbGovernmentMedical s : sbGovernmentMedicals) {
                    QueryWrapper<SbApply> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("is_del", "0");
                    queryWrapper1.eq("id", s.getSb_apply_id());
                    queryWrapper1.and(tmp -> tmp.eq("status", "3").or().eq("status", "4"));
                    SbApply sbApply1 = sbApplyMapper.selectOne(queryWrapper1);
                    if (null != sbApply1) {
                        if (sbApply1.getOrg_code().equals(sysUser.getOrg_code())) {
                            //判定时间是否已经超过6个月
                            long dif = DateUtil.betweenMonth(new Date(), sbApply1.getEnd_time(), false);
                            if (dif < 6) {
                                name.set(s.getProject_name());
                                istime.set(true);
                                break;

                            }
                            //价格不能超过50%
                            // 示例价格
                            String price1 = sbApply1.getPrice();
                            String price2 = sbApply.getPrice();

                            // 将字符串转换为BigDecimal
                            BigDecimal bdPrice1 = new BigDecimal(price1);
                            BigDecimal bdPrice2 = new BigDecimal(price2);

                            // 计算price2是否比price1高50%
                            BigDecimal fiftyPercentMore = bdPrice1.multiply(new BigDecimal("1.5"));

                            if (bdPrice2.compareTo(fiftyPercentMore) >= 0) {
                                isPrice.set(true);
                                break;
                            }

                        }
                    }
                }
            }

            if (StringUtils.isNotBlank(x.getHigh_price())) {
                sbApply.setHigh_price(x.getHigh_price());
            }
            String sb_government_medical_id = IdUtil.simpleUUID();
            x.setId(sb_government_medical_id);
            x.setSb_apply_id(sb_apply_id);
            x.setIs_del("0");
            x.insert();
            //绑定附件
            if (StringUtils.isNotBlank(x.getFileInfoId())) {
                FileInfo fileInfo = fileInfoService.getById(x.getFileInfoId());
                fileInfo.setBizId(x.getId());
                fileInfo.updateById();
            }
            if (x.getSbMedicalCalculate() == null) {
                return;
            }
            //公立医疗机构明细-详情
            SbMedicalCalculate sbMedicalCalculate = x.getSbMedicalCalculate();
            String sb_medical_calculate_id = IdUtil.simpleUUID();
            sbMedicalCalculate.setId(sb_medical_calculate_id);
            sbMedicalCalculate.setSb_government_medical_id(sb_government_medical_id);
            x.setIs_del("0");
            sbMedicalCalculate.setTitle(x.getProject_name() + "服务项目定价测算表");
            sbMedicalCalculate.insert();
            //公立医疗机构明细-详情 劳务支出
            List<SbLabour> sbLabour = x.getSbLabour();
            for (SbLabour l : sbLabour) {
                l.setSb_medical_calculate_id(sb_medical_calculate_id);
                l.insert();
            }
            //公立医疗机构明细-详情 材料消耗支出
            List<SbFixedAssets> sbFixedAssets = x.getSbFixedAssets();
            for (SbFixedAssets f : sbFixedAssets) {
                f.setSb_medical_calculate_id(sb_medical_calculate_id);
                f.insert();
            }
            //公立医疗机构明细-详情 管理费及其他
            List<SbMaterialsConsumption> sbMaterialsConsumption = x.getSbMaterialsConsumption();
            for (SbMaterialsConsumption m : sbMaterialsConsumption) {
                m.setSb_medical_calculate_id(sb_medical_calculate_id);
                m.insert();
            }
            if (istime.get()) {
            }
        });





        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        queryWrapper.eq("fixmedins_code",  sysUser.getOrg_code());
        FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper) ;
        if(null != fixmedinsB){
            if("3".equals(fixmedinsB.getBiznet())){
                if (istime.get()) {
                    return ResultInfo.error(name + "已申请,请六个月后再试！！");
                }

                if (isPrice.get()) {
                    return ResultInfo.error(name + "提价幅度不得超过50%!");
                }
            }
        }



        //公立医疗机构明细 文档输出
        List<SbGovernmentMedical> sbGovernmentMedical_println = new ArrayList<>();
        for (int i = 0; i < sbGovernmentMedical.size(); i++) {
            sbGovernmentMedical.get(i).setIndex(i + 1);
            if (StringUtils.isNotBlank(sbGovernmentMedical.get(i).getHigh_price())) {
                if ("1".equals(sbGovernmentMedical.get(i).getHigh_price())) {
                    sbGovernmentMedical.get(i).setHigh_price("是");
                    sbGovernmentMedical_println.add(sbGovernmentMedical.get(i));
                } else if ("0".equals(sbGovernmentMedical.get(i).getHigh_price())) {
                    sbGovernmentMedical.get(i).setHigh_price("否");
                } else {
                    sbGovernmentMedical.get(i).setHigh_price("无");
                    sbGovernmentMedical_println.add(sbGovernmentMedical.get(i));
                }
            }

            String sb_government_medical_id = sbGovernmentMedical.get(i).getId();
            //公立医疗机构明细-详情
            SbMedicalCalculate sbMedicalCalculate = sbMedicalCalculateMapper.selectOne(Wrappers.<SbMedicalCalculate>lambdaQuery()
                    .eq(SbMedicalCalculate::getSb_government_medical_id, sb_government_medical_id)
            );
            if (sbMedicalCalculate == null) {
                continue;
            }
            sbGovernmentMedical.get(i).setSbMedicalCalculate(sbMedicalCalculate);

            String sb_medical_calculate_id = sbMedicalCalculate.getId();
            //公立医疗机构明细-详情 劳务支出
            List<SbLabour> sbLabour = sbLabourMapper.selectList(Wrappers.<SbLabour>lambdaQuery()
                    .eq(SbLabour::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            sbGovernmentMedical.get(i).setSbLabour(sbLabour);
            //公立医疗机构明细-详情 材料消耗支出
            List<SbFixedAssets> sbFixedAssets = sbFixedAssetsMapper.selectList(Wrappers.<SbFixedAssets>lambdaQuery()
                    .eq(SbFixedAssets::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            sbGovernmentMedical.get(i).setSbFixedAssets(sbFixedAssets);
            //公立医疗机构明细-详情 管理费及其他
            List<SbMaterialsConsumption> sbMaterialsConsumption = sbMaterialsConsumptionMapper.selectList(Wrappers.<SbMaterialsConsumption>lambdaQuery()
                    .eq(SbMaterialsConsumption::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            sbGovernmentMedical.get(i).setSbMaterialsConsumption(sbMaterialsConsumption);

        }

        sbApply.setSbGovernmentMedical(sbGovernmentMedical);
        sbApply.setSbGovernmentMedical_println(sbGovernmentMedical_println);

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
            File templateFile = new File(rootFile, "/templates/1.1公立.docx");
            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;
            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("sbGovernmentMedical", policy)
                    .bind("sbLabour", policy)
                    .bind("sbMaterialsConsumption", policy)
                    .bind("sbFixedAssets", policy)
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
            params = new FastDfsParams("price_declaration/apply", multipartFile, "5", sb_apply_id);
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
            if ("7".equals(sysUser.getUser_type()) || "8".equals(sysUser.getUser_type())) {
                sbApply.setPdf_path(fileInfo.getFileUrl());
                sbApply.setDown_pdf_path(fileInfo.getFileUrl());
                sbApply.insert();
            } else {
                NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
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
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    public ResultInfo lookStateHospital(String id) {
        //申诉信息
        SbApply sbApply = sbApplyMapper.selectById(id);
        //公立医疗机构明细
        List<SbGovernmentMedical> sbGovernmentMedical = sbGovernmentMedicalMapper.selectList(Wrappers.<SbGovernmentMedical>lambdaQuery()
                .eq(SbGovernmentMedical::getSb_apply_id, sbApply.getId())
                .eq(SbGovernmentMedical::getIs_del, "0")
        );
        AtomicInteger i = new AtomicInteger(1);
        sbGovernmentMedical.forEach(x -> {
            x.setIndex(i.getAndIncrement());
            String sb_government_medical_id = x.getId();
            //公立医疗机构明细-详情
            SbMedicalCalculate sbMedicalCalculate = sbMedicalCalculateMapper.selectOne(Wrappers.<SbMedicalCalculate>lambdaQuery()
                    .eq(SbMedicalCalculate::getSb_government_medical_id, sb_government_medical_id)
            );
            x.setSbMedicalCalculate(sbMedicalCalculate);
            String sb_medical_calculate_id = sbMedicalCalculate.getId();
            //公立医疗机构明细-详情 劳务支出
            List<SbLabour> sbLabour = sbLabourMapper.selectList(Wrappers.<SbLabour>lambdaQuery()
                    .eq(SbLabour::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            x.setSbLabour(sbLabour);
            //公立医疗机构明细-详情 材料消耗支出
            List<SbFixedAssets> sbFixedAssets = sbFixedAssetsMapper.selectList(Wrappers.<SbFixedAssets>lambdaQuery()
                    .eq(SbFixedAssets::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            x.setSbFixedAssets(sbFixedAssets);
            //公立医疗机构明细-详情 管理费及其他
            List<SbMaterialsConsumption> sbMaterialsConsumption = sbMaterialsConsumptionMapper.selectList(Wrappers.<SbMaterialsConsumption>lambdaQuery()
                    .eq(SbMaterialsConsumption::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            x.setSbMaterialsConsumption(sbMaterialsConsumption);
        });
        sbApply.setSbGovernmentMedical(sbGovernmentMedical);

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
            File templateFile = new File(rootFile, "/templates/公立.docx");
            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;
            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("sbGovernmentMedical", policy)
                    .bind("sbLabour", policy)
                    .bind("sbMaterialsConsumption", policy)
                    .bind("sbFixedAssets", policy)
                    .build();
            DocUtil.word2RedDocument(templateFile.getPath(), config, data, docxPath, pdfPath);
            Console.log(data);
            //上传文件服务器
            File pdfFile = new File(pdfPath);
            MultipartFile multipartFile = null;
            FastDfsParams params = null;
            //删除文件
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>().eq("bizType", "4").eq("bizId", id));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("price_declaration/apply", multipartFile, "4", id);
            params.setFileName(pdfFileName);
            return fastDfsUtil.uploadFile(params);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultInfo.success();
    }


    /**
     * 告知手续审核接口
     * Author wzn
     * Date 2023/2/10 14:47
     */
    public void audit(SbApply apply) {
        SysUser sysUser = sysUserService.getUser();
        SbApply sbApply = sbApplyMapper.selectById(apply.getId());
        FixmedinsB fixmedinsB = new FixmedinsB();
        if ("7".equals(sbApply.getUser_type()) || "8".equals(sbApply.getUser_type())) {
            UnfixedMechanism unfixedMechanism = unfixedMechanismService.getOne(Wrappers.<UnfixedMechanism>lambdaQuery()
                    .eq(UnfixedMechanism::getFixmedins_code, sbApply.getOrg_code())
                    .eq(UnfixedMechanism::getIs_del, "0")
            );
            fixmedinsB.setFix_blng_admdvs(unfixedMechanism.getFix_blng_admdvs());
            fixmedinsB.setAggrement_lv(unfixedMechanism.getAggrement_lv());
        } else {
            QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("fixmedins_code", sbApply.getOrg_code());
            fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper);
        }

        if (!"1".equals(sysUser.getUser_type())) {
            throw new CustomException("您无审核权限!");
        } else if ("1".equals(sysUser.getUser_type()) && !fixmedinsB.getFix_blng_admdvs_sbApply().equals(sysUser.getOrg_code())) {
            throw new CustomException("您无审核权限!");
        }
        if ("2".equals(sbApply.getStatus()) && StpUtil.hasRole("examine-third") && !"5".equals(apply.getStatus())) {

            if ("7".equals(sbApply.getUser_type()) || "8".equals(sbApply.getUser_type())) {
                sbApply.setStatus("4");
            } else {
                sbApply.setStatus("3");
            }

            sbApply.setEnd_trialer(sysUser.getName());
            sbApply.setEnd_time(new Date());
            //自设项目，添加入目录库
            if ("6".equals(sbApply.getType())) {
                List<SbBusinessProject> sbs = sbBusinessProjectService.list(new QueryWrapper<SbBusinessProject>().eq("apply_id", sbApply.getId()));
                for (SbBusinessProject sb : sbs) {
                    SbZlProject sbZlProject = sbZlProjectService.getOne(new QueryWrapper<SbZlProject>().eq("provincialProjectCode", sb.getProject_code()));
                    if (sbZlProject == null) {
                        SbZlProject sbZlProject_new = new SbZlProject();
                        sbZlProject_new.setId(IdUtil.simpleUUID());
                        sbZlProject_new.setProvincialProjectCode(sb.getProject_code());
                        sbZlProject_new.setDirectoryCoding(sb.getProject_code());
                        sbZlProject_new.setDirectoryName(sb.getProject_name());
                        sbZlProject_new.setChargeUnit(sb.getUnit());
                        sbZlProject_new.setProjectConnotation(sb.getProject_content());
                        sbZlProject_new.setExcludedContent(sb.getExcept_content());
                        sbZlProject_new.setExplain(sb.getExplain());
                        //1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级
                        if ("0".equals(sb.getChild_or_not())) {//非儿童
                            //非儿童一级限价
                            if ("1".equals(fixmedinsB.getAggrement_lv()) || "9".equals(fixmedinsB.getAggrement_lv())) {
                                sbZlProject_new.setNonChildOne(sb.getPrice());
                            } else if ("2".equals(fixmedinsB.getAggrement_lv())) {
                                sbZlProject_new.setNonChildTwo(sb.getPrice());
                            } else if ("3".equals(fixmedinsB.getAggrement_lv())) {
                                sbZlProject_new.setNonChildThree(sb.getPrice());
                            }
                        } else if ("1".equals(sb.getChild_or_not())) {
                            //儿童一级限价
                            if ("1".equals(fixmedinsB.getAggrement_lv()) || "9".equals(fixmedinsB.getAggrement_lv())) {
                                sbZlProject_new.setChildOne(sb.getPrice());
                            } else if ("2".equals(fixmedinsB.getAggrement_lv())) {
                                sbZlProject_new.setChildTwo(sb.getPrice());
                            } else if ("3".equals(fixmedinsB.getAggrement_lv())) {
                                sbZlProject_new.setChildThree(sb.getPrice());
                            }
                        }

                        sbZlProject_new.setIs_autonomy("1");
                        sbZlProject_new.setBelongToOrg(sb.getDept_code());
                        sbZlProject_new.insert();
                    } else {
                        throw new CustomException("项目编码已经存在，审核失败!");
                    }
                }
            }
        } else if ("1".equals(sbApply.getStatus()) && StpUtil.hasRole("examine-second") && !"5".equals(apply.getStatus())) {
            sbApply.setSecond_trialer(sysUser.getName());
            sbApply.setSecond_time(new Date());
            sbApply.setStatus("2");
        } else if ("0".equals(sbApply.getStatus()) && StpUtil.hasRole("examine-first") && !"5".equals(apply.getStatus())) {
            sbApply.setFirst_trialer(sysUser.getName());
            sbApply.setFirst_time(new Date());
            sbApply.setStatus("1");
        } else if (apply.getStatus().equals("5")) {
            sbApply.setRejecter(sysUser.getName());
            sbApply.setReject_time(new Date());
            sbApply.setStatus("5");
            sbApply.setReason(apply.getReason());
        }
        sbApply.updateById();
    }

    /**
     * 非公立医疗机构医疗服务项目自主定价明细表 提交
     *
     * @param sbApply
     * @return
     */
    public ResultInfo insertCivilianMedical(SbApply sbApply) {
        SysUser sysUser = sysUserService.getUser();
        //申诉信息
        String sb_apply_id = IdUtil.simpleUUID();
        sbApply.setId(sb_apply_id);
        sbApply.setCreateTime(new Date());
        sbApply.setUser_type(sysUser.getUser_type());
        sbApply.setIs_del("0");

        //非公立医疗机构明细
        List<SbCivilianMedical> sbCivilianMedical = sbApply.getSbCivilianMedical();
        if (sbCivilianMedical != null) {
            AtomicBoolean istime = new AtomicBoolean(false);
            AtomicBoolean isPrice = new AtomicBoolean(false);
            AtomicReference<String> name = new AtomicReference<>("");
            sbCivilianMedical.forEach(x -> {

                //回填名称和编码
                sbApply.setProject_code(x.getProject_code());
                sbApply.setProject_name(x.getProject_name());
                //回填计价单位和价格
                sbApply.setUnit(x.getUnit());
                sbApply.setPrice(x.getPrice());




                if (StringUtils.isNotBlank(x.getHigh_price())) {
                    sbApply.setHigh_price(x.getHigh_price());
                }
                String sb_government_medical_id = IdUtil.simpleUUID();
                x.setId(sb_government_medical_id);
                x.setSb_apply_id(sb_apply_id);
                x.setIs_del("0");
                x.insert();
                //绑定附件
                if (StringUtils.isNotBlank(x.getFileInfoId())) {
                    FileInfo fileInfo = fileInfoService.getById(x.getFileInfoId());
                    fileInfo.setBizId(x.getId());
                    fileInfo.updateById();
                }
                if (x.getSbMedicalCalculate() == null) {
                    return;
                }
                //非公立医疗机构明细-详情
                SbMedicalCalculate sbMedicalCalculate = x.getSbMedicalCalculate();
                String sb_medical_calculate_id = IdUtil.simpleUUID();
                sbMedicalCalculate.setId(sb_medical_calculate_id);
                sbMedicalCalculate.setSb_government_medical_id(sb_government_medical_id);
                x.setIs_del("0");
                sbMedicalCalculate.setTitle(x.getProject_name() + "服务项目定价测算表");
                sbMedicalCalculate.insert();
                //非公立医疗机构明细-详情 劳务支出
                List<SbLabour> sbLabour = x.getSbLabour();
                for (SbLabour l : sbLabour) {
                    l.setSb_medical_calculate_id(sb_medical_calculate_id);
                    l.insert();
                }
                //非公立医疗机构明细-详情 材料消耗支出
                List<SbFixedAssets> sbFixedAssets = x.getSbFixedAssets();
                for (SbFixedAssets f : sbFixedAssets) {
                    f.setSb_medical_calculate_id(sb_medical_calculate_id);
                    f.insert();
                }
                //非公立医疗机构明细-详情 管理费及其他
                List<SbMaterialsConsumption> sbMaterialsConsumption = x.getSbMaterialsConsumption();
                for (SbMaterialsConsumption m : sbMaterialsConsumption) {
                    m.setSb_medical_calculate_id(sb_medical_calculate_id);
                    m.insert();
                }

                //先查询本机构是否已经申请过该项目
                QueryWrapper<SbCivilianMedical> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("project_code", x.getProject_code());
                List<SbCivilianMedical> sbCivilianMedicals = sbCivilianMedicalMapper.selectList(queryWrapper);
                if (CollectionUtil.isNotEmpty(sbCivilianMedicals)) {
                    for (SbCivilianMedical s : sbCivilianMedicals) {
                        QueryWrapper<SbApply> queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("is_del", "0");
                        queryWrapper1.eq("id", s.getSb_apply_id());
                        queryWrapper1.and(tmp -> tmp.eq("status", "3").or().eq("status", "4"));
                        SbApply sbApply1 = sbApplyMapper.selectOne(queryWrapper1);
                        if (null != sbApply1) {
                            if (sbApply1.getOrg_code().equals(sysUser.getOrg_code())) {
                                //判定时间是否已经超过6个月
                                long dif = DateUtil.betweenMonth(new Date(), sbApply1.getEnd_time(), false);
                                if (dif < 6) {
                                    istime.set(true);
                                    name.set(s.getProject_name());
                                    break;
                                }

                                //价格不能超过50%
                                // 示例价格
                                String price1 = sbApply1.getPrice();
                                String price2 = sbApply.getPrice();

                                // 将字符串转换为BigDecimal
                                BigDecimal bdPrice1 = new BigDecimal(price1);
                                BigDecimal bdPrice2 = new BigDecimal(price2);

                                // 计算price2是否比price1高50%
                                BigDecimal fiftyPercentMore = bdPrice1.multiply(new BigDecimal("1.5"));

                                if (bdPrice2.compareTo(fiftyPercentMore) >= 0) {
                                    isPrice.set(true);
                                    break;
                                }
                            }
                        }
                    }
                    if (istime.get()) {
                        return;
                    }
                }
            });



            QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_del", "0");
            queryWrapper.eq("fixmedins_code",  sysUser.getOrg_code());
            FixmedinsB fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper) ;

            if(null != fixmedinsB){
                if("3".equals(fixmedinsB.getBiznet())){
                    if (istime.get()) {
                        return ResultInfo.error(name + "已申请,请六个月后再试！！");
                    }

                    if (isPrice.get()) {
                        return ResultInfo.error(name + "提价幅度不得超过50%!");
                    }
                }
            }

        }

        //非公立医疗机构明细 文档输出
        List<SbCivilianMedical> sbCivilianMedical_println = new ArrayList<>();
        for (int i = 0; i < sbCivilianMedical.size(); i++) {
            sbCivilianMedical.get(i).setIndex(i + 1);
            if (StringUtils.isNotBlank(sbCivilianMedical.get(i).getHigh_price())) {
                if ("1".equals(sbCivilianMedical.get(i).getHigh_price())) {
                    sbCivilianMedical.get(i).setHigh_price("是");
                    sbCivilianMedical_println.add(sbCivilianMedical.get(i));
                } else if ("0".equals(sbCivilianMedical.get(i).getHigh_price())) {
                    sbCivilianMedical.get(i).setHigh_price("否");
                } else {
                    sbCivilianMedical.get(i).setHigh_price("无");
                    sbCivilianMedical_println.add(sbCivilianMedical.get(i));
                }
            }
            String sb_government_medical_id = sbCivilianMedical.get(i).getId();
            //非公立医疗机构明细-详情
            SbMedicalCalculate sbMedicalCalculate = sbMedicalCalculateMapper.selectOne(Wrappers.<SbMedicalCalculate>lambdaQuery()
                    .eq(SbMedicalCalculate::getSb_government_medical_id, sb_government_medical_id)
            );
            if (sbMedicalCalculate == null) {
                continue;
            }
            sbCivilianMedical.get(i).setSbMedicalCalculate(sbMedicalCalculate);

            String sb_medical_calculate_id = sbMedicalCalculate.getId();
            //公立医疗机构明细-详情 劳务支出
            List<SbLabour> sbLabour = sbLabourMapper.selectList(Wrappers.<SbLabour>lambdaQuery()
                    .eq(SbLabour::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            sbCivilianMedical.get(i).setSbLabour(sbLabour);
            //公立医疗机构明细-详情 材料消耗支出
            List<SbFixedAssets> sbFixedAssets = sbFixedAssetsMapper.selectList(Wrappers.<SbFixedAssets>lambdaQuery()
                    .eq(SbFixedAssets::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            sbCivilianMedical.get(i).setSbFixedAssets(sbFixedAssets);
            //公立医疗机构明细-详情 管理费及其他
            List<SbMaterialsConsumption> sbMaterialsConsumption = sbMaterialsConsumptionMapper.selectList(Wrappers.<SbMaterialsConsumption>lambdaQuery()
                    .eq(SbMaterialsConsumption::getSb_medical_calculate_id, sb_medical_calculate_id)
            );
            sbCivilianMedical.get(i).setSbMaterialsConsumption(sbMaterialsConsumption);
        }

        sbApply.setSbCivilianMedical(sbCivilianMedical);
        sbApply.setSbCivilianMedical_println(sbCivilianMedical_println);

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
            File templateFile = new File(rootFile, "/templates/2.1非公立.docx");
            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;
            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("sbCivilianMedical", policy)
                    .bind("sbCivilianMedical_println", policy)
                    .bind("sbLabour", policy)
                    .bind("sbMaterialsConsumption", policy)
                    .bind("sbFixedAssets", policy)
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
            params = new FastDfsParams("price_declaration/apply", multipartFile, "5", sb_apply_id);
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
            if ("7".equals(sysUser.getUser_type()) || "8".equals(sysUser.getUser_type())) {
                sbApply.setPdf_path(fileInfo.getFileUrl());
                sbApply.setDown_pdf_path(fileInfo.getFileUrl());
                sbApply.insert();
            } else {
                NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
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
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    /**
     * 自主定价明细表 提交
     *
     * @param sbApply
     * @return
     */
    public ResultInfo insertCivilianMaterial(SbApply sbApply) {
        SysUser sysUser = sysUserService.getUser();
        //申诉信息
        String sb_apply_id = IdUtil.simpleUUID();
        sbApply.setId(sb_apply_id);
        sbApply.setCreateTime(new Date());
        sbApply.setUser_type(sysUser.getUser_type());
        sbApply.setIs_del("0");

        //药品耗材
        List<SbCivilianMaterials> sbCivilianMaterial = sbApply.getSbCivilianMaterial();

        AtomicBoolean istime = new AtomicBoolean(false);
        AtomicReference<String> name = new AtomicReference<>("");
        sbCivilianMaterial.forEach(x -> {
            //回填名称和编码
            sbApply.setProject_code(x.getProject_code());
            sbApply.setProject_name(x.getProject_name());
            //回填计价单位和价格
            sbApply.setUnit(x.getUnit());
            sbApply.setPrice(x.getOrg_price());

            //先查询本机构是否已经申请过该项目
            QueryWrapper<SbCivilianMaterials> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("project_code", x.getProject_code());
            List<SbCivilianMaterials> civilianMaterials = sbCivilianMaterialsMapper.selectList(queryWrapper);

            for (SbCivilianMaterials s : civilianMaterials) {
                QueryWrapper<SbApply> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("is_del", "0");
                queryWrapper1.eq("id", s.getSb_apply_id());
                queryWrapper1.and(tmp -> tmp.eq("status", "3").or().eq("status", "4"));
                SbApply sbApply1 = sbApplyMapper.selectOne(queryWrapper1);
                if (null != sbApply1) {
                    if (sbApply1.getOrg_code().equals(sysUser.getOrg_code())) {
                        //判定时间是否已经超过6个月
                        long dif = DateUtil.betweenMonth(new Date(), sbApply1.getEnd_time(), false);
                        if (dif < 12) {
                            istime.set(true);

                            name.set(s.getProject_name());
                            break;
                        }
                    }
                }
            }
            if (istime.get()) {
                return;
            }

            String sb_government_medical_id = IdUtil.simpleUUID();
            x.setId(sb_government_medical_id);
            x.setSb_apply_id(sb_apply_id);
            x.setIs_del("0");
            x.insert();
        });

        if (istime.get()) {
            return ResultInfo.error(name + "已申请,请十二个月后再试！！");
        }

        AtomicInteger i = new AtomicInteger(1);
        sbCivilianMaterial.forEach(x -> {
            x.setIndex(i.getAndIncrement());
            if (StringUtils.isNotEmpty(x.getIs_purchase())) {
                x.setIs_purchase(x.getIs_purchase().equals("0") ? "否" : "是");
            }
        });
        sbApply.setSbCivilianMaterial(sbCivilianMaterial);

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
            File templateFile = new File(rootFile, "/templates/药品耗材.docx");
            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;
            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(sbApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("sbCivilianMaterial", policy)
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
            params = new FastDfsParams("price_declaration/apply", multipartFile, "5", sb_apply_id);
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
            if ("7".equals(sysUser.getUser_type()) || "8".equals(sysUser.getUser_type())) {
                sbApply.setPdf_path(fileInfo.getFileUrl());
                sbApply.setDown_pdf_path(fileInfo.getFileUrl());
                sbApply.insert();
            } else {
                NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
                JSONObject object = signService.autoSignZjqs(register.getCompany_customer_id(), contractId, "", "0", sbApply.getOrg_name(), "0", "2", "2");
                String result = (String) object.get("result");
                if (result.equals("success")) {
                    sbApply.setPdf_path(object.getString("viewpdf_url"));
                    sbApply.setDown_pdf_path(object.getString("download_url"));
                    sbApply.insert();
                } else {
                    String msg = (String) object.get("msg");
                    return ResultInfo.error("机构签章失败！");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    public String down(SbApplyVo bean, HttpServletRequest request, HttpServletResponse response) {
        String file_name = "";
        Map<String, Object> data = new HashMap<String, Object>();
        TemplateExportParams params = new TemplateExportParams();

        String title = "政府非营利".equals(bean.getNatures()) ? "公立" : "非公立";
        switch (bean.getType()) {
            case "4":
                bean.setTitle(title + "医疗机构实行市场调节价管理医疗服务项目价格明细表");
                break;
            case "6":
                bean.setTitle(title + "医疗机构医疗服务自设项目自主定价明细表");
                break;
            case "7":
                bean.setTitle(title + "医疗机构新增医疗服务项目价格明细表");
                break;
            case "8":
                bean.setTitle(title + "医疗机构市管未定价项目医疗服务项目价格明细表");
                break;
            case "9":
                bean.setTitle(title + "医院病房床位明细");
                break;
            case "10":
                bean.setTitle(title + "医院单人间、套间病房床位明细");
                break;
        }

        if ("政府非营利".equals(bean.getNatures())) {
            if ("9".equals(bean.getType())) {
                //
                QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("apply_id", bean.getId());
                SbBedDeclaration sbBedDeclaration = sbBedDeclarationMapper.selectOne(queryWrapper);
                if (null != sbBedDeclaration) {
                    //查明细子表
                    data.put("sbBedDeclaration", sbBedDeclaration);
                    QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("bed_declaration_id", sbBedDeclaration.getId());
                    List<SbBedDetails> sbBedDetails = sbBedDetailsMapper.selectList(queryWrapper2);
                    data.put("sbBedDetails", sbBedDetails);
                }
                params = new TemplateExportParams("templates/下载明细/9 其他病房床位.xlsx");
            } else if ("10".equals(bean.getType())) {
                QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("apply_id", bean.getId());
                SbBedDeclaration sbBedDeclaration = sbBedDeclarationMapper.selectOne(queryWrapper);
                if (null != sbBedDeclaration) {
                    //查明细子表
                    data.put("sbBedDeclaration", sbBedDeclaration);
                    QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("bed_declaration_id", sbBedDeclaration.getId());
                    List<SbBedDetails> sbBedDetails = sbBedDetailsMapper.selectList(queryWrapper2);
                    data.put("sbBedDetails", sbBedDetails);
                }
                params = new TemplateExportParams("templates/下载明细/10 单人间、套间病房床位.xlsx");
            } else {
                List<SbGovernmentMedical> sbGovernmentMedical = sbGovernmentMedicalMapper.selectList(Wrappers.<SbGovernmentMedical>lambdaQuery()
                        .eq(SbGovernmentMedical::getSb_apply_id, bean.getId())
                );
                for (int i = 0; i < sbGovernmentMedical.size(); i++) {
                    sbGovernmentMedical.get(i).setIndex(i + 1);
                }
                data.put("mapList", sbGovernmentMedical);
                params = new TemplateExportParams("templates/下载明细/公立医疗服务.xlsx");
            }

        } else if (("营利性".equals(bean.getNatures()) || "民办非营利".equals(bean.getNatures())) && !"6".equals(bean.getType())) {
            if ("9".equals(bean.getType())) {
                QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("apply_id", bean.getId());
                SbBedDeclaration sbBedDeclaration = sbBedDeclarationMapper.selectOne(queryWrapper);
                if (null != sbBedDeclaration) {
                    //查明细子表
                    data.put("sbBedDeclaration", sbBedDeclaration);
                    QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("bed_declaration_id", sbBedDeclaration.getId());
                    List<SbBedDetails> sbBedDetails = sbBedDetailsMapper.selectList(queryWrapper2);
                    data.put("sbBedDetails", sbBedDetails);
                }
                params = new TemplateExportParams("templates/下载明细/9 其他病房床位.xlsx");
            } else if ("10".equals(bean.getType())) {
                QueryWrapper<SbBedDeclaration> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("apply_id", bean.getId());
                SbBedDeclaration sbBedDeclaration = sbBedDeclarationMapper.selectOne(queryWrapper);
                if (null != sbBedDeclaration) {
                    //查明细子表
                    data.put("sbBedDeclaration", sbBedDeclaration);
                    QueryWrapper<SbBedDetails> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("bed_declaration_id", sbBedDeclaration.getId());
                    List<SbBedDetails> sbBedDetails = sbBedDetailsMapper.selectList(queryWrapper2);
                    data.put("sbBedDetails", sbBedDetails);
                }
                params = new TemplateExportParams("templates/下载明细/10 单人间、套间病房床位.xlsx");
            } else {
                List<SbCivilianMedical> sbCivilianMedical = sbCivilianMedicalMapper.selectList(Wrappers.<SbCivilianMedical>lambdaQuery()
                        .eq(SbCivilianMedical::getSb_apply_id, bean.getId())
                );
                for (int i = 0; i < sbCivilianMedical.size(); i++) {
                    sbCivilianMedical.get(i).setIndex(i + 1);
                    if (StringUtils.isNotBlank(sbCivilianMedical.get(i).getHigh_price())) {
                        if ("1".equals(sbCivilianMedical.get(i).getHigh_price())) {
                            sbCivilianMedical.get(i).setHigh_price("是");
                        } else if ("0".equals(sbCivilianMedical.get(i).getHigh_price())) {
                            sbCivilianMedical.get(i).setHigh_price("否");
                        } else {
                            sbCivilianMedical.get(i).setHigh_price("无");

                        }
                    }
                }
                data.put("mapList", sbCivilianMedical);
                params = new TemplateExportParams("/templates/下载明细/非公立医疗服务.xlsx");
            }

        } else if ("6".equals(bean.getType())) {
            QueryWrapper<SbBusinessProject> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("apply_id", bean.getId());
            SbBusinessProject sbBusinessProject = sbBusinessProjectService.getOne(queryWrapper);
            //service_price0 否  1是
            if (StringUtils.isNotEmpty(sbBusinessProject.getService_price())) {
                if ("0".equals(sbBusinessProject.getService_price())) {
                    sbBusinessProject.setService_price("否");
                } else {
                    sbBusinessProject.setService_price("是");
                }
            }
            data.put("sbBusinessProject", sbBusinessProject);

            params = new TemplateExportParams("templates/下载明细/6 自设项目.xlsx");

        }

        data.put("bean", bean);
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


            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, file_name));
            FileUtils.writeBytes(path + "/" + file_name, response.getOutputStream());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name;
    }
}

