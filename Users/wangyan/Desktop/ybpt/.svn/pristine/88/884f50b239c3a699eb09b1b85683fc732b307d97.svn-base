package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PharInfoBDao {

    public String selectPharInfo(String phar_code,String phar_name){
        String sql = "SELECT\n" +
                "\tPHAR_CODE,\n" +
                "\tPHAR_NAME\n" +
                "FROM\n" +
                "\tCUSTCENT_DB.phar_info_b WHERE\n" +
                "\tADMDVS IN (\n" +
                "\t\t'320300'\n" +
                "\t)\n" +
                "and  VALI_FLAG ='1'\n" ;
        if(StringUtils.isNotEmpty(phar_code)){
            sql += " and PHAR_CODE = '"+phar_code+"'";
        }
        if(StringUtils.isNotEmpty(phar_name)){
            sql += " and PHAR_NAME LIKE '%"+phar_name+"%'" ;
        };
                sql +="UNION \n" +
                "SELECT\n" +
                "\tNURS_CODE,\n" +
                "\tNURS_NAME\n" +
                "FROM\n" +
                "\tCUSTCENT_DB.NURS_INFO_B\n" +
                "WHERE\n" +
                "\tADMDVS IN (\n" +
                "\t\t'320399',\n" +
                "\t\t'320382',\n" +
                "\t\t'320381',\n" +
                "\t\t'320324',\n" +
                "\t\t'320322',\n" +
                "\t\t'320321',\n" +
                "\t\t'320312',\n" +
                "\t\t'320305'\n" +
                "\t) and VALI_FLAG ='1' " ;
        if(StringUtils.isNotEmpty(phar_code)){
            sql += " and NURS_CODE = '"+phar_code+"'";
        }
        if(StringUtils.isNotEmpty(phar_name)){
            sql += " and NURS_NAME LIKE '%"+phar_name+"%'" ;
        };
        return sql;
    }
}
