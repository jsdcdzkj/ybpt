package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.vo.*;
import org.springframework.stereotype.Repository;

@Repository
public class PsnFxMainDetlDDao {
    /**
    *人员登记信息
    * Author wzn
    * Date 2022/6/14 13:44
    */
    public String selectPsnList(Page page, PsnFixMainDetlDVo psnFixMainDetlDVo){
        String sql ="SELECT\n" +
                "\tpfmd.INSUTYPE,\n" +
                "\tpfmd.BIZ_APPY_TYPE,\n" +
                " ndd.nat_dic_val_NAME INSUTYPENAME, \n" +
                "\tndd2.nat_dic_val_NAME BIZ_APPY_TYPE_NAME, "+
                "\tpfmd.FIXMEDINS_CODE,\n" +
                "\tpfmd.FIXMEDINS_NAME,\n" +
                "\tpfmd.BEGNDATE,\n" +
                "\tpfmd.ENDDATE,\n" +
                "\tpfmd.PSN_NO,\n" +
                "\tpfmd.POOLAREA_NO,\n" +
                "\tpib.PSN_NAME,\n" +
                "\tpib.CERTNO, \n" +
                "\tpib.HSECFC, \n" +
                "\tpib.GEND, \n" +
                "\tpib.BRDY, \n" +
                "\tpib.PSN_CERT_TYPE, \n" +
                "\tpib.MOB, \n" +
                "\tpib.NATY, \n" +
                "\tpib.LIVE_ADDR \n" +
                "FROM\n" +
                "\tSETLCENT_DB.PSN_FIX_MAIN_DETL_D pfmd\n" +
                "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = pfmd.PSN_NO \n" +
                " left join POLCENT_DB.NAT_DATA_DIC_A ndd on ndd.nat_dic_val_code=pfmd.INSUTYPE and ndd.DIC_TYPE_CODE='INSUTYPE'\n" +
                "\tleft join POLCENT_DB.NAT_DATA_DIC_A ndd2 on ndd2.nat_dic_val_code=pfmd.BIZ_APPY_TYPE and ndd2.DIC_TYPE_CODE='BIZ_APPY_TYPE' "+
                "WHERE\n" +
                "\tpfmd.VALI_FLAG = '1'" ;
        if(!"".equals(psnFixMainDetlDVo.getInsutype()) && null != psnFixMainDetlDVo.getInsutype()){
            sql +=" and pfmd.INSUTYPE='"+psnFixMainDetlDVo.getInsutype()+"'" ;
        }
        if(!"".equals(psnFixMainDetlDVo.getBiz_appy_type()) && null != psnFixMainDetlDVo.getBiz_appy_type()){
            sql +=" and pfmd.BIZ_APPY_TYPE='"+psnFixMainDetlDVo.getBiz_appy_type()+"'" ;
        }
        if(!"".equals(psnFixMainDetlDVo.getCertno()) && null != psnFixMainDetlDVo.getCertno()){
            sql +=" and pib.CERTNO='"+psnFixMainDetlDVo.getCertno()+"'" ;
        }
        if(!"".equals(psnFixMainDetlDVo.getFixmedins_code()) && null != psnFixMainDetlDVo.getFixmedins_code()){
            sql +=" and pfmd.FIXMEDINS_CODE='"+psnFixMainDetlDVo.getFixmedins_code()+"'" ;
        }
        if(!"".equals(psnFixMainDetlDVo.getPsn_no()) && null != psnFixMainDetlDVo.getPsn_no()){
            sql +=" and pib.PSN_NO='"+psnFixMainDetlDVo.getPsn_no()+"'" ;
        }
        if(!"".equals(psnFixMainDetlDVo.getPsn_name()) && null != psnFixMainDetlDVo.getPsn_name()){
            sql +=" and pib.PSN_NAME like'%"+psnFixMainDetlDVo.getPsn_name()+"%'" ;
        }
        if(!"".equals(psnFixMainDetlDVo.getHsecfc()) && null != psnFixMainDetlDVo.getHsecfc()){
            sql +=" and pib.HSECFC='"+psnFixMainDetlDVo.getHsecfc()+"'" ;
        }

        return sql ;
    }


