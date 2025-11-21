package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.specialDrugFiling.SpecialDrugFiling;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialDrugFilingDao {
    public String selectSpecialDrugFiling(Page page, SpecialDrugFiling specialDrugFiling) {
        String sql = "SELECT\n" +
                "\tSAFE.CERTNO,\n" +
                "c.NAT_DIC_VAL_NAME INSUTYPE,\n" +
                "\tSAFE.PSN_NAME,\n" +
                "\tSAFE.GEND,\n" +
                "\tSAFE.TEL,\n" +
                "d.NAT_DIC_VAL_NAME DCLA_SOUC,\n" +
                "\tSAFE.INSU_ADMDVS,\n" +
                "\tSAFE.DIAG_CODE,\n" +
                "\tSAFE.DIAG_NAME,\n" +
                "\tSAFE.CRTE_TIME\n" +
                "FROM\n" +
                "\tHIBIZ_DB.SPDRUG_APPR_FIL_EVT_C safe\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON safe.INSUTYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'INSUTYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d ON safe.DCLA_SOUC = d.NAT_DIC_VAL_CODE\n" +
                "AND d.DIC_TYPE_CODE = 'DCLA_SOUC' where 1=1 ";
        if(!"".equals(specialDrugFiling.getCertno()) && null != specialDrugFiling.getCertno()){
            sql += " and safe.CERTNO = '"+specialDrugFiling.getCertno()+"'" ;
        }
        if(!"".equals(specialDrugFiling.getInsutype()) && null != specialDrugFiling.getInsutype()){
            sql += " and safe.INSUTYPE = '"+specialDrugFiling.getInsutype()+"'" ;
        }
        if(!"".equals(specialDrugFiling.getPsn_name()) && null != specialDrugFiling.getPsn_name()){
            sql += " and safe.PSN_NAME like '%"+specialDrugFiling.getPsn_name()+"%'" ;
        }
        if(!"".equals(specialDrugFiling.getTel()) && null != specialDrugFiling.getTel()){
            sql += " and safe.TEL = '"+specialDrugFiling.getTel()+"'" ;
        }
        if(!"".equals(specialDrugFiling.getDcla_souc()) && null != specialDrugFiling.getDcla_souc()){
            sql += " and safe.DCLA_SOUC = '"+specialDrugFiling.getDcla_souc()+"'" ;
        }
        if(!"".equals(specialDrugFiling.getInsu_admdvs()) && null != specialDrugFiling.getInsu_admdvs()){
            sql += " and safe.INSU_ADMDVS = '"+specialDrugFiling.getInsu_admdvs()+"'" ;
        }
        if(!"".equals(specialDrugFiling.getDiag_code()) && null != specialDrugFiling.getDiag_code()){
            sql += " and safe.DIAG_CODE = '"+specialDrugFiling.getDiag_code()+"'" ;
        }
        if(!"".equals(specialDrugFiling.getDiag_name()) && null != specialDrugFiling.getDiag_name()){
            sql += " and safe.DIAG_NAME like '%"+specialDrugFiling.getDiag_name()+"%'" ;
        }
        return sql;
    }
}
