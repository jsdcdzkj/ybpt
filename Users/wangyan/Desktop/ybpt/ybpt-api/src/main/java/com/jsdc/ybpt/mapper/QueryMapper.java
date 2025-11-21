package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.QueryDao;
import com.jsdc.ybpt.model_query.*;
import com.jsdc.ybpt.model_query.medicalOrg.FixmedinsCntrRegD;
import com.jsdc.ybpt.model_query.medicalOrg.FixmedinsGather;
import com.jsdc.ybpt.model_query.medicalOrg.School;
import com.jsdc.ybpt.model_query.personnel.OrganizationGinseng;
import com.jsdc.ybpt.model_query.personnel.PersonalChanges;
import com.jsdc.ybpt.model_query.personnel.ReflAppyEvtCVo;
import com.jsdc.ybpt.model_query.settlement.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface QueryMapper {

    @SelectProvider(type = QueryDao.class, method = "drInfoB")
    Page<DrInfoB> drInfoB(Page page, DrInfoB drInfoB);

    @SelectProvider(type = QueryDao.class, method = "empInsuD")
    Page<EmpInsuD> empInsuD(Page page, EmpInsuD empInsuD);

    @SelectProvider(type = QueryDao.class, method = "insuEmpInfoB")
    Page<InsuEmpInfoB> insuEmpInfoB(Page page, InsuEmpInfoB insuEmpInfoB);

    @SelectProvider(type = QueryDao.class, method = "medinsInfoB")
    Page<MedinsInfoB> medinsInfoB(Page page, MedinsInfoB medinsInfoB);

    @SelectProvider(type = QueryDao.class, method = "medTecnInfoB")
    Page<MedTecnInfoB> medTecnInfoB(Page page, MedTecnInfoB medTecnInfoB);

    @SelectProvider(type = QueryDao.class, method = "nursInfoB")
    Page<NursInfoB> nursInfoB(Page page, NursInfoB nursInfoB);

    @SelectProvider(type = QueryDao.class, method = "opspRegEvtC")
    Page<OpspRegEvtC> opspRegEvtC(Page page, OpspRegEvtC opspRegEvtC);

    @SelectProvider(type = QueryDao.class, method = "optPsnB")
    Page<OptPsnB> optPsnB(Page page, OptPsnB optPsnB);

    @SelectProvider(type = QueryDao.class, method = "outAppyEvtC")
    Page<OutAppyEvtC> outAppyEvtC(Page page, OutAppyEvtC outAppyEvtC);

    @SelectProvider(type = QueryDao.class, method = "pharInfoB")
    Page<PharInfoB> pharInfoB(Page page, PharInfoB pharInfoB);

    @SelectProvider(type = QueryDao.class, method = "profInfoB")
    Page<ProfInfoB> profInfoB(Page page, ProfInfoB profInfoB);

    @SelectProvider(type = QueryDao.class, method = "psnClctstdD")
    Page<PsnClctstdD> psnClctstdD(Page page, PsnClctstdD psnClctstdD);

    @SelectProvider(type = QueryDao.class, method = "psnInsuD")
    Page<PsnInsuD> psnInsuD(Page page, PsnInsuD psnInsuD);

    @SelectProvider(type = QueryDao.class, method = "psnInsuStasB")
    Page<PsnInsuStasB> psnInsuStasB(Page page, PsnInsuStasB psnInsuStasB);

    @SelectProvider(type = QueryDao.class, method = "rtalPhacB")
    Page<RtalPhacB> rtalPhacB(Page page, RtalPhacB rtalPhacB);

    @SelectProvider(type = QueryDao.class, method = "spdrugApprFilEvtC")
    Page<SpdrugApprFilEvtC> spdrugApprFilEvtC(Page page, SpdrugApprFilEvtC spdrugApprFilEvtC);

    @SelectProvider(type = QueryDao.class, method = "setlD")
    Page<SetlD> setlD(Page page, SetlD setlD);

    @SelectProvider(type = QueryDao.class, method = "setlDByDiseNoPay")
    Page<SetlDPay> setlDByDiseNoPay(Page page, SetlDPay setlDPay);

    @SelectProvider(type = QueryDao.class, method = "setlDByDiseNoTrt")
    Page<SetlD> setlDByDiseNoTrt(Page page, SetlD setlD);

    @SelectProvider(type = QueryDao.class, method = "setlDByDiseNo")
    Page<SetlD> setlDByDiseNo(Page page, SetlD setlD);

    @SelectProvider(type = QueryDao.class, method = "setlDByDiseNoCount")
    Page<SetlD> setlDByDiseNoCount(Page page, SetlD setlD);

    @SelectProvider(type = QueryDao.class, method = "reflAppyEvtC")
    Page<ReflAppyEvtCVo> reflAppyEvtC(Page page, ReflAppyEvtCVo reflAppyEvtCVo);

    @SelectProvider(type = QueryDao.class, method = "fixmedinsCntrRegD")
    Page<FixmedinsCntrRegD> fixmedinsCntrRegD(Page page, FixmedinsCntrRegD fixmedinsCntrRegD);

    @SelectProvider(type = QueryDao.class, method = "fixmedinsCntrRegDByMedinsInfoB")
    Page<FixmedinsCntrRegD> fixmedinsCntrRegDByMedinsInfoB(Page page, FixmedinsCntrRegD fixmedinsCntrRegD);

    @SelectProvider(type = QueryDao.class, method = "medicalContrast")
    Page<MedinsInfoB> medicalContrast(Page page, MedinsInfoB medinsInfoBD);

    @SelectProvider(type = QueryDao.class, method = "childbirth")
    Page<ChildbirthAllowance> childbirth(Page page, ChildbirthAllowance childbirthAllowance);

    @SelectProvider(type = QueryDao.class, method = "hospitalization")
    Page<HospitalizationCosts> hospitalization(Page page, HospitalizationCosts hospitalizationCosts);

    @SelectProvider(type = QueryDao.class, method = "setlDMonth")
    Page<SetlDMonth> setlDMonth(Page page, SetlDMonth setlDMonth);

    @SelectProvider(type = QueryDao.class, method = "personal")
    Page<PersonalChanges> personal(Page page, PersonalChanges personalChanges);

    @SelectProvider(type = QueryDao.class, method = "organization")
    Page<OrganizationGinseng> organization(Page page, OrganizationGinseng organizationGinseng);

    @SelectProvider(type = QueryDao.class, method = "schoolCount")
    Page<School> schoolCount(Page page, School school);

    @SelectProvider(type = QueryDao.class, method = "schoolDetail")
    Page<School> schoolDetail(Page page, School school);

    @SelectProvider(type = QueryDao.class, method = "sporadicHandle")
    Page<SporadicHandle> sporadicHandle(Page page, SporadicHandle sporadicHandle);

    @SelectProvider(type = QueryDao.class, method = "SetlDYear")
    Page<SetlDYear> SetlDYear(Page page, SetlDYear setlDYear);

    @SelectProvider(type = QueryDao.class, method = "setlDMonthGather")
    Page<SetlDMonthGather> setlDMonthGather(Page page, SetlDMonthGather setlDMonthGather);

    @SelectProvider(type = QueryDao.class, method = "fixmedinsGather")
    Page<FixmedinsGather> fixmedinsGather(Page page, FixmedinsGather fixmedinsGather);
}
