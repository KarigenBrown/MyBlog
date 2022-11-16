package me.myblog.backend.controller;

import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.User;
import me.myblog.framework.domain.vo.UserDetailsVo;
import me.myblog.framework.domain.vo.UserRightsVo;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import me.myblog.framework.service.MenuService;
import me.myblog.framework.service.RoleService;
import me.myblog.framework.service.UserService;
import me.myblog.framework.utils.BeanCopyUtils;
import me.myblog.framework.utils.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public Response<Map<String, String>> login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示,必须要传用户名
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        String token = userService.administratorLogin(user);
        return Response.ok(Map.of("token", token));
    }

    @GetMapping("/userRights")
    public Response<UserRightsVo> getUserRights() {
        // 获取当前登陆的用户
        User loginUser = SecurityUtils.getLoginUser();
        // 根据用户id查询权限信息
        List<String> permissions = menuService.selectPermissionsByUserId(loginUser.getId());
        // 根据用户id查询角色信息
        List<String> roleKeys = roleService.selectRoleKeyByUserId(loginUser.getId());
        // 获取用户信息
        UserDetailsVo userDetails = BeanCopyUtils.copyBean(loginUser, UserDetailsVo.class);
        // 封装数据返回
        UserRightsVo data = new UserRightsVo()
                .setPermissions(permissions)
                .setRoles(roleKeys)
                .setUser(userDetails);
        return Response.ok(data);
    }

}

