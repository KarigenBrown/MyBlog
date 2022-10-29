package me.blog.frontend.controller;

import me.blog.framework.service.LinkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 链接(Link)表控制层
 *
 * @author makejava
 * @since 2022-10-29 18:36:21
 */
@RestController
@RequestMapping("/link")
public class LinkController {
    /**
     * 服务对象
     */
    @Autowired
    private LinkService linkService;

}

