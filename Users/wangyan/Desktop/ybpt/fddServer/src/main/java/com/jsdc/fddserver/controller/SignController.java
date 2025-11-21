package com.jsdc.fddserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsdc.fddserver.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: fddServer
 * @className: SignController
 * @author: wp
 * @description:
 * @date: 2022/9/14 17:04
 */
@RestController
public class SignController {

    @Autowired
    private SignService signService;

    /**
     * 注册
     * @param openId
     * @param accountType
     * @return
     */
    @RequestMapping("regedit.do")
    public JSONObject regedit(String openId, String accountType){
        return signService.regedit(openId, accountType);
    }

    /**
     * 获取企业认证页面地址
     * @param customerId
     * @return
     */
    @RequestMapping("authenticationCompany.do")
    public JSONObject authenticationCompany(String customerId){
        return signService.authenticationCompany(customerId);
    }

    /**
     * 获取个人认证页面地址
     * @param customerId
     * @return
     */
    @RequestMapping("authenticationPersonal.do")
    public JSONObject authenticationPersonal(String customerId){
        return signService.authenticationPersonal(customerId);
    }

    /**
     * 证书申请
     * @param customerId
     * @return
     */
    @RequestMapping("applyCertificate.do")
    public JSONObject applyCertificate(String customerId, String transactionNo){
        return signService.applyCertificate(customerId, transactionNo);
    }

    /**
     * 自定义印章
     * @param customerId
     * @param content
     * @return
     */
    @RequestMapping("customSeal.do")
    public String customSeal(String customerId, String content){
        return signService.customSeal(customerId, content);
    }

    /**
     * 上传印章
     * @param customerId
     * @param base64
     * @return
     */
    @RequestMapping("uploadSeal.do")
    public JSONObject uploadSeal(String customerId, String base64){
        return signService.uploadSeal(customerId, base64);
    }

    /**
     * 设置默认印章
     * @param customerId
     * @param signatureId
     * @return
     */
    @RequestMapping("setDefaultSeal.do")
    public JSONObject setDefaultSeal(String customerId, String signatureId){
        return signService.setDefaultSeal(customerId, signatureId);
    }

    /**
     * 手动签章
     * @param customerId
     * @param transactionId
     * @param contractId
     * @param docTitle
     * @param signType
     * @return
     */
    @RequestMapping("manualSign.do")
    public String manualSign(String customerId, String transactionId, String contractId, String docTitle, String signType){
        return signService.manualSign(customerId, transactionId, contractId, docTitle, signType);
    }

    /**
     * 自动签章
     * @param customerId
     * @param contractId
     * @param docTitle
     * @param positionType
     * @param keyWord
     * @param keywordStrategy
     * @return
     */
    @RequestMapping("authSign.do")
    public JSONObject authSign(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy){
        return signService.authSign(customerId, contractId, docTitle, positionType, keyWord, keywordStrategy);
    }

    /**
     * 自动签章-资金清算
     * @param customerId
     * @param contractId
     * @param docTitle
     * @param positionType
     * @param keyWord
     * @param keywordStrategy
     * @param keyx
     * @param keyy
     * @return
     */
    @RequestMapping("autoSignForZjqs.do")
    public  JSONObject autoSignForZjqs(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy, String keyx, String keyy){
        return signService.autoSignForZjqs(customerId, contractId, docTitle, positionType, keyWord, keywordStrategy, keyx, keyy);
    }

    @RequestMapping("authSignBySeal.do")
    public JSONObject authSign(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy, String signatureId){
        return signService.authSignBySeal(customerId, contractId, docTitle, positionType, keyWord, keywordStrategy, signatureId);
    }

    @RequestMapping("autoSignForZjqsBySeal.do")
    public JSONObject autoSignForZjqsBySeal(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy, String keyx, String keyy, String signatureId) {
        return signService.autoSignForZjqsBySeal(customerId, contractId, docTitle, positionType, keyWord, keywordStrategy, keyx, keyy, signatureId);
    }
    /**
     * 上传合同
     * @param filePath
     * @param title
     * @return
     */
    @RequestMapping("uploadContract.do")
    public String uploadContract(String filePath, String title){
        return signService.uploadContract(filePath, title);
    }

    /**
     * 查询自动签章授权状态
     * @param customerId
     * @return
     */
    @RequestMapping("queryAutoSignAuthority.do")
    public JSONObject queryAutoSignAuthority(String customerId){
        return signService.queryAutoSignAuthority(customerId);
    }

    /**
     * 授权自动签章网址
     * @param customerId
     * @return
     */
    @RequestMapping("autoSignAuthorize.do")
    public String autoSignAuthorize(String customerId, String transactionId){
        return signService.autoSignAuthorize(customerId, transactionId);
    }

    /**
     * 查询实名认证企业信息
     * @param transactionNo
     * @return
     */
    @RequestMapping("companyCertInfo.do")
    public String companyCertInfo(String transactionNo){
        return signService.companyCertInfo(transactionNo);
    }

    /**
     * 合同下载
     * @param contractid
     * @return
     */
    @RequestMapping("downloadPdf.do")
    public String downloadPdf(String contractid){
        return signService.downloadPdf(contractid);
    }

    /**
    *合同查看
    * Author wzn
    * Date 2024/1/2 15:42
    */
    @RequestMapping("viewPdf.do")
    public String viewPdf(String contractid){
        return signService.viewPdf(contractid);
    }



}
