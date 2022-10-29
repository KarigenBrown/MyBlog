package me.blog.frontend.controller;

import me.blog.framework.service.UserRoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户和角色关联表(UserRole)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {
    /**
     * 服务对象
     */
    @Autowired
    private UserRoleService userRoleService;

}

