package com.github.wechat.datasources.aspect;
import com.github.wechat.datasources.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.wechat.datasources.DataSourceNames;
import com.github.wechat.datasources.DynamicDataSource;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 */
@Aspect
@Order(-1)
@Component
public class DataSourceAspect{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.github.wechat.datasources.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);
        if(ds == null){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            //System.out.println("选中数据源："+DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }else {
            DynamicDataSource.setDataSource(ds.name());
            //System.out.println("选中数据源2："+ds.name());
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }
}
