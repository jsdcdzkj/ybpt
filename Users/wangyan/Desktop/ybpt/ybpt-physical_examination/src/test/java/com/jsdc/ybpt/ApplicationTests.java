package com.jsdc.ybpt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.BizReconciliationService;
import com.jsdc.ybpt.service.SysMenuService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BizReconciliationService bizReconciliationService;
    @Autowired
    private SysMenuService sysMenuService;
    @Test
    void contextLoads() {
//        Page<SysUser> page = new Page<>(1,2);
        Page<SysUser> sysUser = sysUserService.getList();
        sysUser.getRecords().forEach(x->{
            System.out.println(x);
        });
        System.out.println("***************************************");
//        Page<SysUser> userPage = sysUserService.page(page);
//        for (SysUser record : userPage.getRecords()) {
//            System.out.println(record);
//        }
    }


}
