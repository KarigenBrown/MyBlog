package me.myblog.backend.controller;

import me.myblog.framework.service.MenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单权限表(Menu)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:14:27
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

