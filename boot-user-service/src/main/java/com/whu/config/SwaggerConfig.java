package com.whu.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


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
                .title("Swagger Petstore")
                .description("This is a sample server Petstore server.  You can find out more about     Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).      For this sample, you can use the api key `special-key` to test the authorization     filters.")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("","", "apiteam@swagger.io"))
                .build();
    }

//    @Bean
//    public Docket customImplementation(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.whu.web.controller"))
//                .build()
////                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
////                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
//                .apiInfo(apiInfo());
//    }

}