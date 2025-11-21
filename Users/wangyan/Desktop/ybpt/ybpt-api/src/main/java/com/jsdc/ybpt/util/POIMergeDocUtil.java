package com.jsdc.ybpt.util;

import cn.hutool.core.util.IdUtil;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * word合成工具类
 *
 * @author 7788
 * @version 1.0
 * @date 2021/1/5 下午 4:46
 * @location wuhan
 */
@Slf4j
public class POIMergeDocUtil {

    private static final String licensePath = "word/license.xml";

    private static final String headPath = "word/head.docx";

    /**
     * @param content  公文地址
     * @param data     需要填写的参数
     * @param destDocx 保存的文件地址
     */
    public static void word2RedDocument(String content, Map<String, String> data, String destDocx, String pdfPath) throws Exception {
        //模板文件地址
        String model = "";
        //模板存放项目里根路径
        URL resource = POIMergeDocUtil.class.getClassLoader().getResource(headPath);

        if (null != resource){
            model =  resource.getPath();
        }

        //模板文件 参数填写
        ConfigureBuilder builder = Configure.builder();
        //设置语法
        builder.buildGramer("${", "}");
        //设置模板文件地址
        XWPFTemplate template = XWPFTemplate.compile(content, builder.build());
        //参数处理
        data.put("name", StringUtils.isBlank(data.get("party_b_name")) ? "______" : data.get("party_b_name"));
        data.put("address", StringUtils.isBlank(data.get("party_b_address")) ? "______" : data.get("party_b_address"));
        data.put("code", StringUtils.isBlank(data.get("party_b_code")) ? "______" : data.get("party_b_code"));
        data.put("year", StringUtils.isBlank(data.get("year")) ? "______" : data.get("year"));
        data.put("mouth", StringUtils.isBlank(data.get("mouth")) ? "_____" : data.get("mouth"));
        data.put("day", StringUtils.isBlank(data.get("day")) ? "_____" : data.get("day"));


        //填充参数
        template.render(data);
//        //获取模板文件  公文
        NiceXWPFDocument main = template.getXWPFDocument();
//        NiceXWPFDocument sub = new NiceXWPFDocument(new FileInputStream(content));
//        // 合并两个文档
//        NiceXWPFDocument newDoc = main.merge(sub);

        // 生成新文档

        FileOutputStream out = new FileOutputStream(destDocx);
        main.write(out);
        main.close();

        wordToPdf(destDocx, pdfPath);
        out.close();
    }

    public static void wordToPdf(String docPath, String toPath) throws Exception {
        FileOutputStream os = null;
        try {
            //凭证 不然切换后有水印
            InputStream is = new ClassPathResource(licensePath).getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(is);
            boolean license = true;
            if (!license) {
                System.out.println("License验证不通过...");
            }
            os = new FileOutputStream(new File(toPath));
            //要转换的word文件
            Document doc = new Document(docPath);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void word2RedDocument2(String content, Map<String, String> data, String destDocx) throws Exception {
        //模板文件地址
        String model = "";
        //模板存放项目里根路径
        URL resource = POIMergeDocUtil.class.getClassLoader().getResource(headPath);

        if (null != resource){
            model =  resource.getPath();
        }

        //模板文件 参数填写
        ConfigureBuilder builder = Configure.builder();
        //设置语法
        builder.buildGramer("${", "}");
        XWPFTemplate template = XWPFTemplate.compile(model, builder.build());
        //填充参数
        template.render(data);
        //获取模板文件  公文
        NiceXWPFDocument main = template.getXWPFDocument();
        NiceXWPFDocument sub = new NiceXWPFDocument(new FileInputStream(content));
        // 合并两个文档
        NiceXWPFDocument newDoc = main.merge(sub);
        // ------------------处理第三个------------------
        // 获取第三个文档
        URL resource3 = POIMergeDocUtil.class.getClassLoader().getResource(headPath);
        String model3 = "";
        if (null != resource){
            model3 =  resource3.getPath();
        }
        //模板文件 参数填写
        XWPFTemplate template3 = XWPFTemplate.compile(model3).render(data);
        //获取模板文件  公文
        NiceXWPFDocument main3 = template3.getXWPFDocument();

        newDoc = newDoc.merge(newDoc);

        // 生成新文档

        FileOutputStream out = new FileOutputStream(destDocx);
        newDoc.write(out);
        newDoc.close();

        wordToPdf(destDocx, destDocx.replace(".docx", ".pdf"));
        out.close();
    }

}

