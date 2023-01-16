package me.myblog.framework.utils;

import me.myblog.framework.domain.entity.Menu;
import me.myblog.framework.domain.vo.MenuTreeVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Karigen B
 * @create 2023-01-15 16:52
 */
public class SystemConverter {
    private SystemConverter() {
    }


    public static List<MenuTreeVo> buildMenuTree(List<Menu> menus) {
        List<MenuTreeVo> menuTreeVos = menus.stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null))
                .toList();

        return menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(0L))
                .map(menuTreeVo -> menuTreeVo.setChildren(SystemConverter.getChildList(menuTreeVos, menuTreeVo)))
                .toList();
    }


    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> all, MenuTreeVo option) {
        return all.stream()
                .filter(menuTreeVo -> Objects.equals(menuTreeVo.getParentId(), option.getId()))
                .map(menuTreeVo -> menuTreeVo.setChildren(SystemConverter.getChildList(all, menuTreeVo)))
                .toList();
    }
}
