package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.DiagListBVo;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import org.springframework.stereotype.Repository;

@Repository
public class DiagListBDao {

    public String diagList(DiagListBVo diagListBVo){
        String sql = "SELECT\n" +
                "\tSP_DISE_CODE opsp_dise_code,SP_DISE_NAME opsp_dise_name,dise_cont,med_type,dise_lmt,dise_lmt_type,insutype,psn_type\n" +
                "FROM\n" +
                "\tpolcent_db.SP_DISE_B\n" +
                "WHERE\n" +
                "\tVALI_FLAG = '1'\n" +
                "AND INSUTYPE = '310'\n" +
                "and MED_TYPE='15'" ;
        if(StringUtils.isNotEmpty(diagListBVo.getOpsp_dise_code())){
            sql += " and SP_DISE_CODE = '"+diagListBVo.getOpsp_dise_code()+"'";
        }
        if(StringUtils.isNotEmpty(diagListBVo.getOpsp_dise_name())){
            sql += " and SP_DISE_NAME LIKE '%"+diagListBVo.getOpsp_dise_name()+"%'" ;
        }
        return sql;
    }
}
