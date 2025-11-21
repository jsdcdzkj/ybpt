package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ReimbursementQuery;
import com.jsdc.ybpt.vo.ReimbursementQuery_org;
import org.springframework.stereotype.Repository;

@Repository
public class ReimbursementDao {

    public String selectBirthSettlement(Page page, ReimbursementQuery reimbursementQuery) {
        String sql = "SELECT\n" +
                "\ta.PSN_NAME psn_name,\n" +
                "\ta.CERTNO certno,\n" +
                "\tb.nat_dic_val_name insu_admdvs ,\n" +
                "\tc.nat_dic_val_name psn_type,\n" +
                "\td.nat_dic_val_name med_type,\n" +
                "\ta.SETL_TIME setl_time,\n" +
                "\ta.FIXMEDINS_CODE fixmedins_code,\n" +
                "\ta.FIXMEDINS_NAME fixmedins_name,\n" +
                "\te.nat_dic_val_name hosp_lv,\n" +
                "\ta.MEDFEE_SUMAMT medfee_sumamt,\n" +
                "\ta.MEDFEE_SUMAMT - a.ACCT_PAY - a.CASH_PAYAMT reimbursement \n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D a\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b on a.INSU_ADMDVS = b.nat_dic_val_code and b.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c on a.PSN_TYPE = c.nat_dic_val_code and c.DIC_TYPE_CODE = 'PSN_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A d on a.MED_TYPE = d.nat_dic_val_code and d.DIC_TYPE_CODE = 'MED_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e on a.HOSP_LV = e.nat_dic_val_code and e.DIC_TYPE_CODE = 'HOSP_LV'\n" +
                "WHERE\n" +
                "\ta.VALI_FLAG = '1' \n";
        sql +=
                "\tAND a.SETL_TIME >= to_date('" + reimbursementQuery.getBeginTime() + " 00:00:00', 'yyyy-MM-dd hh24:mi:ss' ) \n" +
                        "\tAND a.SETL_TIME <= to_date('" + reimbursementQuery.getEndTime() + " 23:59:59', 'yyyy-MM-dd hh24:mi:ss' ) \n";
        if (StringUtils.isNotEmpty(reimbursementQuery.getInsu_admdvs())) {
            sql += "\tAND a.INSU_ADMDVS='" + reimbursementQuery.getInsu_admdvs() + "'\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery.getPsn_type())) {
            sql += "\tAND a.PSN_TYPE='" + reimbursementQuery.getPsn_type() + "'\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery.getMed_type())) {
            sql += "\tAND a.MED_TYPE='" + reimbursementQuery.getMed_type() + "'\n";
        } else {
            sql += "\tAND a.med_type IN ( '51', '52', '530102', '530202', '55', '5305' ) \n";
        }

        sql += "\tAND a.REFD_SETL_FLAG = '0' order by a.SETL_TIME desc\n";

        return sql;
    }


    public String selectBirthSettlement_org(Page page, ReimbursementQuery_org reimbursementQuery_org) {
        String sql = "SELECT\n" +
                "\ta.FIXMEDINS_CODE,\n" +
                "\ta.FIXMEDINS_NAME,\n" +
                "\tf.nat_dic_val_name MATN_TYPE,\n" +
                "\tc.nat_dic_val_name INSUTYPE,\n" +
                "\tb.nat_dic_val_name INSU_ADMDVS,\n" +
                "\te.nat_dic_val_name HOSP_LV,\n" +
                "\tcount( a.SETL_ID ) AS count,\n" +
                "\tcount( DISTINCT a.CERTNO ) AS num,\n" +
                "\tsum( a.MEDFEE_SUMAMT ) MEDFEE_SUMAMT,\n" +
                "\tsum( a.HIFP_PAY ) HIFP_PAY,\n" +
                "\tsum( a.HIFOB_PAY ) HIFOB_PAY,\n" +
                "\tsum( a.CVLSERV_PAY ) CVLSERV_PAY,\n" +
                "\tsum( a.ACCT_PAY ) ACCT_PAY,\n" +
                "\tsum( a.CASH_PAYAMT ) CASH_PAYAMT,\n" +
                "\tsum( a.OWNPAY_HOSP_PART ) OWNPAY_HOSP_PART,\n" +
                "\tsum( a.MAF_PAY ) MAF_PAY \n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D a\n" +
                "\tLEFT JOIN SETLCENT_DB.MDTRT_D aa ON a.MDTRT_ID = aa.MDTRT_ID\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A b ON a.INSU_ADMDVS = b.nat_dic_val_code \n" +
                "\tAND b.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A c ON a.INSUTYPE = c.nat_dic_val_code \n" +
                "\tAND c.DIC_TYPE_CODE = 'INSUTYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A e ON a.HOSP_LV = e.nat_dic_val_code \n" +
                "\tAND e.DIC_TYPE_CODE = 'HOSP_LV'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A f ON aa.MATN_TYPE = f.nat_dic_val_code \n" +
                "\tAND f.DIC_TYPE_CODE = 'MATN_TYPE' \n" +
                "WHERE\n" +
                "\ta.VALI_FLAG = '1' \n";
        if (StringUtils.isNotEmpty(reimbursementQuery_org.getBeginTime())) {
            sql += "and a.SETL_TIME >= to_date('" + reimbursementQuery_org.getBeginTime() + "','yyyy-MM-dd')\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery_org.getEndTime())) {
            sql += "and a.SETL_TIME <= to_date('" + reimbursementQuery_org.getEndTime() + "','yyyy-MM-dd')\n";
        }
        sql += "\tAND f.nat_dic_val_name IS NOT NULL \n" +
                "\tAND a.med_type IN ( '51', '52', '5301', '5302', '530102', '530202', '55', '5305' ) \n" +
                "\tAND a.REFD_SETL_FLAG = '0' \n";

        if (StringUtils.isNotEmpty(reimbursementQuery_org.getInsu_admdvs())) {
            sql += "and a.INSU_ADMDVS = '" + reimbursementQuery_org.getInsu_admdvs() + "'\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery_org.getPsn_type())) {
            String psn_type = "";
            for (String s : reimbursementQuery_org.getPsn_type()) {
                psn_type += "'" + s + "',";
            }
            sql += "and a.PSN_TYPE in (" + psn_type.substring(0, psn_type.length() - 1) + ")\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery_org.getMatn_type())) {
            String matn_type = "";
            for (String s : reimbursementQuery_org.getMatn_type()) {
                matn_type += "'" + s + "',";
            }
            sql += "and aa.MATN_TYPE in (" + matn_type.substring(0, matn_type.length() - 1) + ")\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery_org.getInsutype())) {
            String insutype= "";
            for (String s : reimbursementQuery_org.getInsutype()) {
                insutype += "'" + s + "',";
            }
            sql += "and a.INSUTYPE in (" + insutype.substring(0, insutype.length() - 1) + ")\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery_org.getHosp_lv())) {
            sql += "and a.HOSP_LV = '" + reimbursementQuery_org.getHosp_lv() + "'\n";
        }
        if (StringUtils.isNotEmpty(reimbursementQuery_org.getFixmedins_code())) {
            sql += "and a.FIXMEDINS_CODE = '" + reimbursementQuery_org.getFixmedins_code() + "'\n";
        }
        sql += "GROUP BY\n" +
                "\ta.FIXMEDINS_CODE,\n" +
                "\ta.FIXMEDINS_NAME,\n" +
                "\tf.nat_dic_val_name,\n" +
                "\tb.nat_dic_val_name,\n" +
                "\tc.nat_dic_val_name,\n" +
                "\te.nat_dic_val_name";
        return sql;
    }
}
