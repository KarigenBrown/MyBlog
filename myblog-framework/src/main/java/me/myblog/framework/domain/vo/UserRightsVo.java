package me.myblog.framework.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Karigen B
 * @create 2022-11-16 18:36
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRightsVo {
    private List<String> permissions;
    private List<String> roles;
    private UserDetailsVo user;
}