    /**
    *定点医疗机构就诊信息查询
    * Author wzn
    * Date 2022/6/14 13:53
    */
    public String consultationInfo(Page page,MdtrtDVo mdtrtDVo){
        String sql="SELECT\n" +
                "\tndd.nat_dic_val_name PSN_CERT_TYPE,\n" +
                "\tmd.CERTNO,\n" +
                "\tmd.PSN_NO,\n" +
                "\tmd.MDTRT_ID,\n" +
                "\tmd.PSN_NAME,\n" +
                "\tndd2.nat_dic_val_name GEND,\n" +
                "\tndd3.nat_dic_val_name NATY,\n" +
                "\tBRDY,\n" +
                "\tmd.AGE,\n" +
                "\tmd.TEL,\n" +
                "\tmd.ADDR,\n" +
                "\tndd5.nat_dic_val_name INSUTYPE,\n" +
                "\tndd6.nat_dic_val_name PSN_TYPE,\n" +
                "\tndd20.nat_dic_val_name CVLSERV_FLAG,\n" +
                "\tmd.SP_PSN_TYPE,\n" +
                "\tmd.SP_PSN_TYPE_LV,\n" +
                "\tndd7.nat_dic_val_name CLCT_GRDE,\n" +
                "\tndd8.nat_dic_val_name FLXEMPE_FLAG,\n" +
                "\tndd9.nat_dic_val_name NWB_FLAG,\n" +
                "\tmd.INSU_ADMDVS,\n" +
                "\tmd.EMP_NAME,\n" +
                "\tmd.EMP_TYPE,\n" +
                "\tndd10.nat_dic_val_name ECON_TYPE,\n" +
                "\tmd.AFIL_INDU,\n" +
                "\tmd.FIXMEDINS_CODE,\n" +
                "\tmd.FIXMEDINS_NAME,\n" +
                "\tndd11.nat_dic_val_name HOSP_LV,\n" +
                "\tmd.FIX_BLNG_ADMDVS,\n" +
                "\tndd12.nat_dic_val_name LMTPRIC_HOSP_LV,\n" +
                "\tndd13.nat_dic_val_name DEDC_HOSP_LV,\n" +
                "\tndd14.nat_dic_val_name MED_TYPE,\n" +
                "\tndd15.nat_dic_val_name RLOC_TYPE,\n" +
                "\tmd.YEAR,\n" +
                "\tmd.ADM_DEPT_NAME,\n" +
                "\tmd.DSCG_DEPT_NAME,\n" +
                "\tmd.DISE_NAME,\n" +
                "\tmd.OPRN_OPRT_NAME,\n" +
                "\tmd.OTP_DIAG_INFO,\n" +
                "\tmd.INHOSP_STAS,\n" +
                "\tmd.IPT_DAYS,\n" +
                "\tmd.FETTS,\n" +
                "\tmd.FETUS_CNT,\n" +
                "\tndd16.nat_dic_val_name MATN_TYPE,\n" +
                "\tndd17.nat_dic_val_name LATECHB_FLAG,\n" +
                "\tndd18.nat_dic_val_name PRET_FLAG,\n" +
                "\tmd.COP_FLAG,\n" +
                "\tmd.FPSC_NO \n" +
                "FROM\n" +
                "\tSETLCENT_DB.MDTRT_D md\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd ON ndd.nat_dic_val_code = md.PSN_CERT_TYPE \n" +
                "\tAND ndd.DIC_TYPE_CODE = 'PSN_CERT_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd2 ON ndd2.nat_dic_val_code = md.GEND \n" +
                "\tAND ndd2.DIC_TYPE_CODE = 'GEND'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd3 ON ndd3.nat_dic_val_code = md.NATY \n" +
                "\tAND ndd3.DIC_TYPE_CODE = 'NATY'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd5 ON ndd5.nat_dic_val_code = md.INSUTYPE \n" +
                "\tAND ndd5.DIC_TYPE_CODE = 'INSUTYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd6 ON ndd6.nat_dic_val_code = md.PSN_TYPE \n" +
                "\tAND ndd6.DIC_TYPE_CODE = 'PSN_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd7 ON ndd7.nat_dic_val_code = md.CLCT_GRDE \n" +
                "\tAND ndd7.DIC_TYPE_CODE = 'CLCT_GRDE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd8 ON ndd8.nat_dic_val_code = md.FLXEMPE_FLAG \n" +
                "\tAND ndd8.DIC_TYPE_CODE = 'FLXEMPE_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd9 ON ndd9.nat_dic_val_code = md.NWB_FLAG \n" +
                "\tAND ndd9.DIC_TYPE_CODE = 'NWB_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd10 ON ndd10.nat_dic_val_code = md.ECON_TYPE \n" +
                "\tAND ndd10.DIC_TYPE_CODE = 'ECON_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd11 ON ndd11.nat_dic_val_code = md.HOSP_LV \n" +
                "\tAND ndd11.DIC_TYPE_CODE = 'HOSP_LV'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd12 ON ndd12.nat_dic_val_code = md.LMTPRIC_HOSP_LV \n" +
                "\tAND ndd12.DIC_TYPE_CODE = 'LMTPRIC_HOSP_LV'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd13 ON ndd13.nat_dic_val_code = md.DEDC_HOSP_LV \n" +
                "\tAND ndd13.DIC_TYPE_CODE = 'DEDC_HOSP_LV'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd14 ON ndd14.nat_dic_val_code = md.MED_TYPE \n" +
                "\tAND ndd14.DIC_TYPE_CODE = 'MED_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd15 ON ndd15.nat_dic_val_code = md.RLOC_TYPE \n" +
                "\tAND ndd15.DIC_TYPE_CODE = 'RLOC_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd16 ON ndd16.nat_dic_val_code = md.MATN_TYPE \n" +
                "\tAND ndd16.DIC_TYPE_CODE = 'MATN_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd17 ON ndd17.nat_dic_val_code = md.LATECHB_FLAG \n" +
                "\tAND ndd17.DIC_TYPE_CODE = 'LATECHB_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd18 ON ndd18.nat_dic_val_code = md.PRET_FLAG \n" +
                "\tAND ndd18.DIC_TYPE_CODE = 'PRET_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd19 ON ndd19.nat_dic_val_code = md.COP_FLAG \n" +
                "\tAND ndd19.DIC_TYPE_CODE = 'COP_FLAG' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd20 ON ndd20.nat_dic_val_code = md.CVLSERV_FLAG \n" +
                "\tAND ndd20.DIC_TYPE_CODE = 'CVLSERV_FLAG' \n" +
                "WHERE\n" +
                "\tmd.VALI_FLAG = '1' \n" ;
        if(!"".equals(mdtrtDVo.getCertno()) && null != mdtrtDVo.getCertno()){
            sql += " and md.CERTNO = '"+mdtrtDVo.getCertno()+"'" ;
        }
        if(!"".equals(mdtrtDVo.getFixmedins_code()) && null != mdtrtDVo.getFixmedins_code()){
            sql += " and md.FIXMEDINS_CODE = '"+mdtrtDVo.getFixmedins_code()+"'" ;

        }
        if(!"".equals(mdtrtDVo.getPsn_no()) && null != mdtrtDVo.getPsn_no()){
            sql += " and md.PSN_NO = '"+mdtrtDVo.getPsn_no()+"'" ;
        }
        if(!"".equals(mdtrtDVo.getInsu_admdvs()) && null != mdtrtDVo.getInsu_admdvs()){
            sql += " and md.INSU_ADMDVS = '"+mdtrtDVo.getInsu_admdvs()+"'" ;
        }
        if(!"".equals(mdtrtDVo.getPsn_name()) && null != mdtrtDVo.getPsn_name()){
            sql += " and md.PSN_NAME like '%"+mdtrtDVo.getPsn_name()+"%'" ;
        }
        return sql ;
    }

