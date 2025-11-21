package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.AccountDetails;
import com.jsdc.ybpt.model_query.EmpPayInfo;
import com.jsdc.ybpt.model_query.ResidentPayinfo;
import com.jsdc.ybpt.model_query.StaffPayinfo;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.PersonalSettlementVo;
import org.springframework.stereotype.Repository;

@Repository
public class PersonInfoDao {

    public String selectPersonInfo(Page page, String certno, String cardType, String psn_name, String psn_no, String mob) {
        String sql = "SELECT\n" +
                "\ta.PSN_NAME,\n" +
                "\ta.PSN_NO,\n" +
                "\tb.NAT_DIC_VAL_NAME gend,\n" +
                "\tto_char(a.BRDY,'yyyy-MM-dd') BRDY,\n" +
                "\tc.NAT_DIC_VAL_NAME PSN_CERT_TYPE,\n" +
                "\ta.CERTNO,\n" +
                "\tg.NAT_DIC_VAL_NAME NATY,\n" +
                "\td.NAT_DIC_VAL_NAME RESD_LOC_ADMDVS,\n" +
                "\te.NAT_DIC_VAL_NAME SURV_STAS,\n" +
                "\tf.NAT_DIC_VAL_NAME RETR_TYPE\n" +
                "FROM\n" +
                "\tBASINFOCENT_DB.PSN_INFO_B a \n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.gend = b.NAT_DIC_VAL_CODE and b.DIC_TYPE_CODE='GEND'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.PSN_CERT_TYPE=c.NAT_DIC_VAL_CODE and c.DIC_TYPE_CODE='PSN_CERT_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.RESD_LOC_ADMDVS=d.NAT_DIC_VAL_CODE and d.DIC_TYPE_CODE='ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on a.SURV_STAS=e.NAT_DIC_VAL_CODE and e.DIC_TYPE_CODE='SURV_STAS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f on a.RETR_TYPE=f.NAT_DIC_VAL_CODE and f.DIC_TYPE_CODE='RETR_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g on a.NATY=g.NAT_DIC_VAL_CODE and g.DIC_TYPE_CODE='NATY'\n" +
                "WHERE\n" +
                "\ta.VALI_FLAG = '1'\n";
        if (StringUtils.isNotEmpty(certno)) {
            sql += " and a.certno like '%" + certno + "%'";
        }
        if (StringUtils.isNotEmpty(cardType)) {
            sql += " and a.PSN_CERT_TYPE ='" + cardType + "'";
        }
        if (StringUtils.isNotEmpty(psn_name)) {
            sql += " and a.psn_name like '%" + psn_name + "%'";
        }
        if (StringUtils.isNotEmpty(psn_no)) {
            sql += " and a.psn_no like '%" + psn_no + "%'";
        }
        if (StringUtils.isNotEmpty(mob)) {
            sql += " and a.mob like '%" + mob + "%'";
        }
        return sql;
    }

