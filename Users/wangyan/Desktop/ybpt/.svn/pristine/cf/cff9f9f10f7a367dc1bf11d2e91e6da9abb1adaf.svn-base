package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.PersonInfoMapper;
import com.jsdc.ybpt.model_query.*;
import com.jsdc.ybpt.model_query.settlement.MzSettlement;
import com.jsdc.ybpt.model_query.settlement.MzSettlementDetails;
import com.jsdc.ybpt.model_query.settlement.PersonalSettlement;
import com.jsdc.ybpt.vo.PersonalSettlementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@DS("reflowData")
public class PersonInfoService extends BaseService<PersonInfo> {
    @Autowired
    private PersonInfoMapper personInfoMapper;

    /**
     * 人员信息列表
     * @param pageNo
     * @param pageSize
     * @param certno
     * @param cardType
     * @return
     */
    public Page<PersonInfo> selectPersonInfo(Integer pageNo, Integer pageSize, String certno, String cardType, String psn_name, String psn_no, String mob){
        Page page = new Page(pageNo,pageSize);
        return personInfoMapper.selectPersonInfo(page,certno, cardType, psn_name, psn_no, mob);
    }

    /**
     * 人员信息详情
     * @param carno
     * @return
     */
    public PersonInfo getPersonInfoByCarno(String carno){
        return personInfoMapper.getPersonInfoByCarno(carno);
    };

    /**
     * 单位缴费明细
     * @return
     */
    public Page<EmpPayInfo> getEmpInfo(EmpPayInfo empPayInfo){
        Page page = new Page(empPayInfo.getPageNo(),empPayInfo.getPageSize());
        return personInfoMapper.getEmpInfo(page,empPayInfo);
    }

    /**
     * 职工缴费明细
     * @return
     */
    public Page<StaffPayinfo> getStaffPayinfo(StaffPayinfo staffPayinfo){
        Page page = new Page(staffPayinfo.getPageNo(),staffPayinfo.getPageSize());
        return personInfoMapper.getStaffPayinfo(page,staffPayinfo);
    }

    /**
     * 居民缴费明细
     * @return
     */
    public Page<ResidentPayinfo> getResidentPayinfo(ResidentPayinfo residentPayinfo){
        Page page = new Page(residentPayinfo.getPageNo(),residentPayinfo.getPageSize());
        return personInfoMapper.getResidentPayinfo(page,residentPayinfo);
    }

    /**
     * 个人收入明细
     * @return
     */
    public Page<AccountDetails> getAccountDetails(AccountDetails accountDetails){
        Page page = new Page(accountDetails.getPageNo(),accountDetails.getPageSize());
        return personInfoMapper.getAccountDetails(page,accountDetails);
    }

    /**
     * 个人结算查询分页
     * @param personalSettlementVo
     * @return
     */
    public Page<PersonalSettlement> selectPersonalSettlement_page(PersonalSettlementVo personalSettlementVo){
        Page<PersonalSettlement> page = new Page(personalSettlementVo.getPageNo(),personalSettlementVo.getPageSize());
        return personInfoMapper.selectPersonalSettlement_page(page,personalSettlementVo);
    }

    /**
     * 个人结算查询 数据
     * @param personalSettlementVo
     * @return
     */
    public List<PersonalSettlement> selectPersonalSettlement_excel(PersonalSettlementVo personalSettlementVo){
        return personInfoMapper.selectPersonalSettlement_excel(null,personalSettlementVo);
    }
    /**
     * 门诊结算查询分页
     * @param personalSettlementVo
     * @return
     */
    public Page<MzSettlement> selectMzSettlement_page(PersonalSettlementVo personalSettlementVo){
        Page<PersonalSettlement> page = new Page(personalSettlementVo.getPageNo(),personalSettlementVo.getPageSize());
        return personInfoMapper.selectMzSettlement_page(page,personalSettlementVo);
    }

    /**
     * 门诊结算查询 数据
     * @param personalSettlementVo
     * @return
     */
    public List<MzSettlement> selectMzSettlement_excel(PersonalSettlementVo personalSettlementVo){
        return personInfoMapper.selectMzSettlement_excel(null,personalSettlementVo);
    }

    /**
     * 查询门诊计算详情
     * @param setlId
     * @return
     */
    public List<MzSettlementDetails> selectMzSettleDetails(String setlId){
        return personInfoMapper.selectMzSettleDetails(setlId);
    }
}
