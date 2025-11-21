package com.jsdc.ybpt;

import com.jsdc.ybpt.util.DESUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

//@SpringBootTest
//@SpringBootConfiguration
public class YbptApiApplicationTests {

    @Test
    public void testDESUtilDecrypt() throws UnsupportedEncodingException {
        String decode = URLDecoder.decode("JSivhleeknMmdZdLiFDDCsT%2BMv%2BadAVR");
        String svagvLGJ4p6uDou8 = DESUtil.decryptDES(URLDecoder.decode("JSivhleeknMmdZdLiFDDCsT%2BMv%2BadAVR", String.valueOf(StandardCharsets.UTF_8)), "svagvLGJ4p6uDou8");
        System.out.println(svagvLGJ4p6uDou8);
    }

    @Test
    public void testDESUtilEncrypt() {
        String ss = DESUtil.encryptDES("320304199312134815", "svagvLGJ4p6uDou8");
        System.out.println(ss);
    }

    @Test
    public void testSubString() {
        String a = "E:/upload/uploadtjreportPath/2022/TJ00015/32032319811225688X/32032319811225688X_202209271415406312041431543/6f5522041bc64c76975c2ed7842f5547.pdf";
        String substring = a.substring(9);
        System.out.println(substring);
    }
}
