package com.jsdc.ybpt.dao.eval;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.eval_.EvalOrgTask;
import com.jsdc.ybpt.eval_.EvalOrgTaskVo;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.EvalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (EvalOrgTask)表数据库访问层
 *
 * @author wangyan
 * @since 2023-11-17 10:41:18
 */
@Repository
public class EvalOrgTaskDao {


    public String averageScore(String id) {
        String sql = "SELECT\n" +
                "\tROUND(SUM (score)/count(1), 2) score\n" +
                "FROM\n" +
                "\teval_org_task\n" +
                "WHERE\n" +
                "\ttaskManageId = " + id + "  and ISDEL = 0";
        return sql;
    }

    public String getOrgDetail(EvalVo vo) {
        String sql = "SELECT " +
                "       od.ID,\n" +
                "       od.appealCount,\n" +
                "       od.EVALORGTASKID,\n" +
                "       od.EVALSTARDARDMETHODID,\n" +
                "       od.STATUS,\n" +
                "       od.MEMO,\n" +
                "       od.RANK,\n" +
                "       od.RANKRATE,\n" +
                "       od.TARGETSCORE,\n" +
                "       od.SCORE,\n" +
                "       od.EVALSTARDARDID,\n" +
                "       sm.NAME,\n" +
                "       ot.TASKMANAGEID,\n" +
                "       ot.ORGCODE,\n" +
                "       ot.ORGNAME,\n" +
                "       u.id USERID,\n" +
                "       u.NAME USERNAME\n" +
                "FROM EVAL_ORG_DETAIL od\n" +
                "         LEFT JOIN EVAL_STARDARD_METHOD sm ON od.EVALSTARDARDMETHODID = sm.ID\n" +
                "         LEFT JOIN EVAL_ORG_TASK ot ON od.EVALORGTASKID = ot.ID AND ot.isDel = 0\n" +
                "         LEFT JOIN EVAL_TASK_MANAGE tm ON ot.TASKMANAGEID = tm.ID\n" +
                "         LEFT JOIN EVAL_STARDARD_USER su ON od.EVALSTARDARDID = su.STARDARDID\n" +
                "    AND ot.TASKMANAGEID = su.taskManageId\n" +
                "         LEFT JOIN SYS_USER u ON su.USERID = u.ID\n";
        sql += "WHERE tm.id = '" + vo.getTaskManageId() + "'\n";
        sql += "AND sm.id = '" + vo.getEarnestMoneyId() + "'\n";
        if(StringUtils.isNotEmpty(vo.getOrgCode())){
            sql += "AND ot.orgCode like '%" + vo.getOrgCode() + "%'\n";
        }
        if(StringUtils.isNotEmpty(vo.getStatus())){
            sql += "AND od.status = '" + vo.getStatus() + "'\n";
        }
        return sql;
    }

    public String noScore(Page page, EvalOrgTaskVo evalOrgTaskVo){
        String sql = "SELECT DISTINCT\n" +
                "\teo.orgCode,ecs.title,eot.evalStardardId evalStardardId\n" +
                "FROM\n" +
                "\teval_org_task eo\n" +
                "left join eval_org_detail eot on EOT.evalOrgTaskId = eo.id\n" +
                "left join eval_category_stardard ecs on ecs.id = eot.evalStardardId\n" +
                "left join eval_stardard_user esu on esu.stardardId = eot.evalStardardId\n" +
                "where eot.score is null and eo.taskManageId ='"+evalOrgTaskVo.getTaskManageId()+"'" ;
        return sql ;
    }
}

