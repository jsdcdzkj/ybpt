package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Repository
public class MedinsInfoBDao {

    public String selectMedinsInfo(Page page,String medins_code,String medins_name){
        String sql ="select FIXMEDINS_CODE medins_code,USCC uscc,FIXMEDINS_NAME medins_name,HOSP_LV medinslv,fixmedins_type from POLCENT_DB.fixmedins_b"+
                " WHERE\n1=1 and VALI_FLAG ='1' " ;
        if(StringUtils.isNotEmpty(medins_code)){
            sql += " and FIXMEDINS_CODE = '"+medins_code+"'";
        }
        if(StringUtils.isNotEmpty(medins_name)){
            sql += " and FIXMEDINS_NAME LIKE '%"+medins_name+"%'" ;
        }
        return sql;
    }


    public String selectFdd(Page page,String medins_code,String medins_name,String fixmedins_type){
        String sql ="select FIXMEDINS_CODE medins_code,FIXMEDINS_NAME medins_name  from UNFIXEDMECHANISM"+
                " WHERE\nis_del='0' " ;
        if(StringUtils.isNotEmpty(medins_code)){
            sql += " and FIXMEDINS_CODE = '"+medins_code+"'";
        }
        if(StringUtils.isNotEmpty(medins_name)){
            sql += " and FIXMEDINS_NAME LIKE '%"+medins_name+"%'" ;
        }
        if(StringUtils.isNotEmpty(fixmedins_type)){
            sql += " and fixmedins_type = '"+fixmedins_type+"'" ;
        }
        return sql;
    }



    //机构和药店
    public String selectMedinsInfoType(Page page,String medins_code,String medins_name,String fixmedins_type){
        String sql ="select FIXMEDINS_CODE medins_code,USCC uscc,FIXMEDINS_NAME medins_name,HOSP_LV medinslv,fixmedins_type from FIXMEDINS_B"+
                " WHERE\nis_del='0' " ;
        if(StringUtils.isNotEmpty(medins_code)){
            sql += " and FIXMEDINS_CODE = '"+medins_code+"'";
        }
        if(StringUtils.isNotEmpty(medins_name)){
            sql += " and FIXMEDINS_NAME LIKE '%"+medins_name+"%'" ;
        }
        if(StringUtils.isNotEmpty(fixmedins_type)){
            sql += " and fixmedins_type = '"+fixmedins_type+"'" ;
        }
        return sql;
    }

    //用人单位
    public String selectEmpList(Page page,String medins_code,String medins_name){
        String sql = "select emp_name medins_name,emp_no medins_code,emp_type fixmedins_type from employing_info where is_del='0'";
        if(StringUtils.isNotEmpty(medins_code)){
            sql += "and emp_no = '"+medins_code+"'";
        }
        if(StringUtils.isNotEmpty(medins_name)){
            sql += "and emp_name like '%"+medins_name+"%'";
        }
        return sql;
    }
    //行政管理单位
    public String selectAdminUnitList(Page page,String medins_code,String medins_name){
        String sql = "select emp_name medins_name,emp_no medins_code from administrative_unit where is_del='0'";
        if(StringUtils.isNotEmpty(medins_code)){
            sql += "and emp_no = '"+medins_code+"'";
        }
        if(StringUtils.isNotEmpty(medins_name)){
            sql += "and emp_name like '%"+medins_name+"%'";
        }
        return sql;
    }
    //体检中心
    public String selectOrgList(Page page,String medins_code,String medins_name){
        String sql = "select org_name medins_name,medical_insurance_num medins_code from organization_info where is_del='0'";
        if(StringUtils.isNotEmpty(medins_code)){
            sql += "and medical_insurance_num = '"+medins_code+"'";
        }
        if(StringUtils.isNotEmpty(medins_name)){
            sql += "and org_name like '%"+medins_name+"%'";
        }
        return sql;
    }

    //银行
    public String selectBankList(Page page,String medins_code,String medins_name){
            String sql = "select bankName medins_name ,bankNo medins_code from bank where is_del='0'";
        if(StringUtils.isNotEmpty(medins_code)){
            sql += "and bankNo = '"+medins_code+"'";
        }
        if(StringUtils.isNotEmpty(medins_name)){
            sql += "and bankName like '%"+medins_name+"%'";
        }
        return sql;
    }
}
