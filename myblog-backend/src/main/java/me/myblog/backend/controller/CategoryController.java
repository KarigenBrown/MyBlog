package me.myblog.backend.controller;

import me.myblog.framework.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:13:06
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

}

