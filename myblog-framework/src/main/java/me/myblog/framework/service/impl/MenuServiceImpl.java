package me.myblog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.mapper.MenuMapper;
import me.myblog.framework.domain.entity.Menu;
import me.myblog.framework.service.MenuService;
import me.myblog.framework.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermissionsByUserId(Long userId) {
        // 如果是管理员,返回所有权限
        if (SecurityUtils.isAdministrator()) {
            List<Menu> menus = this.list(
                    Wrappers.<Menu>lambdaQuery()
                            .in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON)
                            .eq(Menu::getStatus, SystemConstants.MENU_STATUS_NORMAL)
            );

            return menus.stream()
                    .map(Menu::getPermission)
                    .toList();
        }
        // 否则返回所具有的权限
        return baseMapper.selectPermissionsByUserId(userId);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus = null;
        // 判断是否是管理员
        if (SecurityUtils.isAdministrator()) {
            // 如果是,获取所有符合要求的Menu
            menus = baseMapper.selectAllRouterMenu();
        } else {
            // 否则,获取当前用户所具有的Menu
            menus = baseMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建menu tree
        // 现找出第一层的菜单,然后找出他们的子菜单设置到children属性中
        return buildMenuTree(menus, SystemConstants.MENU_TREE_ROOT);
    }

    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> parentId.equals(menu.getParentId()))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .toList();
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        return menus.stream()
                .filter(m -> menu.getId().equals(m.getParentId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .toList();
    }
}

