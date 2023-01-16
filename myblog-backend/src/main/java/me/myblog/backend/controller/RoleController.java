package me.myblog.backend.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.Menu;
import me.myblog.framework.domain.entity.Role;
import me.myblog.framework.domain.entity.RoleMenu;
import me.myblog.framework.domain.vo.MenuTreeVo;
import me.myblog.framework.domain.vo.PageVo;
import me.myblog.framework.domain.vo.RoleMenuTreeVo;
import me.myblog.framework.service.MenuService;
import me.myblog.framework.service.RoleMenuService;
import me.myblog.framework.service.RoleService;
import me.myblog.framework.utils.SystemConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/page")
    public Response<PageVo> pagingRoles(@RequestParam(value = "roleName", required = false) String roleName,
                                        @RequestParam(value = "status", required = false) String status,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("pageNumber") Integer pageNumber) {
        // TODO 封装进service
        List<Role> roles = roleService.lambdaQuery()
                .like(StringUtils.hasText(roleName), Role::getRoleName, roleName)
                .eq(StringUtils.hasText(status), Role::getStatus, status)
                .orderByAsc(Role::getRoleSort)
                .page(new Page<>(pageNumber, pageSize))
                .getRecords();

        PageVo data = new PageVo(roles, roles.size());

        return Response.ok(data);
    }

    @PutMapping("/{id}/status/{status}")
    public Response<Object> changeRoleStatus(@PathVariable("id") Long id,
                                             @PathVariable("status") String status) {
        // TODO 封装进service
        Role role = new Role()
                .setId(id)
                .setStatus(status);
        roleService.updateById(role);
        return Response.ok();
    }

    @PostMapping
    // TODO 封装进service
    @Transactional
    public Response<Object> postRole(@RequestBody Role role) {
        roleService.save(role);
        if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
            List<RoleMenu> roleMenus = role.getMenuIds().stream()
                    .map(menuId -> new RoleMenu(role.getId(), menuId))
                    .toList();
            roleMenuService.saveBatch(roleMenus);
        }
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response<Role> getRoleById(@PathVariable("id") Long id) {
        Role data = roleService.getById(id);
        return Response.ok(data);
    }

    @GetMapping("/{id}/roleMenuTree")
    public Response<RoleMenuTreeVo> getRoleMenuTreeById(@PathVariable("id") Long id) {
        // TODO 封装进service
        List<Menu> menus = menuService.lambdaQuery()
                .orderByAsc(Menu::getParentId, Menu::getOrderNumber)
                .list();
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(id);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuTree(menus);
        RoleMenuTreeVo data = new RoleMenuTreeVo(checkedKeys, menuTreeVos);
        return Response.ok(data);
    }

    @PutMapping
    // TODO 封装进service
    @Transactional
    public Response<Object> putRole(@RequestBody Role role) {
        roleService.updateById(role);
        roleMenuService.lambdaUpdate()
                .eq(RoleMenu::getRoleId, role.getId())
                .remove();
        List<RoleMenu> roleMenus = role.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .toList();
        roleMenuService.saveBatch(roleMenus);
        return Response.ok();
    }

    @DeleteMapping("/{id}")
    public Response<Object> deleteRoleById(@PathVariable("id") Long id) {
        roleService.removeById(id);
        return Response.ok();
    }

    @GetMapping("/all")
    public Response<List<Role>> getAllRole() {
        List<Role> data = roleService.lambdaQuery()
                .eq(Role::getStatus, SystemConstants.NORMAL_ROLE_STATUS)
                .list();
        return Response.ok(data);
    }

}

