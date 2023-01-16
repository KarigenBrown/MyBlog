package me.myblog.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.myblog.framework.domain.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermissionsByUserId(Long userId);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);
}

