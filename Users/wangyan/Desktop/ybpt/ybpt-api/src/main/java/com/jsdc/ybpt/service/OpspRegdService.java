package com.jsdc.ybpt.service;


import cn.hutool.core.util.ArrayUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.dao.BizReconciliationDao;
import com.jsdc.ybpt.dao.OpspRegdDao;
import com.jsdc.ybpt.mapper.BizReconciliationMapper;
import com.jsdc.ybpt.mapper.OpspDiseMapper;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.model.OpspDise;
import com.jsdc.ybpt.model.RegistrationInformation;
import com.jsdc.ybpt.vo.NatDataDicAVo;
import com.jsdc.ybpt.vo.OpspRegDVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpspRegdService extends BaseService<BizReconciliation> {

    @Autowired
    private OpspRegdDao opspRegdDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommonService commonService ;

    @Autowired
    private OpspDiseMapper opspDiseMapper ;
    /**
     * 查询回流库数据
     * @return
     */
    @DS("reflowData")
    public List<OpspRegDVo> selectByIdCard(String certno,String psn_cert_type){
        //统计回流库数据
        List<OpspRegDVo> maps = jdbcTemplate.query(opspRegdDao.selectByIdCard(certno,psn_cert_type),new Object[]{}, new BeanPropertyRowMapper<>(OpspRegDVo.class));
        if(null != maps && maps.size()!=0){
            List<NatDataDicAVo> natDataDicAVoList = commonService.selectNameById("NATY",maps.get(0).getNaty()) ;
            if(null != natDataDicAVoList && natDataDicAVoList.size()!=0){
                maps.get(0).setNaty_name(natDataDicAVoList.get(0).getNat_dic_val_name());
            }
        }
        return maps;
    }

    /**
    *通过身份证查询单位
    * Author wzn
    * Date 2022/4/29 15:50
    */
    @DS("reflowData")
    public List<OpspRegDVo> selectCompanyByIdCard(String certno, String psn_cert_type) {
        List<OpspRegDVo> opspRegDVoList = opspDiseMapper.selectCompanyByIdCard(certno,psn_cert_type) ;
        return opspRegDVoList ;
    }





}
