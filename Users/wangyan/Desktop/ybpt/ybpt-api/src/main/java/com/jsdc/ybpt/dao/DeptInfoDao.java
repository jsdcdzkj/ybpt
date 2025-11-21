package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class DeptInfoDao {
    public String getDeptInfoListByCertNo(String certNo) {
        String sql = "SELECT DI.ID,\n" +
                "       DI.DEPT_NAME,\n" +
                "       DI.DEPT_NO,\n" +
                "       DI.EMP_CODE\n" +
                "FROM DEPT_INFO DI\n" +
                "         INNER JOIN EMPLOYING_INFO EI ON EI.EMP_NO = DI.EMP_CODE\n" +
                "         INNER JOIN CIVILWORKER_INFO CI ON EI.EMP_NO = CI.EMP_CODE\n" +
                "WHERE DI.IS_DEL = '0' AND\n"+
                "CI.CERTNO = '" + certNo + "'";

        return sql;
    }
}

