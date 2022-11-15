package me.myblog.backend.controller;

import me.myblog.framework.service.RoleMenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色和菜单关联表(RoleMenu)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:14:49
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    /**
     * 服务对象
     */
    @Autowired
    private RoleMenuService roleMenuService;

}

