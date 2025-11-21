package com.jsdc.ybpt.dao;

public class SysUserRoleDao {

    public String getRoleNameByUser(String userId){
        String sql = "SELECT\n" +
                "\tb.ROLE_NAME\n" +
                "FROM\n" +
                "\tSYS_USER_ROLE A\n" +
                "LEFT JOIN SYS_ROLE b ON A .ROLE_ID = b. ID\n" +
                "WHERE\n" +
                "\tA .USER_ID = '"+userId+"'\n" +
                "AND b.IS_DEL = '0'";
        return sql;
    }
    public String getRoleSymbolByUser(String userId){
        String sql = "SELECT\n" +
                "\tb.role_symbol\n" +
                "FROM\n" +
                "\tSYS_USER_ROLE A\n" +
                "LEFT JOIN SYS_ROLE b ON A .ROLE_ID = b. ID\n" +
                "WHERE\n" +
                "\tA .USER_ID = '"+userId+"'\n" +
                "AND b.IS_DEL = '0'";
        return sql;
    }

}
