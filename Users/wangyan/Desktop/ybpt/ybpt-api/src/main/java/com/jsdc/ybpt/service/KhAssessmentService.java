package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.assessment.KhAssessment;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.KhAssessmentMapper;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhAssessmentService extends BaseService<KhAssessment> {
    @Autowired
    private KhAssessmentMapper khAssessmentMapper ;

    @Autowired
    private SysUserService sysUserService ;

    /**
     *考核单下拉数据
     * Author wzn
     * Date 2022/11/23 14:44
     */
    public List<KhAssessment> dataList(){
        QueryWrapper<KhAssessment> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("is_del",0) ;
        List<KhAssessment> khAssessmentList = khAssessmentMapper.selectList(queryWrapper) ;
        return khAssessmentList ;
    }

    /**
    *根据选择内容，默认选择一个考核单
    * Author wzn
    * Date 2022/11/23 15:09
    */
    public KhAssessment selectBy(KhAssessment khAssessment){
        KhAssessment khAssessmentList = null ;
        QueryWrapper<KhAssessment> queryWrapper = new QueryWrapper<>() ;
        if(StringUtils.isNotEmpty(khAssessment.getOrg_type()) && StringUtils.isNotEmpty(khAssessment.getAggrement_lv()) &&  StringUtils.isNotEmpty(khAssessment.getYear_of_assessment())){
            if("1".equals(khAssessment.getOrg_type())){
                queryWrapper.eq("is_del", "0");
                queryWrapper.eq("org_type", khAssessment.getOrg_type());
                queryWrapper.eq("category", khAssessment.getCategory());
                queryWrapper.eq("aggrement_lv", khAssessment.getAggrement_lv());
                queryWrapper.eq("year_of_assessment", khAssessment.getYear_of_assessment());
                queryWrapper.eq("admdvs", sysUserService.getUser().getOrg_code());
                khAssessmentList = khAssessmentMapper.selectOne(queryWrapper);
            }else{
                queryWrapper.eq("is_del", "0");
                queryWrapper.eq("org_type", khAssessment.getOrg_type());
                queryWrapper.eq("aggrement_lv", khAssessment.getAggrement_lv());
                queryWrapper.eq("year_of_assessment", khAssessment.getYear_of_assessment());
                queryWrapper.eq("admdvs", sysUserService.getUser().getOrg_code());
                khAssessmentList = khAssessmentMapper.selectOne(queryWrapper);
            }
        }

        return khAssessmentList ;
    }

}
