package me.blog.frontend.controller;

import me.blog.framework.service.ArticleTagService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文章标签关联表(ArticleTag)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/articleTag")
public class ArticleTagController {
    /**
     * 服务对象
     */
    @Autowired
    private ArticleTagService articleTagService;

}

