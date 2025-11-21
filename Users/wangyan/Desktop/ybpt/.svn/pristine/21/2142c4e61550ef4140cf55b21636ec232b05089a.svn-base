package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.ReflAppyEvtCDao;
import com.jsdc.ybpt.model.ReflAppyEvtC;
import com.jsdc.ybpt.vo.NatDataDicAByAdmdvsVo;
import com.jsdc.ybpt.vo.ReflAppyEvtCVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReflAppyEvtCMapper extends BaseMapper<ReflAppyEvtC> {

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getNatDataDicA")
  List<Map> getNatDataDicA(ReflAppyEvtCVo vo);

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getDictListBycode")
  List<ReflAppyEvtCVo> getDictListBycode(ReflAppyEvtCVo vo);


  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getNatDataDicAByAdmdvs")
  List<NatDataDicAByAdmdvsVo> getNatDataDicAByAdmdvs(ReflAppyEvtCVo vo);

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getPsnInfoB")
  Map getPsnInfoB(ReflAppyEvtCVo vo);

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getMedinsInfoB")
  Page<Map> getMedinsInfoB(Page page, ReflAppyEvtCVo vo);

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getReflAppyEvtC")
  Page<ReflAppyEvtC> getReflAppyEvtC(Page page, ReflAppyEvtCVo vo);

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getMdtrtD")
  Page<Map> getMdtrtD(Page page, ReflAppyEvtCVo vo);

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "reflAppyEvtC")
  ReflAppyEvtC reflAppyEvtC(ReflAppyEvtCVo vo);

  @SelectProvider(type = ReflAppyEvtCDao.class, method = "getDiagList")
  Page<Map> getDiagList(Page page, ReflAppyEvtCVo vo);
}
