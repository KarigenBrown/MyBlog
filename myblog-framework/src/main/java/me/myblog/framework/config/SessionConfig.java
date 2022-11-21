package me.myblog.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Karigen B
 * @create 2022-11-21 13:08
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig {
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return StringRedisSerializer.UTF_8;
    }
}
