package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class SbZlProjectDao {
    public String verify(String org_code,String project_code,String type) {
        String sql = "SELECT\n" +
                "COUNT (1)\n" +
                "FROM\n" +
                "SB_APPLY sa\n" +
                "WHERE sa.is_del = '0'\n" +
                "AND (\n" +
                "sa.STATUS = '0'\n" +
                "OR sa.STATUS = '1'\n" +
                "OR sa.STATUS = '2'\n" +
                ")\n" +
                "and sa.org_code = '"+org_code+"'\n" +
                "and sa.project_code = '"+project_code+"'\n" +
                "and sa.type = '"+type+"'\n";
        return sql;
    }


    public String verify2(String org_code,String project_code,String type) {
        String sql = "SELECT\n" +
                "months_between(sysdate,sa.end_time )\n" +
                "FROM\n" +
                "SB_APPLY sa\n" +
                "WHERE sa.is_del = '0'\n" +
                "AND (\n" +
                "sa.STATUS = '3'\n" +
                "OR sa.STATUS = '4'\n" +
                ")\n" +
                "and sa.org_code = '"+org_code+"'\n" +
                "and sa.project_code = '"+project_code+"'\n" +
                "and sa.type = '"+type+"'\n";

        return sql;
    }

    public String verify3(String org_code,String project_code,String type) {
        String sql = "SELECT\n" +
                "sa.*\n" +
                "FROM\n" +
                "SB_APPLY sa\n" +
                "WHERE sa.is_del = '0'\n" +
                "AND (\n" +
                "sa.STATUS = '3'\n" +
                "OR sa.STATUS = '4'\n" +
                ")\n" +
                "and sa.org_code = '"+org_code+"'\n" +
                "and sa.project_code = '"+project_code+"'\n" +
                "and sa.type = '"+type+"'\n order by sa.createTime desc";

        return sql;
    }
}
