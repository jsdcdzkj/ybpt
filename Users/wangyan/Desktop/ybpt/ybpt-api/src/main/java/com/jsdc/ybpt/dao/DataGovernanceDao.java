package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.DataGovernance;
import com.jsdc.ybpt.terminal.TerminalInfo;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class DataGovernanceDao {
    public String queryPage(Page page, DataGovernance bean){
        return query(bean);
    }
    public String queryList(DataGovernance bean){
        return query(bean);
    }

    public String query(DataGovernance bean){
        String sql = "select main.* from data_governance main";
        sql += " where main.is_del = '0'";
        if(bean!=null){
            if(StringUtils.isNotEmpty(bean.getAdmdvs())){
                sql += " and main.admdvs = '"+bean.getAdmdvs()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getFixedPointNumberPermission())){
                sql += " and main.fixedPointNumber = '"+bean.getFixedPointNumberPermission()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getFixedPointName())){
                sql += " and main.fixedPointName like '%"+bean.getFixedPointName()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getPersonal_number())){
                sql += " and main.personal_number like '%"+bean.getPersonal_number()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getNameTable())){
                sql += " and main.nameTable like '%"+bean.getNameTable()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getFixedPointNumber())){
                sql += " and main.fixedPointNumber like '%"+bean.getFixedPointNumber()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getName())){
                sql += " and main.name like '%"+bean.getName()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getIdNumber())){
                sql += " and main.IdNumber like '%"+bean.getIdNumber()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getVisitID())){
                sql += " and main.visitID = '"+bean.getVisitID()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getSettlementID())){
                sql += " and main.SettlementID = '"+bean.getSettlementID()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getErrorCode())){
                sql += " and main.errorCode like '%"+bean.getErrorCode()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getRuleName())){
                sql += " and main.ruleName like '%"+bean.getRuleName()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getMedicalCategory())){
                sql += " and main.medicalCategory like '%"+bean.getMedicalCategory()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getUpload_no())){
                sql += " and main.upload_no = '"+bean.getUpload_no()+"'";
            }

            if (StringUtils.isNotEmpty(bean.getStart_time()) && StringUtils.isNotEmpty(bean.getEnd_time())) {
                sql += " and main.createTime >= to_date('" + bean.getStart_time() + "','yyyy-mm-dd  hh24:mi:ss') ";
                sql += " and main.createTime <= to_date('" + bean.getEnd_time() + "','yyyy-mm-dd  hh24:mi:ss') ";
            }

        }
        sql += " order by main.createTime desc";
        return sql;
    }
}
