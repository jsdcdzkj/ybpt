package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.FixmedinsBDao;
import com.jsdc.ybpt.dao.LoanApplicationDao;
import com.jsdc.ybpt.model.LoanApplication;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.vo.BankVo;
import com.jsdc.ybpt.vo.SetlVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface LoanApplicationMapper extends BaseMapper<LoanApplication> {

    @SelectProvider(type = LoanApplicationDao.class, method = "billingData")
    SetlVo billingData(String fixmedins_code);



    @SelectProvider(type = LoanApplicationDao.class, method = "bankTj")
    BankVo bankTj(String bankNo);


}
