package com.jsdc.ybpt.dao.agreementsignDao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.agreementsignVo.NetTagMechanismVo;
import org.springframework.stereotype.Repository;

@Repository
public class NetTagMechanismDao {

    //分页查询
    public String selectPageList(Page page, NetTagMechanismVo vo) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select su.name as operate_user_name, ms.*,agm.year as agreemen_year,agm.category_id  as category_id,");
        buffer.append(" agm.net_grade_id as net_grade_id from net_tag_mechanism ms ");
        buffer.append(" LEFT JOIN net_tag_agreement agm on ms.agreement_id = agm.id ");
        buffer.append(" LEFT JOIN SYS_USER su on ms.operate_user = su.id");
        buffer.append(" where ms.is_del='0' ");
        buffer.append(" and ms.type = '0' ");
        if(StringUtils.isNotEmpty(vo.getAdmdvs())){
            buffer.append(" and ms.admdvs = '" + vo.getAdmdvs() + "'");
        }
        if (StringUtils.isNotEmpty(vo.getMedical_code())) {
            buffer.append(" and ms.medical_code = '").append(vo.getMedical_code()).append("'");
        }
        if ("1".equals(vo.getType_status())) {
            buffer.append(" and ms.status = ").append(vo.getStatus());
        } else if ("2".equals(vo.getType_status())) {
            //网签状态(0未签章/未确认 1已签章/已确认 2已解约 3已过期 4、驳回)
            buffer.append(" and ms.status in ('1','2','4')");
        }
        if (vo.getStatus() != null) {
            buffer.append(" and ms.status = ").append(vo.getStatus());
        }
        if (StringUtils.isNotEmpty(vo.getYear())) {
            buffer.append(" and agm.year = '").append(vo.getYear()).append("'");
        }
        if (StringUtils.isNotEmpty(vo.getAgreement_id())) {
            buffer.append(" and ms.agreement_id = ").append(vo.getAgreement_id());
        }
        if (vo.getCategory_id() != null) {
            buffer.append(" and agm.category_id = ").append(vo.getCategory_id());
        }
        if (vo.getNet_grade_id() != null) {
            buffer.append(" and agm.net_grade_id = ").append(vo.getNet_grade_id());
        }
//        if (StringUtils.isNotEmpty(vo.getType())) {
//            buffer.append(" and ms.type = '").append(vo.getType()).append("'");
//        }
        if (StringUtils.isNotEmpty(vo.getCreateUser())) {
            buffer.append(" and ms.createUser = '").append(vo.getCreateUser()).append("'");
        }
        if (StringUtils.isNotEmpty(vo.getStartTime())) {
            buffer.append("and ms.createTime >=to_date('" + vo.getStartTime() + "','yyyy-mm-dd') ");
        }
        if (StringUtils.isNotEmpty(vo.getEndTime())) {
            buffer.append("and ms.createTime <=to_date('" + vo.getEndTime() + "','yyyy-mm-dd') ");
        }

        if (StringUtils.isNotEmpty(vo.getMechanism_code())){
            if (vo.getMechanism_code().contains("%")){
                buffer.append(" and ms.mechanism_code like  '%/"+vo.getMechanism_code()+"%ESCAPE /'");
            }else {
                buffer.append(" and ms.mechanism_code like  '%"+vo.getMechanism_code()+"%'");
            }

        }

        return buffer.toString();
    }

    public String getOrgUnSeal(Page page,NetTagMechanismVo vo){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" 	*  ");
        sql.append(" FROM ");
        sql.append(" 	FIXMEDINS_B fb  ");
        sql.append(" WHERE ");
        sql.append(" 	1 = 1  ");
        if(StringUtils.isNotEmpty(vo.getMechanism_code())){
            sql.append(" AND fixmedins_code = '" + vo.getMechanism_code() + "'");
        }
        if(StringUtils.isNotEmpty(vo.getFixmedins_name())){
            sql.append(" AND fixmedins_name like '%" + vo.getFixmedins_name() + "%'");
        }
        if(null != vo.getNet_grade_id()){
            sql.append(" AND aggrement_lv = " + vo.getNet_grade_id());
        }
        if(null != vo.getCategory_id()){
            sql.append(" AND fixmedins_type = " + vo.getCategory_id());
        }
        sql.append("    AND fixmedins_type in ('1', '2')");
        sql.append("    AND fix_blng_admdvs = '" + vo.getAdmdvs() + "'");
        sql.append(" 	AND fb.fixmedins_code NOT IN (  ");
        sql.append(" 		SELECT nm.MECHANISM_CODE  ");
        sql.append(" 		FROM  ");
        sql.append(" 		NET_TAG_MECHANISM nm  ");
        sql.append(" 		WHERE  ");
        sql.append(" 		nm.agreement_id IN (  ");
        sql.append(" 			SELECT id FROM NET_TAG_AGREEMENT WHERE is_del = '0'  ");
        if(StringUtils.isNotEmpty(vo.getYear())){
            sql.append(" 	AND year = '" + vo.getYear() + "'");
        }
        sql.append(" ) ");

        sql.append(" AND status in ('0', '1')");
        sql.append(" AND is_del = '0'");
        sql.append(" AND type = '0'");
        sql.append(" ) ");
        return sql.toString();
    }

    public String getOrgUnSealList(NetTagMechanismVo vo){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" 	*  ");
        sql.append(" FROM ");
        sql.append(" 	FIXMEDINS_B fb  ");
        sql.append(" WHERE ");
        sql.append(" 	1 = 1  ");
        if(StringUtils.isNotEmpty(vo.getMechanism_code())){
            sql.append(" AND fixmedins_code = '" + vo.getMechanism_code() + "'");
        }
        if(StringUtils.isNotEmpty(vo.getFixmedins_name())){
            sql.append(" AND fixmedins_name like '%" + vo.getFixmedins_name() + "%'");
        }
        if(null != vo.getNet_grade_id()){
            sql.append(" AND aggrement_lv = " + vo.getNet_grade_id());
        }
        if(null != vo.getCategory_id()){
            sql.append(" AND fixmedins_type = " + vo.getCategory_id());
        }
        sql.append("    AND fixmedins_type in ('1', '2')");
        sql.append("    AND fix_blng_admdvs = '" + vo.getAdmdvs() + "'");
        sql.append(" 	AND fb.fixmedins_code NOT IN (  ");
        sql.append(" 		SELECT nm.MECHANISM_CODE  ");
        sql.append(" 		FROM  ");
        sql.append(" 		NET_TAG_MECHANISM nm  ");
        sql.append(" 		WHERE  ");
        sql.append(" 		nm.agreement_id IN (  ");
        sql.append(" 			SELECT id FROM NET_TAG_AGREEMENT WHERE is_del = '0'  ");
        if(StringUtils.isNotEmpty(vo.getYear())){
            sql.append(" 	AND year = '" + vo.getYear() + "'");
        }
        sql.append(" ) ");

        sql.append(" AND status in ('0', '1')");
        sql.append(" AND is_del = '0'");
        sql.append(" AND type = '0'");
        sql.append(" ) ");
        return sql.toString();
    }
}
