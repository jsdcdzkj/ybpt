package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.PharInfoBDao;
import com.jsdc.ybpt.dao.PsnFxMainDetlDDao;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface PsnFixMainDetlDMapper extends BaseMapper<PsnFixMainDetlDVo> {
    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "selectPsnList")
    Page<PsnFixMainDetlDVo> selectPsnList(Page page,PsnFixMainDetlDVo psnFixMainDetlDVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "consultationInfo")
    Page<MdtrtDVo> consultationInfo(Page page,MdtrtDVo mdtrtDVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "chargeDetails")
    Page<FeeListDVo> chargeDetails(Page page,FeeListDVo feeListDVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "listInformation")
    Page<MdcsFundSetlListVo> listInformation(Page page, MdcsFundSetlListVo mdcsFundSetlListVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "beInHospitalInfo")
    Page<IptMedcasBasVo> beInHospitalInfo(Page page, IptMedcasBasVo iptMedcasBasVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "medDfrDInfo")
    Page<MedDfrDVo> medDfrDInfo(Page page, MedDfrDVo medDfrDVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "outmedMdtrt")
    Page<OutmedMdtrtVo> outmedMdtrt(Page page,OutmedMdtrtVo outmedMdtrtVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "outmedFeeList")
    Page<OutmedFeeListVo> outmedFeeList(Page page,OutmedFeeListVo outmedFeeListVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "reportInfo")
    Page<RpotInfoVo> reportInfo(Page page,RpotInfoVo rpotInfoVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "setlList")
    Page<SetlDVo> setlList(Page page,SetlDVo setlDVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "rxInfoList")
    Page<RxInfoVo> rxInfoList(Page page,RxInfoVo rxInfoVo);

    @SelectProvider(type= PsnFxMainDetlDDao.class,method = "imgList")
    Page<ImgInfoVo> imgList(Page page,ImgInfoVo imgInfoVo);


}
