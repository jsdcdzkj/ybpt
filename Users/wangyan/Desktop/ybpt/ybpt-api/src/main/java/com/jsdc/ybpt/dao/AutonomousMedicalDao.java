package com.jsdc.ybpt.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public class AutonomousMedicalDao {
    /**
     * 统计 组织占比 sql 总数
     */
    public String getOrganization(String year) {
        String sql = "\tselect COUNT(*) from PERSON_SUBSCRIBE_RECORD where 1=1 \n";
        if (!"".equals(year)) {
            sql = sql + " AND  YEAR =  '" + year + "' \n" ;
        }
        return sql;
    }
    /**
     * 统计 组织占比 sql 自主数
     */
    public String getOrganizationZiZhu() {
        String sql ="\t SELECT\n"+
                "\t COUNT(*)\n"+
                "\tFROM\n"+
                "\t AUTONOMOUS_MEDICAL a\n"+
                "\t   INNER JOIN CIVILWORKER_INFO  c ON a.IMP_NO = c.EMP_CODE\n"+
                "\t    where STATUS = 1\n";
        return sql;
    }
}
