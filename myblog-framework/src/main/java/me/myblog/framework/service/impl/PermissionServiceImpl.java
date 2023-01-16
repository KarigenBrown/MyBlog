package me.myblog.framework.service.impl;

import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.service.PermissionService;
import me.myblog.framework.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * @author Karigen B
 * @create 2023-01-07 17:57
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    /**
     * 判断当前用户是否具有该permission
     *
     * @param permission 要判断的权限
     * @return
     */
    @Override
    public boolean hasPermission(String permission) {
        // 如果是超级管理员直接返回true
        return SecurityUtils.isAdministrator()
                // 否则获取当前登陆用户所具有的权限列表
                || SecurityUtils.getLoginUser().getPermissions().contains(permission);
    }
}
