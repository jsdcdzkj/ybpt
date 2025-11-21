package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.DiagListBMapper;
import com.jsdc.ybpt.mapper.OpspDiseListBMapper;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.vo.DiagListBVo;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagListBService extends BaseService<BizReconciliation> {

   @Autowired
   private DiagListBMapper diagListBMapper ;
    
   /**
   *特殊病种查询
   * Author wzn
   * Date 2022/4/26 17:15
   */
    @DS("reflowData")
    public Page<DiagListBVo> diagList(DiagListBVo diagListBVo){
        //统计回流库数据
        Page<DiagListBVo> page = new Page<>( diagListBVo.getPageNo(),diagListBVo.getPageSize());
        Page<DiagListBVo> diagListBVoPage = diagListBMapper.diagList(page,diagListBVo) ;
        return diagListBVoPage;
    }


}
