/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.wechat.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 *
 */
@Configuration
@EnableSwagger2
//@Profile("dev")//正式上线后取消注释
public class SwaggerConfig{
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)        		
        		.apiInfo(apiInfo()).select()
        		//.paths(PathSelectors.any())
        		.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))// 请求处理选择器：确定加入API文档的请求动作
        		.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("公众号后台服务")
            .description("API文档")
            .termsOfServiceUrl("http://www.github.com")
            .version("1.0.0")
            .build();
    }

}