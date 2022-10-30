package me.blog.frontend.controller;

import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.Article;
import me.blog.framework.domain.vo.HotArticleVo;
import me.blog.framework.service.ArticleService;
import me.blog.framework.utils.BeanCopyUtils;
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

    @GetMapping("/hotArticleList")
    public Response<List<HotArticleVo>> hotArticleList() {
        // 查询热门文章,封装成ResponseResult<List<HotArticleVo>>返回
        List<Article> articles = articleService.hotArticleList();
        // 封装vo
        List<HotArticleVo> data = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return Response.ok(data);
    }

}

