package me.myblog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import me.myblog.framework.mapper.CommentMapper;
import me.myblog.framework.domain.entity.Comment;
import me.myblog.framework.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public List<Comment> commentList(String commentType, Long articleId, Integer pageNumber, Integer pageSize) {
        // 分页查询
        return this.page(
                new Page<>(pageNumber, pageSize),
                // 查询对应文章的根评论
                Wrappers.<Comment>lambdaQuery()
                        // 对articleId进行判断
                        .eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId)
                        // 根评论rootId为-1
                        .eq(Comment::getRootId, SystemConstants.ROOT_COMMENT)
                        // 评论类型
                        .eq(Comment::getType, commentType)
                        .orderByAsc(Comment::getCreateTime)
        ).getRecords();
    }

    @Override
    public List<Comment> children(Long rootId) {
        return this.list(
                Wrappers.<Comment>lambdaQuery()
                        .eq(Comment::getRootId, rootId)
                        .orderByAsc(Comment::getCreateTime)
        );
    }

    @Override
    public void addComment(Comment comment) {
        // 评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(HttpCodeEnum.CONTENT_NOT_NULL);
        }
        this.save(comment);
    }
}

