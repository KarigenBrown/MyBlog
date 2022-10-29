package me.blog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.blog.framework.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

