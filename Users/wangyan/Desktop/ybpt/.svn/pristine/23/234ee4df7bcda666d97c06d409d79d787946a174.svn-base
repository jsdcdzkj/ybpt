package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.price.declare.SbApplyDrug;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * (SbApplyDrug)表数据库访问层
 *
 * @author wangYan
 * @since 2023-08-22
 */
@Repository
public class SbApplyDrugDao {

    public String sbApplyDrugPage(Page page, SbApplyDrug sbApplyDrug){
        return this.sbApplyDrugSql(sbApplyDrug);
    }
    public String sbApplyDrugList(SbApplyDrug sbApplyDrug){
        return this.sbApplyDrugSql(sbApplyDrug);
    }

    public String sbApplyDrugSql(SbApplyDrug sbApplyDrug) {
        String sql = "SELECT s.ID,\n" +
                "       s.AUTH_CONTRACTID,\n" +
                "       s.AUTH_DOWN_PDF_PATH,\n" +
                "       s.AUTH_PDF_PATH,\n" +
                "       s.AUTH_TIME,\n" +
                "       s.AUTH_TRIALER,\n" +
                "       s.CREATETIME,\n" +
                "       s.CONTRACTID,\n" +
                "       s.CREATEUSER,\n" +
                "       s.DOWN_PDF_PATH,\n" +
                "       s.END_TIME,\n" +
                "       s.END_TRIALER,\n" +
                "       s.FIRST_TIME,\n" +
                "       s.FIRST_TRIALER,\n" +
                "       s.IS_DEL,\n" +
                "       s.NATURES,\n" +
                "       s.ORG_CODE,\n" +
                "       s.ORG_NAME,\n" +
                "       s.PDF_PATH,\n" +
                "       s.REASON,\n" +
                "       s.REJECT_TIME,\n" +
                "       s.REJECTER,\n" +
                "       s.SECOND_TIME,\n" +
                "       s.SECOND_TRIALER,\n" +
                "       s.STATUS,\n" +
                "       s.TITLE,\n" +
                "       s.TYPE,\n" +
                "       s.UPDATETIME,\n" +
                "       s.UPDATEUSER,\n" +
                "       s.USER_TYPE,\n" +
                "       s.MEMO,\n" +
                "       s.PREMIUM,\n" +
                "       f.FIXMEDINS_CODE,\n" +
                "       f.fix_blng_admdvs_sbApply\n" +
                "FROM SB_APPLY_DRUG s\n" +
                "         INNER JOIN FIXMEDINS_B f ON s.ORG_CODE = f.FIXMEDINS_CODE AND f.IS_DEL = 0\n" +
                "WHERE s.IS_DEL = 0\n";
        if(StringUtils.isNotEmpty(sbApplyDrug.getOrg_code())){
            sql += " AND s.org_code = '" + sbApplyDrug.getOrg_code() + "'\n";
        }
        if(StringUtils.isNotEmpty(sbApplyDrug.getOrg_name())){
            sql += " AND s.org_name like '%" + sbApplyDrug.getOrg_name() + "%'\n";
        }
        if(StringUtils.isNotEmpty(sbApplyDrug.getType())){
            sql += " AND s.type = '" + sbApplyDrug.getType() + "'\n";
        }
        if(StringUtils.isNotEmpty(sbApplyDrug.getStatus())){
            sql += " AND s.status = '" + sbApplyDrug.getStatus() + "'\n";
        }
        if(StringUtils.isNotEmpty(sbApplyDrug.getNatures())){
            sql += " AND s.natures = '" + sbApplyDrug.getNatures() + "'\n";
        }
        if(StringUtils.isNotEmpty(sbApplyDrug.getUser_type())){
            sql += " AND s.user_type = '" + sbApplyDrug.getUser_type() + "'\n";
        }
        if (StringUtils.isNotEmpty(sbApplyDrug.getFix_blng_admdvs()) && !("320399").equals(sbApplyDrug.getFix_blng_admdvs())) {
            sql += " AND f.fix_blng_admdvs_sbApply = '" + sbApplyDrug.getFix_blng_admdvs() + "'\n";
        }
        if(StringUtils.isNotEmpty(sbApplyDrug.getFix_blng_admdvs_sbApply())){
            sql += " AND f.fix_blng_admdvs_sbApply = '" + sbApplyDrug.getFix_blng_admdvs_sbApply() + "'\n";
        }
        sql += " ORDER BY s.createTime desc";
        return sql;
    }
}

