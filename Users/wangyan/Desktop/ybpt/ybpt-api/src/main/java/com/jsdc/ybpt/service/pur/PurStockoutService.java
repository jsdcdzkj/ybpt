package com.jsdc.ybpt.service.pur;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.pur.PurStockoutMapper;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.pur.PurStockout;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.PurStockoutDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * (PurStockout)表服务接口
 *
 * @author wangYan
 * @since 2023-04-03 14:59:51
 */
@Service
public class PurStockoutService extends BaseService<PurStockout> {

    @Autowired
    private PurStockoutMapper purStockoutMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictService sysDictService;

    public Page<PurStockout> getPaging(Integer pageNo, Integer pageSize, PurStockout purStockout) {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<PurStockout> lambdaQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(purStockout.getFix_blng_admdvs())) {
            lambdaQueryWrapper.eq("fix_blng_admdvs", purStockout.getFix_blng_admdvs());
        }
        if (StringUtils.isNotBlank(purStockout.getOrg_code())) {
            lambdaQueryWrapper.eq("org_code", purStockout.getOrg_code());
        }
        if (StringUtils.isNotBlank(purStockout.getOrg_name())) {
            lambdaQueryWrapper.like("org_name", purStockout.getOrg_name());
        }
        if (StringUtils.isNotBlank(purStockout.getCodeList())) {
            lambdaQueryWrapper.like("codeList", purStockout.getCodeList());
        }
        if (null != purStockout.getQueryDate() && purStockout.getQueryDate().length > 1) {
            lambdaQueryWrapper.apply("TO_CHAR (createTime,'yyyy-MM-dd') <= '" + purStockout.getQueryDate()[1] + "' AND TO_CHAR (createTime,'yyyy-MM-dd') >='" + purStockout.getQueryDate()[0] + "'");

        }
        if (StringUtils.isNotBlank(purStockout.getStockout_type())) {
            lambdaQueryWrapper.eq("stockout_type", purStockout.getStockout_type());
        }
        if (StringUtils.isNotBlank(purStockout.getClues())) {
            lambdaQueryWrapper.eq("clues", purStockout.getClues());
        }

        lambdaQueryWrapper.eq("is_del", "0");
        lambdaQueryWrapper.orderByDesc("createTime");
        Page<PurStockout> page = this.page(new Page(pageNo, pageSize), lambdaQueryWrapper);

        //查询统筹区
        HashMap<String, String> tcqMap = new HashMap();
        List<SysDict> tcq = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type", "ADMDVS").eq("is_del", "0"));
        tcq.forEach(x -> tcqMap.put(x.getValue(), x.getLabel()));
        for (int i = 0; i < page.getRecords().size(); i++) {
            PurStockout x = page.getRecords().get(i);
            x.setStockout_type_name("1".equals(x.getStockout_type()) ? "药品" : "耗材");
            x.setFix_blng_admdvs_name(tcqMap.get(x.getFix_blng_admdvs()));
            x.setIsAudit("false");
            //判断是否有审核权限。前端展示用
            if ("1".equals(sysUser.getUser_type()) && sysUser.getOrg_code().equals(x.getFix_blng_admdvs())) {
                x.setIsAudit("true");
            }
        }
        return page;
    }

    public List<PurStockoutDetailVo> getList(PurStockout purStockout) {
        SysUser sysUser = sysUserService.getUser();
        List<PurStockoutDetailVo> list = purStockoutMapper.getList(sysUser, purStockout);
        return list;
    }

}

