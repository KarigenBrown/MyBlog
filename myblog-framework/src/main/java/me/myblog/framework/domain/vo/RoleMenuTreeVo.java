package me.myblog.framework.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Karigen B
 * @create 2023-01-15 17:31
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeVo {
    private List<Long> checkedKeys;
    private List<MenuTreeVo> menus;
}
