package me.myblog.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Karigen B
 * @create 2022-11-15 14:13
 */
@Configuration
public class SwaggerConfig {
    /*@Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("me.myblog.frontend.controller"))
                .build();
    }*/

    private ApiInfo apiInfo() {
        Contact contact = new Contact("karigen", "", "");
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .contact(contact) // 联系方式
                .version("1.0.0") // 版本
                .build();
    }
}
