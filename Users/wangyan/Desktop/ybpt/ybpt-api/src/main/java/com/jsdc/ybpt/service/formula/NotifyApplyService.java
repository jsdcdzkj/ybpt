package com.jsdc.ybpt.service.formula;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.jsdc.ybpt.agreementsignModel.NetTagRegister;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.formula.domain.entity.Catalog;
import com.jsdc.ybpt.formula.domain.entity.CheckFee;
import com.jsdc.ybpt.formula.domain.entity.FixedAssetsDepre;
import com.jsdc.ybpt.formula.domain.entity.Labor;
import com.jsdc.ybpt.formula.domain.entity.ManageLossOtherFee;
import com.jsdc.ybpt.formula.domain.entity.MaterialConsume;
import com.jsdc.ybpt.formula.domain.entity.NotifyApply;
import com.jsdc.ybpt.formula.domain.entity.PriceCalculate;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.formula.NotifyApplyMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.agreementsignService.NetTagRegisterService;
import com.jsdc.ybpt.service.agreementsignService.SignService;
import com.jsdc.ybpt.util.DocUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 制剂告知申请(NotifyApply)业务接口
 *
 * @author yc
 * @since 2024-05-14 11:24:46
 */
@Service
@Slf4j
public class NotifyApplyService extends BaseService<NotifyApply> {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private NotifyApplyMapper notifyApplyMapper;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private FastDfsUtil fastDfsUtil;

    @Autowired
    private SignService signService;

    @Autowired
    private NetTagRegisterService registerService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private CheckFeeService checkFeeService;

    @Autowired
    private LaborService laborService;

    @Autowired
    private ManageLossOtherFeeService manageLossOtherFeeService;

    @Autowired
    private MaterialConsumeService materialConsumeService;

    @Autowired
    private FixedAssetsDepreService fixedAssetsDepreService;

    @Autowired
    private NotifyApplyService notifyApplyService;

    @Autowired
    private PriceCalculateService priceCalculateService;

    /**
     * 制剂告知审核列表
     * Author wb
     * Date 2024/05/15 14:26
     */
    public Page<NotifyApply> selectPreparationNotificationReviewList(NotifyApply notifyApply) {
        SysUser sysUser = sysUserService.getUser();
        Page<NotifyApply> notifyApplyPage = new Page<>();
        notifyApplyPage = notifyApplyMapper.getApplyPage(new Page<>(notifyApply.getPageNo(), notifyApply.getPageSize()), notifyApply, sysUser);
        if (CollectionUtil.isNotEmpty(notifyApplyPage.getRecords())) {
            for (NotifyApply s : notifyApplyPage.getRecords()) {
                //查询统筹区
                HashMap<String, String> tcqMap = new HashMap();
                List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
                tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
                s.setFixBlngAdmdvsName(tcqMap.get(s.getFixBlngAdmdvs()));
                s.setIsAudit("false");
                //判断是否有审核权限。前端展示用
                if ("1".equals(sysUser.getUser_type()) && sysUser.getOrg_code().equals(s.getFixBlngAdmdvs())) {
                    s.setIsAudit("true");
                }
                //协议等级
                FixmedinsB fixmedinsB = new FixmedinsB();

                QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("fixmedins_code", s.getOrgCode());
                fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper);
                if (fixmedinsB != null) {
                    s.setAggrementLv(aggrement_lv().get(fixmedinsB.getAggrement_lv()));
                }

                //经营性质
                if ("1".equals(s.getBiznet())) {
                    s.setBiznet("营利性");
                } else if ("2".equals(s.getBiznet())) {
                    s.setBiznet("民办非营利");
                } else if ("3".equals(s.getBiznet())) {
                    s.setBiznet("政府非营利");
                }

                //是否在医保制剂目录
                if ("1".equals(s.getIsInCategory())) {
                    s.setIsInCategoryDesc("是");
                } else if ("0".equals(s.getIsInCategory())) {
                    s.setIsInCategoryDesc("否");
                }
            }
        }


