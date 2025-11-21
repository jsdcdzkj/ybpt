package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class AgreementDao {
    public String getFirstRecord() {
        String sql = "SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\tAGREEMENT \n" +
                "WHERE\n" +
                "\tROWNUM <= 1";
        return sql;
    }
}
