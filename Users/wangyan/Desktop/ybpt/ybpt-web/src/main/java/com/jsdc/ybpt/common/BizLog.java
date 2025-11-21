package com.jsdc.ybpt.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizLog {
    String operatType();

    String modelName() ;
    String memo() ;



}
