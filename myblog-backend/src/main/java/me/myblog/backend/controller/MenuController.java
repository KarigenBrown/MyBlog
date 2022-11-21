package me.myblog.backend.controller;

import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.Menu;
import me.myblog.framework.domain.vo.RoutersVo;
import me.myblog.framework.service.MenuService;
import me.myblog.framework.utils.SecurityUtils;
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

}

