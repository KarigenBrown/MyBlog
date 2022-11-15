package me.myblog.backend.controller;

import me.myblog.framework.service.TagService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 标签(Tag)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:15:00
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

