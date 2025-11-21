package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.model_check.OrgSubscribeRules;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgSubscribeRulesMapper extends BaseMapper<OrgSubscribeRules> {

    @Delete("DELETE FROM ORG_SUBSCRIBE_RULES " +
            "WHERE id IN ( " +
            "   SELECT t.id  FROM ( " +
            "       SELECT min(ID) AS id " +
            "       FROM ORG_SUBSCRIBE_RULES " +
            "       WHERE time >= TO_CHAR(SYSDATE, 'YYYY-MM-DD') " +
            "       GROUP BY ORG_ID, time " +
            "       HAVING count(ORG_ID) > 1 " +
            "   ) t " +
            ")")
    int deleteDuplicateRules();
}
