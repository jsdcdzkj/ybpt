package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagAgreement;
import com.jsdc.ybpt.dao.MedicalOrgDao;
import com.jsdc.ybpt.model_query.medicalOrg.MedicalDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface MedicalOrgMapper extends BaseMapper<MedicalDept> {
    @SelectProvider(type = MedicalOrgDao.class, method = "selectMedicalDept")
    Page<MedicalDept> selectMedicalDept_page(Page page, String fixmedins_code, String fixmedins_name, String dept_no, String dept_name);

    @SelectProvider(type = MedicalOrgDao.class, method = "selectMedicalDept")
    List<MedicalDept> selectMedicalDept_excel(Page page, String fixmedins_code, String fixmedins_name, String dept_no, String dept_name);

    //根据编码获取详细信息 医疗机构信息
    @SelectProvider(type = MedicalOrgDao.class, method = "selectByMedicalOrgId")
    MedicalDept selectByMedicalOrgId(String fixmedins_code);

    //根据编码获取详细信息 零售药店信息
    @SelectProvider(type = MedicalOrgDao.class, method = "getRtalPhacB")
    MedicalDept getRtalPhacB(String fixmedins_code);

    //获取零售药店和医疗机构等级数据
    @SelectProvider(type = MedicalOrgDao.class, method = "selectOrganizationLevel")
    List<MedicalDept> selectOrganizationLevel(String type);

    //根据等级获取医疗机构信息
    @SelectProvider(type = MedicalOrgDao.class, method = "selectByMedicalLevel")
    List<MedicalDept> selectByMedicalLevel(NetTagAgreement netTagAgreement);

    //根据药店等级获取零售药店信息
    @SelectProvider(type = MedicalOrgDao.class, method = "selectPharmacyLevel")
    List<MedicalDept> selectPharmacyLevel(NetTagAgreement netTagAgreement);
}
