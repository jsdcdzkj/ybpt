package com.jsdc.ybpt.controller_agreement_sign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.agreementsignModel.NetTagAppeal;
import com.jsdc.ybpt.service.agreementsignService.NetTagAppealService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 协议网签-申诉表
 */
@RestController
@RequestMapping("/tagappeal")
public class NetTagAppealController {

    @Autowired
    private NetTagAppealService netTagAppealService;
    @Autowired
    private NetTagMechanismController netTagMechanismController;

    /**
     * 列表查询
     */
    @RequestMapping("/getAllNetTagAppeal")
    public ResultInfo getAllNetTagAppeal(@RequestBody NetTagAppeal bean) {
        Page<NetTagAppeal> pages = netTagAppealService.getAllNetTagAppeal(bean.getPageIndex(), bean.getPageSize(), bean);
        return ResultInfo.success(pages);
    }

    /**
     * id查询
     */
    @RequestMapping("/getOneNetTagAppeal")
    public ResultInfo getOneNetTagAppeal(String id) {
        NetTagAppeal netTagAppeal = netTagAppealService.getOneNetTagAppeal(id);
        return ResultInfo.success(netTagAppeal);
    }

    /**
     * 确认
     */
    @RequestMapping("/getSure")
    public ResultInfo getSure(String id) {
        return netTagAppealService.getSure(id);
    }

    /**
     * 申诉保存
     */
    @RequestMapping("/onSave")
    public ResultInfo onSave(@RequestBody NetTagAppeal bean) {
        return netTagAppealService.onSave(bean);
    }

}
