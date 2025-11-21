package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.AdministrativeUnitMapper;
import com.jsdc.ybpt.mapper.BankMapper;
import com.jsdc.ybpt.model.Bank;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BankService extends BaseService<Bank> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BankMapper bankMapper;

    /**
     * 银行新增接口
     * Author wzn
     * Date 2022/7/4 11:43
     */
    public void addBank(Bank bank) {
        //唯一性校验
        boolean check = this.checkOnly(bank) ;
        if(!check){
            throw new CustomException("银行编码已存在，禁止重复添加") ;
        }
        SysUser sysUser = sysUserService.getUser();
        bank.setCreateUser(sysUser.getUsername());
        bank.setCreateTime(new Date());
        bank.setIs_del("0");
        bank.insert();
    }

    /**
     * 银行修改
     * Author wzn
     * Date 2022/7/4 11:44
     */
    public void updateBank(Bank bank) {
        boolean check = this.checkOnly(bank) ;
        if(!check){
            throw new CustomException("银行编码已存在，禁止重复添加") ;
        }
        SysUser sysUser = sysUserService.getUser();
        bank.setUpdateUser(sysUser.getUsername());
        bank.setUpdateTime(new Date());
        bank.updateById();
    }


    /**
    *银行列表接口
    * Author wzn
    * Date 2022/7/4 11:46
    */
    public Page<Bank> selectList(Bank bank) {
        Page<Bank> page = new Page<>(bank.getPageNo(), bank.getPageSize());
        QueryWrapper<Bank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (!"".equals(bank.getBankName()) && null != bank.getBankName()) {
            queryWrapper.like("bankName", bank.getBankName());
        }
        if (!"".equals(bank.getBankNo()) && null != bank.getBankNo()) {
            queryWrapper.eq("bankNo", bank.getBankNo());
        }
        Page<Bank> bankPage = bankMapper.selectPage(page, queryWrapper);
        return bankPage;
    }


    public List<Bank> selectListAll(){
        QueryWrapper<Bank> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("is_del","0");
        List<Bank> bankList = bankMapper.selectList(queryWrapper) ;
        return bankList;
    }

    /**
    *银行详情接口
    * Author wzn
    * Date 2022/7/4 11:48
    */
    public Bank info(String id) {
        return bankMapper.selectById(id);
    }


    public boolean checkOnly(Bank bank){
        boolean checkOnly = true ; //没有重复
        QueryWrapper<Bank> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("bankNo",bank.getBankNo()) ;
        queryWrapper.eq("is_del","0") ;
        List<Bank> bankList = bankMapper.selectList(queryWrapper) ;
        if(!bankList.isEmpty()){
            checkOnly = false ;
            if(null!=bank.getId()){
                if(bank.getId().equals(bankList.get(0).getId())){
                    checkOnly = true ;
                }
            }
        }
        return checkOnly ;
    }

    /**
    *根据与编码查询银行信息
    * Author wzn
    * Date 2022/7/6 19:15
    */
    public Bank selectByNo(Bank bank)
    {
        QueryWrapper<Bank> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("bankNo",bank.getBankNo()) ;
        return bankMapper.selectOne(queryWrapper);
    }




}
