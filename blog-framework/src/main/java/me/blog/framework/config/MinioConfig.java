package me.blog.framework.config;

import io.minio.MinioClient;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Karigen B
 * @create 2022-11-08 23:27
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private String endpoint;
    private String bucketName;
    private String accessKey;
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .credentials(accessKey, secretKey)
                .endpoint(endpoint)
                .build();
    }
}
