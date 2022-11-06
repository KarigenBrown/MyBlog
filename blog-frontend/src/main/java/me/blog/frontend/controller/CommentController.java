package me.blog.frontend.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.blog.framework.constants.SystemConstants;
import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.Comment;
import me.blog.framework.domain.vo.CommentVo;
import me.blog.framework.domain.vo.PageVo;
import me.blog.framework.service.CommentService;
import me.blog.framework.service.UserService;
import me.blog.framework.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    /**
     * 根据根评论的ID查询所对应的子评论的集合
     *
     * @param rootId:根评论的ID
     * @return
     */
    private List<CommentVo> children(Long rootId) {
        List<Comment> comments = commentService.children(rootId);
        return this.toCommentVoList(comments);
    }

    private List<CommentVo> toCommentVoList(List<Comment> comments) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        // 遍历vo集合
        for (CommentVo commentVo : commentVos) {
            // 通过createBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            // 通过toCommentUserId查询用户的昵称并赋值
            // 如果toCommentUserId不为-1才进行查询
            if (!Objects.equals(commentVo.getToCommentUserId(), SystemConstants.ROOT_COMMENT)) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

    public Response<PageVo> commentList(String commentType, Long articleId, Integer pageNumber, Integer pageSize) {
        List<Comment> comments = commentService.commentList(commentType, articleId, pageNumber, pageSize);
        List<CommentVo> data = toCommentVoList(comments);
        // 查询所有根评论对应的子评论集合,并且赋值给对应的属性
        for (CommentVo commentVo : data) {
            // 查询对应的子评论
            List<CommentVo> children = this.children(commentVo.getId());
            // 赋值
            commentVo.setChildren(children);
        }
        return Response.ok(new PageVo(data, data.size()));
    }

    @GetMapping("/articleCommentList")
    public Response<PageVo> articleCommentList(@RequestParam("articleId") Long articleId,
                                               @RequestParam("pageNumber") Integer pageNumber,
                                               @RequestParam("pageSize") Integer pageSize) {
        return this.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNumber, pageSize);
    }

    @PostMapping
    public Response<Object> addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return Response.ok(null);
    }

    @GetMapping("/linkCommentList")
    public Response<PageVo> linkCommentList(@RequestParam("pageNumber") Integer pageNumber,
                                            @RequestParam("pageSize") Integer pageSize) {
        return this.commentList(SystemConstants.LINK_COMMENT, null, pageNumber, pageSize);
    }
}

