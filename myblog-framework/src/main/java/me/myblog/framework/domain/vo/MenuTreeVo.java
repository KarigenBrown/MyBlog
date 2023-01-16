package me.myblog.framework.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Karigen B
 * @create 2023-01-15 16:53
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeVo {
    private Long id;
    private String label;
    private Long parentId;
    private List<MenuTreeVo> children;
}
