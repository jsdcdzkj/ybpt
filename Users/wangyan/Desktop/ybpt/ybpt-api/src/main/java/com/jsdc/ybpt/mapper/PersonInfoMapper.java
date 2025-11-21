package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.PersonInfoDao;
import com.jsdc.ybpt.model_query.*;
import com.jsdc.ybpt.model_query.settlement.MzSettlement;
import com.jsdc.ybpt.model_query.settlement.MzSettlementDetails;
import com.jsdc.ybpt.model_query.settlement.PersonalSettlement;
import com.jsdc.ybpt.vo.PersonalSettlementVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface PersonInfoMapper extends BaseMapper<PersonInfo> {

    @SelectProvider(type = PersonInfoDao.class, method = "selectPersonInfo")
    Page<PersonInfo> selectPersonInfo(Page page,String certno, String cardType, String psn_name, String psn_no, String mob);

    @SelectProvider(type = PersonInfoDao.class, method = "getPersonInfoByCarno")
    PersonInfo getPersonInfoByCarno(String carno);

    @SelectProvider(type = PersonInfoDao.class, method = "getPersonInfoByCarnos")
    List<PersonInfo> getPersonInfoByCarnos(String carno);

    @SelectProvider(type = PersonInfoDao.class, method = "getPersonInfoByCarnoOne")
    PersonInfo getPersonInfoByCarnoOne(String carno);

    @SelectProvider(type = PersonInfoDao.class, method = "getEmpInfo")
    Page<EmpPayInfo> getEmpInfo(Page page, EmpPayInfo empPayInfo);

    @SelectProvider(type = PersonInfoDao.class, method = "getStaffPayinfo")
    Page<StaffPayinfo> getStaffPayinfo(Page page, StaffPayinfo staffPayinfo);

    @SelectProvider(type = PersonInfoDao.class, method = "getResidentPayinfo")
    Page<ResidentPayinfo> getResidentPayinfo(Page page, ResidentPayinfo residentPayinfo);

    @SelectProvider(type = PersonInfoDao.class, method = "getAccountDetails")
    Page<AccountDetails> getAccountDetails(Page page, AccountDetails accountDetails);

    @SelectProvider(type = PersonInfoDao.class, method = "selectPersonalSettlement")
    Page<PersonalSettlement> selectPersonalSettlement_page(Page page, PersonalSettlementVo personalSettlementVo);

    @SelectProvider(type = PersonInfoDao.class, method = "selectPersonalSettlement")
    List<PersonalSettlement> selectPersonalSettlement_excel(Page page, PersonalSettlementVo personalSettlementVo);

    @SelectProvider(type = PersonInfoDao.class, method = "selectMzSettlement")
    Page<MzSettlement> selectMzSettlement_page(Page page, PersonalSettlementVo personalSettlementVo);

    @SelectProvider(type = PersonInfoDao.class, method = "selectMzSettlement")
    List<MzSettlement> selectMzSettlement_excel(Page page, PersonalSettlementVo personalSettlementVo);
    @SelectProvider(type = PersonInfoDao.class, method = "selectMzSettleDetails")
    List<MzSettlementDetails> selectMzSettleDetails(String setlId);
}
