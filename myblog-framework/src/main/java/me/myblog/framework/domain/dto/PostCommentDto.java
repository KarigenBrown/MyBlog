package me.myblog.framework.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Karigen B
 * @create 2022-11-15 13:54
 */
@Data
@ApiModel(description = "添加评论dto")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDto {
    private Long id;
    @ApiModelProperty(notes = "评论类型(0代表文章评论,1代表链接评论)")
    private String type;
    @ApiModelProperty(notes = "文章id")
    private Long articleId;
    private Long rootId;
    private String content;
    private Long toCommentUserId;
    private Long toCommentId;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
    private Integer deleteFlag;
}
