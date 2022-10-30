package me.blog.frontend.controller;

import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.Category;
import me.blog.framework.domain.vo.CategoryVo;
import me.blog.framework.service.CategoryService;
import me.blog.framework.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryList")
    public Response<List<CategoryVo>> categoryList() {
        List<Category> categories = categoryService.categoryList();
        List<CategoryVo> data = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return Response.ok(data);
    }

}

