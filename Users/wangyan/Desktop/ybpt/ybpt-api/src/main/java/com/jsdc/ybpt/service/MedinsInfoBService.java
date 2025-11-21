package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.MedinsInfoBMapper;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.vo.MedinsInfoBVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MedinsInfoBService extends BaseService<BizReconciliation> {

    @Autowired
    private MedinsInfoBMapper medinsInfoBMapper;

    /**
     * 查询回流库数据 -根据字典类型和code查询名称
     *
     * @return
     */
    @DS("reflowData")
    public Page<MedinsInfoBVo> medinsInfoBVoPage(MedinsInfoBVo medinsInfoBVo) {
        //统计回流库数据
        Page<MedinsInfoBVo> page = new Page<>(medinsInfoBVo.getPageNo(), medinsInfoBVo.getPageSize());
        Page<MedinsInfoBVo> medinsInfoBVoPage = medinsInfoBMapper.selectMedinsInfo(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name());
        return medinsInfoBVoPage;
    }


    public Page<MedinsInfoBVo> selectMedinsInfoType(MedinsInfoBVo medinsInfoBVo) {
        Page<MedinsInfoBVo> page = new Page<>(medinsInfoBVo.getPageNo(), medinsInfoBVo.getPageSize());
        Page<MedinsInfoBVo> medinsInfoBVoPage = null;
        switch (medinsInfoBVo.getFixmedins_type()){
            case "1":
                medinsInfoBVoPage = medinsInfoBMapper.selectAdminUnitList(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name());
                break;
            case "2":
                medinsInfoBVoPage = medinsInfoBMapper.selectMedinsInfoType(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name(),"1");
                break;
            case "3":
                medinsInfoBVoPage = medinsInfoBMapper.selectMedinsInfoType(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name(),"2");
                break;
            case "4":
                medinsInfoBVoPage = medinsInfoBMapper.selectEmpList(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name());
                break;
            case "5":
                medinsInfoBVoPage = medinsInfoBMapper.selectOrgList(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name());
                break;
            case "6":
                medinsInfoBVoPage = medinsInfoBMapper.selectBankList(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name());
                break;
            case "7":
                medinsInfoBVoPage = medinsInfoBMapper.selectFdd(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name(),"2");
                break;
            case "8":
                medinsInfoBVoPage = medinsInfoBMapper.selectFdd(page, medinsInfoBVo.getMedins_code(), medinsInfoBVo.getMedins_name(),"3");
                break;
        }
        return medinsInfoBVoPage;
    }


}
