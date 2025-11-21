package com.jsdc.ybpt.service.eval;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.eval_.*;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.mapper.eval.EvalOrgDetailMapper;
import com.jsdc.ybpt.mapper.eval.EvalOrgTaskMapper;
import com.jsdc.ybpt.mapper.eval.EvalTaskManageMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.EvalVo;
import com.jsdc.ybpt.vo.ResultInfo;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EvalService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private FastDfsUtil fastDfsUtil;
    @Autowired
    private EvalTaskManageMapper evalTaskManageMapper;
    @Autowired
    private EvalOrgTaskMapper evalOrgTaskMapper;
    @Autowired
    private EvalOrgDetailService evalOrgDetailService;
    @Autowired
    private EvalOrgDetailLogService evalOrgDetailLogService;
    @Autowired
    private EvalOrgDetailMapper evalOrgDetailMapper;
    @Autowired
    private EvalDesignService evalDesignService;
    @Autowired
    private EvalDesignCategoryService evalDesignCategoryService;
    @Autowired
    private EvalCategoryStardardService evalCategoryStardardService;
    @Autowired
    private EvalStardardMethodService evalStardardMethodService;
    @Autowired
    private EvalMethodInfoService evalMethodInfoService;
    @Autowired
    private EvalStardardUserService evalStardardUserService;

    /**
     * 机构填写上传
     *
     * @param evalStardardMethodId 内容办法表ID
     * @param year                 考核年度
     * @param orgCode              机构彼岸吗
     */
    public ResultInfo uploadEvalOrgDetail(MultipartFile file, String evalStardardMethodId, String year, String orgCode) {
        SysUser sysUser = sysUserService.getUser();
        //上传文件服务器
        FastDfsParams params = new FastDfsParams("eval/" + year + "/orgData/" + sysUser.getOrg_code() + "/", file, "15", evalStardardMethodId);
        params.setFileName(file.getOriginalFilename());
        return fastDfsUtil.uploadFile(params);
    }

    /**
     * 机构填写申诉上传
     *
     * @param evalStardardMethodId 内容办法表ID
     * @param year                 考核年度
     * @param orgCode              机构彼岸吗
     */
    public ResultInfo uploadAppealEvalOrgDetail(MultipartFile file, String evalStardardMethodId, String year, String orgCode) {
        SysUser sysUser = sysUserService.getUser();
        //上传文件服务器
        FastDfsParams params = new FastDfsParams("eval/" + year + "/orgData/" + sysUser.getOrg_code() + "/", file, "17", evalStardardMethodId);
        params.setFileName(file.getOriginalFilename());
        return fastDfsUtil.uploadFile(params);
    }

    public ResultInfo removeUploadEvalOrgDetail(String id) {
        //清除文件
        FileInfo fileInfo = fileInfoMapper.selectById(id);
        if (fileInfo != null) {
            fileInfoMapper.deleteById(id);
        }
        return fastDfsUtil.deleteFile(fileInfo);
    }

    /**
     * 绩效考核列表
     *
     * @param evalTaskManage
     * @return
     */
    public Page<EvalTaskManage> evalTaskManagePage(EvalTaskManage evalTaskManage) {
        LambdaQueryWrapper<EvalTaskManage> wrapper = new LambdaQueryWrapper<EvalTaskManage>()
                .like(StringUtils.isNotEmpty(evalTaskManage.getTaskName()), EvalTaskManage::getTaskName, evalTaskManage.getTaskName())
                .eq(StringUtils.isNotEmpty(evalTaskManage.getAdmdvs()), EvalTaskManage::getAdmdvs, evalTaskManage.getAdmdvs())
                .eq(StringUtils.isNotEmpty(evalTaskManage.getOrgType()), EvalTaskManage::getOrgType, evalTaskManage.getOrgType())
                .eq(StringUtils.isNotEmpty(evalTaskManage.getCategory()), EvalTaskManage::getCategory, evalTaskManage.getCategory())
                .eq(StringUtils.isNotEmpty(evalTaskManage.getAggrementLv()), EvalTaskManage::getAggrementLv, evalTaskManage.getAggrementLv())
                .eq(StringUtils.isNotEmpty(evalTaskManage.getYear()), EvalTaskManage::getYear, evalTaskManage.getYear())
                .eq(StringUtils.isNotEmpty(evalTaskManage.getNatures()), EvalTaskManage::getNatures, evalTaskManage.getNatures())
                .eq(StringUtils.isNotEmpty(evalTaskManage.getStatus()), EvalTaskManage::getStatus, evalTaskManage.getStatus())
                .ne(EvalTaskManage::getStatus, "0")
                .eq(EvalTaskManage::getIsDel, "0")
                .orderByDesc(EvalTaskManage::getCreateTime);
        Page<EvalTaskManage> page = new Page<>(evalTaskManage.getPageNo(), evalTaskManage.getPageSize());
        Page<EvalTaskManage> evalOrgTaskPage = evalTaskManageMapper.selectPage(page, wrapper);
        return evalOrgTaskPage;
    }

    /**
     * 绩效考核 详情
     *
     * @param assessmentId 考核单ID
     * @return
     */
    public EvalVo getEvalTaskManage(String taskManageId, String assessmentId) {
        return queryDesign(taskManageId, assessmentId, null);
    }

    /**
     * 考核数据
     *
     * @param vo
     * @return
     */
    public ResultInfo getOrgDetailPage(EvalVo vo) {
        if (StringUtils.isEmpty(vo.getTaskManageId())) {
            return ResultInfo.error("请重新执行考核，考核任务ID未找到！");
        }
        if (StringUtils.isEmpty(vo.getEarnestMoneyId())) {
            return ResultInfo.error("请重新执行考核，考核办法ID未找到！");
        }
        Page<EvalVo> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        Page<EvalVo> evalOrgTaskPage = evalOrgDetailMapper.getOrgDetailPage(page, vo);
        for (int i = 0; i < evalOrgTaskPage.getRecords().size(); i++) {
            EvalVo v = evalOrgTaskPage.getRecords().get(i);
            //附件
            List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery().eq(FileInfo::getBizId, v.getId()).eq(FileInfo::getBizType, "15"));
            if (CollectionUtils.isNotEmpty(fileInfos)) {
                List<Map<String, String>> fileList = getFileListMap(fileInfos);
                v.setFiles(fileList);
            }

            //绑定申诉文件
            List<FileInfo> appealFileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery().eq(FileInfo::getBizId, v.getId()).eq(FileInfo::getBizType, "17"));
            if (CollectionUtils.isNotEmpty(appealFileInfos)) {
                List<Map<String, String>> fileList = getFileListMap(appealFileInfos);
                v.setAppealFiles(fileList);
            }

        }

        return ResultInfo.success(evalOrgTaskPage);
    }

    /**
     * 确认考核
     */
    public ResultInfo setOrgDetail(EvalOrgDetail evalOrgDetail) {
        if (StringUtils.isEmpty(evalOrgDetail.getId())) {
            return ResultInfo.error("请尝试刷新页面，考核数据ID未找到！");
        }
        //添加日志
        setOrgDetailLog(evalOrgDetail);

        return ResultInfo.success(evalOrgDetail.updateById());
    }

    //添加日志
    private void setOrgDetailLog(EvalOrgDetail evalOrgDetail) {
        SysUser sysUser = sysUserService.getUser();
        EvalOrgDetailLog evalOrgDetailLog = new EvalOrgDetailLog();
        evalOrgDetailLog.setId(IdUtil.simpleUUID());
        evalOrgDetailLog.setEvalOrgDetail(evalOrgDetail.getId());
        evalOrgDetailLog.setScore(StringUtils.isNotEmpty(evalOrgDetail.getScore()) ? evalOrgDetail.getScore() : "");
        evalOrgDetailLog.setCreateUser(sysUser.getId());
        evalOrgDetailLog.setCreateUserName(sysUser.getName());
        evalOrgDetailLog.setCreateTime(new Date());
        evalOrgDetailLogService.save(evalOrgDetailLog);
    }

    /**
     * 确认考核日志
     */
    public ResultInfo getOrgDetailLog(String id) {
        if (StringUtils.isEmpty(id)) {
            return ResultInfo.error("请尝试刷新页面，考核数据ID未找到！");
        }
        LambdaQueryWrapper<EvalOrgDetailLog> wrapper = new LambdaQueryWrapper<EvalOrgDetailLog>().eq(EvalOrgDetailLog::getEvalOrgDetail, id).orderByDesc(EvalOrgDetailLog::getCreateTime);
        return ResultInfo.success(evalOrgDetailLogService.list(wrapper));
    }

    /**
     * 绩效考核/考核反馈 保存
     */
    public ResultInfo setEvalOrgDetail(EvalVo vo) {
        if (StringUtils.isNotEmpty(vo.getEvalOrgTaskId())) {
            EvalOrgTask evalOrgTask = new EvalOrgTask();
            evalOrgTask.setId(vo.getEvalOrgTaskId());
            evalOrgTask.setStatus(vo.getStatus());
            evalOrgTask.updateById();
        }
        if (vo.getEvalOrgDetailList() != null) {
            for (int i = 0; i < vo.getEvalOrgDetailList().size(); i++) {
                EvalOrgDetail evalOrgDetail = vo.getEvalOrgDetailList().get(i);
                if (StringUtils.isEmpty(evalOrgDetail.getEvalOrgTaskId())) {
                    return ResultInfo.error("请重新提交，机构考核任务ID未找到！");
                }
                if (StringUtils.isEmpty(evalOrgDetail.getEvalStardardMethodId())) {
                    return ResultInfo.error("请重新提交，内容办法表ID未找到！");
                }
                EvalOrgDetail detailServiceOne = evalOrgDetailService.getOne(Wrappers.<EvalOrgDetail>lambdaQuery().eq(EvalOrgDetail::getEvalOrgTaskId, evalOrgDetail.getEvalOrgTaskId()).eq(EvalOrgDetail::getEvalStardardMethodId, evalOrgDetail.getEvalStardardMethodId()));
                if (StringUtils.isNotEmpty(evalOrgDetail.getId()) || (detailServiceOne != null && StringUtils.isNotEmpty(detailServiceOne.getId()))) {
                    if (StringUtils.isEmpty(evalOrgDetail.getId())) {
                        evalOrgDetail.setId(detailServiceOne.getId());
                    }
                    evalOrgDetail.updateById();
                } else {
                    String id = IdUtil.simpleUUID();
                    evalOrgDetail.setId(id);
                    evalOrgDetail.insert();
                }

                EvalStardardMethod evalStardardMethod = evalStardardMethodService.getById(detailServiceOne.getEvalStardardMethodId());
                SysUser user = sysUserService.getUser();
                if ("1".equals(user.getUser_type())
                        || (
                        ("2".equals(user.getUser_type()) || "3".equals(user.getUser_type()))
                                && "1".equals(evalStardardMethod.getAutoScore())
                                && "1".equals(evalOrgDetail.getStatus())
                )
                ) {
                    //提交 添加日志
                    setOrgDetailLog(evalOrgDetail);
                }


                //绑定文件
                if (CollectionUtils.isNotEmpty(evalOrgDetail.getFileIds())) {
                    for (String id : evalOrgDetail.getFileIds()) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setId(id);
                        fileInfo.setBizId(evalOrgDetail.getId());
                        fileInfoMapper.updateById(fileInfo);
                    }
                }

                //绑定申诉文件
                if (CollectionUtils.isNotEmpty(evalOrgDetail.getAppealFileIds())) {
                    //清空操作
                    List<FileInfo> list = fileInfoMapper.selectList(new QueryWrapper<FileInfo>().eq("bizType","17").eq("bizId",evalOrgDetail.getId()));
                    for (int j = 0; j < list.size(); j++) {
                        FileInfo f = list.get(j);
                        fastDfsUtil.deleteFile(f);
                    }
                    for (String id : evalOrgDetail.getAppealFileIds()) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setId(id);
                        fileInfo.setBizId(evalOrgDetail.getId());
                        fileInfoMapper.updateById(fileInfo);
                    }
                }
            }
        }

        return ResultInfo.success();
    }


    /**
     * 考核反馈列表
     *
     * @param evalOrgTask
     * @return
     */
    public Page<EvalOrgTask> evalOrgTaskPage(EvalOrgTask evalOrgTask) {
        LambdaQueryWrapper<EvalOrgTask> wrapper = new LambdaQueryWrapper<EvalOrgTask>()
                .eq(StringUtils.isNotEmpty(evalOrgTask.getOrgCode()), EvalOrgTask::getOrgCode, evalOrgTask.getOrgCode())
                .like(StringUtils.isNotEmpty(evalOrgTask.getOrgName()), EvalOrgTask::getOrgName, evalOrgTask.getOrgName())
                .eq(StringUtils.isNotEmpty(evalOrgTask.getStatus()), EvalOrgTask::getStatus, evalOrgTask.getStatus())
                .eq(StringUtils.isNotEmpty(evalOrgTask.getAdmdvs()), EvalOrgTask::getAdmdvs, evalOrgTask.getAdmdvs())
                .eq(StringUtils.isNotEmpty(evalOrgTask.getOrgType()), EvalOrgTask::getOrgType, evalOrgTask.getOrgType())
                .eq(StringUtils.isNotEmpty(evalOrgTask.getCategory()), EvalOrgTask::getCategory, evalOrgTask.getCategory())
                .eq(StringUtils.isNotEmpty(evalOrgTask.getAggrementLv()), EvalOrgTask::getAggrementLv, evalOrgTask.getAggrementLv())
                .eq(StringUtils.isNotEmpty(evalOrgTask.getYear()), EvalOrgTask::getYear, evalOrgTask.getYear())
                .like(StringUtils.isNotEmpty(evalOrgTask.getTaskName()), EvalOrgTask::getTaskName, evalOrgTask.getTaskName())
                .eq(StringUtils.isNotEmpty(evalOrgTask.getNatures()), EvalOrgTask::getNatures, evalOrgTask.getNatures())
                .eq(EvalOrgTask::getIsDel, "0")
                .orderByDesc(EvalOrgTask::getCreateTime);
        Page<EvalOrgTask> page = new Page<>(evalOrgTask.getPageNo(), evalOrgTask.getPageSize());
        Page<EvalOrgTask> evalOrgTaskPage = evalOrgTaskMapper.selectPage(page, wrapper);
        for (EvalOrgTask orgTask : evalOrgTaskPage.getRecords()) {
            //查主表保证金展示状态
            EvalTaskManage evalTaskManage = evalTaskManageMapper.selectById(orgTask.getTaskManageId());
            if (null != evalTaskManage) {
                if ("0".equals(evalTaskManage.getEarnestMoneyShow())) {
                    orgTask.setMarginStaff("******");
                    orgTask.setMarginResident("******");
                    orgTask.setMarginMedical("******");
                    orgTask.setRefundStaff("******");
                    orgTask.setRefundResident("******");
                    orgTask.setRefundMedical("******");
                    orgTask.setStaffRewards("******");
                    orgTask.setResidentRewards("******");
                    orgTask.setMedRewards("******");
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date expirationTime;
            try {
                expirationTime = sdf.parse(orgTask.getExpirationTime() + " 23:59:59");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (new Date().compareTo(expirationTime) < 0 || expirationTime.compareTo(new Date()) == 0) {
                orgTask.setIsExpiration("1");
            } else {
                orgTask.setIsExpiration("0");
            }
        }
        return evalOrgTaskPage;
    }

    /**
     * 考核反馈 详情
     *
     * @param designId 考核设计表ID
     * @return
     */
    public EvalVo getEvalOrgTask(String taskManageId, String designId, String evalOrgTaskId) {
        return queryDesign(taskManageId, designId, evalOrgTaskId);
    }

    private EvalVo queryDesign(String taskManageId, String designId, String evalOrgTaskId) {
        EvalVo vo = new EvalVo();
        LambdaQueryWrapper<EvalDesign> designQuery = new LambdaQueryWrapper<EvalDesign>().eq(EvalDesign::getId, designId).eq(EvalDesign::getIsDel, "0");

        //考核设计表
        EvalDesign evalDesign = evalDesignService.getOne(designQuery);
        LambdaQueryWrapper<EvalDesignCategory> categoryQuery = new LambdaQueryWrapper<EvalDesignCategory>().eq(EvalDesignCategory::getDesignId, evalDesign.getId())
                .orderByAsc(EvalDesignCategory::getCreateTime);
        //考核设计类目表
        List<EvalDesignCategory> evalDesignCategoryList = evalDesignCategoryService.list(categoryQuery);

        for (int i = 0; i < evalDesignCategoryList.size(); i++) {
            EvalDesignCategory evalDesignCategory = evalDesignCategoryList.get(i);
            LambdaQueryWrapper<EvalCategoryStardard> evalCategoryStardardQuery = new LambdaQueryWrapper<EvalCategoryStardard>().eq(EvalCategoryStardard::getCategoryId, evalDesignCategory.getId())
                    .orderByAsc(EvalCategoryStardard::getCreateTime);
            //考核指标
            List<EvalCategoryStardard> evalCategoryStardardList = evalCategoryStardardService.list(evalCategoryStardardQuery);

            for (int j = 0; j < evalCategoryStardardList.size(); j++) {
                //负责人
                LambdaQueryWrapper<EvalStardardUser> evalStardardUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
                evalStardardUserLambdaQueryWrapper.eq(StringUtils.isNotEmpty(taskManageId), EvalStardardUser::getTaskManageId, taskManageId);
                evalStardardUserLambdaQueryWrapper.eq(EvalStardardUser::getStardardId, evalCategoryStardardList.get(j).getId());
                EvalStardardUser evalStardardUser = evalStardardUserService.getOne(evalStardardUserLambdaQueryWrapper);
                if (evalStardardUser != null) {
                    evalCategoryStardardList.get(j).setUserId(evalStardardUser.getUserId());
                    SysUser sysUser = sysUserService.getById(evalStardardUser.getUserId());
                    if (sysUser != null){
                        evalCategoryStardardList.get(j).setUserName(sysUser.getName());
                    }
                }

                LambdaQueryWrapper<EvalStardardMethod> stardardMethodQuery = new LambdaQueryWrapper<EvalStardardMethod>().eq(EvalStardardMethod::getStardardId, evalCategoryStardardList.get(j).getId())
                        .orderByAsc(EvalStardardMethod::getCreateTime);
                //考核设计评价项目内容办法表
                List<EvalStardardMethod> evalStardardMethodList = evalStardardMethodService.list(stardardMethodQuery);
                evalCategoryStardardList.get(j).setEvalStardardMethods(evalStardardMethodList);

                for (int k = 0; k < evalStardardMethodList.size(); k++) {
                    EvalStardardMethod evalStardardMethod = evalStardardMethodList.get(k);

                    //办法模版
                    if (StringUtils.isNotEmpty(evalStardardMethod.getFileId())) {
                        FileInfo fileTemplate = fileInfoMapper.selectById(evalStardardMethod.getFileId());
                        evalStardardMethodList.get(k).setFileTemplate(fileTemplate);
                    }

                    if (StringUtils.isNotEmpty(evalOrgTaskId)) {
                        LambdaQueryWrapper<EvalOrgDetail> evalOrgDetailQuery = new LambdaQueryWrapper<EvalOrgDetail>().eq(EvalOrgDetail::getEvalOrgTaskId, evalOrgTaskId).eq(EvalOrgDetail::getEvalStardardMethodId, evalStardardMethod.getId());
                        //考核任务详情表
                        EvalOrgDetail evalOrgDetail = evalOrgDetailService.getOne(evalOrgDetailQuery);
                        if (evalOrgDetail != null) {
                            evalOrgDetail.setDetailScore(StringUtils.isNotEmpty(evalOrgDetail.getScore()) ? "" : evalOrgDetail.getScore());
                            //附件
                            List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery().eq(FileInfo::getBizId, evalOrgDetail.getId()).eq(FileInfo::getBizType, "15"));
                            if (CollectionUtils.isNotEmpty(fileInfos)) {
                                List<Map<String, String>> fileList = getFileListMap(fileInfos);
                                evalOrgDetail.setFiles(fileList);
                            }

                            //申诉附件
                            List<FileInfo> appealFiles = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery().eq(FileInfo::getBizId, evalOrgDetail.getId()).eq(FileInfo::getBizType, "17"));
                            if (CollectionUtils.isNotEmpty(appealFiles)) {
                                List<Map<String, String>> fileList = getFileListMap(appealFiles);
                                evalOrgDetail.setAppealFiles(fileList);
                            }

                            evalStardardMethodList.get(k).setEvalOrgDetail(evalOrgDetail);
                        }

                    }

                    LambdaQueryWrapper<EvalMethodInfo> evalMethodInfoQuery = new LambdaQueryWrapper<EvalMethodInfo>().eq(EvalMethodInfo::getMethodId, evalStardardMethod.getId());
                    //考核设计评价项目内容办法详情表
                    List<EvalMethodInfo> evalMethodInfoList = evalMethodInfoService.list(evalMethodInfoQuery);
                    evalStardardMethodList.get(k).setEvalMethodInfos(evalMethodInfoList);
                }
            }
            evalDesignCategoryList.get(i).setEvalCategoryStardards(evalCategoryStardardList);
        }

        evalDesign.setEvalDesignCategorys(evalDesignCategoryList);
        vo.setEvalDesign(evalDesign);
        return vo;
    }

    @NotNull
    private List<Map<String, String>> getFileListMap(List<FileInfo> fileInfos) {
        List<Map<String, String>> fileList = new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put("name", fileInfo.getFileName());
            fileMap.put("url", fileInfo.getFileUrl());
            fileMap.put("id", fileInfo.getId());
            fileList.add(fileMap);
        }
        return fileList;
    }

    public ResultInfo importScore(MultipartFile file, String taskManageId, String evalStardardMethodId, String score, String fromStatus) {
        //导入文件
        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(taskManageId, evalStardardMethodId, score, fromStatus, "score"));
            reader.read(in, -1);
        } catch (IOException ignored) {
        }
        return ResultInfo.success();
    }

    public ResultInfo importTargetScore(MultipartFile file, String taskManageId, String evalStardardMethodId, String score, String fromStatus) {
        //导入文件
        try {
            InputStream in = file.getInputStream();
            //构建对象读取
            Excel07SaxReader reader = new Excel07SaxReader(createRowHandler(taskManageId, evalStardardMethodId, score, fromStatus, "targetScore"));
            reader.read(in, -1);
        } catch (IOException ignored) {
        }
        return ResultInfo.success();
    }

    private RowHandler createRowHandler(String taskManageId, String evalStardardMethodId, String score, String fromStatus, String type) {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                QueryWrapper<EvalOrgDetail> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("taskManageId", taskManageId);
                queryWrapper.eq("evalStardardMethodId", evalStardardMethodId);
                if (rowIndex == 0) {
                    return;
                }
                int s = rowlist.size();
                ArrayList<String> msg = new ArrayList<>();
                EvalOrgDetail evalOrgDetail = new EvalOrgDetail();
                boolean sign = false;

                if (null != rowlist.get(0)) {
                    queryWrapper.eq("orgCode", String.valueOf(rowlist.get(0)));
                    evalOrgDetail = evalOrgDetail.selectOne(queryWrapper);
                } else {
                    throw new CustomException("请完善机构编码！");
                }
                if ("score".equals(type)) {
                    if (null != rowlist.get(1)) {
                        //验证信息 正数、负数、小数
                        if (String.valueOf(rowlist.get(1)).matches("^(\\-|\\+)?\\d+(\\.\\d+)?$") || "0".equals(String.valueOf(rowlist.get(1)))) {
                            BigDecimal scoreExcel = new BigDecimal(String.valueOf(rowlist.get(1)));
                            // 小于等于办法最大份
                            if (scoreExcel.compareTo(new BigDecimal(score)) > 0) {
                                sign = true;
                                msg.add(String.valueOf(rowlist.get(0)));
                            }
                        } else {
                            sign = true;
                            msg.add(String.valueOf(rowlist.get(0)));
                        }
                        evalOrgDetail.setScore(String.valueOf(rowlist.get(1)));
                    } else {
                        sign = true;
                        msg.add(String.valueOf(rowlist.get(0)));
                    }
                } else if ("targetScore".equals(type)) {
                    if (null != rowlist.get(1)) {
                        //验证信息 正数、负数、和小数
                        if (String.valueOf(rowlist.get(1)).matches("^(\\-|\\+)?\\d+(\\.\\d+)?$") || "0".equals(String.valueOf(rowlist.get(1)))) {
                        } else {
                            sign = true;
                            msg.add(String.valueOf(rowlist.get(0)));
                        }
                        evalOrgDetail.setTargetScore(String.valueOf(rowlist.get(1)));
                    } else {
                        throw new CustomException("请完善指标值！");
                    }
                }

                //-1暂存 1待初审 2已初审 3已复审
                if ("2".equals(fromStatus)) {//初审
                    evalOrgDetail.setStatus("2");

                } else if ("4".equals(fromStatus)) {//复审
                    evalOrgDetail.setStatus("3");
                }
                if ("score".equals(type)) {
                    if (sign) {
                        throw new CustomException("该办法的分值为0~" + score + ",机构" + String.join("、", msg) + "的分数不在范围中");
                    } else {
                        evalOrgDetail.updateById();
                        //添加日志
                        setOrgDetailLog(evalOrgDetail);
                    }
                } else if ("targetScore".equals(type)) {
                    if (sign) {
                        throw new CustomException("该办法的指标值为数字,机构" + String.join("、", msg) + "的指标值不在范围中");
                    } else {
                        evalOrgDetail.updateById();
                        //添加日志
                        setOrgDetailLog(evalOrgDetail);
                    }
                }

            }
        };
    }

    public ResultInfo audit(EvalVo vo) {
        if (null != vo && null != vo.getEvalOrgDetailList()) {
            for (EvalOrgDetail detail : vo.getEvalOrgDetailList()) {

                if (StringUtils.isNotEmpty(detail.getScore())) {
                    //添加日志
                    setOrgDetailLog(detail);
                }
                detail.updateById();
            }
        }
        return ResultInfo.success("操作成功");
    }

    public void evalOrgDetailExport(HttpServletResponse response, EvalVo vo) throws IOException {
        List<EvalVo> list = evalOrgDetailMapper.getOrgDetailList(vo);

        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        writer.addHeaderAlias("orgName", "机构名称");
        writer.addHeaderAlias("orgCode", "机构代码");
        writer.addHeaderAlias("score", "分值");

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
