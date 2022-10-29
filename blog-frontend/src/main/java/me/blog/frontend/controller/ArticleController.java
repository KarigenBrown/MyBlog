package me.blog.frontend.controller;

import me.blog.framework.entity.Article;
import me.blog.framework.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 文章表(Article)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:20
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Autowired
    private ArticleService articleService;

    @GetMapping("/test")
    public List<Article> test() {
        return articleService.list();
    }

}

