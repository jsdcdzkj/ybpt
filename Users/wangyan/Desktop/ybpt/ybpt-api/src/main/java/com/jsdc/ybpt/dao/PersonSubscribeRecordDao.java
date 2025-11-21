package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.PersonSubscribeRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class PersonSubscribeRecordDao {
    private static final Logger log = LoggerFactory.getLogger(PersonSubscribeRecordDao.class);
    public String getCount(String orgCode) {
        String sql = "SELECT count( 1 ) \n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD \n" +
                "WHERE\n" +
                "\tIS_DEL = '0' and\n" +
                "\tsched IN ( '1', '3' )";
        if (!"".equals(orgCode)) {
            sql += " and org_id ='" + orgCode + "'";
        }
        return sql;
    }

    /**
     * 预约人数
     * Author wzn
     * Date 2022/6/9 16:13
     */
    public String reservationCount(String orgCode, String type) {
        String sql = "SELECT count( 1 ) \n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD \n" +
                "WHERE\n" +
                "\tsched IN ( " + type + " )";
        if (!"".equals(orgCode)) {
            sql += " and org_id ='" + orgCode + "'";
        }
        return sql;
    }

    public String settlementMore(PersonSubscribeRecordVo personSubscribeRecordVo){
        String sql = "SELECT\n" +
                "\tpsr.id,\n" +
                "\tpsr.YEAR,\n" +
                "\tpsr.APPLY_DATE,\n" +
                "\tpsr.SCHED,\n" +
                "\tpsr.CHECKUP_TIME,\n" +
                "\tpsr.UPLOAD_TIME,\n" +
                "\tpsr.PHONE,\n" +
                "\tpsr.CHARGEPHONE,\n" +
                "\tpsr.ISSETTLEMENT,\n" +
                "\tpi.PACK_NAME,\n" +
                "\tci.name,\n" +
                "\tci.certno,\n" +
                "\tci.dept_id,\n" +
                "\tdi.dept_name,\n" +
                "\tci.emp_name,\n" +
                "\toi.org_name, \n" +
                "\toi.medical_insurance_num \n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD psr\n" +
                "\tLEFT JOIN emp_subscribe_record esr ON psr.EMP_SUB_ID = esr.ID\n" +
                "\tLEFT JOIN CIVILWORKER_INFO ci ON psr.civilworker_id = ci.certno\n" +
                " left join EMPLOYING_INFO ei on ci.emp_code = ei.emp_no " +
                "\tLEFT JOIN PACK_INFO pi ON esr.pack_id = pi.id\n" +
                "\tLEFT JOIN ORGANIZATION_INFO oi ON psr.ORG_ID = oi.MEDICAL_INSURANCE_NUM \n" +
                "\tLEFT JOIN DEPT_INFO di ON di.id=ci.DEPT_ID\n" +
                "WHERE\n 1=1 and psr.is_del = '0' and oi.is_del ='0' AND ci.IS_DEL='0' and psr.SCHED ='3' ";
        if (!"".equals(personSubscribeRecordVo.getName()) && null != personSubscribeRecordVo.getName()) {
            sql += " and ci.name LIKE '%" + personSubscribeRecordVo.getName() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getOrg_name()) && null != personSubscribeRecordVo.getOrg_name()) {
            sql += " and oi.org_name LIKE '%" + personSubscribeRecordVo.getOrg_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getEmp_name()) && null != personSubscribeRecordVo.getEmp_name()) {
            sql += " and ci.emp_name LIKE '%" + personSubscribeRecordVo.getEmp_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getYear()) && null != personSubscribeRecordVo.getYear()) {
            sql += " and psr.YEAR = '" + personSubscribeRecordVo.getYear() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getDept_no()) && null != personSubscribeRecordVo.getDept_no()) {
            sql += " and ci.dept_id = '" + personSubscribeRecordVo.getDept_no() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getPack_name()) && null != personSubscribeRecordVo.getPack_name()) {
            sql += " and pi.PACK_NAME LIKE '%" + personSubscribeRecordVo.getPack_name() + "%' ";
        }
