package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.MedicalItemDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.MedicalItem;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import com.jsdc.ybpt.vo.MedicalItemVo;
import org.apache.ibatis.annotations.Mapper;
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
public interface MedicalItemMapper extends BaseMapper<MedicalItem> {

    @Select("SELECT\n" +
            "\tmi.* ,\n" +
            "\tpi.ID AS pid\n" +
            "FROM\n" +
            "\tPACK_INFO pi\n" +
            "\tLEFT JOIN ITEM_TO_MEAL itm ON itm.PACK_ID = pi.ID\n" +
            "\tLEFT JOIN MEDICAL_ITEM mi ON mi.id = itm.ITEM_ID \n" +
            "WHERE\n" +
            " pi.IS_DEL=0\n" +
            " AND pi.PACK_SOURCE=1")
    List<MedicalItem> findMedicalItemAll();

    @SelectProvider(type = MedicalItemDao.class, method = "getMedicalItemListOfYbByPackYear")
    List<String> getMedicalItemListOfYbByPackYear(String medicalItemYear);

    @SelectProvider(type = MedicalItemDao.class,method ="getListUnion" )
    List<MedicalItem> getListUnion(SysUser sysUser, MedicalItemVo vo, OrganizationInfo organizationInfo);
}
