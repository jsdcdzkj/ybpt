package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.HilistB;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class HilistBDao {

    public String hilistBQueryAll(){
        String sql="SELECT * FROM POLCENT_DB.HILIST_B";
        return sql;
    }
    public String queryPage(Page page, HilistB hilistB){

        /*"SELECT HILIST_NAME,MED_CHRGITM_TYPE," +
                "CHRGITM_LV,LIST_TYPE,HILIST_USE_TYPE," +
                "LMT_CPND_TYPE,WUBI,PINYIN,TO_CHAR(UPDT_TIME,'yyyy-mm-dd') UPDT_TIME," +
                "CRTER_NAME,OPTER_NAME,OPTINS_NO,POOLAREA_NO FROM POLCENT_DB.HILIST_B  where 1=1";*/
        String sql="select a.HILIST_CODE,\n" +
                "a.BEGNDATE,\n" +
                "b.NAT_DIC_VAL_NAME INSU_ADMDVS,\n" +
                "a.HILIST_NAME,\n" +
                "a.ENDDATE,\n" +
                "c.NAT_DIC_VAL_NAME MED_CHRGITM_TYPE,\n" +
                "d.NAT_DIC_VAL_NAME CHRGITM_LV,\n" +
                "i.NAT_DIC_VAL_NAME LMT_USED_FLAG,\n" +
                "e.NAT_DIC_VAL_NAME LIST_TYPE,\n" +
                "j.NAT_DIC_VAL_NAME MED_USE_FLAG,\n" +
                "k.NAT_DIC_VAL_NAME MATN_USED_FLAG,\n" +
                "g.NAT_DIC_VAL_NAME HILIST_USE_TYPE,\n" +
                "h.NAT_DIC_VAL_NAME LMT_CPND_TYPE,\n" +
                "a.WUBI,\n" +
                "a.PINYIN,\n" +
                "a.MEMO,\n" +
                "a.VALI_FLAG,\n" +
                "a.RID,\n" +
                "a.UPDT_TIME,\n" +
                "a.CRTER_ID,\n" +
                "a.CRTER_NAME,\n" +
                "a.CRTE_TIME,\n" +
                "a.CRTE_OPTINS_NO,\n" +
                "a.OPTER_ID,\n" +
                "a.OPTER_NAME,\n" +
                "a.OPT_TIME,\n" +
                "a.OPTINS_NO,\n" +
                "f.NAT_DIC_VAL_NAME POOLAREA_NO\n" +
                "from POLCENT_DB.HILIST_B a\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A b on a.INSU_ADMDVS = b.NAT_DIC_VAL_CODE and b.DIC_TYPE_CODE='ADMDVS'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A c on a.MED_CHRGITM_TYPE = c.NAT_DIC_VAL_CODE and c.DIC_TYPE_CODE='MED_CHRGITM_TYPE'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A d on a.CHRGITM_LV = d.NAT_DIC_VAL_CODE and d.DIC_TYPE_CODE='CHRGITM_LV'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A e on a.LIST_TYPE = e.NAT_DIC_VAL_CODE and e.DIC_TYPE_CODE='LIST_TYPE'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A f on a.INSU_ADMDVS = f.NAT_DIC_VAL_CODE and f.DIC_TYPE_CODE='ADMDVS'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A g on a.HILIST_USE_TYPE = g.NAT_DIC_VAL_CODE and g.DIC_TYPE_CODE='HILIST_USE_TYPE'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A h on a.LMT_CPND_TYPE = h.NAT_DIC_VAL_CODE and h.DIC_TYPE_CODE='LMT_CPND_TYPE'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A i on a.LMT_USED_FLAG = i.NAT_DIC_VAL_CODE and i.DIC_TYPE_CODE='LMT_USED_FLAG'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A j on a.MED_USE_FLAG = j.NAT_DIC_VAL_CODE and j.DIC_TYPE_CODE='MED_USE_FLAG'\n" +
                "left join POLCENT_DB.NAT_DATA_DIC_A k on a.MATN_USED_FLAG = k.NAT_DIC_VAL_CODE and k.DIC_TYPE_CODE='MATN_USED_FLAG'\n" +
                "where a.VALI_FLAG = '1'\n";
                if (StringUtils.isNotEmpty(hilistB.getHilist_code())){
                    sql+=" and  HILIST_CODE='"+hilistB.getHilist_code()+"'";
                }
        return sql;
    }
}
