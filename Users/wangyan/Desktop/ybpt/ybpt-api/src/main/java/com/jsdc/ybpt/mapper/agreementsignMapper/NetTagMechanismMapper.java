package com.jsdc.ybpt.mapper.agreementsignMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagMechanism;
import com.jsdc.ybpt.dao.agreementsignDao.NetTagMechanismDao;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.vo.agreementsignVo.FixmedinsBVo;
import com.jsdc.ybpt.vo.agreementsignVo.NetTagMechanismVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface NetTagMechanismMapper extends BaseMapper<NetTagMechanism> {

    @SelectProvider(type= NetTagMechanismDao.class,method = "selectPageList")
    Page<NetTagMechanismVo> selectPageList(Page page, NetTagMechanismVo vo);

    @SelectProvider(type= NetTagMechanismDao.class,method = "getOrgUnSeal")
    Page<FixmedinsB> getOrgUnSeal(Page page, NetTagMechanismVo vo);

    @SelectProvider(type= NetTagMechanismDao.class,method = "getOrgUnSealList")
    List<FixmedinsB> getOrgUnSealList(NetTagMechanismVo vo);

}
