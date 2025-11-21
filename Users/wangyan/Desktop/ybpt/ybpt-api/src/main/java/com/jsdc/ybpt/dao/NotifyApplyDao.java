package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.formula.domain.entity.NotifyApply;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class NotifyApplyDao {

    public String getApplyPage(Page page, NotifyApply notifyApply, SysUser sysUser) {
        return this.setApplySql(notifyApply, sysUser);
    }

    public String getNotifyApplyExportList(NotifyApply notifyApply, SysUser sysUser) {
        return this.setApplySql(notifyApply, sysUser);
    }


    public String setApplySql(NotifyApply notifyApply, SysUser sysUser) {
        String sql = "select \n" +
                "fna.ID ,\n" +
                " fna.ORG_NAME as orgName,\n" +
                " fna.ORG_CODE as orgCode,\n" +
                "\n" +
                "\n" +
                " fb.BIZNET as biznet,\n" +
                " fb.FIXMEDINS_NAME as fixBlngAdmdvsName,\n" +
                " fb.fix_blng_admdvs_sbApply as fixBlngAdmdvs,\n" +
                " fna.NATIONAL_FORMULA_CODE as nationalFormulaCode,\n" +
                " fna.FORMULA_NAME as formulaName,\n" +
                " fna.APPROVAL_NO as approvalNo,\n" +
                " fna.REGISTER_COMPANY_NAME as registerCompanyName,\n" +
                " fna.DOSAGE_FORM as dosageForm,\n" +
                " fna.SPECS as specs,\n" +
                "  DECODE(fna.IS_IN_CATEGORY, '1', fc.MIN_PACKAGE, fna.MIN_PACKAGE) as minPackage ,\n" +
                " fna.UNIT as unit,\n" +
                " fna.PRICE as price,\n" +
                " fna.LAST_APPLY_PRICE as lastApplyPrice,\n" +
                " fna.IS_IN_CATEGORY isInCategory,\n" +
                " fna.STATUS as status,\n" +
                " fna.REJECT_REASON as rejectReason,\n" +
                " fna.CREATETIME as createtime,\n" +
                " fna.FIRST_CHECK_TIME as firstCheckTime,\n" +
                " fna.FIRST_CHECK_USER as firstCheckUser,\n" +
                " fna.SECOND_CHECK_TIME as secondCheckTime,\n" +
                " fna.SECOND_CHECK_USER as secondCheckUser,\n" +
                " fna.FINISH_CHECK_TIME as finishCheckTime,\n" +
                " fna.FINISH_CHECK_USER as finishCheckUser,\n" +
                " fna.GENERAL_ACCEPT_LETTER_TIME as generalAcceptLetterTime,\n" +
                " fna.REJECT_TIME as rejectTime,\n" +
                " fna.ACCEPT_LETTER_PDF_PATH as acceptLetterPdfPath,\n" +
                " fna.ACCEPT_LETTER_DOWN_PDF_PATH as acceptLetterDownPdfPath,\n" +
                " fna.PDF_PATH as pdfPath,\n" +
                " fna.DOWN_PDF_PATH as downPdfPath,\n" +
                " fna.REJECT_USER as rejectUser \n" +
                " FROM \n" +
                " FORMULA_NOTIFY_APPLY fna \n" +
                " LEFT JOIN FORMULA_CATALOG fc on fna.CATEGORY_ID = fc.ID\n" +
                " INNER JOIN FIXMEDINS_B fb ON fna.org_code = fb.fixmedins_code  AND fb.IS_DEL = 0 \n" +
                " LEFT JOIN unfixedMechanism um ON fna.org_code = um.fixmedins_code\n" +

                " WHERE fna.is_del = 0 ";
        if ("1".equals(sysUser.getUser_type()) && !"320399".equals(sysUser.getOrg_code())) {
            sql += " and (FB.fix_blng_admdvs_sbApply = '" + sysUser.getOrg_code() + "'"
                    + " OR um.fix_blng_admdvs = '" + notifyApply.getOrgCode() + "')";
        } else if (!"1".equals(sysUser.getUser_type())) {
            sql += " and fna.org_code = '" + sysUser.getOrg_code() + "'";
        }
        if(StringUtils.isNotEmpty(notifyApply.getFixBlngAdmdvs())){
            sql += " and (FB.fix_blng_admdvs_sbApply = '" + notifyApply.getFixBlngAdmdvs() + "'"
                    + " OR um.fix_blng_admdvs = '" + notifyApply.getFixBlngAdmdvs() + "')";
        }
        if (StringUtils.isNotEmpty(notifyApply.getOrgCode())) {
            sql += " and fna.ORG_CODE like '%" + notifyApply.getOrgCode() + "%'";
        }
        if (StringUtils.isNotEmpty(notifyApply.getOrgName())) {
            sql += " and fna.ORG_NAME like '%" + notifyApply.getOrgName() + "%'";
        }
        if (StringUtils.isNotEmpty(notifyApply.getNationalFormulaCode())) {
            sql += " and fna.NATIONAL_FORMULA_CODE like '%" + notifyApply.getNationalFormulaCode() + "%'";
        }
        if (StringUtils.isNotEmpty(notifyApply.getFormulaName())) {
            sql += " and fna.FORMULA_NAME like '%" + notifyApply.getFormulaName() + "%'";
        }
        if (StringUtils.isNotEmpty(notifyApply.getRegisterCompanyName())) {
            sql += " and fna.REGISTER_COMPANY_NAME like '%" + notifyApply.getRegisterCompanyName() + "%'";
        }
        if (StringUtils.isNotEmpty(notifyApply.getStatus())) {
            sql += " and  fna.STATUS = '" + notifyApply.getStatus() + "'";
        }

        sql += " order by fna.createTime desc ";
        return sql;
    }

}
