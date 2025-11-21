package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.Bank;
import com.jsdc.ybpt.model.LoanApplication;
import com.jsdc.ybpt.service.BankService;
import com.jsdc.ybpt.service.LoanApplicationService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/loan")
public class LoanApplicationController {
    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private BankService bankService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 贷款申请新增
     * Author wzn
     * Date 2022/7/6 16:35
     */
    @PostMapping("/addLoanApplication")
    public ResultInfo addLoanApplication(@RequestBody LoanApplication loanApplication) {
        loanApplicationService.addLoanApplication(loanApplication);
        return ResultInfo.success();
    }

    /**
     * 历史申贷记录
     * Author wzn
     * Date 2022/7/6 18:18
     */
    @PostMapping("/loanList")
    public ResultInfo loanList(@RequestBody LoanApplication loanApplication) {
        Page<LoanApplication> loanApplicationPage = loanApplicationService.loanList(loanApplication);
        if (null != loanApplicationPage.getRecords() && loanApplicationPage.getRecords().size() > 0) {
            for (LoanApplication l : loanApplicationPage.getRecords()) {
                if (null != l.getApply_bank_id() && !"".equals(l.getApply_bank_id())) {
                    Bank bank = new Bank();
                    bank.setBankNo(l.getApply_bank_id());
                    Bank bank1 = bankService.selectByNo(bank);
                    if (null != bank1) {
                        l.setApply_bank_id_name(bank1.getBankName());
                    }
                }
            }
        }
        return ResultInfo.success(loanApplicationPage);
    }

    /**
     * 贷款审核列表
     * Author wzn
     * Date 2022/7/8 11:39
     */
    @PostMapping("/bankCkeckList")
    public ResultInfo bankCkeckList(@RequestBody LoanApplication loanApplication) {
        Page<LoanApplication> loanApplicationPage = loanApplicationService.bankCkeckList(loanApplication);
        if (null != loanApplicationPage.getRecords() && loanApplicationPage.getRecords().size() > 0) {
            for (LoanApplication l : loanApplicationPage.getRecords()) {
                if (null != l.getApply_bank_id() && !"".equals(l.getApply_bank_id())) {
                    Bank bank = new Bank();
                    bank.setBankNo(l.getApply_bank_id());
                    Bank bank1 = bankService.selectByNo(bank);
                    if (null != bank1) {
                        l.setApply_bank_id_name(bank1.getBankName());
                    }
                }
            }
        }
        return ResultInfo.success(loanApplicationPage);
    }


    /**
     * 详情
     * Author wzn
     * Date 2022/7/7 11:50
     */
    @PostMapping("/info")
    public ResultInfo info(String id) {
        LoanApplication loanApplication = loanApplicationService.getById(id);
        return ResultInfo.success(loanApplication);
    }


    @PostMapping("/getUserInfo")
    public ResultInfo getUserInfo(String fixmedins_code) {
        return ResultInfo.success(sysUserService.getUserInfo(fixmedins_code));
    }

    /**
     * 医保端贷款审核
     * Author wzn
     * Date 2022/7/7 15:47
     */
    @PostMapping("/check")
    public ResultInfo check(@RequestBody LoanApplication loanApplication) {
        loanApplicationService.check(loanApplication);
        return ResultInfo.success();
    }

    /**
     * 银行端贷款审核
     * Author wzn
     * Date 2022/7/9 8:43
     */
    @PostMapping("/bankCheck")
    public ResultInfo bankCheck(@RequestBody LoanApplication loanApplication) {
        loanApplicationService.bankCheck(loanApplication);
        return ResultInfo.success();
    }

    /**
     * 贷款申请撤销
     * Author wzn
     * Date 2022/7/7 16:57
     */
    @PostMapping("/del")
    public ResultInfo del(String id) {
        loanApplicationService.del(id);
        return ResultInfo.success();
    }

    /**
     * 定点医疗机构结算数据信息 -显示近3年数据
     * Author wzn
     * Date 2022/7/8 15:28
     */
    @PostMapping("/billingData")
    public ResultInfo billingData(String fixmedins_code) {

        return ResultInfo.success(loanApplicationService.billingData(fixmedins_code));
    }

    @PostMapping("/timeOut")
    public ResultInfo timeOut(String id) {
        loanApplicationService.timeOut(id);
        return ResultInfo.success();
    }

    /**
     *查看该机构申请所有的银行记录，除了传参这条
     * Author wzn
     * Date 2022/7/12 9:41
     */
    @PostMapping("/bankApplyList")
    public ResultInfo bankApplyList(@RequestBody LoanApplication loanApplication) {
        Page<LoanApplication> loanApplicationPage =  loanApplicationService.bankApplyList(loanApplication);
        if (null != loanApplicationPage.getRecords() && loanApplicationPage.getRecords().size() > 0) {
            for (LoanApplication l : loanApplicationPage.getRecords()) {
                if (null != l.getApply_bank_id() && !"".equals(l.getApply_bank_id())) {
                    Bank bank = new Bank();
                    bank.setBankNo(l.getApply_bank_id());
                    Bank bank1 = bankService.selectByNo(bank);
                    if (null != bank1) {
                        l.setApply_bank_id_name(bank1.getBankName());
                    }
                }
            }
        }
        return ResultInfo.success(loanApplicationPage);
    }



}
