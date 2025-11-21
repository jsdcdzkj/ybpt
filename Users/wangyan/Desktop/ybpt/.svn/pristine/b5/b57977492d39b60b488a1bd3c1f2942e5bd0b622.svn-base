package com.jsdc.ybpt;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.jsdc.ybpt.formula.domain.entity.NotifyApply;
import com.jsdc.ybpt.model.BizReconciliation;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.BizReconciliationService;
import com.jsdc.ybpt.service.FileInfoService;
import com.jsdc.ybpt.service.SysMenuService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.DocUtil;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BizReconciliationService bizReconciliationService;
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    FileInfoService fileInfoService;

    @Autowired
    FastDfsUtil fastDfsUtil;


    @Test
    void contextLoads() {
//        Page<SysUser> page = new Page<>(1,2);
        Page<SysUser> sysUser = sysUserService.getList();
        sysUser.getRecords().forEach(x->{
            System.out.println(x);
        });
        System.out.println("***************************************");
//        Page<SysUser> userPage = sysUserService.page(page);
//        for (SysUser record : userPage.getRecords()) {
//            System.out.println(record);
//        }
    }

    public static void main(String[] args) {
//        createPDF();
        //E:\AAAAword_dc\temp


        Date generalAcceptLetterTime = DateUtil.parseDate("2025-5-27");
        long betweenDay = DateUtil.betweenDay(new Date(), generalAcceptLetterTime, true);



        System.out.println(betweenDay);

        //该制剂已申请成功，下次可申请时间：XX年-XX月-XX日
        DateTime nwTime = DateUtil.offsetDay(generalAcceptLetterTime, 180);
        int year = nwTime.getField(DateField.YEAR);
        int month = nwTime.getField(DateField.MONTH) ;
        int day = nwTime.getField(DateField.DAY_OF_MONTH);

        System.out.println("该制剂已申请成功，下次可申请时间："+year+"年-"+month+"月-"+day+"日");
    }

    @Test
    static void createPDF() {
//        SysUser sysUser = sysUserService.getUser();

        String s ="{\n" +
                "\t\"approvalNo\": \"pizhuwenhao\",\n" +
                "\t\"formulaName\": \"制剂名称1\",\n" +
                "\t\"isInCategory\": \"0\",\n" +
                "\t\"laborList\": [{\n" +
                "\t\t\"participantNum\": \"1\",\n" +
                "\t\t\"payPrice\": \"222\",\n" +
                "\t\t\"isSubtotal\": \"0\",\n" +
                "\t\t\"workHours\": \"2\",\n" +
                "\t\t\"hourWage\": \"111\",\n" +
                "\t\t\"id\": \"1791311312249552899\",\n" +
                "\t\t\"priceCalculateId\": \"1791311312249552898\",\n" +
                "\t\t\"participantName\": \"技术员\"\n" +
                "\t}],\n" +
                "\t\"minPriceUnit\": \"2g\",\n" +
                "\t\"fixedAssetsDepreList\": [{\n" +
                "\t\t\"payPrice\": \"44\",\n" +
                "\t\t\"isSubtotal\": \"0\",\n" +
                "\t\t\"originalValue\": \"11\",\n" +
                "\t\t\"useTime\": \"33\",\n" +
                "\t\t\"useLife\": \"22\",\n" +
                "\t\t\"id\": \"1791311312379576322\",\n" +
                "\t\t\"priceCalculateId\": \"1791311312249552898\",\n" +
                "\t\t\"deviceName\": \"shebeimingzi1\"\n" +
                "\t}],\n" +
                "\t\"catalogCode\": \"100101\",\n" +
                "\t\"fileInfoList\": [{\n" +
                "\t\t\"fileName\": \"标准管理-20240429155851.xlsx\",\n" +
                "\t\t\"bizType\": \"25\",\n" +
                "\t\t\"fileSize\": \"10527\",\n" +
                "\t\t\"bizId\": \"\",\n" +
                "\t\t\"fileUrl\": \"/file/标准管理-20240429155851xlsx/789aa2c6b19844f58ca68d4f7cc893a8.xlsx\",\n" +
                "\t\t\"fileMd5\": \"893e1b87241c7aaf41296a19097a81c7\",\n" +
                "\t\t\"id\": \"2a2d8ea0cc9b450897877a7c90cfe75e\",\n" +
                "\t\t\"uploadTime\": \"2024-05-16 16:11:38\",\n" +
                "\t\t\"contentType\": \"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\",\n" +
                "\t\t\"fileType\": \"2\"\n" +
                "\t}],\n" +
                "\t\"priceCalculate\": {\n" +
                "\t\t\"nonlocalPmPrice\": 11,\n" +
                "\t\t\"notifyApplyId\": \"1791311312190832641\",\n" +
                "\t\t\"projectTotalCost\": 22,\n" +
                "\t\t\"id\": \"1791311312249552898\"\n" +
                "\t},\n" +
                "\t\"productNameCode\": \"chanpinbianma1\",\n" +
                "\t\"remark\": \"备注1\",\n" +
                "\t\"specs\": \"1g\",\n" +
                "\t\"payType\": \"1\",\n" +
                "\t\"registerCompanyName\": \"zhucedanwei\",\n" +
                "\t\"price\": 1122,\n" +
                "\t\"materialConsumeList\": [{\n" +
                "\t\t\"unitPrice\": \"2\",\n" +
                "\t\t\"unit\": \"g\",\n" +
                "\t\t\"payPrice\": \"111\",\n" +
                "\t\t\"id\": \"1791311312312467458\",\n" +
                "\t\t\"priceCalculateId\": \"1791311312249552898\",\n" +
                "\t\t\"productNum\": \"1\",\n" +
                "\t\t\"productName\": \"pinming1\"\n" +
                "\t}],\n" +
                "\t\"id\": \"1791311312190832641\",\n" +
                "\t\"goodsName\": \"shangpinmingc1\",\n" +
                "\t\"localPmPrice\": 112233,\n" +
                "\t\"minPackage\": \"1.1g\",\n" +
                "\t\"nationalFormulaCode\": \"11111\",\n" +
                "\t\"dosageForm\": \"jixing\",\n" +
                "\t\"categoryCode\": \"fenleibianma1\",\n" +
                "\t\"genericNameCode\": \"tongyonbianma1\",\n" +
                "\t\"unit\": \"g\",\n" +
                "\t\"checkFeeList\": [{\n" +
                "\t\t\"unitPrice\": \"12\",\n" +
                "\t\t\"unit\": \"kg\",\n" +
                "\t\t\"payPrice\": \"33\",\n" +
                "\t\t\"isSubtotal\": \"0\",\n" +
                "\t\t\"id\": \"1791311312379576323\",\n" +
                "\t\t\"priceCalculateId\": \"1791311312249552898\",\n" +
                "\t\t\"productNum\": \"1\",\n" +
                "\t\t\"productName\": \"jiance1\"\n" +
                "\t}],\n" +
                "\t\"manageLossOtherFee\": {\n" +
                "\t\t\"subtotal\": 111,\n" +
                "\t\t\"id\": \"1791311312442490882\",\n" +
                "\t\t\"lossOtherFee\": 99,\n" +
                "\t\t\"priceCalculateId\": \"1791311312249552898\",\n" +
                "\t\t\"managFee\": 88\n" +
                "\t}\n" +
                "}";

        NotifyApply notifyApply = JSON.parseObject(s, NotifyApply.class);

        String id = notifyApply.getId();

        //获取模板文档
        File rootFile = null;
        try {
            rootFile = new File("E:\\AAAAword_dc\\temp");
//            rootFile = new File(ResourceUtils.getURL("classpath:").getPath());
            File templateFile = new File(rootFile, "/2.docx");
            String docxPath = rootFile + "/TEMP/" + IdUtil.simpleUUID() + ".docx";
            String pdfFileName = IdUtil.simpleUUID() + ".pdf";
            String pdfPath = rootFile + "/TEMP/" + pdfFileName;
            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(notifyApply), Map.class);
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("laborList", policy)
                    .bind("materialConsumeList", policy)
                    .bind("fixedAssetsDepreList", policy)
                    .bind("checkFeeList", policy)
                    .build();
            log.info("==== docxPath :{} " , docxPath);
            DocUtil.word2RedDocument(templateFile.getPath(), config, data, docxPath, pdfPath);
            Console.log(data);
            log.info("==== docxData ：{} " , JSON.toJSONString(data));

            /*
            //上传文件服务器
            File pdfFile = new File(pdfPath);
            MultipartFile multipartFile = null;
            FastDfsParams params = null;
            //删除文件
            List<FileInfo> fileInfos = fileInfoService.list(new QueryWrapper<FileInfo>()
                    .eq("bizType", "27").eq("bizId", id));
            for (FileInfo fileInfo : fileInfos) {
                fastDfsUtil.deleteFile(fileInfo);
            }
            multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(pdfFile));
            params = new FastDfsParams("notifyApply", multipartFile, "27", id);
            params.setFileName(pdfFileName);
            ResultInfo resultInfo = fastDfsUtil.uploadFile(params);
            FileInfo fileInfo = (FileInfo) resultInfo.getData();
            log.info("==== pdf ：{} " , fileInfo.getFileUrl());*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
