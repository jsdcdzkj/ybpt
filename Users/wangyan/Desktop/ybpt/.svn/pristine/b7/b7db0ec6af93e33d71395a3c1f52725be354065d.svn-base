package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.model_check.RelocatedInfo;
import com.jsdc.ybpt.vo.RelocatedInfoExport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RelocatedInfoMapper extends BaseMapper<RelocatedInfo> {

    @Select("SELECT\n" +
            "\tr.ID,\n" +
            "\tr.MEDPLACE,\n" +
            "\tr.ACCOUNT_BANK,\n" +
            "\tr.ACCOUNT_NO,\n" +
            "\tr.RELOCATED_YEAR,\n" +
            "\tr.CIVILWORKER_ID,\n" +
            "\tc.NAME \n" +
            "FROM\n" +
            "\trelocated_info r\n" +
            "\tLEFT JOIN civilworker_virfy c ON r.CIVILWORKER_ID = c.certno\n" +
            "WHERE\n" +
            "r.ID=#{id} " +
            "\tand r.is_del=0")
    RelocatedInfo findRelocatedInfoOne(RelocatedInfo relocatedInfo);


    @Select("<script>" +
            "SELECT *\n" +
            "\tFROM (SELECT A.*, ROWNUM RN\n" +
            "\tFROM (SELECT\n" +
            "\tr.ID,\n" +
            "\tr.MEDPLACE,\n" +
            "\tr.PHONE,\n" +
            "\tr.ACCOUNT_BANK,\n" +
            "\tc.birthday,\n" +
            "\t\t\t\tFLOOR( MONTHS_BETWEEN( SYSDATE, TO_DATE(c.birthday, 'YYYY-MM-DD') ) / 12 ) AS age,\n" +
            "\tr.ACCOUNT_NO,\n" +
            "\tr.RELOCATED_YEAR,\n" +
            "\tr.CIVILWORKER_ID,\n" +
            "\tc.NAME,\n" +
            "\t\tCASE c.SEX\n" +
            "\tWHEN '0' THEN\n" +
            "\t\t'男'\n" +
            "\tWHEN '1' THEN\n" +
            "\t\t'女'\n" +
            "\tELSE\n" +
            "\t\t'-'\n" +
            "END sex  ,\n" +
//            "\tc.AGE,\n" +
            "\tc.CERTNO as num,\n" +
            "\tc.EMP_NAME as ename,\n" +
            "\tc.EMP_CODE as ecode,\n" +
            "\tc.ADMDVS as admdvs"+
            "\tFROM\n" +
            "\trelocated_info r\n" +
            "\tLEFT JOIN civilworker_info c ON r.CIVILWORKER_ID = c.certno \n" +
            "WHERE\n" +
            "1=1" +
            "\t<if test='name!=null and name!=\"\"'>AND c.NAME like concat(#{name},'%')  </if>\n" +
            "\t<if test='num!=null and num!=\"\"'>AND c.certno = #{num}</if>\n" +
            "\t<if test='orgcode!=null and orgcode!=\"\"'>AND c.emp_code = #{orgcode}</if>\n" +
            "\t<if test='dCode!=null and dCode!=\"\"'>AND r.administrative_unit = #{dCode}</if>\n" +
            "\tand r.is_del=0 AND c.IS_DEL=0) A\n" +
            "\tWHERE ROWNUM <![CDATA[ <= ]]> #{end})\n" +
            "\tWHERE RN <![CDATA[ > ]]> #{start}" +
            "</script>")
    List<RelocatedInfo> FindRelocatedInfo(@Param("name")String name, @Param("num")String num, @Param("start") Integer start,
                                          @Param("end")  Integer end,@Param("orgcode")String orgcode,@Param("dCode")String dCode);

    @Select("<script>" +
            "SELECT\n" +
            "\tCOUNT(1)\n" +
            "FROM\n" +
            "\trelocated_info r\n" +
            "\tLEFT JOIN civilworker_info c ON r.CIVILWORKER_ID = c.certno \n" +
            "WHERE\n" +
            "1=1" +
            "\t<if test='name!=null and name!=\"\"'>AND c.NAME like concat(#{name},'%')  </if>\n" +
            "\t<if test='num!=null and num!=\"\"'>AND c.certno = #{num}</if>\n" +
            "\t<if test='orgcode!=null and orgcode!=\"\"'>AND c.emp_code = #{orgcode}</if>\n" +
            "\t<if test='dCode!=null and dCode!=\"\"'>AND r.administrative_unit = #{dCode}</if>\n" +
            "\tand r.is_del=0\n" +
            "</script>")
    Integer FindRelocatedInfoCount(@Param("name")String name, @Param("num")String num,@Param("orgcode")String orgcode,@Param("dCode")String dCode);


    @Select("<script>" +
            "SELECT *\n" +
            "\tFROM (SELECT A.*, ROWNUM RN\n" +
            "\tFROM (SELECT\n" +
            "\tci.NAME,\n" +
            "\tci.sex,\n" +
            "\tci.AGE,\n" +
            "\tci.CERTNO,\n" +
            "\tci.ADMDVS,\n" +
            "\tri.MEDPLACE,\n" +
            "\tci.EMP_NAME,\n" +
            "\tri.ORG_CODE,\n" +
            "\tri.ACCOUNT_BANK,\n" +
            "\tri.ACCOUNT_NO "+
            "\tFROM\n" +
            "\trelocated_info ri\n" +
            "\tLEFT JOIN civilworker_info ci ON ri.CIVILWORKER_ID = ci.certno \n" +
            "WHERE\n" +
            "1=1" +
            "\t<if test='name!=null  and name!=\"\"'  >AND ci.NAME like concat(#{name},'%')  </if>\n" +
            "\t<if test='num!=null  and num!=\"\"'  >AND ci.certno = #{num}</if>\n" +
            "\t<if test='orgcode!=null  and orgcode!=\"\"'  >AND ci.emp_code = #{orgcode}</if>\n" +
            "\t<if test='dCode!=null  and dCode!=\"\"'  >AND ri.administrative_unit = #{dCode}</if>\n" +
            "\tand ri.is_del=0) A\n" +
            "\tWHERE ROWNUM <![CDATA[ <= ]]> #{end})\n" +
            "\tWHERE RN <![CDATA[ > ]]> #{start}" +
            "</script>")
    List<RelocatedInfoExport> RelocatedInfoExport(@Param("name")String name, @Param("num")String num, @Param("start") Integer start,
                                                @Param("end")  Integer end, @Param("orgcode")String orgcode, @Param("dCode")String dCode);
}
