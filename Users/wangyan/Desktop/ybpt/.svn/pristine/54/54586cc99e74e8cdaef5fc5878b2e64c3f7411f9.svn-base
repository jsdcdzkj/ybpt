package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.model.OpspDise;
import com.jsdc.ybpt.vo.OpspRegDVo;
import org.springframework.stereotype.Repository;

@Repository
public class OpspDiseDao {
    public String selectDiseList(OpspDise opspDise){
        String sql= "SELECT\n" +
                "\t\tod.approvalStatus,\n" +
                "\t\tod.id,\n" +
                "\t\tod.IS_DEL,\n" +
                "\t\tod.PSN_NAME,\n" +
                "\t\tod.PSN_CERT_TYPE,\n" +
                "\t\tod.CERTNO,\n" +
                "\t\tri.OPSP_DISE_NAME,\n" +
                "\t\tod.EMP_NAME, \n" +
                "\t\tod.insutypeName \n" +
                " FROM \n" +
                "\tOPSP_DISE od\n" +
                "\tLEFT JOIN REG_INFO ri ON ri.OPSP_id = od.id\n" +
                "\tWHERE od.CERTNO='"+opspDise.getCertno()+"' and od.medins_code ='"+opspDise.getMedins_code()+"' and od.createUser ='"+opspDise.getCreateUser()+"' order by od.createTime desc" ;
        return sql ;
    }

    /**
    *从回流库查询登记信息
    * Author wzn
    * Date 2022/5/9 10:53
    */
    public String queryAssociation(String certNo,String opsp_dise_code){
        String sql = "SELECT\n" +
                "\tserv_matt_inst_id\n" +
                "FROM\n" +
                "\tHIBIZ_DB.OPSP_REG_EVT_C \n" +
                "WHERE\n" +
                "\tCERTNO = '"+certNo+"' \n" +
                "\tand OPSP_DISE_CODE = '"+opsp_dise_code+"' \n" +
                "\tAND VALI_FLAG = '1' \n" +
                "\tAND RCHK_FLAG = '0'" ;
        return sql ;
    }

    public String opspCheckList(OpspRegDVo opspRegDVo){
        String sql = "SELECT\n" +
                "\tod.approvalStatus,\n" +
                "\tod.id,\n" +
                "\tod.associationId,\n" +
                "\tri.opsp_dise_name,\n" +
                "\tri.medins_name,\n" +
                "\tod.psn_name,\n" +
                "\tod.psn_cert_type,\n" +
                "\tod.certno,\n" +
                "\tod.insu_admdvs_name,\n" +
                "\tod.insutypeName,\n" +
                "\tod.mob,\n" +
                "\tod.live_addr,\n" +
                "\tod.emp_name,\n" +
                "\tri.dise_type_code \n" +
                "FROM\n" +
                "\tOPSP_DISE od\n" +
                "\tLEFT JOIN REG_INFO ri ON ri.opsp_id = od.id\n" +
                "\twhere 1=1 and od.certno = '"+opspRegDVo.getCertno()+"' and od.psn_cert_type ='"+opspRegDVo.getPsn_cert_type()+"' and ri.dise_type_code='"+opspRegDVo.getDise_type_code()+"'" ;
        return sql ;
    }



    public String selectOpspREGByCertNo(String certNo){
        String sql ="select OPSP_DISE_CODE  from HIBIZ_DB.OPSP_REG_EVT_C where RCHK_FLAG in(1,7) and CERTNO="+certNo ;
    return sql ;

    }

    public String selectBydiseByCertNo(String certNo){
        String sql ="select BYDISE_SETL_LIST_CODE OPSP_DISE_CODE  from SETLCENT_DB.BYDISE_SETL_REG_D where  CERTNO="+certNo ;
        return sql ;

    }

}