    public String getPersonInfoByCarno(String carno) {
        String sql = "SELECT\n" +
                "\ta.PSN_NAME,\n" +
                "\ta.PSN_NO,\n" +
                "\tb.NAT_DIC_VAL_NAME gend,\n" +
                "\tto_char(a.BRDY,'yyyy-MM-dd') BRDY,\n" +
                "\tc.NAT_DIC_VAL_NAME PSN_CERT_TYPE,\n" +
                "\ta.CERTNO,\n" +
                "\ta.HSECFC,\n" +
                "\ta.TEL,\n" +
                "\ta.MOB,\n" +
                "\th.NAT_DIC_VAL_NAME POLSTAS,\n" +
                "\ti.NAT_DIC_VAL_NAME RESD_NATU,\n" +
                "\tg.NAT_DIC_VAL_NAME NATY,\n" +
                "\td.NAT_DIC_VAL_NAME RESD_LOC_ADMDVS,\n" +
                "\te.NAT_DIC_VAL_NAME SURV_STAS,\n" +
                "\tf.NAT_DIC_VAL_NAME RETR_TYPE,\n" +
                "\ta.RETR_TYPE RETR_TYPE_CODE,\n" +
                "\ta.HSREG_ADDR,\n" +
                "\ta.HSREG_ADDR_POSCODE,\n" +
                "\tj.NAT_DIC_VAL_NAME LIVE_ADMDVS,\n" +
                "\tl.NAT_DIC_VAL_NAME HLCON,\n" +
                "\ta.LIVE_ADDR,\n" +
                "\ta.LIVE_ADDR_POSCODE,\n" +
                "\tk.NAT_DIC_VAL_NAME MRG_STAS,\n" +
                "\tm.NAT_DIC_VAL_NAME ADMDUT,\n" +
                "\tn.NAT_DIC_VAL_NAME EDUC,\n" +
                "\to.NAT_DIC_VAL_NAME PRO_TECH_DUTY_LV,\n" +
                "\tp.NAT_DIC_VAL_NAME NAT_PRFS_QUA_LV,\n" +
                "\ta.RESDBOOK_NO,\n" +
                "\ta.EMAIL,\n" +
                "\ta.GRAD_SCHL,\n" +
                "\ta.MEMO\n" +
                "FROM\n" +
                "\tBASINFOCENT_DB.PSN_INFO_B a \n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.gend = b.NAT_DIC_VAL_CODE and b.DIC_TYPE_CODE='GEND'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.PSN_CERT_TYPE=c.NAT_DIC_VAL_CODE and c.DIC_TYPE_CODE='PSN_CERT_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.RESD_LOC_ADMDVS=d.NAT_DIC_VAL_CODE and d.DIC_TYPE_CODE='ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on a.SURV_STAS=e.NAT_DIC_VAL_CODE and e.DIC_TYPE_CODE='SURV_STAS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f on a.RETR_TYPE=f.NAT_DIC_VAL_CODE and f.DIC_TYPE_CODE='RETR_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g on a.NATY=g.NAT_DIC_VAL_CODE and g.DIC_TYPE_CODE='NATY'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h on a.POLSTAS=h.NAT_DIC_VAL_CODE and h.DIC_TYPE_CODE='POLSTAS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A i on a.RESD_NATU=i.NAT_DIC_VAL_CODE and i.DIC_TYPE_CODE='RESD_NATU'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A j on a.LIVE_ADMDVS=j.NAT_DIC_VAL_CODE and j.DIC_TYPE_CODE='ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A k on a.MRG_STAS=k.NAT_DIC_VAL_CODE and k.DIC_TYPE_CODE='MRG_STAS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A l on a.HLCON=l.NAT_DIC_VAL_CODE and l.DIC_TYPE_CODE='HLCON'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A m on a.ADMDUT=m.NAT_DIC_VAL_CODE and m.DIC_TYPE_CODE='ADMDUT'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A n on a.EDUC=n.NAT_DIC_VAL_CODE and n.DIC_TYPE_CODE='EDUC'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A o on a.PRO_TECH_DUTY_LV=o.NAT_DIC_VAL_CODE and o.DIC_TYPE_CODE='PRO_TECH_DUTY_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A p on a.NAT_PRFS_QUA_LV=p.NAT_DIC_VAL_CODE and p.DIC_TYPE_CODE='NAT_PRFS_QUA_LV'\n" +
                "WHERE\n" +
                "\ta.VALI_FLAG = '1'\n" +
                "and a.CERTNO ='" + carno + "'";
        return sql;
    }

    public String getEmpInfo(Page page, EmpPayInfo empPayInfo) {
        String sql = "SELECT\n" +
                "\ta.EMP_NO,\n" +
                "\tb. EMP_NAME,\n" +
                "\ta.CASHYM,\n" +
                "\ta.ACCRYM_BEGN,\n" +
                "\ta.ACCRYM_END,\n" +
                "\tc.NAT_DIC_VAL_NAME INSUTYPE,\n" +
                "\ta.EMP_CLCT_PARAVAL,\n" +
                "\ta.EMP_CLCTSTD_SUMAMT,\n" +

                "\ta.PSN_CLCT_PARAVAL,\n" +
                "\ta.PSN_CLCTSTD_SUMAMT,\n" +
                "\ta.PSN_CLCT_AMT,\n" +
                "\ta.OTH_CLCT_AMT,\n" +
                "\ta.INTO_ACCT_AMT,\n" +
                "\td.NAT_DIC_VAL_NAME CLCT_FLAG,\n" +
                "\te.NAT_DIC_VAL_NAME CLCT_TYPE,\n" +
                "\ta.CLCT_PSNCNT,\n" +
                "\ta.STAF_TOTLWAG,\n" +
                "\ta.AMT,\n" +
                "\ta.INTE,\n" +
                "\ta.LATEFEE,\n" +
                "\tf.NAT_DIC_VAL_NAME REVS_FLAG,\n" +
                "\ta.ARVL_DATE,\n" +
                "\ta.ARVL_BCHNO,\n" +
                "\ta.INTSURY_TIME,\n" +
                "\ta.URSN_TIME,\n" +
                "\ta.ELEC_TAXRPT_NO,\n" +
                "\ta.DCLA_PRD,\n" +
                "\tg.NAT_DIC_VAL_NAME POOLAREA_NO,\n" +
                "\ta.TAXDEPT_CODE\n" +
                "FROM\n" +
                "\tCLCTCENT_DB.EMP_CLCT_DETL_D a \n" +
                "\tleft join CUSTCENT_DB.INSU_EMP_INFO_B b on a.EMP_NO = b.EMP_NO\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.INSUTYPE=c.NAT_DIC_VAL_CODE and c.DIC_TYPE_CODE='INSUTYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.CLCT_FLAG=d.NAT_DIC_VAL_CODE and d.DIC_TYPE_CODE='CLCT_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on a.CLCT_TYPE=e.NAT_DIC_VAL_CODE and e.DIC_TYPE_CODE='CLCT_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f on a.REVS_FLAG=f.NAT_DIC_VAL_CODE and f.DIC_TYPE_CODE='REVS_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g on a.POOLAREA_NO=g.NAT_DIC_VAL_CODE and g.DIC_TYPE_CODE='ADMDVS'\n" +
                "WHERE 1=1 \n";
        if (StringUtils.isNotEmpty(empPayInfo.getEmp_no())) {
            sql += "\tand a.EMP_NO = '" + empPayInfo.getEmp_no() + "'\n";
        }
        if (StringUtils.isNotEmpty(empPayInfo.getEmp_name())) {
            sql += "\tand b.EMP_NAME like '%" + empPayInfo.getEmp_name() + "%'\n";
        }
        if (StringUtils.isNotEmpty(empPayInfo.getInsutype())) {
            sql += "\tand a.insutype = '" + empPayInfo.getInsutype() + "'\n";
        }
        sql += " order by a.CASHYM desc";
        return sql;
    }

