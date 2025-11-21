package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.model_query.IncomeAndExpenditure;
import com.jsdc.ybpt.model_query.medicalOrg.BasicFix;
import com.jsdc.ybpt.model_query.personnel.OpspRegEvt;
import com.jsdc.ybpt.vo.*;
import org.springframework.stereotype.Repository;

@Repository
public class DirectoryDao {
    public String selectCatalogItem(Page page, CatalogItem catalogItem) {
        String sql = "SELECT\n" +
                "\tfld.HILIST_CODE,\n" +
                "\tfld.MED_LIST_CODG,\n" +
                "\tfld.FEE_OCUR_TIME,\n" +
                "\tfld.PRIC,\n" +
                "\tfld.CNT,\n" +
                "\tfld.DET_ITEM_FEE_SUMAMT,\n" +
                "\tfld.FIXMEDINS_CODE,\n" +
                "\tfld.FIXMEDINS_NAME,\n" +
                "\tc.NAT_DIC_VAL_NAME MED_TYPE,\n" +
                "\tfld.INSU_ADMDVS,\n" +
                "d.NAT_DIC_VAL_NAME PAY_LOC,\n" +
                "\tfld.INSCP_AMT,\n" +
                "\tsd.HIFMI_PAY,\n" +
                "\tsd.HIFOB_PAY,\n" +
                "\tsd.MAF_PAY,\n" +
                "\tsd.FUND_PAY_SUMAMT,\n" +
                "\tsd.FULAMT_OWNPAY_AMT,\n" +
                "\tsd.PSN_PAY,\n" +
                "\tsd.ACCT_PAY,\n" +
                "\tsd.CASH_PAYAMT,\n" +
                "\tfld.BILG_DEPT_NAME,\n" +
                "\tfld.BILG_DR_NAME,\n" +
                "\tfld.DOSFORM_NAME,\n" +
                "\tfld.SPEC,\n" +
                "e.NAT_DIC_VAL_NAME MED_CHRGITM_TYPE,\n" +
                "f.NAT_DIC_VAL_NAME CHRGITM_LV,\n" +
                "pib.CERTNO,\n" +
                "pib.PSN_NAME\n" +
                "FROM\n" +
                "\tSETLCENT_DB.FEE_LIST_D fld\n" +
                "LEFT JOIN SETLCENT_DB.SETL_D sd ON fld.SETL_ID = sd.SETL_ID\n" +
                "left join BASINFOCENT_DB.PSN_INFO_B pib on pib.PSN_NO = fld.PSN_NO\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON fld.MED_TYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'MED_TYPE' \n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d ON fld.PAY_LOC = d.NAT_DIC_VAL_CODE\n" +
                "AND d.DIC_TYPE_CODE = 'PAY_LOC`' \n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e ON fld.MED_CHRGITM_TYPE = e.NAT_DIC_VAL_CODE\n" +
                "AND e.DIC_TYPE_CODE = 'MED_CHRGITM_TYPE' \n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f ON fld.CHRGITM_LV = f.NAT_DIC_VAL_CODE\n" +
                "AND f.DIC_TYPE_CODE = 'CHRGITM_LV' where TO_CHAR( fld.fee_ocur_time, 'yyyy-MM-dd' ) <='" + catalogItem.getTimes()[1] + "' and TO_CHAR(fld.fee_ocur_time, 'yyyy-MM-dd' ) >= '" + catalogItem.getTimes()[0] + "'";

        if (!"".equals(catalogItem.getFixmedins_code()) && null != catalogItem.getFixmedins_code()) {
            sql += " and FLD.FIXMEDINS_CODE = '" + catalogItem.getFixmedins_code() + "'";
        }
        if (!"".equals(catalogItem.getHilist_code()) && null != catalogItem.getHilist_code()) {
            sql += " and FLD.HILIST_CODE = '" + catalogItem.getHilist_code() + "'";
        }
        if (!"".equals(catalogItem.getMed_list_codg()) && null != catalogItem.getMed_list_codg()) {
            sql += " and FLD.MED_LIST_CODG = '" + catalogItem.getMed_list_codg() + "'";
        }
        if (!"".equals(catalogItem.getMed_type()) && null != catalogItem.getMed_type()) {
            sql += " and FLD.MED_TYPE = '" + catalogItem.getMed_type() + "'";
        }
        return sql;
    }


