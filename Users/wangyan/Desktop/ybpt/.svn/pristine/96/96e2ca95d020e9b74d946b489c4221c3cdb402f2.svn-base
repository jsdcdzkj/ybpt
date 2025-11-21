package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.OpspdiselistB;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class OpspdiselistBQueryDao {
    public String opspQueryAll(){
        String sql="SELECT * FROM POLCENT_DB.OPSP_DISE_LIST_B";
        return  sql;
    }
    public String opspQueryPage(Page page, OpspdiselistB opspdiselistB){
        String sql="select a.OPSP_DISE_CODE,\n" +
                "a.OPSP_DISE_MAJCLS_NAME,\n" +
                "a.OPSP_DISE_SUBD_CLSS_NAME,\n" +
                "b.NAT_DIC_VAL_NAME ADMDVS,\n" +
                "a.MEMO,\n" +
                "a.VALI_FLAG,\n" +
                "a.RID,\n" +
                "a.CRTE_TIME,\n" +
                "a.UPDT_TIME,\n" +
                "a.CRTER_ID,\n" +
                "a.CRTER_NAME,\n" +
                "a.CRTE_OPTINS_NO,\n" +
                "a.OPTER_ID,\n" +
                "a.OPTER_NAME,\n" +
                "a.OPT_TIME,\n" +
                "a.OPTINS_NO,\n" +
                "a.VER,\n" +
                "a.DISE_CONT,\n" +
                "a.VER_NAME,\n" +
                "a.TRT_GUIDE_PAGEN,\n" +
                "a.TRT_GUIDE_ELECACS,\n" +
                "a.OPSP_DISE_NAME,\n" +
                "a.OPSP_DISE_MAJCLS_CODE,\n" +
                "a.ISU_FLAG,\n" +
                "a.TRAM_DATA_ID,\n" +
                "a.EFFT_TIME,\n" +
                "a.INVD_TIME\n" +
                "from POLCENT_DB.OPSP_DISE_LIST_B a\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A b on a.ADMDVS = b.NAT_DIC_VAL_CODE and b.DIC_TYPE_CODE='ADMDVS'\n" +
                "where a.VALI_FLAG = '1'";
        if (StringUtils.isNotEmpty(opspdiselistB.getOpsp_dise_code())){
            sql+="and a.OPSP_DISE_CODE = '"+opspdiselistB.getOpsp_dise_code()+"'";
        }
        return sql;
    }
}
