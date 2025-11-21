package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.StringUtils;

/**
 * @ClassName SysUserDao
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 15:26
 * @Version 1.0
 */
public class SysUserDao {

    public String hehe(){
        String hehe = "";
        hehe = "select * from sys_user";
        return hehe;
    }

    public String selectByPage(Page page, SysUser sysUser) {
        String sql = "SELECT su.ID,\n" +
                "       su.idNumber,\n" +
                "       su.isAdmin,\n" +
                "       su.alias,\n" +
                "       su.BEGINDATE,\n" +
                "       su.CDKEY,\n" +
                "       su.CREATETIME,\n" +
                "       su.CREATEUSER,\n" +
                "       su.DEPT_CODE,\n" +
                "       su.ENDDATE,\n" +
                "       su.IS_AUTH,\n" +
                "       su.LANDLINE,\n" +
                "       su.IS_DEL,\n" +
                "       su.LOCATION,\n" +
                "       su.LOCKFLAG,\n" +
                "       su.MAC,\n" +
                "       su.MAILBOX,\n" +
                "       su.NAME,\n" +
                "       su.ORG_CODE,\n" +
                "       su.PASS,\n" +
                "       su.POSITION,\n" +
                "       su.TELEPHONE,\n" +
                "       su.UPDATETIME,\n" +
                "       su.UPDATEUSER,\n" +
                "       su.USER_TYPE,\n" +
                "       su.USERNAME,\n" +
                "       su.TYPE,\n" +
                "       su.ORG_NAME,\n" +
                "       su.DEPT_NAME\n" +
                "FROM SYS_USER su\n" +
                "         LEFT JOIN FIXMEDINS_B fb ON su.ORG_CODE = fb.fixmedins_code\n" +
                "WHERE su.IS_DEL = 0\n";
        if(StringUtils.isNotEmpty(sysUser.getUser_type())){
            sql += " AND su.USER_TYPE = '" + sysUser.getUser_type() + "'";
        }
        if(StringUtils.isNotEmpty(sysUser.getIdNumber())){
            sql += " AND su.idNumber like '%" + sysUser.getIdNumber() + "%'";
        }
        if(StringUtils.isNotEmpty(sysUser.getOrg_code())){
            sql += " AND (fb.FIX_BLNG_ADMDVS = '" + sysUser.getOrg_code() + "' OR su.ORG_CODE = '" + sysUser.getOrg_code() + "')";
        }
        if(StringUtils.isNotEmpty(sysUser.getUsername())){
            sql += " AND （  su.USERNAME LIKE '%" + sysUser.getUsername() + "%' OR su.NAME LIKE '%" + sysUser.getUsername() + "%'） ";
        }
        return sql;
    }
}
