package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CivilworkerInfoDao {

    public String syncCivilData(String type,String updateTime){
        String sql = "SELECT\n" +
                "\ta.PSN_NAME as name,\n" +
                "\ta.GEND as sex,\n" +
                "\ta.CERTNO as certno,\n" +
                "\ta.SURV_STAS as death_flag,\n" +
                "\tb.PSN_INSU_STAS as insu_state,\n" +
                "\tb.INSU_ADMDVS as insu_admdvs,\n" +
                "\tb.INSUTYPE as insutype,\n" +
                "\ta.RESD_LOC_ADMDVS as admdvs,\n" +
                "\tc.EMP_NAME as emp_name,\n" +
                "\tc.EMP_NO as emp_code,\n" +
                "\tc.EMP_TYPE as emp_type,\n" +
                "\tc.LOC_ADMDVS as emp_admdvs,\n" +
                "\tc.EMP_ADDR as emp_addr,\n" +
                "\td.OUT_FIL_UPLD_STAS as outside_flag\n" +
                "FROM\n" +
                "\tBASINFOCENT_DB.PSN_INFO_B a\n" +
                "\tINNER JOIN INSUCENT_DB.PSN_INSU_D b ON a.PSN_NO = b.PSN_NO \n" +
                "\tleft join CUSTCENT_DB.INSU_EMP_INFO_B c on c.EMP_NO = b.EMP_NO \n" +
                "\tleft join HIBIZ_DB.OUT_APPY_EVT_C d on d.PSN_NO = a.PSN_NO and RCHK_FLAG='1' and d.OUT_FIL_UPLD_STAS = '1' and (d.enddate is null or d.enddate > sysdate)\n" +
                "WHERE\n" +
                "\ta.VALI_FLAG = '1' \n" ;
        if("1".equals(type)){
            sql+="\tAND a.SURV_STAS = '1'\n";
            sql+="\tAND b.PSN_INSU_STAS = '1'\n";
        }
        if(StringUtils.isNotEmpty(updateTime)){
            sql+="\tAND b.UPDT_TIME >= to_date('"+updateTime+"','yyyy-mm-dd HH24:mi:ss')\n";
        }
        sql+=
                "\tAND b.INSUTYPE = '320'\n" +
//                "\tand (d.enddate is null or d.enddate > sysdate)\n" +
                "order by b.UPDT_TIME desc\n";
        return sql;
    }

    /**
     * 统计 体检占比 sql
     * @param year
     * @param deptNo
     * @return
     */
    public String getPhysicalRatio(String year,String deptNo) {
        String sql = "SELECT\n" +
                "\tperson.sched,\n" +
                "\tCOUNT( person.civilworker_id ) as num \n" +
                "FROM\n" +
                "\tcivilworker_info cinfo\n" +
                "\tJOIN person_subscribe_record person ON cinfo.id = person.civilworker_id \n" +
                "WHERE\n 1=1 ";
        if (!"".equals(deptNo)) {
            sql = sql + "\t and cinfo.emp_code = '" + deptNo + "' \n";
        }
        if (!"".equals(year)) {
            sql = sql + "\t and person.year = '" + year + "' \n" ;
        }
        sql = sql + " GROUP BY person.sched ";
        return sql;
    }

    public String civiList(Page page, CivilworkerInfo civilworkerInfo, SysUser sysUser){
        String sql  = "SELECT\n" +
                "\t\t\t\t\tci.ID,\n" +
                "\t\t\t\t\tci.NAME,\n" +
                "\t\t\t\t\tci.certno,\n" +
                "\t\t\t\t\tci.cardType,\n" +
                "\t\t\t\t\tci.telephone,\n" +
                "\t\t\t\t\tci.insu_admdvs,\n" +
                "\t\t\t\t\tci.admdvs,\n" +
                "\t\t\t\t\tci.birthday,\n" +
                "\t\t\t\t\t\t\t\tFLOOR( MONTHS_BETWEEN( SYSDATE, TO_DATE(ci.birthday, 'YYYY-MM-DD') ) / 12 ) AS age,\n" +
                "\t\t\t\t\tci.emp_id,\n" +
                "\t\t\t\t\tci.dept_id,\n" +
                "\t\t\t\t\tdi.dept_name,\n" +
                "\t\t\t\t\tci.emp_name,\n" +
                "\t\t\t\t\tci.emp_code,\n" +
                "\t\t\t\t\tci.emp_type,\n" +
                "\t\t\t\t\tci.emp_admdvs,\n"+
                "\t\t\t\t\tci.emp_addr,\n" +
                "\t\t\t\t\tci.insutype,\n" +
                "\t\t\t\t\tci.insu_state,\n" +
                "\t\t\t\t\tci.outside_flag,\n" +
                "\t\t\t\t\tci.outside_put,\n" +
                "\t\t\t\t\tci.death_flag,\n" +
                "\t\t\t\t\tci.qualifications,\n" +
                "\t\t\t\t\tci.info_source,\n" +
                "\t\t\t\t\tci.createUser,\n" +
                "\t\t\t\t\tci.createTime,\n" +
                "\t\t\t\t\tci.updateUser,\n" +
                "\t\t\t\t\tci.updateTime,\n" +
                "\t\t\t\t\tci.is_del,\n" +
                "\t\t\t\t\tci.login_Name,\n" +
                "\t\t\t\t\tci.pwd,\n" +
                "\t\t\t\t\tci.open_id,\n" +
                " ei.emp_no,\n" +
                " ei.parentOrgCode,\n" +
                "\t\t\t\t\tci.administrative_unit\n" +
                "\t\t\t\tFROM\n" +
                "\t\t\t\t\tcivilworker_info ci\n" +
                "\t\t\t\tleft join EMPLOYING_INFO ei on ci.emp_code = ei.emp_no\n" +
                "\t\t\t\tleft join dept_info di on di.id = ci.dept_id\n" +
                "\t\t\t\tWHERE\n 1=1 and ci.is_del = 0 " ;
        if (!"".equals(civilworkerInfo.getName()) && null != civilworkerInfo.getName()) {
            sql += " and ci.name like '%"+civilworkerInfo.getName()+"%'" ;
        }
        if (!"".equals(civilworkerInfo.getEmp_name()) && null != civilworkerInfo.getEmp_name()) {
            sql += " and ci.emp_name like '%"+civilworkerInfo.getEmp_name()+"%'" ;
        }
        if (!"".equals(civilworkerInfo.getCertno()) && null != civilworkerInfo.getCertno()) {
            sql += " and ci.certno = '"+civilworkerInfo.getCertno()+"'" ;
        }
        if (!"".equals(civilworkerInfo.getCardType()) && null != civilworkerInfo.getCardType()) {
            sql += " and ci.cardType = '"+civilworkerInfo.getCardType()+"'" ;
        }
        if (!"".equals(civilworkerInfo.getEmp_code()) && null != civilworkerInfo.getEmp_code()) {
            sql += " and ci.emp_code = '"+civilworkerInfo.getEmp_code()+"'" ;
        }
        if (!"".equals(civilworkerInfo.getDept_id()) && null != civilworkerInfo.getDept_id()) {
            sql += " and ci.dept_id = '"+civilworkerInfo.getDept_id()+"'" ;
        }
        if (!"".equals(civilworkerInfo.getInsu_admdvs()) && null != civilworkerInfo.getInsu_admdvs()) {
            sql += " and ci.insu_admdvs = '"+civilworkerInfo.getInsu_admdvs()+"'" ;
        }
        if ("4".equals(sysUser.getUser_type())) {//用人单位
            sql += " and ci.emp_code = '"+sysUser.getOrg_code()+"'" ;
        }

        else if("1".equals(sysUser.getUser_type()) && !"admin".equals(sysUser.getUsername()) && !org.springframework.util.StringUtils.hasText(civilworkerInfo.getInsu_admdvs())){//行政单位  只能看下属公务员
            sql += " and ei.parentOrgCode ='"+sysUser.getOrg_code()+"'" ;
        }
        sql += " ORDER BY ci.createTime DESC" ;

        return sql ;
    }
}
