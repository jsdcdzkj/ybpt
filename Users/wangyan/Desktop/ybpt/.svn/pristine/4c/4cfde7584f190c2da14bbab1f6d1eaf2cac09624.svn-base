package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import org.springframework.stereotype.Repository;

@Repository
public class OpspDiseListBDao {

    public String OpspDiseList(OpspDiseListBVo opspDiseListBVo){
        String sql = "SELECT\n" +
                "\todl.OPSP_DISE_CODE,odl.OPSP_DISE_NAME,odl.OPSP_DISE_MAJCLS_CODE,odl.OPSP_DISE_MAJCLS_NAME,ODC.INSUTYPE,odc.ENDDATE\n" +
                "FROM\n" +
                "\tPOLCENT_DB.opsp_dise_list_b odl\n" +
                "\tLEFT JOIN POLCENT_DB.opsp_dise_cro_b odc ON odl.OPSP_DISE_CODE = odc.OPSP_DISE_CODE \n" +
                "WHERE\n" +
                "1=1\n" +
                "\tand odC.DISE_TYPE_CODE='"+opspDiseListBVo.getDise_type_code()+"'\n" +
                "\tAND odl.VALI_FLAG = '1'\n" +
                "\tand ODC.INSUTYPE='310'\n" +
                "\tand odc.ENDDATE is null\n" ;
        if(StringUtils.isNotEmpty(opspDiseListBVo.getOpsp_dise_code())){
            sql += " and odl.OPSP_DISE_CODE = '"+opspDiseListBVo.getOpsp_dise_code()+"'";
        }
        if(StringUtils.isNotEmpty(opspDiseListBVo.getOpsp_dise_name())){
            sql += " and odl.OPSP_DISE_NAME LIKE '%"+opspDiseListBVo.getOpsp_dise_name()+"%'" ;
        }
        return sql;
    }
}
