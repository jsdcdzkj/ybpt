package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerVirfy;
import org.springframework.stereotype.Repository;

@Repository
public class CivilworkerVirfyDao {

    public String civilworkerVirfyList(Page page, CivilworkerVirfy civilworkerVirfy, SysUser sysUser){
        civilworkerVirfy.setParentorgcode(sysUser.getOrg_code());
        String sql = "SELECT\n" +
                "\tcv.id,\n" +
                "\tcv.admdvs,\n" +
                "\tcv.apply_id,\n" +
                "\tcv.audit_id,\n" +
                "\tcv.certno,\n" +
                "\tcv.createtime,\n" +
                "\tcv.createuser,\n" +
                "\tcv.cw_id,\n" +
                "\tcv.death_flag,\n" +
                "\tcv.emp_code,\n" +
                "\tcv.emp_id,\n" +
                "\tcv.emp_name,\n" +
                "\tcv.emp_type,\n" +
                "\tcv.flag,\n" +
                "\tcv.insu_admdvs,\n" +
                "\tcv.insu_state,\n" +
                "\tcv.insutype,\n" +
                "\tcv.IS_DEL,\n" +
                "\tcv.name,\n" +
                "\tcv.outside_flag,\n" +
                "\tcv.outside_put,\n" +
                "\tcv.REASON,\n" +
                "\tcv.status,\n" +
                "\tcv.updatetime,\n" +
                "\tcv.updateuser,\n" +
                "\tcv.verify_type,\n" +
                "\tcv.sex,\n" +
                "\tcv.verify_time,\n" +
                "\tcv.info_source,\n" +
                "\tcv.qualifications,\n" +
                "\tcv.parentcode,\n" +
                "\tcv.telephone,\n" +
                "\tcv.dept_id,\n" +
                "\tcv.dept_name,\n" +
                "\tcv.birthday,\n" +
                "\tcv.CARDTYPE," +
                "FLOOR( MONTHS_BETWEEN( SYSDATE, TO_DATE(cv.birthday, 'YYYY-MM-DD') ) / 12 ) AS age, ei.PARENTORGCODE,di.dept_name AS dname\n" +
                "FROM\n" +
                "\tCIVILWORKER_VIRFY cv\n" +
                "LEFT JOIN EMPLOYING_INFO ei ON ei.EMP_NO = cv .EMP_CODE\n" +
                "LEFT JOIN dept_info di ON di.id = cv .dept_id\n" +
                "WHERE 1=1 " ;
        if("1".equals(sysUser.getUser_type())){
            sql +=  " and ei.PARENTORGCODE = '"+civilworkerVirfy.getParentorgcode()+"'" ;
        }else if("4".equals(sysUser.getUser_type())){
            sql +=  " and cv.apply_id = '"+sysUser.getOrg_code()+"'" ;
        }

        if(!"".equals(civilworkerVirfy.getEmp_name()) && null != civilworkerVirfy.getEmp_name()){
            sql +=" and cv.emp_name like '%"+civilworkerVirfy.getEmp_name()+"%'" ;
        }
        if(!"".equals(civilworkerVirfy.getEmp_code()) && null != civilworkerVirfy.getEmp_code()){
            sql +=" and cv.emp_code like '%"+civilworkerVirfy.getEmp_code()+"%'" ;
        }
        if (!"".equals(civilworkerVirfy.getCardType()) && null != civilworkerVirfy.getCardType()) {
            sql += " and cv.cardType = '"+civilworkerVirfy.getCardType()+"'" ;
        }
        if(!"".equals(civilworkerVirfy.getName()) && null != civilworkerVirfy.getName()){
            sql +=" and cv.name = '"+civilworkerVirfy.getName()+"'" ;
        }
        if(!"".equals(civilworkerVirfy.getCertno()) && null != civilworkerVirfy.getCertno()){
            sql +=" and cv.certno = '"+civilworkerVirfy.getCertno()+"'" ;
        }
        if(!"".equals(civilworkerVirfy.getStatus()) && null != civilworkerVirfy.getStatus()){
            sql +=" and cv.status = '"+civilworkerVirfy.getStatus()+"'" ;
        }
        sql +=" order by cv.createTime desc" ;
        return sql;
    }
}
