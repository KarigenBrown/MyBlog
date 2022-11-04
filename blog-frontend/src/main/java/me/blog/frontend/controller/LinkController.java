package me.blog.frontend.controller;

import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.Link;
import me.blog.framework.domain.vo.LinkVo;
import me.blog.framework.service.LinkService;
import me.blog.framework.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @GetMapping("/allLinks")
    public Response<List<LinkVo>> allLink() {
        List<Link> links = linkService.allLink();

        // 转换成vo
        List<LinkVo> data = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return Response.ok(data);
    }

}

