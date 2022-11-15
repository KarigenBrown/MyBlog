package me.myblog.frontend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Karigen B
 * @create 2022-10-29 18:41
 */
@MapperScan("me.myblog.framework.mapper")
@ComponentScan("me.myblog.framework")
@Configuration
@EnableSwagger2 // 使用starter自动开启
@EnableScheduling
public class FrontendConfig {
}
