package com.jsdc.ybpt.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model_query.DatadictypeA;
import com.jsdc.ybpt.util.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class DatadictypeADao {
    public String datadictypeQuery(Page page, DatadictypeA datadictypeA){
        String sql="SELECT * FROM POLCENT_DB.NAT_DATA_DIC_A where 1=1";
        if (StringUtils.isNotEmpty(datadictypeA.getDic_type_code())){
                sql+="and DIC_TYPE_CODE= '"+datadictypeA.getDic_type_code()+"'";
        }
        if (StringUtils.isNotEmpty(datadictypeA.getNat_dic_val_code())){
            sql+="and DIC_TYPE_CODE= '"+datadictypeA.getNat_dic_val_code()+"'";
        }
        return sql;
    }
}
