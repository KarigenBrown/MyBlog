package me.myblog.backend.controller;

import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.dto.ListTagDto;
import me.myblog.framework.domain.entity.Tag;
import me.myblog.framework.domain.vo.PageVo;
import me.myblog.framework.service.TagService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @GetMapping("/list")
    public Response<PageVo> list(@RequestParam("pageNumber") Integer pageNumber,
                                 @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam ListTagDto listTagDto) {
        List<Tag> data = tagService.pageTagList(pageNumber, pageSize, listTagDto.getName(), listTagDto.getRemark());
        PageVo pageVo = new PageVo(data, data.size());
        return Response.ok(pageVo);
    }

}

