package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.DataDistribution;
import com.jsdc.ybpt.model.DataGovernance;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class DataDistributionDao {
    public String queryPage(Page page, DataDistribution bean){
        return query(bean);
    }
    public String queryList(DataDistribution bean){
        return query(bean);
    }

    public String query(DataDistribution bean){
        String sql = "select main.* from data_distribution main";
        sql += " where main.is_del = '0'";
        if(bean!=null){
            if(StringUtils.isNotEmpty(bean.getAdmdvs())){
                sql += " and main.admdvs = '"+bean.getAdmdvs()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getFixedPointNumberPermission())){
                sql += " and main.fixedPointNumber = '"+bean.getFixedPointNumberPermission()+"'";
            }
            if(StringUtils.isNotEmpty(bean.getFixedPointNumber())){
                sql += " and main.fixedPointNumber like '%"+bean.getFixedPointNumber()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getName())){
                sql += " and main.name like '%"+bean.getName()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getAddr())){
                sql += " and main.addr like '%"+bean.getAddr()+"%'";
            }
            if(StringUtils.isNotEmpty(bean.getBatch_name())){
                sql += " and main.batch_name like '%"+bean.getBatch_name()+"%'";
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
