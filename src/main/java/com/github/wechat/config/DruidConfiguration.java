package com.github.wechat.config;


import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DruidConfiguration {
    @Bean
    public ServletRegistrationBean startViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny","127.0.0.1");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername","api");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }
}
