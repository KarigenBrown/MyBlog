package me.myblog.backend.controller;

import me.myblog.framework.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:15:10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

}

