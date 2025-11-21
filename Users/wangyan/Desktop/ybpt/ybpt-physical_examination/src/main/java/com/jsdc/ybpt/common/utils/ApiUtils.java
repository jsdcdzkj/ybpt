package com.jsdc.ybpt.common.utils;

import cn.dev33.satoken.temp.SaTempUtil;
import com.jsdc.ybpt.vo.ApiLoginInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ApiUtils {

    /**
     * 获取请求机构对象
     * @return ApiLoginInfo
     */
    public static ApiLoginInfo getApiLoginInfo(){
        HttpServletRequest request =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("temp_token");
        ApiLoginInfo apiLoginInfo = SaTempUtil.parseToken(token, ApiLoginInfo.class);
        apiLoginInfo.setRequest_url(request.getRequestURI());
        return apiLoginInfo;
    }
}
