package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class OpspRegdDao {

    public String selectByIdCard(String certno,String psn_cert_type){
        String sql = "SELECT\n" +
                "\tpib.psn_name ,pib.gend,pib.naty,pib.brdy,pib.mob,pib.certno,pib.psn_cert_type ,pib.PSN_NO,pib.live_addr \n" +
                "FROM\n" +
                "\tBASINFOCENT_DB.psn_info_b pib\n" +
                "WHERE\n" +
                "\tcertno = '"+certno+"' \n" +
                "\tAND PSN_CERT_TYPE = '"+psn_cert_type+"'  and VALI_FLAG='1'" ;
        return sql;
    }


    public String selectCompanyByIdCard(String certno,String psn_cert_type){
        String sql = "SELECT\n" +
                "iei.emp_name,pid.insu_admdvs,pid.insutype \n" +
                "FROM\n" +
                "\tBASINFOCENT_DB.psn_info_b pib\n" +
                "\tLEFT JOIN INSUCENT_DB.PSN_INSU_D pid on pid.PSN_NO = pib.PSN_NO\n" +
                "\tleft join CUSTCENT_DB.INSU_EMP_INFO_B iei on iei.emp_no = pid.emp_no " +
                "WHERE\n 1=1 " +
                "\tand pib.certno = '"+certno+"' \n" +
                "\tAND pib.PSN_CERT_TYPE = '"+psn_cert_type+"' and pid.PAUS_INSU_DATE is null  and pib.VALI_FLAG='1' and (pid.INSUTYPE = '310' or  pid.INSUTYPE = '390')" ;
        return sql;
    }

    public String selectList(String certNo){
        String sql= "SELECT\n" +
                "\tod.approvalStatus,\n" +
                "\tod.IS_DEL,\n" +
                "\tod.PSN_NAME,\n" +
                "\tod.PSN_CERT_TYPE,\n" +
                "\tod.CERTNO,\n" +
                "\tri.OPSP_DISE_NAME,\n" +
                "\tod.EMP_NAME\n" +
                "FROM\n" +
                "\tOPSP_DISE od\n" +
                "\tLEFT JOIN REG_INFO ri ON ri.OPSP_id = od.id\n" +
                "\tWHERE od.CERTNO='"+certNo+"'" ;
        return sql ;
    }
}
