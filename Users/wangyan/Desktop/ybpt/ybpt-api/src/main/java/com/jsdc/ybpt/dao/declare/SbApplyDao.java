package com.jsdc.ybpt.dao.declare;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * (SbApply)表数据库访问层
 *
 * @author wangYan
 * @since 2023-02-01 16:22:04
 */
@Repository
public class SbApplyDao {

    public String getApplyPage(Page page, SbApply sbApply, SysUser sysUser) {
        return this.setApplySql(sbApply, sysUser);
    }


    public String getApplyList(SbApply sbApply, SysUser sysUser) {
        return this.setApplySql(sbApply, sysUser);
    }

    public String setApplySql(SbApply sbApply, SysUser sysUser) {
        String sql = "SELECT" +
                "\tnvl(FB.fix_blng_admdvs_sbApply,um.fix_blng_admdvs) FIX_BLNG_ADMDVS, \n" +
                "\tnvl(sbd.project_name,SA.project_name) PROJECT_NAME, \n" +
                "\tnvl(sbd.project_code,SA.project_code) PROJECT_CODE, \n" +
                "\tnvl(sbd.price,SA.price) PRICE, \n" +
                "\tSA.ID, \n" +
                "\tSA.CREATETIME, \n" +
                "\tSA.CREATEUSER, \n" +
                "\tSA.IS_DEL, \n" +
                "\tSA.NATURES, \n" +
                "\tSA.ORG_CODE, \n" +
                "\tSA.ORG_NAME, \n" +
                "\tSA.STATUS, \n" +
                "\tSA.TYPE, \n" +
                "\tSA.UPDATETIME, \n" +
                "\tSA.UPDATEUSER, \n" +
                "\tSA.AUTH_CONTRACTID, \n" +
                "\tSA.AUTH_DOWN_PDF_PATH, \n" +
                "\tSA.AUTH_PDF_PATH, \n" +
                "\tSA.CONTRACTID, \n" +
                "\tSA.PDF_PATH, \n" +
                "\tSA.DOWN_PDF_PATH, \n" +
                "\tSA.REASON, \n" +
                "\tSA.END_TIME, \n" +
                "\tSA.END_TRIALER, \n" +
                "\tSA.FIRST_TIME, \n" +
                "\tSA.FIRST_TRIALER, \n" +
                "\tSA.REJECT_TIME, \n" +
                "\tSA.REJECTER, \n" +
                "\tSA.SECOND_TIME, \n" +
                "\tSA.SECOND_TRIALER, \n" +
                "\tSA.HIGH_PRICE, \n" +
                "\tSA.AUTH_TIME, \n" +
                "\tSA.AUTH_TRIALER, \n" +
                "\tSA.USER_TYPE, \n" +
                "\tSA.TITLE, \n" +
                "\tSA.UNIT\n" +
                "FROM\n" +
                "\tSB_APPLY SA\n" +

                "LEFT JOIN SB_BEDDECLARATION sb ON SB.apply_id = SA.\"ID\"\n" +
                "LEFT JOIN SB_BEDDETAILS sbd ON sbd.bed_declaration_id = sb. ID\n" +

                "LEFT JOIN FIXMEDINS_B fb ON SA.org_code = fb.fixmedins_code\n" +
                "LEFT JOIN unfixedMechanism um ON SA.org_code = um.fixmedins_code\n" +
                "WHERE\n" +
                "\tsa.is_del = 0 ";
        if (StringUtils.isNotEmpty(sbApply.getHigh_price())) {
            if ("1".equals(sbApply.getHigh_price())) {
                sql += " and sa.high_price = '1'";
            } else if ("0".equals(sbApply.getHigh_price())) {
                sql += " and sa.high_price = '0' ";
            } else {
                sql += " and ( sa.high_price == '-1' OR sa.high_price IS NULL ) ";
            }
        }
        if (StringUtils.isNotEmpty(sbApply.getProject_name())) {
            sql += " and sa.project_name LIKE '%" + sbApply.getProject_name() + "%'";
        }
        if (StringUtils.isNotEmpty(sbApply.getProject_code())) {
            sql += " and sa.project_code = '" + sbApply.getProject_code() + "'";
        }
        if (StringUtils.isNotEmpty(sbApply.getOrg_code())) {
            sql += " and sa.org_code = '" + sbApply.getOrg_code() + "'";
        }
        if (StringUtils.isNotEmpty(sbApply.getFix_blng_admdvs())) {
            sql += " and (FB.fix_blng_admdvs_sbApply = '" + sbApply.getFix_blng_admdvs() + "'"
                    + " OR um.fix_blng_admdvs = '" + sbApply.getFix_blng_admdvs() + "')";
        }
        if (StringUtils.isNotEmpty(sbApply.getOrg_name())) {
            sql += " AND sa.org_name LIKE '%" + sbApply.getOrg_name() + "%'";
        }
        if (StringUtils.isNotEmpty(sbApply.getType())) {
            sql += " and sa.type = '" + sbApply.getType() + "'";
        }
        if (StringUtils.isNotEmpty(sbApply.getStatus())) {
            sql += " and sa.status = '" + sbApply.getStatus() + "'";
        }
        if (StringUtils.isNotEmpty(sbApply.getUser_type())) {
            if ("非定点".equals(sbApply.getUser_type())) {
                sql += " and sa.user_type in( '7','8')";
            } else {
                sql += " and (sa.user_type not in( '7','8') or sa.user_type is null)";
            }
        }
        if ("1".equals(sysUser.getUser_type()) && !"320399".equals(sysUser.getOrg_code())) {
            sql += " and (FB.fix_blng_admdvs_sbApply = '" + sysUser.getOrg_code() + "'"
                    + " OR um.fix_blng_admdvs = '" + sbApply.getOrg_code() + "')";
            ;

        } else if (!"1".equals(sysUser.getUser_type())) {
            sql += " and sa.org_code = '" + sysUser.getOrg_code() + "'";
        }
        if (StringUtils.isNotEmpty(sbApply.getNatures())) {
            sql += " and sa.natures = '" + sbApply.getNatures() + "'";
        }
        if (null != sbApply.getQueryDate() && sbApply.getQueryDate().length > 1) {
            sql += " and sa.createTime <= to_date('" + sbApply.getQueryDate()[1] + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sql += " and sa.createTime >= to_date('" + sbApply.getQueryDate()[0] + " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
        }
        if (StringUtils.isNotEmpty(sbApply.getStartTime()) && StringUtils.isNotEmpty(sbApply.getEndTime())) {
            sql += " and TO_CHAR (sa.createTime,'yyyy-MM-dd') >= ('" + sbApply.getStartTime() + "') ";
            sql += " and TO_CHAR (sa.createTime,'yyyy-MM-dd') <= ('" + sbApply.getEndTime() + "') ";
        }
        if (null != sbApply.getAuthDate() && sbApply.getAuthDate().length > 1) {
            sql += " and sa.auth_time <= to_date('" + sbApply.getAuthDate()[1] + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sql += " and sa.auth_time >= to_date('" + sbApply.getAuthDate()[0] + " 00:00:00','yyyy-mm-dd hh24:mi:ss') ";
        }
        if (StringUtils.isNotEmpty(sbApply.getStartTimeAuth()) && StringUtils.isNotEmpty(sbApply.getEndTimeAuth())) {
            sql += " and TO_CHAR (sa.auth_time,'yyyy-MM-dd') >= ('" + sbApply.getStartTimeAuth() + "') ";
            sql += " and TO_CHAR (sa.auth_time,'yyyy-MM-dd') <= ('" + sbApply.getEndTimeAuth() + "') ";
        }
        sql += " order by sa.createTime desc ";
        return sql;
    }
}

