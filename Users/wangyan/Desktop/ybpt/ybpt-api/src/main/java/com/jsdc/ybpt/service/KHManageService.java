package com.jsdc.ybpt.service;

import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.assessment.KHAssessmentContent;
import com.jsdc.ybpt.assessment.KHLog;
import com.jsdc.ybpt.assessment.KHManage;
import com.jsdc.ybpt.assessment.KhAssessmentDetail;
import com.jsdc.ybpt.mapper.*;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.AssessmentVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class KHManageService {
    @Autowired
    KHManageMapper khManageMapper;
    @Autowired
    KHLogMapper khLogMapper;
    @Autowired
    KHLogService khLogService;
    @Autowired
    KhAssessmentDetailMapper khAssessmentDetailMapper;
    @Autowired
    KHAssessmentContentMapper khAssessmentContentMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private SysUserService sysUserService;

    public Page<AssessmentVo> getPage(AssessmentVo vo) {
        Page page = new Page(vo.getPageNo(), vo.getPageSize());
        SysUser sysUser = sysUserService.getUser();
        if ("1".equals(sysUser.getUser_type())) {//行政管理
            vo.setAdmdvs(sysUser.getOrg_code());
        }
        if ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())) {
            vo.setFixmedins_code(sysUser.getOrg_code());
        }

        Page<AssessmentVo> khManagePage = khManageMapper.getList(page, vo);
        return khManagePage;
    }

    public List<AssessmentVo> getList(AssessmentVo vo) {
        Page<AssessmentVo> khManagePage = khManageMapper.getList(new Page<>(1, 100000), vo);
        return khManagePage.getRecords();
    }

    public Map getDetails(AssessmentVo vo) {
        Map map = new HashMap();
        KHManage khManage = khManageMapper.selectById(vo.getId());
        if (khManage != null) {
            if ("1".equals(khManage.getOrg_type())) {
                khManage.setOrg_type("医疗机构");
                if (StringUtils.isNotEmpty(khManage.getCategory())) {
                    if ("1".equals(khManage.getCategory())) {
                        khManage.setCategory_name("门诊");
                    } else if ("2".equals(khManage.getCategory())) {
                        khManage.setCategory_name("住院");
                    }
                }
                if ("1".equals(khManage.getAggrement_lv())) {
                    khManage.setAggrement_lv("一级");
                } else if ("2".equals(khManage.getAggrement_lv())) {
                    khManage.setAggrement_lv("二级");
                } else if ("3".equals(khManage.getAggrement_lv())) {
                    khManage.setAggrement_lv("三级");
                } else if ("9".equals(khManage.getAggrement_lv())) {
                    khManage.setAggrement_lv("未定级");
                }
            } else if ("2".equals(khManage.getOrg_type())) {
                khManage.setOrg_type("零售药店");
                if ("4".equals(khManage.getAggrement_lv())) {
                    khManage.setAggrement_lv("A级");
                } else if ("5".equals(khManage.getAggrement_lv())) {
                    khManage.setAggrement_lv("B级");
                } else if ("6".equals(khManage.getAggrement_lv())) {
                    khManage.setAggrement_lv("C级");
                }
            }
        }
        map.put("khManage", khManage);
        List<KHLog> logs = khLogMapper.selectList(Wrappers.<KHLog>lambdaQuery().eq(KHLog::getKh_manage_id, vo.getId()).orderByDesc(KHLog::getSubmit_time));
        map.put("logs", logs);
        List<KhAssessmentDetail> assessmentDetails = khAssessmentDetailMapper.getList(vo);
        for (int i = 0; i < assessmentDetails.size(); i++) {
            KhAssessmentDetail detail = assessmentDetails.get(i);
            List<FileInfo> fileInfos = new ArrayList<>();
            if (StringUtils.isNotEmpty(detail.getAssessment_content_id())) {
                fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
                        .eq(FileInfo::getBizType, "2")
                        .eq(FileInfo::getBizId, detail.getAssessment_content_id())
                );
            }
            List fileList = new ArrayList<>();
            for (FileInfo fileInfo : fileInfos) {
                Map fileMap = new HashMap();
                fileMap.put("name", fileInfo.getFileName());
                fileMap.put("url", fileInfo.getFileUrl());
                fileMap.put("id", fileInfo.getId());
                fileList.add(fileMap);
            }
            detail.setFiles(fileList);
        }
        map.put("assessmentDetails", assessmentDetails);
        return map;
    }

    public ResultInfo edit(AssessmentVo bean) {
        if (StringUtils.isNotEmpty(bean.getId())) {
            return ResultInfo.error("请尝试刷新页面!");
        }

        SysUser sysUser = sysUserService.getUser();
        bean.setUpdateUser(sysUser.getId());
        bean.setUpdateTime(new Date());

        //审核
        if (StringUtils.isNotEmpty(bean.getVerify_ids())) {
            String[] ids = bean.getVerify_ids().split(",");
            for (int i = 0; i < ids.length; i++) {
                bean.setId(ids[i]);
                khLogService.add(bean.getId(), bean.getLog_title(), bean.getApproval_opinion());
                if (!bean.updateById()) {
                    ResultInfo.error("审核失败，请检查数据后刷新页面重新审核！");
                }
            }
            return ResultInfo.success("审核成功！");
        } else {
            khLogService.add(bean.getId(), bean.getLog_title(), bean.getApproval_opinion());
//            if (CollectionUtils.isNotEmpty(bean.getAssessmentContents())) {
//                khAssessmentContentMapper.delete(Wrappers.<KHAssessmentContent>lambdaQuery().eq(KHAssessmentContent::getManage_id, bean.getId()));
//                for (KHAssessmentContent ac : bean.getAssessmentContents()) {
//                    if(CollectionUtils.isNotEmpty(ac.getFiles())){
//                        for (MultipartFile file:ac.getFiles()) {
//                            if(file == null){
//                                continue;
//                            }
//                            FastDfsParams params = new FastDfsParams("assessment",file,"2",bean.getId());
//                            return fastDfsUtil.uploadFile(params);
//                        }
//                    }
//                    ac.insert();
//                }
//            }
            return ResultInfo.success(bean.updateById());
        }
    }


    /**
     * 提交审核
     * Author wzn
     * Date 2022/11/25 10:26
     */
    public void submitForReview(String id) {
        KHManage khManage = khManageMapper.selectById(id);
        khManage.setStatus("1");
        khManageMapper.updateById(khManage);
        //日志
        try {
            khLogService.add(id, khManage.getFixmedins_name() + "提交审核", "");
        } catch (Exception e) {

        }
    }

    /**
     * 提交审核
     * Author wy
     * Date 2022/11/25 10:26
     */
    public void submitReview(String id) {
        KHManage khManage = khManageMapper.selectById(id);
        khManage.setStatus("1");
        khManageMapper.updateById(khManage);
    }

    /**
     * 提交审核通过
     * Author wy
     * Date 2022/11/28 14:26
     */
    public void submitSuccess(String id) {
        KHManage khManage = khManageMapper.selectById(id);
        khManage.setStatus("2");
        khManageMapper.updateById(khManage);
    }

    /**
     * 提交审核驳回
     * Author wy
     * Date 2022/11/28 14:26
     */
    public void submitReject(String id, String approval_opinion) {
        KHManage khManage = khManageMapper.selectById(id);
        khManage.setStatus("0");
        khManage.setApproval_opinion(approval_opinion);
        khManageMapper.updateById(khManage);
    }

    /**
     * 撤回提交
     * Author wzn
     * Date 2022/11/25 10:44
     */
    public void cancleSub(String id) {
        KHManage khManage = khManageMapper.selectById(id);
        khManage.setStatus("0");
        khManageMapper.updateById(khManage);
        //日志
        try {
            khLogService.add(id, khManage.getFixmedins_name() + "撤回提交", "");
        } catch (Exception e) {

        }
    }

    public ResultInfo fill(String id, String log_title, String approval_opinion, String assessmentDetails, String[] fileRemoveIds, List<MultipartFile> files) {
        //考核管理
        KHManage khManage = khManageMapper.selectById(id);
        khManage.setIf_detail("1");
        Integer scoreCount = 0;
        //删除文件
        if (null != fileRemoveIds && fileRemoveIds.length > 0) {
            List<FileInfo> fileInfos = fileInfoMapper.selectBatchIds(Arrays.asList(fileRemoveIds));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
        }
        //新增考核内容
        String[] details = assessmentDetails.split("jsdc");
        Console.error("考核内容:" + details);
        for (String detail : details) {
            String assessment_detail_id = detail.substring(detail.lastIndexOf("assessment_detail_id="), detail.lastIndexOf("&assess_contentl")).replace("assessment_detail_id=", "");
            String assess_contentl = detail.substring(detail.lastIndexOf("assess_contentl="), detail.lastIndexOf("&scorel=")).replace("assess_contentl=", "");
            KhAssessmentDetail assessmentDetail = khAssessmentDetailMapper.selectById(assessment_detail_id);
            KHAssessmentContent content;
            KHAssessmentContent khAssessmentContent = khAssessmentContentMapper.selectOne(Wrappers.<KHAssessmentContent>lambdaQuery()
                    .eq(KHAssessmentContent::getManage_id, khManage.getId())
                    .eq(KHAssessmentContent::getAssess_detail_id, assessmentDetail.getId())
            );
            if (khAssessmentContent != null) {
                content = khAssessmentContent;
            } else {
                content = new KHAssessmentContent();
            }
            content.setManage_id(khManage.getId());
            content.setAssess_detail_id(assessmentDetail.getId());
            if (StringUtils.isNotEmpty(assess_contentl) && !"null".equals(assess_contentl)) {
                content.setAssess_contentl(assess_contentl);
            } else {
                content.setAssess_contentl("");
            }
            content.insertOrUpdate();
        }
        //文件上传
        if (files != null && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
                if (multipartFile == null) {
                    continue;
                }
                //考核项ID
                String originalFilename = multipartFile.getOriginalFilename();
                String assessment_detail_id = originalFilename.substring(0, originalFilename.indexOf("&"));
                KHAssessmentContent khAssessmentContent = khAssessmentContentMapper.selectOne(Wrappers.<KHAssessmentContent>lambdaQuery()
                        .eq(KHAssessmentContent::getManage_id, khManage.getId())
                        .eq(KHAssessmentContent::getAssess_detail_id, assessment_detail_id)
                );
                String fileName = originalFilename.replaceAll(assessment_detail_id + "&", "");

                //上传文件服务器
                FastDfsParams params = new FastDfsParams("assessment", multipartFile, "2", khAssessmentContent.getId());
                params.setFileName(fileName);
                ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
                if (resultInfo.getCode() != 0) {
                    return resultInfo;
                }
            }
        }
        //重新计算分数
        for (String detail : details) {
            Console.error("重新计算分数:" + detail);
            String assessment_detail_id = detail.substring(detail.lastIndexOf("assessment_detail_id="), detail.lastIndexOf("&assess_contentl")).replace("assessment_detail_id=", "");
            String assess_contentl = detail.substring(detail.lastIndexOf("assess_contentl="), detail.lastIndexOf("&scorel=")).replace("assess_contentl=", "");
            String scorel = detail.substring(detail.lastIndexOf("scorel=")).replace("scorel=", "");
            KhAssessmentDetail assessmentDetail = khAssessmentDetailMapper.selectById(assessment_detail_id);
            KHAssessmentContent content = khAssessmentContentMapper.selectOne(Wrappers.<KHAssessmentContent>lambdaQuery()
                    .eq(KHAssessmentContent::getManage_id, khManage.getId())
                    .eq(KHAssessmentContent::getAssess_detail_id, assessmentDetail.getId())
            );
            String score = "0";
            Long count = fileInfoMapper.selectCount(Wrappers.<FileInfo>lambdaQuery().eq(FileInfo::getBizType, "2").eq(FileInfo::getBizId, content.getId()));
            if (StringUtils.isNotEmpty(assess_contentl) && !"null".equals(assess_contentl)
                    && ("1").equals(assessmentDetail.getIs_text())
                    && count > 0
                    && ("1").equals(assessmentDetail.getIs_file())) {
                score = assessmentDetail.getFull_score();
            } else if (StringUtils.isNotEmpty(assess_contentl) && !"null".equals(assess_contentl)
                    && ("1").equals(assessmentDetail.getIs_text())
                    && !("1").equals(assessmentDetail.getIs_file())) {
                score = assessmentDetail.getFull_score();
            } else if (count > 0
                    && ("1").equals(assessmentDetail.getIs_file())
                    && !("1").equals(assessmentDetail.getIs_text())) {
                score = assessmentDetail.getFull_score();
            }
            content.setManage_id(khManage.getId());
            content.setAssess_detail_id(assessmentDetail.getId());
            if (StringUtils.isNotEmpty(scorel) && !"null".equals(scorel)) {
                content.setScorel(scorel);
                score = scorel;
            } else {
                content.setScorel(score);
            }
            Console.error("机构考核内容kh_assessment_content:" + assessmentDetail);
            Console.error("抽查分数:" + scorel);
            Console.error("计算分数:" + score);
            scoreCount += Integer.valueOf(score);
            content.insertOrUpdate();
        }
        //日志
        khLogService.add(id, sysUserService.getUser().getOrg_name() + log_title, approval_opinion);
        //更新考核管理
        khManage.setScore(String.valueOf(scoreCount));
        khManage.updateById();
        return ResultInfo.success();
    }

    //批量审核
    public ResultInfo batchAudit(AssessmentVo vo) {
        SysUser sysUser = sysUserService.getUser();
        if ("1".equals(sysUser.getUser_type())) {//行政管理
            vo.setAdmdvs(sysUser.getOrg_code());
        }
        if ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())) {
            vo.setFixmedins_code(sysUser.getOrg_code());
        }
        List<KHManage> khManages = khManageMapper.selectList(Wrappers.<KHManage>lambdaQuery()
                .eq(StringUtils.isNotEmpty(vo.getOrg_code()), KHManage::getOrg_type, vo.getOrg_type())
                .eq(KHManage::getAggrement_lv, vo.getAggrement_lv())
                .eq(KHManage::getYear, vo.getYear())
                .eq(KHManage::getStatus, "1")
                .eq(StringUtils.isNotEmpty(vo.getOrg_code()), KHManage::getFixmedins_code, vo.getOrg_code())
                .eq(StringUtils.isNotEmpty(vo.getAdmdvs()), KHManage::getAdmdvs, vo.getAdmdvs())
        );
        if (khManages != null && khManages.size() == 0) {
            return ResultInfo.error("没有符合的审核单");
        }
        for (KHManage khManage : khManages) {
            khLogService.add(khManage.getId(), sysUser.getOrg_name() + "审核通过", "");
            khManage.setStatus("2");
            khManage.updateById();
        }
        return ResultInfo.success("操作成功");
    }

    public void submitExpirationTime(AssessmentVo vo) {
        vo.updateById();
    }
}
