package me.myblog.backend.controller;

import me.myblog.framework.service.UserRoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户和角色关联表(UserRole)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:15:21
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

