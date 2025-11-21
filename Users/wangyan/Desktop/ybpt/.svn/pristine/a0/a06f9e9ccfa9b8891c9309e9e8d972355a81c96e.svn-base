package com.jsdc.fddserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fadada.sdk.base.client.FddBaseClient;
import com.fadada.sdk.base.model.req.*;
import com.fadada.sdk.extra.client.FddExtraClient;
import com.fadada.sdk.extra.model.req.DefaultSignatureParams;
import com.fadada.sdk.verify.client.FddVerifyClient;
import com.fadada.sdk.verify.model.req.ApplyCertParams;
import com.fadada.sdk.verify.model.req.CompanyVerifyUrlParams;
import com.fadada.sdk.verify.model.req.FindCompanyCertParams;
import com.fadada.sdk.verify.model.req.PersonVerifyUrlParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @projectName: fddServer
 * @className: SignService
 * @author: wp
 * @description:
 * @date: 2022/9/14 17:08
 */
@Service
public class SignService {
    @Value("${sign.appid}")
    private String appId;
    @Value("${sign.appkey}")
    private String appKey;
    @Value("${sign.version}")
    private String v;
    @Value("${sign.host}")
    private String host;
    @Value("${sign.notify-url}")
    private String notifyUrl;

    /**
     * 注册账号
     * @param openId
     * @param accountType
     * @return
     *  { "code": 1, "data": "0E76E309xxxxxxF5278Cxxxxxx3CA54D", "msg": "success" }
     */
    public JSONObject regedit(String openId, String accountType){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        RegisterAccountParams params = new RegisterAccountParams();
        //平台方自定义唯一标识
        params.setOpenId(openId);
        //账号类型1个人 2企业
        params.setAccountType(accountType);
        String result = client.invokeRegisterAccount(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }


    /**
     * 获取企业实名认证的网址
     * @param customerId
     * @return
     *  {
     *     "code":1,
     *     "data":{
     *         "transactionNo":"c9bf20031cf34d54a19622f98ad5d18c",
     *         "url":"aHR0cHM6Ly9yZWFsbmFtZXZlcmlmeS10ZXN0LmZhZGFkYS5jb20vZmRkQXV0aGVudGljYXRpb25TZXJ2aWNlL3YxL2FwaS9zeW5zQXV0aGVudGljYXRpb24uYWN0aW9uP3RyYW5zYWN0aW9uX25vPTZCNzlFRDZCMDg0MERFOTA4NjMwNTY0QzFGNDc5NEJCQzg2N0RFRTU3OURDMDQxMzQ3ODNDQjczRDAwOTQwNjQ0RENDRjAwNjE2NzI1NUFFJnNpZ249TVVNeVJUTkRRelU0UlRkQk5EaERSams1TVRZNE5EQkNPVFV5TkRORk5EaEdNa0ZGTVVaR1JnPT0mYXBwX2lkPTQwNjg0OCZ0aW1lc3RhbXA9MTY2MjQyNzA4ODg5Mg=="
     *     },
     *     "msg":"success"
     * }
     */
    public JSONObject authenticationCompany (String customerId){
        FddVerifyClient client = new FddVerifyClient(appId, appKey, v, host);
        CompanyVerifyUrlParams params = new CompanyVerifyUrlParams();
        params.setCustomerId(customerId);
        params.setPageModify("1");//1允许 2不允许 默认为1
        //以下是非必填参数
        params.setVerifiedWay("");
        //实名认证套餐类型： 0：标准方案（对公打款+纸质审核+法人身份+法 人授权）；1：对公打款；2：纸质审核；3：法人身份（授权）认证
        params.setMVerifiedWay("");
        //管理员认证套餐类型： 0：三要素标准方案； 1：三要素补充方 案； 2：四要素标准方案； 3：四要素补充方案； 4：纯三要素方案； 5：纯四要素方案；
        params.setLegalAllowCompanyVerifyWay("");
        //指定管理员为"法人"身份下，允许的认证方式∶ 1.法人身份认证;2.对公打款认证;3.纸质材料认证;
        params.setAgentAllowCompanyVerifyWay("");
        //指定管理员为"代理人"身份下，允许的认证方式∶ 1.法人授权认证;2.对公打款认证;3.纸质材料认证;可同时传入多个值，如"1,2“，表示代理身份下，只允 许"法人授权认证"及"对公打款认证"方式，页面隐藏纸质材料认证"方式;传入空值时，表示允许所有认证方式;
        params.setIdPhotoOptional("");
        //0-只需要头像面 1-头像面与国徽面都需要 2-都不需要 默认为0 //公司信息
        CompanyVerifyUrlParams.CompanyInfo companyInfo = new CompanyVerifyUrlParams.CompanyInfo();
        companyInfo.setCompanyName("");
        companyInfo.setCreditNo("");
        companyInfo.setCreditImagePath("");
        params.setCompanyInfo(null);
        params.setCompanyPrincipalType("");
        //1.法人，2代理人 //法人信息
        CompanyVerifyUrlParams.LegalInfo legalInfo = new CompanyVerifyUrlParams.LegalInfo();
        legalInfo.setLegalName("");
        legalInfo.setLegalId("");
        legalInfo.setLegalMobile("");
        legalInfo.setLegalIdFrontPath("");
        legalInfo.setBankCardNo("");
        params.setLegalInfo(null);
        params.setLegalIdFrontImg(null);
        //代理人信息
        CompanyVerifyUrlParams.AgentInfo agentInfo = new CompanyVerifyUrlParams.AgentInfo();
        agentInfo.setAgentName("");
        agentInfo.setAgentId("");
        agentInfo.setAgentMobile("");
        agentInfo.setAgentIdFrontPath("");
        agentInfo.setBankCardNo("");
        params.setAgentInfo(null);
        params.setAgentIdFrontImg(null);
        params.setCreditImage(null);
        //银行卡信息
        CompanyVerifyUrlParams.BankInfo bankInfo = new CompanyVerifyUrlParams.BankInfo();
        bankInfo.setBankName("招商银行");
        bankInfo.setBankId("");
        bankInfo.setSubbranchName("");
        params.setBankInfo(null);
        params.setBankProvinceName("");
        params.setBankCityName("");
        params.setCertFlag("");
        //是否认证成功后自动申请实名证书 参数值为“0”：不申请，参数值 为“1”：自动申请
        params.setOption("");
        //add（新增）modify（修改）不传默认add
        params.setVerifiedSerialNo("");
        //管理员认证流水号
        params.setAuthorizationFile(null);
        //企业注册申请表
        params.setLegalName("");
        //法人姓名（代理人认证想要传法人姓名可用此参数）
        params.setMobile("");
        //法人/代理人/个人手机号
        params.setLegalAuthorizedMobile("");
        //法人授权手机号,当接口中传入了“法人授权手机号”字段 时，页面中选择法人授权认证时，会将传入的手机号展示出来
        params.setLang("");
        //zh:中文，en:英文默认：中文
        params.setOrganization_type("");
        //0：企业；1：政府/事业单位；2：其他组织；3：个体工商户
        params.setReturnUrl("");
        params.setNotifyUrl(notifyUrl + "/sign/authenticationNotify.do");
        String result = client.invokeCompanyVerifyUrl(params);
        JSONObject res = JSON.parseObject(result);
        return res;
    }

    /**
     * 获取个人认证页面地址
     * @param customerId
     */
    public JSONObject authenticationPersonal(String customerId){
        FddVerifyClient client = new FddVerifyClient(appId, appKey, v, host);
        PersonVerifyUrlParams params = new PersonVerifyUrlParams();
        params.setCustomerId(customerId);
        //客户编号
        params.setVerifiedWay("0");
        //实名认证套餐类型
        params.setPageModify("1");
        //是否允许用户页面修改 1允许 2不允许 //以下是非必填参数
        params.setNotifyUrl(notifyUrl + "/sign/authenticationNotify.do");
        //异步回调地址
        params.setReturnUrl("");
        //同步通知url
        params.setCustomerName("");
        //姓名
        params.setCustomerIdentType("");
        //证件类型
        params.setCustomerIdentNo("");
        //证件号码
        params.setMobile("");
        //手机号码
        params.setIdentFrontPath("");
        //证件正面照下载地址
        params.setIdentBackPath("");
        //证件反面照下载地址
        params.setResultType("");
        //刷脸是否显示结果页面
        params.setCertFlag("");
        //是否认证成功后自动申请实名证书
        params.setCertType("");
        //证件类型
        params.setBankCardNo("");
        //个人银行卡
        params.setOption("");
        //不传默认add
        params.setIdPhotoOptional("");
        //是否需要上传身份照片
        params.setIsMinProgram("");
        //是否跳转法大大公证处小程序认证
        params.setLang("");
        //zh：中文；en：英文
        params.setIsAllowOverseasBankCardAuth("");
        //海外用户是否支持银行卡认证
        //params.setIdentFrontImg(new File(""));
        //证件正面照图片文件
        //params.setIdentBackImg(new File(""));
        //证件反面照图片文件
        String result = client.invokePersonVerifyUrl(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }

    /**
     * 证书申请
     * @param customerId
     * @param transactionNo
     * @return
     *  { "code": 1, "msg": "success" }
     */
    public JSONObject applyCertificate(String customerId, String transactionNo){

        FddVerifyClient client = new FddVerifyClient(appId, appKey, v, host);
        ApplyCertParams params = new ApplyCertParams();
        params.setCustomerId(customerId);
        //客户编号
        params.setVerifiedSerialNo(transactionNo);
        //填写获取实名认证地址返回的交易号ransactionNo
        String result = client.invokeApplyCert(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }

    /**
     * 自定义印章
     * @param customerId
     * @param content
     */
    public String customSeal(String customerId, String content){
        FddBaseClient baseClient = new FddBaseClient(appId, appKey, v, host);
        CustomSignatureParams params = new CustomSignatureParams();
        params.setContent(content);
        //印章展示的文字
        params.setCustomerId(customerId);
        //客户编号
        String result = baseClient.invokeCustomSignature(params);
        JSONObject res = JSON.parseObject(result);
        if(res.getString("code").equals("1")){
            String data = res.getString("data");
            JSONObject obj = JSON.parseObject(data);
            String base64 = obj.getString("signature_img_base64");
            return base64;
        }
        return null;
    }

    /**
     * 上传印章
     * @param customerId
     * @param base64 文件base64
     * @return
     *  { "code": 1, "data": { "signature_id": "12xxxx173", "signature_sub_info":null, "status":null },"msg": "success" }
     */
    public JSONObject uploadSeal(String customerId, String base64){
        FddBaseClient baseClient = new FddBaseClient(appId, appKey, v, host);
        AddSignatureParams params = new AddSignatureParams();
        params.setCustomerId(customerId);
        //客户编号 //下面章图片base64、签章图片、图片公网地址三选一
        params.setSignatureImgBase64(base64);
        String result = baseClient.invokeAddSignature(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }

    public JSONObject setDefaultSeal(String customerId, String signatureId){
        FddExtraClient client = new FddExtraClient(appId, appKey, v, host);
        DefaultSignatureParams params = new DefaultSignatureParams();
        params.setCustomerId(customerId);
        params.setSignatureId(signatureId);
        String result = client.invokeDefaultSignature(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }

    /**
     * 手动签章
     * @param customerId
     * @param transactionId
     * @param contractId
     * @param docTitle
     * @param signType 0:全部 1：标准 2：手写
     * @return
     *  http://xxxx/api/extsign.api?app_id=90........DMxMTFBNw==
     */
    public String manualSign(String customerId, String transactionId, String contractId, String docTitle, String signType){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        ExtSignParams params = new ExtSignParams();
        params.setTransactionId(transactionId);//平台自定义唯一交易号
        params.setContractId(contractId);//此处传入调用上传或填充合同接口成功时定义 的合同编号
        params.setCustomerId(customerId);//此处传入认证成功后成功申请证书的客户编号
        params.setDocTitle(docTitle);
        params.setMobileSignType(signType);
        params.setPositionType("1");//0-关键字（默认）1-坐标
        //params.setSignKeyword("受托方签字");
        //params.setKeywordStrategy("0");
        params.setPcHandSignature("1");
        params.setKeyx("500");
        params.setKeyy("500");
        params.setSignatureShowTime("2");
        params.setNotifyUrl(notifyUrl + "/sign/signNotify.do");
        //0-所有关键字签章 （默认） 1-第一个关键字签章 2-最后一个 关键字签章
        String result = client.invokeExtSign(params);
        System.out.println(result);
        return result;
    }

    /**
     * 自动签章
     * @param customerId 客户编号
     * @param contractId 合同编号
     * @param docTitle 标题
     * @param positionType 签章定位类型 0：关键字  1：坐标
     * @param keyWord 关键字
     * @param keywordStrategy 0：所有关键字签章 1：第一个关键字签章 ； 2：最后一个 关键字签章
     * @return
     *  {"code":"1000","download_url":"https:\/\/testapi.fadada.com:8443\/api\/\/getdocs.action?app_id=406848&timestamp=20220907090756&v=2.0&msg_digest=OEZENzk4RDk4NzY2QjRGNTgyNkI2Nzk2NTY3RkI3MDk2NkE2ODU5QQ==&transaction_id=b4c18b45d9b445b69e327f3c5ea84e0a&send_app_id=null","msg":"文档签署成功","result":"success","viewpdf_url":"https:\/\/testapi.fadada.com:8443\/api\/\/viewdocs.action?app_id=406848&timestamp=20220907090756&v=2.0&msg_digest=OEZENzk4RDk4NzY2QjRGNTgyNkI2Nzk2NTY3RkI3MDk2NkE2ODU5QQ==&transaction_id=b4c18b45d9b445b69e327f3c5ea84e0a&send_app_id=null"}
     */
    public JSONObject authSign(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        ExtSignAutoParams params = new ExtSignAutoParams();
        String transactionId = UUID.randomUUID().toString().replaceAll("-", "");
        params.setTransactionId(transactionId);//平台自定义唯一交易号
        params.setContractId(contractId);//此处传入调用上传或填充合同接口成功时定义 的合同编号
        params.setCustomerId(customerId);//此处传入认证成功后成功申请证书的客户编号
        params.setDocTitle(docTitle);
        params.setPositionType(positionType);//0-关键字（默认）1-坐标
        params.setSignKeyword(keyWord);
        params.setSignatureShowTime("2");
        params.setKeywordStrategy(keywordStrategy);//0：所有关键字签章 1：第一个关键字签章 ； 2：最后一个 关键字签章
        String result = client.invokeExtSignAuto(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }

    /**
     * @param customerId 客户编号
     * @param contractId 合同编号
     * @param docTitle 标题
     * @param positionType 签章定位类型 0：关键字  1：坐标
     * @param keyWord 关键字
     * @param keywordStrategy 0：所有关键字签章 1：第一个关键字签章 ； 2：最后一个 关键字签章
     * @param keyx: 水平偏移量
     * @param keyy: 垂直偏移量
     * @return JSONObject
     * @author wp
     * @description 自动签章-资金清算
     * @date 2023/1/12 10:44
     */
    public JSONObject autoSignForZjqs(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy, String keyx, String keyy){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        ExtSignAutoParams params = new ExtSignAutoParams();
        String transactionId = UUID.randomUUID().toString().replaceAll("-", "");
        params.setTransactionId(transactionId);//平台自定义唯一交易号
        params.setContractId(contractId);//此处传入调用上传或填充合同接口成功时定义 的合同编号
        params.setCustomerId(customerId);//此处传入认证成功后成功申请证书的客户编号
        params.setDocTitle(docTitle);
        params.setPositionType(positionType);//0-关键字（默认）1-坐标
        params.setSignKeyword(keyWord);
        params.setSignatureShowTime("2");
        params.setKeywordStrategy(keywordStrategy);//0：所有关键字签章 1：第一个关键字签章 ； 2：最后一个 关键字签章
        params.setKeyx(keyx);//水平偏移量
        params.setKeyy(keyy);//垂直偏移量
        String result = client.invokeExtSignAuto(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }




    public JSONObject autoSignForZjqsBySeal(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy, String keyx, String keyy, String signatureId) {
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        ExtSignAutoParams params = new ExtSignAutoParams();
        String transactionId = UUID.randomUUID().toString().replaceAll("-", "");
        params.setTransactionId(transactionId);//平台自定义唯一交易号
        params.setContractId(contractId);//此处传入调用上传或填充合同接口成功时定义 的合同编号
        params.setCustomerId(customerId);//此处传入认证成功后成功申请证书的客户编号
        params.setDocTitle(docTitle);
        params.setPositionType(positionType);//0-关键字（默认）1-坐标
        params.setSignKeyword(keyWord);
        params.setSignatureShowTime("2");
        params.setSignatureId(signatureId);
        params.setKeyx(keyx);//水平偏移量
        params.setKeyy(keyy);//垂直偏移量
        params.setKeywordStrategy(keywordStrategy);//0：所有关键字签章 1：第一个关键字签章 ； 2：最后一个 关键字签章
        String result = client.invokeExtSignAuto(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }
    public JSONObject authSignBySeal(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy, String signatureId){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        ExtSignAutoParams params = new ExtSignAutoParams();
        String transactionId = UUID.randomUUID().toString().replaceAll("-", "");
        params.setTransactionId(transactionId);//平台自定义唯一交易号
        params.setContractId(contractId);//此处传入调用上传或填充合同接口成功时定义 的合同编号
        params.setCustomerId(customerId);//此处传入认证成功后成功申请证书的客户编号
        params.setDocTitle(docTitle);
        params.setPositionType(positionType);//0-关键字（默认）1-坐标
        params.setSignKeyword(keyWord);
        params.setSignatureShowTime("2");
        params.setSignatureId(signatureId);
        params.setKeyx("50");
        params.setKeywordStrategy(keywordStrategy);//0：所有关键字签章 1：第一个关键字签章 ； 2：最后一个 关键字签章
        String result = client.invokeExtSignAuto(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }


    /**
     * 上传合同
     * @param filePath
     * @param title
     * @return
     *  { "code": "1000", "msg": "操作成功", "result": "success" }
     */
    public String uploadContract(String filePath, String title){
        FddBaseClient baseClient = new FddBaseClient(appId, appKey, v, host);
        UploadDocsParams params = new UploadDocsParams();
        String contractId = UUID.randomUUID().toString().replaceAll("-", "");
        params.setContractId(contractId);
        //自定义合同id
        params.setDocTitle(title);
        //合同标题 //PDF文档和下载地址二选一
        //params.setFile(new File(filePath));
        params.setDocType(".pdf");
        params.setDocUrl(filePath);
        //合同类型 目前仅支持pdf格式
        String result = baseClient.invokeUploadDocs(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        if("1000".equals(res.getString("code"))){
            return contractId;
        }
        return null;
    }

    /**
     * 查询自动签章授权状态
     * @param customerId
     * @return
     */
    public JSONObject queryAutoSignAuthority(String customerId){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        GetAuthStatusParams params = new GetAuthStatusParams();
        params.setCustomerId(customerId);
        //传入调用授权自动签接口授权之后的客户编号
        String result = client.invokeGetAuthStatus(params);
        System.out.println(result);
        JSONObject res = JSON.parseObject(result);
        return res;
    }

    /**
     * 授权自动签章网址
     * @param customerId
     * @return
     */
    public String autoSignAuthorize(String customerId, String transactionId){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        BeforeAuthSignParams params = new BeforeAuthSignParams();
        //平台自定义唯一交易号
        params.setTransactionId(transactionId);
        //1:授权自动签（目前只能填1）
        params.setAuthType("1");
        //指该份线上授权委托书的合同编号，自定义即可
        params.setContractId(transactionId);
        //传入注册返回的个人或企业客户编号
        params.setCustomerId(customerId);
        //同步通知签署结果地址
        //异步通知签署结果地址
        params.setNotifyUrl(notifyUrl + "/sign/signNotify.do");
        String result = client.invokeBeforeAuthSign(params);
        System.out.println(result);
        return result;
    }

    /**
     * 查询实名认证企业信息
     * @param transactionNo
     * @return
     */
    public String companyCertInfo(String transactionNo){
        FddVerifyClient client = new FddVerifyClient(appId, appKey, v, host);
        FindCompanyCertParams params = new FindCompanyCertParams();
        //此处填获取企业实名认证地址返回的交易号
        params.setVerifiedSerialNo(transactionNo);
        String result = client.invokeFindCompanyCert(params);
        System.out.println(result);
        return result;
    }

    /**
     * 合同下载
     * @param contractid
     * @return
     */
    public String downloadPdf(String contractid){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        DownloadPdfParams params = new DownloadPdfParams();
        params.setContractId(contractid); // 合同编号
        String result = client.invokeDownloadPdf(params);
        System.out.println(result);
        return result;
    }

    /**
    *合同查看
    * Author wzn
    * Date 2024/1/2 15:41
    */
    public String viewPdf(String contractid){
        FddBaseClient client = new FddBaseClient(appId, appKey, v, host);
        ViewPdfURLParams params = new ViewPdfURLParams();
        params.setContractId(contractid); // 合同编号
        String result = client.invokeViewPdfURL(params);
        System.out.println(result);
        return result;
    }
}
