package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class LoanApplicationDao {

    /**
    *最近3年年结算总额
    * Author wzn
    * Date 2022/7/8 15:18
    */
    public String billingData(String fixmedins_code){
        String sql = "SELECT\n" +
                "\tSUM (\n" +
                "\t\tCASE\n" +
                "\t\tWHEN TO_CHAR (SETL_TIME, 'yyyy') = (select extract(year from sysdate) from dual) THEN\n" +
                "\t\t\tMEDFEE_SUMAMT\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) year,\n" +
                "SUM (\n" +
                "\t\tCASE\n" +
                "\t\tWHEN TO_CHAR (SETL_TIME, 'yyyy') = (select extract(year from sysdate) -1 from dual) THEN\n" +
                "\t\t\tMEDFEE_SUMAMT\n" +
                "\t\tELSE\n" +
                "\t\t\t0\n" +
                "\t\tEND\n" +
                "\t) oldyear \n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D\n" +
                "WHERE\n" +
                "\tFIXMEDINS_CODE = '"+fixmedins_code+"'\n" +
                "AND VALI_FLAG = '1'\n" +
                "AND REFD_SETL_FLAG = '0'" ;
        return sql ;
    }


    /**
    *银行放贷统计数据
    * Author wzn
    * Date 2022/7/12 14:14
    */
    public String bankTj(String bankNo){
        String sql ="SELECT\n" +
                "SUM (CASE WHEN bank_satus = '1' THEN 1 ELSE 0\n" +
                "  END\n" +
                ") passQuantity,\n" +
                "SUM (CASE WHEN bank_satus = '2' THEN 1 ELSE 0\n" +
                "  END\n" +
                ") numberOfRejections,\n" +
                "SUM (CASE WHEN bank_satus = '1' THEN determineTheAmount ELSE '0'\n" +
                "  END\n" +
                ") loanAmount\n" +
                "FROM\n" +
                "\tLOANAPPLY where apply_bank_id = '"+bankNo+"'" ;
        return sql ;
    }
}
