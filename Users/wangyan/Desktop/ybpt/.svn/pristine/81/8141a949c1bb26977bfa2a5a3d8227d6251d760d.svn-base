package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.Bank;
import com.jsdc.ybpt.service.BankService;
import com.jsdc.ybpt.service.LoanApplicationService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.BankVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    @Autowired
    private SysDictService sysDictService ;
    @Autowired
    private LoanApplicationService loanApplicationService ;

    /**
    *银行新增
    * Author wzn
    * Date 2022/7/4 13:50
    */
    @PostMapping("/addBank")
    public ResultInfo addCiviWorkerInfo(@RequestBody Bank bank) {
        bankService.addBank(bank);
        return ResultInfo.success();
    }


    /**
    *银行修改
    * Author wzn
    * Date 2022/7/4 13:50
    */
    @PostMapping("/updateBank")
    public ResultInfo updateCiviWorkerInfo(@RequestBody Bank bank) {
        bankService.updateBank(bank);
        return ResultInfo.success();
    }

    /**
    *银行删除接口
    * Author wzn
    * Date 2022/7/4 13:53
    */
    @PostMapping("/delBank")
    public ResultInfo delBank(@RequestBody Bank bank) {
        Long count = loanApplicationService.getCount(bank.getBankNo()) ;
        if(count != 0){
            throw new CustomException("已启用,禁止删除！") ;
        }
        Bank bank1 = new Bank() ;
        bank1.setId(bank.getId());
        bank1.setIs_del("1");
        bankService.updateById(bank1);
        return ResultInfo.success();
    }

    /**
    *银行列表
    * Author wzn
    * Date 2022/7/4 13:53
    */
    @PostMapping("/selectList")
    public ResultInfo selectList(@RequestBody Bank bank){
        Page<Bank> bankPage = bankService.selectList(bank) ;
        return ResultInfo.success(bankPage);
    }


    @PostMapping("/selectListAll")
    public ResultInfo selectListAll(){
        List<Bank> bankList = bankService.selectListAll() ;
        return ResultInfo.success(bankList);
    }


    /**
    *银行详情
    * Author wzn
    * Date 2022/7/4 13:53
    */
    @PostMapping("/info")
    public ResultInfo info(String id){
        Bank bank = bankService.info(id) ;
        return ResultInfo.success(bank);
    }

    /**
    *银行放贷统计
    * Author wzn
    * Date 2022/7/12 14:19
    */
    @PostMapping("/bankTj")
    public ResultInfo bankTj(String fixmedins_code){
        BankVo bankVo = loanApplicationService.bankTj(fixmedins_code) ;
        return ResultInfo.success(bankVo);
    }





}
