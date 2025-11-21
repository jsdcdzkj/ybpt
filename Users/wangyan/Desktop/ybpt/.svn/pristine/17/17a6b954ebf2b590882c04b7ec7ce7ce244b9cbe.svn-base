package com.jsdc.ybpt.dao;

public class SysMenuDao {


    public String getMenusByUser(String userId, String parentId) {
        String sql = "SELECT\n" +
                "\t DISTINCT d.* \n" +
                "FROM\n" +
                "\tsys_user_role b\n" +
                "\tLEFT JOIN SYS_ROLE_MENU c ON b.ROLE_ID = c.ROLE_ID\n" +
                "\tLEFT JOIN SYS_MENU d ON c.MENU_ID = d.id \n" +
                "WHERE\n" +
                "\td.is_del = '0' \n" +
                "\tAND b.user_id = '" + userId + "' \n";
            if (parentId != null) {
                sql += "\tand d.parent_id='" + parentId + "'\n";
            }
                sql +=" ORDER BY\n" +
                      "\td.sort";
        return sql;
    }
}
