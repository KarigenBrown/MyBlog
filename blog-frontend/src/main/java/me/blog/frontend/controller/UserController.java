package me.blog.frontend.controller;

import me.blog.framework.config.MinioConfig;
import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.User;
import me.blog.framework.domain.vo.LoginUserVo;
import me.blog.framework.domain.vo.UserDetailsVo;
import me.blog.framework.enums.HttpCodeEnum;
import me.blog.framework.exception.SystemException;
import me.blog.framework.service.UserService;
import me.blog.framework.utils.BeanCopyUtils;
import me.blog.framework.utils.MinioUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/logout")
    public Response<Object> logout() {
        userService.logout();
        return Response.ok(null);
    }

    @GetMapping("/userDetails")
    public Response<UserDetailsVo> userDetails() {
        User user = userService.userDetails();
        // 封装成UserDetailsVo
        UserDetailsVo data = BeanCopyUtils.copyBean(user, UserDetailsVo.class);
        return Response.ok(data);
    }

    @PostMapping("/userPhoto")
    public Response<String> userPhoto(@RequestPart("userPhoto") MultipartFile userPhoto) {
        String data = userService.userPhoto(userPhoto);
        return Response.ok(data);
    }
}

