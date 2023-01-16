package me.myblog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.myblog.framework.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:31
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

