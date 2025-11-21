package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class PackInfoDao {
    public String getPackInfoRatio(String year, String orgId, SysUser sysUser, OrganizationInfo organizationInfo) {
        String sql = "SELECT\n" +
                "\t pi.PACK_SOURCE as packSource,\n" +
                "\t COUNT(pi.ID) as num \n" +
                "FROM\n" +
                    "\t PACK_INFO pi\n" +
                "\t WHERE pi.IS_DEL = '0'\n";
        if (StringUtils.hasLength(year)) {
            sql = sql + "\t and pi.PACK_YEAR = '" + year + "'\n";
        }


        if (Strings.isNotEmpty(orgId)&&sysUser.getUser_type().equals("5")) {
            sql=sql+" and pi.org_id in ('"+sysUser.getOrg_code()+"','"+organizationInfo.getOrg_code()+"')";
//            sql = sql + "\t and pi.ORG_ID = '" + orgId + "'\n";
        }

        sql = sql + "\n GROUP BY\n" +
                      "\t pi.PACK_SOURCE";
        return sql;
    }
}
