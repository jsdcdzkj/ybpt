package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.model_check.EmpSubscribeRecord;
import com.jsdc.ybpt.vo.EmpExport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpSubscribeRecordMapper extends BaseMapper<EmpSubscribeRecord> {

    @Select("SELECT\n" +
            "\tesr.*,\n" +
            "\tei.EMP_NAME,\n" +
            "\tei.id as eid,\n" +
            "\tpi.org_id,\n" +
            "\tpi.pack_name\n" +
            "FROM\n" +
            "\tEMP_SUBSCRIBE_RECORD esr\n" +
            "\tLEFT JOIN EMPLOYING_INFO ei ON esr.UO_ID = ei.ID \n" +
            "\tLEFT JOIN pack_info pi ON esr.pack_id = pi.id\n" +
            "WHERE\n" +
            "\tesr.id = #{id}"+
            "\tand esr.is_del = '0'")
    EmpSubscribeRecord findEmpSubscribeRecordOne(@Param("id") String id);

    @Select("<script>"+
            "SELECT *\n" +
            "\tFROM (SELECT A.*, ROWNUM RN\n" +
            "\tFROM (SELECT\n" +
            "\tesr.*,\n" +
            "\tei.EMP_NAME AS uo_name,\n" +
            "\tpi.pack_name, \n" +
            "\tesr.org_name as oname, \n" +
            "\tpi.org_id as org_id, \n" +
            "\tpi.pack_year as year\n" +
            "\tFROM\n" +
            "\tEMP_SUBSCRIBE_RECORD esr\n" +
            "\tLEFT JOIN EMPLOYING_INFO ei ON esr.UO_ID = ei.ID\n" +
            "\tLEFT JOIN pack_info pi ON esr.pack_id = pi.id \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\tand esr.is_del = '0' \n" +
            "\t<if test='time !=null'>AND esr.CREATE_TIME = #{time} </if>\n" +
            "\t<if test='uoid !=null'>AND esr.uo_id = #{uoid} </if>\n" +
            "\t<if test='ename !=null'>AND esr.ORG_NAME LIKE concat(#{ename},'%')</if>\n" +
            "\t<if test='pname !=null'>AND pi.PACK_NAME like concat(#{pname},'%')</if>) A\n" +
            "\tWHERE ROWNUM <![CDATA[ <= ]]> #{end})\n" +
            "\tWHERE RN <![CDATA[ > ]]> #{start}"+
            "</script>")
    List<EmpSubscribeRecord> findEmpSubscribeRecord(@Param("time")String time,@Param("ename")String empName,@Param("pname")String packName,
                                                    @Param("start")Integer start,@Param("end")Integer end,@Param("uoid")String uoid);

    @Select("<script>"+
            "\tSELECT\n" +
            "\tcount(1)\n" +
            "FROM\n" +
            "\tEMP_SUBSCRIBE_RECORD esr\n" +
            "\tLEFT JOIN EMPLOYING_INFO ei ON esr.UO_ID = ei.ID\n" +
            "\tLEFT JOIN pack_info pi ON esr.pack_id = pi.id \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\tand esr.is_del = '0' \n" +
            "\t<if test='time !=null'>AND esr.CREATE_TIME = #{time} </if>\n" +
            "\t<if test='uoid !=null'>AND esr.uo_id = #{uoid} </if>\n" +
            "\t<if test='ename !=null'>AND esr.ORG_NAME LIKE concat(#{ename},'%') </if>\n" +
            "\t<if test='pname !=null'>AND pi.PACK_NAME like concat(#{pname},'%')</if>\n" +
            "</script>")
    Integer findEmpSubscribeRecordCount(@Param("time")String time,@Param("ename")String empName,@Param("pname")String packName,@Param("uoid")String uoid);



    @Select("<script>"+
            "SELECT *\n" +
            "\tFROM (SELECT A.*, ROWNUM RN\n" +
            "\tFROM (SELECT\n" +
            "\tesr.*,\n" +
            "\tei.EMP_NAME AS uo_name,\n" +
            "\tpi.pack_name, \n" +
            "\tpi.org_name as oname, \n" +
            "\tpi.pack_year as year\n" +
            "\tFROM\n" +
            "\tEMP_SUBSCRIBE_RECORD esr\n" +
            "\tLEFT JOIN EMPLOYING_INFO ei ON esr.UO_ID = ei.ID\n" +
            "\tLEFT JOIN pack_info pi ON esr.pack_id = pi.id \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\tand esr.is_del = '0' \n" +
            "\t<if test='time !=null'>AND esr.CREATE_TIME = #{time} </if>\n" +
            "\t<if test='uoid !=null and uoid!=\"\"' >AND esr.uo_id = #{uoid} </if>\n" +
            "\t<if test='ename !=null'>AND esr.ORG_NAME LIKE concat(#{ename},'%')</if>\n" +
            "\t<if test='pname !=null'>AND pi.PACK_NAME like concat(#{pname},'%')</if>) A\n" +
            "\tWHERE ROWNUM <![CDATA[ <= ]]> #{end})\n" +
            "\tWHERE RN <![CDATA[ > ]]> #{start}"+
            "</script>")
    List<EmpExport> findEmpSubscribeRecordExport(@Param("time")String time, @Param("ename")String empName, @Param("pname")String packName,
                                                 @Param("start")Integer start, @Param("end")Integer end, @Param("uoid")String uoid);
}
