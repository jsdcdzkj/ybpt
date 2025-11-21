package com.jsdc.ybpt.dao.terminal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.terminal.TerminalInfo;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class TerminalInfoDao {
    public String queryPage(Page page, TerminalInfo bean){
        return query(bean);
    }
    public String queryList(TerminalInfo bean){
        return query(bean);
    }

    public String query(TerminalInfo bean){
        String sql = "select ti.* from terminal_info ti";
        sql += " where ti.is_del = '0'";
        if(bean!=null){
            if(StringUtils.isNotEmpty(bean.getMedical_code())){
                sql += " and ti.medical_code like '%"+bean.getMedical_code()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getOrg_name())){
                sql += " and ti.org_name like '%"+bean.getOrg_name()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getArea())){
                sql += " and ti.area = '"+bean.getArea()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getCred_lv())){
                sql += " and ti.cred_lv = '"+bean.getCred_lv()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getOperator())){
                sql += " and ti.operator = '"+bean.getOperator()+"'";
            }
            if(null != bean.getRoleSymbol() && !bean.getRoleSymbol().isEmpty() && bean.getRoleSymbol().contains("terminalInfo")){
                sql += " and ti.status != '0' and ti.status != '3'";
            }else{
                if(StringUtils.isNotEmpty(bean.getOrg_code())){
                    sql += " and ti.medical_code = '"+bean.getOrg_code()+"'";
                }
            }
            if(null != bean.getStatus()){
                sql += " and ti.status = '"+bean.getStatus()+"'";
            }
            if(bean.getRoleSymbol()!=null && bean.getRoleSymbol().isEmpty()){
                sql += " and ti.status != '0'";
            }
            if (StringUtils.isNotEmpty(bean.getStart_time()) && StringUtils.isNotEmpty(bean.getEnd_time())) {
                sql += " and ti.createTime >= to_date('" + bean.getStart_time() + "-01','yyyy-mm-dd') ";
                sql += " and ti.createTime <= last_day(to_date('" + bean.getEnd_time() + "-01','yyyy-mm-dd')) ";
            }

        }
        sql += " order by ti.status, ti.createTime desc";
        return sql;
    }
}