    /**
    *费用信息
    * Author wzn
    * Date 2022/6/14 20:06
    */
    public String chargeDetails(Page page, FeeListDVo feeListDVo){
        String sql = "SELECT\n" +
                "\tfld.cnt,\n" +
                "\tfld.MDTRT_ID,\n" +
                "\tfld.pric,\n" +
                "\tfld.det_item_fee_sumamt,\n" +
                "\tfld.medins_list_codg,\n" +
                "\tfld.medins_list_name,\n" +
                "\tndd.nat_dic_val_name med_type,\n" +
                "\tfld.prodname,\n" +
                "\tfld.SPEC \n" +
                "FROM\n" +
                "\tSETLCENT_DB.FEE_LIST_D  fld\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd ON ndd.nat_dic_val_code = fld.med_type \n" +
                "\tAND ndd.DIC_TYPE_CODE = 'MED_TYPE'\n" +
                "WHERE\n" +
                "\tfld.MDTRT_ID = '"+feeListDVo.getMDTRT_ID()+"'" ;
        return sql ;
    }


    /**
    *医疗保障基金结算清单信息查询
    * Author wzn
    * Date 2022/6/14 20:06
    */
    public String listInformation(Page page,MdcsFundSetlListVo mdcsFundSetlListVo){
        String sql ="SELECT\n" +
                "\tmfsl.FIXMEDINS_CODE,\n" +
                "\tmfsl.HI_SETL_LV,\n" +
                "\tmfsl.PSN_NO,\n" +
                "\tmfsl.FIXMEDINS_NAME,\n" +
                "\tmfsl.DCLA_TIME,\n" +
                "\tmfsl.PSN_NAME,\n" +
                "\tndd.nat_dic_val_name GEND,\n" +
                "\tmfsl.BRDY,\n" +
                "\tmfsl.AGE,\n" +
                "\tndd2.nat_dic_val_name NATY,\n" +
                "\tndd3.nat_dic_val_name PSN_CERT_TYPE,\n" +
                "\tmfsl.CERTNO,\n" +
                "\tndd4.nat_dic_val_name PRFS,\n" +
                "\tmfsl.CURR_ADDR,\n" +
                "\tmfsl.EMP_NAME,\n" +
                "\tmfsl.CONER_TEL,\n" +
                "\tmfsl.INSUTYPE,\n" +
//                "\tmfsl.SP_PSN_TYPE,\n" +
                "\tndd5.nat_dic_val_name NWB_ADM_TYPE,\n" +
                "\tmfsl.OPSP_MDTRT_TIME,\n" +
                "\tmfsl.SP_PSN_TYPE,\n" +
                "\tndd6.nat_dic_val_name IPT_MED_TYPE,\n" +
                "\tndd7.nat_dic_val_name TRT_TYPE,\n" +
                "\tmfsl.ADM_TIME,\n" +
                "\tmfsl.DSCG_TIME,\n" +
                "\tmfsl.OTP_WM_DIAG,\n" +
                "\tmfsl.OTP_WM_DIAG_DISE_CODE,\n" +
                "\tmfsl.OTP_TCM_DIAG,\n" +
                "\tmfsl.CHFPDR_NAME,\n" +
                "\tmfsl.CHFPDR_CODE,\n" +
                "\tmfsl.PSN_SELFPAY_AMT,\n" +
                "\tmfsl.ACCT_PAYAMT,\n" +
                "\tmfsl.PSN_CASHPAY,\n" +
              "\tmfsl.PSN_OWNPAY_FEE\n" +
                "\t\n" +
                "FROM\n" +
                "\tSETLCENT_DB.MDCS_FUND_SETL_LIST_D mfsl \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd ON ndd.nat_dic_val_code = mfsl.GEND\n" +
                "\tAND ndd.DIC_TYPE_CODE = 'GEND'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd2 ON ndd2.nat_dic_val_code = mfsl.NATY\n" +
                "\tAND ndd2.DIC_TYPE_CODE = 'NATY'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd3 ON ndd3.nat_dic_val_code = mfsl.PSN_CERT_TYPE\n" +
                "\tAND ndd3.DIC_TYPE_CODE = 'PSN_CERT_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd4 ON ndd4.nat_dic_val_code = mfsl.PRFS\n" +
                "\tAND ndd4.DIC_TYPE_CODE = 'PRFS'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd5 ON ndd5.nat_dic_val_code = mfsl.NWB_ADM_TYPE\n" +
                "\tAND ndd5.DIC_TYPE_CODE = 'NWB_ADM_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd6 ON ndd6.nat_dic_val_code = mfsl.IPT_MED_TYPE\n" +
                "\tAND ndd6.DIC_TYPE_CODE = 'IPT_MED_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd7 ON ndd7.nat_dic_val_code = mfsl.TRT_TYPE\n" +
                "\tAND ndd7.DIC_TYPE_CODE = 'TRT_TYPE'\n" +
                "\n" +
                "\twhere mfsl.VALI_FLAG='1'" ;

        if(!"".equals(mdcsFundSetlListVo.getCertno()) && null != mdcsFundSetlListVo.getCertno()){
            sql += " and mfsl.CERTNO = '"+mdcsFundSetlListVo.getCertno()+"'" ;
        }
        if(!"".equals(mdcsFundSetlListVo.getFixmedins_code()) && null != mdcsFundSetlListVo.getFixmedins_code()){
            sql += " and mfsl.FIXMEDINS_CODE = '"+mdcsFundSetlListVo.getFixmedins_code()+"'" ;
        }
        if(!"".equals(mdcsFundSetlListVo.getPsn_no()) && null != mdcsFundSetlListVo.getPsn_no()){
            sql += " and mfsl.PSN_NO = '"+mdcsFundSetlListVo.getPsn_no()+"'" ;
        }
        if(!"".equals(mdcsFundSetlListVo.getFixmedins_name()) && null != mdcsFundSetlListVo.getFixmedins_name()){
            sql += " and mfsl.FIXMEDINS_NAME like '%"+mdcsFundSetlListVo.getFixmedins_name()+"%'" ;
        }
        if(!"".equals(mdcsFundSetlListVo.getHi_setl_lv()) && null != mdcsFundSetlListVo.getHi_setl_lv()){
            sql += " and mfsl.HI_SETL_LV = '"+mdcsFundSetlListVo.getHi_setl_lv()+"'" ;
        }
        if(!"".equals(mdcsFundSetlListVo.getInsutype()) && null != mdcsFundSetlListVo.getInsutype()){
            sql += " and mfsl.INSUTYPE = '"+mdcsFundSetlListVo.getInsutype()+"'" ;
        }
        return sql ;
    }


