package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagAgreement;
import com.jsdc.ybpt.mapper.MedicalOrgMapper;
import com.jsdc.ybpt.model_query.medicalOrg.MedicalDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("reflowData")
public class MedicalOrgService {
    @Autowired
    private MedicalOrgMapper medicalOrgMapper;

    public Page<MedicalDept> selectMedicalDept_page(Integer pageNo, Integer pageSize, String fixmedins_code, String fixmedins_name, String dept_no, String dept_name) {
        Page<MedicalDept> page = new Page(pageNo, pageSize);
        return medicalOrgMapper.selectMedicalDept_page(page, fixmedins_code, fixmedins_name, dept_no, dept_name);
    }

    public List<MedicalDept> selectMedicalDept_excel(String fixmedins_code, String fixmedins_name, String dept_no, String dept_name) {
        return medicalOrgMapper.selectMedicalDept_excel(null, fixmedins_code, fixmedins_name, dept_no, dept_name);
    }

    //根据编码获取详细信息 医疗机构信息
    public MedicalDept selectByMedicalOrgId(String fixmedins_code) {
        return medicalOrgMapper.selectByMedicalOrgId(fixmedins_code);
    }

    //根据编码获取详细信息
    public MedicalDept getRtalPhacB(String fixmedins_code) {
        return medicalOrgMapper.getRtalPhacB(fixmedins_code);
    }

    //获取零售药店和医疗机构等级数据
    public List<MedicalDept> selectOrganizationLevel(String type) {
        return medicalOrgMapper.selectOrganizationLevel(type);
    }

    //根据等级获取医疗机构信息
    public List<MedicalDept> selectByMedicalLevel(NetTagAgreement netTagAgreement) {
        return medicalOrgMapper.selectByMedicalLevel(netTagAgreement);
    }

    //根据药店等级获取零售药店信息
    public List<MedicalDept> selectPharmacyLevel(NetTagAgreement netTagAgreement) {
        return medicalOrgMapper.selectPharmacyLevel(netTagAgreement);
    }
}
