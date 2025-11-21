package com.jsdc.ybpt.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jsdc.ybpt.mapper.AgreementMapper;
import com.jsdc.ybpt.model_check.Agreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementService {
    @Autowired
    private AgreementMapper mapper;
    public Agreement getAgreement() {
        return this.mapper.getFirstRecord();
    }

    public boolean saveOrUpdate(Agreement agreement) {
        return agreement.insertOrUpdate();
    }
}