    /**
    *住院病案首页信息查询
    * Author wzn
    * Date 2022/6/15 12:02
    */
    public String beInHospitalInfo(Page page,IptMedcasBasVo iptMedcasBasVo){
        String sql ="SELECT\n" +
                "\timhb.PSN_NAME,\n" +
                "\tpib.INSUTYPE,\n" +
                "\timhb.FIXMEDINS_CODE,\n" +
                "\timhb.PATN_IPT_CNT,\n" +
                "\tndd.nat_dic_val_name gend,\n" +
                "\timhb.BRDY,\n" +
                "\timhb.NTLY_NAME,\n" +
                "\timhb.CERTNO,\n" +
                "\timhb.CURR_ADDR,\n" +
                "\timhb.PSN_TEL,\n" +
                "\timhb.RESD_ADDR,\n" +
                "\timhb.EMPR_TEL,\n" +
                "\timhb.TRT_TYPE_NAME,\n" +
                "\timhb.ADM_WARD,\n" +
                "\timhb.ADM_DATE,\n" +
                "\timhb.DSCG_DATE,\n" +
                "\timhb.IPT_DAYS,\n" +
                "\timhb.DIE_FLAG,\n" +
                "\timhb.DEPTDRT_NAME,\n" +
                "\timhb.CHFDR_NAME,\n" +
                "\timhb.ATDDR_NAME,\n" +
                "\timhb.CHFPDR_NAME,\n" +
                "\timhb.IPT_DR_NAME,\n" +
                "\timhb.RESP_NURS_NAME,\n" +
                "\timhb.TRAIN_DR_NAME,\n" +
              "\timhb.INTN_DR_NAME,\n" +
                "\timhb.QLTCTRL_NURS_NAME,\n" +
                "\timhb.QLTCTRL_DATE,\n" +
                "\timhb.CNFM_DATE,\n" +
                "\timhb.HIF_PAY_WAY_NAME,\n" +
                "\timhb.QLTCTRL_DR_NAME,\n" +
                "\timhb.MED_FEE_PAYMTD_NAME,\n" +
                "\timhb.SELFPAY_AMT,\n" +
                "\timhb.MEDFEE_SUMAMT,\n" +
                "\timhb.ORDN_MED_SERVFEE,\n" +
                "\timhb.ORDN_TRT_OPRT_FEE,\n" +
                "\timhb.NURS_FEE,\n" +
                "\timhb.COM_MED_SERV_OTH_FEE,\n" +
                "\timhb.PALG_DIAG_FEE,\n" +
                "\timhb.LAB_DIAG_FEE,\n" +
                "\timhb.RDHY_DIAG_FEE,\n" +
                "\timhb.CLNC_DISE_FEE,\n" +
                "\timhb.NSRGTRT_ITEM_FEE,\n" +
                "\timhb.CLNC_PHYS_TRT_FEE,\n" +
                "\timhb.OPRN_TRT_FEE,\n" +
                "\timhb.ANST_FEE,\n" +
                "\timhb.OPRN_FEE,\n" +
                "\timhb.RHAB_FEE,\n" +
                "\timhb.TCM_TRT_FEE,\n" +
                "\timhb.WMFEE,\n" +
                "\timhb.ABTL_MEDN_FEE,\n" +
                "\timhb.TCMPAT_FEE,\n" +
                "\timhb.TCMHERB_FEE,\n" +
                "\timhb.BLO_FEE,\n" +
                "\timhb.ALBU_FEE,\n" +
                "\timhb.GLON_FEE,\n" +
                "\timhb.CLOTFAC_FEE,\n" +
                "\timhb.CYKI_FEE,\n" +
                "\timhb.EXAM_DSPO_MATL_FEE,\n" +
                "\timhb.TRT_DSPO_MATL_FEE,\n" +
                "\timhb.OPRN_DSPO_MATL_FEE,\n" +
                "\timhb.OTH_FEE\n" +
                "FROM\n" +
                "\tSETLCENT_DB.IPT_MEDCAS_HMPG_BAS_INFO_D imhb left join CUSTCENT_DB.PSN_INSU_STAS_B pib on pib.PSN_NO = imhb.PSN_NO\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd ON ndd.nat_dic_val_code = imhb.gend\n" +
                "\tAND ndd.DIC_TYPE_CODE = 'GEND'\n" +
                "\twhere imhb.VALI_FLAG = '1'" ;
        if(!"".equals(iptMedcasBasVo.getCertno()) && null != iptMedcasBasVo.getCertno()){
            sql += " and imhb.CERTNO = '"+iptMedcasBasVo.getCertno()+"'" ;
        }
        if(!"".equals(iptMedcasBasVo.getPsn_name()) && null != iptMedcasBasVo.getPsn_name()){
            sql += " and imhb.PSN_NAME like '%"+iptMedcasBasVo.getPsn_name()+"%'" ;
        }
        if(!"".equals(iptMedcasBasVo.getFixmedins_code()) && null != iptMedcasBasVo.getFixmedins_code()){
            sql += " and imhb.FIXMEDINS_CODE = '"+iptMedcasBasVo.getFixmedins_code()+"'" ;
        }
        if(!"".equals(iptMedcasBasVo.getInsutype()) && null != iptMedcasBasVo.getInsutype()){
            sql += " and pib.INSUTYPE = '"+iptMedcasBasVo.getInsutype()+"'" ;
        }

        if (null != iptMedcasBasVo.getTimes()) {
            sql += " and substr(TO_CHAR(imhb.DSCG_DATE,'YYYY-MM-DD'),1,10) <= '" + iptMedcasBasVo.getTimes()[1] + "' ";
            sql += " and substr(TO_CHAR(imhb.ADM_DATE,'YYYY-MM-DD'),1,10) >= '" + iptMedcasBasVo.getTimes()[0] + "' ";
        }
        return sql ;
    }


