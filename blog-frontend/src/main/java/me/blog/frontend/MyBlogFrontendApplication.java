package me.blog.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Karigen B
 * @create 2022-10-29 18:39
 */
@SpringBootApplication
public class MyBlogFrontendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBlogFrontendApplication.class, args);
    }
}
