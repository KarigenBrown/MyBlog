package me.myblog.framework.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author Karigen B
 * @create 2022-11-06 16:17
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Long id;
    private Long articleId;
    private Long rootId;
    private String content;
    private Long toCommentUserId;
    private String toCommentUserName;
    private Long toCommentId;
    private Long createBy;
    private String username;
    private Date createTime;
    private List<CommentVo> children;
}
