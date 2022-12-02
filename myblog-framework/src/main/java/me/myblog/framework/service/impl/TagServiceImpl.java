package me.myblog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.myblog.framework.mapper.TagMapper;
import me.myblog.framework.domain.entity.Tag;
import me.myblog.framework.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public List<Tag> pageTagList(Integer pageNumber, Integer pageSize, String name, String remark) {
        // 分页查询
        return this.page(
                new Page<>(pageNumber, pageSize),
                Wrappers.<Tag>lambdaQuery()
                        .like(StringUtils.hasText(name), Tag::getName, name)
                        .like(StringUtils.hasText(remark), Tag::getRemark, remark)
        ).getRecords();
        // 封装数据返回

    }
}

