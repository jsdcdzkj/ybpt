package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.MedinsInfoBDao;
import com.jsdc.ybpt.vo.MedinsInfoBVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @ClassName MedinsInfoBMapper
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:40
 * @Version 1.0
 */
@Mapper
public interface MedinsInfoBMapper extends BaseMapper<MedinsInfoBVo> {
    @SelectProvider(type= MedinsInfoBDao.class,method = "selectMedinsInfo")
    Page<MedinsInfoBVo> selectMedinsInfo(Page page,String medins_code,String medins_name);

    @SelectProvider(type= MedinsInfoBDao.class,method = "selectMedinsInfoType")
    Page<MedinsInfoBVo> selectMedinsInfoType(Page page,String medins_code,String medins_name,String fixmedins_type);

    @SelectProvider(type= MedinsInfoBDao.class,method = "selectEmpList")
    Page<MedinsInfoBVo> selectEmpList(Page page,String medins_code,String medins_name);

    @SelectProvider(type= MedinsInfoBDao.class,method = "selectAdminUnitList")
    Page<MedinsInfoBVo> selectAdminUnitList(Page page,String medins_code,String medins_name);

    @SelectProvider(type= MedinsInfoBDao.class,method = "selectOrgList")
    Page<MedinsInfoBVo> selectOrgList(Page page,String medins_code,String medins_name);

    @SelectProvider(type= MedinsInfoBDao.class,method = "selectBankList")
    Page<MedinsInfoBVo> selectBankList(Page page,String medins_code,String medins_name);


    @SelectProvider(type= MedinsInfoBDao.class,method = "selectFdd")
    Page<MedinsInfoBVo> selectFdd(Page page,String medins_code,String medins_name,String fixmedins_type);


}
