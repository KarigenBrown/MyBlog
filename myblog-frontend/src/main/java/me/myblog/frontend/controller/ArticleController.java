package me.myblog.frontend.controller;

import me.myblog.framework.annotation.SystemLog;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.Article;
import me.myblog.framework.domain.vo.ArticleDetailVo;
import me.myblog.framework.domain.vo.ArticleListVo;
import me.myblog.framework.domain.vo.HotArticleVo;
import me.myblog.framework.domain.vo.PageVo;
import me.myblog.framework.service.ArticleService;
import me.myblog.framework.service.CategoryService;
import me.myblog.framework.utils.BeanCopyUtils;
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

    @Autowired
    private CategoryService categoryService;

    @SystemLog(businessName = "获取热点文章")// 000000
    @GetMapping("/hotArticleList")
    public Response<List<HotArticleVo>> hotArticleList() {
        // 查询热门文章,封装成ResponseResult<List<HotArticleVo>>返回
        List<Article> hotArticles = articleService.hotArticleList();
        // 封装vo
        List<HotArticleVo> data = BeanCopyUtils.copyBeanList(hotArticles, HotArticleVo.class);
        return Response.ok(data);
    }

    @GetMapping("/articleList")
    // @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    public Response<PageVo> articleList(@RequestParam(value = "categoryId", required = false) Long categoryId,
                                        @RequestParam("pageNumber") Integer pageNumber,
                                        @RequestParam("pageSize") Integer pageSize) {
        List<Article> articles = articleService.articleList(categoryId, pageNumber, pageSize);
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return Response.ok(new PageVo(articleListVos, articleListVos.size()));
    }

    @GetMapping("/{id}")
    public Response<ArticleDetailVo> articleDetail(@PathVariable("id") Long id) {
        Article articleDetail = articleService.articleDetail(id);
        // 转换成vo
        ArticleDetailVo data = BeanCopyUtils.copyBean(articleDetail, ArticleDetailVo.class);
        return Response.ok(data);
    }

    @PutMapping("/viewCount/{id}")
    public Response<Object> putViewCount(@PathVariable("id") Long id) {
        articleService.putViewCount(id);
        return Response.ok(null);
    }

}

