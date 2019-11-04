package com.whu.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enable:true}")
public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi() {
//        ParameterBuilder sessionIdPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        sessionIdPar.name("SESSIONID").description("用户 sessionid")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(true).build();
//        pars.add(sessionIdPar.build());    //根据每个方法名也知道当前方法在设置什么参数
//        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(pars)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.whu.web.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.whu.web.controller")) //这里写的是API接口所在的包位置

                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restfun风格")
                .version("1.0")
                .build();
    }

}