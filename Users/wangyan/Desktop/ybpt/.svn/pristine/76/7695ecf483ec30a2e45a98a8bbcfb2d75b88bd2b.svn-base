package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.HilistselfpaypropB;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class HilistselfpaypropBDao {
    public String paypropQuery(Page page, HilistselfpaypropB hilistselfpaypropB){
        String sql="SELECT " +
                "hsb.hilist_code,\n" +
                "hsb.selfpay_prop_psn_type,\n" +
                "hsb.begndate,\n" +
                "hsb.insu_admdvs,\n" +
                "hsb.tabname,\n" +
                "hsb.selfpay_prop_type,\n" +
                "hsb.enddate,\n" +
                "hsb.selfpay_prop,\n" +
                "hsb.vali_flag,\n" +
                "hsb.rid,\n" +
                "hsb.updt_time,\n" +
                "hsb.crter_id,\n" +
                "hsb.crter_name,\n" +
                "hsb.crte_time,\n" +
                "hsb.crte_optins_no,\n" +
                "hsb.opter_id,\n" +
                "hsb.opter_name,\n" +
                "hsb.opt_time,\n" +
                "hsb.optins_no,\n" +
                "insutype.nat_dic_val_name insutype_name, " +
                "prop_type.nat_dic_val_name prop_type_name, " +
                "prop_psn_type.nat_dic_val_name prop_psn_type_name, " +
                "poolarea_no from polcent_db.hilist_selfpay_prop_b hsb ";
        sql += "left join polcent_db.nat_data_dic_a insutype " +
                "on hsb.insu_admdvs=insutype.nat_dic_val_code " +
                "and insutype.dic_type_code='ADMDVS' ";
        sql += "left join polcent_db.nat_data_dic_a prop_type " +
                "on hsb.selfpay_prop_type=prop_type.nat_dic_val_code " +
                "and prop_type.dic_type_code='SELFPAY_PROP_TYPE' ";
        sql += "left join polcent_db.nat_data_dic_a prop_psn_type " +
                "on hsb.selfpay_prop_psn_type=prop_psn_type.nat_dic_val_code " +
                "and prop_psn_type.dic_type_code='SELFPAY_PROP_PSN_TYPE' ";

        sql+=" where 1=1 ";
        if(StringUtils.isNotEmpty(hilistselfpaypropB.getHilist_code())){
                sql+="and hsb.HILIST_CODE= '"+hilistselfpaypropB.getHilist_code()+"'";
        }
        return  sql;
    }
}
