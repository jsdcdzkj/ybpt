package com.jsdc.ybpt.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.ConsumablesMapper;
import com.jsdc.ybpt.model.Consumables;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.exception.CustomException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConsumablesService extends BaseService<Consumables> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ConsumablesMapper consumablesMapper;

    @Value("${consumablesUrl}")
    private String consumablesUrl;


    /**
     * 申报接口新增
     * Author wzn
     * Date 2022/6/29 14:43
     */
    public void addConsumables(Consumables consumables) {
        SysUser sysUser = sysUserService.getUser();
        consumables.setFixmedins_no(sysUser.getOrg_code());
        consumables.setMedins_name(sysUser.getOrg_name());
        consumables.setCreateUser(sysUser.getUsername());
        consumables.setCreateTime(new Date());
        consumables.setStatus("0");
        consumables.setIs_del("0");
        consumables.insert();
    }

    /**
     * 申报接口修改
     * Author wzn
     * Date 2022/6/29 14:59
     */
    public void updateConsumables(Consumables consumables) {
        SysUser sysUser = sysUserService.getUser();
        consumables.setUpdateUser(sysUser.getUsername());
        consumables.setUpdateTime(new Date());
        consumables.updateById();
    }

    /**
     * 申报列表接口
     * Author wzn
     * Date 2022/6/29 15:00
     */
    public Page<Consumables> selectList(Consumables consumables) {
        SysUser sysUser = sysUserService.getUser();

        Page<Consumables> page = new Page<>(consumables.getPageNo(), consumables.getPageSize());
        QueryWrapper<Consumables> queryWrapper = new QueryWrapper<>();

        if (!"".equals(consumables.getMcs_code()) && null != consumables.getMcs_code()) {
            queryWrapper.eq("mcs_code", consumables.getMcs_code());
        }
        if (!"".equals(consumables.getStatus()) && null != consumables.getStatus()) {
            queryWrapper.eq("status", consumables.getStatus());
        }

        if ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())) {
            queryWrapper.eq("fixmedins_no", sysUser.getOrg_code());
        }
        queryWrapper.orderByDesc("createTime");
        queryWrapper.eq("is_del", "0");
        Page<Consumables> consumablesPage = consumablesMapper.selectPage(page, queryWrapper);
        return consumablesPage;
    }

    /**
     * 详情
     * Author wzn
     * Date 2022/6/29 16:16
     */
    public Consumables info(Consumables consumables) {
        return consumablesMapper.selectById(consumables.getId());
    }


    /**
     * 审核/撤销接口
     * Author wzn
     * Date 2022/7/1 9:41
     */
    public Consumables verify(Consumables consumables) {
        Consumables consumables1 = consumablesMapper.selectById(consumables.getId());
        if (!"0".equals(consumables1.getStatus())) {
            throw new CustomException("已确认,无法撤销！");
        } else {
            SysUser sysUser = sysUserService.getUser();
            consumables.setUpdateUser(sysUser.getUsername());
            consumables.setUpdateTime(new Date());
            consumables.updateById();
        }
        return consumablesMapper.selectById(consumables.getId());

    }

    @NotNull
    public String getResultInfo(Consumables c, JSONObject json) {
        SysUser sysUser = sysUserService.getUser();
        JSONObject params = new JSONObject();
        params.set("OPTER_NAME", sysUser.getUsername());
        params.set("OPT_TIME", DateUtil.formatDate(new Date()));
        params.set("FIXMEDINS_NO", sysUser.getOrg_code());
        params.set("MEDINS_NAME", sysUser.getOrg_name());
        params.set("VER", c.getVer());
        params.set("PRODUCT_NUM", c.getProduct_num());
        params.set("MCS_CODE", c.getMcs_code());
        params.set("REG_FIL_PROD_NAME", c.getReg_fil_prod_name());
        params.set("REG_FIL_NO", c.getReg_fil_no());
        params.set("MATL", c.getMatl());
        params.set("CHARACTERISTICS", c.getCharacteristics());
        params.set("SPEC", c.getSpec());
        params.set("MOL", c.getMol());
        params.set("MCS_ENTP", c.getMcs_entp());
        json.set("params", params);
        System.out.printf("params:" + json);
        String result = HttpRequest.post(consumablesUrl)
                .body(String.valueOf(json))
                .execute().body();
        System.out.println("result:" + result);
        return result;
    }


}
