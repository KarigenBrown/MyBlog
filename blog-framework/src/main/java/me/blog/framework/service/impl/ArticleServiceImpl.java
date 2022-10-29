package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.constants.SystemConstants;
import me.blog.framework.mapper.ArticleMapper;
import me.blog.framework.domain.entity.Article;
import me.blog.framework.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:29
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public List<Article> hotArticleList() {
        // 查询热门文章,封装成List<Article>返回
        // 最多只插叙10条
        return this.page(new Page<>(1, 10),
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
}

