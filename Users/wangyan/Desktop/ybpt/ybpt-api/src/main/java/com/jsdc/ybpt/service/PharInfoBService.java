package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.MedinsInfoBMapper;
import com.jsdc.ybpt.mapper.PharInfoBMapper;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.vo.MedinsInfoBVo;
import com.jsdc.ybpt.vo.PharInfoBVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharInfoBService extends BaseService<BizReconciliation> {

   @Autowired
   private PharInfoBMapper pharInfoBMapper ;
    /**
     * 查询回流库数据 -根据字典类型和code查询名称
     * @return
     */
    @DS("reflowData")
    public Page<PharInfoBVo> selectPharInfo(PharInfoBVo pharInfoBVo){
        //统计回流库数据
        Page<PharInfoBVo> page = new Page<>( pharInfoBVo.getPageNo(),pharInfoBVo.getPageSize());
        Page<PharInfoBVo> pharInfoBVoPage = pharInfoBMapper.selectPharInfo(page,pharInfoBVo.getPhar_code(),pharInfoBVo.getPhar_name()) ;
        return pharInfoBVoPage;
    }


}
