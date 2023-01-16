package me.myblog.backend.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.Role;
import me.myblog.framework.domain.entity.User;
import me.myblog.framework.domain.entity.UserRole;
import me.myblog.framework.domain.vo.UserRoleIdsVo;
import me.myblog.framework.domain.vo.UserVo;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import me.myblog.framework.service.RoleService;
import me.myblog.framework.service.UserRoleService;
import me.myblog.framework.service.UserService;
import me.myblog.framework.utils.BeanCopyUtils;
import me.myblog.framework.utils.RedisCacheUtils;
import me.myblog.framework.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
    private RedisCacheUtils redisCacheUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

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

    @PostMapping("/logout")
    public Response<Object> logout() {
        userService.administratorLogout();
        return Response.ok();
    }

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Response<List<UserVo>> page(@PathVariable("pageNumber") Integer pageNumber,
                                       @PathVariable("pageSize") Integer pageSize,
                                       @RequestParam(value = "userName", required = false) String userName,
                                       @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                       @RequestParam(value = "status", required = false) String status) {
        // TODO 封装进service
        List<User> users = userService.lambdaQuery()
                .like(StringUtils.hasText(userName), User::getUserName, userName)
                .eq(StringUtils.hasText(phoneNumber), User::getPhoneNumber, pageNumber)
                .eq(StringUtils.hasText(status), User::getStatus, status)
                .page(new Page<>(pageNumber, pageSize))
                .getRecords();
        List<UserVo> data = BeanCopyUtils.copyBeanList(users, UserVo.class);
        return Response.ok(data);
    }

    @PostMapping
    // TODO 封装进service
    @Transactional
    public Response<Object> postUser(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(HttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!(userService.lambdaQuery()
                .eq(User::getUserName, user.getUserName())
                .count() == 0)) {
            throw new SystemException(HttpCodeEnum.USERNAME_EXIST);
        }
        if (!(userService.lambdaQuery()
                .eq(User::getPhoneNumber, user.getPhoneNumber())
                .count() == 0)) {
            throw new SystemException(HttpCodeEnum.PHONE_NUMBER_EXIST);
        }
        if (!(userService.lambdaQuery()
                .eq(User::getEmail, user.getEmail())
                .count() == 0)) {
            throw new SystemException(HttpCodeEnum.EMAIL_EXIST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            List<UserRole> userRoles = user.getRoleIds().stream()
                    .map(roleId -> new UserRole(user.getId(), roleId))
                    .toList();
            userRoleService.saveBatch(userRoles);
        }

        return Response.ok();
    }

    @DeleteMapping("/{ids}")
    public Response<Object> deleteUsersByIds(@PathVariable("ids") List<Long> ids) {
        if (ids.contains(SecurityUtils.getUserId())) {
            return Response.error(500, "不能删除当前你正在使用的用户");
        }
        userService.removeByIds(ids);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response<UserRoleIdsVo> getUserById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        // TODO 封装进service
        List<Role> allRoles = roleService.lambdaQuery()
                .eq(Role::getStatus, SystemConstants.NORMAL_ROLE_STATUS)
                .list();
        List<Long> roleIds = roleService.selectRoleIdsByUserId(id);
        UserRoleIdsVo data = new UserRoleIdsVo(user, allRoles, roleIds);
        return Response.ok(data);
    }

    @PutMapping
    // TODO 封装进service
    @Transactional
    public Response<Object> putUser(@RequestBody User user) {
        userRoleService.lambdaUpdate()
                .eq(UserRole::getUserId, user.getId())
                .remove();
        List<UserRole> userRoles = user.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .toList();
        userRoleService.saveBatch(userRoles);
        userService.updateById(user);
        return Response.ok();
    }

}

