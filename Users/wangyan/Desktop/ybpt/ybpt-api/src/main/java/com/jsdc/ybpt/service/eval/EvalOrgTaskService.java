package com.jsdc.ybpt.service.eval;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.abnormal.SettleAbnormal;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.eval_.EvalOrgTask;
import com.jsdc.ybpt.eval_.EvalTaskManage;
import com.jsdc.ybpt.mapper.eval.EvalOrgTaskMapper;
import com.jsdc.ybpt.mapper.eval.EvalTaskManageMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 * (EvalOrgTask)表服务接口
 *
 * @author wangyan
 * @since 2023-11-17 10:44:47
 */
@Service
public class EvalOrgTaskService extends BaseService<EvalOrgTask> {


    @Autowired
    private EvalOrgTaskMapper evalOrgTaskMapper ;

    @Autowired
    private EvalTaskManageMapper evalTaskManageMapper ;

    @Autowired
    private SysUserService sysUserService ;


    /**
    *历史考核列表接口
    * Author wzn
    * Date 2023/11/27 10:46
    */
    public Page<EvalOrgTask> getHisPageList(EvalOrgTask evalOrgTask){
        Page<EvalOrgTask> page = new Page<>(evalOrgTask.getPageNo(), evalOrgTask.getPageSize());
        QueryWrapper<EvalOrgTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDel", "0");
        //权限 医保机构能查看除了状态不是-1（暂存）
        SysUser sysUser = sysUserService.getUser() ;
        if ("1".equals(sysUser.getUser_type()) && !"320399".equals(sysUser.getOrg_code())) {
            queryWrapper.eq("admdvs", sysUser.getOrg_code());
            queryWrapper.ne("status", "-1");

        }else if("1".equals(sysUser.getUser_type()) && "320399".equals(sysUser.getOrg_code())){
            queryWrapper.ne("status", "-1");
        }else {
            queryWrapper.eq("orgCode", sysUser.getOrg_code());
        }
        //任务名称
        if (!"".equals(evalOrgTask.getTaskName()) && null != evalOrgTask.getTaskName()) {
            queryWrapper.like("taskName", evalOrgTask.getTaskName());
        }
        //统筹区
        if (!"".equals(evalOrgTask.getAdmdvs()) && null != evalOrgTask.getAdmdvs()) {
            queryWrapper.eq("admdvs", evalOrgTask.getAdmdvs());
        }

        //机构类型
        if (StringUtils.isNotEmpty(evalOrgTask.getOrgType())) {
            queryWrapper.eq("orgType", evalOrgTask.getOrgType());
        }
        //机构类别
        if (StringUtils.isNotEmpty(evalOrgTask.getCategory())) {
            queryWrapper.eq("category", evalOrgTask.getCategory());
        }
        //协议等级
        if (StringUtils.isNotEmpty(evalOrgTask.getAggrementLv())) {
            queryWrapper.eq("aggrementLv", evalOrgTask.getAggrementLv());
        }

        //考核年度
        if (StringUtils.isNotEmpty(evalOrgTask.getYear())) {
            queryWrapper.eq("year", evalOrgTask.getYear());
        }
        //经营性质
        if (StringUtils.isNotEmpty(evalOrgTask.getNatures())) {
            queryWrapper.eq("natures", evalOrgTask.getNatures());
        }

        if (StringUtils.isNotEmpty(evalOrgTask.getOrgCode())) {
            queryWrapper.eq("orgCode", evalOrgTask.getOrgCode());
        }
        //精神专科
        if (StringUtils.isNotEmpty(evalOrgTask.getSpirit())) {
            queryWrapper.eq("spirit", evalOrgTask.getSpirit());
        }
        //权限 根据统筹区
        queryWrapper.orderByDesc("createTime") ;
        Page<EvalOrgTask> evalOrgTaskPage = evalOrgTaskMapper.selectPage(page, queryWrapper);

        if(CollectionUtil.isNotEmpty(evalOrgTaskPage.getRecords())){
            for(EvalOrgTask e:evalOrgTaskPage.getRecords()){
                //1:机构 2：药店
                if("1".equals(e.getOrgType())){
                    e.setOrgTypeName("医疗机构");
                }else if("2".equals(e.getOrgType())){
                    e.setOrgTypeName("零售药店");
                }

                //类别1.门诊 2.住院
                if("1".equals(e.getCategory())){
                    e.setCategoryName("门诊");
                }else if("2".equals(e.getCategory())){
                    e.setCategoryName("住院");
                }

                //协议等级 "1", "一级"  "2", "二级"  "3", "三级"  "4", "A级别"   "5", "B级别"   "6", "C级别"   "9", "未定级"
                if("1".equals(e.getAggrementLv())){
                    e.setAggrementLvName("一级");
                }else if("2".equals(e.getAggrementLv())){
                    e.setAggrementLvName("二级");
                }else if("3".equals(e.getAggrementLv())){
                    e.setAggrementLvName("三级");
                }else if("4".equals(e.getAggrementLv())){
                    e.setAggrementLvName("A级");
                }else if("5".equals(e.getAggrementLv())){
                    e.setAggrementLvName("B级");
                }else if("6".equals(e.getAggrementLv())){
                    e.setAggrementLvName("C级");
                }else if("9".equals(e.getAggrementLv())){
                    e.setAggrementLvName("未定级");
                }

                // 经营性质 1:公立 2:私立
                if("1".equals(e.getNatures())){
                    e.setNaturesName("公立");
                }else if("2".equals(e.getNatures())){
                    e.setNaturesName("私立");
                }

                //精神专科 0：非精神专科  1精神专科
                if("0".equals(e.getSpirit())){
                    e.setSpiritName("非精神专科");
                }else if("1".equals(e.getSpirit())){
                    e.setSpiritName("精神专科");
                }
                //查主表保证金展示状态
//               EvalTaskManage evalTaskManage =  evalTaskManageMapper.selectById(e.getTaskManageId()) ;
//                if(null != evalTaskManage){
//                    if("0".equals(evalTaskManage.getEarnestMoneyShow())){
//                        e.setMarginStaff("******");
//                        e.setMarginResident("******");
//                        e.setMarginMedical("******");
//                        e.setRefundStaff("******");
//                        e.setRefundResident("******");
//                        e.setRefundMedical("******");
//                        e.setStaffRewards("******");
//                        e.setResidentRewards("******");
//                        e.setMedRewards("******");
//                    }
//                }
//考核状态 0填报中  1 初审中 2 待复审 3  待计算保证金 4完成   -1  暂存  5待反馈

                if("-1".equals(e.getStatus())){
                    e.setStatusName("暂存");
                }else if("0".equals(e.getStatus())){
                    e.setStatusName("填报中");
                }else if("1".equals(e.getStatus())){
                    e.setStatusName("初审中");
                }
                else if("2".equals(e.getStatus())){
                    e.setStatusName("待复审");
                }else if("3".equals(e.getStatus())){
                    e.setStatusName("待计算保证金");
                }else if("4".equals(e.getStatus())){
                    e.setStatusName("完成");
                }else if("5".equals(e.getStatus())){
                    e.setStatusName("待反馈");
                }
            }
        }
        return evalOrgTaskPage ;
    }


