package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.PackInfoDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.model_check.PackInfo;
import com.jsdc.ybpt.vo.PackInfoChartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangYan
 * @since 2022-05-24
 */
@Mapper
public interface PackInfoMapper extends BaseMapper<PackInfo> {
    @SelectProvider(type = PackInfoDao.class, method = "getPackInfoRatio")
    List<PackInfoChartVo> getPackInfoRatio(String year, String orgId, SysUser sysUser, OrganizationInfo organizationInfo);


    @Select("<script>" +
            "SELECT\n" +
            "\tcount( 1 )  AS count,\n" +
            "\tORG_ID,\n" +
            "\tORG_NAME \n" +
            "FROM\n" +
            "\tPACK_INFO \n" +
            "WHERE\n" +
            "\tIF_USE = 1 \n" +
            "\tAND IF_OPEN = 1 \n" +
            "\tAND IS_DEL = 0 \n" +
            "\t<if test='year !=null and year!=\"\"'>AND PACK_YEAR = #{year}</if> \n" +
            "\t<if test='source !=null and source!=\"\"'>AND PACK_SOURCE = #{source}</if>\n" +
            "\tAND STATUS = 1 \n" +
            "GROUP BY\n" +
            "\tORG_ID,\n" +
            "\tORG_NAME"+
            "</script>" )
    List<PackInfo>getOrgList(@Param("year")String year,@Param("source")String source);
}
