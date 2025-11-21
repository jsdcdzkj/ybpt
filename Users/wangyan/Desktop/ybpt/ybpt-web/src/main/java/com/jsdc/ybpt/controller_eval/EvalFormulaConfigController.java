package com.jsdc.ybpt.controller_eval;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.eval_.EvalFormulaConfig;
import com.jsdc.ybpt.eval_.EvalTaskManage;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.service.eval.EvalDesignService;
import com.jsdc.ybpt.service.eval.EvalFormulaConfigService;
import com.jsdc.ybpt.service.eval.EvalOrgDetailService;
import com.jsdc.ybpt.vo.EvalDesignVo;
import com.jsdc.ybpt.vo.FormulaConfigVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author ：苹果
 * @Description：考核设计表
 * @Date ：2023/11/16 13:39
 * @Modified By：
 */
@RestController
@RequestMapping("/evalFormulaConfig")
public class EvalFormulaConfigController {
    @Autowired
    private EvalFormulaConfigService evalFormulaConfigService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/getConfigList")
    public ResultInfo getConfigList() {
        List<EvalFormulaConfig> list = evalFormulaConfigService.list(new QueryWrapper<EvalFormulaConfig>().eq("isDel", "0").orderByAsc("createTime"));
        return ResultInfo.success(list);
    }

    @PostMapping("/addConfigs")
    public ResultInfo addConfigs(@RequestBody FormulaConfigVo configVo) {
        SysUser sysUser = sysUserService.getUser();
        List<EvalFormulaConfig> configs = configVo.getConfigs();
        if(intervalsOverlap(configs)){
            return ResultInfo.error("指数范围配置不得重叠！");
        }
        //新增
        if (configs != null && configs.size() > 0) {
            for (EvalFormulaConfig config : configs) {
                if(StrUtil.isBlank(config.getIndexBegain()) || StrUtil.isBlank(config.getIndexEnd()) || StrUtil.isBlank(config.getFormula())){
                    return ResultInfo.error("指数范围和公式不得为空！");
                }
                if (!isValid(config.getFormula())) {
                    return ResultInfo.error("公式格式错误！");
                }
            }
            for (EvalFormulaConfig config : configs) {
                if (StrUtil.isNotBlank(config.getId())) {
                    config.setIsDel("1");
                    config.setUpdateTime(new Date());
                    config.setUpdateUser(sysUser.getId());
                    config.updateById();
                }
                config.setFormula(config.getFormula().toUpperCase());
                config.setId(IdUtil.simpleUUID());
                config.setCreateTime(new Date());
                config.setIsDel("0");
                config.setCreateUser(sysUser.getId());
                config.insert();
            }
        } else {
            return ResultInfo.error("请先配置公式！");
        }
        return ResultInfo.success();
    }


    @PostMapping("/delConfig")
    public ResultInfo delConfig(@RequestBody EvalFormulaConfig config) {
        SysUser sysUser = sysUserService.getUser();
        if (StrUtil.isNotBlank(config.getId())) {
            config.setIsDel("1");
            config.setUpdateTime(new Date());
            config.setUpdateUser(sysUser.getId());
            config.updateById();
        } else {
            return ResultInfo.error("请先选择删除的对象！");
        }
        return ResultInfo.success();
    }

    public static boolean isValid(String str) {
        // 正则表达式，匹配任意数字、+ - * / ( )、以及 P M X G （无论大小写）
        String regex = "^[\\d()+\\-*\\/PMXG]*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str).matches();
    }

    //判断区间重叠
    public static boolean intervalsOverlap(List<EvalFormulaConfig> configs) {
        for (int i = 0; i < configs.size(); i++) {
            for (int j = i + 1; j < configs.size(); j++) {
                EvalFormulaConfig config1 = configs.get(i);
                EvalFormulaConfig config2 = configs.get(j);

                if (config1.getIndexBegain().compareTo(config2.getIndexEnd()) < 0 &&
                        config2.getIndexBegain().compareTo(config1.getIndexEnd()) < 0) {
                    // 如果区间重叠
                    return true;
                }
            }
        }
        // 如果没有重叠的区间
        return false;
    }
}