    public String getStaffPayinfo(Page page, StaffPayinfo staffPayinfo) {
        String sql = "SELECT\n" +
                "\ta.EMP_NO,\n" +
                "\tk.EMP_NAME,\n" +
                "\ta.PSN_NO,\n" +
                "\taa.PSN_NAME,\n" +
                "\taa.CERTNO,\n" +
                "\ta.CASHYM,\n" +
                "\ta.ACCRYM_BEGN,\n" +
                "\ta.ACCRYM_END,\n" +
                "\tb.NAT_DIC_VAL_NAME INSUTYPE,\n" +
                "\tc.NAT_DIC_VAL_NAME PSN_TYPE,\n" +
                "\td.NAT_DIC_VAL_NAME PSN_IDET_TYPE,\n" +
                "\te.NAT_DIC_VAL_NAME INSU_IDET,\n" +
                "\ta.EMP_CLCTSTD,\n" +
                "\ta.PSN_CLCTSTD,\n" +
                "\ta.WAG,\n" +
                "\tf.NAT_DIC_VAL_NAME QUOT_CLCT_FLAG,\n" +
                "\ta.EMP_CLCT_PARAVAL,\n" +
                "\ta.EMP_INTO_PARAVAL,\n" +
                "\ta.EMP_CLCT_AMT,\n" +
                "\ta.EMP_CLCT_INTO_ACCT_AMT,\n" +
                "\ta.PSN_CLCT_PARAVAL,\n" +
                "\ta.PSN_INTO_PARAVAL,\n" +
                "\ta.PSN_CLCT_AMT,\n" +
                "\ta.PSN_CLCT_INTO_ACCT_AMT,\n" +
                "\ta.OTH_CLCT_TRAF_AMT,\n" +
                "\ta.OTH_CLCT_AMT,\n" +
                "\ta.FINSUBS_AMT,\n" +
                "\ta.FINSUBS_TRAF_AMT,\n" +
                "\ta.TRAF_SUMAMT,\n" +
                "\ta.CLCT_SUMAMT,\n" +
                "\ta.INTE,\n" +
                "\ta.LATEFEE,\n" +
                "\tg.NAT_DIC_VAL_NAME CLCT_FLAG,\n" +
                "\th.NAT_DIC_VAL_NAME CLCT_TYPE,\n" +
                "\ta.CLCT_TIME,\n" +
                "\ta.ARVLER,\n" +
                "\ta.ARVL_BCHNO,\n" +
                "\tm.NAT_DIC_VAL_NAME REVS_FLAG,\n" +
                "\ta.TRAFER,\n" +
                "\ta.TRAF_TIME,\n" +
                "\ta.INTSURY_TIME,\n" +
                "\ta.URSN_TIME,\n" +
                "\ta.DCLA_PRD,\n" +
                "\ta.ELEC_TAXRPT_NO,\n" +
                "\ti.NAT_DIC_VAL_NAME PEAWKR_FLAG,\n" +
                "\tl.NAT_DIC_VAL_NAME POOLAREA_NO,\n" +
                "\tj.NAT_DIC_VAL_NAME INSUTYPE_RETR_FLAG,\n" +
                "\ta.TAXDEPT_CODE,\n" +
                "\ta.PLAN_BCHNO,\n" +
                "\ta.PSN_INSU_RLTS_ID,\n" +
                "\ta.CLCTSTD_CRTF_RULE_CODG,\n" +
                "\ta.CLCT_RULE_TYPE_CODG,\n" +
                "\ta.INSU_CLCT_MONS\n" +
                "\n" +
                "FROM\n" +
                "\tCLCTCENT_DB.STAF_PSN_CLCT_DETL_D a\n" +
                "\tleft join BASINFOCENT_DB.PSN_INFO_B aa on a.PSN_NO = aa.PSN_NO and aa.VALI_FLAG = '1'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.INSUTYPE = b.NAT_DIC_VAL_CODE and b.DIC_TYPE_CODE='INSUTYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.PSN_TYPE = c.NAT_DIC_VAL_CODE and c.DIC_TYPE_CODE='PSN_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.PSN_IDET_TYPE = d.NAT_DIC_VAL_CODE and d.DIC_TYPE_CODE='PSN_IDET_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on a.INSU_IDET = e.NAT_DIC_VAL_CODE and e.DIC_TYPE_CODE='INSU_IDET'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f on a.QUOT_CLCT_FLAG = f.NAT_DIC_VAL_CODE and f.DIC_TYPE_CODE='QUOT_CLCT_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g on a.CLCT_FLAG = g.NAT_DIC_VAL_CODE and g.DIC_TYPE_CODE='CLCT_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h on a.CLCT_TYPE = h.NAT_DIC_VAL_CODE and h.DIC_TYPE_CODE='CLCT_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A i on a.PEAWKR_FLAG = i.NAT_DIC_VAL_CODE and i.DIC_TYPE_CODE='PEAWKR_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A j on a.INSUTYPE_RETR_FLAG = j.NAT_DIC_VAL_CODE and j.DIC_TYPE_CODE='INSUTYPE_RETR_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A l on a.POOLAREA_NO=l.NAT_DIC_VAL_CODE and l.DIC_TYPE_CODE='ADMDVS'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A m on a.REVS_FLAG=m.NAT_DIC_VAL_CODE and m.DIC_TYPE_CODE='REVS_FLAG'\n" +
                "\tleft join CUSTCENT_DB.INSU_EMP_INFO_B k on a.EMP_NO = k.EMP_NO\n" +
                "where 1=1 ";
        if (StringUtils.isNotEmpty(staffPayinfo.getCertno())) {
            sql += " and aa.CERTNO ='" + staffPayinfo.getCertno() + "'\n";
        }
        if (StringUtils.isNotEmpty(staffPayinfo.getInsu_idet())) {
            sql += " and a.insu_idet ='" + staffPayinfo.getInsu_idet() + "'\n";
        }
        if (StringUtils.isNotEmpty(staffPayinfo.getEmp_no())) {
            sql += " and a.emp_no ='" + staffPayinfo.getEmp_no() + "'\n";
        }
        if (StringUtils.isNotEmpty(staffPayinfo.getClct_type())) {
            sql += " and a.clct_type ='" + staffPayinfo.getInsu_idet() + "'\n";
        }
        if (StringUtils.isNotEmpty(staffPayinfo.getClct_time())) {
            sql += " AND TO_CHAR( a.clct_time, 'yyyy-MM-dd' ) = '" + staffPayinfo.getClct_time() + "' ";
        }
        sql += "order by a.CASHYM desc";
        return sql;
    }

