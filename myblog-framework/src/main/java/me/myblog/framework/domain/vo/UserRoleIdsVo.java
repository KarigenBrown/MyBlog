package me.myblog.framework.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import me.myblog.framework.domain.entity.Role;
import me.myblog.framework.domain.entity.User;

import java.util.List;

/**
 * @author Karigen B
 * @create 2023-01-16 14:17
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleIdsVo {
    private User user;
    private List<Role> roles;
    private List<Long> roleIds;
}
