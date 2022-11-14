package me.blog.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.Article;

import java.util.List;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 18:26:29
 */
public interface ArticleService extends IService<Article> {

    List<Article> hotArticleList();

    List<Article> articleList(Long categoryId, Integer pageNumber, Integer pageSize);

    Article articleDetail(Long id);

    void putViewCount(Long id);
}

