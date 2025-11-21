package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.ReimbursementDao;
import com.jsdc.ybpt.model_query.reimbursement.BirthSettlement;
import com.jsdc.ybpt.model_query.reimbursement.BirthSettlement_org;
import com.jsdc.ybpt.vo.PharInfoBVo;
import com.jsdc.ybpt.vo.ReimbursementQuery;
import com.jsdc.ybpt.vo.ReimbursementQuery_org;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ReimbursementMapper extends BaseMapper<BirthSettlement> {
    @SelectProvider(type= ReimbursementDao.class,method = "selectBirthSettlement")
    Page<BirthSettlement> selectBirthSettlement(Page page, ReimbursementQuery reimbursementQuery);

    @SelectProvider(type= ReimbursementDao.class,method = "selectBirthSettlement")
    List<BirthSettlement> birthSettlement_excel(Page page,ReimbursementQuery reimbursementQuery);

    @SelectProvider(type= ReimbursementDao.class,method = "selectBirthSettlement_org")
    Page<BirthSettlement_org> selectBirthSettlement_org_page(Page page, ReimbursementQuery_org reimbursementQuery_org);

    @SelectProvider(type= ReimbursementDao.class,method = "selectBirthSettlement_org")
    List<BirthSettlement_org> selectBirthSettlement_org_excel(Page page, ReimbursementQuery_org reimbursementQuery_org);
}