    /**
    *医疗拨付信息查询
    * Author wzn
    * Date 2022/6/15 16:19
    */
    public String medDfrDInfo(Page page,MedDfrDVo medDfrDVo){
        String sql = "SELECT\n" +
                "\tmdd.CASHYM,\n" +
                "\tmddd.INSUTYPE,\n" +
                "\tndd.nat_dic_val_name DFR_NOTC_TYPE,\n" +
                "\tndd2.nat_dic_val_name TRT_ISSU_WAY,\n" +
                "\tndd3.nat_dic_val_name DFR_OBJ,\n" +
                "\tmdd.DFR_OBJ_NAME,\n" +
                "\tmdd.DFR_SUMAMT,\n" +
                "\tmdd.BANKCODE,\n" +
                "\tmdd.BANKACCT,\n" +
                "\tmdd.ACCTNAME,\n" +
                "\tndd4.nat_dic_val_name DFR_SOUC,\n" +
                "\tmdd.DOCNO,\n" +
                "\tmdd.SOUC_SN,\n" +
                "\tndd5.nat_dic_val_name FIN_DSPO_FLAG,\n" +
                "\tmdd.PAY_FAIL_DESC,\n" +
                "\tmdd.FIN_ACEN_TIME,\n" +
                "\tndd6.nat_dic_val_name PSN_TYPE,\n" +
                "\tndd7.nat_dic_val_name SP_PSN_TYPE,\n" +
                "\tndd8.nat_dic_val_name ECON_TYPE,\n" +
                "\tndd9.nat_dic_val_name MED_TYPE,\n" +
                "\tndd10.nat_dic_val_name CLR_TYPE,\n" +
                "\tndd11.nat_dic_val_name CLR_TYPE_LV2,\n" +
                "\tndd12.nat_dic_val_name QUTS_TYPE\n" +
                "FROM\n" +
                "\tSETLCENT_DB.MED_DFR_D mdd\n" +
                "\tLEFT JOIN SETLCENT_DB.MED_DFR_DETL_D mddd ON mddd.DFR_ID = mdd.DFR_ID\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd ON ndd.nat_dic_val_code = mdd.DFR_NOTC_TYPE \n" +
                "\tAND ndd.DIC_TYPE_CODE = 'DFR_NOTC_TYPE' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd2 ON ndd2.nat_dic_val_code = mdd.TRT_ISSU_WAY \n" +
                "\tAND ndd2.DIC_TYPE_CODE = 'TRT_ISSU_WAY' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd3 ON ndd3.nat_dic_val_code = mdd.DFR_OBJ \n" +
                "\tAND ndd3.DIC_TYPE_CODE = 'DFR_OBJ' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd4 ON ndd4.nat_dic_val_code = mdd.DFR_SOUC \n" +
                "\tAND ndd4.DIC_TYPE_CODE = 'DFR_SOUC' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd5 ON ndd5.nat_dic_val_code = mdd.FIN_DSPO_FLAG \n" +
                "\tAND ndd5.DIC_TYPE_CODE = 'FIN_DSPO_FLAG' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd6 ON ndd6.nat_dic_val_code = mddd.PSN_TYPE \n" +
                "\tAND ndd6.DIC_TYPE_CODE = 'PSN_TYPE' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd7 ON ndd7.nat_dic_val_code = mddd.SP_PSN_TYPE \n" +
                "\tAND ndd7.DIC_TYPE_CODE = 'SP_PSN_TYPE' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd8 ON ndd8.nat_dic_val_code = mddd.ECON_TYPE \n" +
                "\tAND ndd8.DIC_TYPE_CODE = 'ECON_TYPE' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd9 ON ndd9.nat_dic_val_code = mddd.MED_TYPE \n" +
                "\tAND ndd9.DIC_TYPE_CODE = 'MED_TYPE' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd10 ON ndd10.nat_dic_val_code = mddd.CLR_TYPE \n" +
                "\tAND ndd10.DIC_TYPE_CODE = 'CLR_TYPE' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd11 ON ndd11.nat_dic_val_code = mddd.CLR_TYPE_LV2 \n" +
                "\tAND ndd11.DIC_TYPE_CODE = 'CLR_TYPE_LV2' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd12 ON ndd12.nat_dic_val_code = mddd.QUTS_TYPE \n" +
                "\tAND ndd12.DIC_TYPE_CODE = 'QUTS_TYPE' \n" +
                "\t\n" +
                "WHERE\n" +
                "\tmdd.VALI_FLAG = '1'" ;
        if(!"".equals(medDfrDVo.getBankacct()) && null != medDfrDVo.getBankacct()){
            sql += " and mdd.BANKACCT = '"+medDfrDVo.getBankacct()+"'" ;
        }

        if(!"".equals(medDfrDVo.getDfr_obj_name()) && null != medDfrDVo.getDfr_obj_name()){
            sql += " and mdd.DFR_OBJ_NAME like '%"+medDfrDVo.getDfr_obj_name()+"%'" ;
        }
        if(!"".equals(medDfrDVo.getInsutype()) && null != medDfrDVo.getInsutype()){
            sql += " and mddd.INSUTYPE = '"+medDfrDVo.getInsutype()+"'" ;
        }

        return sql ;
    }


