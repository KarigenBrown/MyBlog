package me.blog.framework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author makejava
 * @since 2022-10-29 18:26:31
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;

}
