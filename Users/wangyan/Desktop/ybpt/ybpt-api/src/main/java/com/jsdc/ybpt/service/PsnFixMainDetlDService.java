package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.PsnFixMainDetlDMapper;
import com.jsdc.ybpt.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PsnFixMainDetlDService extends BaseService<PsnFixMainDetlDVo> {

    @Autowired
    private PsnFixMainDetlDMapper psnFixMainDetlDMapper ;

    @Autowired
    private SysDictService sysDictService ;

    @DS("reflowData")
    public Page<PsnFixMainDetlDVo> selectPsnList(PsnFixMainDetlDVo psnFixMainDetlDVo) {
        //统计回流库数据
        Page<PsnFixMainDetlDVo> page = new Page<>(psnFixMainDetlDVo.getPageNo(), psnFixMainDetlDVo.getPageSize());
        Page<PsnFixMainDetlDVo> psnList = psnFixMainDetlDMapper.selectPsnList(page, psnFixMainDetlDVo);
        return psnList;
    }

    @DS("reflowData")
    public Page<MdtrtDVo> consultationInfo(MdtrtDVo mdtrtDVo) {
        //统计回流库数据
        Page<MdtrtDVo> page = new Page<>(mdtrtDVo.getPageNo(), mdtrtDVo.getPageSize());
        Page<MdtrtDVo> mdtrtDVoPage = psnFixMainDetlDMapper.consultationInfo(page, mdtrtDVo);
        return mdtrtDVoPage;
    }

    @DS("reflowData")
    public Page<FeeListDVo> chargeDetails(FeeListDVo feeListDVo) {
        //统计回流库数据
        Page<FeeListDVo> page = new Page<>(feeListDVo.getPageNo(), feeListDVo.getPageSize());
        Page<FeeListDVo> feeListDVoPage = psnFixMainDetlDMapper.chargeDetails(page, feeListDVo);
        return feeListDVoPage;
    }

    @DS("reflowData")
    public Page<MdcsFundSetlListVo> listInformation(MdcsFundSetlListVo mdcsFundSetlListVo) {
        //统计回流库数据
        Page<MdcsFundSetlListVo> page = new Page<>(mdcsFundSetlListVo.getPageNo(), mdcsFundSetlListVo.getPageSize());
        Page<MdcsFundSetlListVo> mdcsFundSetlListVoPage = psnFixMainDetlDMapper.listInformation(page, mdcsFundSetlListVo);


        return mdcsFundSetlListVoPage;
    }

    @DS("reflowData")
    public Page<IptMedcasBasVo> beInHospitalInfo(IptMedcasBasVo iptMedcasBasVo) {
        //统计回流库数据
        Page<IptMedcasBasVo> page = new Page<>(iptMedcasBasVo.getPageNo(), iptMedcasBasVo.getPageSize());
        Page<IptMedcasBasVo> iptMedcasBasVoPage = psnFixMainDetlDMapper.beInHospitalInfo(page, iptMedcasBasVo);
        return iptMedcasBasVoPage;
    }

    @DS("reflowData")
    public Page<MedDfrDVo> medDfrDInfo(MedDfrDVo medDfrDVo) {
        //统计回流库数据
        Page<MedDfrDVo> page = new Page<>(medDfrDVo.getPageNo(), medDfrDVo.getPageSize());
        Page<MedDfrDVo> medDfrDVoPage = psnFixMainDetlDMapper.medDfrDInfo(page, medDfrDVo);
        return medDfrDVoPage;
    }

    @DS("reflowData")
    public Page<OutmedMdtrtVo> outmedMdtrt(OutmedMdtrtVo outmedMdtrtVo) {
        //统计回流库数据
        Page<OutmedMdtrtVo> page = new Page<>(outmedMdtrtVo.getPageNo(), outmedMdtrtVo.getPageSize());
        Page<OutmedMdtrtVo> outmedMdtrtVoPage = psnFixMainDetlDMapper.outmedMdtrt(page, outmedMdtrtVo);
        return outmedMdtrtVoPage;
    }

    @DS("reflowData")
    public Page<OutmedFeeListVo> outmedFeeList(OutmedFeeListVo outmedFeeListVo) {
        //统计回流库数据
        Page<OutmedFeeListVo> page = new Page<>(outmedFeeListVo.getPageNo(), outmedFeeListVo.getPageSize());
        Page<OutmedFeeListVo> outmedFeeListVoPage = psnFixMainDetlDMapper.outmedFeeList(page, outmedFeeListVo);
        return outmedFeeListVoPage;
    }

    @DS("reflowData")
    public Page<RpotInfoVo> reportInfo(RpotInfoVo rpotInfoVo) {
        //统计回流库数据
        Page<RpotInfoVo> page = new Page<>(rpotInfoVo.getPageNo(), rpotInfoVo.getPageSize());
        Page<RpotInfoVo> rpotInfoVoPage = psnFixMainDetlDMapper.reportInfo(page,rpotInfoVo);
        return rpotInfoVoPage;
    }


    @DS("reflowData")
    public Page<SetlDVo> setlList(SetlDVo setlDVo) {
        //统计回流库数据
        Page<SetlDVo> page = new Page<>(setlDVo.getPageNo(), setlDVo.getPageSize());
        Page<SetlDVo> setlDVoPage = psnFixMainDetlDMapper.setlList(page,setlDVo);
        return setlDVoPage;
    }


    @DS("reflowData")
    public Page<RxInfoVo> rxInfoList(RxInfoVo rxInfoVo) {
        //统计回流库数据
        Page<RxInfoVo> page = new Page<>(rxInfoVo.getPageNo(), rxInfoVo.getPageSize());
        Page<RxInfoVo> rxInfoVoPage = psnFixMainDetlDMapper.rxInfoList(page,rxInfoVo);
        return rxInfoVoPage;
    }

    @DS("reflowData")
    public Page<ImgInfoVo> imgList(ImgInfoVo imgInfoVo) {
        //统计回流库数据
        Page<ImgInfoVo> page = new Page<>(imgInfoVo.getPageNo(), imgInfoVo.getPageSize());
        Page<ImgInfoVo> imgInfoVoPage = psnFixMainDetlDMapper.imgList(page,imgInfoVo);
        return imgInfoVoPage;
    }


}
