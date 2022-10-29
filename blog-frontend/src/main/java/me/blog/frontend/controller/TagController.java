package me.blog.frontend.controller;

import me.blog.framework.service.TagService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 标签(Tag)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    /**
     * 服务对象
     */
    @Autowired
    private TagService tagService;

}

