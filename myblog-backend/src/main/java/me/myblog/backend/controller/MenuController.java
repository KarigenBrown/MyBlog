package me.myblog.backend.controller;

import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.Menu;
import me.myblog.framework.domain.vo.MenuTreeVo;
import me.myblog.framework.domain.vo.MenuVo;
import me.myblog.framework.domain.vo.RoutersVo;
import me.myblog.framework.service.MenuService;
import me.myblog.framework.utils.BeanCopyUtils;
import me.myblog.framework.utils.SecurityUtils;
import me.myblog.framework.utils.SystemConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 菜单权限表(Menu)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:14:27
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;

    @GetMapping("/routers")
    public Response<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        // 查询menu,结果是树形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        // 封装数据返回
        RoutersVo data = new RoutersVo(menus);
        return Response.ok(data);
    }

    @GetMapping("/list")
    public Response<List<MenuVo>> getMenuList(@RequestParam String menuName,
                                              @RequestParam String status) {
        // TODO 封装进service
        List<Menu> menus = menuService.lambdaQuery()
                .eq(StringUtils.hasText(menuName), Menu::getMenuName, menuName)
                .like(StringUtils.hasText(status), Menu::getStatus, status)
                .orderByAsc(Menu::getParentId, Menu::getOrderNumber)
                .list();

        List<MenuVo> data = BeanCopyUtils.copyBeanList(menus, MenuVo.class);

        return Response.ok(data);
    }

    @PostMapping
    public Response<Object> postMenu(@RequestBody Menu menu) {
        menuService.save(menu);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response<Menu> getMenuById(@PathVariable("id") Long id) {
        Menu data = menuService.getById(id);
        return Response.ok(data);
    }

    @PutMapping
    public Response<Object> putMenu(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return Response.error(500, "修改菜单'" + menu.getMenuName() + "'失败,上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return Response.ok();
    }

    @DeleteMapping("/{id}")
    public Response<Object> deleteMenuById(@PathVariable("id") Long id) {
        // TODO 封装进service
        boolean hasChild = menuService.lambdaQuery()
                .eq(Menu::getId, id)
                .count() != 0;

        if (hasChild) {
            return Response.error(500, "存在子菜单不允许删除");
        }

        menuService.removeById(id);
        return Response.ok();
    }

    @GetMapping("/tree")
    public Response<List<MenuTreeVo>> getMenuTree() {
        // TODO 封装进service
        List<Menu> menus = menuService.lambdaQuery()
                .orderByAsc(Menu::getParentId, Menu::getOrderNumber)
                .list();
        List<MenuTreeVo> options = SystemConverter.buildMenuTree(menus);
        return Response.ok(options);
    }

}

