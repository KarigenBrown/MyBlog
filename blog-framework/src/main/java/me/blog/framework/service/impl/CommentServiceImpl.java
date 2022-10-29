package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.mapper.CommentMapper;
import me.blog.framework.entity.Comment;
import me.blog.framework.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}

