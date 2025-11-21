package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.CivilworkerInfoDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.vo.PhysicalRatioVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface CivilworkerInfoMapper extends BaseMapper<CivilworkerInfo> {


    @SelectProvider(type= CivilworkerInfoDao.class,method = "syncCivilData")
    List<CivilworkerInfo> syncCivilData(String type,String updateTime);

    /**
     * 统计 体检占比
     * @param year
     * @param deptNo
     * @return
     */
    @SelectProvider(type= CivilworkerInfoDao.class,method = "getPhysicalRatio")
    List<PhysicalRatioVo> getPhysicalRatio(String year,String deptNo);

    @Select("<script>" +
            "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tCIVILWORKER_INFO \n" +
            "WHERE\n" +
            "\tCERTNO = #{num}" +
            "\tand is_del = 0" +
            "\t<if test='code !=null and code!=\"\"'>and emp_code=#{code}</if>"+
            "</script>")
    CivilworkerInfo selectCivInfo(@Param("num") String num,@Param("code")String code);

    @SelectProvider(type= CivilworkerInfoDao.class,method = "civiList")
    Page<CivilworkerInfo> civiList(Page page, CivilworkerInfo civilworkerInfo, SysUser sysUser);
}