    /**
    *异地就医信息查询
    * Author wzn
    * Date 2022/6/16 11:12
    */
    public String outmedMdtrt(Page page,OutmedMdtrtVo outmedMdtrtVo){
        String sql ="SELECT \n" +
                "\tndd.nat_dic_val_name PSN_CERT_TYPE,\n" +
                "\tomd.CERTNO, \n" +
                "\tomd.mdtrt_id, \n" +
                "\tomd.PSN_NAME,\n" +
                "\tndd2.nat_dic_val_name GEND,\n" +
                "\tndd3.nat_dic_val_name NATY,\n" +
                "\tomd.BRDY,\n" +
                "\tomd.ADDR, \n" +
                "\tndd4.nat_dic_val_name INSUTYPE,\n" +
                "\tndd5.nat_dic_val_name PSN_TYPE,\n" +
                "\tndd6.nat_dic_val_name CVLSERV_FLAG,\n" +
                "\tndd7.nat_dic_val_name CVLSERV_LV,\n" +
                "\tomd.CLCT_GRDE, \n" +
                "\tomd.EMP_NAME, \n" +
                "\tomd.EMP_TYPE, \n" +
                "\tomd.FIXMEDINS_CODE, \n" +
                "\tomd.FIXMEDINS_NAME, \n" +
                "\tomd.BEGNTIME, \n" +
                "\tomd.ENDTIME, \n" +
                "\tndd8.nat_dic_val_name RLOC_TYPE,\n" +
                "\tndd9.nat_dic_val_name ARS_YEAR_IPT_FLAG,\n" +
                "\tndd10.nat_dic_val_name PRE_PAY_FLAG,\n" +
                "\tomd.YEAR, \n" +
                "\tomd.DISE_NAME, \n" +
                "\tomd.DISE_NO, \n" +
                "\tomd.OPRN_OPRT_NAME, \n" +
                "\tomd.OPRN_OPRT_CODE, \n" +
                "\tomd.OTP_DIAG_INFO, \n" +
                "\tomd.CHFPDR_NAME, \n" +
                "\tomd.DSCG_MAINDIAG_NAME\n" +
                "FROM\n" +
                "\tSETLCENT_DB.OUTMED_MDTRT_D omd\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd ON ndd.nat_dic_val_code = omd.PSN_CERT_TYPE\n" +
                "\tAND ndd.DIC_TYPE_CODE = 'PSN_CERT_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd2 ON ndd2.nat_dic_val_code = omd.GEND\n" +
                "\tAND ndd2.DIC_TYPE_CODE = 'GEND'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd3 ON ndd3.nat_dic_val_code = omd.NATY\n" +
                "\tAND ndd3.DIC_TYPE_CODE = 'NATY'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd4 ON ndd4.nat_dic_val_code = omd.INSUTYPE\n" +
                "\tAND ndd4.DIC_TYPE_CODE = 'INSUTYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd5 ON ndd5.nat_dic_val_code = omd.PSN_TYPE\n" +
                "\tAND ndd5.DIC_TYPE_CODE = 'PSN_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd6 ON ndd6.nat_dic_val_code = omd.CVLSERV_FLAG\n" +
                "\tAND ndd6.DIC_TYPE_CODE = 'CVLSERV_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd7 ON ndd7.nat_dic_val_code = omd.CVLSERV_LV\n" +
                "\tAND ndd7.DIC_TYPE_CODE = 'CVLSERV_LV'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd8 ON ndd8.nat_dic_val_code = omd.RLOC_TYPE\n" +
                "\tAND ndd8.DIC_TYPE_CODE = 'RLOC_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd9 ON ndd9.nat_dic_val_code = omd.ARS_YEAR_IPT_FLAG\n" +
                "\tAND ndd9.DIC_TYPE_CODE = 'ARS_YEAR_IPT_FLAG'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd10 ON ndd10.nat_dic_val_code = omd.PRE_PAY_FLAG\n" +
                "\tAND ndd10.DIC_TYPE_CODE = 'PRE_PAY_FLAG'\n" +
                "\t\n" +
                "\twhere omd.VALI_FLAG='1'" ;
        if(!"".equals(outmedMdtrtVo.getCertno()) && null != outmedMdtrtVo.getCertno()){
            sql += " and omd.CERTNO = '"+outmedMdtrtVo.getCertno()+"'" ;
        }
        if(!"".equals(outmedMdtrtVo.getPsn_name()) && null != outmedMdtrtVo.getPsn_name()){
            sql += " and omd.PSN_NAME = '"+outmedMdtrtVo.getPsn_name()+"'" ;
        }
        if(!"".equals(outmedMdtrtVo.getInsutype()) && null != outmedMdtrtVo.getInsutype()){
            sql += " and omd.INSUTYPE = '"+outmedMdtrtVo.getInsutype()+"'" ;
        }

        if(!"".equals(outmedMdtrtVo.getFixmedins_code()) && null != outmedMdtrtVo.getFixmedins_code()){
            sql += " and omd.FIXMEDINS_CODE = '"+outmedMdtrtVo.getFixmedins_code()+"'" ;
        }

        if (null != outmedMdtrtVo.getTimes()) {
            sql += " and substr(TO_CHAR(omd.ENDTIME,'YYYY-MM-DD'),1,10) <= '" + outmedMdtrtVo.getTimes()[1] + "' ";
            sql += " and substr(TO_CHAR(omd.BEGNTIME,'YYYY-MM-DD'),1,10) >= '" + outmedMdtrtVo.getTimes()[0] + "' ";
        }
        return sql ;
    }

