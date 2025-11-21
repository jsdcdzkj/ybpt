package com.jsdc.ybpt.dao.notice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_check.Notice;
import com.jsdc.ybpt.vo.notice.NoticeVo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class NoticeDao {
    public String getPageList(Page<Notice> page, NoticeVo vo) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\tn.id,\n" +
                "\tn.TITLE,\n" +
                "\tn.range,\n" +
                "\tTO_CHAR( DBMS_LOB.SUBSTR( n.CONTENT, 100, 1 ) ) AS CONTENT,\n" +
                "\tn.CREATETIME,\n" +
                "\tn.is_launch,\n" +
                "\tn.launchTime\n" +
                "FROM\n" +
                "\tNOTICE n");
        sql.append("\tWHERE n.is_del = '0' AND ( instr(n.range, 't') > 0 or instr(n.range, 'y') > 0)");

        if (StringUtils.hasLength(vo.getCreateUser())) {
            sql.append("\tand n.createUser = '" + vo.getCreateUser() + "'");
        }

        if (StringUtils.hasText(vo.getTitle())) {
            sql.append("\tand n.title like '%" + vo.getTitle() + "%'");
        }

        if (StringUtils.hasText(vo.getStartTime()) && StringUtils.hasText(vo.getEndTime())) {
            sql.append("\tand n.createTime >= to_date('").append(vo.getStartTime()).append("','yyyy-mm-dd hh24:mi:ss')");
            sql.append("\tand n.createTime <= to_date('").append(vo.getEndTime()).append("','yyyy-mm-dd hh24:mi:ss')");
        }

        if (StringUtils.hasText(vo.getIs_launch())) {
            sql.append("\tand n.is_launch = '" + vo.getIs_launch() + "'");
        }

        if (vo.getRangeList().size() > 0) {
            for (String s : vo.getRangeList()) {
                sql.append("\tAND INSTR(n.range, '").append(s).append("') > 0");
            }
        }
        sql.append("\torder by n.createTime desc");
        return sql.toString();
    }

    public String getPageList2(Page<Notice> page, NoticeVo vo) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\tn.id,\n" +
                "\tn.TITLE,\n" +
                "\tn.range,\n" +
                "\tTO_CHAR( DBMS_LOB.SUBSTR( n.CONTENT, 100, 1 ) ) AS CONTENT,\n" +
                "\tn.CREATETIME,\n" +
                "\tn.is_launch,\n" +
                "\tn.launchTime\n" +
                "FROM\n" +
                "\tNOTICE n");
        sql.append("\tWHERE n.is_del = '0' AND ( instr(n.range, 'j') > 0 or instr(n.range, 'd') > 0)");

        if (StringUtils.hasLength(vo.getCreateUser())) {
            sql.append("\tand n.createUser = '" + vo.getCreateUser() + "'");
        }

        if (StringUtils.hasText(vo.getTitle())) {
            sql.append("\tand n.title like '%" + vo.getTitle() + "%'");
        }

        if (StringUtils.hasText(vo.getStartTime()) && StringUtils.hasText(vo.getEndTime())) {
            sql.append("\tand n.createTime >= to_date('").append(vo.getStartTime()).append("','yyyy-mm-dd hh24:mi:ss')");
            sql.append("\tand n.createTime <= to_date('").append(vo.getEndTime()).append("','yyyy-mm-dd hh24:mi:ss')");
        }

        if (StringUtils.hasText(vo.getIs_launch())) {
            sql.append("\tand n.is_launch = '" + vo.getIs_launch() + "'");
        }

        if (vo.getRangeList().size() > 0) {
            for (String s : vo.getRangeList()) {
                sql.append("\tAND INSTR(n.range, '").append(s).append("') > 0");
            }
        }
        sql.append("\torder by n.createTime desc");
        return sql.toString();
    }

    public String getPageListForAccepter(Page<NoticeVo> page, NoticeVo vo) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\tn.id,\n" +
                "\tn.title,\n" +
                "\tTO_CHAR( DBMS_LOB.SUBSTR( n.CONTENT, 100, 1 ) ) AS CONTENT,\n" +
                "\tn.launchTime,\n" +
                "\tntc.IS_READ \n" +
                "FROM\n" +
                "\tNOTICE_TO_ACCEPTER ntc\n" +
                "\tLEFT JOIN NOTICE n ON ntc.NOTICE_ID = n.id \n" +
                "WHERE\n" +
                "\tntc.ACCEPTER_CODE = '" + vo.getAccepter_code() + "' \n" +
                "\tAND n.IS_LAUNCH = '1' \n" +
                "\tAND n.IS_DEL = '0' \n");

        if (StringUtils.hasText(vo.getTitle())) {
            sql.append("\tand n.title like '%" + vo.getTitle() + "%'");
        }

        if (StringUtils.hasText(vo.getStartTime()) && StringUtils.hasText(vo.getEndTime())) {
            sql.append("\tand n.launchTime >= to_date('").append(vo.getStartTime()).append("','yyyy-mm-dd hh24:mi:ss')");
            sql.append("\tand n.launchTime <= to_date('").append(vo.getEndTime()).append("','yyyy-mm-dd hh24:mi:ss')");
        }

        if (StringUtils.hasText(vo.getIs_read())) {
            sql.append("\tand ntc.is_read = '" + vo.getIs_read() + "'");
        }

        sql.append("ORDER BY\n" +
                "\tLAUNCHTIME DESC");
        return sql.toString();
    }

    public String getNoticeForAccepter(String accepterCode) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\tn.id,\n" +
                "\tn.title,\n" +
                "\tTO_CHAR( DBMS_LOB.SUBSTR( n.CONTENT, 100, 1 ) ) AS CONTENT,\n" +
                "\tn.launchTime,\n" +
                "\tntc.IS_READ \n" +
                "FROM\n" +
                "\tNOTICE_TO_ACCEPTER ntc\n" +
                "\tLEFT JOIN NOTICE n ON ntc.NOTICE_ID = n.id \n" +
                "WHERE\n" +
                "\tntc.ACCEPTER_CODE = '" + accepterCode + "' \n" +
                "\tAND n.IS_LAUNCH = '1' \n" +
                "\tAND ntc.IS_READ = '0' \n" +
                "\tAND n.IS_DEL = '0' \n");
        sql.append("ORDER BY\n" +
                "\tLAUNCHTIME DESC");
        return sql.toString();
    }

    public String getNoticeForAccepterPage(Page<NoticeVo> page,Notice notice,String accepterCode) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\tn.id,\n" +
                "\tn.title,\n" +
                "\tTO_CHAR( DBMS_LOB.SUBSTR( n.CONTENT, 100, 1 ) ) AS CONTENT,\n" +
                "\tn.launchTime,\n" +
                "\tntc.IS_READ \n" +
                "FROM\n" +
                "\tNOTICE_TO_ACCEPTER ntc\n" +
                "\tLEFT JOIN NOTICE n ON ntc.NOTICE_ID = n.id \n" +
                "WHERE\n" +
                "\tntc.ACCEPTER_CODE = '" + accepterCode + "' \n" +
                "\tAND n.IS_LAUNCH = '1' \n" +
                "\tAND n.IS_DEL = '0' \n");
        if (StringUtils.hasText(notice.getTitle())) {
            sql.append("\tand n.title like '%" + notice.getTitle() + "%'");
        }

