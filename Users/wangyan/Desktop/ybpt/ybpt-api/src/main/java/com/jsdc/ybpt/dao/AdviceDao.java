package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.advice.Advice;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * (Advice)表数据库访问层
 *
 * @author wangYan
 * @since 2023-06-21
 */
@Repository
public class AdviceDao {
    public String page(Page page, Advice advice, SysUser sysUser) {
        return this.setApplySql(advice, sysUser);
    }


    public String list(Advice advice, SysUser sysUser) {
        return this.setApplySql(advice, sysUser);
    }

    public String setApplySql(Advice advice, SysUser sysUser) {
        String sql = "SELECT\n" +
                "\tAD.*, nvl(FB.fix_blng_admdvs,um.fix_blng_admdvs) fix_blng_admdvs\n" +
                "FROM\n" +
                "\tADVICE AD\n" +
                "LEFT JOIN FIXMEDINS_B fb ON AD.org_code = fb.fixmedins_code\n" +
                "LEFT JOIN unfixedMechanism um ON AD.org_code = um.fixmedins_code\n" +
                "WHERE\n" +
                "\tAD.is_del = 0 ";

        if (StringUtils.isNotEmpty(advice.getFix_blng_admdvs())) {
            sql += " and (FB.fix_blng_admdvs = '" + advice.getFix_blng_admdvs() + "'"
                    + " OR um.fix_blng_admdvs = '" + advice.getFix_blng_admdvs() + "')";
        }
        if (StringUtils.isNotEmpty(advice.getOrg_name())) {
            sql += " AND AD.org_name LIKE '%" + advice.getOrg_name() + "%'";
        }
        if (StringUtils.isNotEmpty(advice.getUser_type())) {
            if ("非定点".equals(advice.getUser_type())) {
                sql += " and AD.user_type in( '7','8')";
            } else {
                sql += " and (AD.user_type not in( '7','8') or AD.user_type is null)";
            }
        }
        if ("1".equals(sysUser.getUser_type()) && !"320399".equals(sysUser.getOrg_code())) {
            sql += " and (FB.fix_blng_admdvs = '" + sysUser.getOrg_code() + "'"
                    + " OR um.fix_blng_admdvs = '" + advice.getOrg_code() + "')";
            ;

        } else if (!"1".equals(sysUser.getUser_type())) {
            sql += " and AD.org_code = '" + sysUser.getOrg_code() + "'";
        }
        if (StringUtils.isNotEmpty(advice.getNatures())) {
            sql += " and AD.natures = '" + advice.getNatures() + "'";
        }
        if (null != advice.getQueryDate() && advice.getQueryDate().length > 1) {
            sql += " and AD.createTime <= to_date('" + advice.getQueryDate()[1] + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sql += " and AD.createTime >= to_date('" + advice.getQueryDate()[0] + " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
        }
        sql += " order by AD.createTime desc ";
        return sql;
    }
}

