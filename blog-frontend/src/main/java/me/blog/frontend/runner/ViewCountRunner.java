package me.blog.frontend.runner;

import me.blog.framework.constants.SystemConstants;
import me.blog.framework.domain.entity.Article;
import me.blog.framework.service.ArticleService;
import me.blog.framework.utils.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Karigen B
 * @create 2022-11-14 14:42
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息,id和viewCount
        List<Article> articles = articleService.list();
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()
                ));
        // 存储到redis中
        redisCacheUtils.setCacheMap(SystemConstants.VIEW_COUNT_KEY, viewCountMap);
    }
}
