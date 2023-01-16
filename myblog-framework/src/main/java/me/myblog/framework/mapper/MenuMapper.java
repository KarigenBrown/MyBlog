package me.myblog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.myblog.framework.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermissionsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);
}

