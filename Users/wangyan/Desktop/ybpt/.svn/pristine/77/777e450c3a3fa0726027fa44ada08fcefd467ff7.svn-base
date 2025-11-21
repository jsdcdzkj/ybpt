package com.jsdc.ybpt.service.agreementsignService;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagAppeal;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.agreementsignMapper.NetTagAppealMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


@Service
public class NetTagAppealService extends BaseService<NetTagAppeal> {

    @Autowired
    private NetTagAppealMapper netTagAppealMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FixmedinsBService fixmedinsBService;

    @Autowired
    private FixmedinsBMapper fixmedinsBMapper;
    @Autowired
    private NetTagMechanismService netTagMechanismService;

    //列表查询
    public Page<NetTagAppeal> getAllNetTagAppeal(Integer pageIndex, Integer pageSize, NetTagAppeal netTagAppeal) {
        Page<NetTagAppeal> page;
        LambdaQueryWrapper<NetTagAppeal> lambda = new LambdaQueryWrapper();
        lambda.eq(NetTagAppeal::getIs_del, "0");
//        if (StringUtils.isNotEmpty(netTagAppeal.getMechanism_code())) {
//            lambda.like(NetTagAppeal::getMechanism_code, netTagAppeal.getMechanism_code());
//        }
        if (StringUtils.isNotEmpty(netTagAppeal.getMechanism_code())){
            if (netTagAppeal.getMechanism_code().contains("%")){
                lambda.like(NetTagAppeal::getMechanism_code, "/"+netTagAppeal.getMechanism_code()+"ESCAPE /");
            }else {
                lambda.like(NetTagAppeal::getMechanism_code, netTagAppeal.getMechanism_code());
            }
        }
        if (StringUtils.isNotEmpty(netTagAppeal.getMedical_type())) {
            lambda.eq(NetTagAppeal::getMedical_type, netTagAppeal.getMedical_type());
        }
        if (netTagAppeal.getNet_grade_id() != null) {
            lambda.eq(NetTagAppeal::getNet_grade_id, netTagAppeal.getNet_grade_id());
        }
        if (netTagAppeal.getStatus() != null) {
            lambda.eq(NetTagAppeal::getStatus, netTagAppeal.getStatus());
        }
        lambda.orderByDesc(NetTagAppeal::getCreateTime);
        page = netTagAppealMapper.selectPage(new Page<>(pageIndex, pageSize), lambda);
        page.getRecords().forEach(a -> {
            //测试数据
//            a.setFixmedins_name("机构名称");//机构名称
//            a.setNet_grade_name("机构等级");//机构等级
//            a.setLegrep_name("法人代表");//法人代表
//            a.setDept_resper_tel("联系电话");//联系电话

            a.setNet_grade_name(netTagMechanismService.getLevelList().get(a.getNet_grade_id()));
            //回流库查询
            if (null != a.getMedical_type() && null != a.getMedical_code()) {
                List<FixmedinsB> fixmedinsBList = fixmedinsBMapper.selectList(Wrappers.<FixmedinsB>lambdaQuery()
                        .eq(FixmedinsB::getFixmedins_code, a.getMechanism_code()).eq(FixmedinsB::getIs_del, "0"));
                if(!CollectionUtils.isEmpty(fixmedinsBList)){
                    FixmedinsB fixmedinsB = fixmedinsBList.get(0);
                    if (null != fixmedinsB) {
                        a.setFixmedins_name((fixmedinsB.getFixmedins_name() == null) ? "" : fixmedinsB.getFixmedins_name());//机构名称
                        a.setMechanism_code((fixmedinsB.getFixmedins_code() == null) ? "" : fixmedinsB.getFixmedins_code());//机构类型名称
                        a.setMedical_code((fixmedinsB.getFixmedins_code() == null) ? "" : fixmedinsB.getFixmedins_code());//医保编码
                        a.setLegrep_name((fixmedinsB.getHi_resper_name() == null) ? "" : fixmedinsB.getHi_resper_name());//联系人
                        a.setDept_resper_tel((fixmedinsB.getHi_resper_tel() == null) ? "" : fixmedinsB.getHi_resper_tel());//联系电话
                    }
                }

            }
        });
        return page;
    }


    //查看
    public NetTagAppeal getOneNetTagAppeal(String id) {
        return netTagAppealMapper.selectById(id);
    }

    //确认
    public ResultInfo getSure(String id) {
        SysUser sysUser = sysUserService.getUser();
        NetTagAppeal netTagAppeal = netTagAppealMapper.selectById(id);
        //状态 0未确认 1确认
        netTagAppeal.setStatus(1);
        //医保编码
        netTagAppeal.setMedical_code(sysUser.getOrg_code());
        netTagAppeal.setUpdateTime(new Date());
        netTagAppeal.setUpdateUser(sysUser.getId());
        updateById(netTagAppeal);
        return ResultInfo.success();
    }

    /**
     * 申诉保存
     */
    public ResultInfo onSave(NetTagAppeal bean) {
        SysUser sysUser = sysUserService.getUser();
        String orgCode = sysUser.getOrg_code();
        FixmedinsB fixmedinsB = null;
        //user_type 账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行
        if (sysUser.getUser_type().equals("2")) {
            fixmedinsB = fixmedinsBService.selectByYSYD(orgCode, "1");
            bean.setMedical_type("1");
        } else if (sysUser.getUser_type().equals("3")) {
            fixmedinsB = fixmedinsBService.selectByYSYD(orgCode, "2");
            bean.setMedical_type("2");
        }
        if (fixmedinsB != null) {
            bean.setMedical_code(fixmedinsB.getFixmedins_code());//医药机构编码、国家编码
            bean.setMechanism_code(fixmedinsB.getFixmedins_code());
            bean.setNet_grade_id(fixmedinsB.getAggrement_lv());//机构等级
        }
        //状态 0未确认 1确认
        bean.setStatus(0);
        bean.setCreateTime(new Date());
        bean.setCreateUser(sysUser.getId());
        bean.setId(IdUtil.simpleUUID());
        bean.setIs_del("0");
        netTagAppealMapper.insert(bean);
        return ResultInfo.success();
    }

}
