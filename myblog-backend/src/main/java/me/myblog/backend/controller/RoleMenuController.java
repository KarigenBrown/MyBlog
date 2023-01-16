package me.myblog.backend.controller;

import cn.hutool.core.lang.Tuple;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.User;
import me.myblog.framework.domain.vo.UserDetailsVo;
import me.myblog.framework.domain.vo.UserRightsVo;
import me.myblog.framework.service.MenuService;
import me.myblog.framework.service.RoleMenuService;
import me.myblog.framework.service.RoleService;
import me.myblog.framework.utils.BeanCopyUtils;
import me.myblog.framework.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/userRights")
    public Response<UserRightsVo> getUserRights() {
        // 获取当前登陆的用户
        User loginUser = SecurityUtils.getLoginUser();
        // TODO 封装进service
        // Tuple
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

