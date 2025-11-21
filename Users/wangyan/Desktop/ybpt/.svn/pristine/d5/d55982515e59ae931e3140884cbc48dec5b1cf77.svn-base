package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagAgreement;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalOrgDao {

    /**
     * 医疗机构信息
     * 分页查询
     */
    public String selectMedicalDept(Page page, String fixmedins_code, String fixmedins_name, String dept_no, String dept_name) {
        String sql = "SELECT\n" +
                "\ta.FIXMEDINS_CODE fixmedins_code,\n" +
                "\tb.MEDINS_NAME fixmedins_name,\n" +
                "\ta.DEPT_NO dept_no,\n" +
                "\ta.DEPT_NAME dept_name,\n" +
                "\ta.BEGNTIME begntime,\n" +
                "\ta.ENDTIME endtime,\n" +
                "\ta.DEPT_RESPER_NAME dept_resper_name,\n" +
                "\ta.DEPT_RESPER_TEL dept_resper_tel,\n" +
                "\ta.APRV_BED_CNT aprv_bed_cnt,\n" +
                "\ta.DR_PSNCNT dr_psncnt,\n" +
                "\ta.PHAR_PSNCNT phar_psncnt,\n" +
                "\ta.NURS_PSNCNT nurs_psncnt,\n" +
                "\ta.TECN_PSNCNT tecn_psncnt\n" +
                "FROM\n" +
                "\tCUSTCENT_DB.MEDINS_DEPT_B a\n" +
                "\tLEFT JOIN CUSTCENT_DB.MEDINS_INFO_B b ON a.FIXMEDINS_CODE = b.MEDINS_CODE\n" +
                "where a.VALI_FLAG = '1'\n";
        if (StringUtils.isNotEmpty(fixmedins_code)) {
            sql += "and a.FIXMEDINS_CODE='" + fixmedins_code + "'\n";
        }
        if (StringUtils.isNotEmpty(dept_no)) {
            sql += "and a.DEPT_NO='" + dept_no + "'\n";
        }
        if (StringUtils.isNotEmpty(fixmedins_name)) {
            sql += "and b.MEDINS_NAME like '%" + fixmedins_name + "%'\n";
        }
        if (StringUtils.isNotEmpty(dept_name)) {
            sql += "and a.DEPT_NAME like '%" + dept_name + "%'";
        }
        return sql;

    }

    /**
     * 医疗机构信息
     * 根据编码获取详细信息
     *
     * @authon zln
     */
    public String selectByMedicalOrgId(String fixmedins_code) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select NB.MEDINS_CODE as fixmedins_code,NB.MEDINS_NAME as fixmedins_name, ");
        buffer.append(" NB.ADDR as addr,NB.MEDINSLV as cred_lv,nat_dic_val_name as cred_lv_name,NB.LEGREP_NAME as legrep_name, ");
        buffer.append(" NB.BIZNAT as biz_way,'1' as cred_lv_type from CUSTCENT_DB.MEDINS_INFO_B nb  ");
        buffer.append(" LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A dta on dta.nat_dic_val_code=nb.MEDINSLV and dta.dic_type_code='MEDINSLV' ");
        buffer.append(" where NB.VALI_FLAG ='1'  ");
        if (StringUtils.isNotEmpty(fixmedins_code)) {
            buffer.append("and  nb.MEDINS_CODE = '" + fixmedins_code + "'");
        }
        return buffer.toString();
    }

    /**
     * 医疗机构信息根据等级获取
     *
     * @authon zdq
     */
    public String selectByMedicalLevel(NetTagAgreement netTagAgreement) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select NB.MEDINS_CODE as fixmedins_code,NB.MEDINS_NAME as fixmedins_name, ");
        buffer.append(" NB.ADDR as addr,NB.MEDINSLV as cred_lv,nat_dic_val_name as cred_lv_name,NB.LEGREP_NAME as legrep_name, ");
        buffer.append(" NB.BIZNAT as biz_way,'1' as cred_lv_type from CUSTCENT_DB.MEDINS_INFO_B nb  ");
        buffer.append(" LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A dta on dta.nat_dic_val_code=nb.MEDINSLV and dta.dic_type_code='MEDINSLV' ");
        buffer.append(" where NB.VALI_FLAG ='1'  ");
        if (StringUtils.isNotEmpty(netTagAgreement.getNet_grade_id())) {
            buffer.append("and  nb.MEDINSLV = '" + netTagAgreement.getNet_grade_id() + "'");
        }
        if (StringUtils.isNotEmpty(netTagAgreement.getNet_grade_ids())) {
            StringBuffer sb = new StringBuffer();
            for (String str: netTagAgreement.getNet_grade_ids()) {
                sb.append(",'" + str + "'");
            }
            buffer.append("and  nb.MEDINSLV in (" + sb.toString().substring(1) +")");
        }
        return buffer.toString();
    }

    /**
     * 零售药店信息
     */
    public String getRtalPhacB(String fixmedins_code) {
        String sql = "SELECT rp.RTAL_PHAC_CODE as fixmedins_code, " +
                "       rp.RTAL_PHAC_ABBR, " +
                "       rp.RTAL_PHAC_NAME as fixmedins_name, " +
                "       rp.PRNT_RTAL_PHAC_CODE, " +
                "       rp.ITRO, " +
                "       rp.USCC as uscc, " +
                "       rp.LNT, " +
                "       rp.TEL as tel, " +
                "       rp.LAT, " +
                "       rp.ADDR as addr, " +
                "       rp.ADMDVS, " +
                "       rp.BEGNTIME, " +
                "       rp.ENDTIME, " +
                "       rp.BRCH_FLAG, " +
                "       rp.DRUG_BIZ_LIC_NO as drug_biz_lic_no, " +
                "       rp.MEMO, " +
                "       rp.DRUG_BIZ_SCP, " +
                "       rp.ECON_TYPE, " +
                "       rp.PHAR_PSNCNT, " +
                "       rp.PHAC_CHAN_TYPE, " +
                "       rp.EQU_BIZ_LIC_NO, " +
                "       rp.CRED_LV as cred_lv, " +
                "       rp.CRED_LV_NAME as cred_lv_name, " +
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
                "       rp.LEGREP_NAME as legrep_name, " +
                "       rp.LEGREP_CERT_TYPE, " +
                "       rp.LEGREP_CERTNO, " +
                "       rp.REGCAP, " +
                "       rp.BIZ_LIC_EXPY_BEGNTIME, " +
                "       rp.BIZ_LIC_EXPY_ENDTIME, " +
                "       rp.BIZ_WAY as biz_way, " +
                "       rp.ENTP_RESPER, " +
                "       rp.HI_CONER_NAME, " +
                "       rp.HI_CONER_TEL as hi_resper_tel, " +
                "       rp.RTAL_PHAC_EMAIL, " +
                "       rp.BANK_NAME, " +
                "       rp.BANKACCT, " +
                "       LEGREP_CERT_TYPE.NAT_DIC_VAL_NAME LEGREP_CERT_TYPE_NAME " +
                "FROM CUSTCENT_DB.RTAL_PHAC_B rp ";

        sql += "LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A LEGREP_CERT_TYPE " +
                "on rp.LEGREP_CERT_TYPE=LEGREP_CERT_TYPE.NAT_DIC_VAL_CODE " +
                "and LEGREP_CERT_TYPE.DIC_TYPE_CODE='PSN_CERT_TYPE' ";

        sql += "where rp.VALI_FLAG = 1 ";
        if (StringUtils.isNotEmpty(fixmedins_code)) {
            sql += "and rp.RTAL_PHAC_CODE = " + "'" + fixmedins_code + "' ";
        }
        return sql;
    }

    //获取机构等级数据
    public String selectOrganizationLevel(String type) {
        StringBuffer buffer = new StringBuffer();
        if ("1".equals(type)) {
            buffer.append("SELECT NAT_DIC_VAL_CODE as cred_lv,NAT_DIC_VAL_NAME as cred_lv_name from  POLCENT_DB.NAT_DATA_DIC_A b where VALI_FLAG ='1' and dic_type_code='MEDINSLV' ");
        } else {
            buffer.append("select cred_lv,cred_lv_name from CUSTCENT_DB.RTAL_PHAC_B c where VALI_FLAG = '1'  GROUP BY CRED_LV ");
        }
        return buffer.toString();
    }

    //药店机构信息根据等级获取
    public String selectPharmacyLevel(NetTagAgreement netTagAgreement) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" SELECT rp.RTAL_PHAC_CODE AS fixmedins_code, ");
        buffer.append(" rp.RTAL_PHAC_NAME AS fixmedins_name, ");
        buffer.append(" rp.PRNT_RTAL_PHAC_CODE, ");
        buffer.append(" rp.ADDR AS addr, ");
        buffer.append(" rp.ADMDVS ");
        buffer.append(" FROM CUSTCENT_DB.RTAL_PHAC_B rp ");
        buffer.append(" LEFT JOIN POLCENT_DB.NAT_DATA_DIC_A LEGREP_CERT_TYPE ON rp.LEGREP_CERT_TYPE = LEGREP_CERT_TYPE.NAT_DIC_VAL_CODE ");
        buffer.append(" AND LEGREP_CERT_TYPE.DIC_TYPE_CODE = 'PSN_CERT_TYPE' ");
        buffer.append(" WHERE rp.VALI_FLAG = 1 ");

        if (StringUtils.isNotEmpty(netTagAgreement.getNet_grade_id())) {
//            buffer.append(" and  rp.MEDINSLV = '" + level + "'");
        }
        if (StringUtils.isNotEmpty(netTagAgreement.getNet_grade_ids())) {
//            StringBuffer sb = new StringBuffer();
//            for (String str: netTagAgreement.getNet_grade_ids()) {
//                sb.append(",'" + str + "'");
//            }
//            buffer.append("and  nb.MEDINSLV in (" + sb.toString().substring(1) +")");
        }
        return buffer.toString();
    }
}