package me.myblog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.myblog.framework.mapper.RoleMapper;
import me.myblog.framework.domain.entity.Role;
import me.myblog.framework.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否是管理员,如果是.返回集合中只需要有administer
        if (id == 1L) {
            return List.of("administer");
        }
        // 否则查询用户所具有的角色信息
        return baseMapper.selectRoleKeyByUserId(id);
    }
}

