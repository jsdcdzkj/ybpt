package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.*;
import com.jsdc.ybpt.model_query.medicalOrg.FixmedinsCntrRegD;
import com.jsdc.ybpt.model_query.medicalOrg.FixmedinsGather;
import com.jsdc.ybpt.model_query.medicalOrg.School;
import com.jsdc.ybpt.model_query.personnel.OrganizationGinseng;
import com.jsdc.ybpt.model_query.personnel.PersonalChanges;
import com.jsdc.ybpt.model_query.personnel.ReflAppyEvtCVo;
import com.jsdc.ybpt.model_query.settlement.*;
import com.jsdc.ybpt.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class QueryDao {

    public String drInfoB(Page page, DrInfoB drInfoB) {
        String sql = "SELECT di.DR_CODE, " +
                "       di.PSN_NO, " +
                "       di.DR_NAME, " +
                "       di.PSN_ITRO, " +
                "       di.DR_PRAC_TYPE_NAME, " +
                "       di.DR_PRAC_TYPE, " +
                "       di.DR_PRAC_SCP_CODE, " +
                "       di.DR_PRAC_SCP_NAME, " +
                "       di.PRAC_REGN, " +
                "       di.MUL_PRAC_FLAG, " +
                "       di.MAIN_PRACINS_NO, " +
                "       di.MAIN_PRACINS_NAME, " +
                "       di.NEMP_TIME, " +
                "       di.MAIN_PRACINS_ADDR, " +
                "       di.VALI_FLAG, " +
                "       di.DR_PRO_TECH_DUTY, " +
                "       di.DR_PRO_TECH_DUTY_NAME, " +
                "       di.DR_PRAC_CERT_NO, " +
                "       di.RID, " +
                "       di.CRTE_TIME, " +
                "       di.UPDT_TIME, " +
                "       di.CRTER_ID, " +
                "       di.CRTER_NAME, " +
                "       di.CRTE_OPTINS_NO, " +
                "       di.OPTER_ID, " +
                "       di.OPTER_NAME, " +
                "       di.OPT_TIME, " +
                "       di.OPTINS_NO, " +
                "       di.VER, " +
                "       di.ADMDVS, " +
                "       di.SYNC_PRNT_FLAG, " +
                "       di.MEDINS_CODE, " +
                "       di.PRACINS_NAME, " +
                "       di.PRACINS_ADDR, " +
                "       di.MEDINS_NAME, " +
                "       di.CRED_LV, " +
                "       di.VOLA_BHVR, " +
                "       di.PRAC_STAS, " +
                "       di.VER_NAME, " +
                "       di.DR_ID, " +
                "       di.PSN_STAS, " +
                "       di.CNTR_BEGNTIME, " +
                "       di.CNTR_ENDTIME, " +
                "       di.DR_PRAC_LV, " +
                "       di.DR_PRACCERT_ELEC_DOC, " +
                "       di.DR_CERT_CODG, " +
                "       di.DSG_ID " +
                "FROM CUSTCENT_DB.DR_INFO_B di ";
        sql += "where di.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(drInfoB.getDr_name())) {
            sql += "and di.dr_name like '%" + drInfoB.getDr_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(drInfoB.getDr_code())) {
            sql += "and di.dr_code like '%" + drInfoB.getDr_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(drInfoB.getMedins_code())) {
            sql += "and di.medins_code like '%" + drInfoB.getMedins_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(drInfoB.getMedins_name())) {
            sql += "and di.medins_name like '%" + drInfoB.getMedins_name() + "%' ";
        }
        sql += "ORDER BY di.CRTE_TIME DESC ";
        return sql;
    }

    public String empInsuD(Page page,EmpInsuD empInsuD) {
        String sql = "SELECT ei.EMP_INSU_RLTS_ID, " +
                "       ei.EMP_NO, " +
                "       ei.INSUTYPE, " +
                "       ei.HI_TYPE, " +
                "       ei.EMP_INSU_STAS, " +
                "       ei.CLCT_WAY, " +
                "       ei.CLCT_RULE_TYPE_CODG, " +
                "       ei.EMP_INSU_DATE, " +
                "       ei.CLCTSTD_CRTF_RULE_CODG, " +
                "       ei.TAX_BEGN_CLCT_YM, " +
                "       ei.MAX_ACCTPRD, " +
                "       ei.INSU_ADMDVS, " +
                "       ei.MEMO, " +
                "       ei.POOLAREA_NO, " +
                "       ei.CRTER_ID, " +
                "       ei.CRTER_NAME, " +
                "       ei.CRTE_OPTINS_NO, " +
                "       ei.CRTE_TIME, " +
                "       ei.UPDT_TIME, " +
                "       ei.OPTINS_NO, " +
                "       ei.OPT_TIME, " +
                "       ei.OPTER_NAME, " +
                "       ei.OPTER_ID, " +
                "       ei.RID, " +
                "       ei.INSU_EMP_MGT_EID, " +
                "       ei.OPT_CHNL, " +
                "       ei.DSG_ID, " +
                "       INSUTYPE.NAT_DIC_VAL_NAME INSUTYPE_NAME, " +
                "       HI_TYPE.NAT_DIC_VAL_NAME HI_TYPE_NAME, " +
                "       EMP_INSU_STAS.NAT_DIC_VAL_NAME EMP_INSU_STAS_NAME, " +
                "       CLCT_WAY.NAT_DIC_VAL_NAME CLCT_WAY_NAME " +
                "FROM INSUCENT_DB.EMP_INSU_D ei ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE " +
                "on ei.INSUTYPE=INSUTYPE.NAT_DIC_VAL_CODE " +
                "and INSUTYPE.DIC_TYPE_CODE='INSUTYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A HI_TYPE " +
                "on ei.HI_TYPE=HI_TYPE.NAT_DIC_VAL_CODE " +
                "and HI_TYPE.DIC_TYPE_CODE='HI_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A EMP_INSU_STAS " +
                "on ei.EMP_INSU_STAS=EMP_INSU_STAS.NAT_DIC_VAL_CODE " +
                "and EMP_INSU_STAS.DIC_TYPE_CODE='EMP_INSU_STAS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A CLCT_WAY " +
                "on ei.CLCT_WAY=CLCT_WAY.NAT_DIC_VAL_CODE " +
                "and CLCT_WAY.DIC_TYPE_CODE='CLCT_WAY' ";


        sql += "where 1 = 1 ";
        if (StringUtils.isNotEmpty(empInsuD.getEmp_no())) {
            sql += "and ei.emp_no = '" + empInsuD.getEmp_no() + "' ";
        }
        if (StringUtils.isNotEmpty(empInsuD.getInsutype())) {
            sql += "and ei.insutype = '" + empInsuD.getInsutype() + "' ";
        }
        return sql;
    }

    public String insuEmpInfoB(Page page,InsuEmpInfoB insuEmpInfoB) {
        String sql = "SELECT iei.EMP_NO, " +
                "       iei.EMP_ENTT_CODG, " +
                "       iei.EMP_MGT_TYPE, " +
                "       iei.PRNT_EMP_NO, " +
                "       iei.EMP_TYPE, " +
                "       iei.ASOC_LEGENT_FLAG, " +
                "       iei.EMP_NAME, " +
                "       iei.REG_NAME, " +
                "       iei.LOC_ADMDVS, " +
                "       iei.CONER_NAME, " +
                "       iei.CONER_EMAIL, " +
                "       iei.TEL, " +
                "       iei.FAX_NO, " +
                "       iei.TAX_REG_NO, " +
                "       iei.ORGCODE, " +
                "       iei.REGNO, " +
                "       iei.REGNO_CERT_TYPE, " +
                "       iei.EMP_ADDR, " +
                "       iei.POSCODE, " +
                "       iei.APRV_ESTA_DEPT, " +
                "       iei.APRV_ESTA_DATE, " +
                "       iei.APRV_ESTA_DOCNO, " +
                "       iei.PRNT_ADMDVS, " +
                "       iei.INSU_ADMDVS, " +
                "       iei.ORG_VALI_STAS, " +
                "       iei.LEGREP_TEL, " +
                "       iei.LEGREP_NAME, " +
                "       iei.LEGREP_CERTNO, " +
                "       iei.LEGREP_CERT_TYPE, " +
                "       iei.ORGCODE_ISSU_EMP, " +
                "       iei.VALI_FLAG, " +
                "       iei.RID, " +
                "       iei.CRTE_TIME, " +
                "       iei.UPDT_TIME, " +
                "       iei.CRTER_NAME, " +
                "       iei.CRTER_ID, " +
                "       iei.CRTE_OPTINS_NO, " +
                "       iei.OPTER_ID, " +
                "       iei.OPTER_NAME, " +
                "       iei.OPT_TIME, " +
                "       iei.OPTINS_NO, " +
                "       iei.POOLAREA_NO, " +
                "       iei.VER, " +
                "       iei.SYNC_PRNT_FLAG, " +
                "       iei.ENTP_SPEC_FLAG, " +
                "       iei.DSG_ID " +
                "FROM CUSTCENT_DB.INSU_EMP_INFO_B iei";
        return sql;
    }

    public String medinsInfoB(Page page,MedinsInfoB medinsInfoB) {
        String sql = "SELECT mi.MEDINS_CODE, " +
                "       mi.USCC, " +
                "       mi.MEDINS_NAME, " +
                "       mi.MEDINS_ABBR, " +
                "       mi.FAX_NO, " +
                "       mi.MAIN_RESPER, " +
                "       mi.ADMDVS, " +
                "       mi.ADDR, " +
                "       mi.MEDINS_TYPE, " +
//        "       mi.MEDINS_TYPE_NAME, " +
                "       mi.FIXMEDINS_TYPE, " +
                "       mi.REG_STAS, " +
                "       mi.ENRD_STAF_PSNCNT, " +
                "       mi.HOSP_DEPT_CNT, " +
                "       mi.HOSP_KEY_DEPT_CNT, " +
                "       mi.SENR_PROFTTL_PSNCNT, " +
                "       mi.MID_PROFTTL_PSNCNT, " +
                "       mi.PRO_TECHSTF_PSNCNT, " +
                "       mi.APRV_BED_CNT, " +
                "       mi.DEPSENR_PROFTTL_PSNCNT, " +
                "       mi.OTH_PSNCNT, " +
                "       mi.BIZ_PSNCNT, " +
                "       mi.BIZ_AREA, " +
                "       mi.MEDINSLV, " +
                "       mi.HOSP_LV, " +
                "       mi.LNT, " +
                "       mi.LAT, " +
                "       mi.BEGNTIME, " +
                "       mi.ENDTIME, " +
                "       mi.MEMO, " +
                "       mi.GRST_HOSP_FLAG, " +
                "       mi.CRED_LV, " +
//        "       mi.CRED_LV_NAME, " +
                "       mi.PRNT_MEDINS_CODE, " +
                "       mi.VALI_FLAG, " +
                "       mi.MEDINS_NATU, " +
//        "       mi.MEDINS_NATU_NAME, " +
                "       mi.NPMO_FLAG, " +
                "       mi.RID, " +
                "       mi.CRTER_ID, " +
                "       mi.CRTER_NAME, " +
                "       mi.CRTE_TIME, " +
                "       mi.UPDT_TIME, " +
                "       mi.CRTE_OPTINS_NO, " +
                "       mi.OPTER_ID, " +
                "       mi.OPTER_NAME, " +
                "       mi.OPT_TIME, " +
                "       mi.OPTINS_NO, " +
                "       mi.SYNC_PRNT_FLAG, " +
                "       mi.VER, " +
                "       mi.MEDINS_GRADE, " +
                "       mi.VER_RID, " +
                "       mi.VER_NAME, " +
                "       mi.MEDINS_INFO_ID, " +
                "       mi.LEGENT_NAME, " +
                "       mi.LEGREP_NAME, " +
                "       mi.MEDINS_PRAC_LIC_REGNO, " +
                "       mi.BIZNAT, " +
                "       mi.ECON_TYPE, " +
                "       mi.AFIL_RLTS, " +
                "       mi.TRTITEM, " +
                "       mi.MEDINS_PRAC_LIC_EXPY, " +
                "       mi.BANK_NAME, " +
                "       mi.BANKACCT, " +
                "       mi.BANK, " +
                "       mi.INCHG_HOSP_RESPER_NAME, " +
                "       mi.INCHG_HOSP_RESPER_TEL, " +
                "       mi.HI_RESPER_NAME, " +
                "       mi.HI_RESPER_TEL, " +
                "       mi.HI_TEL, " +
                "       mi.HI_EMAIL, " +
                "       mi.MEDINS_CERT_ELEC_DOC, " +
                "       mi.MEDINS_PRAC_LIC_ELEC_DOC, " +
                "       mi.DSG_ID, " +
                "       mi.ASGCODE_DR_CNT, " +
                "       mi.ASGCODE_NURS_CNT, " +
                "       mi.ACT_ADDR_INFO, " +
                "       mi.ACT_ADDR_CODE, " +
                "       mi.EC_OPEN_FLAG, " +
                "       mi.DIF_DSCR, " +
                "       MEDINSLV.NAT_DIC_VAL_NAME MEDINSLV_NAME, " +
                "       AFIL_RLTS.NAT_DIC_VAL_NAME AFIL_RLTS_NAME, " +
                "       ECON_TYPE.NAT_DIC_VAL_NAME ECON_TYPE_NAME, " +
                "       MEDINS_GRADE.NAT_DIC_VAL_NAME MEDINS_GRADE_NAME, " +
                "       HOSP_LV.NAT_DIC_VAL_NAME HOSP_LV_NAME, " +
                "       SYNC_PRNT_FLAG.NAT_DIC_VAL_NAME SYNC_PRNT_FLAG_NAME, " +
                "       BIZNAT.NAT_DIC_VAL_NAME BIZNAT_NAME, " +
                "       MEDINS_TYPE.NAT_DIC_VAL_NAME MEDINS_TYPE_NAME, " +
                "       CRED_LV.NAT_DIC_VAL_NAME CRED_LV_NAME, " +
                "       MEDINS_NATU.NAT_DIC_VAL_NAME MEDINS_NATU_NAME " +
                "FROM CUSTCENT_DB.MEDINS_INFO_B mi ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINSLV " +
                "on mi.MEDINSLV=MEDINSLV.NAT_DIC_VAL_CODE " +
                "and MEDINSLV.DIC_TYPE_CODE='MEDINSLV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AFIL_RLTS " +
                "on mi.AFIL_RLTS=AFIL_RLTS.NAT_DIC_VAL_CODE " +
                "and AFIL_RLTS.DIC_TYPE_CODE='AFIL_RLTS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ECON_TYPE " +
                "on mi.ECON_TYPE=ECON_TYPE.NAT_DIC_VAL_CODE " +
                "and ECON_TYPE.DIC_TYPE_CODE='ECON_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_GRADE " +
                "on mi.MEDINS_GRADE=MEDINS_GRADE.NAT_DIC_VAL_CODE " +
                "and MEDINS_GRADE.DIC_TYPE_CODE='MEDINS_GRADE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A SYNC_PRNT_FLAG " +
                "on mi.SYNC_PRNT_FLAG=SYNC_PRNT_FLAG.NAT_DIC_VAL_CODE " +
                "and SYNC_PRNT_FLAG.DIC_TYPE_CODE='SYNC_PRNT_FLAG' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A HOSP_LV " +
                "on mi.HOSP_LV=HOSP_LV.NAT_DIC_VAL_CODE " +
                "and HOSP_LV.DIC_TYPE_CODE='HOSP_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A BIZNAT " +
                "on mi.BIZNAT=BIZNAT.NAT_DIC_VAL_CODE " +
                "and BIZNAT.DIC_TYPE_CODE='BIZNAT' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_TYPE " +
                "on mi.MEDINS_TYPE=MEDINS_TYPE.NAT_DIC_VAL_CODE " +
                "and MEDINS_TYPE.DIC_TYPE_CODE='MEDINS_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A CRED_LV " +
                "on mi.CRED_LV=CRED_LV.NAT_DIC_VAL_CODE " +
                "and CRED_LV.DIC_TYPE_CODE='CRED_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_NATU " +
                "on mi.MEDINS_NATU=MEDINS_NATU.NAT_DIC_VAL_CODE " +
                "and MEDINS_NATU.DIC_TYPE_CODE='MEDINS_NATU' ";


        sql += "where mi.vali_flag = 1";
        if (StringUtils.isNotEmpty(medinsInfoB.getMedins_code())) {
            sql += "and mi.medins_code like '%" + medinsInfoB.getMedins_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(medinsInfoB.getMedins_name())) {
            sql += "and mi.medins_name like '%" + medinsInfoB.getMedins_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(medinsInfoB.getCred_lv())) {
            sql += " and mi.cred_lv in (" + medinsInfoB.getCred_lv() + ") ";
        }
        return sql;
    }

    public String medicalContrast(Page page,MedinsInfoB medinsInfoB) {
        String sql = "SELECT mi.MEDINS_CODE, " +
                "       mi.USCC, " +
                "       mi.MEDINS_NAME, " +
                "       mi.MEDINS_ABBR, " +
                "       mi.FAX_NO, " +
                "       mi.MAIN_RESPER, " +
                "       mi.ADMDVS, " +
                "       mi.ADDR, " +
                "       mi.MEDINS_TYPE, " +
//        "       mi.MEDINS_TYPE_NAME, " +
                "       mi.FIXMEDINS_TYPE, " +
                "       mi.REG_STAS, " +
                "       mi.ENRD_STAF_PSNCNT, " +
                "       mi.HOSP_DEPT_CNT, " +
                "       mi.HOSP_KEY_DEPT_CNT, " +
                "       mi.SENR_PROFTTL_PSNCNT, " +
                "       mi.MID_PROFTTL_PSNCNT, " +
                "       mi.PRO_TECHSTF_PSNCNT, " +
                "       mi.APRV_BED_CNT, " +
                "       mi.DEPSENR_PROFTTL_PSNCNT, " +
                "       mi.OTH_PSNCNT, " +
                "       mi.BIZ_PSNCNT, " +
                "       mi.BIZ_AREA, " +
                "       mi.MEDINSLV, " +
                "       mi.HOSP_LV, " +
                "       mi.LNT, " +
                "       mi.LAT, " +
                "       mi.BEGNTIME, " +
                "       mi.ENDTIME, " +
                "       mi.MEMO, " +
                "       mi.GRST_HOSP_FLAG, " +
                "       mi.CRED_LV, " +
//        "       mi.CRED_LV_NAME, " +
                "       mi.PRNT_MEDINS_CODE, " +
                "       mi.VALI_FLAG, " +
                "       mi.MEDINS_NATU, " +
//        "       mi.MEDINS_NATU_NAME, " +
                "       mi.NPMO_FLAG, " +
                "       mi.RID, " +
                "       mi.CRTER_ID, " +
                "       mi.CRTER_NAME, " +
                "       mi.CRTE_TIME, " +
                "       mi.UPDT_TIME, " +
                "       mi.CRTE_OPTINS_NO, " +
                "       mi.OPTER_ID, " +
                "       mi.OPTER_NAME, " +
                "       mi.OPT_TIME, " +
                "       mi.OPTINS_NO, " +
                "       mi.SYNC_PRNT_FLAG, " +
                "       mi.VER, " +
                "       mi.MEDINS_GRADE, " +
                "       mi.VER_RID, " +
                "       mi.VER_NAME, " +
                "       mi.MEDINS_INFO_ID, " +
                "       mi.LEGENT_NAME, " +
                "       mi.LEGREP_NAME, " +
                "       mi.MEDINS_PRAC_LIC_REGNO, " +
                "       mi.BIZNAT, " +
                "       mi.ECON_TYPE, " +
                "       mi.AFIL_RLTS, " +
                "       mi.TRTITEM, " +
                "       mi.MEDINS_PRAC_LIC_EXPY, " +
                "       mi.BANK_NAME, " +
                "       mi.BANKACCT, " +
                "       mi.BANK, " +
                "       mi.INCHG_HOSP_RESPER_NAME, " +
                "       mi.INCHG_HOSP_RESPER_TEL, " +
                "       mi.HI_RESPER_NAME, " +
                "       mi.HI_RESPER_TEL, " +
                "       mi.HI_TEL, " +
                "       mi.HI_EMAIL, " +
                "       mi.MEDINS_CERT_ELEC_DOC, " +
                "       mi.MEDINS_PRAC_LIC_ELEC_DOC, " +
                "       mi.DSG_ID, " +
                "       mi.ASGCODE_DR_CNT, " +
                "       mi.ASGCODE_NURS_CNT, " +
                "       mi.ACT_ADDR_INFO, " +
                "       mi.ACT_ADDR_CODE, " +
                "       mi.EC_OPEN_FLAG, " +
                "       mi.DIF_DSCR, " +
                "       MEDINSLV.NAT_DIC_VAL_NAME MEDINSLV_NAME, " +
                "       AFIL_RLTS.NAT_DIC_VAL_NAME AFIL_RLTS_NAME, " +
                "       ECON_TYPE.NAT_DIC_VAL_NAME ECON_TYPE_NAME, " +
                "       MEDINS_GRADE.NAT_DIC_VAL_NAME MEDINS_GRADE_NAME, " +
                "       HOSP_LV.NAT_DIC_VAL_NAME HOSP_LV_NAME, " +
                "       SYNC_PRNT_FLAG.NAT_DIC_VAL_NAME SYNC_PRNT_FLAG_NAME, " +
                "       BIZNAT.NAT_DIC_VAL_NAME BIZNAT_NAME, " +
                "       MEDINS_TYPE.NAT_DIC_VAL_NAME MEDINS_TYPE_NAME, " +
                "       CRED_LV.NAT_DIC_VAL_NAME CRED_LV_NAME, " +
                "       MEDINS_NATU.NAT_DIC_VAL_NAME MEDINS_NATU_NAME " +
                "FROM CUSTCENT_DB.MEDINS_INFO_B mi ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINSLV " +
                "on mi.MEDINSLV=MEDINSLV.NAT_DIC_VAL_CODE " +
                "and MEDINSLV.DIC_TYPE_CODE='MEDINSLV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AFIL_RLTS " +
                "on mi.AFIL_RLTS=AFIL_RLTS.NAT_DIC_VAL_CODE " +
                "and AFIL_RLTS.DIC_TYPE_CODE='AFIL_RLTS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ECON_TYPE " +
                "on mi.ECON_TYPE=ECON_TYPE.NAT_DIC_VAL_CODE " +
                "and ECON_TYPE.DIC_TYPE_CODE='ECON_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_GRADE " +
                "on mi.MEDINS_GRADE=MEDINS_GRADE.NAT_DIC_VAL_CODE " +
                "and MEDINS_GRADE.DIC_TYPE_CODE='MEDINS_GRADE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A SYNC_PRNT_FLAG " +
                "on mi.SYNC_PRNT_FLAG=SYNC_PRNT_FLAG.NAT_DIC_VAL_CODE " +
                "and SYNC_PRNT_FLAG.DIC_TYPE_CODE='SYNC_PRNT_FLAG' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A HOSP_LV " +
                "on mi.HOSP_LV=HOSP_LV.NAT_DIC_VAL_CODE " +
                "and HOSP_LV.DIC_TYPE_CODE='HOSP_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A BIZNAT " +
                "on mi.BIZNAT=BIZNAT.NAT_DIC_VAL_CODE " +
                "and BIZNAT.DIC_TYPE_CODE='BIZNAT' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_TYPE " +
                "on mi.MEDINS_TYPE=MEDINS_TYPE.NAT_DIC_VAL_CODE " +
                "and MEDINS_TYPE.DIC_TYPE_CODE='MEDINS_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A CRED_LV " +
                "on mi.CRED_LV=CRED_LV.NAT_DIC_VAL_CODE " +
                "and CRED_LV.DIC_TYPE_CODE='CRED_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_NATU " +
                "on mi.MEDINS_NATU=MEDINS_NATU.NAT_DIC_VAL_CODE " +
                "and MEDINS_NATU.DIC_TYPE_CODE='MEDINS_NATU' ";


        sql += "where mi.vali_flag = 1";
        if (StringUtils.isNotEmpty(medinsInfoB.getMedins_code())) {
            sql += "and mi.medins_code like '%" + medinsInfoB.getMedins_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(medinsInfoB.getMedins_name())) {
            sql += "and mi.medins_name like '%" + medinsInfoB.getMedins_name() + "%' ";
        }
        return sql;
    }

    public String medTecnInfoB(Page page,MedTecnInfoB medTecnInfoB) {
        String sql = "SELECT mti.MED_TECN_CODE, " +
                "       mti.PSN_NO, " +
                "       mti.MED_TECN_NAME, " +
                "       mti.PSN_ITRO, " +
                "       mti.PRAC_TYPE, " +
                "       mti.PRAC_SCP, " +
                "       mti.PRAC_REGN_ADMDVS, " +
                "       mti.TECN_PRO_TECH_DUTY, " +
                "       mti.PRACINS_NO, " +
                "       mti.PRACINS_NAME, " +
                "       mti.PRACINS_ADDR, " +
                "       mti.NEMP_TIME, " +
                "       mti.VALI_FLAG, " +
                "       mti.RID, " +
                "       mti.CRTE_TIME, " +
                "       mti.UPDT_TIME, " +
                "       mti.CRTER_ID, " +
                "       mti.CRTER_NAME, " +
                "       mti.CRTE_OPTINS_NO, " +
                "       mti.OPTER_ID, " +
                "       mti.OPTER_NAME, " +
                "       mti.OPT_TIME, " +
                "       mti.OPTINS_NO, " +
                "       mti.VER, " +
                "       mti.SYNC_PRNT_FLAG, " +
                "       mti.DSG_ID, " +
                "       PRAC_TYPE.NAT_DIC_VAL_NAME PRAC_TYPE_NAME, " +
                "       PRAC_SCP.NAT_DIC_VAL_NAME PRAC_SCP_NAME, " +
                "       TECN_PRO_TECH_DUTY.NAT_DIC_VAL_NAME TECN_PRO_TECH_DUTY_NAME, " +
                "       SYNC_PRNT_FLAG.NAT_DIC_VAL_NAME SYNC_PRNT_FLAG_NAME " +
                "FROM CUSTCENT_DB.MED_TECN_INFO_B mti ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PRAC_TYPE " +
                "on mti.PRAC_TYPE=PRAC_TYPE.NAT_DIC_VAL_CODE " +
                "and PRAC_TYPE.DIC_TYPE_CODE='PRAC_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PRAC_SCP " +
                "on mti.PRAC_SCP=PRAC_SCP.NAT_DIC_VAL_CODE " +
                "and PRAC_SCP.DIC_TYPE_CODE='PRAC_SCP' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A TECN_PRO_TECH_DUTY " +
                "on mti.TECN_PRO_TECH_DUTY=TECN_PRO_TECH_DUTY.NAT_DIC_VAL_CODE " +
                "and TECN_PRO_TECH_DUTY.DIC_TYPE_CODE='TECN_PRO_TECH_DUTY' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A SYNC_PRNT_FLAG " +
                "on mti.SYNC_PRNT_FLAG=SYNC_PRNT_FLAG.NAT_DIC_VAL_CODE " +
                "and SYNC_PRNT_FLAG.DIC_TYPE_CODE='SYNC_PRNT_FLAG' ";

        sql += "where mti.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(medTecnInfoB.getMed_tecn_code())) {
            sql += "and mti.med_tecn_code like " + "'%" + medTecnInfoB.getMed_tecn_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(medTecnInfoB.getMed_tecn_code())) {
            sql += "and mti.med_tecn_name like " + "'%" + medTecnInfoB.getMed_tecn_code() + "%' ";
        }


        return sql;
    }

    public String nursInfoB(Page page,NursInfoB nursInfoB) {
        String sql = "SELECT ni.NURS_CODE, " +
                "       ni.PSN_NO, " +
                "       ni.NURS_NAME, " +
                "       ni.NURS_PRAC_CERT_NO, " +
                "       ni.PRACINS_NO, " +
                "       ni.PRACINS_ADDR, " +
                "       ni.PRACINS_NAME, " +
                "       ni.NEMP_TIME, " +
                "       ni.VALI_FLAG, " +
                "       ni.NURS_PRO_TECH_DUTY, " +
                "       ni.NURS_PRO_TECH_DUTY_NAME, " +
                "       ni.RID, " +
                "       ni.CRTE_TIME, " +
                "       ni.UPDT_TIME, " +
                "       ni.CRTER_ID, " +
                "       ni.CRTER_NAME, " +
                "       ni.CRTE_OPTINS_NO, " +
                "       ni.OPTER_ID, " +
                "       ni.OPT_TIME, " +
                "       ni.OPTER_NAME, " +
                "       ni.OPTINS_NO, " +
                "       ni.VER, " +
                "       ni.ADMDVS, " +
                "       ni.SYNC_PRNT_FLAG, " +
                "       ni.CRED_LV, " +
                "       ni.VOLA_BHVR, " +
                "       ni.PRAC_STAS, " +
                "       ni.VER_RID, " +
                "       ni.VER_NAME, " +
                "       ni.NURS_ID, " +
                "       ni.CNTR_BEGNTIME, " +
                "       ni.PSN_STAS, " +
                "       ni.CNTR_ENDTIME, " +
                "       ni.PRAC_TYPE, " +
                "       ni.HI_NURS_REGCERT_ELEC_DOC, " +
                "       ni.DSG_ID, " +
                "       PRAC_TYPE.NAT_DIC_VAL_NAME PRAC_TYPE_NAME, " +
                "       PSN_STAS.NAT_DIC_VAL_NAME PSN_STAS_NAME, " +
                "       PRAC_STAS.NAT_DIC_VAL_NAME PRAC_STAS_NAME, " +
                "       CRED_LV.NAT_DIC_VAL_NAME CRED_LV_NAME, " +
                "       SYNC_PRNT_FLAG.NAT_DIC_VAL_NAME SYNC_PRNT_FLAG_NAME " +
                "FROM CUSTCENT_DB.NURS_INFO_B ni ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PRAC_TYPE " +
                "on ni.PRAC_TYPE=PRAC_TYPE.NAT_DIC_VAL_CODE " +
                "and PRAC_TYPE.DIC_TYPE_CODE='PRAC_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PSN_STAS " +
                "on ni.PSN_STAS=PSN_STAS.NAT_DIC_VAL_CODE " +
                "and PSN_STAS.DIC_TYPE_CODE='PSN_STAS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PRAC_STAS " +
                "on ni.PRAC_STAS=PRAC_STAS.NAT_DIC_VAL_CODE " +
                "and PRAC_STAS.DIC_TYPE_CODE='PRAC_STAS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A CRED_LV " +
                "on ni.CRED_LV=CRED_LV.NAT_DIC_VAL_CODE " +
                "and CRED_LV.DIC_TYPE_CODE='CRED_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A SYNC_PRNT_FLAG " +
                "on ni.SYNC_PRNT_FLAG=SYNC_PRNT_FLAG.NAT_DIC_VAL_CODE " +
                "and SYNC_PRNT_FLAG.DIC_TYPE_CODE='SYNC_PRNT_FLAG' ";

        sql += "where ni.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(nursInfoB.getNurs_code())) {
            sql += "and ni.nurs_code like " + "'%" + nursInfoB.getNurs_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(nursInfoB.getNurs_name())) {
            sql += "and ni.nurs_name like " + "'%" + nursInfoB.getNurs_name() + "%' ";
        }

        return sql;
    }

    public String opspRegEvtC(Page page,OpspRegEvtC opspRegEvtC) {
        String sql = "SELECT ore.EVTSN, " +
                "       ore.TRT_DCLA_DETL_SN, " +
                "       ore.OPSP_DISE_CODE, " +
                "       ore.SERV_MATT_INST_ID, " +
                "       ore.SERV_MATT_NODE_INST_ID, " +
                "       ore.EVT_INST_ID, " +
                "       ore.EVT_TYPE, " +
                "       ore.INSUTYPE, " +
                "       ore.DCLA_SOUC, " +
                "       ore.PSN_INSU_RLTS_ID, " +
                "       ore.PSN_NO, " +
                "       ore.DISE_TYPE_CODE, " +
                "       ore.PSN_CERT_TYPE, " +
                "       ore.OPSP_DISE_NAME, " +
                "       ore.CERTNO, " +
                "       ore.PSN_NAME, " +
                "       ore.GEND, " +
                "       ore.NATY, " +
                "       ore.BRDY, " +
                "       ore.TEL, " +
                "       ore.ADDR, " +
                "       ore.INSU_ADMDVS, " +
                "       ore.EMP_NAME, " +
                "       ore.EMP_NO, " +
                "       ore.IDE_FIXMEDINS_NO, " +
                "       ore.HOSP_IDE_DATE, " +
                "       ore.IDE_FIXMEDINS_NAME, " +
                "       ore.DIAG_DR_CODE, " +
                "       ore.DIAG_DR_NAME, " +
                "       ore.APPY_DATE, " +
                "       ore.APPY_REA, " +
                "       ore.AGNTER_NAME, " +
                "       ore.AGNTER_CERT_TYPE, " +
                "       ore.AGNTER_CERTNO, " +
                "       ore.AGNTER_TEL, " +
                "       ore.AGNTER_ADDR, " +
                "       ore.AGNTER_RLTS, " +
                "       ore.BEGNDATE, " +
                "       ore.ENDDATE, " +
                "       ore.VALI_FLAG, " +
                "       ore.RCHK_FLAG, " +
                "       ore.MEMO, " +
                "       ore.RID, " +
                "       ore.CRTER_ID, " +
                "       ore.UPDT_TIME, " +
                "       ore.CRTER_NAME, " +
                "       ore.CRTE_TIME, " +
                "       ore.CRTE_OPTINS_NO, " +
                "       ore.OPTER_ID, " +
                "       ore.OPTER_NAME, " +
                "       ore.OPT_TIME, " +
                "       ore.OPTINS_NO, " +
                "       ore.POOLAREA_NO, " +

                "       AGNTER_CERT_TYPE.NAT_DIC_VAL_NAME AGNTER_CERT_TYPE_NAME, " +
                "       AGNTER_RLTS.NAT_DIC_VAL_NAME AGNTER_RLTS_NAME, " +
                "       EVT_TYPE.NAT_DIC_VAL_NAME EVT_TYPE_NAME, " +
                "       RCHK_FLAG.NAT_DIC_VAL_NAME RCHK_FLAG_NAME, " +
                "       NATY.NAT_DIC_VAL_NAME NATY_NAME, " +
                "       GEND.NAT_DIC_VAL_NAME GEND_NAME, " +
                "       PSN_CERT_TYPE.NAT_DIC_VAL_NAME PSN_CERT_TYPE_NAME, " +
                "       DCLA_SOUC.NAT_DIC_VAL_NAME DCLA_SOUC_NAME, " +
                "       INSUTYPE.NAT_DIC_VAL_NAME INSUTYPE_NAME, " +

                "       DISE_TYPE_CODE.NAT_DIC_VAL_NAME DISE_TYPE_CODE_NAME " +
                "FROM hibiz_db.opsp_reg_evt_c ore ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A DISE_TYPE_CODE " +
                "on ore.DISE_TYPE_CODE=DISE_TYPE_CODE.NAT_DIC_VAL_CODE " +
                "and DISE_TYPE_CODE.DIC_TYPE_CODE='DISE_TYPE_CODE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AGNTER_CERT_TYPE " +
                "on ore.AGNTER_CERT_TYPE=AGNTER_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and AGNTER_CERT_TYPE.DIC_TYPE_CODE='AGNTER_CERT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AGNTER_RLTS " +
                "on ore.AGNTER_RLTS=AGNTER_RLTS.NAT_DIC_VAL_CODE " +
                "and AGNTER_RLTS.DIC_TYPE_CODE='AGNTER_RLTS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A EVT_TYPE " +
                "on ore.EVT_TYPE=EVT_TYPE.NAT_DIC_VAL_CODE " +
                "and EVT_TYPE.DIC_TYPE_CODE='EVT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A RCHK_FLAG " +
                "on ore.RCHK_FLAG=RCHK_FLAG.NAT_DIC_VAL_CODE " +
                "and RCHK_FLAG.DIC_TYPE_CODE='RCHK_FLAG' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A NATY " +
                "on ore.NATY=NATY.NAT_DIC_VAL_CODE " +
                "and NATY.DIC_TYPE_CODE='NATY' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A GEND " +
                "on ore.GEND=GEND.NAT_DIC_VAL_CODE " +
                "and GEND.DIC_TYPE_CODE='GEND' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PSN_CERT_TYPE " +
                "on ore.PSN_CERT_TYPE=PSN_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and PSN_CERT_TYPE.DIC_TYPE_CODE='PSN_CERT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A DCLA_SOUC " +
                "on ore.DCLA_SOUC=DCLA_SOUC.NAT_DIC_VAL_CODE " +
                "and DCLA_SOUC.DIC_TYPE_CODE='DCLA_SOUC' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE " +
                "on ore.INSUTYPE=INSUTYPE.NAT_DIC_VAL_CODE " +
                "and INSUTYPE.DIC_TYPE_CODE='INSUTYPE' ";

        sql += "where ore.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(opspRegEvtC.getCertno())) {
            sql += "and ore.certno like '%" + opspRegEvtC.getCertno() + "%' ";
        }
        if (StringUtils.isNotEmpty(opspRegEvtC.getPsn_name())) {
            sql += "and ore.psn_name like '%" + opspRegEvtC.getPsn_name() + "%' ";
        }
        return sql;
    }

    public String optPsnB(Page page,OptPsnB optPsnB) {
        String sql = "SELECT op.OPTER_NO, " +
                "       op.PSN_NO, " +
                "       op.PSN_NAME, " +
                "       op.AFIL_OPTINS_NO, " +
                "       op.VALI_FLAG, " +
                "       op.MEMO, " +
                "       op.OPTER_TYPE, " +
                "       op.CERTNO, " +
                "       op.PSN_CERT_TYPE, " +
                "       op.TEL, " +
                "       op.MOB, " +
                "       op.RID, " +
                "       op.CRTE_TIME, " +
                "       op.UPDT_TIME, " +
                "       op.CRTER_ID, " +
                "       op.CRTER_NAME, " +
                "       op.CRTE_OPTINS_NO, " +
                "       op.OPTER_ID, " +
                "       op.OPTER_NAME, " +
                "       op.OPT_TIME, " +
                "       op.OPTINS_NO, " +
                "       op.VER, " +
                "       op.ADMDVS, " +
                "       op.SYNC_PRNT_FLAG, " +
                "       op.VER_RID, " +
                "       op.PSN_STAS, " +
                "       op.VER_NAME, " +
                "       op.OPTER_PSN_ID, " +
                "       op.EMP_NAME, " +
                "       op.GEND, " +
                "       op.NATY, " +
                "       op.POLSTAS, " +
                "       op.EDUC, " +
                "       op.PRO, " +
                "       op.PROFTTL, " +
                "       op.DEPT, " +
                "       op.DUTY, " +
                "       op.DUTY_RANK, " +
                "       op.PATC_JOB_TIME, " +
                "       op.DSG_ID, " +
                "       PSN_CERT_TYPE.NAT_DIC_VAL_NAME PSN_CERT_TYPE_NAME, " +
                "       SYNC_PRNT_FLAG.NAT_DIC_VAL_NAME SYNC_PRNT_FLAG_NAME " +
                "FROM CUSTCENT_DB.OPT_PSN_B op ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PSN_CERT_TYPE " +
                "on op.PSN_CERT_TYPE=PSN_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and PSN_CERT_TYPE.DIC_TYPE_CODE='PSN_CERT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A SYNC_PRNT_FLAG " +
                "on op.SYNC_PRNT_FLAG=SYNC_PRNT_FLAG.NAT_DIC_VAL_CODE " +
                "and SYNC_PRNT_FLAG.DIC_TYPE_CODE='SYNC_PRNT_FLAG' ";

        sql += "where op.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(optPsnB.getOpter_no())) {
            sql += "and op.opter_no like " + "'%" + optPsnB.getOpter_no() + "%' ";
        }
        if (StringUtils.isNotEmpty(optPsnB.getCertno())) {
            sql += "and op.certno like " + "'%" + optPsnB.getCertno() + "%' ";
        }
        if (StringUtils.isNotEmpty(optPsnB.getPsn_name())) {
            sql += "and op.psn_name like " + "'%" + optPsnB.getPsn_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(optPsnB.getEmp_name())) {
            sql += "and op.emp_name like " + "'%" + optPsnB.getEmp_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(optPsnB.getDuty())) {
            sql += "and op.duty like " + "'%" + optPsnB.getDuty() + "%' ";
        }

        return sql;
    }

    public String outAppyEvtC(Page page,OutAppyEvtC outAppyEvtC) {
        String sql = "SELECT oae.EVTSN, " +
                "       oae.TRT_DCLA_DETL_SN, " +
                "       oae.SERV_MATT_INST_ID, " +
                "       oae.SERV_MATT_NODE_INST_ID, " +
                "       oae.EVT_INST_ID, " +
                "       oae.EVT_TYPE, " +
                "       oae.INSUTYPE, " +
                "       oae.DCLA_SOUC, " +
                "       oae.PSN_NO, " +
                "       oae.PSN_INSU_RLTS_ID, " +
                "       oae.PSN_CERT_TYPE, " +
                "       oae.CERTNO, " +
                "       oae.PSN_NAME, " +
                "       oae.GEND, " +
                "       oae.NATY, " +
                "       oae.INSU_ADMDVS, " +
                "       oae.BRDY, " +
                "       oae.TEL, " +
                "       oae.EMP_NO, " +
                "       oae.EMP_NAME, " +
                "       oae.RLOC_ADMDVS, " +
                "       oae.RLOC_COTY_TYPE, " +
                "       oae.RLOC_HSORG_NAME, " +
                "       oae.RLOC_HSORG_CONER, " +
                "       oae.RLOC_HSORG_TEL, " +
                "       oae.OUT_ONLN_WAY, " +
                "       oae.RLOC_REA, " +
                "       oae.RESOUT_ADDR, " +
                "       oae.MEMO, " +
                "       oae.AGNTER_NAME, " +
                "       oae.AGNTER_CERT_TYPE, " +
                "       oae.AGNTER_CERTNO, " +
                "       oae.AGNTER_TEL, " +
                "       oae.AGNTER_ADDR, " +
                "       oae.AGNTER_RLTS, " +
                "       oae.BEGNDATE, " +
                "       oae.ENDDATE, " +
                "       oae.OUT_FIL_UPLD_STAS, " +
                "       oae.ATT_CNT, " +
                "       oae.VALI_FLAG, " +
                "       oae.RCHK_FLAG, " +
                "       oae.RID, " +
                "       oae.UPDT_TIME, " +
                "       oae.CRTER_ID, " +
                "       oae.CRTER_NAME, " +
                "       oae.CRTE_TIME, " +
                "       oae.CRTE_OPTINS_NO, " +
                "       oae.OPTER_ID, " +
                "       oae.OPTER_NAME, " +
                "       oae.OPT_TIME, " +
                "       oae.OPTINS_NO, " +
                "       oae.POOLAREA_NO, " +
                "       oae.TRAFOUT_FIXMEDINS_CODE, " +
                "       oae.TRAFOUT_FIXMEDINS_NAME, " +
                "       oae.DSG_ID, " +

                "       AGNTER_CERT_TYPE.NAT_DIC_VAL_NAME AGNTER_CERT_TYPE_NAME, " +
                "       AGNTER_RLTS.NAT_DIC_VAL_NAME AGNTER_RLTS_NAME, " +
                "       EVT_TYPE.NAT_DIC_VAL_NAME EVT_TYPE_NAME, " +
                "       RCHK_FLAG.NAT_DIC_VAL_NAME RCHK_FLAG_NAME, " +
                "       NATY.NAT_DIC_VAL_NAME NATY_NAME, " +
                "       GEND.NAT_DIC_VAL_NAME GEND_NAME, " +
                "       PSN_CERT_TYPE.NAT_DIC_VAL_NAME PSN_CERT_TYPE_NAME, " +
                "       DCLA_SOUC.NAT_DIC_VAL_NAME DCLA_SOUC_NAME, " +
                "       INSUTYPE.NAT_DIC_VAL_NAME INSUTYPE_NAME, " +

                "       OUT_FIL_UPLD_STAS.NAT_DIC_VAL_NAME OUT_FIL_UPLD_STAS_NAME, " +
                "       OUT_ONLN_WAY.NAT_DIC_VAL_NAME OUT_ONLN_WAY_NAME, " +
                "       RLOC_COTY_TYPE.NAT_DIC_VAL_NAME RLOC_COTY_TYPE_NAME, " +
                "       RLOC_REA.NAT_DIC_VAL_NAME RLOC_REA_NAME " +
                "FROM HIBIZ_DB.OUT_APPY_EVT_C oae ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A OUT_FIL_UPLD_STAS " +
                "on oae.OUT_FIL_UPLD_STAS=OUT_FIL_UPLD_STAS.NAT_DIC_VAL_CODE " +
                "and OUT_FIL_UPLD_STAS.DIC_TYPE_CODE='OUT_FIL_UPLD_STAS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A OUT_ONLN_WAY " +
                "on oae.OUT_ONLN_WAY=OUT_ONLN_WAY.NAT_DIC_VAL_CODE " +
                "and OUT_ONLN_WAY.DIC_TYPE_CODE='OUT_ONLN_WAY' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A RLOC_COTY_TYPE " +
                "on oae.RLOC_COTY_TYPE=RLOC_COTY_TYPE.NAT_DIC_VAL_CODE " +
                "and RLOC_COTY_TYPE.DIC_TYPE_CODE='RLOC_COTY_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A RLOC_REA " +
                "on oae.rloc_rea=RLOC_REA.NAT_DIC_VAL_CODE " +
                "and RLOC_REA.DIC_TYPE_CODE='RLOC_REA' ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AGNTER_CERT_TYPE " +
                "on oae.AGNTER_CERT_TYPE=AGNTER_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and AGNTER_CERT_TYPE.DIC_TYPE_CODE='AGNTER_CERT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AGNTER_RLTS " +
                "on oae.AGNTER_RLTS=AGNTER_RLTS.NAT_DIC_VAL_CODE " +
                "and AGNTER_RLTS.DIC_TYPE_CODE='AGNTER_RLTS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A EVT_TYPE " +
                "on oae.EVT_TYPE=EVT_TYPE.NAT_DIC_VAL_CODE " +
                "and EVT_TYPE.DIC_TYPE_CODE='EVT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A RCHK_FLAG " +
                "on oae.RCHK_FLAG=RCHK_FLAG.NAT_DIC_VAL_CODE " +
                "and RCHK_FLAG.DIC_TYPE_CODE='RCHK_FLAG' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A NATY " +
                "on oae.NATY=NATY.NAT_DIC_VAL_CODE " +
                "and NATY.DIC_TYPE_CODE='NATY' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A GEND " +
                "on oae.GEND=GEND.NAT_DIC_VAL_CODE " +
                "and GEND.DIC_TYPE_CODE='GEND' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PSN_CERT_TYPE " +
                "on oae.PSN_CERT_TYPE=PSN_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and PSN_CERT_TYPE.DIC_TYPE_CODE='PSN_CERT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A DCLA_SOUC " +
                "on oae.DCLA_SOUC=DCLA_SOUC.NAT_DIC_VAL_CODE " +
                "and DCLA_SOUC.DIC_TYPE_CODE='DCLA_SOUC' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE " +
                "on oae.INSUTYPE=INSUTYPE.NAT_DIC_VAL_CODE " +
                "and INSUTYPE.DIC_TYPE_CODE='INSUTYPE' ";

        sql += "where oae.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(outAppyEvtC.getCertno())) {
            sql += "and oae.certno like '%" + outAppyEvtC.getCertno() + "%' ";
        }
        if (StringUtils.isNotEmpty(outAppyEvtC.getPsn_name())) {
            sql += "and oae.psn_name like '%" + outAppyEvtC.getPsn_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(outAppyEvtC.getCrte_time())) {
            sql += " AND TO_CHAR( oae.CRTE_TIME, 'yyyy-MM-dd' ) = '" + outAppyEvtC.getCrte_time() + "' ";
        }
        if (StringUtils.isNotEmpty(outAppyEvtC.getEvt_type())) {
            sql += " AND evt_type = '" + outAppyEvtC.getEvt_type() + "' ";
        }
        return sql;
    }

    public String pharInfoB(Page page,PharInfoB pharInfoB) {
        String sql = "SELECT pi.PHAR_CODE, " +
                "       pi.PSN_NO, " +
                "       pi.PHAR_NAME, " +
                "       pi.PSN_ITRO, " +
                "       pi.PHAR_PRAC_TYPE, " +
//        "       pi.PHAR_PRAC_TYPE_NAME, " +
                "       pi.PHAR_PRAC_SCP, " +
//        "       pi.PHAR_PRAC_SCP_NAME, " +
                "       pi.PRAC_REGN, " +
                "       pi.PRACINS_NO, " +
                "       pi.PRACINS_NAME, " +
                "       pi.PRACINS_ADDR, " +
                "       pi.NEMP_TIME, " +
                "       pi.VALI_FLAG, " +
                "       pi.PHAR_TYPE, " +
//        "       pi.PHAR_PRO_TECH_DUTY_NAME, " +
                "       pi.PHAR_PRO_TECH_DUTY, " +
                "       pi.RID, " +
                "       pi.CRTE_TIME, " +
                "       pi.UPDT_TIME, " +
                "       pi.CRTER_ID, " +
                "       pi.CRTER_NAME, " +
                "       pi.CRTE_OPTINS_NO, " +
                "       pi.OPTER_ID, " +
                "       pi.OPTER_NAME, " +
                "       pi.OPT_TIME, " +
                "       pi.OPTINS_NO, " +
                "       pi.VER, " +
                "       pi.ADMDVS, " +
                "       pi.SYNC_PRNT_FLAG, " +
                "       pi.CRED_LV, " +
                "       pi.VOLA_BHVR, " +
                "       pi.PRAC_STAS, " +
                "       pi.VER_RID, " +
                "       pi.VER_NAME, " +
                "       pi.PHAR_ID, " +
                "       pi.PSN_STAS, " +
                "       pi.CNTR_BEGNTIME, " +
                "       pi.CNTR_ENDTIME, " +
                "       pi.PRACCERT_TYPE, " +
                "       pi.PHAR_PRAC_CERT_NO, " +
                "       pi.REGCERT_NO, " +
                "       pi.PHAR_PRAC_EMP, " +
                "       pi.REG_TIME, " +
                "       pi.EXPY_ENDTIME, " +
                "       pi.PHAR_CERT_ELEC_DOC, " +
                "       pi.PRAC_PHAR_REGCERT_ELEC_DOC, " +
                "       pi.DSG_ID, " +
                "       pi.DIF_DSCR, " +
                "       pi.CERT_ELEC_DOC, " +
                "       pi.CERT_NO, " +
                "       pi.CERT_TYPE, " +
                "       PHAR_PRAC_TYPE.NAT_DIC_VAL_NAME PHAR_PRAC_TYPE_NAME, " +
                "       CRED_LV.NAT_DIC_VAL_NAME CRED_LV_NAME, " +
                "       PHAR_PRO_TECH_DUTY.NAT_DIC_VAL_NAME PHAR_PRO_TECH_DUTY_NAME, " +
                "       PHAR_TYPE.NAT_DIC_VAL_NAME PHAR_TYPE_NAME, " +
                "       PHAR_PRAC_SCP.NAT_DIC_VAL_NAME PHAR_PRAC_SCP_NAME, " +
                "       SYNC_PRNT_FLAG.NAT_DIC_VAL_NAME SYNC_PRNT_FLAG_NAME " +
                "FROM CUSTCENT_DB.PHAR_INFO_B pi ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PHAR_PRAC_TYPE " +
                "on pi.PHAR_PRAC_TYPE=PHAR_PRAC_TYPE.NAT_DIC_VAL_CODE " +
                "and PHAR_PRAC_TYPE.DIC_TYPE_CODE='PHAR_PRAC_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A CRED_LV " +
                "on pi.CRED_LV=CRED_LV.NAT_DIC_VAL_CODE " +
                "and CRED_LV.DIC_TYPE_CODE='CRED_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PHAR_PRO_TECH_DUTY " +
                "on pi.PHAR_PRO_TECH_DUTY=PHAR_PRO_TECH_DUTY.NAT_DIC_VAL_CODE " +
                "and PHAR_PRO_TECH_DUTY.DIC_TYPE_CODE='PHAR_PRO_TECH_DUTY' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PHAR_TYPE " +
                "on pi.PHAR_TYPE=PHAR_TYPE.NAT_DIC_VAL_CODE " +
                "and PHAR_TYPE.DIC_TYPE_CODE='PHAR_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PHAR_PRAC_SCP " +
                "on pi.PHAR_PRAC_SCP=PHAR_PRAC_SCP.NAT_DIC_VAL_CODE " +
                "and PHAR_PRAC_SCP.DIC_TYPE_CODE='PHAR_PRAC_SCP' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A SYNC_PRNT_FLAG " +
                "on pi.SYNC_PRNT_FLAG=SYNC_PRNT_FLAG.NAT_DIC_VAL_CODE " +
                "and SYNC_PRNT_FLAG.DIC_TYPE_CODE='SYNC_PRNT_FLAG' ";

        sql += "where pi.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(pharInfoB.getPhar_code())) {
            sql += "and pi.phar_code like " + "'%" + pharInfoB.getPhar_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(pharInfoB.getPhar_name())) {
            sql += "and pi.phar_name like " + "'%" + pharInfoB.getPhar_name() + "%' ";
        }

        return sql;
    }

    public String profInfoB(Page page,ProfInfoB profInfoB) {
        String sql = "SELECT pi.PROF_NO, " +
                "       pi.PSN_NO, " +
                "       pi.PROF_NAME, " +
                "       pi.OFF_TEL, " +
                "       pi.GEND, " +
                "       pi.CURR_DUTY, " +
                "       pi.PROF_TYPE, " +
                "       pi.MEDINS_DEPT, " +
                "       pi.PROF_DOMA, " +
                "       pi.PROFAREA, " +
                "       pi.PRO_TECH_PROFTTL, " +
                "       pi.USCC, " +
                "       pi.EMP_NAME, " +
                "       pi.PROFTTL_JOB_BEGNTIME, " +
                "       pi.ITRO, " +
                "       pi.EMP_ADDR, " +
                "       pi.ENDTIME, " +
                "       pi.BEGNTIME, " +
                "       pi.VALI_FLAG, " +
                "       pi.EMPPROF_FILE_ADDR, " +
                "       pi.ADMDVS, " +
                "       pi.MOB, " +
                "       pi.EMAIL, " +
                "       pi.POSCODE, " +
                "       pi.DCLA_WAY, " +
                "       pi.RID, " +
                "       pi.CRTE_TIME, " +
                "       pi.UPDT_TIME, " +
                "       pi.CRTER_ID, " +
                "       pi.CRTER_NAME, " +
                "       pi.CRTE_OPTINS_NO, " +
                "       pi.OPTER_ID, " +
                "       pi.OPTER_NAME, " +
                "       pi.OPT_TIME, " +
                "       pi.OPTINS_NO, " +
                "       pi.VER, " +
                "       pi.SYNC_PRNT_FLAG, " +
                "       pi.DSG_ID " +
                "FROM CUSTCENT_DB.PROF_INFO_B pi ";
        sql +=  "WHERE\n" +
                "\tpi.VALI_FLAG = '1'\n";

        if (StringUtils.isNotEmpty(profInfoB.getProf_no())) {
            sql += " and pi.prof_no like '%" + profInfoB.getProf_no() + "%'";
        }
        if (StringUtils.isNotEmpty(profInfoB.getProf_name())) {
            sql += " and pi.prof_name like '%" + profInfoB.getProf_name() + "%'";
        }
        if (StringUtils.isNotEmpty(profInfoB.getCurr_duty())) {
            sql += " and pi.curr_duty like '%" + profInfoB.getCurr_duty() + "%'";
        }
        if (StringUtils.isNotEmpty(profInfoB.getProf_type())) {
            sql += "and pi.prof_type = '" + profInfoB.getProf_type() + "' ";
        }

        return sql;
    }

    public String psnClctstdD(Page page,PsnClctstdD psnClctstdD) {
        String sql = "SELECT " +
                "       pib.psn_name, " +
                "       pib.hsecfc, " +
                "       pib.certno, " +
                "       pc.PSN_CLCTSTD_SN, " +
                "       pc.EMP_NO, " +
                "       pc.PSN_NO, " +
                "       pc.INSUTYPE, " +
                "       pc.YEAR, " +
                "       pc.INSUTYPE_RETR_FLAG, " +
                "       pc.JAN_WAG, " +
                "       pc.JAN_CLCTSTD_RULE_CODG, " +
                "       pc.JAN_PSN_CLCTSTD, " +
                "       pc.FEB_WAG, " +
                "       pc.FEB_CLCTSTD_RULE_CODG, " +
                "       pc.FEB_PSN_CLCTSTD, " +
                "       pc.MAR_WAG, " +
                "       pc.MAR_CLCTSTD_RULE_CODG, " +
                "       pc.MAR_PSN_CLCTSTD, " +
                "       pc.APR_WAG, " +
                "       pc.APR_CLCTSTD_RULE_CODG, " +
                "       pc.APR_PSN_CLCTSTD, " +
                "       pc.MAY_WAG, " +
                "       pc.MAY_CLCTSTD_RULE_CODG, " +
                "       pc.MAY_PSN_CLCTSTD, " +
                "       pc.JUNE_WAG, " +
                "       pc.JUNE_CLCTSTD_RULE_CODG, " +
                "       pc.JUNE_PSN_CLCTSTD, " +
                "       pc.JULY_WAG, " +
                "       pc.JULY_CLCTSTD_RULE_CODG, " +
                "       pc.JULY_PSN_CLCTSTD, " +
                "       pc.AUG_WAG, " +
                "       pc.AUG_PSN_CLCTSTD, " +
                "       pc.AUG_CLCTSTD_RULE_CODG, " +
                "       pc.SEPT_WAG, " +
                "       pc.SEPT_CLCTSTD_RULE_CODG, " +
                "       pc.SEPT_PSN_CLCTSTD, " +
                "       pc.OCT_WAG, " +
                "       pc.OCT_CLCTSTD_RULE_CODG, " +
                "       pc.OCT_PSN_CLCTSTD, " +
                "       pc.NOV_WAG, " +
                "       pc.NOV_CLCTSTD_RULE_CODG, " +
                "       pc.NOV_PSN_CLCTSTD, " +
                "       pc.DEC_WAG, " +
                "       pc.DEC_CLCTSTD_RULE_CODG, " +
                "       pc.PSN_INSU_RLTS_ID, " +
                "       pc.DEC_PSN_CLCTSTD, " +
                "       pc.INSU_ADMDVS, " +
                "       pc.POOLAREA_NO, " +
                "       pc.OPT_CHNL, " +
                "       pc.OPTINS_NO, " +
                "       pc.OPTER_ID, " +
                "       pc.OPTER_NAME, " +
                "       pc.OPT_TIME, " +
                "       pc.CRTE_OPTINS_NO, " +
                "       pc.CRTER_ID, " +
                "       pc.CRTER_NAME, " +
                "       pc.CRTE_TIME, " +
                "       pc.UPDT_TIME, " +
                "       pc.RID, " +
                "       INSUTYPE.NAT_DIC_VAL_NAME INSUTYPE_NAME, " +
                "       e.NAT_DIC_VAL_NAME INSU_ADMDVS_NAME, " +
                "       INSUTYPE_RETR_FLAG.NAT_DIC_VAL_NAME INSUTYPE_RETR_FLAG_NAME " +
                "FROM INSUCENT_DB.PSN_CLCTSTD_D pc ";

        sql += "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = pc.PSN_NO\n";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on pc.INSU_ADMDVS = e.nat_dic_val_code and e.DIC_TYPE_CODE = 'ADMDVS'\n";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE " +
                "on pc.INSUTYPE=INSUTYPE.NAT_DIC_VAL_CODE " +
                "and INSUTYPE.DIC_TYPE_CODE='INSUTYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE_RETR_FLAG " +
                "on pc.INSUTYPE_RETR_FLAG=INSUTYPE_RETR_FLAG.NAT_DIC_VAL_CODE " +
                "and INSUTYPE_RETR_FLAG.DIC_TYPE_CODE='INSUTYPE_RETR_FLAG' ";

        sql += "where 1 = 1 ";
        if (StringUtils.isNotEmpty(psnClctstdD.getPsn_name())) {
            sql += "and pib.psn_name like " + "'%" + psnClctstdD.getPsn_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(psnClctstdD.getHsecfc())) {
            sql += "and pib.hsecfc like " + "'%" + psnClctstdD.getHsecfc() + "%' ";
        }
        if (StringUtils.isNotEmpty(psnClctstdD.getCertno())) {
            sql += "and pib.certno like " + "'%" + psnClctstdD.getCertno() + "%' ";
        }
        if (StringUtils.isNotEmpty(psnClctstdD.getEmp_no())) {
            sql += "and pc.emp_no = '" + psnClctstdD.getEmp_no() + "' ";
        }
        if (StringUtils.isNotEmpty(psnClctstdD.getPsn_no())) {
            sql += "and pc.psn_no = '" + psnClctstdD.getPsn_no() + "' ";
        }
        if (StringUtils.isNotEmpty(psnClctstdD.getInsutype())) {
            sql += "and pc.insutype = '" + psnClctstdD.getInsutype() + "' ";
        }
        if (StringUtils.isNotEmpty(psnClctstdD.getYear())) {
            sql += "and pc.year = '" + psnClctstdD.getYear() + "' ";
        }
        if (StringUtils.isNotEmpty(psnClctstdD.getPoolarea_no())) {
            sql += "and pc.poolarea_no = '" + psnClctstdD.getPoolarea_no() + "' ";
        }
        return sql;
    }

    public String psnInsuD(Page page,PsnInsuD psnInsuD) {
        String sql = "SELECT " +
                "       pib.psn_name, " +
                "       pib.hsecfc, " +
                "       pib.certno, " +
                "       pi.PSN_INSU_RLTS_ID, " +
                "       pi.EMP_NO, " +
                "       pi.PSN_NO, " +
                "       pi.INSUTYPE, " +
                "       pi.CRT_INSU_DATE, " +
                "       pi.PAUS_INSU_DATE, " +
                "       pi.PSN_INSU_STAS, " +
                "       pi.INSUTYPE_RETR_FLAG, " +
                "       pi.PSN_TYPE, " +
                "       pi.CLCT_WAY, " +
                "       pi.EMP_FOM, " +
                "       pi.MAX_ACCTPRD, " +
                "       pi.QUTS_TYPE, " +
                "       pi.ACCT_CRTN_YM, " +
                "       pi.PSN_INSU_DATE, " +
                "       pi.FST_INSU_YM, " +
                "       pi.CLCTSTD_CRTF_RULE_CODG, " +
                "       pi.CLCT_RULE_TYPE_CODG, " +
                "       pi.HI_TYPE, " +
                "       pi.INSU_ADMDVS, " +
                "       pi.OPT_CHNL, " +
                "       pi.POOLAREA_NO, " +
                "       pi.OPTINS_NO, " +
                "       pi.OPTER_ID, " +
                "       pi.OPTER_NAME, " +
                "       pi.OPT_TIME, " +
                "       pi.CRTE_OPTINS_NO, " +
                "       pi.CRTER_ID, " +
                "       pi.CRTER_NAME, " +
                "       pi.CRTE_TIME, " +
                "       RETR_TRT_ENJYMNT_FLAG.NAT_DIC_VAL_NAME RETR_TRT_ENJYMNT_FLAG_NAME, " +
                "       OPT_CHNL.NAT_DIC_VAL_NAME OPT_CHNL_NAME, " +
                "       HI_TYPE.NAT_DIC_VAL_NAME HI_TYPE_NAME, " +
                "       QUTS_TYPE.NAT_DIC_VAL_NAME QUTS_TYPE_NAME, " +
                "       EMP_FOM.NAT_DIC_VAL_NAME EMP_FOM_NAME, " +
                "       CLCT_WAY.NAT_DIC_VAL_NAME CLCT_WAY_NAME, " +
                "       PSN_TYPE.NAT_DIC_VAL_NAME PSN_TYPE_NAME, " +
                "       INSUTYPE_RETR_FLAG.NAT_DIC_VAL_NAME INSUTYPE_RETR_FLAG_NAME, " +
                "       PSN_INSU_STAS.NAT_DIC_VAL_NAME PSN_INSU_STAS_NAME, " +
                "       INSUTYPE.NAT_DIC_VAL_NAME INSUTYPE_NAME " +
                "FROM INSUCENT_DB.PSN_INSU_D pi ";

        sql += "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = pi.PSN_NO\n";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A RETR_TRT_ENJYMNT_FLAG " +
                "on pi.RETR_TRT_ENJYMNT_FLAG=RETR_TRT_ENJYMNT_FLAG.NAT_DIC_VAL_CODE " +
                "and RETR_TRT_ENJYMNT_FLAG.DIC_TYPE_CODE='RETR_TRT_ENJYMNT_FLAG' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A OPT_CHNL " +
                "on pi.OPT_CHNL=OPT_CHNL.NAT_DIC_VAL_CODE " +
                "and OPT_CHNL.DIC_TYPE_CODE='OPT_CHNL' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A HI_TYPE " +
                "on pi.HI_TYPE=HI_TYPE.NAT_DIC_VAL_CODE " +
                "and HI_TYPE.DIC_TYPE_CODE='HI_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A QUTS_TYPE " +
                "on pi.QUTS_TYPE=QUTS_TYPE.NAT_DIC_VAL_CODE " +
                "and QUTS_TYPE.DIC_TYPE_CODE='QUTS_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A EMP_FOM " +
                "on pi.EMP_FOM=EMP_FOM.NAT_DIC_VAL_CODE " +
                "and EMP_FOM.DIC_TYPE_CODE='EMP_FOM' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A CLCT_WAY " +
                "on pi.CLCT_WAY=CLCT_WAY.NAT_DIC_VAL_CODE " +
                "and CLCT_WAY.DIC_TYPE_CODE='CLCT_WAY' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PSN_TYPE " +
                "on pi.PSN_TYPE=PSN_TYPE.NAT_DIC_VAL_CODE " +
                "and PSN_TYPE.DIC_TYPE_CODE='PSN_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE_RETR_FLAG " +
                "on pi.INSUTYPE_RETR_FLAG=INSUTYPE_RETR_FLAG.NAT_DIC_VAL_CODE " +
                "and INSUTYPE_RETR_FLAG.DIC_TYPE_CODE='INSUTYPE_RETR_FLAG' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PSN_INSU_STAS " +
                "on pi.PSN_INSU_STAS=PSN_INSU_STAS.NAT_DIC_VAL_CODE " +
                "and PSN_INSU_STAS.DIC_TYPE_CODE='PSN_INSU_STAS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE " +
                "on pi.INSUTYPE=INSUTYPE.NAT_DIC_VAL_CODE " +
                "and INSUTYPE.DIC_TYPE_CODE='INSUTYPE' ";

        sql += "where 1 = 1 ";
        if (StringUtils.isNotEmpty(psnInsuD.getPsn_name())) {
            sql += "and pib.psn_name like " + "'%" + psnInsuD.getPsn_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(psnInsuD.getHsecfc())) {
            sql += "and pib.hsecfc like " + "'%" + psnInsuD.getHsecfc() + "%' ";
        }
        if (StringUtils.isNotEmpty(psnInsuD.getCertno())) {
            sql += "and pib.certno like " + "'%" + psnInsuD.getCertno() + "%' ";
        }
        if (StringUtils.isNotEmpty(psnInsuD.getPsn_no())) {
            sql += "and pi.psn_no like " + "'%" + psnInsuD.getPsn_no() + "%' ";
        }
        if (StringUtils.isNotEmpty(psnInsuD.getEmp_no())) {
            sql += "and pi.emp_no like " + "'%" + psnInsuD.getEmp_no() + "%' ";
        }
        return sql;
    }

    public String psnInsuStasB(Page page,PsnInsuStasB psnInsuStasB) {
        String sql = "SELECT pis.PSN_INSU_RLTS_ID, " +
                "       pis.PSN_NO, " +
                "       pis.PSN_INSU_STAS, " +
                "       pis.PSN_TYPE, " +
                "       pis.INSUTYPE, " +
                "       pis.CRT_INSU_DATE, " +
                "       pis.PAUS_INSU_DATE, " +
                "       pis.INSU_ADMDVS, " +
                "       pis.MEMO, " +
                "       pis.VALI_FLAG, " +
                "       pis.RID, " +
                "       pis.CRTE_TIME, " +
                "       pis.UPDT_TIME, " +
                "       pis.CRTER_ID, " +
                "       pis.CRTER_NAME, " +
                "       pis.CRTE_OPTINS_NO, " +
                "       pis.OPTER_ID, " +
                "       pis.OPTER_NAME, " +
                "       pis.OPT_TIME, " +
                "       pis.OPTINS_NO, " +
                "       pis.VER, " +
                "       pis.SYNC_PRNT_FLAG, " +
                "       pis.DSG_ID " +
                "FROM CUSTCENT_DB.PSN_INSU_STAS_B pis";
        return sql;
    }

    public String rtalPhacB(Page page,RtalPhacB rtalPhacB) {
        String sql = "SELECT rp.RTAL_PHAC_CODE, " +
                "       rp.RTAL_PHAC_ABBR, " +
                "       rp.RTAL_PHAC_NAME, " +
                "       rp.PRNT_RTAL_PHAC_CODE, " +
                "       rp.ITRO, " +
                "       rp.USCC, " +
                "       rp.LNT, " +
                "       rp.TEL, " +
                "       rp.LAT, " +
                "       rp.ADDR, " +
                "       rp.ADMDVS, " +
                "       rp.BEGNTIME, " +
                "       rp.ENDTIME, " +
                "       rp.BRCH_FLAG, " +
                "       rp.DRUG_BIZ_LIC_NO, " +
                "       rp.MEMO, " +
                "       rp.DRUG_BIZ_SCP, " +
                "       rp.ECON_TYPE, " +
                "       rp.PHAR_PSNCNT, " +
                "       rp.PHAC_CHAN_TYPE, " +
                "       rp.EQU_BIZ_LIC_NO, " +
                "       rp.CRED_LV, " +
                "       rp.CRED_LV_NAME, " +
                "       rp.VALI_FLAG, " +
                "       rp.RID, " +
                "       rp.CRTE_TIME, " +
                "       rp.UPDT_TIME, " +
                "       rp.CRTER_ID, " +
                "       rp.CRTER_NAME, " +
                "       rp.CRTE_OPTINS_NO, " +
                "       rp.OPTER_ID, " +
                "       rp.OPTER_NAME, " +
                "       rp.OPT_TIME, " +
                "       rp.OPTINS_NO, " +
                "       rp.VER, " +
                "       rp.SYNC_PRNT_FLAG, " +
                "       rp.VER_RID, " +
                "       rp.VER_NAME, " +
                "       rp.RTAL_PHAC_ID, " +
                "       rp.BIZ_STAS, " +
                "       rp.LEGREP_NAME, " +
                "       rp.LEGREP_CERT_TYPE, " +
                "       rp.LEGREP_CERTNO, " +
                "       rp.REGCAP, " +
                "       rp.BIZ_LIC_EXPY_BEGNTIME, " +
                "       rp.BIZ_LIC_EXPY_ENDTIME, " +
                "       rp.BIZ_WAY, " +
                "       rp.ENTP_RESPER, " +
                "       rp.HI_CONER_NAME, " +
                "       rp.HI_CONER_TEL, " +
                "       rp.RTAL_PHAC_EMAIL, " +
                "       rp.BANK_NAME, " +
                "       rp.BANKACCT, " +
                "       LEGREP_CERT_TYPE.NAT_DIC_VAL_NAME LEGREP_CERT_TYPE_NAME " +
                "FROM CUSTCENT_DB.RTAL_PHAC_B rp ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A LEGREP_CERT_TYPE " +
                "on rp.LEGREP_CERT_TYPE=LEGREP_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and LEGREP_CERT_TYPE.DIC_TYPE_CODE='PSN_CERT_TYPE' ";


        sql += "where rp.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(rtalPhacB.getRtal_phac_code())) {
            sql += "and rp.RTAL_PHAC_CODE like " + "'%" + rtalPhacB.getRtal_phac_code() + "%' ";
        }
        if (StringUtils.isNotEmpty(rtalPhacB.getRtal_phac_name())) {
            sql += "and rp.RTAL_PHAC_NAME like " + "'%" + rtalPhacB.getRtal_phac_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(rtalPhacB.getCred_lv())) {
            sql += " and rp.CRED_LV in (" + rtalPhacB.getCred_lv() + ") ";
        }
        return sql;
    }

    public String spdrugApprFilEvtC(Page page,SpdrugApprFilEvtC spdrugApprFilEvtC) {
        String sql = "SELECT safe.EVTSN, " +
                "       safe.TRT_DCLA_DETL_SN, " +
                "       safe.SERV_MATT_INST_ID, " +
                "       safe.SERV_MATT_NODE_INST_ID, " +
                "       safe.EVT_INST_ID, " +
                "       safe.EVT_TYPE, " +
                "       safe.DCLA_SOUC, " +
                "       safe.INSUTYPE, " +
                "       safe.PSN_NO, " +
                "       safe.PSN_INSU_RLTS_ID, " +
                "       safe.PSN_CERT_TYPE, " +
                "       safe.CERTNO, " +
                "       safe.PSN_NAME, " +
                "       safe.GEND, " +
                "       safe.BRDY, " +
                "       safe.TEL, " +
                "       safe.ADDR, " +
                "       safe.INSU_ADMDVS, " +
                "       safe.EMP_NO, " +
                "       safe.EMP_NAME, " +
                "       safe.BEGNDATE, " +
                "       safe.ENDDATE, " +
                "       safe.DIAG_CODE, " +
                "       safe.DIAG_NAME, " +
                "       safe.BIZ_USED_FLAG, " +
                "       safe.AGNTER_NAME, " +
                "       safe.AGNTER_CERT_TYPE, " +
                "       safe.AGNTER_CERTNO, " +
                "       safe.AGNTER_TEL, " +
                "       safe.AGNTER_ADDR, " +
                "       safe.AGNTER_RLTS, " +
                "       safe.VALI_FLAG, " +
                "       safe.RCHK_FLAG, " +
                "       safe.MEMO, " +
                "       safe.RID, " +
                "       safe.UPDT_TIME, " +
                "       safe.CRTER_NAME, " +
                "       safe.CRTER_ID, " +
                "       safe.CRTE_TIME, " +
                "       safe.CRTE_OPTINS_NO, " +
                "       safe.OPTER_ID, " +
                "       safe.OPTER_NAME, " +

                "       AGNTER_CERT_TYPE.NAT_DIC_VAL_NAME AGNTER_CERT_TYPE_NAME, " +
                "       AGNTER_RLTS.NAT_DIC_VAL_NAME AGNTER_RLTS_NAME, " +
                "       EVT_TYPE.NAT_DIC_VAL_NAME EVT_TYPE_NAME, " +
                "       RCHK_FLAG.NAT_DIC_VAL_NAME RCHK_FLAG_NAME, " +
//        "       NATY.NAT_DIC_VAL_NAME NATY_NAME, " +
                "       GEND.NAT_DIC_VAL_NAME GEND_NAME, " +
                "       PSN_CERT_TYPE.NAT_DIC_VAL_NAME PSN_CERT_TYPE_NAME, " +
                "       DCLA_SOUC.NAT_DIC_VAL_NAME DCLA_SOUC_NAME, " +
                "       INSUTYPE.NAT_DIC_VAL_NAME INSUTYPE_NAME " +

                "FROM HIBIZ_DB.SPDRUG_APPR_FIL_EVT_C safe ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AGNTER_CERT_TYPE " +
                "on safe.AGNTER_CERT_TYPE=AGNTER_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and AGNTER_CERT_TYPE.DIC_TYPE_CODE='AGNTER_CERT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AGNTER_RLTS " +
                "on safe.AGNTER_RLTS=AGNTER_RLTS.NAT_DIC_VAL_CODE " +
                "and AGNTER_RLTS.DIC_TYPE_CODE='AGNTER_RLTS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A EVT_TYPE " +
                "on safe.EVT_TYPE=EVT_TYPE.NAT_DIC_VAL_CODE " +
                "and EVT_TYPE.DIC_TYPE_CODE='EVT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A RCHK_FLAG " +
                "on safe.RCHK_FLAG=RCHK_FLAG.NAT_DIC_VAL_CODE " +
                "and RCHK_FLAG.DIC_TYPE_CODE='RCHK_FLAG' ";
//    sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A NATY " +
//        "on safe.NATY=NATY.NAT_DIC_VAL_CODE " +
//        "and NATY.DIC_TYPE_CODE='NATY' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A GEND " +
                "on safe.GEND=GEND.NAT_DIC_VAL_CODE " +
                "and GEND.DIC_TYPE_CODE='GEND' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A PSN_CERT_TYPE " +
                "on safe.PSN_CERT_TYPE=PSN_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and PSN_CERT_TYPE.DIC_TYPE_CODE='PSN_CERT_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A DCLA_SOUC " +
                "on safe.DCLA_SOUC=DCLA_SOUC.NAT_DIC_VAL_CODE " +
                "and DCLA_SOUC.DIC_TYPE_CODE='DCLA_SOUC' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A INSUTYPE " +
                "on safe.INSUTYPE=INSUTYPE.NAT_DIC_VAL_CODE " +
                "and INSUTYPE.DIC_TYPE_CODE='INSUTYPE' ";

        sql += "where safe.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(spdrugApprFilEvtC.getCertno())) {
            sql += "and safe.certno like '%" + spdrugApprFilEvtC.getCertno() + "%' ";
        }
        if (StringUtils.isNotEmpty(spdrugApprFilEvtC.getPsn_name())) {
            sql += "and safe.psn_name like '%" + spdrugApprFilEvtC.getPsn_name() + "%' ";
        }
        if (StringUtils.isNotEmpty(spdrugApprFilEvtC.getCrte_time())) {
            sql += " AND TO_CHAR( safe.crte_time, 'yyyy-MM-dd' ) = '" + spdrugApprFilEvtC.getCrte_time() + "' ";
        }
        if (StringUtils.isNotEmpty(spdrugApprFilEvtC.getInsutype())) {
            sql += "and safe.insutype = '" + spdrugApprFilEvtC.getInsutype() + "' ";
        }
        return sql;
    }

    public String reflAppyEvtC(Page page,ReflAppyEvtCVo reflAppyEvtCVo) {
        String sql = "SELECT " +
                "        RAEC.EVTSN, " +
                "        RAEC.TRT_DCLA_DETL_SN, " +
                "        RAEC.SERV_MATT_INST_ID, " +
                "        RAEC.EVT_INST_ID, " +
                "        RAEC.SERV_MATT_NODE_INST_ID, " +
                "        RAEC.EVT_TYPE, " +
                "        RAEC.INSUTYPE, " +
                "        RAEC.DCLA_SOUC, " +
                "        RAEC.PSN_NO, " +
                "        RAEC.PSN_INSU_RLTS_ID, " +
                "        RAEC.PSN_CERT_TYPE, " +
                "        RAEC.CERTNO, " +
                "        RAEC.PSN_NAME, " +
                "        RAEC.GEND, " +
                "        RAEC.BRDY, " +
                "        RAEC.ADDR, " +
                "        RAEC.TEL, " +
                "        RAEC.EMP_NO, " +
                "        RAEC.INSU_ADMDVS, " +
                "        RAEC.EMP_NAME, " +
                "        RAEC.FIXMEDINS_CODE, " +
                "        RAEC.FIXMEDINS_NAME, " +
                "        RAEC.HOSP_LV, " +
                "        RAEC.FIX_BLNG_ADMDVS, " +
                "        RAEC.DIAG_CODE, " +
                "        RAEC.DIAG_NAME, " +
                "        RAEC.DRORD, " +
                "        RAEC.DISE_COND_DSCR, " +
                "        RAEC.MDTRTAREA_ADMDVS, " +
                "        RAEC.REFLIN_MEDINS_NO, " +
                "        RAEC.REFLIN_MEDINS_NAME, " +
                "        RAEC.OUT_FLAG, " +
                "        RAEC.REFL_DATE, " +
                "        RAEC.REFL_REA, " +
                "        RAEC.REFL_SETL_FLAG, " +
                "        RAEC.REFL_OPNN, " +
                "        RAEC.MEMO, " +
                "        RAEC.AGNTER_CERT_TYPE, " +
                "        RAEC.AGNTER_NAME, " +
                "        RAEC.AGNTER_CERTNO, " +
                "        RAEC.AGNTER_TEL, " +
                "        RAEC.AGNTER_ADDR, " +
                "        RAEC.AGNTER_RLTS, " +
                "        RAEC.BEGNDATE, " +
                "        RAEC.ENDDATE, " +
                "        RAEC.MDTRT_ID, " +
                "        RAEC.SETL_ID, " +
                "        RAEC.HOSP_AGRE_REFL_FLAG, " +
                "        RAEC.VALI_FLAG, " +
                "        RAEC.RCHK_FLAG, " +
                "        RAEC.RID, " +
                "        RAEC.UPDT_TIME, " +
                "        RAEC.CRTER_ID, " +
                "        RAEC.CRTER_NAME, " +
                "        RAEC.CRTE_TIME, " +
                "        RAEC.CRTE_OPTINS_NO, " +
                "        RAEC.OPTER_ID, " +
                "        RAEC.OPTER_NAME, " +
                "        RAEC.OPT_TIME, " +
                "        RAEC.OPTINS_NO, " +
                "        RAEC.POOLAREA_NO, " +
                "        RAEC.REFL_FIL_TYPE, " +
                "        RAEC.ALLO_SETL_CNT, " +
                "       RFT.NAT_DIC_VAL_NAME as REFL_FIL_TYPE_NAME, " +
                "       RF.NAT_DIC_VAL_NAME as RCHK_FLAG_NAME, " +
                "       I.NAT_DIC_VAL_NAME as INSUTYPE_NAME, " +
                "       VF.NAT_DIC_VAL_NAME as VALI_FLAG_NAME, " +
                "       PCT.NAT_DIC_VAL_NAME as PSN_CERT_TYPE_NAME " +
                "FROM hibiz_db.refl_appy_evt_c RAEC " +
//                "FROM refl_appy_evt_c RAEC " +
                "LEFT JOIN polcent_db.nat_data_dic_a RFT " +
                "                   ON RFT.DIC_TYPE_CODE = 'REFL_FIL_TYPE' AND RFT.NAT_DIC_VAL_CODE = RAEC.REFL_FIL_TYPE " +
                "LEFT JOIN polcent_db.nat_data_dic_a RF " +
                "                   ON RF.DIC_TYPE_CODE = 'RCHK_FLAG' AND RF.NAT_DIC_VAL_CODE = RAEC.RCHK_FLAG " +
                "LEFT JOIN polcent_db.nat_data_dic_a I " +
                "                   ON I.DIC_TYPE_CODE = 'INSUTYPE' AND I.NAT_DIC_VAL_CODE = RAEC.INSUTYPE " +
                "LEFT JOIN polcent_db.nat_data_dic_a VF " +
                "                   ON VF.DIC_TYPE_CODE = 'VALI_FLAG' AND VF.NAT_DIC_VAL_CODE = RAEC.VALI_FLAG " +
                "LEFT JOIN polcent_db.nat_data_dic_a PCT " +
                "                   ON PCT.DIC_TYPE_CODE = 'PSN_CERT_TYPE' AND PCT.NAT_DIC_VAL_CODE = RAEC.PSN_CERT_TYPE " +
                "WHERE RAEC.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(reflAppyEvtCVo.getCertno())) {
            sql += " AND RAEC.CERTNO = '" + reflAppyEvtCVo.getCertno() + "'";
        }
        if (!"".equals(reflAppyEvtCVo.getBegndate()) && null != reflAppyEvtCVo.getBegndate()) {
            sql += " AND TO_CHAR( RAEC.begndate, 'yyyy-MM-dd' ) >= '" + reflAppyEvtCVo.getBegndate() + "' ";
        }
        if (!"".equals(reflAppyEvtCVo.getEnddate()) && null != reflAppyEvtCVo.getEnddate()) {
            sql += " AND TO_CHAR( RAEC.enddate, 'yyyy-MM-dd' ) <= '" + reflAppyEvtCVo.getEnddate() + "' ";
        }
        if (StringUtils.isNotEmpty(reflAppyEvtCVo.getCrte_time())) {
            sql += " AND TO_CHAR( RAEC.crte_time, 'yyyy-MM-dd' ) = '" + reflAppyEvtCVo.getCrte_time() + "' ";
        }
        if (StringUtils.isNotEmpty(reflAppyEvtCVo.getRchk_flag())) {
            sql += " AND RAEC.RCHK_FLAG = '" + reflAppyEvtCVo.getRchk_flag() + "'";
        }
        sql += " ORDER BY RAEC.RCHK_FLAG";

        return sql;
    }

    public String fixmedinsCntrRegD(Page page,FixmedinsCntrRegD fixmedinsCntrRegD) {
        String sql = "SELECT " +
                "        fcrd.FIX_CNTR_ID, " +
                "        fcrd.FIXMEDINS_CODE, " +
                "        fcrd.FIX_CNTR_TYPE, " +
                "        fcrd.FIX_CNTR_NAME, " +
                "        fcrd.BEGNTIME, " +
                "        fcrd.FIX_BLNG_ADMDVS, " +
                "        fcrd.CNTR_SIGN_YEAR, " +
                "        fcrd.ENDTIME, " +
                "        fcrd.MEMO, " +
                "        fcrd.VALI_FLAG, " +
                "        fcrd.CNTR_SIGN_TIME, " +
                "        fcrd.UPDT_TIME, " +
                "        fcrd.RID, " +
                "        fcrd.CRTER_ID, " +
                "        fcrd.CRTER_NAME, " +
                "        fcrd.CRTE_TIME, " +
                "        fcrd.CRTE_OPTINS_NO, " +
                "        fcrd.OPTER_ID, " +
                "        fcrd.OPTER_NAME, " +
                "        fcrd.OPT_TIME, " +
                "        fcrd.OPTINS_NO, " +
                "        fcrd.POOLAREA_NO, " +
                "        fcrd.FIXMEDINS_CHRG_LV, " +
                "        fcrd.HI_SYS_EMP_CODE, " +
                "        fcrd.FIX_CNTR_SERV_OBJ, " +
                "        fcrd.FIX_CNTR_SERV_SCP, " +
                "        fcrd.FIX_CNTR_ELEC_DOC, " +
                "        fcrd.VER_NAME, " +
                "        fcrd.TRAM_DATA_ID, " +
                "        fcrd.ISU_FLAG, " +
                "        fcrd.CNTR_STAS, " +
                "        fcrd.EFFT_TIME, " +
                "        fcrd.INVD_TIME  " +
                "FROM " +
                "        POLCENT_DB.FIXMEDINS_CNTR_REG_D fcrd " +
                "WHERE " +
                "        fcrd.VALI_FLAG = '1' ";
        getfixmedinsCntrRegDWhere(fixmedinsCntrRegD, sql);
        return sql;
    }


    public String fixmedinsCntrRegDByMedinsInfoB(Page page,FixmedinsCntrRegD fixmedinsCntrRegD) {
        String sql = "SELECT " +
                "        fcrd.FIX_CNTR_ID, " +
                "        fcrd.FIXMEDINS_CODE, " +
                "        fcrd.FIX_CNTR_TYPE, " +
                "        fcrd.FIX_CNTR_NAME, " +
                "        fcrd.BEGNTIME, " +
                "        fcrd.FIX_BLNG_ADMDVS, " +
                "        fcrd.CNTR_SIGN_YEAR, " +
                "        fcrd.ENDTIME, " +
                "        fcrd.MEMO, " +
                "        fcrd.VALI_FLAG, " +
                "        fcrd.CNTR_SIGN_TIME, " +
                "        fcrd.UPDT_TIME, " +
                "        fcrd.RID, " +
                "        fcrd.CRTER_ID, " +
                "        fcrd.CRTER_NAME, " +
                "        fcrd.CRTE_TIME, " +
                "        fcrd.CRTE_OPTINS_NO, " +
                "        fcrd.OPTER_ID, " +
                "        fcrd.OPTER_NAME, " +
                "        fcrd.OPT_TIME, " +
                "        fcrd.OPTINS_NO, " +
                "        fcrd.POOLAREA_NO, " +
                "        fcrd.FIXMEDINS_CHRG_LV, " +
                "        fcrd.HI_SYS_EMP_CODE, " +
                "        fcrd.FIX_CNTR_SERV_OBJ, " +
                "        fcrd.FIX_CNTR_SERV_SCP, " +
                "        fcrd.FIX_CNTR_ELEC_DOC, " +
                "        fcrd.VER_NAME, " +
                "        fcrd.TRAM_DATA_ID, " +
                "        fcrd.ISU_FLAG, " +
                "        fcrd.CNTR_STAS, " +
                "        fcrd.EFFT_TIME, " +
                "        fcrd.INVD_TIME,  " +
                "        mi.medins_name,  " +
                "       MEDINSLV.NAT_DIC_VAL_NAME MEDINSLV_NAME, " +
                "       AFIL_RLTS.NAT_DIC_VAL_NAME AFIL_RLTS_NAME, " +
                "       ECON_TYPE.NAT_DIC_VAL_NAME ECON_TYPE_NAME, " +
                "       MEDINS_GRADE.NAT_DIC_VAL_NAME MEDINS_GRADE_NAME, " +
                "       HOSP_LV.NAT_DIC_VAL_NAME HOSP_LV_NAME, " +
                "       SYNC_PRNT_FLAG.NAT_DIC_VAL_NAME SYNC_PRNT_FLAG_NAME, " +
                "       BIZNAT.NAT_DIC_VAL_NAME BIZNAT_NAME, " +
                "       MEDINS_TYPE.NAT_DIC_VAL_NAME MEDINS_TYPE_NAME, " +
                "       CRED_LV.NAT_DIC_VAL_NAME CRED_LV_NAME, " +
                "       MEDINS_NATU.NAT_DIC_VAL_NAME MEDINS_NATU_NAME " +
                "FROM " +
                "        POLCENT_DB.FIXMEDINS_CNTR_REG_D fcrd " +
                "LEFT JOIN CUSTCENT_DB.MEDINS_INFO_B mi ON mib.MEDINS_CODE = fcrd.FIXMEDINS_CODE ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINSLV " +
                "on mi.MEDINSLV=MEDINSLV.NAT_DIC_VAL_CODE " +
                "and MEDINSLV.DIC_TYPE_CODE='MEDINSLV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A AFIL_RLTS " +
                "on mi.AFIL_RLTS=AFIL_RLTS.NAT_DIC_VAL_CODE " +
                "and AFIL_RLTS.DIC_TYPE_CODE='AFIL_RLTS' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ECON_TYPE " +
                "on mi.ECON_TYPE=ECON_TYPE.NAT_DIC_VAL_CODE " +
                "and ECON_TYPE.DIC_TYPE_CODE='ECON_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_GRADE " +
                "on mi.MEDINS_GRADE=MEDINS_GRADE.NAT_DIC_VAL_CODE " +
                "and MEDINS_GRADE.DIC_TYPE_CODE='MEDINS_GRADE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A SYNC_PRNT_FLAG " +
                "on mi.SYNC_PRNT_FLAG=SYNC_PRNT_FLAG.NAT_DIC_VAL_CODE " +
                "and SYNC_PRNT_FLAG.DIC_TYPE_CODE='SYNC_PRNT_FLAG' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A HOSP_LV " +
                "on mi.HOSP_LV=HOSP_LV.NAT_DIC_VAL_CODE " +
                "and HOSP_LV.DIC_TYPE_CODE='HOSP_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A BIZNAT " +
                "on mi.BIZNAT=BIZNAT.NAT_DIC_VAL_CODE " +
                "and BIZNAT.DIC_TYPE_CODE='BIZNAT' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_TYPE " +
                "on mi.MEDINS_TYPE=MEDINS_TYPE.NAT_DIC_VAL_CODE " +
                "and MEDINS_TYPE.DIC_TYPE_CODE='MEDINS_TYPE' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A CRED_LV " +
                "on mi.CRED_LV=CRED_LV.NAT_DIC_VAL_CODE " +
                "and CRED_LV.DIC_TYPE_CODE='CRED_LV' ";
        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A MEDINS_NATU " +
                "on mi.MEDINS_NATU=MEDINS_NATU.NAT_DIC_VAL_CODE " +
                "and MEDINS_NATU.DIC_TYPE_CODE='MEDINS_NATU' ";

        sql += "WHERE " +
                "        fcrd.VALI_FLAG = '1' ";
        return sql;
    }

    public String fixmedinsGather(Page page,FixmedinsGather vo) {
        String sql = "SELECT \n" +
                "                   FB.FIXMEDINS_CODE,\n" +
                "                   FB.FIXMEDINS_NAME,\n" +
                "                   FB.MEDINS_MGTCODE,\n" +
                "                   FB.FIXMEDINS_TYPE,\n" +
                "                   FB.PROV_PLAF_CODE,\n" +
                "                   FB.OUT_FIXMEDINS_TYPE,\n" +
                "                   FB.FIX_ONLN_OPEN_FLAG,\n" +
                "                   FB.OUT_ONLN_OPEN_TYPE,\n" +
                "                   FB.HI_RESPER_NAME,\n" +
                "                   FB.HI_RESPER_CERT_TYPE,\n" +
                "                   FB.HI_RESPER_CERTNO,\n" +
                "                   FB.HI_RESPER_TEL,\n" +
                "                   FB.ORGCODE,\n" +
                "                   FB.FIX_BLNG_ADMDVS,\n" +
                "                   FB.LMTPRIC_HOSP_LV,\n" +
                "                   FB.DEDC_HOSP_LV,\n" +
                "                   FB.POOLAREA_NO," +
                "                   FB.BEGNTIME," +
                "                   FB.ENDTIME," +
//        "       M.NAT_DIC_VAL_NAME as MEDINSLV_NAME,\n" +
//        "       FOOF.NAT_DIC_VAL_NAME as FIX_ONLN_OPEN_FLAG_NAME,\n" +
//        "       OOOT.NAT_DIC_VAL_NAME as OUT_ONLN_OPEN_TYPE_NAME,\n" +
//        "       HRCT.NAT_DIC_VAL_NAME as HI_RESPER_CERT_TYPE_NAME\n," +
                "       HV.NAT_DIC_VAL_NAME as HOSP_LV_NAME,\n" +
                "       LHL.NAT_DIC_VAL_NAME as LMTPRIC_HOSP_LV_NAME,\n" +
                "       DHL.NAT_DIC_VAL_NAME as DEDC_HOSP_LV_NAME,\n" +
                "       OFT.NAT_DIC_VAL_NAME as OUT_FIXMEDINS_TYPE_NAME\n" +
                "FROM polcent_db.FIXMEDINS_B FB\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a M\n" +
//        "                   ON M.DIC_TYPE_CODE = 'MEDINSLV' AND M.NAT_DIC_VAL_CODE = FB.MEDINSLV\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a FOOF\n" +
//        "                   ON FOOF.DIC_TYPE_CODE = 'FIX_ONLN_OPEN_FLAG' AND FOOF.NAT_DIC_VAL_CODE = FB.FIX_ONLN_OPEN_FLAG\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a OOOT\n" +
//        "                   ON OOOT.DIC_TYPE_CODE = 'OUT_ONLN_OPEN_TYPE' AND OOOT.NAT_DIC_VAL_CODE = FB.OUT_ONLN_OPEN_TYPE\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a HRCT\n" +
//        "                   ON HRCT.DIC_TYPE_CODE = 'HI_RESPER_CERT_TYPE' AND HRCT.NAT_DIC_VAL_CODE = FB.HI_RESPER_CERT_TYPE\n" +
                "LEFT JOIN polcent_db.nat_data_dic_a HV\n" +
                "                   ON HV.DIC_TYPE_CODE = 'HOSP_LV' AND HV.NAT_DIC_VAL_CODE = FB.HOSP_LV\n" +
                "LEFT JOIN polcent_db.nat_data_dic_a LHL\n" +
                "                   ON LHL.DIC_TYPE_CODE = 'LMTPRIC_HOSP_LV' AND LHL.NAT_DIC_VAL_CODE = FB.LMTPRIC_HOSP_LV\n" +
                "LEFT JOIN polcent_db.nat_data_dic_a DHL\n" +
                "                   ON DHL.DIC_TYPE_CODE = 'DEDC_HOSP_LV' AND DHL.NAT_DIC_VAL_CODE = FB.DEDC_HOSP_LV\n" +
                "LEFT JOIN polcent_db.nat_data_dic_a OFT\n" +
                "                   ON OFT.DIC_TYPE_CODE = 'OUT_FIXMEDINS_TYPE' AND OFT.NAT_DIC_VAL_CODE = FB.OUT_FIXMEDINS_TYPE\n" +
                "WHERE  FB.FIXMEDINS_TYPE = 1 AND FB.VALI_FLAG = 1\n";
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(vo.getMedins_code())) {
            sql += " AND FB.FIXMEDINS_CODE = '" + vo.getMedins_code() + "'";
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(vo.getMedins_name())) {
            sql += " AND FB.FIXMEDINS_NAME LIKE '%" + vo.getMedins_name() + "%'";
        }

        return sql;
    }

    private String getfixmedinsCntrRegDWhere(FixmedinsCntrRegD fixmedinsCntrRegD, String sql) {
        if (StringUtils.isNotEmpty(fixmedinsCntrRegD.getFixmedins_code())) {
            sql += " AND fcrd.FIXMEDINS_CODE = '" + fixmedinsCntrRegD.getFixmedins_code() + "'";
        }
        if (StringUtils.isNotEmpty(fixmedinsCntrRegD.getFix_cntr_type())) {
            sql += " AND fcrd.FIX_CNTR_TYPE = '" + fixmedinsCntrRegD.getFix_cntr_type() + "'";
        }
        if (!"".equals(fixmedinsCntrRegD.getBegntime()) && null != fixmedinsCntrRegD.getBegntime()) {
            sql += " AND TO_CHAR( RAEC.BEGNTIME, 'yyyy-MM-dd' ) <= '" + fixmedinsCntrRegD.getBegntime() + "' ";
        }
        if (StringUtils.isNotEmpty(fixmedinsCntrRegD.getEndtime())) {
            sql += " AND TO_CHAR( RAEC.ENDTIME, 'yyyy-MM-dd' ) = '" + fixmedinsCntrRegD.getEndtime() + "' ";
        }
        if (StringUtils.isNotEmpty(fixmedinsCntrRegD.getFix_cntr_name())) {
            sql += " AND fcrd.FIX_CNTR_NAME = '" + fixmedinsCntrRegD.getFix_cntr_name() + "'";
        }
        if (StringUtils.isNotEmpty(fixmedinsCntrRegD.getCntr_sign_year())) {
            sql += " AND fcrd.CNTR_SIGN_YEAR = '" + fixmedinsCntrRegD.getCntr_sign_year() + "'";
        }
        if (StringUtils.isNotEmpty(fixmedinsCntrRegD.getFix_blng_admdvs())) {
            sql += " AND fcrd.FIX_BLNG_ADMDVS = '" + fixmedinsCntrRegD.getFix_blng_admdvs() + "'";
        }
        return sql;
    }

    public String setlDByDiseNoTrt(Page page,SetlD setlD) {
        String sql = "SELECT " +
//                "        IF( sd.AGE >=70 , '是' , '否' ) AGE_FLAG, " +
                "        sd.SETL_ID, " +
                "        sd.CLR_OPTINS, " +
                "        sd.MEDINS_SETL_ID, " +
                "        sd.MDTRT_ID, " +
                "        sd.INIT_SETL_ID, " +
                "        sd.PSN_NO, " +
                "        sd.PSN_INSU_RLTS_ID, " +
                "        sd.PSN_NAME, " +
                "        sd.PSN_CERT_TYPE, " +
                "        sd.GEND, " +
                "        sd.CERTNO, " +
                "        sd.NATY, " +
                "        sd.BRDY, " +
                "        sd.AGE, " +
                "        sd.INSUTYPE, " +
                "        sd.PSN_TYPE, " +
                "        sd.CVLSERV_FLAG, " +
                "        sd.CVLSERV_LV, " +
                "        sd.SP_PSN_TYPE, " +
                "        sd.SP_PSN_TYPE_LV, " +
                "        sd.CLCT_GRDE, " +
                "        sd.FLXEMPE_FLAG, " +
                "        sd.NWB_FLAG, " +
                "        sd.INSU_ADMDVS, " +
                "        sd.EMP_NO, " +
                "        sd.EMP_NAME, " +
                "        sd.EMP_TYPE, " +
                "        sd.ECON_TYPE, " +
                "        sd.AFIL_INDU, " +
                "        sd.AFIL_RLTS, " +
                "        sd.EMP_MGT_TYPE, " +
                "        sd.FIXMEDINS_CODE, " +
                "        sd.FIXMEDINS_NAME, " +
                "        sd.PAY_LOC, " +
                "        sd.HOSP_LV, " +
                "        sd.FIX_BLNG_ADMDVS, " +
                "        sd.LMTPRIC_HOSP_LV, " +
                "        sd.DEDC_HOSP_LV, " +
                "        sd.BEGNDATE, " +
                "        sd.ENDDATE, " +
                "        sd.SETL_TIME, " +
                "        sd.MDTRT_CERT_NO, " +
                "        sd.MDTRT_CERT_TYPE, " +
                "        sd.MED_TYPE, " +
                "        sd.SETL_TYPE, " +
                "        sd.CLR_TYPE, " +
                "        sd.CLR_WAY, " +
                "        sd.PSN_SETLWAY, " +
                "        sd.MEDFEE_SUMAMT, " +
                "        sd.FULAMT_OWNPAY_AMT, " +
                "        sd.OVERLMT_SELFPAY, " +
                "        sd.PRESELFPAY_AMT, " +
                "        sd.INSCP_AMT, " +
                "        sd.DEDC_STD, " +
                "        sd.CRT_DEDC, " +
                "        sd.ACT_PAY_DEDC, " +
                "        sd.HIFP_PAY, " +
                "        sd.POOL_PROP_SELFPAY, " +
                "        sd.HI_AGRE_SUMFEE, " +
                "        sd.CVLSERV_PAY, " +
                "        sd.HIFES_PAY, " +
                "        sd.HIFMI_PAY, " +
                "        sd.HIFOB_PAY, " +
                "        sd.HIFDM_PAY, " +
                "        sd.MAF_PAY, " +
                "        sd.OTHFUND_PAY, " +
                "        sd.FUND_PAY_SUMAMT, " +
                "        sd.PSN_PAY, " +
                "        sd.ACCT_PAY, " +
                "        sd.CASH_PAYAMT, " +
                "        sd.OWNPAY_HOSP_PART, " +
                "        sd.BALC, " +
                "        sd.ACCT_MULAID_PAY, " +
                "        sd.REFD_SETL_FLAG, " +
                "        sd.CAL_IPT_CNT, " +
                "        sd.SETL_CASHPAY_WAY, " +
                "        sd.YEAR, " +
                "        sd.DISE_NO, " +
                "        sd.DISE_NAME, " +
                "        sd.INVONO, " +
                "        sd.MANL_REIM_REA, " +
                "        sd.VALI_FLAG  " +
                "FROM " +
                "        SETLCENT_DB.SETL_D sd " +
                "WHERE " +
                "        sd.VALI_FLAG = '1' AND sd.dise_no IS NOT NULL AND sd.dise_no <> ' ' ";
        getSetlDWhere(setlD, sql);
        return sql;
    }

    public String setlD(Page page,SetlD setlD) {
        String sql = "SELECT " +
                "        sd.POOLAREA_NO,sd.FIXMEDINS_CODE,sd.FIXMEDINS_NAME, " +
                "        sum(case when sd.REFD_SETL_FLAG = '0' then 1 else 0 end ) PERSON_COUNT, " +//就诊人次
                "        count( DISTINCT sd.CERTNO ) PERSON_NUM, " +//就诊人数
                "        sum( sd.MEDFEE_SUMAMT ) MEDFEE_SUMAMT, " +//医疗费总额
                "        sum( sd.POOL_PROP_SELFPAY ) POOL_PROP_SELFPAY, " +//基本医疗统筹支付比例
                "        sum( sd.HIFOB_PAY ) HIFOB_PAY, " +//大额医疗补助基金支出
                "        sum( sd.CVLSERV_PAY ) CVLSERV_PAY, " +//公务员医疗补助资金支出
                "        sum( sd.ACCT_PAY ) ACCT_PAY, " +//个人账户支出
                "        sum( sd.CASH_PAYAMT ) CASH_PAYAMT, " +//现金支付金额
                "        sum( sd.OWNPAY_HOSP_PART ) OWNPAY_HOSP_PART, " +//自费中医院负担部分
                "        sum( sd.HIFES_PAY ) HIFES_PAY, " +//补充医疗保险基金支出
                "        sum( sd.HIFMI_PAY ) HIFMI_PAY, " +//大病补充医疗保险基金支出
                "        sum( sd.MAF_PAY ) MAF_PAY, " +//医疗救助基金支出
                "        sum( sd.HIFDM_PAY ) HIFDM_PAY " +//伤残人员医疗保障基金支出
                "FROM " +
                "        SETLCENT_DB.SETL_D sd " +
                "WHERE " +
                "        sd.VALI_FLAG = '1' ";
        getSetlDWhere(setlD, sql);
        sql += " GROUP BY sd.POOLAREA_NO, sd.FIXMEDINS_CODE, sd.FIXMEDINS_NAME";
        return sql;
    }

    public String setlDByDiseNoPay(Page page,SetlDPay setlDPay) {
        String sql = "SELECT " +
//                "        IF( sd.AGE >=70 , '是' , '否' ) AGE_FLAG, " +
                "        sd.SETL_ID, " +
                "        sd.CLR_OPTINS, " +
                "        sd.MEDINS_SETL_ID, " +
                "        sd.MDTRT_ID, " +
                "        sd.INIT_SETL_ID, " +
                "        sd.PSN_NO, " +
                "        sd.PSN_INSU_RLTS_ID, " +
                "        sd.PSN_NAME, " +
                "        sd.PSN_CERT_TYPE, " +
                "        sd.GEND, " +
                "        sd.CERTNO, " +
                "        sd.NATY, " +
                "        sd.BRDY, " +
                "        sd.AGE, " +
                "        sd.INSUTYPE, " +
                "        sd.PSN_TYPE, " +
                "        sd.CVLSERV_FLAG, " +
                "        sd.CVLSERV_LV, " +
                "        sd.SP_PSN_TYPE, " +
                "        sd.SP_PSN_TYPE_LV, " +
                "        sd.CLCT_GRDE, " +
                "        sd.FLXEMPE_FLAG, " +
                "        sd.NWB_FLAG, " +
                "        sd.INSU_ADMDVS, " +
                "        sd.EMP_NO, " +
                "        sd.EMP_NAME, " +
                "        sd.EMP_TYPE, " +
                "        sd.ECON_TYPE, " +
                "        sd.AFIL_INDU, " +
                "        sd.AFIL_RLTS, " +
                "        sd.EMP_MGT_TYPE, " +
                "        sd.FIXMEDINS_CODE, " +
                "        sd.FIXMEDINS_NAME, " +
                "        sd.PAY_LOC, " +
                "        sd.HOSP_LV, " +
                "        sd.FIX_BLNG_ADMDVS, " +
                "        sd.LMTPRIC_HOSP_LV, " +
                "        sd.DEDC_HOSP_LV, " +
                "        sd.BEGNDATE, " +
                "        sd.ENDDATE, " +
                "        sd.SETL_TIME, " +
                "        sd.MDTRT_CERT_NO, " +
                "        sd.MDTRT_CERT_TYPE, " +
                "        sd.MED_TYPE, " +
                "        sd.SETL_TYPE, " +
                "        sd.CLR_TYPE, " +
                "        sd.CLR_WAY, " +
                "        sd.PSN_SETLWAY, " +
                "        sd.MEDFEE_SUMAMT, " +
                "        sd.FULAMT_OWNPAY_AMT, " +
                "        sd.OVERLMT_SELFPAY, " +
                "        sd.PRESELFPAY_AMT, " +
                "        sd.INSCP_AMT, " +
                "        sd.DEDC_STD, " +
                "        sd.CRT_DEDC, " +
                "        sd.ACT_PAY_DEDC, " +
                "        sd.HIFP_PAY, " +
                "        sd.POOL_PROP_SELFPAY, " +
                "        sd.HI_AGRE_SUMFEE, " +
                "        sd.CVLSERV_PAY, " +
                "        sd.HIFES_PAY, " +
                "        sd.HIFMI_PAY, " +
                "        sd.HIFOB_PAY, " +
                "        sd.HIFDM_PAY, " +
                "        sd.MAF_PAY, " +
                "        sd.OTHFUND_PAY, " +
                "        sd.FUND_PAY_SUMAMT, " +
                "        sd.PSN_PAY, " +
                "        sd.ACCT_PAY, " +
                "        sd.CASH_PAYAMT, " +
                "        sd.OWNPAY_HOSP_PART, " +
                "        sd.BALC, " +
                "        sd.ACCT_MULAID_PAY, " +
                "        sd.REFD_SETL_FLAG, " +
                "        sd.CAL_IPT_CNT, " +
                "        sd.SETL_CASHPAY_WAY, " +
                "        sd.YEAR, " +
                "        sd.DISE_NO, " +
                "        sd.DISE_NAME, " +
                "        sd.INVONO, " +
                "        sd.MANL_REIM_REA, " +
                "        sd.VALI_FLAG  " +
                "FROM " +
                "        SETLCENT_DB.SETL_D sd " +
                "WHERE " +
                "        sd.VALI_FLAG = '1' AND sd.dise_no IS NOT NULL AND sd.dise_no <> ' ' ";
        return sql;
    }

    public String setlDByDiseNo(Page page,SetlD setlD) {
        String sql = "SELECT " +
//                "        IF( sd.AGE >=70 , '是' , '否' ) AGE_FLAG, " +
                "        sd.SETL_ID, " +
                "        sd.CLR_OPTINS, " +
                "        sd.MEDINS_SETL_ID, " +
                "        sd.MDTRT_ID, " +
                "        sd.INIT_SETL_ID, " +
                "        sd.PSN_NO, " +
                "        sd.PSN_INSU_RLTS_ID, " +
                "        sd.PSN_NAME, " +
                "        sd.PSN_CERT_TYPE, " +
                "        sd.GEND, " +
                "        sd.CERTNO, " +
                "        sd.NATY, " +
                "        sd.BRDY, " +
                "        sd.AGE, " +
                "        sd.INSUTYPE, " +
                "        sd.PSN_TYPE, " +
                "        sd.CVLSERV_FLAG, " +
                "        sd.CVLSERV_LV, " +
                "        sd.SP_PSN_TYPE, " +
                "        sd.SP_PSN_TYPE_LV, " +
                "        sd.CLCT_GRDE, " +
                "        sd.FLXEMPE_FLAG, " +
                "        sd.NWB_FLAG, " +
                "        sd.INSU_ADMDVS, " +
                "        sd.EMP_NO, " +
                "        sd.EMP_NAME, " +
                "        sd.EMP_TYPE, " +
                "        sd.ECON_TYPE, " +
                "        sd.AFIL_INDU, " +
                "        sd.AFIL_RLTS, " +
                "        sd.EMP_MGT_TYPE, " +
                "        sd.FIXMEDINS_CODE, " +
                "        sd.FIXMEDINS_NAME, " +
                "        sd.PAY_LOC, " +
                "        sd.HOSP_LV, " +
                "        sd.FIX_BLNG_ADMDVS, " +
                "        sd.LMTPRIC_HOSP_LV, " +
                "        sd.DEDC_HOSP_LV, " +
                "        sd.BEGNDATE, " +
                "        sd.ENDDATE, " +
                "        sd.SETL_TIME, " +
                "        sd.MDTRT_CERT_NO, " +
                "        sd.MDTRT_CERT_TYPE, " +
                "        sd.MED_TYPE, " +
                "        sd.SETL_TYPE, " +
                "        sd.CLR_TYPE, " +
                "        sd.CLR_WAY, " +
                "        sd.PSN_SETLWAY, " +
                "        sd.MEDFEE_SUMAMT, " +
                "        sd.FULAMT_OWNPAY_AMT, " +
                "        sd.OVERLMT_SELFPAY, " +
                "        sd.PRESELFPAY_AMT, " +
                "        sd.INSCP_AMT, " +
                "        sd.DEDC_STD, " +
                "        sd.CRT_DEDC, " +
                "        sd.ACT_PAY_DEDC, " +
                "        sd.HIFP_PAY, " +
                "        sd.POOL_PROP_SELFPAY, " +
                "        sd.HI_AGRE_SUMFEE, " +
                "        sd.CVLSERV_PAY, " +
                "        sd.HIFES_PAY, " +
                "        sd.HIFMI_PAY, " +
                "        sd.HIFOB_PAY, " +
                "        sd.HIFDM_PAY, " +
                "        sd.MAF_PAY, " +
                "        sd.OTHFUND_PAY, " +
                "        sd.FUND_PAY_SUMAMT, " +
                "        sd.PSN_PAY, " +
                "        sd.ACCT_PAY, " +
                "        sd.CASH_PAYAMT, " +
                "        sd.OWNPAY_HOSP_PART, " +
                "        sd.BALC, " +
                "        sd.ACCT_MULAID_PAY, " +
                "        sd.REFD_SETL_FLAG, " +
                "        sd.CAL_IPT_CNT, " +
                "        sd.SETL_CASHPAY_WAY, " +
                "        sd.YEAR, " +
                "        sd.DISE_NO, " +
                "        sd.DISE_NAME, " +
                "        sd.INVONO, " +
                "        sd.MANL_REIM_REA, " +
                "        sd.VALI_FLAG  " +
                "FROM " +
                "        SETLCENT_DB.SETL_D sd " +
                "WHERE " +
                "        sd.VALI_FLAG = '1' AND sd.dise_no IS NOT NULL AND sd.dise_no <> ' ' ";
        getSetlDWhere(setlD, sql);
        return sql;
    }

    public String setlDByDiseNoCount(SetlD setlD) {
        String sql = "SELECT " +
                "        sd.POOLAREA_NO,sd.FIXMEDINS_CODE,sd.FIXMEDINS_NAME, " +
                "        sum( sd.MEDFEE_SUMAMT ) MEDFEE_SUMAMT, " +//医疗费总额
                "        sum( sd.POOL_PROP_SELFPAY ) POOL_PROP_SELFPAY, " +//基本医疗统筹支付比例
                "        sum( sd.HIFOB_PAY ) HIFOB_PAY, " +//大额医疗补助基金支出
                "        sum( sd.CVLSERV_PAY ) CVLSERV_PAY, " +//公务员医疗补助资金支出
                "        sum( sd.ACCT_PAY ) ACCT_PAY, " +//个人账户支出
                "        sum( sd.CASH_PAYAMT ) CASH_PAYAMT, " +//现金支付金额
                "        sum( sd.OWNPAY_HOSP_PART ) OWNPAY_HOSP_PART, " +//自费中医院负担部分
                "        sum( sd.HIFES_PAY ) HIFES_PAY, " +//补充医疗保险基金支出
                "        sum( sd.HIFMI_PAY ) HIFMI_PAY, " +//大病补充医疗保险基金支出
                "        sum( sd.MAF_PAY ) MAF_PAY, " +//医疗救助基金支出
                "        sum( sd.HIFDM_PAY ) HIFDM_PAY " +//伤残人员医疗保障基金支出
                "FROM " +
                "        SETLCENT_DB.SETL_D sd " +
                "WHERE " +
                "        sd.VALI_FLAG = '1' AND sd.dise_no IS NOT NULL AND sd.dise_no <> ' ' ";
        getSetlDWhere(setlD, sql);
        sql += " GROUP BY sd.POOLAREA_NO, sd.FIXMEDINS_CODE, sd.FIXMEDINS_NAME";
        return sql;
    }

    @NotNull
    private String getSetlDWhere(SetlD setlD, String sql) {
        if (!"".equals(setlD.getPoolarea_no()) && null != setlD.getPoolarea_no()) {
            sql += " and sd.POOLAREA_NO = '" + setlD.getPoolarea_no() + "' ";
        }
        if (!"".equals(setlD.getMed_type()) && null != setlD.getMed_type()) {
            sql += " and sd.med_type in (" + setlD.getMed_type() + ") ";
        }
        if (!"".equals(setlD.getSetl_type()) && null != setlD.getSetl_type()) {
            sql += " and sd.setl_type = '" + setlD.getSetl_type() + "' ";
        }
        if (!"".equals(setlD.getInsutype()) && null != setlD.getInsutype()) {
            sql += " and sd.insutype = '" + setlD.getInsutype() + "' ";
        }
        if (!"".equals(setlD.getBegntime()) && null != setlD.getBegntime()) {
            sql += " AND TO_CHAR( sd.CRTE_TIME, 'yyyy-MM-dd' ) >= '" + setlD.getBegntime() + "' ";
        }
        if (!"".equals(setlD.getEndtime()) && null != setlD.getEndtime()) {
            sql += " AND TO_CHAR( sd.CRTE_TIME, 'yyyy-MM-dd' ) <= '" + setlD.getEndtime() + "' ";
        }
        return sql;
    }

    public String childbirth(ChildbirthAllowance childbirthAllowance) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }

    public String hospitalization(HospitalizationCosts hospitalizationCosts) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }

    public String setlDMonth(SetlDMonth setlDMonth) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }

    public String setlDMonthGather(SetlDMonthGather setlDMonthGather) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }

    public String SetlDYear(SetlDYear setlDYear) {
        String sql = "SELECT " +
                "        sd.POOLAREA_NO,sd.FIXMEDINS_CODE,sd.FIXMEDINS_NAME, " +
                "        sum(case when sd.REFD_SETL_FLAG = '0' then 1 else 0 end ) PERSON_COUNT, " +//就诊人次
                "        count( DISTINCT sd.CERTNO ) PERSON_NUM, " +//就诊人数
                "        sum( sd.MEDFEE_SUMAMT ) MEDFEE_SUMAMT, " +//医疗费总额
                "        sum( sd.POOL_PROP_SELFPAY ) POOL_PROP_SELFPAY, " +//基本医疗统筹支付比例
                "        sum( sd.HIFOB_PAY ) HIFOB_PAY, " +//大额医疗补助基金支出
                "        sum( sd.CVLSERV_PAY ) CVLSERV_PAY, " +//公务员医疗补助资金支出
                "        sum( sd.ACCT_PAY ) ACCT_PAY, " +//个人账户支出
                "        sum( sd.CASH_PAYAMT ) CASH_PAYAMT, " +//现金支付金额
                "        sum( sd.OWNPAY_HOSP_PART ) OWNPAY_HOSP_PART, " +//自费中医院负担部分
                "        sum( sd.HIFES_PAY ) HIFES_PAY, " +//补充医疗保险基金支出
                "        sum( sd.HIFMI_PAY ) HIFMI_PAY, " +//大病补充医疗保险基金支出
                "        sum( sd.MAF_PAY ) MAF_PAY, " +//医疗救助基金支出
                "        sum( sd.HIFDM_PAY ) HIFDM_PAY " +//伤残人员医疗保障基金支出
                "FROM " +
                "        SETLCENT_DB.SETL_D sd " +
                "WHERE " +
                "        sd.VALI_FLAG = '1' ";
        if (!"".equals(setlDYear.getPoolarea_no()) && null != setlDYear.getPoolarea_no()) {
            sql += " and sd.POOLAREA_NO = '" + setlDYear.getPoolarea_no() + "' ";
        }
        if (!"".equals(setlDYear.getMed_type()) && null != setlDYear.getMed_type()) {
            sql += " and sd.med_type in (" + setlDYear.getMed_type() + ") ";
        }
        if (!"".equals(setlDYear.getSetl_type()) && null != setlDYear.getSetl_type()) {
            sql += " and sd.setl_type = '" + setlDYear.getSetl_type() + "' ";
        }
        if (!"".equals(setlDYear.getInsutype()) && null != setlDYear.getInsutype()) {
            sql += " and sd.insutype = '" + setlDYear.getInsutype() + "' ";
        }
        if (!"".equals(setlDYear.getBegntime()) && null != setlDYear.getBegntime()) {
            sql += " AND TO_CHAR( sd.CRTE_TIME, 'yyyy-MM-dd' ) >= '" + setlDYear.getBegntime() + "' ";
        }
        if (!"".equals(setlDYear.getEndtime()) && null != setlDYear.getEndtime()) {
            sql += " AND TO_CHAR( sd.CRTE_TIME, 'yyyy-MM-dd' ) <= '" + setlDYear.getEndtime() + "' ";
        }
        sql += " GROUP BY sd.POOLAREA_NO, sd.FIXMEDINS_CODE, sd.FIXMEDINS_NAME";
        return sql;
    }

    public String sporadicHandle(SporadicHandle sporadicHandle) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }

    public String personal(PersonalChanges personalChanges) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }

    public String organization(OrganizationGinseng organizationGinseng) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }


    public String schoolCount(School school) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }

    public String schoolDetail(School school) {
        String sql = "select * from BASINFOCENT_DB.PSN_INFO_B ";
        return sql;
    }
}
