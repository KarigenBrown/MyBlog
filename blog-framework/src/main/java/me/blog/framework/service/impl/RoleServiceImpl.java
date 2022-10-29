package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.mapper.RoleMapper;
import me.blog.framework.domain.entity.Role;
import me.blog.framework.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}

