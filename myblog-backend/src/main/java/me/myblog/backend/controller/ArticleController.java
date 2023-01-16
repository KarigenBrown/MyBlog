package me.myblog.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.dto.ArticleDto;
import me.myblog.framework.domain.dto.PostArticleDto;
import me.myblog.framework.domain.entity.Article;
import me.myblog.framework.domain.entity.ArticleTag;
import me.myblog.framework.domain.vo.ArticleVo;
import me.myblog.framework.domain.vo.PageVo;
import me.myblog.framework.service.ArticleService;
import me.myblog.framework.service.ArticleTagService;
import me.myblog.framework.service.FileService;
import me.myblog.framework.utils.BeanCopyUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    private FileService fileService;

    @Autowired
    private ArticleTagService articleTagService;

    @PostMapping
    public Response<Object> postArticle(@RequestBody PostArticleDto postArticleDto) {
        Article article = BeanCopyUtils.copyBean(postArticleDto, Article.class);
        articleService.postArticle(article, postArticleDto.getTagIds());
        return Response.ok();
    }

    @PostMapping("/image")
    public Response<String> postImage(@RequestPart("image") MultipartFile image) {
        String url = fileService.uploadPicture(image);
        return Response.ok(url);
    }

    @GetMapping("/list")
    public Response<PageVo> getArticleList(@RequestParam("pageNumber") Integer pageNumber,
                                           @RequestParam("pageSize") Integer pageSize,
                                           @RequestParam(value = "title", required = false) String title,
                                           @RequestParam(value = "summary", required = false) String summary) {
        // TODO 封装进service
        List<Article> articles = articleService.lambdaQuery()
                .like(StringUtils.hasText(title), Article::getTitle, title)
                .like(StringUtils.hasText(summary), Article::getSummary, summary)
                .page(new Page<>(pageNumber, pageSize))
                .getRecords();

        PageVo data = new PageVo()
                .setRows(articles)
                .setTotal(articles.size());

        return Response.ok(data);
    }

    @GetMapping("/{id}")
    public Response<ArticleVo> getArticleById(@PathVariable("id") Integer id) {
        Article article = articleService.getById(id);

        // TODO 封装进service
        List<Long> tags = articleTagService.lambdaQuery()
                .eq(ArticleTag::getArticleId, id)
                .list().stream()
                .map(ArticleTag::getTagId)
                .toList();

        ArticleVo data = BeanCopyUtils.copyBean(article, ArticleVo.class);
        data.setTags(tags);
        return Response.ok(data);
    }

    @PutMapping
    // TODO 封装进service
    @Transactional
    public Response<Object> putArticleById(@RequestBody ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        articleService.updateById(article);

        articleTagService.lambdaUpdate()
                .eq(ArticleTag::getArticleId, article.getId())
                .remove();

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tag -> new ArticleTag(article.getId(), tag))
                .toList();
        articleTagService.saveBatch(articleTags);

        return Response.ok();
    }

    @DeleteMapping("/{id}")
    public Response<Object> deleteArticleById(@PathVariable("id") Integer id) {
        articleTagService.removeById(id);
        return Response.ok();
    }

}

