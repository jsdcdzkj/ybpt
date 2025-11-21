package com.jsdc.ybpt.service.agreementsignService;

import cn.hutool.http.HttpRequest;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsdc.ybpt.agreementsignModel.NetTagLog;
import com.jsdc.ybpt.service.PersonSubscribeRecordService;
import com.jsdc.ybpt.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: ybpt
 * @className: SignService
 * @author: wp
 * @description:
 * @date: 2022/9/14 17:30
 */
@Service
public class SignService {
    @Value("${sign.host}")
    private String host;

    @Value("${netTagNginxPath}")
    private String nginxPath;

    @Value("${signPdfPath}")
    private String signPdfPath;

    @Value("${netSignPdfPath}")
    private String netSignPdfPath;

    @Value("${signPdfPath}")
    private String fastDfs_downurl;

    private static final Logger log = LoggerFactory.getLogger(SignService.class);
    /**
     *
     * @param openId
     * @param accountType
     * @return
     */
    public JSONObject regedit(String openId, String accountType){
        String url = host + "/regedit.do";
        Map<String, String> params = new HashMap<>();
        params.put("openId", openId);
        params.put("accountType", accountType);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }

    /**
     * 获取企业认证页面地址
     * @param customerId
     * @return
     */
    public JSONObject authenticationCompany(String customerId){
        String url = host + "/authenticationCompany.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }

    /**
     * 获取个人认证页面地址
     * @param customerId
     * @return
     */
    public JSONObject authenticationPersonal(String customerId){
        String url = host + "/authenticationPersonal.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }

    /**
     * 证书申请
     * @param customerId
     * @param transactionNo
     * @return
     */
    public JSONObject applyCertificate(String customerId, String transactionNo){
        String url = host + "/applyCertificate.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("transactionNo", transactionNo);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }

    /**
     * 自定义印章
     * @param customerId
     * @param content
     * @return
     */
    public String customSeal(String customerId, String content){
        String url = host + "/customSeal.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("content", content);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return res;
    }


    /**
     * 上传印章
     * @param customerId
     * @param base64
     * @return
     */
    public JSONObject uploadSeal(String customerId, String base64){
        String url = host + "/uploadSeal.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("base64", base64);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
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
    public String manualSign(String customerId, String transactionId, String contractId, String docTitle, String signType){
        String url = host + "/manualSign.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("transactionId", transactionId);
        params.put("contractId", contractId);
        params.put("docTitle", docTitle);
        params.put("signType", signType);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return res;
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
    public JSONObject authSign(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy){
        String url = host + "/authSign.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("contractId", contractId);
        params.put("docTitle", docTitle);
        params.put("positionType", positionType);
        params.put("keyWord", keyWord);
        params.put("keywordStrategy", keywordStrategy);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }
    /**
     * 自动签章-资金清算
     * @param customerId
     * @param contractId
     * @param docTitle
     * @param positionType
     * @param keyWord
     * @param keywordStrategy
     * @param keyx 偏移量
     * @param keyy 偏移量
     * @return
     */
    public JSONObject autoSignZjqs(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy,String keyx,String keyy){
        String url = host + "/autoSignForZjqs.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("contractId", contractId);
        params.put("docTitle", docTitle);
        params.put("positionType", positionType);
        params.put("keyWord", keyWord);
        params.put("keywordStrategy", keywordStrategy);
        params.put("keyx", keyx);
        params.put("keyy", keyy);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }

    public JSONObject autoSignZjqsBySeal(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy,String keyx,String keyy, String signatureId){
        String url = host + "/autoSignForZjqsBySeal.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("contractId", contractId);
        params.put("docTitle", docTitle);
        params.put("positionType", positionType);
        params.put("keyWord", keyWord);
        params.put("keywordStrategy", keywordStrategy);
        params.put("keyx", keyx);
        params.put("keyy", keyy);
        params.put("signatureId", signatureId);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }
    /**
     * 医保局自动签章
     * 自主选择印章
     * @param customerId
     * @param contractId
     * @param docTitle
     * @param positionType
     * @param keyWord
     * @param keywordStrategy
     * @param signatureId
     * @return
     */
    public JSONObject adminAuthSign(String customerId, String contractId, String docTitle, String positionType, String keyWord, String keywordStrategy, String signatureId){
        String url = host + "/authSignBySeal.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("contractId", contractId);
        params.put("docTitle", docTitle);
        params.put("positionType", positionType);
        params.put("keyWord", keyWord);
        params.put("keywordStrategy", keywordStrategy);
        params.put("signatureId", signatureId);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }

    /**
     * 上传合同
     * @param filePath
     * @param title
     * @return
     */
    public String uploadContract(String filePath, String title){
        String url = host + "/uploadContract.do";
        //String tempPath = filePath.substring(9);
        String tempPath = netSignPdfPath + filePath+"?download=0";
        Map<String, String> params = new HashMap<>();
        params.put("filePath", tempPath);
        /*Map<String, String> params = new HashMap<>();
        params.put("filePath", filePath);*/
        params.put("title", title);
        log.info(tempPath);
        log.info(params.toString());
        log.info(url);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        log.info(res);
        return res;
    }
    /**
     * 上传合同-资金清算
     * @param filePath
     * @param title
     * @return
     */
    public String uploadContract_zjqs(String filePath, String title){
        log.info("==== filePath :{} " , filePath);
        log.info("==== title :{} " , title);

        String url = host + "/uploadContract.do";
        String tempPath = netSignPdfPath+filePath;
        Map<String, String> params = new HashMap<>();
        params.put("filePath", tempPath);
        /*Map<String, String> params = new HashMap<>();
        params.put("filePath", filePath);*/
        params.put("title", title);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return res;
    }
    /**
     * 查询自动签章授权状态
     * @param customerId
     * @return
     */
    public JSONObject queryAutoSignAuthority(String customerId){
        String url = host + "/queryAutoSignAuthority.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return JSONObject.parseObject(res);
    }

    /**
     * 授权自动签章网址
     * @param customerId
     * @param transactionId
     * @return
     */
    public String autoSignAuthorize(String customerId, String transactionId){
        String url = host + "/autoSignAuthorize.do";
        Map<String, String> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("transactionId", transactionId);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        return res;
    }

    /**
     * 查询企业实名认证信息
     * @param transactionNo
     * @return
     */
    public JSONObject companyCertInfo(String transactionNo){
        String url = host + "/companyCertInfo.do";
        Map<String, String> params = new HashMap<>();
        params.put("transactionNo", transactionNo);
        String res = HttpRequest.post(url).formStr(params).timeout(10000).execute().body();
        JSONObject jsonObject = JSON.parseObject(res);
        return  jsonObject;
    }

}
