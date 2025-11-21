package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.AdministrativeUnitMapper;
import com.jsdc.ybpt.mapper.LoanApplicationMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.LoanApplication;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.BankVo;
import com.jsdc.ybpt.vo.SetlVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanApplicationService extends BaseService<LoanApplication> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private LoanApplicationMapper loanApplicationMapper;


    /**
     * 贷款申请新增
     * Author wzn
     * Date 2022/7/6 16:32
     */
    public void addLoanApplication(LoanApplication loanApplication) {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<LoanApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixmedins_code", loanApplication.getFixmedins_code());
        queryWrapper.eq("apply_satus", "0");
//        queryWrapper.eq("apply_bank_id", loanApplication.getApply_bank_id());
        Long count = loanApplicationMapper.selectCount(queryWrapper);
        if (count != 0) {
            throw new CustomException("正在审核，禁止重复申请！");
        }
        loanApplication.setCreateUser(sysUser.getUsername());
        loanApplication.setCreateTime(new Date());
        loanApplication.setIs_del("0");
        loanApplication.setApply_satus("0");
        loanApplication.setApply_time(new Date());
        loanApplication.insert();
    }

    /**
    *历史申贷记录
    * Author wzn
    * Date 2022/7/6 18:18
    */
    public Page<LoanApplication> loanList(LoanApplication loanApplication) {
        SysUser sysUser = sysUserService.getUser() ;
        Page<LoanApplication> page = new Page<>(loanApplication.getPageNo(), loanApplication.getPageSize());
        QueryWrapper<LoanApplication> queryWrapper = new QueryWrapper<>();
        if(!"".equals(loanApplication.getFixmedins_code()) && null != loanApplication.getFixmedins_code()){
            queryWrapper.eq("fixmedins_code", loanApplication.getFixmedins_code());
        }
        if(!"".equals(loanApplication.getFixmedins_name()) && null != loanApplication.getFixmedins_name()){
            queryWrapper.like("fixmedins_name", loanApplication.getFixmedins_name());
        }
        if(!"".equals(loanApplication.getApply_bank_id()) && null != loanApplication.getApply_bank_id()){
            queryWrapper.eq("apply_bank_id", loanApplication.getApply_bank_id());
        }
        if(!"".equals(loanApplication.getApply_satus()) && null != loanApplication.getApply_satus()){
            queryWrapper.eq("apply_satus", loanApplication.getApply_satus());
        }
        if(!"".equals(loanApplication.getBank_satus()) && null != loanApplication.getBank_satus()){
            queryWrapper.eq("bank_satus", loanApplication.getBank_satus());
        }
        if("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type())){
            queryWrapper.eq("fixmedins_code",sysUser.getOrg_code()) ;
        }else if("1".equals(sysUser.getUser_type())){//行政单位查看所有的审核记录  除了撤销
            queryWrapper.ne("apply_satus","3") ;
        }else if("6".equals(sysUser.getUser_type())){
            queryWrapper.eq("apply_bank_id",sysUser.getOrg_code());
            queryWrapper.and(x->x.eq("bank_satus","1").or().eq("bank_satus","2").or().eq("bank_satus","3"));
        }
        queryWrapper.orderByDesc("apply_time") ;
        return  loanApplicationMapper.selectPage(page,queryWrapper) ;
    }

    /**
    *银行端审核列表
    * Author wzn
    * Date 2022/7/8 11:38
    */
    public Page<LoanApplication> bankCkeckList(LoanApplication loanApplication) {
        SysUser sysUser = sysUserService.getUser() ;
        Page<LoanApplication> page = new Page<>(loanApplication.getPageNo(), loanApplication.getPageSize());
        QueryWrapper<LoanApplication> queryWrapper = new QueryWrapper<>();
        if(!"".equals(loanApplication.getFixmedins_code()) && null != loanApplication.getFixmedins_code()){
            queryWrapper.eq("fixmedins_code", loanApplication.getFixmedins_code());
        }
        if(!"".equals(loanApplication.getFixmedins_name()) && null != loanApplication.getFixmedins_name()){
            queryWrapper.like("fixmedins_name", loanApplication.getFixmedins_name());
        }
        if("6".equals(sysUser.getUser_type())){
            queryWrapper.eq("bank_satus","0") ;
            queryWrapper.eq("apply_bank_id",sysUser.getOrg_code()) ;
        }

        return  loanApplicationMapper.selectPage(page,queryWrapper) ;
    }


    /**
    *医保端贷款审核
    * Author wzn
    * Date 2022/7/7 15:46
    */
    public void check(LoanApplication loanApplication){
        SysUser sysUser = sysUserService.getUser() ;
        if("1".equals(loanApplication.getApply_satus())){
            loanApplication.setBank_satus("0");
        }
        loanApplication.setYbreviewer(sysUser.getUsername());
        loanApplication.setYbreviewTime(new Date());
        loanApplication.updateById() ;
    }

    /**
    *银行贷款审核
    * Author wzn
    * Date 2022/7/9 8:42
    */
    public void bankCheck(LoanApplication loanApplication){
        SysUser sysUser = sysUserService.getUser() ;
        LoanApplication loanApplication1 = new LoanApplication() ;
        loanApplication1.setId(loanApplication.getId());
        loanApplication1.setBank_satus(loanApplication.getBank_satus());
        loanApplication1.setBank_reason(loanApplication.getBank_reason());
        loanApplication1.setDetermineTheAmount(loanApplication.getDetermineTheAmount());
        loanApplication1.setReviewer(sysUser.getUsername());
        loanApplication1.setReviewTime(new Date());
        loanApplication1.updateById() ;
    }
    /**
    *撤销
    * Author wzn
    * Date 2022/7/7 16:57
    */
    public void del(String id){
        LoanApplication loanApplication = new LoanApplication() ;
        loanApplication.setId(id);
        loanApplication.setApply_satus("3");
        loanApplication.updateById() ;
    }

    /**
    *结算金额查询
    * Author wzn
    * Date 2022/7/9 9:29
    */
    @DS("reflowData")
    public SetlVo billingData(String fixmedins_code){
        SetlVo setlVo =  loanApplicationMapper.billingData(fixmedins_code) ;
        return setlVo ;
    }


    /**
    *贷款申请超时，医保重新审核
    * Author wzn
    * Date 2022/7/9 9:29
    */
    public void timeOut(String id){
        LoanApplication loanApplication = new LoanApplication() ;
        loanApplication.setId(id);
        loanApplication.setApply_satus("0");
        loanApplication.setYbreviewer("");
        loanApplication.setYbreviewTime(null);
        loanApplication.setBank_satus("3");
        loanApplication.updateById() ;
    }


    /**
    *查看该机构申请所有的银行记录，除了传参这条
    * Author wzn
    * Date 2022/7/12 9:41
    */
    public Page<LoanApplication> bankApplyList(LoanApplication loanApplication){
        Page<LoanApplication> page = new Page<>(loanApplication.getPageNo(), loanApplication.getPageSize());
        QueryWrapper<LoanApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fixmedins_code",loanApplication.getFixmedins_code()) ;
        queryWrapper.ne("id",loanApplication.getId()) ;
        queryWrapper.isNotNull("apply_bank_id") ;
        Page<LoanApplication> loanApplications = loanApplicationMapper.selectPage(page,queryWrapper) ;
        return loanApplications ;
    }


    public Long getCount(String bankId){
        QueryWrapper<LoanApplication> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_bank_id",bankId) ;

        return loanApplicationMapper.selectCount(queryWrapper) ;
    }



    /**
    *放贷银行统计
    * Author wzn
    * Date 2022/7/12 14:14
    */
    public BankVo bankTj(String bankNo){
        return loanApplicationMapper.bankTj(bankNo) ;
    }



}
