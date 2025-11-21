package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.capitalSettlement.QsOrgConfirmation;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class QsOrgConfirmationDao {

    public String getList(Page page, QsOrgConfirmation bean){
        String sql = "SELECT qo.ID,\n" +
                "       qo.ADMDVS,\n" +
                "       qo.CENTRE_PDF_PATH,\n" +
                "       qo.CENTRE_SIGN_TIME,\n" +
                "       qo.CREATETIME,\n" +
                "       qo.CREATEUSER,\n" +
                "       qo.IS_DEL,\n" +
                "       qo.ORG_CODE,\n" +
                "       qo.ORG_NAME,\n" +
                "       qo.DOWN_PDF_PATH,\n" +
                "       qo.ORG_SIGN_TIME,\n" +
                "       qo.STATUS,\n" +
                "       qo.UPDATETIME,\n" +
                "       qo.UPDATEUSER,\n" +
                "       qo.YEAR\n" +
                "FROM QS_ORG_CONFIRMATION qo\n";
        sql += "WHERE qo.IS_DEL =0\n";
        if(StringUtils.isNotEmpty(bean.getOrg_code())){
            sql += "AND qo.org_code = '" + bean.getOrg_code() + "'\n";
        }
        if(StringUtils.isNotEmpty(bean.getOrg_name())){
            sql += "AND qo.org_name = '" + bean.getOrg_name() + "'\n";
        }
        if(StringUtils.isNotEmpty(bean.getAdmdvs())){
            sql += "AND qo.admdvs = '" + bean.getAdmdvs() + "'\n";
        }
        if(StringUtils.isNotEmpty(bean.getYear())){
            sql += "AND qo.year = '" + bean.getYear() + "'\n";
        }
        if(StringUtils.isNotEmpty(bean.getStatus())){
            sql += "AND qo.status = '" + bean.getStatus() + "'\n";
        }
        sql += "ORDER BY qo.UPDATETIME DESC nulls last";

        return sql;
    }
}
