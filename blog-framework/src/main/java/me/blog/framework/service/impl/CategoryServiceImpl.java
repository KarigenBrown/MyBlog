package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.mapper.CategoryMapper;
import me.blog.framework.entity.Category;
import me.blog.framework.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}