    public String getResidentPayinfo(Page page, ResidentPayinfo residentPayinfo) {
        String sql = "SELECT\n" +
                "\ta.EMP_NO,\n" +
                "\tk.EMP_NAME,\n" +
                "\ta.PSN_NO,\n" +
                "\taa.PSN_NAME,\n" +
                "\taa.CERTNO,\n" +
                "\ta.PSN_INSU_RLTS_ID,\n" +
                "\tb.NAT_DIC_VAL_NAME INSUTYPE,\n" +
                "\tc.NAT_DIC_VAL_NAME PSN_TYPE,\n" +
                "\td.NAT_DIC_VAL_NAME PSN_IDET_TYPE,\n" +
                "\te.NAT_DIC_VAL_NAME INSU_IDET,\n" +
                "\ta.ELEC_TAXRPT_NO,\n" +
                "\ta.CASHYM,\n" +
                "\ta.ACCRYM_BEGN,\n" +
                "\ta.ACCRYM_END,\n" +
                "\ta.PSN_CLCT_AMT,\n" +
                "\tm.NAT_DIC_VAL_NAME REVS_FLAG,\n" +
                "\tg.NAT_DIC_VAL_NAME CLCT_FLAG,\n" +
                "\th.NAT_DIC_VAL_NAME CLCT_TYPE,\n" +
                "\ta.CLCT_TIME,\n" +
                "\tf.NAT_DIC_VAL_NAME QUOT_CLCT_FLAG,\n" +
                "\ta.INTSURY_TIME,\n" +
                "\ta.URSN_TIME,\n" +
                "\ta.DCLA_PRD,\n" +
                "\tj.NAT_DIC_VAL_NAME INSUTYPE_RETR_FLAG,\n" +
                "\tl.NAT_DIC_VAL_NAME POOLAREA_NO,\n" +
                "\ta.TAXDEPT_CODE,\n" +
                "\ta.INIT_RSDT_CLCT_DETL_ID,\n" +
                "\ta.PLAN_BCHNO,\n" +
                "\ta.CRTE_OPTINS_NO,\n" +
                "\ta.CRTER_ID,\n" +
                "\ta.CRTER_NAME,\n" +
                "\ta.CRTE_TIME,\n" +
                "\ta.OPTINS_NO,\n" +
                "\ta.OPTER_ID,\n" +
                "\ta.OPTER_NAME,\n" +
                "\ta.OPT_TIME,\n" +
                "\ta.RID,\n" +
                "\ta.UPDT_TIME,\n" +
                "\ta.FINSUBS_AMT,\n" +
                "\ta.OTH_CLCT_AMT,\n" +
                "\ta.CLCT_SUMAMT,\n" +
                "\ta.SEND_FLAG,\n" +
                "\ta.CLCT_RULE_TYPE_CODG \n" +
                "FROM\n" +
                "\tCLCTCENT_DB.RSDT_PSN_CLCT_DETL_D a\n" +
                "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B aa ON a.PSN_NO = aa.PSN_NO \n" +
                "\tAND aa.VALI_FLAG = '1'\n" +
                "\tLEFT JOIN CUSTCENT_DB.INSU_EMP_INFO_B k ON a.EMP_NO = k.EMP_NO \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.INSUTYPE = b.NAT_DIC_VAL_CODE and b.DIC_TYPE_CODE='INSUTYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.PSN_TYPE = c.NAT_DIC_VAL_CODE and c.DIC_TYPE_CODE='PSN_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.PSN_IDET_TYPE = d.NAT_DIC_VAL_CODE and d.DIC_TYPE_CODE='PSN_IDET_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on a.INSU_IDET = e.NAT_DIC_VAL_CODE and e.DIC_TYPE_CODE='INSU_IDET'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f on a.QUOT_CLCT_FLAG = f.NAT_DIC_VAL_CODE and f.DIC_TYPE_CODE='QUOT_CLCT_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g on a.CLCT_FLAG = g.NAT_DIC_VAL_CODE and g.DIC_TYPE_CODE='CLCT_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h on a.CLCT_TYPE = h.NAT_DIC_VAL_CODE and h.DIC_TYPE_CODE='CLCT_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A j on a.INSUTYPE_RETR_FLAG = j.NAT_DIC_VAL_CODE and j.DIC_TYPE_CODE='INSUTYPE_RETR_FLAG'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A l on a.POOLAREA_NO=l.NAT_DIC_VAL_CODE and l.DIC_TYPE_CODE='ADMDVS'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A m on a.REVS_FLAG=m.NAT_DIC_VAL_CODE and m.DIC_TYPE_CODE='REVS_FLAG'\n" +
                "WHERE\n" +
                "\t1 = 1 \n";
        if (StringUtils.isNotEmpty(residentPayinfo.getCertno())) {
            sql += " and aa.CERTNO ='" + residentPayinfo.getCertno() + "'\n";
        }
        if (StringUtils.isNotEmpty(residentPayinfo.getInsu_idet())) {
            sql += " and a.insu_idet ='" + residentPayinfo.getInsu_idet() + "'\n";
        }
        if (StringUtils.isNotEmpty(residentPayinfo.getEmp_no())) {
            sql += " and a.emp_no ='" + residentPayinfo.getEmp_no() + "'\n";
        }
        if (StringUtils.isNotEmpty(residentPayinfo.getClct_type())) {
            sql += " and a.clct_type ='" + residentPayinfo.getInsu_idet() + "'\n";
        }
        if (StringUtils.isNotEmpty(residentPayinfo.getClct_time())) {
            sql += " and a.clct_time ='" + residentPayinfo.getClct_time() + "'\n";
        }
        sql += "order by a.CASHYM desc";
        return sql;
    }

