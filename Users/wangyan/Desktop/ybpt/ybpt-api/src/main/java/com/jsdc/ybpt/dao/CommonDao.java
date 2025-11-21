package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class CommonDao {

    public String selectNameById(String dic_type_code,String nat_dic_val_code){
        String sql = "\tSELECT\n" +
                "\t\tNAT_DIC_VAL_NAME \n" +
                "\tFROM\n" +
                "\t\tPOLCENT_DB.nat_data_dic_a \n" +
                "\tWHERE\n" +
                "\t\tDIC_TYPE_CODE = '"+dic_type_code+"' \n" +
                "\t\tAND NAT_DIC_VAL_CODE = '"+nat_dic_val_code+"'" ;
        return sql;
    }

    public String selectDicList(String dic_type_code){
        String sql = "\tSELECT\n" +
                "\t\tNAT_DIC_VAL_NAME, \n" +
                "\t\tNAT_DIC_VAL_CODE \n" +
                "\tFROM\n" +
                "\t\tPOLCENT_DB.nat_data_dic_a \n" +
                "\tWHERE\n" +
                "\t\tDIC_TYPE_CODE = '"+dic_type_code+"' and DIC_SOUC_ADMDVS in('100000','320300')\n";
        return sql;
    }
}
