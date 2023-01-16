package me.myblog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.myblog.framework.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    List<Long> selectRoleIdsByUserId(Long userId);
}