    /**
    *2.5.2门慢、门特审批信息查询
    * Author wzn
    * Date 2022/8/1 14:09
    */
    public String queryOpsp(Page page, OpspRegEvt opspRegEvt){
        String sql ="SELECT\n" +
                "\tore.POOLAREA_NO,\n" +
                "c.NAT_DIC_VAL_NAME INSUTYPE,\n" +
                "i.NAT_DIC_VAL_NAME DISE_TYPE_CODE,\n" +
                "d.NAT_DIC_VAL_NAME RCHK_FLAG,\n" +
                "\tore.PSN_NAME,\n" +
                "e.NAT_DIC_VAL_NAME PSN_CERT_TYPE,\n" +
                "\tore.CERTNO,\n" +
                "\tore.OPSP_DISE_NAME,\n" +
                "\tore.BEGNDATE,\n" +
                "\tore.ENDDATE,\n" +
                "\tore.VALI_FLAG,\n" +
                "\tore.IDE_FIXMEDINS_NO,\n" +
                "\tore.IDE_FIXMEDINS_NAME,\n" +
                "\tore.DIAG_DR_CODE,\n" +
                "\tore.DIAG_DR_NAME,\n" +
                "\tore.APPY_DATE,\n" +
                "\tore.APPY_REA,\n" +
                "\tore.HOSP_IDE_DATE,\n" +
                "\tore.TEL,\n" +
                "\tore.EMP_NO,\n" +
                "\tore.OPTER_NAME,\n" +
                "\tore.OPT_TIME,\n" +
                "f.NAT_DIC_VAL_NAME DCLA_SOUC,\n" +
                "g.NAT_DIC_VAL_NAME SURV_STAS,\n" +
                "h.NAT_DIC_VAL_NAME RETR_TYPE,\n" +
                "\tore.EMP_NAME,\n" +
                "\tore.MEMO\n" +
                "FROM\n" +
                "\tHIBIZ_DB.OPSP_REG_EVT_C ore\n" +
                "LEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib on pib.PSN_NO = ore.PSN_NO\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON ore.INSUTYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'INSUTYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d ON ore.RCHK_FLAG = d.NAT_DIC_VAL_CODE\n" +
                "AND d.DIC_TYPE_CODE = 'RCHK_FLAG'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e ON ore.PSN_CERT_TYPE = e.NAT_DIC_VAL_CODE\n" +
                "AND e.DIC_TYPE_CODE = 'PSN_CERT_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f ON ore.DCLA_SOUC = f.NAT_DIC_VAL_CODE\n" +
                "AND f.DIC_TYPE_CODE = 'DCLA_SOUC'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g ON pib.SURV_STAS = g.NAT_DIC_VAL_CODE\n" +
                "AND g.DIC_TYPE_CODE = 'SURV_STAS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h ON pib.RETR_TYPE = h.NAT_DIC_VAL_CODE\n" +
                "AND h.DIC_TYPE_CODE = 'RETR_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A i ON ore.DISE_TYPE_CODE = i.NAT_DIC_VAL_CODE\n" +
                "AND i.DIC_TYPE_CODE = 'DISE_TYPE_CODE' where 1=1 " ;
        if (!"".equals(opspRegEvt.getOpsp_dise_name()) && null != opspRegEvt.getOpsp_dise_name()) {
            sql += " and ore.OPSP_DISE_NAME like '%" + opspRegEvt.getOpsp_dise_name() + "%'";
        }
        if (!"".equals(opspRegEvt.getRchk_flag()) && null != opspRegEvt.getRchk_flag()) {
            sql += " and ore.RCHK_FLAG = '" + opspRegEvt.getRchk_flag() + "'";
        }
        if (!"".equals(opspRegEvt.getVali_flag()) && null != opspRegEvt.getVali_flag()) {
            sql += " and ore.VALI_FLAG = '" + opspRegEvt.getVali_flag() + "'";
        }
        if (!"".equals(opspRegEvt.getPoolarea_no()) && null != opspRegEvt.getPoolarea_no()) {
            sql += " and ore.POOLAREA_NO = '" + opspRegEvt.getPoolarea_no() + "'";
        }
        if (!"".equals(opspRegEvt.getInsutype()) && null != opspRegEvt.getInsutype()) {
            sql += " and ore.INSUTYPE = '" + opspRegEvt.getInsutype() + "'";
        }
        if (!"".equals(opspRegEvt.getDise_type_code()) && null != opspRegEvt.getDise_type_code()) {
            sql += " and ore.DISE_TYPE_CODE = '" + opspRegEvt.getDise_type_code() + "'";
        }
        if (!"".equals(opspRegEvt.getRetr_type()) && null != opspRegEvt.getRetr_type()) {
            sql += " and pib.RETR_TYPE = '" + opspRegEvt.getRetr_type() + "'";
        }
        if (!"".equals(opspRegEvt.getIde_fixmedins_no()) && null != opspRegEvt.getIde_fixmedins_no()) {
            sql += " and ore.IDE_FIXMEDINS_NO = '" + opspRegEvt.getIde_fixmedins_no() + "'";
        }
        if (!"".equals(opspRegEvt.getIde_fixmedins_name()) && null != opspRegEvt.getIde_fixmedins_name()) {
            sql += " and ore.IDE_FIXMEDINS_NAME like '%" + opspRegEvt.getIde_fixmedins_name() + "%'";
        }
        if (!"".equals(opspRegEvt.getDiag_dr_code()) && null != opspRegEvt.getDiag_dr_code()) {
            sql += " and ore.diag_dr_code = '" + opspRegEvt.getDiag_dr_code() + "'";
        }
        if (!"".equals(opspRegEvt.getDiag_dr_name()) && null != opspRegEvt.getDiag_dr_name()) {
            sql += " and ore.diag_dr_name like '%" + opspRegEvt.getDiag_dr_name() + "%'";
        }
        if (!"".equals(opspRegEvt.getBegndate()) && null != opspRegEvt.getBegndate()) {
            sql += " AND TO_CHAR( ore.BEGNDATE, 'yyyy-MM-dd' ) >= '" + opspRegEvt.getBegndate() + "' ";
        }
        if (!"".equals(opspRegEvt.getEnddate()) && null != opspRegEvt.getEnddate()) {
            sql += " AND TO_CHAR( ore.ENDDATE, 'yyyy-MM-dd' ) <= '" + opspRegEvt.getEnddate() + "' ";
        }
        if (!"".equals(opspRegEvt.getSurv_stas()) && null != opspRegEvt.getSurv_stas()) {
            sql += " and pib.SURV_STAS = '" + opspRegEvt.getSurv_stas() + "'";
        }
        if (!"".equals(opspRegEvt.getDcla_souc()) && null != opspRegEvt.getDcla_souc()) {
            sql += " and ore.DCLA_SOUC = '" + opspRegEvt.getDcla_souc() + "'";
        }
        return sql ;
    }
    
    
    
