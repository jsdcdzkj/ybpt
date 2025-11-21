package com.jsdc.ybpt.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.model.OrgReconciliation;
import com.jsdc.ybpt.vo.ReconciliationApiVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class OrgReconciliationService extends BaseService<OrgReconciliation> {
    @Autowired
    private BizReconciliationService bizReconciliationService;

    public ResultInfo checkReconciliation(OrgReconciliation o) {
        //查询对账记录医保端数据
        BizReconciliation b = bizReconciliationService.getById(o.getBizId());
        //对账
        if (
                contrast(b.getMedfee_sumamt(), o.getMedfee_sumamt()) &&
                contrast(b.getHifp_pay(), o.getHifp_pay()) &&
                contrast(b.getCvlserv_pay(), o.getCvlserv_pay()) &&
                contrast(b.getHifes_pay(), o.getHifes_pay()) &&
                contrast(b.getHifmi_pay(), o.getHifmi_pay()) &&
                contrast(b.getMaf_pay(), o.getMaf_pay()) &&
                contrast(b.getOthfund_pay(), o.getOthfund_pay()) &&
                contrast(b.getAcct_pay(), o.getAcct_pay()) &&
                contrast(b.getCash_payamt(), o.getCash_payamt()) &&
                contrast(b.getHifdm_pay(), o.getHifdm_pay()) &&
                contrast(b.getAcct_mulaid_pay(), o.getAcct_mulaid_pay()) &&
                contrast(b.getPerson_num(), o.getPerson_num()) &&
                contrast(b.getPerson_count(), o.getPerson_count()) &&
                contrast(b.getOwnpay_hosp_part(), o.getOwnpay_hosp_part())
        ) {
            o.setCheck_status("1");
            o.setCheck_time(DateUtil.formatDateTime(new Date()));
            o.updateById();
            return ResultInfo.success(o);
        }
        o.setCheck_status("2");
        o.updateById();
        return ResultInfo.error("对账不通过！");
    }

    /**
     * 日对账对外接口
     * @param data
     * @return
     */
    public ResultInfo day_reconciliation(ReconciliationApiVo data){
        List<OrgReconciliation> resultList = new ArrayList<>();
        //查询日对账数据
        QueryWrapper qw = new QueryWrapper();
        qw.eq("fixmedins_code",data.getFixmedins_code());
        qw.eq("settle_date",data.getSettle_date());
        qw.eq("is_del","0");
        List<OrgReconciliation> orgReconciliations = this.list(qw);
        HashMap<String,OrgReconciliation> orgMap = new HashMap();
        for (OrgReconciliation o: orgReconciliations) {
            //对账类型+统筹区确认唯一记录
            orgMap.put(o.getReconciliation_type()+"_"+o.getInsu_admdvs(),o);
        }
        //遍历数据验证结果
        for (OrgReconciliation o: data.getDay_details()) {
            //查询是否有此对账信息
            if(orgMap.containsKey(o.getReconciliation_type()+"_"+o.getInsu_admdvs())){
                OrgReconciliation sys_o = orgMap.get(o.getReconciliation_type()+"_"+o.getInsu_admdvs());
                //如果已经对账成功，则直接返回结果
                if("1".equals(sys_o.getCheck_status())){
                    resultList.add(sys_o);
                }else{
                    BeanUtil.copyProperties(o,sys_o);
                    ResultInfo r = this.checkReconciliation(sys_o);
                    if(r.getCode() == 0){
                        OrgReconciliation o_r = (OrgReconciliation) r.getData();
                        resultList.add(o_r);
                    }else{
                        sys_o.setCheck_status("2");
                        resultList.add(sys_o);
                    }
                }
            }
        }
        return ResultInfo.success(resultList);
    }

    /**
     * 对比两个数是否相等
     *
     * @param num1
     * @param num2
     * @return
     */
    private Boolean contrast(String num1, String num2) {
        num1 = num1==null?"0":num1;
        num2 = num2==null?"0":num2;
        return new BigDecimal(num1).compareTo(new BigDecimal(num2)) == 0;
    }
}
