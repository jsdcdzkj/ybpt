package com.jsdc.ybpt.dao;

import org.springframework.stereotype.Repository;

@Repository
public class SysFileDao {

    /**
     * 对外接口上传报告，根据ASSOCIATIONID 删除 上传 文件
     *
     * @param   ASSOCIATIONID
     * @return
     */
    public String deleteRecords(String ASSOCIATIONID) {
        String sql = "\t DELETE \n" +
                "\tFROM\n" +
                "\tSYS_FILE \n" +
                "\tWHERE 1=1\n";
        if (!"".equals(ASSOCIATIONID)) {
            sql = sql + " AND SYS_FILE.ASSOCIATIONID  = '" + ASSOCIATIONID + "' \t";
        }
        return sql;
    }

    public String selectByAssociationId(String ASSOCIATIONID) {
        String sql = "\t SELECT * FROM  SYS_FILE WHERE 1=1  \n" ;

        if (!"".equals(ASSOCIATIONID)) {
            sql = sql + " AND  ASSOCIATIONID  = '" + ASSOCIATIONID + "' \t";
        }
        return sql;
    }
}
