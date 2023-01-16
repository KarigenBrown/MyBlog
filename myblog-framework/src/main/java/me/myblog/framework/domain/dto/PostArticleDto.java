package me.myblog.framework.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import me.myblog.framework.domain.entity.Tag;

import java.util.List;

/**
 * @author Karigen B
 * @create 2023-01-07 14:59
 */
@Data
@ApiModel
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PostArticleDto {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    private String isTop;
    private String status;
    private Long viewCount;
    private String isComment;
    private List<Long> tagIds;
}
