package com.jsdc.ybpt.dao;

import com.jsdc.ybpt.vo.AssessmentVo;
import org.springframework.stereotype.Repository;

@Repository
public class KhAssessmentDetailDao {

    public String getList(AssessmentVo vo) {
        String sql = "select ad.id,\n" +
                "       ad.assessment_id,\n" +
                "       ad.assess_question,\n" +
                "       ad.full_score,\n" +
                "       ad.is_text,\n" +
                "       ad.is_file,\n" +
                "       ad.sort,\n" +
                "       ac.id assessment_content_id,\n" +
                "       nvl(ac.scorel,0) scorel,\n" +
                "       ac.assess_contentl\n" +
                "from kh_assessment_detail ad\n" +
                "         left join kh_assessment a on ad.assessment_id = a.id\n" +
                "         left join kh_task_manage tm on a.id = tm.assessment_id\n" +
                "         left join kh_manage m on tm.id = m.task_manage_id\n" +
                "         left join kh_assessment_content ac on (ad.id = ac.assess_detail_id and m.id = ac.manage_id)\n";
        sql += " WHERE m.id = '" + vo.getId() + "'\n";
        sql += " order by ad.sort\n";
        return sql;
    }
}
