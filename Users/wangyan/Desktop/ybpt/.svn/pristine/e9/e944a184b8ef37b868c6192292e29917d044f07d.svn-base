package com.jsdc.ybpt.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

@Repository
public class DiseaseMutexDao {


    public String getMmmtDisease(Page page, String diseCode, String diseName) {
        String sql = "SELECT\n" +
                "\tOPSP_DISE_CODE diseCode,\n" +
                "\tOPSP_DISE_NAME diseName,\n" +
                "\tOPSP_DISE_MAJCLS_NAME diseClassName\n" +
                "FROM\n" +
//                "\tPOLCENT_DB.OPSP_DISE_LIST_B\n" +
                "\tOPSP_DISE_LIST_B\n" +
                "where VALI_FLAG='1'\n";
        if (StrUtil.isNotEmpty(diseCode)) {
            sql += "and OPSP_DISE_CODE ='" + diseCode + "'\n";
        }
        if (StrUtil.isNotEmpty(diseName)) {
            sql += "and OPSP_DISE_NAME like '%" + diseName + "%'";
        }
        return sql;
    }
}
