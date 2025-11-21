package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.OpspDiseDao;
import com.jsdc.ybpt.dao.OpspRegdDao;
import com.jsdc.ybpt.model.OpspDise;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import com.jsdc.ybpt.vo.OpspRegDVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface OpspDiseMapper extends BaseMapper<OpspDise> {
    @SelectProvider(type= OpspDiseDao.class,method = "selectDiseList")
    Page<OpspDise> selectDiseList(Page page, OpspDise opspDise);


    @SelectProvider(type= OpspRegdDao.class,method = "selectCompanyByIdCard")
    List<OpspRegDVo> selectCompanyByIdCard(String certno, String psn_cert_type);

    @SelectProvider(type= OpspDiseDao.class,method = "queryAssociation")
    List<OpspDise> queryAssociation(String certNo,String opsp_dise_code);

    @SelectProvider(type= OpspDiseDao.class,method = "opspCheckList")
    Page<OpspDise> opspCheckList(Page page, OpspRegDVo opspRegDVo);


    @SelectProvider(type= OpspDiseDao.class,method = "selectOpspREGByCertNo")
    List<OpspDiseListBVo> selectOpspREGByCertNo(String certno);

    @SelectProvider(type= OpspDiseDao.class,method = "selectBydiseByCertNo")
    List<OpspDiseListBVo> selectBydiseByCertNo(String certno);



}