        return notifyApplyPage;
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
     * 功能描述: <br>
     * 〈制剂告知申请列表〉
     *
     * @param notifyApply
     * @Return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jsdc.ybpt.formula.domain.entity.NotifyApply>
     * @Author: yc
     * @Date: 2024/5/15 16:46
     */
    public Page<NotifyApply> applyPage(NotifyApply notifyApply) {

        String isExport = notifyApply.getIsExport();

        Page<NotifyApply> notifyApplyPage = notifyApplyMapper.applyPage(new Page<>(notifyApply.getPageNo(), notifyApply.getPageSize()), notifyApply);

        if (CollectionUtil.isNotEmpty(notifyApplyPage.getRecords())) {
            for (NotifyApply s : notifyApplyPage.getRecords()) {
                //查询统筹区
                HashMap<String, String> tcqMap = new HashMap();
                List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
                tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));

                s.setFixBlngAdmdvsName(tcqMap.get(s.getFixBlngAdmdvs()));

                //协议等级
                String orgCode = s.getOrgCode();
                if (StringUtils.isNotBlank(orgCode)) {

                    FixmedinsB fixmedinsB = new FixmedinsB();

                    QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("fixmedins_code", s.getOrgCode());
                    fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper);
                    if (fixmedinsB != null) {
                        s.setAggrementLv(aggrement_lv().get(fixmedinsB.getAggrement_lv()));

                        if (StringUtils.isNotBlank(isExport) && isExport.equals("1")) {
                            //1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级
                            String aggrementLv = s.getAggrementLv();
                            if (StringUtils.isNotBlank(aggrementLv)) {

                                if (aggrementLv.equals("1")) {
                                    aggrementLv = "一级";
                                } else if (aggrementLv.equals("2")) {
                                    aggrementLv = "二级";
                                } else if (aggrementLv.equals("3")) {
                                    aggrementLv = "三级";
                                } else if (aggrementLv.equals("4")) {
                                    aggrementLv = "A级";
                                } else if (aggrementLv.equals("5")) {
                                    aggrementLv = "B级";
                                } else if (aggrementLv.equals("6")) {
                                    aggrementLv = "C级";
                                } else if (aggrementLv.equals("9")) {
                                    aggrementLv = "未定级";
                                }
                            }
                            s.setAggrementLv(aggrementLv);
                        }

                    }
                }
                //经营性质
//                biznet
                if (StringUtils.isNotBlank(isExport) && isExport.equals("1")) {
//                    1:营利性 2:民办非营利 3:政府非营利
                    String biznet = s.getBiznet();
                    if (StringUtils.isNotBlank(biznet)) {
                        if (biznet.equals("1")) {
                            biznet = "营利性";
                        } else if (biznet.equals("2")) {
                            biznet = "民办非营利";
                        } else if (biznet.equals("3")) {
                            biznet = "政府非营利";
                        }
                        s.setBiznet(biznet);
                    }

                    String isInCategory = s.getIsInCategory();
                    if (StringUtils.isNotBlank(isInCategory)) {

                        if (isInCategory.equals("1")) {
                            s.setIsInCategoryDesc("是");
                        } else {
                            s.setIsInCategoryDesc("否");
                        }
                    }
                    if (StringUtils.isNotBlank(s.getStatus())) {

                        //审核流程
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
                    }

                }
            }
        }
        return notifyApplyPage;
    }

    /**
     * 功能描述: <br>
     * 〈保存申请〉
     *
     * @param notifyApply
     * @Return: boolean
     * @Author: yc
     * @Date: 2024/5/15 16:50
     */
    @Transactional
    public ResultInfo saveVO(NotifyApply notifyApply) {

        String isInCategory = notifyApply.getIsInCategory();
        String nationalFormulaCode = notifyApply.getNationalFormulaCode();


        //默认值
        notifyApply.setStatus("0");
        notifyApply.setIsDel("0");
        notifyApply.setRejectReason("");
        notifyApply.setRejectTime(null);
        notifyApply.setRejectUser("");
        notifyApply.setFirstCheckUser("");
        notifyApply.setFirstCheckTime(null);
        notifyApply.setSecondCheckUser("");
        notifyApply.setSecondCheckTime(null);
        notifyApply.setFinishCheckUser("");
        notifyApply.setFinishCheckTime(null);

        String idOld = notifyApply.getId();
        SysUser sysUser = sysUserService.getUser();
        //修改
        if (StringUtils.isNotBlank(idOld)) {
            checkSaveItem(notifyApply, idOld);
            notifyApply.setUpdateuser(sysUser.getName());
            notifyApply.setUpdatetime(new Date());
            this.updateById(notifyApply);
        } else {

            //校验新增操作
            checkSaveItem(notifyApply, null);

            notifyApply.setCreatetime(new Date());
            notifyApply.setCreateuser(sysUser.getName());
            this.save(notifyApply);
        }

        String id = notifyApply.getId();
        //保存测算表信息
        saveItemList(notifyApply);

        if (isInCategory.equals("0")) {
            notifyApply.setIsInCategoryDesc("否");
        } else {
            notifyApply.setIsInCategoryDesc("是");
        }

        //生成pdf信息
        ////todo-yc  pro
        createPDFPRO(notifyApply);
        //测试
//        createPDFUAT(notifyApply);

        //修改信息
        updateById(notifyApply);

        //保存附件信息
        List<FileInfo> fileInfoList = notifyApply.getFileInfoList();
        if (CollectionUtil.isEmpty(fileInfoList)) {
            throw new CustomException("请上传附件");
        }
        for (FileInfo fileInfo : fileInfoList) {
            fileInfo.setBizId(id);
            fileInfoService.updateById(fileInfo);
        }

        return ResultInfo.success();
    }

    private void checkSaveItem(NotifyApply notifyApply, String idOld) {


        String isInCategory = notifyApply.getIsInCategory();
        String nationalFormulaCode = notifyApply.getNationalFormulaCode();
        String orgCode = notifyApply.getOrgCode();

        if (StringUtils.isBlank(isInCategory)) {
            throw new CustomException("isInCategory 为空");
        }

        if (StringUtils.isBlank(nationalFormulaCode)) {
            throw new CustomException("nationalFormulaCode 为空");
        }
        if (StringUtils.isBlank(orgCode)) {
            throw new CustomException("orgCode 为空");
        }

        if (isInCategory.equals("0")) {
            QueryWrapper<Catalog> queryWrapper = new QueryWrapper<Catalog>()
                    .eq("NATIONAL_FORMULA_CODE", nationalFormulaCode);
            //修改操作
            if (StringUtils.isNotBlank(idOld)) {
                queryWrapper.notIn("id", idOld);
            }

            //判断机构制剂代码是否存在
            long cc = catalogService.count(queryWrapper);
            if (cc > 0) {
                throw new CustomException("该制剂在医保制剂目录里，请重新填写！");
            }
        }
        QueryWrapper<NotifyApply> wrapper = new QueryWrapper<NotifyApply>()
                .eq("NATIONAL_FORMULA_CODE", nationalFormulaCode)
                .eq("ORG_CODE", orgCode)
                .orderByDesc("CREATETIME");
        //修改操作
        if (StringUtils.isNotBlank(idOld)) {
            wrapper.notIn("id", idOld);
        }
        //判断上一次制剂代码的价格
        List<NotifyApply> list = notifyApplyService.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<NotifyApply> endList = list.stream()
                    .filter(x -> x.getStatus().equals("4"))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(endList)) {
                NotifyApply apply = endList.get(0);
                notifyApply.setLastApplyPrice(apply.getPrice());

                Date generalAcceptLetterTime = apply.getGeneralAcceptLetterTime();
                long betweenDay = DateUtil.betweenDay(new Date(), generalAcceptLetterTime, true);
                if (betweenDay < 180) {
                    //该制剂已申请成功，下次可申请时间：XX年-XX月-XX日
                    DateTime nwTime = DateUtil.offsetDay(generalAcceptLetterTime, 180);
                    int year = nwTime.getField(DateField.YEAR);
                    int month = nwTime.getField(DateField.MONTH) + 1;
                    int day = nwTime.getField(DateField.DAY_OF_MONTH);
                    throw new CustomException("该制剂已申请成功，下次可申请时间：" + year + "年" + month + "月" + day + "日");
                }
            }
            List<NotifyApply> applyList = list.stream()
                    .filter(x -> !x.getStatus().equals("4"))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(applyList)) {
                throw new CustomException("该制剂申请中不可重复申请");
            }
        }
    }


    private void saveItemList(NotifyApply notifyApply) {
        String id = notifyApply.getId();
        PriceCalculate priceCalculate = notifyApply.getPriceCalculate();
        List<Labor> laborList = notifyApply.getLaborList();
        List<MaterialConsume> materialConsumeList = notifyApply.getMaterialConsumeList();
        List<FixedAssetsDepre> fixedAssetsDepreList = notifyApply.getFixedAssetsDepreList();
        List<CheckFee> checkFeeList = notifyApply.getCheckFeeList();
        ManageLossOtherFee manageLossOtherFee = notifyApply.getManageLossOtherFee();

        priceCalculate.setNotifyApplyId(id);
        boolean save = priceCalculateService.save(priceCalculate);
        log.info("=== 保存测算表信息 ：{} ", save);
        String priceCalculateId = priceCalculate.getId();

        for (Labor labor : laborList) {
            labor.setPriceCalculateId(priceCalculateId);
            laborService.save(labor);
        }
//        laborService.saveBatch(laborList);
        log.info("=== 保存劳务支出数量 ：{} ", laborList.size());

        for (MaterialConsume materialConsume : materialConsumeList) {
            materialConsume.setPriceCalculateId(priceCalculateId);
            materialConsumeService.save(materialConsume);
        }
//        materialConsumeService.saveBatch(materialConsumeList);
        log.info("=== 保存 材料消耗指出 数量 ：{} ", materialConsumeList.size());

        for (FixedAssetsDepre VO : fixedAssetsDepreList) {
            VO.setPriceCalculateId(priceCalculateId);
            fixedAssetsDepreService.save(VO);
        }
//        fixedAssetsDepreService.saveBatch(fixedAssetsDepreList);
        log.info("=== 保存 固定资产折旧 数量 ：{} ", fixedAssetsDepreList.size());

        for (CheckFee checkFee : checkFeeList) {
            checkFee.setPriceCalculateId(priceCalculateId);
            checkFeeService.save(checkFee);
        }
//        checkFeeService.saveBatch(checkFeeList);
        log.info("=== 保存 检验费 数量 ：{} ", checkFeeList.size());

        manageLossOtherFee.setPriceCalculateId(priceCalculateId);
        boolean b = manageLossOtherFeeService.save(manageLossOtherFee);

        log.info("=== 保存 管理费损耗费 结果 ：{} ", b);

    }

    @Value("${fastDfs_url}")
    private String upload_url;


    /**
     * 功能描述: <br>
     * 〈生产环境〉
     *
     * @param notifyApply
     * @Return: void
     * @Author: yc
     * @Date: 2024/5/30 17:11
     */
    private void createPDFPRO(NotifyApply notifyApply) {
        SysUser sysUser = sysUserService.getUser();

        String id = notifyApply.getId();

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());

            log.info(" ==== rootFile :{} ", rootFile.getPath());

            File templateFile = new File(rootFile, "/templates/医疗机构制剂定价测算表.docx");

            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;

            log.info("==== docxPath :{} ", docxPath);
            log.info("==== pdfPath :{} ", pdfPath);

            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(notifyApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("laborList", new LoopRowTableRenderPolicy())
                    .bind("materialConsumeList", policy)
                    .bind("fixedAssetsDepreList", policy)
                    .bind("checkFeeList", policy)
                    .build();

            DocUtil.word2RedDocument(templateFile.getPath(), config, data, docxPath, pdfPath);
            Console.log(data);
            log.info("==== docxData ：{} ", JSON.toJSONString(data));
            //上传文件服务器
            File pdfFile = new File(pdfPath);
            MultipartFile multipartFile = null;
            FastDfsParams params = null;
            //删除文件
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>()
                    .eq("bizType", "27").eq("bizId", id));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("notifyApply", multipartFile, "27", id);
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            log.info("==== pdf ：{} ", fileInfo.getFileUrl());
            //上传

            String contractId = signService.uploadContract_zjqs(fileInfo.getFileUrl(), "");
            if (StrUtil.isEmpty(contractId)) {
                throw new CustomException("申请上传失败!");
            }
            notifyApply.setContractId(contractId);

            //机构签章
            if ("7".equals(sysUser.getUser_type()) || "8".equals(sysUser.getUser_type())) {
                notifyApply.setPdfPath(fileInfo.getFileUrl());
                notifyApply.setDownPdfPath(fileInfo.getFileUrl());
            } else {
                NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery()
                        .eq(NetTagRegister::getUser_id, sysUser.getOrg_code())
                        .eq(NetTagRegister::getIs_del, 0));
                JSONObject object = signService.autoSignZjqs(register.getCompany_customer_id()
                        , contractId, "", "0"
                        , notifyApply.getOrgName(), "0", "2", "2");
                String result = (String) object.get("result");
                if (result.equals("success")) {
                    notifyApply.setPdfPath(object.getString("viewpdf_url"));
                    notifyApply.setDownPdfPath(object.getString("download_url"));
                } else {
                    String msg = (String) object.get("msg");
                    throw new CustomException(msg);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: <br>
     * 〈测试环境〉
     *
     * @param notifyApply
     * @Return: void
     * @Author: yc
     * @Date: 2024/5/30 17:11
     */
    private void createPDFUAT(NotifyApply notifyApply) {
        SysUser sysUser = sysUserService.getUser();

        String id = notifyApply.getId();

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());

            log.info(" ==== rootFile :{} ", rootFile.getPath());

            ClassPathResource classPathResource = new ClassPathResource("/templates/医疗机构制剂定价测算表.docx");
            InputStream inputStream = classPathResource.getInputStream();


            String docxPath = "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = "/TEMP/" + pdfFileName;
            log.info("==== docxPath :{} ", docxPath);
            log.info("==== pdfPath :{} ", pdfPath);

            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(notifyApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("laborList", new LoopRowTableRenderPolicy())
                    .bind("materialConsumeList", policy)
                    .bind("fixedAssetsDepreList", policy)
                    .bind("checkFeeList", policy)
                    .build();

            DocUtil.word2RedDocumentNw(inputStream, config, data, docxPath, pdfPath);
            Console.log(data);
            log.info("==== docxData ：{} ", JSON.toJSONString(data));
            //上传文件服务器
            File pdfFile = new File(pdfPath);
            MultipartFile multipartFile = null;
            FastDfsParams params = null;
            //删除文件
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>()
                    .eq("bizType", "27").eq("bizId", id));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("notifyApply", multipartFile, "27", id);
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            log.info("==== pdf ：{} ", fileInfo.getFileUrl());
            //临时代码，用于展示pdf
            notifyApply.setPdfPath("https://textapi.fadada.com/api2//viewdocs.action?app_id=502087&timestamp=20230403115137&v=2.0&msg_digest=OEU3QjAxNDkyN0RCMkE0RDEzQjgyNjE4RDUwRTk2RDZEMDREMzQ4NA==&transaction_id=5a1fe50b34f7440c8e5ec911d268c022&send_app_id=null");
            notifyApply.setDownPdfPath(upload_url + fileInfo.getFileUrl());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void download(HttpServletResponse response) {

        try {

            //医疗机构制剂价格告知报告
            ClassLoader classLoader = ResourceReader.class.getClassLoader();
            int BUFFER_SIZE = 4096;
            InputStream inputStream = classLoader.getResourceAsStream("templates/医疗机构制剂价格告知报告.docx");
            String filename = "医疗机构制剂价格告知报告.docx";

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小

            response.setContentType("application/octet-stream");

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            int len = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    len += bytesRead;
                    outputStream.write(buffer, 0, bytesRead);
                }
                response.addHeader("Content-Length", "" + len);
            } finally {
                inputStream.close();
                outputStream.close();
            }
//            outputStream.flush();
        } catch (IOException ex) {
            log.error("异常", ex);
        }
    }

    public NotifyApply detailInfo(String id) {

        NotifyApply notifyApply = getById(id);

        PriceCalculate priceCalculate = priceCalculateService.getOne(new QueryWrapper<PriceCalculate>().eq("NOTIFY_APPLY_ID", id));
        String priceCalculateId = priceCalculate.getId();
        notifyApply.setPriceCalculate(priceCalculate);

        List<Labor> laborList = laborService
                .list(new QueryWrapper<Labor>().eq("PRICE_CALCULATE_ID", priceCalculateId));
        laborList = laborList.stream()
                .sorted(Comparator.comparing(Labor::getIsSubtotal, Comparator.nullsFirst(String::compareTo)))
                .collect(Collectors.toList());
        notifyApply.setLaborList(laborList);

        //IS_SUBTOTAL
        //是否是小计数据：0-否，1-是
        List<MaterialConsume> materialConsumeList = materialConsumeService.list(new QueryWrapper<MaterialConsume>().eq("PRICE_CALCULATE_ID", priceCalculateId));
        materialConsumeList = materialConsumeList.stream()
                .sorted(Comparator.comparing(MaterialConsume::getIsSubtotal, Comparator.nullsFirst(String::compareTo)))
                .collect(Collectors.toList());
        notifyApply.setMaterialConsumeList(materialConsumeList);

        List<FixedAssetsDepre> fixedAssetsDepreList = fixedAssetsDepreService.list(new QueryWrapper<FixedAssetsDepre>().eq("PRICE_CALCULATE_ID", priceCalculateId));
        fixedAssetsDepreList = fixedAssetsDepreList.stream()
                .sorted(Comparator.comparing(FixedAssetsDepre::getIsSubtotal, Comparator.nullsFirst(String::compareTo)))
                .collect(Collectors.toList());
        notifyApply.setFixedAssetsDepreList(fixedAssetsDepreList);

        List<CheckFee> checkFeeList = checkFeeService.list(new QueryWrapper<CheckFee>().eq("PRICE_CALCULATE_ID", priceCalculateId));
        checkFeeList = checkFeeList.stream()
                .sorted(Comparator.comparing(CheckFee::getIsSubtotal, Comparator.nullsFirst(String::compareTo)))
                .collect(Collectors.toList());
        notifyApply.setCheckFeeList(checkFeeList);

        ManageLossOtherFee lossOtherFee = manageLossOtherFeeService.getOne(new QueryWrapper<ManageLossOtherFee>().eq("PRICE_CALCULATE_ID", priceCalculateId));
        notifyApply.setManageLossOtherFee(lossOtherFee);

        List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>()
                .eq("BIZID", id)
                .eq("BIZTYPE", "25")
        );
        notifyApply.setFileInfoList(fileInfos);


        return notifyApply;
    }

    /**
     * 制剂告知审核接口
     * Author wb
     * Date 2023/2/10 14:47
     */
    public void audit(NotifyApply apply) {
        SysUser sysUser = sysUserService.getUser();
        NotifyApply notifyApply = notifyApplyMapper.selectById(apply.getId());
        FixmedinsB fixmedinsB = new FixmedinsB();
        QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixmedins_code", notifyApply.getOrgCode());
        fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper);

        if (!"1".equals(sysUser.getUser_type())) {
            throw new CustomException("您无审核权限!");
        } else if ("1".equals(sysUser.getUser_type()) && !fixmedinsB.getFix_blng_admdvs_sbApply().equals(sysUser.getOrg_code())) {
            throw new CustomException("您无审核权限!");
        }
        if ("0".equals(notifyApply.getStatus()) && StpUtil.hasRole("prepare_first") && !"5".equals(apply.getStatus())) {
            notifyApply.setFirstCheckUser(sysUser.getName());
            notifyApply.setFirstCheckTime(new Date());
            notifyApply.setStatus("1");
        } else if ("1".equals(notifyApply.getStatus()) && StpUtil.hasRole("prepare_recheck") && !"5".equals(apply.getStatus())) {
            notifyApply.setSecondCheckUser(sysUser.getName());
            notifyApply.setSecondCheckTime(new Date());
            notifyApply.setStatus("2");
        } else if ("2".equals(notifyApply.getStatus()) && StpUtil.hasRole("prepare_final") && !"5".equals(apply.getStatus())) {
            notifyApply.setStatus("3");
            notifyApply.setFinishCheckUser(sysUser.getName());
            notifyApply.setFinishCheckTime(new Date());

        } else if ("0".equals(notifyApply.getStatus()) && StpUtil.hasRole("prepare_first") && "5".equals(apply.getStatus())) {
            notifyApply.setRejectUser(sysUser.getName());
            notifyApply.setRejectTime(new Date());
            notifyApply.setStatus("5");
            notifyApply.setRejectReason(apply.getRejectReason());
        } else if ("1".equals(notifyApply.getStatus()) && StpUtil.hasRole("prepare_recheck") && "5".equals(apply.getStatus())) {
            notifyApply.setRejectUser(sysUser.getName());
            notifyApply.setRejectTime(new Date());
            notifyApply.setStatus("5");
            notifyApply.setRejectReason(apply.getRejectReason());
        } else if ("2".equals(notifyApply.getStatus()) && StpUtil.hasRole("prepare_final") && "5".equals(apply.getStatus())) {
            notifyApply.setRejectUser(sysUser.getName());
            notifyApply.setRejectTime(new Date());
            notifyApply.setStatus("5");
            notifyApply.setRejectReason(apply.getRejectReason());

        } else if ("3".equals(notifyApply.getStatus()) && StpUtil.hasRole("prepare_final") && "5".equals(apply.getStatus())) {
            notifyApply.setRejectUser(sysUser.getName());
            notifyApply.setRejectTime(new Date());
            notifyApply.setStatus("5");
            notifyApply.setRejectReason(apply.getRejectReason());

        }
