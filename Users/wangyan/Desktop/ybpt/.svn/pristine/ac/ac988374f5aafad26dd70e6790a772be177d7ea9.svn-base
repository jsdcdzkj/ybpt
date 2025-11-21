package com.jsdc.ybpt.dao.pur;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.pur.PurStockout;
import org.springframework.stereotype.Repository;

/**
 * (PurStockout)表数据库访问层
 *
 * @author wangYan
 * @since 2023-04-03 14:59:49
 */
@Repository
public class PurStockoutDao {

    public String getList(SysUser sysUser, PurStockout purStockout) {
        String sql = "SELECT ps.ORG_CODE,\n" +
                "       ps.ORG_NAME,\n" +
                "       ps.STOCKOUT_TYPE,\n" +
                "       ps.FIX_BLNG_ADMDVS,\n" +
                "       ps.UPDATEUSER,\n" +
                "       ps.UPDATETIME,\n" +
                "       ps.CREATEUSER,\n" +
                "       ps.CREATETIME,\n" +
                "       ps.IS_DEL,\n" +
                "       psd.ID,\n" +
                "       psd.CODE,\n" +
                "       psd.unit,\n" +
                "       psd.CREATEUSER,\n" +
                "       psd.CREATETIME,\n" +
                "       psd.END_DATE,\n" +
                "       psd.NAME,\n" +
                "       psd.NOTE,\n" +
                "       psd.PRICE,\n" +
                "       psd.QUANTITY,\n" +
                "       psd.START_DATE,\n" +
                "       psd.STOCKOUT_TYPE,\n" +
                "       psd.SUB_QUANTITY,\n" +
                "       psd.COMPANY,\n" +
                "       psd.ENTERPRISE\n" +
                "FROM PUR_STOCKOUT_DETAIL psd\n" +
                "         INNER JOIN PUR_STOCKOUT ps ON psd.PUR_STOCKOUT_ID = ps.ID\n";
        sql += " WHERE ps.is_del = 0 ";
        if ("1".equals(sysUser.getUser_type())) {
            if (StringUtils.isNotBlank(purStockout.getOrg_code())) {
                sql += " and ps.org_code = '" + purStockout.getOrg_code() + "'";
            }
            if (StringUtils.isNotBlank(purStockout.getOrg_name())) {
                sql += " and ps.org_name like '%"+purStockout.getOrg_name()+"%' " ;
            }
        } else {
            sql += " and ps.org_code = '" + purStockout.getOrg_code() + "'";
        }
        if ("1".equals(sysUser.getUser_type()) && StringUtils.isNotBlank(purStockout.getOrg_name())) {
            sql += " and ps.org_code = '" + purStockout.getOrg_code() + "'";
        }
        if ("1".equals(sysUser.getUser_type()) && StringUtils.isNotBlank(purStockout.getFix_blng_admdvs())) {
            sql += " and ps.fix_blng_admdvs = '" + purStockout.getFix_blng_admdvs() + "'";
        }
        if (StringUtils.isNotBlank(purStockout.getStartTime()) && StringUtils.isNotBlank(purStockout.getEndTime())) {
            sql += " and TO_CHAR (ps.createTime,'yyyy-MM-dd') >= ('" + purStockout.getStartTime() + "') ";
            sql += " and TO_CHAR (ps.createTime,'yyyy-MM-dd') <= ('" + purStockout.getEndTime() + "') ";
        }
        if (StringUtils.isNotBlank(purStockout.getStockout_type())) {
            sql += " and ps.stockout_type = '" + purStockout.getStockout_type() + "'";
        }
        if (StringUtils.isNotBlank(purStockout.getClues())) {
            sql += " and ps.clues = '" + purStockout.getClues() + "'";
        }

        sql += "  order by  ps.createTime desc ";

        return sql;
    }

}

