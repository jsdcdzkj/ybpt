package com.jsdc.ybpt.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.assessment.KHManage;
import com.jsdc.ybpt.assessment.KhAssessment;
import com.jsdc.ybpt.assessment.KhAssessmentDetail;
import com.jsdc.ybpt.assessment.KhTaskManage;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.*;
import com.jsdc.ybpt.vo.AssessmentVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 考核信息
 */
@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    @Autowired
    private KhTaskManageService khTaskManageService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private KhAssessmentDetailService detailService;
    @Autowired
    private KhAssessmentService khAssessmentService;
    @Autowired
    private KHManageService khManageService;
    @Autowired
    private KHLogService khLogService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("assessmentList")
    public ResultInfo assessmentList(@RequestBody AssessmentVo vo) {

        Page<AssessmentVo> khManagePage = khManageService.getPage(vo);
        if (CollectionUtils.isNotEmpty(khManagePage.getRecords())) {
            for (AssessmentVo k : khManagePage.getRecords()) {
                if ("1".equals(k.getOrg_type())) {
                    if (StringUtils.isNotEmpty(k.getCategory())) {
                        if ("1".equals(k.getCategory())) {
                            k.setCategory_name("门诊");
                        } else if ("2".equals(k.getCategory())) {
                            k.setCategory_name("住院");
                        }
                    }
                    k.setOrg_type("医疗机构");
                    if ("1".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("一级");
                    } else if ("2".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("二级");
                    } else if ("3".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("三级");
                    } else if ("9".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("未定级");
                    }
                } else if ("2".equals(k.getOrg_type())) {
                    k.setOrg_type("零售药店");
                    if ("4".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("A级");
                    } else if ("5".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("B级");
                    } else if ("6".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("C级");
                    }
                }


            }
        }
        return ResultInfo.success(khManagePage);
    }

    /**
     * 详情
     *
     * @return
     */
    @RequestMapping("assessmentDetails")
    public ResultInfo assessmentDetails(@RequestBody AssessmentVo vo) {
        return ResultInfo.success(khManageService.getDetails(vo));
    }

    /**
     * 填报
     * 抽查
     *
     * @return
     */
    @RequestMapping("assessmentFill")
    public ResultInfo assessmentFill(String id, String log_title, String approval_opinion, String assessmentDetails, String[] fileRemoveIds, List<MultipartFile> files) {
        return khManageService.fill(id, log_title, approval_opinion, assessmentDetails, fileRemoveIds, files);
    }

    /**
     * 更新过期时间
     * Author wy
     * Date 2022/12/08 14:56
     */
    @RequestMapping("submitExpirationTime")
    public ResultInfo submitExpirationTime(@RequestBody AssessmentVo vo) {
        khManageService.submitExpirationTime(vo);
        return ResultInfo.success();
    }

    /**
     * 提交审核
     * 撤回提交
     * 批量审核
     *
     * @return
     */
    @RequestMapping("assessmentEdit")
    public ResultInfo assessmentEdit(@RequestBody AssessmentVo vo) {
        return ResultInfo.success(khManageService.edit(vo));
    }

    /**
     * 提交审核接口
     * Author wzn
     * Date 2022/11/25 10:33
     */
    @RequestMapping("submitForReview")
    public ResultInfo submitForReview(String id) {
        khManageService.submitForReview(id);
        return ResultInfo.success();
    }

    /**
     * 提交审核接口
     * Author wy
     * Date 2022/11/25 10:33
     */
    @RequestMapping("submitReview")
    public ResultInfo submitReview(String id) {
        khManageService.submitReview(id);
        return ResultInfo.success();
    }

    /**
     * 提交审核通过
     * Author wy
     * Date Date 2022/11/28 14:26
     */
    @RequestMapping("submitSuccess")
    public ResultInfo submitSuccess(String id) {
        khManageService.submitSuccess(id);
        return ResultInfo.success();
    }

    /**
     * 提交审核驳回
     * Author wy
     * Date Date 2022/11/28 14:26
     */
    @RequestMapping("submitReject")
    public ResultInfo submitReject(String id,String approval_opinion) {
        khManageService.submitReject(id,approval_opinion);
        return ResultInfo.success();
    }

    /**
     * 提交批量审核
     * Author wy
     * Date Date 2022/11/28 14:26
     */
    @RequestMapping("batchAudit")
    public ResultInfo batchAudit(@RequestBody AssessmentVo vo) {
        return khManageService.batchAudit(vo);
    }

    /**
     * 撤回提交
     * Author wzn
     * Date 2022/11/25 10:44
     */
    @RequestMapping("cancleSub")
    public ResultInfo cancleSub(String id) {
        khManageService.cancleSub(id);
        return ResultInfo.success();
    }

    /**
     * 日志详情接口
     * Author wzn
     * Date 2022/11/25 11:50
     */
    @RequestMapping("logList")
    public ResultInfo logList(String id) {
        return ResultInfo.success(khLogService.logList(id));
    }


    /**
     * 导出
     *
     * @return
     */
    @RequestMapping("assessmentExport")
    public void assessmentExport(HttpServletResponse response, AssessmentVo vo) throws IOException {
        List<AssessmentVo> list = khManageService.getList(vo);
        if (CollectionUtils.isNotEmpty(list)) {
            for (KHManage k : list) {
                String score = k.getScore();
                k.setScore("");
                if ("0".equals(k.getStatus())) {
                    k.setStatus("填报中");
                } else if ("1".equals(k.getStatus())) {
                    k.setStatus("待审核");
                } else if ("2".equals(k.getStatus())) {
                    k.setStatus("审核通过");
                    k.setScore(score);
                }
                if ("1".equals(k.getOrg_type())) {
                    k.setOrg_type("医疗机构");
                    if ("1".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("一级");
                    } else if ("2".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("二级");
                    } else if ("3".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("三级");
                    } else if ("9".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("未定级");
                    }
                } else if ("2".equals(k.getOrg_type())) {
                    k.setOrg_type("零售药店");
                    if ("4".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("A级");
                    } else if ("5".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("B级");
                    } else if ("6".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("C级");
                    }
                }
            }
        }
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("task_name", "任务名称");
        writer.addHeaderAlias("org_type", "机构类型");
        writer.addHeaderAlias("aggrement_lv", "协议等级");
        writer.addHeaderAlias("YEAR", "考核年度");
        writer.addHeaderAlias("fixmedins_name", "机构名称");
        writer.addHeaderAlias("fixmedins_code", "机构编码");
        writer.addHeaderAlias("score", "得分");
        writer.addHeaderAlias("status", "考核状态");

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
     * 新增考核任务
     * Author wzn
     * Date 2022/11/21 14:47
     */
    @RequestMapping("addTaskMange")
    public ResultInfo addTaskMange(@RequestBody KhTaskManage khTaskManage) {
        khTaskManageService.addTaskMange(khTaskManage);
        return ResultInfo.success();
    }


    /**
    *穿梭框 获取已选机构接口
    * Author wzn
    * Date 2022/12/6 16:03
    */
    @RequestMapping("getAll")
    public ResultInfo getAll(@RequestBody KhTaskManage khTaskManage) {
        return ResultInfo.success(khTaskManageService.getAll(khTaskManage));
    }


    @RequestMapping("/exportData")
    public void exportSettleAbnormalData(HttpServletResponse response, String id) throws Exception{
        List<FixmedinsB> fixmedinsBList = khTaskManageService.getOrg(id) ;
        if(CollectionUtils.isNotEmpty(fixmedinsBList)){
            for(FixmedinsB f:fixmedinsBList){
                if ("1".equals(f.getAggrement_lv())) {
                    f.setAggrement_lv("一级");
                } else if ("2".equals(f.getAggrement_lv())) {
                    f.setAggrement_lv("二级");
                } else if ("3".equals(f.getAggrement_lv())) {
                    f.setAggrement_lv("三级");
                } else if ("9".equals(f.getAggrement_lv())) {
                    f.setAggrement_lv("未定级");
                }else if ("4".equals(f.getAggrement_lv())) {
                    f.setAggrement_lv("A级");
                } else if ("5".equals(f.getAggrement_lv())) {
                    f.setAggrement_lv("B级");
                } else if ("6".equals(f.getAggrement_lv())) {
                    f.setAggrement_lv("C级");
                }
            }
        }

        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("fixmedins_code", "定点医药机构编号");
        writer.addHeaderAlias("fixmedins_name", "定点医药机构名称");
        writer.addHeaderAlias("aggrement_lv", "协议等级");


        //只导出定义字段
        writer.setOnlyAlias(true) ;
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(fixmedinsBList, true);
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
     * 修改考核任务
     * Author wzn
     * Date 2022/11/21 15:04
     */
    @RequestMapping("updTaskMange")
    public ResultInfo updTaskMange(@RequestBody KhTaskManage khTaskManage) {
        khTaskManageService.updTaskMange(khTaskManage);
        return ResultInfo.success();
    }


    /**
     * 删除考核任务
     * Author wzn
     * Date 2022/11/21 15:08
     */
    @RequestMapping("delTaskMange")
    public ResultInfo delTaskMange(String id) {
        khTaskManageService.delTaskMange(id);
        return ResultInfo.success();
    }


    /**
     * 发布考核任务
     * Author wzn
     * Date 2022/11/21 15:50
     */
    @RequestMapping("publishTaskMange")
    public ResultInfo publishTaskMange(String id) {
        khTaskManageService.publishTaskMange(id);
        return ResultInfo.success();
    }


    /**
     * 撤销发布
     * Author wzn
     * Date 2022/11/22 10:05
     */
    @RequestMapping("undoPublication")
    public ResultInfo undoPublication(String id) {
        return khTaskManageService.undoPublication(id);
    }

    /**
     * 保存考核单-考核项
     *
     * @param khAssessment
     * @return
     */
    @RequestMapping("saveAssess")
    public ResultInfo saveAssess(@RequestBody KhAssessment khAssessment) {
        SysUser sysUser = sysUserService.getUser();
        //type不为空说明是复制功能，需要新增记录
        if (StrUtil.isNotEmpty(khAssessment.getId()) && StrUtil.isEmpty(khAssessment.getType())) {
            //每年 每一种机构类型 每一个协议等级只能有一条数据
            long count = 0;
            if(khAssessment.getOrg_type().equals("1")){
                count = khAssessmentService.count(new QueryWrapper<KhAssessment>()
                        .eq("is_del", "0")
                        .eq("org_type", khAssessment.getOrg_type())
                        .eq("category", khAssessment.getCategory())
                        .eq("admdvs", sysUser.getOrg_code())
                        .eq("aggrement_lv", khAssessment.getAggrement_lv())
                        .eq("year_of_assessment", khAssessment.getYear_of_assessment())
                        .ne("id", khAssessment.getId()));
            }else{
                count = khAssessmentService.count(new QueryWrapper<KhAssessment>()
                        .eq("is_del", "0")
                        .eq("org_type", khAssessment.getOrg_type())
                        .eq("admdvs", sysUser.getOrg_code())
                        .eq("aggrement_lv", khAssessment.getAggrement_lv())
                        .eq("year_of_assessment", khAssessment.getYear_of_assessment())
                        .ne("id", khAssessment.getId()));
            }

            if (count > 0) {
                return ResultInfo.error("该年度已存在相同机构类型和协议等级的考核模板");
            }
            //若考核单已被使用，且考核任务已经发布，不允许编辑
            long count_pub = khTaskManageService.count(new QueryWrapper<KhTaskManage>()
                    .eq("assessment_id", khAssessment.getId())
                    .eq("publish_status", "1"));
            if (count_pub > 0) {
                return ResultInfo.error("该考核单已被使用且已发布，无法执行编辑操作");
            }
            //修改
            khAssessment.setUpdateTime(new Date());
            khAssessment.setUpdateUser(sysUser.getId());
            //删除老考核项
            detailService.remove(new QueryWrapper<KhAssessmentDetail>().eq("assessment_id", khAssessment.getId()));
        } else {
            //每年 每一种机构类型 每一个协议等级只能有一条数据
            long count = 0;
            if(khAssessment.getOrg_type().equals("1")){
                count = khAssessmentService.count(new QueryWrapper<KhAssessment>()
                        .eq("is_del", "0")
                        .eq("org_type", khAssessment.getOrg_type())
                        .eq("category", khAssessment.getCategory())
                        .eq("admdvs", sysUser.getOrg_code())
                        .eq("aggrement_lv", khAssessment.getAggrement_lv())
                        .eq("year_of_assessment", khAssessment.getYear_of_assessment()));
            }else{
                count = khAssessmentService.count(new QueryWrapper<KhAssessment>()
                        .eq("is_del", "0")
                        .eq("org_type", khAssessment.getOrg_type())
                        .eq("admdvs", sysUser.getOrg_code())
                        .eq("aggrement_lv", khAssessment.getAggrement_lv())
                        .eq("year_of_assessment", khAssessment.getYear_of_assessment()));
            }

            if (count > 0) {
                return ResultInfo.error("该年度已存在相同机构类型和协议等级的考核模板");
            }
            //新增和复制
            khAssessment.setId(IdUtil.simpleUUID());
            khAssessment.setCreateTime(new Date());
            khAssessment.setAdmdvs(sysUser.getOrg_code());
            khAssessment.setCreateUser(sysUser.getId());
            khAssessment.setIs_del("0");
        }
        //新增明细
        int i = 0;
        for (KhAssessmentDetail detail : khAssessment.getDetails()) {
            detail.setId(IdUtil.simpleUUID());
            detail.setAssessment_id(khAssessment.getId());
            detail.setSort(i++);
            detail.insert();
        }
        khAssessment.insertOrUpdate();
        return ResultInfo.success();
    }

    /**
     * 获取考核单详情
     *
     * @param id
     * @return
     */
    @RequestMapping("getAssessDetail")
    public ResultInfo getAssessDetail(String id) {
        KhAssessment khAssessment = khAssessmentService.getById(id);
        List<KhAssessmentDetail> details = detailService.list(new QueryWrapper<KhAssessmentDetail>().eq("assessment_id", khAssessment.getId()).orderByAsc("sort"));
        for (KhAssessmentDetail x : details) {
            String checkListStr = "";
            if ("1".equals(x.getIs_text())) {
                checkListStr = "文本描述";
            }
            if ("1".equals(x.getIs_file())) {
                if (StrUtil.isNotEmpty(checkListStr)) {
                    checkListStr += ",";
                }
                checkListStr += "文件上传";
            }
            x.setCheckList(checkListStr.split(","));
        }
        khAssessment.setDetails(details);
        return ResultInfo.success(khAssessment);
    }

    /**
     * 获取考核单列表
     *
     * @param khAssessment
     * @return
     */
    @RequestMapping("getAssessList")
    public ResultInfo getAssessList(@RequestBody KhAssessment khAssessment) {
        SysUser sysUser = sysUserService.getUser();
        Page page = new Page(khAssessment.getPageNo(), khAssessment.getPageSize());
        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del", "0");
        if (StrUtil.isNotEmpty(khAssessment.getAssess_name())) {
            qw.like("assess_name", khAssessment.getAssess_name());
        }
        if (StrUtil.isNotEmpty(khAssessment.getOrg_type())) {
            qw.eq("org_type", khAssessment.getOrg_type());
        }
        if (StrUtil.isNotEmpty(khAssessment.getAggrement_lv())) {
            qw.eq("aggrement_lv", khAssessment.getAggrement_lv());
        }
        if (StrUtil.isNotEmpty(khAssessment.getYear_of_assessment())) {
            qw.eq("year_of_assessment", khAssessment.getYear_of_assessment());
        }
        qw.eq("admdvs",sysUser.getOrg_code());
        qw.orderByDesc("createTime");
        Page<KhAssessment> pageinfo = khAssessmentService.page(page, qw);
        HashMap<String, String> lvMap = aggrement_lv();
        for (KhAssessment record : pageinfo.getRecords()) {
            if (StringUtils.isNotEmpty(record.getCategory())) {
                record.setCategory_name(StringUtils.equals(record.getCategory(), "1") ? "门诊" : "住院");
            }
            record.setOrg_type("1".equals(record.getOrg_type()) ? "医疗机构" : "零售药店");
            record.setAggrement_lv(lvMap.get(record.getAggrement_lv()));
        }
        return ResultInfo.success(pageinfo);
    }

    private HashMap<String, String> aggrement_lv() {
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
     * 删除考核单
     *
     * @param assessment_id
     * @return
     */
    @RequestMapping("delAssess")
    public ResultInfo delAssess(String assessment_id) {
        //判断是否被使用，若被使用 无法被删除
        long count = khTaskManageService.count(new QueryWrapper<KhTaskManage>().eq("assessment_id", assessment_id));
        if (count > 0) {
            return ResultInfo.error("该考核单已被使用，无法删除！");
        }
        KhAssessment khAssessment = khAssessmentService.getById(assessment_id);
        khAssessment.setIs_del("1");
        khAssessment.updateById();
        return ResultInfo.success();
    }


    /**
     * 考核任务列表
     * Author wzn
     * Date 2022/11/22 11:48
     */
    @PostMapping("/selectTaskManageList")
    public ResultInfo selectTaskManageList(@RequestBody KhTaskManage khTaskManage) {
        Page<KhTaskManage> khTaskManagePage = khTaskManageService.selectTaskManageList(khTaskManage);
        if (CollectionUtils.isNotEmpty(khTaskManagePage.getRecords())) {
            for (KhTaskManage k : khTaskManagePage.getRecords()) {
                if ("1".equals(k.getOrg_type())) {
                    if (StringUtils.isNotEmpty(k.getCategory())) {
                        if ("1".equals(k.getCategory())) {
                            k.setCategory_name("门诊");
                        } else if ("2".equals(k.getCategory())) {
                            k.setCategory_name("住院");
                        }
                    }
                    k.setOrg_type("医疗机构");
                    if ("1".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("一级");
                    } else if ("2".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("二级");
                    } else if ("3".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("三级");
                    } else if ("9".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("未定级");
                    }
                } else if ("2".equals(k.getOrg_type())) {
                    k.setOrg_type("零售药店");
                    if ("4".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("A级");
                    } else if ("5".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("B级");
                    } else if ("6".equals(k.getAggrement_lv())) {
                        k.setAggrement_lv("C级");
                    }
                }


            }
        }
        return ResultInfo.success(khTaskManagePage);
    }


    /**
     * 考核单下拉数据
     * Author wzn
     * Date 2022/11/23 14:44
     */
    @PostMapping("/checkListDropDownData")
    public ResultInfo checkListDropDownData() {
        return ResultInfo.success(khAssessmentService.dataList());
    }

    /**
     * 根据选择内容，默认选择一个考核单
     * Author wzn
     * Date 2022/11/23 15:09
     */
    @PostMapping("/selectBy")
    public ResultInfo selectBy(@RequestBody KhAssessment khAssessment) {
        return ResultInfo.success(khAssessmentService.selectBy(khAssessment));
    }

    /**
     * 考核任务详情接口
     * Author wzn
     * Date 2022/11/23 16:29
     */
    @RequestMapping("info")
    public ResultInfo info(String id) {
        return ResultInfo.success(khTaskManageService.info(id));
    }

    /**
     * 考核单详情接口
     * Author wzn
     * Date 2022/11/24 10:39
     */
    @RequestMapping("assesDetail")
    public ResultInfo assesDetail(String id) {
        return ResultInfo.success(khTaskManageService.assesDetail(id));
    }


}
