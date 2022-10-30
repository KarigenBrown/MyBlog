package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.constants.SystemConstants;
import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.Category;
import me.blog.framework.mapper.ArticleMapper;
import me.blog.framework.domain.entity.Article;
import me.blog.framework.mapper.CategoryMapper;
import me.blog.framework.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Article> hotArticleList() {
        // 查询热门文章,封装成List<Article>返回
        // 最多只插叙10条
        return this.page(
                new Page<>(1, 10),
                Wrappers.<Article>lambdaQuery()
                        // 必须是正式文章
                        .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                        // 按照浏览量进行降序排序
                        .orderByDesc(Article::getViewCount)
        ).getRecords();

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
                }).toList();
    }

    @Override
    public Article articleDetail(Long id) {
        // 根据id查询文章
        Article article = this.getById(id);
        // 根据分类id查询分类名
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category != null) {
            article.setCategoryName(category.getName());
        }
        return article;
    }
}