    /**
    *险种配置接口
    * Author wzn
    * Date 2023/11/27 15:48
    */
    public void updeById(EvalOrgTask evalOrgTask){
        EvalOrgTask evalOrgTask1 = new EvalOrgTask() ;
        evalOrgTask1.setId(evalOrgTask.getId());
        evalOrgTask1.setStaffProportion(evalOrgTask.getStaffProportion());
        evalOrgTask1.setResidentProportion(evalOrgTask.getResidentProportion());
        evalOrgTask1.setMedicalTreatmentProportion(evalOrgTask.getMedicalTreatmentProportion());
        evalOrgTask1.updateById() ;
    }




    /**
    *历史考核信息导出
    * Author wzn
    * Date 2023/11/28 10:14
    */
    public void evalOrgTaskExport(HttpServletResponse response, EvalOrgTask evalOrgTask) throws Exception{
        QueryWrapper<EvalOrgTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDel", "0");
        //任务名称
        if (!"".equals(evalOrgTask.getTaskName()) && null != evalOrgTask.getTaskName()) {
            queryWrapper.like("taskName", evalOrgTask.getTaskName());
        }
        //统筹区
        if (!"".equals(evalOrgTask.getAdmdvs()) && null != evalOrgTask.getAdmdvs()) {
            queryWrapper.like("admdvs", evalOrgTask.getAdmdvs());
        }

        //机构类型
        if (StringUtils.isNotEmpty(evalOrgTask.getOrgType())) {
            queryWrapper.eq("orgType", evalOrgTask.getOrgType());
        }
        //机构类别
        if (StringUtils.isNotEmpty(evalOrgTask.getCategory())) {
            queryWrapper.eq("category", evalOrgTask.getCategory());
        }
        //协议等级
        if (StringUtils.isNotEmpty(evalOrgTask.getAggrementLv())) {
            queryWrapper.eq("aggrementLv", evalOrgTask.getAggrementLv());
        }

        //考核年度
        if (StringUtils.isNotEmpty(evalOrgTask.getYear())) {
            queryWrapper.eq("year", evalOrgTask.getYear());
        }
        //经营性质
        if (StringUtils.isNotEmpty(evalOrgTask.getNatures())) {
            queryWrapper.eq("natures", evalOrgTask.getNatures());
        }

        if (StringUtils.isNotEmpty(evalOrgTask.getOrgCode())) {
            queryWrapper.eq("orgCode", evalOrgTask.getOrgCode());
        }
        //精神专科
        if (StringUtils.isNotEmpty(evalOrgTask.getSpirit())) {
            queryWrapper.eq("spirit", evalOrgTask.getSpirit());
        }
        //权限 根据统筹区
        queryWrapper.orderByDesc("createTime") ;
        List<EvalOrgTask> details = evalOrgTaskMapper.selectList(queryWrapper);


        if(CollectionUtil.isNotEmpty(details)){
            for(EvalOrgTask e:details){
                //1:机构 2：药店
                if("1".equals(e.getOrgType())){
                    e.setOrgTypeName("医疗机构");
                }else if("2".equals(e.getOrgType())){
                    e.setOrgTypeName("零售药店");
                }

                //类别1.门诊 2.住院
                if("1".equals(e.getCategory())){
                    e.setCategoryName("门诊");
                }else if("2".equals(e.getCategory())){
                    e.setCategoryName("住院");
                }

                //协议等级 "1", "一级"  "2", "二级"  "3", "三级"  "4", "A级别"   "5", "B级别"   "6", "C级别"   "9", "未定级"
                if("1".equals(e.getAggrementLv())){
                    e.setAggrementLvName("一级");
                }else if("2".equals(e.getAggrementLv())){
                    e.setAggrementLvName("二级");
                }else if("3".equals(e.getAggrementLv())){
                    e.setAggrementLvName("三级");
                }else if("4".equals(e.getAggrementLv())){
                    e.setAggrementLvName("A级");
                }else if("5".equals(e.getAggrementLv())){
                    e.setAggrementLvName("B级");
                }else if("6".equals(e.getAggrementLv())){
                    e.setAggrementLvName("C级");
                }else if("9".equals(e.getAggrementLv())){
                    e.setAggrementLvName("未定级");
                }

                // 经营性质 1:公立 2:私立
                if("1".equals(e.getNatures())){
                    e.setNaturesName("公立");
                }else if("2".equals(e.getNatures())){
                    e.setNaturesName("私立");
                }

                //精神专科 0：非精神专科  1精神专科
                if("0".equals(e.getSpirit())){
                    e.setSpiritName("非精神专科");
                }else if("1".equals(e.getSpirit())){
                    e.setSpiritName("精神专科");
                }

                //状态0填报中  1待复审 2 待计算保证金 3完成  -1  暂存
                if("-1".equals(e.getStatus())){
                    e.setStatusName("暂存");
                }else if("0".equals(e.getStatus())){
                    e.setStatusName("填报中");
                }else if("1".equals(e.getStatus())){
                    e.setStatusName("初审中");
                }
                else if("2".equals(e.getStatus())){
                    e.setStatusName("待复审");
                }else if("3".equals(e.getStatus())){
                    e.setStatusName("待计算保证金");
                }else if("4".equals(e.getStatus())){
                    e.setStatusName("完成");
                }

                //查主表保证金展示状态
//                EvalTaskManage evalTaskManage =  evalTaskManageMapper.selectById(e.getTaskManageId()) ;
//                if(null != evalTaskManage){
//                    if("0".equals(evalTaskManage.getEarnestMoneyShow())){
//                        e.setMarginStaff("******");
//                        e.setMarginResident("******");
//                        e.setMarginMedical("******");
//                        e.setRefundStaff("******");
//                        e.setRefundResident("******");
//                        e.setRefundMedical("******");
//                        e.setStaffRewards("******");
//                        e.setResidentRewards("******");
//                        e.setMedRewards("******");
//                    }
//                }

            }
        }
        // 通过工具类创建writer，默认创建xls格式
        BigExcelWriter writer = (BigExcelWriter) ExcelUtil.getBigWriter();
        writer.addHeaderAlias("taskName", "考核任务名称");
        writer.addHeaderAlias("orgTypeName", "机构类型");
        writer.addHeaderAlias("categoryName", "机构类别");
        writer.addHeaderAlias("aggrementLvName", "协议等级");
        writer.addHeaderAlias("naturesName", "经营性质");
        writer.addHeaderAlias("spiritName", "精神类医院");
        writer.addHeaderAlias("year", "考核年度");
        writer.addHeaderAlias("orgName", "机构名称");
        writer.addHeaderAlias("orgCode", "机构编码");
        writer.addHeaderAlias("statusName", "审核状态");
        writer.addHeaderAlias("expirationTime", "过期时间");
        writer.addHeaderAlias("score", "得分");
        writer.addHeaderAlias("averageScore", "机构平均分");
        writer.addHeaderAlias("orgRate", "指数");
        writer.addHeaderAlias("totalScore", "任务总分");
        writer.addHeaderAlias("totalPercent", "百分制得分");
        writer.addHeaderAlias("averagePercent", "百分制平均分");
//        writer.addHeaderAlias("margin", "保证金金额");
        writer.addHeaderAlias("marginStaff", "保证金(职工)");
        writer.addHeaderAlias("refundStaff", "返还保证金(职工)");
        writer.addHeaderAlias("staffRewards", "奖惩金额(职工)");

        writer.addHeaderAlias("marginResident", "保证金(居民)");
        writer.addHeaderAlias("refundResident", "返还保证金(居民)");
        writer.addHeaderAlias("residentRewards", "奖惩金额(居民)");


        writer.addHeaderAlias("marginMedical", "保证金(医疗救助)");
//        writer.addHeaderAlias("refund", "保证金返还金额");
        writer.addHeaderAlias("refundMedical", "返还保证金(医疗救助)");
        writer.addHeaderAlias("medRewards", "奖惩金额(医疗救助)");


        //只导出定义字段
        writer.setOnlyAlias(true) ;
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

}
