package me.blog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.blog.framework.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}

