package me.myblog.backend.controller;

import me.myblog.framework.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文章表(Article)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:09:57
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    /**
     * 服务对象
     */
    @Autowired
    private ArticleService articleService;

}

