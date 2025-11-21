package com.jsdc.ybpt.common;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.jsdc.ybpt.model.SysBizLog;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Aspect
public class BizLogAspect {

    @Autowired
    private SysUserService sysUserService ;


    /**
     * 后置操作
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @AfterReturning(value = "@annotation(bizLog)",returning="resultInfo")
    public void after(JoinPoint point, BizLog bizLog, ResultInfo resultInfo) throws Throwable {
        try{
            SysBizLog sysBizLog = new SysBizLog() ;
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (servletRequestAttributes != null) {
                HttpServletRequest request = servletRequestAttributes.getRequest();

                sysBizLog.setRequestUrl(request.getRequestURI());
            }
            sysBizLog.setId(IdUtil.simpleUUID());
            sysBizLog.setOperatId(sysUserService.getUser().getUsername());
            sysBizLog.setOperatType(bizLog.operatType());
            sysBizLog.setMemo(bizLog.memo());
            sysBizLog.setModelName(bizLog.modelName());
            Object[] args = point.getArgs();
            if (ArrayUtils.isNotEmpty(args)) {
                List<Object> logArgs = Arrays.stream(args).filter(arg ->
                        (!(arg instanceof HttpServletRequest) &&
                                !(arg instanceof HttpServletResponse)&&
                                !(arg instanceof HttpSession)))
                        .collect(Collectors.toList());
                sysBizLog.setParams(JSONObject.toJSONString(logArgs));
            }


            sysBizLog.setResultParams(JSONObject.toJSONString(resultInfo));
            sysBizLog.setCreateTime(new Date());
            sysBizLog.insert();
        }catch (Exception e) {

        }

    }
}
