package com.jsdc.ybpt.controller_eval;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.eval_.EvalOrgDetail;
import com.jsdc.ybpt.eval_.EvalOrgTask;
import com.jsdc.ybpt.eval_.EvalTaskManage;
import com.jsdc.ybpt.service.eval.*;
import com.jsdc.ybpt.vo.EvalDesignVo;
import com.jsdc.ybpt.vo.EvalVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效考核/考核反馈
 */
@RestController
@RequestMapping("/eval")
public class EvalController {
    @Autowired
    private EvalService evalService;
    @Autowired
    private EvalOrgTaskService evalOrgTaskService;

    /**
     * 运维专用接口 回退 暂存 admin账户显示按钮
     */
    @RequestMapping("updateOrgTaskManagerStatus")
    public ResultInfo updateOrgTaskManagerStatus(String id){
        EvalOrgTask evalTaskManage = new EvalOrgTask();
        evalTaskManage.setId(id);
        evalTaskManage.setStatus("-1");
        return ResultInfo.success(evalTaskManage.updateById());
    }

    /**
     * 机构填写上传
     *
     * @param evalStardardMethodId 内容办法表ID
     * @param year                 考核年度
     * @param orgCode              机构机构编码
     */
    @RequestMapping("/uploadEvalOrgDetail")
    @ResponseBody
    public ResultInfo uploadEvalOrgDetail(MultipartFile file, String evalStardardMethodId, String year, String orgCode) {
        return evalService.uploadEvalOrgDetail(file, evalStardardMethodId, year, orgCode);
    }

    /**
     * 机构填写 申诉上传
     *
     * @param evalStardardMethodId 内容办法表ID
     * @param year                 考核年度
     * @param orgCode              机构机构编码
     */
    @RequestMapping("/uploadAppealEvalOrgDetail")
    @ResponseBody
    public ResultInfo uploadAppealEvalOrgDetail(MultipartFile file, String evalStardardMethodId, String year, String orgCode) {
        return evalService.uploadAppealEvalOrgDetail(file, evalStardardMethodId, year, orgCode);
    }

    /**
     * 删除文件
     *
     * @param id 文件id
     * @return
     */
    @RequestMapping("/removeUploadEvalOrgDetail")
    public ResultInfo removeUploadEvalOrgDetail(String id) {
        return ResultInfo.success(evalService.removeUploadEvalOrgDetail(id));
    }

    /**
     * 分值导入
     *
     * @param file
     * @return
     */
    @RequestMapping("/importScore")
    public ResultInfo importScore(MultipartFile file, String taskManageId, String evalStardardMethodId, String score, String fromStatus) {
        return evalService.importScore(file, taskManageId, evalStardardMethodId, score, fromStatus);
    }

    /**
     * 指标导入
     *
     * @param file
     * @return
     */
    @RequestMapping("/importTargetScore")
    public ResultInfo importTargetScore(MultipartFile file, String taskManageId, String evalStardardMethodId, String score, String fromStatus) {
        return evalService.importTargetScore(file, taskManageId, evalStardardMethodId, score, fromStatus);
    }

    /**
     * 批量确认
     *
     * @return
     */
    @RequestMapping("/audit")
    public ResultInfo audit(@RequestBody EvalVo vo) {
        return evalService.audit(vo);
    }

    /**
     * 绩效考核列表
     *
     * @param evalTaskManage
     * @return
     */
    @RequestMapping("/evalTaskManagePage")
    public ResultInfo evalTaskManagePage(EvalTaskManage evalTaskManage) {
        return ResultInfo.success(evalService.evalTaskManagePage(evalTaskManage));
    }

    /**
     * 绩效考核 详情
     *
     * @param assessmentId 考核单ID
     * @return
     */
    @RequestMapping("/getEvalTaskManage")
    public ResultInfo getEvalTaskManage(String taskManageId, String assessmentId) {
        return ResultInfo.success(evalService.getEvalTaskManage(taskManageId, assessmentId));
    }

    /**
     * 考核数据
     */
    @RequestMapping("/getOrgDetailPage")
    public ResultInfo getOrgDetailPage(EvalVo vo) {
        return evalService.getOrgDetailPage(vo);
    }


    /**
     * 确认考核
     */
    @RequestMapping("/setOrgDetail")
    public ResultInfo setOrgDetail(EvalOrgDetail evalOrgDetail) {
        return evalService.setOrgDetail(evalOrgDetail);
    }

    /**
     * 确认考核日志列表
     */
    @RequestMapping("/getOrgDetailLog")
    public ResultInfo getOrgDetailLog(String id) {
        return evalService.getOrgDetailLog(id);
    }

    /**
     * 绩效考核/考核反馈 保存
     */
    @RequestMapping("/setEvalOrgDetail")
    public ResultInfo setEvalOrgDetail(@RequestBody EvalVo vo) {
        return evalService.setEvalOrgDetail(vo);
    }

    /**
     * 考核反馈列表
     *
     * @param evalOrgTask
     * @return
     */
    @RequestMapping("/evalOrgTaskPage")
    public ResultInfo evalOrgTaskPage(EvalOrgTask evalOrgTask) {
        return ResultInfo.success(evalService.evalOrgTaskPage(evalOrgTask));
    }

    /**
     * 考核反馈 详情
     *
     * @param designId 考核设计表ID
     * @return
     */
    @RequestMapping("/getEvalOrgTask")
    public ResultInfo getEvalOrgTask(String taskManageId, String designId, String evalOrgTaskId) {
        return ResultInfo.success(evalService.getEvalOrgTask(taskManageId, designId, evalOrgTaskId));
    }

    /**
     *考核反馈-分值导出
     */
    @RequestMapping("/evalOrgDetailExport")
    public void evalOrgDetailExport(HttpServletResponse response, EvalVo vo){
        try {
            evalService.evalOrgDetailExport(response,vo) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
