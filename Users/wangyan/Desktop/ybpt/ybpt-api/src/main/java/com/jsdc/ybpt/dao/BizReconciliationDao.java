package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class BizReconciliationDao {

    /**
     * 日对账
     *
     * @param settleDate
     * @param insutype
     * @param medTypes
     * @param psnTypes
     * @return
     */
    public String getReflowReconciliationData(String settleDate, String insutype, String medTypes, String psnTypes, String fixmedins_code, String insu_admdvs) {
        String sql = "SELECT\n" +
                "\tFIXMEDINS_CODE fixmedins_code,\n" +
//                "\tFIXMEDINS_NAME fixmedins_name,\n" +
                "\tINSU_ADMDVS insu_admdvs,\n" +
                "\tsum( CASE WHEN REFD_SETL_FLAG = '1' AND init_setl_id IS NOT NULL THEN - 1 ELSE 1 END ) person_count,\n" +
                "\tcount( DISTINCT CERTNO ) person_num,\n" +
                "\tsum( MEDFEE_SUMAMT ) medfee_sumamt,\n" +
                "\tsum( HIFP_PAY ) hifp_pay,\n" +
                "\tsum( CVLSERV_PAY ) cvlserv_pay,\n" +
                "\tsum( HIFES_PAY ) hifes_pay,\n" +
                "\tsum( HIFMI_PAY ) hifmi_pay,\n" +
                "\tsum( HIFOB_PAY ) hifob_pay,\n" +
                "\tsum( MAF_PAY ) maf_pay,\n" +
                "\tsum( OTHFUND_PAY ) othfund_pay,\n" +
                "\tsum( ACCT_PAY ) acct_pay,\n" +
                "\tsum( CASH_PAYAMT ) cash_payamt,\n" +
                "\tsum( HIFDM_PAY ) hifdm_pay,\n" +
                "\tsum( ACCT_MULAID_PAY ) acct_mulaid_pay,\n" +
                "\tsum( OWNPAY_HOSP_PART ) ownpay_hosp_part \n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D \n" +
                "WHERE\n" +
                "\tTO_CHAR( SETL_TIME, 'yyyy-MM-dd' ) = '" + settleDate + "' \n" +
                "\tAND VALI_FLAG = '1' \n" +
                "\tAND PAY_LOC != '1'  \n" +
                "\tand INSUTYPE='" + insutype + "'\n" +
                "\tand MED_TYPE in (" + medTypes + ")\n" +
                "\tand PSN_TYPE in (" + psnTypes + ")\n";
        if (StringUtils.isNotEmpty(fixmedins_code)) {
            sql += "\tand FIXMEDINS_CODE = '" + fixmedins_code + "'\n";
        }
        if (StringUtils.isNotEmpty(insu_admdvs)) {
            sql += "\tand INSU_ADMDVS = '" + insu_admdvs + "'\n";
        }
        sql += "GROUP BY\n" +
                "\tFIXMEDINS_CODE,\n" +
//                "\tFIXMEDINS_NAME,\n" +
                "\tINSU_ADMDVS";
        return sql;
    }

    /**
     * 异地日对账
     *
     * @param settleDate
     * @param insutype
     * @param medTypes
     * @param psnTypes
     * @return
     */
    public String getReflowReconciliationData_yd(String settleDate, String insutype, String medTypes, String psnTypes, String fixmedins_code, String insu_admdvs) {
        String sql = "SELECT\n" +
                "\tFIXMEDINS_CODE fixmedins_code,\n" +
//                "\tFIXMEDINS_NAME fixmedins_name,\n" +
                "\tPAY_LOC insu_admdvs,\n" +
                "\tsum( CASE WHEN REFD_SETL_FLAG = '1' AND init_setl_id IS NOT NULL THEN - 1 ELSE 1 END ) person_count,\n" +
                "\tcount( DISTINCT CERTNO ) person_num,\n" +
                "\tsum( MEDFEE_SUMAMT ) medfee_sumamt,\n" +
                "\tsum( HIFP_PAY ) hifp_pay,\n" +
                "\tsum( CVLSERV_PAY ) cvlserv_pay,\n" +
                "\tsum( HIFES_PAY ) hifes_pay,\n" +
                "\tsum( HIFMI_PAY ) hifmi_pay,\n" +
                "\tsum( HIFOB_PAY ) hifob_pay,\n" +
                "\tsum( MAF_PAY ) maf_pay,\n" +
                "\tsum( OTHFUND_PAY ) othfund_pay,\n" +
                "\tsum( ACCT_PAY ) acct_pay,\n" +
                "\tsum( CASH_PAYAMT ) cash_payamt,\n" +
                "\tsum( HIFDM_PAY ) hifdm_pay,\n" +
                "\tsum( ACCT_MULAID_PAY ) acct_mulaid_pay,\n" +
                "\tsum( OWNPAY_HOSP_PART ) ownpay_hosp_part \n" +
                "FROM\n" +
                "\tSETLCENT_DB.OUTMED_SETL_D \n" +
                "WHERE\n" +
                "\tTO_CHAR( SETL_TIME, 'yyyy-MM-dd' ) = '" + settleDate + "' \n" +
                "\tAND VALI_FLAG = '1' \n" +
                "\tAND PAY_LOC IN ( '3', '4' )  \n" +
                "\tand INSUTYPE='" + insutype + "'\n" +
                "\tand MED_TYPE in (" + medTypes + ")\n" +
                "\tand INSU_ADMDVS not in ('320399', '320312', '320321', '320322', '320324', '320381', '320382', '320305')\n" +
                "\tand PSN_TYPE in (" + psnTypes + ")\n";
        if (StringUtils.isNotEmpty(fixmedins_code)) {
            sql += "\tand FIXMEDINS_CODE = '" + fixmedins_code + "'\n";
        }
        if (StringUtils.isNotEmpty(insu_admdvs)) {
            sql += "\tand PAY_LOC = '" + insu_admdvs + "'\n";
        }
        sql += "GROUP BY\n" +
                "\tFIXMEDINS_CODE,\n" +
                "\tPAY_LOC\n" ;
        return sql;
    }
    public String getDetailsExcel(String settleDate, String insutype, String medTypes, String psnTypes, String fixmedins_code, String insu_admdvs) {
        String sql = "SELECT\n" +
                "\tTO_CHAR( SETL_TIME, 'yyyy-MM-dd' ) SETL_DATE,\n" +
                "\tSETL_ID ,\n" +
                "\tMDTRT_ID,\n" +
                "\tINIT_SETL_ID,\n" +
                "\tMEDINS_SETL_ID,\n" +
                "\tPSN_NO,\n" +
                "\tPSN_TYPE,\n" +
                "\tCERTNO,\n" +
                "\tINSUTYPE,\n" +
                "\tSETL_TIME,\n" +
                "\tMED_TYPE,\n" +
                "\tINSU_ADMDVS,\n" +
                "\tFIXMEDINS_CODE,\n" +
                "\tFIXMEDINS_NAME,\n" +
                "\tREFD_SETL_FLAG\n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D \n" +
                "WHERE\n" +
                "\tTO_CHAR( SETL_TIME, 'yyyy-MM-dd' ) = '" + settleDate + "' \n" +
                "\tAND VALI_FLAG = '1' \n" +
                "\tAND PAY_LOC != '1'  \n" +
                "\tand INSUTYPE='" + insutype + "'\n" +
                "\tand MED_TYPE in (" + medTypes + ")\n" +
                "\tand PSN_TYPE in (" + psnTypes + ")\n";
                if (StringUtils.isNotEmpty(fixmedins_code)) {
                    sql += "\tand FIXMEDINS_CODE = '" + fixmedins_code + "'\n";
                }
                if (StringUtils.isNotEmpty(insu_admdvs)) {
                    sql += "\tand INSU_ADMDVS = '" + insu_admdvs + "'\n";
                }
        return sql;
    }

    public String getDetailsExcel_yd(String settleDate, String insutype, String medTypes, String psnTypes, String fixmedins_code, String insu_admdvs) {
        String sql = "SELECT\n" +
                "\tTO_CHAR( SETL_TIME, 'yyyy-MM-dd' ) SETL_DATE,\n" +
                "\tSETL_ID ,\n" +
                "\tMDTRT_ID,\n" +
                "\tINIT_SETL_ID,\n" +
                "\tMEDINS_SETL_ID,\n" +
                "\tPSN_NO,\n" +
                "\tPSN_TYPE,\n" +
                "\tCERTNO,\n" +
                "\tINSUTYPE,\n" +
                "\tSETL_TIME,\n" +
                "\tMED_TYPE,\n" +
                "\tPAY_LOC INSU_ADMDVS,\n" +
                "\tFIXMEDINS_CODE,\n" +
                "\tFIXMEDINS_NAME,\n" +
                "\tREFD_SETL_FLAG\n" +
                "FROM\n" +
                "\tSETLCENT_DB.OUTMED_SETL_D \n" +
                "WHERE\n" +
                "\tTO_CHAR( SETL_TIME, 'yyyy-MM-dd' ) = '" + settleDate + "' \n" +
                "\tAND VALI_FLAG = '1' \n" +
                "\tAND PAY_LOC IN ( '3', '4' )  \n" +
                "\tand INSUTYPE='" + insutype + "'\n" +
                "\tand MED_TYPE in (" + medTypes + ")\n" +
                "\tand INSU_ADMDVS not in ('320399', '320312', '320321', '320322', '320324', '320381', '320382', '320305')\n" +
                "\tand PSN_TYPE in (" + psnTypes + ")\n";
        if (StringUtils.isNotEmpty(fixmedins_code)) {
            sql += "\tand FIXMEDINS_CODE = '" + fixmedins_code + "'\n";
        }
        if (StringUtils.isNotEmpty(insu_admdvs)) {
            sql += "\tand PAY_LOC = '" + insu_admdvs + "'\n";
        }
        return sql;
    }
}
