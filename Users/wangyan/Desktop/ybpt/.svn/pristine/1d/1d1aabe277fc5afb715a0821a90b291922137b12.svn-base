package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.PersonSubscribeRecordDao;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.PersonSubscribeRecord;
import com.jsdc.ybpt.vo.EChat;
import com.jsdc.ybpt.vo.PersonSubscribeRecordVo;
import com.jsdc.ybpt.vo.PhysicalRatioVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface PersonSubscribeRecordMapper extends BaseMapper<PersonSubscribeRecord> {

    @Select("SELECT\n" +
            "\t\t\tpsr.ID,\n" +
            "\t\t\tpsr.EMP_SUB_ID,\n" +
            "\t\t\tpsr.CIVILWORKER_ID,\n" +
            "\t\t\tpsr.YEAR,\n" +
            "\t\t\tpsr.ORG_ID,\n" +
            "\t\t\tpsr.PACK_ID,\n" +
            "\t\t\tpsr.APPLY_DATE,\n" +
            "\t\t\tpsr.SCHED,\n" +
            "\t\t\tpsr.CHECKUP_TIME,\n" +
            "\t\t\tpsr.UPLOAD_TIME,\n" +
            "\t\t\tpi.PACK_NAME,\n" +
            "\t\t\tci.name,\n" +
            "\t\t\toi.org_name \n" +
            "\t\tFROM\n" +
            "\t\t\tPERSON_SUBSCRIBE_RECORD psr\n" +
            "\t\t\tLEFT JOIN emp_subscribe_record esr ON psr.EMP_SUB_ID = esr.ID\n" +
            "\t\t\tLEFT JOIN CIVILWORKER_INFO ci ON psr.civilworker_id = ci.certno\n" +
            "\t\t\tLEFT JOIN PACK_INFO pi ON esr.pack_id = pi.id\n" +
            "\t\t\tLEFT JOIN ORGANIZATION_INFO oi ON psr.ORG_ID = oi.id \n" +
            "\t\tWHERE\n" +
            "\t\t\t1 = 1 \n" +
            "\t\t\tAND psr.ID = #{id} ")
    PersonSubscribeRecord findPersonSubscribeRecordOne(@Param("id") String id);

    @Select("<script>" +
            "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\ta.* ,\n" +
            "\t\tROWNUM AS rn \n" +
            "\tFROM\n" +
            "\t\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tpsr.ID,\n" +
            "\t\t\tpsr.EMP_SUB_ID,\n" +
            "\t\t\tpsr.CIVILWORKER_ID,\n" +
            "\t\t\tpsr.YEAR,\n" +
            "\t\t\tpsr.ORG_ID,\n" +
            "\t\t\tpsr.PACK_ID,\n" +
            "\t\t\tpsr.APPLY_DATE,\n" +
            "\t\t\tpsr.SCHED,\n" +
            "\t\t\tpsr.CHECKUP_TIME,\n" +
            "\t\t\tpsr.UPLOAD_TIME,\n" +
            "\t\t\tpi.PACK_NAME as pname,\n" +
            "\t\t\tci.name,\n" +
            "\t\t\toi.org_name as oname \n" +
            "\t\tFROM\n" +
            "\t\t\tPERSON_SUBSCRIBE_RECORD psr\n" +
            "\t\t\tLEFT JOIN emp_subscribe_record esr ON psr.EMP_SUB_ID = esr.ID\n" +
            "\t\t\tLEFT JOIN CIVILWORKER_INFO ci ON psr.civilworker_id = ci.certno\n" +
            "\t\t\tLEFT JOIN PACK_INFO pi ON esr.pack_id = pi.id\n" +
            "\t\t\tLEFT JOIN ORGANIZATION_INFO oi ON psr.ORG_ID = oi.id \n" +
            "\t\tWHERE\n" +
            "\t\t\t1 = 1 \n" +
            "\t\t\tand psr.is_del = '0' \n" +
            "\t\t\t<if test='esid!=null'>AND psr.EMP_SUB_ID = #{esid} </if>\n" +
            "\t\t\t<if test='cname!=null'>AND ci.NAME = #{cname} </if> \n" +
            "\t\t\t<if test='year!=null'>AND psr.YEAR = #{year} </if>\n" +
            "\t\t\t<if test='oname!=null'>AND oi.ORG_NAME = #{oname} </if> \n" +
            "\t\t\t<if test='pname!=null'>AND pi.PACK_NAME = #{pname} </if> \n" +
            "\t\t\t<if test='sched!=null'>AND psr.SCHED = #{sched} </if>\n" +
            "\t\t) a \n" +
            "WHERE\n" +
            "\tROWNUM <![CDATA[ <= ]]> #{end} ) WHERE rn <![CDATA[ > ]]>#{start}</script>")
    List<PersonSubscribeRecord> findPersonSubscribeRecord(@Param("esid") String esid, @Param("cname") String cname, @Param("year") String year,
                                                          @Param("oname") String oname, @Param("pname") String pname, @Param("sched") String sched,
                                                          @Param("start") Integer start, @Param("end") Integer end);

    /**
     * 已体检人数统计
     * Author wzn
     * Date 2022/5/27 17:55
     */
    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "getCount")
    Integer getCount(String orgCode);

    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "reservationCount")
    Integer reservationCount(String orgCode, String type);


    /**
     * 预约记录
     * Author wzn
     * Date 2022/5/31 16:02
     */
    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "personSelectList")
    Page<PersonSubscribeRecordVo> selectListAll(Page page, PersonSubscribeRecordVo personSubscribeRecordVo);

    /**
     * 预约记录 全部
     * Author wy
     * Date 2022/5/31 16:02
     */
    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "personSelectListAll")
    List<PersonSubscribeRecordVo> listAll(PersonSubscribeRecordVo personSubscribeRecordVo);


    @Select("<script>" +
            "SELECT\n" +
            "\tcount( 1 ) \n" +
            "FROM\n" +
            "\tPERSON_SUBSCRIBE_RECORD psr\n" +
            "\tLEFT JOIN emp_subscribe_record esr ON psr.EMP_SUB_ID = esr.ID\n" +
            "\tLEFT JOIN CIVILWORKER_INFO ci ON psr.civilworker_id = ci.certno\n" +
            "\tLEFT JOIN PACK_INFO pi ON esr.pack_id = pi.id\n" +
            "\tLEFT JOIN ORGANIZATION_INFO oi ON psr.ORG_ID = oi.id \n" +
            "WHERE\n" +
            "\t1 =1\n" +
            "\t\t\tand psr.is_del = '0' \n" +
            "\t<if test='esid!=null'>AND psr.EMP_SUB_ID = #{esid} </if>\n" +
            "\t<if test='cname!=null'>AND ci.NAME = #{cname} </if> \n" +
            "\t<if test='year!=null'>AND psr.YEAR = #{year} </if>\n" +
            "\t<if test='oname!=null'>AND oi.ORG_NAME = #{oname} </if>\n" +
            "\t<if test='pname!=null'>AND pi.PACK_NAME = #{pname} </if>\n" +
            "\t<if test='sched!=null'>AND psr.SCHED = #{sched} </if>\n" +
            "</script>")
    Integer findPersonSubscribeRecordCount(@Param("esid") String esid, @Param("cname") String cname, @Param("year") String year,
                                           @Param("oname") String oname, @Param("pname") String pname, @Param("sched") String sched);

    @Select("<script>" +
            "SELECT\n" +
            "\tci.NAME,\n" +
            "\tci.CERTNO,\n" +
            "\tNVL(ci.AGE, '-') as AGE,\n" +
            "\tCASE ci.SEX\n" +
            "\tWHEN '0' THEN\n" +
            "\t\t'男'\n" +
            "\tWHEN '1' THEN\n" +
            "\t\t'女'\n" +
            "\tELSE\n" +
            "\t\t'-'\n" +
            "END sex  ,\n" +
            "\tpsr.apply_date \n" +
            "FROM\n" +
            "\tPERSON_SUBSCRIBE_RECORD psr\n" +
            "\tLEFT JOIN CIVILWORKER_INFO ci ON ci.certno = psr.CIVILWORKER_ID \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\t\t\tand psr.is_del = '0' \n" +
            "\t<if test='rid!=null and rid!=\"\"' >AND psr.EMP_SUB_ID =#{rid}</if>" +
            "\t<if test='oid!=null and oid!=\"\"' >AND psr.org_id =#{oid}</if>" +
            "\t<if test='code!=null and code!=\"\"' >AND ci.emp_code =#{code}</if>" +
            "\t<if test='time!=null and time!=\"\"'>AND psr.apply_date =#{time}</if>" +
            "</script>")
    List<Map<String, String>> findCiviWorkerByRid(@Param("rid") String rid, @Param("oid") String oid, @Param("time") String time, @Param("code") String code);


    @Select("<script>" +
            "SELECT\n" +
            "\tmi.ITEM_NAME AS name,\n" +
            "\tmi.ITEM_NO,\n" +
            "\tNVL(mi.IS_GENERIC ,'1') AS generic \n" +
            "FROM\n" +
            "\tITEM_TO_MEAL itm\n" +
            "\tLEFT JOIN MEDICAL_ITEM mi ON itm.ITEM_ID = mi.ID \n" +
            "WHERE\n" +
            "\t1 = 1" +
            "\t<if test='pid!=null'>AND itm.PACK_ID = #{pid}</if>" +
            "\torder by generic asc" +
            "</script>")
    List<Map<String, String>> findItemToMealByPid(@Param("pid") String pid);

    /**
     * @Description: 查询每条预约记录下有每天多少人
     * @param: [id]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author: 苹果
     * @date: 2022/6/1
     * @time: 14:36
     */
    @Select("SELECT\n" +
            "\tCOUNT( 1 ) AS num,\n" +
            "\tORG_ID AS ORG_ID,\n" +
            "\tAPPLY_DATE AS day \n" +
            "FROM\n" +
            "\tPERSON_SUBSCRIBE_RECORD \n" +
            "WHERE\n" +
            "\tIS_DEL = 0 \n" +
            "\tAND sched = '0' \n" +
            "\tAND EMP_SUB_ID = #{id} \n" +
            "GROUP BY\n" +
            "\tAPPLY_DATE,\n" +
            "\tORG_ID")
    List<Map<String, Object>> findPersonNumEveDay(@Param("id") String id);


    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "findPersonSubscribeRecordByCivilWorker")
    List<PersonSubscribeRecord> findPersonSubscribeRecordByCivilWorker(String civilWorkerId, String type);

    @Select("<script>" +
            "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tCIVILWORKER_INFO ci\n" +
            "\tLEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tpsr.CIVILWORKER_ID \n" +
            "\tFROM\n" +
            "\t\tPERSON_SUBSCRIBE_RECORD psr\n" +
            "\t\tLEFT JOIN CIVILWORKER_INFO ci ON psr.CIVILWORKER_ID = ci.certno AND ci.IS_DEL='0' \n" +
            "\tWHERE\n" +
            "\t\tci.EMP_CODE = #{id} \n" +
            "\t\tand psr.IS_DEL='0'\n" +
            "\t\tand psr.SCHED IN(0,1,2,3)\n" +
            "\t\t<if test='year !=null and year!=\"\"'>AND psr.year = #{year} </if> \n" +
            "\t\t<if test='num !=null and num!=\"\"'>\n" +
            "\t\tHAVING\n" +
            "\t\tCOUNT(1)<![CDATA[  > ]]>0\n" +
            "\t\tAND COUNT(1)<![CDATA[  <= ]]>#{num}</if>\n" +
            "\t\tGROUP BY\n" +
            "\t\tpsr.CIVILWORKER_ID \n" +
            "\t) a ON  a.CIVILWORKER_ID = ci.certno \n" +
            "WHERE\n" +
            "\ta.CIVILWORKER_ID IS  NULL \n" +
            "\tAND ci.EMP_CODE = #{id}" +
            "\t\t\tand ci.death_flag = '1' \n" +
            "\t\t\t\tAND ci.IS_DEL='0'\n" +
            "\t<if test='name !=null and name!=\"\"'>AND ci.name  LIKE concat(#{name},'%')</if>" +
            "</script>")
    List<CivilworkerInfo> findSubCid(@Param("id") String id, @Param("year") String year,
                                     @Param("name") String name,@Param("num")String num);



    @Select("<script>" +
            "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tCIVILWORKER_INFO ci\n" +
            "\tLEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tpsr.CIVILWORKER_ID ,\n" +
            "\t\tpsr.APPLY_DATE\n" +
            "\tFROM\n" +
            "\t\tPERSON_SUBSCRIBE_RECORD psr\n" +
            "\t\tLEFT JOIN CIVILWORKER_INFO ci ON psr.CIVILWORKER_ID = ci.certno AND ci.IS_DEL='0' \n" +
            "\tWHERE\n" +
            "\t\tci.EMP_CODE = #{id} \n" +
            "\t\tand psr.IS_DEL='0'\n" +
            "\t\tand psr.SCHED IN(0,1,2,3)\n" +
            "\t\t<if test='year !=null and year!=\"\"'>AND psr.year = #{year} </if>\n" +
            "\t\t<if test='num !=null and num!=\"\"'>\n" +
            "\t\tHAVING\n" +
            "\t\tCOUNT(1)<![CDATA[  = ]]>#{num}</if>" +
            "\t\tGROUP BY\n" +
            "\t\tpsr.CIVILWORKER_ID ,\n" +
            "\t\tpsr.APPLY_DATE\n" +
            "\t) a ON  a.CIVILWORKER_ID = ci.certno \n" +
            "WHERE\n" +
            "\ta.CIVILWORKER_ID IS NOT NULL \n" +
            "\tAND ci.EMP_CODE = #{id}" +
            "\t\t\tand ci.death_flag = '1' \n" +
            "\t\t\t\tAND ci.IS_DEL='0'\n" +
            "\t<if test='name !=null and name!=\"\"'>AND ci.name  LIKE concat(#{name},'%')</if>" +
            "\t\t\tand (ci.outside_put <![CDATA[  <> ]]>'1' OR ci.outside_put IS NULL)\n" +
            "</script>")
    List<CivilworkerInfo> findSubCidOver(@Param("id") String id, @Param("year") String year,
                                         @Param("name") String name,@Param("num")String num);

    /**
     * 统计
     * 服务排行
     */
    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "getRanking")
    List<PersonSubscribeRecordVo> getRanking(String year);

    /**
     * 统计
     * 服务排行-机构名称
     */
    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "getOrgId")
    String getOrgId(String org_id);

    /**
     * 修改为已上传报告
     */

    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "medicalOver")
    String medicalOver(String id);


    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "yyExport")
    List<PersonSubscribeRecordVo> yyExport(PersonSubscribeRecordVo personSubscribeRecordVo);


    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "noAppointment")
    Page<CivilworkerInfo> noAppointment(Page page, PersonSubscribeRecordVo personSubscribeRecordVo);

    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "adminUnitPhysicalRatioCheckedByOrg")
    List<PhysicalRatioVo> adminUnitPhysicalRatioCheckedByOrg(String year, String empCode, String medicalInsuranceNum);

    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "yearListPhysicalRatioCheckedByOrg")
    List<String> yearListPhysicalRatioCheckedByOrg(String medicalInsuranceNum);


    @Select("SELECT\n" +
            "\tt.TODAY,\n" +
            "  COUNT(1) AS count\n" +
            "FROM\n" +
            "\t( SELECT\n" +
            "\tto_char( SYSDATE - LEVEL + 1, 'yyyy-mm-dd' ) as today \n" +
            "FROM\n" +
            "\tDUAL CONNECT BY LEVEL <= 7) t\n" +
            "\tLEFT JOIN PERSON_SUBSCRIBE_RECORD psr ON  t.TODAY = psr.APPLY_DATE\n" +
            "WHERE\n" +
            "\tAPPLY_DATE IN ( t.TODAY ) \n" +
            "\tAND psr.ORG_ID=#{code}\n" +
            "\tGROUP BY\n" +
            "\tt.TODAY")
    List<EChat> eChatSCHED0(@Param("code") String code);

    @Select("SELECT\n" +
            "\tt.TODAY,\n" +
            "  COUNT(1) AS count\n" +
            "FROM\n" +
            "\t( SELECT\n" +
            "\tto_char( SYSDATE - LEVEL + 1, 'yyyy-mm-dd' ) as today \n" +
            "FROM\n" +
            "\tDUAL CONNECT BY LEVEL <= 7) t\n" +
            "\tLEFT JOIN PERSON_SUBSCRIBE_RECORD psr ON  t.TODAY = psr.APPLY_DATE\n" +
            "WHERE\n" +
            "\tAPPLY_DATE IN ( t.TODAY ) \n" +
            "\tAND psr.ORG_ID=#{code}\n" +
            "\tAND psr.SCHED IN (1,3)\n" +
            "\tGROUP BY\n" +
            "\tt.TODAY")
    List<EChat> eChatSCHED1(@Param("code") String code);

    @Select("SELECT\n" +
            "\tto_char( SYSDATE - LEVEL + 1, 'yyyy-mm-dd' ) today \n" +
            "FROM\n" +
            "\tDUAL CONNECT BY LEVEL <= 7")
    List<Map<String, Object>> eChat1();

    @SelectProvider(type = PersonSubscribeRecordDao.class, method = "countBackOutTimesPersonally")
    Integer  countBackOutTimesPersonally(String civilCertNo, String year);




    @Select("SELECT\n" +
            "\tt.TODAY,\n" +
            "  COUNT(1) AS count\n" +
            "FROM\n" +
            "\t( SELECT\n" +
            "\tto_char( SYSDATE - LEVEL + 1, 'yyyy-mm-dd' ) as today \n" +
            "FROM\n" +
            "\tDUAL CONNECT BY LEVEL <= 30) t\n" +
            "\tLEFT JOIN PERSON_SUBSCRIBE_RECORD psr ON  t.TODAY = psr.APPLY_DATE\n" +
            "WHERE\n" +
            "\tAPPLY_DATE IN ( t.TODAY ) \n" +
            "\tAND psr.IS_DEL=0 \n" +
            "\tGROUP BY\n" +
            "\tt.TODAY")
    List<EChat> eChatSCHEDYB0();

    @Select("SELECT\n" +
            "\tt.TODAY,\n" +
            "  COUNT(1) AS count\n" +
            "FROM\n" +
            "\t( SELECT\n" +
            "\tto_char( SYSDATE - LEVEL + 1, 'yyyy-mm-dd' ) as today \n" +
            "FROM\n" +
            "\tDUAL CONNECT BY LEVEL <= 30) t\n" +
            "\tLEFT JOIN PERSON_SUBSCRIBE_RECORD psr ON  t.TODAY = psr.APPLY_DATE\n" +
            "WHERE\n" +
            "\tAPPLY_DATE IN ( t.TODAY ) \n" +
            "\tAND psr.IS_DEL=0 \n" +
            "\tAND psr.SCHED IN (1,3)\n" +
            "\tGROUP BY\n" +
            "\tt.TODAY")
    List<EChat> eChatSCHEDYB1();

    @Select("SELECT\n" +
            "\tto_char( SYSDATE - LEVEL + 1, 'yyyy-mm-dd' ) today \n" +
            "FROM\n" +
            "\tDUAL CONNECT BY LEVEL <= 30")
    List<Map<String, Object>> eChatYB1();

    @SelectProvider(type = PersonSubscribeRecordDao.class,method = "settlementMore")
    List<PersonSubscribeRecordVo> settlementMore(PersonSubscribeRecordVo personSubscribeRecordVo);
}
