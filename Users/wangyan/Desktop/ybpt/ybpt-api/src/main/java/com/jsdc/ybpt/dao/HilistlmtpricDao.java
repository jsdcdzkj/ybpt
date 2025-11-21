package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.HilistlmtpricD;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class HilistlmtpricDao {
    public String hilistlmtpricQuery(Page page, HilistlmtpricD hilistlmtpricD){
        String sql="SELECT " +
                "ld.hilist_code,\n" +
                "ld.hilist_lmtpric_type,\n" +
                "ld.begndate,\n" +
//                "ld.insu_admdvs,\n" +
                "ld.tabname,\n" +
                "ld.overlmt_dspo_way,\n" +
                "ld.enddate,\n" +
                "ld.hilist_pric_uplmt_amt,\n" +
                "ld.vali_flag,\n" +
                "ld.rid,\n" +
                "ld.updt_time,\n" +
                "ld.crter_id,\n" +
                "ld.crter_name,\n" +
                "ld.crte_time,\n" +
                "ld.crte_optins_no,\n" +
                "ld.opter_id,\n" +
                "ld.opter_name,\n" +
                "ld.opt_time,\n" +
                "ld.optins_no,\n" +
                "insutype.nat_dic_val_name insutype_name, " +
                "ld.poolarea_no FROM POLCENT_DB.HILIST_LMTPRIC_D ld ";
        sql += "left join polcent_db.nat_data_dic_a insutype " +
                "on ld.insu_admdvs=insutype.nat_dic_val_code " +
                "and insutype.dic_type_code='ADMDVS' ";
        sql+=" where 1=1 ";
        if (StringUtils.isNotEmpty(hilistlmtpricD.getHilist_code())){
            sql+="and ld.HILIST_CODE= '"+hilistlmtpricD.getHilist_code()+"'";
        }
        return sql;
    }
}
