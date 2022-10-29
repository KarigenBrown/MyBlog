package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.constants.SystemConstants;
import me.blog.framework.domain.entity.Article;
import me.blog.framework.mapper.ArticleMapper;
import me.blog.framework.mapper.CategoryMapper;
import me.blog.framework.domain.entity.Category;
import me.blog.framework.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Category> categoryList() {
        // 查询文章表,状态为已发布的文章
        List<Article> articles = articleMapper.selectList(Wrappers.<Article>lambdaQuery()
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
        );

        // 获取文章的分类id,并且去重
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        // 查询分类表
        List<Category> categories = this.listByIds(categoryIds);

        return categories.stream()
                .filter(category ->
                        SystemConstants.CATEGORY_STATUS_NORMAL
                                .equals(category.getStatus()))
                .toList();
    }
}

