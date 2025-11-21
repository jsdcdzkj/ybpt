package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.FixmedinsBDao;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.LoanApplication;
import com.jsdc.ybpt.model_query.MedinsInfoB;
import com.jsdc.ybpt.model_query.RtalPhacB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:40
 * @Version 1.0
 */
@Mapper
public interface FixmedinsBMapper extends BaseMapper<FixmedinsB> {
    @SelectProvider(type= FixmedinsBDao.class,method = "selectByCode")
    List<FixmedinsB> selectByCode(FixmedinsB fixmedinsB);

    @SelectProvider(type= FixmedinsBDao.class,method = "selectRtalPhacByCode")
    RtalPhacB selectRtalPhacByCode(RtalPhacB rtalPhacB);

    @SelectProvider(type= FixmedinsBDao.class,method = "selectMedinsInfoByCode")
    MedinsInfoB selectMedinsInfoByCode(MedinsInfoB medinsInfoB);



    @SelectProvider(type= FixmedinsBDao.class,method = "dkApplySelect")
    LoanApplication dkApplySelect(LoanApplication loanApplication);

    @SelectProvider(type= FixmedinsBDao.class,method = "ydApplyInfoList")
    LoanApplication ydApplyInfoList(LoanApplication loanApplication);
    /**
     * 根据编码查询零售药店数据
     * @authon zln
     * @param mechanism_code
     * @param fixmedins_type
     * @return
     */
    @SelectProvider(type= FixmedinsBDao.class,method = "selectByYSYD")
    FixmedinsB selectByYSYD(String mechanism_code,String fixmedins_type);

    @SelectProvider(type= FixmedinsBDao.class,method = "pldr")
    List<FixmedinsB> pldr();

}
