package com.jsdc.ybpt.SchedulingTask;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.service.BizReconciliationMonthService;
import com.jsdc.ybpt.service.BizReconciliationService;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.OrgReconciliationMonthService;
import com.jsdc.ybpt.util.ReconciliationUtils;
import com.jsdc.ybpt.vo.ReconciliationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ReconciliationTask
 * @Description 对账定时任务
 * @Author xujian
 * @Date 2022/4/18 15:54
 * @Version 1.0
 */
@Component
public class ReconciliationTask {

    @Autowired
    private BizReconciliationService bizReconciliationService;
    @Autowired
    private BizReconciliationMonthService bizReconciliationMonthService;
    @Autowired
    private OrgReconciliationMonthService orgReconciliationMonthService;
    @Autowired
    private FixmedinsBService fixmedinsBService;

    /**
     * 日对账记录生成
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void genReconciliation() {
        DateTime yesterday = DateUtil.yesterday();
        String settleDate = DateUtil.formatDate(yesterday);
        List<ReconciliationType> reconciliationTypes = ReconciliationUtils.getReconciliationTypes();
        //通过机构编码获取机构名称封装
        HashMap<String,String> orgMap = new HashMap<>();
        List<FixmedinsB> fixmedinsBList = fixmedinsBService.list();
        for (FixmedinsB fixmedinsB : fixmedinsBList) {
            orgMap.put(fixmedinsB.getFixmedins_code(),fixmedinsB.getFixmedins_name());
        }
        System.out.println("************"+settleDate+"对账记录开始生成************");
        for (ReconciliationType rt : reconciliationTypes){
            //统计回流库数据
            List<BizReconciliation> reconciliations = bizReconciliationService.getReflowReconciliationData(settleDate,rt.getInsutype(),rt.getMedTypes(),rt.getPsnTypes(),null,null);
            if (reconciliations != null && reconciliations.size() > 0) {
                for (BizReconciliation r : reconciliations) {
                    //日对账结果
                    r.setId(IdUtil.simpleUUID());
                    r.setReconciliation_type(rt.getType());
                    if(orgMap.containsKey(r.getFixmedins_code())){
                        r.setFixmedins_name(orgMap.get(r.getFixmedins_code()));
                    }
                    r.setSettle_date(settleDate);
                    r.setCreateTime(DateUtil.formatDateTime(new Date()));
                    r.setIs_del("0");
                    r.insert();
                    //机构日对账信息
                    OrgReconciliation o = new OrgReconciliation();
                    o.setId(IdUtil.simpleUUID());
                    o.setInsu_admdvs(r.getInsu_admdvs());
                    o.setReconciliation_type(rt.getType());
                    o.setFixmedins_code(r.getFixmedins_code());
                    o.setFixmedins_name(r.getFixmedins_name());
                    o.setSettle_date(r.getSettle_date());
                    o.setCreateTime(DateUtil.formatDateTime(new Date()));
                    o.setCheck_status("0");
                    o.setIs_del("0");
                    o.setBizId(r.getId());
                    o.insert();
                }
            }
        }
        //异地
        List<ReconciliationType> reconciliationTypes_yd = ReconciliationUtils.getReconciliationTypes_yd();
        for (ReconciliationType rt : reconciliationTypes_yd){
            //统计回流库数据
            List<BizReconciliation> reconciliations = bizReconciliationService.getReflowReconciliationData_yd(settleDate,rt.getInsutype(),rt.getMedTypes(),rt.getPsnTypes(),null,null);
            if (reconciliations != null && reconciliations.size() > 0) {
                for (BizReconciliation r : reconciliations) {
                    //日对账结果
                    r.setId(IdUtil.simpleUUID());
                    r.setReconciliation_type(rt.getType());
                    r.setSettle_date(settleDate);
                    if(orgMap.containsKey(r.getFixmedins_code())){
                        r.setFixmedins_name(orgMap.get(r.getFixmedins_code()));
                    }
                    r.setCreateTime(DateUtil.formatDateTime(new Date()));
                    r.setIs_del("0");
                    r.insert();
                    //机构日对账信息
                    OrgReconciliation o = new OrgReconciliation();
                    o.setId(IdUtil.simpleUUID());
                    o.setInsu_admdvs(r.getInsu_admdvs());
                    o.setReconciliation_type(rt.getType());
                    o.setFixmedins_code(r.getFixmedins_code());
                    o.setFixmedins_name(r.getFixmedins_name());
                    o.setSettle_date(r.getSettle_date());
                    o.setCreateTime(DateUtil.formatDateTime(new Date()));
                    o.setCheck_status("0");
                    o.setIs_del("0");
                    o.setBizId(r.getId());
                    o.insert();
                }
            }
        }
        System.out.println("************"+settleDate+"对账记录生成结束************");
    }

    /**
     * 月对账记录生成
     */
    @Scheduled(cron = "0 0 1 1 * ?")
//    @Scheduled(cron = "0 0 19 7 * ?")
    public void genReconciliation_month() {
        DateTime dateTime = DateUtil.offsetMonth(new Date(), -1);
        String settleDate = DateUtil.format(dateTime, "YYYY-MM");
        List<ReconciliationType> reconciliationTypes = ReconciliationUtils.getReconciliationTypes();
        //通过机构编码获取机构名称封装
        HashMap<String,String> orgMap = new HashMap<>();
        List<FixmedinsB> fixmedinsBList = fixmedinsBService.list();
        for (FixmedinsB fixmedinsB : fixmedinsBList) {
            orgMap.put(fixmedinsB.getFixmedins_code(),fixmedinsB.getFixmedins_name());
        }
        System.out.println("************"+settleDate+"月对账记录开始生成************");
        for (ReconciliationType rt : reconciliationTypes){
            //统计回流库数据
            List<BizReconciliationMonth> reconciliations = bizReconciliationMonthService.getReflowReconciliationData(settleDate,rt.getInsutype(),rt.getMedTypes(),rt.getPsnTypes(),null,null);
            if (reconciliations != null && reconciliations.size() > 0) {
                for (BizReconciliationMonth r : reconciliations) {
                    //月对账结果
                    r.setId(IdUtil.simpleUUID());
                    r.setReconciliation_type(rt.getType());
                    if(orgMap.containsKey(r.getFixmedins_code())){
                        r.setFixmedins_name(orgMap.get(r.getFixmedins_code()));
                    }
                    r.setSettle_date(settleDate);
                    r.setCreateTime(DateUtil.formatDateTime(new Date()));
                    r.setIs_del("0");
                    r.insert();
                    //机构月对账信息
                    OrgReconciliationMonth o = new OrgReconciliationMonth();
                    //查询本月如果有对账成功数据，则不新增
                    QueryWrapper qw = new QueryWrapper();
                    qw.eq("fixmedins_code",r.getFixmedins_code());
                    qw.eq("reconciliation_type",rt.getType());
                    qw.eq("insu_admdvs",r.getInsu_admdvs());
                    qw.eq("check_status","1");
                    qw.eq("settle_date",r.getSettle_date());
                    OrgReconciliationMonth oo = orgReconciliationMonthService.getOne(qw);
                    if(oo == null){
                        o.setId(IdUtil.simpleUUID());
                        o.setInsu_admdvs(r.getInsu_admdvs());
                        o.setReconciliation_type(rt.getType());
                        o.setFixmedins_code(r.getFixmedins_code());
                        o.setFixmedins_name(r.getFixmedins_name());
                        o.setSettle_date(r.getSettle_date());
                        o.setCreateTime(DateUtil.formatDateTime(new Date()));
                        o.setCheck_status("0");
                        o.setIs_del("0");
                        o.setBizId(r.getId());
                        o.insert();
                    }
                }
            }
        }
        //异地月对账
        List<ReconciliationType> reconciliationTypes_yd = ReconciliationUtils.getReconciliationTypes_yd();
        System.out.println("************"+settleDate+"月对账记录开始生成************");
        for (ReconciliationType rt : reconciliationTypes_yd){
            //统计回流库数据
            List<BizReconciliationMonth> reconciliations = bizReconciliationMonthService.getReflowReconciliationData_yd(settleDate,rt.getInsutype(),rt.getMedTypes(),rt.getPsnTypes(),null,null);
            if (reconciliations != null && reconciliations.size() > 0) {
                for (BizReconciliationMonth r : reconciliations) {
                    //日对账结果
                    r.setId(IdUtil.simpleUUID());
                    r.setReconciliation_type(rt.getType());
                    if(orgMap.containsKey(r.getFixmedins_code())){
                        r.setFixmedins_name(orgMap.get(r.getFixmedins_code()));
                    }
                    r.setSettle_date(settleDate);
                    r.setCreateTime(DateUtil.formatDateTime(new Date()));
                    r.setIs_del("0");
                    r.insert();
                    //机构月对账信息
                    OrgReconciliationMonth o = new OrgReconciliationMonth();
                    //查询本月如果有对账成功数据，则不新增
                    QueryWrapper qw = new QueryWrapper();
                    qw.eq("fixmedins_code",r.getFixmedins_code());
                    qw.eq("reconciliation_type",rt.getType());
                    qw.eq("insu_admdvs",r.getInsu_admdvs());
                    qw.eq("check_status","1");
                    qw.eq("settle_date",r.getSettle_date());
                    OrgReconciliationMonth oo = orgReconciliationMonthService.getOne(qw);
                    if(oo == null) {
                        o.setId(IdUtil.simpleUUID());
                        o.setInsu_admdvs(r.getInsu_admdvs());
                        o.setReconciliation_type(rt.getType());
                        o.setFixmedins_code(r.getFixmedins_code());
                        o.setFixmedins_name(r.getFixmedins_name());
                        o.setSettle_date(r.getSettle_date());
                        o.setCreateTime(DateUtil.formatDateTime(new Date()));
                        o.setCheck_status("0");
                        o.setIs_del("0");
                        o.setBizId(r.getId());
                        o.insert();
                    }
                }
            }
        }
        System.out.println("************"+settleDate+"月对账记录生成结束************");
    }

}
