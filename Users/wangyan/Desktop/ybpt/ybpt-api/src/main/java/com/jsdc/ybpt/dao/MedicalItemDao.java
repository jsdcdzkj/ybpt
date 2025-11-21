package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.MedicalItem;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.vo.MedicalItemVo;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalItemDao {
    public String getMedicalItemListOfYbByPackYear(String medicalItemYear) {
        String sql = "select mi.id\n" +
                "from MEDICAL_ITEM mi\n" +
                "where mi.IS_GENERIC = '1'\n" +
                "and mi.IS_DEL = '0'\n" +
                "and mi.YEAR = '" + medicalItemYear + "'";
        return sql;
    }

    public String getListUnion(SysUser sysUser, MedicalItemVo vo, OrganizationInfo organizationInfo){
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT * FROM MEDICAL_ITEM ");
        sql.append("WHERE IS_DEL = '0' AND ITEM_STATE = '1' ");
        sql.append(" AND (IS_GENERIC<>'1' OR IS_GENERIC IS NULL) ");
        if (sysUser.getUser_type().equals("1")) {
            sql.append(" and ORG_ID = '"+sysUser.getOrg_code()+"'");
        }
        //体检机构 可以看到所属行政管理单位的数据
        if (sysUser.getUser_type().equals("5")) {
            sql.append(" and ORG_ID in ('"+sysUser.getOrg_code()+"')");
        }
        sql.append("UNION ");
        sql.append("SELECT * FROM MEDICAL_ITEM ");
        sql.append("WHERE IS_DEL = '0' AND ITEM_STATE = '1' ");
        if (sysUser.getUser_type().equals("1")) {
            sql.append(" and ORG_ID = '"+sysUser.getOrg_code()+"'");
        }
        //体检机构 可以看到所属行政管理单位的数据
        if (sysUser.getUser_type().equals("5")) {
            sql.append(" and ORG_ID in ('"+sysUser.getOrg_code()+"','"+organizationInfo.getOrg_code()+"')");
        }
        sql.append(" AND IS_GENERIC='1' ");
        sql.append(" and year='"+vo.getYear()+"'");


        return sql.toString();
    }
}
