package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.ReimbursementMapper;
import com.jsdc.ybpt.model_query.reimbursement.BirthSettlement;
import com.jsdc.ybpt.model_query.reimbursement.BirthSettlement_org;
import com.jsdc.ybpt.vo.PharInfoBVo;
import com.jsdc.ybpt.vo.ReimbursementQuery;
import com.jsdc.ybpt.vo.ReimbursementQuery_org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("reflowData")
public class ReimbursementService {
    @Autowired
    private ReimbursementMapper reimbursementMapper;

    public Page<BirthSettlement> selectBirthSettlement(ReimbursementQuery reimbursementQuery){
        Page<BirthSettlement> page = new Page(reimbursementQuery.getPageNo(),reimbursementQuery.getPageSize());
        return reimbursementMapper.selectBirthSettlement(page,reimbursementQuery);
    }

    public List<BirthSettlement> birthSettlement_excel(ReimbursementQuery reimbursementQuery){
        return reimbursementMapper.birthSettlement_excel(null,reimbursementQuery);
    }

    public Page<BirthSettlement_org> selectBirthSettlement_org_page(ReimbursementQuery_org reimbursementQuery_org){
        Page<BirthSettlement> page = new Page(reimbursementQuery_org.getPageNo(),reimbursementQuery_org.getPageSize());
        return reimbursementMapper.selectBirthSettlement_org_page(page,reimbursementQuery_org);
    }

    public List<BirthSettlement_org> selectBirthSettlement_org_excel(ReimbursementQuery_org reimbursementQuery_org){
        return reimbursementMapper.selectBirthSettlement_org_excel(null,reimbursementQuery_org);
    }
}
