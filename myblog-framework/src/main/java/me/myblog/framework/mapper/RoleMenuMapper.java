package me.myblog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.myblog.framework.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

