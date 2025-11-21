package com.jsdc.ybpt.service;

import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.IsConfigMapper;
import com.jsdc.ybpt.model_check.IsConfig;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：苹果
 * @Description：
 * @Date ：2023/1/3 16:01
 * @Modified By：
 */
@Service
public class IsConfigService extends BaseService<IsConfig> {
    @Autowired
    private IsConfigMapper isConfigMapper;

    public ResultInfo relocatedConfig() {
        IsConfig isConfig = this.isConfigMapper.selectById(1);
        return ResultInfo.success(isConfig);
    }

    public ResultInfo upRelocatedConfig(IsConfig bean) {
         this.isConfigMapper.updateById(bean);
        return ResultInfo.success();
    }
}
