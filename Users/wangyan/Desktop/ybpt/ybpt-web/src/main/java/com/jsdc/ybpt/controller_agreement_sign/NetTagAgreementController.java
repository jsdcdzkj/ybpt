package com.jsdc.ybpt.controller_agreement_sign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagAgreement;
import com.jsdc.ybpt.service.agreementsignService.NetTagAgreementService;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 协议网签-协议表
 */
@RestController
@RequestMapping("/tagagreement")
public class NetTagAgreementController {

    @Autowired
    private NetTagAgreementService netTagAgreementService;

    /**
     * 分页查询协议信息
     */
    @RequestMapping("/selectNetTagAgreement")
    public ResultInfo selectNetTagAgreement(@RequestBody NetTagAgreement netTagAgreement) {
        int pageIndex = netTagAgreement.getPageNo() == null ? 1 : netTagAgreement.getPageNo();
        int pageSize = netTagAgreement.getPageSize() == null ? 10 : netTagAgreement.getPageSize();
        Page<NetTagAgreement> pages = netTagAgreementService.selectByAgreementNo(pageIndex, pageSize, netTagAgreement);
        return ResultInfo.success(pages);
    }

    /**
     * 查询协议信息列表
     */
    @RequestMapping("/getList")
    public ResultInfo getList(@RequestBody NetTagAgreement netTagAgreement) {
        List<NetTagAgreement> list = netTagAgreementService.getList(netTagAgreement);
        return ResultInfo.success(list);
    }

    /**
     * 新增协议信息
     */
    @RequestMapping(value = "/insertNetTagAgreement",method = RequestMethod.POST)
    public ResultInfo insertNetTagAgreement(@RequestBody NetTagAgreement netTagAgreement) {
        if (StringUtils.isEmpty(netTagAgreement.getId())){
            return netTagAgreementService.insertNetTagAgreement(netTagAgreement);
        }
      return netTagAgreementService.updateNetTagAg(netTagAgreement);
    }

    /**
     * 修改协议信息
     */
    @RequestMapping("/updateNetTagAgreement")
    public ResultInfo updateNetTagAgreement(@RequestBody NetTagAgreement netTagAgreement) {
        int i = netTagAgreementService.updateNetTagAgreement(netTagAgreement);
        return ResultInfo.success(i);
    }

    /**
     * 删除协议信息
     */
    @RequestMapping("/deleteNetTagAgreement")
    public ResultInfo deleteNetTagAgreement(@RequestBody NetTagAgreement netTagAgreement) {
        int i = netTagAgreementService.deleteNetTagAgreement(netTagAgreement);
        return ResultInfo.success(i);
    }

    /**
     * 根据当前登录人拿到甲方信息
     */
    @RequestMapping("/selectNetTagAgreementByLogin")
    public ResultInfo selectNetTagAgreementByLogin() {
        return ResultInfo.success(netTagAgreementService.selectNetTagAgreementByLogin());
    }

    /**
     * 修改协议状态
     */
    @RequestMapping("/updateNetTagAgreementStatus")
    public ResultInfo updateNetTagAgreementStatus(@RequestBody NetTagAgreement netTagAgreement) {
        return netTagAgreementService.updateNetTagAgreementStatus(netTagAgreement);
    }

    /**
     * 根据ID查询
     * @param netTagAgreement
     * @return
     */
    @RequestMapping("/getOneInfo")
    public ResultInfo getOneInfo(@RequestBody NetTagAgreement netTagAgreement){
        return ResultInfo.success(netTagAgreementService.getOneInfo(netTagAgreement.getId()));
    }

    /**
     * 查询回流库机构
     */
    @RequestMapping("/selectHlkList")
    public ResultInfo selectHlkList(@RequestBody NetTagAgreement netTagAgreement) {
        return netTagAgreementService.selectHlkList(netTagAgreement);
    }
}
