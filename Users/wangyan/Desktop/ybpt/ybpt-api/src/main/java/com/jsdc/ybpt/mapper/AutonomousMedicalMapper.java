package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.AutonomousMedicalDao;
import com.jsdc.ybpt.model_check.AutonomousMedical;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangYan
 * @since 2022-05-24
 */
@Mapper
public interface AutonomousMedicalMapper extends BaseMapper<AutonomousMedical> {

    /**
     * 统计 组织占比 总数
     */
    @SelectProvider(type= AutonomousMedicalDao.class,method = "getOrganization")
    String getOrganization(String year);
    /**
     * 统计 组织占比 自主
     */
    @SelectProvider(type= AutonomousMedicalDao.class,method = "getOrganizationZiZhu")
    String getOrganizationZiZhu();


    @Select("\n" +
            "SELECT\n" +
            "\tcount(1) \n" +
            "FROM\n" +
            "\tautonomous_medical \n" +
            "WHERE\n" +
            "\tAPPLY_YEAR = ( SELECT extract( year FROM SYSDATE ) FROM dual ) \n" +
            "\tAND STATUS =1\n"+
            "\tAND IMP_NO=#{no}")
    Integer findAutonomousMedicalCount(@Param("no")String no);
}
