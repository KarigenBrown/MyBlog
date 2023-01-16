package me.myblog.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.Link;
import me.myblog.framework.domain.vo.PageVo;
import me.myblog.framework.service.LinkService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Response<PageVo> page(@PathVariable("pageNumber") Integer pageNumber,
                                 @PathVariable("pageSize") Integer pageSize,
                                 @RequestParam("name") String name,
                                 @RequestParam("status") String status) {
        // TODO 封装进service
        List<Link> links = linkService.lambdaQuery()
                .like(StringUtils.hasText(name), Link::getName, name)
                .eq(StringUtils.hasText(status), Link::getStatus, status)
                .page(new Page<>(pageNumber, pageSize))
                .getRecords();
        PageVo data = new PageVo(links, links.size());
        return Response.ok(data);
    }

    @PostMapping
    public Response<Object> postLink(@RequestBody Link link) {
        linkService.save(link);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response<Link> getLinkById(@PathVariable(value = "id") Long id) {
        Link link = linkService.getById(id);
        return Response.ok(link);
    }

    @PutMapping
    public Response<Object> putLink(@RequestBody Link link) {
        linkService.updateById(link);
        return Response.ok();
    }

    @PutMapping("/status")
    public Response<Object> changeLinkStatus(@RequestBody Link link) {
        linkService.updateById(link);
        return Response.ok();
    }

    @DeleteMapping("/{id}")
    public Response<Object> deleteLinkById(@PathVariable Long id) {
        linkService.removeById(id);
        return Response.ok();
    }

}

