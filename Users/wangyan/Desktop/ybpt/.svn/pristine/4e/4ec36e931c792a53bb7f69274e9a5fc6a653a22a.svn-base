package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class SbBedDeclarationDao {

    public String verify(String org_code,String project_code) {
        String sql = "SELECT\n" +
                "\tCOUNT (1)\n" +
                "FROM\n" +
                "\tSB_APPLY sa\n" +
                "LEFT JOIN SB_BEDDECLARATION sb ON SB.apply_id = SA.\"ID\"\n" +
                "LEFT JOIN SB_BEDDETAILS sbd ON sbd.bed_declaration_id = sb. ID\n" +
                "WHERE sa.is_del = '0'\n" +
                "\t and org_code = '"+org_code+"'\n" +
                " and sa.TYPE = '10'"+
                "AND (\n" +
                "\tsa.STATUS = '0'\n" +
                "\tOR sa.STATUS = '1'\n" +
                "\tOR sa.STATUS = '2'\n" +
                ")\n" +
                "AND sbd.project_code = '"+project_code+"'";

        return sql;
    }


    public String verify2(String org_code,String project_code) {
        String sql = "SELECT\n" +
                "\tmonths_between(sysdate,sa.end_time )\n" +
                "FROM\n" +
                "\tSB_APPLY sa\n" +
                "LEFT JOIN SB_BEDDECLARATION sb ON SB.apply_id = SA.\"ID\"\n" +
                "LEFT JOIN SB_BEDDETAILS sbd ON sbd.bed_declaration_id = sb. ID\n" +
                "WHERE sa.is_del = '0'\n" +
                "\t and org_code = '"+org_code+"'\n" +
                " and sa.TYPE = '10'"+
                "AND (\n" +
                "\tsa.STATUS = '3'\n" +
                "\tOR sa.STATUS = '4'\n" +
                ")\n" +
                "AND sbd.project_code = '"+project_code+"'";

        return sql;
    }

    public String verify3(String org_code,String project_code,String type) {
        String sql = "SELECT\n" +
                "\tsa.*\n" +
                "FROM\n" +
                "\tSB_APPLY sa\n" +
                "LEFT JOIN SB_BEDDECLARATION sb ON SB.apply_id = SA.\"ID\"\n" +
                "LEFT JOIN SB_BEDDETAILS sbd ON sbd.bed_declaration_id = sb. ID\n" +
                "WHERE sa.is_del = '0'\n" +
                "\t and org_code = '"+org_code+"'\n" +
                " and sa.TYPE = '"+type+"'"+
                "AND (\n" +
                "\tsa.STATUS = '3'\n" +
                "\tOR sa.STATUS = '4'\n" +
                ")\n" +
                "AND sbd.project_code = '"+project_code+"' order by sa.createTime desc";

        return sql;
    }


}
