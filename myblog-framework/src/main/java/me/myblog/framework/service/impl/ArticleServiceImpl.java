package me.myblog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.myblog.framework.config.MinioConfig;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.domain.entity.ArticleTag;
import me.myblog.framework.domain.entity.Category;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import me.myblog.framework.mapper.ArticleMapper;
import me.myblog.framework.domain.entity.Article;
import me.myblog.framework.mapper.CategoryMapper;
import me.myblog.framework.service.ArticleService;
import me.myblog.framework.service.ArticleTagService;
import me.myblog.framework.service.FileService;
import me.myblog.framework.utils.MinioUtils;
import me.myblog.framework.utils.PathUtils;
import me.myblog.framework.utils.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:29
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private FileService fileService;

    @Override
    public List<Article> hotArticleList() {
        // 查询热门文章,封装成List<Article>返回
        // 最多只插叙10条
        List<Article> articles = this.page(
                new Page<>(1, 10),
                Wrappers.<Article>lambdaQuery()
                        // 必须是正式文章
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                        // 按照浏览量进行降序排序
                        .orderByDesc(Article::getViewCount)
        ).getRecords();

        return articles.stream()
                .peek(article -> {
                    // 从redis中获取viewCount
                    Long viewCount = redisCacheUtils.getCacheMapValue(SystemConstants.VIEW_COUNT_KEY, article.getId().toString());
                    article.setViewCount(viewCount);
                }).sorted((article1, article2) -> (int) (article1.getViewCount() - article2.getViewCount()))
                .toList();

        // bean拷贝,实际用的时候采用在数据库select
        /*ArrayList<HotArticleVo> hotArticleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtils.copyProperties(article, hotArticleVo);
            hotArticleVos.add(hotArticleVo);
            return hotArticleVos;
        }*/
        // return BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
    }

    @Override
    public List<Article> articleList(Long categoryId, Integer pageNumber, Integer pageSize) {
        // 分页查询
        List<Article> articles = this.page(
                new Page<>(pageNumber, pageSize),
                // 查询条件
                Wrappers.<Article>lambdaQuery()
                        // 状态是正式发布的
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                        // 对isTop进行降序
                        .orderByDesc(Article::getIsTop)
                        // 如果有categoryId,要求查询时和传入的相同
                        .eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
        ).getRecords();

        // 查询categoryName
        /*for (Article article : articles) {
            // categoryId去查询categoryName
            Category category = categoryMapper.selectById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }*/

        return articles.stream()
                .peek(article -> {
                    // 获取分类id,查询分类信息,获取分类名称
                    Category category = categoryMapper.selectById(article.getCategoryId());
                    // 把分类名称设置给article
                    article.setCategoryName(category.getName());
                    // 从redis中获取viewCount
                    Long viewCount = redisCacheUtils.getCacheMapValue(SystemConstants.VIEW_COUNT_KEY, article.getId().toString());
                    article.setViewCount(viewCount);
                }).toList();
    }

    @Override
    public Article articleDetail(Long id) {
        // 根据id查询文章
        Article article = this.getById(id);
        // 从redis中获取viewCount
        Long viewCount = redisCacheUtils.getCacheMapValue(SystemConstants.VIEW_COUNT_KEY, id.toString());
        article.setViewCount(viewCount);
        // 根据分类id查询分类名
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category != null) {
            article.setCategoryName(category.getName());
        }
        return article;
    }

    @Override
    public void putViewCount(Long id) {
        // 更新redis中对应id的浏览量
        redisCacheUtils.incrementCacheMapValue(SystemConstants.VIEW_COUNT_KEY, id.toString(), 1);
    }

    @Override
    @Transactional
    public void postArticle(Article article, List<Long> tagIds) {
        this.save(article);

        List<ArticleTag> articleTags = tagIds.stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .toList();
        articleTagService.saveBatch(articleTags);
    }
}

