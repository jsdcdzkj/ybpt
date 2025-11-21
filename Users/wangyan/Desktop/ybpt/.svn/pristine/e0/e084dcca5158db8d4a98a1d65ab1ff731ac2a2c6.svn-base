package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.vo.ReflAppyEvtCVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ReflAppyEvtCDao {

  public String getDiagList(ReflAppyEvtCVo vo) {
    String sql = "SELECT dl.DIAG_LIST_ID,\n" +
        "       dl.DIAG_CODE,\n" +
        "       dl.DIAG_NAME\n" +
        "FROM polcent_db.diag_list_b dl\n" +
        "WHERE dl.VALI_FLAG = 1\n";
    if (StringUtils.isNotEmpty(vo.getDIAG_CODE())) {
      sql += " AND dl.DIAG_CODE = '" + vo.getDIAG_CODE() + "'";
    }
    if (StringUtils.isNotEmpty(vo.getDIAG_NAME())) {
      sql += " AND dl.DIAG_NAME like '%" + vo.getDIAG_NAME() + "%'";
    }
    return sql;
  }

  public String reflAppyEvtC(ReflAppyEvtCVo vo) {
    String sql = "SELECT RAEC.* " +
        "FROM HIBIZ_DB.refl_appy_evt_c RAEC\n" +
        "WHERE RAEC.CERTNO = '" + vo.getCERTNO() + "'";
    if (StringUtils.isNotEmpty(vo.getRCHK_FLAG())) {
      sql += " AND RAEC.rchk_flag = '" + vo.getRCHK_FLAG() + "'";
    }

    return sql;
  }

  public String getReflAppyEvtC(ReflAppyEvtCVo vo) {
    String sql = "SELECT RAEC.id," +
        "       RAEC.EVTSN,\n" +
        "       RAEC.TRT_DCLA_DETL_SN,\n" +
        "       RAEC.SERV_MATT_INST_ID,\n" +
        "       RAEC.SERV_MATT_NODE_INST_ID,\n" +
        "       RAEC.EVT_INST_ID,\n" +
        "       RAEC.EVT_TYPE,\n" +
        "       RAEC.INSUTYPE,\n" +
        "       RAEC.DCLA_SOUC,\n" +
        "       RAEC.PSN_NO,\n" +
        "       RAEC.PSN_INSU_RLTS_ID,\n" +
        "       RAEC.PSN_CERT_TYPE,\n" +
        "       RAEC.CERTNO,\n" +
        "       RAEC.PSN_NAME,\n" +
        "       RAEC.GEND,\n" +
        "       RAEC.BRDY,\n" +
        "       RAEC.TEL,\n" +
        "       RAEC.ADDR,\n" +
        "       RAEC.INSU_ADMDVS,\n" +
        "       RAEC.EMP_NO,\n" +
        "       RAEC.EMP_NAME,\n" +
        "       RAEC.FIXMEDINS_CODE,\n" +
        "       RAEC.FIXMEDINS_NAME,\n" +
        "       RAEC.HOSP_LV,\n" +
        "       RAEC.FIX_BLNG_ADMDVS,\n" +
        "       RAEC.DIAG_CODE,\n" +
        "       RAEC.DIAG_NAME,\n" +
        "       RAEC.DRORD,\n" +
        "       RAEC.DISE_COND_DSCR,\n" +
        "       RAEC.MDTRTAREA_ADMDVS,\n" +
        "       RAEC.REFLIN_MEDINS_NO,\n" +
        "       RAEC.REFLIN_MEDINS_NAME,\n" +
        "       RAEC.OUT_FLAG,\n" +
        "       RAEC.REFL_DATE,\n" +
        "       RAEC.REFL_REA,\n" +
        "       RAEC.REFL_SETL_FLAG,\n" +
        "       RAEC.REFL_OPNN,\n" +
//        "       RAEC.MEMO,\n" +
        "       RAEC.AGNTER_NAME,\n" +
        "       RAEC.AGNTER_CERT_TYPE,\n" +
        "       RAEC.AGNTER_CERTNO,\n" +
        "       RAEC.AGNTER_TEL,\n" +
        "       RAEC.AGNTER_ADDR,\n" +
        "       RAEC.AGNTER_RLTS,\n" +
        "       RAEC.ENDDATE,\n" +
        "       RAEC.BEGNDATE,\n" +
        "       RAEC.SETL_ID,\n" +
        "       RAEC.MDTRT_ID,\n" +
        "       RAEC.HOSP_AGRE_REFL_FLAG,\n" +
        "       RAEC.VALI_FLAG,\n" +
        "       RAEC.RCHK_FLAG,\n" +
        "       RAEC.RID,\n" +
        "       RAEC.UPDT_TIME,\n" +
        "       RAEC.CRTER_ID,\n" +
        "       RAEC.CRTER_NAME,\n" +
        "       RAEC.CRTE_TIME,\n" +
        "       RAEC.CRTE_OPTINS_NO,\n" +
        "       RAEC.OPTER_ID,\n" +
        "       RAEC.OPTER_NAME,\n" +
        "       RAEC.OPTINS_NO,\n" +
        "       RAEC.OPT_TIME,\n" +
        "       RAEC.POOLAREA_NO,\n" +
        "       RAEC.REFL_FIL_TYPE,\n" +
        "       RAEC.ALLO_SETL_CNT,\n" +
        "       RAEC.RCHK_FLAG_NAME,\n" +
        "       RAEC.INSUTYPE_NAME,\n" +
        "       RAEC.VALI_FLAG_NAME,\n" +
        "       RAEC.PSN_CERT_TYPE_NAME,\n" +
        "       RAEC.INSU_ADMDVS_NAME,\n" +
        "       RAEC.out_fil_upld_stas_name,\n" +
        "       RAEC.refl_fil_type_name,\n" +
        "       RAEC.dcla_souc_name,\n" +
        "       RAEC.refl_setl_flag_name,\n" +
        "       RAEC.hosp_agre_refl_flag_name,\n" +
        "       RAEC.agnter_cert_type_name,\n" +
        "       RAEC.agnter_rlts_name\n" +
//        "       RF.NAT_DIC_VAL_NAME as RCHK_FLAG_NAME,\n" +
//        "       I.NAT_DIC_VAL_NAME as INSUTYPE_NAME,\n" +
//        "       VF.NAT_DIC_VAL_NAME as VALI_FLAG_NAME,\n" +
//        "       PCT.NAT_DIC_VAL_NAME as PSN_CERT_TYPE_NAME\n" +
//        "FROM hibiz_db.refl_appy_evt_c RAEC\n" +
        "FROM refl_appy_evt_c RAEC\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a RF\n" +
//        "                   ON RF.DIC_TYPE_CODE = 'RCHK_FLAG' AND RF.NAT_DIC_VAL_CODE = RAEC.RCHK_FLAG\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a I\n" +
//        "                   ON I.DIC_TYPE_CODE = 'INSUTYPE' AND I.NAT_DIC_VAL_CODE = RAEC.INSUTYPE\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a VF\n" +
//        "                   ON VF.DIC_TYPE_CODE = 'VALI_FLAG' AND VF.NAT_DIC_VAL_CODE = RAEC.VALI_FLAG\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a PCT\n" +
//        "                   ON PCT.DIC_TYPE_CODE = 'PSN_CERT_TYPE' AND PCT.NAT_DIC_VAL_CODE = RAEC.PSN_CERT_TYPE\n" +
        "WHERE 1 = 1\n";
    if (StringUtils.isNotEmpty(vo.getCERTNO())) {
      sql += " AND RAEC.CERTNO = '" + vo.getCERTNO() + "'";
    }
    if (StringUtils.isNotEmpty(vo.getRCHK_FLAG())) {
      sql += " AND RAEC.RCHK_FLAG = '" + vo.getRCHK_FLAG() + "'";
    }
    sql += " ORDER BY RAEC.RCHK_FLAG";

    return sql;
  }

  public String getNatDataDicA(ReflAppyEvtCVo vo) {
    String sql = "SELECT NDDA.NAT_DIC_VAL_CODE,\n" +
            "       NDDA.NAT_DIC_VAL_NAME\n" +
            "FROM polcent_db.nat_data_dic_a NDDA\n" +
            "WHERE NDDA.VALI_FLAG = 1\n";
    if (StringUtils.isNotEmpty(vo.getDIC_TYPE_CODE())) {
      sql += " AND NDDA.DIC_TYPE_CODE = '" + vo.getDIC_TYPE_CODE() + "'";
    }
    if (StringUtils.isNotEmpty(vo.getDIC_SOUC_ADMDVS())) {
      sql += " AND NDDA.DIC_SOUC_ADMDVS = '" + vo.getDIC_SOUC_ADMDVS() + "'";
    }
    return sql;
  }

  public String getPsnInfoB(ReflAppyEvtCVo vo) {
    String sql = "SELECT PIB.PSN_NO,\n" +
        "       PIB.PSN_MGTCODE,\n" +
        "       PIB.PSN_NAME,\n" +
        "       PIB.ALIS,\n" +
        "       PIB.GEND,\n" +
        "       PIB.BRDY,\n" +
        "       PIB.FILE_BRDY,\n" +
        "       PIB.PSN_CERT_TYPE,\n" +
        "       PIB.CERTNO,\n" +
        "       PIB.HSECFC,\n" +
        "       PIB.TEL,\n" +
        "       PIB.NATY,\n" +
        "       PIB.MOB,\n" +
        "       PIB.NAT_REGN_CODE,\n" +
        "       PIB.EMAIL,\n" +
        "       PIB.POLSTAS,\n" +
        "       PIB.FST_PATC_JOB_DATE,\n" +
        "       PIB.RESD_NATU,\n" +
        "       PIB.RESD_LOC_ADMDVS,\n" +
        "       PIB.HSREG_ADDR,\n" +
        "       PIB.HSREG_ADDR_POSCODE,\n" +
        "       PIB.LIVE_ADMDVS,\n" +
        "       PIB.LIVE_ADDR,\n" +
        "       PIB.LIVE_ADDR_POSCODE,\n" +
        "       PIB.RESDBOOK_NO,\n" +
        "       PIB.MRG_STAS,\n" +
        "       PIB.HLCON,\n" +
//        "       PIB.MEMO,\n" +
        "       PIB.SURV_STAS,\n" +
        "       PIB.MUL_PROV_MNT_FLAG,\n" +
        "       PIB.ADMDUT,\n" +
        "       PIB.RETR_TYPE,\n" +
        "       PIB.GRAD_SCHL,\n" +
        "       PIB.EDUC,\n" +
        "       PIB.PRO_TECH_DUTY_LV,\n" +
        "       PIB.NAT_PRFS_QUA_LV,\n" +
        "       PIB.VALI_FLAG,\n" +
        "       PIB.RID,\n" +
        "       PIB.CRTE_TIME,\n" +
        "       PIB.UPDT_TIME,\n" +
        "       PIB.CRTER_ID,\n" +
        "       PIB.CRTER_NAME,\n" +
        "       PIB.CRTE_OPTINS_NO,\n" +
        "       PIB.OPTER_ID,\n" +
        "       PIB.OPTER_NAME,\n" +
        "       PIB.OPT_TIME,\n" +
        "       PIB.OPTINS_NO,\n" +
        "       PIB.VER,\n" +
        "       P.NAT_DIC_VAL_NAME as PSN_CERT_TYPE_NAME,\n" +
        "       G.NAT_DIC_VAL_NAME as GEND_NAME,\n" +
        "       N.NAT_DIC_VAL_NAME as NATY_NAME\n" +
        "FROM basinfocent_db.psn_info_b PIB\n" +
        "LEFT JOIN polcent_db.nat_data_dic_a P\n" +
        "                   ON P.DIC_TYPE_CODE = 'PSN_CERT_TYPE' AND P.NAT_DIC_VAL_CODE = PIB.PSN_CERT_TYPE\n" +
        "LEFT JOIN polcent_db.nat_data_dic_a G\n" +
        "                   ON G.DIC_TYPE_CODE = 'GEND' AND G.NAT_DIC_VAL_CODE = PIB.GEND\n" +
        "LEFT JOIN polcent_db.nat_data_dic_a N\n" +
        "                   ON N.DIC_TYPE_CODE = 'NATY' AND N.NAT_DIC_VAL_CODE = PIB.NATY\n";
    sql += " WHERE PIB.VALI_FLAG = 1";
    if (StringUtils.isNotEmpty(vo.getCERTNO())) {
      sql += " AND PIB.CERTNO = '" + vo.getCERTNO() + "'";
    }
    if (StringUtils.isNotEmpty(vo.getPSN_CERT_TYPE())) {
      sql += " AND PIB.PSN_CERT_TYPE = '" + vo.getPSN_CERT_TYPE() + "'";
    }
    if (StringUtils.isNotEmpty(vo.getHSECFC())) {
      sql += " PIB.hsecfc = '" + vo.getHSECFC() + "'";
    }

    return sql;
  }

  public String getMedinsInfoB(ReflAppyEvtCVo vo) {
    String sql = "SELECT \n" +
        "                   FB.FIXMEDINS_CODE,\n" +
        "                   FB.FIXMEDINS_NAME,\n" +
        "                   FB.MEDINS_MGTCODE,\n" +
        "                   FB.FIXMEDINS_TYPE,\n" +
        "                   FB.PROV_PLAF_CODE,\n" +
        "                   FB.OUT_FIXMEDINS_TYPE,\n" +
        "                   FB.FIX_ONLN_OPEN_FLAG,\n" +
        "                   FB.OUT_ONLN_OPEN_TYPE,\n" +
        "                   FB.HI_RESPER_NAME,\n" +
        "                   FB.HI_RESPER_CERT_TYPE,\n" +
        "                   FB.HI_RESPER_CERTNO,\n" +
        "                   FB.HI_RESPER_TEL,\n" +
        "                   FB.ORGCODE,\n" +
        "                   FB.FIX_BLNG_ADMDVS,\n" +
        "                   FB.LMTPRIC_HOSP_LV,\n" +
        "                   FB.DEDC_HOSP_LV,\n" +
        "                   FB.POOLAREA_NO," +
//        "       M.NAT_DIC_VAL_NAME as MEDINSLV_NAME,\n" +
//        "       FOOF.NAT_DIC_VAL_NAME as FIX_ONLN_OPEN_FLAG_NAME,\n" +
//        "       OOOT.NAT_DIC_VAL_NAME as OUT_ONLN_OPEN_TYPE_NAME,\n" +
//        "       HRCT.NAT_DIC_VAL_NAME as HI_RESPER_CERT_TYPE_NAME\n," +
        "       HV.NAT_DIC_VAL_NAME as HOSP_LV_NAME,\n" +
        "       LHL.NAT_DIC_VAL_NAME as LMTPRIC_HOSP_LV_NAME,\n" +
        "       DHL.NAT_DIC_VAL_NAME as DEDC_HOSP_LV_NAME,\n" +
        "       OFT.NAT_DIC_VAL_NAME as OUT_FIXMEDINS_TYPE_NAME\n" +
        "FROM polcent_db.FIXMEDINS_B FB\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a M\n" +
//        "                   ON M.DIC_TYPE_CODE = 'MEDINSLV' AND M.NAT_DIC_VAL_CODE = FB.MEDINSLV\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a FOOF\n" +
//        "                   ON FOOF.DIC_TYPE_CODE = 'FIX_ONLN_OPEN_FLAG' AND FOOF.NAT_DIC_VAL_CODE = FB.FIX_ONLN_OPEN_FLAG\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a OOOT\n" +
//        "                   ON OOOT.DIC_TYPE_CODE = 'OUT_ONLN_OPEN_TYPE' AND OOOT.NAT_DIC_VAL_CODE = FB.OUT_ONLN_OPEN_TYPE\n" +
//        "LEFT JOIN polcent_db.nat_data_dic_a HRCT\n" +
//        "                   ON HRCT.DIC_TYPE_CODE = 'HI_RESPER_CERT_TYPE' AND HRCT.NAT_DIC_VAL_CODE = FB.HI_RESPER_CERT_TYPE\n" +
        "LEFT JOIN polcent_db.nat_data_dic_a HV\n" +
        "                   ON HV.DIC_TYPE_CODE = 'HOSP_LV' AND HV.NAT_DIC_VAL_CODE = FB.HOSP_LV\n" +
        "LEFT JOIN polcent_db.nat_data_dic_a LHL\n" +
        "                   ON LHL.DIC_TYPE_CODE = 'LMTPRIC_HOSP_LV' AND LHL.NAT_DIC_VAL_CODE = FB.LMTPRIC_HOSP_LV\n" +
        "LEFT JOIN polcent_db.nat_data_dic_a DHL\n" +
        "                   ON DHL.DIC_TYPE_CODE = 'DEDC_HOSP_LV' AND DHL.NAT_DIC_VAL_CODE = FB.DEDC_HOSP_LV\n" +
        "LEFT JOIN polcent_db.nat_data_dic_a OFT\n" +
        "                   ON OFT.DIC_TYPE_CODE = 'OUT_FIXMEDINS_TYPE' AND OFT.NAT_DIC_VAL_CODE = FB.OUT_FIXMEDINS_TYPE\n" +
        "WHERE  FB.FIXMEDINS_TYPE = 1 AND FB.VALI_FLAG = 1\n";
    if (StringUtils.isNotEmpty(vo.getMEDINS_CODE())) {
      sql += " AND FB.FIXMEDINS_CODE = '" + vo.getMEDINS_CODE() + "'";
    }
    if (StringUtils.isNotEmpty(vo.getMEDINS_NAME())) {
      sql += " AND FB.FIXMEDINS_NAME LIKE '%" + vo.getMEDINS_NAME() + "%'";
    }

    return sql;
  }

  public String getNatDataDicAByAdmdvs(ReflAppyEvtCVo vo) {
    String sql = "SELECT NDDA.NAT_DIC_VAL_CODE value,\n" +
        "       NDDA.NAT_DIC_VAL_NAME label\n" +
        "FROM POLCENT_DB.NAT_DATA_DIC_A NDDA\n" +
        "WHERE NDDA.DIC_TYPE_CODE = 'ADMDVS'\n" +
        "  AND NDDA.VALI_FLAG = 1\n";
    if (StringUtils.isNotEmpty(vo.getPRNT_DIC_VAL_CODE())) {
      sql += " AND NDDA.PRNT_DIC_VAL_CODE = '" + vo.getPRNT_DIC_VAL_CODE() + "'";
    } else {
      //国家医疗保障局 总父类
      sql += " AND NDDA.PRNT_DIC_VAL_CODE = '100000'";
    }

    return sql;
  }

  public String getMdtrtD(ReflAppyEvtCVo vo) {
    String sql = "SELECT MD.MEDINS_SETL_ID,\n" +
        "       MD.MDTRT_ID,\n" +
        "       MD.PSN_NO,\n" +
        "       MD.PSN_INSU_RLTS_ID,\n" +
        "       MD.PSN_CERT_TYPE,\n" +
        "       MD.CERTNO,\n" +
        "       MD.PSN_NAME,\n" +
        "       MD.GEND,\n" +
        "       MD.NATY,\n" +
        "       MD.BRDY,\n" +
        "       MD.AGE,\n" +
        "       MD.CONER_NAME,\n" +
        "       MD.TEL,\n" +
        "       MD.ADDR,\n" +
        "       MD.INSUTYPE,\n" +
        "       MD.PSN_TYPE,\n" +
        "       MD.CVLSERV_FLAG,\n" +
        "       MD.CVLSERV_LV,\n" +
        "       MD.SP_PSN_TYPE,\n" +
        "       MD.SP_PSN_TYPE_LV,\n" +
        "       MD.CLCT_GRDE,\n" +
        "       MD.FLXEMPE_FLAG,\n" +
        "       MD.NWB_FLAG,\n" +
        "       MD.INSU_ADMDVS,\n" +
        "       MD.EMP_NO,\n" +
        "       MD.EMP_NAME,\n" +
        "       MD.EMP_TYPE,\n" +
        "       MD.AFIL_INDU,\n" +
        "       MD.ECON_TYPE,\n" +
        "       MD.AFIL_RLTS,\n" +
        "       MD.PAY_LOC,\n" +
        "       MD.EMP_MGT_TYPE,\n" +
        "       MD.FIXMEDINS_CODE,\n" +
        "       MD.FIXMEDINS_NAME,\n" +
        "       MD.HOSP_LV,\n" +
        "       MD.FIX_BLNG_ADMDVS,\n" +
        "       MD.BEGNTIME,\n" +
        "       MD.LMTPRIC_HOSP_LV,\n" +
        "       MD.DEDC_HOSP_LV,\n" +
        "       MD.ENDTIME,\n" +
        "       MD.MDTRT_CERT_TYPE,\n" +
        "       MD.MDTRT_CERT_NO,\n" +
        "       MD.RLOC_TYPE,\n" +
        "       MD.MED_TYPE,\n" +
        "       MD.ARS_YEAR_IPT_FLAG,\n" +
        "       MD.PRE_PAY_FLAG,\n" +
        "       MD.REFL_OLD_MDTRT_ID,\n" +
        "       MD.MEDRCDNO,\n" +
        "       MD.IPT_OTP_NO,\n" +
        "       MD.CHFPDR_CODE,\n" +
        "       MD.ADM_DIAG_DSCR,\n" +
        "       MD.ADM_DEPT_CODG,\n" +
        "       MD.ADM_DEPT_NAME,\n" +
        "       MD.ADM_BED,\n" +
        "       MD.TRAF_DEPT_FLAG,\n" +
        "       MD.WARDAREA_BED,\n" +
        "       MD.DSCG_MAINDIAG_CODE,\n" +
        "       MD.DSCG_DEPT_CODG,\n" +
        "       MD.DSCG_DEPT_NAME,\n" +
        "       MD.DSCG_BED,\n" +
        "       MD.MAIN_COND_DSCR,\n" +
        "       MD.DSCG_WAY,\n" +
        "       MD.DISE_NO,\n" +
        "       MD.DISE_NAME,\n" +
        "       MD.OPRN_OPRT_CODE,\n" +
        "       MD.OPRN_OPRT_NAME,\n" +
        "       MD.OTP_DIAG_INFO,\n" +
        "       MD.INHOSP_STAS,\n" +
        "       MD.IPT_DAYS,\n" +
        "       MD.DIE_DATE,\n" +
        "       MD.BIRCTRL_TYPE,\n" +
        "       MD.FETTS,\n" +
        "       MD.GESO_VAL,\n" +
        "       MD.FETUS_CNT,\n" +
        "       MD.MATN_TYPE,\n" +
        "       MD.PREY_TIME,\n" +
        "       MD.LATECHB_FLAG,\n" +
        "       MD.FPSC_NO,\n" +
        "       MD.BIRCTRL_MATN_DATE,\n" +
        "       MD.COP_FLAG,\n" +
        "       MD.TRT_DCLA_DETL_SN,\n" +
        "       MD.VALI_FLAG,\n" +
        "       MD.MEMO,\n" +
        "       MD.RID,\n" +
        "       MD.UPDT_TIME,\n" +
        "       MD.CRTER_ID,\n" +
        "       MD.CRTER_NAME,\n" +
        "       MD.CRTE_TIME,\n" +
        "       MD.CRTE_OPTINS_NO,\n" +
        "       MD.OPTER_ID,\n" +
        "       MD.OPTER_NAME,\n" +
        "       MD.OPT_TIME,\n" +
        "       MD.OPTINS_NO,\n" +
        "       MD.CHFPDR_NAME,\n" +
        "       MD.POOLAREA_NO,\n" +
        "       MD.DSCG_MAINDIAG_NAME,\n" +
        "       MD.ADM_CATY,\n" +
        "       MD.DSCG_CATY,\n" +
        "       MD.TTP_PAY_FLAG,\n" +
        "       MD.TTP_PAY_PROP,\n" +
        "       MD.DISE_TYPE_CODE,\n" +
        "       MD.SAME_DISE_ADM_FLAG,\n" +
        "       MD.QUTS_TYPE\n" +
        "FROM setlcent_db.mdtrt_d MD\n" +
        "WHERE \n";

    sql += " MD.FIXMEDINS_CODE = '" + vo.getFIXMEDINS_CODE() + "'";
    if (StringUtils.isNotEmpty(vo.getCERTNO())) {
      sql += " AND MD.CERTNO = '" + vo.getCERTNO() + "'";
    }

    return sql;
  }


  public String getDictListBycode(ReflAppyEvtCVo vo){
    String sql = "select nat_dic_val_code,nat_dic_val_name from POLCENT_DB.NAT_DATA_DIC_A where DIC_TYPE_CODE='"+vo.getNat_dic_val_code()+"'" ;
    return sql ;
  }
}
