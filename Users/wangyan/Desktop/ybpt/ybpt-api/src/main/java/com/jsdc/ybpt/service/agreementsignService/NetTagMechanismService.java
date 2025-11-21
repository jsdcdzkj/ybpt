package com.jsdc.ybpt.service.agreementsignService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.*;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.*;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.model_query.medicalOrg.MedicalDept;
import com.jsdc.ybpt.service.AdministrativeUnitService;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.MedicalOrgService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.POIMergeDocUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.agreementsignVo.NetTagMechanismVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class NetTagMechanismService extends BaseService<NetTagMechanism> {

    @Autowired
    private AdministrativeUnitService administrativeUnitService;
    @Autowired
    private NetTagMechanismMapper mapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FddService fddService;
    @Autowired
    private SignService signService;
    @Autowired
    private NetTagRegisterService registerService;
    @Autowired
    private MedicalOrgService medicalOrgService;
    @Autowired
    private NetTagAgreementMapper netTagAgreementMapper;
    @Autowired
    private NetTagSuppService netTagSuppService;

    @Autowired
    private NetTagFileMapper netTagFileMapper;

    @Autowired
    private NetTagAgreementStatusMemberMapper netTagAgreementStatusMemberMapper;

    @Autowired
    private NetTagSuppMapper netTagSuppMapper;
    @Autowired
    private FixmedinsBService fixmedinsBService;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;

    @Autowired
    private NetTagLogService logService;

    @Value("${netTagFilePath}")
    private String netTagFilePath;

    @Value("${netTagNginxPath}")
    private String nginxPath;

    @Value("${signPdfPath}")
    private String fastDfs_downurl;

    @Value("${fastDfs_downurl}")
    private String fastDfs_url;


    @Autowired
    private FastDfsUtil fastDfsUtil;

    /**
     * 查询历史记录
     * 1、网签审核(只查询未审核的)
     * 2、历史网签（除去未审核的，全部查出来）
     * 3、0协议
     *
     * @param vo
     * @return
     * @auth zln
     */
    public Page<NetTagMechanismVo> selectPageList(Page page, NetTagMechanismVo vo) {
        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();
        if(StringUtils.equals(sysUser.getUser_type(), "1")){
            if(StringUtils.isEmpty(vo.getAdmdvs())){
                vo.setAdmdvs(orgCode);
            }
        }else{
            FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getIs_del, "0").eq(FixmedinsB::getFixmedins_code, orgCode));
            vo.setAdmdvs(fixmedinsB.getFix_blng_admdvs_sbApply());
        }

        Page<NetTagMechanismVo> pageList = mapper.selectPageList(page, vo);
        for (NetTagMechanismVo member : pageList.getRecords()){
            if (member.getStatus() == 0) {
                member.setStatus_name("待审核");
            } else if (member.getStatus() == 1) {
                member.setStatus_name("已签章");
            } else if (member.getStatus() == 4) {
                member.setStatus_name("已驳回");
            } else if (member.getStatus() == 2) {
                member.setStatus_name("已解约");
            } else if (member.getStatus() == 3) {
                member.setStatus_name("已过期");
            }
            fixmedinsBService.selectByListPage(member);

            QueryWrapper<NetTagMechanism> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_del","0");
            queryWrapper.eq("mechanism_code",member.getMechanism_code());
            String year = DateUtil.thisYear() + "";
            queryWrapper.eq("year",year);
            queryWrapper.eq("type","0");
            queryWrapper.in("status",Arrays.asList(0,1,3));
            long count = mapper.selectCount(queryWrapper);
            if(count > 0) {
                member.setIs_agreement("1");
            }else {
                member.setIs_agreement("0");
            }
        }
        return pageList;
    }

    /**
     * 网签查看
     *
     * @param id
     * @return
     * @auth zln
     */
    public NetTagMechanism details(String id) {
        NetTagMechanism netTagMechanism = mapper.selectById(id);
        if(StringUtils.isEmpty(netTagMechanism.getReview_url())){
            String fileId = netTagMechanism.getPdf_id();
            NetTagFile file = netTagFileMapper.selectById(fileId);
            String path = file.getFilePath();
            //String tempPath = file.getFilePath().substring(9);
            //tempPath = nginxPath+tempPath;
            netTagMechanism.setReview_url(fastDfs_downurl + path+"?download=0");
        }
        return netTagMechanism;
    }

    /**
     * 网签审核
     * status 1.已审批  4.驳回
     *
     * @param bean
     * @return
     * @auth zln
     */
    public ResultInfo updateStatus(NetTagMechanism bean) {
        SysUser sysUser = sysUserService.getUser();
        String result = "";
        NetTagLog log = new NetTagLog(sysUser.getId(), "网签审核", "");
        if (1 == bean.getStatus()) {//签章通过
            NetTagRegister register = null;
            List<NetTagRegister> registers = registerService.list(new QueryWrapper<NetTagRegister>().eq("user_id", sysUser.getOrg_code()).eq("is_del", "0"));
            if(CollectionUtils.isNotEmpty(registers)){
                register = registers.get(0);
            }
            if (null != register) {
                //签企业章
                bean = baseMapper.selectById(bean.getId());
                JSONObject object = signService.adminAuthSign(register.getCompany_customer_id(), bean.getContract_id(), "", "0", "甲      方：", "2", register.getCompany_seal_id());
//                JSONObject object = signService.adminAuthSign(register.getCompany_customer_id(), bean.getContract_id(), "", "0", "单位盖章：", "1", register.getPersonal_seal_id());

                //签个人章
                result = (String) object.get("result");
                if (result.equals("success")) {
                    JSONObject personalSign = signService.adminAuthSign(register.getCompany_customer_id(), bean.getContract_id(), "", "0", "法定代表人（签章）：", "1", register.getPersonal_seal_id());
                    if(StringUtils.equals(personalSign.getString("result"), "success")){
                        String viewUrl = personalSign.getString("viewpdf_url");
                        String downloadUrl = personalSign.getString("download_url");
                        bean.setReview_url(viewUrl);
                        bean.setDownload_url(downloadUrl);
                        bean.setStatus(1);
                        bean.setHas_sign("4");
                        bean.setUpdateTime(new Date());
                        bean.setOperate_user(sysUser.getId());
                        mapper.updateById(bean);
                        log.setContent("审核通过");
                        logService.save(log);
                        return ResultInfo.success();
                    }
                }else {
                    String msg = (String) object.get("msg");
                    return ResultInfo.error(msg);
                }
            }
            log.setContent("审核失败，用户未注册");
            logService.save(log);
            return ResultInfo.error("审核失败，用户未注册");
        } else if (4 == bean.getStatus()) {//驳回
            bean.setOperate_user(sysUser.getId());
            bean.setApproval_time(new Date());
            if (mapper.updateById(bean) > 0) {
                log.setContent("驳回成功");
                logService.save(log);
                return ResultInfo.success();
            } else {
                log.setContent("审核失败，系统错误");
                logService.save(log);
                return ResultInfo.error("驳回失败");
            }
        }
        log.setContent("审核失败，系统错误");
        logService.save(log);
        return ResultInfo.error("审核失败");
    }


    /**
     * 医药机构端
     * 新增申请
     */
    public ResultInfo onSave(NetTagMechanism bean) throws ParseException {

        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();

        //找到对应年份是否存在协议
//        List<NetTagMechanism> netTagMechanismList = mapper.selectList(Wrappers.<NetTagMechanism>lambdaQuery()
//                .eq(NetTagMechanism::getMechanism_code, orgCode)
//                .eq(NetTagMechanism::getYear, new DateTime().toString("yyyy"))
//                .eq(NetTagMechanism::getIs_del, "0")
//                .eq(NetTagMechanism::getType, "0")
//                .in(NetTagMechanism::getStatus, "0,1,3")
//        );
        QueryWrapper<NetTagMechanism> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del","0");
        queryWrapper.eq("mechanism_code",orgCode);
        int year = DateUtil.thisYear();
        queryWrapper.eq("year",year);
        queryWrapper.eq("type","0");
        queryWrapper.in("status",Arrays.asList(0,1,3));
        long count = mapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResultInfo.error("本年度已申请请勿重新申请");
        }

        bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        bean.setIs_del("0");
        bean.setCreateTime(new Date());
        bean.setCreateUser(sysUser.getId());
        //网签状态(0未审核/未确认 1已审核/已确认 2已解约 3已过期 4、驳回)
        bean.setStatus(0);
        bean.setType("0");
        bean.setYear(String.valueOf(year));
        bean.setMechanism_code(orgCode);
        bean.setHas_sign("1");
        String invalidDate = year+"-12-31";
        String startDate = year+"-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        bean.setInvalid_date(sdf.parse(invalidDate));
        bean.setStartDate(sdf.parse(startDate));
        String net_grade_id = "1";
        //user_type 账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行
        if (sysUser.getUser_type().equals("2")) {
            bean.setMedical_type("1");
            FixmedinsB fixmedinsB = fixmedinsBService.selectByYSYD(orgCode, "1");
            String admdvs = fixmedinsB.getFix_blng_admdvs_sbApply();
            if(StringUtils.isEmpty(admdvs)){
                return ResultInfo.error("请联系管理员维护告知手续统筹区！");
            }
            bean.setAdmdvs(admdvs);
            net_grade_id = fixmedinsB.getAggrement_lv();
        } else if (sysUser.getUser_type().equals("3")) {
            bean.setMedical_type("2");
            FixmedinsB fixmedinsB = fixmedinsBService.selectByYSYD(orgCode, "2");
            String admdvs = fixmedinsB.getFix_blng_admdvs_sbApply();

            if(StringUtils.isEmpty(admdvs)){
                return ResultInfo.error("请联系管理员维护告知手续统筹区！");
            }
            bean.setAdmdvs(admdvs);
            net_grade_id = fixmedinsB.getAggrement_lv();
        }

        //找到对应年份 对应等级 的协议模板
        List<NetTagAgreement> list = netTagAgreementMapper.selectList(Wrappers.<NetTagAgreement>lambdaQuery()
                .eq(NetTagAgreement::getNet_grade_id, Integer.parseInt(net_grade_id))
                .eq(NetTagAgreement::getYear, new DateTime().toString("yyyy"))
                .eq(NetTagAgreement::getIs_del, "0")
                .eq(NetTagAgreement::getStatus, 0)
        );
        if (CollectionUtils.isNotEmpty(list)) {
            bean.setAgreement_id(list.get(0).getId());
        } else {
            //部分下线协议模板
            List<NetTagAgreement> list2 = netTagAgreementMapper.selectList(Wrappers.<NetTagAgreement>lambdaQuery()
                    .eq(NetTagAgreement::getNet_grade_id, Integer.parseInt(net_grade_id))
                    .eq(NetTagAgreement::getYear, new DateTime().toString("yyyy"))
                    .eq(NetTagAgreement::getIs_del, "0")
                    .eq(NetTagAgreement::getStatus, 2)
            );
            if (CollectionUtils.isNotEmpty(list2)) {
                //部分下线的机构
                List<NetTagAgreementStatusMember> statusMemberList = netTagAgreementStatusMemberMapper.selectList(Wrappers.<NetTagAgreementStatusMember>lambdaQuery()
                        .eq(NetTagAgreementStatusMember::getNet_tag_agreement_id, list2.get(0).getId())
                        .eq(NetTagAgreementStatusMember::getOrg_id, orgCode)
                        .eq(NetTagAgreementStatusMember::getIs_del, "0")
                );
                //若该机构协议模板未下线
                if (CollectionUtils.isEmpty(statusMemberList)) {
                    bean.setAgreement_id(list2.get(0).getId());
                } else {
                    return ResultInfo.error("未找到本年度对应协议模板");
                }
            } else {
                return ResultInfo.error("未找到本年度对应协议模板");
            }

        }
        if (mapper.insert(bean) > 0) {
            /**
             * 保存申请信息成功后进行签章操作
             * 签章操作分为两步：
             * 1. 上传合同
             * 2. 自动盖企业章
             * 3. 手动签个人章
             */
            NetTagFile file = netTagFileMapper.selectById(bean.getPdf_id());
            if (null != file) {
                String contractId = signService.uploadContract(file.getFilePath(), "");
                if (StringUtils.isNotEmpty(contractId)) {
                    bean.setContract_id(contractId);
                    if (updateById(bean)) {
                        NetTagRegister register = registerService.getOne(Wrappers.<NetTagRegister>lambdaQuery().eq(NetTagRegister::getUser_id, sysUser.getOrg_code()).eq(NetTagRegister::getIs_del, 0));
                        JSONObject object2 = signService.authSign(register.getCompany_customer_id(), bean.getContract_id(), "", "0", "乙      方", "2");
                        JSONObject object = signService.authSign(register.getCompany_customer_id(), bean.getContract_id(), "", "0", "单位盖章：", "1");

                        String result = (String) object.get("result");
                        if (result.equals("success")) {
                            String url = signService.manualSign(register.getPersonal_customer_id(), bean.getId(), bean.getContract_id(), "", "2");
                            NetTagLog log = new NetTagLog(sysUser.getId(), "网签申请", "申请成功");
                            logService.save(log);
                            return ResultInfo.success(url);
                        }else {
                            String msg = (String) object.get("msg");
                            throw new RuntimeException(msg);
                        }
//                        String url = signService.manualSign(register.getPersonal_customer_id(), bean.getId(), bean.getContract_id(), "", "0");
//                        return ResultInfo.success(url);
                    }
                }
            }

        }
        throw new RuntimeException("申请失败!");
    }

    /**
     * 医保端
     * 新增补充协议
     */
    public ResultInfo onSaveBcxy(NetTagMechanism bean) {
        SysUser sysUser = sysUserService.getUser();
        String[] status = new String[]{"0", "1", "3"};
        if (StringUtils.isNotEmpty(bean.getMedicalCodeList())) {
            for (String code : bean.getMedicalCodeList()) {

                //找到对应年份是否存在该模板的补充协议
                List<NetTagMechanism> netTagMechanismList = mapper.selectList(Wrappers.<NetTagMechanism>lambdaQuery()
                        .eq(NetTagMechanism::getMechanism_code, code)
                        .eq(NetTagMechanism::getAgreement_id, bean.getAgreement_id())
                        .eq(NetTagMechanism::getType, "1")
                        .eq(NetTagMechanism::getYear, new DateTime().toString("yyyy"))
                        .eq(NetTagMechanism::getIs_del, "0")
                        .in(NetTagMechanism::getStatus, status)
                );
                if (CollectionUtils.isEmpty(netTagMechanismList)) {
                    FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getFixmedins_code, code).eq(FixmedinsB::getIs_del, 0));
                    if(StringUtils.isEmpty(fixmedinsB.getFix_blng_admdvs_sbApply())){
                        return ResultInfo.error("请联系管理员维护告知手续统筹区！");
                    }
                    bean.setAdmdvs(fixmedinsB.getFix_blng_admdvs_sbApply());
                    bean.setId(IdUtil.simpleUUID());
                    bean.setIs_del("0");
                    bean.setCreateTime(new Date());
                    bean.setCreateUser(sysUser.getId());
                    //网签状态(0未审核/未确认 1已审核/已确认 2已解约 3已过期 4、驳回)
                    bean.setStatus(0);
                    bean.setMechanism_code(code);
                    bean.setMedical_code(sysUser.getOrg_code());
                    bean.setYear(DateUtil.thisYear() + "");
                    bean.setType("1");
                    bean.setHas_sign("1");
                    //生成PDF
                    String pdfId = generatePDFId(bean);
                    bean.setPdf_id(pdfId);
                    save(bean);
                    NetTagLog log = new NetTagLog(sysUser.getId(), "新增补充协议", "新增成功");
                    logService.save(log);
                }
            }
        }
        return ResultInfo.success();
    }


    public ResultInfo onSaveBcxy2(NetTagMechanism bean) {
        bean.setAgreement_id("695a82712ae64a638edb8bb2a44f5871");
        List<String> medicalCodeList = new ArrayList<>() ;
        medicalCodeList.add("P32031200500") ;
        medicalCodeList.add("P32031200241") ;
        bean.setMedicalCodeList(medicalCodeList);
        bean.setMedical_type("2");
        bean.setSignDate(DateUtil.parseDate("2023-01-01"));
        bean.setType("1");

        SysUser sysUser = sysUserService.getUser();
        String[] status = new String[]{"0", "1", "3"};
        if (StringUtils.isNotEmpty(bean.getMedicalCodeList())) {
            for (String code : bean.getMedicalCodeList()) {

                //找到对应年份是否存在该模板的补充协议
                List<NetTagMechanism> netTagMechanismList = mapper.selectList(Wrappers.<NetTagMechanism>lambdaQuery()
                        .eq(NetTagMechanism::getMechanism_code, code)
                        .eq(NetTagMechanism::getAgreement_id, bean.getAgreement_id())
                        .eq(NetTagMechanism::getType, "1")
                        .eq(NetTagMechanism::getYear, new DateTime().toString("yyyy"))
                        .eq(NetTagMechanism::getIs_del, "0")
                        .in(NetTagMechanism::getStatus, status)
                );
                if (CollectionUtils.isEmpty(netTagMechanismList)) {
                    FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getFixmedins_code, code).eq(FixmedinsB::getIs_del, 0));
                    bean.setAdmdvs(fixmedinsB.getFix_blng_admdvs());
                    bean.setId(IdUtil.simpleUUID());
                    bean.setIs_del("0");
                    bean.setCreateTime(new Date());
                    bean.setCreateUser(sysUser.getId());
                    //网签状态(0未审核/未确认 1已审核/已确认 2已解约 3已过期 4、驳回)
                    bean.setStatus(0);
                    bean.setMechanism_code(code);
                    bean.setMedical_code(sysUser.getOrg_code());
                    bean.setYear(DateUtil.thisYear() + "");
                    bean.setType("1");
                    bean.setHas_sign("1");
                    //生成PDF
                    String pdfId = generatePDFId(bean);
                    bean.setPdf_id(pdfId);
                    save(bean);
                    NetTagLog log = new NetTagLog(sysUser.getId(), "新增补充协议", "新增成功");
                    logService.save(log);
                }
            }
        }
        return ResultInfo.success();
    }

    //生成PDF
    public String generatePDFId(NetTagMechanism bean) {
        SysUser sysUser = sysUserService.getUser();
        String orgCode = bean.getMechanism_code();
        MedicalDept medicalDept = null;
        FixmedinsB fixmedinsB = new FixmedinsB();
        fixmedinsB.setFixmedins_code(orgCode);
        List<FixmedinsB> fixmedinsBS = fixmedinsBMapper.selectList(Wrappers.<FixmedinsB>lambdaQuery()
                .in(FixmedinsB::getFix_blng_admdvs, Arrays.asList("320399", "320382", "320381", "320324", "320322", "320321", "320312", "320305"))
                .eq(FixmedinsB::getFixmedins_code, orgCode)
        );

        if (CollectionUtils.isEmpty(fixmedinsBS)) {
            throw new RuntimeException("未找到该机构等级对应模板");
        }
        FixmedinsB fixmedinsB1 = fixmedinsBS.get(0);

        if (fixmedinsB1 != null) {
            //找到对应年份 对应等级 的协议模板
            List<NetTagSupp> list = netTagSuppMapper.selectList(Wrappers.<NetTagSupp>lambdaQuery()
                    .eq(NetTagSupp::getId, bean.getAgreement_id())
            );
            String pdfId = "";
            if (CollectionUtils.isNotEmpty(list)) {
                NetTagSupp netTagSupp = list.get(0);
                NetTagFile netTagFile = netTagFileMapper.selectById(netTagSupp.getFile_id());
//            String fileId = netTagAgreement.getFile_id();

                Map<String, String> data = new HashMap<>();
                data.put("party_b_name", fixmedinsB1.getFixmedins_name());
                data.put("party_b_address", null == fixmedinsB1?"":fixmedinsB1.getAddress());
                data.put("party_b_code", fixmedinsB1.getFixmedins_code() );
                data.put("year", null == bean.getSignDate() ? "______" : bean.getSignDate().getYear()+1900+"");
                data.put("mouth",null == bean.getSignDate() ? "_____" : bean.getSignDate().getMonth()+1+"");
                data.put("day",null == bean.getSignDate() ? "_____" : bean.getSignDate().getDate()+"");
                try {
                    //判断路径是否存在
                    if (!StringUtils.isEmpty(netTagFilePath)) {
                        File file = new File(netTagFilePath);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                    }
//                    String destDocx = netTagFilePath + netTagFile.getOldFileName();
                    String destDocx = netTagFilePath + netTagFile.getNewFileName();
                    String pdfPath = netTagFilePath + IdUtil.simpleUUID()+".pdf";
                    //从文件服务器拉取文件到本地临时用
                    NetTagSuppService.download(fastDfs_url+netTagFile.getFilePath(),netTagFilePath+"model/"+ netTagFile.getNewFileName()) ;


                    POIMergeDocUtil.word2RedDocument(netTagFilePath+"model/"+ netTagFile.getNewFileName(), data, destDocx, pdfPath);

                    NetTagFile netTagFile2 = new NetTagFile();
                    pdfId = UUID.randomUUID().toString().replaceAll("-", "");

                    //上传PDF
                    MultipartFile pdfFile = NetTagSuppService.convertToMultipartFile(pdfPath);

                    FastDfsParams params2 = new FastDfsParams("netTagFile", pdfFile, "22", pdfId);
                    params2.setFileName(pdfFile.getName());
                    ResultInfo<FileInfo>  resultInfo2 = fastDfsUtil.uploadFile2(params2);

                    netTagFile2.setId(pdfId);
                    //截取文件名后缀改为.pdf
                    netTagFile2.setOldFileName(destDocx.substring(0, destDocx.lastIndexOf(".")) + ".pdf");
                    netTagFile2.setNewFileName(UUID.randomUUID().toString().replaceAll("-", "") + ".pdf");
                    netTagFile2.setFilePath(resultInfo2.getData().getFileUrl());
                    netTagFile2.setAssociationId("");
                    netTagFile2.setCreateTime(new Date());
                    netTagFile2.setUpdateTime(new Date());
                    netTagFile2.setIs_del("0");
                    netTagFileMapper.insert(netTagFile2);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                return pdfId;
            }
        }
        return "";
    }

    /**
     * 医药机构端
     * 网签撤销
     */
    public ResultInfo onRevoke(NetTagMechanism bean) {
        SysUser sysUser = sysUserService.getUser();
        bean.setIs_del("1");
        bean.setUpdateTime(new Date());
        bean.setUpdateUser(sysUser.getId());
        mapper.updateById(bean);
        NetTagLog log = new NetTagLog(sysUser.getId(), "网签撤销", "撤销成功");
        logService.save(log);
        return ResultInfo.success();
    }

    /**
     * 医保端
     * 网签解约
     */
    public ResultInfo updJy(NetTagMechanism bean) {
        SysUser sysUser = sysUserService.getUser();
        //网签状态(0未签章/未确认 1已签章/已确认 2已解约 3已过期 4、驳回 5、撤销)
        bean.setStatus(2);
        bean.setRescind(new Date());
        bean.setUpdateTime(new Date());
        bean.setUpdateUser(sysUser.getId());
        bean.setOperate_user(sysUser.getId());
        if(null != bean.getStatus() &&  5 == bean.getStatus()){
            bean.setRevoke_time(new Date());
        }
        mapper.updateById(bean);
        NetTagLog log = new NetTagLog(sysUser.getId(), "网签解约", "解约成功");
        logService.save(log);
        return ResultInfo.success();
    }



    //生成PDF
    public ResultInfo generatePDF(NetTagMechanism bean) {
        //首先查询此医疗机构的等级
        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();
        MedicalDept medicalDept = null;

        FixmedinsB fixmedinsB = new FixmedinsB();
        fixmedinsB.setFixmedins_code(orgCode);
        List<FixmedinsB> fixmedinsBS = fixmedinsBMapper.selectList(Wrappers.<FixmedinsB>lambdaQuery()
                .in(FixmedinsB::getFix_blng_admdvs, Arrays.asList("320399", "320382", "320381", "320324", "320322", "320321", "320312", "320305"))
                .eq(FixmedinsB::getFixmedins_code, orgCode)
        );
        if (CollectionUtils.isEmpty(fixmedinsBS)) {
            return ResultInfo.error("未找到该机构等级对应模板");
        }
        FixmedinsB fixmedinsB1 = fixmedinsBS.get(0);

//        if (medicalDept != null) {
//            bean.setMedical_code(medicalDept.getFixmedins_name());//医药机构编码、国家编码
//            net_grade_id = medicalDept.getCred_lv();//机构等级
//        }

        if(StringUtils.isEmpty(fixmedinsB1.getAggrement_lv())){
            return ResultInfo.error("当前机构未设置协议等级，请联系医保人员");
        }
        //找到对应年份 对应等级 的协议模板
        List<NetTagAgreement> list = netTagAgreementMapper.selectList(Wrappers.<NetTagAgreement>lambdaQuery()
                .eq(NetTagAgreement::getNet_grade_id, Integer.parseInt(fixmedinsB1.getAggrement_lv()))
                .eq(NetTagAgreement::getYear, new DateTime().toString("yyyy") + "")
                .eq(NetTagAgreement::getIs_del, "0")
                .eq(NetTagAgreement::getStatus, 0)
        );
        String pdfId = "";
        if(CollectionUtils.isEmpty(list)){
            //查是否有部分上线的模版
            List<NetTagAgreement> list2 = netTagAgreementMapper.selectList(Wrappers.<NetTagAgreement>lambdaQuery()
                    .eq(NetTagAgreement::getNet_grade_id, Integer.parseInt(fixmedinsB1.getAggrement_lv()))
                    .eq(NetTagAgreement::getYear, new DateTime().toString("yyyy") + "")
                    .eq(NetTagAgreement::getIs_del, "0").eq(NetTagAgreement::getStatus, 2)) ;

            if(CollectionUtils.isNotEmpty(list2)){
                //有的话 查是否在下线名单里
                QueryWrapper<NetTagAgreementStatusMember> queryWrapper = new QueryWrapper<>() ;
                queryWrapper.eq("net_tag_agreement_id",list2.get(0).getId()) ;
                queryWrapper.eq("org_code",sysUser.getOrg_code()) ;
                queryWrapper.eq("is_del","0") ;
                Long count = netTagAgreementStatusMemberMapper.selectCount(queryWrapper);
                if(count==0){
                    list = list2 ;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            NetTagAgreement netTagAgreement = list.get(0);
            NetTagFile netTagFile = netTagFileMapper.selectById(netTagAgreement.getFile_id());
//            String fileId = netTagAgreement.getFile_id();
            //处理签署日期
            String year = "";
            String mouth = "";
            String day = "";
            if (null != bean && null != bean.getSignDate()){
                year = new DateTime(bean.getSignDate()).toString("yyyy");
                mouth = new DateTime(bean.getSignDate()).toString("MM");
                day = new DateTime(bean.getSignDate()).toString("dd");
            }

            Map<String, String> data = new HashMap<>();
            data.put("party_b_name", fixmedinsB1.getFixmedins_name());
            data.put("legrep_name", fixmedinsB1.getLegrep_name());
            //todo 根据机构信息填充甲方信息

            data.put("party_b_address", null == fixmedinsB1?"":fixmedinsB1.getAddress());
            data.put("party_b_code", fixmedinsB1.getFixmedins_code() );
            data.put("year", null == bean.getSignDate() ? "______" : bean.getSignDate().getYear()+1900+"");
            data.put("mouth",null == bean.getSignDate() ? "_____" : bean.getSignDate().getMonth()+1+"");
            data.put("day",null == bean.getSignDate() ? "_____" : bean.getSignDate().getDate()+"");
            try {
                //判断路径是否存在
                if (!StringUtils.isEmpty(netTagFilePath)) {
                    File file = new File(netTagFilePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
                //String destDocx = netTagFilePath + netTagFile.getOldFileName();
                String destDocx = netTagFilePath + netTagFile.getNewFileName();
                String pdfPath = netTagFilePath + IdUtil.simpleUUID()+".pdf";

                //从文件服务器拉取文件到本地临时用
                NetTagSuppService.download(fastDfs_url+netTagFile.getFilePath(),netTagFilePath+"model/"+ netTagFile.getNewFileName()) ;

                POIMergeDocUtil.word2RedDocument(netTagFilePath+"model/"+ netTagFile.getNewFileName(), data, destDocx, pdfPath);

                NetTagFile netTagFile2 = new NetTagFile();
                pdfId = UUID.randomUUID().toString().replaceAll("-", "");

                //上传PDF
                MultipartFile pdfFile = NetTagSuppService.convertToMultipartFile(pdfPath);
                FastDfsParams params2 = new FastDfsParams("netTagFile", pdfFile, "22", pdfId);
                params2.setFileName(pdfFile.getName());
                ResultInfo<FileInfo>  resultInfo = fastDfsUtil.uploadFile2(params2);

                netTagFile2.setId(pdfId);
                //截取文件名后缀改为.pdf
                netTagFile2.setOldFileName(destDocx.substring(0, destDocx.lastIndexOf(".")) + ".pdf");
                netTagFile2.setNewFileName(UUID.randomUUID().toString().replaceAll("-", "") + ".pdf");
                netTagFile2.setFilePath(resultInfo.getData().getFileUrl());
                netTagFile2.setAssociationId("");
                netTagFile2.setCreateTime(new Date());
                netTagFile2.setUpdateTime(new Date());
                netTagFile2.setIs_del("0");
                netTagFileMapper.insert(netTagFile2);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return ResultInfo.success(pdfId);
        } else {
            return ResultInfo.error("未找到对应的模板");
        }
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
     * 更新机构信息
     * @param fixmedinsB
     * @return
     */
    public ResultInfo orgInfoMaintain(FixmedinsB fixmedinsB){
        String id = fixmedinsB.getId();
        FixmedinsB org = fixmedinsBService.getById(id);
        BeanUtil.copyProperties(fixmedinsB, org, CopyOptions.create().setIgnoreCase(true).ignoreNullValue());
        org.setHasMaintained("1");
        if(fixmedinsBService.updateById(org)){
            SysUser sysUser = sysUserService.getUser();
            NetTagLog log = new NetTagLog(sysUser.getId(), "更新机构信息", "更新成功");
            logService.save(log);
            return ResultInfo.success();
        }else{
            return ResultInfo.error("保存失败");
        }
    }

    public ResultInfo checkMaintained(){
        SysUser user = sysUserService.getUser();
        String orgCode = user.getOrg_code();
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getFixmedins_code, orgCode).eq(FixmedinsB::getIs_del, "0"));
        return ResultInfo.success(StringUtils.isNotEmpty(fixmedinsB.getHasMaintained()) ? fixmedinsB.getHasMaintained() : "0");
    }

    public void getPosition(){
        SysUser user = sysUserService.getUser();
        String orgCode = user.getOrg_code();
        FixmedinsB fixmedinsB = fixmedinsBService.getOne(Wrappers.<FixmedinsB>lambdaQuery().eq(FixmedinsB::getFixmedins_code, orgCode).eq(FixmedinsB::getIs_del, "0"));
        String admdvs = fixmedinsB.getFix_blng_admdvs();
    }

    /**
     * 获取属地下拉选项
     * @return
     */
    public ResultInfo getXzqh(){
        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();
        LambdaQueryWrapper<AdministrativeUnit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdministrativeUnit::getIs_del, "0");
        if(!StringUtils.equals(orgCode, "320399")){
            wrapper.eq(AdministrativeUnit::getAdmdvs, orgCode);
        }
        List<AdministrativeUnit> list = administrativeUnitService.list(wrapper);
        return ResultInfo.success(list);
    }

    public ResultInfo getOrgUnSeal(NetTagMechanismVo vo){

        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();
        Page page = new Page(vo.getPageIndex(), vo.getPageSize());
        if(StringUtils.isEmpty(vo.getAdmdvs())){
            vo.setAdmdvs(orgCode);
        }
        if(StringUtils.isEmpty(vo.getYear())){
            Calendar calendar = Calendar.getInstance();
            // 获取当前年
            int year = calendar.get(Calendar.YEAR);
            vo.setYear(String.valueOf(year));
        }
        Page<FixmedinsB> pageInfo = mapper.getOrgUnSeal(page, vo);
        pageInfo.getRecords().forEach(x -> {
            x.setCred_lv_name(org.apache.commons.lang3.StringUtils.equals(x.getFixmedins_type(), "1")?"医疗机构":"药店");
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(x.getAggrement_lv())){
                x.setAggrement_lv_name(getLevelList().get(x.getAggrement_lv()));
            }
        });
        return ResultInfo.success(pageInfo);
    }

    @SneakyThrows
    public void exportUnSeal(NetTagMechanismVo vo, HttpServletResponse response){
        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();
        if(StringUtils.isEmpty(vo.getAdmdvs())){
            vo.setAdmdvs(orgCode);
        }
        if(StringUtils.isEmpty(vo.getYear())){
            Calendar calendar = Calendar.getInstance();
            // 获取当前年
            int year = calendar.get(Calendar.YEAR);
            vo.setYear(String.valueOf(year));
        }
        List<FixmedinsB> list = mapper.getOrgUnSealList(vo);
        list.forEach(x -> {
            x.setCred_lv_name(org.apache.commons.lang3.StringUtils.equals(x.getFixmedins_type(), "1")?"医疗机构":"药店");
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(x.getAggrement_lv())){
                x.setAggrement_lv_name(getLevelList().get(x.getAggrement_lv()));
            }
        });
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("fixmedins_code", "国家编码");
        writer.addHeaderAlias("medins_mgtcode", "医保编码");
        writer.addHeaderAlias("cred_lv_name", "类型");
        writer.addHeaderAlias("fixmedins_name", "机构名称");
        writer.addHeaderAlias("aggrement_lv_name", "协议等级");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("license", "许可证登记号");
        writer.addHeaderAlias("legrep_name", "法定代表人");
        writer.addHeaderAlias("legrep_person", "联系人");
        writer.addHeaderAlias("legrep_mobile", "联系电话");
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
