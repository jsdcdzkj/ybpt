package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.dao.BizReconciliationDao;
import com.jsdc.ybpt.dao.BizReconciliationMonthDao;
import com.jsdc.ybpt.mapper.BizReconciliationMapper;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.model.BizReconciliationMonth;
import com.jsdc.ybpt.vo.ReconciliationExcelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BizReconciliationMonthService extends BaseService<BizReconciliationMonth> {
    @Autowired
    private BizReconciliationMonthDao bizReconciliationMonthDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 查询回流库数据
     * @return
     */
    @DS("reflowData")
    public List<BizReconciliationMonth> getReflowReconciliationData(String settleDate,String insutype,String medTypes,String psnTypes,String fixmedins_code,String insu_admdvs){
        //统计回流库数据
        List<BizReconciliationMonth> maps = jdbcTemplate.query(
                bizReconciliationMonthDao.getReflowReconciliationData( settleDate, insutype, medTypes, psnTypes,fixmedins_code,insu_admdvs),
                new Object[]{},
                new BeanPropertyRowMapper<>(BizReconciliationMonth.class)
        );
        return maps;
    }
    /**
     * 查询回流库月数据
     * @return
     */
    @DS("reflowData")
    public List<BizReconciliationMonth> getReflowReconciliationData_yd(String settleDate,String insutype,String medTypes,String psnTypes,String fixmedins_code,String insu_admdvs){
        //统计回流库数据
        List<BizReconciliationMonth> maps = jdbcTemplate.query(
                bizReconciliationMonthDao.getReflowReconciliationData_yd( settleDate, insutype, medTypes, psnTypes,fixmedins_code,insu_admdvs),
                new Object[]{},
                new BeanPropertyRowMapper<>(BizReconciliationMonth.class)
        );
        return maps;
    }
    /**
     * 查询对账数据详情
     * @return
     */
    @DS("reflowData")
    public List<ReconciliationExcelVo> getDetailsExcel(String settleDate, String insutype, String medTypes, String psnTypes, String fixmedins_code, String insu_admdvs){
        //统计回流库数据
        List<ReconciliationExcelVo>  list = jdbcTemplate.query(
                bizReconciliationMonthDao.getDetailsExcel( settleDate, insutype, medTypes, psnTypes,fixmedins_code,insu_admdvs),
                new Object[]{},
                new BeanPropertyRowMapper<>(ReconciliationExcelVo.class)
        );
        return list;
    }

    /**
     * 查询对账数据月详情
     * @return
     */
    @DS("reflowData")
    public List<ReconciliationExcelVo> getDetailsExcel_yd(String settleDate, String insutype, String medTypes, String psnTypes, String fixmedins_code, String insu_admdvs){
        //统计回流库数据
        List<ReconciliationExcelVo>  list = jdbcTemplate.query(
                bizReconciliationMonthDao.getDetailsExcel_yd( settleDate, insutype, medTypes, psnTypes,fixmedins_code,insu_admdvs),
                new Object[]{},
                new BeanPropertyRowMapper<>(ReconciliationExcelVo.class)
        );
        return list;
    }
}
