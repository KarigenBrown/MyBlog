package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.mapper.ArticleMapper;
import me.blog.framework.entity.Article;
import me.blog.framework.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:29
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}

