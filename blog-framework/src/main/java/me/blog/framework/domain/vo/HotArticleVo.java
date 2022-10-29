package me.blog.framework.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Karigen B
 * @create 2022-10-29 21:09
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    private String title;
    private Long viewCount;
}