    /**
    *异地就医费用列表
    * Author wzn
    * Date 2022/6/17 16:13
    */
    public String outmedFeeList(Page page,OutmedFeeListVo outmedFeeListVo){
        String sql  = "SELECT\n" +
                "\tofl.CNT,\n" +
                "\tofl.PRIC,\n" +
                "\tofl.DET_ITEM_FEE_SUMAMT,\n" +
                "\tofl.PRIC_UPLMT_AMT,\n" +
                "\tofl.SELFPAY_PROP,\n" +
                "\tofl.FULAMT_OWNPAY_AMT,\n" +
                "\tofl.OVERLMT_SELFPAY,\n" +
                "\tofl.PRESELFPAY_AMT,\n" +
                "\tofl.INSCP_AMT,\n" +
                "\tofl.CVLSERV_BEDFEE_AMT,\n" +
                "\tofl.MEDINS_DISC_AMT,\n" +
                "\tofl.MEDINS_LIST_NAME,\n" +
                "\tofl.MEDINS_LIST_CODG,\n" +
                "\tofl.PRODNAME,\n" +
                "\tofl.SPEC,\n" +
                "\tofl.HILIST_NAME,\n" +
                "\tofl.HILIST_CODE\n" +
                "\t\n" +
                "FROM\n" +
                "\tSETLCENT_DB.OUTMED_FEE_LIST_D ofl \n" +
                "WHERE\n" +
                "\tofl.MDTRT_ID = '"+outmedFeeListVo.getMdtrt_id()+"'" ;
        return sql ;
    }

    /**
    *病理检查报告信息查询
    * Author wzn
    * Date 2022/6/20 15:23
    */
    public String reportInfo(Page page,RpotInfoVo rpotInfoVo){
        String sql = "SELECT\n" +
                "\tpib.PSN_NAME,\n" +
                "\tpib.CERTNO,\n" +
                "\tpib.GEND,\n" +
                "\tpib.RESD_LOC_ADMDVS,\n" +
                "\tpib.BRDY,\n" +
                "\tpib.PSN_CERT_TYPE,\n" +
                "\tpib.MOB,\n" +
                "\tpib.NATY,\n" +
                "\tpib.LIVE_ADDR,\n" +
                "\tperi.FIXMEDINS_CODE,\n" +
                "\tperi.CMA_DATE,\n" +
                "\tperi.RPT_DATE,\n" +
                "\tperi.CLNC_DIAG,\n" +
                "\tperi.EXAM_FND,\n" +
                "\tperi.PALG_DIAG,\n" +
                "\tperi.RPOT_DOC\n" +
                "\t\n" +
                "\t\n" +
                "FROM\n" +
                "\tSETLCENT_DB.PALG_EXAM_RPOT_INFO_D peri\n" +
                "\tLEFT JOIN BASINFOCENT_DB.PSN_INFO_B pib ON pib.PSN_NO = peri.PSN_NO\n" +
                "\twhere peri.VALI_FLAG='1'" ;
        if(!"".equals(rpotInfoVo.getCertno()) && null != rpotInfoVo.getCertno()){
            sql += " and pib.CERTNO = '"+rpotInfoVo.getCertno()+"'" ;
        }
        if(!"".equals(rpotInfoVo.getPsn_no()) && null != rpotInfoVo.getPsn_no()){
            sql += " and pib.PSN_NO = '"+rpotInfoVo.getPsn_no()+"'" ;
        }

        if(!"".equals(rpotInfoVo.getPsn_name()) && null != rpotInfoVo.getPsn_name()){
            sql += " and pib.PSN_NAME = '"+rpotInfoVo.getPsn_name()+"'" ;
        }
        if(!"".equals(rpotInfoVo.getResd_loc_admdvs()) && null != rpotInfoVo.getResd_loc_admdvs()){
            sql += " and pib.RESD_LOC_ADMDVS = '"+rpotInfoVo.getResd_loc_admdvs()+"'" ;
        }

        return sql ;
    }


