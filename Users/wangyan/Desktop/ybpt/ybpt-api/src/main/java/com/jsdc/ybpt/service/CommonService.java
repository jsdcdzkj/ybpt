package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.dao.CommonDao;
import com.jsdc.ybpt.dao.OpspRegdDao;
import com.jsdc.ybpt.mapper.CommonMapper;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.vo.NatDataDicAVo;
import com.jsdc.ybpt.vo.OpspRegDVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService extends BaseService<BizReconciliation> {

    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 查询回流库数据 -根据字典类型和code查询名称
     * @return
     */
    @DS("reflowData")
    public List<NatDataDicAVo> selectNameById(String dic_type_code,String nat_dic_val_code){
        //统计回流库数据
        List<NatDataDicAVo> maps = commonMapper.selectNameById(dic_type_code,nat_dic_val_code);
        return maps;
    }



    @DS("reflowData")
    public List<NatDataDicAVo> selectDicList(String dic_type_code){
        //统计回流库数据
        List<NatDataDicAVo> maps = commonMapper.selectDicList(dic_type_code);
        return maps;
    }


}