//        if (!"".equals(personSubscribeRecordVo.getSched()) && null != personSubscribeRecordVo.getSched()) {
//            sql += " and psr.SCHED = '" + personSubscribeRecordVo.getSched() + "' ";
//        }
        if (!"".equals(personSubscribeRecordVo.getApply_date()) && null != personSubscribeRecordVo.getApply_date()) {
            sql += " and psr.APPLY_DATE = '" + personSubscribeRecordVo.getApply_date() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getBegin_upload_time()) && null != personSubscribeRecordVo.getBegin_upload_time()) {
            sql += " and psr.upload_time >= '" + personSubscribeRecordVo.getBegin_upload_time() + "' ";
        }
        if ("0".equals(personSubscribeRecordVo.getSched())) {//待体检查预约时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and psr.APPLY_DATE <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and psr.APPLY_DATE >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("1".equals(personSubscribeRecordVo.getSched())) {//已体检查体检时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.CHECKUP_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.CHECKUP_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("3".equals(personSubscribeRecordVo.getSched())) {//已上传报告
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.UPLOAD_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.UPLOAD_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if (!"".equals(personSubscribeRecordVo.getCertno()) && null != personSubscribeRecordVo.getCertno()) {
            sql += " and ci.certno = '" + personSubscribeRecordVo.getCertno() + "' ";
        }
//        if (!"".equals(personSubscribeRecordVo.getOrg_id()) && null != personSubscribeRecordVo.getOrg_id()) {
//            sql += " and psr.org_id = '" + personSubscribeRecordVo.getOrg_id() + "' ";
//        }

        if ("1".equals(personSubscribeRecordVo.getSysUser().getUser_type()) && !"admin".equals(personSubscribeRecordVo.getSysUser().getUsername())) {//行政单位  只能看下属公务员
            sql += " and ei.parentOrgCode ='" + personSubscribeRecordVo.getSysUser().getOrg_code() + "'";
        }else if ("5".equals(personSubscribeRecordVo.getSysUser().getUser_type())) {
            sql += " and oi.medical_insurance_num = '"+personSubscribeRecordVo.getOrg_code()+"'" ;
        }else  if("4".equals(personSubscribeRecordVo.getSysUser().getUser_type())){ //用人单位 只能查本单位的公务员预约记录
            sql += " and ci.emp_code = '" + personSubscribeRecordVo.getEmp_code() + "' ";
        }
        sql += " order by psr.createTime desc";
        return sql;
    }
    public String personSelectList(Page page, PersonSubscribeRecordVo personSubscribeRecordVo) {
        String sql = "SELECT\n" +
                "\tpsr.id,\n" +
                "\tpsr.YEAR,\n" +
                "\tpsr.APPLY_DATE,\n" +
                "\tpsr.SCHED,\n" +
                "\tpsr.CHECKUP_TIME,\n" +
                "\tpsr.UPLOAD_TIME,\n" +
                "\tpsr.PHONE,\n" +
                "\tpsr.cardType,\n" +
                "\tpsr.CHARGEPHONE,\n" +
                "\tpsr.ISSETTLEMENT,\n" +
                "\tpsr.settlementTime,\n" +
                "\tpi.PACK_NAME,\n" +
                "\tci.name,\n" +
                "\tci.certno,\n" +
                "\tci.dept_id,\n" +
                "\tdi.dept_name,\n" +
                "\tci.emp_name,\n" +
                "\toi.org_name, \n" +
                "\tci.is_del as civil_is_del, \n" +
                "\toi.medical_insurance_num \n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD psr\n" +
                "\tLEFT JOIN emp_subscribe_record esr ON psr.EMP_SUB_ID = esr.ID\n" +
                "\tLEFT JOIN CIVILWORKER_INFO ci ON psr.civilworker_id = ci.certno and ci.is_del=0\n" +
                " left join EMPLOYING_INFO ei on ci.emp_code = ei.emp_no " +
                "\tLEFT JOIN PACK_INFO pi ON esr.pack_id = pi.id\n" +
                "\tLEFT JOIN ORGANIZATION_INFO oi ON psr.ORG_ID = oi.MEDICAL_INSURANCE_NUM \n" +
                "\tLEFT JOIN DEPT_INFO di ON di.id=ci.DEPT_ID\n" +
                "WHERE\n 1=1 and psr.is_del = '0' and oi.is_del ='0'";
        if (!"".equals(personSubscribeRecordVo.getName()) && null != personSubscribeRecordVo.getName()) {
            sql += " and ci.name LIKE '%" + personSubscribeRecordVo.getName() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getOrg_name()) && null != personSubscribeRecordVo.getOrg_name()) {
            sql += " and oi.org_name LIKE '%" + personSubscribeRecordVo.getOrg_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getEmp_name()) && null != personSubscribeRecordVo.getEmp_name()) {
            sql += " and ci.emp_name LIKE '%" + personSubscribeRecordVo.getEmp_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getYear()) && null != personSubscribeRecordVo.getYear()) {
            sql += " and psr.YEAR = '" + personSubscribeRecordVo.getYear() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getDept_no()) && null != personSubscribeRecordVo.getDept_no()) {
            sql += " and ci.dept_id = '" + personSubscribeRecordVo.getDept_no() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getPack_name()) && null != personSubscribeRecordVo.getPack_name()) {
            sql += " and pi.PACK_NAME LIKE '%" + personSubscribeRecordVo.getPack_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getSched()) && null != personSubscribeRecordVo.getSched()) {
            sql += " and psr.SCHED = '" + personSubscribeRecordVo.getSched() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getApply_date()) && null != personSubscribeRecordVo.getApply_date()) {
            sql += " and psr.APPLY_DATE = '" + personSubscribeRecordVo.getApply_date() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getBegin_upload_time()) && null != personSubscribeRecordVo.getBegin_upload_time()) {
            sql += " and psr.upload_time >= '" + personSubscribeRecordVo.getBegin_upload_time() + "' ";
        }
        if ("0".equals(personSubscribeRecordVo.getSched())) {//待体检查预约时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and psr.APPLY_DATE <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and psr.APPLY_DATE >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("1".equals(personSubscribeRecordVo.getSched())) {//已体检查体检时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.CHECKUP_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.CHECKUP_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("3".equals(personSubscribeRecordVo.getSched())) {//已上传报告
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.UPLOAD_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.UPLOAD_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if (!"".equals(personSubscribeRecordVo.getCertno()) && null != personSubscribeRecordVo.getCertno()) {
            sql += " and ci.certno = '" + personSubscribeRecordVo.getCertno() + "' ";
        }

        if (null != personSubscribeRecordVo.getIsSettlement() && !"".equals(personSubscribeRecordVo.getIsSettlement()) ) {
            sql += " and psr.isSettlement = '" + personSubscribeRecordVo.getIsSettlement() + "' ";
        }

//        if (!"".equals(personSubscribeRecordVo.getOrg_id()) && null != personSubscribeRecordVo.getOrg_id()) {
//            sql += " and psr.org_id = '" + personSubscribeRecordVo.getOrg_id() + "' ";
//        }
        if (!"".equals(personSubscribeRecordVo.getCardType()) && null != personSubscribeRecordVo.getCardType()) {
            sql += " and psr.cardType = '" + personSubscribeRecordVo.getCardType() + "' ";
        }

        if ("1".equals(personSubscribeRecordVo.getSysUser().getUser_type()) && !"admin".equals(personSubscribeRecordVo.getSysUser().getUsername())) {//行政单位  只能看下属公务员
            sql += " and ei.parentOrgCode ='" + personSubscribeRecordVo.getSysUser().getOrg_code() + "'";
        }else if ("5".equals(personSubscribeRecordVo.getSysUser().getUser_type())) {
            sql += " and oi.medical_insurance_num = '"+personSubscribeRecordVo.getOrg_code()+"'" ;
        }else  if("4".equals(personSubscribeRecordVo.getSysUser().getUser_type())){ //用人单位 只能查本单位的公务员预约记录
            sql += " and ci.emp_code = '" + personSubscribeRecordVo.getEmp_code() + "' ";
        }
        sql += " order by psr.createTime,psr.ID desc";

        log.info("personSelectList====" + sql.toString());
        return sql;
    }

    public String personSelectListAll(PersonSubscribeRecordVo personSubscribeRecordVo) {
        String sql = "SELECT\n" +
                "\tpsr.id,\n" +
                "\tpsr.YEAR,\n" +
                "\tpsr.APPLY_DATE,\n" +
                "\tpsr.SCHED,\n" +
                "\tpsr.CHECKUP_TIME,\n" +
                "\tpsr.UPLOAD_TIME,\n" +
                "\tpi.PACK_NAME,\n" +
                "\tci.name,\n" +
                "\tci.certno,\n" +
                "\tci.emp_name,\n" +
                "\toi.org_name, \n" +
                "\toi.medical_insurance_num \n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD psr\n" +
                "\tLEFT JOIN emp_subscribe_record esr ON psr.EMP_SUB_ID = esr.ID\n" +
                "\tLEFT JOIN CIVILWORKER_INFO ci ON psr.civilworker_id = ci.certno\n" +
                " left join EMPLOYING_INFO ei on ci.emp_code = ei.emp_no " +
                "\tLEFT JOIN PACK_INFO pi ON esr.pack_id = pi.id\n" +
                "\tLEFT JOIN ORGANIZATION_INFO oi ON psr.ORG_ID = oi.MEDICAL_INSURANCE_NUM \n" +
                "WHERE\n 1=1 and psr.is_del = '0' and oi.is_del ='0'";
        if (!"".equals(personSubscribeRecordVo.getName()) && null != personSubscribeRecordVo.getName()) {
            sql += " and ci.name LIKE '%" + personSubscribeRecordVo.getName() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getOrg_name()) && null != personSubscribeRecordVo.getOrg_name()) {
            sql += " and oi.org_name LIKE '%" + personSubscribeRecordVo.getOrg_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getEmp_name()) && null != personSubscribeRecordVo.getEmp_name()) {
            sql += " and ci.emp_name LIKE '%" + personSubscribeRecordVo.getEmp_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getYear()) && null != personSubscribeRecordVo.getYear()) {
            sql += " and psr.YEAR = '" + personSubscribeRecordVo.getYear() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getPack_name()) && null != personSubscribeRecordVo.getPack_name()) {
            sql += " and pi.PACK_NAME LIKE '%" + personSubscribeRecordVo.getPack_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getSched()) && null != personSubscribeRecordVo.getSched()) {
            sql += " and psr.SCHED = '" + personSubscribeRecordVo.getSched() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getApply_date()) && null != personSubscribeRecordVo.getApply_date()) {
            sql += " and psr.APPLY_DATE = '" + personSubscribeRecordVo.getApply_date() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getBegin_upload_time()) && null != personSubscribeRecordVo.getBegin_upload_time()) {
            sql += " and psr.upload_time >= '" + personSubscribeRecordVo.getBegin_upload_time() + "' ";
        }
        if ("0".equals(personSubscribeRecordVo.getSched())) {//待体检查预约时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and psr.APPLY_DATE <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and psr.APPLY_DATE >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("1".equals(personSubscribeRecordVo.getSched())) {//已体检查体检时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.CHECKUP_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.CHECKUP_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("3".equals(personSubscribeRecordVo.getSched())) {//已上传报告
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.UPLOAD_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.UPLOAD_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if (!"".equals(personSubscribeRecordVo.getCertno()) && null != personSubscribeRecordVo.getCertno()) {
            sql += " and ci.certno = '" + personSubscribeRecordVo.getCertno() + "' ";
        }
//        if (!"".equals(personSubscribeRecordVo.getOrg_id()) && null != personSubscribeRecordVo.getOrg_id()) {
//            sql += " and psr.org_id = '" + personSubscribeRecordVo.getOrg_id() + "' ";
//        }

        if ("1".equals(personSubscribeRecordVo.getSysUser().getUser_type()) && !"admin".equals(personSubscribeRecordVo.getSysUser().getUsername())) {//行政单位  只能看下属公务员
            sql += " and ei.parentOrgCode ='" + personSubscribeRecordVo.getSysUser().getOrg_code() + "'";
        }else if ("5".equals(personSubscribeRecordVo.getSysUser().getUser_type())) {
            sql += " and oi.medical_insurance_num = '"+personSubscribeRecordVo.getOrg_code()+"'" ;
        }else  if("4".equals(personSubscribeRecordVo.getSysUser().getUser_type())){ //用人单位 只能查本单位的公务员预约记录
            sql += " and ci.emp_code = '" + personSubscribeRecordVo.getEmp_code() + "' ";
        }
        sql += " order by psr.createTime desc";

        log.info("personSelectListAll==" + sql.toString());
        return sql;
    }

    public String yyExport(PersonSubscribeRecordVo personSubscribeRecordVo) {
        String sql = "SELECT\n" +
                "\tpsr.id,\n" +
                "\tpsr.YEAR,\n" +
                "\tpsr.APPLY_DATE,\n" +
                "\tpsr.SCHED,\n" +
                "\tpsr.CHECKUP_TIME,\n" +
                "\tpsr.phone,\n" +
                "\tpsr.UPLOAD_TIME,\n" +
                "\tpsr.ISSETTLEMENT,\n" +
                "\tpsr.SETTLEMENTTIME,\n" +
                "\tpi.PACK_NAME,\n" +
                "\tci.name,\n" +
                "\tci.certno,\n" +
                "\tci.emp_name,\n" +
                "\toi.org_name, \n" +
                "\toi.medical_insurance_num \n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD psr\n" +
                "\tLEFT JOIN emp_subscribe_record esr ON psr.EMP_SUB_ID = esr.ID\n" +
                "\tLEFT JOIN CIVILWORKER_INFO ci ON psr.civilworker_id = ci.certno\n" +
                " left join EMPLOYING_INFO ei on ci.emp_code = ei.emp_no " +
                "\tLEFT JOIN PACK_INFO pi ON esr.pack_id = pi.id\n" +
                "\tLEFT JOIN ORGANIZATION_INFO oi ON psr.ORG_ID = oi.MEDICAL_INSURANCE_NUM \n" +
                "WHERE\n ci.is_del = '0' and psr.is_del = '0' and oi.is_del ='0'"; //离世的人务员体检记录也要展示
//                "WHERE\n psr.is_del = '0' and oi.is_del ='0'";
        if (null != personSubscribeRecordVo.getIsSettlement() && !"".equals(personSubscribeRecordVo.getIsSettlement()) ) {
            sql += " and psr.isSettlement = '" + personSubscribeRecordVo.getIsSettlement() + "' ";
        }

        if (!"".equals(personSubscribeRecordVo.getName()) && null != personSubscribeRecordVo.getName()) {
            sql += " and ci.name LIKE '%" + personSubscribeRecordVo.getName() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getEmp_name()) && null != personSubscribeRecordVo.getEmp_name()) {
            sql += " and ci.emp_name LIKE '%" + personSubscribeRecordVo.getEmp_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getYear()) && null != personSubscribeRecordVo.getYear()) {
            sql += " and psr.YEAR = '" + personSubscribeRecordVo.getYear() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getPack_name()) && null != personSubscribeRecordVo.getPack_name()) {
            sql += " and pi.PACK_NAME LIKE '%" + personSubscribeRecordVo.getPack_name() + "%' ";
        }
        if (!"".equals(personSubscribeRecordVo.getSched()) && null != personSubscribeRecordVo.getSched()) {
            sql += " and psr.SCHED = '" + personSubscribeRecordVo.getSched() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getApply_date()) && null != personSubscribeRecordVo.getApply_date()) {
            sql += " and psr.APPLY_DATE = '" + personSubscribeRecordVo.getApply_date() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getBegin_upload_time()) && null != personSubscribeRecordVo.getBegin_upload_time()) {
            sql += " and psr.upload_time >= '" + personSubscribeRecordVo.getBegin_upload_time() + "' ";
        }
        if ("0".equals(personSubscribeRecordVo.getSched())) {//待体检查预约时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and psr.APPLY_DATE <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and psr.APPLY_DATE >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("1".equals(personSubscribeRecordVo.getSched())) {//已体检查体检时间
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.CHECKUP_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.CHECKUP_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
        if ("3".equals(personSubscribeRecordVo.getSched())) {//已上传报告
            if (null != personSubscribeRecordVo.getTimes()) {
                sql += " and substr(psr.UPLOAD_TIME,1,10) <= '" + personSubscribeRecordVo.getTimes()[1] + "' ";
                sql += " and substr(psr.UPLOAD_TIME,1,10) >= '" + personSubscribeRecordVo.getTimes()[0] + "' ";
            }
        }
//        if (!"".equals(personSubscribeRecordVo.getEnd_upload_time()) && null != personSubscribeRecordVo.getEnd_upload_time()) {
//            sql += " and psr.upload_time <= '" + personSubscribeRecordVo.getEnd_upload_time() + "' ";
//        }
//        if (!"".equals(personSubscribeRecordVo.getOrg_code()) && null != personSubscribeRecordVo.getOrg_code()) {
//            sql += " and oi.medical_insurance_num = '" + personSubscribeRecordVo.getOrg_code() + "' ";
//        }
        if (!"".equals(personSubscribeRecordVo.getCertno()) && null != personSubscribeRecordVo.getCertno()) {
            sql += " and ci.certno = '" + personSubscribeRecordVo.getCertno() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getOrg_id()) && null != personSubscribeRecordVo.getOrg_id()) {
            sql += " and psr.org_id = '" + personSubscribeRecordVo.getOrg_id() + "' ";
        }
        if (!"".equals(personSubscribeRecordVo.getOrg_name()) && null != personSubscribeRecordVo.getOrg_name()) {
            sql += " and oi.org_name LIKE '%" + personSubscribeRecordVo.getOrg_name() + "%' ";
        }
        //用人单位 只能查本单位的公务员预约记录
        if (!"".equals(personSubscribeRecordVo.getEmp_code()) && null != personSubscribeRecordVo.getEmp_code()) {
            sql += " and ci.emp_code = '" + personSubscribeRecordVo.getEmp_code() + "' ";
        }

        log.info("yyExport=====" + sql);
        return sql;
    }

    /**
     * 统计-服务排名
     */
    public String getRanking(String year) {
        String sql = "\tSELECT\n" +
                "\tPERSON_SUBSCRIBE_RECORD.ORG_ID,\n" +
                "\t  PERSON_SUBSCRIBE_RECORD.SCHED,\n" +
                "\t  count( * ) as count\n" +
                "\t   FROM\n" +
                "\t       PERSON_SUBSCRIBE_RECORD\n" +
                "\t   where 1=1 \n"+
                 "\t AND is_del = 0  \n";
        if (!"".equals(year)) {
            sql = sql + " AND  YEAR =  '" + year + "' \n";
        }
        sql = sql + "\t   GROUP BY\n" +
                "\t    PERSON_SUBSCRIBE_RECORD.ORG_ID,\n" +
                "\t           PERSON_SUBSCRIBE_RECORD.SCHED\n";
        return sql;
    }

    /**
     * 统计-服务排名 机构名称
     */
    public String getOrgId(String org_id) {
        String sql = "\tSELECT \n" +
                "\tORGANIZATION_INFO.ORG_NAME\n" +
                "\t  FROM\n" +
                "\t ORGANIZATION_INFO \n" +
                "\t WHERE  1=1\n " +
                "\t AND is_del = 0  \n";
        if (!"".equals(org_id)) {
            sql = sql + " AND ORGANIZATION_INFO.MEDICAL_INSURANCE_NUM  = '" + org_id + "' \t";
        }
        return sql;
    }

    /**
     * 修改为已上传报告
     */
    public String medicalOver(String id) {
        String sql = "\tUPDATE PERSON_SUBSCRIBE_RECORD SET PERSON_SUBSCRIBE_RECORD.SCHED = '3'  WHERE 1=1 \n";
        if (!"".equals(id)) {
            sql = sql + " AND PERSON_SUBSCRIBE_RECORD.ID   = '" + id + "' \t";
        }
        return sql;
    }

    public String noAppointment(Page page, PersonSubscribeRecordVo personSubscribeRecordVo) {
        String sql = "SELECT\n" +
                "\tci.*\n" +
                "FROM\n" +
                "\tCIVILWORKER_INFO ci\n" +
                "WHERE\n" +
                "\tci. certno NOT IN (\n" +
                "\t\tSELECT\n" +
                "\t\t\tpsr.civilworker_id\n" +
                "\t\tFROM\n" +
                "\t\t\tPERSON_SUBSCRIBE_RECORD psr where 1=1\n";
        if (!"".equals(personSubscribeRecordVo.getYear()) && null != personSubscribeRecordVo.getYear()) {
            sql += " and psr.YEAR = '" + personSubscribeRecordVo.getYear() + "' ";
        }
        sql += "\t)";
        if (!"".equals(personSubscribeRecordVo.getCertno()) && null != personSubscribeRecordVo.getCertno()) {
            sql += " and ci.certno = '" + personSubscribeRecordVo.getCertno() + "' ";
        }
        //用人单位 只能查本单位的公务员预约记录
        if (!"".equals(personSubscribeRecordVo.getEmp_code()) && null != personSubscribeRecordVo.getEmp_code()) {
            sql += " and ci.emp_code = '" + personSubscribeRecordVo.getEmp_code() + "' ";
        }
        return sql;
    }
    /**
     * //todo 此方法存在bug 当套餐出现多个时，统计出来的数据会少于实际的下面slq应修改为<code>
     SELECT     psr.SCHED,     COUNT( psr.SCHED ) as count     FROM
     PERSON_SUBSCRIBE_RECORD psr     LEFT JOIN CIVILWORKER_INFO ci ON psr.CIVILWORKER_ID = ci.CERTNO
     WHERE     psr.IS_DEL = '0' AND     ci.IS_DEL = '0'
     AND psr.ORG_ID = 'TJ00024' //medicalInsuranceNum
     AND ci.EMP_CODE = '32030000000000000010009950' // empCode
     GROUP BY   psr.SCHED
      </code> 此问题只出现在体检机构端 忙园区没方测试
     */
    public String adminUnitPhysicalRatioCheckedByOrg(String year, String empCode, String medicalInsuranceNum) {
        String sql = "SELECT\n" +
                "\tpsr.SCHED,\n" +
                "\tCOUNT( psr.SCHED ) as count\n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD psr\n" +
                "\tLEFT JOIN CIVILWORKER_INFO ci ON psr.CIVILWORKER_ID = ci.CERTNO\n" +
                "\tLEFT JOIN PACK_INFO pi ON psr.PACK_ID = pi.ID\n" +
                "\tLEFT JOIN ORGANIZATION_INFO oi ON pi.ORG_ID = oi.MEDICAL_INSURANCE_NUM \n" +
                "WHERE\n" +
                "\tpsr.IS_DEL = '0' AND\n" +
                "\tci.IS_DEL = '0' \n" ;

        if (StringUtils.isNotEmpty(medicalInsuranceNum)) {
            sql += "\tAND oi.MEDICAL_INSURANCE_NUM = '" + medicalInsuranceNum + "'\n";
        }
        if (StringUtils.isNotEmpty(year)) {
            sql += "\tAND psr.YEAR = '" + year + "' \n";
        }
        if (StringUtils.isNotEmpty(empCode)) {
            sql += "\tAND ci.EMP_CODE = '" + empCode + "'\n";
        }
        sql += "GROUP BY\n" +
                "\tpsr.SCHED";
        return sql;
    }

    public String yearListPhysicalRatioCheckedByOrg(String medicalInsuranceNum) {
        String sql = "SELECT\n" +
                "\tDISTINCT psr.YEAR\n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD psr\n" +
                "\tLEFT JOIN CIVILWORKER_INFO ci ON psr.CIVILWORKER_ID = ci.ID\n" +
                "\tLEFT JOIN PACK_INFO pi ON psr.PACK_ID = pi.ID\n" +
                "\tLEFT JOIN ORGANIZATION_INFO oi ON pi.ORG_ID = oi.MEDICAL_INSURANCE_NUM \n" +
                "WHERE\n" +
                "\tpsr.IS_DEL = '0' \n" +
                "\tAND oi.MEDICAL_INSURANCE_NUM = '" + medicalInsuranceNum + "'";
        return sql;
    }

    public String countBackOutTimesPersonally(String civilCertNo, String year) {
        String sql = "select count(*) as count\n" +
                      "from PERSON_SUBSCRIBE_RECORD PSR\n" +
                      "left join EMP_SUBSCRIBE_RECORD ESR\n" +
                        "on PSR.EMP_SUB_ID = ESR.ID\n" +
                "where CIVILWORKER_ID = '" + civilCertNo + "'\n" +
                " and ESR.IS_PERSONAL = '1'\n" +
                " and PSR.IS_DEL = '1'\n" +
                " and PSR.YEAR = '" + year + "'";
        return sql;
    }

    public String findPersonSubscribeRecordByCivilWorker(String civilWorkerId, String type) {
        String sql = "SELECT\n" +
                "\tPSR.ID,\n" +
                "\tPSR.APPLY_DATE,\n" +
                "\tPSR.YEAR,\n" +
                "\tPSR.SCHED,\n" +
                "\tPI.PACK_NAME AS pname,\n" +
                "\tOI.ORG_NAME AS oname,\n" +
                "\tPI.PACK_MONEY AS packMoney\n" +
                "FROM\n" +
                "\tPERSON_SUBSCRIBE_RECORD PSR\n" +
                "\tLEFT JOIN ORGANIZATION_INFO OI ON OI.MEDICAL_INSURANCE_NUM = PSR.ORG_ID\n" +
                "\tLEFT JOIN PACK_INFO PI ON PSR.PACK_ID = PI.ID \n" +
                "WHERE\n" +
                "\tPSR.CIVILWORKER_ID = '" + civilWorkerId + "'\n" +
                "\tAND PSR.IS_DEL = '0'";
        if (Objects.equals(type, "un")) {
            sql += "\tAND (PSR.SCHED = '0' OR PSR.SCHED = '2')\n";
        }
        if (Objects.equals(type, "ed")) {
            sql += "\tAND (PSR.SCHED = '1' OR PSR.SCHED = '3')\n";
        }

        sql += "\tORDER BY\n" +
                "\tPSR.APPLY_DATE DESC";
        return sql;
    }
}