//        if (StringUtils.hasText(notice.getLaunchTime()) && StringUtils.hasText(vo.getEndTime())) {
//            sql.append("\tand n.createTime >= to_date('").append(vo.getStartTime()).append("','yyyy-mm-dd hh24:mi:ss')");
//            sql.append("\tand n.createTime <= to_date('").append(vo.getEndTime()).append("','yyyy-mm-dd hh24:mi:ss')");
//        }
        sql.append("ORDER BY\n" +
                "\tLAUNCHTIME DESC");
        return sql.toString();
    }

    /**
     * @Author: yc
     * @Params:
     * @Return:
     * @Description：获取当前行政单位下面的所有(用人单位)对应的用户信息
     * @Date ：2023/5/22 下午 3:38
     * @Modified By：
     */
    public String getYRDWSysuserByParentOrgCode(String parentOrgCode) {
        String sql = "SELECT\n" +
                "\tsu.* \n" +
                "FROM\n" +
                "\tEMPLOYING_INFO ei\n" +
                "\tLEFT JOIN SYS_USER su ON su.ORG_CODE = ei.EMP_NO \n" +
                "WHERE\n" +
                "\tei.PARENTORGCODE = '"+ parentOrgCode +"'" +
                "AND su.id is not null";
        return sql;
    }

    /**
     * @Author: yc
     * @Params:
     * @Return:
     * @Description：获取当前行政单位下面的所有(体检机构)对应的用户信息
     * @Date ：2023/5/22 下午 3:38
     * @Modified By：
     */
    public String getTJJGSysuserByMedicalInsuranceNum(String orgCode) {
        String sql = "SELECT\n" +
                "\tsu.* \n" +
                "FROM\n" +
                "\tORGANIZATION_INFO oi\n" +
                "\tLEFT JOIN SYS_USER su ON su.ORG_CODE = oi.MEDICAL_INSURANCE_NUM \n" +
                "WHERE\n" +
                "\toi.ORG_CODE = '"+ orgCode +"'";
        return sql;
    }

    public String getCivilInfoListByEmpParentOrgCode(String parentOrgCode) {
        String sql = "select DISTINCT ci.CERTNO from CIVILWORKER_INFO ci where ci.EMP_CODE in (SELECT\n" +
                "\tei.EMP_NO\n" +
                "FROM\n" +
                "\tEMPLOYING_INFO ei\n" +
                "\tLEFT JOIN SYS_USER su ON su.ORG_CODE = ei.EMP_NO \n" +
                "WHERE\n" +
                "\tei.PARENTORGCODE = '" + parentOrgCode +"')";
        return sql;
    }

    public String getListByCivilCertNo(String cardId) {
        StringBuilder sql = new StringBuilder("SELECT\n" +
                "\tn.id,\n" +
                "\tn.title,\n" +
                "\tTO_CHAR( DBMS_LOB.SUBSTR( n.CONTENT, 100, 1 ) ) AS CONTENT,\n" +
                "\tn.launchTime,\n" +
                "\tntc.IS_READ \n" +
                "FROM\n" +
                "\tNOTICE_TO_ACCEPTER ntc\n" +
                "\tLEFT JOIN NOTICE n ON ntc.NOTICE_ID = n.id \n" +
                "WHERE\n" +
                "\tntc.ACCEPTER_CODE = '" + cardId + "' \n" +
                "\tAND n.IS_LAUNCH = '1' \n" +
                "\tAND n.IS_DEL = '0' \n");
        return sql.toString();
    }
}
