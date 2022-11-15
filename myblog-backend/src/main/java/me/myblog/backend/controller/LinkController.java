package me.myblog.backend.controller;

import me.myblog.framework.service.LinkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 链接(Link)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:14:14
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

