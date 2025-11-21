package com.jsdc.ybpt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.capitalSettlement.QsOrgConfirmation;
import com.jsdc.ybpt.mapper.QsOrgConfirmationMapper;
import com.jsdc.ybpt.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QsOrgConfirmationService extends BaseService<QsOrgConfirmation> {

    @Autowired
    private SysUserService sysUserService ;
    @Autowired
    QsOrgConfirmationMapper qsOrgConfirmationMapper;

    public Page<QsOrgConfirmation> getList(QsOrgConfirmation bean) {
        SysUser sysUser = sysUserService.getUser();
        if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            bean.setOrg_code(sysUser.getOrg_code());
        }
        Page<QsOrgConfirmation> page = qsOrgConfirmationMapper.getList(new Page<>(bean.getPageIndex(), bean.getPageSize()),bean);
        return page;
    }
}
