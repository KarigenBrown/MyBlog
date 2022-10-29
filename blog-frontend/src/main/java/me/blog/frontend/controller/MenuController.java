package me.blog.frontend.controller;

import me.blog.framework.service.MenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单权限表(Menu)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;

}