//        else if (null!=apply.getStatus() && apply.getStatus().equals("5")) {
//            notifyApply.setRejectUser(sysUser.getName());
//            notifyApply.setRejectTime(new Date());
//            notifyApply.setStatus("5");
//            notifyApply.setRejectReason(apply.getRejectReason());
//        }
        notifyApplyMapper.updateById(notifyApply);
    }

    /**
     * 制剂告知列表接口
     * Author wb
     * Date 2023/2/7 14:03
     */
    public Page<NotifyApply> notifyApplyExportList(NotifyApply notifyApply) {
        SysUser sysUser = sysUserService.getUser();
        Page<NotifyApply> notifyApplyPage = new Page<>();
        List<NotifyApply> list = notifyApplyMapper.getNotifyApplyExportList(notifyApply, sysUser);
        for (NotifyApply s : list) {
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
            if ("0".equals(s.getIsInCategory())) {
                s.setIsInCategoryDesc("否");
            } else if ("1".equals(s.getIsInCategory())) {
                s.setIsInCategoryDesc("是");
            }
            //经营性质
            if ("1".equals(s.getBiznet())) {
                s.setBiznet("营利性");
            } else if ("2".equals(s.getBiznet())) {
                s.setBiznet("民办非营利");
            } else if ("3".equals(s.getBiznet())) {
                s.setBiznet("政府非营利");
            }

        }
        notifyApplyPage.setRecords(list);

        if (CollectionUtil.isNotEmpty(notifyApplyPage.getRecords())) {
            for (NotifyApply s : notifyApplyPage.getRecords()) {
                //查询统筹区
                HashMap<String, String> tcqMap = new HashMap();
                List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
                tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
                s.setFixBlngAdmdvsName(tcqMap.get(s.getFixBlngAdmdvs()));
                s.setIsAudit("false");
                //判断是否有审核权限。前端展示用
                if ("1".equals(sysUser.getUser_type()) && sysUser.getOrg_code().equals(s.getFixBlngAdmdvs())) {
                    s.setIsAudit("true");
                }
                //协议等级
                FixmedinsB fixmedinsB = new FixmedinsB();

                QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("fixmedins_code", s.getOrgCode());
                fixmedinsB = fixmedinsBMapper.selectOne(queryWrapper);
                if (fixmedinsB != null) {
                    s.setAggrementLv(aggrement_lv().get(fixmedinsB.getAggrement_lv()));
                }
            }
        }


        return notifyApplyPage;
    }

    @Transactional
    public boolean updateVO(NotifyApply notifyApply) {

        String id = notifyApply.getId();

        //删除关联信息
        //测算
        PriceCalculate priceCalculate = priceCalculateService.getOne(new QueryWrapper<PriceCalculate>().eq("NOTIFY_APPLY_ID", id));
        priceCalculateService.removeById(priceCalculate.getId());
        //劳务支出
        laborService.remove(new QueryWrapper<Labor>().eq("PRICE_CALCULATE_ID", priceCalculate.getId()));
        //材料消耗
        materialConsumeService.remove(new QueryWrapper<MaterialConsume>().eq("PRICE_CALCULATE_ID", priceCalculate.getId()));
        //固定资产
        fixedAssetsDepreService.remove(new QueryWrapper<FixedAssetsDepre>().eq("PRICE_CALCULATE_ID", priceCalculate.getId()));
        //检验费
        checkFeeService.remove(new QueryWrapper<CheckFee>().eq("PRICE_CALCULATE_ID", priceCalculate.getId()));
        //管理费
        manageLossOtherFeeService.remove(new QueryWrapper<ManageLossOtherFee>().eq("PRICE_CALCULATE_ID", priceCalculate.getId()));

        List<FileInfo> fileInfoList = notifyApply.getFileInfoList();
        List<String> collect = fileInfoList.stream()
                .map(FileInfo::getId)
                .collect(Collectors.toList());
        //文件
        fileInfoService.remove(new QueryWrapper<FileInfo>()
                .eq("BIZID", id)
                .notIn("id", collect)
        );
        saveVO(notifyApply);

        return true;
    }

    public ResultInfo checkSave(NotifyApply notifyApply) {
        String idOld = notifyApply.getId();

        try {
            checkSaveItem(notifyApply, idOld);
        } catch (CustomException e) {
            return ResultInfo.error(e.getMessage());
        }

        return ResultInfo.success();
    }
}

