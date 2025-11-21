package com.jsdc.ybpt.dao.eval;

import org.springframework.stereotype.Repository;

@Repository
public class EvalCategoryStardardDao {

    /**getMethod
    *根据考核单查询所有办法
    * Author wzn
    * Date 2023/11/29 9:48
    */
    public String getMethod(String id ){
        String sql = "select esm.id,type from eval_design_category edc\n" +
                "left join eval_category_stardard ecs on ecs.categoryId = EDC.id\n" +
                "left join eval_stardard_method esm on esm.stardardId = ecs.id\n" +
                "where EDC.designId = '"+id+"'\n" +
                "and esm. configuration = 1" ;
        return sql ;
    }


    public String getSum(String id){
        String sql = "SELECT\n" +
                "\tSUM (score) score\n" +
                "FROM\n" +
                "\teval_org_detail\n" +
                "where evalOrgTaskId = '"+id+"'" ;
        return sql ;
    }


    public String getMedAndStar(String id){
        String sql = "select esm.stardardId,esm.id from eval_design_category edc\n" +
                "left join eval_category_stardard ecs on ecs.categoryId = EDC.id\n" +
                "left join eval_stardard_method esm on esm.stardardId = ecs.id\n" +
                "where EDC.designId = '"+id+"'" ;
        return sql ;
    }
}