    public String getAccountDetails(Page page, AccountDetails accountDetails) {
        String sql = "SELECT\n" +
                "\ta.PSN_NO,\n" +
                "\taa.PSN_NAME,\n" +
                "\taa.CERTNO,\n" +
                "\ta.ACCT_INCEXPD_SOUC_ID,\n" +
                "\ta.TABNAME,\n" +
                "\ta.YEAR,\n" +
                "\ta.PSN_INSU_RLTS_ID,\n" +
                "\tb.NAT_DIC_VAL_NAME INSU_ADMDVS,\n" +
                "\tc.NAT_DIC_VAL_NAME ACCT_INCEXPD_TYPE,\n" +
                "\ta.INC_SUMAMT,\n" +
                "\ta.ACCT_PSN_CLCT_CRTYEAR_INC,\n" +
                "\ta.EMP_CLCT_INTO_ACCT_AMT,\n" +
                "\ta.UEBMI_ACCT_LASTY_CROV,\n" +
                "\ta.UEBMI_ACCT_PTYEAR_CROV_INC,\n" +
                "\ta.CVLSERV_ACCT_CRTYEAR_INC,\n" +
                "\ta.CVLSERV_ACCT_LASTY_CROV_AMT,\n" +
                "\ta.CVLSERV_ACCT_PTYEAR_CROV_INC,\n" +
                "\ta.OTH_ACCT_CRTYEAR_INC,\n" +
                "\ta.OTH_ACCT_LASTY_CROV_AMT,\n" +
                "\ta.OTH_ACCT_PTYEAR_INC,\n" +
                "\ta.OTH_ACCT_INC,\n" +
                "\ta.ACCT_MODI_DATE,\n" +
                "\ta.ACCT_INT_DATE,\n" +
                "\ta.VALI_FLAG,\n" +
                "\ta.MEMO,\n" +
                "\ta.RID,\n" +
                "\ta.UPDT_TIME,\n" +
                "\ta.CRTER_ID,\n" +
                "\ta.CRTER_NAME,\n" +
                "\ta.CRTE_TIME,\n" +
                "\ta.CRTE_OPTINS_NO,\n" +
                "\ta.OPTER_ID,\n" +
                "\ta.OPTER_NAME,\n" +
                "\ta.OPT_TIME,\n" +
                "\ta.OPTINS_NO,\n" +
                "\td.NAT_DIC_VAL_NAME POOLAREA_NO \n" +
                "FROM\n" +
                "\tCLCTCENT_DB.ACCT_INC_DETL_D a\n" +
                "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B aa ON a.PSN_NO = aa.PSN_NO \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.INSU_ADMDVS=b.NAT_DIC_VAL_CODE and b.DIC_TYPE_CODE='ADMDVS'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.ACCT_INCEXPD_TYPE=c.NAT_DIC_VAL_CODE and c.DIC_TYPE_CODE='ACCT_INCEXPD_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.POOLAREA_NO=d.NAT_DIC_VAL_CODE and d.DIC_TYPE_CODE='ADMDVS'\n" +
                "WHERE\n" +
                "\t1 = 1 \n" +
                "\tAND a.VALI_FLAG = '1' \n" +
                "\tAND aa.VALI_FLAG = '1' \n";
        if (StringUtils.isNotEmpty(accountDetails.getCertno())) {
            sql += "\tAND aa.CERTNO = '" + accountDetails.getCertno() + "'\n";
        }
        if (StringUtils.isNotEmpty(accountDetails.getAcct_incexpd_type())) {
            sql += " and a.acct_incexpd_type ='" + accountDetails.getAcct_incexpd_type() + "'\n";
        }
        if (StringUtils.isNotEmpty(accountDetails.getYear())) {
            sql += " and a.year ='" + accountDetails.getYear() + "'\n";
        }
        sql += " order by year desc";
        return sql;
    }

