package me.myblog.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.myblog.framework.domain.entity.Comment;

import java.util.List;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
public interface CommentService extends IService<Comment> {

    List<Comment> commentList(String commentType, Long articleId, Integer pageNumber, Integer pageSize);

    List<Comment> children(Long rootId);

    void addComment(Comment comment);
}

