package com.jsdc.ybpt.controller_agreement_sign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.ybpt.agreementsignModel.NetTagMechanism;
import com.jsdc.ybpt.agreementsignModel.NetTagRegister;
import com.jsdc.ybpt.service.agreementsignService.NetTagMechanismService;
import com.jsdc.ybpt.service.agreementsignService.NetTagRegisterService;
import com.jsdc.ybpt.service.agreementsignService.NetTagSealService;
import com.jsdc.ybpt.vo.ResultInfo;
import com.jsdc.ybpt.vo.agreementsignVo.FixmedinsBVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.Optional;

/**
 * @projectName: ybpt
 * @className: NetTagSignController
 * @author: wp
 * @description:
 * @date: 2022/9/6 17:15
 */
@RestController
@RequestMapping("sign")
public class NetTagSignController {
    @Autowired
    private NetTagMechanismService netTagMechanismService;
    @Autowired
    private NetTagSealService netTagSealService;
    @Autowired
    private NetTagRegisterService registerService;

    /**
     * 手动签章完成后回调接口
     * @param transaction_id
     * @param contract_id
     * @param result_code
     * @param result_desc
     * @param download_url
     * @param viewpdf_url
     * @param timestamp
     * @param msg_digest
     * @return
     */
    @RequestMapping("signNotify.do")
    public String notify(String transaction_id, String contract_id, String result_code, String result_desc, String download_url, String viewpdf_url, String timestamp, String msg_digest){
        System.out.println("result_code: " + result_code);
        System.out.println("transaction_id: " + transaction_id);
        System.out.println("viewpdf_url: " + viewpdf_url);
        System.out.println("download_url: " + download_url);
        System.out.println("contract_id: " + contract_id);
        if("3000".equals(result_code)){
            Optional<NetTagMechanism> o = Optional.ofNullable(netTagMechanismService.getById(transaction_id));
            if(o.isPresent()){
                NetTagMechanism netTagMechanism = o.get();
                netTagMechanism.setReview_url(viewpdf_url);
                netTagMechanism.setDownload_url(download_url);
                netTagMechanism.setUpdateTime(new Date());
                netTagMechanism.setHas_sign("2");
                netTagMechanism.setLicensing_id(contract_id);
                netTagMechanismService.updateById(netTagMechanism);
            }else{
                Optional<NetTagRegister> op = Optional.ofNullable(registerService.getById(transaction_id));
                if(op.isPresent()){
                    NetTagRegister register = op.get();
                    register.setRegister_status("2");
                    register.setAuth_pdf_view(viewpdf_url);
                    register.setAuth_pdf_download(download_url);
                    register.setUpdateTime(new Date());
                    registerService.updateById(register);
                }else{
                    Optional<NetTagMechanism> on = Optional.ofNullable(netTagMechanismService.getOne(Wrappers.<NetTagMechanism>lambdaQuery().eq(NetTagMechanism::getIs_del, "0").eq(NetTagMechanism::getCompany_transaction_id, transaction_id)));
                    if(on.isPresent()){
                        NetTagMechanism netTagMechanism = o.get();
                        netTagMechanism.setReview_url(viewpdf_url);
                        netTagMechanism.setDownload_url(download_url);
                        netTagMechanism.setUpdateTime(new Date());
                        netTagMechanism.setHas_sign("4");
                        netTagMechanismService.updateById(netTagMechanism);
                    }
                }
            }
        }
        return "200";
    }

    /**
     * 手动签章完成后页面跳转地址
     * @param transaction_id
     * @param contract_id
     * @param result_code
     * @param result_desc
     * @param download_url
     * @param viewpdf_url
     * @param timestamp
     * @param msg_digest
     * @return
     */
    @RequestMapping("signReturn.do")
    public String returnUrl(String transaction_id, String contract_id, String result_code, String result_desc, String download_url, String viewpdf_url, String timestamp, String msg_digest){
        System.out.println("result_code: " + result_code);
        System.out.println("transaction_id: " + transaction_id);
        return "";
    }

    /**
     * 实名注册回调接口
     * @return
     * statusDesc=&certStatus=0&appId=406848&customerId=D5E004F8B83D54F4DD9D88D22CF5A573&sign=ODBBMkE1MzJGNDMyMUEzRDg3REJGQzY2MDA2NEZDMjA1MzNDNUJCNA%3D%3D&authenticationType=1&payStatus=&status=2&serialNo=4a3d0efc00e04188b3da8f95ab80006b&timestamp=20220908144945
     */
    @RequestMapping("authenticationNotify.do")
    public String authenticationNotify(@RequestBody String json){
        System.out.println(json);
        netTagSealService.authenticationNotify(json);
        return "200";
    }

    /**
     * 用户认证
     * @param type
     * @return
     */
    @RequestMapping(value = "authenticationbak.do")
    public ResultInfo authenticationbak(@RequestParam(value = "file", required = false) MultipartFile file, String type) throws Exception {
        return netTagSealService.authenticationbak(type, file);
    }

    /**
     * 企业注册、认证
     * @return
     */
    @RequestMapping(value = "companyAuthentication.do")
    public ResultInfo companyAuthentication() throws Exception {
        return netTagSealService.companyAuthentication();
    }

    /**
     * 个人注册、认证
     * @return
     */
    @RequestMapping(value = "personalAuthentication.do")
    public ResultInfo personalAuthentication(){
        return netTagSealService.personalAuthentication();
    }

    /**
     * 变更
     * @return
     */
    @RequestMapping(value = "changeCompanyInfo.do")
    public ResultInfo changeCompanyInfo(){
        return netTagSealService.changeCompanyInfo();
    }



    /**
     * 授权自动签章
     * @return
     */
    @RequestMapping(value = "authAutoSign.do")
    public ResultInfo authAutoSign(){
        return netTagSealService.authAutoSign();
    }


    /**
     * 查询当前用户是否认证完成
     * @return
     */
    @RequestMapping(value = "checkAuthentication.do")
    public ResultInfo checkAuthentication(){
        return netTagSealService.checkAuthentication();
    }

    @RequestMapping(value = "getOrgPage.do")
    public ResultInfo getOrgPage(@RequestBody FixmedinsBVo vo){
        return netTagSealService.getOrgPage(vo);
    }

    @RequestMapping(value = "getRegisterInfo.do")
    public ResultInfo getRegisterInfo(){
        return netTagSealService.getRegisterInfo();
    }


    /**
     *法大大回调失败,企业认证
     * Author wzn
     * Date 2023/11/6 15:00
     */
    @RequestMapping(value = "manualOperationCom.do")
    public ResultInfo manualOperationCom(String customerId){
        netTagSealService.manualOperationCom(customerId) ;
        return ResultInfo.success();
    }

    /**
     *法大大回调失败,个人认证
     * Author wzn
     * Date 2023/11/6 15:00
     */
    @RequestMapping(value = "manualOperationPer.do")
    public ResultInfo manualOperationPer(String customerId){
        netTagSealService.manualOperationPer(customerId) ;
        return ResultInfo.success();
    }
}
