package me.blog.frontend.controller;

import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.User;
import me.blog.framework.domain.vo.LoginUserVo;
import me.blog.framework.enums.HttpCodeEnum;
import me.blog.framework.exception.SystemException;
import me.blog.framework.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response<LoginUserVo> login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示,必须要传用户名
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        LoginUserVo data = userService.login(user);
        return Response.ok(data);
    }
}

