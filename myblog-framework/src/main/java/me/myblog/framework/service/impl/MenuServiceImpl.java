package me.myblog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.mapper.MenuMapper;
import me.myblog.framework.domain.entity.Menu;
import me.myblog.framework.service.MenuService;
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
    public List<String> selectPermissionsByUserId(Long id) {
        // 如果是管理员,返回所有权限
        if (id == 1L) {
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
        return baseMapper.selectPermissionsByUserId(id);
    }
}

