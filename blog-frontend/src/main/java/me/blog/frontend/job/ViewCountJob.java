package me.blog.frontend.job;

import me.blog.framework.constants.SystemConstants;
import me.blog.framework.domain.entity.Article;
import me.blog.framework.service.ArticleService;
import me.blog.framework.utils.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Karigen B
 * @create 2022-11-14 14:51
 */
@Component
public class ViewCountJob {

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateViewCount() {
        // 获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCacheUtils.getCacheMap(SystemConstants.VIEW_COUNT_KEY);
        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article()
                        .setId(Long.valueOf(entry.getKey()))
                        .setViewCount(entry.getValue().longValue()))
                .toList();
        // 更新到数据库中
        articleService.updateBatchById(articles);
    }
}