    public String selectPersonalSettlement(Page page, PersonalSettlementVo personalSettlementVo) {
        String sql = "SELECT\n" +
                "\ta.FIXMEDINS_CODE fixmedins_code,\n" +
                "\ta.FIXMEDINS_NAME fixmedins_name,\n" +
                "\ta.SETL_ID setl_id,\n" +
                "\ta.PSN_NAME psn_name,\n" +
                "\ta.PSN_NO\n psn_no\n,\n" +
                "\ta.CLR_OPTINS\n clr_optins\n,\n" +
                "\ta.CERTNO certno,\n" +
                "\tb.nat_dic_val_name med_type,\n" +
                "\tc.nat_dic_val_name setl_type,\n" +
                "\td.nat_dic_val_name pay_loc,\n" +
                "\ta.SETL_TIME setl_time,\n" +
                "\ta.MEDFEE_SUMAMT medfee_sumamt,\n" +
                "\ta.HIFP_PAY hifp_pay,\n" +
                "\ta.HIFOB_PAY hifob_pay,\n" +
                "\ta.CVLSERV_PAY cvlserv_pay,\n" +
                "\ta.ACCT_PAY acct_pay,\n" +
                "\ta.CASH_PAYAMT cash_payamt,\n" +
                "\ta.OWNPAY_HOSP_PART ,\n" +
                "\ta.FULAMT_OWNPAY_AMT fulamt_ownpay_amt,\n" +
                "\ta.OVERLMT_SELFPAY overlmt_selfpay,\n" +
                "\ta.PRESELFPAY_AMT preselfpay_amt,\n" +
                "\ta.DEDC_STD dedc_std,\n" +
                "\ta.POOL_PROP_SELFPAY pool_prop_selfpay,\n" +
                "\ta.MAF_PAY ,\n" +
                "\ta.HIFES_PAY ,\n" +
                "\ta.HIFMI_PAY ,\n" +
                "\ta.OTHFUND_PAY ,\n" +
                "\ta.DISE_NO ,\n" +
                "\ta.DISE_NAME \n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D a\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.MED_TYPE = b.nat_dic_val_code and b.DIC_TYPE_CODE = 'MED_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.SETL_TYPE = c.nat_dic_val_code and c.DIC_TYPE_CODE = 'SETL_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.PAY_LOC = d.nat_dic_val_code and d.DIC_TYPE_CODE = 'PAY_LOC'\n" +
                "where a.VALI_FLAG='1'\n" +
                "AND a.REFD_SETL_FLAG = '0'\n";
        if (StringUtils.isNotEmpty(personalSettlementVo.getCertno())) {
            sql += "and a.CERTNO='" + personalSettlementVo.getCertno() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getSetl_id())) {
            sql += "and a.SETL_ID='" + personalSettlementVo.getSetl_id() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getPsn_no())) {
            sql += "and a.PSN_NO='" + personalSettlementVo.getPsn_no() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getPsn_name())) {
            sql += "and a.PSN_NAME like '%" + personalSettlementVo.getPsn_name() + "%'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getClr_optins())) {
            sql += "and a.CLR_OPTINS like '%" + personalSettlementVo.getClr_optins() + "%'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getBeginDate())) {
            sql += "and a.SETL_TIME >= to_date('" + personalSettlementVo.getBeginDate() + "','yyyy-MM-dd')\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getEndDate())) {
            sql += "and a.SETL_TIME <= to_date('" + personalSettlementVo.getEndDate() + "','yyyy-MM-dd')\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getMed_type())) {
            String med_type = "";
            for (String s : personalSettlementVo.getMed_type()) {
                med_type += "'" + s + "',";
            }
            sql += "and a.MED_TYPE in (" + med_type.substring(0, med_type.length() - 1) + ")\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getSetl_type())) {
            String setl_type = "";
            for (String s : personalSettlementVo.getSetl_type()) {
                setl_type += "'" + s + "',";
            }
            sql += "and a.SETL_TYPE in (" + setl_type.substring(0, setl_type.length() - 1) + ")\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getInsutype())) {
            sql += "and a.INSUTYPE = '" + personalSettlementVo.getInsutype() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getPay_loc())) {
            sql += "and a.PAY_LOC = '" + personalSettlementVo.getPay_loc() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getDise_no())) {
            sql += "and a.DISE_NO = '" + personalSettlementVo.getDise_no() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getInsu_admdvs())) {
            sql += "and a.INSU_ADMDVS = '" + personalSettlementVo.getInsu_admdvs() + "'\n";
        }
        sql += " order by a.SETL_TIME desc";
        return sql;
    }

    public String selectMzSettlement(Page page, PersonalSettlementVo personalSettlementVo) {
        String sql = "SELECT\n" +
                "\ta.FIXMEDINS_CODE fixmedins_code,\n" +
                "\ta.FIXMEDINS_NAME fixmedins_name,\n" +
                "\tg.nat_dic_val_name hosp_lv,\n" +
                "\ta.PSN_NAME psn_name,\n" +
                "\ta.SETL_ID setl_id,\n" +
                "\ta.CERTNO certno,\n" +
                "\tb.nat_dic_val_name med_type,\n" +
                "\tc.nat_dic_val_name setl_type,\n" +
                "\tto_char(a.SETL_TIME,'yyyy') year,\n" +
                "\ta.SETL_TIME setl_time,\n" +
                "\ta.MEDFEE_SUMAMT medfee_sumamt,\n" +
                "\ta.FULAMT_OWNPAY_AMT fulamt_ownpay_amt,\n" +
                "\ta.OVERLMT_SELFPAY overlmt_selfpay,\n" +
                "\ta.INSCP_AMT inscp_amt,\n" +
                "\ta.PRESELFPAY_AMT preselfpay_amt,\n" +
                "\ta.HIFP_PAY hifp_pay,\n" +
                "\ta.DEDC_STD dedc_std,\n" +
                "\ta.HIFOB_PAY hifob_pay,\n" +
                "\ta.CRT_DEDC crt_dedc,\n" +
                "\ta.CVLSERV_PAY cvlserv_pay,\n" +
                "\ta.ACT_PAY_DEDC act_pay_dedc,\n" +
                "\ta.ACCT_PAY acct_pay,\n" +
                "\ta.CASH_PAYAMT cash_payamt,\n" +
                "\ta.OWNPAY_HOSP_PART ,\n" +
                "\ta.POOL_PROP_SELFPAY pool_prop_selfpay,\n" +
                "\ta.HIFDM_PAY hifdm_pay,\n" +
                "\ta.MAF_PAY maf_pay,\n" +
                "\ta.HIFES_PAY ,\n" +
                "\ta.HIFMI_PAY hifmi_pay,\n" +
                "\ta.FUND_PAY_SUMAMT fund_pay_sumamt,\n" +
                "\th.nat_dic_val_name refd_setl_flag,\n" +
                "\ta.OTHFUND_PAY othfund_pay,\n" +
                "\ta.PSN_PAY psn_pay,\n" +
                "\ta.DISE_NO dise_no,\n" +
                "\ta.DISE_NAME dise_name,\n" +
                "\ta.BEGNDATE begndate,\n" +
                "\ta.ENDDATE enddate,\n" +
                "\td.nat_dic_val_name insutype,\n" +
                "\te.nat_dic_val_name insu_admdvs,\n" +
                "\tf.nat_dic_val_name pay_loc,\n" +
                "\ta.CRTER_NAME crter_name,\n" +
                "\ta.OPTER_NAME opter_name,\n" +
                "\tto_char(a.OPT_TIME,'yyyy-MM-dd HH24:mi:ss') opt_time\n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D a\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.MED_TYPE = b.nat_dic_val_code and b.DIC_TYPE_CODE = 'MED_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.SETL_TYPE = c.nat_dic_val_code and c.DIC_TYPE_CODE = 'SETL_TYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.INSUTYPE = d.nat_dic_val_code and d.DIC_TYPE_CODE = 'INSUTYPE'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on a.INSU_ADMDVS = e.nat_dic_val_code and e.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f on a.PAY_LOC = f.nat_dic_val_code and f.DIC_TYPE_CODE = 'PAY_LOC'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A g on a.HOSP_LV = g.nat_dic_val_code and g.DIC_TYPE_CODE = 'HOSP_LV'\n" +
                "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A h on a.REFD_SETL_FLAG = h.nat_dic_val_code and h.DIC_TYPE_CODE = 'REFD_SETL_FLAG'\n" +
                "where a.VALI_FLAG='1'\n";
        if (StringUtils.isNotEmpty(personalSettlementVo.getCertno())) {
            sql += "and a.CERTNO='" + personalSettlementVo.getCertno() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getYear())) {
            sql += "and to_char(a.SETL_TIME,'yyyy')='" + personalSettlementVo.getYear() + "'\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getBeginDate())) {
            sql += "and a.SETL_TIME >= to_date('" + personalSettlementVo.getBeginDate() + "','yyyy-MM-dd')\n";
        }
        if (StringUtils.isNotEmpty(personalSettlementVo.getEndDate())) {
            sql += "and a.SETL_TIME <= to_date('" + personalSettlementVo.getEndDate() + "','yyyy-MM-dd')\n";
        }
        return sql;
    }

    public String selectMzSettleDetails(String setlId) {
        String sql = "SELECT\n" +
                "\ta.FEE_OCUR_TIME fee_ocur_time,\n" +
                "\ta.RX_DRORD_NO rx_drord_no,\n" +
                "\ta.HILIST_CODE hilist_code,\n" +
                "\ta.HILIST_NAME hilist_name,\n" +
                "\ta.MEDINS_LIST_NAME medins_list_name,\n" +
                "\ta.LIST_TYPE list_type,\n" +
                "\ta.MED_CHRGITM_TYPE med_chrgitm_type,\n" +
                "\ta.PRIC pric,\n" +
                "\ta.CNT cnt,\n" +
                "\ta.DET_ITEM_FEE_SUMAMT det_item_fee_sumamt,\n" +
                "\ta.FULAMT_OWNPAY_AMT fulamt_ownpay_amt,\n" +
                "\ta.PRIC_UPLMT_AMT pric_uplmt_amt,\n" +
                "\ta.OVERLMT_SELFPAY overlmt_selfpay, \n" +
                "\ta.SELFPAY_PROP selfpay_prop,\n" +
                "\ta.PRESELFPAY_AMT preselfpay_amt,\n" +
                "\ta.REIM_PROP reim_prop,\n" +
                "\ta.INSCP_AMT inscp_amt,\n" +
                "\ta.CVLSERV_BEDFEE_AMT cvlserv_bedfee_amt,\n" +
                "\ta.MEDINS_DISC_AMT medins_disc_amt\n" +
                "FROM\n" +
                "\tSETLCENT_DB.FEE_LIST_D a\n" +
                "where a.VALI_FLAG='1'\n" +
                "and a.SETL_ID = '" + setlId + "'";
        return sql;
    }

    public String getPersonInfoByCarnos(String carno) {
        String sql = "SELECT\n" +
                "\ta.PSN_NAME,\n" +
                "\ta.PSN_NO,\n" +
                "\tto_char( a.BRDY, 'yyyy-MM-dd' ) BRDY,\n" +
                "\ta.CERTNO,\n" +
                "\ta.HSECFC,\n" +
                "\ta.TEL,\n" +
                "\ta.MOB,\n" +
                "\ta.RETR_TYPE RETR_TYPE_CODE,\n" +
                "\ta.HSREG_ADDR,\n" +
                "\ta.HSREG_ADDR_POSCODE,\n" +
                "\ta.LIVE_ADDR,\n" +
                "\ta.LIVE_ADDR_POSCODE,\n" +
                "\ta.RESDBOOK_NO,\n" +
                "\ta.EMAIL,\n" +
                "\ta.GRAD_SCHL,\n" +
                "\ta.MEMO \n" +
                "FROM\n" +
                "\tBASINFOCENT_DB.PSN_INFO_B a " +
                "WHERE\n" +
                "\ta.VALI_FLAG = '1'\n" +
                "and a.CERTNO in ('" + carno + "')";
        return sql;
    }

    public String getPersonInfoByCarnoOne(String carno) {
        String sql = "SELECT\n" +
                "\ta.PSN_NAME,\n" +
                "\ta.PSN_NO,\n" +
                "\tto_char( a.BRDY, 'yyyy-MM-dd' ) BRDY,\n" +
                "\ta.CERTNO,\n" +
                "\ta.HSECFC,\n" +
                "\ta.TEL,\n" +
                "\ta.MOB,\n" +
                "\ta.RETR_TYPE RETR_TYPE_CODE,\n" +
                "\ta.HSREG_ADDR,\n" +
                "\ta.HSREG_ADDR_POSCODE,\n" +
                "\ta.LIVE_ADDR,\n" +
                "\ta.LIVE_ADDR_POSCODE,\n" +
                "\ta.RESDBOOK_NO,\n" +
                "\ta.EMAIL,\n" +
                "\ta.GRAD_SCHL,\n" +
                "\ta.MEMO \n" +
                "FROM\n" +
                "\tBASINFOCENT_DB.PSN_INFO_B a " +
                "WHERE\n" +
                "\ta.VALI_FLAG = '1'\n" +
                "and a.CERTNO = '" + carno + "'";
        return sql;
    }

}
