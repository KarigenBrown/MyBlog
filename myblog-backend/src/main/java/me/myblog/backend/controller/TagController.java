package me.myblog.backend.controller;

import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.dto.ListTagDto;
import me.myblog.framework.domain.entity.Tag;
import me.myblog.framework.domain.vo.PageVo;
import me.myblog.framework.domain.vo.TagVo;
import me.myblog.framework.service.TagService;
import me.myblog.framework.utils.BeanCopyUtils;
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

    @GetMapping("/page")
    public Response<PageVo> page(@RequestParam("pageNumber") Integer pageNumber,
                                 @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam ListTagDto listTagDto) {
        List<Tag> data = tagService.pageTagList(pageNumber, pageSize, listTagDto.getName(), listTagDto.getRemark());
        PageVo pageVo = new PageVo(data, data.size());
        return Response.ok(pageVo);
    }

    @PostMapping
    public Response<Object> postTag(@RequestBody Tag tag) {
        tagService.save(tag);
        return Response.ok();
    }

    @DeleteMapping("/{id}")
    public Response<Object> deleteTagById(@PathVariable("id") Long id) {
        tagService.removeById(id);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response<Tag> getTagById(@PathVariable("id") Long id) {
        Tag data = tagService.getById(id);
        return Response.ok(data);
    }

    @PutMapping
    public Response<Object> updateTagById(@RequestBody Tag tag) {
        tagService.updateById(tag);
        return Response.ok();
    }

    @GetMapping("/all")
    public Response<List<TagVo>> getAllTags() {
        List<Tag> tags = tagService.list();
        List<TagVo> data = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return Response.ok(data);
    }

}

