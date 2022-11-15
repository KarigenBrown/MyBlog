package me.myblog.backend.controller;

import me.myblog.framework.service.RoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 角色信息表(Role)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:14:39
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    /**
     * 服务对象
     */
    @Autowired
    private RoleService roleService;

}

