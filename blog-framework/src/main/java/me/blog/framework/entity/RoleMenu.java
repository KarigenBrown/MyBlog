package me.blog.framework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu {
    //角色ID
    private Long roleId;
    //菜单ID
    private Long menuId;

}
