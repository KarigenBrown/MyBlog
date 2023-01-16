package me.myblog.frontend.controller;

import me.myblog.framework.annotation.SystemLog;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.User;
import me.myblog.framework.domain.vo.LoginUserVo;
import me.myblog.framework.domain.vo.UserDetailsVo;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import me.myblog.framework.service.FileService;
import me.myblog.framework.service.UserService;
import me.myblog.framework.utils.BeanCopyUtils;
import org.eclipse.collections.api.tuple.Pair;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    @Autowired
    private FileService fileService;

    @PostMapping("/login")
    public Response<LoginUserVo> login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示,必须要传用户名
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        Pair<String, User> pair = userService.userLogin(user);
        /*Object loginUser = map.get("loginUser");
        String jwt = (String) map.get("jwt");*/
        String jwt = pair.getOne();
        User loginUser = pair.getTwo();

        // 把User转化成UserInfoVo
        UserDetailsVo userDetailsVo = BeanCopyUtils.copyBean(loginUser, UserDetailsVo.class);

        // 把token和userInfo封装返回
        LoginUserVo data = new LoginUserVo(jwt, userDetailsVo);
        return Response.ok(data);
    }

    @PostMapping("/logout")
    public Response<Object> logout() {
        userService.logout();
        return Response.ok();
    }

    @GetMapping("/userDetails")
    public Response<UserDetailsVo> getUserDetails() {
        User user = userService.getUserDetails();
        // 封装成UserDetailsVo
        UserDetailsVo data = BeanCopyUtils.copyBean(user, UserDetailsVo.class);
        return Response.ok(data);
    }

    @PostMapping("/userPhoto")
    public Response<String> userPhoto(@RequestPart("userPhoto") MultipartFile userPhoto) {
        String data = fileService.uploadPicture(userPhoto);
        return Response.ok(data);
    }

    @SystemLog(businessName = "更新用户信息")
    @PutMapping("/userDetails")
    public Response<Object> putUserDetails(@RequestBody User user) {
        userService.putUserDetails(user);
        return Response.ok(null);
    }

    @PostMapping("/register")
    public Response<Object> register(@RequestBody User user) {
        userService.register(user);
        return Response.ok(null);
    }
}

