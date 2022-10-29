package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.mapper.TagMapper;
import me.blog.framework.entity.Tag;
import me.blog.framework.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

