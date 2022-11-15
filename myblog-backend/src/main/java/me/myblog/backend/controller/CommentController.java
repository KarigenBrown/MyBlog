package me.myblog.backend.controller;

import me.myblog.framework.service.CommentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:14:02
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Autowired
    private CommentService commentService;

}

