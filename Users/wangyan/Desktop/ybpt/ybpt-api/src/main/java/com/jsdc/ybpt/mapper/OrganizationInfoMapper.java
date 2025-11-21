package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.OrganizationInfoDao;
import com.jsdc.ybpt.dao.PersonSubscribeRecordDao;
import com.jsdc.ybpt.model_check.OrganizationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangYan
 * @since 2022-05-24
 */
@Mapper
public interface OrganizationInfoMapper extends BaseMapper<OrganizationInfo> {

//    @SelectProvider(type = OrganizationInfoDao.class, method = "selectTJ")
//    OrganizationInfo selectTJ(String orgCode);

}
