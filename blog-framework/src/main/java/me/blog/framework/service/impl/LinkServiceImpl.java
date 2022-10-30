package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.constants.SystemConstants;
import me.blog.framework.mapper.LinkMapper;
import me.blog.framework.domain.entity.Link;
import me.blog.framework.service.LinkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 链接(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public List<Link> allLink() {
        // 查询所有审核通过的
        return this.list(
                Wrappers.<Link>lambdaQuery()
                        .eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL)
        );
    }
}

