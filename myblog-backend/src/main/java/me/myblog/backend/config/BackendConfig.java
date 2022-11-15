package me.myblog.backend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Karigen B
 * @create 2022-11-15 15:00
 */
@MapperScan("me.myblog.framework.mapper")
@ComponentScan("me.myblog.framework")
@Configuration
public class BackendConfig {
}
