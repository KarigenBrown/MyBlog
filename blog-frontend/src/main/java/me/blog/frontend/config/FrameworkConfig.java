package me.blog.frontend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Karigen B
 * @create 2022-10-29 18:41
 */
@MapperScan("me.blog.framework.mapper")
@ComponentScan("me.blog.framework")
@Configuration
public class FrameworkConfig {
}