    /**
    *结算信息查询
    * Author wzn
    * Date 2022/6/20 15:23
    */
    public String setlList(Page page,SetlDVo setlDVo){
        String sql = "SELECT\n" +
                "\tsd.PSN_NAME,\n" +
                "\tndd.nat_dic_val_NAME PSN_CERT_TYPE,\n" +
                "\tsd.CERTNO,\n" +
                "\tndd2.nat_dic_val_NAME GEND,\n" +
                "\tndd3.nat_dic_val_NAME NATY,\n" +
                "\tsd.BRDY,\n" +
                "\tsd.AGE,\n" +
                "\tndd4.nat_dic_val_NAME INSUTYPE,\n" +
                "\tndd5.nat_dic_val_NAME INSU_ADMDVS,\n" +
                "\tsd.FIXMEDINS_CODE,\n" +
                "\tsd.FIXMEDINS_NAME,\n" +
                "\tsd.SETL_TIME,\n" +
                "\tndd6.nat_dic_val_NAME MED_TYPE,\n" +
                "\tndd7.nat_dic_val_NAME SETL_TYPE,\n" +
                "\tsd.MEDFEE_SUMAMT,\n" +
                "\tsd.HIFP_PAY,\n" +
                "\tsd.CVLSERV_PAY,\n" +
                "\tsd.HIFES_PAY,\n" +
                "\tsd.HIFMI_PAY,\n" +
                "\tsd.HIFOB_PAY,\n" +
                "\tsd.MAF_PAY,\n" +
                "\tsd.OTHFUND_PAY,\n" +
                "\tsd.ACCT_PAY,\n" +
                "\tsd.CASH_PAYAMT,\n" +
                "\tsd.OWNPAY_HOSP_PART,\n" +
                "\tndd8.nat_dic_val_NAME POOLAREA_NO\n" +
                "FROM\n" +
                "\tSETLCENT_DB.SETL_D sd\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd ON ndd.nat_dic_val_code = sd.PSN_CERT_TYPE \n" +
                "\tAND ndd.DIC_TYPE_CODE = 'PSN_CERT_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd2 ON ndd2.nat_dic_val_code = sd.GEND \n" +
                "\tAND ndd2.DIC_TYPE_CODE = 'GEND'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd3 ON ndd3.nat_dic_val_code = sd.NATY \n" +
                "\tAND ndd3.DIC_TYPE_CODE = 'NATY'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd4 ON ndd4.nat_dic_val_code = sd.INSUTYPE \n" +
                "\tAND ndd4.DIC_TYPE_CODE = 'INSUTYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd5 ON ndd5.nat_dic_val_code = sd.INSU_ADMDVS \n" +
                "\tAND ndd5.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd6 ON ndd6.nat_dic_val_code = sd.MED_TYPE \n" +
                "\tAND ndd6.DIC_TYPE_CODE = 'MED_TYPE'\n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd7 ON ndd7.nat_dic_val_code = sd.SETL_TYPE \n" +
                "\tAND ndd7.DIC_TYPE_CODE = 'SETL_TYPE' \n" +
                "\tLEFT JOIN POLCENT_DB.NAT_DATA_DIC_A ndd8 ON ndd8.nat_dic_val_code = sd.POOLAREA_NO \n" +
                "\tAND ndd8.DIC_TYPE_CODE = 'ADMDVS'\n" +
                "WHERE\n" +
                "\tsd.VALI_FLAG = '1'" ;
        if(!"".equals(setlDVo.getCertno()) && null != setlDVo.getCertno()){
            sql += " and sd.CERTNO = '"+setlDVo.getCertno()+"'" ;
        }
        if(!"".equals(setlDVo.getFixmedins_code()) && null != setlDVo.getFixmedins_code()){
            sql += " and sd.FIXMEDINS_CODE = '"+setlDVo.getFixmedins_code()+"'" ;
        }
        return  sql ;
    }


    /**
     *定点医疗机构门急诊诊疗处方信息查询
     * Author wzn
     * Date 2022/6/20 15:23
     */
    public String rxInfoList(Page page,RxInfoVo rxInfoVo){
        String sql = "SELECT\n fot.*,sdd.IPT_OTP_NO,sdd.PSN_NAME " +

                "FROM\n" +
                "\tSETLCENT_DB.FIXMEDINS_OTP_TRT_RX_INFO_C fot  left join SETLCENT_DB.RX_D sdd on fot.RX_ID = sdd.RX_ID\n" +

                "WHERE\n" +
                "\tfot.VALI_FLAG = '1' and sdd.VALI_FLAG = '1'" ;
        if(!"".equals(rxInfoVo.getFixmedins_code()) && null != rxInfoVo.getFixmedins_code()){
            sql += " and fot.FIXMEDINS_CODE = '"+rxInfoVo.getFixmedins_code()+"'" ;
        }
        if(!"".equals(rxInfoVo.getRx_detl_name()) && null != rxInfoVo.getRx_detl_name()){
            sql += " and fot.RX_DETL_NAME like '%"+rxInfoVo.getRx_detl_name()+"%'" ;
        }
        if(!"".equals(rxInfoVo.getPsn_name()) && null != rxInfoVo.getPsn_name()){
            sql += " and sdd.PSN_NAME like '%"+rxInfoVo.getPsn_name()+"%'" ;
        }
        if(!"".equals(rxInfoVo.getRxno()) && null != rxInfoVo.getRxno()){
            sql += " and fot.RXNO = '"+rxInfoVo.getRxno()+"'" ;
        }
        if(!"".equals(rxInfoVo.getMdtrt_sn()) && null != rxInfoVo.getMdtrt_sn()){
            sql += " and fot.MDTRT_SN = '"+rxInfoVo.getMdtrt_sn()+"'" ;
        }
        if(!"".equals(rxInfoVo.getIpt_otp_no()) && null != rxInfoVo.getIpt_otp_no()){
            sql += " and sdd.IPT_OTP_NO = '"+rxInfoVo.getIpt_otp_no()+"'" ;
        }


        return  sql ;
    }


    /**
     *CLNC_EXAM_RPOT_IMG_INFO_D（临床检查报告影像信息表）
     * Author wzn
     * Date 2022/6/20 15:23
     */
    public String imgList(Page page,ImgInfoVo imgInfoVo){
        String sql = "SELECT\n cer.*,ce.PSN_NO " +

                "FROM\n" +
                "\tSETLCENT_DB.CLNC_EXAM_RPOT_IMG_INFO_D cer left join SETLCENT_DB.CLNC_EXAM_RPOT_D ce on ce.MDTRT_SN =cer.MDTRT_SN \n" +

                "WHERE\n" +
                "\tcer.VALI_FLAG = '1'" ;
        if(!"".equals(imgInfoVo.getFixmedins_code()) && null != imgInfoVo.getFixmedins_code()){
            sql += " and cer.FIXMEDINS_CODE = '"+imgInfoVo.getFixmedins_code()+"'" ;
        }
        if(!"".equals(imgInfoVo.getPsn_no()) && null != imgInfoVo.getPsn_no()){
            sql += " and ce.PSN_NO = '"+imgInfoVo.getPsn_no()+"'" ;
        }
        if(!"".equals(imgInfoVo.getPoolarea_no()) && null != imgInfoVo.getPoolarea_no()){
            sql += " and cer.POOLAREA_NO = '"+imgInfoVo.getPoolarea_no()+"'" ;
        }
        if(!"".equals(imgInfoVo.getPatn_name()) && null != imgInfoVo.getPatn_name()){
            sql += " and cer.PATN_NAME like '%"+imgInfoVo.getPatn_name()+"%'" ;
        }
        return  sql ;
    }
}


