package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.AssessmentVo;
import org.springframework.stereotype.Repository;

@Repository
public class KHManageDao {
    public String getList(Page page, AssessmentVo vo) {
        String sql = "SELECT m.id,\n" +
                "       m.aggrement_lv,\n" +
                "       m.task_manage_id,\n" +
                "       m.fixmedins_code,\n" +
                "       m.fixmedins_name,\n" +
                "       m.org_type,\n" +
                "       m.category,\n" +
                "       m.score,\n" +
                "       m.status,\n" +
                "       m.year,\n" +
                "       m.if_detail,\n" +
                "       m.admdvs,\n" +
                "       m.expiration_time,\n" +
                "       tm.task_name\n" +
                "FROM KH_MANAGE m\n";
        sql += " left join KH_TASK_MANAGE tm on tm.id = m.task_manage_id\n";
        sql += " left join FIXMEDINS_B fb on fb.fixmedins_code = m.fixmedins_code\n";
        sql += " WHERE m.is_del = 0\n" ;
        if(StringUtils.isNotEmpty(vo.getAdmdvs())){
            sql += " AND m.admdvs = '" + vo.getAdmdvs() + "'\n";
        }
        if (StringUtils.isNotEmpty(vo.getTask_name())) {
            sql += " AND tm.task_name like '%" + vo.getTask_name() + "%'\n";
        }
        if (StringUtils.isNotEmpty(vo.getOrg_type())) {
            sql += " AND M.org_type = '" + vo.getOrg_type() + "'\n";
        }
        if (StringUtils.isNotEmpty(vo.getAggrement_lv())) {
            sql += " AND M.aggrement_lv = '" + vo.getAggrement_lv() + "'\n";
        }
        if (StringUtils.isNotEmpty(vo.getYear())) {
            sql += " AND M.year = '" + vo.getYear() + "'\n";
        }
        if (StringUtils.isNotEmpty(vo.getFixmedins_code())) {
            sql += " AND M.fixmedins_code = '" + vo.getFixmedins_code() + "'\n";
        }
        if (StringUtils.isNotEmpty(vo.getFixmedins_name())) {
            sql += " AND M.fixmedins_name like '%" + vo.getFixmedins_name() + "%'\n";
        }
        if (StringUtils.isNotEmpty(vo.getStatus())) {
            sql += " AND M.status = '" + vo.getStatus() + "'\n";
        }
        return sql;
    }

}
