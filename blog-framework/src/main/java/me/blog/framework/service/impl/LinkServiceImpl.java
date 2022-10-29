package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.mapper.LinkMapper;
import me.blog.framework.domain.entity.Link;
import me.blog.framework.service.LinkService;
import org.springframework.stereotype.Service;

/**
 * 链接(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

}