    /**
    *
    * Author wzn
    * Date 2022/8/3 9:24
    */
    public String basicMedicalInfo(Page page, BasicFix basicFix){
        String sql="SELECT\n" +
                "\tFIB.FIXMEDINS_CODE,\n" +
                "\tFIB.FIXMEDINS_NAME,\n" +
                "c.NAT_DIC_VAL_NAME FIXMEDINS_TYPE,\n" +
                "e.NAT_DIC_VAL_NAME POOLAREA_NO,\n" +
                "mib.MEDINS_ABBR,\n" +
                "mib.ADDR yyaddr,\n" +
                "rpb.RTAL_PHAC_ABBR,\n" +
                "rpb.ADDR ydaddr,\n" +
                "d.NAT_DIC_VAL_NAME FIX_BLNG_ADMDVS,\n" +
                "f.NAT_DIC_VAL_NAME HOSP_LV,\n" +
                "g.NAT_DIC_VAL_NAME LMTPRIC_HOSP_LV,\n" +
                "h.NAT_DIC_VAL_NAME DEDC_HOSP_LV,\n" +
                "FIB.NAT_PLAF_CODE,\n" +
                "i.NAT_DIC_VAL_NAME OUT_FIXMEDINS_TYPE,\n" +
                "k.NAT_DIC_VAL_NAME OUT_ONLN_OPEN_TYPE,\n" +
                "l.NAT_DIC_VAL_NAME FIX_ONLN_OPEN_FLAG,\n" +
                "FIB.HI_RESPER_NAME,\n" +
                "j.NAT_DIC_VAL_NAME HI_RESPER_CERT_TYPE,\n" +
                "FIB.HI_RESPER_CERTNO,\n" +
                "FIB.HI_RESPER_TEL,\n" +
                "FIB.BEGNTIME,\n" +
                "FIB.PROV_PLAF_CODE,\n" +
                "FIB.ENDTIME\n" +
                "FROM\n" +
                "\tpolcent_db.FIXMEDINS_B fib\n" +
                "left join CUSTCENT_DB.MEDINS_INFO_B mib on mib.MEDINS_CODE = fib.FIXMEDINS_CODE\n" +
                "left join CUSTCENT_DB.RTAL_PHAC_B rpb on rpb.RTAL_PHAC_CODE= fib.FIXMEDINS_CODE\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON fib.FIXMEDINS_TYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'FIXMEDINS_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d ON fib.FIX_BLNG_ADMDVS = d.NAT_DIC_VAL_CODE\n" +
                "AND d.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e ON fib.POOLAREA_NO = e.NAT_DIC_VAL_CODE\n" +
                "AND e.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f ON fib.HOSP_LV = f.NAT_DIC_VAL_CODE\n" +
                "AND f.DIC_TYPE_CODE = 'HOSP_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g ON fib.LMTPRIC_HOSP_LV = g.NAT_DIC_VAL_CODE\n" +
                "AND g.DIC_TYPE_CODE = 'LMTPRIC_HOSP_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h ON fib.DEDC_HOSP_LV = h.NAT_DIC_VAL_CODE\n" +
                "AND h.DIC_TYPE_CODE = 'DEDC_HOSP_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A i ON fib.OUT_FIXMEDINS_TYPE = i.NAT_DIC_VAL_CODE\n" +
                "AND i.DIC_TYPE_CODE = 'OUT_FIXMEDINS_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A j ON fib.HI_RESPER_CERT_TYPE = j.NAT_DIC_VAL_CODE\n" +
                "AND j.DIC_TYPE_CODE = 'HI_RESPER_CERT_TYPE'\n" +
        "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A k ON fib.OUT_ONLN_OPEN_TYPE = k.NAT_DIC_VAL_CODE\n" +
                "AND k.DIC_TYPE_CODE = 'OUT_ONLN_OPEN_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A l ON fib.FIX_ONLN_OPEN_FLAG = l.NAT_DIC_VAL_CODE\n" +
                "AND l.DIC_TYPE_CODE = 'FIX_ONLN_OPEN_FLAG'\n where fib.VALI_FLAG = '1' " ;
        if(!"".equals(basicFix.getFixmedins_code()) && null != basicFix.getFixmedins_code()){
            sql += " and FIB.FIXMEDINS_CODE = '"+basicFix.getFixmedins_code()+"'" ;
        }
        if(!"".equals(basicFix.getFixmedins_name()) && null != basicFix.getFixmedins_name()){
            sql += " and FIB.FIXMEDINS_NAME like '%"+basicFix.getFixmedins_name()+"%'" ;
        }
        if(!"".equals(basicFix.getFixmedins_type()) && null != basicFix.getFixmedins_type()){
            sql += " and FIB.fixmedins_type = '"+basicFix.getFixmedins_type()+"'" ;
        }
        if(!"".equals(basicFix.getPoolarea_no()) && null != basicFix.getPoolarea_no()){
            sql += " and FIB.poolarea_no = '"+basicFix.getPoolarea_no()+"'" ;
        }
        if(!"".equals(basicFix.getLmtpric_hosp_lv()) && null != basicFix.getLmtpric_hosp_lv()){
            sql += " and FIB.lmtpric_hosp_lv = '"+basicFix.getLmtpric_hosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getHosp_lv()) && null != basicFix.getHosp_lv()){
            sql += " and FIB.hosp_lv = '"+basicFix.getHosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getLmtpric_hosp_lv()) && null != basicFix.getLmtpric_hosp_lv()){
            sql += " and FIB.lmtpric_hosp_lv = '"+basicFix.getLmtpric_hosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getDedc_hosp_lv()) && null != basicFix.getDedc_hosp_lv()){
            sql += " and FIB.dedc_hosp_lv = '"+basicFix.getDedc_hosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getFix_onln_open_flag()) && null != basicFix.getFix_onln_open_flag()){
            sql += " and FIB.fix_onln_open_flag = '"+basicFix.getFix_onln_open_flag()+"'" ;
        }

        return sql ;

    }



    /**
    *2.5.14个人账户收支情况查询
    * Author wzn
    * Date \ 15:15
    */
    public String basicMedicalInfoExport(BasicFix basicFix){
        String sql="SELECT\n" +
                "\tFIB.FIXMEDINS_CODE,\n" +
                "\tFIB.FIXMEDINS_NAME,\n" +
                "c.NAT_DIC_VAL_NAME FIXMEDINS_TYPE,\n" +
                "e.NAT_DIC_VAL_NAME POOLAREA_NO,\n" +
                "mib.MEDINS_ABBR,\n" +
                "mib.ADDR yyaddr,\n" +
                "rpb.RTAL_PHAC_ABBR,\n" +
                "rpb.ADDR ydaddr,\n" +
                "d.NAT_DIC_VAL_NAME FIX_BLNG_ADMDVS,\n" +
                "f.NAT_DIC_VAL_NAME HOSP_LV,\n" +
                "g.NAT_DIC_VAL_NAME LMTPRIC_HOSP_LV,\n" +
                "h.NAT_DIC_VAL_NAME DEDC_HOSP_LV,\n" +
                "FIB.NAT_PLAF_CODE,\n" +
                "i.NAT_DIC_VAL_NAME OUT_FIXMEDINS_TYPE,\n" +
                "k.NAT_DIC_VAL_NAME OUT_ONLN_OPEN_TYPE,\n" +
                "l.NAT_DIC_VAL_NAME FIX_ONLN_OPEN_FLAG,\n" +
                "FIB.HI_RESPER_NAME,\n" +
                "j.NAT_DIC_VAL_NAME HI_RESPER_CERT_TYPE,\n" +
                "FIB.HI_RESPER_CERTNO,\n" +
                "FIB.HI_RESPER_TEL,\n" +
                "FIB.BEGNTIME,\n" +
                "FIB.PROV_PLAF_CODE,\n" +
                "FIB.ENDTIME\n" +
                "FROM\n" +
                "\tpolcent_db.FIXMEDINS_B fib\n" +
                "left join CUSTCENT_DB.MEDINS_INFO_B mib on mib.MEDINS_CODE = fib.FIXMEDINS_CODE\n" +
                "left join CUSTCENT_DB.RTAL_PHAC_B rpb on rpb.RTAL_PHAC_CODE= fib.FIXMEDINS_CODE\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON fib.FIXMEDINS_TYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'FIXMEDINS_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d ON fib.FIX_BLNG_ADMDVS = d.NAT_DIC_VAL_CODE\n" +
                "AND d.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e ON fib.POOLAREA_NO = e.NAT_DIC_VAL_CODE\n" +
                "AND e.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f ON fib.HOSP_LV = f.NAT_DIC_VAL_CODE\n" +
                "AND f.DIC_TYPE_CODE = 'HOSP_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g ON fib.LMTPRIC_HOSP_LV = g.NAT_DIC_VAL_CODE\n" +
                "AND g.DIC_TYPE_CODE = 'LMTPRIC_HOSP_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h ON fib.DEDC_HOSP_LV = h.NAT_DIC_VAL_CODE\n" +
                "AND h.DIC_TYPE_CODE = 'DEDC_HOSP_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A i ON fib.OUT_FIXMEDINS_TYPE = i.NAT_DIC_VAL_CODE\n" +
                "AND i.DIC_TYPE_CODE = 'OUT_FIXMEDINS_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A j ON fib.HI_RESPER_CERT_TYPE = j.NAT_DIC_VAL_CODE\n" +
                "AND j.DIC_TYPE_CODE = 'HI_RESPER_CERT_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A k ON fib.OUT_ONLN_OPEN_TYPE = k.NAT_DIC_VAL_CODE\n" +
                "AND k.DIC_TYPE_CODE = 'OUT_ONLN_OPEN_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A l ON fib.FIX_ONLN_OPEN_FLAG = l.NAT_DIC_VAL_CODE\n" +
                "AND l.DIC_TYPE_CODE = 'FIX_ONLN_OPEN_FLAG'\n where fib.VALI_FLAG = '1' " ;
        if(!"".equals(basicFix.getFixmedins_code()) && null != basicFix.getFixmedins_code()){
            sql += " and FIB.FIXMEDINS_CODE = '"+basicFix.getFixmedins_code()+"'" ;
        }
        if(!"".equals(basicFix.getFixmedins_name()) && null != basicFix.getFixmedins_name()){
            sql += " and FIB.FIXMEDINS_NAME like '%"+basicFix.getFixmedins_name()+"%'" ;
        }
        if(!"".equals(basicFix.getFixmedins_type()) && null != basicFix.getFixmedins_type()){
            sql += " and FIB.fixmedins_type = '"+basicFix.getFixmedins_type()+"'" ;
        }
        if(!"".equals(basicFix.getPoolarea_no()) && null != basicFix.getPoolarea_no()){
            sql += " and FIB.poolarea_no = '"+basicFix.getPoolarea_no()+"'" ;
        }
        if(!"".equals(basicFix.getLmtpric_hosp_lv()) && null != basicFix.getLmtpric_hosp_lv()){
            sql += " and FIB.lmtpric_hosp_lv = '"+basicFix.getLmtpric_hosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getHosp_lv()) && null != basicFix.getHosp_lv()){
            sql += " and FIB.hosp_lv = '"+basicFix.getHosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getLmtpric_hosp_lv()) && null != basicFix.getLmtpric_hosp_lv()){
            sql += " and FIB.lmtpric_hosp_lv = '"+basicFix.getLmtpric_hosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getDedc_hosp_lv()) && null != basicFix.getDedc_hosp_lv()){
            sql += " and FIB.dedc_hosp_lv = '"+basicFix.getDedc_hosp_lv()+"'" ;
        }
        if(!"".equals(basicFix.getFix_onln_open_flag()) && null != basicFix.getFix_onln_open_flag()){
            sql += " and FIB.fix_onln_open_flag = '"+basicFix.getFix_onln_open_flag()+"'" ;
        }

        return sql ;

    }


    public String personalIncomeAndExpenditure(Page page, IncomeAndExpenditure incomeAndExpenditure){
        String sql ="SELECT\n" +
                "\tpib.PSN_NAME,\n" +
                "\tpib.CERTNO,\n" +
                "\taid.INSU_ADMDVS,\n" +
                "\taid.YEAR,\n" +
                "\taid.INC_SUMAMT,\n" +
                "aid.CRTE_TIME,\n" +
                "aid.OPTINS_NO,\n" +
                "c.NAT_DIC_VAL_NAME ACCT_INCEXPD_TYPE\n" +
                "FROM\n" +
                "\tCLCTCENT_DB.ACCT_INC_DETL_D aid\n" +
                "LEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = aid.PSN_NO\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON aid.ACCT_INCEXPD_TYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'ACCT_INCEXPD_TYPE'\n" +
                "WHERE\n" +
                "\taid.VALI_FLAG = '1' and pib.VALI_FLAG = '1' \n" ;
        if(!"".equals(incomeAndExpenditure.getCertno()) && null != incomeAndExpenditure.getCertno()) {
            sql += " AND pib.CERTNO = '" + incomeAndExpenditure.getCertno() + "'";
        }
        if(!"".equals(incomeAndExpenditure.getYear()) && null != incomeAndExpenditure.getYear()){
            sql += " AND aid.YEAR = '"+incomeAndExpenditure.getYear()+"'" ;
        }
        sql +=
                "UNION ALL\n" +
                "\tSELECT\n" +
                "\t\tpib.PSN_NAME PSN_NAME2,\n" +
                "\t\tpib.CERTNO CERTNO2,\n" +
                "\t\tapd.INSU_ADMDVS INSU_ADMDVS2,\n" +
                "\t\tapd.YEAR year2,\n" +
                "\t\tapd.PAY_SUMAMT,\n" +
                "\t\tapd.CRTE_TIME,\n" +
                "\t\tapd.OPTINS_NO,\n" +
                "c.NAT_DIC_VAL_NAME ACCT_INCEXPD_TYPE\n" +
                "\tFROM\n" +
                "\t\tSETLCENT_DB.ACCT_PAY_DETL_D apd\n" +
                "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = apd.PSN_NO \n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON apd.ACCT_INCEXPD_TYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'ACCT_INCEXPD_TYPE'\n" +
                "where\n" +
                "apd.VALI_FLAG = '1' and pib.VALI_FLAG = '1'\n";
        if(!"".equals(incomeAndExpenditure.getCertno()) && null != incomeAndExpenditure.getCertno()) {
            sql += " AND pib.CERTNO = '" + incomeAndExpenditure.getCertno() + "'";
        }
        if(!"".equals(incomeAndExpenditure.getYear()) && null != incomeAndExpenditure.getYear()){
            sql += " AND apd.YEAR = '"+incomeAndExpenditure.getYear()+"'" ;
        }
        return sql ;
    }


    public String personalIncomeAndExpenditureExport(IncomeAndExpenditure incomeAndExpenditure){
        String sql ="SELECT\n" +
                "\tpib.PSN_NAME,\n" +
                "\tpib.CERTNO,\n" +
                "\taid.INSU_ADMDVS,\n" +
                "\taid.YEAR,\n" +
                "\taid.INC_SUMAMT,\n" +
                "aid.CRTE_TIME,\n" +
                "aid.OPTINS_NO,\n" +
                "c.NAT_DIC_VAL_NAME ACCT_INCEXPD_TYPE\n" +
                "FROM\n" +
                "\tCLCTCENT_DB.ACCT_INC_DETL_D aid\n" +
                "LEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = aid.PSN_NO\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON aid.ACCT_INCEXPD_TYPE = c.NAT_DIC_VAL_CODE\n" +
                "AND c.DIC_TYPE_CODE = 'ACCT_INCEXPD_TYPE'\n" +
                "WHERE\n" +
                "\taid.VALI_FLAG = '1' and pib.VALI_FLAG = '1' \n" ;
        if(!"".equals(incomeAndExpenditure.getCertno()) && null != incomeAndExpenditure.getCertno()) {
            sql += " AND pib.CERTNO = '" + incomeAndExpenditure.getCertno() + "'";
        }
        if(!"".equals(incomeAndExpenditure.getYear()) && null != incomeAndExpenditure.getYear()){
            sql += " AND aid.YEAR = '"+incomeAndExpenditure.getYear()+"'" ;
        }
        sql +=
                "UNION ALL\n" +
                        "\tSELECT\n" +
                        "\t\tpib.PSN_NAME PSN_NAME2,\n" +
                        "\t\tpib.CERTNO CERTNO2,\n" +
                        "\t\tapd.INSU_ADMDVS INSU_ADMDVS2,\n" +
                        "\t\tapd.YEAR year2,\n" +
                        "\t\tapd.PAY_SUMAMT,\n" +
                        "\t\tapd.CRTE_TIME,\n" +
                        "\t\tapd.OPTINS_NO,\n" +
                        "c.NAT_DIC_VAL_NAME ACCT_INCEXPD_TYPE\n" +
                        "\tFROM\n" +
                        "\t\tSETLCENT_DB.ACCT_PAY_DETL_D apd\n" +
                        "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = apd.PSN_NO \n" +
                        "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON apd.ACCT_INCEXPD_TYPE = c.NAT_DIC_VAL_CODE\n" +
                        "AND c.DIC_TYPE_CODE = 'ACCT_INCEXPD_TYPE'\n" +
                        "where\n" +
                        "apd.VALI_FLAG = '1' and pib.VALI_FLAG = '1'\n";
        if(!"".equals(incomeAndExpenditure.getCertno()) && null != incomeAndExpenditure.getCertno()) {
            sql += " AND pib.CERTNO = '" + incomeAndExpenditure.getCertno() + "'";
        }
        if(!"".equals(incomeAndExpenditure.getYear()) && null != incomeAndExpenditure.getYear()){
            sql += " AND apd.YEAR = '"+incomeAndExpenditure.getYear()+"'" ;
        }
        return sql ;
    }

    /**
    *2.1零报数据查询列表接口
    * Author wzn
    * Date 2022/8/17 10:12
    */
    public String zeroReportData(Page page, ZeroReport zeroReport){
        String sql = "" ;
        return sql ;
    }

    /**
    *零报数据查询列表导出接口
    * Author wzn
    * Date 2022/8/17 10:13
    */
    public String zeroReportDataExport(ZeroReport zeroReport){
        String sql = "" ;
        return sql ;
    }
    
    
    /**
    *2.1.2零报待复核数据查询
    * Author wzn
    * Date 2022/8/17 14:29
    */
    public String zeroReportViewData(Page page, ZeroReportReview zeroReportReview){
        String sql = "" ;
        return sql ;
    }

    /**
    *2.1.2零报待复核数据导出
    * Author wzn
    * Date 2022/8/17 14:30
    */
    public String zeroReportDataViewExport(ZeroReport zeroReport){
        String sql = "" ;
        return sql ;
    }


    /**
    *2.2.2用药范围查询
    * Author wzn
    * Date 2022/8/19 10:01
    */
    public String scopeOfMedicationData(Page page, ScopeOfMedication scopeOfMedication){
        String sql = "" ;
        return sql ;
    }

    /**
    *用药范围查询导出接口
    * Author wzn
    * Date 2022/8/19 10:13
    */
    public String scopeOfMedicationDataExport(ScopeOfMedication scopeOfMedication){
        String sql = "" ;
        return sql ;
    }
    
    
    /**
    *2.5.7零星报销受理情况查询
    * Author wzn
    * Date 2022/8/25 9:43
    */
    public String sporadicReimbursementData(Page page, SporadicReimbursement sporadicReimbursement){
        String sql = "" ;
        return sql ;
    }


    /**
     *2.5.7零星报销受理情况查询导出
     * Author wzn
     * Date 2022/8/25 9:43
     */
    public String sporadicReimbursementDataExport(ScopeOfMedication scopeOfMedication){
        String sql = "" ;
        return sql ;
    }


    /**
    *2.5.8生育医疗费受理情况查询
    * Author wzn
    * Date 2022/8/25 11:26
    */
    public String maternityMedicalExpensesData(Page page, MaternityMedicalExpenses maternityMedicalExpenses){
        String sql = "" ;
        return sql ;
    }


    /**
    *生育医疗费受理情况查询导出
    * Author wzn
    * Date 2022/8/25 11:26
    */
    public String maternityMedicalExpensesDataExport(MaternityMedicalExpenses maternityMedicalExpenses){
        String sql = "" ;
        return sql ;
    }


    /**
    *2.5.9生育津贴受理情况查询
    * Author wzn
    * Date 2022/8/26 14:58
    */
    public String maternityBenefitsData(Page page, MaternityBenefits maternityBenefits){
        String sql = "" ;
        return sql ;
    }


    /**
    *生育津贴受理情况查询导出接口
    * Author wzn
    * Date 2022/8/26 14:59
    */
    public String maternityBenefitsDataExport(MaternityBenefits maternityBenefits){
        String sql = "" ;
        return sql ;
    }


    /**
     * 2.5.10零星报销结算进度查询
     * @param page
     * @param
     * @return
     */
    public String reimbursementSettlementData(Page page,ReimbursementSettlement reimbursementSettlement){
        String sql = "" ;
        return sql ;
    }


    /**
     * 2.5.10零星报销结算进度查询导出接口
     * @param reimbursementSettlement
     * @return
     */
    public String reimbursementSettlementDataExport(ReimbursementSettlement reimbursementSettlement){
        String sql = "" ;
        return sql ;
    }


    /**
     * 2.5.11生育待遇结算进度查询
     * @param page
     * @param
     * @return
     */
    public String fertilitySettlementProgressData(Page page,FertilitySettlementProgress fertilitySettlementProgress){
        String sql = "" ;
        return sql ;
    }


    /**
     * 生育待遇结算进度查询导出接口
     * @param
     * @return
     */
    public String fertilitySettlementProgressDataExport(FertilitySettlementProgress fertilitySettlementProgress){
        String sql = "" ;
        return sql ;
    }


    /**
     * 2.5.4异地就医备案情况查询
     * @param page
     * @param
     * @return
     */
    public String offsiteFilingData(Page page,OffsiteFiling offsiteFiling){
        String sql = "" ;
        return sql ;
    }


    /**
     * 异地就医备案情况查询导出
     * @param offsiteFiling
     * @return
     */
    public String offsiteFilingDataExport(OffsiteFiling offsiteFiling){
        String sql = "" ;
        return sql ;
    }


    /**
    *药品目录信息查询
    * Author wzn
    * Date 2022/9/5 14:36
    */
    public String medicineInfoData(Page page, MedicineInfo medicineInfo){
        String sql = "SELECT\n" +
                "\twt.MED_LIST_CODG,\n" +
                "\twt.DRUG_PRODNAME,\n" +
                "\twt.DRUG_GENNAME,\n" +
                "\twt.DRUG_DOSFORM,\n" +
                "\twt.DRUG_SPEC,\n" +
                "\twt.PACSPEC,\n" +
                "\twt.BEGNDATE,\n" +
                "\twt.ENDDATE,\n" +
                "\twt.PRODENTP_NAME,\n" +
                "\twt.LMT_USESCP\n" +
                "FROM\n" +
                "\tPOLCENT_DB.WM_TCMPAT_INFO_B wt\n" +
                "where WT.VALI_FLAG = '1'" ;
        if(!"".equals(medicineInfo.getDrug_genname()) && null != medicineInfo.getDrug_genname()){
            sql += " and wt.DRUG_GENNAME like '%"+medicineInfo.getDrug_genname()+"%'" ;
        }
        if(!"".equals(medicineInfo.getMed_list_codg()) && null != medicineInfo.getMed_list_codg()){
            sql += " and wt.MED_LIST_CODG = '"+medicineInfo.getMed_list_codg()+"'" ;
        }
        if(!"".equals(medicineInfo.getDrug_prodname()) && null != medicineInfo.getDrug_prodname()){
            sql += " and wt.DRUG_PRODNAME like '%"+medicineInfo.getDrug_prodname()+"%'" ;
        }
        if (!"".equals(medicineInfo.getBegndate()) && null != medicineInfo.getBegndate()) {
            sql += " AND TO_CHAR( wt.BEGNDATE, 'yyyy-MM-dd' ) >= '" + medicineInfo.getBegndate() + "' ";
        }
        if (!"".equals(medicineInfo.getEnddate()) && null != medicineInfo.getEnddate()) {
            sql += " AND TO_CHAR( wt.ENDDATE, 'yyyy-MM-dd' ) <= '" + medicineInfo.getEnddate() + "' ";
        }
        return sql ;
    }


    /**
    *药品目录信息查询导出
    * Author wzn
    * Date 2022/9/5 14:36
    */
    public String medicineInfoDataExport(MedicineInfo medicineInfo){
        String sql = "SELECT\n" +
                "\twt.MED_LIST_CODG,\n" +
                "\twt.DRUG_PRODNAME,\n" +
                "\twt.DRUG_GENNAME,\n" +
                "\twt.DRUG_DOSFORM,\n" +
                "\twt.DRUG_SPEC,\n" +
                "\twt.PACSPEC,\n" +
                "\twt.BEGNDATE,\n" +
                "\twt.ENDDATE,\n" +
                "\twt.PRODENTP_NAME,\n" +
                "\twt.LMT_USESCP\n" +
                "FROM\n" +
                "\tPOLCENT_DB.WM_TCMPAT_INFO_B wt\n" +
                "where WT.VALI_FLAG = '1'" ;
        if(!"".equals(medicineInfo.getDrug_genname()) && null != medicineInfo.getDrug_genname()){
            sql += " and wt.DRUG_GENNAME like '%"+medicineInfo.getDrug_genname()+"%'" ;
        }
        if(!"".equals(medicineInfo.getMed_list_codg()) && null != medicineInfo.getMed_list_codg()){
            sql += " and wt.MED_LIST_CODG = '"+medicineInfo.getMed_list_codg()+"'" ;
        }
        if(!"".equals(medicineInfo.getDrug_prodname()) && null != medicineInfo.getDrug_prodname()){
            sql += " and wt.DRUG_PRODNAME like '%"+medicineInfo.getDrug_prodname()+"%'" ;
        }
        if (!"".equals(medicineInfo.getBegndate()) && null != medicineInfo.getBegndate()) {
            sql += " AND TO_CHAR( wt.BEGNDATE, 'yyyy-MM-dd' ) >= '" + medicineInfo.getBegndate() + "' ";
        }
        if (!"".equals(medicineInfo.getEnddate()) && null != medicineInfo.getEnddate()) {
            sql += " AND TO_CHAR( wt.ENDDATE, 'yyyy-MM-dd' ) <= '" + medicineInfo.getEnddate() + "' ";
        }
        return sql ;
    }


    /**
     *4医疗服务项目信息查询
     * Author wzn
     * Date 2022/9/7 10:42
     */
    public String medicalServiceData(Page page, MedicalService medicalService){
        String sql = "SELECT\n" +
                "\ttsb.MED_LIST_CODG,\n" +
                "\ttsb.PRCUNT,\n" +
                "\ttsb.TRT_EXCT_CONT,\n" +
                "\ttsb.TRT_ITEM_CONT,\n" +
                "\ttsb.SERVITEM_NAME,\n" +
                "\ttsb.BEGNDATE,\n" +
                "\ttsb.ENDDATE,\n" +
                "h.NAT_DIC_VAL_NAME SELFPAY_PROP_PSN_TYPE,\n" +
                "h2.NAT_DIC_VAL_NAME HILIST_LMTPRIC_TYPE,\n" +
                "h3.NAT_DIC_VAL_NAME MED_CHRGITM_TYPE,\n" +
                "h4.NAT_DIC_VAL_NAME CHRGITM_LV,\n" +
                " hspb.SELFPAY_PROP,\n" +
                " hld.HILIST_PRIC_UPLMT_AMT "+
                "FROM\n" +
                "\tPOLCENT_DB.TRT_SERV_B tsb\n" +
                "left join POLCENT_DB.HILIST_SELFPAY_PROP_B hspb on HSPB.HILIST_CODE = TSB.MED_LIST_CODG\n" +
                "left join POLCENT_DB.HILIST_LMTPRIC_D hld on hld.HILIST_CODE = TSB.MED_LIST_CODG\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h ON hspb.SELFPAY_PROP_PSN_TYPE = h.NAT_DIC_VAL_CODE and h.DIC_TYPE_CODE = 'SELFPAY_PROP_PSN_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h2 ON hld.HILIST_LMTPRIC_TYPE = h2.NAT_DIC_VAL_CODE and h2.DIC_TYPE_CODE = 'HILIST_LMTPRIC_TYPE'\n" +
                "left join POLCENT_DB.HILIST_B hb on hb.HILIST_CODE = TSB.MED_LIST_CODG\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h3 ON hb.MED_CHRGITM_TYPE = h3.NAT_DIC_VAL_CODE and h3.DIC_TYPE_CODE = 'MED_CHRGITM_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h4 ON hb.CHRGITM_LV = h4.NAT_DIC_VAL_CODE and h4.DIC_TYPE_CODE = 'CHRGITM_LV'\n" +
                "where TSB.VALI_FLAG = '1'" ;
        if(!"".equals(medicalService.getServitem_name()) && null != medicalService.getServitem_name()){
            sql += " and tsb.SERVITEM_NAME like '%"+medicalService.getServitem_name()+"%'" ;
        }

        if (!"".equals(medicalService.getBegndate()) && null != medicalService.getBegndate()) {
            sql += " AND TO_CHAR( tsb.BEGNDATE, 'yyyy-MM-dd' ) >= '" + medicalService.getBegndate() + "' ";
        }
        if (!"".equals(medicalService.getEnddate()) && null != medicalService.getEnddate()) {
            sql += " AND TO_CHAR( tsb.ENDDATE, 'yyyy-MM-dd' ) <= '" + medicalService.getEnddate() + "' ";
        }
        return sql ;
    }


    /**
    *医疗服务项目信息查询导出
    * Author wzn
    * Date 2022/9/7 14:15
    */
    public String medicalServiceDataExport(MedicalService medicalService){
        String sql = "SELECT\n" +
                "\ttsb.MED_LIST_CODG,\n" +
                "\ttsb.PRCUNT,\n" +
                "\ttsb.TRT_EXCT_CONT,\n" +
                "\ttsb.TRT_ITEM_CONT,\n" +
                "\ttsb.SERVITEM_NAME,\n" +
                "\ttsb.BEGNDATE,\n" +
                "\ttsb.ENDDATE,\n" +
                "h.NAT_DIC_VAL_NAME SELFPAY_PROP_PSN_TYPE,\n" +
                "h2.NAT_DIC_VAL_NAME HILIST_LMTPRIC_TYPE,\n" +
                "h3.NAT_DIC_VAL_NAME MED_CHRGITM_TYPE,\n" +
                "h4.NAT_DIC_VAL_NAME CHRGITM_LV,\n" +
                " hspb.SELFPAY_PROP,\n" +
                " hld.HILIST_PRIC_UPLMT_AMT "+
                "FROM\n" +
                "\tPOLCENT_DB.TRT_SERV_B tsb\n" +
                "left join POLCENT_DB.HILIST_SELFPAY_PROP_B hspb on HSPB.HILIST_CODE = TSB.MED_LIST_CODG\n" +
                "left join POLCENT_DB.HILIST_LMTPRIC_D hld on hld.HILIST_CODE = TSB.MED_LIST_CODG\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h ON hspb.SELFPAY_PROP_PSN_TYPE = h.NAT_DIC_VAL_CODE and h.DIC_TYPE_CODE = 'SELFPAY_PROP_PSN_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h2 ON hld.HILIST_LMTPRIC_TYPE = h2.NAT_DIC_VAL_CODE and h2.DIC_TYPE_CODE = 'HILIST_LMTPRIC_TYPE'\n" +
                "left join POLCENT_DB.HILIST_B hb on hb.HILIST_CODE = TSB.MED_LIST_CODG\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h3 ON hb.MED_CHRGITM_TYPE = h3.NAT_DIC_VAL_CODE and h3.DIC_TYPE_CODE = 'MED_CHRGITM_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h4 ON hb.CHRGITM_LV = h4.NAT_DIC_VAL_CODE and h4.DIC_TYPE_CODE = 'CHRGITM_LV'\n" +
                "where TSB.VALI_FLAG = '1'" ;
        if(!"".equals(medicalService.getServitem_name()) && null != medicalService.getServitem_name()){
            sql += " and tsb.SERVITEM_NAME like '%"+medicalService.getServitem_name()+"%'" ;
        }

        if (!"".equals(medicalService.getBegndate()) && null != medicalService.getBegndate()) {
            sql += " AND TO_CHAR( tsb.BEGNDATE, 'yyyy-MM-dd' ) >= '" + medicalService.getBegndate() + "' ";
        }
        if (!"".equals(medicalService.getEnddate()) && null != medicalService.getEnddate()) {
            sql += " AND TO_CHAR( tsb.ENDDATE, 'yyyy-MM-dd' ) <= '" + medicalService.getEnddate() + "' ";
        }
        return sql ;
    }



    /**
    *医用耗材目录信息查询
    * Author wzn
    * Date 2022/9/8 14:23
    */
    public String medicalConsumablesData(Page page, MedicalConsumables medicalConsumables){
        String sql = "SELECT\n" +
                "\t\t\tmib.MCS_NAME,\n" +
                "\t\t\tmib.MED_LIST_CODG,\n" +
                "mib.BEGNDATE,\n" +
                "\t\t\tmib.ENDDATE,\n" +
                "\t\t\th1.NAT_DIC_VAL_NAME SELFPAY_PROP_PSN_TYPE,\n" +
                "hspb.SELFPAY_PROP,\n" +
                "hld.HILIST_PRIC_UPLMT_AMT,\n" +
                "\t\t\th2.NAT_DIC_VAL_NAME HILIST_LMTPRIC_TYPE,\n" +
                "\t\t\th3.NAT_DIC_VAL_NAME MED_CHRGITM_TYPE,\n" +
                "\t\t\th4.NAT_DIC_VAL_NAME CHRGITM_LV,\n" +
                "\t\t\th5.NAT_DIC_VAL_NAME LMT_USED_FLAG\n" +
                "\t\tFROM\n" +
                "\t\t\tPOLCENT_DB.MCS_INFO_B mib\n" +
                "\t\tLEFT JOIN POLCENT_DB.HILIST_SELFPAY_PROP_B hspb ON HSPB.HILIST_CODE = mib.MED_LIST_CODG\n" +
                "\t\tLEFT JOIN POLCENT_DB.HILIST_LMTPRIC_D hld ON hld.HILIST_CODE = mib.MED_LIST_CODG\n" +
                "\t\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h1 ON hspb.SELFPAY_PROP_PSN_TYPE = h1.NAT_DIC_VAL_CODE\n" +
                "left join POLCENT_DB.HILIST_B hb on hb.HILIST_CODE = mib.MED_LIST_CODG\n" +
                "\t\tAND h1.DIC_TYPE_CODE = 'SELFPAY_PROP_PSN_TYPE'\n" +
                "\t\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h2 ON hld.HILIST_LMTPRIC_TYPE = h2.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h2.DIC_TYPE_CODE = 'HILIST_LMTPRIC_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h3 ON hb.MED_CHRGITM_TYPE = h3.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h3.DIC_TYPE_CODE = 'MED_CHRGITM_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h4 ON hb.CHRGITM_LV = h4.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h4.DIC_TYPE_CODE = 'CHRGITM_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h5 ON hb.LMT_USED_FLAG = h5.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h5.DIC_TYPE_CODE = 'LMT_USED_FLAG'\n" +
                "\n" +
                "\t\tWHERE\n" +
                "\t\t\tmib.VALI_FLAG = '1'" ;
        if(!"".equals(medicalConsumables.getMcs_name()) && null != medicalConsumables.getMcs_name()){
            sql += " and mib.MCS_NAME like '%"+medicalConsumables.getMcs_name()+"%'" ;
        }

        if (!"".equals(medicalConsumables.getBegndate()) && null != medicalConsumables.getBegndate()) {
            sql += " AND TO_CHAR( mib.BEGNDATE, 'yyyy-MM-dd' ) >= '" + medicalConsumables.getBegndate() + "' ";
        }
        if (!"".equals(medicalConsumables.getEnddate()) && null != medicalConsumables.getEnddate()) {
            sql += " AND TO_CHAR( mib.ENDDATE, 'yyyy-MM-dd' ) <= '" + medicalConsumables.getEnddate() + "' ";
        }
        return sql ;
    }



    /**
    *医用耗材目录信息查询导出
    * Author wzn
    * Date 2022/9/8 14:26
    */
    public String medicalConsumablesDataExport(MedicalConsumables medicalConsumables){
        String sql = "SELECT\n" +
                "\t\t\tmib.MCS_NAME,\n" +
                "\t\t\tmib.MED_LIST_CODG,\n" +
                "mib.BEGNDATE,\n" +
                "\t\t\tmib.ENDDATE,\n" +
                "\t\t\th1.NAT_DIC_VAL_NAME SELFPAY_PROP_PSN_TYPE,\n" +
                "hspb.SELFPAY_PROP,\n" +
                "hld.HILIST_PRIC_UPLMT_AMT,\n" +
                "\t\t\th2.NAT_DIC_VAL_NAME HILIST_LMTPRIC_TYPE,\n" +
                "\t\t\th3.NAT_DIC_VAL_NAME MED_CHRGITM_TYPE,\n" +
                "\t\t\th4.NAT_DIC_VAL_NAME CHRGITM_LV,\n" +
                "\t\t\th5.NAT_DIC_VAL_NAME LMT_USED_FLAG\n" +
                "\t\tFROM\n" +
                "\t\t\tPOLCENT_DB.MCS_INFO_B mib\n" +
                "\t\tLEFT JOIN POLCENT_DB.HILIST_SELFPAY_PROP_B hspb ON HSPB.HILIST_CODE = mib.MED_LIST_CODG\n" +
                "\t\tLEFT JOIN POLCENT_DB.HILIST_LMTPRIC_D hld ON hld.HILIST_CODE = mib.MED_LIST_CODG\n" +
                "\t\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h1 ON hspb.SELFPAY_PROP_PSN_TYPE = h1.NAT_DIC_VAL_CODE\n" +
                "left join POLCENT_DB.HILIST_B hb on hb.HILIST_CODE = mib.MED_LIST_CODG\n" +
                "\t\tAND h1.DIC_TYPE_CODE = 'SELFPAY_PROP_PSN_TYPE'\n" +
                "\t\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h2 ON hld.HILIST_LMTPRIC_TYPE = h2.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h2.DIC_TYPE_CODE = 'HILIST_LMTPRIC_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h3 ON hb.MED_CHRGITM_TYPE = h3.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h3.DIC_TYPE_CODE = 'MED_CHRGITM_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h4 ON hb.CHRGITM_LV = h4.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h4.DIC_TYPE_CODE = 'CHRGITM_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h5 ON hb.LMT_USED_FLAG = h5.NAT_DIC_VAL_CODE\n" +
                "\t\tAND h5.DIC_TYPE_CODE = 'LMT_USED_FLAG'\n" +
                "\n" +
                "\t\tWHERE\n" +
                "\t\t\tmib.VALI_FLAG = '1'" ;
        if(!"".equals(medicalConsumables.getMcs_name()) && null != medicalConsumables.getMcs_name()){
            sql += " and mib.MCS_NAME like '%"+medicalConsumables.getMcs_name()+"%'" ;
        }

        if (!"".equals(medicalConsumables.getBegndate()) && null != medicalConsumables.getBegndate()) {
            sql += " AND TO_CHAR( mib.BEGNDATE, 'yyyy-MM-dd' ) >= '" + medicalConsumables.getBegndate() + "' ";
        }
        if (!"".equals(medicalConsumables.getEnddate()) && null != medicalConsumables.getEnddate()) {
            sql += " AND TO_CHAR( mib.ENDDATE, 'yyyy-MM-dd' ) <= '" + medicalConsumables.getEnddate() + "' ";
        }
        return sql ;
    }

    
    /**
    *结算发生数据对比
    * Author wzn
    * Date 2022/9/14 9:48
    */
    public String billingComparison(SetlDVo setlDVo){
        String sql = "SELECT\n" +
                "\tsd.FIXMEDINS_CODE,\n" +
                "\tsd.FIXMEDINS_NAME,\n" +
                "\tsd.POOLAREA_NO,\n" +
                "\tsd.DISE_NAME,\n" +
                "\tsd.DISE_NO,\n" +
                "sum(case when sd.REFD_SETL_FLAG = '0' then 1 else 0 end ) PERSON_COUNT,\n" +
                "count( DISTINCT sd.CERTNO ) PERSON_NUM ,\n" +
                "sum( sd.MEDFEE_SUMAMT ) MEDFEE_SUMAMT,\n" +
                "sum( sd.HIFOB_PAY ) HIFOB_PAY,\n" +
                "sum( sd.CVLSERV_PAY ) CVLSERV_PAY,\n" +
                "sum( sd.ACCT_PAY ) ACCT_PAY,\n" +
                "sum( sd.CASH_PAYAMT ) CASH_PAYAMT,\n" +
                "sum( sd.OWNPAY_HOSP_PART ) OWNPAY_HOSP_PART,\n" +
                "sum( sd.HIFES_PAY ) HIFES_PAY,\n" +
                "sum( sd.HIFMI_PAY ) HIFMI_PAY,\n" +
                "sum( sd.MAF_PAY ) MAF_PAY,\n" +
                "sum( sd.HIFDM_PAY ) HIFDM_PAY,\n" +
                "sum( sd.MEDFEE_SUMAMT )/sum(case when sd.REFD_SETL_FLAG = '0' then 1 else 0 end ) perTimeCost,\n" +
                "sum(sd.FULAMT_OWNPAY_AMT) fulamt_ownpay_amt\n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D sd\n" +
                "where sd.FIXMEDINS_CODE = '"+setlDVo.getFixmedins_code()+"'  "  ;
        if(!"".equals(setlDVo.getDise_no()) && null != setlDVo.getDise_no()){
            sql += " and sd.DISE_NO = '"+setlDVo.getDise_no()+"'" ;
        }
        if(!"".equals(setlDVo.getPoolarea_no()) && null != setlDVo.getPoolarea_no()){
            sql += " and sd.POOLAREA_NO = '"+setlDVo.getPoolarea_no()+"'" ;
        }
        if(!"".equals(setlDVo.getSetl_type()) && null != setlDVo.getSetl_type()){
            sql += " and sd.SETL_TYPE = '"+setlDVo.getSetl_type()+"'" ;
        }
        if(!"".equals(setlDVo.getMed_type()) && null != setlDVo.getMed_type()){
            sql += " and sd.MED_TYPE in ("+setlDVo.getMed_type()+")" ;
        }
        if (!"".equals(setlDVo.getBegndate()) && null != setlDVo.getBegndate()) {
            sql += " AND TO_CHAR( sd.BEGNDATE, 'yyyy-MM-dd' ) >= '" + setlDVo.getBegndate() + "' ";
        }
        if (!"".equals(setlDVo.getEnddate()) && null != setlDVo.getEnddate()) {
            sql += " AND TO_CHAR( sd.ENDDATE, 'yyyy-MM-dd' ) <= '" + setlDVo.getEnddate() + "' ";
        }
               sql += "GROUP BY sd.POOLAREA_NO, sd.FIXMEDINS_CODE, sd.FIXMEDINS_NAME,DISE_NAME,DISE_NO" ;
        return sql ;
    }




    /**
    *3.6城乡居民基本医疗保险人员统计表
    * Author wzn
    * Date 2022/9/16 11:28
    */
    public String insuranceData(Page page, Insurance insurance){
        String sql = "SELECT\n" +
                "\th3.NAT_DIC_VAL_NAME INSU_ADMDVS,\n" +
                "\th2.NAT_DIC_VAL_NAME PSN_TYPE,\n" +
                "count(1)\n" +
                "FROM\n" +
                "\tINSUCENT_DB.PSN_INSU_D pid\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h2 ON pid.PSN_TYPE = h2.NAT_DIC_VAL_CODE AND h2.DIC_TYPE_CODE = 'PSN_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h3 ON pid.INSU_ADMDVS = h3.NAT_DIC_VAL_CODE AND h3.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "where PID.INSU_ADMDVS in('320399','320382','320381','320324','320322','320321','320312','320305')\n" ;
        if(!"".equals(insurance.getInsu_admdvs()) && null != insurance.getInsu_admdvs()){
            sql += " and pid.INSU_ADMDVS = '"+insurance.getInsu_admdvs()+"'" ;
        }
        if(!"".equals(insurance.getPsn_insu_stas()) && null != insurance.getPsn_insu_stas()){
            sql += " and pid.PSN_INSU_STAS = '"+insurance.getPsn_insu_stas()+"'" ;
        }
        if(!"".equals(insurance.getPsn_type()) && null != insurance.getPsn_type()){
            sql += " and pid.PSN_TYPE = '"+insurance.getPsn_type()+"'" ;
        }
                sql +="group by h3.NAT_DIC_VAL_NAME,h2.NAT_DIC_VAL_NAME" ;

        return sql ;
    }



    /**
     *3.6城乡居民基本医疗保险人员统计表导出
     * Author wzn
     * Date 2022/9/16 11:28
     */
    public String insuranceDataExport(Insurance insurance){
        String sql = "SELECT\n" +
                "\th3.NAT_DIC_VAL_NAME INSU_ADMDVS,\n" +
                "\th2.NAT_DIC_VAL_NAME PSN_TYPE,\n" +
                "count(1)\n" +
                "FROM\n" +
                "\tINSUCENT_DB.PSN_INSU_D pid\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h2 ON pid.PSN_TYPE = h2.NAT_DIC_VAL_CODE AND h2.DIC_TYPE_CODE = 'PSN_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h3 ON pid.INSU_ADMDVS = h3.NAT_DIC_VAL_CODE AND h3.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "where PID.INSU_ADMDVS in('320399','320382','320381','320324','320322','320321','320312','320305')\n" ;
        if(!"".equals(insurance.getInsu_admdvs()) && null != insurance.getInsu_admdvs()){
            sql += " and pid.INSU_ADMDVS = '"+insurance.getInsu_admdvs()+"'" ;
        }
        if(!"".equals(insurance.getPsn_insu_stas()) && null != insurance.getPsn_insu_stas()){
            sql += " and pid.PSN_INSU_STAS = '"+insurance.getPsn_insu_stas()+"'" ;
        }
        if(!"".equals(insurance.getPsn_type()) && null != insurance.getPsn_type()){
            sql += " and pid.PSN_TYPE = '"+insurance.getPsn_type()+"'" ;
        }
        sql +="group by h3.NAT_DIC_VAL_NAME,h2.NAT_DIC_VAL_NAME" ;

        return sql ;
    }
}



